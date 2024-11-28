from fastapi import APIRouter, HTTPException
from connectPostgres.scheme.scheme import DBConnectionRequest
from connectPostgres.db.db import get_session
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine
from sqlalchemy import text
import pandas as pd
import os
from fastapi.responses import FileResponse

router = APIRouter()


@router.post("/postgres-connect/")
async def connect_postgres(req: DBConnectionRequest):   
    """Connect to a PostgreSQL database, execute a query, and return results as CSV."""
    db_url = f"postgresql+asyncpg://{req.username}:{req.password}@{req.host}:{req.port}/{req.database}"

    try:
        engine = create_async_engine(db_url, echo=True, future=True)
        async_session_maker = sessionmaker(engine, class_=AsyncSession, expire_on_commit=False)

        async for session in get_session(async_session_maker):
            result = await session.execute(text(req.query))
            rows = result.fetchall()

        header = ",".join(result.keys())  
        data = "\n".join([",".join(map(str, row)) for row in rows])  
        response_text = f"{header}\n{data}"
          
        return {
            "filename": "db_post",
            "content": response_text
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error connecting to the database: {str(e)}")
