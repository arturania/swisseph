#include "EXTERN.h"
#include "perl.h"
#include "XSUB.h"

#include "ppport.h"

// Declarations of the Swiss Ephemeris function (reduced from the original version)
//#include "swedll32.h"
#include "swephexp.h"

// Defaulting the calendar style: From 1582/10/15 on, use gregorian
static inline int gregflag_from_date( int year, int month, int day )
{ 
  return (year<1582 ? SE_JUL_CAL :
          year>1582 ? SE_GREG_CAL :
          month<10  ? SE_JUL_CAL :
          month>10  ? SE_GREG_CAL :
          day<15    ? SE_JUL_CAL : SE_GREG_CAL);
}
#define gregflag_from_jd(x) ((x) >= 2299160.5 ? SE_GREG_CAL : SE_JUL_CAL)

MODULE = SwissEph		PACKAGE = SwissEph		

# swe_version0()
# This is a help function for function SwissEph.pm:swe_version().
# It returns the Swiss Ephemeris version.
char * 
swe_version0()
CODE:
  char s[255];
  RETVAL = swe_version(s);
OUTPUT:
  RETVAL

# swe_get_library_path()
SV*
swe_get_library_path(ipl) 
CODE:  
  char spath[255];
  RETVAL = newSVpvn("", 255);
  swe_get_library_path(spath);
  sv_setpvn(RETVAL, spath, strlen(spath)); 
OUTPUT:
  RETVAL

###############################################################3
# functions from swephlib.c
###############################################################3

# This is swe_cotrans, transforms coordinates; sample call:
# @cout = swe_cotrans(\@cin, $eps);
SV* 
swe_cotrans(xpi, eps) 
  SV* xpi
  double eps
PPCODE:
  int i;
  double in[3], out[3];
  AV* _xpi = (AV*) SvRV(xpi);
  in[0] = SvNV(*av_fetch(_xpi, 0, TRUE));
  in[1] = SvNV(*av_fetch(_xpi, 1, TRUE));
  in[2] = SvNV(*av_fetch(_xpi, 2, TRUE));
  swe_cotrans(in, out, eps);
  for (i = 0; i < 3; i++)
    XPUSHs(newSVnv(out[i]));
  PERL_UNUSED_VAR(RETVAL);

# This is swe_cotrans, transforms coordinates; sample call:
# @cout = swe_cotrans(\@cin, $eps);
SV* 
swe_cotrans_sp(xpi, eps) 
  SV* xpi
  double eps
PPCODE:
  int i;
  double in[6], out[6];
  AV* _xpi = (AV*) SvRV(xpi);
  for (i = 0; i < 6; i++)
    in[i] = SvNV(*av_fetch(_xpi, i, TRUE));
  swe_cotrans_sp(in, out, eps);
  for (i = 0; i < 6; i++)
    XPUSHs(newSVnv(out[i]));
  PERL_UNUSED_VAR(RETVAL);

 # See swe_deltat()
double
swe_deltat( tjd_ut ) 
  double tjd_ut
CODE:
  RETVAL = swe_deltat( tjd_ut );
OUTPUT:
  RETVAL

 # Delta T extended
 # input:  $tjd_ut, $iflag
 # output: $hp = {dt =>,      * Delta T value
 #                serr =>,    * warning string, unless empty
 #               }
HV *
swe_deltat_ex(tjd_ut,iflag)
  double tjd_ut
  int iflag
PREINIT:
  double dt = 0;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  dt = swe_deltat_ex(tjd_ut, iflag, serr); 
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "dt", 2, newSVnv(dt), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL

 # Sidereal time from Julian date
double
swe_sidtime(tjd_ut)
  double tjd_ut
CODE:
  RETVAL = swe_sidtime(tjd_ut);
OUTPUT:
  RETVAL      

 # Sidereal time from Julian date, ecl. obliquity and nutation
double
swe_sidtime0(tjd_ut, eps, nut)
  double tjd_ut
  double eps
  double nut
CODE:
  RETVAL = swe_sidtime0(tjd_ut, eps, nut);
OUTPUT:
  RETVAL      

 # time equation
 # input:  $tjd_ut
 # output: $hp = {retval =>,     
 #                time_equ =>, *  time equation value
 #                serr =>,     *  error message (on error only)
 #               }
HV *
swe_time_equ(tjd_ut)
  double tjd_ut
PREINIT:
  int retval;
  double time_equation;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  retval = swe_time_equ(tjd_ut, &time_equation, serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "time_equ", 8, newSVnv(time_equation), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # conversion of Local Mean Time to Local Apparent Time
 # input:  $tjd_lmt : tjd in Local Mean Time
 #         $geolon  : geographic longitude
 # output: $hp = {retval =>,     
 #                tjd_lat =>,  *  tjd in Local Apparent Time
 #                serr =>,     *  error message (on error only)
 #               }
HV *
swe_lmt_to_lat(tjd_lmt, geolon)
  double tjd_lmt
  double geolon
PREINIT:
  int retval;
  double tjd_lat;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  retval = swe_lmt_to_lat(tjd_lmt, geolon, &tjd_lat, serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "tjd_lat", 7, newSVnv(tjd_lat), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # conversion of Local Apparent Time to Local Mean Time
 # input:  $tjd_lat : tjd in Local Apparent Time
 #         $geolon  : geographic longitude
 # output: $hp = {retval =>,     
 #                tjd_lmt =>,  *  tjd in Local Mean Time
 #                serr =>,     *  error message (on error only)
 #               }
HV *
swe_lat_to_lmt(tjd_lat, geolon)
  double tjd_lat
  double geolon
PREINIT:
  int retval;
  double tjd_lmt;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  retval = swe_lat_to_lmt(tjd_lat, geolon, &tjd_lmt, serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "tjd_lmt", 7, newSVnv(tjd_lmt), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # tidal acceleration to be used in swe_deltat()
double
swe_get_tid_acc()
CODE:
  RETVAL = swe_get_tid_acc();
OUTPUT:
  RETVAL      

void
swe_set_tid_acc(tacc)
double tacc
CODE:
  swe_set_tid_acc(tacc);

void
swe_set_delta_t_userdef(dt)
double dt
CODE:
  swe_set_delta_t_userdef(dt);

 # swe_degnorm
double
swe_degnorm(din)
double din
CODE:
  RETVAL = swe_degnorm(din);
OUTPUT:
  RETVAL      

 # swe_radnorm
double
swe_radnorm(din)
double din
CODE:
  RETVAL = swe_radnorm(din);
OUTPUT:
  RETVAL      

double
swe_difdegn(p1, p2)
double p1
double p2
CODE:
  RETVAL = swe_difdegn(p1, p2);
OUTPUT:
  RETVAL

# swe_deg_midp
double
swe_deg_midp(x1, x0)
double x1
double x0
CODE:
  RETVAL = swe_deg_midp(x1, x0);
OUTPUT:
  RETVAL      

# swe_rad_midp
double
swe_rad_midp(x1, x0)
double x1
double x0
CODE:
  RETVAL = swe_rad_midp(x1, x0);
OUTPUT:
  RETVAL      

double
swe_difdeg2n(p1, p2)
double p1
double p2
CODE:
  RETVAL = swe_difdeg2n(p1, p2);
OUTPUT:
  RETVAL

double
swe_difrad2n(p1, p2)
double p1
double p2
CODE:
  RETVAL = swe_difrad2n(p1, p2);
OUTPUT:
  RETVAL

double
swe_d2l(p)
double p
CODE:
  RETVAL = swe_d2l(p);
OUTPUT:
  RETVAL

# swe_day_of_week()
int
swe_day_of_week(tjd)
double tjd
CODE:
  RETVAL = swe_day_of_week(tjd);
OUTPUT:
  RETVAL

#################################################################
# functions from swedate.c
#################################################################

 # Julian date from calendar date
 # Last parameter (optional) is the gregflag
 # If it is not given, it is defaulted according to input date
double
swe_julday(year,month,day,hour,...) 
    int year
    int month
    int day
    double hour
CODE:
  int gregflag;
  if (items > 4) gregflag = (int)SvIV(ST(4));
  else gregflag = gregflag_from_date(year,month,day);
  RETVAL = swe_julday(year,month,day,hour,gregflag);
OUTPUT:
  RETVAL  

 # revjul
 # input:  $tjd, $gregflag (optional)
 # If gregflag is not given, it is defaulted according to tjd.
 # output: $hp = {iyar =>,    
 #                imon =>,   
 #                iday =>, 
 #                dhou =>, 
 #                ihou =>, 
 #                imin =>, 
 #                isec =>,
 #               }
HV *
swe_revjul(tjd,...)
  double tjd
PREINIT:
  double dhou, dsec;
  int iyar, imon, iday, ihou, imin, isec;
  int gregflag;
//  double half_second = 1 / 86400.0 / 2;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  if (items > 1) gregflag = (int)SvIV(ST(1));
  else gregflag = gregflag_from_jd(tjd);  
  swe_revjul(tjd, gregflag, &iyar, &imon, &iday, &dhou);
//  if (24 - dhou < half_second) {
//    tjd += half_second;
//    swe_revjul(tjd, gregflag, &iyar, &imon, &iday, &dhou);
//    dhou = 0;
//  }
  dsec = dhou * 3600.0 + 0.00005;
  ihou = (int) (dsec / 3600.0);
  dsec -= ihou * 3600.0;
  imin = (int) (dsec / 60.0);
  dsec -= imin * 60.0;
  isec = (int) dsec;
  (void)hv_store(hp, "iyar", 4, newSViv(iyar), 0);
  (void)hv_store(hp, "imon", 4, newSViv(imon), 0);
  (void)hv_store(hp, "iday", 4, newSViv(iday), 0);
  (void)hv_store(hp, "ihou", 4, newSViv(ihou), 0);
  (void)hv_store(hp, "imin", 4, newSViv(imin), 0);
  (void)hv_store(hp, "isec", 4, newSViv(isec), 0);
  (void)hv_store(hp, "dsec", 4, newSVnv(dsec), 0);
  (void)hv_store(hp, "dhou", 4, newSVnv(dhou), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # local time to UTC or UTC to local time
HV *
swe_utc_time_zone(iyear,imonth,iday,ihour,imin,dsec,dtimezone) 
    int iyear
    int imonth
    int iday
    int ihour
    int imin
    double dsec
    double dtimezone
CODE:
  int iyear_out, imonth_out, iday_out, ihour_out, imin_out;
  double dsec_out;
  HV* hp;
  hp = (HV *)sv_2mortal((SV *)newHV());
  swe_utc_time_zone(iyear,imonth,iday,ihour,imin,dsec,dtimezone,&iyear_out, &imonth_out, &iday_out, &ihour_out, &imin_out, &dsec_out);
  (void)hv_store(hp, "year_out", 8, newSViv(iyear_out), 0);
  (void)hv_store(hp, "month_out", 9, newSViv(imonth_out), 0);
  (void)hv_store(hp, "day_out", 7, newSViv(iday_out), 0);
  (void)hv_store(hp, "hour_out", 8, newSViv(ihour_out), 0);
  (void)hv_store(hp, "min_out", 7, newSViv(imin_out), 0);
  (void)hv_store(hp, "sec_out", 7, newSVnv(dsec_out), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL  

 # Julian date from calendar date (UTC)
 # Last parameter (optional) is the gregflag
 # If it is not given, it is defaulted according to input date
HV *
swe_utc_to_jd(iyear,imonth,iday,ihour,imin,dsec,...) 
    int iyear
    int imonth
    int iday
    int ihour
    int imin
    double dsec
CODE:
  int gregflag, retval;
  double dret[2];
  char serr[AS_MAXCH];
  HV* hp;
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  if (items > 6) gregflag = (int)SvIV(ST(6));
  else gregflag = gregflag_from_date(iyear,imonth,iday);
  retval = swe_utc_to_jd(iyear,imonth,iday,ihour,imin,dsec,gregflag,dret,serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    retval = -1;
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "tjd_et", 6, newSVnv(dret[0]), 0);
  (void)hv_store(hp, "tjd_ut", 6, newSVnv(dret[1]), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL  

 # Calendar date (UTC) from JD (ET)
 # input:  $tjd, $gregflag (optional)
 # If gregflag is not given, it is defaulted according to tjd.
 # output: $hp = {iyar =>,    
 #                imon =>,   
 #                iday =>, 
 #                ihou =>, 
 #                imin =>, 
 #                dsec =>,
 #               }
HV *
swe_jdet_to_utc(tjd,...)
  double tjd
PREINIT:
  double dsec;
  int iyar, imon, iday, ihou, imin;
  int gregflag;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  if (items > 1) gregflag = (int)SvIV(ST(1));
  else gregflag = gregflag_from_jd(tjd);  
  swe_jdet_to_utc(tjd, gregflag, &iyar, &imon, &iday, &ihou, &imin, &dsec);
  (void)hv_store(hp, "iyar", 4, newSViv(iyar), 0);
  (void)hv_store(hp, "imon", 4, newSViv(imon), 0);
  (void)hv_store(hp, "iday", 4, newSViv(iday), 0);
  (void)hv_store(hp, "ihou", 4, newSViv(ihou), 0);
  (void)hv_store(hp, "imin", 4, newSViv(imin), 0);
  (void)hv_store(hp, "dsec", 4, newSVnv(dsec), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # Calendar date (UTC) from JD (UT)
 # input:  $tjd, $gregflag (optional)
 # If gregflag is not given, it is defaulted according to tjd.
 # output: $hp = {iyar =>,    
 #                imon =>,   
 #                iday =>, 
 #                ihou =>, 
 #                imin =>, 
 #                dsec =>,
 #               }
HV *
swe_jdut1_to_utc(tjd,...)
  double tjd
PREINIT:
  double dsec;
  int iyar, imon, iday, ihou, imin;
  int gregflag;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  if (items > 1) gregflag = (int)SvIV(ST(1));
  else gregflag = gregflag_from_jd(tjd);  
  swe_jdut1_to_utc(tjd, gregflag, &iyar, &imon, &iday, &ihou, &imin, &dsec);
  (void)hv_store(hp, "iyar", 4, newSViv(iyar), 0);
  (void)hv_store(hp, "imon", 4, newSViv(imon), 0);
  (void)hv_store(hp, "iday", 4, newSViv(iday), 0);
  (void)hv_store(hp, "ihou", 4, newSViv(ihou), 0);
  (void)hv_store(hp, "imin", 4, newSViv(imin), 0);
  (void)hv_store(hp, "dsec", 4, newSVnv(dsec), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL


##############################################################
# functions from swehouse.c
##############################################################

 # houses
 # input:  $tjd_ut, $geolat, $geolon, $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #                ascmc =>,    * array pointer, see swisseph docu
 #                asc =>, 
 #                mc =>, 
 #                armc =>, 
 #                vertex =>,
 #                equasc =>,   * "equatorial ascendant"
 #                coasc1 =>,   * "co-ascendant 1" (M. Munkasey)
 #                coasc2 =>,   * "co-ascendant 2" (M. Munkasey)
 #                polasc =>,   * "polar ascendant" (M. Munkasey)
 #               }
HV *
swe_houses(tjd_ut,geolat,geolon,hsys)
  double tjd_ut
  double geolat
  double geolon
  char* hsys
PREINIT:
  int i, retval;
  double cusps[37], ascmc[10];
  HV* hp;
  AV* avcusps = newAV();
  AV* avascmc = newAV();
  SV* svcusps;
  SV* svascmc;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avcusps);
  av_clear(avascmc);
  retval = swe_houses(tjd_ut, geolat, geolon, (char) *hsys, cusps, ascmc);
  if( *hsys=='G' ) {
    for (i=0;i<37;i++) av_push(avcusps,(newSVnv(cusps[i])));
  } else {
    for (i=0;i<13;i++) av_push(avcusps,(newSVnv(cusps[i])));
  }
  for (i=0;i<10;i++) av_push(avascmc,(newSVnv(ascmc[i])));
  svcusps = newRV_noinc((SV*) avcusps);
  svascmc = newRV_noinc((SV*) avascmc);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "cusps", 5, newSVsv(svcusps), 0);
  (void)hv_store(hp, "ascmc", 5, newSVsv(svascmc), 0);
  (void)hv_store(hp, "asc", 3, newSVnv(ascmc[0]), 0);
  (void)hv_store(hp, "mc", 2, newSVnv(ascmc[1]), 0);
  (void)hv_store(hp, "armc", 4, newSVnv(ascmc[2]), 0);
  (void)hv_store(hp, "vertex", 6, newSVnv(ascmc[3]), 0);
  (void)hv_store(hp, "equasc", 6, newSVnv(ascmc[4]), 0);
  (void)hv_store(hp, "coasc1", 6, newSVnv(ascmc[5]), 0);
  (void)hv_store(hp, "coasc2", 6, newSVnv(ascmc[6]), 0);
  (void)hv_store(hp, "polasc", 6, newSVnv(ascmc[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # houses_armc
 # input:  $armc, $geolat, $eps, $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #                ascmc =>,    * array pointer, see swisseph docu
 #                asc =>, 
 #                mc =>, 
 #                armc =>, 
 #                vertex =>,
 #                equasc =>,   * "equatorial ascendant"
 #                coasc1 =>,   * "co-ascendant 1" (M. Munkasey)
 #                coasc2 =>,   * "co-ascendant 2" (M. Munkasey)
 #                polasc =>,   * "polar ascendant" (M. Munkasey)
 #               }
HV *
swe_houses_armc(armc,geolat,eps,hsys)
  double armc
  double geolat
  double eps
  char* hsys
PREINIT:
  int i, retval;
  double cusps[37], ascmc[10];
  HV* hp;
  AV* avcusps = newAV();
  AV* avascmc = newAV();
  SV* svcusps;
  SV* svascmc;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avcusps);
  av_clear(avascmc);
  retval = swe_houses_armc(armc, geolat, eps, (char) *hsys, cusps, ascmc);
  if( *hsys=='G' ) {
    for (i=0;i<37;i++) av_push(avcusps,(newSVnv(cusps[i])));
  } else {
    for (i=0;i<13;i++) av_push(avcusps,(newSVnv(cusps[i])));
  }
  for (i=0;i<10;i++) av_push(avascmc,(newSVnv(ascmc[i])));
  svcusps = newRV_noinc((SV*) avcusps);
  svascmc = newRV_noinc((SV*) avascmc);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "cusps", 5, newSVsv(svcusps), 0);
  (void)hv_store(hp, "ascmc", 5, newSVsv(svascmc), 0);
  (void)hv_store(hp, "asc", 3, newSVnv(ascmc[0]), 0);
  (void)hv_store(hp, "mc", 2, newSVnv(ascmc[1]), 0);
  (void)hv_store(hp, "armc", 4, newSVnv(ascmc[2]), 0);
  (void)hv_store(hp, "vertex", 6, newSVnv(ascmc[3]), 0);
  (void)hv_store(hp, "equasc", 6, newSVnv(ascmc[4]), 0);
  (void)hv_store(hp, "coasc1", 6, newSVnv(ascmc[5]), 0);
  (void)hv_store(hp, "coasc2", 6, newSVnv(ascmc[6]), 0);
  (void)hv_store(hp, "polasc", 6, newSVnv(ascmc[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # houses_armc_ex2
 # input:  $armc, $geolat, $eps, $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #                ascmc =>,    * array pointer, see swisseph docu
 #                cusps_speed, * array pointer, see swisseph docu
 #                ascmc_speed, * array pointer, see swisseph docu
 #                asc =>, 
 #                mc =>, 
 #                armc =>, 
 #                vertex =>,
 #                equasc =>,   * "equatorial ascendant"
 #                coasc1 =>,   * "co-ascendant 1" (M. Munkasey)
 #                coasc2 =>,   * "co-ascendant 2" (M. Munkasey)
 #                polasc =>,   * "polar ascendant" (M. Munkasey)
 #                serr =>,     * error message or warning
 #               }
HV *
swe_houses_armc_ex2(armc,geolat,eps,hsys)
  double armc
  double geolat
  double eps
  char* hsys
PREINIT:
  int i, retval;
  double cusps[37], ascmc[10];
  double cusps_speed[37], ascmc_speed[10];
  char serr[255];
  HV* hp;
  AV* avcusps = newAV();
  AV* avascmc = newAV();
  AV* avcusps_speed = newAV();
  AV* avascmc_speed = newAV();
  SV* svcusps;
  SV* svascmc;
  SV* svcusps_speed;
  SV* svascmc_speed;
CODE:
  *serr = '\0';
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avcusps);
  av_clear(avascmc);
  av_clear(avcusps_speed);
  av_clear(avascmc_speed);
  retval = swe_houses_armc_ex2(armc, geolat, eps, (char) *hsys, cusps, ascmc, cusps_speed, ascmc_speed, serr);
  if( *hsys=='G' ) {
    for (i=0;i<37;i++) av_push(avcusps,(newSVnv(cusps[i])));
    for (i=0;i<37;i++) av_push(avcusps_speed,(newSVnv(cusps_speed[i])));
  } else {
    for (i=0;i<13;i++) av_push(avcusps,(newSVnv(cusps[i])));
    for (i=0;i<13;i++) av_push(avcusps_speed,(newSVnv(cusps_speed[i])));
  }
  for (i=0;i<10;i++) av_push(avascmc,(newSVnv(ascmc[i])));
  for (i=0;i<10;i++) av_push(avascmc_speed,(newSVnv(ascmc_speed[i])));
  svcusps = newRV_noinc((SV*) avcusps);
  svascmc = newRV_noinc((SV*) avascmc);
  svcusps_speed = newRV_noinc((SV*) avcusps_speed);
  svascmc_speed = newRV_noinc((SV*) avascmc_speed);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "cusps", 5, newSVsv(svcusps), 0);
  (void)hv_store(hp, "cusps_speed", 11, newSVsv(svcusps_speed), 0);
  (void)hv_store(hp, "ascmc", 5, newSVsv(svascmc), 0);
  (void)hv_store(hp, "ascmc_speed", 11, newSVsv(svascmc_speed), 0);
  (void)hv_store(hp, "asc", 3, newSVnv(ascmc[0]), 0);
  (void)hv_store(hp, "mc", 2, newSVnv(ascmc[1]), 0);
  (void)hv_store(hp, "armc", 4, newSVnv(ascmc[2]), 0);
  (void)hv_store(hp, "vertex", 6, newSVnv(ascmc[3]), 0);
  (void)hv_store(hp, "equasc", 6, newSVnv(ascmc[4]), 0);
  (void)hv_store(hp, "coasc1", 6, newSVnv(ascmc[5]), 0);
  (void)hv_store(hp, "coasc2", 6, newSVnv(ascmc[6]), 0);
  (void)hv_store(hp, "polasc", 6, newSVnv(ascmc[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # houses_ex
 # input:  $tjd_ut, $iflag, $geolat, $geolon, $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #                ascmc =>,    * array pointer, see swisseph docu
 #                asc =>, 
 #                mc =>, 
 #                armc =>, 
 #                vertex =>,
 #                equasc =>,   * "equatorial ascendant"
 #                coasc1 =>,   * "co-ascendant 1" (M. Munkasey)
 #                coasc2 =>,   * "co-ascendant 2" (M. Munkasey)
 #                polasc =>,   * "polar ascendant" (M. Munkasey)
 #               }
HV *
swe_houses_ex(tjd_ut,iflag,geolat,geolon,hsys)
  double tjd_ut
  int iflag
  double geolat
  double geolon
  char* hsys
PREINIT:
  int i, retval;
  double cusps[37], ascmc[10];
  HV* hp;
  AV* avcusps = newAV();
  AV* avascmc = newAV();
  SV* svcusps;
  SV* svascmc;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avcusps);
  av_clear(avascmc);
  retval = swe_houses_ex(tjd_ut, iflag, geolat, geolon, (char) *hsys, cusps, ascmc);
  if( *hsys=='G' ) {
    for (i=0;i<37;i++) av_push(avcusps,(newSVnv(cusps[i])));
  } else {
    for (i=0;i<13;i++) av_push(avcusps,(newSVnv(cusps[i])));
  }
  for (i=0;i<10;i++) av_push(avascmc,(newSVnv(ascmc[i])));
  svcusps = newRV_noinc((SV*) avcusps);
  svascmc = newRV_noinc((SV*) avascmc);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "cusps", 5, newSVsv(svcusps), 0);
  (void)hv_store(hp, "ascmc", 5, newSVsv(svascmc), 0);
  (void)hv_store(hp, "asc", 3, newSVnv(ascmc[0]), 0);
  (void)hv_store(hp, "mc", 2, newSVnv(ascmc[1]), 0);
  (void)hv_store(hp, "armc", 4, newSVnv(ascmc[2]), 0);
  (void)hv_store(hp, "vertex", 6, newSVnv(ascmc[3]), 0);
  (void)hv_store(hp, "equasc", 6, newSVnv(ascmc[4]), 0);
  (void)hv_store(hp, "coasc1", 6, newSVnv(ascmc[5]), 0);
  (void)hv_store(hp, "coasc2", 6, newSVnv(ascmc[6]), 0);
  (void)hv_store(hp, "polasc", 6, newSVnv(ascmc[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # houses_ex2
 # input:  $tjd_ut, $iflag, $geolat, $geolon, $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #                ascmc =>,    * array pointer, see swisseph docu
 #                cusps_speed, * array pointer, see swisseph docu
 #                ascmc_speed, * array pointer, see swisseph docu
 #                asc =>, 
 #                mc =>, 
 #                armc =>, 
 #                vertex =>,
 #                equasc =>,   * "equatorial ascendant"
 #                coasc1 =>,   * "co-ascendant 1" (M. Munkasey)
 #                coasc2 =>,   * "co-ascendant 2" (M. Munkasey)
 #                polasc =>,   * "polar ascendant" (M. Munkasey)
 #                serr =>,     * error message or warning
 #               }
HV *
swe_houses_ex2(tjd_ut,iflag,geolat,geolon,hsys)
  double tjd_ut
  int iflag
  double geolat
  double geolon
  char* hsys
PREINIT:
  int i, retval;
  double cusps[37], ascmc[10];
  double cusps_speed[37], ascmc_speed[10];
  char serr[255];
  HV* hp;
  AV* avcusps = newAV();
  AV* avascmc = newAV();
  AV* avcusps_speed = newAV();
  AV* avascmc_speed = newAV();
  SV* svcusps;
  SV* svascmc;
  SV* svcusps_speed;
  SV* svascmc_speed;
CODE:
  *serr = '\0';
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avcusps);
  av_clear(avascmc);
  retval = swe_houses_ex2(tjd_ut, iflag, geolat, geolon, (char) *hsys, cusps, ascmc, cusps_speed, ascmc_speed, serr);
  if( *hsys=='G' ) {
    for (i=0;i<37;i++) av_push(avcusps,(newSVnv(cusps[i])));
    for (i=0;i<37;i++) av_push(avcusps_speed,(newSVnv(cusps_speed[i])));
  } else {
    for (i=0;i<13;i++) av_push(avcusps,(newSVnv(cusps[i])));
    for (i=0;i<13;i++) av_push(avcusps_speed,(newSVnv(cusps_speed[i])));
  }
  for (i=0;i<10;i++) av_push(avascmc,(newSVnv(ascmc[i])));
  for (i=0;i<10;i++) av_push(avascmc_speed,(newSVnv(ascmc_speed[i])));
  svcusps = newRV_noinc((SV*) avcusps);
  svascmc = newRV_noinc((SV*) avascmc);
  svcusps_speed = newRV_noinc((SV*) avcusps_speed);
  svascmc_speed = newRV_noinc((SV*) avascmc_speed);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "cusps", 5, newSVsv(svcusps), 0);
  (void)hv_store(hp, "cusps_speed", 11, newSVsv(svcusps_speed), 0);
  (void)hv_store(hp, "ascmc", 5, newSVsv(svascmc), 0);
  (void)hv_store(hp, "ascmc_speed", 11, newSVsv(svascmc_speed), 0);
  (void)hv_store(hp, "asc", 3, newSVnv(ascmc[0]), 0);
  (void)hv_store(hp, "mc", 2, newSVnv(ascmc[1]), 0);
  (void)hv_store(hp, "armc", 4, newSVnv(ascmc[2]), 0);
  (void)hv_store(hp, "vertex", 6, newSVnv(ascmc[3]), 0);
  (void)hv_store(hp, "equasc", 6, newSVnv(ascmc[4]), 0);
  (void)hv_store(hp, "coasc1", 6, newSVnv(ascmc[5]), 0);
  (void)hv_store(hp, "coasc2", 6, newSVnv(ascmc[6]), 0);
  (void)hv_store(hp, "polasc", 6, newSVnv(ascmc[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # house_pos
 # input:  $armc, $geolat, $eps, $hsys, $ecl_lon, $ecl_lat
 # output: $hp = {ihno =>,   
 #                dhpos =>,    
 #                dhpos_deg =>, 
 #                serr =>,      * error string, on error only
 #               }
HV *
swe_house_pos(armc,geolat,eps,hsys, ecl_lon, ecl_lat)
  double armc
  double geolat
  double eps
  char* hsys
  double ecl_lon
  double ecl_lat
PREINIT:
  int retval = 0;
  double dh;
  char serr[255];
  double xpin[2];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  xpin[0] = ecl_lon;
  xpin[1] = ecl_lat;
  *serr = '\0';
  dh = swe_house_pos(armc, geolat, eps, (char) *hsys, xpin, serr);
  if (*serr != '\0') {
    retval = -1;
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "ihno", 4, newSViv((int) dh), 0);
  (void)hv_store(hp, "dhpos", 5, newSVnv(dh), 0);
  (void)hv_store(hp, "dhpos_deg", 9, newSVnv((dh - 1) * 30.0), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # house_name
 # input:  $hsys
 # output: $hp = {cusps =>,    * array pointer, see swisseph docu
 #               }
char * 
swe_house_name(hsys)
  char* hsys
CODE:
  RETVAL = swe_house_name((char) *hsys);
OUTPUT:
  RETVAL

#############################################################
# functions from sweph.c and related stuff
#############################################################

 # calc
 # input:  $tjd, $ipl, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #               }
HV *
swe_calc(tjd,ipl,iflag)
  double tjd
  int ipl
  int iflag
PREINIT:
  int i, retval = 0;
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  *serr = '\0';
  retval = swe_calc(tjd, ipl, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # calc_ut
 # input:  $tjd_ut, $ipl, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #               }
HV *
swe_calc_ut(tjd_ut,ipl,iflag)
  double tjd_ut
  int ipl
  int iflag
PREINIT:
  int i, retval = 0;
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  *serr = '\0';
  retval = swe_calc_ut(tjd_ut, ipl, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # function calculates planetocentric positions
 # input:  $tjd_ut, $ipl, $iplctr, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #               }
HV *
swe_calc_pctr(tjd_ut,ipl,iplctr,iflag)
  double tjd_ut
  int ipl
  int iplctr
  int iflag
PREINIT:
  int i, retval = 0;
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  *serr = '\0';
  retval = swe_calc_pctr(tjd_ut, ipl, iplctr, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar
 # input:  $star, $tjd, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar(star,tjd,iflag)
  char* star
  double tjd
  int iflag
PREINIT:
  int i, retval = 0;
  char _star[255];
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar(_star, tjd, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar2
 # input:  $star, $tjd, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar2(star,tjd,iflag)
  char* star
  double tjd
  int iflag
PREINIT:
  int i, retval = 0;
  char _star[255];
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar2(_star, tjd, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar_ut
 # input:  $star, $tjd, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar_ut(star,tjd,iflag)
  char *star
  double tjd
  int iflag
PREINIT:
  int i, retval = 0;
  char _star[255];
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar_ut(_star, tjd, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar2_ut
 # input:  $star, $tjd, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                xx =>,      * position array 
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar2_ut(star,tjd,iflag)
  char *star
  double tjd
  int iflag
PREINIT:
  int i, retval = 0;
  char _star[255];
  char serr[255];
  double xx[6];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar2_ut(_star, tjd, iflag, xx, serr);
  for (i=0;i<6;i++) av_push(avxx,(newSVnv(xx[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "xx", 2, newSVsv(svxx), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar_mag
 # input:  $star
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                dmag =>,    * magnitude of fixed star
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar_mag(star)
  char *star
PREINIT:
  int retval = 0;
  char _star[255];
  char serr[255];
  HV* hp;
  double dmag;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar_mag(_star, &dmag, serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dmag", 4, newSVnv(dmag), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # fixstar2_mag
 # input:  $star
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                dmag =>,    * magnitude of fixed star
 #                starname =>,      * fixed star name
 #               }
HV *
swe_fixstar2_mag(star)
  char *star
PREINIT:
  int retval = 0;
  char _star[255];
  char serr[255];
  HV* hp;
  double dmag;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  *serr = '\0';
  retval = swe_fixstar2_mag(_star, &dmag, serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dmag", 4, newSVnv(dmag), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # calc
 # input:  $tjd, $ipl, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                dmax =>,    * maximum distance
 #                dmin =>,    * minimum distance
 #                dtrue =>,   * true distance
 #               }
HV *
swe_orbit_max_min_true_distance(tjd,ipl,iflag)
  double tjd
  int ipl
  int iflag
PREINIT:
  int retval = 0;
  char serr[255];
  double dmax, dmin, dtrue;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  retval = swe_orbit_max_min_true_distance(tjd, ipl, iflag, &dmax, &dmin, &dtrue, serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dmax", 4, newSVnv(dmax), 0);
  (void)hv_store(hp, "dmin", 4, newSVnv(dmin), 0);
  (void)hv_store(hp, "dtrue", 4, newSVnv(dtrue), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # calc
 # input:  $tjd, $ipl, $iflag
 # output: $hp = {retval =>,   
 #                serr =>,    * error string, on error only
 #                dret =>,    * array of data
 #               }
HV *
swe_get_orbital_elements(tjd,ipl,iflag)
  double tjd
  int ipl
  int iflag
PREINIT:
  int i, retval = 0;
  char serr[255];
  double dret[20];
  AV* avxx = newAV();
  SV* svxx;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxx);
  *serr = '\0';
  retval = swe_get_orbital_elements(tjd, ipl, iflag, dret, serr);
  for (i=0;i<20;i++) av_push(avxx,(newSVnv(dret[i])));
  svxx = newRV_noinc((SV*) avxx);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dret", 4, newSVsv(svxx), 0);
  (void)hv_store(hp, "sema", 4, newSVnv(dret[0]), 0);
  (void)hv_store(hp, "ecce", 4, newSVnv(dret[1]), 0);
  (void)hv_store(hp, "incl", 4, newSVnv(dret[2]), 0);
  (void)hv_store(hp, "node", 4, newSVnv(dret[3]), 0);
  (void)hv_store(hp, "parg", 4, newSVnv(dret[4]), 0);
  (void)hv_store(hp, "peri", 4, newSVnv(dret[5]), 0);
  (void)hv_store(hp, "mean_anom", 9, newSVnv(dret[6]), 0);
  (void)hv_store(hp, "true_anom", 9, newSVnv(dret[7]), 0);
  (void)hv_store(hp, "ecce_anom", 9, newSVnv(dret[8]), 0);
  (void)hv_store(hp, "mean_long", 9, newSVnv(dret[9]), 0);
  (void)hv_store(hp, "sid_period", 10, newSVnv(dret[10]), 0);
  (void)hv_store(hp, "daily_motion", 12, newSVnv(dret[11]), 0);
  (void)hv_store(hp, "trop_period", 11, newSVnv(dret[12]), 0);
  (void)hv_store(hp, "synod_period", 12, newSVnv(dret[13]), 0);
  (void)hv_store(hp, "perihelion_time", 15, newSVnv(dret[14]), 0);
  (void)hv_store(hp, "perihelion_distance", 19, newSVnv(dret[15]), 0);
  (void)hv_store(hp, "aphelion_distance", 17, newSVnv(dret[16]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # swe_set_ephe_path()
void
swe_set_ephe_path(path) 
  char *path
CODE:  
  swe_set_ephe_path(path);

 # swe_set_jpl_file()
void
swe_set_jpl_file(fname) 
  char *fname
CODE:  
  swe_set_jpl_file(fname);

 # swe_close()
void
swe_close() 
CODE:  
  swe_close();

 # Direct translation of swe_get_planet_name()
SV*
swe_get_planet_name(ipl) 
  int ipl
CODE:  
  char plnam[255];
  RETVAL = newSVpvn("", 255);
  swe_get_planet_name(ipl, plnam);
  sv_setpvn(RETVAL, plnam, strlen(plnam)); 
OUTPUT:
  RETVAL

 # swe_set_topo()
void
swe_set_topo(geolon, geolat, geoalt) 
  double geolon
  double geolat
  double geoalt
CODE:  
  swe_set_topo(geolon, geolat, geoalt);

 # swe_get_ayanamsa()
double
swe_get_ayanamsa(tjd_et)
  double tjd_et
CODE:
  RETVAL = swe_get_ayanamsa(tjd_et);
OUTPUT:
  RETVAL      

 # swe_get_ayanamsa_ut()
double
swe_get_ayanamsa_ut(tjd_ut)
  double tjd_ut
CODE:
  RETVAL = swe_get_ayanamsa_ut(tjd_ut);
OUTPUT:
  RETVAL      

 # Function calculates ayanamsa, depending on ephemeris flag
 # input:  $tjd_et, $iflag
 # output: $hp = {retval => 
 #                daya =>,    * ayanamsa value
 #                serr =>,    * warning string, unless empty
 #               }
HV *
swe_get_ayanamsa_ex(tjd_et,iflag)
  double tjd_et
  int iflag
PREINIT:
  double daya;
  int retval = 0;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  retval = swe_get_ayanamsa_ex(tjd_et, iflag, &daya, serr); 
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "daya", 4, newSVnv(daya), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL

 # Function calculates ayanamsa, depending on ephemeris flag
 # input:  $tjd_ut, $iflag
 # output: $hp = {retval => 
 #                daya =>,    * ayanamsa value
 #                serr =>,    * warning string, unless empty
 #               }
HV *
swe_get_ayanamsa_ex_ut(tjd_ut,iflag)
  double tjd_ut
  int iflag
PREINIT:
  double daya;
  int retval = 0;
  char serr[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  retval = swe_get_ayanamsa_ex_ut(tjd_ut, iflag, &daya, serr); 
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "daya", 4, newSVnv(daya), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL

 # swe_get_ayanamsa_name()
SV*
swe_get_ayanamsa_name(isidmode) 
  int isidmode
CODE:  
  char aynam[255];
  RETVAL = newSVpvn("", 255);
  strcpy(aynam, swe_get_ayanamsa_name(isidmode));
  sv_setpvn(RETVAL, aynam, strlen(aynam)); 
OUTPUT:
  RETVAL

 # Function calculates ayanamsa, depending on ephemeris flag
 # input:  $tjd_ut, $iflag
 # output: $hp = {retval => 
 #                daya =>,    * ayanamsa value
 #                serr =>,    * warning string, unless empty
 #               }
HV *
swe_get_current_file_data(ifno)
  int ifno
PREINIT:
  double tfstart;
  double tfend;
  int denum;
  char fnam[255];
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(fnam, swe_get_current_file_data(ifno, &tfstart, &tfend, &denum)); 
  (void)hv_store(hp, "tfstart", 7, newSVnv(tfstart), 0);
  (void)hv_store(hp, "tfend", 5, newSVnv(tfend), 0);
  (void)hv_store(hp, "denum", 5, newSViv(denum), 0);
  (void)hv_store(hp, "fnam", 4, newSVpvn(fnam, strlen(fnam)), 0);
  RETVAL = hp;
OUTPUT:
  RETVAL

 # swe_set_sid_mode()
void
swe_set_sid_mode(sidmode, t0, ayant0) 
  int sidmode
  double t0
  double ayant0
CODE:  
  swe_set_sid_mode(sidmode, t0, ayant0);

###################################################################
# functions from swecl.c
###################################################################

 # swe_gauquelin_sector
 # input:  $ipl
 #         $star        star name, if object is a star
 #         $iflag       ephemeris flag
 #         $imeth       method flag
 #                 0    use Placidus house position
 #                 1    use Placidus house posiition (with planetary lat = 0)
 #                 2    use rise and set of body's disc center
 #                 3    use rise and set of body's disc center with refraction
 #         $geopos      pointer to array of geogr. long., lat., height
 #         $atpress     atmospheric pressure
 #         $attemp      atmospheric temperature
 # output: $hp = {retval =>,   
 #                serr =>,       * error string, on error only
 #                dsector =>,    * sector position
 #                starname =>,   * fixed star name
 #               }
HV *
swe_gauquelin_sector(tjd_ut,ipl,star,iflag,imeth,geopos,atpress,attemp)
  double tjd_ut
  int ipl
  char *star
  int iflag
  int imeth
  SV* geopos  
  double atpress
  double attemp
PREINIT:
  int i, retval = 0;
  double dret = 0, gp[3];
  char _star[255];
  char serr[255];
  HV* hp;
CODE:
  AV* _geopos = (AV*) SvRV(geopos);
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  retval = swe_gauquelin_sector(tjd_ut,ipl,_star,iflag,imeth,gp,atpress,attemp,&dret,serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dsector", 7, newSVnv(dret), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_occult_when_loc
# input:  $tjd         start date for search
#         $ipl
#         $star        star name, if object is a star
#         $iflag       ephemeris flag
#         $backw       1, if backward search
#         $geopos      pointer to array of geogr. long., lat., height
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                starname =>,   * fixed star name
#                tret =>,       * pointer to tret array (see docu)
#                attr =>,       * pointer to attr array (see docu)
#                occ_maximum =>,   * time of max. occultation
#                t1st_contact =>,       
#                t2nd_contact =>,       
#                t3rd_contact =>,       
#                t4th_contact =>,       
#                disc_ratio =>,        * size ratio of the two discs
#                fraction_diameter =>, * percentage of diameter occulted
#                fraction_disc =>,     * percentage of disc occulted
#                core_shadow_km =>,    * diameter of core shadow (km, negative)
#                body_azimuth =>,      
#                body_alt_true =>,      
#                separation_angle =>,      
#               }
HV *
swe_lun_occult_when_loc(tjd,ipl,star,iflag,backw,geopos)
  double tjd
  int ipl
  char *star
  int iflag
  int backw
  SV* geopos  
PREINIT:
  int i, retval = 0;
  double tret[20], attr[20], gp[3];
  char _star[255];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  AV* avattr = newAV();
  SV* svtret;
  SV* svattr;
  AV* _geopos = (AV*) SvRV(geopos);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  av_clear(avtret);
  av_clear(avattr);
  swe_set_topo(gp[0], gp[1], gp[1]);
  retval = swe_lun_occult_when_loc(tjd,ipl,_star,iflag,gp,tret,attr,backw,serr);
  if (attr[0] > 1) attr[0] = 1; /* should be fixed in Swisseph code */
  if (attr[2] > 1) attr[2] = 1; /* should be fixed in Swisseph code */
  for (i=0;i<7;i++) av_push(avtret,(newSVnv(tret[i])));
  for (i=0;i<8;i++) av_push(avattr,(newSVnv(attr[i])));
  svtret = newRV_noinc((SV*) avtret);
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "occ_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "t1st_contact", 12, newSVnv(tret[1]), 0);
  (void)hv_store(hp, "t2nd_contact", 12, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "t3rd_contact", 12, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "t4th_contact", 12, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "fraction_diameter", 17, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "disc_ratio", 10, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "fraction_disc", 13, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "core_shadow_km", 14, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "body_azimuth", 12, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "body_alt_true", 13, newSVnv(attr[5]), 0);
  /*(void)hv_store(hp, "body_alt_app", 12, newSVnv(attr[6]), 0);*/
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

void
swe_set_astro_models(samod, iflag)
  char *samod
  int  iflag
CODE:
  swe_set_astro_models(samod, iflag);

# swe_sol_eclipse_when_loc
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $backw       1, if backward search
#         $geopos      pointer to array of geogr. long., lat., height
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                tret =>,       * pointer to tret array (see docu)
#                attr =>,       * pointer to attr array (see docu)
#                ecl_maximum =>,   * time of max. eclipse
#                t1st_contact =>,       
#                t2nd_contact =>,       
#                t3rd_contact =>,       
#                t4th_contact =>,       
#                disc_ratio =>,        * size ratio of the two discs
#                fraction_diameter =>, * percentage of diameter occulted
#                fraction_disc =>,     * percentage of disc occulted
#                core_shadow_km =>,    * diameter of core shadow (km, negative)
#                sun_azimuth =>,      
#                sun_alt_true =>,      
#                separation_angle =>,      
#               }
HV *
swe_sol_eclipse_when_loc(tjd,iflag,backw,geopos)
  double tjd
  int iflag
  int backw
  SV* geopos  
PREINIT:
  int i, retval = 0;
  double tret[20], attr[20], gp[3];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  AV* avattr = newAV();
  SV* svtret;
  SV* svattr;
  AV* _geopos = (AV*) SvRV(geopos);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  av_clear(avtret);
  av_clear(avattr);
  swe_set_topo(gp[0], gp[1], gp[1]);
  retval = swe_sol_eclipse_when_loc(tjd,iflag,gp,tret,attr,backw,serr);
  for (i=0;i<7;i++) av_push(avtret,(newSVnv(tret[i])));
  for (i=0;i<11;i++) av_push(avattr,(newSVnv(attr[i])));
  svtret = newRV_noinc((SV*) avtret);
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "ecl_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "t1st_contact", 12, newSVnv(tret[1]), 0);
  (void)hv_store(hp, "t2nd_contact", 12, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "t3rd_contact", 12, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "t4th_contact", 12, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "fraction_diameter", 17, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "disc_ratio", 10, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "fraction_disc", 13, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "core_shadow_km", 14, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "sun_azimuth", 11, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "sun_alt_true", 12, newSVnv(attr[5]), 0);
  /*(void)hv_store(hp, "body_alt_app", 12, newSVnv(attr[6]), 0);*/
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  (void)hv_store(hp, "saros_series", 12, newSVnv(attr[9]), 0);
  (void)hv_store(hp, "saros_ecl_no", 12, newSVnv(attr[10]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_occult_when_glob
# input:  $tjd         start date for search
#         $ipl
#         $star        star name, if object is a star
#         $iflag       ephemeris flag
#         $ifltype     eclipse type to be searched (SE_ECL_TOTAL, etc.)
#                      0, if any type of eclipse is wanted
#                      this functionality also works with occultations
#         $backw       1, if backward search
#                      If you want to have only one conjunction
#                      of the moon with the body tested, add the following 
#                      flag:
#                      backward |= SE_ECL_ONE_TRY. If this flag is not set,
#                      the function will search for an occultation until it
#                      finds one. For bodies with ecliptical latitudes > 5,
#                      the function may search successlessly until it reaches
#                      the end of the ephemeris.
# output: $hp = {retval =>,  ERR or occultation type
#                            SE_ECL_TOTAL or SE_ECL_ANNULAR or SE_ECL_PARTIAL
#                            or SE_ECL_ANNULAR_TOTAL
#                            SE_ECL_CENTRAL
#                            SE_ECL_NONCENTRAL
#                serr =>,       * error string, on error only
#                starname =>,   * fixed star name
#                tret =>,       * pointer to tret array (see docu)
#                occ_maximum =>,   * time of max. occultation
#                occ_local_noon =>,   
#                occ_begin =>,       
#                occ_end =>,       
#                occ_total_begin =>,       
#                occ_total_end =>,       
#                occ_central_begin =>,       
#                occ_central_end =>,       
#               }
HV *
swe_lun_occult_when_glob(tjd,ipl,star,iflag,ifltype,backw)
  double tjd
  int ipl
  char *star
  int iflag
  int ifltype
  int backw
PREINIT:
  int i, retval = 0;
  double tret[20];
  char _star[255];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  SV* svtret;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  *serr = '\0';
  av_clear(avtret);
  retval = swe_lun_occult_when_glob(tjd,ipl,_star,iflag,ifltype,tret,backw,serr);
  for (i=0;i<8;i++) av_push(avtret,(newSVnv(tret[i])));
  svtret = newRV_noinc((SV*) avtret);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "occ_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "occ_local_noon", 14, newSVnv(tret[1]), 0);
  (void)hv_store(hp, "occ_begin", 9, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "occ_end", 7, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "occ_total_begin", 15, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "occ_total_end", 13, newSVnv(tret[5]), 0);
  (void)hv_store(hp, "occ_central_begin", 17, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "occ_central_end", 15, newSVnv(tret[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_sol_eclipse_when_glob
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $ifltype     eclipse type to be searched (SE_ECL_TOTAL, etc.)
#                      0, if any type of eclipse is wanted
#                      this functionality also works with occultations
#         $backw       1, if backward search
#                      If you want to have only one conjunction
#                      of the moon with the body tested, add the following 
#                      flag:
#                      backward |= SE_ECL_ONE_TRY. If this flag is not set,
#                      the function will search for an occultation until it
#                      finds one. For bodies with ecliptical latitudes > 5,
#                      the function may search successlessly until it reaches
#                      the end of the ephemeris.
# output: $hp = {retval =>,  ERR or occultation type
#                            SE_ECL_TOTAL or SE_ECL_ANNULAR or SE_ECL_PARTIAL
#                            or SE_ECL_ANNULAR_TOTAL
#                            SE_ECL_CENTRAL
#                            SE_ECL_NONCENTRAL
#                serr =>,       * error string, on error only
#                tret =>,       * pointer to tret array (see docu)
#                ecl_maximum =>,   * time of max. occultation
#                ecl_local_noon =>,   
#                ecl_begin =>,       
#                ecl_end =>,       
#                ecl_total_begin =>,       
#                ecl_total_end =>,       
#                ecl_central_begin =>,       
#                ecl_central_end =>,       
#               }
HV *
swe_sol_eclipse_when_glob(tjd,iflag,ifltype,backw)
  double tjd
  int iflag
  int ifltype
  int backw
PREINIT:
  int i, retval = 0;
  double tret[20];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  SV* svtret;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avtret);
  retval = swe_sol_eclipse_when_glob(tjd,iflag,ifltype,tret,backw,serr);
  for (i=0;i<8;i++) av_push(avtret,(newSVnv(tret[i])));
  svtret = newRV_noinc((SV*) avtret);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "ecl_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "ecl_local_noon", 14, newSVnv(tret[1]), 0);
  (void)hv_store(hp, "ecl_begin", 9, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "ecl_end", 7, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "ecl_total_begin", 15, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "ecl_total_end", 13, newSVnv(tret[5]), 0);
  (void)hv_store(hp, "ecl_central_begin", 17, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "ecl_central_end", 15, newSVnv(tret[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_eclipse_when
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $ifltype     eclipse type to be searched (SE_ECL_TOTAL, etc.)
#                      0, if any type of eclipse is wanted
#                      this functionality also works with occultations
#         $backw       1, if backward search
# output: $hp = {retval =>,  ERR or occultation type
#                            SE_ECL_TOTAL or SE_ECL_PENUMBRAL or SE_ECL_PARTIAL
#                serr =>,       * error string, on error only
#                tret =>,       * pointer to tret array (see docu)
#                ecl_maximum =>,   * time of max. occultation
#                ecl_partial_begin =>,   
#                ecl_partial_end =>,       
#                ecl_total_begin =>,       
#                ecl_total_end =>,       
#                ecl_penumbral_begin =>,       
#                ecl_penumbral_end =>,       
#               }
HV *
swe_lun_eclipse_when(tjd,iflag,ifltype,backw)
  double tjd
  int iflag
  int ifltype
  int backw
PREINIT:
  int i, retval = 0;
  double tret[20];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  SV* svtret;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avtret);
  retval = swe_lun_eclipse_when(tjd,iflag,ifltype,tret,backw,serr);
  for (i=0;i<8;i++) av_push(avtret,(newSVnv(tret[i])));
  svtret = newRV_noinc((SV*) avtret);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "ecl_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "ecl_partial_begin", 17, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "ecl_partial_end", 15, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "ecl_total_begin", 15, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "ecl_total_end", 13, newSVnv(tret[5]), 0);
  (void)hv_store(hp, "ecl_begin", 9, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "ecl_end", 7, newSVnv(tret[7]), 0);
  (void)hv_store(hp, "ecl_penumbral_begin", 19, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "ecl_penumbral_end", 17, newSVnv(tret[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_eclipse_when_loc
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $backw       1, if backward search
#         $geopos      pointer to array of geogr. long., lat., height
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                tret =>,       * pointer to tret array (see docu)
#                attr =>,       * pointer to attr array (see docu)
#                ecl_maximum =>,   * time of max. eclipse
#                ecl_partial_begin =>,   
#                ecl_partial_end =>,       
#                ecl_total_begin =>,       
#                ecl_total_end =>,       
#                ecl_penumbral_begin =>,       
#                ecl_penumbral_end =>,       
#                ecl_tmoonrise =>, * time of moonrise during eclipse
#                ecl_tmoonset =>,  * time of moonset during eclipse
#                mag_umbral =>,  
#                mag_penumbral =>, 
#                moon_azimuth =>,      
#                moon_alt_true =>,      
#                moon_alt_app =>,      
#                separation_angle =>, * of Moon from center of shadow
#               }
HV *
swe_lun_eclipse_when_loc(tjd,iflag,backw,geopos)
  double tjd
  int iflag
  int backw
  SV* geopos  
PREINIT:
  int i, retval = 0;
  double tret[20], attr[20], gp[3];
  char serr[255];
  HV* hp;
  AV* avtret = newAV();
  AV* avattr = newAV();
  SV* svtret;
  SV* svattr;
  AV* _geopos = (AV*) SvRV(geopos);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  av_clear(avtret);
  av_clear(avattr);
  swe_set_topo(gp[0], gp[1], gp[1]);
  retval = swe_lun_eclipse_when_loc(tjd,iflag,gp,tret,attr,backw,serr);
  for (i=0;i<10;i++) av_push(avtret,(newSVnv(tret[i])));
  for (i=0;i<11;i++) av_push(avattr,(newSVnv(attr[i])));
  svtret = newRV_noinc((SV*) avtret);
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "tret", 4, newSVsv(svtret), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "ecl_maximum", 11, newSVnv(tret[0]), 0);
  (void)hv_store(hp, "ecl_partial_begin", 17, newSVnv(tret[2]), 0);
  (void)hv_store(hp, "ecl_partial_end", 15, newSVnv(tret[3]), 0);
  (void)hv_store(hp, "ecl_total_begin", 15, newSVnv(tret[4]), 0);
  (void)hv_store(hp, "ecl_total_end", 13, newSVnv(tret[5]), 0);
  (void)hv_store(hp, "ecl_begin", 9, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "ecl_end", 7, newSVnv(tret[7]), 0);
  (void)hv_store(hp, "ecl_penumbral_begin", 19, newSVnv(tret[6]), 0);
  (void)hv_store(hp, "ecl_penumbral_end", 17, newSVnv(tret[7]), 0);
  (void)hv_store(hp, "ecl_tmoonrise", 13, newSVnv(tret[8]), 0);
  (void)hv_store(hp, "ecl_tmoonset", 12, newSVnv(tret[9]), 0);
  (void)hv_store(hp, "mag_umbral", 10, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "mag_penumbral", 13, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "moon_azimuth", 12, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "moon_alt_true", 13, newSVnv(attr[5]), 0);
  (void)hv_store(hp, "moon_alt_app", 12, newSVnv(attr[6]), 0);
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  (void)hv_store(hp, "saros_series", 12, newSVnv(attr[9]), 0);
  (void)hv_store(hp, "saros_ecl_no", 12, newSVnv(attr[10]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_occult_where
# input:  $tjd         start date for search
#         $ipl
#         $star        star name, if object is a star
#         $iflag       ephemeris flag
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                starname =>,   * fixed star name
#                attr =>,       * pointer to attr array (see docu)
#                geopos =>,     * pointer to geopos array (see docu)
#                disc_ratio =>,        * size ratio of the two discs
#                fraction_diameter =>, * percentage of diameter occulted
#                fraction_disc =>,     * percentage of disc occulted
#                core_shadow_km =>,    * diameter of core shadow (km, negative)
#                body_azimuth =>,      
#                body_alt_true =>,      
#                separation_angle =>,      
#                geo_long =>,      
#                geo_lat =>,      
#               }
HV *
swe_lun_occult_where(tjd,ipl,star,iflag)
  double tjd
  int ipl
  char *star
  int iflag
PREINIT:
  int i, retval = 0;
  double attr[20], gp[3];
  char _star[255];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  AV* avgeopos = newAV();
  SV* svattr;
  SV* svgeopos;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  *serr = '\0';
  av_clear(avgeopos);
  av_clear(avattr);
  retval = swe_lun_occult_where(tjd,ipl,_star,iflag,gp,attr,serr);
  if (attr[0] > 1) attr[0] = 1; /* should be fixed in Swisseph code */
  if (attr[2] > 1) attr[2] = 1; /* should be fixed in Swisseph code */
  for (i=0;i<3;i++) av_push(avgeopos,(newSVnv(gp[i])));
  for (i=0;i<8;i++) av_push(avattr,(newSVnv(attr[i])));
  svgeopos = newRV_noinc((SV*) avgeopos);
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  (void)hv_store(hp, "geopos", 6, newSVsv(svgeopos), 0);
  (void)hv_store(hp, "geo_long", 8, newSVnv(gp[0]), 0);
  (void)hv_store(hp, "geo_lat", 7, newSVnv(gp[1]), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "fraction_diameter", 17, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "disc_ratio", 10, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "fraction_disc", 13, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "core_shadow_km", 14, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "body_azimuth", 12, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "body_alt_true", 13, newSVnv(attr[5]), 0);
  /*(void)hv_store(hp, "body_alt_app", 12, newSVnv(attr[6]), 0);*/
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_sol_eclipse_where
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                attr =>,       * pointer to attr array (see docu)
#                geopos =>,     * pointer to geopos array (see docu)
#                disc_ratio =>,        * size ratio of the two discs
#                fraction_diameter =>, * percentage of diameter occulted
#                fraction_disc =>,     * percentage of disc occulted
#                core_shadow_km =>,    * diameter of core shadow (km, negative)
#                sun_azimuth =>,      
#                sun_alt_true =>,      
#                separation_angle =>,      
#                geo_long =>,      
#                geo_lat =>,      
#               }
HV *
swe_sol_eclipse_where(tjd,iflag)
  double tjd
  int iflag
PREINIT:
  int i, retval = 0;
  double attr[20], gp[3];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  AV* avgeopos = newAV();
  SV* svattr;
  SV* svgeopos;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avgeopos);
  av_clear(avattr);
  retval = swe_sol_eclipse_where(tjd,iflag,gp,attr,serr);
  for (i=0;i<3;i++) av_push(avgeopos,(newSVnv(gp[i])));
  for (i=0;i<11;i++) av_push(avattr,(newSVnv(attr[i])));
  svgeopos = newRV_noinc((SV*) avgeopos);
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "geopos", 6, newSVsv(svgeopos), 0);
  (void)hv_store(hp, "geo_long", 8, newSVnv(gp[0]), 0);
  (void)hv_store(hp, "geo_lat", 7, newSVnv(gp[1]), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "fraction_diameter", 17, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "disc_ratio", 10, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "fraction_disc", 13, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "core_shadow_km", 14, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "sun_azimuth", 11, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "sun_alt_true", 12, newSVnv(attr[5]), 0);
  /*(void)hv_store(hp, "body_alt_app", 12, newSVnv(attr[6]), 0);*/
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  (void)hv_store(hp, "saros_series", 12, newSVnv(attr[9]), 0);
  (void)hv_store(hp, "saros_ecl_no", 12, newSVnv(attr[10]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_sol_eclipse_how
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $geopos      pointer to array of geogr. long., lat., height
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                attr =>,       * pointer to attr array (see docu)
#                disc_ratio =>,        * size ratio of the two discs
#                fraction_diameter =>, * percentage of diameter occulted
#                fraction_disc =>,     * percentage of disc occulted
#                core_shadow_km =>,    * diameter of core shadow (km, negative)
#                sun_azimuth =>,      
#                sun_alt_true =>,      
#                sun_alt_app =>,      
#                separation_angle =>,      
#               }
HV *
swe_sol_eclipse_how(tjd,iflag,geopos)
  double tjd
  I32 iflag
  SV* geopos  
PREINIT:
  I32 i, retval = 0;
  double attr[20], gp[3];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  SV* svattr;
  AV* _geopos = (AV*) SvRV(geopos);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  av_clear(avattr);
  swe_set_topo(gp[0], gp[1], gp[2]);
  retval = swe_sol_eclipse_how(tjd,iflag,gp,attr,serr);
  for (i=0;i<11;i++) av_push(avattr,(newSVnv(attr[i])));
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "fraction_diameter", 17, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "disc_ratio", 10, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "fraction_disc", 13, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "core_shadow_km", 14, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "sun_azimuth", 11, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "sun_alt_true", 12, newSVnv(attr[5]), 0);
  (void)hv_store(hp, "sun_alt_app", 11, newSVnv(attr[6]), 0);
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  (void)hv_store(hp, "saros_series", 12, newSVnv(attr[9]), 0);
  (void)hv_store(hp, "saros_ecl_no", 12, newSVnv(attr[10]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_lun_eclipse_how
# input:  $tjd         start date for search
#         $iflag       ephemeris flag
#         $geopos      pointer to array of geogr. long., lat., height
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                attr =>,       * pointer to attr array (see docu)
#                mag_umbral =>,        * umbral magnitude
#                mag_penumbral =>,     * penumbral magnitude
#                moon_azimuth =>,      
#                moon_alt_true =>,      
#                moon_alt_app =>,      
#                separation_angle =>, * of Moon from center of shadow
#               }
HV *
swe_lun_eclipse_how(tjd,iflag,geopos)
  double tjd
  int iflag
  SV* geopos  
PREINIT:
  int i, retval = 0;
  double attr[20], gp[3];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  SV* svattr;
  AV* _geopos = (AV*) SvRV(geopos);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  av_clear(avattr);
  swe_set_topo(gp[0], gp[1], gp[1]);
  retval = swe_lun_eclipse_how(tjd,iflag,gp,attr,serr);
  for (i=0;i<11;i++) av_push(avattr,(newSVnv(attr[i])));
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "mag_umbral", 10, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "mag_penumbral", 13, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "moon_azimuth", 12, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "moon_alt_true", 13, newSVnv(attr[5]), 0);
  (void)hv_store(hp, "moon_alt_app", 12, newSVnv(attr[6]), 0);
  (void)hv_store(hp, "separation_angle", 16, newSVnv(attr[7]), 0);
  (void)hv_store(hp, "saros_series", 12, newSVnv(attr[9]), 0);
  (void)hv_store(hp, "saros_ecl_no", 12, newSVnv(attr[10]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_pheno
# input:  $tjd         start date for search
#         $ipl   
#         $iflag       ephemeris flag
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                attr =>,       * pointer to attr array (see docu)
#                phase_angle =>,* phase angle (earth-planet-sun)
#                phase =>,      * phase (illumined fraction of disc)
#                elongation =>, * 
#                disc_diameter =>, * 
#                magnitude =>,     * 
#                hor_parallax =>,  * horizontal parallax (Moon) 
#               }
HV *
swe_pheno(tjd,ipl,iflag)
  double tjd
  int ipl
  int iflag
PREINIT:
  int i, retval = 0;
  double attr[20];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  SV* svattr;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avattr);
  retval = swe_pheno(tjd,ipl,iflag,attr,serr);
  for (i=0;i<8;i++) av_push(avattr,(newSVnv(attr[i])));
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "phase_angle", 11, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "phase", 5, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "elongation", 10, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "disc_diameter", 13, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "magnitude", 9, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "hor_parallax", 12, newSVnv(attr[5]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_pheno_ut
# input:  $tjd         start date for search
#         $ipl   
#         $iflag       ephemeris flag
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                attr =>,       * pointer to attr array (see docu)
#                phase_angle =>,* phase angle (earth-planet-sun)
#                phase =>,      * phase (illumined fraction of disc)
#                elongation =>, * 
#                disc_diameter =>, * 
#                magnitude =>,     * 
#                hor_parallax =>,  * horizontal parallax (Moon) 
#               }
HV *
swe_pheno_ut(tjd,ipl,iflag)
  double tjd
  int ipl
  int iflag
PREINIT:
  int i, retval = 0;
  double attr[20];
  char serr[255];
  HV* hp;
  AV* avattr = newAV();
  SV* svattr;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avattr);
  retval = swe_pheno_ut(tjd,ipl,iflag,attr,serr);
  for (i=0;i<8;i++) av_push(avattr,(newSVnv(attr[i])));
  svattr = newRV_noinc((SV*) avattr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "attr", 4, newSVsv(svattr), 0);
  (void)hv_store(hp, "phase_angle", 11, newSVnv(attr[0]), 0);
  (void)hv_store(hp, "phase", 5, newSVnv(attr[1]), 0);
  (void)hv_store(hp, "elongation", 10, newSVnv(attr[2]), 0);
  (void)hv_store(hp, "disc_diameter", 13, newSVnv(attr[3]), 0);
  (void)hv_store(hp, "magnitude", 9, newSVnv(attr[4]), 0);
  (void)hv_store(hp, "hor_parallax", 12, newSVnv(attr[5]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_refrac_extended
# input:  $inalt       altitude of object above geometric horizon in degrees
#         $geoalt      altitude of observer above sea level in meters
#         $atpress     atmospheric pressure
#         $attemp      atmospheric temperature (hectopascal)
#         $lapserate   (dT/dh) [°K/m]
#         $calcflag    SE_TRUE_TO_APP (0) or SE_APP_TO_TRUE (1)
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                dret =>,       * pointer to dret array (see docu)
#                alt_true =>,   * true altitude
#                alt_apparent =>,   * apparent altitude
#                refraction =>,     
#                dip =>,        * dip of the horizon
#               }
HV *
swe_refrac_extended(inalt,geoalt,atpress,attemp,lapserate,calcflag)
  double inalt
  double geoalt
  double atpress
  double attemp
  double lapserate
  int calcflag
PREINIT:
  int i, retval = 0;
  double dret[4];
  char serr[255];
  HV* hp;
  AV* avdret = newAV();
  SV* svdret;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  av_clear(avdret);
  swe_refrac_extended(inalt, geoalt, atpress, attemp, lapserate, calcflag, dret);
  for (i=0;i<4;i++) av_push(avdret,(newSVnv(dret[i])));
  svdret = newRV_noinc((SV*) avdret);
  (void)hv_store(hp, "dret", 4, newSVsv(svdret), 0);
  retval = 0;
  if (calcflag == 0) { /* true to apparent */
    (void)hv_store(hp, "alt_true", 8, newSVnv(dret[0]), 0);
    if (dret[1] != dret[0]) {
      (void)hv_store(hp, "alt_apparent", 12, newSVnv(dret[1]), 0);
    } else {
      strcpy(serr, "body is invisible");
      (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
      retval = -1;
    }
  } else { /* apparent to true */
    if (inalt >= dret[3]) {
      (void)hv_store(hp, "alt_apparent", 12, newSVnv(dret[1]), 0);
      (void)hv_store(hp, "alt_true", 8, newSVnv(dret[0]), 0);
    } else {
      strcpy(serr, "body is invisible");
      (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
      retval = -1;
    }
  }
  (void)hv_store(hp, "refraction", 10, newSVnv(dret[2]), 0);
  (void)hv_store(hp, "dip", 3, newSVnv(dret[3]), 0);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # Direct translation of swe_refrac()
double
swe_refrac(inalt,atpress,attemp,calcflag)
  double inalt
  double atpress
  double attemp
  int calcflag
CODE:  
  RETVAL = swe_refrac(inalt,atpress,attemp,calcflag);
OUTPUT:
  RETVAL

 # swe_set_lapse_rate()
void
swe_set_lapse_rate(lapserate) 
  double lapserate
CODE:  
  swe_set_lapse_rate(lapserate);

# swe_azalt
HV *
swe_azalt_test(tjd,calcflag,geopos, atpress, attemp,xin)
  double tjd
  int calcflag
  SV* geopos  
  double atpress
  double attemp
  SV* xin  
PREINIT:
  int i;
  double xaz[3], gp[3], xi[2];
  HV* hp;
  AV* avxaz = newAV();
  SV* svxaz;
  AV* _geopos = (AV*) SvRV(geopos);
  AV* _xin = (AV*) SvRV(xin);
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  for (i=0; i<2; i++) xi[i] = SvNV(*av_fetch(_xin, i, TRUE));
  av_clear(avxaz);
  swe_azalt(tjd,calcflag,gp,atpress,attemp,xi,xaz);
  for (i=0;i<3;i++) av_push(avxaz,(newSVnv(xaz[i])));
  svxaz = newRV_noinc((SV*) avxaz);
  (void)hv_store(hp, "xaz", 3, newSVsv(svxaz), 0);
  (void)hv_store(hp, "azimuth", 7, newSVnv(xaz[0]), 0);
  (void)hv_store(hp, "alt_true", 8, newSVnv(xaz[1]), 0);
  if (xaz[2] != xaz[1])
    (void)hv_store(hp, "alt_app", 7, newSVnv(xaz[2]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_azalt
# input:  $tjd         start date for search
#         $calcflag    either SE_ECL2HOR or SE_EQU2HOR
#         $geopos      pointer to array of geogr. long., lat., height
#         $atpress     atmospheric pressure
#         $attemp      atmospheric temperature
#         $xin         pointer to array of long./lat. or ra./de
# output: @aret        azimuth, true altitude, apparent altitude (if possible)
SV*
swe_azalt(tjd,calcflag,geopos,atpress,attemp,xin) 
  double tjd
  int calcflag
  SV* geopos  
  double atpress
  double attemp
  SV* xin
PPCODE:
  int i;
  double gp[3], xaz[3], xp[3];
  AV* _geopos = (AV*) SvRV(geopos);
  AV* _xp = (AV*) SvRV(xin);
  xp[0] = SvNV(*av_fetch(_xp, 0, TRUE));
  xp[1] = SvNV(*av_fetch(_xp, 1, TRUE));
  xp[2] = 1;
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  swe_azalt(tjd,calcflag,gp,atpress,attemp,xp,xaz);
  for (i = 0; i < 3; i++)
    XPUSHs(newSVnv(xaz[i]));
  PERL_UNUSED_VAR(RETVAL);

# swe_azalt_rev
# input:  $tjd         start date for search
#         $calcflag    either SE_ECL2HOR or SE_EQU2HOR
#         $geopos      pointer to array of geogr. long., lat., height
#         $xin         pointer to array of azimuth and true altitude
# output: @aret        ecl. longitude/latitude or ra/decl
SV*
swe_azalt_rev(tjd,calcflag,geopos,xin) 
  double tjd
  int calcflag
  SV* geopos  
  SV* xin
PPCODE:
  int i;
  double gp[3], xout[3], xp[3];
  AV* _geopos = (AV*) SvRV(geopos);
  AV* _xp = (AV*) SvRV(xin);
  xp[0] = SvNV(*av_fetch(_xp, 0, TRUE));
  xp[1] = SvNV(*av_fetch(_xp, 1, TRUE));
  xp[2] = 1;
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  swe_azalt_rev(tjd,calcflag,gp,xp,xout);
  for (i = 0; i < 2; i++)
    XPUSHs(newSVnv(xout[i]));
  PERL_UNUSED_VAR(RETVAL);

 # swe_rise_trans
 # input:  $tjd
 #         $ipl
 #         $star        star name, if object is a star
 #         $epheflag    ephemeris flag
 #         $rsmi        calc flag:
 #                      SE_CALC_RISE, SE_CALC_SET, SE_CALC_MTRANSIT, 
 #                      SE_CALC_ITRANSIT
 #                      | SE_BIT_DISC_CENTER   for rises of disc center of body
 #                      | SE_BIT_NO_REFRACTION to neglect refraction
 #         $geopos      pointer to array of geogr. long., lat., height
 #         $atpress     atmospheric pressure
 #         $attemp      atmospheric temperature
 # output: $hp = {retval =>,   
 #                serr =>,       * error string, on error only
 #                dret =>,       * tjd of rising time etc.
 #                starname =>,   * fixed star name
 #               }
HV *
swe_rise_trans(tjd,ipl,star,epheflag,rsmi,geopos,atpress,attemp)
  double tjd
  int ipl
  char *star
  int epheflag
  int rsmi
  SV* geopos  
  double atpress
  double attemp
PREINIT:
  int i, retval = 0;
  double dret = 0, gp[3];
  char _star[255];
  char serr[255];
  HV* hp;
CODE:
  AV* _geopos = (AV*) SvRV(geopos);
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  retval = swe_rise_trans(tjd,ipl,_star,epheflag,rsmi,gp,atpress,attemp,&dret,serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dret", 4, newSVnv(dret), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

 # swe_rise_trans_true_hor
 # input:  $tjd
 #         $ipl
 #         $star        star name, if object is a star
 #         $epheflag    ephemeris flag
 #         $rsmi        calc flag:
 #                      SE_CALC_RISE, SE_CALC_SET, SE_CALC_MTRANSIT, 
 #                      SE_CALC_ITRANSIT
 #                      | SE_BIT_DISC_CENTER   for rises of disc center of body
 #                      | SE_BIT_NO_REFRACTION to neglect refraction
 #         $geopos      pointer to array of geogr. long., lat., height
 #         $atpress     atmospheric pressure
 #         $attemp      atmospheric temperature
 #         $horhgt      height of the horizon
 # output: $hp = {retval =>,   
 #                serr =>,       * error string, on error only
 #                dret =>,       * tjd of rising time etc.
 #                starname =>,   * fixed star name
 #               }
HV *
swe_rise_trans_true_hor(tjd,ipl,star,epheflag,rsmi,geopos,atpress,attemp,horhgt)
  double tjd
  int ipl
  char *star
  int epheflag
  int rsmi
  SV* geopos  
  double atpress
  double attemp
  double horhgt
PREINIT:
  int i, retval = 0;
  double dret = 0, gp[3];
  char _star[255];
  char serr[255];
  HV* hp;
CODE:
  AV* _geopos = (AV*) SvRV(geopos);
  hp = (HV *)sv_2mortal((SV *)newHV());
  strcpy(_star, star);
  for (i=0; i<3; i++) gp[i] = SvNV(*av_fetch(_geopos, i, TRUE));
  *serr = '\0';
  retval = swe_rise_trans_true_hor(tjd,ipl,_star,epheflag,rsmi,gp,atpress,attemp,horhgt,&dret,serr);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  (void)hv_store(hp, "dret", 4, newSVnv(dret), 0);
  (void)hv_store(hp, "starname", 8, newSVpvn(_star, strlen(_star)), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_nod_aps
# input:  $tjd
#         $ipl
#         $iflag
#         $method     for explanation, see docu
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                xnasc =>,      * pointer to position array of ascending node
#                xndsc =>,      * pointer to position array of descending node
#                xperi =>,      * pointer to position array of perihelion
#                xaphe =>,      * pointer to position array of aphelion
#               }
HV *
swe_nod_aps(tjd,ipl,iflag,method)
  double tjd
  int ipl
  int iflag
  int method
PREINIT:
  int i, retval;
  double xnasc[6], xndsc[6], xperi[6], xaphe[6];
  char serr[255];
  HV* hp;
  AV* avxnasc = newAV(); AV* avxndsc = newAV(); 
  AV* avxperi = newAV(); AV* avxaphe = newAV();
  SV* svxnasc; SV* svxndsc; SV* svxperi; SV* svxaphe;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxnasc); av_clear(avxndsc); av_clear(avxperi); av_clear(avxaphe);
  *serr = '\0';
  retval = swe_nod_aps(tjd,ipl,iflag,method,xnasc,xndsc,xperi,xaphe,serr);
  for (i=0;i<6;i++) av_push(avxnasc,(newSVnv(xnasc[i])));
  for (i=0;i<6;i++) av_push(avxndsc,(newSVnv(xndsc[i])));
  for (i=0;i<6;i++) av_push(avxperi,(newSVnv(xperi[i])));
  for (i=0;i<6;i++) av_push(avxaphe,(newSVnv(xaphe[i])));
  svxnasc = newRV_noinc((SV*) avxnasc);
  svxndsc = newRV_noinc((SV*) avxndsc);
  svxperi = newRV_noinc((SV*) avxperi);
  svxaphe = newRV_noinc((SV*) avxaphe);
  (void)hv_store(hp, "xnasc", 5, newSVsv(svxnasc), 0);
  (void)hv_store(hp, "xndsc", 5, newSVsv(svxndsc), 0);
  (void)hv_store(hp, "xperi", 5, newSVsv(svxperi), 0);
  (void)hv_store(hp, "xaphe", 5, newSVsv(svxaphe), 0);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_nod_aps_ut
# input:  $tjd_ut
#         $ipl
#         $iflag
#         $method     for explanation, see docu
# output: $hp = {retval =>,   
#                serr =>,       * error string, on error only
#                xnasc =>,      * pointer to position array of ascending node
#                xndsc =>,      * pointer to position array of descending node
#                xperi =>,      * pointer to position array of perihelion
#                xaphe =>,      * pointer to position array of aphelion
#               }
HV *
swe_nod_aps_ut(tjd,ipl,iflag,method)
  double tjd
  int ipl
  int iflag
  int method
PREINIT:
  int i, retval;
  double xnasc[6], xndsc[6], xperi[6], xaphe[6];
  char serr[255];
  HV* hp;
  AV* avxnasc = newAV(); AV* avxndsc = newAV(); 
  AV* avxperi = newAV(); AV* avxaphe = newAV();
  SV* svxnasc; SV* svxndsc; SV* svxperi; SV* svxaphe;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  av_clear(avxnasc); av_clear(avxndsc); av_clear(avxperi); av_clear(avxaphe);
  *serr = '\0';
  retval = swe_nod_aps_ut(tjd,ipl,iflag,method,xnasc,xndsc,xperi,xaphe,serr);
  for (i=0;i<6;i++) av_push(avxnasc,(newSVnv(xnasc[i])));
  for (i=0;i<6;i++) av_push(avxndsc,(newSVnv(xndsc[i])));
  for (i=0;i<6;i++) av_push(avxperi,(newSVnv(xperi[i])));
  for (i=0;i<6;i++) av_push(avxaphe,(newSVnv(xaphe[i])));
  svxnasc = newRV_noinc((SV*) avxnasc);
  svxndsc = newRV_noinc((SV*) avxndsc);
  svxperi = newRV_noinc((SV*) avxperi);
  svxaphe = newRV_noinc((SV*) avxaphe);
  (void)hv_store(hp, "xnasc", 5, newSVsv(svxnasc), 0);
  (void)hv_store(hp, "xndsc", 5, newSVsv(svxndsc), 0);
  (void)hv_store(hp, "xperi", 5, newSVsv(svxperi), 0);
  (void)hv_store(hp, "xaphe", 5, newSVsv(svxaphe), 0);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_heliacal_ut
HV *
swe_heliacal_ut(tjdstart,dgeo,datm,dobs,objname,type_event,helflag)
  double tjdstart
  SV* dgeo
  SV* datm
  SV* dobs
  char* objname
  int type_event
  int helflag
PREINIT:
  int i, retval;
  AV* _dgeo;
  AV* _datm;
  AV* _dobs;
  double __dgeo[3], __datm[4], __dobs[6], dret[50];
  char serr[255];
  AV* avdret = newAV();
  SV* svdret;
  HV* hp;
CODE:
  for (i = 0; i < 50; i++)
    dret[i] = 0;
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  _dgeo = (AV*) SvRV(dgeo);
  for (i=0; i<3; i++) __dgeo[i] = SvNV(*av_fetch(_dgeo, i, TRUE));
  _datm = (AV*) SvRV(datm);
  for (i=0; i<4; i++) __datm[i] = SvNV(*av_fetch(_datm, i, TRUE));
  _dobs = (AV*) SvRV(dobs);
  for (i=0; i<6; i++) __dobs[i] = SvNV(*av_fetch(_dobs, i, TRUE));
  retval = swe_heliacal_ut(tjdstart,__dgeo,__datm,__dobs,objname,type_event,helflag,dret,serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  for (i=0;i<=30;i++) av_push(avdret,(newSVnv(dret[i])));
  svdret = newRV_noinc((SV*) avdret);
  (void)hv_store(hp, "dret", 4, newSVsv(svdret), 0);
  (void)hv_store(hp, "tstart", 6, newSVnv(dret[0]), 0);
  if (dret[1] > 0)
    (void)hv_store(hp, "topt", 4, newSVnv(dret[1]), 0);
  if (dret[2] > 0)
    (void)hv_store(hp, "tend", 4, newSVnv(dret[2]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_pheno_heliacal_ut
HV *
swe_heliacal_pheno_ut(tjdstart,dgeo,datm,dobs,objname,type_event,helflag)
  double tjdstart
  SV* dgeo
  SV* datm
  SV* dobs
  char* objname
  int type_event
  int helflag
PREINIT:
  int i, retval;
  AV* _dgeo;
  AV* _datm;
  AV* _dobs;
  double __dgeo[3], __datm[4], __dobs[6], dret[50];
  char serr[255];
  AV* avdret = newAV();
  SV* svdret;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  _dgeo = (AV*) SvRV(dgeo);
  for (i=0; i<3; i++) __dgeo[i] = SvNV(*av_fetch(_dgeo, i, TRUE));
  _datm = (AV*) SvRV(datm);
  for (i=0; i<4; i++) __datm[i] = SvNV(*av_fetch(_datm, i, TRUE));
  _dobs = (AV*) SvRV(dobs);
  for (i=0; i<6; i++) __dobs[i] = SvNV(*av_fetch(_dobs, i, TRUE));
  retval = swe_heliacal_pheno_ut(tjdstart,__dgeo,__datm,__dobs,objname,type_event,helflag,dret,serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  for (i=0;i<=30;i++) av_push(avdret,(newSVnv(dret[i])));
  svdret = newRV_noinc((SV*) avdret);
  (void)hv_store(hp, "dret", 4, newSVsv(svdret), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

# swe_vis_limit_mag
HV *
swe_vis_limit_mag(tjdstart,dgeo,datm,dobs,objname,helflag)
  double tjdstart
  SV* dgeo
  SV* datm
  SV* dobs
  char* objname
  int helflag
PREINIT:
  int i, retval;
  AV* _dgeo;
  AV* _datm;
  AV* _dobs;
  double __dgeo[3], __datm[4], __dobs[6], dret[50];
  char serr[255];
  AV* avdret = newAV();
  SV* svdret;
  HV* hp;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  *serr = '\0';
  _dgeo = (AV*) SvRV(dgeo);
  for (i=0; i<3; i++) __dgeo[i] = SvNV(*av_fetch(_dgeo, i, TRUE));
  _datm = (AV*) SvRV(datm);
  for (i=0; i<4; i++) __datm[i] = SvNV(*av_fetch(_datm, i, TRUE));
  _dobs = (AV*) SvRV(dobs);
  for (i=0; i<6; i++) __dobs[i] = SvNV(*av_fetch(_dobs, i, TRUE));
  retval = swe_vis_limit_mag(tjdstart,__dgeo,__datm,__dobs,objname,helflag,dret,serr);
  (void)hv_store(hp, "retval", 6, newSViv(retval), 0);
  if (*serr != '\0') {
    (void)hv_store(hp, "serr", 4, newSVpvn(serr, strlen(serr)), 0);
  }
  for (i=0;i<=7;i++) av_push(avdret,(newSVnv(dret[i])));
  svdret = newRV_noinc((SV*) avdret);
  (void)hv_store(hp, "dret", 4, newSVsv(svdret), 0);
  (void)hv_store(hp, "dvislim", 7, newSVnv(dret[0]), 0);
  (void)hv_store(hp, "daltobj", 7, newSVnv(dret[1]), 0);
  (void)hv_store(hp, "daziobj", 7, newSVnv(dret[2]), 0);
  (void)hv_store(hp, "daltsun", 7, newSVnv(dret[3]), 0);
  (void)hv_store(hp, "dazisun", 7, newSVnv(dret[4]), 0);
  (void)hv_store(hp, "daltmoo", 7, newSVnv(dret[5]), 0);
  (void)hv_store(hp, "dazimoo", 7, newSVnv(dret[6]), 0);
  (void)hv_store(hp, "dmag", 4, newSVnv(dret[7]), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL

HV *
swe_split_deg(ddeg, roundflag)
  double ddeg
  int roundflag
PREINIT:
  HV *hp;
  int ideg, imin, isec, isgn;
  double dsecfr;
CODE:
  hp = (HV *)sv_2mortal((SV *)newHV());
  swe_split_deg(ddeg, roundflag, &ideg, &imin, &isec, &dsecfr, &isgn);
  (void)hv_store(hp, "ideg", 4, newSViv(ideg), 0);
  (void)hv_store(hp, "imin", 4, newSViv(imin), 0);
  (void)hv_store(hp, "isec", 4, newSViv(isec), 0);
  (void)hv_store(hp, "dsecfr", 6, newSVnv(dsecfr), 0);
  (void)hv_store(hp, "isgn", 4, newSViv(isgn), 0);
  RETVAL = hp; 
OUTPUT:
  RETVAL
