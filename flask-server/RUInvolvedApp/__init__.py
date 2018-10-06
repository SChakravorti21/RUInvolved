import os
from flask import Flask, redirect

app = Flask(__name__)
app.secret_key = os.environ["RUInvolved_Secret"]

@app.route('/', methods=['GET'])
def index():
    return 'App is running!'