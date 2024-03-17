from calculate import calculateMean, calculateMphToKPh, calculateFahrenheitToCelcius


class Weather():
    def __init__(self, response):
        self.address = response.get('address')
        self.latitude = response.get('latitude')
        self.longitude = response.get('longitude')
        self.days = [WeatherDay(day) for day in response.get('days')]

    def __str__(self):
        days_str = ", ".join([str(day) for day in self.days])
        return f"Weather: address={self.address}, days=[{days_str}]"


class WeatherDay():
    def __init__(self, day):
        self.datetime = day.get('datetime')
        self.tempmax = day.get('tempmax')
        self.tempmin = day.get('tempmin')
        self.sunrise = day.get('sunrise')
        self.sunset = day.get('sunset')
        self.hours = [WeatherDayHour(hour) for hour in day.get('hours')]


class WeatherDayHour():
    def __init__(self, hour):
        self.datetime = hour.get('datetime')
        self.temp = hour.get('temp')
        self.feelslike = hour.get('feelslike')
        self.precipprob = hour.get('precipprob')
        self.preciptype = hour.get('preciptype')


class CurrentWeatherVisualCrossing():
    def __init__(self, data):
        self.temp = data.get('temp')
        self.feelslike = data.get('feelslike')
        self.precipprob = data.get('precipprob')
        self.preciptype = data.get('preciptype')
        self.windspeed = data.get('windspeed')


class CurrentWeatherWeatherApi():
    def __init__(self, data):
        self.temp = data.get('temp_f')
        self.feelslike = data.get('feelslike_f')
        self.windspeed = data.get('wind_mph')
        self.icon = data.get('condition').get('icon')


class CurrentWeather():
    def __init__(self, weather_visual: CurrentWeatherVisualCrossing, weather_api: CurrentWeatherWeatherApi):
        self.temp = self.calculate_temp(weather_visual.temp, weather_api.temp)
        self.feelslike = self.calculate_temp(weather_visual.feelslike, weather_api.feelslike)
        self.windspeed = self.calculate_wind_speed(weather_visual.windspeed, weather_api.windspeed)
        self.precipprob = weather_visual.precipprob
        self.preciptype = weather_visual.preciptype
        self.icon = weather_api.icon

    def calculate_temp(self, temp1, temp2):
        temp_f = calculateMean(temp1, temp2)
        return calculateFahrenheitToCelcius(temp_f)

    def calculate_wind_speed(self, wind_speed1, wind_speed2):
        wind_speed = calculateMean(wind_speed1, wind_speed2)
        return calculateMphToKPh(wind_speed)
