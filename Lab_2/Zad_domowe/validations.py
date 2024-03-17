import re

from starlette import status
from datetime import datetime, timedelta
from fastapi import HTTPException


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

    date_order_error = validate_date_order(date1, date2)
    if date_order_error is not None:
        return date_order_error


def validate_location(location: str):
    pattern = re.compile(r'^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\s-'']*$')
    error = not bool(pattern.match(location))

    if error:
        raise HTTPException(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            detail="Invalid location format")


def validate_date(date_str):
    date = datetime.strptime(date_str, "%Y-%m-%d")
    two_weeks_from_now = datetime.now() + timedelta(weeks=2)
    error = date > two_weeks_from_now

    if error:
        raise HTTPException(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            detail="Date must by not later than 2 weeks from now")

def validate_date_order(date1_str, date2_str):
    date1 = datetime.strptime(date1_str, "%Y-%m-%d")
    date2 = datetime.strptime(date2_str, "%Y-%m-%d")

    if date2 < date1:
        raise HTTPException(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            detail="End date must be greater than or equal to start date")
