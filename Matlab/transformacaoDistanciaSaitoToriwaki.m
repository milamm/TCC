%% Transformacao de Distancia de Saito e Toriwaki (imagem bin�ria)
clear all;
close all;

image = imread('circulo.jpg');

%limiar
image_bin = im2bw(image(:,:,1),0.6);
imshow(image_bin,'InitialMagnification',800);

[h,w] = size(image_bin);
max_dist = w*h;
max_gray = max_dist/5;
image1 = max_dist*ones(h,w);
image2 = image1;

F = image_bin;
G_ = max_dist*ones(h,w);
G = max_dist*ones(h,w);
%% Transformacao 1 (linhas)
figure;

% forward scan
for i = 1 : h     
    if(F(i,1)==0)
        G_(i,1) = 0;
    end
    for j = 2 : w
        if(F(i,j)~=0)
            G_(i,j) = (G_(i,j-1)^(1/2) + 1)^2;
        else
            G_(i,j) = 0;
        end
    end
end

% backward scan
for i = 1 : h 
    G(i,:) = G_(i,:);
    for j = (w-1) : -1 : 1
        G(i,j) = min([(G(i,j+1)^(1/2) + 1)^2 G_(i,j)]);
    end
end
imshow(G,[0 max_gray],'InitialMagnification',800);
% for i = 1 : h 
%     i
%     % forward scan
%     for j = 1 : w
%         if(j==1 || image_bin(i,j)==0)
%             image1(i,j) = 0;
%         else
%             image1(i,j) = (image1(i,j-1)^(1/2) + 1)^2;            
%         end
%         imshow(image1,[0 max_gray],'InitialMagnification',800);
%     end
%     
%     % backward scan
%     for j = w-1 : 1
%         image1(i,j) = min((image1(i,j+1)^(1/2) + 1)^2,image1(i,j));
%         imshow(image1,[0 max_gray],'InitialMagnification',800);
%     end
% end

%% Transformacao 2 (colunas)
figure;
H_ = max_dist*ones(h,w);
H = max_dist*ones(h,w);

% top-bottom
for j = 1 : w
    H_(1,j) = G(1,j);
    for i = 2 : h
        if G(i,j) > (G(i-1,j) + 1) 
            if ( i + (G(i,j) - G(i-1,j) - 1)/2 ) > h
                n_max = h - i;
            else
                n_max = (G(i,j) - G(i-1,j) - 1)/2;
            end
            for n = 0 : n_max
                if ( G(i-1,j) + (n+1)^2 ) < G(i+n,j)
                    H_(i+n,j) = G(i-1,j) + (n+1)^2;
                else 
                    break;
                end
            end  
        else
            H_(i,j) = G(i,j);
        end
    end
end
imshow(H_,[0 max_gray],'InitialMagnification',800);

% bottom-top
for j = 1 : w
    H(h,j) = H_(h,j);
    for i = (h-1) : -1 : 1
        if H_(i,j) > (H_(i+1,j) + 1) 
            if ( i - (H_(i,j) - H_(i+1,j) - 1)/2 ) < 1
                n_max = i - 1;
            else
                n_max = (H_(i,j) - H_(i+1,j) - 1)/2;
            end
            for n = 0 : n_max
                if ( H_(i+1,j) + (n+1)^2 ) < H_(i-n,j)
                    H(i-n,j) = H_(i+1,j) + (n+1)^2;
                else 
                    break;
                end
            end  
        else
            H(i,j) = H_(i,j);
        end
    end
end
imshow(H,[0 max_gray],'InitialMagnification',800);
% i = 2; j = 1;
% image2(1,:) = image1(1,:);
% for i = 2 : h 
%     i
%     for j = 1 : w
%         j
%         if image1(i,j) > (image1(i-1,j) + 1)
%             for n = 0 : (image1(i,j) - image1(i-1,j) - 1)/2
%                 if(n+i<h)
%                     if image1(i-1,j) + (n+1)^2 < image1(i+n,j)
%                         image2(i+n,j) = image1(i-1,j) + (n+1)^2;
%                     else 
%                         i = i+1;
%                     end
%                 end
%             end
%         else
%             image2(i,j) = image1(i,j);
%         end
%         imshow(image2,[0 max_gray],'InitialMagnification',800);
%     end
% end