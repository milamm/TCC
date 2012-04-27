RECORTA E CONVERTE IMAGEM COLORIDA EM OUTRA EM PRETO E BRANCO

ENTRADA � A IMAGEM COMPLETA E COLORIDA DA WEBCAN
SA�DA � UM RECORTE EM PRETO E BRANCO CORRESPONDENTE A �REA A SER PROCESSADA

(IH1,IW1),
(IH2,IW2) ---> S�o as coordenadas iniciais e finais da regi�o da
imagem a ser processada. Estas coordenadas s�o definidas no
processo de determina��o do volume de amostragem.

BW ----------> Matriz que cont�m a imagem a ser processada. O seu tamanho �
definido por (IH1,IW1), (IH2,IW2)

K ----------> N�vel de corte na detec��o de pixel branco.

ab,ag,ar,ac -> Vari�veis auxiliares

PARA (ih<--IH1 AT� IH2)
{
   PARA  (iw<-IW1 AT� IW2)
   {
      a<--Pixels[iw][ih];
	ab=AZUL(a)	ag=VERDE(a)	ar=VERMELHO(a)
	ac=(ab+ag+ar)
    
   }	
      SE ac>K Ent�o BW[xx][yy]=255 
   SE N�O BW[xx][yy]=0
}
