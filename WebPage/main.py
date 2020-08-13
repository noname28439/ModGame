from flask import Flask, render_template, request

import dataBaseManager as DBM




app = Flask(__name__)



SECRET_KEY = b'\x1f\xc0\x84cP\xe7[\xae\x8d\xc1\x93<\x1b\xc7aq\xaa~\xa41\xa8M \x8c\xa4\xac\x8a\xc8/8\x7f\xf5'

DBM.set_pw_salt(SECRET_KEY)
app.secret_key = SECRET_KEY

DBM.set_pw_salt(SECRET_KEY)

@app.route("/")
def login():
    return  render_template("ndifnh.html")

@app.route("/home")
def home():
    return render_template("index.html")


@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        name = request.form["username"]
        pw = request.form["password"]
        access = DBM.check_access(name, pw)


    elif request.method == "GET":
        return render_template("login.html")




if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0",port=5000)