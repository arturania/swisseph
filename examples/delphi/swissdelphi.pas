unit swissdelphi;

{ Swiss Ephemeris constants for Delphi, by Sarah Ashton }
{ based on Swiss Ephemeris sample code for Delphi 2
  extensions by Robert Amlung, Strasbourg }

{ Others extensions by Pierre Fontaine, Villejuif, France }
{ 03/02/2011 - update for version 1.77 }

{ Others extensions by Pierre Fontaine, St Sauveur la Pommeraye, France 
  pierre_fontaine@orange.fr
}
{ 03/03/2014 - update for version 2.00 }
{ 21/10/2015 - update for version 2.04 decorated DLL
  27/05/2016 - update for version 2.05 decorated DLL
  add a DEFINE for using trace (swetrs32.dll)
  20/01/2017 - update for version 2.06
  19/01/2018 - update for version 2.07
  02/02/2018 - update for version 2.07.01
  
  }

{ ATTENTION: there may be errors in some less important function
  declarations, because we have not tested all of them.
  If a function does not work, you can try to correct it comparing
  it with the C-declaration in swephexp.h. This should be rather simple
  from the given samples. Please, inform Astrodienst about errors. }
{$WRITEABLECONST ON}
{ $DEFINE SWISSTRACE }   // For using trace with swetrs32.dll

interface

const
{$IFDEF SWISSTRACE}
 DLL_NAME = 'swetrs32.dll';
{$ELSE}
 DLL_NAME = 'swedll32.dll';
{$ENDIF}
// ************************
// defined in swephexp.h
// ************************
  // definitions for use also by non-C programmers
  SE_AUNIT_TO_KM        =  (149597870.691);
  SE_AUNIT_TO_LIGHTYEAR =  (1.0/63241.077088071);
  SE_AUNIT_TO_PARSEC    =  (1.0/206264.8062471);
  
  // values for gregflag in swe_julday() and swe_revjul()
  SE_JUL_CAL  = 0;
  SE_GREG_CAL = 1;

  // planet numbers for the ipl parameter in swe_calc()

  SE_ECL_NUT: Integer = -1;

  SE_SUN       = 0;
  SE_MOON      = 1;
  SE_MERCURY   = 2;
  SE_VENUS     = 3;
  SE_MARS      = 4;
  SE_JUPITER   = 5;
  SE_SATURN    = 6;
  SE_URANUS    = 7;
  SE_NEPTUNE   = 8;
  SE_PLUTO     = 9;
  SE_MEAN_NODE = 10;
  SE_TRUE_NODE = 11;
  SE_MEAN_APOG = 12;
  SE_OSCU_APOG = 13;
  SE_EARTH     = 14;
  SE_CHIRON    = 15;
  SE_PHOLUS    = 16;
  SE_CERES     = 17;
  SE_PALLAS    = 18;
  SE_JUNO      = 19;
  SE_VESTA     = 20;
  SE_INTP_APOG = 21;
  SE_INTP_PERG = 22;

  SE_NPLANETS = 23;

  SE_AST_OFFSET = 10000;

  { Body numbers of other asteroids are above SE_AST_OFFSET (=10000) and have to be constructed as follows:
    ipl = SE_AST_OFFSET + Minor_Planet_Catalogue_number;
    e.g. Eros :  ipl = SE_AST_OFFSET +  433
    The names of the asteroids and their catalogue numbers can be found in seasnam.txt. }

  SE_VARUNA = SE_AST_OFFSET + 20000;

  SE_FICT_OFFSET   = 40;
  SE_FICT_OFFSET_1 = 39;
  SE_FICT_MAX      = 999;
  SE_NFICT_ELEM    = 19;

  SE_COMET_OFFSET = 1000;

  SE_NALL_NAT_POINTS = SE_NPLANETS + SE_NFICT_ELEM;

  { Hamburger or Uranian "planets" }
  SE_CUPIDO: Integer   = 40;
  SE_HADES: Integer    = 41;
  SE_ZEUS: Integer     = 42;
  SE_KRONOS: Integer   = 43;
  SE_APOLLON: Integer  = 44;
  SE_ADMETOS: Integer  = 45;
  SE_VULKANUS: Integer = 46;
  SE_POSEIDON: Integer = 47;
  { other ficticious bodies }
  SE_ISIS: Integer              = 48;
  SE_NIBIRU: Integer            = 49;
  SE_HARRINGTON: Integer        = 50;
  SE_NEPTUNE_LEVERRIER: Integer = 51;
  SE_NEPTUNE_ADAMS: Integer     = 52;
  SE_PLUTO_LOWELL: Integer      = 53;
  SE_PLUTO_PICKERING: Integer   = 54;
  SE_VULCAN                     = 55;
  SE_WHITE_MOON                 = 56;
  SE_PROSERPINA                 = 57;
  SE_WALDEMATH                  = 58;

  SE_FIXSTAR = -10;

  { points returned by swe_houses() and swe_houses_armc() ascmc[0...10] }
  SE_ASC    = 0;
  SE_MC     = 1;
  SE_ARMC   = 2;
  SE_VERTEX = 3;
  SE_EQUASC = 4; { "equatorial ascendant" }
  SE_COASC1 = 5; { "co-ascendant" (W. Koch) }
  SE_COASC2 = 6; { "co-ascendant" (M. Munkasey) }
  SE_POLASC = 7; { "polar ascendant" (M. Munkasey) }
  SE_NASCMC = 8;

  { Flag bits for parameter iflag in function swe_calc()
    Iflag = 0 delivers - the default ephemeris (SWISS) is used,
    - apparent geocentric positions referring to the true equinox of date
    are returned.
    If not only coordinates, but also speed values are required, use iflag
    = SEFLG_SPEED. }
  SEFLG_JPLEPH: Longint     = 1; { use JPL ephemeris }
  SEFLG_SWIEPH: Longint     = 2; { use SWISSEPH ephemeris }
  SEFLG_DEFAULTEPH: Longint = 2; { default SWISSEPH }
  SEFLG_MOSEPH: Longint     = 4; { use Moshier ephemeris }
  SEFLG_HELCTR: Longint     = 8; { return heliocentric position }
  SEFLG_TRUEPOS: Longint    = 16; { return true positions, not apparent }
  SEFLG_J2000: Longint      = 32; { no precession, i.e. give J2000 equinox }
  SEFLG_NONUT: Longint      = 64; { no nutation, i.e. mean equinox of date }
  SEFLG_SPEED3: Longint     = 128; { do not use it, SPEED is faster and more precise }
  SEFLG_SPEED: Longint      = 256; { high precision speed (analytical computation) }
  SEFLG_NOGDEFL: Longint    = 512; { turn off gravitational deflection }
  SEFLG_NOABERR: Longint    = 1024; { turn off 'annual' aberration of light }
  SEFLG_ASTROMETRIC         = 1536; {astrometric position, i.e. with light-time,
                                     but without aberration and light deflection
                                     (SEFLG_NOABERR|SEFLG_NOGDEFL)}
  SEFLG_EQUATORIAL: Longint = (2 * 1024); { equatorial positions are wanted }
  SEFLG_XYZ: Longint        = (4 * 1024);
  { cartesian, not polar, coordinates are wanted }
  SEFLG_RADIANS: Longint = (8 * 1024);
  { coordinates are wanted in radians, not degrees }
  SEFLG_BARYCTR: Longint  = (16 * 1024); { barycentric positions }
  SEFLG_TOPOCTR: Longint  = (32 * 1024); { topocentric positions }
  SEFLG_ORBEL_AA          = (32 * 1024); {used for Astronomical Almanac mode in 
                                          calculation of Kepler elipses (=SEFLG_TOPOCTR)}
  SEFLG_SIDEREAL: Longint = (64 * 1024); { sidereal positions }
  SEFLG_ICRS: Longint     = (128 * 1024); { ICRS (DE406 reference frame) }
  SEFLG_DPSIDEPS_1980: Longint = (256 * 1024); { reproduce JPL Horizons 1962 - today to 0.002 arcsec. }
  SEFLG_JPLHOR_APPROX: Longint = (512 * 1024); { SEFLG_JPLHOR_APPROX	(512*1024) }
  SEFLG_JPLHOR: Longint = (256 * 1024); // =SEFLG_DPSIDEPS_1980;
  
  SE_SIDBITS          = 256; 
  SE_SIDBIT_ECL_T0    = 256; { for projection onto ecliptic of t0 }
  SE_SIDBIT_SSY_PLANE = 512; { for projection onto solar system plane }
  SE_SIDBIT_USER_UT   = 1024; { with user-defined ayanamsha, t0 is UT }

  { Sidereal modes (ayanamsas) for swe_set_sid_mode }
  SE_SIDM_FAGAN_BRADLEY   = 0;
  SE_SIDM_LAHIRI          = 1;
  SE_SIDM_DELUCE          = 2;
  SE_SIDM_RAMAN           = 3;
  SE_SIDM_USHASHASHI      = 4;
  SE_SIDM_KRISHNAMURTI    = 5;
  SE_SIDM_DJWHAL_KHUL     = 6;
  SE_SIDM_YUKTESHWAR      = 7;
  SE_SIDM_JN_BHASIN       = 8;
  SE_SIDM_BABYL_KUGLER1   = 9;
  SE_SIDM_BABYL_KUGLER2   = 10;
  SE_SIDM_BABYL_KUGLER3   = 11;
  SE_SIDM_BABYL_HUBER     = 12;
  SE_SIDM_BABYL_ETPSC     = 13;
  SE_SIDM_ALDEBARAN_15TAU = 14;
  SE_SIDM_HIPPARCHOS      = 15;
  SE_SIDM_SASSANIAN       = 16;
  SE_SIDM_GALCENT_0SAG    = 17;
  SE_SIDM_J2000           = 18;
  SE_SIDM_J1900           = 19;
  SE_SIDM_B1950           = 20;
  SE_SIDM_SURYASIDDHANTA  = 21; // v1.79
  SE_SIDM_SURYASIDDHANTA_MSUN  = 22; // v1.79
  SE_SIDM_ARYABHATA       = 23; // v1.79
  SE_SIDM_ARYABHATA_MSUN  = 24; // v1.79
  SE_SIDM_SS_REVATI       = 25;
  SE_SIDM_SS_CITRA        = 26; 
  SE_SIDM_TRUE_CITRA      = 27;
  SE_SIDM_TRUE_REVATI     = 28;
  SE_SIDM_TRUE_PUSHYA     = 29;
  SE_SIDM_GALCENT_RGILBRAND = 30;
  SE_SIDM_GALEQU_IAU1958  = 31;
  SE_SIDM_GALEQU_TRUE     = 32;
  SE_SIDM_GALEQU_MULA     = 33;
  SE_SIDM_GALALIGN_MARDYKS= 34;
  SE_SIDM_TRUE_MULA       = 35;
  SE_SIDM_GALCENT_MULA_WILHELM = 36;
  SE_SIDM_ARYABHATA_522    = 37;
  SE_SIDM_BABYL_BRITTON    = 38;
  SE_SIDM_TRUE_SHEORAN     = 39;
//  SE_SIDM_GALCENT_COCHRANE = 40;
//  SE_SIDM_MANJULA          = 41;
  SE_SIDM_USER            = 255; //user-defined ayanamsha, t0 is TT

  SE_NSIDM_PREDEF = 40; // v2.07

  { Modes for planetary nodes/apsides, swe_nod_aps(), swe_nod_aps_ut() }
  SE_NODBIT_MEAN     = 1; { mean nodes/apsides }
  SE_NODBIT_OSCU     = 2; { osculating nodes/apsides }
  SE_NODBIT_OSCU_BAR = 4; { same, but motion about solar system barycenter is considered }
  SE_NODBIT_FOPOINT = 256; { focal point of orbit instead of aphelion }

  SE_MAX_STNAME: Longint = 256; { maximum size of fixstar name;
    the parameter star in swe_fixstar
    must allow twice this space for
    the returned star name. }

  { eclipse codes }
  SE_ECL_CENTRAL: Longint        = 1;
  SE_ECL_NONCENTRAL: Longint     = 2;
  SE_ECL_TOTAL: Longint          = 4;
  SE_ECL_ANNULAR: Longint        = 8;
  SE_ECL_PARTIAL: Longint        = 16;
  SE_ECL_ANNULAR_TOTAL: Longint  = 32;
  SE_ECL_PENUMBRAL: Longint      = 64;
  SE_ECL_ALLTYPES_SOLAR: Longint = 63;
  // (SE_ECL_CENTRAL|SE_ECL_NONCENTRAL|SE_ECL_TOTAL|SE_ECL_ANNULAR|SE_ECL_PARTIAL|SE_ECL_ANNULAR_TOTAL)
  SE_ECL_ALLTYPES_LUNAR: Longint = 84; // (SE_ECL_TOTAL|SE_ECL_PARTIAL|SE_ECL_PENUMBRAL)
  SE_ECL_VISIBLE: Longint        = 128;
  SE_ECL_MAX_VISIBLE: Longint    = 256;
  SE_ECL_1ST_VISIBLE: Longint    = 512;
  SE_ECL_PARTBEG_VISIBLE: Longint = 512;
  SE_ECL_2ND_VISIBLE: Longint    = 1024;
  SE_ECL_TOTBEG_VISIBLE: Longint = 1024;
  SE_ECL_3RD_VISIBLE: Longint    = 2048;
  SE_ECL_TOTEND_VISIBLE: Longint = 2048;
  SE_ECL_4TH_VISIBLE: Longint    = 4096;
  SE_ECL_PARTEND_VISIBLE: Longint    = 4096;
  SE_ECL_PENUMBBEG_VISIBLE: Longint  = 8192;
  SE_ECL_PENUMBEND_VISIBLE: Longint  = 16384;
  SE_ECL_OCC_BEG_DAYLIGHT: Longint   = 8192;
  SE_ECL_OCC_END_DAYLIGHT: Longint   = 16384;
  SE_ECL_ONE_TRY: Longint        = (32 * 1024);
  { check if the next conjunction of the moon with
    a planet is an occultation; don't search further }

  { Indices for swe_rise_trans() }
  SE_CALC_RISE           = 1;
  SE_CALC_SET            = 2;
  SE_CALC_MTRANSIT       = 4; { upper meridian transit }
  SE_CALC_ITRANSIT       = 8; { lower meridian transit }
  SE_BIT_DISC_CENTER     = 256;  { added to SE_CALC_RISE/SET if rise or set of disc center is  requried }
  SE_BIT_DISC_BOTTOM     = 8192; { added to SE_CALC_RISE/SET if rise or set of lower limb of disc is  requried }
  SE_BIT_GEOCTR_NO_ECL_LAT = 128;{use geocentric rather than topocentric position of object and 
                                  ignore its ecliptic latitude }
  SE_BIT_NO_REFRACTION   = 512;  { added to SE_CALC_RISE/SET if refraction is not to be considered }
  SE_BIT_CIVIL_TWILIGHT  = 1024; { added to SE_CALC_RISE/SET }
  SE_BIT_CIVIL           = 1024; { added to SE_CALC_RISE/SET }
  SE_BIT_NAUTIC_TWILIGHT = 2048; { added to SE_CALC_RISE/SET }
  SE_BIT_NAUTIC          = 2048; { added to SE_CALC_RISE/SET }
  SE_BIT_ASTRO_TWILIGHT  = 4096; { added to SE_CALC_RISE/SET }
  SE_BIT_ASTRO           = 4096; { added to SE_CALC_RISE/SET }
  SE_BIT_FIXED_DISC_SIZE = (16 * 1024); { neglect the effect of distance on disc size }
  SE_BIT_HINDU_RISING    = 896;  {(SE_BIT_DISC_CENTER|SE_BIT_NO_REFRACTION|SE_BIT_GEOCTR_NO_ECL_LAT)}

  { Bits for data conversion with swe_azalt() and swe_azalt_rev() }
  SE_ECL2HOR = 0;
  SE_EQU2HOR = 1;
  SE_HOR2ECL = 0;
  SE_HOR2EQU = 1;

  { For swe_refrac() }
  SE_TRUE_TO_APP = 0;
  SE_APP_TO_TRUE = 1;

  { Divers
    * only used for experimenting with various JPL ephemeris files
    * which are available at Astrodienst's internal network
  }
  SE_DE_NUMBER    = 431;
  SE_FNAME_DE200  = 'de200.eph';
  SE_FNAME_DE403  = 'de403.eph';
  SE_FNAME_DE404  = 'de404.eph';
  SE_FNAME_DE405  = 'de405.eph';
  SE_FNAME_DE406  = 'de406.eph';
  SE_FNAME_DE431  = 'de431.eph';
  SE_FNAME_DFT    = SE_FNAME_DE431;
  SE_FNAME_DFT2   = SE_FNAME_DE406;
  SE_STARFILE_OLD = 'fixstars.cat';
  SE_STARFILE     = 'sefstars.txt';
  SE_ASTNAMFILE   = 'seasnam.txt';
  SE_FICTFILE     = 'seorbe1.txt';

  SE_EPHE_PATH: Ansistring = '\\sweph\\ephe\\';

  { defines for function swe_split_deg() (in swephlib.c) }
  SE_SPLIT_DEG_ROUND_SEC = 1;
  SE_SPLIT_DEG_ROUND_MIN = 2;
  SE_SPLIT_DEG_ROUND_DEG = 4;
  SE_SPLIT_DEG_ZODIACAL  = 8;
  SE_SPLIT_DEG_NAKSHATRA = 1024;
  SE_SPLIT_DEG_KEEP_SIGN = 16;
  { don't round to next sign, e.g. 29.9999999 will be rounded
    to 29°59'59" (or 29°59' or 29°) }
  SE_SPLIT_DEG_KEEP_DEG = 32;
  { don't round to next degree, e.g. 13.9999999 will be rounded
    to 13°59'59" (or 13°59' or 13°) }

  { for heliacal functions }
  SE_HELIACAL_RISING    = 1;
  SE_HELIACAL_SETTING   = 2;
  SE_MORNING_FIRST      = 1; // SE_HELIACAL_RISING
  SE_EVENING_LAST       = 2; // SE_HELIACAL_SETTING
  SE_EVENING_FIRST      = 3;
  SE_MORNING_LAST       = 4;
  SE_ACRONYCHAL_RISING  = 5; // still not implemented
  SE_COSMICAL_SETTING   = 6; // still not implemented
  SE_ACRONYCHAL_SETTING = 6; // SE_COSMICAL_SETTING

  SE_HELFLAG_LONG_SEARCH     = 128;
  SE_HELFLAG_HIGH_PRECISION  = 256;
  SE_HELFLAG_OPTICAL_PARAMS  = 512;
  SE_HELFLAG_NO_DETAILS      = 1024;
  SE_HELFLAG_SEARCH_1_PERIOD = 2048;
  SE_HELFLAG_VISLIM_DARK     = 4096;
  SE_HELFLAG_VISLIM_NOMOON   = 8192;
  SE_HELFLAG_VISLIM_PHOTOPIC = 16384;
  SE_HELFLAG_VISLIM_SCOTOPIC = 32768;
  SE_HELFLAG_AV              = 65536;
  SE_HELFLAG_AVKIND_VR       = 65536;
  SE_HELFLAG_AVKIND_PTO      = 131072;
  SE_HELFLAG_AVKIND_MIN7     = 262144;
  SE_HELFLAG_AVKIND_MIN9     = 491520;
  SE_HELFLAG_AVKIND          = 950272;
  // (SE_HELFLAG_AVKIND_VR|SE_HELFLAG_AVKIND_PTO|SE_HELFLAG_AVKIND_MIN7|SE_HELFLAG_AVKIND_MIN9)
  TJD_INVALID       = 99999999.0;
  SIMULATE_VICTORVB = 1;
  
  SE_HELIACAL_LONG_SEARCH     = 128;
  SE_HELIACAL_HIGH_PRECISION  = 256;
  SE_HELIACAL_OPTICAL_PARAMS  = 512;
  SE_HELIACAL_NO_DETAILS      = 1024;
  SE_HELIACAL_SEARCH_1_PERIOD = 2048;
  SE_HELIACAL_VISLIM_DARK     = 4096;
  SE_HELIACAL_VISLIM_NOMOON   = 8192;
  SE_HELIACAL_VISLIM_PHOTOPIC = 16384;
  SE_HELIACAL_AVKIND_VR       = 32768;
  SE_HELIACAL_AVKIND_PTO      = 65536;
  SE_HELIACAL_AVKIND_MIN7     = 131072;
  SE_HELIACAL_AVKIND_MIN9     = 262144;
  SE_HELIACAL_AVKIND          = 491520;

  SE_PHOTOPIC_FLAG  = 0;
  SE_SCOTOPIC_FLAG  = 1;
  SE_MIXEDOPIC_FLAG = 2;

{* for swe_set_tid_acc() and ephemeris-dependent delta t:
 * intrinsic tidal acceleration in the mean motion of the moon,
 * not given in the parameters list of the ephemeris files but computed
 * by Chapront/Chapront-Touzé/Francou A&A 387 (2002), p. 705.
 *}
 SE_TIDAL_DE200          =-23.8946;
 SE_TIDAL_DE403          =-25.580;  // was (-25.8) until V. 1.76.2 */
 SE_TIDAL_DE404          =-25.580;  // was (-25.8) until V. 1.76.2 */
 SE_TIDAL_DE405          =-25.826;  // was (-25.7376) until V. 1.76.2 */
 SE_TIDAL_DE406          =-25.826;  // was (-25.7376) until V. 1.76.2 */
 SE_TIDAL_DE421          =-25.85;   // JPL Interoffice Memorandum 14-mar-2008 on DE421 Lunar Orbit */
 SE_TIDAL_DE422          =-25.85;   // JPL Interoffice Memorandum 14-mar-2008 on DE421 (sic!) Lunar Orbit */
 SE_TIDAL_DE430          =-25.82;   // JPL Interoffice Memorandum 9-jul-2013 on DE430 Lunar Orbit */
 SE_TIDAL_DE431          =-25.80;   // IPN Progress Report 42-196 • February 15, 2014, p. 15; was (-25.82) in V. 2.00.00 */
 SE_TIDAL_26             =-26.0;
 SE_TIDAL_DEFAULT        =SE_TIDAL_DE431;
 SE_TIDAL_AUTOMATIC      =999999;
 SE_TIDAL_MOSEPH         =SE_TIDAL_DE404;
 SE_TIDAL_SWIEPH         =SE_TIDAL_DEFAULT;
 SE_TIDAL_JPLEPH         =SE_TIDAL_DEFAULT;

  //* for function swe_set_delta_t_userdef() */
 SE_DELTAT_AUTOMATIC    = (-1E-10);

 SE_MODEL_DELTAT         =0;
 SE_MODEL_PREC_LONGTERM  =1;
 SE_MODEL_PREC_SHORTTERM =2;
 SE_MODEL_NUT            =3;
 SE_MODEL_BIAS           =4;
 SE_MODEL_JPLHOR_MODE    =5;
 SE_MODEL_JPLHORA_MODE   =6;
 SE_MODEL_SIDT           =7;
 NSE_MODELS              =8;

{ precession models }
 SEMOD_NPREC               = 10;
 SEMOD_PREC_IAU_1976       = 1;
 SEMOD_PREC_LASKAR_1986    = 2;
 SEMOD_PREC_WILL_EPS_LASK  = 3;
 SEMOD_PREC_WILLIAMS_1994  = 4;
 SEMOD_PREC_SIMON_1994     = 5;
 SEMOD_PREC_IAU_2000       = 6;
 SEMOD_PREC_BRETAGNON_2003 = 7;
 SEMOD_PREC_IAU_2006       = 8;
 SEMOD_PREC_VONDRAK_2011   = 9;
 SEMOD_PREC_OWEN_1990      = 10;
 SEMOD_PREC_DEFAULT   =    SEMOD_PREC_VONDRAK_2011;
{* former implementations of the used 
 * IAU 1976, 2000 and 2006 for a limited time range
 * in combination with a different model for 
 * long term precession. 
   SEMOD_PREC_DEFAULT_SHORT = SEMOD_PREC_IAU_2000;
 *}
 SEMOD_PREC_DEFAULT_SHORT = SEMOD_PREC_VONDRAK_2011;

{ nutation models }
 SEMOD_NNUT		         =4;
 SEMOD_NUT_IAU_1980          =1;
 SEMOD_NUT_IAU_CORR_1987     =2; // Herring's (1987) corrections to IAU 1980 
 // nutation series. AA (1996) neglects them.
 SEMOD_NUT_IAU_2000A         =3; // very time consuming ! */
 SEMOD_NUT_IAU_2000B         =4; // fast, but precision of milli-arcsec */
 SEMOD_NUT_DEFAULT           =SEMOD_NUT_IAU_2000B;  // fast, but precision of milli-arcsec */

{ methods for sidereal time }
 SEMOD_NSIDT		         =4;
 SEMOD_SIDT_IAU_1976         =1;
 SEMOD_SIDT_IAU_2006         =2;
 SEMOD_SIDT_IERS_CONV_2010   =3;
 SEMOD_SIDT_LONGTERM         =4;
// SEMOD_SIDT_PREC_MODEL       =3;
 SEMOD_SIDT_DEFAULT          =SEMOD_SIDT_LONGTERM;
// SEMOD_SIDT_DEFAULT          =SEMOD_SIDT_IERS_CONV_2010;

{ frame bias methods }
 SEMOD_NBIAS		         =3;
 SEMOD_BIAS_NONE             =1;  //* ignore frame bias */
 SEMOD_BIAS_IAU2000          =2;  //* use frame bias matrix IAU 2000 */
 SEMOD_BIAS_IAU2006          =3;  //* use frame bias matrix IAU 2000 */
 SEMOD_BIAS_DEFAULT          =SEMOD_BIAS_IAU2006;

{ methods of JPL Horizons (iflag & SEFLG_JPLHOR), 
 * using daily dpsi, deps;  see explanations below }
 SEMOD_NJPLHOR		         =2;
 SEMOD_JPLHOR_LONG_AGREEMENT  =1; // daily dpsi and deps from file are 
                                 // limited to 1962 - today. JPL uses the
				                         // first and last value for all  dates 
				                         // beyond this time range. 
 SEMOD_JPLHOR_NOT_EXTENDED   =2; // outside the available time range 
                                 // 1962 - today default to SEFLG_JPLHOR_APROX
 SEMOD_JPLHOR_DEFAULT     =   SEMOD_JPLHOR_LONG_AGREEMENT;
{* Note, currently this is the only option for SEMOD_JPLHOR..*/
 * SEMOD_JPLHOR_LONG_AGREEMENT, if combined with SEFLG_JPLHOR provides good 
 * agreement with JPL Horizons for 9998 BC (-9997) until 9999 CE. 
 * - After 20-jan-1962 until today, Horizons uses correct dpsi and deps. 
 * - For dates before that, it uses dpsi and deps of 20-jan-1962, which 
 *   provides a continuous ephemeris, but does not make sense otherwise.
 * - Before 1.1.1799 and after 1.1.2202, the precession model Owen 1990
 *   is used, as in Horizons. 
 * An agreement with Horizons to a couple of milli arc seconds is achieved 
 * for the whole time range of Horizons. (BC 9998-Mar-20 to AD 9999-Dec-31 TT.)
 *}

{* methods of approximation of JPL Horizons (iflag & SEFLG_JPLHORA), 
 * without dpsi, deps; see explanations below }
 SEMOD_NJPLHORA	 =3;
 SEMOD_JPLHORA_1     =1;
 SEMOD_JPLHORA_2     =2;
 SEMOD_JPLHORA_3     =3;
 SEMOD_JPLHORA_DEFAULT  =   SEMOD_JPLHORA_3;
{* With SEMOD_JPLHORA_1, planetary positions are always calculated 
 * using a recent precession/nutation model. Frame bias matrix is applied 
 * with some correction to RA and another correction is added to epsilon.
 * This provides a very good approximation of JPL Horizons positions. 
 * With SEMOD_JPLHORA_2, frame bias as r$ecommended by IERS Conventions 2003 
 * and 2010 is *not* applied. Instead, dpsi_bias and deps_bias are added to 
 * nutation. This procedure is found in some older astronomical software.
 * Equatorial apparent positions will be close to JPL Horizons 
 * (within a few mas) beetween 1962 and current years. Ecl. longitude 
 * will be good, latitude bad. 
 * With SEMOD_JPLHORA_3 works like SEMOD_JPLHORA_3 after 1962, but like
 * SEFLG_JPLHOR before that. This allows EXTREMELY good agreement with JPL 
 * Horizons over its whole time range.
 *}
 
 SEMOD_NDELTAT		                     =5;
 SEMOD_DELTAT_STEPHENSON_MORRISON_1984   =1;
 SEMOD_DELTAT_STEPHENSON_1997            =2;
 SEMOD_DELTAT_STEPHENSON_MORRISON_2004   =3;
 SEMOD_DELTAT_ESPENAK_MEEUS_2006         =4;
 SEMOD_DELTAT_STEPHENSON_ETC_2016        =5;
 SEMOD_DELTAT_DEFAULT =  SEMOD_DELTAT_STEPHENSON_ETC_2016;
// SEMOD_DELTAT_DEFAULT =  SEMOD_DELTAT_ESPENAK_MEEUS_2006;

  SEHOUSE_SYSTEM: array [0 .. 23] of AnsiChar = ('P', 'K', 'O', 'R', 'C', 'E', 'V', 'X', 'H', 'T',
    'M', 'B', 'G', 'U', 'W', 'Y', 'D', 'N', 'L', 'Q', 'S', 'F', 'I', 'i');
  { **'P'     Placidus
    **'A' or 'E'      Equal (cusp 1 is Ascendant)
    **'B'     Alcabitius
    **'C'     Campanus
    **'D'     equal (MC)
    **'F'     Carter poli-equ.
    **'G'     36 Gauquelin sectors
    **'H'     azimuthal or horizontal system
    **'I'     Sunshine
    **'i'     Sunshine/alt.
    **'K'     Koch
    **'L'     Pullen SD
    **'M'     Morinus
    **'N'     equal/1=Aries
    **'O'     Porphyrius
    **'Q'     Pullen SR
    **'R'     Regiomontanus
    **'S'     Sripati
    **'T'     Polich/Page ("topocentric" system)
    **'U'     Krusinski-Pisa-Goelzer
    **'V'     Vehlow equal (Asc. in middle of house 1)
    **'W'     equal, whole sign
    **'X'     axial rotation system  Meridian houses
    **'Y'     APC houses
  }

  // ******My constants
  SE_NESSUS = 17066;

  // ************************
  // exported functions
  // ************************

function swe_heliacal_ut(tjdstart_ut: Double; { start date (JD) for event search }
  var geopos: Double;
  { array of 3 doubles geogr. longitude, latitude, eye height (m above sea level) }
  var datm: Double; { array of 4 doubles atm. pressure, temperature, RH, and VR
    - pressure     atmospheric pressure (mbar, =hPa) default 1013.25hPa
    - temperature  deg C, default 15 deg C (if at
    If both attemp and atpress are 0, a temperature and
    atmospheric pressure are estimated from the above-mentioned
    default values and the height above sea level.
    - RH  relative humidity in %
    - VR              VR>=1: the Meteorological range: default 40 km }
  var dobs: Double; { array of doubles
    dobs[0]: age of observer in years (default = 36)
    dobs[1]: Snellen ratio of observers eyes (default = 1 = normal)
    The following parameters are only relevant if the flag SE_HELFLAG_OPTICAL_PARAMS is set:
    dobs[2]: 0 = monocular, 1 = binocular (actually a boolean)
    dobs[3]: telescope magnification: 0 = default to naked eye (binocular), 1 = naked eye
    dobs[4]: optical aperture (telescope diameter) in mm
    dobs[5]: optical transmission }

  ObjectName: PansiChar; { name string of fixed star or planet }
  TypeEvent: Longint;
  { event_type = SE_HELIACAL_RISING (1): morning first (exists for all visible planets and stars)
    event_type = SE_HELIACAL_SETTING (2): evening last (exists for all visible planets and stars)
    event_type = SE_EVENING_FIRST (3): evening first (exists for Mercury, Venus, and the Moon)
    event_type = SE_MORNING_LAST (4): morning last (exists for Mercury, Venus, and the Moon) }
  helflag: Longint;
  { helflag contains ephemeris flag, like iflag in swe_calc() etc. In addition it can contain the following bits:
    SE_HELFLAG_LONG_SEARCH (128): A heliacal event is searched until found.
    If this bit is NOT set and no event is found within 5 synodic periods, the function stops
    searching and  returns ERR.
    SE_HELFLAG_HIGH_PRECISION (256): More rigorous but also slower algorithms are used
    SE_HELFLAG_OPTICAL_PARAMS (512): Use this with calculations for optical instruments.
    Unless this bit is set, the values of dobs[2-5] are ignored.
    SE_HELFLAG_NO_DETAILS (1024): provide the date, but not details like visibility start, optimum, and end.
    This bit makes the program a bit faster. }
  var dret: Double;
  { dret[0]: start visibility (Julian day number)
    dret[1]: optimum visibility (Julian day number)
    dret[2]: end of visibility (Julian day number) }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_heliacal_ut@40';

function swe_heliacal_pheno_ut(tjd_ut: Double; var geopos: Double; var datm: Double;
  var dobs: Double; ObjectName: PansiChar; TypeEvent: Longint; helflag: Longint; var darr: Double;
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_heliacal_pheno_ut@40';

function swe_vis_limit_mag(tjdut: Double; var geopos: Double; var datm: Double; var dobs: Double;
  ObjectName: PansiChar; helflag: Longint; var dret: Double; sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_vis_limit_mag@36';

{ the following are secret, for Victor Reijs' }
function swe_heliacal_angle(tjdut: Double; var dgeo: Double; var datm: Double; var dobs: Double;
  helflag: Longint; mag: Double; azi_obj: Double; azi_sun: Double; azi_moon: Double;
  alt_moon: Double; var dret: Double; sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_heliacal_angle@72';

function swe_topo_arcus_visionis(tjdut: Double; var dgeo: Double; var datm: Double;
  var dobs: Double; helflag: Longint; mag: Double; azi_obj: Double; alt_obj: Double;
  azi_sun: Double; azi_moon: Double; alt_moon: Double; var dret: Double; sErr: PansiChar): Longint;
  STDCALL; EXTERNAL DLL_NAME NAME '_swe_topo_arcus_visionis@80';

{ the following is secret, for Dieter, allows to test old models of
  precession, nutation, etc. Search for SE_MODEL_... in this file  }
procedure swe_set_astro_models(var imodel: Longint);
  STDCALL; EXTERNAL DLL_NAME NAME '_swe_set_astro_models@4';

// ************************
// exports from sweph.c
// ************************

function swe_version(s: PAnsiChar): PAnsiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_version@4';

function swe_get_library_path(s: PansiChar): PansiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_get_library_path@4';

{ Calculate positions of planets, asteroids, lunar nodes and apogees }
function swe_calc(tjd: Double; { Julian day, Ephemeris Time }
  ipl: Integer; { planet number }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_calc@24';

function swe_calc_ut(tjd: Double; { Julian day, Universal Time }
  ipl: Integer; { planet number }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_calc_ut@24';

function swe_fixstar(star: PansiChar; { star name 40 bytes }
  tjd: Double; { Julian day in Ephemeris Time }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_fixstar@24';
  {return -1 if star not found}

function swe_fixstar2(star: PansiChar; { star name 40 bytes }
  tjd: Double; { Julian day in Ephemeris Time }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_fixstar2@24';
  {return -1 if star not found}

function swe_fixstar_ut(star: PansiChar; { star name 40 bytes }
  tjd: Double; { Julian day in Universal Time }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_fixstar_ut@24';
  {return -1 if star not found}

function swe_fixstar2_ut(star: PansiChar; { star name 40 bytes }
  tjd: Double; { Julian day in Universal Time }
  iflag: Longint; { flag bits }
  var xx: Double;
  { first of 6 doubles : longitude, latitude, distance,long. speed, lat. speed, dist. speed }
  sErr: PansiChar) { 256 bytes for Error-String }
  : Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_fixstar2_ut@24';
  {return -1 if star not found}

function swe_fixstar_mag(star: PansiChar; mag: Double; sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_fixstar_mag@12';

function swe_fixstar2_mag(star: PansiChar; mag: Double; sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_fixstar2_mag@12';
  
{ Close Swiss Ephemeris. Release resources used by the Swiss Ephemeris DLL.
  i.e. - the ephemeris path set by swe_set_ephe_path()
  - the JPL file name set by swe_set_jpl_file()
  - the geographical location set by swe_set_topo() for topocentric planetary positions
  - the sidereal mode set by swe_set_sid_mode() for sidereal planetary positions }
procedure swe_close; STDCALL; EXTERNAL DLL_NAME NAME '_swe_close@0';

{ Set path for ephemeris files }
procedure swe_set_ephe_path(path: PansiChar); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_ephe_path@4';

{ set file name of JPL file }
procedure swe_set_jpl_file(fname: PansiChar); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_jpl_file@4';

{ get planet name }
function swe_get_planet_name(ipl: Integer; pname: PansiChar): PansiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_get_planet_name@8';

{ set geographic position of observer }
procedure swe_set_topo(geolon: Double; { geographic longitude }
  geolat: Double; { geographic latitude }
  height: Double); { altitude above sea }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_set_topo@24';

{ set sidereal mode }
procedure swe_set_sid_mode(sid_mode: Longint; t0: Double; { reference epoch }
  ayan_t0: Double); { initial ayanamsha at t0 }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_set_sid_mode@20';

{ get ayanamsa }
function swe_get_ayanamsa(tjd_et: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_get_ayanamsa@8';

function swe_get_ayanamsa_ut(tjd_ut: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_get_ayanamsa_ut@8';

function swe_get_ayanamsa_name(isidmod: Longint): PansiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_get_ayanamsa_name@4';

function swe_get_ayanamsa_ex(tjd_et: Double; iflag: Longint; var daya: double; sErr: PansiChar): Longint; STDCALL; 
  EXTERNAL DLL_NAME NAME '_swe_get_ayanamsa_ex@20';

function swe_get_ayanamsa_ex_ut(tjd_ut: Double; iflag: Longint; var daya: double; sErr: PansiChar): Longint; STDCALL; 
  EXTERNAL DLL_NAME NAME '_swe_get_ayanamsa_ex_ut@20';

// ************************
// exports from swedate.c
// ************************

function swe_date_conversion(year: Integer; month: Integer; day: Integer; utime: Double;
  { hours (decimal, with fraction) }
  c: AnsiChar; { 'g'regorian or 'j'ulian }
  var tjd: Double): Integer; STDCALL; EXTERNAL DLL_NAME NAME '_swe_date_conversion@28';

function swe_julday(year: Integer; month: Integer; day: Integer; hour: Double; gregflag: Integer)
{ Gregorian calendar: 1, Julian calendar: 0 }
  : Double; STDCALL; EXTERNAL DLL_NAME NAME '_swe_julday@24';

procedure swe_revjul(tjd: Double; gregflag: Integer; { Gregorian calendar: 1, Julian calendar: 0 }
  var year: Integer; var month: Integer; var day: Integer; var hour: Double); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_revjul@28';

function swe_utc_to_jd(iyear: Longint; { year }
  imonth: Longint; { month }
  iday: Longint; { day }
  ihour: Longint; { hour }
  imin: Longint; { minute }
  dsec: Double; { second }
  gregflag: Longint; { flags }
  var dret: Double; { ret }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_utc_to_jd@40';

procedure swe_jdet_to_utc(tjd_et: Double; { Julian day, Ephemeris Time }
  gregflag: Longint; { flag }
  var iyear: Longint; { year }
  var imonth: Longint; { month }
  var iday: Longint; { day }
  var ihour: Longint; { hour }
  var imin: Longint; { minute }
  var dsec: Double); { second }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_jdet_to_utc@36';

procedure swe_jdut1_to_utc(tjd_ut: Double; { Julian day, Universal Time }
  gregflag: Longint; { flag }
  var iyear: Longint; { year }
  var imonth: Longint; { month }
  var iday: Longint; { day }
  var ihour: Longint; { hour }
  var imin: Longint; { minute }
  var dsec: Double); { second }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_jdut1_to_utc@36';

procedure swe_utc_time_zone(iyear: Longint; imonth: Longint; iday: Longint; ihour: Longint;
  imin: Longint; dsec: Double; d_timezone: Double; var iyear_out: Longint; { year }
  var imonth_out: Longint; { month }
  var iday_out: Longint; { day }
  var ihour_out: Longint; { hour }
  var imin_out: Longint; { minute }
  var dsec_out: Double); { second }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_utc_time_zone@60';

// ************************
// exports from swehouse.c
// ************************

{ get house name }
function swe_house_name(ihsy: Integer): PansiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_house_name@4';

function swe_houses(tjdut: Double; geolat: Double; geolon: Double; hsys: AnsiChar;
  var hcusp0: Double; var ascmc0: Double): Integer; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_houses@36';

function swe_houses_ex(tjdut: Double; iflag: Longint; { 0 or SEFLG_SIDEREAL or
    SEFLG_RADIANS }
  geolat: Double; geolon: Double; hsys: AnsiChar; var hcusp0: Double; var ascmc0: Double): Integer;
  STDCALL; EXTERNAL DLL_NAME NAME '_swe_houses_ex@40';

function swe_houses_armc(armc: Double; geolat: Double; eps: Double; hsys: AnsiChar;
  var hcusp0: Double; var ascmc0: Double): Integer; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_houses_armc@36';

function swe_house_pos(armc: Double; geolon: Double; eps: Double; hsys: AnsiChar; var xpin: Double;
  { 2 doubles: ecl. longitude and latitude }
  sErr: PansiChar): Double; STDCALL; EXTERNAL DLL_NAME NAME '_swe_house_pos@36';

// ************************
// exports from swecl.c
// ************************

function swe_gauquelin_sector(t_ut: Double; ipl: Longint; starname: PansiChar; iflag: Longint;
  imeth: Longint; var geopos: Double; atpress: Double; attemp: Double; var dgsect: Double;
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_gauquelin_sector@52';

{ Eclipse calculations }

{ Computes geographic location and attributes of solar eclipse at a given tjd }
function swe_sol_eclipse_where(tjd_ut: Double; ifl: Longint; { Ephemeris flag }
  var geopos: Double; { array of 2 doubles : geo longitude, geo latitude }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_sol_eclipse_where@24';

function swe_lun_occult_where(tjd: Double; ipl: Longint; starname: PansiChar; ifl: Longint;
  var geopos: Double; { array of 2 doubles : geo longitude, geo latitude }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_lun_occult_where@32';

{ Computes attributes of a solar eclipse for given tjd, geolon, geolat }
function swe_sol_eclipse_how(tjd_ut: Double; { time, Jul. day UT }
  ifl: Longint; { ephemeris flag }
  var geopos: Double; { geo longitude, latitude, height above sea }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_sol_eclipse_how@24';

{ Finds time of next local eclipse }
function swe_sol_eclipse_when_loc(tjd: Double; { start date for search, Jul. day UT }
  ifl: Longint; { Ephemeris flag }
  var geopos: Double; { array of 3 doubles : geo longitude, latitude, height above sea }
  var tret: Double; { first of 10 doubles }
  var attr: Double; { first of 20 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_sol_eclipse_when_loc@32';

function swe_lun_occult_when_loc(tjd_start: Double; ipl: Longint; starname: PansiChar; ifl: Longint;
  var geopos: Double; { array of 3 doubles : geo longitude, latitude, height above sea }
  var tret: Double; { first of 10 doubles }
  var attr: Double; { first of 20 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_lun_occult_when_loc@40';

{ Finds time of next eclipse globally }
function swe_sol_eclipse_when_glob(tjd: Double; { start date for search, Jul. day UT }
  ifl: Longint; { Ephemeris flag }
  ifltype: Longint; { eclipse type wanted: SE_ECL_TOTAL etc }
  var tret: Double; { first of 10 doubles }
  { var attr: Double; } { first of 20 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_sol_eclipse_when_glob@28';

{ finds time of next occultation globally }
function swe_lun_occult_when_glob(tjd_start: Double; ipl: Longint; starname: PansiChar;
  { must be NULL or ”” if not a star }
  ifl: Longint; ifltype: Longint; var geopos: Double;
  { array of 3 doubles : geo longitude, latitude, height above sea }
  var tret: Double; { first of 10 doubles }
  var attr: Double; { first of 20 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_lun_occult_when_glob@36';

{ computes attributes of a lunar eclipse for given tjd }
function swe_lun_eclipse_how(tjd_ut: Double; { time, Jul. day UT }
  ifl: Longint; { Ephemeris flag }
  var geopos: Double; { array of 3 doubles : longitude, latitude, height above sea }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_lun_eclipse_how@24';

function swe_lun_eclipse_when(tjd: Double; { start date for search, Jul. day UT }
  ifl: Longint; { Ephemeris flag }
  ifltype: Longint; { eclipse type wanted: SE_ECL_TOTAL etc }
  var tret: Double; { first of 10 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_lun_eclipse_when@28';

function swe_lun_eclipse_when_loc(tjd: Double; { start date for search, Jul. day UT }
  ifl: Longint; { Ephemeris flag }
  var geopos: Double; { array of 3 doubles : longitude, latitude, height above sea }
  var tret: Double; { first of 10 doubles }
  var attr: Double; { first of 10 doubles }
  backward: Longint; { TRUE, if backward search }
  sErr: PansiChar): Longint; STDCALL;  EXTERNAL DLL_NAME NAME '_swe_lun_eclipse_when_loc@32';

{ planetary phenomena }

function swe_pheno(tjd_et: Double; { time Jul. Day ET }
  ipl: Longint; { planet number }
  ifl: Longint; { Ephemeris flag }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_pheno@24';

function swe_pheno_ut(tjd_ut: Double; { time Jul. Day UT }
  ipl: Longint; { planet number }
  ifl: Longint; { Ephemeris flag }
  var attr: Double; { first of 20 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_pheno_ut@24';

function swe_refrac(inalt: Double; atpress: Double;
  { atmospheric pressure in mbar (hPa) }
  attemp: Double; { atmospheric temperature in degrees Celsius }
  ifl: Longint) { either SE_TRUE_TO_APP or SE_APP_TO_TRUE }
  : Double; STDCALL; EXTERNAL DLL_NAME NAME '_swe_refrac@28';

function swe_refrac_extended(inalt: Double; geoalt: Double; atpress: Double; attemp: Double;
  lapse_rate: Double; calc_flag: Longint; var dret: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_refrac_extended@48';

procedure swe_set_lapse_rate(lapse_rate: Double); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_lapse_rate@8';

procedure swe_azalt(tjd_ut: Double; { UT }
  cflag: Longint; { SE_ECL2HOR or SE_EQU2HOR }
  var geopos: Double; { array of 3 doubles : longitude, latitude, height above sea }
  atpress: Double; { atmospheric pressure in mbar (hPa) }
  attemp: Double; { atmospheric temperature in degrees Celsius }
  var xin: Double; { array of 3 doubles : ecliptical or equatorial coordinates }
  var xaz: Double); { array of 3 doubles : azimuth, true altitude, apparent altitude }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_azalt@40';

procedure swe_azalt_rev(tjd_ut: Double; { UT }
  cflag: Longint; { SE_HOR2ECL or SE_HOR2EQU }
  var geopos: Double; { array of 3 doubles : longitude, latitude, height above sea }
  var xin: Double; { array of 2 doubles : azimuth and true altitude }
  var xout: Double); { array of 3 doubles : ecliptical or equatorial coordinates: x,y,z }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_azalt_rev@24';

function swe_rise_trans(tjd_ut: Double; { Search after this time (UT) }
  ipl: Longint; { planet number , if planet or moon }
  starname: PansiChar; { star name, if star }
  ifl: Longint; { Ephemeris flag }
  rsmi: Longint; { integer specifying that rise, set, orone of the two meridian transits is wanted }
  var geopos: Double; { array of 3 doubles : geo longitude, latitude, height above sea of observer }
  atpress: Double; { atmospheric pressure in mbar (hPa) }
  attemp: Double; { atmospheric temperature in degrees Celsius }
  var tret: Double; { first of 10 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_rise_trans@52';

function swe_rise_trans_true_hor(tjd_ut: Double; { Search after this time (UT) }
  ipl: Longint; { planet number , if planet or moon }
  starname: PansiChar; { star name, if star }
  ifl: Longint; { Ephemeris flag }
  rsmi: Longint; { integer specifying that rise, set, orone of the two meridian transits is wanted }
  var geopos: Double; { array of 3 doubles : geo longitude, latitude, height above sea of observer }
  atpress: Double; { atmospheric pressure in mbar (hPa) }
  attemp: Double; { atmospheric temperature in degrees Celsius }
  horhgt: Double; {height of local horizon in deg at the point where the body rises or sets}
  var tret: Double; { first of 10 doubles }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_rise_trans@60';

{ Compute planetary nodes and apsides }
function swe_nod_aps(tjd_et: Double; { Julian day, Ephemeris Time }
  ipl: Longint; { planet number }
  iflag: Longint; { flag bits }
  method: Longint; { calculation method }
  var xnasc: Double; { First of 6 doubles for ascending node }
  var xndsc: Double; { First of 6 doubles for descending node }
  var xperi: Double; { First of 6 doubles for perihelion }
  var xaphe: Double; { First of 6 doubles for aphelion }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_nod_aps@40';

function swe_nod_aps_ut(tjd_ut: Double; { Julian day, Universal Time }
  ipl: Longint; { planet number }
  iflag: Longint; { flag bits }
  method: Longint; { calculation method }
  var xnasc: Double; { First of 6 doubles for ascending node }
  var xndsc: Double; { First of 6 doubles for descending node }
  var xperi: Double; { First of 6 doubles for perihelion }
  var xaphe: Double; { First of 6 doubles for aphelion }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_nod_aps_ut@40';

{********
ext_def (int32) swe_get_orbital_elements(
  double tjd_et, int32 ipl, int32 iflag, double *dret, char *serr);
ext_def (int32) swe_orbit_max_min_true_distance(double tjd_et, int32 ipl, int32 iflag, double *dmax, double *dmin, double *dtrue, char *serr);
********}
function swe_get_orbital_elements(tjd_et: Double; { Julian day, Ephemeris Time }
  ipl: Longint; { planet number }
  iflag: Longint; { flag bits }
  var dret: Double; { First of ?? doubles for  }
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_get_orbital_elements@24';

function swe_orbit_max_min_true_distance(tjd_et: Double; { Julian day, Ephemeris Time }
  ipl: Longint; { planet number }
  iflag: Longint; { flag bits }
  var dmax: Double; 
  var dmin: Double; 
  var dtrue: Double; 
  sErr: PansiChar): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_orbit_max_min_true_distance@32';

// ************************
// exports from swephlib.c
// ************************

{ delta T }
function swe_deltat(tjd: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_deltat@8';

function swe_deltat_ex(tjd: Double; iflag: Longint;sErr: PansiChar): Double; STDCALL; 
  EXTERNAL DLL_NAME NAME '_swe_deltat_ex@16';
  
{ Equation of time }
function swe_time_equ(tjd_et: Double; var e: Double;
  { local apparent - local mean time }
  sErr: PansiChar) { Error-String }
  : Integer; STDCALL; EXTERNAL DLL_NAME NAME '_swe_time_equ@16';
  
function swe_lmt_to_lat(tjd_lmt: Double; geolon: Double; var tjd_lat: Double;
  sErr: PansiChar) { Error-String }
  : Integer; STDCALL; EXTERNAL DLL_NAME NAME '_swe_lmt_to_lat@24';

function swe_lat_to_lmt(tjd_lat: Double; geolon: Double; var tjd_lmt: Double;
  sErr: PansiChar) { Error-String }
  : Integer; STDCALL; EXTERNAL DLL_NAME NAME '_swe_lat_to_lmt@24';
 
{ sideral time }
function swe_sidtime(tjdut: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_sidtime@8';

function swe_sidtime0(tjdut: Double; eps_true: Double; nut_long: Double): Integer; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_sidtime0@24';

{ interpolate nut }
procedure swe_set_interpolate_nut(do_interpolate: LongBool); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_interpolate_nut@4';

{ Coordinate transformation, from ecliptic to equator (-ve eps) or vice-versa }
procedure swe_cotrans(var xpin: Double; { long., lat., dist }
  var xpout: Double; eps: Double); { obliquity }
STDCALL; EXTERNAL DLL_NAME NAME '_swe_cotrans@16';

{ Coordinate transformation of position and speed }
procedure swe_cotrans_sp(var xpin: Double; var xpout: Double; eps: Double); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_cotrans_sp@16';

{ tidal acceleration to be used in swe_deltat() }
function swe_get_tid_acc { (t_acc: Double) }
  : Double; STDCALL; EXTERNAL DLL_NAME NAME '_swe_get_tid_acc@0';

procedure swe_set_tid_acc(t_acc: Double); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_tid_acc@8';

{********
/* set a user defined delta t to be returned by functions
 * swe_deltat() and swe_deltat_ex() */
ext_def (void) swe_set_delta_t_userdef(double dt);
********}
procedure swe_set_delta_t_userdef(dt: Double); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_set_delta_t_userdef@8';

function swe_degnorm(x: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_degnorm@8';

function swe_radnorm(x: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_radnorm@8';

function swe_rad_midp(x1: Double; x0: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_rad_midp@16';

function swe_deg_midp(x1: Double; x0: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_deg_midp@16';

procedure swe_split_deg(ddeg: Double; roundflag: Longint; var ideg: Longint; var imin: Longint;
  var isec: Longint; var dsecfr: Double; var isgn: Longint); STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_split_deg@32';

// *******************************************
// other functions from swephlib.c;
// they are not needed for Swiss Ephemeris,
// but may be useful to former Placalc users.
// *******************************************

{ normalize argument into interval [0..DEG360] }
function swe_csnorm(p: Longint): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_csnorm@4';

{ distance in centisecs p1 - p2 normalized to [0..360[ }
function swe_difcsn(p1: Longint; p2: Longint): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_difcsn@8';

function swe_difdegn(p1: Double; p2: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_difdegn@16';

{ distance in centisecs p1 - p2 normalized to [-180..180[ }
function swe_difcs2n(p1: Longint; p2: Longint): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_difcs2n@8';

function swe_difdeg2n(p1: Double; p2: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_difdeg2n@16';

function swe_difrad2n(p1: Double; p2: Double): Double; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_difrad2n@16';

{ round second, but at 29.5959 always down }
function swe_csroundsec(x: Longint): Longint; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_csroundsec@4';

{ double to int32 with rounding, no overflow check }
function swe_d2l(x: Double): Longint; STDCALL; EXTERNAL DLL_NAME NAME '_swe_d2l@8';

{ monday = 0, ... sunday = 6 }
function swe_day_of_week(var tjd: Double): Integer; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_day_of_week@8';

function swe_cs2timestr(t: Longint; sep: Integer; suppresszero: LongBool; s: PansiChar): PansiChar;
  STDCALL; EXTERNAL DLL_NAME NAME '_swe_cs2timestr@16';

function swe_cs2lonlatstr(t: Longint; pch: AnsiChar; mch: AnsiChar; s: PansiChar): PansiChar;
  STDCALL; EXTERNAL DLL_NAME NAME '_swe_cs2lonlatstr_d@16';

function swe_cs2degstr(t: Longint; s: PansiChar): PansiChar; STDCALL;
  EXTERNAL DLL_NAME NAME '_swe_cs2degstr@8';

implementation

end.
