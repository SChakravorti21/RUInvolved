from pymongo import MongoClient
from RUInvolvedApp.config import DB_CLIENT_URI

class MongoDBClient():
    def __init__(self):
        self.client = MongoClient(DB_CLIENT_URI)
        self.db = self.client["ruinvolved"]
        self.clubs = self.db["clubs"]
        self.categories = self.db["categories"]

    def find_clubs_from_categories(self, categories):
        clubs = self.categories.aggregate([
            { 
                '$match': { 
                    'category_id': { '$in': categories } 
                } 
            }, 
            { 
                '$unwind': '$clubs' 
            }, 
            { 
                '$group': {
                    '_id': 0,
                    'clbs': { '$addToSet': '$clubs' } 
                }
            },
            {
                '$unwind': '$clbs'
            },
            {
                '$lookup': {
                    'from': 'clubs',
                    'localField': 'clbs',
                    'foreignField': 'Id',
                    'as': 'found'
                }
            },
            { 
                '$unwind': '$found'
            },
            {
                '$project': {
                    '_id': 0,
                    'clbs': False
                }
            }
        ])

        # `clubs` is a cursor, so we need to iterate through it
        # anyways to turn them into a list. Also remove the _id
        # since it is not serializable
        cleaned_clubs = []
        for club in clubs:
            club["found"].pop('_id', None)
            cleaned_clubs.append(club["found"])

        return cleaned_clubs

    def insert_clubs(self, clubs):
        self.clubs.insert_many(clubs)

    def insert_categories(self, categories):
        self.categories.insert_many(categories)

    def clear_db(self):
        self.clubs.remove({})
        self.categories.remove({})