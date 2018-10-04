import requests

class Scraper():
    BASE_URL = "https://rutgers.campuslabs.com/engage/api/discovery/search/organizations"
    CLUBS_URL = "%s?top=800" % BASE_URL

    def request_clubs(self):
        r = requests.get(self.CLUBS_URL)
        r = r.json()
        return r

    def create_organization_sets(self, clubs):
        categories = dict()

        for club in clubs:
            club_id = int(club["Id"])

            for category_id, category_name in zip(club["CategoryIds"], club["CategoryNames"]):
                if category_id not in categories:
                    categories[category_id] = {
                        "category_id": category_id,
                        "category_name": category_name,
                        "clubs": [ club_id ]
                    }
                else:
                    categories[category_id]["clubs"].append(club_id)

        return categories