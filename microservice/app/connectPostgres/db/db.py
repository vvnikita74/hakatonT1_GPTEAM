from typing import AsyncGenerator
from core.config import app_setting
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine


async def init_bd(url):
    engine = create_async_engine(
        url, echo=True, future=True)
    async_session = sessionmaker(
        engine, class_=AsyncSession, expire_on_commit=False
    )




async def get_session(async_session_maker: sessionmaker) -> AsyncGenerator[AsyncSession, None]:
    """Generates an async session."""
    async with async_session_maker() as session:
        yield session