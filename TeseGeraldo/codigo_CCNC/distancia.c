ENTRADA BW imagem binarizada

SA�DA   ID imagem transformada

IHD,IWD Tamanho da imagem BW e ID

yy,xx,sai,ws_d,y1,x1 vari�veis auxiliares

   PARA (yy=0 AT� IHD)
   {
      PARA(xx=0 AT� IWD)
      {
         SE(BW[xx][yy]==0) ENT�O ID[xx][yy]=0;
         SE N�O
         {
            sai=0;
            ws_d=1;
            ENQUANTO (sai==0)
            {
                PARA (y1=(yy-ws_d) AT�(yy+ws_d))
                {
                    PARA (x1=(xx-ws_d) AT�(xx+ws_d))
                    {
                        SE((y1>=0)&&(x1>=0)&&(y1<IHD)&&(x1<IWD)) ENT�O
                        {
                           SE(BW[x1][y1]==0) ENT�O
                           {
                              ID[xx][yy]=255-ws_d;
                              sai=1;
                           }
                        }
                        SE(sai==1) ENT�O break;
                    }
                    SE(sai==1) ENT�O break;
                }
                ws_d++;
            }
        }
      }
   }
