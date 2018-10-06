import os
from flask import Flask, redirect
from RUInvolvedApp.views import clubs

app = Flask(__name__)
app.secret_key = os.environ["RUInvolved_Secret"]

app.register_blueprint(clubs.mod)

@app.route('/', methods=['GET'])
def index():
    return 'App is running!'