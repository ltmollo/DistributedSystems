from fastapi import FastAPI, HTTPException

from fastapi.responses import HTMLResponse
from starlette import status
from starlette.responses import JSONResponse

from WeatherClasses import Weather, CurrentWeatherVisualCrossing, CurrentWeatherWeatherApi, CurrentWeather
from responses import generate_response
from validations import validate
from auth import authenticate

import aiohttp

app = FastAPI()

@app.get("/start")
async def input_details():
    with open("templates/input.html", "r") as f:
        html_content = f.read()
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)

@app.get("/test_weather")
async def fetch_weather_data(app_token: str, location: str, date1: str, date2: str):

    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error

    with open('api_keys.txt', 'r') as f:
        for line in f:
            key, value = line.strip().split(' = ')
            if key == 'visualcrossing_app_key':
                API_KEY = value
                break

    url1 = f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{location}/{date1}/{date2}?key={API_KEY}"

    async with aiohttp.ClientSession() as session:
        async with session.get(url1) as response:
            if response.status == 200:
                return await response.json()
            else:
                raise HTTPException(status_code=response.status, detail=f"Failed to fetch weather data: {response}")

@app.get("/test2_weather")
async def fetch_weather_data2(app_token: str, latitude: str, longitude: str):

    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error

    with open('api_keys.txt', 'r') as f:
        for line in f:
            key, value = line.strip().split(' = ')
            if key == 'wheatherApi_app_key':
                API_KEY = value
                break

    url2 = f"https://api.weatherapi.com/v1/current.json?q={latitude}%2C{longitude}&key={API_KEY}"

    async with aiohttp.ClientSession() as session:
        async with session.get(url2) as response:
            if response.status == 200:
                return await response.json()
            else:
                raise HTTPException(status_code=response.status, detail=f"Failed to fetch weather data: {response}")

@app.get("/weather")
async def get_weather(app_token: str, location: str, date1: str = "", date2: str = ""):

    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error
    try:
        error = validate(location, date1, date2)
        if error is not None:
            return error
        weather_data = await fetch_weather_data(app_token, location, date1, date2)
        weather = Weather(weather_data)
        current_weather_visual = CurrentWeatherVisualCrossing(weather_data.get('currentConditions'))

        api_data = await fetch_weather_data2(app_token, weather.latitude, weather.longitude)
        current_weather_api = CurrentWeatherWeatherApi(api_data.get('current'))

        current_weather = CurrentWeather(current_weather_visual, current_weather_api)

        html_content = await generate_response(weather, current_weather)
        return HTMLResponse(content=html_content, status_code=200)
    except HTTPException as e:
        return JSONResponse(status_code=e.status_code, content={"error": e.detail})