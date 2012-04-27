/**************TRANSFORMADA WATERSHED*****************************/
/*HISTOGRAMA*/
   for(i=0;i<256;i++)  hist[i]=0; //Inicializa vetor h
   hmin=255;
   hmax=0;

   for (ih = 0; ih < IHD; ih++)
   {
      for (iw = 0; iw < IWD; iw++)
      {
         ac=ID[iw][ih];  //(ab+ag+ar)/3;
         hist[ac]++;
         if (ac<hmin) hmin=ac;
         if (ac>hmax) hmax=ac;
      }
   }
//-----------------------------------------------------------------------
/*HISTOGRAMA ACUMULADO*/
   for (i=0;i<256;i++) hc[i]=0;
   for (i=hmin+1;i<hmax+1+1;i++) hc[i]=hc[i-1]+hist[i-1];
 //----------------------------------------------------------------------
/*ORDENAÇÃO DO VETOR DE IMAGEM*/
   for (ih = 0; ih < IHD; ih++)  //LINHA 17
      for (iw = 0; iw < IWD; iw++)
      {
         ac=ID[iw][ih];
         ima[iw][ih]=ac;
         ims[hc[ac]][0]=iw;
         ims[hc[ac]][1]=ih;
         hc[ac]++;
      }
// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*INICIALIZAÇÕES*/
   rotulo=0;
   fndx=0;
   indx=0;

   p1_ui=imd[0];
   p2_i=imo[0];
   for(iiix=0;iiix<IWDIHD; iiix++)
   {
      *p1_ui=0;
      p1_ui++;

      *p2_i=INIT;
      p2_i++;
   }
   for (i=0;i<LENFIFO;i++)
   {
      fifo[i][1]=0;
      fifo[i][2]=0;
   }

   ii=0;
   for (i=0;i<256;i++)
   {
      if (hist[i]>0)
      {
         hord[ii][0]=i;
         hord[ii][1]=hist[i];
         ii++;
      }
   }
   nh=ii; //Número de classes de cinza identificadas na imagem
   for (iii=0;iii<nh;iii++)
   {
      if (iii==0)
      {
         start=0;
         final=hord[0][1];
      }
      else
      {
        start=final;
        final=final+hord[iii][1];
      }
      h=hord[iii][0];
      for (j=start;j<final;j++)
      {
         ph=ims[j][1];
         pw=ims[j][0];
         imo[pw][ph]=MASK;
         i=0;
         while (i<8)
         {
            x=NG[i][0];y=NG[i][1];
            i=i+1;
            if (((ph+y)>-1)&&((pw+x)>-1)&&((ph+y)<(IHD))&&((pw+x)<(IWD)))
            {
               if ((imo[pw+x][ph+y]>0)||(imo[pw+x][ph+y]==WSHED))
               {
                  imd[pw][ph]=1;
                  fifo[fndx][0]=pw;  //QUEUE_ADD(pw,ph);
                  fifo[fndx][1]=ph;
                  fndx++;
                  i=9;
                }
            }
        }
    }
    distancia=1;
    fifo[fndx][0]=-1; //QUEUE_ADD(-1,-1);
    fifo[fndx][1]=-1;
    fndx++;


   while (1)
    {
        //ptr=QUEUE_FIRST();//LINHA 58
           /*
           a1=indx;
           indx++;
           */
           ptr=indx;
           indx++;



        pw=fifo[ptr][0];
        ph=fifo[ptr][1];
        if (ph==-1)// % Linha 15
        {
            if (indx==fndx)
                break;
            else
            {
                //QUEUE_ADD(-1,-1);
                fifo[fndx][0]=-1;
                fifo[fndx][1]=-1;
                fndx++;


                distancia++;

                //ptr=QUEUE_FIRST();//LINHA 58
                  ptr=indx;
                  indx++;



                pw=fifo[ptr][0];
                ph=fifo[ptr][1];

            };//    %linha 24
        };//  %linha25
        i=0;
        while (i<8)
        {
            x=NG[i][0];y=NG[i][1];
            i++;
            if (((ph+y)>-1)&&((pw+x)>-1)&&((ph+y)<(IHD))&&((pw+x)<(IWD)))
            {
                if((imd[pw+x][ph+y]<=distancia)&&((imo[pw+x][ph+y]>0)||(imo[pw+x][ph+y]==WSHED)))
                {
                    if (imo[pw+x][ph+y]>0)
                    {

                        if (imo[pw][ph]==MASK)  //||(imo[pw][ph]==WSHED))//   %linha29
                        {
                            imo[pw][ph]=imo[pw+x][ph+y];// %talvez seja aquui

                        }
                        else// %linha 31
                        {
                            if ((imo[pw][ph]!=imo[pw+x][ph+y]))
                            {
                                imo[pw][ph]=WSHED;// %linha 33
                            }
                        }
                    }
                    else// %linha 36
                    {
                        if (imo[pw][ph]==MASK) //%linha 37
                        {
                            imo[pw][ph]=WSHED;
                        }// %linha 39
                    }// %linha 40
                }
                else //%linha 41
                {
                    if ((((imo[pw+x][ph+y]==MASK)&&(imd[pw+x][ph+y]==0))))
                    {
                        imd[pw+x][ph+y]=distancia+1;
                        //QUEUE_ADD(pw+x,ph+y);
                        fifo[fndx][0]=pw+x;
                        fifo[fndx][1]=ph+y;
                        fndx++;


                    }// %linha 47

                }// %linha 48
            }
         }// %linha 49
    }// %linha 50

    for (j=start;j<final;j++)
    {
        ph=ims[j][1];
        pw=ims[j][0];
        imd[pw][ph]=0;
        if (imo[pw][ph]==MASK)
        {
            rotulo++; //=rotulo+1;

            //QUEUE_ADD(pw,ph);
            fifo[fndx][0]=pw;
            fifo[fndx][1]=ph;
            fndx++;



            imo[pw][ph]=rotulo;
            while (!(fndx==indx))
            {
                //ptr=QUEUE_FIRST();//LINHA 58
                  ptr=indx;
                  indx++;




                pwl=fifo[ptr][0];
                phl=fifo[ptr][1];
                i=0;
                while (i<8)
                {
                    x=NG[i][0];y=NG[i][1];
                    i++;
                    if (((phl+y)>-1)&&((pwl+x)>-1)&&((phl+y)<(IHD))&&((pwl+x)<(IWD)))
                    {
                        if (imo[pwl+x][phl+y]==MASK) // %linha 61
                        {

                            //QUEUE_ADD(pwl+x,phl+y);
                            fifo[fndx][0]=pwl+x;
                            fifo[fndx][1]=phl+y;
                            fndx++;

                            imo[pwl+x][phl+y]=rotulo;
                        }
                    }
                }
            }
        }
    }
}
