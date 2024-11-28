import uvicorn
from fastapi import FastAPI
from fastapi.responses import ORJSONResponse
from fastapi.middleware.cors import CORSMiddleware

from core.config import app_setting
from decors.decors import router
from connectPostgres import connect
from parser.scheme import scheme


app = FastAPI(
    docs_url="/api/openapi",
    openapi_url="/api/openapi.json",
    default_response_class=ORJSONResponse
)


origin = [
    "http://localhost",
    "http://localhost:8080",
    "http://localhost:8081",
    "*"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["GET", "POST", "PATCH", "DELETE"],
)


app.include_router(router, prefix="/api/v1", tags=["file"])
app.include_router(connect.router, prefix="/api/v1", tags=["bd"])
app.include_router(scheme.router, prefix="/api/v1", tags=["parser"])



if __name__ == "__main__":
    uvicorn.run(
        'main:app',
        host=app_setting.project_host,
        port=app_setting.project_port,
        reload=True
    )
