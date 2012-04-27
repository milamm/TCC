object Form2: TForm2
  Left = 197
  Top = 166
  Width = 477
  Height = 275
  Caption = 'CCNC - Service'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 8
    Top = 8
    Width = 121
    Height = 89
    Caption = 'Valve'
    TabOrder = 0
    object Button2: TButton
      Left = 24
      Top = 16
      Width = 75
      Height = 25
      Caption = 'On'
      TabOrder = 0
      OnClick = Button2Click
    end
    object Button3: TButton
      Left = 24
      Top = 48
      Width = 75
      Height = 25
      Caption = 'Off'
      TabOrder = 1
      OnClick = Button3Click
    end
  end
  object GroupBox2: TGroupBox
    Left = 136
    Top = 8
    Width = 121
    Height = 89
    Caption = 'Pump'
    TabOrder = 1
    object Button4: TButton
      Left = 24
      Top = 16
      Width = 75
      Height = 25
      Caption = 'On'
      TabOrder = 0
      OnClick = Button4Click
    end
    object Button5: TButton
      Left = 24
      Top = 48
      Width = 75
      Height = 25
      Caption = 'Off'
      TabOrder = 1
      OnClick = Button5Click
    end
  end
  object GroupBox3: TGroupBox
    Left = 264
    Top = 8
    Width = 185
    Height = 89
    Caption = 'Peltier'
    TabOrder = 2
    object Button6: TButton
      Left = 8
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Max Temp'
      TabOrder = 0
      OnClick = Button6Click
    end
    object Button7: TButton
      Left = 8
      Top = 48
      Width = 75
      Height = 25
      Caption = 'MIn Temp'
      TabOrder = 1
      OnClick = Button7Click
    end
    object Button8: TButton
      Left = 96
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Control On'
      TabOrder = 2
      OnClick = Button8Click
    end
    object Button9: TButton
      Left = 96
      Top = 48
      Width = 75
      Height = 25
      Caption = 'Control Off'
      TabOrder = 3
      OnClick = Button9Click
    end
  end
  object Button1: TButton
    Left = 312
    Top = 208
    Width = 75
    Height = 25
    Caption = 'Exit'
    TabOrder = 3
    OnClick = Button1Click
  end
  object GroupBox4: TGroupBox
    Left = 8
    Top = 104
    Width = 249
    Height = 129
    Caption = 'WebCam'
    TabOrder = 4
    object Label1: TLabel
      Left = 100
      Top = 24
      Width = 134
      Height = 13
      Caption = 'File  name of recorded frame'
    end
    object Button10: TButton
      Left = 8
      Top = 56
      Width = 75
      Height = 25
      Caption = 'Start Frame'
      TabOrder = 0
      OnClick = Button10Click
    end
    object Button11: TButton
      Left = 8
      Top = 88
      Width = 75
      Height = 25
      Caption = 'Stop Frame'
      TabOrder = 1
      OnClick = Button11Click
    end
    object Button12: TButton
      Left = 8
      Top = 24
      Width = 75
      Height = 25
      Caption = 'Single Frame'
      TabOrder = 2
      OnClick = Button12Click
    end
    object Button13: TButton
      Left = 128
      Top = 80
      Width = 75
      Height = 25
      Caption = 'Setup'
      TabOrder = 3
      OnClick = Button13Click
    end
    object Edit1: TEdit
      Left = 104
      Top = 40
      Width = 121
      Height = 21
      TabOrder = 4
    end
  end
  object Button14: TButton
    Left = 264
    Top = 112
    Width = 75
    Height = 25
    Caption = 'Sample ON'
    TabOrder = 5
    OnClick = Button14Click
  end
end
