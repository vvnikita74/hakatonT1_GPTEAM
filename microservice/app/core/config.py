from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    project_host: str
    project_port: int
    
    model_config = SettingsConfigDict(env_file=".env")


app_setting = Settings()