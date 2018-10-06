from RUInvolvedApp.db import dbclient
from flask import Blueprint, request, Response, json

mod = Blueprint('clubs', __name__)

@mod.route('/clubs/', methods=['GET'])
def get_clubs_for_categories():
    categories = request.args.get('categories')
    clubs = dbclient.find_clubs_from_categories([6475])

    from pprint import pprint
    pprint(clubs)

    return json.dumps(clubs)

@mod.route('/clubs/categories', methods=['GET'])
def get_all_categories():
    categories = dbclient.categories.find({}, { '_id': 0 })
    return json.dumps([category for category in categories])
