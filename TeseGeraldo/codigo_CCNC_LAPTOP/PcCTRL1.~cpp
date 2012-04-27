//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop

#include "PcCTRL1.h"
#include "Unit2.h"
#include "PcCTRL3.h"
#include "stdio.h"
#include <dir.h>

#include <dos.h>
#include <process.h>
#include<math.h>
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "AdPort"
#pragma link "ADTrmEmu"
#pragma link "OoMisc"
#pragma link "AdPacket"
#pragma link "VidGrab"
#pragma resource "*.dfm"
#define FALSE 0;
#define TRUE 1;
#define TAMVETDET 2 //era 150
#define REFADC 5.03
#define VET_IMG 500
//---------------------------------------------------------------------------
#define LINHA 800
#define COLUNA 800
#define IH1 213   // 194
#define IH2 283   //266
#define IW1 11
#define IW2 630
#define IWD 619 //IW2-IW1
#define IHD 70  //72  //IH2-IH1
#define IWDIHD 43330   //44568
#define THR 25
#define ponto_ficticio -1,-1
#define MASK -2
#define WSHED 0
#define INIT -1
#define LENFIFO 90000
//---------------------------------------------------------------------------


TForm1 *Form1;
char vetsat[11];
char supersatatual;
float supersatold;
float supersat,tinf;

AnsiString TsupOld,TinfOld;

unsigned int t_sup_indx,t_inf_indx;
int nciclo;

float T_sup,T_sup_v[20],T_inf,T_inf_v[20],T_dif;
float vet_t_dif[25];

AnsiString vetarq[VET_IMG];
unsigned int narq;    //número de imagens gravadas

int cxi;
unsigned short SetTemp;
unsigned int i1,i2;
char P1;
char cicloautomatico;
struct  time t,tbon,tboff;
struct date d;

unsigned int iciclo;
AnsiString reflaser;

unsigned int nrefb;
float vet_det_float[TAMVETDET];
unsigned int i_det_float;
float sampleVolume;


void __fastcall desabilita_envio_temp(void);
void __fastcall habilita_envio_temp(void);
void __fastcall liga_valvula(void);
void __fastcall desliga_valvula(void);
void __fastcall liga_bomba(void);
void __fastcall desliga_bomba(void);
char   *current_directory(char *path);
char   *geraNome(char *nome);
void __fastcall verifica_back(void);
float __fastcall calc_temp_ss(float tsup, float supersat);
void __fastcall watershed(char *arq);

//DECLARAÇÕES DA ROTINA WATERSHED




long  unsigned int ims[IHD*IWD][2];
unsigned int ima[IWD][IHD];
unsigned int dist[IWD][IHD];
int imo[IWD][IHD],*p2_i;     //ajustar o correto tamanho de imo
unsigned int imd[IWD][IHD];
int fifo[LENFIFO][2];
unsigned int BW[IWD][IHD];
unsigned int ID[IWD][IHD],*p1_ui;
int p[2],pl[2];
int g[IWD][IHD];


unsigned int indx=0; //Utilizada na rotina QUEUE_FIRST
unsigned int fndx=0;
unsigned int a1,nh,IW,IH;
unsigned int hord[257][2];

int c[9]={clGreen,clBlue,clLime,clYellow,clSkyBlue,clFuchsia,clTeal,clOlive,clAqua};
int NG[8][2]={{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

unsigned int iii,ii,final,iiix,recImagemCnt;
int ph,pw,pwl,phl;


//    TJPEGImage *jpgImage = new TJPEGImage;
//    Graphics::TBitmap *bmpImage = new Graphics::TBitmap;
    int iXPixel = 0, iYPixel = 0, iNumXPixels, iNumYPixels;

unsigned int thrx;
int iw,ih;
long unsigned int a,ab,ag,ar,ac;
int ix,xx,yy;
unsigned int ws_d,y1,x1;
boolean sai;
unsigned int h,hist[256],hc[257],hmin,hmax,i;
int ptr,xr,yr;
unsigned int  rotulo_atual,rotulo;
int start,x,y,j;
unsigned int distancia_atual,distancia,ngotas;
//char narq[MAXPATH];
long unsigned int sec1,sec2,sec3,soma;
unsigned int QUEUE_ADD_CNT,QUEUE_FIRST_CNT,QUEUE_EMPTY_CNT;
float conc;
int modo=false;







//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------]
void __fastcall desabilita_envio_temp(void)
{
   Form1->ApdComPort1->Output = 'L';
}
//---------------------------------------------------------------------------]
void __fastcall habilita_envio_temp(void)
{
   Form1->ApdComPort1->Output = 'M';
}
//---------------------------------------------------------------------------
void __fastcall liga_valvula(void)
{
   Form1->ApdComPort1->Output = 'C';
   Form1->Edit6->Color=clRed;
   Form1->Edit6->Update();
}
//---------------------------------------------------------------------------
void __fastcall desliga_valvula(void)
{
   Form1->ApdComPort1->Output = 'D';
   Form1->Edit6->Color=clSilver;
   Form1->Edit6->Update();
}
//---------------------------------------------------------------------------
void __fastcall liga_bomba(void)
{
   Form1->ApdComPort1->Output = 'E';
   Form1->Edit7->Color=clRed;
   Form1->Edit7->Update();

}
//---------------------------------------------------------------------------
void __fastcall desliga_bomba(void)
{
   Form1->ApdComPort1->Output = 'F';
   Form1->Edit7->Color=clSilver;
   Form1->Edit7->Update();
}
//---------------------------------------------------------------------------
void __fastcall TForm1::ApdDataPacket1Packet(TObject *Sender, Pointer Data,
      int Size)
{
   unsigned short oc1rs_var;
   ApdComPort1->Output = String((char)oc1rs_var);//(oc1rs_var/256);
   ApdComPort1->Output = String((char)int(oc1rs_var/256));//(oc1rs_var/256);
}
//---------------------------------------------------------------------------
void __fastcall TForm1::ApdDataPacket2Packet(TObject *Sender, Pointer Data,
      int Size)
{
   ApdComPort1->Output = String((char)SetTemp);
   ApdComPort1->Output = String((char)int(SetTemp/256));
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox1Click(TObject *Sender)
{
   supersat=0.1;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();

   tinf=calc_temp_ss(T_sup,supersat);

   if (CheckBox1->Checked)
      {
         vetsat[0]=true;
         supersatatual=0;
         SetTemp=(1000.0*tinf);
         ApdComPort1->Output = 'B'; //retirar o commentário para setar a temp dif fgmp
      }
   else
   {
      vetsat[0]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox2Click(TObject *Sender)
{
   supersat=0.2;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox2->Checked)
   {
      vetsat[1]=true;
      supersatatual=1;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[1]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox3Click(TObject *Sender)
{
   supersat=0.3;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox3->Checked)
   {
      vetsat[2]=true;
      supersatatual=2;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[2]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox4Click(TObject *Sender)
{
   supersat=0.4;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox4->Checked)
   {
      vetsat[3]=true;
      supersatatual=3;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[3]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox5Click(TObject *Sender)
{
   supersat=0.5;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox5->Checked)
   {
      vetsat[4]=true;
      supersatatual=4;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[4]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox6Click(TObject *Sender)
{
   supersat=0.6;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox6->Checked)
   {
      vetsat[5]=true;
      supersatatual=5;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[5]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox7Click(TObject *Sender)
{
   supersat=0.7;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox7->Checked)
   {
      vetsat[6]=true;
      supersatatual=6;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[6]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox8Click(TObject *Sender)
{
   supersat=0.8;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox8->Checked)
   {
      vetsat[7]=true;
      supersatatual=7;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[7]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox9Click(TObject *Sender)
{
   supersat=0.9;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox9->Checked)
   {
      vetsat[8]=true;
      supersatatual=8;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B';
   }
   else
   {
      vetsat[8]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox10Click(TObject *Sender)
{
   supersat=1.0;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox10->Checked)
   {
      vetsat[9]=true;
      supersatatual=9;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B'; //retirar o commentário para setar a temp dif fgmp
   }
   else
   {
      vetsat[9]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button10Click(TObject *Sender)
{
   int a, tbomba;
   Button10->Enabled=false;
   while (fabs(T_dif-tinf)>0.1)
   {Application->ProcessMessages();}
   nciclo=nciclo+1;
   getdate(&d);
   gettime(&tboff);  //Registra início da amostragem
   liga_valvula();
   narq=0; //zera contador de imagens;
   ngotas=0;
   for (ih = 0; ih < IHD; ih++)
      for (iw = 0; iw < IWD; iw++)
         Image1->Canvas->Pixels[iw][ih]=clWhite;
   DelayTicks(20,true);
   gettime(&t);
   liga_bomba();
   DelayTicks(150,true);  //era 150 fgmp 01/02/2010
   desliga_valvula();
   DelayTicks(20,true);
   desliga_bomba();
   Form1->Edit8->Color=clRed;
   Form1->Edit8->Update();
   DelayTicks(20,true);
   VideoGrabber1->CaptureFrameTo (fc_TBitmap);
   VideoGrabber1->BurstType = fc_TBitmap;
   VideoGrabber1->BurstMode = True;
   Timer2->Enabled=true;  //Duração da gravação da imagem
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
   Timer1->Enabled=false;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button11Click(TObject *Sender)
{
   modo=false;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::ApdDataPacket3Packet(TObject *Sender, Pointer Data,
      int Size)
{
   char* MyData = new char[Size];
   char str1[100];
   unsigned int i,sec,idx;
   float value;
   FILE *out;
if (P1>20) //Aguarda 20 passagens para inciar a captura e apresentação dos dados
{
   Move(Data, MyData, Size);
   strncpy(str1,MyData+5,6);
   str1[6]=0;
   value=StrToFloatDef(str1,-1);
   if (value != -1)
   {
      T_sup_v[t_sup_indx]=value;
      t_sup_indx++;
      if(t_sup_indx>20)t_sup_indx=0;
      T_sup=0.0;
      for(idx=0;idx<20;idx++)T_sup=T_sup+T_sup_v[idx];
      T_sup=T_sup/20.0;
   }


   strncpy(str1,MyData+11,6);
   str1[6]=0;
     value=StrToFloatDef(str1,-1);
   if (value != -1)
   {
      T_inf_v[t_inf_indx]=value;
      t_inf_indx++;
      if(t_inf_indx>10)t_inf_indx=0;
      T_inf=0.0;
      for(idx=0;idx<10;idx++)T_inf=T_inf+T_inf_v[idx];
      T_inf=T_inf/10.0;
   }
   i1++;
   T_dif=T_sup-T_inf;
   Edit9->Text=FloatToStrF(T_sup,ffFixed,5,3);
   Edit10->Text=FloatToStrF(T_inf,ffFixed,5,3);
   Edit11->Text=FloatToStrF(T_dif,ffFixed,5,3);
   cxi++;

}
if (P1<30) P1++;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormCreate(TObject *Sender)
{
   unsigned int i;
   T_sup=0.0;
   T_inf=0.0;
   t_sup_indx=0;
   t_inf_indx=0;

   VideoGrabber1->UseNearestVideoSize (640, 480, false);
   i_det_float=0;
   iciclo=0;
   nrefb=0;
   cicloautomatico=false;
   Timer3->Enabled=false;
   Timer4->Enabled=false;
   narq=0; //controle do número de imagens geradas
   strcpy(thr,"0.2");
   i1=0;
   flag_test=0;
   ApdDataPacket3->AutoEnable=TRUE;
   ApdDataPacket3->Enabled=TRUE;
   P1=0;
   SetTemp=0;
   FILE *F1;
   char str1[80],str2[80];
   if ((F1 = fopen("PcCTRL.config", "r+t"))== NULL)
   {
      Application->MessageBox("Cannot open output file.", "Look", MB_OK);
   }
   else
   {
      fscanf(F1,"%s %s",&str1,&str2); //Edit1  Corrdenada do ponto x1
      strcpy(Form1->cpx1,str2);

      fscanf(F1,"%s %s",&str1,&str2); //Edit2  Corrdenada do ponto x2
      strcpy(Form1->cpx2,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit3  Corrdenada do ponto y1
      strcpy(Form1->cpy1,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit4  Corrdenada do ponto y2;
      strcpy(Form1->cpy2,str2);

      fscanf(F1,"%s %s",&str1,&str2);//Edit5  Escala X
      strcpy(Form1->escx,str2);

      fscanf(F1,"%s %s",&str1,&str2);  //Edit6  Escala Y
      strcpy(Form1->escy,str2);

     fscanf(F1,"%s %s",&str1,&str2);//Edit8 Exposição
      strcpy(Form1->expo,str2);
      expoi=StrToInt(str2);


      fscanf(F1,"%s %s",&str1,&str2); //Edit9 Thresholding
      strcpy(Form1->thr,str2);

      fscanf(F1,"%s %s",&str1,&str2); //Volume de amostragem
      strcpy(Form1->sampleVolume,str2);
      sampVol=StrToFloat(Form1->sampleVolume);

      fscanf(F1,"%s %s",&str1,&str2); //Volume de amostragem
      strcpy(T2IntervalStr,str2);
      Timer2->Interval=StrToInt(str2);

      fclose (F1);
   }
   VideoGrabber1->FrameRate = 1;
   VideoGrabber1->Enabled=TRUE;
   VideoGrabber1->StoragePath="\VG";
   VideoGrabber1->BurstMode = False;
   VideoGrabber1->VideoDevice =0;
   VideoGrabber1->Update();
   VideoGrabber1->CameraControlAuto=FALSE;
   VideoGrabber1->Update();
   VideoGrabber1->SetCameraControl (TCameraControl (4), Form1->expoi);
   VideoGrabber1->Update();
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button12Click(TObject *Sender)
{
   i2=i2+1;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button14Click(TObject *Sender)
{
   ApdComPort1->Output = 'H'; //SOBE AO MÁXIMO A TEMPERATURA
   flag_test=2;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button13Click(TObject *Sender)
{
   ApdComPort1->Output = 'G'; //Baixa ao máximo a temperatura inferior
   flag_test=1;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button15Click(TObject *Sender)
{
   ApdComPort1->Output = 'I';
   flag_test=3;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button16Click(TObject *Sender)
{
   ApdComPort1->Output = 'J';
   flag_test=0;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button17Click(TObject *Sender)
{
   VideoGrabber1->ShowDialog(dlg_Device);
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Timer2Timer(TObject *Sender)
{
//FIM DO PERÍODO DE AMOSTRAGEM
   char a[80],nomearq[80];
   unsigned int i;
   FILE *out;
   Timer2->Enabled=false;
   VideoGrabber1->BurstMode = False;
   supersatatual++;
   if (supersatatual>10)supersatatual=0;
   while(vetsat[supersatatual]==false)
   {
      supersatatual++;
      if (supersatatual>10)supersatatual=0;
   }
   supersatold=supersat;
   TsupOld=Edit9->Text;
   TinfOld=Edit10->Text;
   switch (supersatatual)
   {
    case 0:
       supersat=0.1;
       break;
    case 1:
       supersat=0.2;
       break;
    case 2:
       supersat=0.3;
       break;
    case 3:
       supersat=0.4;
       break;
    case 4:
       supersat=0.5;
       break;
    case 5:
       supersat=0.6;
       break;
    case 6:
       supersat=0.7;
       break;
    case 7:
       supersat=0.8;
       break;
    case 8:
       supersat=0.9;
       break;
    case 9:
       supersat=1.0;
       break;
    case 10:
       supersat=StrToFloat(Edit21->Text);  //1.0;
       break;
   }
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   SetTemp=(1000.0*tinf);
   ApdComPort1->Output = 'B';
   geraNome(nomearq);
   if ((out = fopen(nomearq, "a+t")) == NULL)
   {
      Application->MessageBox("Cannot open output file.", "Look", MB_OK);
   }
   else
   {
      fprintf(out,"%4d %2d %2d %2d:%02d:%02d %s %s %s %s\n",
                            d.da_year, d.da_mon, d.da_day,
                            tboff.ti_hour, tboff.ti_min,tboff.ti_sec,
                            FloatToStrF(supersatold,ffFixed,5,1),
                            Edit32->Text,Edit34->Text,Edit27->Text);
      fclose(out);
   }
   Form1->Edit8->Color=clSilver;
   Form1->Edit8->Update();

   Button10->Enabled=true;
   if(modo==true) Form1->Timer7->Enabled=true;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CCNCServiceClick(TObject *Sender)
{
   Form2 = new TForm2(Application);
   Form2->ShowModal();
   delete Form2;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CameraSetup1Click(TObject *Sender)
{
   VideoGrabber1->ShowDialog(dlg_Device);
}
//---------------------------------------------------------------------------
char  *current_directory(char *path)
{
  strcpy(path, "X:\\");      /* fill string with form of response: X:\ */
  path[0] = 'A' + getdisk();    /* replace X with current drive letter */
  getcurdir(0, path+3);  /* fill rest of string with current directory */
  return(path);
}
//---------------------------------------------------------------------------
char  *geraNome(char *nome)
{
   struct date d;
   char aux[3];
   int i;

   getdate(&d);
   strcpy(nome,IntToStr(d.da_year).c_str());  //.c_str()

   if(d.da_mon<10)
   {
      aux[0]='0';
      strcpy(aux+1,IntToStr(d.da_mon).c_str());
      aux[2]=0;
   }
   else
   {
      strcpy(aux,IntToStr(d.da_mon).c_str());
   }
   StrCat(nome,aux);


   if(d.da_day<10)
   {
      aux[0]='0';
      strcpy(aux+1,IntToStr(d.da_day).c_str());
      aux[2]=0;
   }
   else
   {
      strcpy(aux,IntToStr(d.da_day).c_str());
   }
   StrCat(nome,aux);
   StrCat(nome,"CCNC");
   StrCat(nome,".dat");
   return(nome);
}
/******************************************************************************/
void __fastcall TForm1::Exit1Click(TObject *Sender)
{
   ApdComPort1->Open=false;
   VideoGrabber1->StopPreview();//=false;
   Application->Terminate();
}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormClose(TObject *Sender, TCloseAction &Action)
{
   ApdComPort1->Open=false;
   Application->Terminate();

}
//---------------------------------------------------------------------------

void __fastcall TForm1::ConfiguraoGeral1Click(TObject *Sender)
{
    Form3 = new TForm3(Application);
    Form3->ShowModal();
    delete Form3;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Timer3Timer(TObject *Sender)
{
   Timer3->Enabled=false;
   Timer3->Interval=40000*2;
   Button10->Click();
}
//----------------------------2-----------------------------------------------
void __fastcall TForm1::Timer5Timer(TObject *Sender)
{
   Timer5->Enabled=false;
   VideoGrabber1->FrameRate = 25;
   VideoGrabber1->Enabled=TRUE;
   VideoGrabber1->StoragePath="\VG";
   VideoGrabber1->BurstMode = False;
   VideoGrabber1->VideoDevice =0;
   VideoGrabber1->Update();
   VideoGrabber1->CameraControlAuto=FALSE;
   VideoGrabber1->Update();
   VideoGrabber1->SetCameraControl (TCameraControl (4), Form1->expoi);
   VideoGrabber1->Update();
}
//---------------------------------------------------------------------------
float __fastcall calc_temp_ss(float tsup, float supersat)
{
   float temp_ss,ess,esi,es3,ss,dt,ti,t;
   ess=0.610778*exp((17.269*T_sup)/(T_sup+237.3));
   ss=0.0;
   dt=0.001;
   ti=T_sup-dt;
   while(ss<supersat)
   {
      ti=ti-dt;
      esi=0.610778*exp((17.269*ti)/(ti+237.3));
      t=(ti+T_sup)/2;
      es3=0.610778*exp((17.269*t)/(t+237.3));
      ss=((((ess+esi)/2)/es3)-1)*100;
   }
   temp_ss=T_sup-ti;
   return(temp_ss);
}
//---------------------------------------------------------------------------
void __fastcall TForm1::CheckBox11Click(TObject *Sender)
{
   supersat=StrToFloat(Edit21->Text);  //1.0;
   Edit1->Text=FloatToStrF(supersat,ffFixed,5,3);
   Edit1->Update();
   tinf=calc_temp_ss(T_sup,supersat);
   if (CheckBox11->Checked)
   {
      vetsat[10]=true;
      supersatatual=10;
      SetTemp=(1000.0*tinf);
      ApdComPort1->Output = 'B'; //retirar o commentário para setar a temp dif fgmp
   }
   else
   {
      vetsat[10]=false;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Timer7Timer(TObject *Sender)
{
   Timer7->Enabled=false;
   Button10->Click();
}
/***************************************************************************/
/***************************************************************************/
/***************************************************************************/
//---------------------------------------------------------------------------
void __fastcall watershed(void)
{
   for (xx = 0; xx<IWD ; xx++)
   {
      BW[xx][0]=0;
      BW[xx][IHD-1]=0;
   }
   for (yy = 0; yy<IHD; yy++)
   {
      BW[0][yy]=0;
      BW[IWD-1][yy]=0;
   }
/**FIM *CONVERTE IMAGEM COLORIDA EM BW**************/
/*********************TRANSFORMADA DE DISTÂNCIA**********************/

   for (yy=0; yy<IHD; yy++)
   {
      for(xx=0; xx<IWD; xx++)
      {
         if(BW[xx][yy]==0) ID[xx][yy]=0;
         else
         {
            sai=0;
            ws_d=1;
            while (sai==0)
            {
                for(y1=(yy-ws_d);y1<=(yy+ws_d);y1++)
                {
                    for(x1=(xx-ws_d);x1<=(xx+ws_d);x1++)
                    {
                        if((y1>=0)&&(x1>=0)&&(y1<IHD)&&(x1<IWD))
                        {
                           if(BW[x1][y1]==0)
                           {
                              ID[xx][yy]=255-ws_d;
                              sai=1;
                           }
                        }
                        if(sai==1)break;
                    }
                    if(sai==1) break;
                }
                ws_d++;
            }
        }
      }
   }
/***FIM******************TRANSFORMADA DE DISTÂNCIA**********************/

/***********************TRANSFORMADA WATERSHED*****************************/
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
//----------------------------------------------------------------------------
                  // histograma acumulado
   for (i=0;i<256;i++) hc[i]=0;
   for (i=hmin+1;i<hmax+1+1;i++) hc[i]=hc[i-1]+hist[i-1];
 //----------------------------------------------------------------------------

                  // vetor ordenado da imagem
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
//                %INICIALIZAÇÕES
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
//sec2=GetTickCount();
//sec3=sec3+(sec2-sec1);
if (rotulo>ngotas) ngotas=rotulo;
Form1->Edit32->Text=IntToStr(ngotas);
conc=ngotas/0.0285266;
Form1->Edit34->Text=FloatToStrF(conc,ffFixed,5,3);
for (ih = 0; ih < IHD; ih++)
   for (iw = 0; iw < IWD; iw++)
      if(imo[iw][ih]==0) Form1->Image1->Canvas->Pixels[iw][ih]=clRed;
//---------------------------------------------------------------------------

//FIM DO WTSH2
/************************FIM TRANSFORMADA WATERSHED*************************/
}
//---------------------------------------------------------------------------
void __fastcall TForm1::VideoGrabber1FrameCaptureCompleted(TObject *Sender,
      const TFrameData &FrameData, TFrameCaptureDest DestType,
      AnsiString FileName, bool Success)
{
   unsigned int K;
   K=78;
   if (alinhamento==false)
   {
      Graphics::TBitmap *bmpImage = new Graphics::TBitmap;
      bmpImage->Assign (FrameData.Bitmap);
      /***CONVERTE IMAGEM COLORIDA EM BW**************/
      ix=0;
      yy=0;
      soma=0;

/*
(IH1,IW1),
(IH2,IW2) ---> São as coordenadas iniciais e finais da região da
               imagem a ser processada. Estas coordenadas são definidas no
               processo de determinação do volume de amostragem.

BW ----------> Matriz que contém a imagem a ser processada. O seu tamanho é
               definido por (IH1,IW1), (IH2,IW2)
ID ----------> Matriz que contem a transformada de distância de BW
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
            ID[xx][yy]=0;
            xx++;
         }
         yy++;
      }
      delete bmpImage;
      for (ih = 0; ih < IHD; ih++)
         for (iw = 0; iw < IWD; iw++)
            if(BW[iw][ih]==255) Image1->Canvas->Pixels[iw][ih]=clLime;

      watershed();
      Edit33->Text=IntToStr(narq);
      narq=narq+1;
   }
   else
   {
      narq=narq+1;
      Form3->Edit1->Text=IntToStr(narq);
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::RecImagemTimer(TObject *Sender)
{  //Registra Imagem para processamento
   if(recImagemCnt<10)
   VideoGrabber1->CaptureFrameTo (fc_TBitmap);
   else
   {
      RecImagem->Enabled=False;
      recImagemCnt=0;
   }
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button1Click(TObject *Sender)
{
   modo=true;
   Button10->Click();
}
//---------------------------------------------------------------------------

