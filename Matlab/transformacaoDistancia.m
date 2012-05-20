%% Transformacao de Distancia (imagem bin�ria)
clear all;
close all;

image = imread('HEME.jpg');

%limiar
image_bin = im2bw(image(:,:,1),0.6);
imshow(image_bin,'InitialMagnification',500);

[h,w] = size(image_bin);
max_dist = w*h;
image_bin = image_bin*max_dist;
image_out = image_bin;

%max_dist = floor(((w/2)^2+(h/2)^2)^(1/2));
%step = floor(256/max_dist);
figure;
notConv = 1;
while(notConv)
    notConv = 0;

    pix_buffer = max_dist*ones(3,3);
    distance_vector = max_dist*ones(3,3);
    pix_buffer(3,2) = image_out(2,1);
    pix_buffer(2,3) = image_out(1,2);
    pix_buffer(3,3) = image_out(2,2);
    
    for y = 1:h
        for x = 1:w
            disp([y, x]);
            if(image_out(y,x)==max_dist)
                % checa se existe pixel 0 na vizinhanca
                neighbor_calc = 0;
                for i = 1:3
                    for j = 1:3
                        if(pix_buffer(i,j)==0)
                            image_out(y,x) = 1;
                            pix_buffer(2,2) = 1;
                            break;
                        else
                            if(pix_buffer(i,j)~=max_dist)
                                distance = pix_buffer(i,j) + 1;
                                if(distance<image_out(y,x))
                                    image_out(y,x) = distance;
                                    pix_buffer(2,2) = distance;
                                end
                               % neighbor_calc = 1;
                            end
                        end
                    end
                end
                %if(image_out(y,x)~=1)
                if(image_out(y,x)==max_dist)
                    %if(neighbor_calc)
                     %   image_out(y,x) = min(distance_vector(:)) + 1;
                    %else
                        notConv = 1;
                    %end
                end            
            end

            % atualiza buffer (horizontal)
            if(x==1)
                v1 = pix_buffer(2,:);
                v2 = pix_buffer(3,:);
            end
            if(x==2)
                v1(1,3) = pix_buffer(2,2);
            end
            pix_buffer(:,1) = pix_buffer(:,2);
            pix_buffer(:,2) = pix_buffer(:,3);
            if(x<w-1)
                switch y
                    case 1
                        pix_buffer(2:3,3) = image_out(y:y+1,x+2);
                        pix_buffer(1,3) = max_dist;
                    case h
                        pix_buffer(1:2,3) = image_out(y-1:y,x+2);
                        pix_buffer(3,3) = max_dist;
                    otherwise
                        pix_buffer(:,3) = image_out(y-1:y+1,x+2);
                end
            else
                pix_buffer(:,3) = max_dist;
            end
        end

        %atualiza buffer (vertical)
        pix_buffer(1,:) = v1;
        pix_buffer(2,:) = v2;
        pix_buffer(3,:) = [max_dist, max_dist, max_dist];
        if(y<h-1)
            pix_buffer(3,2:3) = image_out(y+2,1:2);
        %else
        %    pix_buffer(3,2:3) = max_dist;
        end

    end
    imshow(image_out, [0 255],'InitialMagnification',500);     
end