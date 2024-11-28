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


# @router.post("/postgres-connect/")
# async def connect_postgres(req: DBConnectionRequest):
#     db_url = f"postgresql+asyncpg://{req.username}:{req.password}@{req.host}:{req.port}/{req.database}"

#     try:
#         engine = create_async_engine(url=db_url, echo=True, future=True)
#         async_session = sessionmaker(engine, class_=AsyncSession, expire_on_commit=False)
        
#         table_data = {}
        
#         async with engine.begin() as conn:
#             result = await conn.execute(text(
#                 "SELECT table_name FROM information_schema.tables WHERE table_schema='public';"
#             ))
        
#         async for session in get_session(async_session):
#             results = await session.execute(
#                 text("SELECT table_name FROM information_schema.tables WHERE table_schema='public';")
#             )
#             tables = [row[0] for row in results.fetchall()]
        
#         for table in tables:
#             table_query = text(f"select * from {table}")
#             result = await session.execute(table_query)
#             rows = result.fetchall()
            
            
#             df = pd.DataFrame(rows, columns=result.keys())
#             table_data[table] = df
        
#         output_file = "output.csv"
        
#         # with pd.ExcelWriter(output_file) as writer:
#         #     for table, df in table_data.items():
#         #         df.to_excel(writer, sheet_name=table, index=False)
#         df.to_csv(output_file, index=False)
#         session.close()
        
#         return FileResponse(output_file, filename="data.xlsx", media_type="text/csv")

#     except Exception as e:
#         raise HTTPException(status_code=500, detail=f"Error connecting to the database: {str(e)}"

@router.post("/postgres-connect/")
async def connect_postgres(req: DBConnectionRequest):
    """Connect to a PostgreSQL database, execute a query, and return results as CSV."""
    db_url = f"postgresql+asyncpg://{req.username}:{req.password}@{req.host}:{req.port}/{req.database}"

    try:
        # Create the async engine and sessionmaker
        engine = create_async_engine(db_url, echo=True, future=True)
        async_session_maker = sessionmaker(engine, class_=AsyncSession, expire_on_commit=False)

        # Use get_session to manage the database session
        async for session in get_session(async_session_maker):
            result = await session.execute(text(req.query))
            rows = result.fetchall()

        # Convert results to a DataFrame and save as CSV
        df = pd.DataFrame(rows, columns=result.keys())
        output_file = "output.csv"
        df.to_csv(output_file, index=False)

        return FileResponse(output_file, filename="data.csv", media_type="text/csv")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error connecting to the database: {str(e)}")
