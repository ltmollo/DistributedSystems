from fastapi import FastAPI, HTTPException
from fastapi.responses import HTMLResponse
from starlette import status
from starlette.responses import JSONResponse

from WeatherClasses import Weather, CurrentWeatherVisualCrossing, CurrentWeatherWeatherApi, CurrentWeather
from html_response import generate_response
from validations import validate
from auth import authenticate
from helpers import get_key, generate_json_response

API_KEYS_PATH = './api_keys.txt'
TOKENS_PATH = './tokens.txt'

app = FastAPI()


@app.get("/start")
async def input_details():
    with open("templates/input.html", "r") as f:
        html_content = f.read()
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)


@app.get("/test_weather")
async def fetch_weather_data_visual(app_token: str, location: str, date1: str = "", date2: str = ""):
    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error

    API_KEY = get_key(API_KEYS_PATH, 'VISUAL_CROSSING_APP_KEY')

    url = f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{location}/{date1}/{date2}?key={API_KEY}"

    return await generate_json_response(url)


@app.get("/test2_weather")
async def fetch_weather_data_api(app_token: str, latitude: str, longitude: str):
    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error

    API_KEY = get_key(API_KEYS_PATH, 'WEATHER_API_APP_KEY')

    url = f"https://api.weatherapi.com/v1/current.json?q={latitude}%2C{longitude}&key={API_KEY}"

    return await generate_json_response(url)


@app.get("/weather")
async def get_weather(app_token: str, location: str, date1: str = "", date2: str = ""):
    auth_error = authenticate(app_token)
    if auth_error:
        return auth_error
    try:

        error = validate(location, date1, date2)
        if error is not None:
            return error

        weather_data = await fetch_weather_data_visual(app_token, location, date1, date2)
        weather = Weather(weather_data)

        weather_data2 = await fetch_weather_data_visual(app_token, location)

        current_weather_visual = CurrentWeatherVisualCrossing(weather_data2.get('currentConditions'))

        api_data = await fetch_weather_data_api(app_token, weather.latitude, weather.longitude)
        current_weather_api = CurrentWeatherWeatherApi(api_data.get('current'))

        current_weather = CurrentWeather(current_weather_visual, current_weather_api)

        html_content = await generate_response(weather, current_weather)
        return HTMLResponse(content=html_content, status_code=200)
    except HTTPException as e:
        return JSONResponse(status_code=e.status_code, content={"error": e.detail})
