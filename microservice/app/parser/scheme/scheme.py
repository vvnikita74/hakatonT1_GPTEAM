from pydantic import BaseModel, HttpUrl
from fastapi import APIRouter, HTTPException
import requests
from bs4 import BeautifulSoup


class URLRequest(BaseModel):
    url: HttpUrl
    
    
router = APIRouter()


@router.post("/parse/")
async def parse(request: URLRequest):
    url = request.url
    
    try:
        response = requests.get(url=url, timeout=10)
        response.raise_for_status()
        html_conent = response.text
        
        soup = BeautifulSoup(html_conent, 'html.parser')
        if meta := soup.find('meta', attrs={"name": "description"}):
            description = meta.get("content", "No description found")
        
        text_content = ' '.join(soup.stripped_strings)
        
        return {
            "filename": "poxui",
            "content": text_content[:500],  
        }
    except requests.exceptions.RequestException as e:
        raise HTTPException(status_code=400, detail=f"Failed to fetch the webpage: {e}")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An unexpected error occurred: {e}")