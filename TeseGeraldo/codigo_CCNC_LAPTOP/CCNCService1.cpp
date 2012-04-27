//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "CCNCService1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TCCNCService *CCNCService;
//---------------------------------------------------------------------------
__fastcall TCCNCService::TCCNCService(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
