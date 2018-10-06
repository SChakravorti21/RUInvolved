
from scraper import Scraper
from db_client import MongoDBClient

scraper = Scraper()
clubs = scraper.request_clubs()["value"]
clubs = scraper.clean_clubs(clubs)

categories = scraper.create_organization_sets(clubs)
categories = [category for category_id, category in categories.items()]

dbclient = MongoDBClient()
# dbclient.clear_db()
# dbclient.insert_clubs(clubs)
# dbclient.insert_categories(categories)

from pprint import pprint

for club in dbclient.find_clubs_from_categories([ 6475 , 2384]):
    pprint(club)