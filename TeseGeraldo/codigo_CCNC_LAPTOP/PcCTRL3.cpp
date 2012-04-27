//---------------------------------------------------------------------------

#include <vcl.h>
#include "stdio.h"

#include "math.h"
#include "dstring.h"
#pragma hdrstop

#include "PcCTRL3.h"
#include "PcCTRL1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm3 *Form3;
//---------------------------------------------------------------------------
__fastcall TForm3::TForm3(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm3::Button1Click(TObject *Sender)
{
  FILE *F1;
  unsigned int a;
   if ((F1 = fopen("PcCTRL.config", "w+t"))== NULL)
   {
      Application->MessageBox("Cannot open output file.", "Look", MB_OK);
      //return 1;
   }
   else
   {
      fprintf(F1,"X1 %s\n\r",Edit1->Text);
      fprintf(F1,"X2 %s\n\r",Edit2->Text);
      fprintf(F1,"Y1 %s\n\r",Edit3->Text);
      fprintf(F1,"Y2 %s\n\r",Edit4->Text);
      fprintf(F1,"Scale_X %s\n\r",Edit5->Text);
      fprintf(F1,"Scale_Y %s\n\r",Edit6->Text);

      fprintf(F1,"Exposition %s\n\r",Edit8->Text);
      fprintf(F1,"Thresholding %s\n\r",Edit9->Text);
      fprintf(F1,"SampleVolume %s\n\r",Edit7->Text);
      fprintf(F1,"Period %s\n\r",Edit10->Text);
      fclose (F1);
      a=StrToInt(Edit8->Text);
      Form1->VideoGrabber1->SetCameraControl (TCameraControl (4),a);
   }

  strcpy(Form1->cpx1,Edit1->Text.c_str());
  strcpy(Form1->cpx2,Edit2->Text.c_str());
  strcpy(Form1->cpy1,Edit3->Text.c_str());
  strcpy(Form1->cpy2,Edit4->Text.c_str());
  strcpy(Form1->expo,Edit8->Text.c_str());
  strcpy(Form1->thr,Edit9->Text.c_str()) ;
  strcpy(Form1->sampleVolume,Edit7->Text.c_str()) ;

  Form1->Timer2->Interval=StrToInt(Edit10->Text); //Janela de amostragem
  strcpy(Form1->T2IntervalStr,Edit10->Text.c_str());

  Form1->sampVol=StrToFloat(Form1->sampleVolume); //.c_str()) ;






}
//---------------------------------------------------------------------------


void __fastcall TForm3::FormCreate(TObject *Sender)
{
  FILE *F1;
  char str1[80],str2[80];
  float h,x;
   if ((F1 = fopen("PcCTRL.config", "r+t"))== NULL)
   {
      Application->MessageBox("Cannot open output file.", "Look", MB_OK);
      //return 1;
   }
   else
   {
      fscanf(F1,"%s %s",&str1,&str2); //Edit1  Corrdenada do ponto x1
      Edit1->Text=str2;
      strcpy(Form1->cpx1,str2);

      fscanf(F1,"%s %s",&str1,&str2); //Edit2  Corrdenada do ponto x2
      Edit2->Text=str2;
      strcpy(Form1->cpx2,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit3  Corrdenada do ponto y1
      Edit3->Text=str2;
      strcpy(Form1->cpy1,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit4  Corrdenada do ponto y2;
      Edit4->Text=str2;
      strcpy(Form1->cpy2,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit5  Escala X
      Edit5->Text=str2;
      strcpy(Form1->escx,str2);

      fscanf(F1,"%s %s",&str1,&str2);  //Edit6  Escala Y
      Edit6->Text=str2;
      strcpy(Form1->escy,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit8 Exposi��o
      Edit8->Text=str2;
      strcpy(Form1->expo,str2);

      fscanf(F1,"%s %s",&str1,&str2); //Edit9 Thresholding
      Edit9->Text=str2;
      strcpy(Form1->thr,str2);

      fscanf(F1,"%s %s",&str1,&str2); //Edit7 sampleVolume
      Edit7->Text=str2;
      strcpy(Form1->sampleVolume,str2);
      Form1->sampVol=StrToFloat(Form1->sampleVolume);

      fscanf(F1,"%s %s",&str1,&str2); //Edit7 sampleVolume
      Edit10->Text=str2;
      strcpy(Form1->T2IntervalStr,str2);
      Form1->Timer2->Interval=StrToInt(str2);




      fclose (F1);
   //   h=StrToFloat(Form1->escy);
   //   x=StrToFloat(Form1->escx);
   //   Form1->sampVol=(4*atan(1))*(h/2.0)*(h/2.0)*x;
      Edit7->Text=FloatToStrF(Form1->sampVol,ffFixed,5,3);


   }
        
}
//---------------------------------------------------------------------------

