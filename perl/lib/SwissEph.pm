#  Copyright (C) 1997 - 2008 Astrodienst AG, Switzerland.  All rights reserved.
#
# License conditions
# ------------------
#
# This file is part of Swiss Ephemeris.
#
# Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
# or distributor accepts any responsibility for the consequences of using it,
# or for whether it serves any particular purpose or works at all, unless he
# or she says so in writing.
#
# Swiss Ephemeris is made available by its authors under a dual licensing
# system. The software developer, who uses any part of Swiss Ephemeris
# in his or her software, must choose between one of the two license models,
# which are
# a) GNU public license version 2 or later
# b) Swiss Ephemeris Professional License
#
# The choice must be made before the software developer distributes software
# containing parts of Swiss Ephemeris to others, and before any public
# service using the developed software is activated.
#
# If the developer choses the GNU GPL software license, he or she must fulfill
# the conditions of that license, which includes the obligation to place his
# or her whole software project under the GNU GPL or a compatible license.
# See http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
#
# If the developer choses the Swiss Ephemeris Professional license,
# he must follow the instructions as found in http://www.astro.com/swisseph/
# and purchase the Swiss Ephemeris Professional Edition from Astrodienst
# and sign the corresponding license contract.
#
# The License grants you the right to use, copy, modify and redistribute
# Swiss Ephemeris, but only under certain conditions described in the License.
# Among other things, the License requires that the copyright notices and
# this notice be preserved on all copies.
#
# Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl
#
# The authors of Swiss Ephemeris have no control or influence over any of
# the derived works, i.e. over software or services created by other
# programmers which use Swiss Ephemeris functions.
#
# The names of the authors or of the copyright holder (Astrodienst) must not
# be used for promoting any software, product or service which uses or contains
# the Swiss Ephemeris. This copyright notice is the ONLY place where the
# names of the authors can legally appear, except in cases where they have
# given special permission in writing.
#
# The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
# for promoting such software, products or services.

package SwissEph;

use 5.008007;
use strict;
use warnings;

require Exporter;

our $VERSION = "1.80.00";

our @ISA = qw(Exporter);

our @SWE_FUNCTIONS = qw(
        swe_utc_time_zone
	swe_azalt swe_azalt_rev swe_calc swe_calc_ut swe_close 
	swe_cotrans swe_cotrans_sp swe_day_of_week swe_deg_midp 
	swe_degnorm swe_deltat swe_difdegn swe_difdeg2n swe_difrad2n
	swe_fixstar swe_fixstar_mag swe_fixstar_ut swe_gauquelin_sector
	swe_get_ayanamsa swe_get_ayanamsa_ut swe_get_ayanamsa_name
	swe_get_planet_name swe_get_tid_acc
	swe_heliacal_ut swe_heliacal_pheno_ut swe_vis_limit_mag
	swe_houses swe_houses_armc swe_houses_ex swe_house_pos swe_julday 
	swe_house_name
	swe_lun_occult_when_glob swe_lun_occult_when_loc 
	swe_lun_occult_where swe_nod_aps swe_pheno swe_pheno_ut swe_rad_midp
	swe_radnorm swe_refrac swe_refrac_extended swe_revjul swe_rise_trans 
	swe_set_ephe_path swe_set_jpl_file swe_set_sid_mode
	swe_set_tid_acc swe_set_topo swe_sidtime swe_sidtime0
	swe_sol_eclipse_how swe_sol_eclipse_when_glob 
	swe_sol_eclipse_when_loc swe_sol_eclipse_where swe_time_equ
	swe_utc_to_jd swe_jdet_to_utc swe_jdut1_to_utc
	swe_version swe_version0
	swe_split_deg
	);
#	swe_rise_trans_true_hor

our @SWE_CONSTANTS = qw(
	    SE_ECL_NUT SE_SUN SE_MOON SE_MERCURY SE_VENUS SE_MARS SE_JUPITER 
	    SE_SATURN SE_URANUS SE_NEPTUNE SE_PLUTO SE_MEAN_NODE
	    SE_TRUE_NODE SE_MEAN_APOG SE_OSCU_APOG
	    SE_EARTH SE_CHIRON SE_PHOLUS SE_CERES SE_PALLAS SE_JUNO 
	    SE_VESTA SE_INTP_APOG SE_INTP_PERG 
	    SE_NPLANETS SE_AST_OFFSET SE_VARUNA 
	    SE_FICT_OFFSET SE_FICT_OFFSET_1 SE_FICT_MAX SE_NFICT_ELEM
	    SE_NALL_NAT_POINTS SE_COMET_OFFSET 
	    SE_CUPIDO SE_HADES SE_ZEUS SE_KRONOS SE_APOLLON SE_ADMETOS 
	    SE_VULKANUS SE_POSEIDON SE_ISIS SE_NIBIRU 
	    SE_HARRINGTON SE_NEPTUNE_LEVERRIER SE_NEPTUNE_ADAMS 
	    SE_PLUTO_LOWELL SE_PLUTO_PICKERING 
	    SE_VULCAN SE_WHITE_MOON SE_PROSERPINA SE_WALDEMATH
	    SE_FIXSTAR SE_ASC SE_MC SE_ARMC SE_VERTEX SE_EQUASC 
	    SE_COASC1 SE_COASC2 SE_POLASC SE_NASCMC
	    SEFLG_JPLEPH SEFLG_SWIEPH SEFLG_MOSEPH SEFLG_HELCTR SEFLG_TRUEPOS 
	    SEFLG_J2000 SEFLG_NONUT SEFLG_SPEED3 SEFLG_SPEED SEFLG_NOGDEFL 
	    SEFLG_NOABERR SEFLG_EQUATORIAL SEFLG_XYZ SEFLG_RADIANS 
	    SEFLG_BARYCTR SEFLG_TOPOCTR SEFLG_SIDEREAL SEFLG_ICRS
	    SEFLG_DEFAULTEPH
	    SE_SIDM_FAGAN_BRADLEY SE_SIDM_LAHIRI SE_SIDM_DELUCE SE_SIDM_RAMAN 
	    SE_SIDM_USHASHASHI SE_SIDM_KRISHNAMURTI SE_SIDM_DJWHAL_KHUL 
	    SE_SIDM_YUKTESHWAR SE_SIDM_JN_BHASIN SE_SIDM_BABYL_KUGLER1 
	    SE_SIDM_BABYL_KUGLER2 SE_SIDM_BABYL_KUGLER3 SE_SIDM_BABYL_HUBER 
	    SE_SIDM_BABYL_ETPSC SE_SIDM_ALDEBARAN_15TAU SE_SIDM_HIPPARCHOS 
	    SE_SIDM_SASSANIAN SE_SIDM_GALCENT_0SAG SE_SIDM_J2000 
	    SE_SIDM_J1900 SE_SIDM_B1950 SE_SIDM_USER SE_NSIDM_PREDEF
	    SE_NODBIT_MEAN SE_NODBIT_OSCU SE_NODBIT_OSCU_BAR SE_NODBIT_FOPOINT
	    SE_JUL_CAL SE_GREG_CAL
	    SE_ECL_CENTRAL SE_ECL_NONCENTRAL SE_ECL_TOTAL SE_ECL_ANNULAR 
	    SE_ECL_PARTIAL SE_ECL_ANNULAR_TOTAL SE_ECL_PENUMBRAL 
	    SE_ECL_VISIBLE SE_ECL_1ST_VISIBLE SE_ECL_2ND_VISIBLE 
	    SE_ECL_3RD_VISIBLE SE_ECL_4TH_VISIBLE SE_ECL_ONE_TRY
	    SE_CALC_RISE SE_CALC_SET SE_CALC_MTRANSIT SE_CALC_ITRANSIT 
	    SE_BIT_DISC_CENTER SE_BIT_NO_REFRACTION SE_BIT_CIVIL_TWILIGHT 
	    SE_BIT_NAUTIC_TWILIGHT SE_BIT_ASTRO_TWILIGHT SE_ECL2HOR 
	    SE_EQU2HOR SE_HOR2ECL SE_HOR2EQU SE_TRUE_TO_APP SE_APP_TO_TRUE
	    SE_SPLIT_DEG_ROUND_SEC SE_SPLIT_DEG_ROUND_MIN SE_SPLIT_DEG_ROUND_DEG
	    SE_SPLIT_DEG_ZODIACAL SE_SPLIT_DEG_KEEP_SIGN SE_SPLIT_DEG_KEEP_DEG
	    SE_HELIACAL_RISING SE_HELIACAL_SETTING 
            SE_EVENING_FIRST SE_MORNING_LAST
	    SE_HELFLAG_LONG_SEARCH SE_HELFLAG_HIGH_PRECISION
            SE_HELFLAG_OPTICAL_PARAMS SE_HELFLAG_NO_DETAILS
            );

our @EXPORT = qw();
our @EXPORT_OK = (@SWE_FUNCTIONS, @SWE_CONSTANTS);
our %EXPORT_TAGS = (
        all => [ @EXPORT_OK, @EXPORT ],
        functions => \@SWE_FUNCTIONS, 
        constants => \@SWE_CONSTANTS
        );

require XSLoader;
XSLoader::load('SwissEph', $VERSION);

sub swe_version
{
  my $hp = {version_sweperl => $VERSION,
            version_swisseph => SwissEph::swe_version0(),
  };
  return $hp;
}

use constant SE_JUL_CAL => 0;
use constant SE_GREG_CAL => 1;

use constant SE_ECL_NUT => -1;
use constant SE_SUN => 0;
use constant SE_MOON => 1;
use constant SE_MERCURY => 2;
use constant SE_VENUS => 3;
use constant SE_MARS => 4;
use constant SE_JUPITER => 5;
use constant SE_SATURN => 6;
use constant SE_URANUS => 7;
use constant SE_NEPTUNE => 8;
use constant SE_PLUTO => 9;
use constant SE_MEAN_NODE => 10;
use constant SE_TRUE_NODE => 11;
use constant SE_MEAN_APOG => 12;
use constant SE_OSCU_APOG => 13;
use constant SE_EARTH => 14;
use constant SE_CHIRON => 15;
use constant SE_PHOLUS => 16;
use constant SE_CERES => 17;
use constant SE_PALLAS => 18;
use constant SE_JUNO => 19;
use constant SE_VESTA => 20;
use constant SE_INTP_APOG => 21;
use constant SE_INTP_PERG => 22;
use constant SE_NPLANETS => 23;

use constant SE_AST_OFFSET => 10000;
use constant SE_VARUNA => (SE_AST_OFFSET + 20000);

use constant SE_FICT_OFFSET => 40;
use constant SE_FICT_OFFSET_1 => 39;
use constant SE_FICT_MAX => 999;
use constant SE_NFICT_ELEM => 15;
use constant SE_NALL_NAT_POINTS => (SE_NPLANETS + SE_NFICT_ELEM);

use constant SE_COMET_OFFSET => 1000;

use constant SE_CUPIDO => 40;
use constant SE_HADES => 41;
use constant SE_ZEUS => 42;
use constant SE_KRONOS => 43;
use constant SE_APOLLON => 44;
use constant SE_ADMETOS => 45;
use constant SE_VULKANUS => 46;
use constant SE_POSEIDON => 47;
use constant SE_ISIS => 48;
use constant SE_NIBIRU => 49;
use constant SE_HARRINGTON => 50;
use constant SE_NEPTUNE_LEVERRIER => 51;
use constant SE_NEPTUNE_ADAMS => 52;
use constant SE_PLUTO_LOWELL => 53;
use constant SE_PLUTO_PICKERING => 54;
use constant SE_VULCAN => 55;
use constant SE_WHITE_MOON => 56;
use constant SE_PROSERPINA => 57;
use constant SE_WALDEMATH => 58;

use constant SE_FIXSTAR => (-10);

use constant SE_ASC => 0;
use constant SE_MC => 1;
use constant SE_ARMC => 2;
use constant SE_VERTEX => 3;
use constant SE_EQUASC => 4; # "equatorial ascendant"
use constant SE_COASC1 => 5; # "co-ascendant" (W. Koch)
use constant SE_COASC2 => 6; # "co-ascendant" (M. Munkasey)
use constant SE_POLASC => 7; # "polar ascendant" (M. Munkasey)
use constant SE_NASCMC => 8;

use constant SEFLG_JPLEPH => 1;     # use JPL ephemeris
use constant SEFLG_SWIEPH => 2;     # use SWISSEPH ephemeris
use constant SEFLG_MOSEPH => 4;     # use Moshier ephemeris
use constant SEFLG_DEFAULTEPH => SEFLG_SWIEPH;  

use constant SEFLG_HELCTR => 8;     # return heliocentric position
use constant SEFLG_TRUEPOS => 16;   # return true positions, not apparent
use constant SEFLG_J2000 => 32;     # no precession, i.e. give J2000 equinox
use constant SEFLG_NONUT => 64;     # no nutation, i.e. mean equinox of date
use constant SEFLG_SPEED3 => 128;   # speed from 3 positions (do not use it,
                                    # SEFLG_SPEED is faster and more precise.)
use constant SEFLG_SPEED => 256;    # high precision speed
use constant SEFLG_NOGDEFL => 512;  # turn off gravitational deflection
use constant SEFLG_NOABERR => 1024; # turn off 'annual' aberration of light
use constant SEFLG_EQUATORIAL => (2*1024);  # equatorial positions are wanted
use constant SEFLG_XYZ => (4*1024); # cartesian, not polar, coordinates
use constant SEFLG_RADIANS => (8*1024);  # coordinates in radians, not degrees
use constant SEFLG_BARYCTR => (16*1024); # barycentric positions
use constant SEFLG_TOPOCTR => (32*1024); # topocentric positions
use constant SEFLG_SIDEREAL => (64*1024);# sidereal positions
use constant SEFLG_ICRS => (128*1024);   # ICRS (DE406 reference frame)

use constant SE_SIDBITS => 256;  
use constant SE_SIDBIT_ECL_T0 => 256;   # projection onto ecliptic of t0
use constant SE_SIDBIT_SSY_PLANE => 512;# projection onto solar system plane

# ayanamsas
use constant SE_SIDM_FAGAN_BRADLEY => 0;
use constant SE_SIDM_LAHIRI => 1;
use constant SE_SIDM_DELUCE => 2;
use constant SE_SIDM_RAMAN => 3;
use constant SE_SIDM_USHASHASHI => 4;
use constant SE_SIDM_KRISHNAMURTI => 5;
use constant SE_SIDM_DJWHAL_KHUL => 6;
use constant SE_SIDM_YUKTESHWAR => 7;
use constant SE_SIDM_JN_BHASIN => 8;
use constant SE_SIDM_BABYL_KUGLER1 => 9;
use constant SE_SIDM_BABYL_KUGLER2 => 10;
use constant SE_SIDM_BABYL_KUGLER3 => 11;
use constant SE_SIDM_BABYL_HUBER => 12;
use constant SE_SIDM_BABYL_ETPSC => 13;
use constant SE_SIDM_ALDEBARAN_15TAU => 14;
use constant SE_SIDM_HIPPARCHOS => 15;
use constant SE_SIDM_SASSANIAN => 16;
use constant SE_SIDM_GALCENT_0SAG => 17;
use constant SE_SIDM_J2000 => 18;
use constant SE_SIDM_J1900 => 19;
use constant SE_SIDM_B1950 => 20;
use constant SE_SIDM_USER => 255;
use constant SE_NSIDM_PREDEF => 21;

# for swe_nod_aps()
use constant SE_NODBIT_MEAN => 1; # mean nodes/apsides
use constant SE_NODBIT_OSCU => 2; # osculating nodes/apsides
use constant SE_NODBIT_OSCU_BAR => 4; # motion about solar system barycenter
use constant SE_NODBIT_FOPOINT => 256;# ellipse focal point instead of aphelion

# for eclipse computations
use constant SE_ECL_CENTRAL => 1; 
use constant SE_ECL_NONCENTRAL => 2; 
use constant SE_ECL_TOTAL => 4; 
use constant SE_ECL_ANNULAR => 8; 
use constant SE_ECL_PARTIAL => 16; 
use constant SE_ECL_ANNULAR_TOTAL => 32; 
use constant SE_ECL_PENUMBRAL => 64; 
use constant SE_ECL_VISIBLE => 128; 
use constant SE_ECL_1ST_VISIBLE => 512; 
use constant SE_ECL_2ND_VISIBLE => 1024; 
use constant SE_ECL_3RD_VISIBLE => 2048; 
use constant SE_ECL_4TH_VISIBLE => 4096; 
use constant SE_ECL_ONE_TRY => (32*1024); 

# for swe_rise_transit() and swe_rise_trans_true_hor())
use constant SE_CALC_RISE => 1;
use constant SE_CALC_SET => 2;
use constant SE_CALC_MTRANSIT => 4;
use constant SE_CALC_ITRANSIT => 8;
# | the following to SE_CALC_RISE (or _SET) ...
use constant SE_BIT_DISC_CENTER => 256;     # for rise (set) of _disc center_ 
use constant SE_BIT_NO_REFRACTION => 512;   # to neglect refraction
use constant SE_BIT_CIVIL_TWILIGHT => 1024; # sun reaches alt -6° 
use constant SE_BIT_NAUTIC_TWILIGHT => 2048;# sun reaches alt -12°
use constant SE_BIT_ASTRO_TWILIGHT => 4096; # sun reaches alt -18°

# for swe_azalt() and swe_azalt_rev()
use constant SE_ECL2HOR => 0; # ecliptic to horizon
use constant SE_EQU2HOR => 1; # equator to horizon
use constant SE_HOR2ECL => 0; # horizon to ecliptic
use constant SE_HOR2EQU => 1; # horizon to equator

# for swe_refrac()
use constant SE_TRUE_TO_APP => 0;
use constant SE_APP_TO_TRUE => 1;

# for swe_heliacal_ut()
use constant SE_HELIACAL_RISING => 1;
use constant SE_HELIACAL_SETTING => 2;
use constant SE_EVENING_FIRST => 3;
use constant SE_MORNING_LAST => 4;
use constant SE_HELFLAG_LONG_SEARCH => 128;
use constant SE_HELFLAG_HIGH_PRECISION => 256;
use constant SE_HELFLAG_OPTICAL_PARAMS => 512;
use constant SE_HELFLAG_NO_DETAILS => 1024;

# for swe_split_deg
use constant SE_SPLIT_DEG_ROUND_SEC => 1;
use constant SE_SPLIT_DEG_ROUND_MIN => 2;
use constant SE_SPLIT_DEG_ROUND_DEG => 4;
use constant SE_SPLIT_DEG_ZODIACAL  => 8;
use constant SE_SPLIT_DEG_KEEP_SIGN =>16;   # don't round to next sign,
					    # e.g. 29.9999999 will be rounded
					    # to 29°59'59" (or 29°59' or 29°) 
use constant SE_SPLIT_DEG_KEEP_DEG  =>32;   # don't round to next degree
					    # e.g. 13.9999999 will be rounded
					    # to 13°59'59" (or 13°59' or 13°) 


1;
__END__

=head1 NAME

SwissEph - The Swiss Ephemeris made accessible for Perl 

=head1 SYNOPSIS

=head2 Simple example

  use SwissEph qw(:all);

  my $ERR = -1;
  my $day = 1;
  my $month = 1;
  my $year = 2000;
  my $hour = 2.9876;
  my $gregflag = SE_GREG_CAL; # for Gregorian calendar
  my $geolat = 47; # geographical latitude
  my $geolon =  8; # geographical longitude
  
  # Julian day number UT
  my $tjd_ut = swe_julday($year, $month, $day, $hour, $gregflag);

  # position of Sun
  my $ref = swe_calc_ut($tjd_ut, SE_SUN, SEFLG_SPEED);
  if ($ref->{retval} == $ERR) {
    print "Error: $ref->{serr}\n";
    exit;
  }
  print "Position of Sun:\n";
  print "long=$ref->{xx}->[0], lat=$ref->{xx}->[1], r=$ref->{xx}->[2], ";
  print "speed=$ref->{xx}->[3]\n";

  # House cusps, Placidus
  $ref = swe_houses($tjd_ut, $geolat, $geolon, "P");
  print "armc = $ref->{armc}\n";
  print "mc   = $ref->{mc}\n";
  print "asc  = $ref->{asc}\n";
  print "houses:\n";
  for (my $i = 1; $i <= 12; $i++) {
    print "  $i:\t$ref->{cusps}->[$i]\n";
  }

=head1 DESCRIPTION

SwissEph is a simple Perl wrapper for the Swiss Ephemeris (see 
http://www.astro.com/swisseph). It is implemented as an XSUB. 

This manual only tells how to call the Swiss Ephemeris functions from Perl.
For more detailed information, use the Swiss Ephemeris Programmers' Manual 
and the general documentation: 
http://www.astro.com/swisseph/swephprg.htm and
http://www.astro.com/swisseph/swisseph.htm

For questions not answered in the  documentations, please become a member 
of the Swiss Ephemeris Mailing list: swisseph@yahoogroups.com and ask them
there.

In order to run the Swiss Ephemeris with the Perl module SwissEph.pm, 
you have to:

1. Install the Swiss Ephemeris. You may download the Swiss 
Ephemeris DLL from http://www.astro.com/swisseph, or download the 
whole Swiss Ephemeris C source code and compile a static or a
dynamic shared library. We built the package on a Linux system and use a
shared library of the Swiss Ephemeris functions.

2. Install the XS library:
- Unpack the ??????
- Open the file Makefile.PL, and edit it according to your requirements. 
Then run it.
- make install

If you work on a Windows machine and prefer to use the Swiss Ephemeris 
DLL, you may want to study Rüdiger Plantiko's Perl module for the 
Swiss Ephemeris at 
http://www.astrotexte.ch/sources/SwissEph.zip. There is also 
a documentation in German language by Rüdiger Plantiko at 
http://www.astrotexte.ch/sources/swe_perl.html).

=head1 EXPORT

None by default.
Any function can be imported (all functions are included in C<@EXPORT_OK>).
All functions can bee imported as follows:
use SwissEph(':all');

Additional to the functions declared in the XS file, the module SwissEph.pm 
exports all constants required for the use of the Swiss Ephemeris.

=head1 FUNCTIONS

=head2 Swiss Ephemeris version and Perl wrapper version

* swe_version( );

  Output: $ref     Pointer to a hash, which contains:
	  ->version_sweperl  Version number of Perl wrapper (from 
                             SwissEph.pm)
          ->version_swisseph Version of Swiss Ephemeris (from SE_VERSION)

=head2 Opening and Closing the Ephemeris

* swe_set_ephe_path($path);

  Function tells the SwissEphemeris where to finde the ephemeris files.

  Input:  $path   Path of the ephemeris files

* swe_set_jpl_file($filename);

  Function tells the SwissEphemeris which JPL ephemeris file to use.

  This function is only needed if a JPL ephemeris is used, and if
  this JPL ephemeris is not the standard one.

* swe_close( );
  
  Function closes all open files and frees all space that was allocated
  for the use of the Swiss Ephemeris.

=head2 Julian Daynumber and Calendar Conversions

* $tjd = swe_julday($year, $month, $day, $hour, $calflag);

  Function calculates the Julian Daynumber from a calendar date.
  $calflag can have two values:
    $calflag=SE_GREG_CAL; # use Gregorian calendar
    $calflag=SE_JUL_CAL;  # use Gregorian calendar
  $calflag may be omitted. If so, calendar dates before 1582/10/15 are 
  treated as Julian, whereas dates after 1582/10/15 are treated as 
  Gregorian.

* $ref = swe_revjul($tjd, $calflag);

  Function calculates the calendar date of a given julian day number.
  $calflag is optional, vide explanation of swe_julday( ).
  Output: $ref     Pointer to a hash, which contains:
          ->iyar   year
          ->imon   month
	  ->iday   day
	  ->dhou   hour (decimal)
	  ->ihour  hour (integer)
	  ->imin   minute
	  ->isec   second

* $ref = swe_utc_time_zone($year, $month, $day, $hour, $min, $sec, $dtimezone);

  Function converts local time to UTC, given a time zone offset,
  or UTC to local time.
  input
    year ... sec     date and time (sec may be a decimal)
    dtimezone       timezone offset
  output
    year_out ... sec_out
 
  For time zones east of Greenwich, d_timezone is positive.
  For time zones west of Greenwich, d_timezone is negative.
 
  For conversion from local time to utc, use +d_timezone.
  For conversion from utc to local time, use -d_timezone.

* $ref = swe_utc_to_jd($iyar, $imon, $iday, $ihou, $imin, $dsec, $calflag);

  Function calculates the Julian Daynumber from a calendar date in UTC.
  As for the input, note that dsec is a double.
  $calflag is optional, vide explanation of swe_julday( ).
  Note: 60 <= dsec < 61 is allowed, if it is a valid leap second.
  Output: $ref     Pointer to a hash, which contains:
          ->retval ERR or OK
          ->serr   Error string, on error only
          ->tjd_ut Julian day UT (UT1)
          ->tjd_et Julian day ET (TT)

* $ref = swe_jdet_to_utc($tjd, $calflag);

  Function calculates the calendar date UTC for a given 
  Julian day number, where the Julian day number is ET (TT).
  $calflag is optional, vide explanation of swe_julday( ).
  Output: $ref     Pointer to a hash, which contains:
          ->iyar   year
          ->imon   month
	  ->iday   day
	  ->ihour  hour 
	  ->imin   minute
	  ->dsec   second
  Note: in case of a leap second, 60 <= dsec < 61

* $ref = swe_jdut1_to_utc($tjd, $calflag);

  Function calculates the calendar date UTC for a given 
  Julian day number, where the Julian day number is UT (UT1).
  $calflag is optional, vide explanation of swe_julday( ).
  Output: $ref     Pointer to a hash, which contains:
          ->iyar   year
          ->imon   month
	  ->iday   day
	  ->ihour  hour 
	  ->imin   minute
	  ->dsec   second
  Note: in case of a leap second, 60 <= dsec < 61

* $dow = swe_day_of_week($tjd);
  
  Function returns the day of week for a Julian day number.
  Monday = 0, ... Sunday = 6.

=head2 Delta T and Tidal Acceleration of the Moon

* $dt = swe_deltat($tjd_et);
  
  Function calculates Delta T for a given Julian day number in 
  Ephemeris Time.

* $tacc = swe_get_tid_acc( );

  Function returns the tidal acceleration on which the Swiss Ephemeris
  is based.

* swe_set_tid_acc($tacc);

  Function sets the tidal acceleration to any other value. This is 
  relevant for Delta T calculations.

=head2 Sidereal Time

* $tsid = swe_sidtime($tjd_ut);
  
  Function calculates sidereal time in hours for a given Julian 
  day number in Universal Time.

* $tsid = swe_sidtime0($tjd_ut, $eps, $nut);
  
  Function calculates sidereal time in hours:
  $tjd_ut     Julian day number in Universal Time
  $eps        Obliquity of ecliptic, degrees
  $nut        Nutation in longitude, degrees
  This function provides the same result as swe_sidtime(), if $eps is
  the obliquity of the true ecliptic of date (mean eps + nutation in
  obliquity).

=head2 Time Equation

* $ref = swe_time_equ($tjd_ut);

  Function calculates the time equation
  Input:  $tjd_ut  Julian day number, Universal Time
  Output: $ref     Pointer to a hash, which contains:
          -> retval     OK or ERR
          -> time_equ   time equation in days
          -> serr       error message (on error only)

=head2 Positions of Celestial Bodies

* $ref = swe_calc($tjd_et, $ipl, $iflag);

  Function calculates the position of a planet or asteroid.

  Input:  $tjd_et  Julian day number, Ephemeris Time
          $ipl     Planet identification number (see below)
          $iflag   Calculation specifications (see below)
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or $iflag; $iflag may have been corrected
          -> serr  Error string, on error only
          -> xx    Pointer to position array. The content of this
	           array depends on the calculation specifications
		   in iflag.

* $ref = swe_calc_ut($tjd_ut, $ipl, $iflag);

  Function does the same as swe_calc( ), but for a Universal Time
  Julian day number.

* Planet numbers ($ipl) for swe_calc( )

  Many Swiss Ephemeris functions use planet numbers to identify the
  planet for which a calculation is to be done. For this, you can
  use the constants defined in SwissEph.pm:

  SE_SUN, SE_MOON, SE_MERCURY, SE_VENUS, SE_MARS, SE_JUPITER, 
  SE_SATURN, SE_URANUS, SE_NEPTUNE, SE_PLUTO, SE_MEAN_NODE, 
  SE_TRUE_NODE, SE_MEAN_APOG, SE_OSCU_APOG, SE_INTP_APOG,
  SE_INTP_PERG, SE_EARTH, SE_CHIRON, SE_PHOLUS, SE_CERES, 
  SE_PALLAS, SE_JUNO, SE_VESTA.

  For asteroids, the planet number is defined as:
  $ipl = SE_AST_OFFSET + minor_planet_catalogue_number;

  For more info, e.g. fictitious bodies, see the constants list
  in SwissEph.pm.

* Calculation Specifications ($iflag) for swe_calc( ) 

  The function swe_calc( ) can provide different kinds of planetary 
  positions, e.g. it can provide them in different coordinate systems,
  or it can provid true or apparent positions, geocentric, heliocentric,
  or barycentric positions, etc. etc. In order to specify the result 
  you need, use the input variable $iflag. $iflag is a bit map. The
  calculation specification are or'ed as single bits to this bit map.

  SEFLG_JPLEPH = 1          # use JPL ephemeris
  SEFLG_SWIEPH = 2          # use SWISSEPH ephemeris
  SEFLG_MOSEPH = 4          # use Moshier ephemeris
  SEFLG_DEFAULTEPH = SEFLG_SWIEPH  
  SEFLG_HELCTR = 8          # return heliocentric position
  SEFLG_TRUEPOS = 16        # return true positions, not apparent
  SEFLG_J2000 = 32          # no precession, i.e. give J2000 equinox
  SEFLG_NONUT = 64          # no nutation, i.e. mean equinox of date
  SEFLG_SPEED3 = 128        # speed from 3 positions (do not use it,
		            # SEFLG_SPEED is faster and more precise.)
  SEFLG_SPEED = 256         # high precision speed
  SEFLG_NOGDEFL = 512       # turn off gravitational deflection
  SEFLG_NOABERR = 1024      # turn off 'annual' aberration of light
  SEFLG_EQUATORIAL = (2*1024) # equatorial positions are wanted
  SEFLG_XYZ = (4*1024)      # cartesian, not polar, coordinates
  SEFLG_RADIANS = (8*1024)  # coordinates in radians, not degrees
  SEFLG_BARYCTR = (16*1024) # barycentric positions
  SEFLG_TOPOCTR = (32*1024) # topocentric positions
  SEFLG_SIDEREAL = (64*1024)# sidereal positions
  SEFLG_ICRS = (128*1024)   # ICRS (DE406 reference frame)

* swe_set_topo($geolon, $geolat, $geoalt);

  Function sets the geographical position for topocentric calculations.
  Call this function before calling swe_calc( ) with the topocentric 
  flag (SEFLG_TOPOCTR).
  Input:  $geolon  Geographical longitude
          $geolat  Geographical latitude
          $geoalt  Height above sea level in meters

* swe_set_ayanamsa($isidmode, t0, ayan_t0);

  Function sets the ayanamsa for the calculation of sidereal positions
  of planets or fixed stars. Call this function before calling swe_calc( )
  with the sidereal flag (SEFLG_SIDEREAL).

  Input:  $isidmode Sidereal mode, s. below.
          $t0       To define your own ayanamsa: start time.
	            0, if you use a predefined ayanamsa
          $ayan_t0  To define your own ayanamsa: ayanamsa at start time
	            0, if you use a predefined ayanamsa

  Predefined ayanamsas:
  SE_SIDM_FAGAN_BRADLEY, SE_SIDM_LAHIRI. For the other predefined 
  ayanamsas, please study the C Programmer's Manual.

* $daya = swe_get_ayanamsa($tjd_et);

  Function returns the ayanamsa for a Julian day number.

  Input:  $tjd_et  Julian day number in Ephemeris Time
  Output: $daya    Ayanamsa in degrees

* $daya = swe_get_ayanamsa_ut($tjd_ut);

  Function does the same as swe_get_ayanamsa( ), but input parameter
  is Universal Time.

* $ayaname = swe_get_ayanamsa_name($isidmode);

  Function returns the name of the ayanamsa.

=head2 Positions of Fixed Stars

* $ref = swe_fixstar($star, $tjd_et, $iflag);

  Function calculates the position of a fixed star.

  Input:  $star    Star name, e.g. "spica" or "alVir"
          $tjd_et  Julian day number, Ephemeris Time
          $iflag   Calculation specifications (see above)
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or $iflag; $iflag may have been corrected
          -> serr  Error string, on error only
          -> xx    Pointer to position array. The content of this
	           array depends on the calculation specifications
		   in iflag.
          -> starname Corrected star name, e.g. like "Spica,alVir"

* $ref = swe_fixstar_ut($star, $tjd_ut, $iflag);

  Function does the same as swe_fixstar( ), but for a Universal Time
  Julian day number.

=head2 Name, Magnitude, Phase, Elongation, Disc Diameter, etc.

* $name = swe_get_planet_name($ipl);

  Function returns the planet name.

  Input:  $ipl     Planet identification number
  Output: $name    Name of planet.

* $ref = swe_pheno($tjd_et, $ipl, $iflag);

  Function calculates the position of a planet or asteroid.

  Input:  $tjd_et  Julian day number, Ephemeris Time
          $ipl     Planet identification number
          $iflag   Calculation specifications
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or $iflag; $iflag may have been corrected
          -> serr  Error string, on error only
	  -> attr  Pointer to an array. Array contains the values 
	           of the following hash members.
          -> phase_angle Phase angle (Earth-Planet-Sun)
          -> phase Phase (illumined fraction of disc)
          -> elongation 
          -> disc_diameter
	  -> magnitude
	  -> hor_parallax   Horizontal parallax of the Moon.

* $ref = swe_pheno($tjd_et, $ipl, $iflag);

  Function does the same as swe_pheno( ), but for a Universal Time
  Julian day number.

* $ref = swe_fixstar_mag($star);

  Function calculates the magnitude of a fixed star.

  Input:  $star    Star name, e.g. "spica" or "alVir"
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or OK
          -> serr  Error string, on error only
          -> dmag  Magnitude of fixed star
          -> starname Corrected star name, e.g. like "Spica,alVir"

=head2 Rising, Setting and Meridian Transits

* $ref = swe_rise_trans($tjd_ut, $ipl, $star, $epheflag, $rsmi, $geopos, $atpress, $attemp);

  Function calculates the rising, setting, or meridian transit of 
  a celestial body.

  Input:  $tjd_ut  Julian day number, Universal Time
          $ipl     Planet identification number
          $star    Star name, if a star is calculated; otherwise ""
          $epheflag   Ephemeris flag, SEFLG_SWIEPH, SEFLG_JPLEPH, or
	           SEFLG_MOSEPH
          $rsmi    Calculation flag:
	           SE_CALC_RISE, SE_CALC_SET, SE_CALC_MTRANSIT,
		   SE_CALC_ITRANSIT.
		   | SE_BIT_DISC_CENTER   to calculate risings and settings
		                          for the disc center of the body
		   | SE_BIT_NO_REFRACTION to neglect refraction
          $geopos  Pointer ot array of geogr. long., lat., height
	  $atpress Atmospheric pressure in hPa (mbar)
	  $attemp  Atmospheric temperature in degree C
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or OK
          -> serr  Error string, on error only
	  -> dret  Julian day number in Universal Time of rising, etc.
	  -> starname  Corrected star name

* $ref = swe_rise_trans_true_hor($tjd_ut, $ipl, $star, $epheflag, $rsmi, $geopos, $atpress, $attemp, $horhgt);

  This function is similar to the function swe_rise_trans(), but
  the rising and setting can be calculated for a horizon which is 
  not flat. For this reason it has an additional parameter 
          $horhgt  height of the horizon in degrees at the point
	           where the body is expected to rise

=head2 Nodes and Apsides

* $ref = swe_nod_aps($tjd_et, $ipl, $iflag, $method);

  Function calculates the positions of the nodes and apsides of 
  planets.

  Input:  $tjd_ut  Julian day number, Universal Time
          $ipl     Planet identification number
          $iflag   (same parameter as with swe_calc( ))
          $method  (see C Programmer's Manual)=
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or $iflag; $iflag may have been corrected
          -> serr  Error string, on error only
	  -> xnasc Pointer to position array of ascending node
	  -> xndsc Pointer to position array of descending node
	  -> xperi Pointer to position array of perihelion
	  -> xaphe Pointer to position array of aphelion

=head2 Eclipses and Occultations by the Moon

* $ref = swe_sol_eclipse_when_glob($tjd_ut, $iflag, $ifltype, $backw);

  Finds the next solar eclipse no matter where on earth.

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $ifltype Eclipse type to be searched; 0 if any type of
	           eclipse is wanted
          $backw   Search backward in time
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or eclipse type
          -> serr  Error string, on error only
	  -> tret  Pointer to array, which contains the values fo the
	           following hash members:
	  -> ecl_maximum      time of maximum eclipse (UT)
	  -> ecl_local_noon   time when eclipse happens at local noon (UT)
	  -> ecl_begin
	  -> ecl_end
	  -> ecl_total_begin
	  -> ecl_total_end
	  -> ecl_central_begin
	  -> ecl_central_end

  The following bits define the eclipse type
  SE_ECL_CENTRAL, SE_ECL_NONCENTRAL, SE_ECL_TOTAL, SE_ECL_ANNULAR,
  SE_ECL_PARTIAL, SE_ECL_ANNULAR_TOTAL

* $ref = swe_sol_eclipse_when_loc($tjd_ut, $iflag, $backw, $geopos);

  Finds the next solar eclipse for a given place on earth.

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $backw   Search backward in time
          $geopos  Pointer to array of geograph. long., lat., height
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or eclipse type
          -> serr  Error string, on error only
	  -> tret  Pointer to array (s. C Programmer's Manual)
	  -> attr  Pointer to array (s. C Programmer's Manual)
	  -> ecl_maximum       time of maximum eclipse (UT)
	  -> t1st_contact
	  -> t2nd_contact
	  -> t3rd_contact
	  -> t4th_contact
	  -> disc_ratio        size ratio of the two discs
	  -> fraction_diameter percentage of diameter occulted
	  -> fraction_disc     percentage of disc occulted
	  -> core_shadow_km    diameter of core shadow (km, negative
	                       with total, positive with annular ecl.)
	  -> sun_azimuth
	  -> sun_alt_true
	  -> separation_angle

* $ref = swe_sol_eclipse_where($tjd_ut, $iflag);

  Finds the place on earth where the eclipse is maximal at a given
  time. 

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
  Output: $ref     Pointer to a hash, which contains:
          -> retval            ERR or eclipse type
          -> serr              Error string, on error only
	  -> attr              Pointer to array (s. Programmer's Manual)
	  -> geopos            Pointer to array, geogr. position
	                       where eclipse is maximal
	  -> disc_ratio        size ratio of the two discs
	  -> fraction_diameter percentage of diameter occulted
	  -> fraction_disc     percentage of disc occulted
	  -> core_shadow_km    diameter of core shadow (km, negative
	                       with total, positive with annular ecl.)
	  -> sun_azimuth
	  -> sun_alt_true
	  -> separation_angle
	  -> geo_long          (from geopos)
	  -> geo_lat           (from geopos)

* $ref = swe_sol_eclipse_how($tjd_ut, $iflag, $geopos);

  Function calculates local character of an eclipse

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $geopos  Pointer to array of geograph. long., lat., height
  Output: $ref     Pointer to a hash, which contains:
          -> retval            ERR or eclipse type
          -> serr              Error string, on error only
	  -> attr              Pointer to array (s. Programmer's Manual)
	  -> disc_ratio        size ratio of the two discs
	  -> fraction_diameter percentage of diameter occulted
	  -> fraction_disc     percentage of disc occulted
	  -> core_shadow_km    diameter of core shadow (km, negative
	                       with total, positive with annular ecl.)
	  -> sun_azimuth
	  -> sun_alt_true
	  -> separation_angle

* $ref = swe_lun_eclipse_when($tjd_ut, $iflag, $ifltype, $backw);

  Finds the next lunar eclipse

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $ifltype Eclipse type to be searched; 0 if any type of
	           eclipse is wanted
          $backw   Search backward in time
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or eclipse type
          -> serr  Error string, on error only
	  -> tret  Pointer to array, which contains the values fo the
	           following hash members:
	  -> ecl_maximum      time of maximum eclipse (UT)
	  -> ecl_partial_begin
	  -> ecl_partial_end
	  -> ecl_total_begin
	  -> ecl_total_end
	  -> ecl_penumbral_begin
	  -> ecl_penumbral_end

  There are the following eclipse types for lunar eclipses:
  SE_ECL_TOTAL, SE_ECL_PENUMBRAL, SE_ECL_PARTIAL

* $ref = swe_lun_eclipse_how($tjd_ut, $iflag, $geopos);

  Function calculates the local character of an eclipse.

  Input:  $tjd_ut  Julian day number, Universal Time
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $geopos  Pointer to array of geograph. long., lat., height
  Output: $ref     Pointer to a hash, which contains:
          -> retval            ERR or eclipse type
          -> serr              Error string, on error only
	  -> attr              Pointer to array (s. Programmer's Manual)   
	  -> mag_umbral        Umbral magnitude
	  -> mag_penumbral     Penumbral magnitude

* $ref = swe_lun_occult_when_glob($tjd_ut, $ipl, $star, $iflag, $ifltype, $backw);

  Finds the next occultation of a celestial body by the moon,
  no matter where on earth.

  Input:  $tjd_ut  Julian day number, Universal Time
	  $ipl     Planet occulted
	  $star    Star name, if a star occultation is searched
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $ifltype Eclipse type to be searched; 0 if any type of
	           eclipse is wanted
          $backw   Search backward in time
  Output: $ref     Pointer to a hash, which contains:
          -> retval     ERR or occultation type
          -> serr  Error string, on error only
          -> starname  Corrected star name
	  -> tret  Pointer to array, which contains the values fo the
	           following hash members:
	  -> occ_maximum      Time of maximum occultation (UT)
	  -> occ_local_noon   Occ. happens at local noon (UT)
	  -> occ_begin
	  -> occ_end
	  -> occ_total_begin
	  -> occ_total_end
	  -> occ_central_begin
	  -> occ_central_end

  There are the following eclipse types for lunar eclipses:
  SE_ECL_TOTAL, SE_ECL_PARTIAL, SE_ECL_CENTRAL, SE_ECL_NONCENTRAL

* $ref = swe_lun_occult_when_loc($tjd_ut, $ipl, $star, $iflag, $backw, $geopos);

  Finds the next solar eclipse for a given place on earth.

  Input:  $tjd_ut  Julian day number, Universal Time
	  $ipl     Planet occulted
	  $star    Star name, if a star occultation is searched
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
          $backw   Search backward in time
          $geopos  Pointer to array of geograph. long., lat., height
  Output: $ref     Pointer to a hash, which contains:
          -> retval            ERR or eclipse type
          -> serr              Error string, on error only
          -> starname          Corrected star name
	  -> tret  Pointer to array (s. C Programmer's Manual)
	  -> attr  Pointer to array (s. C Programmer's Manual)
	  -> ecl_maximum       time of maximum occultation (UT)
	  -> t1st_contact
	  -> t2nd_contact
	  -> t3rd_contact
	  -> t4th_contact
	  -> disc_ratio        size ratio of the two discs
	  -> fraction_diameter percentage of diameter occulted
	  -> fraction_disc     percentage of disc occulted
	  -> core_shadow_km    diameter of core shadow (km, negative
	                       with total, positive with annular ecl.)
	  -> body_azimuth
	  -> body_alt_true
	  -> separation_angle

* $ref = swe_lun_occult_where($tjd_ut, $ipl, $star, $iflag);

  Finds the place on earth where the occultation is maximal at a given
  time. 

  Input:  $tjd_ut  Julian day number, Universal Time
	  $ipl     Planet occulted
	  $star    Star name, if a star occultation is searched
          $iflag   (specify ephemeris to be used, cf. swe_calc( ))
  Output: $ref     Pointer to a hash, which contains:
          -> retval            ERR or eclipse type
          -> serr              Error string, on error only
          -> starname          Corrected star name
	  -> attr              Pointer to array (s. Programmer's Manual)
	  -> geopos            Pointer to array, geogr. position
	                       where eclipse is maximal
	  -> disc_ratio        size ratio of the two discs
	  -> fraction_diameter percentage of diameter occulted
	  -> fraction_disc     percentage of disc occulted
	  -> core_shadow_km    diameter of core shadow (km, negative
	                       with total, positive with annular ecl.)
	  -> body_azimuth
	  -> body_alt_true
	  -> separation_angle
	  -> geo_long          (from geopos)
	  -> geo_lat           (from geopos)

=head2 Coordinate Transformations

* @cout = swe_cotrans(\@cin, $eps);

  Function transforms polar coordinates from equator to ecliptic or 
  from ecliptic to equator.
  Input:  \@cin    Polar coordinates of point to be transformed;
		   pointer to an array of 3 decimals. 
                   (e.g. longitude, latitude, distance)
	  $eps     Ecliptic obliquity. 
		   Must be positive for transformation equator->ecliptic
		   Must be negative for transformation ecliptic->equator
  Output: @cout    Output coordinates; an array of 3 decimals.

* @cout = swe_cotrans_sp(\@cin, $eps);
 
  Function does the same as swe_cotrans(), but in addition, it also 
  transforms the speed of a body to another coordinate system.  
  The input and output arrays have 6 decimals, 3 for the position 
  and 3 for the speed.  

* $ref = swe_azalt($tjd_ut, $calcflag, $geopos, $atpress, $attemp, $xin);

  Function calculates the azimuth and altitude from ecliptical or 
  equatorial coordinates.

  Input:  $tjd_ut   Julian day number, UT
	  $calcflag either SE_ECL2HOR or SE_EQU2HOR
	  $geopos   Pointer to array of geograph. long., lat., height
	  $atpress  Atmospheric pressure in hPa (mbar)
	  $attemp   Atmospheric temperature in degree C
	  $xin      Pointer to array, position of body, either in 
	            equatorial or ecliptical polar coordinates, 
		    depending on $calcflag
  Output: @aret     azimuth, true altitude, apparent altitude

* $ref = swe_azalt_rev($tjd_ut, $calcflag, $geopos, $atpress, $attemp, $xin);

  Function transforms azimuth and altitude to either ecliptical or 
  equatorial coordinates.

  Input:  $tjd_ut   Julian day number, UT
	  $calcflag either SE_HOR2ECL or SE_HOR2EQU
	  $geopos   Pointer to array of geograph. long., lat., height
	  $xin      Pointer to array, azimuth and true altitude
  Output: @aret     either ecliptical or equatorial coordinates
  
=head2 Astrological Houses

* $ref = swe_houses($tjd_ut, $geolat, $geolon, $hsys);
  
  Function calculates the astrological houses and related stuff.
  Input:  $tjd_ut  Julian day number in UT
          $geolat  Geographical latitude
          $geolon  Geographical longitude
          $hsys    House system, a 1-char string; defaults to Placidus
	           (For more information, read the Swiss Ephemeris
		   Programmer's Manual.)
  Output: $ref     Pointer to a hash, which contains:
          ->cusps  Pointer to an array: the twelve houses (cusps[1..12]).
          ->ascmc  Pointer to an array: See the Programmer's Manual.
	  ->asc    Ascendant
	  ->mc     Midheaven (Medium Coeli)
	  ->armc   Right ascension of the midheaven
	  ->vertex Vertex
	  ->equasc "equatorial ascendant"
	  ->coasc1 "co-ascendant 1" (M. Munkasey)
	  ->coasc2 "co-ascendant 2" (M. Munkasey)
	  ->polasc "polar ascendant" (M. Munkasey)

* $ref = swe_houses_ex($tjd_ut, $iflag, $geolat, $geolon, $hsys);
  
  Function calculates the astrological houses and related stuff.
  The only difference from swe_houses( ) is that it has an
  additional input parameter $iflag, which allows for the calculation
  of houses for sidereal zodiacs. Set $iflag |= SEFLG_SIDEREAL.

* $ref = swe_houses_armc($tjd_ut, $geolat, $geolon, $hsys);

  Function calculates the astrological houses and related stuff, if 
  a date is not given, but a latitude and the right ascension of the
  midheaven.
  Input:  $armc    Right ascension of the Midheaven
          $geolat  Geographical latitude
          $eps     Obliquity of true ecliptic of date
          $hsys    House system, a 1-char string; cf. swe_houses( )
  Output: same as with swe_houses( )

* $ref = swe_house_pos($armc, $geolat, $eps, $hsys, $ecl_lon, $ecl_lat);

  Function calculates the astrological house position of a planet or 
  other celestial point. 
  Input:  $armc    Right ascension of the Midheaven
          $geolat  Geographical latitude
          $eps     Obliquity of true ecliptic of date
          $hsys    House system, a 1-char string; cf. swe_houses( )
          $ecl_lon ecliptic longitude of body
          $ecl_lat ecliptic latitude of body
  Output: $ref     Pointer to a hash, which contains:
          ->ihno   house number, integer
	  ->dhpos  house position, decimal between 1.0 and 12.9999
	  ->dhpos_deg  house position, degrees, between 0° and 359.9999°
	  ->serr   error string, on error only

* $hnam = swe_house_name($hsys);

  Function returns the name of the house method.
  Input:  $hsys    House system, a 1-char string; cf. swe_houses( )

* $ref = swe_gauquelin_sector($tjd_ut, $ipl, $star, $iflag, $imeth, $geopos, $atpress, $attemp);

  Function calculates the Gauquelin sector position of a planet or other
  object.

  Input:  $tjd_ut   Julian day number, UT
          $ipl      planet number 
	  $star     Star name (string), if star is being calculated
	  $iflag    Ephemeris flag
	  $imeth    Method flag
	       0    use Placidus house position
	       1    use Placidus house posiition (with planetary lat = 0)
	       2    use rise and set of body's disc center
	       3    use rise and set of body's disc center with refraction
	  $geopos   Pointer to array of geograph. long., lat., height
	  $atpress  Atmospheric pressure in hPa (mbar)
	  $attemp   Atmospheric temperature in degree C
  Output: $ref      Pointer to a hash, which contains:
          ->retval  OK or ERR
	  ->serr    Error string, on error only
	  ->dsector Gauquelin sector position of object
	  ->starname Star name, corrected

=head2 Refraction

* $outalt = swe_refrac($inalt, $atpress, $attemp, $calcflag);

  Function calculates true altitude from apparent altitude or
  apparent altitude from true altitude.

  Input:  $inalt    true or apparent altitude of a planet, depending
                    on $calcflag
	  $atpress  Atmospheric pressure in hPa (mbar)
	  $attemp   Atmospheric temperature in degree C
	  $calcflag either SE_CALC_APP_TO_TRUE or SE_CALC_TRUE_TO_APP
  Output: $outalt   either apparent or true altitude of the planet,
                    depending on $calcflag

  For more detailed information, read the C Programmer's Manual.

* $ref = swe_refrac_extended($inalt, $geoalt, $atpress, $attemp, $lapserate, $calcflag);

  Function calculates true altitude from apparent altitude or
  apparent altitude from true altitude.
  It does the same as swe_refrac(), but is more skilled. 
  (see C Programmer's Manual)

  Input:  $inalt    true or apparent altitude of a planet, depending
                    on $calcflag
	  $geoalt   altitude of observer above sea level in meters
	  $atpress  Atmospheric pressure in hPa (mbar)
	  $attemp   Atmospheric temperature in degree C
	  $lapserate (dT/dh) [°K/m]
	  $calcflag either SE_CALC_APP_TO_TRUE or SE_CALC_TRUE_TO_APP
  Output: $ref      Pointer to a hash, which contains:
          ->retval  OK or ERR
	  ->serr    Error string, on error only
	  ->dret    Pointer to an array which contains the values of
	            the following hash members:
	  ->alt_true 
	  ->alt_apparent 
	  ->refraction
	  ->dip     Dip of the horizon 

  For more detailed information, read the C Programmer's Manual.

=head2 Heliacal Phenomena
  
* $ref = swe_heliacal_ut($tjd_start, $dgeo, $datm, $dobs, $objname, $type_event, $iflag)

  Function calls mornig first, morning last, evening first, or 
  evening last of a planet or fixed star.

  Input:  $tjd_start    start date for search
          $dgeo         array pointer (geogr. longitude, latitude, height)
	  $datm         array pointer (atm. pressure, temperature, 
	                relative humidity, visibility/extinction coeffictient)
          $dobs         array pointer (observer age, Snellen ratio, 
	                is_binocular, telescope magnification, telescope
			diameter, telescope transmission)
          $objname      string: object name
          $type_event   SE_HELIACAL_RISING (1)
                        SE_HELIACAL_SETTING (2)
                        SE_EVENING_FIRST (3)
                        SE_MORNING_LAST (4)
          $iflag        ephemeris flag, can be combined with the 
	                following flags:
			SE_HELFLAG_LONG_SEARCH     search until found
		        SE_HELFLAG_HIGH_PRECISION  slower but preciser
                        SE_HELFLAG_NO_DETAILS      very fast, no details
			SE_HELFLAG_OPTICAL_PARAMS  binocular/telescope
                        (for more info, please read the Swiss Ephemeris
                        Programmer's Manual swephprg.doc)

  Output: $ref      Pointer to a hash, which contains:
          ->retval      OK or ERR
	  ->serr        error or warning message
          ->tstart      start of visibility (jd)
          ->topt        optimum visibility (jd)
          ->tend        end of visibility (jd)
	  ->dret        array of doubles, contains tstart, topt, tend.

* $ref = swe_vis_limit_mag($tjd_start, $dgeo, $datm, $dobs, $objname, $iflag)

  Function calculates magnitude limit for observation of a body under
  the specified conditions.

  Input:  same as with swe_heliacal_ut(), but type_event is missing.

  Output: $ref        Pointer to a hash, which contains:
          ->retval    can have the following values:
                      -2        Object is below the horizon
                      -1 (ERR)  Error
                       0 (OK)   Ok, photopic vision
                       &1       Ok, scotopic vision
                       &2       Ok, near limit photopic/scotopic vision
	  ->serr      Error or warning message
          ->dvislim   magnitude required for visibility
          ->daltobj   altitude of object above horizon
          ->daziobj   azimuth of object
          ->daltsun   altitude of sun above horizon
          ->dazisun   azimuth of moon
          ->daltmoo   altitude of sun above horizon
          ->dazimoo   azimuth of moon
	  ->dret      Array pointer, containing:
	              - magnitude limit
		      - altitudes and azimuts of object, Sun, and Moon.
  
=head2 Normalisation to 360° and Angles between Ecliptic Points

* $ddeg = swe_degnorm($ddeg);

  Function normalises a value, which is <0° or >360° to a value between
  0° and 360°.

* $drad = swe_radnorm($drad);

  Function normalises a value, which is <0 or >2PI to a value between
  0 and 2PI.

* $ddeg = swe_difdegn($ddeg1, $ddeg2);

  Function returns the swe_degnorm($deg1 - ddeg2).

* $ddeg = swe_difdegn($ddeg1, $ddeg2);

  Function returns the swe_degnorm($deg1 - ddeg2).

* $ddeg = swe_difdeg2n($ddeg1, $ddeg2);

  Function returns the angle distance of $ddeg1 from $ddeg 2 as
  a value between -180° and +180°.

* $drad = swe_difrad2n($drad1, $drad2);

  Function returns the angle distance of $drad1 from $drad 2 as
  a value between -PI and +PI.

* $ddeg = swe_deg_midp($ddeg1, $ddeg2);

  Function returns the midpoint between the two positions.

* $drad = swe_rad_midp($drad1, $drad2);

  Function returns the midpoint between the two positions, in radians.

* $ref swe_split_deg($ddeg, $splitflag);

  Function returns a hash with fields ideg, imin, isec, dfrc, isgn
  splitflag 
  SE_SPLIT_DEG_ROUND_SEC  1;
  SE_SPLIT_DEG_ROUND_MIN => 2;
  SE_SPLIT_DEG_ROUND_DEG => 4;
  SE_SPLIT_DEG_ZODIACAL  => 8;
  SE_SPLIT_DEG_KEEP_SIGN =>16;   # don't round to next sign,
                                 # e.g. 29.9999999 will be rounded
                                 # to 29°59'59" (or 29°59' or 29°)
  SE_SPLIT_DEG_KEEP_DEG => 32 
  

=head1 SEE ALSO

=over 4

=item *

Swiss Ephemeris homepage: http://www.astro.com/swisseph

=item *

Swiss Ephemeris Mailing list: swisseph@yahoogroups.com 

=item *

Description of Rüdiger Plantiko's Perl wrapper for the Swiss Ephemeris 
(in german language): http://www.astrotexte.ch/sources/swe_perl.html

=item *

Perl XS documentation: perlxs, perlxstuts, perlguts, perlapi 

=back

=head1 AUTHORS

Dieter Koch and Alois Treindl, Astrodienst, Zürich, Switzerland 
(dieter@astro.ch, alois@astro.ch)

=head1 COPYRIGHT AND LICENSE

Copyright of this Perl Port (C) 2009 by Astrodienst AG, Switzerland.
All rights reserved.

Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
or distributor accepts any responsibility for the consequences of using it,
or for whether it serves any particular purpose or works at all, unless he
or she says so in writing.

Swiss Ephemeris is made available by its authors under a dual licensing
system. The software developer, who uses any part of Swiss Ephemeris
in his or her software, must choose between one of the two license models,
which are
a) GNU public license version 2 or later
b) Swiss Ephemeris Professional License

The choice must be made before the software developer distributes software
containing parts of Swiss Ephemeris to others, and before any public
service using the developed software is activated.

If the developer choses the GNU GPL software license, he or she must fulfill
the conditions of that license, which includes the obligation to place his
or her whole software project under the GNU GPL or a compatible license.
See http://www.gnu.org/licenses/old-licenses/gpl-2.0.html

If the developer choses the Swiss Ephemeris Professional license,
he must follow the instructions as found in http://www.astro.com/swisseph/
and purchase the Swiss Ephemeris Professional Edition from Astrodienst
and sign the corresponding license contract.

The License grants you the right to use, copy, modify and redistribute
Swiss Ephemeris, but only under certain conditions described in the License.
Among other things, the License requires that the copyright notices and
this notice be preserved on all copies.

Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl

The authors of Swiss Ephemeris have no control or influence over any of
the derived works, i.e. over software or services created by other
programmers which use Swiss Ephemeris functions.

The names of the authors or of the copyright holder (Astrodienst) must not
be used for promoting any software, product or service which uses or contains
the Swiss Ephemeris. This copyright notice is the ONLY place where the
names of the authors can legally appear, except in cases where they have
given special permission in writing.

The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
for promoting such software, products or services.

=cut
