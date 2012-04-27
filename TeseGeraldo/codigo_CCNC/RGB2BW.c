RECORTA E CONVERTE IMAGEM COLORIDA EM OUTRA EM PRETO E BRANCO

ENTRADA É A IMAGEM COMPLETA E COLORIDA DA WEBCAN
SAÍDA É UM RECORTE EM PRETO E BRANCO CORRESPONDENTE A ÁREA A SER PROCESSADA

(IH1,IW1),
(IH2,IW2) ---> São as coordenadas iniciais e finais da região da
imagem a ser processada. Estas coordenadas são definidas no
processo de determinação do volume de amostragem.

BW ----------> Matriz que contém a imagem a ser processada. O seu tamanho é
definido por (IH1,IW1), (IH2,IW2)

K ----------> Nível de corte na detecção de pixel branco.

ab,ag,ar,ac -> Variáveis auxiliares

PARA (ih<--IH1 ATÉ IH2)
{
   PARA  (iw<-IW1 ATÉ IW2)
   {
      a<--Pixels[iw][ih];
	ab=AZUL(a)	ag=VERDE(a)	ar=VERMELHO(a)
	ac=(ab+ag+ar)
    
   }	
      SE ac>K Então BW[xx][yy]=255 
   SE NÃO BW[xx][yy]=0
}
