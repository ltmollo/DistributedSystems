from pydantic import BaseModel
from fastapi import Body, FastAPI, status
from fastapi.responses import JSONResponse
from typing import List

app=FastAPI( )

class Vote(BaseModel):
    vote: int

class Poll(BaseModel):
    question: str
    votes: List[Vote]


polls = {}


@app.get("/poll")
async def get_pools():
    return polls


@app.post("/poll")
async def create_poll(poll: Poll):
    polls[len(polls)] = poll
    return {"message": "Poll created successfully"}


@app.get("/poll/{poll_id}")
async def get_pools(poll_id: int):
    return polls[poll_id]


@app.delete("/poll/{poll_id}")
async def delete_and_error(poll_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)
    else:
        del polls[poll_id]
        return {"message": " deleted successfully"}


@app.get("/poll/{poll_id}/vote")
async def vote_poll(poll_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes = polls[poll_id].votes

    return votes


@app.post("/poll/{poll_id}/vote")
async def add_vote(poll_id: int, vote: Vote):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    if vote.vote != 0:
        vote.vote = 1
    polls[poll_id].votes.append(vote)
    return {"message": "Votes recorded successfully"}


@app.get("/poll/{poll_id}/vote/{vote_id}")
async def get_vote(poll_id: int, vote_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes = polls[poll_id].votes
    if len(votes) < vote_id:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    return votes[vote_id]


@app.put("/poll/{poll_id}/vote/{vote_id}")
async def vote_change(poll_id: int, vote_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes = polls[poll_id].votes
    if len(votes) < vote_id:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes[vote_id].vote = 1 - votes[vote_id].vote

    return {"message" : "Changed successfully"}


@app.delete("/poll/{poll_id}/vote/{vote_id}")
async def delete_vote(poll_id: int, vote_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes = polls[poll_id].votes
    if len(votes) < vote_id:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    del votes[vote_id]

    return {"message" : "Deleted successfully"}


@app.get("/poll/{poll_id}/votesummary")
async def sum_votes(poll_id: int):
    if poll_id not in polls:
        return JSONResponse(status_code=status.HTTP_404_NOT_FOUND, content=return_content)

    votes = polls[poll_id].votes
    yes = 0
    all_votes = len(votes)
    for vote in votes:
        yes += vote.vote

    result = "Yes: " + str(yes) + ", No: " + str(all_votes - yes) + ", Total: " + str(all_votes)

    return result