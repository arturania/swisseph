unit sample2;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls;

type
  TForm1 = class(TForm)
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    button_doit: TButton;
    button_exit: TButton;
    day: TEdit;
    month: TEdit;
    year: TEdit;
    outfld: TMemo;
    hour: TEdit;
    minute: TEdit;
    second: TEdit;
    combo_utet: TComboBox;
    lon_deg: TEdit;
    lon_min: TEdit;
    lon_sec: TEdit;
    Combo_EW: TComboBox;
    lat_deg: TEdit;
    lat_min: TEdit;
    lat_sec: TEdit;
    Combo_NS: TComboBox;
    combo_hsys: TComboBox;
    procedure button_doitClick(Sender: TObject);
    procedure button_exitClick(Sender: TObject);
  private
    { Private-Deklarationen }
  public
    { Public-Deklarationen }
  end;

var
  Form1: TForm1;

implementation
var
  iday, imonth, iyear, ipl, ihour, imin, isec: Integer;
  xx    : Array[0..5] of double;
  serr :  Array[0..255] of Char;

function swe_calc(tjd          : double;    {Julian day number}
                   ipl         : Integer;   {planet number}
                   iflag       : Longint;   {flag bits}
                   var xx      : double;    {first of 6 doubles}
                   sErr        : PChar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_calc@24';

procedure swe_close; stdcall; far; external 'swedll32.dll' name '_swe_close@0';

procedure swe_cotrans(var xpin  : double;
                   var xpout    : double;
                   eps          : double
); stdcall; far; external 'swedll32.dll' name '_swe_cotrans@16';

procedure swe_cotrans_sp(var xpin  : double;
                   var xpout    : double;
                   eps          : double
); stdcall; far; external 'swedll32.dll' name '_swe_cotrans_sp@16';

function swe_csnorm(p: Longint
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_csnorm@4';

function swe_csroundsec(x: Longint
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_csroundsec@4';

function swe_cs2degstr(t: Longint;
                   s     : pchar
): pchar; stdcall; far; external 'swedll32.dll' name '_swe_cs2degstr@8';

function swe_cs2lonlatstr(t: Longint;
                   pch   : char;
                   mch   : char;
                   s     : pchar
): pchar; stdcall; far; external 'swedll32.dll' name '_swe_cs2lonlatstr_d@16';

function swe_cs2timestr(t: Longint;
                   sep   : integer;
                   suppresszero : integer;
                   s     : pchar
): pchar; stdcall; far; external 'swedll32.dll' name '_swe_cs2timestr@16';

function swe_date_conversion(year: integer;
                   month       : integer;
                   day         : integer;
                   utime       : double;
                   c           : char;      {'g'regorian or 'j'ulian}
                   var tjd     : double
): integer; stdcall; far; external 'swedll32.dll' name '_swe_date_conversion@28';

function swe_day_of_week(var tjd: double
): integer; stdcall; far; external 'swedll32.dll' name '_swe_day_of_week@8';

function swe_degnorm(x: double
): double; stdcall; far; external 'swedll32.dll' name '_swe_degnorm@8';

function swe_deltat(tjd  : double                   
): double; stdcall; far; external 'swedll32.dll' name '_swe_deltat@8';

function swe_difcsn(p1: Longint;
                   p2 : Longint
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_difcsn@8';

function swe_difcs2n(p1: Longint;
                   p2 : Longint
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_difcs2n@8';

function swe_difdegn(p1 : double;
                   p2     : double
): double; stdcall; far; external 'swedll32.dll' name '_swe_difdegn@16';

function swe_difdeg2n(p1 : double;
                   p2     : double                   
): double; stdcall; far; external 'swedll32.dll' name '_swe_difdeg2n@16';

function swe_d2l(x: double
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_d2l@8';

function swe_fixstar(star      : pchar;     {star name}
                   tjd         : double;    {Julian day number}
                   iflag       : Longint;   {flag bits}
                   var xx      : double;    {first of 6 doubles}
                   sErr        : pchar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_fixstar@24';

function swe_get_planet_name(ipl : integer;
                   pname      : PChar
) : PChar; stdcall; far; external 'swedll32.dll' name '_swe_get_planet_name@8';

function swe_get_tid_acc(t_acc: double
) : double; stdcall; far; external 'swedll32.dll' name '_swe_get_tid_acc@0';

function swe_houses(tjdut      : double;
                    geolat     : double;
                    geolon     : double;
                    hsys       : char;
                    var hcusp0 : double;
                    var ascmc0 : double
) : integer; stdcall; far; external 'swedll32.dll' name '_swe_houses@36';

function swe_houses_armc(armc  : double;
                    geolat     : double;
                    eps        : double;
                    hsys       : char;
                    var hcusp0 : double;
                    var ascmc0 : double
) : integer; stdcall; far; external 'swedll32.dll' name '_swe_houses_armc@36';

function swe_house_pos(armc: double;
                    geolon : double;
                    eps    : double;
                    hsys       : char;
                    var xpin   : double;  {2 doubles: ecl. longitude and latitude}
                    serr       : pchar
): double; stdcall; far; external 'swedll32.dll' name '_swe_house_pos@36';

function swe_julday(year     : integer;
                    month      : integer;
                    day        : integer;
                    hour       : double;
                    gregflag   : integer
) : double; stdcall; far; external 'swedll32.dll' name '_swe_julday@24';

procedure swe_revjul(tjd        : double;
                    gregflag    : integer;
                    var year    : integer;
                    var month   : integer;
                    var day     : integer;
                    var hour    : double
); stdcall; far; external 'swedll32.dll' name '_swe_revjul@28';

procedure swe_set_ephe_path(path: pchar
); stdcall; far; external 'swedll32.dll' name '_swe_set_ephe_path@4';

procedure swe_set_jpl_file(fname: pchar
); stdcall; far; external 'swedll32.dll' name '_swe_set_jpl_file@4';

procedure swe_set_tid_acc(t_acc: double
); stdcall; far; external 'swedll32.dll' name '_swe_set_tid_acc@8';

procedure swe_set_topo(geolon   : double;
                   geolat       : double;
                   height       : double
); stdcall; far; external 'swedll32.dll' name '_swe_set_topo@24';

function swe_sidtime(tjdut: double                    
) : double; stdcall; far; external 'swedll32.dll' name '_swe_sidtime@8';

function swe_sidtime0(tjdut: double;
                    eps_true: double;
                    nut_long: double                    
) : Integer; stdcall; far; external 'swedll32.dll' name '_swe_sidtime0@24';


{$R *.DFM}

procedure TForm1.button_doitClick(Sender: TObject);
    const
   hsys: array[0..4] of Char = ('P', 'K', 'R', 'C', 'E');
   SE_ECL_NUT: integer = -1;
   SE_SUN : integer =          0;
   SE_MOON : integer =         1;
   SE_MERCURY : integer =      2;
   SE_VENUS : integer =        3;
   SE_MARS : integer =         4;
   SE_JUPITER : integer =      5;
   SE_SATURN : integer =       6;
   SE_URANUS : integer =       7;
   SE_NEPTUNE : integer =      8;
   SE_PLUTO : integer =        9;
   SE_MEAN_NODE : integer =    10;
   SE_TRUE_NODE : integer =    11;
   SE_MEAN_APOG : integer =    12;
   SE_OSCU_APOG : integer =    13;
   SE_EARTH : integer =        14;
   SE_CHIRON : integer =       15;
   SE_PHOLUS : integer =       16;
   SE_CERES : integer =        17;
   SE_PALLAS : integer =       18;
   SE_JUNO : integer =         19;
   SE_VESTA : integer =        20;

   SE_NPLANETS : integer =     21;
   SE_AST_OFFSET : integer =   10000;
   SE_FICT_OFFSET : integer =  40;
   SE_NFICT_ELEM : integer =   15;

   { Hamburger or Uranian "planets" }
   SE_CUPIDO : integer =       	40;
   SE_HADES : integer =        	41;
   SE_ZEUS : integer =         	42;
   SE_KRONOS : integer =       	43;
   SE_APOLLON : integer =      	44;
   SE_ADMETOS : integer =      	45;
   SE_VULKANUS : integer =     	46;
   SE_POSEIDON : integer =     	47;
   { other ficticious bodies }
   SE_ISIS : integer =         	48;
   SE_NIBIRU : integer =       	49;
   SE_HARRINGTON : integer =           50;
   SE_NEPTUNE_LEVERRIER : integer =    51;
   SE_NEPTUNE_ADAMS : integer =        52;
   SE_PLUTO_LOWELL : integer =         53;
   SE_PLUTO_PICKERING : integer =      54;

   {  flag bits for parameter iflag in function swe_calc()
   The flag bits are defined in such a way that iflag = 0 delivers what one
   usually wants:
     - the default ephemeris (SWISS) is used,
     - apparent geocentric positions referring to the true equinox of date are returned.
     If not only coordinates, but also speed values are required, use iflag = SEFLG_SPEED.
   }
   SEFLG_JPLEPH : Longint =    	1;       { use JPL ephemeris }
   SEFLG_SWIEPH : Longint =    	2;       { use SWISSEPH ephemeris }
   SEFLG_MOSEPH : Longint =    	4;       { use Moshier ephemeris }
   SEFLG_HELCTR : Longint =        8;      { return heliocentric position }
   SEFLG_TRUEPOS : Longint =       16;    { return true positions, not apparent }
   SEFLG_J2000 : Longint =         32;    { no precession, i.e. give J2000 equinox }
   SEFLG_NONUT : Longint =         64;    { no nutation, i.e. mean equinox of date }
   SEFLG_SPEED : Longint =         256;    { high precision speed (analytical computation) }
   SEFLG_NOGDEFL : Longint =       512;    { turn off gravitational deflection }
   SEFLG_NOABERR : Longint =       1024;   { turn off 'annual' aberration of light }
   SEFLG_EQUATORIAL : Longint =    (2*1024);   { equatorial positions are wanted }
   SEFLG_XYZ : Longint =           (4*1024);   { cartesian, not polar, coordinates are wanted}
   SEFLG_RADIANS : Longint =       (8*1024);   { coordinates are wanted in radians, not degrees }
   SEFLG_BARYCTR : Longint =       (16*1024);  { barycentric positions }
   SEFLG_TOPOCTR : Longint =       (32*1024);  { topocentric positions }


var
   tjd, tjdet, tjdut, tsid, armc, dhour, deltat : double;
   eps_true, nut_long: double;
   glon, glat: double;
   sjul: String[30];
   s: Array[0..255] of Char;
   sout: string[255];
   i, j: integer;
   iflag, rflag : Longint;
   ilondeg, ilonmin, ilonsec, ilatdeg, ilatmin, ilatsec: Integer;
   hcusps: array[0..12] of double;
   ascmc: array[0..9] of double;
   pname: array[0..19] of Char;
begin
  outfld.clear;
  iflag := 0;
  iday := StrToInt(day.Text);
  imonth := StrToInt(month.Text);
  iyear := StrToInt(year.Text);
  ihour := StrToInt(hour.Text);
  imin := StrToInt(minute.Text);
  isec := StrToInt(second.Text);
  ilondeg := StrToInt(lon_deg.Text);
  ilonmin := StrToInt(lon_min.Text);
  ilonsec := StrToInt(lon_sec.Text);
  ilatdeg := StrToInt(lat_deg.Text);
  ilatmin := StrToInt(lat_min.Text);
  ilatsec := StrToInt(lat_sec.Text);
  dhour := ihour + imin / 60.0 + isec / 3600.0;
  tjd := swe_julday(iyear, imonth, iday, dhour, 1);
  deltat := swe_deltat(tjd);
  if (combo_utet.ItemIndex <= 0)then
    begin
    {input date is Universal Time}
    Str((deltat * 86400):16:10,  sjul);
    sout := 'deltat:     ' + sjul + ' sec';
    outfld.Lines.Add(sout);
    tjdut := tjd;
    tjdet := tjd + deltat;
    Str(tjdut:10:8,  sjul);
    sjul := sjul + ' UT';
    end
  else
    begin
    {input date is Ephemeris Time}
    tjdet := tjd;
    tjdut := tjd - deltat;
    Str(tjdet:16:10,  sjul);
    sjul := sjul + ' ET';
    end;
  sout := 'jul. day:   ' + sjul;
  outfld.Lines.Add( sout);
  outfld.Lines.Add(' ');
  { planets }
  for i := SE_SUN to SE_TRUE_NODE do
    begin
      rflag := swe_calc(tjdet, i, iflag, xx[0], serr);
      if (rflag <> iflag) then outfld.Lines.Add(StrPas(serr));
      Str(xx[0]:16:10,  sjul);
      swe_get_planet_name(i, pname);
      sout := StrPas(pname);
      for j := StrLen(pname) to 11 do sout := sout + ' ';
      sout := sout + sjul;
      outfld.Lines.Add( sout);
    end;
  { for houses: ecliptic obliquity and nutation }
  rflag := swe_calc(tjdet, SE_ECL_NUT, 0, xx[0], serr);
  if (rflag <> 0) then outfld.Lines.Add(StrPas(serr));
  eps_true := xx[0];
  nut_long := xx [2];
  { geographic position }
  glon := ilondeg + ilonmin / 60.0 + ilonsec / 3600.0;
  if (combo_EW.ItemIndex > 0) then glon := -glon;
  glat := ilatdeg + ilatmin / 60.0 + ilatsec / 3600.0;
  if (combo_NS.ItemIndex > 0) then glat := -glat;
  { sidereal time }
  tsid := swe_sidtime(tjdut);
  tsid := tsid + glon / 15;
  armc := tsid * 15;
  outfld.Lines.Add(' ');
  Str(tsid:16:10, sjul);
  sout := 'Sid. time   ' + sjul;
  outfld.Lines.Add( sout);
  Str(armc:16:10, sjul);
  sout := 'ARMC        ' + sjul;
  outfld.Lines.Add( sout);
  { house method }
  i := combo_hsys.ItemIndex;
  if (i < 0) then i := 0;
  swe_houses_armc(armc, glat, eps_true, hsys[i], hcusps[0], ascmc[0]);
  Str(ascmc[0]:16:10, sjul);
  sout := 'ascendant   ' + sjul;
  outfld.Lines.Add( sout);
  Str(ascmc[1]:16:10, sjul);
  sout := 'MC          ' + sjul;
  outfld.Lines.Add( sout);
  Str(ascmc[3]:16:10, sjul);
  sout := 'vertex      ' + sjul;
  outfld.Lines.Add( sout);
  outfld.Lines.Add( '');
  for i := 1 to 12 do
    begin
      Str(i:2, sjul);
      sout := 'house ' + sjul + '    ';
      Str(hcusps[i]:16:10, sjul);
      sout := sout + sjul;
      outfld.Lines.Add( sout);
    end;

end;

procedure TForm1.button_exitClick(Sender: TObject);
begin
swe_close;
  close;
  Exit;
end;

end.
