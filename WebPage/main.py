from flask import Flask, render_template, request, url_for, session, Markup, redirect, jsonify
import random
import dataBaseManager as DBM


app = Flask(__name__)

SECRET_KEY = b'\x1f\xc0\x84cP\xe7[\xae\x8d\xc1\x93<\x1b\xc7aq\xaa~\xa41\xa8M \x8c\xa4\xac\x8a\xc8/8\x7f\xf5'

DBM.set_pw_salt(SECRET_KEY)
app.secret_key = SECRET_KEY

DBM.set_pw_salt(SECRET_KEY)

#@app.route("/")
#def index():
#    return  render_template("base.html")



@app.route("/")
def index():
    return redirect("/rank/bestPlayers")


fakePlayerScoreList = ["S.Bot_1:2865","S.Bot_7:2864","XVC-Bot:2984","GG:2865","Noname:1480","S.Bot_4:2863","A:1003","TestClient:307","NoBot:2"]

@app.route("/rank/bestPlayers")
def rank_page():
    return render_template("scores.html")
@app.route("/rank/requestAPI", methods=["POST"])
def rank_api():
    chosenSlot = random.randint(0,len(fakePlayerScoreList)-1)
    fakePlayerScoreList[chosenSlot] = fakePlayerScoreList[chosenSlot].split(":")[0]+":"+str(int(fakePlayerScoreList[chosenSlot].split(":")[1])+1)
    return jsonify(fakePlayerScoreList)


@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        name = request.form["username"]
        pw = request.form["password"]
        access = DBM.check_access(name, pw)

        if access == 0:         #Access
            return Markup("<h1>Wilkommen, "+name+"</h1>")
        elif access == 1:       #Wrong PW
            return Markup("<h1>Fehler: Falsches Passwort</h1>")
        elif access == 2:       #Wrong Username
            return Markup("<h1>Fehler: Falscher Nutzername</h1>")

    elif request.method == "GET":
        return render_template("login.html")
    
    
@app.route("/register", methods=["GET", "POST"])
def register():
    if request.method == "POST":
        name = request.form["username"]
        pw = request.form["password"]
        result = DBM.add_user(name, pw)


    elif request.method == "GET":
        return render_template("login.html")

@app.route("/home")
def home():
    return render_template("base.html")
    if request.method["POST"]:
        logout = request.form["submit"]
    else:
        return render_template("login.html")

@app.route("/Jonathan")
def jonathan():
    return render_template("jonathan.html")




if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0",port=25565)