clear all
close all

[x,y] = meshgrid(-1*pi:pi/64:1*pi);

z=-1*abs((sin(x).*sin(y)));

%[x,y] = meshgrid([-2:.25:2]);
%z = 10*(x.*exp(-x.^2-y.^2));


[x1 y1]=size(z);
for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>-0.2)
            z(i,j)=0.2;
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
    

