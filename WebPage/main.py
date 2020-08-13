from flask import Flask, render_template, request


app = Flask(__name__)


@app.route("/")
def login():
    return  render_template("ndifnh.html")

@app.route("/home")
def home():
    return render_template("index.html")


@app.route("/login")
def home():
    return render_template("login.html")




if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0",port=5000)