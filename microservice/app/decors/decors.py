from fastapi import File, UploadFile, HTTPException
from fastapi.routing import APIRouter

import mimetypes
import pandas as pd
import pytesseract
from PIL import Image
import openpyxl
from io import BytesIO
from PyPDF2 import PdfReader
from docx import Document
from loguru import logger


router = APIRouter()

FILE_PROCESSOR = {
    "excel": lambda content: process_excel(content=content),
    "pdf": lambda content: process_pdf(content=content),
    "csv": lambda content: process_csv(content=content),
    "image": lambda content: process_image(content=content),
    "word": lambda content: process_word(content=content),
}


@router.post("/decode-file/")
async def decode_file(file: UploadFile = File(...)):
    try:
        file_content = await file.read()
        mime_type = mimetypes.guess_type(file.filename)
        
        logger.info(f"{mime_type=}")
        logger.info(f"{file.filename=}")
        if mime_type is None:
            raise HTTPException(status_code=400, detail="Неподерживаемый формат файла")
        
        handler_key = (
            "excel" if "excel" in mime_type or file.filename.endswith(".xlsx") else
            "pdf" if "pdf" in mime_type or file.filename.endswith(".pdf") else
            "csv" if "csv" in mime_type or file.filename.endswith(".csv") else
            "word" if "word" in mime_type or file.filename.endswith(".docx") else
            "image" if mime_type.startswith("image/") else
            None
        )
        logger.info(f"{handler_key}")
        
        if handler_key and handler_key in FILE_PROCESSOR:
            content = FILE_PROCESSOR[handler_key](file_content)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error processing file: {str(e)}")

    logger.info(
        {
            "filename": file.filename,
            "content": content
        }
    )

    return {
        "filename": file.filename,
        "content": content
    }


def process_excel(content: bytes) -> str:
    excel_file = BytesIO(content)
    df = pd.read_excel(excel_file, engine="openpyxl")
    return df.to_string(index=False)


def process_pdf(content: bytes) -> str:
    pdf_file = BytesIO(content)
    logger.info(pdf_file)
    
    reader = PdfReader(pdf_file)
    logger.info(reader)
    text = ""
    for page in reader.pages:
        text += page.extract_text()
        logger.info(text)
    return text.strip()


def process_csv(content: bytes) -> str:
    csv_file = BytesIO(content)
    df = pd.read_csv(csv_file)
    return df.to_string(index=False)


def process_image(content: bytes) -> str:
    image = Image.open(BytesIO(content))
    return pytesseract.image_to_string(image)


def process_word(content: bytes):
    docs_file = BytesIO(content)
    document = Document(docs_file)
    logger.info(document)
    return "\n".join([para.text for para in document.paragraphs])