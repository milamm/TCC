clear all;
close all;

%image = imread('circulo_cut.jpg');
%image = imread('circulo.jpg');
image = imread('HEME.jpg');

%limiar
image_bin = im2bw(image(:,:,1),0.6);
imshow(image_bin,'InitialMagnification',800);

[IHD,IWD] = size(image_bin);
BW = image_bin;
ID = IHD*IWD*ones(IHD,IWD);

for yy = 1 : IHD
    yy
    for xx = 1 : IWD
        if BW(yy,xx) == 0
            ID(yy,xx) = 0;
        else
            sai = 0;
            ws_d = 1;
            while sai == 0
                for y1 = (yy-ws_d) : (yy+ws_d) 
                    for x1 = (xx-ws_d) : (xx+ws_d)
                        if (y1>=1) && (x1>=1) && (y1<=IHD) && (x1<=IWD)
                            if BW(y1,x1) == 0
                                ID(yy,xx) = 255-ws_d;
                                sai=1;
                            end
                        end
                        if sai == 1
                            break;
                        end
                    end
                    if sai == 1
                        break;
                    end
                end
                ws_d = ws_d+1;
            end
        end
    end
end
figure; imshow(ID,[200 max(ID(:))],'InitialMagnification',800);

%    for (yy=0; yy<IHD; yy++)
%    {
%       for(xx=0; xx<IWD; xx++)
%       {
%          if(BW[xx][yy]==0) ID[xx][yy]=0;
%          else
%          {
%             sai=0;
%             ws_d=1;
%             while (sai==0)
%             {
%                 for(y1=(yy-ws_d);y1<=(yy+ws_d);y1++)
%                 {
%                     for(x1=(xx-ws_d);x1<=(xx+ws_d);x1++)
%                     {
%                         if((y1>=0)&&(x1>=0)&&(y1<IHD)&&(x1<IWD))
%                         {
%                            if(BW[x1][y1]==0)
%                            {
%                               ID[xx][yy]=255-ws_d;
%                               sai=1;
%                            }
%                         }
%                         if(sai==1)break;
%                     }
%                     if(sai==1) break;
%                 }
%                 ws_d++;
%             }
%         }
%       }
%    }