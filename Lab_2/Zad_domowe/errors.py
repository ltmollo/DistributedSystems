from fastapi import HTTPException

def handle_http_exception(e: HTTPException):
    status_code = e.status_code
    detail = e.detail

    html_response = f"""
        <html>
        <head><title>Error {status_code}</title></head>
        <body style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
            <h1>Error: {status_code}</h1>
            <p>{detail}</p>
            <img src = https://http.dog/{status_code}.jpg width=1024px height=800px alt='dog http img' style="margin: auto" />
        </body>
        </html>
        """

    return html_response, status_code