#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef JAVAME
#define NO_JPL
#endif /* JAVAME */
#ifdef NO_RISE_TRANS
#define ASTROLOGY
#endif /* NO_RISE_TRANS */
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 1.80.00
   of Astrodienst AG, Switzerland from the original C Code to Java. For
   copyright see the original copyright notices below and additional
   copyright notes in the file named LICENSE, or - if this file is not
   available - the copyright notes at http://www.astro.ch/swisseph/ and
   following.
   
   For any questions or comments regarding this port to Java, you should
   ONLY contact me and not Astrodienst, as the Astrodienst AG is not involved
   in this port in any way.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 23rd of April 2001

*/
/*
  $Header: /home/dieter/sweph/RCS/swetest.c,v 1.74 2008/06/16 10:07:20 dieter Exp $
  swetest.c     A test program

  Authors: Dieter Koch and Alois Treindl, Astrodienst Zuerich

**************************************************************/

/* Copyright (C) 1997 - 2008 Astrodienst AG, Switzerland.  All rights reserved.

  License conditions
  ------------------

  This file is part of Swiss Ephemeris.

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
*/
import swisseph.*;

import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

/**
* A class to test (probably) all of the swiss ephemeris routines with
* (probably) all possible options. See parameter -h for infos about
* all the parameter switches.<P>
*/
public class Swetest
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  static final String infocmd0 = "\n"+
  "  Swetest computes a complete set of geocentric planetary positions,\n"+
  "  for a given date or a sequence of dates.\n"+
  "  Input can either be a date or an absolute julian day number.\n"+
  "  0:00 (midnight).\n"+
  "  With the proper options, swetest can be used to output a printed\n"+
  "  ephemeris and transfer the data into other programs like spreadsheets\n"+
  "  for graphical display.\n"+
  "  Version: $Header: /users/dieter/sweph/RCS/swetest.c,v 1.78 2010/06/25 07:22:10 dieter Exp $\n"+
  "\n";
  static final String infocmd1 = "\n"+
  "  Command line options:\n"+
  "     help commands:\n"+
  "        -?, -h  display whole info\n"+
  "        -hcmd   display commands\n"+
  "        -hplan  display planet numbers\n"+
  "        -hform  display format characters\n"+
  "        -hdate  display input date format\n"+
  "        -hexamp  display examples\n"+
  "     input time formats:\n"+
  "        -bDATE  begin date; e.g. -b1.1.1992 if\n"+
  "                Note: the date format is day month year (European style).\n"+
  "        -bj...  begin date as an absolute Julian day number; e.g. -bj2415020.5\n"+
  "        -j...   same as -bj\n"+
  "        -tHH.MMSS  input time (ephemeris time)\n"+
  "        -ut     input date is universal time\n"+
  "	-utHH:MM:SS input time\n"+
  "	-utHH.MMSS input time\n"+
  "     output time for eclipses, occultations, risings/settings is UT by default\n"+
  "        -lmt    output date/time is LMT (with -geopos)\n"+
  "        -lat    output date/time is LAT (with -geopos)\n"+
  "     object, number of steps, step with\n"+
  "        -pSEQ   planet sequence to be computed.\n"+
  "                See the letter coding below.\n"+
  "        -dX     differential ephemeris: print differential ephemeris between\n"+
  "                body X and each body in list given by -p\n"+
  "                example: -p2 -d0 -fJl -n366 -b1.1.1992 prints the longitude\n"+
  "                distance between SUN (planet 0) and MERCURY (planet 2)\n"+
  "                for a full year starting at 1 Jan 1992.\n"+
  "	-DX	midpoint ephemeris, works the same way as the differential\n"+
  "		mode -d described above, but outputs the midpoint position.\n"+
  "        -nN     output data for N consecutive days; if no -n option\n"+
  "                is given, the default is 1. If the option -n without a\n"+
  "                number is given, the default is 20.\n"+
  "        -sN     timestep N days, default 1. This option is only meaningful\n"+
  "                when combined with option -n.\n"+
  "";
  static final String infocmd2 =
  "     output format:\n"+
  "        -fSEQ   use SEQ as format sequence for the output columns;\n"+
  "                default is PLBRS.\n"+
  "        -head   don\'t print the header before the planet data. This option\n"+
  "                is useful when you want to paste the output into a\n"+
  "                spreadsheet for displaying graphical ephemeris.\n"+
  "        +head   header before every step (with -s..) \n"+
  "        -gPPP   use PPP as gap between output columns; default is a single\n"+
  "                blank.  -g followed by white space sets the\n"+
  "                gap to the TAB character; which is useful for data entry\n"+
  "                into spreadsheets.\n"+
  "     astrological house system:\n"+
  "        -house[long,lat,hsys]	\n"+
  "		include house cusps. The longitude, latitude (degrees with\n"+
  "		DECIMAL fraction) and house system letter can be given, with\n"+
  "		commas separated, + for east and north. If none are given,\n"+
  "		Greenwich UK and Placidus is used: 0.00,51.50,p.\n"+
  "		The output lists 12 house cusps, Asc, MC, ARMC and Vertex.\n"+
  "		Houses can only be computed if option -ut is given.\n"+
  "                   A  equal\n"+
  "                   E  equal\n"+
  "                   B  Alcabitius\n"+
  "                   C  Campanus\n"+
  "                   G  36 Gauquelin sectors\n"+
  "                   H  horizon / azimut\n"+
  "                   K  Koch\n"+
  "                   M  Morinus\n"+
  "                   O  Porphyry\n"+
  "                   P  Placidus\n"+
  "                   R  Regiomontanus\n"+
  "                   T  Polich/Page (\"topocentric\")\n"+
  "                   U  Krusinski-Pisa-Goelzer\n"+
  "                   V  equal Vehlow\n"+
  "                   W  equal, whole sign\n"+
  "                   X  axial rotation system/ Meridian houses\n"+
  "                   Y  APC houses\n"+
  "        -hsy[hsys]	\n"+
  "		house system to be used (for house positions of planets)\n"+
  "		for long, lat, hsys, see -house\n"+
  "        -geopos[long,lat,elev]	\n"+
  "		Geographic position. Can be used for azimuth and altitude\n"+
  "                or topocentric or house cups calculations.\n"+
  "                The longitude, latitude (degrees with DECIMAL fraction)\n"+
  "		and elevation (meters) can be given, with\n"+
  "		commas separated, + for east and north. If none are given,\n"+
  "		Greenwich is used: 0,51.5,0\n"+
  "     sidereal astrology:\n"+
  "	-ay..   ayanamsa, with number of method, e.g. ay0 for Fagan/Bradley\n"+
  "	-sid..    sidereal, with number of method; 'sid0' for Fagan/Bradley\n"+
  "	                                           'sid1' for Lahiri\n"+
  "	-sidt0..  sidereal, projection on ecliptic of t0 \n"+
  "	-sidsp..  sidereal, projection on solar system plane \n"+
  "";
  static final String infocmd3 = ""+
  "     ephemeris specifications:\n"+
  "        -edirPATH change the directory of the ephemeris files \n"+
  "        -eswe   swiss ephemeris\n"+
#ifndef NO_JPL
  "        -ejpl   jpl ephemeris (DE431), or with ephemeris file name\n"+
  "                -ejplde200.eph \n"+
#endif /* NO_JPL */
  "        -emos   moshier ephemeris\n"+
  "        -true             true positions\n"+
  "        -noaberr          no aberration\n"+
  "        -nodefl           no gravitational light deflection\n"+
  "	-noaberr -nodefl  astrometric positions\n"+
  "        -j2000            no precession (i.e. J2000 positions)\n"+
  "        -icrs             ICRS (use Internat. Celestial Reference System)\n"+
  "        -nonut            no nutation \n"+
  "        -speed            calculate high precision speed \n"+
  "        -speed3           'low' precision speed from 3 positions \n"+
  "                          do not use this option. -speed parameter\n"+
  "			  is faster and preciser \n"+
  "	-iXX	          force iflag to value XX\n"+
#ifndef NO_JPL
  "        -testaa96         test example in AA 96, B37,\n"+
  "                          i.e. venus, j2450442.5, DE200.\n"+
  "                          attention: use precession IAU1976\n"+
  "                          and nutation 1980 (s. swephlib.h)\n"+
  "        -testaa95\n"+
  "        -testaa97\n"+
#endif /* NO_JPL */
  "        -roundsec         round to seconds\n"+
  "        -roundmin         round to minutes\n"+
  "";
  static final String infocmd4 = ""+
  "     observer position:\n"+
  "        -hel    compute heliocentric positions\n"+
  "        -bary   compute barycentric positions (bar. earth instead of node) \n"+
  "        -topo[long,lat,elev]	\n"+
  "		topocentric positions. The longitude, latitude (degrees with\n"+
  "		DECIMAL fraction) and elevation (meters) can be given, with\n"+
  "		commas separated, + for east and north. If none are given,\n"+
  "		Zuerich is used: 8.55,47.38,400\n"+
  "\n"+
#ifndef ASTROLOGY
  "     special events:\n"+
  "        -solecl solar eclipse\n"+
  "                output 1st line:\n"+
  "                  eclipse date,\n"+
  "                  time of maximum (UT),\n"+
  "                  core shadow width (negative with total eclipses),\n"+
  "                  fraction of solar diameter that is eclipsed\n"+
  "		  Julian day number (6-digit fraction) of maximum\n"+
  "                output 2nd line:\n"+
  "                  start and end times for partial and total phase\n"+
  "                output 3rd line:\n"+
  "                  geographical longitude and latitude of maximum eclipse,\n"+
  "                  totality duration at that geographical position,\n"+
  "                output with -local, see below.\n"+
  "        -occult occultation of planet or star by the moon. Use -p to \n"+
  "                specify planet (-pf -xfAldebaran for stars) \n"+
  "                output format same as with -solecl\n"+
  "        -lunecl lunar eclipse\n"+
  "                output 1st line:\n"+
  "                  eclipse date,\n"+
  "                  time of maximum (UT),\n"+
  "		  Julian day number (6-digit fraction) of maximum\n"+
  "                output 2nd line:\n"+
  "                  6 contacts for start and end of penumbral, partial, and\n"+
  "                  total phase\n"+
#endif /* ASTROLOGY */
  "";
static final String infocmd5 = ""+
  "        -local  only with -solecl or -occult, if the next event of this\n"+
  "                kind is wanted for a given geogr. position.\n"+
  "                Use -geopos[long,lat,elev] to specify that position.\n"+
  "                If -local is not set, the program \n"+
  "                searches for the next event anywhere on earth.\n"+
  "                output 1st line:\n"+
  "                  eclipse date,\n"+
  "                  time of maximum,\n"+
  "                  fraction of solar diameter that is eclipsed\n"+
  "                output 2nd line:\n"+
  "                  local eclipse duration,\n"+
  "                  local four contacts,\n"+
#ifndef JAVAME
#ifndef ASTROLOGY
  "        -hev[type] heliacal events,\n"+
  "		type 1 = heliacal rising\n"+
  "		type 2 = heliacal setting\n"+
  "		type 3 = evening first\n"+
  "		type 4 = morning last\n"+
  "	        type 0 or missing = all four events are listed.\n"+
  ""+
#endif /* ASTROLOGY */
#endif /* JAVAME */
  "        -rise   rising and setting of a planet or star.\n"+
  "                Use -geopos[long,lat,elev] to specify geographical position.\n"+
#ifndef ASTROLOGY
  "        -metr   southern and northern meridian transit of a planet of star\n"+
  "                Use -geopos[long,lat,elev] to specify geographical position.\n"+
  "     specifications for eclipses:\n"+
  "        -total  total eclipse (only with -solecl, -lunecl)\n"+
  "        -partial partial eclipse (only with -solecl, -lunecl)\n"+
  "        -annular annular eclipse (only with -solecl)\n"+
  "        -anntot annular-total (hybrid) eclipse (only with -solecl)\n"+
  "        -penumbral penumbral lunar eclipse (only with -lunecl)\n"+
  "        -central central eclipse (only with -solecl, nonlocal)\n"+
  "        -noncentral non-central eclipse (only with -solecl, nonlocal)\n"+
#endif /* ASTROLOGY */
  "     specifications for risings and settings:\n"+
  "        -norefrac   neglect refraction (with option -rise)\n"+
  "        -disccenter find rise of disc center (with option -rise)\n"+
  "	-hindu      hindu version of sunrise (with option -rise)\n"+
  "";
static final String infocmd6 = ""+
  "     specifications for heliacal events:\n"+
  "        -at[press,temp,rhum,visr]:\n"+
  "	            pressure in hPa\n"+
  "		    temperature in degrees Celsius\n"+
  "		    relative humidity in %\n"+
  "		    visual range, interpreted as follows:\n"+
  "		      > 1 : meteorological range in km\n"+
  "		      1>visr>0 : total atmospheric coefficient (ktot)\n"+
  "		      = 0 : calculated from press, temp, rhum\n"+
  "		    Default values are -at1013.25,15,40,0\n"+
  "         -obs[age,SN] age of observer and Snellen ratio\n"+
  "	            Default values are -obs36,1\n"+
  "         -opt[age,SN,binocular,magn,diam,transm]\n"+
  "	            age and SN as with -obs\n"+
  "		    0 monocular or 1 binocular\n"+
  "		    telescope magnification\n"+
  "		    optical aperture in mm\n"+
  "		    optical transmission\n"+
  "		    Default values: -opt36,1,1,1,0,0 (naked eye)\n"+
  "     backward search:\n"+
  "        -bwd\n";
/* characters still available:
    bcgijklruvxy
 */
  static final String infoplan = "\n"+
  "  Planet selection letters:\n"+
  "     planetary lists:\n"+
  "        d (default) main factors 0123456789mtABCcg\n"+
  "        p main factors as above, plus main asteroids DEFGHI\n"+
  "        h ficticious factors J..X\n"+
  "        a all factors\n"+
  "        (the letters above can only appear as a single letter)\n\n"+
  "     single planet letters:\n"+
  "        0 Sun (character zero)\n"+
  "        1 Moon (character 1)\n"+
  "        2 Mercury\n"+
  "        ....\n"+
  "        9 Pluto\n"+
  "        m mean lunar node\n"+
  "        t true lunar node\n"+
#ifndef ASTROLOGY
  "        n nutation\n"+
#endif /* ASTROLOGY */
  "        o obliquity of ecliptic\n"+
  "	q delta t\n"+
  "	y time equation\n"+
  "        A mean lunar apogee (Lilith, Black Moon) \n"+
#ifndef ASTROLOGY
  "        B osculating lunar apogee \n"+  // True Lilith
  "        c intp. lunar apogee \n"+
  "        g intp. lunar perigee \n"+
#endif /* ASTROLOGY */
  "        C Earth (in heliocentric or barycentric calculation)\n"+
  "     dwarf planets, plutoids\n"+
  "        F Ceres\n"+
  "	9 Pluto\n"+
  "	s -xs136199   Eris\n"+
  "	s -xs136472   Makemake\n"+
  "	s -xs136108   Haumea\n"+
  "     some minor planets:\n"+
  "        D Chiron\n"+
#ifndef ASTROLOGY
  "        E Pholus\n"+
  "        G Pallas \n"+
  "        H Juno \n"+
  "        I Vesta \n"+
  "        s minor planet, with MPC number given in -xs\n"+
  "     fixed stars:\n"+
  "        f fixed star, with name or number given in -xf option\n"+
  "	f -xfSirius   Sirius\n"+
  "     fictitious objects:\n"+
  "        J Cupido \n"+
  "        K Hades \n"+
  "        L Zeus \n"+
  "        M Kronos \n"+
  "        N Apollon \n"+
  "        O Admetos \n"+
  "        P Vulkanus \n"+
  "        Q Poseidon \n"+
  "        R Isis (Sevin) \n"+
  "        S Nibiru (Sitchin) \n"+
  "        T Harrington \n"+
  "        U Leverrier's Neptune\n"+
  "        V Adams' Neptune\n"+
  "        W Lowell's Pluto\n"+
  "        X Pickering's Pluto\n"+
  "        Y Vulcan\n"+
  "        Z White Moon\n"+
  "	w Waldemath's dark Moon\n"+
  "        z hypothetical body, with number given in -xz\n"+
#endif /* ASTROLOGY */
  "        e print a line of labels\n"+
  "          \n";
/* characters still available
   cgjv
*/
  static final String infoform = "\n"+
  "  Output format SEQ letters:\n"+
  "  In the standard setting five columns of coordinates are printed with\n"+
  "  the default format PLBRS. You can change the default by providing an\n"+
  "  option like -fCCCC where CCCC is your sequence of columns.\n"+
  "  The coding of the sequence is like this:\n"+
  "        y year\n"+
  "        Y year.fraction_of_year\n"+
  "        p planet index\n"+
  "        P planet name\n"+
  "        J absolute juldate\n"+
  "        T date formatted like 23.02.1992 \n"+
  "        t date formatted like 920223 for 1992 february 23\n"+
  "        L longitude in degree ddd mm'ss\"\n"+
  "        l longitude decimal\n"+
  "        Z longitude ddsignmm'ss\"\n"+
  "        S speed in longitude in degree ddd:mm:ss per day\n"+
  "        SS speed for all values specified in fmt\n"+
  "        s speed longitude decimal (degrees/day)\n"+
  "        ss speed for all values specified in fmt\n"+
  "        B latitude degree\n"+
  "        b latitude decimal\n"+
  "        R distance decimal in AU\n"+
  "        r distance decimal in AU, Moon in seconds parallax\n"+
  "          relative distance (1000=nearest, 0=furthest)\n"+
  "        A right ascension in hh:mm:ss\n"+
  "        a right ascension hours decimal\n"+
  "        D declination degree\n"+
  "        d declination decimal\n"+
  "        I Azimuth degree\n"+
  "        i Azimuth decimal\n"+
  "        H Height degree\n"+
  "        h Height decimal\n"+
  "        K Height (with refraction) degree\n"+
  "        k Height (with refraction) decimal\n"+
  "        G house position in degrees\n"+
  "        g house position in degrees decimal\n"+
  "        j house number 1.0 - 12.99999\n"+
  "        X x-, y-, and z-coordinates ecliptical\n"+
  "        x x-, y-, and z-coordinates equatorial\n"+
  "        U unit vector ecliptical\n"+
  "        u unit vector equatorial\n"+
  "        Q l, b, r, dl, db, dr, a, d, da, dd\n"+
#ifndef ASTROLOGY
  "	n nodes (mean): ascending/descending (Me - Ne); longitude decimal\n"+
  "	N nodes (osculating): ascending/descending, longitude; decimal\n"+
  "	f apsides (mean): perihel, aphel, second focal point; longitude dec.\n"+
  "	F apsides (osc.): perihel, aphel, second focal point; longitude dec.\n"+
  "	+ phase angle\n"+
  "	- phase\n"+
  "	* elongation\n"+
  "	/ apparent diameter of disc (without refraction)\n"+
  "	= magnitude\n"+
#endif /* ASTROLOGY */
  "";
  static final String infoform2 = ""+
  "        v (reserved)\n"+
  "        V (reserved)\n"+
  "	";
  static final String infodate = "\n"+
  "  Date entry:\n"+
  "  In the interactive mode, when you are asked for a start date,\n"+
  "  you can enter data in one of the following formats:\n"+
  "\n"+
  "        1.2.1991        three integers separated by a nondigit character for\n"+
  "                        day month year. Dates are interpreted as Gregorian\n"+
  "                        after 4.10.1582 and as Julian Calendar before.\n"+
  "                        Time is always set to midnight.\n"+
  "                        If the three letters jul are appended to the date,\n"+
  "                        the Julian calendar is used even after 1582.\n"+
  "                        If the four letters greg are appended to the date,\n"+
  "                        the Gregorian calendar is used even before 1582.\n"+
  "\n"+
  "        j2400123.67     the letter j followed by a real number, for\n"+
  "                        the absolute Julian daynumber of the start date.\n"+
  "                        Fraction .5 indicates midnight, fraction .0\n"+
  "                        indicates noon, other times of the day can be\n"+
  "                        chosen accordingly.\n"+
  "\n"+
  "        <RETURN>        repeat the last entry\n"+
  "        \n"+
  "        .               stop the program\n"+
  "\n"+
  "        +20             advance the date by 20 days\n"+
  "\n"+
  "        -10             go back in time 10 days\n";
  static final String infoexamp = "\n"+
  "\n"+
  "  Examples:\n"+
  "\n"+
  "    java Swetest -p2 -b1.12.1900 -n15 -s2\n"+
  "	ephemeris of Mercury (-p2) starting on 1 Dec 1900,\n"+
  "	15 positions (-n15) in two-day steps (-s2)\n"+
  "\n"+
  "    java Swetest -p2 -b1.12.1900 -n15 -s2 -fTZ -roundsec -g, -head\n"+
  "	same, but output format =  date and zodiacal position (-fTZ),\n"+
  "	separated by comma (-g,) and rounded to seconds (-roundsec),\n"+
  "	without header (-head).\n"+
  "\n"+
  "    java Swetest -ps -xs433 -b1.12.1900\n"+
  "	position of asteroid 433 Eros (-ps -xs433)\n"+
  "\n"+
  "    java Swetest -pf -xfAldebaran -b1.1.2000\n"+
  "	position of fixed star Aldebaran \n"+
  "\n"+
  "    java Swetest -p1 -d0 -b1.12.1900 -n10 -fPTl -head\n"+
  "	angular distance of moon (-p1) from sun (-d0) for 10\n"+
  "	consecutive days (-n10).\n"+
  "\n"+
  "    java Swetest -p6 -DD -b1.12.1900 -n100 -s5 -fPTZ -head -roundmin\n"+
  "      Midpoints between Saturn (-p6) and Chiron (-DD) for 100\n"+
  "      consecutive steps (-n100) with 5-day steps (-s5) with\n"+
  "      longitude in degree-sign format (-f..Z) rounded to minutes (-roundmin)\n"+
  "\n"+
  "    java Swetest -b5.1.2002 -p -house12.05,49.50,k -ut12:30\n"+
  "	Koch houses for a location in Germany at a given date and time\n";
  /**************************************************************/

  SwissData swed = new SwissData();
  SwissLib  sl   = new SwissLib();
  SwissEph  sw   = new SwissEph();
  SweDate   sd   = null;
#ifndef JAVAME
#ifndef ASTROLOGY
  SweHel    sh   = new SweHel(sw, sl, null, swed);
#endif /* ASTROLOGY */
#endif /* JAVAME */
  CFmt      f    = new CFmt();

  static final double J2000=2451545.0;  /* 2000 January 1.5 */
  public double square_sum(double x[]) { return x[0]*x[0]+x[1]*x[1]+x[2]*x[2]; }

  static final int BIT_ROUND_SEC=1;
  static final int BIT_ROUND_MIN=2;
  static final int BIT_ZODIAC=4;
  static final int BIT_LZEROES=8;

  static final int BIT_TIME_LZEROES =  8;
  static final int BIT_TIME_LMT     = 16;
  static final int BIT_TIME_LAT     = 32;

  static final String PLSEL_D="0123456789mtA";
  static final String PLSEL_P="0123456789mtA"+
#ifndef ASTROLOGY
"BCcg"+
#endif /* ASTROLOGY */
                              "DEFGHI";
  static final String PLSEL_H="JKLMNOPQRSTUVWXYZw";
#ifdef ASTROLOGY
  static final String PLSEL_A="0123456789mtACDEFGHIJKLMNOPQRSTUVWXYZ";
#else
  static final String PLSEL_A="0123456789mtABCcgDEFGHIJKLMNOPQRSTUVWXYZw";
#endif /* ASTROLOGY */

  static final char DIFF_DIFF='d';
  static final char DIFF_MIDP='D';
  static final int MODE_HOUSE=1;
  static final int MODE_LABEL=2;

  String se_pname;
  static final String[] zod_nam = new String[]
                                {"ar", "ta", "ge", "cn", "le", "vi",
                                 "li", "sc", "sa", "cp", "aq", "pi"};

  String star = "algol", star2;
  String sastno = "433";
  String shyp = "1";

  /* globals shared between main() and print_line() */
  private String fmt = "PLBRS";
  private String gap = " ";
  private double t, te, tut, jut = 0;
  private int jmon, jday, jyear;
  private int ipl = SweConst.SE_SUN, ipldiff = SweConst.SE_SUN, nhouses = 12;
  private String spnam, spnam2="";
  private StringBuffer serr=new StringBuffer();
  private StringBuffer serr_save=new StringBuffer();
  private StringBuffer serr_warn=new StringBuffer();
  private boolean gregflag = SweDate.SE_GREG_CAL;
  private int diff_mode = 0;
  private boolean universal_time = false;
  private int round_flag = 0;
  private int time_flag = 0;
  private boolean short_output = false;
  private int special_event = 0;
  private int special_mode = 0;
  private boolean hel_using_AV = false;
  private double x[]=new double[6], x2[]=new double[6], xequ[]=new double[6],
         xcart[]=new double[6], xcartq[]=new double[6],
         xobl[]=new double[6], xaz[]=new double[6], xt[]=new double[6],
         hpos, hpos2, armc, xsv[]=new double[6];
  private DblObj hposj=new DblObj(); /* double used as output parameter */
  private int hpos_meth = 0;
  private double geopos[]=new double[10];
  private double attr[]=new double[20], tret[]=new double[20],
         datm[]=new double[4], dobs[]=new double[6];
  private int iflag = 0, iflag2;              /* external flag: helio, geo... */
  private static final String hs_nam[] = {"undef",
        "Ascendant", "MC", "ARMC", "Vertex"};
  private int direction = 1;
  private boolean direction_flag = false;
  private int helflag = 0;
  private double tjd = 2415020.5;
  private int nstep = 1, istep;
  private int search_flag = 0;
  private String sout;
#ifdef JAVAME
  private int whicheph = SweConst.SEFLG_MOSEPH;
#else
  private int whicheph = SweConst.SEFLG_SWIEPH;
#endif /* JAVAME */
  private String psp;
  private int p=0; // Index for psp
  private boolean norefrac = false;
  private boolean disccenter = false;

  static final int SP_LUNAR_ECLIPSE    = 1;
  static final int SP_SOLAR_ECLIPSE    = 2;
  static final int SP_OCCULTATION      = 3;
  static final int SP_RISE_SET         = 4;
  static final int SP_MERIDIAN_TRANSIT = 5;
  static final int SP_HELIACAL         = 6;

  static final int SP_MODE_HOW      = 2;       /* an option for Lunar */
  static final int SP_MODE_LOCAL    = 8;       /* an option for Solar */
  static final int SP_MODE_HOCAL    = 4096;


  static final int ECL_LUN_PENUMBRAL     = 1;       /* eclipse types for hocal list */
  static final int ECL_LUN_PARTIAL       = 2;
  static final int ECL_LUN_TOTAL         = 3;
  static final int ECL_SOL_PARTIAL       = 4;
  static final int ECL_SOL_ANNULAR       = 5;
  static final int ECL_SOL_TOTAL         = 6;
  private static final int AS_MAXCH     = 256;






  String SE_EPHE_PATH="";

  /**
  * This class is not to be instantiated, it is to be run via the main method.
  */
  private Swetest() { }

  /**
  * Use the parameter -h to get infos about the available options.
  */
  public static void main(String argv[]) {
    Swetest swt=new Swetest();
    System.exit(swt.main_start(argv));
  }

  private int main_start(String[] argv) {
    String sdate_save = "";
    String s1, s2;
    String sp, sp2;
    String spno;
    String plsel = PLSEL_D;
    int i, j, n, iflag_f = -1, iflgt;
    int line_count, line_limit = 3200000;
    double daya;
    double top_long = 0; /* Greenwich, UK */
    double top_lat = 51.5;
    double top_elev = 0;
    boolean have_geopos = false;
    int ihsy = (int)'p';
    boolean do_houses = false;
    String ephepath;
#ifndef NO_JPL
    String fname;
#endif /* NO_JPL */
    String sdate;
    String begindate = null;
    long iflgret;
    boolean with_header = true;
    boolean with_header_always = false;
    boolean do_ayanamsa = false;
    int sid_mode = SweConst.SE_SIDM_FAGAN_BRADLEY;
    double t2, tstep = 1, thour = 0;
    double delt;
    datm[0] = 0; datm[1] = 0; datm[2] = 0; datm[3] = 0;
    dobs[0] = 0; dobs[1] = 0;
    dobs[2] = 0; dobs[3] = 0; dobs[4] = 0; dobs[5] = 0;
/*  swe_set_tid_acc(-25.858); * to test delta t output */
    serr.setLength(0); serr_save.setLength(0); serr_warn.setLength(0);
    sdate_save = "";

    sd=new SweDate(tjd,gregflag);

    ephepath="";
#ifndef NO_JPL
    fname=SweConst.SE_FNAME_DFT;
#endif /* NO_JPL */
    for (i = 0; i < argv.length; i++) {
      if (argv[i].startsWith("-DSE_EPHE_PATH")) {
        if (++i<argv.length) {
          SE_EPHE_PATH=argv[i];
        }
      } else if (argv[i].startsWith("-ut")) {
        // hh:mmss or hh:mm:ss
        universal_time = true;
        if (argv[i].length() > 3) {
          s1 = "\n" + argv[i].substring(3);
          s1 = s1.substring(0, SMath.min(s1.length(), 30));
          if (s1.indexOf(':')>=0) {
            s1=s1.substring(0,s1.indexOf(':'))+"."+s1.substring(s1.indexOf(':')+1);
            if (s1.indexOf(':')>=0) {
              s1=s1.substring(0,s1.indexOf(':'))+s1.substring(s1.indexOf(':')+1);
            }
          }

          // Extension to allow fraction of seconds; not internationalized.
          double frac = 0;
#ifndef ORIGINAL
          if (s1.indexOf('.') != s1.lastIndexOf('.')) {
            // HH:MM:SS.FFF
            frac = Double.valueOf(
                s1.substring(s1.lastIndexOf('.'))).doubleValue();
            s1 = s1.substring(0,s1.lastIndexOf('.'));
          }
#endif /* ORIGINAL */

          thour = Double.valueOf(s1).doubleValue();
          /* h.mmss -> decimal */
          // Allowing for negative times: this is different from the C code!
#ifdef ORIGINAL
          t = (thour%1.0) * 100 + 1e-6;
#else
          t = (thour%1.0) * 100 + (thour<0?-1e-6:1e-6);
#endif /* ORIGINAL */
          j = (int) t;
          t = (t%1.0) * 100 + 1e-6;
          thour = (int) thour + j / 60.0 + t / 3600.0 + frac / 3600.0;
        }
      } else if (argv[i].startsWith("-head")) {
        with_header = false;
      } else if (argv[i].startsWith("+head")) {
        with_header_always = true;
      } else if (argv[i].equals("-j2000")) {
        iflag |= SweConst.SEFLG_J2000;
      } else if (argv[i].equals("-icrs")) {
        iflag |= SweConst.SEFLG_ICRS;
      } else if (argv[i].startsWith("-ay")) {
        do_ayanamsa = true;
        sid_mode=0;
        if (argv[i].length()>3) {
          sid_mode=Integer.parseInt(argv[i].substring(3));
        }
        sw.swe_set_sid_mode(sid_mode, 0, 0);
#ifndef ASTROLOGY
      } else if (argv[i].startsWith("-sidt0")) {
        iflag |= SweConst.SEFLG_SIDEREAL;
//      sid_mode = atol(argv[i]+6);
        sid_mode=0;
        if (argv[i].length()>6) {
          sid_mode=Integer.parseInt(argv[i].substring(6));
        }
        if (sid_mode == 0)
          sid_mode = SweConst.SE_SIDM_FAGAN_BRADLEY;
        sid_mode |= SweConst.SE_SIDBIT_ECL_T0;
        sw.swe_set_sid_mode(sid_mode, 0, 0);
      } else if (argv[i].startsWith("-sidsp")) {
        iflag |= SweConst.SEFLG_SIDEREAL;
//      sid_mode = atol(argv[i]+6);
        sid_mode=0;
        if (argv[i].length()>6) {
          sid_mode=Integer.parseInt(argv[i].substring(6));
        }
        if (sid_mode == 0)
          sid_mode = SweConst.SE_SIDM_FAGAN_BRADLEY;
        sid_mode |= SweConst.SE_SIDBIT_SSY_PLANE;
        sw.swe_set_sid_mode(sid_mode, 0, 0);
#endif /* ASTROLOGY */
      } else if (argv[i].startsWith("-sid")) {
        iflag |= SweConst.SEFLG_SIDEREAL;
//      sid_mode = atol(argv[i]+4);
        sid_mode=0;
        if (argv[i].length()>4) {
          try {
            sid_mode=Integer.parseInt(argv[i].substring(4));
          } catch (NumberFormatException nf) {
            // Anything not being a number will be considered '0'
          }
        }
        if (sid_mode > 0)
          sw.swe_set_sid_mode(sid_mode, 0, 0);
      } else if (argv[i].equals("-jplhora")) {
        iflag |= SweConst.SEFLG_JPLHOR_APPROX;
      } else if (argv[i].equals("-jplhor")) {
        iflag |= SweConst.SEFLG_JPLHOR;
      } else if (argv[i].startsWith("-j")) {
        begindate = argv[i].substring(1);
#ifndef NO_JPL
      } else if (argv[i].startsWith("-ejpl")) {
        whicheph = SweConst.SEFLG_JPLEPH;
        if (argv[i].length()>5) {
          fname=argv[i].substring(5);
          fname=fname.substring(0, SMath.min(fname.length(), AS_MAXCH - 1));
        }
#endif /* NO_JPL */
      } else if (argv[i].startsWith("-edir")) {
        if (argv[i].length() > 5) {
          ephepath=argv[i].substring(5);
          ephepath=ephepath.substring(0, SMath.min(ephepath.length(), AS_MAXCH - 1));
        }
#ifndef JAVAME
      } else if (argv[i].startsWith("-eswe")) {
        whicheph = SweConst.SEFLG_SWIEPH;
#endif /* JAVAME */
      } else if (argv[i].startsWith("-emos")) {
        whicheph = SweConst.SEFLG_MOSEPH;
      } else if (argv[i].startsWith("-helflag")) {
        helflag = SwissLib.atoi(argv[i].substring(8));
        if (helflag >= SweConst.SE_HELFLAG_AV)
          hel_using_AV = true;
      } else if (argv[i].equals("-hel")) {
        iflag |= SweConst.SEFLG_HELCTR;
      } else if (argv[i].equals("-bary")) {
        iflag |= SweConst.SEFLG_BARYCTR;
      } else if (argv[i].startsWith("-house")) {
        sout="";
// Java: use atof()?
        // sscanf(argv[i] + 6, "%lf,%lf,%c", &top_long, &top_lat, sout);
        try {
          String h=argv[i].substring(6);
          int idx=h.indexOf(',');
          String hf=h.substring(0,idx);
          top_long=Double.valueOf(hf).doubleValue();
          h=h.substring(idx+1);
          idx=h.indexOf(',');
          if (idx<0) { idx=h.length(); }
          hf=h.substring(0,idx);
          top_lat=Double.valueOf(hf).doubleValue();
          if (h.length() > idx) {
            sout = h.substring(idx+1, idx+2);
          }
        } catch (NumberFormatException nfe) {
        } catch (StringIndexOutOfBoundsException aie) {
        }
        top_elev = 0;
        if (sout.length()>0) { ihsy = sout.charAt(0); }
        do_houses = true;
        have_geopos = true;
      } else if (argv[i].startsWith("-hsy")) {
        if (argv[i].length()<5) {
          ihsy = 'p';
        } else {
          ihsy = argv[i].charAt(4);
        }
        if (argv[i].length()>5) {
          hpos_meth = Integer.parseInt(argv[i].substring(5));
        }
        have_geopos = true;
      } else if (argv[i].startsWith("-topo") ||
                 argv[i].startsWith("-geopos")) {
        int plen=(argv[i].startsWith("-topo")?5:7);
        if (plen==5) { iflag |= SweConst.SEFLG_TOPOCTR; }
//      sscanf(argv[i] + 5, "%lf,%lf,%lf", &top_long, &top_lat, &top_elev);
        if (argv[i].length()>plen) {
          String fl=argv[i].substring(plen);
          top_long=Double.parseDouble(fl.substring(0,fl.indexOf(',')));
          fl=fl.substring(fl.indexOf(',')+1);
          top_lat=Double.parseDouble(fl.substring(0,fl.indexOf(',')));
          fl=fl.substring(fl.indexOf(',')+1);
          top_elev=Double.parseDouble(fl);
        }
        have_geopos = true;
      } else if (argv[i].equals("-true")) {
        iflag |= SweConst.SEFLG_TRUEPOS;
      } else if (argv[i].equals("-noaberr")) {
        iflag |= SweConst.SEFLG_NOABERR;
      } else if (argv[i].equals("-nodefl")) {
        iflag |= SweConst.SEFLG_NOGDEFL;
      } else if (argv[i].equals("-nonut")) {
        iflag |= SweConst.SEFLG_NONUT;
      } else if (argv[i].equals("-speed3")) {
        iflag |= SweConst.SEFLG_SPEED3;
      } else if (argv[i].equals("-speed")) {
        iflag |= SweConst.SEFLG_SPEED;
#ifndef NO_JPL
      } else if (argv[i].startsWith("-testaa")) {
        whicheph = SweConst.SEFLG_JPLEPH;
        fname=SweConst.SE_FNAME_DE200;
        if (argv[i].substring(7).equals("95"))
          begindate = "j2449975.5";
        if (argv[i].substring(7).equals("96"))
          begindate = "j2450442.5";
        if (argv[i].substring(7).equals("97"))
          begindate = "j2450482.5";
        fmt = "PADRu";
        universal_time = false;
        plsel="3";
#endif /* NO_JPL */
      } else if (argv[i].equals("-lmt")) {
        universal_time = true;
        time_flag |= BIT_TIME_LMT;
      } else if (argv[i].equals("-lat")) {
        universal_time = true;
        time_flag |= BIT_TIME_LAT;
      } else if (argv[i].equals("-lunecl")) {
        special_event = SP_LUNAR_ECLIPSE;
      } else if (argv[i].equals("-solecl")) {
        special_event = SP_SOLAR_ECLIPSE;
        have_geopos = true;
      } else if (argv[i].equals("-short")) {
        short_output = true;
      } else if (argv[i].equals("-occult")) {
        special_event = SP_OCCULTATION;
        have_geopos = true;
      } else if (argv[i].equals("-hocal")) {
        /* used to create a listing for inclusion in hocal.c source code */
        special_event |= SP_MODE_HOCAL;
      } else if (argv[i].equals("-how")) {
    	special_mode |= SP_MODE_HOW;
      } else if (argv[i].equals("-total")) {
    	search_flag |= SweConst.SE_ECL_TOTAL|SweConst.SE_ECL_CENTRAL|SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-annular")) {
    	search_flag |= SweConst.SE_ECL_ANNULAR|SweConst.SE_ECL_CENTRAL|SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-anntot")) {
    	search_flag |= SweConst.SE_ECL_ANNULAR_TOTAL|SweConst.SE_ECL_CENTRAL|SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-partial")) {
    	search_flag |= SweConst.SE_ECL_PARTIAL|SweConst.SE_ECL_CENTRAL|SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-penumbral")) {
    	search_flag |= SweConst.SE_ECL_PENUMBRAL;
      } else if (argv[i].equals("-noncentral")) {
        search_flag &= ~SweConst.SE_ECL_CENTRAL;
        search_flag |= SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-central")) {
        search_flag &= ~SweConst.SE_ECL_NONCENTRAL;
        search_flag |= SweConst.SE_ECL_CENTRAL;
      } else if (argv[i].equals("-local")) {
    	special_mode |= SP_MODE_LOCAL;
      } else if (argv[i].equals("-rise")) {
    	special_event = SP_RISE_SET;
        have_geopos = true;
#ifndef ASTROLOGY
      } else if (argv[i].equals("-norefrac")) {
        norefrac = true;
      } else if (argv[i].equals("-disccenter")) {
        disccenter = true;
      } else if (argv[i].equals("-hindu")) {
        norefrac = true;
        disccenter = true;
      } else if (argv[i].equals("-metr")) {
    	special_event = SP_MERIDIAN_TRANSIT;
        have_geopos = true;
      } else if (argv[i].startsWith("-hev")) {
        special_event = SP_HELIACAL;
        search_flag = 0;
        if (argv[i].length() > 4)
          search_flag = SwissLib.atoi(argv[i].substring(4));
        have_geopos = true;
        if (argv[i].indexOf("A") >= 0 || argv[i].indexOf("V") >= 0) hel_using_AV = true;
      } else if (argv[i].startsWith("-at")) {
//        sscanf(argv[i]+3, "%lf,%lf,%lf,%lf", &(datm[0]), &(datm[1]), &(datm[2]), &(datm[3]));
    	StringTokenizer d = new StringTokenizer(argv[i].substring(3),",");
    	try {
    	  datm[0] = Double.parseDouble(d.nextToken());
    	  datm[1] = Double.parseDouble(d.nextToken());
    	  datm[2] = Double.parseDouble(d.nextToken());
    	  datm[3] = Double.parseDouble(d.nextToken());
    	} catch (NumberFormatException ne) {
    	}
      } else if (argv[i].startsWith("-obs")) {
//        sscanf(argv[i]+4, "%lf,%lf", &(dobs[0]), &(dobs[1]));
      	StringTokenizer d = new StringTokenizer(argv[i].substring(4),",");
    	try {
    	  dobs[0] = Double.parseDouble(d.nextToken());
    	  dobs[1] = Double.parseDouble(d.nextToken());
    	} catch (NumberFormatException ne) {
    	}
      } else if (argv[i].startsWith("-opt")) {
//        sscanf(argv[i]+4, "%lf,%lf,%lf,%lf,%lf,%lf", &(dobs[0]), &(dobs[1]), &(dobs[2]), &(dobs[3]), &(dobs[4]), &(dobs[5]));
       	StringTokenizer d = new StringTokenizer(argv[i].substring(4),",");
      	try {
      	  dobs[0] = Double.parseDouble(d.nextToken());
      	  dobs[1] = Double.parseDouble(d.nextToken());
      	  dobs[2] = Double.parseDouble(d.nextToken());
      	  dobs[3] = Double.parseDouble(d.nextToken());
      	  dobs[4] = Double.parseDouble(d.nextToken());
      	  dobs[5] = Double.parseDouble(d.nextToken());
      	} catch (NumberFormatException ne) {
      	}
#endif /* ASTROLOGY */
      } else if (argv[i].equals("-bwd")) {
        direction = -1;
        direction_flag = true;
      } else if (argv[i].startsWith("-p")) {
        if (argv[i].length()>2) {
          spno = argv[i].substring(2);
          switch ((int)spno.charAt(0)) {
          case 'd':
          /*
          case '\0':
          case ' ':
          */
                          plsel = PLSEL_D; break;
          case 'p':  plsel = PLSEL_P; break;
          case 'h':  plsel = PLSEL_H; break;
          case 'a':  plsel = PLSEL_A; break;
          default:   plsel = spno;
          }
        } else {
          // We need at least one char in Java, to be able to use existing code
          plsel = " ";
        }
      } else if (argv[i].startsWith("-xs")) {
        /* number of asteroid */
        sastno="";
        if (argv[i].length()>3) {
          sastno=argv[i].substring(3);
          sastno=sastno.substring(0, SMath.min(sastno.length(), AS_MAXCH - 1));
        }
      } else if (argv[i].startsWith("-xf")) {
        /* name or number of fixed star */
        star="";
        if (argv[i].length()>3) {
          star=argv[i].substring(3);
          star=star.substring(0, SMath.min(star.length(), AS_MAXCH - 1));
        }
      } else if (argv[i].startsWith("-xz")) {
        /* number of hypothetical body */
        if (argv[i].length()>3) {
          shyp=argv[i].substring(3);
          shyp=shyp.substring(0, SMath.min(shyp.length(), AS_MAXCH - 1));
        }
      } else if (argv[i].startsWith("-x")) {
        /* name or number of fixed star */
        star="";
        if (argv[i].length()>2) {
          star=argv[i].substring(2);
          star=star.substring(0, SMath.min(star.length(), AS_MAXCH - 1));
        }
      } else if (argv[i].startsWith("-n")) {
        nstep=0;
        if (argv[i].length()>2) {
          nstep = Integer.parseInt(argv[i].substring(2));
        }
        if (nstep==0) {
          nstep=20;
        }
      } else if (argv[i].startsWith("-i")) {
        if (iflag_f<0) iflag_f = 0;
        if (argv[i].length()>2) {
          iflag_f = Integer.parseInt(argv[i].substring(2));
        }
        if ((iflag_f & SweConst.SEFLG_XYZ)!=0)
          fmt = "PX";
      } else if (argv[i].startsWith("-s")) {
        tstep=0;
        if (argv[i].length()>2) {
          tstep = Double.parseDouble(argv[i].substring(2));
        }
      } else if (argv[i].startsWith("-b")) {
        begindate="";
        if (argv[i].length()>2) {
          begindate = argv[i].substring(2);
        }
      } else if (argv[i].startsWith("-f")) {
        fmt="";
        if (argv[i].length()>2) {
          fmt = argv[i].substring(2);
        }
      } else if (argv[i].startsWith("-g")) {
        gap = "\t";
        if (argv[i].length()>2) {
          gap = argv[i].substring(2);
        }
      } else if (argv[i].startsWith("-d")
             || argv[i].startsWith("-D")) {
        diff_mode = (int)argv[i].charAt(1); /* 'd' or 'D' */
        ipldiff=-1;
        if (argv[i].length()>2) {
          sp = argv[i].substring(2);
          ipldiff = letter_to_ipl((int) sp.charAt(0));
        }
        if (ipldiff <0) ipldiff = SweConst.SE_SUN;
        spnam2=sw.swe_get_planet_name(ipldiff);
      } else if (argv[i].equals("-roundsec")) {
        round_flag |= BIT_ROUND_SEC;
      } else if (argv[i].equals("-roundmin")) {
        round_flag |= BIT_ROUND_MIN;
      } else if (argv[i].startsWith("-t")) {
        if (argv[i].length() > 2) {
          s1=argv[i].substring(2);
          if (s1.indexOf(':')>=0) {
            s1=s1.substring(0,s1.indexOf(':'))+"."+s1.substring(s1.indexOf(':')+1);
            if (s1.indexOf(':')>=0) {
              s1=s1.substring(0,s1.indexOf(':'))+s1.substring(s1.indexOf(':')+1);
            }
          }

          // Extension to allow fraction of seconds; not internationalized!
          double frac = 0;
          if (s1.indexOf('.') != s1.lastIndexOf('.')) {
            // HH:MM:SS.FFF
            frac = Double.valueOf(
                s1.substring(s1.lastIndexOf('.'))).doubleValue();
            s1 = s1.substring(0,s1.lastIndexOf('.'));
          }

          thour = Double.valueOf(s1).doubleValue();
          /* h.mmss -> decimal */
          // Allowing for negative times: this is different from the C code!
#ifdef ORIGINAL
          t = (thour%1.) * 100 + (thour<0?-1e-6:1e-6);
#else
          t = (thour%1.) * 100 + 1e-6;
#endif /* ORIGINAL */
          j = (int) t;
          t = (t%1.) * 100 + 1e-6;
          thour = (int) thour + j / 60.0 + t / 3600.0 + frac / 3600.0;
        }
      } else if (argv[i].startsWith("-h")
        || argv[i].startsWith("-?")) {
        sp=" ";
        if (argv[i].length()>2) {
          sp = argv[i].substring(2);
        }
        if (sp.charAt(0) == 'c' || sp.charAt(0) == ' ') {
          System.out.print(infocmd0);
          System.out.print(infocmd1);
          System.out.print(infocmd2);
          System.out.print(infocmd3);
          System.out.print(infocmd4);
          System.out.print(infocmd5);
          System.out.print(infocmd6);
        }
        if (sp.charAt(0) == 'p' || sp.charAt(0) == ' ')
          System.out.print(infoplan);
        if (sp.charAt(0) == 'f' || sp.charAt(0) == ' ') {
          System.out.print(infoform);
          System.out.print(infoform2);
        }
        if (sp.charAt(0) == 'd' || sp.charAt(0) == ' ')
          System.out.print(infodate);
        if (sp.charAt(0) == 'e' || sp.charAt(0) == ' ')
          System.out.print(infoexamp);
//      goto end_main;
        /* close open files and free allocated space */
        sw.swe_close();
        return SweConst.OK;
      } else {
        sout="illegal option "+argv[i].substring(0, SMath.min(argv[i].length(), AS_MAXCH-50))+"\n";
        System.out.print(sout);
        System.exit(1);
      }
    }
#ifndef ASTROLOGY
    if (special_event == SP_OCCULTATION ||
    	special_event == SP_RISE_SET ||
    	special_event == SP_MERIDIAN_TRANSIT ||
    	special_event == SP_HELIACAL) {
#else
    if (special_event == SP_OCCULTATION ||
    	special_event == SP_RISE_SET) {
#endif /* ASTROLOGY */
      ipl = letter_to_ipl((int)plsel.charAt(0));
      if (plsel.charAt(0) == 'f') {
        ipl = SweConst.SE_FIXSTAR;
      } else {
        star = "";
      }
      if (special_event == SP_OCCULTATION && ipl == 1)
        ipl = 2; /* no occultation of moon by moon */
    }
#ifdef PRELOAD_FIXSTARS
    if (plsel.indexOf('f') >= 0 && nstep > 3) {
      sw.preloadFixstarsFile(serr);
    }
#endif /* PRELOAD_FIXSTARS */
    geopos[0] = top_long;
    geopos[1] = top_lat;
    geopos[2] = top_elev;
    sw.swe_set_topo(top_long, top_lat, top_elev);
    if (with_header) {
System.out.print("swetest ");
      for (i = 0; i < argv.length; i++) {
        System.out.print(argv[i]);
        System.out.print(" ");
      }
    }
    iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | whicheph;
//  if (strpbrk(fmt, "SsQ") != NULL && !(iflag & SEFLG_SPEED3)) 
    if ((fmt.indexOf("S")>=0 || fmt.indexOf("s")>=0 || fmt.indexOf("Q")>=0) && (iflag & SweConst.SEFLG_SPEED3) == 0) {
      iflag |= SweConst.SEFLG_SPEED;
    }
    String argv0=System.getProperties().getProperty("user.dir");
//ephepath="./ephe";
    if (ephepath.length() == 0) {
      StringBuffer sbEphepath = new StringBuffer();
      if (make_ephemeris_path(iflag, argv[0], sbEphepath) == SweConst.ERR) {
        iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | SweConst.SEFLG_MOSEPH;
        whicheph = SweConst.SEFLG_MOSEPH;
      } else {
        ephepath = sbEphepath.toString();
      }
    }
    sw.swe_set_ephe_path(ephepath);
#ifndef NO_JPL
    if ((whicheph & SweConst.SEFLG_JPLEPH)!=0)
      sw.swe_set_jpl_file(fname);
#endif /* NO_JPL */
    while (true) {
      serr.setLength(0); serr_save.setLength(0); serr_warn.setLength(0);
      if (begindate == null) {
        System.out.print("\nDate ?");
        sdate = "";
//      if( !fgets(sdate, AS_MAXCH, stdin) ) goto end_main;
        try {
          InputStreamReader in=new InputStreamReader(System.in);
          BufferedReader bin=new BufferedReader(in);
          sdate=bin.readLine();
        } catch (IOException ie) {
          System.out.println(ie.getMessage());
        }
	if (sdate.length() <= 0) {	// goto end_main:
          sw.swe_close();
          return SweConst.OK;
        }
      } else {
        sdate = begindate.substring(0, SMath.min(begindate.length(), AS_MAXCH-1));
        begindate = ".";  /* to exit afterwards */
      }
      if (sdate.equals("-bary")) {
        iflag = iflag & ~SweConst.SEFLG_HELCTR;
        iflag |= SweConst.SEFLG_BARYCTR;
        sdate = "";
      } else if (sdate.equals("-hel")) {
        iflag = iflag & ~SweConst.SEFLG_BARYCTR;
        iflag |= SweConst.SEFLG_HELCTR;
        sdate = "";
      } else if (sdate.equals("-geo")) {
        iflag = iflag & ~SweConst.SEFLG_BARYCTR;
        iflag = iflag & ~SweConst.SEFLG_HELCTR;
        sdate = "";
#ifndef NO_JPL
      } else if (sdate.equals("-ejpl")) {
        iflag &= ~SweConst.SEFLG_EPHMASK;
        iflag |= SweConst.SEFLG_JPLEPH;
        sdate = "";
#endif /* NO_JPL */
#ifndef JAVAME
      } else if (sdate.equals("-eswe")) {
        iflag &= ~SweConst.SEFLG_EPHMASK;
        iflag |= SweConst.SEFLG_SWIEPH;
        sdate = "";
#endif /* JAVAME */
      } else if (sdate.equals("-emos")) {
        iflag &= ~SweConst.SEFLG_EPHMASK;
        iflag |= SweConst.SEFLG_MOSEPH;
        sdate = "";
      } else if (sdate.startsWith("-xs")) {
        /* number of asteroid */
        sastno=sdate.substring(3);
        sdate = "";
      }
//    swe_set_tid_acc((double) (iflag & SEFLG_EPHMASK));
      sp = sdate;
      if (sp.length()>0 && sp.charAt(0) == '.') {
//      goto end_main;
        /* close open files and free allocated space */
        sw.swe_close();
        return SweConst.OK;
      } else if (sp.length() == 0 || sp.charAt(0) == '\n' || sp.charAt(0) == '\r') {
        sdate=sdate_save;
      } else {
        sdate_save=sdate;
      }
      if ("".equals(sdate)) {
    	sdate = "j" + tjd;
      }
      if (sp.length()>0 && sp.charAt(0) == 'j') {   /* it's a day number */
        if (sp.indexOf(',') >= 0)
          sp=sp.substring(0,sp.indexOf(','))+'.'+sp.substring(sp.indexOf(',')+1);
//      sscanf(sp+1,"%lf", &tjd);
        tjd = Double.parseDouble(sp.substring(1));
        if (tjd < 2299160.5)
          gregflag = SweDate.SE_JUL_CAL;
        else
          gregflag = SweDate.SE_GREG_CAL;
        if (sp.indexOf("jul") >= 0)
          gregflag = SweDate.SE_JUL_CAL;
        else if (sp.indexOf("greg") >= 0)
          gregflag = SweDate.SE_GREG_CAL;
//      swe_revjul(tjd, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(tjd);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
      } else if (sp.length()>0 && sp.charAt(0) == '+') {
        n=0;
        if (sp.length()>1) {
          n = Integer.parseInt(sp.substring(1));
        }
        if (n == 0) n = 1;
        tjd += n;
//      swe_revjul(tjd, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(tjd);
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
      } else if (sp.length()>0 && sp.charAt(0) == '-') {
        n = Integer.parseInt(sp);
        if (n == 0) n = -1;
        tjd += n;
//      swe_revjul(tjd, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(tjd);
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
      } else {
//      if (sscanf (sp, "%d%*c%d%*c%d", &jday,&jmon,&jyear) < 1) exit(1);
        jday=jmon=jyear=0;
        boolean neg=false;
        i=0;
        try {
          neg=sp.charAt(0)=='-';
          if (neg) { i++; }
          while (Character.isDigit(sp.charAt(i))) {
            jday=jday*10+Character.digit(sp.charAt(i++),10);
          }
          if (neg) { jday=-jday; neg=false; }

          while (i<sp.length() &&
                 !Character.isDigit(sp.charAt(i)) && sp.charAt(i)!='-') { i++; }
          neg=sp.charAt(i)=='-';
          if (neg) { i++; }
          while (i<sp.length() && Character.isDigit(sp.charAt(i))) {
            jmon=jmon*10+Character.digit(sp.charAt(i++),10);
          }
          if (neg) { jmon=-jmon; neg=false; }

          while (i<sp.length() &&
                 !Character.isDigit(sp.charAt(i)) && sp.charAt(i)!='-') { i++; }
          neg=sp.charAt(i)=='-';
          if (neg) { i++; }
          while (i<sp.length() && Character.isDigit(sp.charAt(i))) {
            jyear=jyear*10+Character.digit(sp.charAt(i++),10);
          }
          if (neg) { jyear=-jyear; }
        } catch (StringIndexOutOfBoundsException sob) {
          System.exit(1);
        }
        if (jyear * 10000 + jmon * 100 + jday < 15821015)
          gregflag = SweDate.SE_JUL_CAL;
        else
          gregflag = SweDate.SE_GREG_CAL;
        if (sp.indexOf("jul") >= 0)
          gregflag = SweDate.SE_JUL_CAL;
        else if (sp.indexOf("greg") >= 0)
          gregflag = SweDate.SE_GREG_CAL;
        jut = 0;
        sd.setDate(jyear,jmon,jday,jut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_DATE); // Keep Date!
        tjd = sd.getJulDay();
        tjd += thour / 24.0;
        sd.setJulDay(tjd);
      }
      if (special_event > 0) {
    	StringBuffer sbstar = new StringBuffer(star);
        do_special_event(tjd, ipl, sbstar, special_event, special_mode, geopos, datm, dobs, serr);
        star = sbstar.toString();
        sw.swe_close();
        return SweConst.OK;
      }
      line_count = 0;
      for (t = tjd, istep = 1; istep <= nstep; t += tstep, istep++) {
        if (t < 2299160.5)
          gregflag = SweDate.SE_JUL_CAL;
        else
          gregflag = SweDate.SE_GREG_CAL;
        if (sdate.indexOf("jul") >= 0)
          gregflag = SweDate.SE_JUL_CAL;
        else if (sdate.indexOf("greg") >= 0)
          gregflag = SweDate.SE_GREG_CAL;
        t2 = t;
//      swe_revjul(t2, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t2);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        if (with_header) {
          System.out.print("\ndate (dmy) "+jday+"."+jmon+"."+jyear);
          if (gregflag)
            System.out.print(" greg.");
          else
            System.out.print(" jul.");
          t2 = jut;
          System.out.print("  "+f.fmt("% 2d",(int) t2)+":");
          t2 = (t2 - (int) t2) * 60;
          System.out.print(f.fmt("%02d",(int)t2)+":");
          t2 = (t2 - (int) t2) * 60;
          System.out.print(f.fmt("%02d",(int) t2));
          if (universal_time)
            System.out.print(" UT");
          else
            System.out.print(" ET");
          System.out.print("\t\tversion " + sw.swe_version());
        }
        delt = SweDate.getDeltaT(t);
        if (universal_time) {
          if (with_header) {
            System.out.print("\nUT: "+f.fmt("%.11f",t));
          }
          if (with_header) {
            System.out.print("     delta t: "+f.fmt("%f",delt * 86400.0)+" sec");
          }
          te = t + delt;
          tut = t;
        } else {
          te = t;
          tut = t - delt;
        }
        iflgret = sw.swe_calc(te, SweConst.SE_ECL_NUT, iflag, xobl, serr);
        if (with_header) {
          System.out.print("\nET: "+f.fmt("%.11f",te));
          if ((iflag & SweConst.SEFLG_SIDEREAL)!=0) {
            daya = sw.swe_get_ayanamsa(te);
            System.out.print("   ayanamsa = "+dms(daya, round_flag));
          }
	  if (have_geopos) {
	    System.out.print("\ngeo. long "+f.fmt("%f",geopos[0])+
                             ", lat "+f.fmt("%f",geopos[1])+
                             ", alt "+f.fmt("%f",geopos[2]));
	  }
          if (iflag_f >=0) {
            iflag = iflag_f;
          }
          if (plsel.indexOf('o') < 0) {
            System.out.print("\n"+f.fmt("%-15s","Epsilon (true)")+" "+dms(xobl[0],round_flag));
          }
          if (plsel.indexOf('n') < 0) {
            System.out.print("\nNutation        ");
            System.out.print(dms(xobl[2], round_flag));
            System.out.print(gap);
            System.out.print(dms(xobl[3], round_flag));
          }
          System.out.print("\n");

          if (do_houses) {
	    String shsy = sw.swe_house_name((char)ihsy);
            if (!universal_time) {
              do_houses = false;
              System.out.print("option -house requires option -ut for "+
                               "Universal Time\n");
            } else {
              s1 = dms(top_long, round_flag);
              s2 = dms(top_lat, round_flag);
              System.out.println("Houses system "+(char)ihsy+" (" + shsy + ") for long=" + s1 + ", lat=" + s2);
            }     
          }         
        }
        if (with_header && !with_header_always)
          with_header = false;
        if (do_ayanamsa) {
          daya = sw.swe_get_ayanamsa(te);
          System.out.print("Ayanamsa");
          System.out.print(gap);
          System.out.print(dms(daya, round_flag));
          System.out.println();
	  /*printf("Ayanamsa%s%s\n", gap, dms(daya, round_flag));*/
          continue;
        }
        if (plsel.indexOf('e')>=0) {
          print_line(MODE_LABEL);
        }
        for (psp = plsel, p=0; p<plsel.length(); p++) {
          if (psp.charAt(p) == ' ' || // These deals with plsel == '' in C
            psp.charAt(p) == 'e') { continue; }
#ifndef ASTROLOGY
          ipl = letter_to_ipl((int) psp.charAt(p));
          if (psp.charAt(p) == 'f') {
            ipl = SweConst.SE_FIXSTAR;
          } else if (psp.charAt(p) == 's') {
            try {
// Well, no: sastno may be appended a letter like '10s'!!!
//              ipl = Integer.parseInt(sastno) + 10000;
              ipl = SwissLib.atoi(sastno) + 10000;
            } catch (NumberFormatException ne) {
              ipl = SweConst.ERR;
            }
          } else if (psp.charAt(p) == 'z') {
            try {
              ipl = Integer.parseInt(shyp) + SweConst.SE_FICT_OFFSET_1;
            } catch (NumberFormatException ne) {
              ipl = SweConst.ERR;
            }
          }
          if (ipl == -2) {
            System.out.print("illegal parameter -p"+plsel+"\n");
            System.exit(1);
          }
#endif /* ASTROLOGY */
          if ((iflag & SweConst.SEFLG_HELCTR)!=0) {
            if (ipl == SweConst.SE_SUN
                  || ipl == SweConst.SE_MEAN_NODE
                  || ipl == SweConst.SE_TRUE_NODE
                  || ipl == SweConst.SE_MEAN_APOG
                  || ipl == SweConst.SE_OSCU_APOG)
              continue;
          } else if ((iflag & SweConst.SEFLG_BARYCTR)!=0) {
            if (ipl == SweConst.SE_MEAN_NODE || ipl == SweConst.SE_TRUE_NODE
                  || ipl == SweConst.SE_MEAN_APOG
                  || ipl == SweConst.SE_OSCU_APOG)
              continue;
          } else          /* geocentric */
            if (ipl == SweConst.SE_EARTH)
              continue;
          /* ecliptic position */
          if (iflag_f >=0) {
            iflag = iflag_f;
          }
#ifndef ASTROLOGY
#ifndef JAVAME
          if (ipl == SweConst.SE_FIXSTAR) {
            StringBuffer sstar=new StringBuffer(star);
            iflgret = sw.swe_fixstar(sstar, te, iflag, x, serr);
            star=sstar.toString();
            /* magnitude, etc. */
            if (iflgret != SweConst.ERR && fmt.indexOf("=") >= 0) {
              double mag = 0;
              iflgret = SweConst.OK;
              try {
                mag = sw.getFixstarMagnitude(sstar);
              } catch (SwissephException se) {
                iflgret = se.getRC();
                serr = new StringBuffer(se.getMessage());
              }
              attr[4] = mag;
            }
            se_pname=star;
          } else {
#endif /* JAVAME */
#endif /* ASTROLOGY */
            iflgret = sw.swe_calc(te, ipl, iflag, x, serr);
#ifndef ASTROLOGY
            /* phase, magnitude, etc. */
            if (iflgret != SweConst.ERR && (fmt.indexOf("+")>=0 ||
                fmt.indexOf("-")>=0 || fmt.indexOf("*")>=0 ||
                fmt.indexOf("/")>=0 || fmt.indexOf("=")>=0)) {
              iflgret = sw.swe_pheno(te, ipl, iflag, attr, serr);
            }
#endif /* ASTROLOGY */
            se_pname=sw.swe_get_planet_name(ipl);
#ifndef ASTROLOGY
#ifndef JAVAME
          }
          if (psp.charAt(p) == 'q') {/* delta t */
            x[0] = SweDate.getDeltaT(te) * 86400;
            x[1] = x[2] = x[3] = 0;
            se_pname = "Delta T";
          }
#endif /* JAVAME */
#endif /* ASTROLOGY */
          if (psp.charAt(p) == 'o') {/* ecliptic is wanted, remove nutation */
            x[2] = x[3] = 0;
            se_pname="Ecl. Obl.";
          }
          if (psp.charAt(p) == 'n') {/* nutation is wanted, remove ecliptic */
            x[0] = x[2];
            x[1] = x[3];
            x[2] = x[3] = 0;
            se_pname="Nutation";
          }
	  if (psp.charAt(p) == 'y') {/* time equation */
            double[] dtmp = new double[] {x[0]};
	    iflgret = sw.swe_time_equ(tut, dtmp, serr);
            x[0] = dtmp[0];
	    x[0] *= 86400; /* in seconds */;
	    x[1] = x[2] = x[3] = 0;
	    se_pname = "Time Equ.";
	  }
          if (iflgret < 0) {
            if (!serr.toString().equals(serr_save.toString())
              && (ipl == SweConst.SE_SUN || ipl == SweConst.SE_MOON
                  || ipl == SweConst.SE_MEAN_NODE || ipl == SweConst.SE_TRUE_NODE
                  || ipl == SweConst.SE_CHIRON || ipl == SweConst.SE_PHOLUS || ipl == SweConst.SE_CUPIDO
                  || ipl >= SweConst.SE_AST_OFFSET || ipl == SweConst.SE_FIXSTAR)) {
              System.out.print("error: ");
              System.out.print(serr.toString());
              System.out.print("\n");
            }
            serr_save=new StringBuffer(serr.toString());
          } else if (serr.length()>0 && serr_warn.length()==0) {
            if (serr.toString().indexOf("'seorbel.txt' not found")<0) {
              serr_warn=new StringBuffer(serr.toString());
            }
          }
          if (diff_mode != 0) {
            iflgret = sw.swe_calc(te, ipldiff, iflag, x2, serr);
            if (iflgret < 0) {
              System.out.print("error: ");
              System.out.print(serr.toString());
              System.out.print("\n");
            }
            if (diff_mode == DIFF_DIFF) {
              for (i = 1; i < 6; i++)
                x[i] -= x2[i];
              if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                x[0] = sl.swe_difdeg2n(x[0], x2[0]);
              else
                x[0] = sl.swe_difrad2n(x[0], x2[0]);
            } else {      /* DIFF_MIDP */
              for (i = 1; i < 6; i++)
                x[i] = (x[i] + x2[i]) / 2;
#ifndef ASTROLOGY
              if ((iflag & SweConst.SEFLG_RADIANS) == 0)
#endif /* ASTROLOGY */
                x[0] = sl.swe_deg_midp(x[0], x2[0]);
#ifndef ASTROLOGY
              else
                x[0] = sl.swe_rad_midp(x[0], x2[0]);
#endif /* ASTROLOGY */
            }
          }
          /* equator position */
//        if (strpbrk(fmt, "aADdQ") != null) { ... }
          if (fmt.indexOf("a")>=0 || fmt.indexOf("A")>=0 ||
              fmt.indexOf("D")>=0 || fmt.indexOf("d")>=0 ||
              fmt.indexOf("Q")>=0) {
            iflag2 = iflag | SweConst.SEFLG_EQUATORIAL;
#ifndef ASTROLOGY
#ifndef JAVAME
            if (ipl == SweConst.SE_FIXSTAR) {
              StringBuffer sstar=new StringBuffer(star);
              iflgret = sw.swe_fixstar(sstar, te, iflag2, xequ, serr);
              star=sstar.toString();
            } else {
#endif /* JAVAME */
#endif /* ASTROLOGY */
              iflgret = sw.swe_calc(te, ipl, iflag2, xequ, serr);
#ifndef ASTROLOGY
#ifndef JAVAME
            }
#endif /* ASTROLOGY */
#endif /* JAVAME */
            if (diff_mode != 0) {
              iflgret = sw.swe_calc(te, ipldiff, iflag2, x2, serr);
              if (diff_mode == DIFF_DIFF) {
                for (i = 1; i < 6; i++)
                  xequ[i] -= x2[i];
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  xequ[0] = sl.swe_difdeg2n(xequ[0], x2[0]);
                else
                  xequ[0] = sl.swe_difrad2n(xequ[0], x2[0]);
              } else {    /* DIFF_MIDP */
                for (i = 1; i < 6; i++)
                  xequ[i] = (xequ[i] + x2[i]) / 2;
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  xequ[0] = sl.swe_deg_midp(xequ[0], x2[0]);
                else
                  xequ[0] = sl.swe_rad_midp(xequ[0], x2[0]);
              }
            }
          }
          /* azimuth and height */
//        if (strpbrk(fmt, "IiHhKk") != NULL) { ... }
          if (fmt.indexOf("I")>=0 || fmt.indexOf("i")>=0 ||
              fmt.indexOf("H")>=0 || fmt.indexOf("h")>=0 ||
              fmt.indexOf("K")>=0 || fmt.indexOf("k")>=0) {
            /* first, get topocentric equatorial positions */
            iflgt = whicheph | SweConst.SEFLG_EQUATORIAL | SweConst.SEFLG_TOPOCTR;
#ifndef ASTROLOGY
#ifndef JAVAME
            if (ipl == SweConst.SE_FIXSTAR) {
              StringBuffer sstar=new StringBuffer(star);
              iflgret = sw.swe_fixstar(sstar, te, iflgt, xt, serr);
              star=sstar.toString();
            } else {
#endif /* JAVAME */
#endif /* ASTROLOGY */
              iflgret = sw.swe_calc(te, ipl, iflgt, xt, serr);
#ifndef JAVAME
#ifndef ASTROLOGY
            }
#endif /* ASTROLOGY */
#endif /* JAVAME */
#ifndef NO_RISE_TRANS
            /* to azimuth/height */
            /* atmospheric pressure "0" has the effect that a value
             * of 1013.25 mbar is assumed at 0 m above sea level.
             * If the altitude of the observer is given (in geopos[2])
             * pressure is estimated according to that */
            sw.swe_azalt(tut, SweConst.SE_EQU2HOR, geopos, 0, 10, xt, xaz);
            if (diff_mode!=0) {
              iflgret = sw.swe_calc(te, ipldiff, iflgt, xt, serr);
              sw.swe_azalt(tut, SweConst.SE_EQU2HOR, geopos, 0, 10, xt, x2);
              if (diff_mode == DIFF_DIFF) {
                for (i = 1; i < 3; i++)
                  xaz[i] -= x2[i];
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  xaz[0] = sl.swe_difdeg2n(xaz[0], x2[0]);
                else
                  xaz[0] = sl.swe_difrad2n(xaz[0], x2[0]);
              } else {    /* DIFF_MIDP */
                for (i = 1; i < 3; i++)
                  xaz[i] = (xaz[i] + x2[i]) / 2;
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  xaz[0] = sl.swe_deg_midp(xaz[0], x2[0]);
                else
                  xaz[0] = sl.swe_rad_midp(xaz[0], x2[0]);
              }
            }
#endif /* NO_RISE_TRANS */
          }
          /* ecliptic cartesian position */
//        if (strpbrk(fmt, "XU") != null) { ... }
          if (fmt.indexOf("X")>=0 || fmt.indexOf("U")>=0) {
            iflag2 = iflag | SweConst.SEFLG_XYZ;
#ifndef ASTROLOGY
#ifndef JAVAME
            if (ipl == SweConst.SE_FIXSTAR) {
              StringBuffer sstar=new StringBuffer(star);
              iflgret = sw.swe_fixstar(sstar, te, iflag2, xcart, serr);
              star=sstar.toString();
            } else {
#endif /* JAVAME */
#endif /* ASTROLOGY */
              iflgret = sw.swe_calc(te, ipl, iflag2, xcart, serr);
#ifndef JAVAME
#ifndef ASTROLOGY
            }
#endif /* JAVAME */
#endif /* ASTROLOGY */
            if (diff_mode != 0) {
              iflgret = sw.swe_calc(te, ipldiff, iflag2, x2, serr);
              if (diff_mode == DIFF_DIFF) {
                for (i = 0; i < 6; i++)
                  xcart[i] -= x2[i];
              } else {
                  xcart[i] = (xcart[i] + x2[i]) / 2;
              }
            }
          }
          /* equator cartesian position */
//        if (strpbrk(fmt, "xu") != null) { ... }
          if (fmt.indexOf("x")>=0 || fmt.indexOf("u")>=0) {
            iflag2 = iflag | SweConst.SEFLG_XYZ | SweConst.SEFLG_EQUATORIAL;
#ifndef ASTROLOGY
#ifndef JAVAME
            if (ipl == SweConst.SE_FIXSTAR) {
              StringBuffer sstar=new StringBuffer(star);
              iflgret = sw.swe_fixstar(sstar, te, iflag2, xcartq, serr);
              star=sstar.toString();
            } else {
#endif /* JAVAME */
#endif /* ASTROLOGY */
              iflgret = sw.swe_calc(te, ipl, iflag2, xcartq, serr);
#ifndef JAVAME
#ifndef ASTROLOGY
            }
#endif /* ASTROLOGY */
#endif /* JAVAME */
            if (diff_mode != 0) {
              iflgret = sw.swe_calc(te, ipldiff, iflag2, x2, serr);
              if (diff_mode == DIFF_DIFF) {
                for (i = 0; i < 6; i++)
                  xcartq[i] -= x2[i];
              } else {
                  xcartq[i] = (xcart[i] + x2[i]) / 2;
              }
            }
          }
          /* house position */
//          if (strpbrk(fmt, "gGj") != NULL) { ... }
          if (fmt.indexOf("g")>=0 || fmt.indexOf("G")>=0 || fmt.indexOf("j")>=0) {
            armc = sl.swe_degnorm(sl.swe_sidtime(tut) * 15 + geopos[0]);
            for (i = 0; i < 6; i++)
              xsv[i] = x[i];
            if (hpos_meth == 1)
              xsv[1] = 0;
              if (ipl == SweConst.SE_FIXSTAR)
                star2=star;
              else
                star2 = "";
#ifndef NO_RISE_TRANS
            if (hpos_meth >= 2 && Character.toLowerCase((char)ihsy) == 'g') {
              StringBuffer sstar2=new StringBuffer(star2);
              sw.swe_gauquelin_sector(tut, ipl, sstar2, iflag, hpos_meth, geopos, 0, 0, hposj, serr);
              star2=sstar2.toString();
            } else {
#endif /* NO_RISE_TRANS */
              hposj.val = sw.swe_house_pos(armc, geopos[1], xobl[0], ihsy, xsv, serr);
#ifndef NO_RISE_TRANS
            }
#endif /* NO_RISE_TRANS */
            if (Character.toLowerCase((char)ihsy) == 'g')
              hpos = (hposj.val - 1) * 10;
            else
              hpos = (hposj.val - 1) * 30;
            if (diff_mode!=0) {
              for (i = 0; i < 6; i++)
                xsv[i] = x2[i];
              if (hpos_meth == 1)
                xsv[1] = 0;
              hpos2 = sw.swe_house_pos(armc, geopos[1], xobl[0], ihsy, xsv, serr);
              if (Character.toLowerCase((char)ihsy) == 'g')
                hpos2 = (hpos2 - 1) * 10;
              else
                hpos2 = (hpos2 - 1) * 30;
              if (diff_mode == DIFF_DIFF) {
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  hpos = sl.swe_difdeg2n(hpos, hpos2);
                else
                  hpos = sl.swe_difrad2n(hpos, hpos2);
              } else {    /* DIFF_MIDP */
                if ((iflag & SweConst.SEFLG_RADIANS) == 0)
                  hpos = sl.swe_deg_midp(hpos, hpos2);
                else
                  hpos = sl.swe_rad_midp(hpos, hpos2);
              }
            }
          }
          spnam=se_pname;
          print_line(0);
          line_count++;
          if (line_count >= line_limit) {
            System.out.print("****** line count "+line_limit+" was exceeded\n");
            break;
          }
        }         /* for psp */
        if (do_houses) {
          double cusp[]=new double[100];
          int iofs;
          if (Character.toLowerCase((char)ihsy) == 'g')
            nhouses = 36;
          iofs = nhouses + 1;
          iflgret = sw.swe_houses(t,iflag, top_lat, top_long, ihsy, cusp, cusp, iofs);
          if (iflgret < 0) {
            if (!serr.toString().equals(serr_save.toString())) {
              System.out.print("error: ");
              System.out.print(serr.toString());
              System.out.print("\n");
            }
            serr_save=new StringBuffer(serr.toString());
          } else {
            for (ipl = 1; ipl < iofs+4; ipl++) {
              x[0] = cusp[ipl];
              x[1] = 0;   /* latitude */
              x[2] = 1.0; /* pseudo radius vector */
//              if (strpbrk(fmt, "aADdQ") != NULL)
              if (fmt.indexOf('a')>=0 || fmt.indexOf('A')>=0 ||
                  fmt.indexOf('D')>=0 || fmt.indexOf('d')>=0 ||
                  fmt.indexOf('Q')>=0) {
                sl.swe_cotrans(x, 0, xequ, 0, -xobl[0]);
              }
              print_line(MODE_HOUSE);
              line_count++;
            }
          }
        }   
        if (line_count >= line_limit)
          break;
      }           /* for tjd */
      if (serr_warn.length() != 0) {
        System.out.print("\nwarning: ");
        System.out.print(serr_warn.toString());
        System.out.print("\n");
      }
    }             /* while 1 */
// end_main:
//    sw.swe_close();
//    return SweConst.OK;
  }

  /*
   * The string fmt contains a sequence of format specifiers;
   * each character in fmt creates a column, the columns are
   * sparated by the gap string.
   */
  int print_line(int mode) {
    String sp, sp2;
    double t2, ju2 = 0;
    double y_frac;
    double ar, sinp;
    double dret[] = new double[20];
    boolean is_house = ((mode & MODE_HOUSE) != 0);
    boolean is_label = ((mode & MODE_LABEL) != 0);
    int iflgret;
    int c=0;
    for (sp = fmt; c<fmt.length(); c++) {
      if (is_house && "bBsSrRxXuUQnNfF+-*/=".indexOf(sp.charAt(c))>=0) {
        continue;
      }
      if (c != 0)
        System.out.print(gap);
      switch((int)sp.charAt(c)) {
      case 'y':
          if (is_label) { System.out.println("year"); break; }
          System.out.print(jyear);
          break;
      case 'Y':
          if (is_label) { System.out.println("year"); break; }
          t2 = sd.getJulDay(jyear,1,1,ju2,gregflag);
          y_frac = (t - t2) / 365.0;
          System.out.print(f.fmt("%.2f",jyear + y_frac));
          break;
      case 'p':
          if (is_label) { System.out.println("obj.nr"); break; }
	  if (! is_house && diff_mode == DIFF_DIFF) {
	    System.out.print(ipl + "-" + ipldiff);
	  } else if (! is_house && diff_mode == DIFF_MIDP) {
	    System.out.print(ipl + "/" + ipldiff);
	  } else {
	    System.out.print(ipl);
	  }
          break;
      case 'P':
          if (is_label) { System.out.println("name           "); break; }
          if (is_house) {
	    if (ipl <= nhouses) {
	      System.out.print("house " + f.fmt("%2d", ipl) + "       ");
	    } else {
	      System.out.print(f.fmt("%-15s", hs_nam[ipl - nhouses]));
	    }
	  } else if (diff_mode == DIFF_DIFF) {
	    System.out.print(f.fmt("%.3s", spnam) + "-" + f.fmt("%.3s", spnam2));
	  } else if (diff_mode == DIFF_MIDP) {
	    System.out.print(f.fmt("%.3s", spnam) + "/" + f.fmt("%.3s", spnam2));
	  } else {
	    System.out.print(f.fmt("%-15s", spnam));
	  }
          break;
      case 'J':
          if (is_label) { System.out.println("julday"); break; }
          y_frac = (t - (int)(t)) * 100;
	  if (SMath.floor(y_frac) != y_frac) {
	    System.out.print(f.fmt("%.5f", t));
	  } else {
	    System.out.print(f.fmt("%.2f", t));
	  }
          break;
      case 'T':
          if (is_label) { System.out.println("date"); break; }
          System.out.print(f.fmt("%02d",jday)+"."+f.fmt("%02d",jmon)+"."+jyear);
          if (jut != 0) {
            int h, m, s;
            s = (int) (jut * 3600 + 0.5);
            h = (int) (s / 3600.0);
            m = (int) ((s % 3600) / 60.0);
            s %= 60;
	    System.out.print(" " + h + ":" + f.fmt("%02d", m) + ":" + f.fmt("%02d", s));
            if (universal_time)
              System.out.print(" UT");
            else
              System.out.print(" ET");
          }
          System.out.print(sout);
          break;
      case 't':
          if (is_label) { System.out.println("date"); break; }
          System.out.print(f.fmt("%02d",jyear % 100)+f.fmt("%02d",jmon)+f.fmt("%02d",jday));
          break;
      case 'L':
          if (is_label) { System.out.println("long."); break; }
          if (p >= psp.length() || (p < psp.length() && psp.charAt(p) != 'q' && psp.charAt(p) != 'y')) { /* != (delta t or time equation) */
            System.out.print(dms(x[0], round_flag));
            break;
          }
          // Fall through else...
      case 'l':
          if (is_label && sp.charAt(c) == 'l') { System.out.println("long"); break; }
          System.out.print(f.fmt("%# 11.7f", x[0]));
          break;
      case 'G':
          if (is_label) { System.out.println("housPos"); break; }
          System.out.print(dms(hpos, round_flag));
          break;
      case 'g':
          if (is_label) { System.out.println("housPos"); break; }
          System.out.print(f.fmt("%# 11.7f", hpos));
          break;
      case 'j':
          if (is_label) { System.out.println("houseNr"); break; }
          System.out.print(f.fmt("%# 11.7f", hposj.val));
          break;
      case 'Z':
          if (is_label) { System.out.println("long"); break; }
          System.out.print(dms(x[0], round_flag|BIT_ZODIAC));
          break;
      case 'S':
      case 's':
          if (fmt.indexOf("X")>=0 || fmt.indexOf("U")>=0 ||
              fmt.indexOf("x")>=0 || fmt.indexOf("u")>=0 ||
              (sp.length()>c+1 &&
               (sp.charAt(c+1) == 'S' || sp.charAt(c+1) == 's'))) {
            int c2=0;
            for (sp2 = fmt; c2<fmt.length(); c2++) {
              if (c2 != 0)
                System.out.print(gap);
              switch((int)sp2.charAt(c2)) {
                case 'L':   /* speed! */
                case 'Z':   /* speed! */
                  if (is_label) { System.out.println("lon/day"); break; }
                  System.out.print(dms(x[3], round_flag));
                  break;
                case 'l':   /* speed! */
                  if (is_label) { System.out.println("lon/day"); break; }
                  System.out.print(f.fmt("%11.7f", x[3]));
                  break;
                case 'B':   /* speed! */
                  if (is_label) { System.out.println("lat/day"); break; }
                  System.out.print(dms(x[4], round_flag));
                  break;
                case 'b':   /* speed! */
                  if (is_label) { System.out.println("lat/day"); break; }
                  System.out.print(f.fmt("%11.7f", x[4]));
                  break;
                case 'A':   /* speed! */
                  if (is_label) { System.out.println("RA/day"); break; }
                  System.out.print(dms(xequ[3]/15,
                                   round_flag|SweConst.SEFLG_EQUATORIAL));
                  break;
                case 'a':   /* speed! */
                  if (is_label) { System.out.println("RA/day"); break; }
                  System.out.print(f.fmt("%11.7f", xequ[3]));
                  break;
                case 'D':   /* speed! */
                  if (is_label) { System.out.println("dcl/day"); break; }
                  System.out.print(dms(xequ[4], round_flag));
                  break;
                case 'd':   /* speed! */
                  if (is_label) { System.out.println("dcl/day"); break; }
                  System.out.print(f.fmt("%11.7f", xequ[4]));
                  break;
                case 'R':   /* speed! */
                case 'r':   /* speed! */
                  if (is_label) { System.out.println("AU/day"); break; }
                  System.out.print(f.fmt("%# 14.9f", x[5]));
                  break;
                case 'U':   /* speed! */
                case 'X':   /* speed! */
		  if (is_label) { 
		    System.out.print("speed_0");
		    System.out.print(gap);
		    System.out.print("speed_1");
		    System.out.print(gap);
		    System.out.print("speed_2");
		    break; 
		  }
                  if (sp.charAt(c) =='U')
                    ar = SMath.sqrt(square_sum(xcart));
                  else
                    ar = 1;
                  System.out.print(f.fmt("%# 14.9f", xcart[3]/ar));
		  System.out.print(gap);
                  System.out.print(f.fmt("%# 14.9f", xcart[4]/ar));
		  System.out.print(gap);
                  System.out.print(f.fmt("%# 14.9f", xcart[5]/ar));
                  break;
                case 'u':   /* speed! */
                case 'x':   /* speed! */
		  if (is_label) { 
		    System.out.print("speed_0");
		    System.out.print(gap);
		    System.out.print("speed_1");
		    System.out.print(gap);
		    System.out.print("speed_2");
		    break; 
		  }
                  if (sp.charAt(c) =='u')
                    ar = SMath.sqrt(square_sum(xcartq));
                  else
                    ar = 1;
                  System.out.print(f.fmt("%# 14.9f", xcartq[3]/ar));
		  System.out.print(gap);
                  System.out.print(f.fmt("%# 14.9f", xcartq[4]/ar));
		  System.out.print(gap);
                  System.out.print(f.fmt("%# 14.9f", xcartq[5]/ar));
                  break;
                default:
                  break;
              }
            }
            if (sp.charAt(c+1) == 'S' || sp.charAt(c+1) == 's')
              c++;
          } else if (sp.charAt(c) == 'S') {
            if (is_label) { System.out.println("deg/day"); break; }
            System.out.print(dms(x[3], round_flag));
          } else {
            if (is_label) { System.out.println("deg/day"); break; }
            System.out.print(f.fmt("%# 11.7f", x[3]));
          }
          break;
      case 'B':
          if (is_label) { System.out.println("lat"); break; }
          System.out.print(dms(x[1], round_flag));
          break;
      case 'b':
          if (is_label) { System.out.println("lat"); break; }
          System.out.print(f.fmt("%# 11.7f", x[1]));
          break;
      case 'A':     /* right ascension */
          if (is_label) { System.out.println("RA"); break; }
          System.out.print(dms(xequ[0]/15,
                               round_flag|SweConst.SEFLG_EQUATORIAL));
          break;
      case 'a':     /* right ascension */
          if (is_label) { System.out.println("RA"); break; }
          System.out.print(f.fmt("%# 11.7f", xequ[0]));
          break;
      case 'D':     /* declination */
          if (is_label) { System.out.println("decl"); break; }
          System.out.print(dms(xequ[1], round_flag));
          break;
      case 'd':     /* declination */
          if (is_label) { System.out.println("decl"); break; }
          System.out.print(f.fmt("%# 11.7f", xequ[1]));
          break;
      case 'I':     /* azimuth */
          if (is_label) { System.out.println("azimuth"); break; }
          System.out.print(dms(xaz[0], round_flag));
          break;
      case 'i':     /* azimuth */
          if (is_label) { System.out.println("azimuth"); break; }
          System.out.print(f.fmt("%# 11.7f", xaz[0]));
          break;
      case 'H':     /* height */
          if (is_label) { System.out.println("height"); break; }
          System.out.print(dms(xaz[1], round_flag));
          break;
      case 'h':     /* height */
          if (is_label) { System.out.println("height"); break; }
          System.out.print(f.fmt("%# 11.7f", xaz[1]));
          break;
      case 'K':     /* height (apparent) */
          if (is_label) { System.out.println("hgtApp"); break; }
          System.out.print(dms(xaz[2], round_flag));
          break;
      case 'k':     /* height (apparent) */
          if (is_label) { System.out.println("hgtApp"); break; }
          System.out.print(f.fmt("%# 11.7f", xaz[2]));
          break;
      case 'R':
          if (is_label) { System.out.println("distAU"); break; }
          System.out.print(f.fmt("%# 14.9f", x[2]));
          break;
#ifndef ASTROLOGY
      case 'r':
          if (is_label) { System.out.println("dist"); break; }
          if ( ipl == SweConst.SE_MOON ) { /* for moon print parallax */
//            /* geocentric horizontal parallax: */
//            if (0) {
//              sinp = 8.794 / x[2];    /* in seconds of arc */
//              ar = sinp * (1 + sinp * sinp * 3.917402e-12);
//              /* the factor is 1 / (3600^2 * (180/pi)^2 * 6) */
//	        printf("%# 13.5f\" %# 13.5f'", ar, ar/60.0);
//            }
            sw.swe_pheno(te, ipl, iflag, dret, serr);
            System.out.print(f.fmt("%# 13.5f", dret[5] * 3600) + "\"");
          } else {
            System.out.print(f.fmt("%# 14.9f", x[2]));
          }
          break;
#endif /* ASTROLOGY */
      case 'U':
      case 'X':
          if (sp.charAt(c) =='U')
            ar = SMath.sqrt(square_sum(xcart));
          else
            ar = 1;
	  System.out.print(f.fmt("%# 14.9f", xcart[0]/ar));
	  System.out.print(gap);
	  System.out.print(f.fmt("%# 14.9f", xcart[1]/ar));
	  System.out.print(gap);
	  System.out.print(f.fmt("%# 14.9f", xcart[2]/ar));
          break;
      case 'u':
      case 'x':
	  if (is_label) { 
	    System.out.print("x0");
	    System.out.print(gap);
	    System.out.print("x1");
	    System.out.print(gap);
	    System.out.print("x2");
	    break; 
	  }
          if (sp.charAt(c) =='u')
            ar = SMath.sqrt(square_sum(xcartq));
          else
            ar = 1;
	  System.out.print(f.fmt("%# 14.9f", xcartq[0]/ar));
	  System.out.print(gap);
	  System.out.print(f.fmt("%# 14.9f", xcartq[1]/ar));
	  System.out.print(gap);
	  System.out.print(f.fmt("%# 14.9f", xcartq[2]/ar));
          break;
      case 'Q':
	  if (is_label) { System.out.println("Q"); break; }
	  System.out.print(f.fmt("%-15s", spnam));
	  System.out.print(dms(x[0], round_flag));
	  System.out.print(dms(x[1], round_flag));
	  System.out.print("  " + f.fmt("%# 14.9f", x[2]));
	  System.out.print(dms(x[3], round_flag));
	  System.out.print(dms(x[4], round_flag));
	  System.out.print("  " + f.fmt("%# 14.9f\n", x[5]));
	  System.out.print("               " + dms(xequ[0], round_flag));
	  System.out.print(dms(xequ[1], round_flag));
	  System.out.print("                " + dms(xequ[3], round_flag));
	  System.out.print(dms(xequ[4], round_flag));
          break;
#ifndef ASTROLOGY
      case 'N':
      case 'n': {
          double xasc[]=new double[6], xdsc[]=new double[6];
          int imeth = (Character.isLowerCase(sp.charAt(c)))?
                         SweConst.SE_NODBIT_MEAN:SweConst.SE_NODBIT_OSCU;
          iflgret = sw.swe_nod_aps(te, ipl, iflag, imeth, xasc,
                                   xdsc, null, null, serr);
          if (iflgret >= 0 &&
              (ipl <= SweConst.SE_NEPTUNE || sp.charAt(c) == 'N') ) {
	    if (is_label) { 
	      System.out.print("nodAsc");
	      System.out.print(gap);
	      System.out.print("nodDesc");
	      break; 
	    }
            System.out.print(f.fmt("%# 11.7f",xasc[0]));
            System.out.print(gap);
            System.out.print(f.fmt("%# 11.7f",xdsc[0]));
          }
        };
        break;
      case 'F':
      case 'f':
        if (!is_house) {
          double xfoc[]=new double[6], xaph[]=new double[6],
                 xper[]=new double[6];
          int imeth = (Character.isLowerCase(sp.charAt(c)))?
                             SweConst.SE_NODBIT_MEAN:SweConst.SE_NODBIT_OSCU;
          iflgret = sw.swe_nod_aps(te, ipl, iflag, imeth, null, null,
                                   xper, xaph, serr);
          if (iflgret >= 0 && (ipl <= SweConst.SE_NEPTUNE ||
              sp.charAt(c) == 'F') ) {
	    if (is_label) { 
	      System.out.print("peri");
	      System.out.print(gap);
	      System.out.print("apo");
	      break; 
	    }
            System.out.print(f.fmt("%# 11.7f", xper[0]));
            System.out.print(gap);
            System.out.print(f.fmt("%# 11.7f", xaph[0]));
          }
          imeth |= SweConst.SE_NODBIT_FOPOINT;
          iflgret = sw.swe_nod_aps(te, ipl, iflag, imeth, null, null,
                                   xper, xfoc, serr);
          if (iflgret >= 0 && (ipl <= SweConst.SE_NEPTUNE ||
              sp.charAt(c) == 'F') ) {
	    if (is_label) { 
	      System.out.print(gap);
	      System.out.print("focus");
	      break; 
	    }
	    System.out.print(gap);
            System.out.print(f.fmt("%# 11.7f", xfoc[0]));
          }
        };
        break;
#endif /* ASTROLOGY */
      case '+':
          if (is_house) break;
          if (is_label) { System.out.println("phase"); break; }
          System.out.print(dms(attr[0], round_flag));
          break;
      case '-':
          if (is_label) { System.out.println("phase"); break; }
          if (is_house) break;
          System.out.print("  "+f.fmt("%# 14.9f", attr[1]));
          break;
      case '*':
          if (is_label) { System.out.println("elong"); break; }
          if (is_house) break;
          System.out.print(dms(attr[2], round_flag));
          break;
      case '/':
          if (is_label) { System.out.println("diamet"); break; }
          if (is_house) break;
          System.out.print(dms(attr[3], round_flag));
          break;
      case '=':
          if (is_label) { System.out.println("magn"); break; }
          if (is_house) break;
          System.out.print("  "+f.fmt("%# 6.1f", attr[4])+"m");
          break;
      case 'V': /* human design gates */
      case 'v': {
          double xhds;
          int igate, iline, ihex;
          final int hexa[] = new int[]{1, 43, 14, 34, 9, 5, 26, 11, 10, 58, 38, 54, 61, 60, 41, 19, 13, 49, 30, 55, 37, 63, 22, 36, 25, 17, 21, 51, 42, 3, 27, 24, 2, 23, 8, 20, 16, 35, 45, 12, 15, 52, 39, 53, 62, 56, 31, 33, 7, 4, 29, 59, 40, 64, 47, 6, 46, 18, 48, 57, 32, 50, 28, 44};
          if (is_label) { System.out.print("hds"); break; }
          if (is_house) break;
          xhds = sl.swe_degnorm(x[0] - 223.25);
          ihex = (int) SMath.floor(xhds / 5.625);
          iline = ((int) (SMath.floor(xhds / 0.9375))) % 6 + 1 ;
          igate = hexa[ihex];
          System.out.print(f.fmt("%2d", igate) + "." + f.fmt("%d", iline));
	  if (sp.charAt(c) == 'V')
	    System.out.print(" " + f.fmt("%2d", sl.swe_d2l(100 * ((xhds / 0.9375) % 1))) + "%");
          break;
        }
      }     /* switch */
    }       /* for sp */
    System.out.print("\n");
    return SweConst.OK;
  }

#undef OUTPUT_EXTRA_PRECISION
  private String dms(double xv, int iflg) {
    int izod;
    int k, kdeg, kmin, ksec;
    String c = swed.ODEGREE_STRING;
    String s1;
    String s;
    int sgn;
    if (Double.isNaN(xv))
      return "nan";
    s = "";
    if ((iflg & SweConst.SEFLG_EQUATORIAL)!=0)
      c = "h";
    if (xv < 0) {
      xv = -xv;
      sgn = -1;
    } else
      sgn = 1;
    if ((iflg & BIT_ROUND_MIN)!=0)
      xv = sl.swe_degnorm(xv + 0.5/60);
    if ((iflg & BIT_ROUND_SEC)!=0)
      xv = sl.swe_degnorm(xv + 0.5/3600);
    if ((iflg & BIT_ZODIAC)!=0) {
      izod = (int) (xv / 30)%12;
      xv%=30.;
      kdeg = (int) xv;
      s=f.fmt("%2ld",kdeg)+" "+zod_nam[izod]+" ";
    } else {
      kdeg = (int) xv;
      s=" "+f.fmt("%3ld", kdeg)+c;
    }
    xv -= kdeg;
    xv *= 60;
    kmin = (int) xv;
    if ((iflg & BIT_ZODIAC)!=0 && (iflg & BIT_ROUND_MIN)!=0) {
      s1=f.fmt("%2ld", kmin);
    } else {
      s1=f.fmt("%2ld", kmin)+"'";
    }
    s+=s1;
    if ((iflg & BIT_ROUND_MIN)!=0)
      return dms_label_return_dms(sgn,s,iflg);
    xv -= kmin;
    xv *= 60;
    ksec = (int) xv;
    if ((iflg & BIT_ROUND_SEC)!=0) {
      s1=f.fmt("%2ld", ksec)+"\"";
    } else {
      s1=f.fmt("%2ld", ksec);
    }
    s+=s1;
    if ((iflg & BIT_ROUND_SEC)!=0)
      return dms_label_return_dms(sgn,s,iflg);
    xv -= ksec;
#if OUTPUT_EXTRA_PRECISION
    k = (int) (xv * 100000 + 0.5);
    s1="."+f.fmt("%05ld", k);
#else
    k = (int) (xv * 10000 + 0.5);
    s1="."+f.fmt("%04ld", k);
#endif /* OUTPUT_EXTRA_PRECISION */
    s+=s1;
    return dms_label_return_dms(sgn,s,iflg);
  }

  private String dms_label_return_dms(int sgn, String s, int iflg) {
    if (sgn < 0) {
      for (int i=0; i<s.length();i++) {
        if (Character.isDigit(s.charAt(i))) {
          s=s.substring(0,i-1)+"-"+s.substring(i);
          break;
        }
      }
    }
    if ((iflg & BIT_LZEROES) != 0) {
      // while ((sp = strchr(s+2, ' ')) != NULL) *sp = '0';	// Replaces all spaces by '0'es
      s = s.substring(0,2) + s.substring(2).replace(' ', '0');
    }
    return(s);
  }

  private int letter_to_ipl(int letter) {
    if (letter >= (int)'0' && letter <= (int)'9')
      return letter - (int)'0' + SweConst.SE_SUN;
#ifdef ASTROLOGY
    if (letter == (int)'A' || letter == (int)'D')
      return letter - (int)'A' + SweConst.SE_MEAN_APOG;
#else
    if (letter >= (int)'A' && letter <= (int)'I')
      return letter - (int)'A' + SweConst.SE_MEAN_APOG;
    if (letter >= (int)'J' && letter <= (int)'Z')
      return letter - (int)'J' + SweConst.SE_CUPIDO;
#endif /* ASTROLOGY */
    switch (letter) {
      case 'm': return SweConst.SE_MEAN_NODE;
      case 'c': return SweConst.SE_INTP_APOG;
      case 'g': return SweConst.SE_INTP_PERG;
      case 'n':
      case 'o': return SweConst.SE_ECL_NUT;
      case 't': return SweConst.SE_TRUE_NODE;
#ifndef ASTROLOGY
      case 'f': return SweConst.SE_FIXSTAR;
#endif /* ASTROLOGY */
      case 'w': return SweConst.SE_WALDEMATH;
      case 'e': /* swetest: a line of labels */
      case 'q': /* swetest: delta t */
      case 'y': /* swetest: time equation */
      case 's': /* swetest: an asteroid, with number given in -xs[number] */
      case 'z': /* swetest: a fictitious body, number given in -xz[number] */
      case 'd': /* swetest: default (main) factors 0123456789mtABC */
      case 'p': /* swetest: main factors ('d') plus main asteroids DEFGHI */
      case 'h': /* swetest: fictitious factors JKLMNOPQRSTUVWXYZw */
      case 'a': /* swetest: all factors, like 'p'+'h' */
        return -1;
    }
    return -2;
  }


  private int ut_to_lmt_lat(double t_ut, double[] geopos, double[] t_ret, StringBuffer serr) {
    int iflgret = SweConst.OK;
    if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
      t_ut += geopos[0] / 360.0;
      if ((time_flag & BIT_TIME_LAT) != 0) {
        double[] ar_t_ut = new double[] { t_ut };
        iflgret = sw.swe_lmt_to_lat(t_ut, geopos[0], ar_t_ut, null);
	t_ut = ar_t_ut[0];
      }
    }
    t_ret[0] = t_ut;
    return iflgret;
  }


  private int call_rise_set(double t_ut, int ipl, StringBuffer star, int whicheph, int special_mode, double[] geopos, StringBuffer serr) {
    int ii;
    int rsmi = 0;
    double tret[] = new double[10];
    double t0, t1;
    int retc = SweConst.OK;
    DblObj tret_out = new DblObj(); // used as a temporary output double value
    sw.swe_set_topo(geopos[0], geopos[1], geopos[2]); 
    do_printf("\n");
    /* loop over days */
    for (ii = 0; ii < nstep; ii++, t_ut = tret[1] + 0.1) {
      sout = "";
      /* swetest -rise
       * calculate and print rising and setting */
      if (special_event == SP_RISE_SET) {
        /* rising */
        rsmi = SweConst.SE_CALC_RISE;
        if (norefrac) rsmi |= SweConst.SE_BIT_NO_REFRACTION;
        if (disccenter) rsmi |= SweConst.SE_BIT_DISC_CENTER;
	tret_out.val = tret[0];
        if (sw.swe_rise_trans(t_ut, ipl, star, whicheph, rsmi, geopos, 1013.25, 10, tret_out, serr) != SweConst.OK) {
          do_printf(serr);
          System.exit(0);
        } 
	tret[0] = tret_out.val;
        /* setting */
        rsmi = SweConst.SE_CALC_SET;
        if (norefrac) rsmi |= SweConst.SE_BIT_NO_REFRACTION;
        if (disccenter) rsmi |= SweConst.SE_BIT_DISC_CENTER;
        tret_out.val = tret[1]; // temporary assignment to have an output variable
        if (sw.swe_rise_trans(t_ut, ipl, star, whicheph, rsmi, geopos, 1013.25, 10, tret_out, serr) != SweConst.OK) {
          do_printf(serr);
          System.exit(0);
        }
        tret[1] = tret_out.val;
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
          double[] dtmp = new double[] { tret[0] }; // temporary assignment to have an output variable
	  retc = ut_to_lmt_lat(tret[0], geopos, dtmp, serr);
          tret[0] = dtmp[0];
          dtmp[0] = tret[1]; // temporary assignment to have an output variable
	  retc = ut_to_lmt_lat(tret[1], geopos, dtmp, serr);
          tret[1] = dtmp[0];
        }
        t0 = 0; t1 = 0;
        sout = "rise     ";
        if (tret[0] == 0 || tret[0] > tret[1]) {
          sout += "         -                     ";
        } else {
	  t0 = tret[0];
//          swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[0]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
#ifdef JAVAME
          sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + f.fmt("%s", hms(jut,BIT_LZEROES)) + "    ";
#else
          sout += String.format(Locale.US, "%2d.%02d.%04d\t%s    ", jday, jmon, jyear, hms(jut,BIT_LZEROES));
#endif /* JAVAME */
        }
        sout += "set      ";
        if (tret[1] == 0) {
          sout += "         -                     \n";
        } else {
	  t1 = tret[1];
//          swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[1]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
#ifdef JAVAME
          sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + f.fmt("%s", hms(jut,BIT_LZEROES)) + "    ";
#else
          sout += String.format(Locale.US, "%2d.%02d.%04d\t%s    ", jday, jmon, jyear, hms(jut,BIT_LZEROES));
#endif /* JAVAME */
        }
        if (t0 != 0 && t1 != 0) {
          t0 = (t1 - t0) * 24;
	    sout += "dt = " + hms(t0, BIT_LZEROES);
        }
        sout += "\n";
        do_printf(sout);
      }
#ifndef ASTROLOGY
      /* swetest -metr
       * calculate and print transits over meridian (midheaven and lower
       * midheaven */
      if (special_event == SP_MERIDIAN_TRANSIT) {
        /* transit over midheaven */
	tret_out.val = tret[0];
        if (sw.swe_rise_trans(t_ut, ipl, star, whicheph, SweConst.SE_CALC_MTRANSIT, geopos, 1013.25, 10, tret_out, serr) != SweConst.OK) {
          do_printf(serr);
          return SweConst.ERR;
        }
        tret[0] = tret_out.val;
        /* transit over lower midheaven */
        tret_out.val = tret[1];
        if (sw.swe_rise_trans(t_ut, ipl, star, whicheph, SweConst.SE_CALC_ITRANSIT, geopos, 1013.25, 10, tret_out, serr) != SweConst.OK) {
          do_printf(serr);
          return SweConst.ERR;
        }
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
          double[] dtmp = new double[] { tret[0] }; // temporary assignment to have an output variable
	  retc = ut_to_lmt_lat(tret[0], geopos, dtmp, serr);
          tret[0] = dtmp[0];
          dtmp[0] = tret[1]; // temporary assignment to have an output variable
	  retc = ut_to_lmt_lat(tret[1], geopos, dtmp, serr);
          tret[1] = dtmp[0];
        }
        tret[1] = tret_out.val;
        sout = "mtransit ";
        if (tret[0] == 0) sout += "         -                     ";
        else {
//          swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[0]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
#ifdef JAVAME
          sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + f.fmt("%s", hms(jut,BIT_LZEROES)) + "    ";
#else
          sout += String.format(Locale.US, "%2d.%02d.%04d\t%s    ", jday, jmon, jyear, hms(jut,BIT_LZEROES));
#endif /* JAVAME */
        }
        sout += "itransit ";
        if (tret[1] == 0) sout += "         -                     \n";
        else {
//          swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[1]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
#ifdef JAVAME
          sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + f.fmt("%s", hms(jut,BIT_LZEROES)) + "\n";
#else
          sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\n", jday, jmon, jyear, hms(jut,BIT_LZEROES));
#endif /* JAVAME */
        }
        do_printf(sout);
      }
#endif /* ASTROLOGY */
    }
    return retc;
  }

  private int call_lunar_eclipse(double t_ut, int whicheph, int special_mode, double[] geopos, StringBuffer serr) {
    int i, ii, retc, eclflag, ecl_type = 0;
//    int ihou[] = new int[1], imin[] = new int[1], isec[] = new int[1], isgn[] = new int[1]; // int used as output parameters
//    double dfrc[] = new double[1] /* output par. */;
    double attr[] = new double[30];
    double dt;
    String s1, sout_short, sfmt;
    /* no selective eclipse type set, set all */
    if ((search_flag & SweConst.SE_ECL_ALLTYPES_LUNAR) == 0)
      search_flag |= SweConst.SE_ECL_ALLTYPES_LUNAR;
    do_printf("\n");
    for (ii = 0; ii < nstep; ii++, t_ut += direction) {
      sout = "";
#ifndef ASTROLOGY
      /* swetest -lunecl -how 
       * type of lunar eclipse and percentage for a given time: */
      if ((special_mode & SP_MODE_HOW) != 0) {
        if ((eclflag = sw.swe_lun_eclipse_how(t_ut, whicheph, geopos, attr, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } else {
          if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
            ecl_type = ECL_LUN_TOTAL;
	    sfmt = "total lunar eclipse: %f o/o \n";
          } else if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0)  {
            ecl_type = ECL_LUN_PARTIAL;
	    sfmt = "partial lunar eclipse: %f o/o \n";
          } else if ((eclflag & SweConst.SE_ECL_PENUMBRAL)!=0)  {
            ecl_type = ECL_LUN_PENUMBRAL;
	    sfmt = "penumbral lunar eclipse: %f o/o \n";
          } else {
	    sfmt = "no lunar eclipse \n";
          }
	  sout = sfmt;
	  if (sfmt.indexOf('%') >= 0) {
#ifdef JAVAME
            sout = sfmt.replaceFirst("%[^f]*f", ""+attr[0]);
#else
            sout = String.format(Locale.US, sfmt, attr[0]);
#endif /* JAVAME */
	  }
          do_printf(sout);
        }
        continue;
      }
#endif /* ASTROLOGY */
#ifndef ASTROLOGY
      /* swetest -lunecl 
       * find next lunar eclipse: */
      /* locally visible lunar eclipse */
      if ((special_mode & SP_MODE_LOCAL) != 0) {
        if ((eclflag = sw.swe_lun_eclipse_when_loc(t_ut, whicheph, geopos, 
                  tret, attr, direction_flag ? 1 : 0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } 
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
	  for (i = 0; i < 10; i++) {
            double[] dtmp = new double[1];
	    if (tret[i] != 0) {
              dtmp[0] = tret[i];
	      retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
              tret[i] = dtmp[0];
	    }
	  }
        }
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL) != 0) {
          sout = "total   ";
	  ecl_type = ECL_LUN_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_PENUMBRAL) != 0) {
          sout = "penumb. ";
	  ecl_type = ECL_LUN_PENUMBRAL;
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL) != 0) {
          sout = "partial ";
	  ecl_type = ECL_LUN_PARTIAL;
        }
        sout += "lunar eclipse\t";
//        swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t_ut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        /*if ((eclflag = swe_lun_eclipse_how(t_ut, whicheph, geopos, attr, serr)) == ERR) {
          do_printf(serr);
          return ERR;
        }*/
        dt = (tret[3] - tret[2]) * 24 * 60;
//        sprintf(s1, "%d min %4.2f sec", (int) dt, fmod(dt, 1) * 60);
#ifdef JAVAME
        s1 = f.fmt("%d", (int) dt) + " min " + f.fmt("%4.2f", (dt % 1) * 60) + " sec";
#else
        s1 = String.format(Locale.US, "%d min %4.2f sec", (int) dt, (dt % 1) * 60);
#endif /* JAVAME */
        /* short output: 
         * date, time of day, umbral magnitude, umbral duration, saros series, member number */
//      sprintf(sout_short, "%s\t%2d.%2d.%4d\t%s\t%.3f\t%s\t%d\t%d\n", sout, jday, jmon, jyear, hms(jut,0), attr[8],s1, (int) attr[9], (int) attr[10]);
//      sprintf(sout + strlen(sout), "%2d.%02d.%04d\t%s\t%.4f/%.4f\tsaros %d/%d\t%.6f\n", jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[0],attr[1], (int) attr[9], (int) attr[10], t_ut);
#ifdef JAVAME
        sout_short = sout + "\t" + f.fmt("%2d", jday) + "." + f.fmt("%2d", jmon) + "." + f.fmt("%4d", jyear) + "\t" + hms(jut,0) + "\t" + f.fmt("%.3f", attr[8]) + "\t" + s1 + "\t" + (int) attr[9] + "\t" + (int) attr[10] + "\n";
        sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + hms(jut,BIT_LZEROES) + "\t" + f.fmt("%.4f", attr[0]) + "/" + f.fmt("%.4f", attr[1]) + "\tsaros " + (int) attr[9] + "/" + (int) attr[10] + "\t" + f.fmt("%.6f", t_ut) + "\n";
#else
        sout_short = String.format(Locale.US, "%s\t%2d.%2d.%4d\t%s\t%.3f\t%s\t%d\t%d\n", sout, jday, jmon, jyear, hms(jut,0), attr[8],s1, (int) attr[9], (int) attr[10]);
        sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\t%.4f/%.4f\tsaros %d/%d\t%.6f\n", jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[0],attr[1], (int) attr[9], (int) attr[10], t_ut);
#endif /* JAVAME */
        /* second line:
         * eclipse times, penumbral, partial, total begin and end */
        if ((eclflag & SweConst.SE_ECL_PENUMBBEG_VISIBLE) != 0)
	  sout += "  " + hms_from_tjd(tret[6]) + " "; 
        else
	  sout += "      -         ";
        if ((eclflag & SweConst.SE_ECL_PARTBEG_VISIBLE) != 0)
	  sout += hms_from_tjd(tret[2]) + " "; 
        else
	  sout += "    -         ";
        if ((eclflag & SweConst.SE_ECL_TOTBEG_VISIBLE) != 0)
	  sout += hms_from_tjd(tret[4]) + " "; 
        else
	  sout += "    -         ";
        if ((eclflag & SweConst.SE_ECL_TOTEND_VISIBLE) != 0)
	  sout += hms_from_tjd(tret[5]) + " "; 
        else
	  sout += "    -         ";
        if ((eclflag & SweConst.SE_ECL_PARTEND_VISIBLE) != 0)
	  sout += hms_from_tjd(tret[3]) + " "; 
        else
	  sout += "    -         ";
        if ((eclflag & SweConst.SE_ECL_PENUMBEND_VISIBLE) != 0)
	  sout += hms_from_tjd(tret[7]) + " "; 
        else
	  sout += "    -         ";
        sout += "\n";
      /* global lunar eclipse */
      } else {
        if ((eclflag = sw.swe_lun_eclipse_when(t_ut, whicheph, search_flag, 
                  tret, direction_flag?1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } 
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_LUN_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_PENUMBRAL)!=0) {
          sout="penumb. ";
          ecl_type = ECL_LUN_PENUMBRAL;
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_LUN_PARTIAL;
        }
        sout+="lunar eclipse\t";
        if ((eclflag = sw.swe_lun_eclipse_how(t_ut, whicheph, geopos, attr, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        }
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
          double[] dtmp = new double[1];
	  for (i = 0; i < 10; i++) {
	    if (tret[i] != 0) {
              dtmp[0] = tret[i];
	      retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
              tret[i] = dtmp[0];
            }
	  }
        }
        t_ut = tret[0];
//      swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t_ut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        dt = (tret[3] - tret[2]) * 24 * 60;
#ifdef JAVAME
          s1 = f.fmt("%d", (int)dt) + " min " + f.fmt("%4.2f", (dt %1) * 60) + " sec";
#else
          s1 = String.format(Locale.US, "%d min %4.2f sec", (int) dt, (dt % 1) * 60);
#endif /* JAVAME */
        /* short output: 
         * date, time of day, umbral magnitude, umbral duration, saros series, member number */
//        sprintf(sout_short, "%s\t%2d.%2d.%4d\t%s\t%.3f\t%s\t%d\t%d\n", sout, jday, jmon, jyear, hms(jut,0), attr[8],s1, (int) attr[9], (int) attr[10]);
//        sprintf(sout + strlen(sout), "%2d.%02d.%04d\t%s\t%.4f/%.4f\tsaros %d/%d\t%.6f\n", jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[0],attr[1], (int) attr[9], (int) attr[10], t_ut);
#ifdef JAVAME
          sout_short = sout + "\t" + f.fmt("%2d", jday) + "." + f.fmt("%2d", jmon) + "." + f.fmt("%4d", jyear) + "\t" +
              hms(jut,0) + "\t" + f.fmt("%.3f", attr[8]) + "\t" + s1 + "\t" +
              f.fmt("%d", (int)attr[9]) + "\t" + f.fmt("%d", (int)attr[10]) + "\n";
          sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" +
              hms(jut,BIT_LZEROES) + "\t" + f.fmt("%.4f", attr[0]) + "/" + f.fmt("%.4f", attr[1]) +
              "\tsaros " + f.fmt("%d", (int) attr[9]) + "/" + f.fmt("%d", (int) attr[10]) + "\t" + f.fmt("%.6f", t_ut) + "\n";
#else
          sout_short = String.format(Locale.US, "%s\t%2d.%2d.%4d\t%s\t%.3f\t%s\t%d\t%d\n",
              sout, jday, jmon, jyear, hms(jut,0), attr[8],s1, (int) attr[9], (int) attr[10]);
          sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\t%.4f/%.4f\tsaros %d/%d\t%.6f\n",
              jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[0],attr[1], (int) attr[9], (int) attr[10], t_ut);
#endif /* JAVAME */
        /* second line:
         * eclipse times, penumbral, partial, total begin and end */
        sout += "  "+hms_from_tjd(tret[6])+" "; 
        if (tret[2] != 0)
          sout += hms_from_tjd(tret[2])+" "; 
        else
          sout+="   -         ";
        if (tret[4] != 0)
          sout += hms_from_tjd(tret[4])+" "; 
        else
          sout+="   -         ";
        if (tret[5] != 0)
          sout += hms_from_tjd(tret[5]) + " "; 
        else
          sout+="   -         ";
        if (tret[3] != 0)
          sout += hms_from_tjd(tret[3]) + " "; 
        else
          sout+="   -         ";
        sout += hms_from_tjd(tret[7]) + "\n";
        if ((special_mode & SP_MODE_HOCAL) != 0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(), isgn=new IntObj();
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN, ihou, imin, isec, dfrc, isgn);
          sout="\""+f.fmt("%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        } 
      }
#endif /* ASTROLOGY */
      if (short_output)
        do_printf(sout_short);
      else
        do_printf(sout);
    }     
    return SweConst.OK;
  }

  private int call_solar_eclipse(double t_ut, int whicheph, int special_mode, double[] geopos, StringBuffer serr) {
    int i, ii, retc, eclflag, ecl_type = 0;
    double dt, tret[] = new double[30], attr[] = new double[30], geopos_max[] = new double[3];
    String s1, s2, sout_short;
    boolean has_found = false;
    /* no selective eclipse type set, set all */
    if ((search_flag & SweConst.SE_ECL_ALLTYPES_SOLAR) == 0)
      search_flag |= SweConst.SE_ECL_ALLTYPES_SOLAR;
    /* for local eclipses: set geographic position of observer */
    if ((special_mode & SP_MODE_LOCAL) != 0)
      sw.swe_set_topo(geopos[0], geopos[1], geopos[2]);
    do_printf("\n");
    for (ii = 0; ii < nstep; ii++, t_ut += direction) {
      sout = "";
#ifndef ASTROLOGY
      /* swetest -solecl -local -geopos...
       * find next solar eclipse observable from a given geographic position */
      if ((special_mode & SP_MODE_LOCAL) != 0) {
        if ((eclflag = sw.swe_sol_eclipse_when_loc(t_ut, whicheph, geopos, tret, attr, direction_flag?1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } else { 
          has_found = false;
          t_ut = tret[0];
          if ((search_flag & SweConst.SE_ECL_TOTAL) != 0 && (eclflag & SweConst.SE_ECL_TOTAL) != 0) {
            sout="total   ";
            has_found = true;
            ecl_type = ECL_SOL_TOTAL;
          }
          if ((search_flag & SweConst.SE_ECL_ANNULAR) != 0 && (eclflag & SweConst.SE_ECL_ANNULAR) != 0) {
            sout="annular ";
            has_found = true;
            ecl_type = ECL_SOL_ANNULAR;
          }
          if ((search_flag & SweConst.SE_ECL_PARTIAL) != 0 && (eclflag & SweConst.SE_ECL_PARTIAL) != 0) {
            sout="partial ";
            has_found = true;
            ecl_type = ECL_SOL_PARTIAL;
          }
          if (!has_found) {
            ii--;
          } else {
            sw.swe_calc(t_ut + SweDate.getDeltaT(t_ut), SweConst.SE_ECL_NUT, 0, x, serr);
	    if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
              double[] dtmp = new double[1];
	      for (i = 0; i < 10; i++) {
	        if (tret[i] != 0)
                  dtmp[0] = tret[i];
		  retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
                  tret[i] = dtmp[0];
	      }
	    }
	    t_ut = tret[0];
//          swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
            sd.setJulDay(t_ut);
            sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
            jyear=sd.getYear();
            jmon=sd.getMonth();
            jday=sd.getDay();
            jut=sd.getHour();
            dt = (tret[3] - tret[2]) * 24 * 60;
//	  sprintf(sout + strlen(sout), "%2d.%02d.%04d\t%s\t%.4f/%.4f/%.4f\tsaros %d/%d\t%.6f\n", jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[8], attr[0], attr[2], (int) attr[9], (int) attr[10], t_ut);
//	  sprintf(sout + strlen(sout), "\t%d min %4.2f sec\t", (int) dt, fmod(dt, 1) * 60); 
#ifdef JAVAME
            sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + hms(jut,BIT_LZEROES) + "\t" +
                f.fmt("%.4f", attr[8]) + "/" + f.fmt("%.4f", attr[0]) + "/" + f.fmt("%.4f", attr[2]) +
                "\tsaros " + f.fmt("%d", (int) attr[9]) + "/" + f.fmt("%d", (int) attr[10]) + "\t" + f.fmt("%.6f", t_ut) + "\n";
            sout += "\t"+(int)dt+" min "+f.fmt("%4.2f",(dt%1.) * 60)+" sec\t";
#else
            sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\t%.4f/%.4f/%.4f\tsaros %d/%d\t%.6f\n",
                jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[8], attr[0], attr[2], (int) attr[9], (int) attr[10], t_ut);
            sout += String.format(Locale.US, "\t%d min %4.2f sec\t", (int) dt, (dt % 1.) * 60); 
#endif /* JAVAME */
            if ((eclflag & SweConst.SE_ECL_1ST_VISIBLE)!=0)
              sout += hms_from_tjd(tret[1]) + " "; 
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_2ND_VISIBLE)!=0)
              sout += hms_from_tjd(tret[2]) + " "; 
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_3RD_VISIBLE)!=0)
              sout += hms_from_tjd(tret[3]) + " "; 
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_4TH_VISIBLE)!=0)
              sout += hms_from_tjd(tret[4]) + " "; 
            else
              sout+="   -         ";
#if 0
//          sprintf(sout + strlen(sout), "\t%d min %4.2f sec   %s %s %s %s", 
//                (int) dt, fmod(dt, 1) * 60, 
//                strcpy(s1, hms(fmod(tret[1] + 0.5, 1) * 24, BIT_LZEROES)), 
//                strcpy(s3, hms(fmod(tret[2] + 0.5, 1) * 24, BIT_LZEROES)), 
//                strcpy(s4, hms(fmod(tret[3] + 0.5, 1) * 24, BIT_LZEROES)),
//                strcpy(s2, hms(fmod(tret[4] + 0.5, 1) * 24, BIT_LZEROES)));
#endif
            sout+="\n";
            do_printf(sout);
          }
        }
      }   /* endif search_local */
#endif /* ASTROLOGY */
#ifndef ASTROLOGY
      /* swetest -solecl
       * find next solar eclipse observable from anywhere on earth */
      if ((special_mode & SP_MODE_LOCAL) == 0) {
        if ((eclflag = sw.swe_sol_eclipse_when_glob(t_ut, whicheph, search_flag,
                  tret, direction_flag?1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } 
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_SOL_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR)!=0) {
          sout="annular ";
          ecl_type = ECL_SOL_ANNULAR;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR_TOTAL)!=0) {
          sout="ann-tot ";
          ecl_type = ECL_SOL_ANNULAR;        /* by Alois: what is this ? */
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_SOL_PARTIAL;
        }
        if ((eclflag & SweConst.SE_ECL_NONCENTRAL)!=0 && (eclflag & SweConst.SE_ECL_PARTIAL)==0)
          sout+="non-central ";
        sw.swe_sol_eclipse_where(t_ut, whicheph, geopos_max, attr, serr);
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
          double[] dtmp = new double[1];
	  for (i = 0; i < 10; i++) {
	    if (tret[i] != 0)
              dtmp[0] = tret[i];
	      retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
              tret[i] = dtmp[0];
	  }
        }
//      swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(tret[0]);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
#ifdef JAVAME
        sout_short = sout + "\t"+f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+
            f.fmt("%4d",jyear)+"\t"+hms(jut,0)+"\t"+f.fmt("%.3f",attr[8]);
        sout += f.fmt("%2d",jday)+"."+f.fmt("%02d",jmon)+"."+f.fmt("%04d",jyear)+"\t"+
            hms(jut,0)+"\t"+f.fmt("%f",attr[3])+" km\t"+
            f.fmt("%.4f",attr[8])+"/"+f.fmt("%.4f",attr[0])+"/"+
            f.fmt("%.4f",attr[2])+"\tsaros "+(int)attr[9]+"/"+
            (int)attr[10]+"\t"+f.fmt("%.6f",tret[0])+"\n";
#else
        sout_short = String.format(Locale.US, "%s\t%2d.%2d.%4d\t%s\t%.3f", sout, jday, jmon, jyear, hms(jut,0), attr[8]);
        sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\t%f km\t%.4f/%.4f/%.4f\tsaros %d/%d\t%.6f\n", 
                  jday, jmon, jyear, hms(jut,0), attr[3], attr[8], attr[0], attr[2], (int) attr[9], (int) attr[10], tret[0]);
#endif /* JAVAME */
        sout += "\t" + hms_from_tjd(tret[2]) + " ";
        if (tret[4] != 0) {
          sout += hms_from_tjd(tret[4]) + " "; 
        } else {
          sout+="   -         ";
        }
        if (tret[5] != 0) {
          sout += hms_from_tjd(tret[5]) + " "; 
        } else {
          sout+="   -         ";
        }
        sout += hms_from_tjd(tret[3]) + "\n"; 
        s1=dms(geopos_max[0], BIT_ROUND_MIN);
        s2=dms(geopos_max[1], BIT_ROUND_MIN);
        sout += "\t" + s1 + "\t" + s2;
        sout += "\t";
        sout_short += "\t";
        if ((eclflag & SweConst.SE_ECL_PARTIAL)==0 && (eclflag & SweConst.SE_ECL_NONCENTRAL)==0) {
          if ((eclflag = sw.swe_sol_eclipse_when_loc(t_ut - 10, whicheph, geopos_max, tret, attr, 0, serr)) == SweConst.ERR) {
            do_printf(serr);
            return SweConst.ERR;
          }
          if (SMath.abs(tret[0] - t_ut) > 2) 
            do_printf("when_loc returns wrong date\n");
          dt = (tret[3] - tret[2]) * 24 * 60;
#ifdef JAVAME
          s1 = (int)dt + " min "+f.fmt("%4.2f", (dt % 1) * 60)+" sec";
#else
          s1 = String.format(Locale.US, "%d min %4.2f sec", (int) dt, (dt % 1) * 60);
#endif /* JAVAME */
          sout += s1;
          sout_short += s1;
        }
        sout_short += "\t" + (int) attr[9] + "\t" + (int) attr[10];
        sout += "\n";
        sout_short += "\n";
        if ((special_mode & SP_MODE_HOCAL)!= 0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(), isgn=new IntObj();
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN, ihou, imin, isec, dfrc, isgn);
          sout="\""+f.fmt("%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        } 
        /*printf("len=%ld\n", strlen(sout));*/
        if (short_output)
	  do_printf(sout_short);
        else
	  do_printf(sout);
      }
    }
    return SweConst.OK;
  }

  private int call_lunar_occultation(double t_ut, int ipl, StringBuffer star, int whicheph, int special_mode, double[] geopos, StringBuffer serr) {
    int i, ii, ecl_type = 0, eclflag, retc;
    double dt, tret[] = new double[30], attr[] = new double[30], geopos_max[] = new double[3];
    String s1, s2;
    boolean has_found = false;
    /* no selective eclipse type set, set all */
    if ((search_flag & SweConst.SE_ECL_ALLTYPES_SOLAR) == 0)
      search_flag |= SweConst.SE_ECL_ALLTYPES_SOLAR;
    /* for local occultations: set geographic position of observer */
    if ((special_mode & SP_MODE_LOCAL) != 0)
      sw.swe_set_topo(geopos[0], geopos[1], geopos[2]); 
    do_printf("\n");
    for (ii = 0; ii < nstep; ii++) {
      sout = "";
      if ((special_mode & SP_MODE_LOCAL) != 0) {
        if ((eclflag = sw.swe_lun_occult_when_loc(t_ut, ipl, star, whicheph, geopos, tret, attr, direction_flag?1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } else { 
          t_ut = tret[0];
	  if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
            double[] dtmp = new double[1];
	    for (i = 0; i < 10; i++) {
	      if (tret[i] != 0)
                dtmp[0] = tret[i];
	        retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
                tret[i] = dtmp[0];
	    }
	  }
	  has_found = false;
	  sout = "";
          if ((search_flag & SweConst.SE_ECL_TOTAL) != 0 && (eclflag & SweConst.SE_ECL_TOTAL) != 0) {
            sout += "total";
            has_found = true;
            ecl_type = ECL_SOL_TOTAL;
          }
          if ((search_flag & SweConst.SE_ECL_ANNULAR) != 0 && (eclflag & SweConst.SE_ECL_ANNULAR) != 0) {
            sout += "annular";
            has_found = true;
            ecl_type = ECL_SOL_ANNULAR;
          }
          if ((search_flag & SweConst.SE_ECL_PARTIAL) != 0 && (eclflag & SweConst.SE_ECL_PARTIAL) != 0) {
            sout += "partial";
            has_found = true;
            ecl_type = ECL_SOL_PARTIAL;
          }
	  if (ipl != SweConst.SE_SUN) {
	    if ((eclflag & SweConst.SE_ECL_OCC_BEG_DAYLIGHT) != 0 && (eclflag & SweConst.SE_ECL_OCC_END_DAYLIGHT) != 0)
	      sout += "(daytime)"; /* occultation occurs during the day */
	    else if ((eclflag & SweConst.SE_ECL_OCC_BEG_DAYLIGHT) != 0)
	      sout += "(sunset) "; /* occultation occurs during the day */
	    else if ((eclflag & SweConst.SE_ECL_OCC_END_DAYLIGHT) != 0)
	      sout += "(sunrise)"; /* occultation occurs during the day */
	  }
	  while (sout.length() < 17)
	    sout += " ";
          if (!has_found) {
            ii--;
          } else {
            sw.swe_calc_ut(t_ut, SweConst.SE_ECL_NUT, 0, x, serr);
//            swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
            sd.setJulDay(tret[0]);
            sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
            jyear=sd.getYear();
            jmon=sd.getMonth();
            jday=sd.getDay();
            jut=sd.getHour();
	    dt = (tret[3] - tret[2]) * 24 * 60;
#ifdef JAVAME
            sout += f.fmt("%2d", jday) + "." + f.fmt("%02d", jmon) + "." + f.fmt("%04d", jyear) + "\t" + f.fmt("%s", hms(jut,BIT_LZEROES)) + "\t" + f.fmt("%f", attr[0]) + "o/o\n";
            sout += "\t" + f.fmt("%d", (int) dt) + " min " + f.fmt("%4.2f", (dt%1) * 60) + " sec\t";
#else
            sout += String.format(Locale.US, "%2d.%02d.%04d\t%s\t%fo/o\n", jday, jmon, jyear, hms(jut,BIT_LZEROES), attr[0]);
            sout += String.format(Locale.US, "\t%d min %4.2f sec\t", (int) dt, (dt%1) * 60);
#endif /* JAVAME */
            if ((eclflag & SweConst.SE_ECL_1ST_VISIBLE) != 0)
              sout += hms_from_tjd(tret[1]) + " ";
            else
              sout += "   -         ";
            if ((eclflag & SweConst.SE_ECL_2ND_VISIBLE) != 0)
              sout += hms_from_tjd(tret[2]) + " ";
            else
              sout += "   -         ";
            if ((eclflag & SweConst.SE_ECL_3RD_VISIBLE) != 0)
              sout += hms_from_tjd(tret[3]) + " ";
            else
              sout += "   -         ";
            if ((eclflag & SweConst.SE_ECL_4TH_VISIBLE) != 0)
              sout += hms_from_tjd(tret[4]) + " ";
            else
              sout += "   -         ";
#if 0
	  sprintf(sout + strlen(sout), "\t%d min %4.2f sec   %s %s %s %s", 
                (int) dt, fmod(dt, 1) * 60, 
                strcpy(s1, hms(fmod(tret[1] + 0.5, 1) * 24, BIT_LZEROES)), 
                strcpy(s3, hms(fmod(tret[2] + 0.5, 1) * 24, BIT_LZEROES)), 
                strcpy(s4, hms(fmod(tret[3] + 0.5, 1) * 24, BIT_LZEROES)),
                strcpy(s2, hms(fmod(tret[4] + 0.5, 1) * 24, BIT_LZEROES)));
#endif
            sout += "\n";
            do_printf(sout);
          }
        }
      }   /* endif search_local */
      if ((special_mode & SP_MODE_LOCAL) == 0) {
      /* * global search for occultations */
        if ((eclflag = sw.swe_lun_occult_when_glob(t_ut, ipl, star, whicheph, search_flag, tret, direction_flag?1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          return SweConst.ERR;
        } 
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_SOL_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR)!=0) {
          sout="annular ";
          ecl_type = ECL_SOL_ANNULAR;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR_TOTAL)!=0) {
          sout="ann-tot ";
          ecl_type = ECL_SOL_ANNULAR;        /* by Alois: what is this ? */
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_SOL_PARTIAL;
        }
        if ((eclflag & SweConst.SE_ECL_NONCENTRAL)!=0 && (eclflag & SweConst.SE_ECL_PARTIAL)==0)
          sout+="non-central ";
        t_ut = tret[0];
        sw.swe_lun_occult_where(t_ut, ipl, star, whicheph, geopos_max, attr, serr);
        if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
          double[] dtmp = new double[1];
	  for (i = 0; i < 10; i++) {
	    if (tret[i] != 0)
              dtmp[0] = tret[i];
	      retc = ut_to_lmt_lat(tret[i], geopos, dtmp, serr);
              tret[i] = dtmp[0];
	  }
        }
//      swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(tret[0]);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        sout += f.fmt("%2d",jday)+"."+f.fmt("%02d",jmon)+"."+f.fmt("%04d",jyear)+"\t"+hms(jut,BIT_LZEROES)+"\t"+f.fmt("%f",attr[3])+" km\t"+f.fmt("%f",attr[0])+" o/o\n";
        sout += "\t" + hms_from_tjd(tret[2]) + " "; 
        if (tret[4] != 0)
          sout += hms_from_tjd(tret[4]) + " "; 
        else
          sout+="   -         ";
        if (tret[5] != 0)
          sout += hms_from_tjd(tret[5]) + " "; 
        else
          sout+="   -         ";
        sout += hms_from_tjd(tret[3]) + "\n"; 
        s1=dms(geopos_max[0], BIT_ROUND_MIN);
        s2=dms(geopos_max[1], BIT_ROUND_MIN);
        sout += "\t"+s1+"\t"+s2;
        if ((eclflag & SweConst.SE_ECL_PARTIAL)==0 && (eclflag & SweConst.SE_ECL_NONCENTRAL)==0) {
          if ((eclflag = sw.swe_lun_occult_when_loc(t_ut - 10, ipl, star, whicheph, geopos_max, tret, attr, 0, serr)) == SweConst.ERR) {
            do_printf(serr);
            return SweConst.ERR;
          }
          if (SMath.abs(tret[0] - t_ut) > 2) 
            do_printf("when_loc returns wrong date\n");
          dt = (tret[3] - tret[2]) * 24 * 60;
          sout += "\t"+(int)dt+" min "+f.fmt("%4.2f",(dt%1.)*60)+" sec\t";
        } 
        sout+="\n";
        if ((special_mode & SP_MODE_HOCAL) != 0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(), isgn=new IntObj();
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN, ihou, imin, isec, dfrc, isgn);
          sout="\""+f.fmt("%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        } 
        do_printf(sout);
      }
#endif /* ASTROLOGY */
      t_ut += direction;
    }
    return SweConst.OK;
  }

  private void do_print_heliacal(double[] dret, int event_type, String obj_name) {
    String sevtname[] = new String[] {"", 
  	"heliacal rising ", 
	"heliacal setting", 
	"evening first   ", 
	"morning last    ", 
	"evening rising  ", 
	"morning setting ",};
    String stz = "UT";
    String stim0, stim1, stim2;
    if ((time_flag & BIT_TIME_LMT) != 0)
      stz = "LMT";
    if ((time_flag & BIT_TIME_LAT) != 0)
      stz = "LAT";
    sout = "";
//    swe_revjul(dret[0], gregflag, &jyear, &jmon, &jday, &jut);
    sd.setJulDay(dret[0]);
    sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
    if (event_type <= 4) {
      if (hel_using_AV) {
        stim0 = hms_from_tjd(dret[0]); 
        stim0 = remove_whitespace(stim0);
        /* The following line displays only the beginning of visibility. */
#ifdef JAVAME
        sout += obj_name + " " + sevtname[event_type] + ": " + sd.getYear() + "/" + f.fmt("%02d", sd.getMonth()) + "/" + f.fmt("%02d", sd.getDay()) + " " + stim0 + " " + stz + " (" + f.fmt("%.5f", dret[0]) + ")\n";
#else
        sout += String.format("%s %s: %d/%02d/%02d %s %s (%.5f)\n", obj_name, sevtname[event_type], sd.getYear(), sd.getMonth(), sd.getDay(), stim0, stz, dret[0]);
#endif /* JAVAME */
      } else {
        /* display the moment of beginning and optimum visibility */
        stim0 = hms_from_tjd(dret[0]); 
        stim1 = hms_from_tjd(dret[1]); 
        stim2 = hms_from_tjd(dret[2]); 
        stim0 = remove_whitespace(stim0);
        stim1 = remove_whitespace(stim1);
        stim2 = remove_whitespace(stim2);
#ifdef JAVAME
        sout += obj_name + " " + sevtname[event_type] + ": " + sd.getYear() + "/" + f.fmt("%02d", sd.getMonth()) + "/" + f.fmt("%02d", sd.getDay()) + " " + stim0 + " " + stz + " (" + f.fmt("%.5f", dret[0]) + ", opt " + stim1 + ", end " + stim2 + ", dur " + f.fmt("%.1f", (dret[2] - dret[0]) * 1440) + " min\n";
#else
        sout += String.format("%s %s: %d/%02d/%02d %s %s (%.5f), opt %s, end %s, dur %.1f min\n", obj_name, sevtname[event_type], sd.getYear(), sd.getMonth(), sd.getDay(), stim0, stz, dret[0], stim1, stim2, (dret[2] - dret[0]) * 1440);
#endif /* JAVAME */
      }
    } else {
      stim0 = hms_from_tjd(dret[0]); 
      stim0 = remove_whitespace(stim0);
#ifdef JAVAME
      sout += obj_name + " " + sevtname[event_type] + ": " + sd.getYear() + "/" + f.fmt("%02d", sd.getMonth()) + "/" + f.fmt("%02d", sd.getDay()) + " " + stim0 + " " + stz + " " + f.fmt("%f", dret[0]) + "\n";
#else
      sout += String.format(Locale.US, "%s %s: %d/%02d/%02d %s %s (%f)\n", obj_name, sevtname[event_type], sd.getYear(), sd.getMonth(), sd.getDay(), stim0, stz, dret[0]);
#endif /* JAVAME */
    }
    do_printf(sout);
  }

#ifndef NO_RISE_TRANS
#ifndef ASTROLOGY
#ifndef JAVAME
  private int call_heliacal_event(double t_ut, int ipl, StringBuffer star, int whicheph, int special_mode, double[] geopos, double[] datm, double[] dobs, StringBuffer serr) {
    int ii, retc, event_type = 0, retflag;
    double dret[] = new double[40], tsave1, tsave2 = 0;
    StringBuffer obj_name = new StringBuffer();
    helflag |= whicheph;
    /* if invalid heliacal event type was required, set 0 for any type */
    if (search_flag < 0 || search_flag > 6)
      search_flag = 0;
    /* optical instruments used: */
    if (dobs[3] > 0) 
      helflag |= SweConst.SE_HELFLAG_OPTICAL_PARAMS;
    if (hel_using_AV)
      helflag |= SweConst.SE_HELFLAG_AV;
    if (ipl == SweConst.SE_FIXSTAR)
      obj_name.append(star);
    else
      obj_name.append(sw.swe_get_planet_name(ipl));
    do_printf("\n");
    mercury_outer: for (ii = 0; ii < nstep; ii++, t_ut = dret[0] + 1) {
      sout = "";
      if (search_flag > 0)
        event_type = search_flag;
      else if (ipl == SweConst.SE_MOON)
        event_type = SweConst.SE_EVENING_FIRST;
      else
        event_type = SweConst.SE_HELIACAL_RISING;
      retflag = sh.swe_heliacal_ut(t_ut, geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
      if (retflag == SweConst.ERR) {
        do_printf(serr);
        return SweConst.ERR;
      }
      if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
        double[] dtmp = new double[1];
        dtmp[0] = dret[0];
        retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
        dret[0] = dtmp[0];
        dtmp[0] = dret[1];
        retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
        dret[1] = dtmp[0];
        dtmp[0] = dret[2];
        retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
        dret[2] = dtmp[0];
      }
      do_print_heliacal(dret, event_type, obj_name.toString());
      /* list all events within synodic cycle */
      if (search_flag == 0) {
        if (ipl == SweConst.SE_VENUS || ipl == SweConst.SE_MERCURY) {
          /* we have heliacal rising (morning first), now find morning last */
          event_type = SweConst.SE_MORNING_LAST;
          retflag = sh.swe_heliacal_ut(dret[0], geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
          if (retflag == SweConst.ERR) {
            do_printf(serr);
            return SweConst.ERR;
          }
          if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
            double[] dtmp = new double[1];
            dtmp[0] = dret[0];
            retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
            dret[0] = dtmp[0];
            dtmp[0] = dret[1];
            retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
            dret[1] = dtmp[0];
            dtmp[0] = dret[2];
            retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
            dret[2] = dtmp[0];
          }
          do_print_heliacal(dret, event_type, obj_name.toString());
          tsave1 = dret[0];
          /* mercury can have several evening appearances without any morning
           * appearances in betweeen. We have to find out when the next 
           * morning appearance is and then find all evening appearances 
           * that take place before that */
          if (ipl == SweConst.SE_MERCURY) {
            event_type = SweConst.SE_HELIACAL_RISING;
            retflag = sh.swe_heliacal_ut(dret[0], geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
            if (retflag == SweConst.ERR) {
              do_printf(serr);
              return SweConst.ERR;
            }
            tsave2 = dret[0];
          }
//      repeat_mercury:
          /* evening first */
          event_type = SweConst.SE_EVENING_FIRST;
          retflag = sh.swe_heliacal_ut(tsave1, geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
          if (retflag == SweConst.ERR) {
            do_printf(serr);
            return SweConst.ERR;
          }
          if (ipl == SweConst.SE_MERCURY && dret[0] > tsave2)
            continue mercury_outer;
          if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
            double[] dtmp = new double[1];
            dtmp[0] = dret[0];
            retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
            dret[0] = dtmp[0];
            dtmp[0] = dret[1];
            retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
            dret[1] = dtmp[0];
            dtmp[0] = dret[2];
            retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
            dret[2] = dtmp[0];
          }
          do_print_heliacal(dret, event_type, obj_name.toString());
        }
        while(true) {
          if (ipl == SweConst.SE_MOON) {
            /* morning last */
            event_type = SweConst.SE_MORNING_LAST;
            retflag = sh.swe_heliacal_ut(dret[0], geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
            if (retflag == SweConst.ERR) {
              do_printf(serr);
              return SweConst.ERR;
            }
            if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
              double[] dtmp = new double[1];
              dtmp[0] = dret[0];
              retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
              dret[0] = dtmp[0];
              dtmp[0] = dret[1];
              retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
              dret[1] = dtmp[0];
              dtmp[0] = dret[2];
              retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
              dret[2] = dtmp[0];
            }
            do_print_heliacal(dret, event_type, obj_name.toString());
          } else {
            /* heliacal setting (evening last) */
            event_type = SweConst.SE_HELIACAL_SETTING;
            retflag = sh.swe_heliacal_ut(dret[0], geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
            if (retflag == SweConst.ERR) {
              do_printf(serr);
              return SweConst.ERR;
            }
            if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
              double[] dtmp = new double[1];
              dtmp[0] = dret[0];
              retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
              dret[0] = dtmp[0];
              dtmp[0] = dret[1];
              retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
              dret[1] = dtmp[0];
              dtmp[0] = dret[2];
              retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
              dret[2] = dtmp[0];
            }
            do_print_heliacal(dret, event_type, obj_name.toString());
            if (false && ipl == SweConst.SE_MERCURY) {
              tsave1 = dret[0];
//              goto repeat_mercury;
              // quick hack by duplicating the code from the first part of the repeat_mercury: section
              /* evening first */
              event_type = SweConst.SE_EVENING_FIRST;
              retflag = sh.swe_heliacal_ut(tsave1, geopos, datm, dobs, obj_name, event_type, helflag, dret, serr);
              if (retflag == SweConst.ERR) {
                do_printf(serr);
                return SweConst.ERR;
              }
              if (ipl == SweConst.SE_MERCURY && dret[0] > tsave2)
                continue mercury_outer;
              if ((time_flag & (BIT_TIME_LMT | BIT_TIME_LAT)) != 0) {
                double[] dtmp = new double[1];
                dtmp[0] = dret[0];
                retc = ut_to_lmt_lat(dret[0], geopos, dtmp, serr);
                dret[0] = dtmp[0];
                dtmp[0] = dret[1];
                retc = ut_to_lmt_lat(dret[1], geopos, dtmp, serr);
                dret[1] = dtmp[0];
                dtmp[0] = dret[2];
                retc = ut_to_lmt_lat(dret[2], geopos, dtmp, serr);
                dret[2] = dtmp[0];
              }
              do_print_heliacal(dret, event_type, obj_name.toString());
            } else {
            	break;
            }
          }
        } // end of goto loop
      }
    }
    return SweConst.OK;
  }
#endif /* NO_RISE_TRANS */
#endif /* ASTROLOGY */
#endif /* JAVAME */

  private int do_special_event(double tjd, int ipl, StringBuffer star, int special_event, int special_mode, double[] geopos, double[] datm, double[] dobs, StringBuffer serr) {
    int retc = 0;
#ifndef NO_RISE_TRANS
    /* risings, settings, meridian transits */
    if (special_event == SP_RISE_SET || 
        special_event == SP_MERIDIAN_TRANSIT)
      retc = call_rise_set(tjd, ipl, star, whicheph, special_mode, geopos, serr);
    /* lunar eclipses */
    if (special_event == SP_LUNAR_ECLIPSE)
      retc = call_lunar_eclipse(tjd, whicheph, special_mode, geopos, serr);
    /* solar eclipses */
    if (special_event == SP_SOLAR_ECLIPSE)
      retc = call_solar_eclipse(tjd, whicheph, special_mode, geopos, serr);
#ifndef ASTROLOGY
    /* occultations by the moon */
    if (special_event == SP_OCCULTATION)
      retc = call_lunar_occultation(tjd, ipl, star, whicheph, special_mode, geopos, serr);
#ifndef JAVAME
    /* heliacal event */
    if (special_event == SP_HELIACAL)
      retc = call_heliacal_event(tjd, ipl, star, whicheph, special_mode, geopos, datm, dobs, serr);
#endif /* JAVAME */
#endif /* ASTROLOGY */
#endif /* NO_RISE_TRANS */
    return retc;
  }

  private String hms_from_tjd(double x) {
    String s;
    s = hms(((x + 1000000.5)%1.) * 24, BIT_LZEROES)+" ";
    return s;
  }
  
  private String hms(double x, int iflag) {
    String s;
    int sp;
    String c = swed.ODEGREE_STRING;
    x += 0.5 / 36000.0; /* round to 0.1 sec */
    s=dms(x, iflag);
    sp = s.indexOf(c);
    if (sp >= 0) {
      s = s.substring(0,sp) + ":" + s.substring(sp+c.length());
      s = s.substring(0,sp+3) + ":" + s.substring(sp+4);
      s = s.substring(0,sp+8);
    }
    return s;
  }
  
  static void do_printf(StringBuffer info) {
    System.out.print(info.toString());
  }
  static void do_printf(String info) {
    System.out.print(info);
  }

  /* make_ephemeris_path().
   * ephemeris path includes
   *   current working directory
   *   + program directory
   *   + default path from swephexp.h on current drive
   *   +                              on program drive
   *   +                              on drive C:
   */
  private int make_ephemeris_path(int iflg, String argv0, StringBuffer opath) {	// path for output
    int sp;
    String path = "";
    String dirglue = swed.DIR_GLUE;
    int pathlen = 0;
    /* moshier needs no ephemeris path */
    if ((iflg & SweConst.SEFLG_MOSEPH)!=0)
      return SweConst.OK;
    /* current working directory */
    path="."+swed.PATH_SEPARATOR.charAt(0);
    /* program directory */
    sp = argv0.lastIndexOf(dirglue);
    if (sp >= 0) {
      pathlen = sp;
      if (path.length() + pathlen < AS_MAXCH-2) {
        path += argv0.substring(0, SMath.min(argv0.length(), pathlen));
        path += swed.PATH_SEPARATOR.charAt(0);
      }
    }
    if (path.length() + SweConst.SE_EPHE_PATH.length() < AS_MAXCH-1)
      path+=SweConst.SE_EPHE_PATH;
    // output path:
    opath.setLength(0);
    opath.append(path);
    return SweConst.OK;
  }

  private String remove_whitespace(String s) {
//    char *sp, s1[AS_MAXCH];
//    if (s == NULL) return;
//    for (sp = s; *sp == ' '; sp++)
//      ;
//    strcpy(s1, sp);
//    while (*(sp = s1 + strlen(s1) - 1) == ' ')
//      *sp = '\0';
//    strcpy(s, s1);
    return s.trim();
  }

} // End of class Swetest
