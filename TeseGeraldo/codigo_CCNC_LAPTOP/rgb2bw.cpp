/*
        RECORTA CONVERTE IMAGENS COLORIDAS EM NÍVEIS DE CINZA

(IH1,IW1),
(IH2,IW2) ---> São as coordenadas iniciais e finais da região da
               imagem a ser processada. Estas coordenadas são definidas no
               processo de determinação do volume de amostragem.

BW ----------> Matriz que contém a imagem a ser processada. O seu tamanho é
               definido por (IH1,IW1), (IH2,IW2)
K  ----------> Nível de corte na detecção de pixel branco.
xx,yy,ih,iw -> Variáveis auxiliares

*/
   for (ih = IH1; ih < IH2; ih++)
   {
      xx=0;
      for (iw = IW1; iw < IW2; iw++)
      {
         a=bmpImage->Canvas->Pixels[iw][ih];
         ab=(a)>>16;  ag=(a & 0x00FF00)>>8; ar=(a & 0x0000FF);
         ac=(ab+ag+ar);
         if (ac>K) BW[xx][yy]=255; else BW[xx][yy]=0;
         xx++;
      }
      yy++;
   }
