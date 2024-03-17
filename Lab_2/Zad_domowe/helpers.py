import aiohttp
from fastapi import HTTPException


def get_key(filepath: str, key: str) -> str:
    with open(filepath, 'r') as f:
        for line in f:
            wanted_key, value = line.strip().split(' = ')
            if wanted_key == key:
                return value


async def generate_json_response(url: str):
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            if response.status == 200:
                return await response.json()
            else:
                error_message = await response.text()
                raise HTTPException(status_code=response.status,
                                    detail=f"Failed to fetch weather data from: {url}. Server response: {error_message}")
