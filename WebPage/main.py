from flask import Flask, render_template, request, url_for, session, Markup, redirect, jsonify
import random
import dataBaseManager as DBM
import dataBaseManager


app = Flask(__name__)

SECRET_KEY = b'\x1f\xc0\x84cP\xe7[\xae\x8d\xc1\x93<\x1b\xc7aq\xaa~\xa41\xa8M \x8c\xa4\xac\x8a\xc8/8\x7f\xf5'

DBM.set_pw_salt(SECRET_KEY)
app.secret_key = SECRET_KEY

DBM.set_pw_salt(SECRET_KEY)

#@app.route("/")
#def index():
#    return  render_template("base.html")


naviBar = Markup("""
<ul class="navi">
    <li><a class="aktiv wasonpage" href="/rank/bestPlayers">Scores</a> </li>
    <li><a class="aktiv wasonpage" href="/login">Login</a></li>
    <li><a class="aktiv wasonpage" href="/register">Registration</a></li>
    <li><a class="aktiv wasonpage" href="#"></a></li>
    <li><a class="aktiv wasonpage" href="#">Admin Login</a></li>
</ul>
""")


ELEMENT_SCRIPT_BACK = """
<script>
    setInterval(function () {
        window.location=history.back();
    },1000)
</script>
"""

"""
<li><a class="aktiv wasonpage" target="_blank" href="https://www.instagram.com/nicht_leon0/">Instagram</a></li>
    <li><a class="aktiv wasonpage" target="_blank" href="https://www.youtube.com/channel/UCHCaqURRYqQA7bEgLO-Rxbw?view_as=subscriber">Youtube</a></li>
    <li><a class="aktiv wasonpage" target="_blank" href="https://www.instagram.com/jhonjirwin_official/">Ehrenmann</a></li>
    <li><a class="aktiv wasonpage" target="_blank" href="https://www.aes-maintal.de">Schule (mittelmäßig)</a></li>
"""


def showShortMessage(text):
    return Markup("<h1>"+text+"</h1>"+ELEMENT_SCRIPT_BACK)


@app.route("/")
def index():
    return redirect("/rank/bestPlayers")


fakePlayerScoreList = ["S.Bot_1:2865","S.Bot_7:2864","XVC-Bot:2984","GG:2865","Noname:1480","S.Bot_4:2863","A:1003","TestClient:307","NoBot:2"]

@app.route("/rank/bestPlayers")
def rank_page():
    return render_template("scores.html", PY_NAVIBAR=naviBar)
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
        access = dataBaseManager.check_access(name, pw)

        if access == 0:         #Access
            return Markup("<h1>Wilkommen "+name+", du kannst dich jetzt mit deinen Anmeldedaten auch auf den Gameserver anmelden!</h1><!--<br>--><button style=\"font-size: xx-large;\"><a href=\"https://github.com/noname28439/ModGame\">Weitere Informationen findest du auf dem Github des Projektes!</a></button>")
        elif access == 1:       #Wrong PW
            return Markup("<h1>Fehler: Falsches Passwort</h1>")
        elif access == 2:       #Wrong Username
            return Markup("<h1>Fehler: Falscher Nutzername</h1>")

    elif request.method == "GET":
        return render_template("login.html", PY_NAVIBAR=naviBar)
    
    
@app.route("/register", methods=["GET", "POST"])
def register():
    if request.method == "POST":

        name = request.form["username"]
        email = request.form["email"]
        pw = request.form["password"]
        pw2 = request.form["password2"]

        if not pw==pw2:
            return showShortMessage("Die eingegebenen Passwörter sind nicht identisch!")
        if dataBaseManager.user_exists(name):
            return showShortMessage("Es gibt bereits einen Nutzer mit dem Namen "+name+"!")


        result = dataBaseManager.add_user(name, pw, email)

        if result==0:
            return showShortMessage("Es gibt bereits einen Nutzer mit dem Namen " + name + "!")
        elif result==1:
            return showShortMessage("Beim Erstellen des Accounts ist ein Fehler aufgetreten!")

    elif request.method == "GET":
        return render_template("register.html", PY_NAVIBAR=naviBar)




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