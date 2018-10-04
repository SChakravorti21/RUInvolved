
from scraper import Scraper

scraper = Scraper()
clubs = scraper.request_clubs()["value"]
categories = scraper.create_organization_sets(clubs)