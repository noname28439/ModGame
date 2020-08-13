from flask import Flask, render_template


app = Flask(name)


@app.route("/")
def login():
    return  render_template("ndifnh.html")

@app.route("/home")
def home():
    return render_template("index")





if name == "main":
    app.run(debug=True, host="0.0.0.0",port=5000)