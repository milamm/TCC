//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit2.h"
#include "PcCTRL1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm2 *Form2;
char sample;
//---------------------------------------------------------------------------
__fastcall TForm2::TForm2(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button2Click(TObject *Sender)
{
         Form1->ApdComPort1->Output = 'C';
         Form1->Edit6->Color=clRed;

}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button3Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'D';
        Form1->Edit6->Color=clSilver;

}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button4Click(TObject *Sender)
{

        Form1->ApdComPort1->Output = 'E';
        DelayTicks(500,true);
        Form1->ApdComPort1->Output = 'F';
        Form1->Edit7->Color=clRed;

}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button5Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'F';
        Form1->Edit7->Color=clSilver;
        
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button6Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'H'; //SOBE AO MÁXIMO A TEMPERATURA
        Form1->flag_test=2;

}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button7Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'G'; //Baixa ao máximo a temperatura inferior
        Form1->flag_test=1;
        
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button8Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'J';
        Form1->flag_test=0;
        
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button9Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'I';
        Form1->flag_test=3;
        
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button1Click(TObject *Sender)
{
        Form1->ApdComPort1->Output = 'D';
        Form1->Edit6->Color=clSilver;

        Form1->ApdComPort1->Output = 'F';
        Form1->Edit7->Color=clSilver;

        Form1->ApdComPort1->Output = 'J';
        Form1->flag_test=0;
Close();


        
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button10Click(TObject *Sender)
{
   Form1->VideoGrabber1->BurstType = fc_JpegFile;
   Form1->VideoGrabber1->BurstMode = True;
   Form1->alinhamento=true;


}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button11Click(TObject *Sender)
{
   Form1->VideoGrabber1->BurstMode = False;
   Form1->VideoGrabber1->BurstType = fc_TBitmap;
   Form1->alinhamento=false;
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button13Click(TObject *Sender)
{
   Form1->VideoGrabber1->ShowDialog(dlg_Device);
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button12Click(TObject *Sender)
{
/*   if (CheckBox1->Checked) {
      Form_MainDemo->VideoGrabber1->CaptureFrameTo (fc_JpegFile, Edit12->Text);
   }
   else {*/
      Form1->VideoGrabber1->CaptureFrameTo (fc_JpegFile);
//   }

}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button14Click(TObject *Sender)
{
   if (sample==false)
   {
      Button14->Caption="Sample OFF";
      Form1->ApdComPort1->Output = 'E'; //Liga Bomba
      DelayTicks(20,true);

      Form1->ApdComPort1->Output = 'C';  //Liga Válvula
      Form1->Edit6->Color=clRed;
      sample=true;
   }
   else
   {
         Button14->Caption="Sample ON";
        Form1->ApdComPort1->Output = 'D';
        Form1->Edit6->Color=clSilver;
        DelayTicks(20,true);
        Form1->ApdComPort1->Output = 'F';
        Form1->Edit7->Color=clSilver;
        sample=false;
        
   }
   


}
//---------------------------------------------------------------------------

void __fastcall TForm2::FormCreate(TObject *Sender)
{
sample=false;
}
//---------------------------------------------------------------------------

