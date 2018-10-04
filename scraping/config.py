from os import environ

DB_USERNAME = environ["RUInvolved_Username"]
DB_PASSWORD = environ["RUInvolved_Password"]

DB_CLIENT_URI = "mongodb+srv://%s:%s@ruinvolved-mtrqy.mongodb.net/test?retryWrites=true" % (DB_USERNAME, DB_PASSWORD)