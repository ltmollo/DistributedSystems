from WeatherClasses import Weather, CurrentWeather
from calculate import calculateFahrenheitToCelcius

async def generate_response(weather: Weather, current_weather: CurrentWeather):
    html_content = "<body style='display: flex;'>"
    html_content += "<div style='width: 40%;'>"
    html_content += "<h1 style='color: blue;'>Weather Report</h1>"
    html_content += f"<h2>{weather.address}</h2>"

    for day in weather.days:
        html_content += "<table style='border: 1px solid black; border-collapse: collapse; width: 100%;'>"
        html_content += f"<tr><th colspan='5' style='background-color: lightgray; width: 20%;'>Date: {day.datetime}</th></tr>"
        html_content += "<tr><th style='width: 10%;'>Time</th><th style='width: 10%;'>Temperature</th><th style='width: 10%;'>Feels Like</th><th style='width: 10%;'>Precipitation</th></tr>"

        for hour in day.hours:
            precip_color = 'lightgreen'
            if 30 >= hour.precipprob < 60:
                precip_color = 'lightblue'
            elif hour.precipprob >= 60:
                precip_color = '#FFC0CB'

            html_content += "<tr>"
            html_content += f"<td style='border: 1px solid black; text-align: center; width: 10%;'>{hour.datetime[:-3]}</td>"
            html_content += f"<td style='border: 1px solid black; text-align: center; width: 10%;'>{calculateFahrenheitToCelcius(hour.temp)}</td>"
            html_content += f"<td style='border: 1px solid black; text-align: center; width: 10%;'>{calculateFahrenheitToCelcius(hour.feelslike)}</td>"
            html_content += f"<td style='border: 1px solid black; text-align: center; width: 10%; background-color: {precip_color};'>{None if (hour.preciptype is None or hour.precipprob == 0) else str(hour.precipprob)[:-2] + "% " + ", ".join(hour.preciptype)}</td>"
            html_content += "</tr>"
        html_content += "<br></br>"
    html_content += "</table>"
    html_content += "</div>"

    html_content += "<div style='margin: 10%; border: 1px solid black; text-align: center; height: 350px'>"
    html_content += "<h3>Current Weather</h3>"
    if current_weather.icon:
        html_content += f"<img src='{current_weather.icon}' alt='Weather Icon'>"
    html_content += f"<p>Temperature: {current_weather.temp}</p>"
    html_content += f"<p>Feels Like: {current_weather.feelslike}</p>"
    html_content += f"<p>Wind Speed: {current_weather.windspeed}</p>"
    html_content += f"<p>Precipitation Probability: {current_weather.precipprob}</p>"
    html_content += f"<p>Precipitation Type: {current_weather.preciptype}</p>"
    html_content += "</div>"

    html_content += "</body>"

    return html_content


