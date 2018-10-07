from RUInvolvedApp.db import dbclient
from flask import Blueprint, request, Response, json

mod = Blueprint('admin', __name__)

@mod.route('/club/update-location', methods=['POST'])
def update_club_location():
    posted_data = request.json
    print(json)

    club_id = posted_data['club_id']
    location = {
        lat: posted_data['lat'],
        lng: posted_data['lng']
    }

    updated_id = dbclient.clubs.update_one({ 'Id': club_id }, { '$set': { 'geolocation': location } })
    print(updated_id)

    return Response(status=200)
