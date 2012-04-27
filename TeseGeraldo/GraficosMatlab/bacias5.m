clear all
close all
[x,y] = meshgrid(-2*pi:pi/64:2*pi);
z = -abs(peaks(x,y)); %superfície

[x1 y1]=size(z);
for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>0)
            z(i,j)=z(i,j)*1;
%         else
%             z(i,j)=z(i,j);
        end
    end
end
figure(1) 

S = [0,45]
surfl(x,y,z,S);
shading interp %faceted % flat %interp
colormap(gray);
h=10;
ax=4
ay=4
axis([-ax  ax-1  -ay  ay-1  -h  h])
%t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
az=0;el=90
view(az,el)
%figure(2) 
%contour(z)
hold on
stop
   

S = [0,45]
surfl(x,y,z,S);
shading interp %faceted % flat %interp
colormap(gray);
h=10;
ax=4
ay=4
axis([-ax  ax-1  -ay  ay-1  -h  h])
%t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
az=70;el=2
view(az,el)
hold on
nn=2;






for(n=0.5:-0.05:0)
    figure(nn)    
    nn=nn+1;
    S = [0,45]
    surfl(x,y,z,S);
    shading interp %faceted % flat %interp
    colormap(gray);
    h=10;
    ax=4
    ay=4
    axis([-ax  ax-1  -ay  ay-1  -h  h])
    az=70;el=90
    view(az,el)
%     t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
%     view(t)
    hold on
    S = [0,90]
    z1=-n*ones(x1,y1); %Plano de corte
    surfl(x,y,z1,S);
    shading flat %faceted % flat %interp
    colormap(gray);
    h=10;
    ax=4
    ay=4
    axis([-ax  ax-1  -ay  ay-1  -h  h])
    t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
    view(t)
end
    

