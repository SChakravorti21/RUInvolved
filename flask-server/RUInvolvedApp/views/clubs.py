from RUInvolvedApp.db import dbclient
from flask import Blueprint, request, Response, json

mod = Blueprint('clubs', __name__)

@mod.route('/club/', methods=['GET'])
def get_club_with_id():
    club_id = request.args.get('id')
    club = dbclient.clubs.find_one({ 'generated_id': club_id })

    if not club:
        return Response(status=401)

    club.pop('_id', None)
    return json.dumps(club)

@mod.route('/clubs/', methods=['GET'])
def get_clubs_for_categories():
    categories = request.args.get('categories')

    # The categories come as a pseudo-array in the form of a string
    # in the following format: [Category Name 1,Category Name 2,...]
    # Remove the array brackets and split by the commas to get each 
    # category
    categories = categories[1:-1].split(',')
    
    clubs = dbclient.find_clubs_from_categories(categories)
    return json.dumps(clubs)

@mod.route('/clubs/categories', methods=['GET'])
def get_all_categories():
    categories = dbclient.categories.find({}, { '_id': 0 })
    return json.dumps([category for category in categories])
