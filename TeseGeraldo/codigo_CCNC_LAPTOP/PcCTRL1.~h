//---------------------------------------------------------------------------

#ifndef PcCTRL1H
#define PcCTRL1H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "AdPort.hpp"
#include "ADTrmEmu.hpp"
#include "OoMisc.hpp"
#include "AdPacket.hpp"
#include <Menus.hpp>
#include <ExtCtrls.hpp>
#include <Chart.hpp>
#include <Series.hpp>
#include <TeEngine.hpp>
#include <TeeProcs.hpp>
#include "VidGrab.hpp"

//---------------------------------------------------------------------------
class TForm1 : public TForm
{
__published:	// IDE-managed Components
        TApdComPort *ApdComPort1;
        TAdTerminal *AdTerminal1;
        TApdDataPacket *ApdDataPacket1;
        TApdDataPacket *ApdDataPacket2;
        TMainMenu *MainMenu1;
        TMenuItem *File1;
        TMenuItem *Tools1;
        TMenuItem *Help1;
        TMenuItem *Comunications1;
        TMenuItem *GraphicsOptions1;
        TMenuItem *Setup1;
        TMenuItem *New1;
        TMenuItem *Open1;
        TMenuItem *Exit1;
        TMenuItem *CCNCHelp1;
        TMenuItem *About1;
        TGroupBox *GroupBox4;
        TGroupBox *GroupBox5;
        TCheckBox *CheckBox1;
        TCheckBox *CheckBox2;
        TCheckBox *CheckBox3;
        TCheckBox *CheckBox4;
        TCheckBox *CheckBox5;
        TCheckBox *CheckBox6;
        TCheckBox *CheckBox7;
        TCheckBox *CheckBox8;
        TCheckBox *CheckBox9;
        TCheckBox *CheckBox10;
        TGroupBox *GroupBox6;
        TEdit *Edit6;
        TEdit *Edit7;
        TEdit *Edit8;
        TEdit *Edit9;
        TEdit *Edit10;
        TEdit *Edit11;
        TLabel *Label1;
        TLabel *Label2;
        TLabel *Label3;
        TLabel *Label7;
        TLabel *Label8;
        TLabel *Label6;
        TLabel *Label9;
        TGroupBox *GroupBox7;
        TButton *Button10;
        TButton *Button11;
        TTimer *Timer1;
        TApdDataPacket *ApdDataPacket3;
        TTimer *Timer2;
        TApdDataPacket *ApdDataPacket4;
        TMenuItem *CCNCService;
        TMenuItem *CameraSetup1;
        TMenuItem *Calibrao1;
        TMenuItem *ConfiguraoGeral1;
        TTimer *Timer3;
        TTimer *Timer4;
        TTimer *Timer5;
        TEdit *Edit21;
        TCheckBox *CheckBox11;
        TTimer *Timer7;
        TEdit *Edit27;
        TLabel *Label20;
   TTimer *RecImagem;
   TImage *Image1;
   TVideoGrabber *VideoGrabber1;
   TEdit *Edit32;
   TEdit *Edit33;
        TEdit *Edit34;
   TEdit *Edit1;
   TLabel *Label4;
   TLabel *Label5;
   TLabel *Label10;
   TLabel *Label11;
   TLabel *Label12;
   TLabel *Label13;
   TLabel *Label14;
   TButton *Button1;
        void __fastcall ApdDataPacket1Packet(TObject *Sender, Pointer Data,
          int Size);
        void __fastcall ApdDataPacket2Packet(TObject *Sender, Pointer Data,
          int Size);
        void __fastcall CheckBox1Click(TObject *Sender);
        void __fastcall CheckBox2Click(TObject *Sender);
        void __fastcall CheckBox3Click(TObject *Sender);
        void __fastcall CheckBox4Click(TObject *Sender);
        void __fastcall CheckBox5Click(TObject *Sender);
        void __fastcall CheckBox6Click(TObject *Sender);
        void __fastcall CheckBox7Click(TObject *Sender);
        void __fastcall CheckBox8Click(TObject *Sender);
        void __fastcall CheckBox9Click(TObject *Sender);
        void __fastcall CheckBox10Click(TObject *Sender);
        void __fastcall Button10Click(TObject *Sender);
        void __fastcall Timer1Timer(TObject *Sender);
        void __fastcall Button11Click(TObject *Sender);
        void __fastcall ApdDataPacket3Packet(TObject *Sender, Pointer Data,
          int Size);
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall Button12Click(TObject *Sender);
        void __fastcall Button14Click(TObject *Sender);
        void __fastcall Button13Click(TObject *Sender);
        void __fastcall Button15Click(TObject *Sender);
        void __fastcall Button16Click(TObject *Sender);
        void __fastcall Button17Click(TObject *Sender);
        void __fastcall Timer2Timer(TObject *Sender);
        void __fastcall CCNCServiceClick(TObject *Sender);
        void __fastcall CameraSetup1Click(TObject *Sender);
        void __fastcall Exit1Click(TObject *Sender);
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall ConfiguraoGeral1Click(TObject *Sender);
        void __fastcall Timer3Timer(TObject *Sender);
//        void __fastcall delayVideoGraberTimer(TObject *Sender);
        void __fastcall Timer5Timer(TObject *Sender);
        void __fastcall CheckBox11Click(TObject *Sender);
        void __fastcall Timer7Timer(TObject *Sender);
   void __fastcall VideoGrabber1FrameCaptureCompleted(TObject *Sender,
          const TFrameData &FrameData, TFrameCaptureDest DestType,
          AnsiString FileName, bool Success);
   void __fastcall RecImagemTimer(TObject *Sender);
   void __fastcall Button1Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm1(TComponent* Owner);
int flag_test;
char thr[10],cpx1[10],cpx2[10],cpy1[10],cpy2[10]; //0.2 84 165 1 320
char sampleVolume[20];
char escx[10],escy[10];
char expo[10];
char T2IntervalStr[20];
unsigned int expoi,alinhamento;
float sampVol;
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
