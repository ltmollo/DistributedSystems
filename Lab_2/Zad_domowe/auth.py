from fastapi import HTTPException

TOKENS_FILE = "tokens.txt"
def load_tokens_from_file(filename: str):
    with open(filename, "r") as file:
        lines = file.readlines()
        return [line.strip() for line in lines if line.strip()]

tokens = load_tokens_from_file(TOKENS_FILE)

def authenticate(token: str):
    if token not in tokens:
        raise HTTPException(status_code=401, detail="Invalid app token")
    return None
