% Grafico de supersaturação em função da altura da câmara
clear all

H=10;
dz=H/1000;
%z=0:dz:(H);
z=5.0

tu=25-5;
ti=20.0-5-0.0625;

tz=(((tu-ti)/H).*z)+ti

esat=0.61078*exp((17.269*tz)./(tz+237.3))

ei=0.61078*exp((17.269*ti)/(ti+237.3))
eu=0.61078*exp((17.269*tu)/(tu+237.3))
ez=(((eu-ei)/H).*z)+ei  %pressão parcial de vapor
%--------------------------------------------------------------------------

RH=ez./esat*100;
S=RH-100

stop


plot(S,z)
grid
[x,y]=max(S)
xlabel('Supersaturação (%)')
ylabel('Altura da câmara (mm)')
axis([0 1.2 0 10])


stop



tm=(tu+ti)/2


e3=0.61078*exp((17.269*tm)/(tm+237.3))



sh2=((((ei+eu)/2)/e3)-1)*100