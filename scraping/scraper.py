import requests
import uuid

class Scraper():
    BASE_URL = "https://rutgers.campuslabs.com/engage/api/discovery/search/organizations"
    CLUBS_URL = "%s?top=800" % BASE_URL

    def request_clubs(self):
        r = requests.get(self.CLUBS_URL)
        r = r.json()
        return r

    def clean_clubs(self, clubs):
        for club in clubs:
            club.pop("@search.score", None)
            club["Id"] = int(club["Id"])
            club["generated_id"] = uuid.uuid4().hex[:6]
            club["geolocation"] = {
                'lat': 40.502573,
                'lng': -74.452631
            }

        return clubs

    def create_organization_sets(self, clubs):
        categories = dict()

        for club in clubs:
            club_id = int(club["Id"])

            for category_id, category_name in zip(club["CategoryIds"], club["CategoryNames"]):
                if category_id not in categories:
                    categories[category_id] = {
                        "category_id": int(category_id),
                        "category_name": category_name,
                        "clubs": [ club_id ]
                    }
                else:
                    categories[category_id]["clubs"].append(club_id)

        return categories