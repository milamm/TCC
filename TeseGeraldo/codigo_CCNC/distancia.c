ENTRADA BW imagem binarizada

SAÍDA   ID imagem transformada

IHD,IWD Tamanho da imagem BW e ID

yy,xx,sai,ws_d,y1,x1 variáveis auxiliares

   PARA (yy=0 ATÉ IHD)
   {
      PARA(xx=0 ATÉ IWD)
      {
         SE(BW[xx][yy]==0) ENTÃO ID[xx][yy]=0;
         SE NÃO
         {
            sai=0;
            ws_d=1;
            ENQUANTO (sai==0)
            {
                PARA (y1=(yy-ws_d) ATÉ(yy+ws_d))
                {
                    PARA (x1=(xx-ws_d) ATÉ(xx+ws_d))
                    {
                        SE((y1>=0)&&(x1>=0)&&(y1<IHD)&&(x1<IWD)) ENTÃO
                        {
                           SE(BW[x1][y1]==0) ENTÃO
                           {
                              ID[xx][yy]=255-ws_d;
                              sai=1;
                           }
                        }
                        SE(sai==1) ENTÃO break;
                    }
                    SE(sai==1) ENTÃO break;
                }
                ws_d++;
            }
        }
      }
   }
