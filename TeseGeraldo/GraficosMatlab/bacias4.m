clear all
close all
[x,y] = meshgrid(-10:0.1:10);
z=(x.^2+y.^2);
z1=25*ones(201,201);

[x1 y1]=size(z);
for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>25)
            z(i,j)=25;
        end
    end
end
p1=15; p2=100;
z1(p1:p1+98,p1:p1+98)=z(52:150,52:150);
z1(p2:p2+98,p2:p2+98)=z(52:150,52:150);


for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>24)&& (z(i,j)<25)
            z(i,j)=25.5;
        end
    end
end

S = [0,90]
surfl(x,y,z1,S);
shading interp %faceted % flat %interp
colormap(gray);

stop

h=10;
ax=10
ay=10

%axis([-ax  ax  -ay  ay  -h  h])
    stop

%z = -0.5*abs(peaks(x,y));
%z=-1*abs(sin(x).*sin(y));
%z=4*(sin(x).*sin(y));


    



[x1 y1]=size(z);
for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>-0.0001)
            z(i,j)=0.1;
        end
    end
end
nn=1
    figure(nn)    
    nn=nn+1;
    S = [0,45]
    surfl(x,y,z,S);
    shading interp %faceted % flat %interp
    colormap(gray);
    h=4.5;
    ax=4.5
    ay=4.5

    axis([-ax  ax  -ay  ay  -h  h])
    t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
    view(t)
    stop
    
for(n=2:-0.2:0.002)
    figure(nn)    
    nn=nn+1;
    S = [0,45]
  %  lighting flat %none
    surfl(x,y,z,S);
    shading interp %faceted % flat %interp
    colormap(gray);
    h=n;
    ax=4.5
    ay=4.5

    axis([-ax  ax  -ay  ay  -h  h])
    
    t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
    view(t)
    
  %  contour(z)
 
end
    

