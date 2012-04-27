close all
clear all

[x,y] = meshgrid(-2*pi:pi/64:2*pi);
z = (peaks(x,y));
%z=-1*abs(sin(x).*sin(y));
%z=4*(sin(x).*sin(y));


[x1 y1]=size(z);
for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)>-0.06)
            z(i,j)=-0.06;
        end
    end
end



for(i=1:x1)
    for(j=1:y1)
        if(z(i,j)<-0.06)
            z(i,j)=1;
        else
            z(i,j)=0;
        end
    end
end


    D = bwdist(~z);
    D = -D;
    D(~D) = -Inf;
    L = watershed(D);



figure(1)
 imshow(L)
% figure(2)
% imagesc(z)
        