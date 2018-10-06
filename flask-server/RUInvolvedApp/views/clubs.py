from RUInvolvedApp.db import dbclient
from flask import Blueprint, Response, json

mod = Blueprint('clubs', __name__)

@mod.route('/clubs/categories', methods=['GET'])
def get_all_categories():
    categories = dbclient.categories.find({}, { '_id': 0 })
    return json.dumps([category for category in categories])
