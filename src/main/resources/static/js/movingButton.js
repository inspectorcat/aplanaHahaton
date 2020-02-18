
function getObj(objID)
{
    if (document.getElementById) {return document.getElementById(objID);}
    else if (document.all) {return document.all[objID];}
    else if (document.layers) {return document.layers[objID];}
}

var width = window.innerWidth;
var height = window.innerHeight;
var ie4 = document.all;
var ns6 = document.getElementById&&!document.all;
cobj = getObj("button");

function moveIt(){
    y = Math.floor(Math.random() * (height - 128));
    x = Math.floor(Math.random() * (width - 230));
    if (ie4){
        cobj.style.top  = y;
        cobj.style.left = x;
    }
    else if (ns6){
        cobj.style.top  = y+"px";
        cobj.style.left = x+"px";
    }
}