from pymongo import MongoClient
from config import DB_CLIENT_URI

class MongoDBClient():
    def __init__(self):
        self.client = MongoClient(DB_CLIENT_URI)
        self.db = self.client["ruinvolved"]
        self.clubs = self.db["clubs"]
        self.categories = self.db["categories"]

    def find_clubs_from_categories(self, categories):
        return self.categories.aggregate([
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
                    '_id': False,
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
                    '_id': False,
                    'clbs': False
                }
            }
        ])

    def insert_clubs(self, clubs):
        self.clubs.insert_many(clubs)

    def insert_categories(self, categories):
        self.categories.insert_many(categories)

    def clear_db(self):
        self.clubs.remove({})
        self.categories.remove({})