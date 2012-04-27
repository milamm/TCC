% Grafico de supersaturação em função da altura da câmara
clear all

H=10;
dz=H/100;
z=0:dz:(H);

tu=22.372;
ti=17.506;

tz=(((tu-ti)/H).*z)+ti

esat=0.61078*exp((17.269*tz)./(tz+237.3))

ei=0.61078*exp((17.269*ti)/(ti+237.3))
eu=0.61078*exp((17.269*tu)/(tu+237.3))
ez=(((eu-ei)/H).*z)+ei  %pressão parcial de vapor
%--------------------------------------------------------------------------

RH=ez./esat*100;
S=RH-100
plotyy(z,S,z,tz)
grid
[x,y]=max(S)
ylabel('Supersaturação (%)')
xlabel('Altura da câmara (mm)')
%axis([0 1.2 0 10])


stop
%-----------------------------------------------------------------------
subplot(3,1,1),plot(z,tz)
axis([0 10 17 23])
% ylabel('Temp (C)')

subplot(3,1,2),plot(z,ez,'g')
axis([0 10 2 2.75])
hold on
subplot(3,1,2),plot(z,esat)


subplot(3,1,3),plot(z,S)
% xlabel('altura interna da SDCC (mm)')
axis([0 10 0 1.2])

%-----------------------------------------------------------------------


plot(S,z)
hold on
plot(z,tz)

tm=(tu+ti)/2


e3=0.61078*exp((17.269*tm)/(tm+237.3))



sh2=((((ei+eu)/2)/e3)-1)*100