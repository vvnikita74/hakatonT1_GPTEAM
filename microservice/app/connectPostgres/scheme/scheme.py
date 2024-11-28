from pydantic import BaseModel, PostgresDsn


class DBConnectionRequest(BaseModel):
    host: str
    port: int
    username: str
    password: str
    database: str
    query: str | None = "SELECT * FROM employees;"
