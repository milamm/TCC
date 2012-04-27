object Form3: TForm3
  Left = 225
  Top = 170
  Width = 682
  Height = 312
  Caption = 'General Setup'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poDesktopCenter
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 8
    Top = 16
    Width = 289
    Height = 201
    Caption = 'Setup Sample Volume'
    TabOrder = 0
    object Label1: TLabel
      Left = 56
      Top = 176
      Width = 68
      Height = 13
      Caption = 'Volume (mmm)'
    end
    object Edit1: TEdit
      Left = 16
      Top = 88
      Width = 60
      Height = 21
      TabOrder = 0
      Text = 'Edit1'
    end
    object Edit2: TEdit
      Left = 88
      Top = 88
      Width = 60
      Height = 21
      TabOrder = 1
      Text = 'Edit2'
    end
    object Edit3: TEdit
      Left = 56
      Top = 56
      Width = 60
      Height = 21
      TabOrder = 2
      Text = 'Edit3'
    end
    object Edit4: TEdit
      Left = 64
      Top = 120
      Width = 50
      Height = 21
      TabOrder = 3
      Text = 'Edit4'
    end
    object Edit5: TEdit
      Left = 200
      Top = 64
      Width = 60
      Height = 21
      TabOrder = 4
      Text = 'Edit5'
    end
    object Edit6: TEdit
      Left = 200
      Top = 104
      Width = 60
      Height = 21
      TabOrder = 5
      Text = 'Edit6'
    end
    object Edit7: TEdit
      Left = 128
      Top = 168
      Width = 60
      Height = 21
      TabOrder = 6
      Text = 'Edit7'
    end
  end
  object GroupBox2: TGroupBox
    Left = 304
    Top = 16
    Width = 185
    Height = 201
    Caption = 'C'#226'mera'
    TabOrder = 1
    object Label2: TLabel
      Left = 24
      Top = 32
      Width = 49
      Height = 13
      Caption = 'Exposi'#231#227'o'
    end
    object Edit8: TEdit
      Left = 80
      Top = 24
      Width = 60
      Height = 21
      TabOrder = 0
      Text = 'Edit8'
    end
  end
  object GroupBox3: TGroupBox
    Left = 496
    Top = 16
    Width = 161
    Height = 201
    Caption = 'Image Processing'
    TabOrder = 2
    object Label3: TLabel
      Left = 16
      Top = 32
      Width = 61
      Height = 13
      Caption = 'Thresholding'
    end
    object Label4: TLabel
      Left = 32
      Top = 80
      Width = 30
      Height = 13
      Caption = 'Period'
    end
    object Label5: TLabel
      Left = 112
      Top = 80
      Width = 19
      Height = 13
      Caption = 'Sec'
    end
    object Edit9: TEdit
      Left = 88
      Top = 24
      Width = 60
      Height = 21
      TabOrder = 0
      Text = 'Edit9'
    end
    object Edit10: TEdit
      Left = 72
      Top = 72
      Width = 41
      Height = 21
      TabOrder = 1
      Text = '10'
    end
  end
  object Button1: TButton
    Left = 232
    Top = 240
    Width = 75
    Height = 25
    Caption = 'Save'
    TabOrder = 3
    OnClick = Button1Click
  end
  object Button3: TButton
    Left = 344
    Top = 240
    Width = 75
    Height = 25
    Caption = 'Exit'
    TabOrder = 4
  end
end
