class Weather():
    def __init__(self, response):
        self.address = response.get('address')
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


