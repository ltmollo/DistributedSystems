from fastapi import HTTPException

def load_tokens_from_file(filename):
    with open(filename, "r") as file:
        lines = file.readlines()
        return [line.strip() for line in lines if line.strip()]  # Usuń puste linie i zbędne spacje

tokens = load_tokens_from_file("tokens.txt")

def authenticate(token: str):
    if token not in tokens:
        raise HTTPException(status_code=401, detail="Invalid app token")
    return None
