var e='o01x19lo03rec';
function readCookie(name) {
	var n = name + "=";
	var d = document.cookie.split(';');
	for(var i=0;i < d.length;i++) {
		var c = d[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(n) == 0) return c.substring(n.length,c.length);
	}
	return null;
}
var c=0;
var f = readCookie('cdhc');if (f) c=f;
var d = readCookie('cdht');
if (d!==e)c=0;
c++;
function hidemsg(){
	document.getElementById('msgw').style.display="none";
	document.getElementById('msgb').style.display="none";
}
function showmsg(m){
	msgw=document.getElementById('msgw');
	msg=(m==1)?"<b>Notice</b><br />Please check this publication out in your library.<br /><br /><div style='text-align:center;'><a href='#' onclick='hidemsg();return false; return false;' style='display:box;padding:5px;border:1px solid black;background:white;text-decoration:none;color:black;margin:20px;font-family:Arial,Tahoma,Verdana,sans-serif;'>Confirm</a>":"<b>Notice</b><br />Please check out additional resources in your local library - our website is not set up for access the full text of this resource.<br /><br /><div style='text-align:center;'><a href='#' onclick='hidemsg();return false; return false;' style='display:box;padding:5px;border:1px solid black;background:white;text-decoration:none;color:black;margin:20px;font-family:Arial,Tahoma,Verdana,sans-serif;'>Confirm</a>";	if(!msgw){
	document.write("<div id='msgb' style='background-color:#222222;position:absolute;width:100%;height:100%;left:0;top:0;z-index:30;opacity:0.6;-moz-opacity:0.6;filter:alpha(opacity=60);'> </div>");
	document.write("<div id='msgw' style='z-index:40;border:4px solid #CE5626;background-color:#F9EDE1;padding:30px;position:relative;top:60px;width:700px;margin:auto;text-align:left;'>"+msg+"</div></div>")
	}else{
	document.getElementById('msgb').style.display="block";
	msgw.innerHTML=msg;
	msgw.style.display="block";
	}
}
document.cookie="cdht=" +escape('o01x19lo03rec')+";path=/;expires=Thu, 08 Mar 2012 19:59:20 -0600";
document.cookie="cdhc=" +c+";path=/;expires=Thu, 08 Mar 2012 19:59:20 -0600";if(c>=40&&c%5==0)showmsg(1);
if(c==8)showmsg();


