(function(){var g=!0,h=null,i=!1,j=(new Date).getTime();var aa=this,ba=function(a,b){var c=a.split("."),d=aa;!(c[0]in d)&&d.execScript&&d.execScript("var "+c[0]);for(var e;c.length&&(e=c.shift());)!c.length&&void 0!==b?d[e]=b:d=d[e]?d[e]:d[e]={}},ca=function(a,b,c){return a.call.apply(a.bind,arguments)},da=function(a,b,c){if(!a)throw Error();if(2<arguments.length){var d=Array.prototype.slice.call(arguments,2);return function(){var c=Array.prototype.slice.call(arguments);Array.prototype.unshift.apply(c,d);return a.apply(b,c)}}return function(){return a.apply(b,
arguments)}},k=function(a,b,c){k=Function.prototype.bind&&-1!=Function.prototype.bind.toString().indexOf("native code")?ca:da;return k.apply(h,arguments)};var ea=/&/g,fa=/</g,ga=/>/g,ha=/\"/g,l={"\x00":"\\0","\u0008":"\\b","\u000c":"\\f","\n":"\\n","\r":"\\r","\t":"\\t","\x0B":"\\x0B",'"':'\\"',"\\":"\\\\"},m={"'":"\\'"};var ia=document,o=window;var r=function(a){return"true"==a?g:"false"==a?i:i},ja=/^([\w-]+\.)*([\w-]{2,})(\:[0-9]+)?$/,s=function(a){return!a?"pagead2.googlesyndication.com":(a=a.match(ja))?a[0]:"pagead2.googlesyndication.com"};var v=parseFloat("0"),ka=isNaN(v)||1<v||0>v?0:v;var la=r("true"),ma=r("false"),na=r("false"),pa=r("false");var qa=function(){return s("")};s("");var w=function(a){return!!a&&"function"==typeof a&&!!a.call},ra=function(a,b){if(!(2>arguments.length))for(var c=1,d=arguments.length;c<d;++c)a.push(arguments[c])};function y(a){return"function"==typeof encodeURIComponent?encodeURIComponent(a):escape(a)}function sa(a,b){a.addEventListener?a.addEventListener("load",b,i):a.attachEvent&&a.attachEvent("onload",b)};ba("google_protectAndRun",function(a,b,c){a=k(b,aa,a);b=window.onerror;window.onerror=a;try{c()}catch(d){var c=d.toString(),e="";d.fileName&&(e=d.fileName);var f=-1;d.lineNumber&&(f=d.lineNumber);if(!a(c,e,f))throw d;}window.onerror=b});
ba("google_handleError",function(a,b,c,d){0.01>Math.random()&&(a=["http://",qa(),"/pagead/gen_204","?id=jserror","&jscb=",la?1:0,"&jscd=",ma?1:0,"&context=",y(a),"&msg=",y(b),"&file=",y(c),"&line=",y(d.toString()),"&url=",y(ia.URL.substring(0,512)),"&ref=",y(ia.referrer.substring(0,512))],a.push(["&client=",y(o.google_ad_client),"&format=",y(o.google_ad_format),"&slotname=",y(o.google_ad_slot),"&output=",y(o.google_ad_output),"&ad_type=",y(o.google_ad_type)].join("")),a=a.join(""),o.google_image_requests||
(o.google_image_requests=[]),b=o.document.createElement("img"),b.src=a,o.google_image_requests.push(b));return!na});var ta=function(a){try{var b=a.google_test;a.google_test=!b;if(a.google_test===!b)return a.google_test=b,g}catch(c){}return i},z=h,ua=function(){if(!z){for(var a=window;a!=a.parent&&ta(a.parent);)a=a.parent;z=a}return z};var A,B=function(a){this.c=[];this.a=a||window;this.b=0;this.d=h},va=function(a,b){this.l=a;this.i=b};B.prototype.n=function(a,b){0==this.b&&0==this.c.length&&(!b||b==window)?(this.b=2,this.g(new va(a,window))):this.h(a,b)};B.prototype.h=function(a,b){this.c.push(new va(a,b||this.a));C(this)};B.prototype.o=function(a){this.b=1;a&&(this.d=this.a.setTimeout(k(this.f,this),a))};B.prototype.f=function(){1==this.b&&(this.d!=h&&(this.a.clearTimeout(this.d),this.d=h),this.b=0);C(this)};B.prototype.p=function(){return g};
B.prototype.nq=B.prototype.n;B.prototype.nqa=B.prototype.h;B.prototype.al=B.prototype.o;B.prototype.rl=B.prototype.f;B.prototype.sz=B.prototype.p;var C=function(a){a.a.setTimeout(k(a.m,a),0)};B.prototype.m=function(){if(0==this.b&&this.c.length){var a=this.c.shift();this.b=2;a.i.setTimeout(k(this.g,this,a),0);C(this)}};B.prototype.g=function(a){this.b=0;a.l()};
var wa=function(a){try{return a.sz()}catch(b){return i}},xa=function(a){return!!a&&("object"==typeof a||"function"==typeof a)&&wa(a)&&w(a.nq)&&w(a.nqa)&&w(a.al)&&w(a.rl)},D=function(){if(A&&wa(A))return A;var a=ua(),b=a.google_jobrunner;return xa(b)?A=b:a.google_jobrunner=A=new B(a)},ya=function(a,b){D().nq(a,b)},za=function(a,b){D().nqa(a,b)};var Aa=/MSIE [2-7]|PlayStation|Gecko\/20090226/i,Ba=/Android|Opera/,Ca=function(){var a=E,b=F.google_ad_width,c=F.google_ad_height,d=["<iframe"],e;for(e in a)a.hasOwnProperty(e)&&ra(d,e+"="+a[e]);d.push('style="left:0;position:absolute;top:0;"');d.push("></iframe>");b="border:none;height:"+c+"px;margin:0;padding:0;position:relative;visibility:visible;width:"+b+"px";return['<ins style="display:inline-table;',b,'"><ins id="',a.id+"_anchor",'" style="display:block;',b,'">',d.join(" "),"</ins></ins>"].join("")};var Da=function(){},G=function(a,b,c){switch(typeof b){case "string":Ea(b,c);break;case "number":c.push(isFinite(b)&&!isNaN(b)?b:"null");break;case "boolean":c.push(b);break;case "undefined":c.push("null");break;case "object":if(b==h){c.push("null");break}if(b instanceof Array){var d=b.length;c.push("[");for(var e="",f=0;f<d;f++)c.push(e),G(a,b[f],c),e=",";c.push("]");break}c.push("{");d="";for(e in b)b.hasOwnProperty(e)&&(f=b[e],"function"!=typeof f&&(c.push(d),Ea(e,c),c.push(":"),G(a,f,c),d=","));
c.push("}");break;case "function":break;default:throw Error("Unknown type: "+typeof b);}},J={'"':'\\"',"\\":"\\\\","/":"\\/","\u0008":"\\b","\u000c":"\\f","\n":"\\n","\r":"\\r","\t":"\\t","\x0B":"\\u000b"},Fa=/\uffff/.test("\uffff")?/[\\\"\x00-\x1f\x7f-\uffff]/g:/[\\\"\x00-\x1f\x7f-\xff]/g,Ea=function(a,b){b.push('"');b.push(a.replace(Fa,function(a){if(a in J)return J[a];var b=a.charCodeAt(0),e="\\u";16>b?e+="000":256>b?e+="00":4096>b&&(e+="0");return J[a]=e+b.toString(16)}));b.push('"')};var K="google_ad_block,google_ad_channel,google_ad_client,google_ad_format,google_ad_height,google_ad_host,google_ad_host_channel,google_ad_host_tier_id,google_ad_output,google_ad_override,google_ad_region,google_ad_section,google_ad_slot,google_ad_type,google_ad_width,google_adtest,google_allow_expandable_ads,google_alternate_ad_url,google_alternate_color,google_analytics_domain_name,google_analytics_uacct,google_bid,google_city,google_color_bg,google_color_border,google_color_line,google_color_link,google_color_text,google_color_url,google_container_id,google_contents,google_country,google_cpm,google_ctr_threshold,google_cust_age,google_cust_ch,google_cust_gender,google_cust_id,google_cust_interests,google_cust_job,google_cust_l,google_cust_lh,google_cust_u_url,google_disable_video_autoplay,google_ed,google_eids,google_enable_ose,google_encoding,google_font_face,google_font_size,google_frame_id,google_gl,google_hints,google_image_size,google_kw,google_kw_type,google_language,google_max_num_ads,google_max_radlink_len,google_num_radlinks,google_num_radlinks_per_unit,google_num_slots_to_rotate,google_only_ads_with_video,google_only_pyv_ads,google_only_userchoice_ads,google_override_format,google_page_url,google_previous_watch,google_previous_searches,google_referrer_url,google_region,google_reuse_colors,google_rl_dest_url,google_rl_filtering,google_rl_mode,google_rt,google_safe,google_scs,google_skip,google_tag_info,google_targeting,google_tdsma,google_tfs,google_tl,google_ui_features,google_ui_version,google_video_doc_id,google_video_product_type,google_with_pyv_ads".split(",");var L=function(a){this.a=a;a.google_iframe_oncopy||(a.google_iframe_oncopy={handlers:{},log:[],img:0.01>Math.random()?[]:h});this.e=a.google_iframe_oncopy;a.setTimeout(k(this.k,this),3E4)},Ga;var M="var i=this.id,s=window.google_iframe_oncopy,H=s&&s.handlers,h=H&&H[i],w=this.contentWindow,d;try{d=w.document}catch(e){}if(h&&d&&(!d.body||!d.body.firstChild)){if(h.call){i+='.call';setTimeout(h,0)}else if(h.match){i+='.nav';w.location.replace(h)}s.log&&s.log.push(i)}";
/[&<>\"]/.test(M)&&(-1!=M.indexOf("&")&&(M=M.replace(ea,"&amp;")),-1!=M.indexOf("<")&&(M=M.replace(fa,"&lt;")),-1!=M.indexOf(">")&&(M=M.replace(ga,"&gt;")),-1!=M.indexOf('"')&&(M=M.replace(ha,"&quot;")));Ga=M;L.prototype.set=function(a,b){this.e.handlers[a]=b;this.a.addEventListener&&this.a.addEventListener("load",k(this.j,this,a),i)};L.prototype.j=function(a){var a=this.a.document.getElementById(a),b=a.contentWindow.document;if(a.onload&&b&&(!b.body||!b.body.firstChild))a.onload()};
L.prototype.k=function(){if(this.e.img){var a=this.e.log,b=this.a.document;a.length&&(b=["http://",qa(),"/pagead/gen_204?id=iframecopy&log=",y(a.join("-")),"&url=",y(b.URL.substring(0,512)),"&ref=",y(b.referrer.substring(0,512))].join(""),a.length=0,a=new Image,this.e.img.push(a),a.src=b)}};var Ha=function(){var a="script",b=s(""),c="",c=pa?"https":"http";return["<",a,' src="',[c,"://",b,"/pagead/js/r20120222/r20110914/show_ads_impl.js"].join(""),'"></',a,">"].join("")},Ia=function(a,b,c,d){return function(){var e=i;d&&D().al(3E4);try{var f;try{f=!!a.document.getElementById(b).contentWindow.document}catch($a){f=i}if(f){var u=
a.document.getElementById(b).contentWindow,p=u.document;if(!p.body||!p.body.firstChild)p.open(),u.google_async_iframe_close=g,p.write(c)}else{var H=a.document.getElementById(b).contentWindow,R;f=c;f=""+f;if(f.quote)R=f.quote();else{u=['"'];for(p=0;p<f.length;p++){var I=f.charAt(p),oa=I.charCodeAt(0),Sa=u,Ta=p+1,S;if(!(S=l[I])){var x;if(31<oa&&127>oa)x=I;else{var q=I;if(q in m)x=m[q];else if(q in l)x=m[q]=l[q];else{var n=q,t=q.charCodeAt(0);if(31<t&&127>t)n=q;else{if(256>t){if(n="\\x",16>t||256<t)n+=
"0"}else n="\\u",4096>t&&(n+="0");n+=t.toString(16).toUpperCase()}x=m[q]=n}}S=x}Sa[Ta]=S}u.push('"');R=u.join("")}H.location.replace("javascript:"+R)}e=g}catch(ab){H=ua().google_jobrunner,xa(H)&&H.rl()}e&&(new L(a)).set(b,Ia(a,b,c,i))}};window.google_loader_used=g;(function(a){"google_onload_fired"in a||(a.google_onload_fired=i,sa(a,function(){a.google_onload_fired=g}))})(window);if(!window.google_loader_experiment){var N;a:{var Ja=["async_bad_black","block_bad_black"];if(!(1.0E-4>Math.random())){var Ka=Math.random();if(Ka<ka){N=Ja[Math.floor(Ka/ka*Ja.length)];break a}}N=h}window.google_loader_experiment=N||""||"launch"}var O;
a:{try{if(window.google_enable_async!==g&&"blockodd"==window.google_loader_experiment&&1==window.top.location.hostname.length%2){O=i;break a}}catch(La){}O=g}var P;if(P=O){var Q;if(window.google_enable_async===i)Q=0;else{var Ma=navigator.userAgent,Na=window.google_loader_experiment;Q=(Aa.test(Ma)?i:Ba.test(Ma)?"async_bad_black"==Na:g)&&!window.google_container_id&&(!window.google_ad_output||"html"==window.google_ad_output)}P=Q}
if(P){var T=window;T.google_unique_id?++T.google_unique_id:T.google_unique_id=1;var U=window;if(!U.google_slot_list||!U.google_slot_list.push)U.google_slot_list=[];U.google_slot_list.push([U.google_ad_slot||"",U.google_ad_format||"",U.google_ad_width||"",U.google_ad_height||""].join("."));for(var V=window,_script$$inline_89="script",W,F=V,E={allowtransparency:'"true"',frameborder:'"0"',height:'"'+V.google_ad_height+'"',hspace:'"0"',marginwidth:'"0"',marginheight:'"0"',onload:'"'+Ga+'"',scrolling:'"no"',
vspace:'"0"',width:'"'+V.google_ad_width+'"'},Oa=F.document,X=E.id,Pa=0;!X||F.document.getElementById(X);)X="aswift_"+Pa++;E.id=X;E.name=X;Oa.write(Ca());W=X;var Qa;V.google_page_url&&(V.google_page_url=""+V.google_page_url);for(var Ra=[],Y=0,Ua=K.length;Y<Ua;Y++){var Z=K[Y];if(V[Z]!=h){var $;try{var Va=[];G(new Da,V[Z],Va);$=Va.join("")}catch(Wa){}$&&ra(Ra,Z,"=",$,";")}}Qa=Ra.join("");for(var Xa=0,Ya=K.length;Xa<Ya;Xa++)V[K[Xa]]=h;var Za=(new Date).getTime(),bb=window.google_loader_experiment,cb=
["<!doctype html><html><body><",_script$$inline_89,">",Qa,"google_show_ads_impl=true;google_unique_id=",V.google_unique_id,';google_async_iframe_id="',W,'";google_start_time=',j,";",bb?'google_loader_experiment="'+bb+'";':"","google_bpp=",Za>j?Za-j:1,";</",_script$$inline_89,">",Ha(),"</body></html>"].join("");(V.document.getElementById(W)?ya:za)(Ia(V,W,cb,g))}else window.q=j,document.write(Ha());})();
