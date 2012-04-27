object Form1: TForm1
  Left = 187
  Top = 138
  Width = 946
  Height = 543
  Caption = 'CCNC-SDTC ver 1.0'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  Position = poDesktopCenter
  OnClose = FormClose
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Label6: TLabel
    Left = 243
    Top = 28
    Width = 11
    Height = 13
    Caption = #186'C'
  end
  object Label20: TLabel
    Left = 8
    Top = 344
    Width = 78
    Height = 13
    Caption = 'Text to put in file'
  end
  object Image1: TImage
    Left = 8
    Top = 384
    Width = 265
    Height = 70
  end
  object GroupBox4: TGroupBox
    Left = 117
    Top = 7
    Width = 164
    Height = 250
    Caption = 'Measured Parameters'
    TabOrder = 1
    object Label1: TLabel
      Left = 4
      Top = 29
      Width = 69
      Height = 13
      Caption = 'T. Upper Plate'
    end
    object Label2: TLabel
      Left = 4
      Top = 52
      Width = 71
      Height = 13
      Caption = 'T. Botton Plate'
    end
    object Label3: TLabel
      Left = 4
      Top = 76
      Width = 29
      Height = 13
      Caption = 'T. Diff'
    end
    object Label7: TLabel
      Left = 132
      Top = 52
      Width = 11
      Height = 13
      Caption = #186'C'
    end
    object Label8: TLabel
      Left = 133
      Top = 76
      Width = 11
      Height = 13
      Caption = #186'C'
    end
    object Label9: TLabel
      Left = 132
      Top = 28
      Width = 11
      Height = 13
      Caption = #186'C'
    end
    object Label4: TLabel
      Left = 4
      Top = 100
      Width = 50
      Height = 13
      Caption = 'Super Sat.'
    end
    object Label5: TLabel
      Left = 8
      Top = 145
      Width = 42
      Height = 13
      Caption = 'N. Drops'
    end
    object Label10: TLabel
      Left = 9
      Top = 168
      Width = 66
      Height = 13
      Caption = 'Concentration'
    end
    object Label11: TLabel
      Left = 11
      Top = 205
      Width = 48
      Height = 13
      Caption = 'N. Frames'
    end
    object Label12: TLabel
      Left = 136
      Top = 100
      Width = 8
      Height = 13
      Caption = '%'
    end
    object Label13: TLabel
      Left = 128
      Top = 168
      Width = 25
      Height = 13
      Caption = 'd/cm'
    end
    object Label14: TLabel
      Left = 153
      Top = 162
      Width = 6
      Height = 13
      Caption = '3'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -7
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
    end
    object Edit9: TEdit
      Left = 80
      Top = 24
      Width = 50
      Height = 21
      TabOrder = 0
      Text = '0.0'
    end
    object Edit10: TEdit
      Left = 80
      Top = 48
      Width = 50
      Height = 21
      TabOrder = 1
      Text = '0.0'
    end
    object Edit11: TEdit
      Left = 80
      Top = 72
      Width = 50
      Height = 21
      TabOrder = 2
      Text = '0.0'
    end
    object Edit1: TEdit
      Left = 80
      Top = 96
      Width = 49
      Height = 21
      TabOrder = 3
      Text = 'Edit1'
    end
  end
  object GroupBox5: TGroupBox
    Left = 8
    Top = 8
    Width = 105
    Height = 129
    Caption = 'S. Saturation'
    TabOrder = 2
    object CheckBox1: TCheckBox
      Left = 8
      Top = 16
      Width = 41
      Height = 17
      Caption = '0.1'
      TabOrder = 0
      OnClick = CheckBox1Click
    end
    object CheckBox2: TCheckBox
      Left = 8
      Top = 32
      Width = 41
      Height = 17
      Caption = '0.2'
      TabOrder = 1
      OnClick = CheckBox2Click
    end
    object CheckBox3: TCheckBox
      Left = 8
      Top = 48
      Width = 41
      Height = 17
      Caption = '0.3'
      TabOrder = 2
      OnClick = CheckBox3Click
    end
    object CheckBox4: TCheckBox
      Left = 8
      Top = 64
      Width = 41
      Height = 17
      Caption = '0.4'
      TabOrder = 3
      OnClick = CheckBox4Click
    end
    object CheckBox5: TCheckBox
      Left = 8
      Top = 80
      Width = 41
      Height = 17
      Caption = '0.5'
      TabOrder = 4
      OnClick = CheckBox5Click
    end
    object CheckBox6: TCheckBox
      Left = 56
      Top = 16
      Width = 41
      Height = 17
      Caption = '0.6'
      TabOrder = 5
      OnClick = CheckBox6Click
    end
    object CheckBox7: TCheckBox
      Left = 56
      Top = 32
      Width = 41
      Height = 17
      Caption = '0.7'
      TabOrder = 6
      OnClick = CheckBox7Click
    end
    object CheckBox8: TCheckBox
      Left = 56
      Top = 48
      Width = 41
      Height = 17
      Caption = '0.8'
      TabOrder = 7
      OnClick = CheckBox8Click
    end
    object CheckBox9: TCheckBox
      Left = 56
      Top = 64
      Width = 41
      Height = 17
      Caption = '0.9'
      TabOrder = 8
      OnClick = CheckBox9Click
    end
    object CheckBox10: TCheckBox
      Left = 56
      Top = 80
      Width = 41
      Height = 17
      Caption = '1.0'
      TabOrder = 9
      OnClick = CheckBox10Click
    end
    object Edit21: TEdit
      Left = 8
      Top = 104
      Width = 33
      Height = 21
      TabOrder = 10
      Text = '2.0'
    end
    object CheckBox11: TCheckBox
      Left = 48
      Top = 104
      Width = 49
      Height = 17
      Caption = 'Other'
      TabOrder = 11
      OnClick = CheckBox11Click
    end
  end
  object GroupBox6: TGroupBox
    Left = 8
    Top = 144
    Width = 105
    Height = 113
    Caption = 'Status'
    TabOrder = 3
    object Edit6: TEdit
      Left = 24
      Top = 24
      Width = 57
      Height = 21
      AutoSelect = False
      Color = clScrollBar
      TabOrder = 0
      Text = 'VALVE'
    end
    object Edit7: TEdit
      Left = 24
      Top = 48
      Width = 57
      Height = 21
      Color = clScrollBar
      TabOrder = 1
      Text = 'PUMP'
    end
    object Edit8: TEdit
      Left = 24
      Top = 72
      Width = 57
      Height = 21
      Color = clScrollBar
      TabOrder = 2
      Text = 'MEASURING'
    end
  end
  object GroupBox7: TGroupBox
    Left = 8
    Top = 264
    Width = 273
    Height = 73
    Caption = 'Command'
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    ParentFont = False
    TabOrder = 4
    object Button10: TButton
      Left = 8
      Top = 16
      Width = 73
      Height = 25
      Caption = 'Start Single'
      TabOrder = 0
      OnClick = Button10Click
    end
    object Button11: TButton
      Left = 8
      Top = 40
      Width = 73
      Height = 25
      Caption = 'Stop'
      TabOrder = 1
      OnClick = Button11Click
    end
    object Button1: TButton
      Left = 80
      Top = 16
      Width = 75
      Height = 25
      Caption = 'Start Burst'
      TabOrder = 2
      OnClick = Button1Click
    end
  end
  object AdTerminal1: TAdTerminal
    Left = 504
    Top = 104
    Width = 33
    Height = 33
    Capture = cmOn
    CaptureFile = 'APROTERM.CAP'
    ComPort = ApdComPort1
    Rows = 15
    ScrollbackRows = 15
    Scrollback = False
    Color = clBlack
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clSilver
    Font.Height = -19
    Font.Name = 'Terminal'
    Font.Style = []
    ParentColor = False
    ParentFont = False
    TabOrder = 0
  end
  object Edit27: TEdit
    Left = 8
    Top = 360
    Width = 265
    Height = 21
    TabOrder = 5
  end
  object VideoGrabber1: TVideoGrabber
    Left = 288
    Top = 8
    Width = 640
    Height = 480
    Caption = 'VideoGrabber1'
    AutoStartPreview = True
    AudioDevice = 0
    AudioFormat = af_default
    AudioRendering = False
    AutoConnectRelatedPins = True
    AutoFileName = fn_Sequential
    AutoFilePrefix = 'vg'
    AutoRefreshPreview = False
    AutoSize = True
    AutoStartPlayer = True
    AVICaptureCanPause = False
    AVIFormatOpenDML = True
    BurstCount = 0
    BorderStyle = bsSingle
    BurstInterval = 0
    BurstMode = False
    BurstType = fc_TBitmap
    BusyCursor = crHourGlass
    AudioCapture = False
    CaptureFileExt = 'avi'
    ColorKeyEnabled = False
    CompressionMode = cm_NoCompression
    CompressionType = ct_Video
    DVNativeInterleavedToAVI = False
    DVRgb219 = False
    DVReduceFrameRate = False
    DVUseExternalAudio = False
    FrameBuffers = 1
    FrameCaptureBeforeDrawing = False
    FrameCaptureZoomSize = 100
    FrameGrabber = fg_BothStreams
    FullScreenPreview = False
    InvertedColors = False
    JPEGPerformance = jpBestQuality
    JPEGProgressiveDisplay = False
    JPEGQuality = 100
    NormalCursor = crDefault
    VideoPortEnabled = False
    PlayerFastSeekSpeedRatio = 4
    PlayerSpeedRatio = 1
    PreallocCapFileEnabled = False
    PreallocCapFileSizeInMB = 100
    PreallocCapFileRecreate = False
    PreviewZoomSize = 100
    PlayerRefreshPausedDisplay = True
    ReducePreviewCPULoad = False
    StoreDeviceSettingsInRegistry = True
    ShapeOnFrameEnabled = False
    TextOnFrameEnabled = False
    TextOnFrame_Left = 0
    TextOnFrame_Top = 0
    TextOnFrame_Right = 320
    TextOnFrameBkColor = clWhite
    TextOnFrameAlign = tf_Left
    TextOnFrameFont.Charset = DEFAULT_CHARSET
    TextOnFrameFont.Color = clAqua
    TextOnFrameFont.Height = -11
    TextOnFrameFont.Name = 'MS Sans Serif'
    TextOnFrameFont.Style = []
    TextOnFrameString = 'this text can be written over video frames'
    TextOnFrameTransparent = True
    TVAutoTuneWeakChannels = False
    TranslateMouseCoordinates = True
    Version = 'v6.7 (build 6.7.7), February 12, 2004'
    VideoDevice = 0
    Visible = True
    OnFrameCaptureCompleted = VideoGrabber1FrameCaptureCompleted
  end
  object Edit32: TEdit
    Left = 197
    Top = 148
    Width = 49
    Height = 21
    TabOrder = 7
    Text = 'Edit32'
  end
  object Edit33: TEdit
    Left = 195
    Top = 208
    Width = 49
    Height = 21
    TabOrder = 8
    Text = 'Edit33'
  end
  object Edit34: TEdit
    Left = 197
    Top = 172
    Width = 49
    Height = 21
    TabOrder = 9
    Text = 'Edit34'
  end
  object ApdComPort1: TApdComPort
    ComNumber = 4
    Baud = 57600
    Tracing = tlOn
    TraceName = 'APRO.TRC'
    LogName = 'APRO.LOG'
    TapiMode = tmOff
    Left = 624
    Top = 48
  end
  object ApdDataPacket1: TApdDataPacket
    Enabled = True
    StartString = 'CMD1'
    ComPort = ApdComPort1
    PacketSize = 0
    OnPacket = ApdDataPacket1Packet
    Left = 664
    Top = 80
  end
  object ApdDataPacket2: TApdDataPacket
    Enabled = True
    StartString = 'SETTEMP'
    ComPort = ApdComPort1
    PacketSize = 0
    OnPacket = ApdDataPacket2Packet
    Left = 664
    Top = 112
  end
  object MainMenu1: TMainMenu
    Left = 632
    Top = 16
    object File1: TMenuItem
      Caption = 'File'
      object New1: TMenuItem
        Caption = 'New'
      end
      object Open1: TMenuItem
        Caption = 'Open'
      end
      object Exit1: TMenuItem
        Caption = 'Exit'
        OnClick = Exit1Click
      end
    end
    object Tools1: TMenuItem
      Caption = 'Tools'
      object Comunications1: TMenuItem
        Caption = 'Comunications'
      end
      object GraphicsOptions1: TMenuItem
        Caption = 'Graphics Options'
      end
      object Setup1: TMenuItem
        Caption = 'Setup'
      end
      object CCNCService: TMenuItem
        Caption = 'CCNC Service'
        OnClick = CCNCServiceClick
      end
      object CameraSetup1: TMenuItem
        Caption = 'Camera Setup'
        OnClick = CameraSetup1Click
      end
      object Calibrao1: TMenuItem
        Caption = 'Calibra'#231#227'o'
      end
      object ConfiguraoGeral1: TMenuItem
        Caption = 'General setup'
        OnClick = ConfiguraoGeral1Click
      end
    end
    object Help1: TMenuItem
      Caption = 'Help'
      object CCNCHelp1: TMenuItem
        Caption = 'CCNC Help'
      end
      object About1: TMenuItem
        Caption = 'About'
      end
    end
  end
  object Timer1: TTimer
    Enabled = False
    Interval = 10000
    OnTimer = Timer1Timer
    Left = 696
    Top = 16
  end
  object ApdDataPacket3: TApdDataPacket
    Enabled = False
    AutoEnable = False
    EndCond = [ecString]
    StartString = 'TSTI'
    EndString = #13
    ComPort = ApdComPort1
    PacketSize = 0
    OnPacket = ApdDataPacket3Packet
    Left = 664
    Top = 16
  end
  object Timer2: TTimer
    Enabled = False
    Interval = 10000
    OnTimer = Timer2Timer
    Left = 696
    Top = 48
  end
  object ApdDataPacket4: TApdDataPacket
    Enabled = True
    EndCond = [ecString]
    StartString = 'DETI'
    EndString = 'TIDE'
    ComPort = ApdComPort1
    PacketSize = 0
    Left = 664
    Top = 48
  end
  object Timer3: TTimer
    Enabled = False
    OnTimer = Timer3Timer
    Left = 696
    Top = 80
  end
  object Timer4: TTimer
    Enabled = False
    Interval = 500
    Left = 696
    Top = 112
  end
  object Timer5: TTimer
    Enabled = False
    Interval = 10000
    OnTimer = Timer5Timer
    Left = 696
    Top = 144
  end
  object Timer7: TTimer
    Enabled = False
    OnTimer = Timer7Timer
    Left = 752
    Top = 16
  end
  object RecImagem: TTimer
    Enabled = False
    Interval = 500
    OnTimer = RecImagemTimer
    Left = 752
    Top = 48
  end
end
