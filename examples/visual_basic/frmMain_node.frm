VERSION 5.00
Begin VB.Form frmMain 
   Caption         =   "vbsweph"
   ClientHeight    =   8670
   ClientLeft      =   165
   ClientTop       =   735
   ClientWidth     =   9120
   LinkTopic       =   "Form1"
   ScaleHeight     =   8670
   ScaleWidth      =   9120
   StartUpPosition =   3  'Windows Default
   Begin VB.CheckBox is_sidereal 
      Caption         =   "sidereal"
      Height          =   252
      Left            =   2880
      TabIndex        =   26
      Top             =   480
      Width           =   1092
   End
   Begin VB.CheckBox is_j2000 
      Caption         =   "Equinox J2000"
      Height          =   192
      Left            =   4440
      TabIndex        =   25
      Top             =   480
      Width           =   1332
   End
   Begin VB.CheckBox is_apparent 
      Caption         =   "apparent pos."
      Height          =   195
      Left            =   2880
      TabIndex        =   24
      Top             =   240
      Value           =   1  'Checked
      Width           =   1335
   End
   Begin VB.TextBox geolat 
      Height          =   288
      Left            =   6960
      TabIndex        =   23
      Text            =   "47.00"
      Top             =   480
      Width           =   612
   End
   Begin VB.TextBox geolon 
      Height          =   288
      Left            =   5880
      TabIndex        =   22
      Text            =   "8.00"
      Top             =   480
      Width           =   732
   End
   Begin VB.CheckBox with_houses 
      Caption         =   "with Placidus houses"
      Height          =   192
      Left            =   5880
      TabIndex        =   21
      Top             =   240
      Width           =   1932
   End
   Begin VB.TextBox fstar 
      Height          =   324
      Left            =   3600
      TabIndex        =   19
      Top             =   960
      Width           =   1212
   End
   Begin VB.CheckBox add_hypo 
      Caption         =   "with hypothetical bodies"
      Height          =   255
      Left            =   5880
      TabIndex        =   18
      Top             =   0
      Width           =   2415
   End
   Begin VB.CheckBox bary_flag 
      Caption         =   "barycentric"
      Height          =   192
      Left            =   4440
      TabIndex        =   17
      Top             =   240
      Width           =   1092
   End
   Begin VB.TextBox minute 
      Alignment       =   2  'Center
      Height          =   288
      Left            =   2880
      MaxLength       =   2
      TabIndex        =   13
      Text            =   "00"
      Top             =   960
      Width           =   372
   End
   Begin VB.TextBox hour 
      Alignment       =   2  'Center
      Height          =   288
      Left            =   2280
      MaxLength       =   2
      TabIndex        =   12
      Text            =   "00"
      Top             =   960
      Width           =   372
   End
   Begin VB.PictureBox out 
      AutoRedraw      =   -1  'True
      Height          =   7455
      Left            =   120
      ScaleHeight     =   7395
      ScaleWidth      =   8595
      TabIndex        =   11
      Top             =   1440
      Width           =   8655
   End
   Begin VB.CheckBox hel_flag 
      Caption         =   "heliocentric"
      Height          =   192
      Left            =   4440
      TabIndex        =   10
      Top             =   0
      Width           =   1215
   End
   Begin VB.CheckBox et_flag 
      Caption         =   "Ephemeris time"
      Height          =   192
      Left            =   2880
      TabIndex        =   9
      Top             =   0
      Value           =   1  'Checked
      Width           =   1572
   End
   Begin VB.PictureBox Picture1 
      Height          =   372
      Left            =   120
      Picture         =   "frmMain_node.frx":0000
      ScaleHeight     =   315
      ScaleWidth      =   2355
      TabIndex        =   8
      Top             =   0
      Width           =   2415
   End
   Begin VB.CommandButton Compute 
      Caption         =   "Compute!"
      Height          =   372
      Left            =   5880
      TabIndex        =   7
      Top             =   960
      Width           =   1092
   End
   Begin VB.TextBox Year 
      Height          =   288
      Left            =   1320
      TabIndex        =   3
      Text            =   "1997"
      Top             =   960
      Width           =   732
   End
   Begin VB.TextBox Month 
      Height          =   288
      Left            =   720
      TabIndex        =   2
      Text            =   "1"
      Top             =   960
      Width           =   492
   End
   Begin VB.TextBox Day 
      Height          =   288
      Left            =   120
      MaxLength       =   2
      TabIndex        =   1
      Text            =   "1"
      Top             =   960
      Width           =   492
   End
   Begin ComctlLib.StatusBar sbStatusBar 
      Align           =   2  'Align Bottom
      Height          =   270
      Left            =   0
      TabIndex        =   0
      Top             =   8400
      Width           =   9120
      _ExtentX        =   16087
      _ExtentY        =   476
      SimpleText      =   ""
      _Version        =   327682
      BeginProperty Panels {0713E89E-850A-101B-AFC0-4210102A8DA7} 
         NumPanels       =   3
         BeginProperty Panel1 {0713E89F-850A-101B-AFC0-4210102A8DA7} 
            AutoSize        =   1
            Object.Width           =   10451
            Text            =   "Status"
            TextSave        =   "Status"
            Key             =   ""
            Object.Tag             =   ""
         EndProperty
         BeginProperty Panel2 {0713E89F-850A-101B-AFC0-4210102A8DA7} 
            Style           =   6
            AutoSize        =   2
            TextSave        =   "15.12.2003"
            Key             =   ""
            Object.Tag             =   ""
         EndProperty
         BeginProperty Panel3 {0713E89F-850A-101B-AFC0-4210102A8DA7} 
            Style           =   5
            AutoSize        =   2
            TextSave        =   "14:09"
            Key             =   ""
            Object.Tag             =   ""
         EndProperty
      EndProperty
   End
   Begin VB.Label Label7 
      Caption         =   "fixed star"
      Height          =   252
      Left            =   3600
      TabIndex        =   20
      Top             =   720
      Width           =   732
   End
   Begin VB.Label Label6 
      Alignment       =   2  'Center
      Caption         =   ":"
      Height          =   252
      Left            =   2640
      TabIndex        =   16
      Top             =   960
      Width           =   252
   End
   Begin VB.Label Label5 
      Caption         =   "Min."
      Height          =   252
      Left            =   2880
      TabIndex        =   15
      Top             =   720
      Width           =   372
   End
   Begin VB.Label Label4 
      Caption         =   "Hour"
      Height          =   252
      Left            =   2280
      TabIndex        =   14
      Top             =   720
      Width           =   372
   End
   Begin VB.Label Label3 
      Caption         =   "Year"
      Height          =   252
      Left            =   1320
      TabIndex        =   6
      Top             =   720
      Width           =   372
   End
   Begin VB.Label Label2 
      Caption         =   "Month"
      DataField       =   "Month"
      Height          =   252
      Left            =   720
      TabIndex        =   5
      Top             =   720
      Width           =   492
   End
   Begin VB.Label Label1 
      Caption         =   "Day"
      Height          =   252
      Left            =   120
      TabIndex        =   4
      Top             =   720
      Width           =   372
   End
   Begin VB.Menu mnuFile 
      Caption         =   "&File"
      Begin VB.Menu mnuFileExit 
         Caption         =   "E&xit"
      End
   End
   Begin VB.Menu mnuHelp 
      Caption         =   "&Help"
      Begin VB.Menu mnuHelpAbout 
         Caption         =   "&About vbsweph..."
      End
   End
End
Attribute VB_Name = "frmMain"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
VERSION 5#
Object = "{6B7E6392-850A-101B-AFC0-4210102A8DA7}#1.3#0"; "Comctl32.ocx"
Begin VB.Form frmMain
   Caption = "vbsweph"
   ClientHeight = 8670
   ClientLeft = 165
   ClientTop = 735
   ClientWidth = 9120
   LinkTopic = "Form1"
   ScaleHeight = 8670
   ScaleWidth = 9120
   StartUpPosition = 3    'Windows Default
   Begin VB.CheckBox is_sidereal
      Caption = "sidereal"
      Height = 252
      Left = 2880
      TabIndex = 26
      Top = 480
      Width = 1092
   End
   Begin VB.CheckBox is_j2000
      Caption = "Equinox J2000"
      Height = 192
      Left = 4440
      TabIndex = 25
      Top = 480
      Width = 1332
   End
   Begin VB.CheckBox is_apparent
      Caption = "apparent pos."
      Height = 195
      Left = 2880
      TabIndex = 24
      Top = 240
      Value = 1              'Checked
      Width = 1335
   End
   Begin VB.TextBox geolat
      Height = 288
      Left = 6960
      TabIndex = 23
      Text = "47.00"
      Top = 480
      Width = 612
   End
   Begin VB.TextBox geolon
      Height = 288
      Left = 5880
      TabIndex = 22
      Text = "8.00"
      Top = 480
      Width = 732
   End
   Begin VB.CheckBox with_houses
      Caption = "with Placidus houses"
      Height = 192
      Left = 5880
      TabIndex = 21
      Top = 240
      Width = 1932
   End
   Begin VB.TextBox fstar
      Height = 324
      Left = 3600
      TabIndex = 19
      Top = 960
      Width = 1212
   End
   Begin VB.CheckBox add_hypo
      Caption = "with hypothetical bodies"
      Height = 255
      Left = 5880
      TabIndex = 18
      Top = 0
      Width = 2415
   End
   Begin VB.CheckBox bary_flag
      Caption = "barycentric"
      Height = 192
      Left = 4440
      TabIndex = 17
      Top = 240
      Width = 1092
   End
   Begin VB.TextBox minute
      Alignment = 2          'Center
      Height = 288
      Left = 2880
      MaxLength = 2
      TabIndex = 13
      Text = "00"
      Top = 960
      Width = 372
   End
   Begin VB.TextBox hour
      Alignment = 2          'Center
      Height = 288
      Left = 2280
      MaxLength = 2
      TabIndex = 12
      Text = "00"
      Top = 960
      Width = 372
   End
   Begin VB.PictureBox out
      AutoRedraw = -1         'True
      Height = 7455
      Left = 120
      ScaleHeight = 7395
      ScaleWidth = 8595
      TabIndex = 11
      Top = 1440
      Width = 8655
   End
   Begin VB.CheckBox hel_flag
      Caption = "heliocentric"
      Height = 192
      Left = 4440
      TabIndex = 10
      Top = 0
      Width = 1215
   End
   Begin VB.CheckBox et_flag
      Caption = "Ephemeris time"
      Height = 192
      Left = 2880
      TabIndex = 9
      Top = 0
      Value = 1              'Checked
      Width = 1572
   End
   Begin VB.PictureBox Picture1
      Height = 372
      Left = 120
      Picture         =   "frmMain_node.frx":0000
      ScaleHeight = 315
      ScaleWidth = 2355
      TabIndex = 8
      Top = 0
      Width = 2415
   End
   Begin VB.CommandButton Compute
      Caption = "Compute!"
      Height = 372
      Left = 5880
      TabIndex = 7
      Top = 960
      Width = 1092
   End
   Begin VB.TextBox Year
      Height = 288
      Left = 1320
      TabIndex = 3
      Text = "1997"
      Top = 960
      Width = 732
   End
   Begin VB.TextBox Month
      Height = 288
      Left = 720
      TabIndex = 2
      Text = "1"
      Top = 960
      Width = 492
   End
   Begin VB.TextBox Day
      Height = 288
      Left = 120
      MaxLength = 2
      TabIndex = 1
      Text = "1"
      Top = 960
      Width = 492
   End
   Begin ComctlLib.StatusBar sbStatusBar
      Align = 2              'Align Bottom
      Height = 270
      Left = 0
      TabIndex = 0
      Top = 8400
      Width = 9120
      _ExtentX        =   16087
      _ExtentY        =   476
      SimpleText = ""
      _Version        =   327682
      BeginProperty Panels {0713E89E-850A-101B-AFC0-4210102A8DA7}
         NumPanels = 3
         BeginProperty Panel1 {0713E89F-850A-101B-AFC0-4210102A8DA7}
            AutoSize = 1
            Object.Width = 10451
            Text = "Status"
            TextSave = "Status"
            Key = ""
            Object.Tag = ""
         EndProperty
         BeginProperty Panel2 {0713E89F-850A-101B-AFC0-4210102A8DA7}
            Style = 6
            AutoSize = 2
            TextSave = "15.12.2003"
            Key = ""
            Object.Tag = ""
         EndProperty
         BeginProperty Panel3 {0713E89F-850A-101B-AFC0-4210102A8DA7}
            Style = 5
            AutoSize = 2
            TextSave = "13:48"
            Key = ""
            Object.Tag = ""
         EndProperty
      EndProperty
   End
   Begin VB.Label Label7
      Caption = "fixed star"
      Height = 252
      Left = 3600
      TabIndex = 20
      Top = 720
      Width = 732
   End
   Begin VB.Label Label6
      Alignment = 2          'Center
      Caption = ":"
      Height = 252
      Left = 2640
      TabIndex = 16
      Top = 960
      Width = 252
   End
   Begin VB.Label Label5
      Caption = "Min."
      Height = 252
      Left = 2880
      TabIndex = 15
      Top = 720
      Width = 372
   End
   Begin VB.Label Label4
      Caption = "Hour"
      Height = 252
      Left = 2280
      TabIndex = 14
      Top = 720
      Width = 372
   End
   Begin VB.Label Label3
      Caption = "Year"
      Height = 252
      Left = 1320
      TabIndex = 6
      Top = 720
      Width = 372
   End
   Begin VB.Label Label2
      Caption = "Month"
      DataField = "Month"
      Height = 252
      Left = 720
      TabIndex = 5
      Top = 720
      Width = 492
   End
   Begin VB.Label Label1
      Caption = "Day"
      Height = 252
      Left = 120
      TabIndex = 4
      Top = 720
      Width = 372
   End
   Begin VB.Menu mnuFile
      Caption = "&File"
      Begin VB.Menu mnuFileExit
         Caption = "E&xit"
      End
   End
   Begin VB.Menu mnuHelp
      Caption = "&Help"
      Begin VB.Menu mnuHelpAbout
         Caption = "&About vbsweph..."
      End
   End
End
Attribute VB_Name = "frmMain"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
' Swiss Ephemeris Release 1.60  9-jan-2000
'
' Declarations for Visual Basic 5.0
' The DLL file must exist in the same directory as the VB executable, or in a system
' directory where it can be found at runtime
'
Private Type orient
    i As Long
    s As String * 16
    End Type
'
' Declarations for Visual Basic 5.0
' The DLL file must exist in the same directory as the VB executable, or in a system
' directory where it can be found at runtime
'

Private Declare Function swe_azalt Lib "swedll32.dll" _
        Alias "_swe_azalt@40" ( _
          ByVal tjd_ut As Double, _
          ByVal calc_flag As Long, _
          ByRef geopos As Double, _
          ByVal atpress As Double, _
          ByVal attemp As Double, _
          ByRef xin As Double, _
          ByRef xaz As Double _
        ) As Long  'geopos must be the first of three array elements
                   'xin must be the first of two array elements
                   'xaz must be the first of three array elements

Private Declare Function swe_azalt_rev Lib "swedll32.dll" _
        Alias "_swe_azalt_rev@24" ( _
          ByVal tjd_ut As Double, _
          ByVal calc_flag As Long, _
          ByRef geopos As Double, _
          ByRef xin As Double, _
          ByRef xout As Double _
        ) As Long  'geopos must be the first of three array elements
                   'xin must be the first of two array elements
                   'xout must be the first of three array elements

Private Declare Function swe_calc Lib "swedll32.dll" _
        Alias "_swe_calc@24" ( _
          ByVal tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long   ' x must be first of six array elements
                    ' serr must be able to hold 256 bytes

Private Declare Function swe_calc_d Lib "swedll32.dll" _
        Alias "_swe_calc_d@20" ( _
          ByRef tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes

Private Declare Function swe_calc_ut Lib "swedll32.dll" _
        Alias "_swe_calc_ut@24" ( _
          ByVal tjd_ut As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long   ' x must be first of six array elements
                    ' serr must be able to hold 256 bytes

Private Declare Function swe_calc_ut_d Lib "swedll32.dll" _
        Alias "_swe_calc_ut_d@20" ( _
          ByRef tjd_ut As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes

Private Declare Function swe_close Lib "swedll32.dll" _
        Alias "_swe_close@0" ( _
        ) As Long

Private Declare Function swe_close_d Lib "swedll32.dll" _
        Alias "_swe_close_d@4" ( _
          ByVal ivoid As Long _
        ) As Long       ' argument ivoid is ignored

Private Declare Sub swe_cotrans Lib "swedll32.dll" _
        Alias "_swe_cotrans@16" ( _
          ByRef xpo As Double, _
          ByRef xpn As Double, _
          ByVal eps As Double _
        )

Private Declare Function swe_cotrans_d Lib "swedll32.dll" _
        Alias "_swe_cotrans_d@12" ( _
          ByRef xpo As Double, _
          ByRef xpn As Double, _
          ByRef eps As Double _
        ) As Long

Private Declare Sub swe_cotrans_sp Lib "swedll32.dll" _
        Alias "_swe_cotrans_sp@16" ( _
          ByRef xpo As Double, _
          ByRef xpn As Double, _
          ByVal eps As Double _
        )

Private Declare Function swe_cotrans_sp_d Lib "swedll32.dll" _
        Alias "_swe_cotrans_sp_d@12" ( _
          ByRef xpo As Double, _
          ByRef xpn As Double, _
          ByRef eps As Double _
        ) As Long

Private Declare Sub swe_cs2degstr Lib "swedll32.dll" _
        Alias "_swe_cs2degstr@8" ( _
          ByVal t As Long, _
          ByVal s As String _
        )

Private Declare Function swe_cs2degstr_d Lib "swedll32.dll" _
        Alias "_swe_cs2degstr_d@8" ( _
          ByVal t As Long, _
          ByVal s As String _
        ) As Long

Private Declare Sub swe_cs2lonlatstr Lib "swedll32.dll" _
        Alias "_swe_cs2lonlatstr@16" ( _
          ByVal t As Long, _
          ByVal pchar As Byte, _
          ByVal mchar As Byte, _
          ByVal s As String _
        )

Private Declare Function swe_cs2lonlatstr_d Lib "swedll32.dll" _
        Alias "_swe_cs2lonlatstr_d@16" ( _
          ByVal t As Long, _
          ByRef pchar As Byte, _
          ByRef mchar As Byte, _
          ByVal s As String _
        ) As Long

Private Declare Sub swe_cs2timestr Lib "swedll32.dll" _
        Alias "_swe_cs2timestr@16" ( _
          ByVal t As Long, _
          ByVal sep As Long, _
          ByVal supzero As Long, _
          ByVal s As String _
        )

Private Declare Function swe_cs2timestr_d Lib "swedll32.dll" _
        Alias "_swe_cs2timestr_d@16" ( _
          ByVal t As Long, _
          ByVal sep As Long, _
          ByVal supzero As Long, _
          ByVal s As String _
        ) As Long

Private Declare Function swe_csnorm Lib "swedll32.dll" _
        Alias "_swe_csnorm@4" ( _
          ByVal p As Long _
        ) As Long

Private Declare Function swe_csnorm_d Lib "swedll32.dll" _
        Alias "_swe_csnorm_d@4" ( _
          ByVal p As Long _
        ) As Long

Private Declare Function swe_csroundsec Lib "swedll32.dll" _
        Alias "_swe_csroundsec@4" ( _
          ByVal p As Long _
        ) As Long

Private Declare Function swe_csroundsec_d Lib "swedll32.dll" _
        Alias "_swe_csroundsec_d@4" ( _
          ByVal p As Long _
        ) As Long

Private Declare Function swe_d2l Lib "swedll32.dll" _
        Alias "_swe_d2l@8" ( _
        ) As Long

Private Declare Function swe_d2l_d Lib "swedll32.dll" _
        Alias "_swe_d2l_d@4" ( _
        ) As Long

Private Declare Function swe_date_conversion Lib "swedll32.dll" _
        Alias "_swe_date_conversion@28" ( _
          ByVal Year As Long, _
          ByVal Month As Long, _
          ByVal Day As Long, _
          ByVal utime As Double, _
          ByVal cal As Byte, _
          ByRef tjd As Double _
        ) As Long

Private Declare Function swe_date_conversion_d Lib "swedll32.dll" _
        Alias "_swe_date_conversion_d@24" ( _
          ByVal Year As Long, _
          ByVal Month As Long, _
          ByVal Day As Long, _
          ByRef utime As Double, _
          ByRef cal As Byte, _
          ByRef tjd As Double _
        ) As Long

Private Declare Function swe_day_of_week Lib "swedll32.dll" _
        Alias "_swe_day_of_week@8" ( _
          ByVal jd As Double _
        ) As Long

Private Declare Function swe_day_of_week_d Lib "swedll32.dll" _
        Alias "_swe_day_of_week_d@4" ( _
          ByRef jd As Double _
        ) As Long

Private Declare Function swe_degnorm Lib "swedll32.dll" _
        Alias "_swe_degnorm@8" ( _
          ByVal jd As Double _
        ) As Double

Private Declare Function swe_degnorm_d Lib "swedll32.dll" _
        Alias "_swe_degnorm_d@4" ( _
          ByRef jd As Double _
        ) As Long

Private Declare Function swe_deltat Lib "swedll32.dll" _
        Alias "_swe_deltat@8" ( _
          ByVal jd As Double _
        ) As Double

Private Declare Function swe_deltat_d Lib "swedll32.dll" _
        Alias "_swe_deltat_d@8" ( _
          ByRef jd As Double, _
          ByRef deltat As Double _
        ) As Long

Private Declare Function swe_difcs2n Lib "swedll32.dll" _
        Alias "_swe_difcs2n@8" ( _
          ByVal p1 As Long, _
          ByVal p2 As Long _
        ) As Long

Private Declare Function swe_difcs2n_d Lib "swedll32.dll" _
        Alias "_swe_difcs2n_d@8" ( _
          ByVal p1 As Long, _
          ByVal p2 As Long _
        ) As Long

Private Declare Function swe_difcsn Lib "swedll32.dll" _
        Alias "_swe_difcsn@8" ( _
          ByVal p1 As Long, _
          ByVal p2 As Long _
        ) As Long

Private Declare Function swe_difcsn_d Lib "swedll32.dll" _
        Alias "_swe_difcsn_d@8" ( _
          ByVal p1 As Long, _
          ByVal p2 As Long _
        ) As Long

Private Declare Function swe_difdeg2n Lib "swedll32.dll" _
        Alias "_swe_difdeg2n@16" ( _
          ByVal p1 As Double, _
          ByVal p2 As Double _
        ) As Double

Private Declare Function swe_difdeg2n_d Lib "swedll32.dll" _
        Alias "_swe_difdeg2n_d@12" ( _
          ByRef p1 As Double, _
          ByRef p2 As Double, _
          ByRef diff As Double _
        ) As Long

Private Declare Function swe_difdegn Lib "swedll32.dll" _
        Alias "_swe_difdegn@16" ( _
          ByVal p1 As Double, _
          ByVal p2 As Double _
        ) As Long

Private Declare Function swe_difdegn_d Lib "swedll32.dll" _
        Alias "_swe_difdegn_d@12" ( _
          ByRef p1 As Double, _
          ByRef p2 As Double, _
          ByRef diff As Double _
        ) As Long

Private Declare Function swe_fixstar Lib "swedll32.dll" _
        Alias "_swe_fixstar@24" ( _
          ByVal star As String, _
          ByVal tjd As Double, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes
                        ' star must be able to hold 40 bytes

Private Declare Function swe_fixstar_d Lib "swedll32.dll" _
        Alias "_swe_fixstar_d@20" ( _
          ByVal star As String, _
          ByRef tjd As Double, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes
                        ' star must be able to hold 40 bytes

Private Declare Function swe_fixstar_ut Lib "swedll32.dll" _
        Alias "_swe_fixstar_ut@24" ( _
          ByVal star As String, _
          ByVal tjd_ut As Double, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes
                        ' star must be able to hold 40 bytes

Private Declare Function swe_fixstar_ut_d Lib "swedll32.dll" _
        Alias "_swe_fixstar_ut_d@20" ( _
          ByVal star As String, _
          ByRef tjd_ut As Double, _
          ByVal iflag As Long, _
          ByRef x As Double, _
          ByVal serr As String _
        ) As Long       ' x must be first of six array elements
                        ' serr must be able to hold 256 bytes
                        ' star must be able to hold 40 bytes

Private Declare Function swe_get_ayanamsa Lib "swedll32.dll" _
        Alias "_swe_get_ayanamsa@8" ( _
          ByVal tjd_et As Double _
        ) As Double

Private Declare Function swe_get_ayanamsa_d Lib "swedll32.dll" _
        Alias "_swe_get_ayanamsa_d@8" ( _
          ByRef tjd_et As Double, _
          ByRef ayan As Double _
        ) As Long

Private Declare Function swe_get_ayanamsa_ut Lib "swedll32.dll" _
        Alias "_swe_get_ayanamsa_ut@8" ( _
          ByVal tjd_ut As Double _
        ) As Double

Private Declare Function swe_get_ayanamsa_ut_d Lib "swedll32.dll" _
        Alias "_swe_get_ayanamsa_ut_d@8" ( _
          ByRef tjd_ut As Double, _
          ByRef ayan As Double _
        ) As Long

Private Declare Sub swe_get_planet_name Lib "swedll32.dll" _
        Alias "_swe_get_planet_name@8" ( _
          ByVal ipl As Long, _
          ByVal pname As String _
        )

Private Declare Function swe_get_planet_name_d Lib "swedll32.dll" _
        Alias "_swe_get_planet_name_d@8" ( _
          ByVal ipl As Long, _
          ByVal pname As String _
        ) As Long

Private Declare Function swe_get_tid_acc Lib "swedll32.dll" _
        Alias "_swe_get_tid_acc@0" ( _
        ) As Double

Private Declare Function swe_get_tid_acc_d Lib "swedll32.dll" _
        Alias "_swe_get_tid_acc_d@4" ( _
          ByRef x As Double _
        ) As Long

Private Declare Function swe_houses Lib "swedll32.dll" _
        Alias "_swe_houses@36" ( _
          ByVal tjd_ut As Double, _
          ByVal geolat As Double, _
          ByVal geolon As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_houses_d Lib "swedll32.dll" _
        Alias "_swe_houses_d@24" ( _
          ByRef tjd_ut As Double, _
          ByRef geolat As Double, _
          ByRef geolon As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_houses_ex Lib "swedll32.dll" _
        Alias "_swe_houses_ex@40" ( _
          ByVal tjd_ut As Double, _
          ByVal iflag As Long, _
          ByVal geolat As Double, _
          ByVal geolon As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_houses_ex_d Lib "swedll32.dll" _
        Alias "_swe_houses_ex_d@28" ( _
          ByRef tjd_ut As Double, _
          ByVal iflag As Long, _
          ByRef geolat As Double, _
          ByRef geolon As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_houses_armc Lib "swedll32.dll" _
        Alias "_swe_houses_armc@36" ( _
          ByVal armc As Double, _
          ByVal geolat As Double, _
          ByVal eps As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_houses_armc_d Lib "swedll32.dll" _
        Alias "_swe_houses_armc_d@24" ( _
          ByRef armc As Double, _
          ByRef geolat As Double, _
          ByRef eps As Double, _
          ByVal ihsy As Long, _
          ByRef hcusps As Double, _
          ByRef ascmc As Double _
        ) As Long       ' hcusps must be first of 13 array elements
                        ' ascmc must be first of 10 array elements

Private Declare Function swe_house_pos Lib "swedll32.dll" _
        Alias "_swe_house_pos@36" ( _
          ByVal armc As Double, _
          ByVal geolat As Double, _
          ByVal eps As Double, _
          ByVal ihsy As Long, _
          ByRef xpin As Double, _
          ByVal serr As String _
        ) As Double
                        ' xpin must be first of 2 array elements

Private Declare Function swe_house_pos_d Lib "swedll32.dll" _
        Alias "_swe_house_pos_d@28" ( _
          ByRef armc As Double, _
          ByRef geolat As Double, _
          ByRef eps As Double, _
          ByVal ihsy As Long, _
          ByRef xpin As Double, _
          ByRef hpos As Double, _
          ByVal serr As String _
        ) As Long
                        ' xpin must be first of 2 array elements

Private Declare Function swe_julday Lib "swedll32.dll" _
        Alias "_swe_julday@24" ( _
          ByVal Year As Long, _
          ByVal Month As Long, _
          ByVal Day As Long, _
          ByVal hour As Double, _
          ByVal gregflg As Long _
        ) As Double

Private Declare Function swe_julday_d Lib "swedll32.dll" _
        Alias "_swe_julday_d@24" ( _
          ByVal Year As Long, _
          ByVal Month As Long, _
          ByVal Day As Long, _
          ByRef hour As Double, _
          ByVal gregflg As Long, _
          ByRef tjd As Double _
        ) As Long

Private Declare Function swe_lun_eclipse_how Lib "swedll32.dll" _
        Alias "_swe_lun_eclipse_how@24" ( _
          ByVal tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_lun_eclipse_how_d Lib "swedll32.dll" _
        Alias "_swe_lun_eclipse_how_d@20" ( _
          ByRef tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_lun_eclipse_when Lib "swedll32.dll" _
        Alias "_swe_lun_eclipse_when@28" ( _
          ByVal tjd_start As Double, _
          ByVal ifl As Long, _
          ByVal ifltype As Long, _
          ByRef tret As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_lun_eclipse_when_d Lib "swedll32.dll" _
        Alias "_swe_lun_eclipse_when_d@24" ( _
          ByRef tjd_start As Double, _
          ByVal ifl As Long, _
          ByVal ifltype As Long, _
          ByRef tret As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_nod_aps Lib "swedll32.dll" _
        Alias "_swe_nod_aps@40" ( _
          ByVal tjd_et As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByVal method As Long, _
          ByRef xnasc As Double, _
          ByRef xndsc As Double, _
          ByRef xperi As Double, _
          ByRef xaphe As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_nod_aps_ut Lib "swedll32.dll" _
        Alias "_swe_nod_aps_ut@40" ( _
          ByVal tjd_ut As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByVal method As Long, _
          ByRef xnasc As Double, _
          ByRef xndsc As Double, _
          ByRef xperi As Double, _
          ByRef xaphe As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_pheno Lib "swedll32.dll" _
        Alias "_swe_pheno@24" ( _
          ByVal tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_pheno_ut Lib "swedll32.dll" _
        Alias "_swe_pheno_ut@24" ( _
          ByVal tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_pheno_d Lib "swedll32.dll" _
        Alias "_swe_pheno_d@20" ( _
          ByRef tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_pheno_ut_d Lib "swedll32.dll" _
        Alias "_swe_pheno_ut_d@20" ( _
          ByRef tjd As Double, _
          ByVal ipl As Long, _
          ByVal iflag As Long, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_refrac Lib "swedll32.dll" _
        Alias "_swe_refrac@28" ( _
          ByVal inalt As Double, _
          ByVal atpress As Double, _
          ByVal attemp As Double, _
          ByVal calc_flag As Long _
        ) As Double

Private Declare Sub swe_revjul Lib "swedll32.dll" _
        Alias "_swe_revjul@28" ( _
          ByVal tjd As Double, _
          ByVal gregflg As Long, _
          ByRef Year As Long, _
          ByRef Month As Long, _
          ByRef Day As Long, _
          ByRef hour As Double _
        )

Private Declare Function swe_revjul_d Lib "swedll32.dll" _
        Alias "_swe_revjul_d@24" ( _
          ByRef tjd As Double, _
          ByVal gregflg As Long, _
          ByRef Year As Long, _
          ByRef Month As Long, _
          ByRef Day As Long, _
          ByRef hour As Double _
        ) As Long

Private Declare Function swe_rise_trans Lib "swedll32.dll" _
        Alias "_swe_rise_trans@52" ( _
          ByVal tjd_ut As Double, _
          ByVal ipl As Long, _
          ByVal starname As String, _
          ByVal epheflag As Long, _
          ByVal rsmi As Long, _
          ByRef geopos As Double, _
          ByVal atpress As Double, _
          ByVal attemp As Double, _
          ByRef tret As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Sub swe_set_ephe_path Lib "swedll32.dll" _
        Alias "_swe_set_ephe_path@4" ( _
          ByVal path As String _
        )

Private Declare Function swe_set_ephe_path_d Lib "swedll32.dll" _
        Alias "_swe_set_ephe_path_d@4" ( _
          ByVal path As String _
        ) As Long

Private Declare Sub swe_set_jpl_file Lib "swedll32.dll" _
        Alias "_swe_set_jpl_file@4" ( _
          ByVal file As String _
        )

Private Declare Function swe_set_jpl_file_d Lib "swedll32.dll" _
        Alias "_swe_set_jpl_file_d@4" ( _
          ByVal file As String _
        ) As Long

Private Declare Function swe_set_sid_mode Lib "swedll32.dll" _
        Alias "_swe_set_sid_mode@20" ( _
          ByVal sid_mode As Long, _
          ByVal t0 As Double, _
          ByVal ayan_t0 As Double _
        ) As Long

Private Declare Function swe_set_sid_mode_d Lib "swedll32.dll" _
        Alias "_swe_sid_mode_d@12" ( _
          ByVal sid_mode As Long, _
          ByRef t0 As Double, _
          ByRef ayan_t0 As Double _
        ) As Long

Private Declare Sub swe_set_topo Lib "swedll32.dll" _
        Alias "_swe_set_topo@24" ( _
          ByVal geolon As Double, _
          ByVal geolat As Double, _
          ByVal altitude As Double _
        )

Private Declare Function swe_set_topo_d Lib "swedll32.dll" _
        Alias "_swe_set_topo_d@12" ( _
          ByRef geolon As Double, _
          ByRef geolat As Double, _
          ByRef altitude As Double _
        ) As Long

Private Declare Sub swe_set_tid_acc Lib "swedll32.dll" _
        Alias "_swe_set_tid_acc@8" ( _
          ByVal x As Double _
        )

Private Declare Function swe_set_tid_acc_d Lib "swedll32.dll" _
        Alias "_swe_set_tid_acc_d@4" ( _
          ByRef x As Double _
        ) As Long

Private Declare Function swe_sidtime0 Lib "swedll32.dll" _
        Alias "_swe_sidtime0@24" ( _
          ByVal tjd_ut As Double, _
          ByVal ecl As Double, _
          ByVal nut As Double _
        ) As Double

Private Declare Function swe_sidtime0_d Lib "swedll32.dll" _
        Alias "_swe_sidtime0_d@16" ( _
          ByRef tjd_ut As Double, _
          ByRef ecl As Double, _
          ByRef nut As Double, _
          ByRef sidt As Double _
        ) As Long

Private Declare Function swe_sidtime Lib "swedll32.dll" _
        Alias "_swe_sidtime@8" ( _
          ByVal tjd_ut As Double _
        ) As Double

Private Declare Function swe_sidtime_d Lib "swedll32.dll" _
        Alias "_swe_sidtime_d@8" ( _
          ByRef tjd_ut As Double, _
          ByRef sidt As Double _
        ) As Long

Private Declare Function swe_sol_eclipse_how Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_how@24" ( _
          ByVal tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_how_d Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_how_d@20" ( _
          ByRef tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_when_glob Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_when_glob@28" ( _
          ByVal tjd_start As Double, _
          ByVal ifl As Long, _
          ByVal ifltype As Long, _
          ByRef tret As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_when_glob_d Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_when_glob_d@24" ( _
          ByRef tjd_start As Double, _
          ByVal ifl As Long, _
          ByVal ifltype As Long, _
          ByRef tret As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_when_loc Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_when_loc@32" ( _
          ByVal tjd_start As Double, _
          ByVal ifl As Long, _
          ByRef tret As Double, _
          ByRef attr As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_when_loc_d Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_when_loc_d@28" ( _
          ByRef tjd_start As Double, _
          ByVal ifl As Long, _
          ByRef tret As Double, _
          ByRef attr As Double, _
          ByVal backward As Long, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_where Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_where@24" ( _
          ByVal tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_sol_eclipse_where_d Lib "swedll32.dll" _
        Alias "_swe_sol_eclipse_where_d@20" ( _
          ByRef tjd_ut As Double, _
          ByVal ifl As Long, _
          ByRef geopos As Double, _
          ByRef attr As Double, _
          ByVal serr As String _
        ) As Long

Private Declare Function swe_time_equ Lib "swedll32.dll" _
        Alias "_swe_time_equ@16" ( _
          ByVal tjd_ut As Double, _
          ByRef e As Double, _
          ByVal serr As String _
        ) As Long
 
' values for gregflag in swe_julday() and swe_revjul()
 Const SE_JUL_CAL As Integer = 0
 Const SE_GREG_CAL As Integer = 1

' planet and body numbers (parameter ipl) for swe_calc()
 Const SE_SUN As Integer = 0
 Const SE_MOON As Integer = 1
 Const SE_MERCURY As Integer = 2
 Const SE_VENUS As Integer = 3
 Const SE_MARS As Integer = 4
 Const SE_JUPITER As Integer = 5
 Const SE_SATURN As Integer = 6
 Const SE_URANUS As Integer = 7
 Const SE_NEPTUNE As Integer = 8
 Const SE_PLUTO   As Integer = 9
 Const SE_MEAN_NODE As Integer = 10
 Const SE_TRUE_NODE As Integer = 11
 Const SE_MEAN_APOG As Integer = 12
 Const SE_OSCU_APOG As Integer = 13
 Const SE_EARTH     As Integer = 14
 Const SE_CHIRON    As Integer = 15
 Const SE_PHOLUS    As Integer = 16
 Const SE_CERES     As Integer = 17
 Const SE_PALLAS    As Integer = 18
 Const SE_JUNO      As Integer = 19
 Const SE_VESTA     As Integer = 20
  
 Const SE_NPLANETS  As Integer = 21
 Const SE_AST_OFFSET  As Integer = 10000

' Hamburger or Uranian ficticious "planets"
 Const SE_FICT_OFFSET As Integer = 40
 Const SE_FICT_MAX  As Integer = 999 'maximum number for ficticious planets
                                     'if taken from file seorbel.txt
 Const SE_NFICT_ELEM  As Integer = 15 'number of built-in ficticious planets
 Const SE_CUPIDO As Integer = 40
 Const SE_HADES As Integer = 41
 Const SE_ZEUS As Integer = 42
 Const SE_KRONOS As Integer = 43
 Const SE_APOLLON As Integer = 44
 Const SE_ADMETOS As Integer = 45
 Const SE_VULKANUS As Integer = 46
 Const SE_POSEIDON As Integer = 47
' other ficticious bodies
 Const SE_ISIS As Integer = 48
 Const SE_NIBIRU As Integer = 49
 Const SE_HARRINGTON As Integer = 50
 Const SE_NEPTUNE_LEVERRIER As Integer = 51
 Const SE_NEPTUNE_ADAMS As Integer = 52
 Const SE_PLUTO_LOWELL As Integer = 53
 Const SE_PLUTO_PICKERING As Integer = 54

' points returned by swe_houses() and swe_houses_armc()
' in array ascmc(0...10)
 Const SE_ASC       As Integer = 0
 Const SE_MC        As Integer = 1
 Const SE_ARMC      As Integer = 2
 Const SE_VERTEX    As Integer = 3
 Const SE_EQUASC    As Integer = 4  ' "equatorial ascendant"
 Const SE_NASCMC    As Integer = 5  ' number of such points
 
' iflag values for swe_calc()/swe_calc_ut() and swe_fixstar()/swe_fixstar_ut()
Const SEFLG_JPLEPH As Long = 1
Const SEFLG_SWIEPH As Long = 2
Const SEFLG_MOSEPH As Long = 4
Const SEFLG_SPEED As Long = 256
Const SEFLG_HELCTR As Long = 8
Const SEFLG_TRUEPOS As Long = 16
Const SEFLG_J2000 As Long = 32
Const SEFLG_NONUT As Long = 64
Const SEFLG_NOGDEFL As Long = 512
Const SEFLG_NOABERR As Long = 1024
Const SEFLG_EQUATORIAL As Long = 2048
Const SEFLG_XYZ As Long = 4096
Const SEFLG_RADIANS As Long = 8192
Const SEFLG_BARYCTR As Long = 16384
Const SEFLG_TOPOCTR As Long = 32768
Const SEFLG_SIDEREAL As Long = 65536

'eclipse codes
Const SE_ECL_CENTRAL As Long = 1
Const SE_ECL_NONCENTRAL As Long = 2
Const SE_ECL_TOTAL As Long = 4
Const SE_ECL_ANNULAR As Long = 8
Const SE_ECL_PARTIAL As Long = 16
Const SE_ECL_ANNULAR_TOTAL As Long = 32
Const SE_ECL_PENUMBRAL As Long = 64
Const SE_ECL_VISIBLE As Long = 128
Const SE_ECL_MAX_VISIBLE As Long = 256
Const SE_ECL_1ST_VISIBLE As Long = 512
Const SE_ECL_2ND_VISIBLE As Long = 1024
Const SE_ECL_3RD_VISIBLE As Long = 2048
Const SE_ECL_4TH_VISIBLE As Long = 4096

'sidereal modes, for swe_set_sid_mode()
Const SE_SIDM_FAGAN_BRADLEY    As Long = 0
Const SE_SIDM_LAHIRI           As Long = 1
Const SE_SIDM_DELUCE           As Long = 2
Const SE_SIDM_RAMAN            As Long = 3
Const SE_SIDM_USHASHASHI       As Long = 4
Const SE_SIDM_KRISHNAMURTI     As Long = 5
Const SE_SIDM_DJWHAL_KHUL      As Long = 6
Const SE_SIDM_YUKTESHWAR       As Long = 7
Const SE_SIDM_JN_BHASIN        As Long = 8
Const SE_SIDM_BABYL_KUGLER1    As Long = 9
Const SE_SIDM_BABYL_KUGLER2   As Long = 10
Const SE_SIDM_BABYL_KUGLER3   As Long = 11
Const SE_SIDM_BABYL_HUBER     As Long = 12
Const SE_SIDM_BABYL_ETPSC     As Long = 13
Const SE_SIDM_ALDEBARAN_15TAU As Long = 14
Const SE_SIDM_HIPPARCHOS      As Long = 15
Const SE_SIDM_SASSANIAN       As Long = 16
Const SE_SIDM_GALCENT_0SAG    As Long = 17
Const SE_SIDM_J2000           As Long = 18
Const SE_SIDM_J1900           As Long = 19
Const SE_SIDM_B1950           As Long = 20
Const SE_SIDM_USER            As Long = 255

Const SE_NSIDM_PREDEF         As Long = 21

Const SE_SIDBITS              As Long = 256
'for projection onto ecliptic of t0
Const SE_SIDBIT_ECL_T0        As Long = 256
'for projection onto solar system plane
Const SE_SIDBIT_SSY_PLANE     As Long = 512

' modes for planetary nodes/apsides, swe_nod_aps(), swe_nod_aps_ut()
Const SE_NODBIT_MEAN        As Long = 1
Const SE_NODBIT_OSCU        As Long = 2
Const SE_NODBIT_OSCU_BAR    As Long = 3
Const SE_NODBIT_FOPOINT     As Long = 256

' indices for swe_rise_trans()
Const SE_CALC_RISE      As Long = 1
Const SE_CALC_SET       As Long = 2
Const SE_CALC_MTRANSIT      As Long = 4
Const SE_CALC_ITRANSIT      As Long = 8
Const SE_BIT_DISC_CENTER        As Long = 256 '/* to be added to SE_CALC_RISE/SET */
                    '/* if rise or set of disc center is */
                    '/* requried */
Const SE_BIT_NO_REFRACTION      As Long = 512 '/* to be added to SE_CALC_RISE/SET, */
                    '/* if refraction is not to be considered */

' bits for data conversion with swe_azalt() and swe_azalt_rev()
Const SE_ECL2HOR        As Long = 0
Const SE_EQU2HOR        As Long = 1
Const SE_HOR2ECL        As Long = 0
Const SE_HOR2EQU        As Long = 1

' for swe_refrac()
Const SE_TRUE_TO_APP        As Long = 0
Const SE_APP_TO_TRUE        As Long = 1



Dim iday%
Dim imonth%
Dim iyear%
Dim ihour%
Dim imin
Dim starname As String
Dim lon
Dim lat
Dim tjd_ut
Dim tdj_et



Private Function outdeg(x As Double) As String
  fract = Abs(x) - Int(Abs(x))
  Min = Int(fract * 60)
  sec = fract * 3600 - Min * 60
  outdeg = Format(Sgn(x) * Int(Abs(x)), "###0") + "°" + Format(Min, "00") + "'" + Format(sec, "00.0000")
End Function
Private Function outdeg3(x As Double) As String
  fract = Abs(x) - Int(Abs(x))
  Min = Int(fract * 60)
  sec = fract * 3600 - Min * 60
  outdeg3 = Format(Sgn(x) * Int(Abs(x)), "000") + "°" + Format(Min, "00") + "'" + Format(sec, "00.0000")
End Function


' the DLL returns Null-terminated C strings; for VB the terminating NULL
' character must befound and the string length must be set accordingly
Private Function set_strlen(c$) As String
i = InStr(c$, Chr$(0))
If (i > 0) Then c$ = Left(c$, i - 1)
set_strlen = c$
End Function



Private Sub bary_flag_Click()
 If bary_flag Then hel_flag = 0
End Sub

Private Sub Compute_Click()
    Dim x(6) As Double
    Dim x2(6) As Double
    Dim cusp(13) As Double
    Dim ascmc(10) As Double
    Dim attr(20) As Double
    Dim tret(20) As Double
    Dim geopos(10) As Double
    Dim geoposx(10) As Double
    Dim xnasc(6) As Double
    Dim xndsc(6) As Double
    Dim xperi(6) As Double
    Dim xaphe(6) As Double
    Dim cal As Byte
    Dim o As orient
    Dim ss As String * 16
    cal = 103  ' g for gregorian calendar
    fMainForm.out.Cls
    h = ihour% + imin / 60#
    olen = LenB(ss)
    geopos(0) = lon
    geopos(1) = lat
    geopos(2) = 0
 ' the next two functions do the same job, converting a calendar date
 ' into a Julian day number
 ' swe_date_conversion() checks for legal dates while swe_julday() handles
 ' even illegal things like 45 Januar etc.
    tjd_ut = swe_julday(iyear%, imonth%, iday%, h, 1)
    retval = swe_date_conversion(iyear, imonth, iday, h, cal, tjd_ut)
    If retval <> 0 Then
        Call MsgBox("Illegal Date", vbOKOnly)
        Exit Sub
    End If
    If et_flag Then
     tjd_et = tjd_ut
     tjd_ut = tjd_et - swe_deltat(tjd_et)
    Else
     tjd_et = tjd_ut + swe_deltat(tjd_ut)
    End If
    t2 = tjd_ut - 2415018.5
    If t2 < 0 Then
      t2 = t2
    End If
    If tjd_ut = tjd_et Then
      ut$ = ""
    Else
      ut$ = "  UT=" + Format(tjd_ut)
    End If
    fMainForm.out.Print "ET="; tjd_et, ut$
     For planet = SE_SUN To SE_PLUTO_PICKERING
        iflag = SEFLG_SPEED + SEFLG_SWIEPH
        If bary_flag = 1 Then
            iflag = iflag + SEFLG_BARYCTR
        End If
        If hel_flag = 1 Then
            iflag = iflag + SEFLG_HELCTR
        End If
        If is_j2000 = 1 Then
            iflag = iflag + SEFLG_J2000
        End If
        If Not is_apparent = 1 Then
           iflag = iflag + SEFLG_TRUEPOS
        End If
        If is_sidereal = 1 Then
           iflag = iflag + SEFLG_SIDEREAL
           'a = swe_set_sid_mode(SEFLG_SIDM_FAGAN_BRADLEY, 0, 0)
        End If
        serr$ = String(255, 0)
        plnam$ = String(20, 0)
        ret_flag = swe_calc(tjd_et, planet, iflag, x(0), serr$)
        serr$ = set_strlen(serr$)
        If ret_flag <> iflag And Len(serr$) > 0 Then
            fMainForm.out.Print "swe_calc reports: "; serr$
        End If
        Call swe_get_planet_name(planet, plnam$)
        plnam$ = set_strlen(plnam$)
        plnam$ = Left(plnam$, 10)
        fMainForm.out.Print plnam$, outdeg3(x(0)), outdeg(x(1)); "  ", x(2)
        If planet = SE_VESTA Then
          If add_hypo = 0 Then Exit For
          planet = SE_CUPIDO    ' skip undefined planet numbers
          fMainForm.out.Print ""
        End If
        serr$ = String(255, 0)
        retflag = swe_nod_aps_ut(tjd_ut, planet, iflag, 0, xnasc(0), xndsc(0), xperi(0), xaphe(0), serr$)
        fMainForm.out.Print "  node", outdeg3(xnasc(0)), outdeg(xnasc(1)); "  ", xnasc(2)
        serr$ = String(255, 0)
        retflag = swe_rise_trans(tjd_ut, planet, "", 0, SE_CALC_RISE, geopos(0), 0, 0, tret(0), serr$)
        fMainForm.out.Print "  rise", tret(0)
     Next planet
     ' if something was entered in the fixed star field, it is computed
     If Len(starname) > 0 Then
       serr$ = String(255, 0)
       starname = starname + String(40, 0)  ' make it at least 40 bytes
       ret_flag = swe_fixstar(starname, tjd_et, iflag, x(0), serr$)
       serr$ = set_strlen(serr$)
       starname = set_strlen(starname)
       If ret_flag < 0 Then
         fMainForm.out.Print "swe_fixstar() reports: "; serr$
       Else
       fMainForm.out.Print starname, outdeg3(x(0)), outdeg(x(1)); "  ", x(2)
       End If
     End If
     ' now come the houses
     If with_houses Then
       fMainForm.out.Print ""
       ret_flag = swe_houses_ex(tjd_ut, iflag, lat, lon, Asc("P"), cusp(0), ascmc(0))
     For i = 1 To 12
        ' x(0) = cusp(i)
        ' x(1) = 0
        fMainForm.out.Print "House "; i, outdeg3(cusp(i)), ' outdeg3(x(1)),
        If i Mod 3 = 0 Then fMainForm.out.Print ""
       Next i
     End If
     'serr$ = String(255, 0)
     'ret_flag = swe_sol_eclipse_when_glob(tjd_ut, SEFLG_SWIEPH, 0, tret(0), 0, serr$)
     'serr$ = String(255, 0)
     'ret_flag = swe_sol_eclipse_where(tret(0), SEFLG_SWIEPH, geoposx(0), attr(0), serr$)
     'fMainForm.out.Print "eclipse "; ret_flag, tret(0), outdeg3(geoposx(0)), outdeg3(geopos(1)),
     'serr$ = String(255, 0)
     'ret_flag = swe_rise_trans(tjd_ut, SE_MOON, "", SEFLG_SWIEPH, SE_CALC_RISE, geopos(0), 1013.25, 10, tret(0), serr$)
     'fMainForm.out.Print "next rise of Moon "; ret_flag, tret(0), 'outdeg3(geoposx(0)), outdeg3(geopos(1)),
     'serr$ = String(255, 0)
     'retc = swe_time_equ(tjd_ut, tret(0), serr$)
     'fMainForm.out.Print " te", tret(0)
     swe_close
End Sub

Private Sub Day_Change()
 iday% = Val(Day.Text)
End Sub


Private Sub Form_Load()
    Me.Left = GetSetting(App.Title, "Settings", "MainLeft", 1000)
    Me.Top = GetSetting(App.Title, "Settings", "MainTop", 1000)
    Me.Width = GetSetting(App.Title, "Settings", "MainWidth", 6500)
    Me.Height = GetSetting(App.Title, "Settings", "MainHeight", 6500)
    iday% = 1
    imonth% = 1
    iyear% = 1997
    lon = 8#
    lat = 47#

End Sub


Private Sub Form_Unload(Cancel As Integer)
    Dim i As Integer


    'close all sub forms
    For i = Forms.Count - 1 To 1 Step -1
        Unload Forms(i)
    Next
    If Me.WindowState <> vbMinimized Then
        SaveSetting App.Title, "Settings", "MainLeft", Me.Left
        SaveSetting App.Title, "Settings", "MainTop", Me.Top
        SaveSetting App.Title, "Settings", "MainWidth", Me.Width
        SaveSetting App.Title, "Settings", "MainHeight", Me.Height
    End If
End Sub




Private Sub fstar_Change()
 starname = fstar.Text
End Sub

Private Sub geolat_Change()
  lat = Val(geolat)
End Sub

Private Sub geolon_Change()
  lon = Val(geolon)
End Sub

Private Sub hel_flag_Click()
  If hel_flag Then bary_flag = 0
End Sub

Private Sub hour_Change()
 ihour% = Val(hour.Text)
End Sub

Private Sub minute_Change()
 imin = Val(minute.Text)
End Sub

Private Sub mnuHelpAbout_Click()
    frmAbout.Show vbModal, Me
End Sub



Private Sub mnuViewOptions_Click()
    'To Do
    MsgBox "Options Dialog Code goes here!"
End Sub



Private Sub mnuViewStatusBar_Click()
    If mnuViewStatusBar.Checked Then
        sbStatusBar.Visible = False
        mnuViewStatusBar.Checked = False
    Else
        sbStatusBar.Visible = True
        mnuViewStatusBar.Checked = True
    End If
End Sub


Private Sub mnuViewToolbar_Click()
    If mnuViewToolbar.Checked Then
        tbToolBar.Visible = False
        mnuViewToolbar.Checked = False
    Else
        tbToolBar.Visible = True
        mnuViewToolbar.Checked = True
    End If
End Sub



Private Sub tbToolBar_ButtonClick(ByVal Button As ComctlLib.Button)


    Select Case Button.Key


        Case "New"
            mnuFileNew_Click
        Case "New"
            mnuFileNew_Click
        Case "Open"
            mnuFileOpen_Click
        Case "Save"
            mnuFileSave_Click
        Case "Print"
            mnuFilePrint_Click
        Case "Cut"
            'To Do
            MsgBox "Cut Code goes here!"
        Case "Copy"
            'To Do
            MsgBox "Copy Code goes here!"
        Case "Paste"
            'To Do
            MsgBox "Paste Code goes here!"
        Case "Bold"
            'To Do
            MsgBox "Bold Code goes here!"
        Case "Italic"
            'To Do
            MsgBox "Italic Code goes here!"
        Case "Underline"
            'To Do
            MsgBox "Underline Code goes here!"
        Case "Left"
            'To Do
            MsgBox "Left Code goes here!"
        Case "Center"
            'To Do
            MsgBox "Center Code goes here!"
        Case "Right"
            'To Do
            MsgBox "Right Code goes here!"
    End Select
End Sub







Private Sub mnuHelpContents_Click()
    

End Sub


Private Sub mnuHelpSearch_Click()
 
End Sub

Private Sub mnuViewRefresh_Click()
    'To Do
    MsgBox "Refresh Code goes here!"
End Sub

Private Sub mnuFileOpen_Click()
    Dim sFile As String


    With dlgCommonDialog
        'To Do
        'set the flags and attributes of the
        'common dialog control
        .Filter = "All Files (*.*)|*.*"
        .ShowOpen
        If Len(.filename) = 0 Then
            Exit Sub
        End If
        sFile = .filename
    End With
    'To Do
    'process the opened file
End Sub


Private Sub mnuFileClose_Click()
    'To Do
    MsgBox "Close Code goes here!"
End Sub


Private Sub mnuFileSave_Click()
    'To Do
    MsgBox "Save Code goes here!"
End Sub


Private Sub mnuFileSaveAs_Click()
    'To Do
    'Setup the common dialog control
    'prior to calling ShowSave
    dlgCommonDialog.ShowSave
End Sub


Private Sub mnuFileSaveAll_Click()
    'To Do
    MsgBox "Save All Code goes here!"
End Sub


Private Sub mnuFileProperties_Click()
    'To Do
    MsgBox "Properties Code goes here!"
End Sub


Private Sub mnuFilePageSetup_Click()
    dlgCommonDialog.ShowPrinter
End Sub


Private Sub mnuFilePrintPreview_Click()
    'To Do
    MsgBox "Print Preview Code goes here!"
End Sub


Private Sub mnuFilePrint_Click()
    'To Do
    MsgBox "Print Code goes here!"
End Sub


Private Sub mnuFileSend_Click()
    'To Do
    MsgBox "Send Code goes here!"
End Sub


Private Sub mnuFileMRU_Click(Index As Integer)
    'To Do
    MsgBox "MRU Code goes here!"
End Sub


Private Sub mnuFileExit_Click()
    'unload the form
    Unload Me
End Sub

Private Sub mnuFileNew_Click()
    'To Do
    MsgBox "New File Code goes here!"
End Sub




Private Sub Month_Change()
imonth% = Val(Month.Text)
End Sub

Private Sub Year_Change()
iyear% = Val(Year.Text)
End Sub


