
var surrounder = document.getElementById("PlayerRankSystemMainDiv");
var playerRankDiv = document.getElementById("PlayerRankSystemPlayerList");

var URL = "/rank/requestAPI";




//first load, when the Page is loaded
load_API_json_page("POST",URL);

//Loop for updating PlayerList
setInterval(function (){
    load_API_json_page("POST", URL);
},500);


if(targetMethod==null)
    targetMethod = "now"



function load_API_json_page(method, url) {
    var request = new XMLHttpRequest();
    request.open(method,url, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");


    request.addEventListener('load', function(event) {
        if (request.status >= 200 && request.status < 300) {
            var recved = request.responseText;
            var rcvstr = JSON.parse(recved);

            playerRankDiv.innerHTML = "";

            rcvstr = sortList(rcvstr);

            for(var i = 0; i<rcvstr.length;i++){

            var rcvargs = rcvstr[i].split(":");

                playerRankDiv.innerHTML+="<li><code class='PlayerRankSystemPlayerLine'>"+rcvargs[0] + " --> "+rcvargs[1]+" [Points]</code></li>"
            }



        } else {
            console.log(request.statusText, request.responseText);
        }
    });
    request.send("target="+targetMethod);
}

function sortList(list){
    var toReturn = [];

    var numlist = [];
    var nameList = [];
    for(var i = 0; i< list.length;i++){
        numlist.push(list[i].split(":")[1]);
        nameList.push(list[i].split(":")[0]);
    }
    numlist.sort(function(a,b) {
        return a - b;
    });
    numlist.reverse();

    //console.log(numlist);

    for(var i = 0; i< numlist.length;i++) {
        var foundName = "ERROR-NameNotFound";
        var number = numlist[i];
        for(var ii = 0; ii<list.length;ii++){
            if(list[ii].split(":")[1]==number) {
                var isNameAlreadyInTable = false;
                for(var iii = 0; iii<toReturn.length;iii++) {
                    if(toReturn[iii]==list[ii].split(":")[0]+":"+number.toString()){
                        isNameAlreadyInTable = true;
                    }
                }
                if(!isNameAlreadyInTable)
                    foundName = list[ii].split(":")[0];
            }
        }
        toReturn.push(foundName+":"+number.toString())
    }


    return toReturn;
}

