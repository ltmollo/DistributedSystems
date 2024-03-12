from fastapi import FastAPI, HTTPException

from fastapi.responses import HTMLResponse
from starlette import status
from starlette.responses import JSONResponse

from WeatherClasses import Weather
from responses import generate_response
from validations import validate

import aiohttp

app = FastAPI()

@app.get("/start")
async def input_details():
    with open("templates/input.html", "r") as f:
        html_content = f.read()
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)

@app.get("/test_weather")
async def fetch_weather_data(location: str, date1: str, date2: str):
    with open('api_key.txt', 'r') as f:
        API_KEY = f.read().strip()

    url = f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{location}/{date1}/{date2}?key={API_KEY}"

    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            if response.status == 200:
                return await response.json()
            else:
                raise HTTPException(status_code=response.status, detail=f"Failed to fetch weather data: {response}")


@app.get("/weather")
async def get_weather(location: str, date1: str, date2: str):
    try:
        error = validate(location, date1, date2)
        if error is not None:
            return error
        weather_data = await fetch_weather_data(location, date1, date2)
        weather = Weather(weather_data)
        html_content = await generate_response(weather)
        return HTMLResponse(content=html_content, status_code=200)
    except HTTPException as e:
        return JSONResponse(status_code=e.status_code, content={"error": e.detail})