if True:
    import sqlite3
    import  hashlib


    conn = sqlite3.connect("userDB.db",  check_same_thread=False)
    c = conn.cursor()

    PW_SALT = b""
    DB_NAME = "users"

    def set_pw_salt(salt):
        PW_SALT = salt

    def hash_string(input):
        to_ret = str(hashlib.pbkdf2_hmac('sha256',input.encode('utf-8'),PW_SALT,100000))
        while "'" in to_ret:
            to_ret = to_ret.replace("'", "")
        print(str(to_ret))
        return str(to_ret)



    def add_user(name, pw, coins=1):
        if check_access(name, "NICHTWICHTIG") == 2:     #Noch kein User mit diesem Namen
            c.execute("INSERT INTO " + DB_NAME + " VALUES ('" + str(name) + "','" + hash_string(pw) + "'," + str(coins) + ")")
            conn.commit()
            return 0;
        else:
            return 1;

    def check_access(name, pw):
        c.execute("SELECT * FROM users WHERE username='"+name+"'")
        data = c.fetchall()
        if(not len(data)>1):
            if (not len(data) < 1):
                if data[0][1]==hash_string(pw):
                    return 0
                else:
                    return 1
            else:
                return 2
        else:
            print("ERROR: multiple Users with the Name "+name)
            return -1
        conn.commit()

    def set_user_pw(name, new_pw):
        tosend = "UPDATE "+DB_NAME+" SET password = '"+hash_string(new_pw)+"' WHERE username = '"+str(name)+"'"
        print(tosend)
        c.execute(tosend)
        conn.commit()

    def set_user_coins(name, new_coins):
        c.execute("UPDATE " + DB_NAME + " SET coins = " + str(new_coins) + " WHERE username = '" + str(name) + "'")
        conn.commit()

    def get_user_coins(name):
        c.execute("SELECT coins FROM users WHERE username='" + name + "'")
        data = c.fetchall()
        conn.commit()
        return data[0][0]

    def remove_user_coins(name, amount):
        if get_user_coins(name)>=amount:
            set_user_coins(name, get_user_coins(name)-int(amount))
            return 0
        return 1

    def add_user_coins(name, amount):
        set_user_coins(name, get_user_coins(name) + int(amount))

    def use_user_coins(name, amount, to_run):
        if has_user_coins(name, amount):
            remove_user_coins(name, amount)
            to_run()

    def has_user_coins_consume(name, amount):
        if has_user_coins(name, amount):
            remove_user_coins(name, amount)
            return True
        else:
            return False

    def has_user_coins(name, amount):
        return get_user_coins(name)>=amount

    def read_table():
        c.execute("SELECT * FROM users")
        data = c.fetchall()
        conn.commit()
        return data





    def conn_set_cursor():
        c = conn.cursor()

    def conn_close():
        conn.close()


    def x_one_use_create_user_table():
        c.execute("""CREATE TABLE users (
            username text,
            password text,
            coins integer
        )""")
        conn.commit()


    #def add_user(name, password, coins=10):
    #    c.execute("INSERT INTO users VALUES ("+str(id)+",'"+str(name)+"','"+str(password)+"',"+str(coins)+")")
    #    conn.commit()




if(__name__=="__main__"):
    print("DBM-Debugg-Tester starting...")
    try:
        while True:
            str_in = input("-->")
            args = str_in.split(" ")

            if args[0]=="add":
                add_user(args[1],args[2])
            elif args[0]=="check":
                print(check_access(args[1],args[2]))
            elif args[0]=="set_pw":
                set_user_pw(args[1],args[2])
            elif args[0] == "set_coins":
                set_user_coins(args[1], args[2])
            elif args[0] == "get_coins":
                print(get_user_coins(args[1]))
            elif args[0] == "remove_coins":
                remove_user_coins(args[1], args[2])
            elif args[0] == "add_coins":
                add_user_coins(args[1], args[2])
            elif str_in=="read":
                for i in read_table():
                    print(i)
            elif str_in=="create":
                print(x_one_use_create_user_table())
            elif args[0]=="hash":
                print(hash_string(args[1]))
    except KeyboardInterrupt:
        print("\n\nStopping...")
        conn_close()
        quit()