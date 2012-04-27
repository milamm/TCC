//---------------------------------------------------------------------------

#ifndef PcCTRL3H
#define PcCTRL3H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
//---------------------------------------------------------------------------
class TForm3 : public TForm
{
__published:	// IDE-managed Components
        TGroupBox *GroupBox1;
        TEdit *Edit1;
        TEdit *Edit2;
        TEdit *Edit3;
        TEdit *Edit4;
        TEdit *Edit5;
        TEdit *Edit6;
        TEdit *Edit7;
        TLabel *Label1;
        TGroupBox *GroupBox2;
        TEdit *Edit8;
        TLabel *Label2;
        TGroupBox *GroupBox3;
        TEdit *Edit9;
        TLabel *Label3;
        TButton *Button1;
        TButton *Button3;
        TEdit *Edit10;
        TLabel *Label4;
        TLabel *Label5;
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm3(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm3 *Form3;
//---------------------------------------------------------------------------
#endif
