
from scraper import Scraper
from db_client import MongoDBClient

scraper = Scraper()
clubs = scraper.request_clubs()["value"]

categories = scraper.create_organization_sets(clubs)
categories = [category for category_id, category in categories.items()]

dbclient = MongoDBClient()
dbclient.clear_db()
dbclient.insert_clubs(clubs)
dbclient.insert_categories(categories)