from RUInvolvedApp.db import dbclient
from flask import Blueprint, request, Response, jsonify

mod = Blueprint('admin', __name__)

@mod.route('/club/update-location', methods=['POST'])
def update_club_location():
    posted_data = request.get_json()

    club_id = posted_data.get('club_id')
    location = {
        'lat': posted_data.get('lat'),
        'lng': posted_data.get('lng')
    }

    dbclient.clubs.update_one({ 'Id': club_id }, { '$set': { 'geolocation': location } })
    return jsonify(location)
