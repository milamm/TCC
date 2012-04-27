clear all
close all
[x,y] = meshgrid(-2*pi:pi/64:2*pi);
z = (peaks(x,y));
%z=-1*abs(sin(x).*sin(y));
%z=4*(sin(x).*sin(y));

% 
% [x1 y1]=size(z);
% for(i=1:x1)
%     for(j=1:y1)
%         if(z(i,j)>-0.05)
%             z(i,j)=-0.05;
%         end
%     end
% end

% [x1 y1]=size(z);
% for(i=1:x1)
%     for(j=1:y1)
%         if(z(i,j)<-0.2)
%             z(i,j)=1;
%         end
%     end
% end


nn=1
    figure(nn)    
    nn=nn+1;
    S = [0,90]
    surfl(x,y,z,S);
    shading interp %faceted % flat %interp
    colormap(gray);
    h=10;
    ax=4
    ay=4

    axis([-ax  ax-1  -ay  ay-1  -h  h])
    t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
    view(t)
   % contour(z)
 
    
for(n=3:-0.1:2)
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

    axis([-ax  ax  -ay  ay  -h  10])
    
    t=[1 0 0 -0.5;0 1 0 -0.5; 0 0 -1 10; 0 0 0  1]
    view(t)
%   figure(2)  
%     contour(z)
%  
end
    

