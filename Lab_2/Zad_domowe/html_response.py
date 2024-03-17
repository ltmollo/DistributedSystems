from WeatherClasses import Weather, CurrentWeather
from calculate import calculateFahrenheitToCelcius


def add_current_weather(current_weather: CurrentWeather):
    html_content = "<div style='margin: 10%; border: 1px solid black; text-align: center; height: 350px'>"
    html_content += "<h3>Current Weather</h3>"
    if current_weather.icon:
        html_content += f"<img src='{current_weather.icon}' alt='Weather Icon'>"

    html_content += f"<p>Temperature: {current_weather.temp}°C</p>"
    html_content += f"<p>Feels Like: {current_weather.feelslike}°C</p>"
    html_content += f"<p>Wind Speed: {current_weather.windspeed}km/h</p>"
    html_content += f"<p>Precipitation Probability: {current_weather.precipprob}%</p>"
    html_content += f"<p>Precipitation Type: {current_weather.preciptype}</p>"
    html_content += "</div>"
    return html_content


def choose_precip_color(precipprob):
    precip_color = 'lightgreen'
    if 30 >= precipprob < 60:
        precip_color = 'lightblue'
    elif precipprob >= 60:
        precip_color = '#FFC0CB'
    return precip_color


def choose_temp_color(weather_temp, current_temp):
    if weather_temp < current_temp:
        temp_color = 'lightcoral'
    elif weather_temp > current_temp:
        temp_color = 'lightgreen'
    else:
        temp_color = 'lightblue'
    return temp_color


def choose_feels_like_color(weather_feels_like, weather_temp):
    if weather_feels_like >= weather_temp:
        feelslike_color = 'lightgreen'
    elif abs(weather_feels_like - weather_temp) <= 1:
        feelslike_color = 'lightblue'
    else:
        feelslike_color = 'lightcoral'
    return feelslike_color


async def generate_response(weather: Weather, current_weather: CurrentWeather):
    html_content = "<body style='display: flex;'>"
    html_content += "<div style='width: 40%;'>"
    html_content += "<h1 style='color: blue;'>Weather Report</h1>"
    html_content += f"<h2>{weather.address}</h2>"

    for day in weather.days:
        html_content += "<table style='border: 1px solid black; border-collapse: collapse; width: 100%;'>"
        html_content += (f"<tr>"
                         f"<th colspan='5' style='background-color: lightgray; width: 20%;'>Date: {day.datetime}</th>"
                         f"</tr>")

        type_of_data = ["Time", "Temperature", "Feels like", "Precipitation"]
        html_content += "<tr>"

        for type in type_of_data:
            html_content += f"<th style='width: 10%;'>{type}</th>"
        html_content += "</tr>"

        for hour in day.hours:
            precip_color = choose_precip_color(hour.precipprob)

            weather_temp = calculateFahrenheitToCelcius(hour.temp)
            weather_feels_like = calculateFahrenheitToCelcius(hour.feelslike)
            current_temp = current_weather.temp

            temp_color = choose_temp_color(weather_temp, current_temp)
            feels_like_color = choose_feels_like_color(weather_feels_like, weather_temp)

            html_content += "<tr>"

            data = [("white", hour.datetime[:-3]), (temp_color, str(weather_temp) + "°C"),
                    (feels_like_color, str(weather_feels_like) + "°C"),
                    (precip_color, None if (hour.preciptype is None or hour.precipprob == 0)
                    else str(hour.precipprob)[:-2] + '% ' + ', '.join(hour.preciptype))]

            for color, value in data:
                html_content += f"<td style='border: 1px solid black; text-align: center; width: 10%; background-color: {color};'>{value}</td>"
            html_content += "</tr>"
        html_content += "<br></br>"
    html_content += "</table>"
    html_content += "</div>"

    html_content += add_current_weather(current_weather)

    html_content += "</body>"

    return html_content
