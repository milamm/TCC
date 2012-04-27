%CAlculo do erro máximo do volume de amostragem
a=2;%8;
b=73;
c=89; %345;
d=14; %12;
e=620;
f=606;

a1=0.05;
b1=1;
c1=1;
d1=0.05;
e1=1;
f1=1;

v=(pi*a^2*b^2*d*e)/(4*c^2*f)

dvda=(2*pi*b^2*d*e*a)/(4*c^2*f)

dvdb=(2*pi*a^2*d*e*b)/(4*c^2*f)

dvdc=(pi*a^2*b^2*d*e*(-2))/(4*f*c^3)

dvdd=(pi*a^2*b^2*e)/(4*c^2*f)

dvde=(pi*a^2*b^2*d)/(4*c^2*f)

dvdf=(pi*a^2*b^2*d*e*(-1))/(4*c^2*f^2)

dv=dvda*a1+dvdb*b1+dvdc*c1+dvdd*d1+dvde*e1+dvdf*f1
v


stop









v=sym('pi*((a*b)/(2*c))^2*d*e/f');






a1=0.05;
b1=1;
c1=1;
d1=0.05;
e1=12;
f1=12;
dv=(diff(v,'a'))*2
simplify(dv)



%+diff(v,'b')*b1+diff(v,'c')*c1+diff(v,'d')*d1+diff(v,'e')*e1+diff(v,'f')*f1