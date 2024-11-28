FROM python:3.11-slim 

WORKDIR /app
COPY requirements.txt .
COPY app/ .

RUN pip3 install -r requirements.txt --no-cache-dir

EXPOSE 8000

CMD ["python", "main.py"]