import re

from starlette import status
from starlette.responses import JSONResponse
from datetime import datetime, timedelta

def validate(location: str, date1: str, date2: str):
    location_error = validate_location(location)
    if location_error is not None:
        return location_error

    if date1 != "":
        date1_error = validate_date(date1)
        if date1_error is not None:
            return date1_error

    if date2 == "":
        return

    date2_error = validate_date(date2)
    if date2_error is not None:
        return date2_error

def validate_location(location: str):
    pattern = re.compile(r'^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\s-'']*$')
    error = not bool(pattern.match(location))

    if error:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                     content={"error Message": "Invalid location format"})


def validate_date(date_str):

    date = datetime.strptime(date_str, "%Y-%m-%d")
    two_weeks_from_now = datetime.now() + timedelta(weeks=2)
    error = date > two_weeks_from_now

    if error:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            content={"error Message": "Date must by not late than 2 weeks from now"})
