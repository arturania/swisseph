unit swiss;

{ Swiss Ephemeris function declarations for Delphi 2 (32-bit), by Sarah Ashton}

interface

{Calculate positions of planets, asteroids, lunar nodes and apogees}
function swe_calc(tjd          : double;    {Julian day, Ephemeris Time}
                   ipl         : Integer;   {planet number}
                   iflag       : Longint;   {flag bits}
                   var xx      : double;    {first of 6 doubles}
                   sErr        : PChar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_calc@24';

function swe_calc_ut(tjd         : double;    {Julian day, Universal Time}
                     ipl         : Integer;   {planet number}
                     iflag       : Longint;   {flag bits}
                     var xx      : double;    {first of 6 doubles}
                     sErr        : PChar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_calc_ut@24';


{Close Swiss Ephemeris. Release resources used by the Swiss Ephemeris DLL.
 i.e. - the ephemeris path set by swe_set_ephe_path()
      - the JPL file name set by swe_set_jpl_file()
      - the geographical location set by swe_set_topo() for topocentric
planetary positions
      - the sidereal mode set by swe_set_sid_mode() for sidereal planetary
positions}
procedure swe_close; stdcall; far; external 'swedll32.dll' name
'_swe_close@0';

{Coordinate transformation, from ecliptic to equator (-ve eps) or
vice-versa}
procedure swe_cotrans(var xpin  : double; {long., lat., dist}
                   var xpout    : double;
                   eps          : double   {obliquity}
); stdcall; far; external 'swedll32.dll' name '_swe_cotrans@16';

{Coordinate transformation of position and speed}
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
): pchar; stdcall; far; external 'swedll32.dll' name
'_swe_cs2lonlatstr_d@16';

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
): integer; stdcall; far; external 'swedll32.dll' name
'_swe_date_conversion@28';

function swe_day_of_week(var tjd: double
): integer; stdcall; far; external 'swedll32.dll' name
'_swe_day_of_week@8';

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
                   tjd         : double;    {Julian day in Ephemeris Time}
                   iflag       : Longint;   {flag bits}
                   var xx      : double;    {first of 6 doubles}
                   sErr        : pchar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name '_swe_fixstar@24';

function swe_fixstar_ut(star   : pchar;     {star name}
                   tjd         : double;    {Julian day in Universal Time}
                   iflag       : Longint;   {flag bits}
                   var xx      : double;    {first of 6 doubles}
                   sErr        : pchar      {Error-String}
): Longint; stdcall; far; external 'swedll32.dll' name
'_swe_fixstar_ut@24';

function swe_get_planet_name(ipl : integer;
                   pname      : PChar
): PChar; stdcall; far; external 'swedll32.dll' name
'_swe_get_planet_name@8';

function swe_get_tid_acc(t_acc: double
): double; stdcall; far; external 'swedll32.dll' name '_swe_get_tid_acc@0';

function swe_houses(tjdut      : double;
                    geolat     : double;
                    geolon     : double;
                    hsys       : char;
                    var hcusp0 : double;
                    var ascmc0 : double
): integer; stdcall; far; external 'swedll32.dll' name '_swe_houses@36';

function swe_houses_ex(tjdut   : double;
                    iflag      : Longint; {0 or SEFLG_SIDEREAL or
SEFLG_RADIANS}
                    geolat     : double;
                    geolon     : double;
                    hsys       : char;
                    var hcusp0 : double;
                    var ascmc0 : double
): integer; stdcall; far; external 'swedll32.dll' name '_swe_houses_ex@40';

function swe_houses_armc(armc  : double;
                    geolat     : double;
                    eps        : double;
                    hsys       : char;
                    var hcusp0 : double;
                    var ascmc0 : double
): integer; stdcall; far; external 'swedll32.dll' name
'_swe_houses_armc@36';

function swe_house_pos(armc: double;
                    geolon : double;
                    eps    : double;
                    hsys      : char;
                    var xpin  : double;  {2 doubles: ecl. longitude and
latitude}
                    serr      : pchar
): double; stdcall; far; external 'swedll32.dll' name '_swe_house_pos@36';

function swe_julday(year     : integer;
                    month      : integer;
                    day        : integer;
                    hour       : double;
                    gregflag   : integer
): double; stdcall; far; external 'swedll32.dll' name '_swe_julday@24';

procedure swe_revjul(tjd        : double;
                    gregflag    : integer;
                    var year    : integer;
                    var month   : integer;
                    var day     : integer;
                    var hour    : double
); stdcall; far; external 'swedll32.dll' name '_swe_revjul@28';

{Equation of time}
function swe_time_equ(tjd_et   : double;
                   var e       : double; {local apparent - local mean time}
                   sErr        : PChar      {Error-String}
): integer; stdcall; far; external 'swedll32.dll' name '_swe_time_equ@16';

{Set path for ephemeris files}
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
): double; stdcall; far; external 'swedll32.dll' name '_swe_sidtime@8';

function swe_sidtime0(tjdut: double;
                    eps_true: double;
                    nut_long: double
): Integer; stdcall; far; external 'swedll32.dll' name '_swe_sidtime0@24';

procedure swe_set_sid_mode(sid_mode: longint;
                            t0      : double;
                            ayan_t0 : double
); stdcall; far; external 'swedll32.dll' name '_swe_set_sid_mode@20';

function swe_get_ayanamsa(tjd_et: double
): double; stdcall; far; external 'swedll32.dll' name
'_swe_get_ayanamsa@8';

function swe_get_ayanamsa_ut(tjd_ut: double
): double; stdcall; far; external 'swedll32.dll' name
'_swe_get_ayanamsa_ut@8';


{Eclipse calculations??}
{Computes geographic location and attributes of solar eclipse at a given
tjd}
function swe_sol_eclipse_where(tjd_ut  : double;
                            ifl        : longint; {Ephemeris flag }
                            var geopos : double;  {longitude, latitude,
height above sea}                           
                            var attr   : double; {first of 20 doubles}
                            serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_sol_eclipse_where@24';

{Computes attributes of a solar eclipse for given tjd, geolon, geolat}
function swe_sol_eclipse_how(tjd_ut    : double;        {time, Jul. day UT}
                            ifl        : longint; {ephemeris flag}
                            var geopos : double;  {longitude, latitude,
height above sea}
                            var attr   : double; {first of 20 doubles}
                            serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_sol_eclipse_how@24';

{Finds time of next local eclipse}
function swe_sol_eclipse_when_loc(tjd  : double;  {start date for search,
Jul. day UT}
                            ifl        : longint; {Ephemeris flag }
                            var geopos : double;  {longitude, latitude,
height above sea}
                            var tret   : double; {first of 10 doubles}
                            var attr   : double; {first of 20 doubles}
                            backward   : boolean; {TRUE, if backward
search}
                            serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_sol_eclipse_when_loc@32';

{Finds time of next eclipse globally}
function swe_sol_eclipse_when_glob(tjd  : double;  {start date for search,
Jul. day UT}
                             ifl        : longint; {Ephemeris flag}
                             ifltype    : longint; {eclipse type wanted:
SE_ECL_TOTAL etc}
                             var tret   : double; {first of 10 doubles}
                             var attr   : double; {first of 20 doubles}
                             backward   : boolean; {TRUE, if backward
search}
                             serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_sol_eclipse_when_glob@28';

{computes attributes of a lunar eclipse for given tjd}
function swe_lun_eclipse_how(tjd_ut     : double;  {time, Jul. day UT}
                             ifl        : longint; {Ephemeris flag}
                             var geopos : double;  {longitude, latitude,
height above sea}
                             var attr   : double; {first of 20 doubles}
                             serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_lun_eclipse_how@24';

function swe_lun_eclipse_when(tjd       : double;  {start date for search,
Jul. day UT}
                             ifl        : longint; {Ephemeris flag}
                             ifltype    : longint; {eclipse type wanted:
SE_ECL_TOTAL etc}
                             var tret   : double; {first of 10 doubles}
                             backward   : boolean; {TRUE, if backward
search}
                             serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_lun_eclipse_when@28';


{planetary phenomena}
function swe_pheno(tjd_et    : double; {time Jul. Day ET}
                  ipl        : longint; {planet number}
                  ifl        : longint; {Ephemeris flag}
                  var attr   : double; {first of 20 doubles}
                  serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name '_swe_pheno@24';

function swe_pheno_ut(tjd_ut : double; {time Jul. Day UT}
                  ipl        : longint; {planet number}
                  ifl        : longint; {Ephemeris flag}
                  var attr   : double; {first of 20 doubles}
                  serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name '_swe_pheno_ut@24';

function swe_refrac(inalt   : double;
                   atpress  : double; {atmospheric pressure in mbar (hPa)}
                   attemp   : double; {atmospheric temperature in degrees
Celsius}
                   ifl      : longint {either SE_TRUE_TO_APP or
SE_APP_TO_TRUE}
): double; stdcall; far; external 'swedll32.dll' name '_swe_refrac@28';

procedure swe_azalt(tjd_ut  : double;   {UT}
                 cflag      : longint; {SE_ECL2HOR or SE_EQU2HOR}
                 var geopos : double;  {longitude, latitude, height above
sea}
                 atpress    : double; {atmospheric pressure in mbar (hPa)}
                 attemp     : double; {atmospheric temperature in degrees
Celsius}
                 var xin    : double;   {ecliptical or equatorial
coordinates}
                 var xaz    : double    {azimuth, true altitude, apparent
altitude}
); stdcall; far; external 'swedll32.dll' name '_swe_azalt@40';

procedure swe_azalt_rev(tjd_ut  : double;       {UT}
                     cflag      : longint; {SE_HOR2ECL or SE_HOR2EQU}
                     var geopos : double;  {longitude, latitude, height
above sea}
                     var xin    : double;  {azimuth and true altitude}
                     var xout   : double        {ecliptical or equatorial
coordinates: x,y,z}
); stdcall; far; external 'swedll32.dll' name '_swe_azalt_rev@24';

function swe_rise_trans(tjd_ut : double; {Search after this time (UT)}
                    ipl        : longint; {planet number}
                    starname   : pchar;     {star name, if star}
                    ifl        : longint; {Ephemeris flag}
                    rsmi       : longint; {Transits wanted}
                    var geopos : double;  {longitude, latitude, height
above sea}
                    atpress    : double; {atmospheric pressure in mbar
(hPa)}
                    attemp     : double; {atmospheric temperature in
degrees Celsius}
                    var tret   : double; {first of 10 doubles}
                    serr       : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_rise_trans@52';


{Compute planetary nodes and apsides}
function swe_nod_aps(tjd_et : double; {Julian day, Ephemeris Time}
                  ipl       : longint; {planet number}
                  iflag     : Longint;   {flag bits}
                  method    : longint; {calculation method}
                  var xnasc : double; {First of 6 doubles for ascending
node}
                  var xndsc : double; {First of 6 doubles for descending
node}
                  var xperi : double; {First of 6 doubles for perihelion}
                  var xaphe : double; {First of 6 doubles for aphelion}
                  serr      : pchar
): longint; stdcall; far; external 'swedll32.dll' name '_swe_nod_aps@40';

function swe_nod_aps_ut(tjd_ut : double; {Julian day, Universal Time}
                  ipl       : longint;{planet number}
                  iflag     : Longint;{flag bits}
                  method    : longint;{calculation method}
                  var xnasc : double; {First of 6 doubles for ascending
node}
                  var xndsc : double; {First of 6 doubles for descending
node}
                  var xperi : double; {First of 6 doubles for perihelion}
                  var xaphe : double; {First of 6 doubles for aphelion}
                  serr      : pchar
): longint; stdcall; far; external 'swedll32.dll' name
'_swe_nod_aps_ut@40';


{Swiss Ephemeris constants for Pascal}
const
   SE_ECL_NUT: integer         = -1;
   SE_SUN: integer             = 0;
   SE_MOON: integer            = 1;
   SE_MERCURY: integer         = 2;
   SE_VENUS: integer           = 3;
   SE_MARS: integer            = 4;
   SE_JUPITER: integer         = 5;
   SE_SATURN: integer          = 6;
   SE_URANUS: integer          = 7;
   SE_NEPTUNE: integer         = 8;
   SE_PLUTO: integer           = 9;
   SE_MEAN_NODE: integer       = 10;
   SE_TRUE_NODE: integer       = 11;
   SE_MEAN_APOG: integer       = 12;
   SE_OSCU_APOG: integer       = 13;
   SE_EARTH: integer           = 14;
   SE_CHIRON: integer          = 15;
   SE_PHOLUS: integer          = 16;
   SE_CERES: integer           = 17;
   SE_PALLAS: integer          = 18;
   SE_JUNO: integer            = 19;
   SE_VESTA: integer           = 20;

   SE_NPLANETS: integer        = 21;
   SE_AST_OFFSET: integer      = 10000;
   SE_FICT_OFFSET: integer     = 40;
   SE_NFICT_ELEM: integer      = 15;

   { Hamburger or Uranian "planets" }
   SE_CUPIDO: integer          = 40;
   SE_HADES: integer           = 41;
   SE_ZEUS: integer            = 42;
   SE_KRONOS: integer          = 43;
   SE_APOLLON: integer         = 44;
   SE_ADMETOS: integer         = 45;
   SE_VULKANUS: integer        = 46;
   SE_POSEIDON: integer        = 47;
   { other ficticious bodies }
   SE_ISIS: integer            = 48;
   SE_NIBIRU: integer          = 49;
   SE_HARRINGTON: integer      = 50;
   SE_NEPTUNE_LEVERRIER: integer = 51;
   SE_NEPTUNE_ADAMS: integer     = 52;
   SE_PLUTO_LOWELL: integer      = 53;
   SE_PLUTO_PICKERING: integer   =  54;

{Flag bits for parameter iflag in function swe_calc()
 Iflag = 0 delivers - the default ephemeris (SWISS) is used,
     - apparent geocentric positions referring to the true equinox of date
are returned.
     If not only coordinates, but also speed values are required, use iflag
= SEFLG_SPEED.}
   SEFLG_JPLEPH: Longint = 1;       { use JPL ephemeris }
   SEFLG_SWIEPH: Longint = 2;       { use SWISSEPH ephemeris }
   SEFLG_MOSEPH: Longint = 4;       { use Moshier ephemeris }
   SEFLG_HELCTR: Longint = 8;      { return heliocentric position }
   SEFLG_TRUEPOS: Longint = 16;    { return true positions, not apparent }
   SEFLG_J2000: Longint = 32;    { no precession, i.e. give J2000 equinox }
   SEFLG_NONUT: Longint = 64;    { no nutation, i.e. mean equinox of date }
   SEFLG_SPEED: Longint = 256;    { high precision speed (analytical
computation) }
   SEFLG_NOGDEFL: Longint = 512;    { turn off gravitational deflection }
   SEFLG_NOABERR: Longint = 1024;   { turn off 'annual' aberration of light
}
   SEFLG_EQUATORIAL: Longint = (2*1024);   { equatorial positions are
wanted }
   SEFLG_XYZ: Longint = (4*1024);   { cartesian, not polar, coordinates are
wanted}
   SEFLG_RADIANS: Longint   = (8*1024);   { coordinates are wanted in
radians, not degrees }
   SEFLG_BARYCTR: Longint   = (16*1024);  { barycentric positions }
   SEFLG_TOPOCTR: Longint   = (32*1024);  { topocentric positions }
   SEFLG_SIDEREAL: longint  =  (64*1024);  { sidereal positions }

   SE_SIDBITS              = 256; {for projection onto ecliptic of t0}
   SE_SIDBIT_ECL_T0        = 256;  {for projection onto solar system plane}
   SE_SIDBIT_SSY_PLANE     = 512;

   SE_MAX_STNAME : Longint = 20; {maximum size of fixstar name;
                                   the parameter star in swe_fixstar
                                   must allow twice this space for
                                   the returned star name.}

   SEHOUSE_SYSTEM: array[0..9] of Char = ('P', 'K', 'O', 'R', 'C', 'E',
'V', 'X', 'H', 'T');
{       'P'     Placidus
        'K'     Koch
        'O'     Porphyrius
        'R'     Regiomontanus
        'C'     Campanus
        'A' or 'E'      Equal (cusp 1 is Ascendant)
        'V'     Vehlow equal (Asc. in middle of house 1)
        'X'     axial rotation system
        'H'     azimuthal or horizontal system
        'T'     Polich/Page ("topocentric" system)
}
       

 {Sidereal modes for swe_set_sid_mode}
    SE_SIDM_FAGAN_BRADLEY = 0;
    SE_SIDM_LAHIRI = 1;
    SE_SIDM_DELUCE = 2;
    SE_SIDM_RAMAN = 3;
    SE_SIDM_USHASHASHI = 4;
    SE_SIDM_KRISHNAMURTI = 5;
    SE_SIDM_DJWHAL_KHUL = 6;
    SE_SIDM_YUKTESHWAR = 7;
    SE_SIDM_JN_BHASIN = 8;
    SE_SIDM_BABYL_KUGLER1 = 9;
    SE_SIDM_BABYL_KUGLER2 = 10;
    SE_SIDM_BABYL_KUGLER3 = 11;
    SE_SIDM_BABYL_HUBER = 12;
    SE_SIDM_BABYL_ETPSC = 13;
    SE_SIDM_ALDEBARAN_15TAU = 14;
    SE_SIDM_HIPPARCHOS = 15;
    SE_SIDM_SASSANIAN = 16;
    SE_SIDM_GALCENT_0SAG = 17;
    SE_SIDM_J2000 = 18;
    SE_SIDM_J1900 = 19;
    SE_SIDM_B1950 = 20;
    SE_SIDM_USER = 255;

    SE_NSIDM_PREDEF    = 21;

{eclipse codes}
    SE_ECL_CENTRAL: longint       = 1;
    SE_ECL_NONCENTRAL: longint    = 2;
    SE_ECL_TOTAL: longint         = 4;
    SE_ECL_ANNULAR: longint       = 8;
    SE_ECL_PARTIAL: longint       = 16;
    SE_ECL_ANNULAR_TOTAL: longint = 32;
    SE_ECL_PENUMBRAL: longint     = 64;
    SE_ECL_VISIBLE: longint       = 128;
    SE_ECL_MAX_VISIBLE: longint   = 256;
    SE_ECL_1ST_VISIBLE: longint   = 512;
    SE_ECL_2ND_VISIBLE: longint   = 1024;
    SE_ECL_3RD_VISIBLE: longint   = 2048;
    SE_ECL_4TH_VISIBLE: longint   = 4096;

{Bits for data conversion with swe_azalt() and swe_azalt_rev()}
    SE_ECL2HOR         = 0;
    SE_EQU2HOR         = 1;
    SE_HOR2ECL         = 0;
    SE_HOR2EQU         = 1;

{For swe_refrac()}
    SE_TRUE_TO_APP     = 0;
    SE_APP_TO_TRUE     = 1;

{Indices for swe_rise_trans()}
    SE_CALC_RISE        = 1;
    SE_CALC_SET         = 2;
    SE_CALC_MTRANSIT    = 4;    {upper meridian transit}
    SE_CALC_ITRANSIT    = 8;    {lower meridian transit}
    SE_BIT_DISC_CENTER  = 256; {added to SE_CALC_RISE/SET if rise or set of
disc center is  requried}
    SE_BIT_NO_REFRACTION =512; {added to SE_CALC_RISE/SET if refraction is
not to be considered }


{points returned by swe_houses() and swe_houses_armc() ascmc[0...10]}
    SE_ASC              = 0;
    SE_MC               = 1;
    SE_ARMC             = 2;
    SE_VERTEX           = 3;
    SE_EQUASC           = 4;    { "equatorial ascendant" }
    SE_COASC1           = 5;    { "co-ascendant" (W. Koch) }
    SE_COASC2           = 6;    { "co-ascendant" (M. Munkasey) }
    SE_POLASC           = 7;    { "polar ascendant" (M. Munkasey)}
    SE_NASCMC           = 8;

{Modes for planetary nodes/apsides, swe_nod_aps(), swe_nod_aps_ut()}
    SE_NODBIT_MEAN      = 1;
    SE_NODBIT_OSCU      = 2;
    SE_NODBIT_OSCU_BAR  = 4;
    SE_NODBIT_FOPOINT   = 256;

implementation

end.
