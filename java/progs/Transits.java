#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef JAVAME
#undefine TRANSITS
#undefine TEST_ITERATIONS
#endif /* JAVAME */
#ifdef TEST_ITERATIONS
#define TRANSITS
#endif /* TEST_ITERATIONS */
#ifdef TRANSITS
/*
   The swisseph package used herein is a Java port of the Swiss Ephemeris
   of Astrodienst Zuerich, Switzerland. For copyright notices see the file
   LICENSE or - if not included - see at http://www.astro.com for license
   information.

   This small program is heavily based upon sweclips.c from the original
   Swiss Ephemeris package.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 25th of November 2001
*/

import swisseph.*;
import java.text.*;  // DateFormat etc.
import java.util.*;  // Locale etc.

/**
* Test program for planetary transits.
* See class swisseph.SwissEph.<P>
* Invoke with parameter -h to get the help text.
* @see swisseph.SwissEph
*/
public class Transits
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  // Consecutive transit calculations need a minimum time difference
  static final double MIN_TIME_DIFF = 1./24./3600./2.;

  static final String infocmd0 = "\n"+
  "  'Transits' computes planetary transits over specified longitudes,\n"+
  "  latitudes, distances, speeds in any of these directions or\n"+
  "  in any above variation over other planets.\n\n";

  static final String infocmd1 = "\n"+
  "  You can calculate several kinds of transits:\n\n"+
  "    - When do planets transit a certain longitude,\n"+
  "      latitude or distance:\n"+
  "         -p.. -b... -lon... [other options]\n"+
  "         -p.. -b... -lat... [other options]\n"+
  "         -p.. -b... -dist... [other options]\n"+
  "    - When do planets transit a certain speed in\n"+
  "      longitude, latitude or distance:\n"+
  "         -p.. -b... -s -lon... [other options]\n"+
  "         -p.. -b... -s -lat... [other options]\n"+
  "         -p.. -b... -s -dist... [other options]\n"+
  "    - When do planets transit other planet's\n"+
  "      longitude, latitude or distance:\n"+
  "         -p.. -P.. -b... -lon... [other options])\n"+
  "         -p.. -P.. -b... -lat... [other options])\n"+
  "         -p.. -P.. -b... -dist... [other options])\n"+
  "    - When do planets transit other planet's\n"+
  "      speed in longitude, latitude or distance:\n"+
  "         -p.. -P.. -b... -s -lon... [other options])\n"+
  "         -p.. -P.. -b... -s -lat... [other options])\n"+
  "         -p.. -P.. -b... -s -dist... [other options])\n"+
  "    - When does the SUM (==Yoga) of two planet's positions\n"+
  "      (or speeds with -s option) reach a certain value:\n"+
  "         -p.. -P.. -b... +lon... [other options])\n"+
  "         -p.. -P.. -b... +lat... [other options])\n"+
  "         -p.. -P.. -b... +dist... [other options])\n"+
  "\n    Other options:\n"+
  "    - List all transits in a date range by giving a second date:\n"+
  "         [base options] -b... -b...\n"+
  "         [base options] -b... -B...\n"+
  "    - Give an exact starting and / or end time:\n"+
  "         [base options] -et... (or: -t...)\n"+
  "         [base options] -ut...\n"+
  "         [base options] -lt...\n"+
  "         [base options] -ET... (or: -T...)\n"+
  "         [base options] -UT...\n"+
  "         [base options] -LT...\n"+
  "    - List a fixed number of consecutive transits:\n"+
  "         [base options] -n...\n"+
  "         [base options] -N...\n"+
  "    - Search backwards (\"reverse\"):\n"+
  "         [base options] -r\n"+
  "    - Calculate in the sidereal zodiac:\n"+
  "         [base options] -sid.\n"+
  "    - Calculate topocentric or heliocentric instead of geocentric:\n"+
  "         [base options] -topo...\n"+
  "         [base options] -hel...\n"+
  "    - Modify the input parsing:\n"+
  "         [base options] -loc... (input parsing AND output formatting)\n"+
  "         [base options] -iloc...\n"+
  "         [base options] -Dloc[...]\n"+
  "         [base options] -Nloc[...]\n"+
  "    - Modify the output and output formatting:\n"+
  "         [base options] -head, -q\n"+
  "         [base options] -f...\n"+
#ifdef TEST_ITERATIONS
  "         [base_options] -iter\n"+
#endif /* TEST_ITERATIONS */
  "         [base options] -loc... (input parsing AND output formatting)\n"+
  "         [base options] -oloc...\n"+
  "         [base options] -dloc[...]\n"+
  "         [base options] -nloc[...]\n"+
#ifdef EXTPRECISION
  "    - Modify the calculation precision:\n"+
  "         [base options] -x...\n"+
#endif /* EXTPRECISION */
  "    - Give the path to the ephemeris data files:\n"+
  "         [base options] -edir...\n"+
  "    - List all availables Locales for use with -loc etc. options:\n"+
  "         -locales\n"+
  "    - Convert between Julian day numbers and dates and vice versa:\n"+
  "         -b... -cv [other options]\n"+
  "";

  static final String infodate = "\n"+
  "  Date entry:\n"+
  "  You can enter the start date entry (option '-b') and the end date\n"+
  "  entry (if required, option '-B') in one of the following formats:\n"+
  "\n"+
  "        2-27-1991       three integers separated by a nondigit character for\n"+
  "                        day month year. Dates are interpreted as Gregorian\n"+
  "                        after October 4, 1582 and as Julian Calender before.\n"+
  "                        Time is always set to midnight. Use -et, -ut or -lt\n"+
  "                        to set the time.\n"+
  "                        The sequence of year, month and day is determined\n"+
  "                        by the locale settings, see options -loc etc.. With\n"+
  "                        -locde 5.8.2000 would be interpreted as a date in\n"+
  "                        August, -locen would see a date in May 2000.\n"+
  "                        Use -et / -ut / -lt without any following time to\n"+
  "                        force times to be interpreted as ET (-et), UT (-ut)\n"+
  "                        or local time (LT, -lt). Default ist ET.\n"+
  "                        If the three letters 'jul' are appended to the date,\n"+
  "                        the Julian calendar is used even after 1582.\n"+
  "                        If the four letters 'greg' are appended to the date,\n"+
  "                        the Gregorian calendar is used even before 1582.\n"+
  "\n"+
  "        j2400123.67     the letter j followed by a real number, for\n"+
  "                        the absolute Julian daynumber of the start date.\n"+
  "                        Fraction .5 indicates midnight, fraction .0\n"+
  "                        indicates noon, other times of the day can be\n"+
  "                        chosen accordingly.\n"+
  "        today           this will use the current date.\n"+
  "  You can enter any time entry (options -et / -ET / -ut / -UT / -lt / -LT)\n"+
  "  in the following formats:\n"+
  "        hh:mm:ss        three integers representing hour, minutes and\n"+
  "                        seconds separated by non-digits\n"+
  "        now             (String) use current time.\n";

  String infocmd2 = null;
  String infoexamples = null;

  private void initHelpTexts() {
  infocmd2 = "\n"+
  "  Command line options:\n"+
  "    Main options:\n"+
  "        -bDATE    use this date; use format -b3/24/1993 or -bj2400000.5,\n"+
  "                  to express the date as absolute Julian day number.\n"+
  "                  Use option -hdate for more information.\n"+
  "                  You can use two -b... options to give a starting and\n"+
  "                  an end date.\n"+
  "                  Note: the date format is month/day/year (US style)\n"+
  "                  by default.\n"+
  "        -j....    Same as -bj....\n"+
  "        -BDATE    use this date as the end date for a time range, use\n"+
  "                  -Bj..... for a julian day number\n"+
  "                  Same as a second -b... option, but it searches for\n"+
  "                  transits over ANY of the adjacent transit points instead\n"+
  "                  of looking for the NEXT transit point only, when giving\n"+
  "                  more than one longitude etc.. See the -n / -N options\n"+
  "                  for similar considerations.\n"+
  "        -J....    Same as -Bj....\n"+
  "        -uthh:mm:ss hour in UT for -b... date\n"+
  "        -UThh:mm:ss hour in UT for -B... date. If -UT is not given, it\n"+
  "                    defaults to the value of -ut\n"+
  "                    You can use the String 'now' for the current UTC time.\n"+
  "        -ethh:mm:ss hour in ET for -b... date, it defaults to 0.0\n"+
  "        -EThh:mm:ss hour in ET for -B... date. Default: the value of -et\n"+
  "        -lthh:mm:ss hour in local time in the timezone as it is setup on\n"+
  "                    your computer\n"+
  "        -LThh:mm:ss hour in local time in the timezone as it is setup on\n"+
  "                    your computer\n"+
  "                    of -lt\n"+
  "                    Be aware that the local time offset will be constant\n"+
  "                    for all times being output. E.g.: when daylight\n"+
  "                    saving time is in effect NOW but not on the transit\n"+
  "                    time, the output time will appear to be incorrect by\n"+
  "                    the daylight saving offset.\n"+
  "        -utnow (etc.): The String 'now' uses the current time.\n"+
  "        -p.\n"+
  "        -p...     One or more planets for which to calculate the transit.\n"+
  "                  Supported planet numbers are:\n"+
  "                    0 Sun        8 Neptune            D Chiron\n"+
  "                    1 Moon       9 Pluto              E Pholus\n"+
  "                    2 Mercury    m Mean node          F Ceres\n"+
  "                    3 Venus      t True node          G Pallas\n"+
  "                    4 Mars       A mean lunar apogee  H Juno\n"+
  "                    5 Jupiter      (Lilith)           I Vesta\n"+
  "                    6 Saturn     B osculating lunar   c Interpolated lunar apogee\n"+
  "                    7 Uranus       apogee             g Interpolated lunar perigee\n"+
  "        -P.\n"+
  "        -P...     Calculate transits relative to this or these planets.\n"+
  "                  When giving more than one planet here, it will calculate\n"+
  "                  the next or previous transit(s) over any of these\n"+
  "                  planets.\n"+
  "        -lon...   longitude or longitudinal speed, over which the transit\n"+
  "                  has to occur. If two planets are given, this means the\n"+
  "                  position (or speed) of planet -px after planet -Px\n"+
  "        -lat...   latitude or latitudinal speed, over which the transit\n"+
  "                  has to occur. If two planets are given, this means the\n"+
  "                  position (or speed) of planet -px after planet -Px\n"+
  "        -dist...  distance or speed in distance movement in AU, over which\n"+
  "                  the transit has to occur. If two planets are given, this\n"+
  "                  means the distance position (or speed) of planet -px\n"+
  "                  after planet -Px\n"+
  "        +lon...   same as -lon for transits of one planet over another\n"+
  "                  planet with the difference that the SUM (Yoga) of the\n"+
  "                  positions or speeds of both planets will be calculated\n"+
  "        +lat...   same as -lat for transits of one planet over another\n"+
  "                  planet with the difference that the SUM (Yoga) of the\n"+
  "                  positions or speeds of both planets will be calculated\n"+
  "        +dist...  same as -dist for transits of one planet over another\n"+
  "                  planet with the difference that the SUM (Yoga) of the\n"+
  "                  positions or speeds of both planets will be calculated\n"+
  "        -lon, -lat, -dist, +lon, +lat, +dist can all take a  form that\n"+
  "                  increases the given value on each iteration by an offset.\n"+
  "                  Alternatively, you can give multiple values separated by\n"+
  "                  a forward slash (\"/\").\n"+
  "                  The correct syntax is:\n"+
  "                  {\"-\"|\"+\"}{\"lon\"|\"lat\"|\"dist\"}STARTVAL[\"+\"|\"-\"OFFSET]\n"+
  "                  or:\n"+
  "                  {\"-\"|\"+\"}{\"lon\"|\"lat\"|\"dist\"}VAL[\"/\"VAL]\n"+
  "                  STARTVAL, OFFSET and VAL are normal floating point numbers.\n"+
  "                  Use with option -n / -N or with -b -b / -b -B. Example:\n"+
  "                  -lon0+30.0 -n12\n"+
  "                  searches for 12 consecutive transits with the degree\n"+
  "                  changing from one step to the other by 30 degrees.\n"+
  "                  -lat0/0.1/-0.1 -b1-1-2005 -B1-1-2006\n"+
  "                  searches for any transits over 0, 0.1 and -0.1\n"+
  "                  latitudinal degree in the year 2005.\n"+
  "        -s        Calculate the transit over a given speed instead of\n"+
  "                  a given position\n"+
  "\n    Additional options:\n"+
  "        -r        search backward\n"+
  "        -topo[long;lat;elev]\n"+
  "                  Calculate related to a position on the surface of the\n"+
  "                  earth, default is geocentric calculation. Longitude,\n"+
  "                  latitude (degrees with decimal fraction) and elevation\n"+
  "                  (meters) are optional. Default is Z\u00fcrich: 8.55;47.38;400\n"+
  "        -hel      Perform heliocentric calculations instead of geocentric\n"+
  "        -sid.     a sidereal mode, if sidereal calculation is wanted.\n"+
  "                  Valid modes are:\n"+
  "                     0 Fagan/Bradley           10 Babylonian, Kugler2\n"+
  "                     1 Lahiri                  11 Babylonian, Kugler3\n"+
  "                     2 DeLuce                  12 Babylonian, Huber\n"+
  "                     3 Raman                   13 Babylonian, Mercier\n"+
  "                     4 Ushashashi              14 t0=Aldebaran, 15"+swed.ODEGREE_CHAR+" taurus\n"+
  "                     5 Krishnamurti            15 Hipparchos\n"+
  "                     6 Djwhal Khul             16 Sassanian\n"+
  "                     7 Sri Yukteshwar          17 Galactic center=0"+swed.ODEGREE_CHAR+" sagitt.\n"+
  "                     8 JN Bhasin               18 J2000\n"+
  "                     9 Babylonian, Kugler1     19 J1900\n"+
  "                                               20 B1950\n"+
  "        -n...     search for <n> transits instead of just one. If you want\n"+
  "                  all transits in a time range, use option -B... to give a\n"+
  "                  second date\n"+
  "        -N...     search for <N> transits instead of just one. Differently\n"+
  "                  to the -n option, this searches for the next OR(!) the\n"+
  "                  same OR(!) the previous transit position value, when you\n"+
  "                  give an increment value to the -lon etc. options. This\n"+
  "                  is useful ONLY, when a planet can move both direct and\n"+
  "                  retrograde, so you will not miss any transit point.\n"+
  "                  Notice the difference between the two commands:\n"+
  "                    java Transits -p5 -b01/01/2012 -lon60+10 -n6 -oloc24\n"+
  "                    java Transits -p5 -b01/01/2012 -lon60+10 -N6 -oloc24\n"+
#ifdef EXTPRECISION
  "        -x...     Increase calculation precision beyond the available\n"+
  "                  planetary calculation precision. E.g. -x1000 will stop\n"+
  "                  the transit calculation process not before the transit\n"+
  "                  point will have been approached by a factor 1000 of the\n"+
  "                  available precision of the planetary position calculation.\n"+
  "                  This will cause additional calculation iterations. On the\n"+
  "                  other hand, it might be used to stop calculation earlier,\n"+
  "                  if you give a number less than one, e.g. 0.01.\n"+
  "                  This precision modifier defaults to 1, meaning: don't\n"+
  "                  alter precision.\n"+
#endif /* EXTPRECISION */
  "        -f...     Output format options, default is -fdt or -fvdt, if we\n"+
  "                  calculate consecutive transits with changing degrees.\n"+
  "        -f+...    Same as -f, but append the following options to the\n"+
  "                  default options instead of replacing them all.\n"+
  "                  Available options:\n"+
  "          n       The name of the planet(s).\n"+
  "          d, d..  Output transit date and time with a given number of\n"+
  "                  decimal places in the seconds part.\n"+
  "                  'd5' might give you a time output like 20:26:46.80099,\n"+
  "                  'd' (or 'd0') will result in 20:26:47\n"+
  "                  All date output is localized to the 'en_US' locale if\n"+
  "                  not specified by the different -loc etc. options.\n"+
  "          t       Postfix the dates with 'ET' (Ephemeris Time) or 'UT'\n"+
  "                  (Universal Time) as appropriate.\n"+
  "          j, j..  Output transit date and time as julian day numbers with\n"+
  "                  the given numbers of decimal places. Saying 'j' is\n"+
  "                  identical to saying 'j8': 8 decimal places.\n"+
  "          v, v..  Output the transit degree or distance or speed value\n"+
  "                  with the given number of decimal places. 'v' is equal\n"+
  "                  to 'v2'\n"+
  "          p, p..  Output the actual position (or speed) on the found date\n"+
  "                  with the given number of decimal places. 'p' is equal\n"+
  "                  to 'p2', which means two decimal places.\n"+
  "               Only when calculating relative transits:\n"+
  "          P, P..  (Capital P) adds the real difference or sum (for yoga\n"+
  "                  transits) of the planets positions to the output. 'P'\n"+
  "                  means 'P2', which means, output the number with two\n"+
  "                  decimal places.\n\n"+
  "  Localization (internationalization):\n"+
  "  ====================================\n"+
  "  Input parsing and output formatting is done using the 'en_US' locale,\n"+
  "  meaning in the american style by default."+
  "  Localization knows about two different fields: numbers and dates. You\n"+
  "  can give both localization information for input parsing and output\n"+
  "  formatting. The default is 'en_US', you can change it to your current\n"+
  "  system locale by giving the option -loc without any locale added.\n"+
  "  The -loc options will change all patterns at the same time, all other\n"+
  "  options will just care for partial aspects at a time.\n"+
  "    -loc  is for input parsing and output formatting of numbers and dates\n\n"+
  "    -iloc is for input parsing of numbers and dates only\n"+
  "    -oloc is for output formatting of numbers and dates only\n\n"+
  "    -Dloc is for input parsing of dates only\n"+
  "    -Nloc is for input parsing of numbers only\n"+
  "    -dloc is for output formatting of dates only\n"+
  "    -nloc is for output of numbers like degrees or speed or JD\n\n"+
  "    The locale parameter without any locale string added to it (-loc /\n"+
  "    -iloc / -oloc etc.pp.) will use the default system locale. Add the\n"+
  "    locale String to use a specific locale, e.g. -dlocro for romanian\n"+
  "    date output formatting or -olochi_IN to use the indian hindi style\n"+
  "    in output. Use -locswiss to revert to the default behaviour of\n"+
  "    Swetest.java and the original C versions of swiss ephemeris.\n"+
  "    You can append '24' to -loc etc., to use 24 hours date formats on\n"+
  "    output, even when the localization would use AM/PM formats. E.g.:\n"+
  "    -loc24hi_IN (24 hour time format in Hindi) or: -loc24 or -loc24en\n"+
  "    ATTENTION: input parsing of time is ALWAYS done in the 24 hour format\n"+
  "    hh:mm:ss only!\n"+
  "    -locales  List all available locales. Does not compute anything.\n\n"+
  "  Other options:\n"+
  "  ==============\n"+
#ifndef NO_JPL
  "        -ejpl...  calculate with JPL ephemeris. Append filename of JPL\n"+
  "                  data file. If the filename is not appended, the\n"+
  "                  default SweConst.SE_FNAME_DE406 (de406.eph) is used.\n"+
#endif /* NO_JPL */
  "        -eswe     calculate with swiss ephemeris\n"+
#ifndef NO_MOSHIER
  "        -emos     calculate with moshier ephemeris\n"+
#endif /* NO_MOSHIER */
  "        -edirPATH change the directory of the ephemeris data files \n"+
  "        -q\n"+
  "        -head     don\'t print the header before the planet data\n"+
#ifdef TEST_ITERATIONS
  "        -iter     output the number of calculations needed to find this\n"+
  "                  planet's transit. Output format:\n"+
  "                  \"Iterations:\" (planet char.)  count\n"+
  "                  \"Iterations:\" (planet char.)  count \"(\"count\"/\"count\"/\"...\")\"\n"+
  "                  E.g., planet number three (Venus) needed 54 iterations\n"+
  "                  for the calculation of three transit values, that\n"+
  "                  required 18 planet calculations each:\n"+
  "                        Iterations (3):       54 (18/18/18)\n"+
  "                  Be aware, the transit values become ordered by\n" +
  "                  Math.abs(val), so the order of the numbers in the\n" +
  "                  parenthesis may be different than in the arguments.\n" +
#endif /* TEST_ITERATIONS */
  "\n    Output of informations only:\n"+
  "        -locales  List all available locales. Does not compute anything.\n\n"+
  "        -b... -cv Convert between julian day numbers and dates back and\n"+
  "                  forth and exit. Does not compute anything. May be\n"+
  "                  combined with -ut / -et option. Default is ET. See\n"+
  "                  option -hdate for info about the possible date\n"+
  "                  parameters.\n\n"+
  "    Help options:\n"+
  "        -?, -h    display info\n"+
  "        -hdate    display date info\n"+
  "        -hex      display examples\n";


  infoexamples = "\n"+
  "  Examples:\n\n"+
  "  Simple transits:\n"+
  "     Next transit of the moon over 123.4702 degrees:\n"+
  "       java Transits -p1 -lon123.4702 -btoday -utnow\n\n"+
  "     The first complete Nakshatras cycle in year 2006 starting with\n"+
  "     Ashvini (0"+swed.ODEGREE_CHAR+" in sidereal zodiac) and Lahiri ayanamsh related to a\n"+
  "     topocentric position somewhere in Germany:\n"+
  "       java Transits -b1/1/2006 -ut -topo11.0/52.22/160 -p1 -lon0+13.3333333333333 -n27 -sid1 -fv6dtj -ilocen -oloc\n\n"+
  "     Transits of pluto or uranus or neptune over 0 degrees/day of\n"+
  "     latitudinal speed before 1998 with output of real speed on that\n"+
  "     date, formatted in your current Locale:\n"+
  "       java Transits -p789 -lat0 -s -b12/31/1997 -utnow -n10 -r -fdtjp -oloc\n\n"+
  "     When will be mercury's next 10 stationary states (speed = 0) in\n"+
  "     it's longitudinal direction of movement (\"when does mercury change\n"+
  "     it's motion from or to retrograde\"):\n"+
  "       java Transits -p2 -btoday -utnow -lon0 -s -n10 -oloc\n\n"+
  "     When did Pluto cross a very far distance of 50.2 AU between year 0:\n"+
  "     and today with additional output of the julian day number:\n"+
  "       java Transits -p9 -b1/1/0 -Btoday -dist50.2 -f+j5p\n\n"+
#ifdef EXTPRECISION
  "     See the difference in precision for different precision factors\n"+
  "     (higher is more precise):\n"+
#ifdef TEST_ITERATIONS
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -x0.001 -iter\n"+
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -x1 -iter\n"+
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -x10000 -iter\n\n"+
#else
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -q -x0.001\n"+
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -q -x1\n"+
  "       java Transits -pt -lon100 -b1/1/2004 -ilocen -f+jP12 -q -x10000\n\n"+
#endif /* TEST_ITERATIONS */
#endif /* EXTPRECISION */
  "  Transits relative to other planets:\n"+
  "     All full, half and new moons in year 2008 in local time zone:\n"+
  "     BE AWARE: All times will have an identical offset to UTC time,\n"+
  "               even though this offset may change probably due to\n"+
  "               daylight savings in effect or not in effect!\n"+
  "       java Transits -p0 -P1 -lon0+90 -b1/1/2007 -lt -B12/31/2008 -LT23:59:59 -loc24en\n\n"+
  "     Conjunctions of jupiter with saturn since 2000 until 2050:\n"+
  "       java Transits -p5 -P6 -lon0 -b1/1/2000 -b12/31/2050 -fdtjpP -oloc24\n\n"+
  "     The next 20 transits of mars over any of the major planets with any\n"+
  "     of the major aspect angles in western astrology:\n"+
  "       java Transits -p4 -P0123456789 -lon0/30/45/60/90/120/150/180 -btoday -utnow -n20 -f+pP\n\n"+
  "     The next 30 tithis starting with the next purnima (full moon):\n"+
  "       java Transits -btoday -ut -p1 -P0 -lon180+12 -n30 -sid1\n\n"+
  "       Note: you have to use:\n"+
  "       -lon0/12/24/36/48/60/72/84/96/108/120/132/144/156/168/180/192/204/216/228/240/252/264/276/288/300/312/324/336/348\n"+
  "       if you like to start with ANY of the tithis and not with 180 degrees.\n\n"+
  "     The first 12 yogas starting from January 1, 2006:\n"+
  "       java Transits -p0 -P1 +lon0+13.33333333333333 -sid1 -b1/1/2006 -ut -n12 -ilocen -f+P\n\n"+
  "     Past Sade Sadhi for a moon in Kanya since April 1957:\n"+
  "       java Transits -p6 -lon120/210 -b4/13/1957 -btoday -ut -sid1 -oloc\n\n"+
  "     All trigon aspects between any of the major planets during the time\n"+
  "     of January 1, 2006 and January 1, 2008.\n"+
  "       java Transits -b1/1/2006 -B1/1/2008 -p0123456789 -P0123456789 -lon120 -oloc\n\n\n"+
  "     IMPORTANT NOTE: this kind of calculation may take a LONG(!) time to\n"+
  "           complete, as it will try to find transits of planets not being\n"+
  "           able to have that distance to each other, e.g., Sun and Mercury.\n"+
  "           Those kind of transits will be calculated up to the end of the\n"+
  "           calculable time, which might be far beyond the year 5000.\n"+
  "     So, you will not get any results from the following one, but it will\n"+
  "     take a rather long time to complete:\n"+
  "       java Transits -p23 -P23 -btoday -lon75 -n10 -oloc\n\n"+
  "     If you add another planet or degree, for which a transit will be found,\n"+
  "     the problem will disappear:\n"+
  "       java Transits -p23 -P235 -btoday -lon75 -n10 -oloc\n"+
  "       java Transits -p23 -P23 -btoday -lon75/0 -n10 -oloc\n\n"+
#ifdef TEST_ITERATIONS
  "  Display information about the calculations themselves:\n"+
  "     How many calculations does it take, to find ten full moons:\n"+
  "       java Transits -p0 -P1 -b1/1/2008 -lon180 -n10 -oloc -iter\n\n"+
  "     More interesting is another case:\n"+
  "       java Transits -btoday -n2 -s -sid1 -p0123456789 -P0123456789 -lat1E-2+3E-2 -f+pP -iter\n\n"+
#endif /* TEST_ITERATIONS */
  "";
  }

  /**************************************************************/


  SwissEph  sw=new SwissEph();
  SwissLib  sl=new SwissLib();
  Extlib    el=new Extlib();
  SwissData swed=new SwissData();
  CFmt f=new CFmt();

  Locale[] locs = Locale.getAvailableLocales();
  String locale = "en_US"; // Make en_US the default
  String Nlocale = null;   // Locale to localize numbers on input
  String Dlocale = null;   // Locale to interpret dates on input
  String nlocale = null;   // Locale to localize numbers on output
  String dlocale = null;   // Locale to interpret dates on output

  boolean force24hSystem = false;


  String dateFracSeparator = ".";
  String numIFracSeparator = ".";
  String numOFracSeparator = ".";
  // For formatting dates:
  SimpleDateFormat dif = null;
  SimpleDateFormat dof = null;
  // For formatting the decimal parts of the seconds in dates:
  // Fraction of a second not (yet?) supported on input.
  NumberFormat dnof = null;
  // For formatting other numbers:
  NumberFormat nnif = null;
  NumberFormat nnof = null;
  int secondsIdx = 0;

  double tzOffset = 0;

  /**
  * See -h parameter for help on all parameters.
  */
  public static void main(String argv[]) {
    Transits sc=new Transits();

//    sc.tzOffset = TimeZone.getDefault().getOffset(new Date().getTime())/1000.0/3600.0/24.0; // Uses the CURRENT time zone offset in all cases!
    Calendar cal = Calendar.getInstance();
    sc.tzOffset = (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET))/1000.0/3600.0/24.0;

    System.exit(sc.startCalculations(argv, true));
  }

  /**
  * If you want to use this class in your own programs, you would
  * just call this method. All output will go to stdout only (so
  * far).
  * @param argv array of Strings containing all parameters like on
  * the command line.
  * @return nothing so far
  */
  public int startCalculations(String[] argv) {
    return startCalculations(argv, false);
  }

  private int startCalculations(String[] argv, boolean withErrMsg) {
    TransitArguments a = null;
    TransitCalculator tcs[] = null;

    try {
      a = parseArgs(argv);
      if (a == null) { return 0; }
      if (!a.convert) {
        tcs = initCalculators(a);
      }
      writeCmdLine(argv, a.withHeader);
      if (!a.convert) {
        writeHeader(a);
      }
    } catch (NullPointerException np) {
System.err.println(np);
      return 1;
    } catch (IllegalArgumentException ia) {
      if (withErrMsg) {
        System.err.println(ia.getMessage());
      }
      return -1;
    }

    ///////////////////////////////////////////////////////////////////
    // Calculation and output                                        //
    ///////////////////////////////////////////////////////////////////
    if (a.convert) {
      if (a.isUt) {
        a.sde1.setJulDay(a.sde1.getJulDay() - a.sde1.getDeltaT());
      }
      System.out.println(printFormatted(a,null));
      return 0;
    }

    boolean outOfTimeRange = false;
    TransitResult tr = null;
    boolean isFirstCalculation = true;

    // Calculate for a time range or for a number of times:
    while(a.count > 0 ||
          (a.enddate != null && !a.back && a.tjde1 < a.jdET2) ||
          (a.enddate != null && a.back && a.tjde1 > a.jdET2)) {

      tr = calcNextTransit(a, tcs);

      // None of the calculations ended normally, so we can stop calculation here:
      if (Double.isNaN(tr.jdET) || tr.jdET == Double.MAX_VALUE || tr.jdET == -Double.MAX_VALUE) {
        if (isFirstCalculation) {
          System.err.println("No transit... ");
        }
        break;
      }
      isFirstCalculation = false;

      // Output calculated data
      a.sde1.setJulDay(tr.jdET);
      if (a.rollover) {
        while (a.to.values[0].doubleValue() < 0) {
          a.to.values[0] = new Double(a.to.values[0].doubleValue() + 360.0);
        }
        if (a.to.values[0].doubleValue() > 360) {
          a.to.values[0] = new Double(a.to.values[0].doubleValue()%360.0);
        }
      }
      System.out.println(printFormatted(a, tr));

      // Initialize next calculation
      a.tjde1 = a.sde1.getJulDay() + (a.back?-MIN_TIME_DIFF:MIN_TIME_DIFF);
      // In the end, we have to adjust the transit value, if we have
      // varying transit values. We have two different cases:
      // a.varyingTransitPoints && a.duplicateTransitPoints:
      //    e.g., -lon0+30 -N...
      //    Take the "successful" transit value to be the new transitVal
      // a.varyingTransitPoints && !a.duplicateTransitPoints:
      //    e.g., -lon0+30 -n...
      //    Add 'to.offset' to each transitVal unconditionally
      for(int tvn = 0; tvn < a.to.values.length; tvn++) {
        if (a.duplicateTransitPoints) {
          a.to.values[tvn] = new Double(tr.transitValue);
        } else if (a.varyingTransitPoints) {
          a.to.values[tvn] = new Double(a.to.values[tvn].doubleValue() + a.to.offset);
        }
      }

      // Note down that we have done one calculation:
      a.count--;
    } // while (in timeRange or cnt > 0)...

    if (outOfTimeRange) {
      System.err.println("\nCalculation(s) exceed(s) available time range.");
    }

#ifdef TEST_ITERATIONS
    if (a.withIterations) {
      System.out.println("Sum of calculation iterations: " + a.v.iterationsSum);
    }
#endif /* TEST_ITERATIONS */
    sw.swe_close();
    return 0;
  }


  TransitResult calcNeighbouringTransits(TransitArguments a, int pl1, int pl2) {
    TransitResult tr = new TransitResult();
    tr.jdET = a.v.jdEnd;

    double[] tValues = null;
    if (a.v.tvOffset == 0) {
      tValues = new double[] {a.v.transitVal};
    } else {
      tValues = new double[] {a.v.transitVal,
                              (a.v.transitVal-a.v.tvOffset+360.)%360.,
                              (a.v.transitVal+a.v.tvOffset)%360.};
    }
    boolean[] doCalc = new boolean[] {true,
                                      a.v.duplicateTransitPoints && a.v.rollover,
                                      a.v.duplicateTransitPoints};

    double jd = 0./0.;
    int errCnt = 0, skipCnt = 0;
    a.v.outOfTimeRange = false;
    boolean skip = false;

    for (int i = 0; i < tValues.length; i++) {
      if (doCalc[i]) {
        try {
          // Skip calculation, if:
          // ONLY the ORDER of the planets is different from previous
          // calculations, AND:
          skip = a.v.tcIndex >= a.idxDuplicates &&
                  // we don't care about a relative transit value of 0 in all cases:
                 (tValues[i] == 0 ||
                  // we don't care about a lon calculation with 180 degrees as well
                  (a.to.idxOffset == 0 && tValues[i] == 180));
          if (skip) {
		// check for isNaN, otherwise the final check for errCnt may
		// not recognize, it didn't find a transit point. We can
		// "probably" remove the assignment all together, as skipping
		// just means skipping, so one shouldn't care...
		// The problem with it arises on the final check for errCnt,
		// which does not take in account the non-error case, where
		// only the skipped value will be available.
//		if (!Double.isNaN(jd)) {
		jd = (a.v.back?-Double.MAX_VALUE:Double.MAX_VALUE);
		tr.jdET = jd;
		skipCnt++;
//		}
          } else { // calculate:
            jd = calcTransit(a.v.tc,tValues[i],a.v.jdStart,tr.jdET,a.v.back);
          }
        } catch (SwissephException swe) {
          errCnt++;
          if ((swe.getType() & SwissephException.OUT_OF_TIME_RANGE) != 0 ||
              (swe.getType() & SwissephException.BEYOND_USER_TIME_LIMIT) != 0) {
            // Hack, SwissEph does not yet return meaningful types in all cases...
            a.v.outOfTimeRange = true;
            // Hack, SwissEph does not yet return meaningful types in all cases...
          } else if (swe.toString().indexOf("not found in the paths of:") > 0) {
            a.v.outOfTimeRange = true;
          } else if (swe.toString().indexOf(" outside ") > 0 &&
                     swe.toString().indexOf(" range ") > 0 &&
                     swe.toString().indexOf(" range ") > swe.toString().indexOf(" outside ")) {
            a.v.outOfTimeRange = true;
          } else if (swe.toString().indexOf(" is restricted to ") > 0) {
            a.v.outOfTimeRange = true;
          } else if ((swe.getType() & SwissephException.BEYOND_USER_TIME_LIMIT) != 0) {
            jd = (a.v.back?-Double.MAX_VALUE:Double.MAX_VALUE);
          } else {
            System.err.println("ERROR: " + swe.getMessage());
          }
        } // try ... catch ...
#ifdef TEST_ITERATIONS
        if (a.withIterations) {
          if (skip) {
            a.v.iterateString += "/0";
            a.v.iterations += 0;
          } else {
            a.v.iterateString += "/" + sw.getIterateCount();
            a.v.iterations += sw.getIterateCount();
          }
        }
#endif /* TEST_ITERATIONS */
        tr.jdET = (a.v.back?SMath.max(tr.jdET,jd):SMath.min(tr.jdET,jd));
        if (tr.jdET == jd) {
          tr.transitValue = tValues[i];
        }
      }
    } // for ...

    if ((errCnt == 1 && !a.v.duplicateTransitPoints) ||
        (errCnt == 3 && a.v.duplicateTransitPoints)) { // No more transit points possible
      tr.jdET = (a.v.back?-Double.MAX_VALUE:Double.MAX_VALUE);
    }

    return tr;
  }


  double calcTransit(TransitCalculator tc,
                     double val,
                     double jdStart,
                     double jdEnd,
                     boolean back)
      throws SwissephException {
    tc.setOffset(val);
    try {
      double et = sw.getTransitET(tc, jdStart, back, jdEnd);
      return et;
    } catch (SwissephException swe) {
      throw swe;
    }
  }


  // Return a String of each of the planets combinations as a
  // sequence of the two characters meaning both planets
  String getPlanetCombinations(String planets1, String planets2) {
    String pls0 = ""; // For 0 deg., 0 deg/day, 180 deg. in long.,
                      // and any value in dist, the order of the
                      // planets does not matter. So skip the
                      // the second entry in this case (e.g.,
                      // planets 24 == planets 42).
                      // We return these planet combinations plus "@"
                      // plus all the other combinations in pls, which
                      // are duplicates of pls0 planet combinations in
                      // the other order. E.g., when pls0 contains the
                      // combination '08', pls will contain '80'.
    String pls = "";
    for(int n1 = 0; n1 < planets1.length(); n1++) {
      for(int n2 = 0; n2 < planets2.length(); n2++) {
        // Skip planet combinations with both planets being the same...
        if (planets1.charAt(n1) != planets2.charAt(n2)) {
          String comb = planets1.charAt(n1) + "" + planets2.charAt(n2);
          String revComb = planets2.charAt(n2) + "" + planets1.charAt(n1);
          int idxR = pls0.indexOf(revComb);
          int idx = pls0.indexOf(comb);
          // Add only, if this planetary combination is not yet inserted
          // (idx < 0 or the position of the idx in the String is odd...)
          if ((idx < 0 || (idx >= 0 && (idx & 0x00000001) == 1)) &&
              (idxR < 0 || (idxR >= 0 && (idxR & 0x00000001) == 1))) {
            // pls0 does not contain this planet combination in ANY order...

            // Create planet combinations that most probably don't start with
            // "02", "03" or similar, otherwise we might get "Out of time
            // range", if the longitude value exceeds the maximum distance
            // between Sun and Mercury, Sun and Venus or Venus and Mercury.
            // Also moon should rather be calculated later, as moon transits
            // require more iterations than others.
            if (comb.indexOf('0') >= 0 || comb.indexOf('1') >= 0 ||
                "23".equals(comb) || "32".equals(comb)) {
              pls0 += comb;
            } else {
              pls0 = comb + pls0;
            }
          } else {
            idxR = pls.indexOf(revComb);
            if (idxR < 0 || (idxR >= 0 && (idxR & 0x00000001) == 1)) {
              if (comb.indexOf('0') >= 0 || comb.indexOf('1') >= 0 ||
                  "23".equals(comb) || "32".equals(comb)) {
                pls += comb;
              } else {
                pls = comb + pls;
              }
            }
          }
        }
      }
    }
    return pls0 + "@" + pls;
  }

  String group(ObjFormatter f, int cnt, String pad) {
    String s = "";
    for(int i=0; i<cnt; i++) {
      s += pad + f.format(i);
    }
    return s.substring(pad.length());
  }

  // A number of e.g. 263.83 will be parsed as 263.83 in 'en' locale, but as
  // 263 only in 'de' locale. This method parses the double in the appropriate
  // locale.
  // The String 's' has to contain the parameter name + "@" + double value,
  // eg. "-lon@234.8903"
  double readLocalizedDouble(String s, String locale) {
    if (s != null && s.length() > 0) {
      String num = s.substring(s.indexOf("@")+1);
      String par = s.substring(0,s.indexOf("@"))+num;

      String dblString = "";
      char lastChar = ' ';
      boolean hasFrac = false;
      boolean hasExp = false;

      for (int i=0; i< num.length(); i++) {
        char ch = num.charAt(i);
        if (!Character.isDigit(ch)) {
          // Has to be a decimal point, an exponent or a sign after an exponent:
          if (ch == numIFracSeparator.charAt(0)) {
            if (hasFrac) {
              return invalidValue(par,s.indexOf('@'));
            }
            if (numIFracSeparator.length() > 1) {
//...
            }
            ch = '.';
            hasFrac = true;
          } else if (ch == '+' || ch == '-') {
            if (i != 0 ||
                (lastChar == 'E' && dblString.length() == 0)) {
              //invalidValue(tv,idx);
              return invalidValue(par,s.indexOf('@'));
            }
          } else if (ch == 'E') { // Exponent
            if (hasExp || dblString.length() == 0) {
              //invalidValue(tv,idx);
              return invalidValue(par,s.indexOf('@'));
            }
            hasExp = true;
          } else {  // Invalid character in String!
            //invalidValue(tv,idx,Nlocale);
            return invalidValue(par,s.indexOf('@'), locale);
          }
        }
        dblString += ch;
        lastChar = ch;
      }
      if (dblString.length() > 0) {
        return Double.valueOf(dblString).doubleValue();
      }
    }

    return 0./0.;
  }

  // Print the date according to the format string given by the -f flag.
  // Planets and (calculation) flag are only needed for calculation of
  // actual positions or speeds when using -fp
  String printFormatted(TransitArguments a, TransitResult tr) {
    String s = "";
    StringBuffer serr = null;
    double[] xx = null;
    int idx, pflags, ret;

    boolean rollover = ((a.iflag & SweConst.SEFLG_TRANSIT_SPEED) == 0) &&
                       ((a.iflag & SweConst.SEFLG_TRANSIT_LONGITUDE) != 0);
    boolean cntIsSet = false;
    int cnt = 0;
    for (int n = 0; n < a.outputFormat.length(); n++) {
      if (n > 0) {
        s += "   ";
      }
      switch ((int)a.outputFormat.charAt(n)) {
        // Print date and time:
        case (int)'n': // output planet name(s):
                       int len = 9;
                       if (a.pls1.indexOf("g") > 0 ||
                           a.pls1.indexOf("c") > 0) {
                         len += 4;
                       }
                       String plNames = sw.swe_get_planet_name(tr.pl1);
                       plNames = (plNames + "             ").substring(0,len);

                       if (tr.pl2 >= 0) {
                         len += 12;
                         if (a.pls2.indexOf("g") > 0 ||
                             a.pls2.indexOf("c") > 0) {
                           len += 4;
                         }
                         plNames += " - " + sw.swe_get_planet_name(tr.pl2);
                       }
                       s += (plNames+"                      ").substring(0,len);
                       break;
        case (int)'d': // output date, read optional decimal places:
                       cnt = 0;
                       for (int i = n+1; i < a.outputFormat.length(); i++) {
                         if (Character.isDigit(a.outputFormat.charAt(i))) {
                           cnt = cnt * 10 +
                                 Character.getNumericValue(a.outputFormat.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       s += jdToDate(a.sde1, a.isUt, a.isLt, cnt);
                       break;
        case (int)'t': // Append 'ET', 'UT', or 'LT':
                       cnt = 0;
                       if (n != 0) {
                         s = s.substring(0,s.length() - 2);
                       }
                       s += (a.isLt?"LT":(a.isUt?"UT":"ET"));
                       break;
        // Print date as julian day number:
        case (int)'j': // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < a.outputFormat.length(); i++) {
                         if (Character.isDigit(a.outputFormat.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(a.outputFormat.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 8; }
                       s += printJD(a.sde1.getJulDay(), a.isUT, a.isLT, cnt);
                       break;
        // Output degree etc.:
        case (int)'v': // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < a.outputFormat.length(); i++) {
                         if (Character.isDigit(a.outputFormat.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(a.outputFormat.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 2; }
                       s += printFloat(tr.transitValue, 3, cnt, (rollover?360.:0.)) + swed.ODEGREE_CHAR+"";
                       if ((a.iflag & SweConst.SEFLG_TRANSIT_SPEED) != 0) {
                         s += "/day";
                       }
                       break;
        // Output actual position or speed:
        case (int)'p': // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < a.outputFormat.length(); i++) {
                         if (Character.isDigit(a.outputFormat.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(a.outputFormat.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 8; }

                       serr = new StringBuffer();
                       xx = new double[6];
                       idx = 0;
                       if ((a.iflag & SweConst.SEFLG_TRANSIT_LATITUDE) != 0) {
                         idx = 1;
                       } else if ((a.iflag & SweConst.SEFLG_TRANSIT_DISTANCE) != 0) {
                         idx = 2;
                       }
                       pflags = a.iflag;
                       if ((a.iflag & SweConst.SEFLG_TRANSIT_SPEED) != 0) {
                         idx += 3;
                         pflags |= SweConst.SEFLG_SPEED;
                       }
                       pflags &= ~(SweConst.SEFLG_TRANSIT_LONGITUDE |
                                   SweConst.SEFLG_TRANSIT_LATITUDE |
                                   SweConst.SEFLG_TRANSIT_DISTANCE |
                                   SweConst.SEFLG_TRANSIT_SPEED);

                       ret = sw.swe_calc(a.sde1.getJulDay(), tr.pl1, pflags, xx, serr);
                       if (ret < 0) {
                         s += serr.toString().substring(0,10)+"...";
                       } else {
                         s += printFloat(xx[idx], 4, cnt, (rollover?360.:0.));
                       }
// Puuuh: we need to calc it from tc!!!???!?!?!?
                       if (tr.pl2 >= 0) {
                         s += "  ";
                         ret = sw.swe_calc(a.sde1.getJulDay(), tr.pl2, pflags, xx, serr);
                         if (ret < 0) {
                           s += serr.toString().substring(0,10)+"...";
                           break;
                         } else {
                           s += printFloat(xx[idx], 3, cnt, (rollover?360.:0.));
                         }
                       }
                       break;
        // 'P' adds output of two planets position (speed) difference:
        case (int)'P': if (tr.pl2 < 0) { break; }
                       // read optional decimal places:
                       cnt = 0;
                       cntIsSet = false;
                       for (int i = n+1; i < a.outputFormat.length(); i++) {
                         if (Character.isDigit(a.outputFormat.charAt(i))) {
                           cntIsSet = true;
                           cnt = cnt * 10 +
                                 Character.getNumericValue(a.outputFormat.charAt(i));
                           n++;
                         } else {
                           break;
                         }
                       }
                       if (!cntIsSet) { cnt = 8; }

                       serr = new StringBuffer();
                       xx = new double[6];
                       idx = 0;
                       if ((a.iflag & SweConst.SEFLG_TRANSIT_LATITUDE) != 0) {
                         idx = 1;
                       } else if ((a.iflag & SweConst.SEFLG_TRANSIT_DISTANCE) != 0) {
                         idx = 2;
                       }
                       pflags = a.iflag;
                       if ((a.iflag & SweConst.SEFLG_TRANSIT_SPEED) != 0) {
                         idx += 3;
                         pflags |= SweConst.SEFLG_SPEED;
                       }
                       pflags &= ~(SweConst.SEFLG_TRANSIT_LONGITUDE |
                                   SweConst.SEFLG_TRANSIT_LATITUDE |
                                   SweConst.SEFLG_TRANSIT_DISTANCE |
                                   SweConst.SEFLG_TRANSIT_SPEED);

                       ret = sw.swe_calc(a.sde1.getJulDay(), tr.pl1, pflags, xx, serr);
                       if (ret < 0) {
                         s += serr.toString().substring(0,10)+"...";
                         break;
                       }
// Puuuh: we need to calc it from tc!!!???!?!?!?
                       double x1 = xx[idx];
                       ret = sw.swe_calc(a.sde1.getJulDay(), tr.pl2, pflags, xx, serr);
                       if (ret < 0) {
                         s += serr.toString().substring(0,10)+"...";
                         break;
                       }
                       x1 += (a.yogaTransit?xx[idx]:-xx[idx]);
                       if (idx >= 1) {
                         s += printFloat(x1, 3, cnt, (rollover?360.:0.));
                       } else {
                         s += printFloat((x1+360.0)%360.0, 3, cnt, (rollover?360.:0.));
                       }
                       break;
      }
    }
    return s;
  }


  TransitOffsets parseTransitValString(String tv) {
    if ("".equals(tv.trim())) { return null; }

    TransitOffsets to = new TransitOffsets();

    int idx = (tv.startsWith("-l")?4:5);
    // save "lon" / "lat" / "dist" for possible error messages:
    String which = tv.substring(1,idx);
    // this is the string with transit values, which can appear in
    // three different forms:
    // -  100
    // -  100+10
    // -  30/45/60.0/90/1.2E2/180
    // All these numbers are localized and may be prefixed by a sign.
    // Base 10 is assumed.
    String vals = tv.substring(idx).toUpperCase();

    boolean numberEnd = false;
    boolean multipleValues = false;
    boolean isExponent = false;

    Vector tmpValues = new Vector();

    String dblString = "";
    char lastChar = '\0';

    for (int i=0; i< vals.length(); i++) {
      char ch = vals.charAt(i);
      if (!Character.isDigit(ch)) {
        // Could be a decimal point, plus or minus for the next number,
        // or  '/' as a separator between different values:
        if (ch == numIFracSeparator.charAt(0)) {
          if (numIFracSeparator.length() > 1) {
//...
          }
          ch = '.';
        } else if (ch == '+' || ch == '-') {
          if (lastChar != 'E' && dblString.length() != 0) {
            numberEnd = true;
          }
          if (lastChar != 'E' && multipleValues && lastChar != '/') {
            invalidValue(tv,idx);
            return null;
          }
        } else if (ch == '/') {
          numberEnd = true;
          multipleValues = true;
        } else if (ch == 'E') { // Exponent
          if (isExponent || dblString.length() == 0) {
            invalidValue(tv,idx);
            return null;
          }
          isExponent = true;
        } else {  // Invalid character in String!
          invalidValue(tv,idx,Nlocale);
          return null;
        }
      }
      if (ch != 'E' && (numberEnd || i == vals.length()-1)) {
        if (i == vals.length()-1) { dblString += ch; }
        if (multipleValues || tmpValues.size() == 0) {
          tmpValues.addElement(Double.valueOf(dblString));
        } else {
          to.offset = Double.valueOf(dblString).doubleValue();
        }
        dblString = "";
        numberEnd = false;
        isExponent = false;
      } else if (ch == 'E' && (numberEnd || i == vals.length()-1)) {
System.err.println(4);
        invalidValue(tv,idx,Nlocale);
        return null;
      } else if (ch != '/') {
        dblString += ch;
      }
      lastChar = ch;
    }
    if (dblString.length() > 0) {
      tmpValues.addElement(Double.valueOf(dblString));
    }

    if (tmpValues.size() == 0) {
      return null;
    }
    to.values = new Double[tmpValues.size()];
    to.values = (Double[])tmpValues.toArray(to.values);

    if (which.equals("lon")) {
      to.idxOffset = 0;
    } else if (which.equals("lat")) {
      to.idxOffset = 1;
    } else if (which.equals("dist")) {
      to.idxOffset = 2;
    }
    return to;
  }

  double parseHour(String s) {
    if (s.equals("now")) {
      java.util.Calendar cal=java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"));
      return cal.get(java.util.Calendar.HOUR_OF_DAY)+
             cal.get(java.util.Calendar.MINUTE)/60.+
             cal.get(java.util.Calendar.SECOND)/3600.;
    }
    // 18:23:45 or shorter versions or other separators
    double[] h=new double[]{0,0,0};
    int field=0;
    boolean isFrac = false;
    double frac = 1;

    for (int n=0; n<s.length();) {
      if (Character.isDigit(s.charAt(n))) {
        if (isFrac) {
          frac*=10;
          h[field]+=((double)((int)s.charAt(n)-(int)'0'))/frac;
        } else {
          h[field]*=10;
          h[field]+=((int)s.charAt(n)-(int)'0');
        }
        n++;
      } else {
        field++;
        if (field==h.length) {
          field--;
          // Maybe decimal separator?
          if (s.charAt(n) == dateFracSeparator.charAt(0)) {
            if (s.indexOf(dateFracSeparator) == n) {
              n += dateFracSeparator.length();
              isFrac = true;
            } else {
              break; // Well, should be an error!
            }
          } else {
            break;
          }
        } else {
          while (n<s.length()-1 && !Character.isDigit(s.charAt(++n)));
        }
      }
    }
    return h[0]+h[1]/60.+h[2]/3600.;
  }


  int invalidValue(String arg, int idx) {
    return invalidValue(arg, idx, null);
  }
  int invalidValue(String arg, int idx, String locale) {
    if (arg.length()==idx) {
      System.err.println("\nMissing argument to parameter '"+arg+
                         "'.\nTry option -h for a syntax description.");
      System.exit(1);
    } else {
      System.err.println("\nInvalid argument (" + arg.substring(idx) +
                         ") to parameter '" + arg.substring(0,idx) +
                         "'.\n" + (locale != null?"Maybe the locale (" +
                         Nlocale + ") requires a different format.\n" +
                         "Also t":"\nT") + "ry option -h for a syntax " +
                         "description.");
      System.exit(1);
    }
    return(1);
  }


  String invalidArgument(String arg, int idx) {
    return invalidArgument(arg, idx, null);
  }
  String invalidArgument(String arg, int idx, String locale) {
    if (arg.length()==idx) {
      return "Missing argument to parameter '" + arg + "'.\nTry option -h " +
             "for a syntax description.";
    } else {
      return "Invalid argument (" + arg.substring(idx) + ") to parameter '" +
             arg.substring(0,idx) + "'.\n" + (locale != null?"Maybe the " +
             "locale (" + Nlocale + ") requires a different format.\n" +
             "Also t":"\nT") + "ry option -h for a syntax description.";
    }
  }


  String checkLocale(String locale, String defLocale) {
    if ("".equals(defLocale)) {
      defLocale = Locale.getDefault().toString();
    }
    
    if (locale == null) {
      return defLocale;
    }
    if ("".equals(locale)) {
      return Locale.getDefault().toString();
    }
    for(int n=0; n < locs.length; n++) {
      if (locs[n].toString().equals(locale)) {
        return locale;
      }
    }
System.err.println("Warning: Locale '" + locale + "' not found, using default locale '" + defLocale + "'");
System.err.println("Use option -locales to list all available Locales.");
    return defLocale;
  }


  /**
  * Returns date + time in localized form from a JDET in a SweDate
  * object
  */
  String jdToDate(SweDate sdET, boolean printUT, boolean printLocalTime, int decPlaces) {
    if (decPlaces > 18) { decPlaces = 18; }

    SweDate sx = new SweDate(
                        sdET.getJulDay()-
                        (printUT?sdET.getDeltaT():0)+
                        (printLocalTime?tzOffset:0)+
                        0.5/24./3600/SMath.pow(10,decPlaces));

    // sx.getDate() will round a second time. We have to inhibit
    // this be cutting of the spare decimal places beyond the
    // the relevant numbers

    double hour=sx.getHour();
    double mseconds=((hour*3600.)%1.);

    String s1 = "";
    String s2 = "";

    String s = null;
    String pat = dof.toPattern();
    // sx.getDate() will round by itself, but rounding is not allowed
    // to occur anymore. We only need an accuracy to a second for
    // getDate(), so let us cut off the rest here already!
    sx.setJulDay(sx.getJulDay() - mseconds/24./3600. + 0.5/24./3600.);

    if (sx.getYear() == 0) {
      // DateFormat.format() will not allow a year "0"...
      // Hack: We take the normal pattern and replace 0001 by 0000
      s = dof.format(sx.getDate(0));
      int idxy = el.getPatternLastIdx(pat, "yyyy", dof);

      s = s.substring(0,idxy+2) +
          s.substring(idxy,idxy+1) +
          s.substring(idxy+3);
    } else if (sx.getYear() < 0) {
      // We add one year, as the date formatter skips year 0
      sx.setYear(sx.getYear()+1);

      int idx = pat.indexOf("y");
      SimpleDateFormat dfm = (SimpleDateFormat)dof.clone();
      // If the separator between day, month and year is '-' we have to
      // (should?) distinguish it from the '-' of the year, so we add a
      // space before the year in this case. See locales da / da_DK /
      // es_BO / es_CL / es_HN / es_NI / es_PR / es_SV / nl / nl_NL /
      // pt / pt_PT.
      String sep = (idx>0 && pat.charAt(idx-1)=='-'?" ":"");
      pat = pat.substring(0,idx) + sep + "'-'" + pat.substring(idx);
      dfm.applyPattern(pat);
      s = dfm.format(sx.getDate(0));
    } else {
      s = dof.format(sx.getDate(0));
    }

    if (decPlaces > 0) {
      secondsIdx = el.getPatternLastIdx(pat, "ss", dof);
      s1 = s.substring(0,secondsIdx + 1/* +idxAdd */);
      s2 = s.substring(secondsIdx + 1/* +idxAdd */);
      s = dateFracSeparator;
      for (int i = decPlaces; i > 0; i--) {
        mseconds = (mseconds * 10) % 10;
        s += dnof.format((int)mseconds);
      }
    }

    return s1 + s + s2;
  }

  // Prints a floatingpoint number internationalized and
  // with correct rounding to a given number of decimal places
  String printFloat(double val, int width, int decPlaces) {
    return printFloat(val, width, decPlaces, 0);
  }
  String printFloat(double val, int width, int decPlaces, double wrapAt) {
    if (decPlaces > 18) { decPlaces = 18; }
    // Well, could be another width, if necessary...
    if (width > 61) { width = 61; }
    String s = "                                                            ";
    if (val < 0) {
      val = -val;
      s += "-";
    }

    val += 0.5/SMath.pow(10,decPlaces);

    if (wrapAt != 0 && val >= wrapAt) {
      val -= wrapAt;
    }

    int len = (String.valueOf((int)val)).length();
    s += nnof.format((int)val);
    s = s.substring(SMath.max(len,s.length()-width));

    if (decPlaces > 0) {
      s += numOFracSeparator;
      double parts = val - (int)val;
      for (int i = decPlaces; i > 0; i--) {
        parts = (parts * 10) % 10;
        s += nnof.format((int)parts);
      }
    }

    return s;
  }


  String printJD(double jd, boolean printUT, boolean printLocalTime, int decPlaces) {
    if (decPlaces > 18) { decPlaces = 18; }

    SweDate sx = new SweDate(
                        jd-
                        (printUT?SweDate.getDeltaT(jd):0)+
                        (printLocalTime?tzOffset:0)+
                        0.5/SMath.pow(10,decPlaces));
    jd = sx.getJulDay();

    String s = nnof.format((int)jd);

    if (decPlaces > 0) {
      s += numOFracSeparator;
      double parts = SMath.abs(jd - (int)jd);
      for (int i = decPlaces; i > 0; i--) {
        parts = (parts * 10) % 10;
        s += nnof.format((int)parts);
      }
    }

    return s;
  }


  String doubleToDMS(double d) {
    int deg=(int)d;
    int min=(int)((d%1.)*60.);
    int sec=(int)(((d*60.)%1.)*60.);
    return ((deg<10?" ":"")+nnof.format(deg)+swed.ODEGREE_CHAR+""+
            (min<10?nnof.format(0):"")+nnof.format(min)+"'"+
            (sec<10?nnof.format(0):"")+nnof.format(sec)+"\"");
  }


  // Returns ET date from the (localized) String dt
  SweDate makeDate(String dt, double hour, boolean ut, boolean localtime, String locString) {
    if (dt==null) { return null; }
    SweDate sd = new SweDate();
    boolean gregflag = SweDate.SE_GREG_CAL;

    if (dt.charAt(0) == 'j') {   /* parse a julian day number */
      double tjd = 0.;
      tjd = readLocalizedDouble("-b' or '-B@" + dt.substring(1), locString);
      if (ut) {
        // The JD is meant to represent a UT date,
        // but this method is to return ET:
        tjd += SweDate.getDeltaT(tjd);
        // Substract timezone offset, if input was localtime:
        if (localtime) {
          tjd -= tzOffset;
        }
      }
      if (tjd < sd.getGregorianChange()) {
        gregflag = SweDate.SE_JUL_CAL;
      } else {
        gregflag = SweDate.SE_GREG_CAL;
      }
      if (dt.indexOf("jul") >= 0) {
        gregflag = SweDate.SE_JUL_CAL;
      } else if (dt.indexOf("greg") >= 0) {
        gregflag = SweDate.SE_GREG_CAL;
      }
      sd.setJulDay(tjd);
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay number!
    } else if (dt.equals("today")) {
      java.util.Calendar cal=java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"));
      dt=dif.format(cal.getTime());
      return makeDate(dt, hour, ut, localtime, locString);
    } else { // Parse a date string
      // We just parse the date as a sequence of three integers
      // How these three numbers will be interpreted (y-m-d or d-m-y
      // or whatever) will be determined from the locale pattern.
      int ints[] = new int[]{0, 0, 0};
      int jday, jmon, jyear;
      int i=0, n=0;
      boolean neg=false;
      try {
        for (; n<3; n++) {
          ints[n] = 0;
          neg=(dt.charAt(i)=='-');
          if (neg) { i++; }
          while (Character.isDigit(dt.charAt(i))) {
            ints[n]=ints[n]*10+Character.digit(dt.charAt(i++),10);
          }
          if (neg) { ints[n]=-ints[n]; }
          int nd = 0; // no digit
          while (!Character.isDigit(dt.charAt(i))) { nd++; i++; }
          if (dt.charAt(i-1)=='-' && nd > 1) { i--; }
        }
      } catch (StringIndexOutOfBoundsException siobe) {
        if (neg) { ints[n]=-ints[n]; }
      } catch (ArrayIndexOutOfBoundsException aob) {
      }
      // order the integers into date parts according to the locale:
      String pat = dif.toPattern().toLowerCase();
      int d = pat.indexOf("d");
      int m = pat.indexOf("m");
      int y = pat.indexOf("y");
      if (m < d && m < y) {
        jmon = ints[0];
        if (y < d) {  // m-y-d
          jyear = ints[1]; jday = ints[2];
        } else {      // m-d-y
          jday = ints[1]; jyear = ints[2];
        }
      } else if (y < d && y < m) {
        jyear = ints[0];
        if (m < d) {  // y-m-d
          jmon = ints[1]; jday = ints[2];
        } else {      // y-d-m
          jday = ints[1]; jmon = ints[2];
        }
      } else {
        jday = ints[0];
        if (m < y) {  // d-m-y
          jmon = ints[1]; jyear = ints[2];
        } else {      // d-y-m
          jyear = ints[1]; jmon = ints[2];
        }
      }

      sd.setDate(jyear,jmon,jday,hour,SweDate.SE_GREG_CAL);
      if (ut) { // We need ET in this method...
        double jdx = sd.getJulDay() + sd.getDeltaT();
        // Substract timezone offset, if input was localtime:
        if (localtime) {
          jdx -= tzOffset;
        }
        sd.setJulDay(jdx);
      }
      if (sd.getJulDay() < sd.getGregorianChange()) {
        gregflag = SweDate.SE_JUL_CAL;
      } else {
        gregflag = SweDate.SE_GREG_CAL;
      }
      if (dt.indexOf("jul") >= 0) {
        gregflag = SweDate.SE_JUL_CAL;
      } else if (dt.indexOf("greg") >= 0) {
        gregflag = SweDate.SE_GREG_CAL;
      }
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_DATE); // Keep Date!
    }
    return sd;
  }

  /* make_ephemeris_path().
   * ephemeris path includes
   *   current working directory
   *   + program directory
   *   + default path from swephexp.h on current drive
   *   +                              on program drive
   *   +                              on drive C:
   */
  int make_ephemeris_path(int iflag, String argv0) {
    String path="", s="";
    int sp;
    String dirglue = swed.DIR_GLUE;
    int pathlen=0;
    /* moshier needs no ephemeris path */
    if ((iflag & SweConst.SEFLG_MOSEPH)!=0)
      return SweConst.OK;
    /* current working directory */
    path="."+SwissData.PATH_SEPARATOR;
    /* program directory */
    sp = argv0.lastIndexOf(dirglue);
    if (sp >= 0) {
      pathlen = sp;
      if (path.length() + pathlen < SwissData.AS_MAXCH-1) {
        s=argv0.substring(0,pathlen);
        path+=s+SwissData.PATH_SEPARATOR;
      }
    }
    if (path.length() + pathlen < SwissData.AS_MAXCH-1)
      path+=SweConst.SE_EPHE_PATH;
    return SweConst.OK;
  }

  int letter_to_ipl(char letter) {
//    if (letter >= '0' && letter <= '9')
//      return (int)letter - '0' + SweConst.SE_SUN;
//    switch ((int)letter) {
//      case (int)'m': return SweConst.SE_MEAN_NODE;
//      case (int)'t': return SweConst.SE_TRUE_NODE;
//      case (int)'A': return SweConst.SE_MEAN_APOG;
//      case (int)'B': return SweConst.SE_OSCU_APOG;
//      case (int)'D': return SweConst.SE_CHIRON;
//      case (int)'E': return SweConst.SE_PHOLUS;
//      case (int)'F': return SweConst.SE_CERES;
//      case (int)'G': return SweConst.SE_PALLAS;
//      case (int)'H': return SweConst.SE_JUNO;
//      case (int)'I': return SweConst.SE_VESTA;
//case (int)'R': return SweConst.SE_ISIS;
//      case (int)'c': return SweConst.SE_INTP_APOG;
//      case (int)'g': return SweConst.SE_INTP_PERG;
//    }
//
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
      case (int)'m': return SweConst.SE_MEAN_NODE;
      case (int)'c': return SweConst.SE_INTP_APOG;
      case (int)'g': return SweConst.SE_INTP_PERG;
//      case (int)'n':
//      case (int)'o': return SweConst.SE_ECL_NUT;
      case (int)'t': return SweConst.SE_TRUE_NODE;
#ifndef ASTROLOGY
//      case (int)'f': return SweConst.SE_FIXSTAR;
#endif /* ASTROLOGY */
      case (int)'w': return SweConst.SE_WALDEMATH;
//      case 'e': /* swetest: a line of labels */
//      case 'q': /* swetest: delta t */
//      case 's': /* swetest: an asteroid, with number given in -xs[number] */
//      case 'z': /* swetest: a fictitious body, number given in -xz[number] */
//      case 'd': /* swetest: default (main) factors 0123456789mtABC */
//      case 'p': /* swetest: main factors ('d') plus main asteroids DEFGHI */
//      case 'h': /* swetest: fictitious factors JKLMNOPQRSTUVWXYZw */
//      case 'a': /* swetest: all factors, like 'p'+'h' */
//        return -1;
    }
    return -1;
  }

  String limitLineLength(String line, int maxLen, String nlPad) {
    String s = "";
    maxLen -= nlPad.length();

    while(line.length() > 0) {
      if (line.length() > maxLen) {
        String t = line.substring(0, maxLen+1);
        for(int idx = t.length() - 1; idx >= 0; idx--) {
          if (Character.isWhitespace(t.charAt(idx))) {
            t = t.substring(0, idx);
            line = line.substring(idx + 1);
            break;
          }
        }

        if (t.length() > maxLen) { // No space character found
          // try to find any non-digit or letter
          for(int idx = t.length() - 1; idx >= 0; idx--) {
            if (Character.isLetterOrDigit(t.charAt(idx))) {
              t = t.substring(0, idx);
              line = line.substring(idx);
              break;
            }
          }
        }

        if (t.length() > maxLen) { // All letters or digits,
          // split at maxLen
          t = line.substring(0, maxLen);
          line = line.substring(maxLen);
        }
        s += "\n" + nlPad + t;
      } else {
        s += "\n" + nlPad + line;
        break;
      }
    }
    return s.substring(("\n"+nlPad).length());
  }

  String getPlanetNames(String pls, String pad) {
    String s = "";
    for(int n = 0; n < pls.length(); n++) {
      int pl = letter_to_ipl(pls.charAt(n));
      s += pad + sw.swe_get_planet_name(pl);
    }
    return s.substring(pad.length());
  }

  TransitArguments parseArgs(String argv[])
      throws IllegalArgumentException {
    TransitArguments a = new TransitArguments();
    /*
     * parse command line
    */
    for (int i = 0; i < argv.length; i++) {
      if (argv[i].equals("-hdate")) {
        System.out.println(infodate);
        sw.swe_close();
        return(null);
      } else if (argv[i].equals("-hex")) {
        if (infoexamples == null) {
          initHelpTexts();
        }
        System.out.println(infoexamples);
        sw.swe_close();
        return(null);
      } else if (argv[i].equals("-h") ||
                 argv[i].equals("--help") ||
                 argv[i].equals("-?") ||
                 argv[i].equals("/?")) {
        System.out.println(infocmd0);
        System.out.println(infocmd1);
        if (infoexamples == null) {
          initHelpTexts();
        }
        System.out.println(infocmd2);
        System.out.println(infoexamples);
        sw.swe_close();
        return(null);
      } else if (argv[i].equals("-head") ||
        argv[i].equals("-q")) {
        a.withHeader = false;
      } else if (argv[i].equals("-r")) {
        a.back = true;
      } else if (argv[i].startsWith("-lon") ||
                 argv[i].startsWith("-lat") ||
                 argv[i].startsWith("-dist") ||
                 argv[i].startsWith("+lon") ||
                 argv[i].startsWith("+lat") ||
                 argv[i].startsWith("+dist")) {
        if (!"".equals(a.transitValString)) {
          throw new IllegalArgumentException(
                  "Only one of -lon / -lat / -dist / +lon / +lat / +dist\n" +
                  "may be given. Use the -h option for more information.");
        }

        a.yogaTransit = (argv[i].charAt(0) == '+');
        argv[i] = "-" + argv[i].substring(1);

        a.transitValString = argv[i];
        int idx = (argv[i].charAt(1) == 'd'?5:4);
      } else if (argv[i].startsWith("-topo")) {
        a.iflag |= SweConst.SEFLG_TOPOCTR;
        a.topoS=argv[i].substring(5);
      } else if (argv[i].equals("-hel") ||
                 argv[i].equals("-helio")) {
        a.helio = true;
        a.iflag |= SweConst.SEFLG_HELCTR;
        a.iflag |= SweConst.SEFLG_NOABERR;
        a.iflag |= SweConst.SEFLG_NOGDEFL;
      } else if (argv[i].startsWith("-sid")) {
        try {
          a.sidmode=Integer.parseInt(argv[i].substring(4));
          if (a.sidmode<0 || a.sidmode>20) {
            throw new IllegalArgumentException(invalidArgument(argv[i],4));
          }
        } catch (NumberFormatException nf) {
          throw new IllegalArgumentException(invalidArgument(argv[i],4));
        }
#ifndef NO_JPL
      } else if (argv[i].startsWith("-ejpl")) {
        a.whicheph = SweConst.SEFLG_JPLEPH;
        if (argv[i].length() > 5) {
          a.fname = argv[i].substring(5);
        }
#endif /* NO_JPL */
      } else if (argv[i].equals("-eswe")) {
        a.whicheph = SweConst.SEFLG_SWIEPH;
#ifndef NO_MOSHIER
      } else if (argv[i].equals("-emos")) {
        a.whicheph = SweConst.SEFLG_MOSEPH;
#endif /* NO_MOSHIER */
      } else if (argv[i].startsWith("-edir")) {
        if (argv[i].length()>5) {
          a.ephepath=argv[i].substring(5);
        }
      } else if (argv[i].startsWith("-ut") ||
                 argv[i].startsWith("-et") ||
                 argv[i].startsWith("-lt") ||
                 argv[i].startsWith("-t")) {
        int len=(argv[i].startsWith("-t")?2:3);
        a.isLt=argv[i].startsWith("-lt");
        a.isUt=argv[i].startsWith("-ut") || a.isLt;
        if (argv[i].length()>len) {
          a.sBeginhour = argv[i].substring(len);
        }
      } else if (argv[i].startsWith("-UT") ||
                 argv[i].startsWith("-ET") ||
                 argv[i].startsWith("-LT") ||
                 argv[i].startsWith("-T")) {
        int len=(argv[i].startsWith("-T")?2:3);
        a.isLT=argv[i].startsWith("-LT");
        a.isUT=argv[i].startsWith("-UT") || a.isLT;
        a.sEndhour = argv[i].substring(len);
        a.endTimeIsSet = true;
      } else if (argv[i].equals("-s")) {
        a.calcSpeed = true;
      } else if (argv[i].startsWith("-b") ||
                 argv[i].startsWith("-j")) {
        if (argv[i].length()<3) {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        } else {
          boolean wob = (argv[i].startsWith("-j"));
          if (a.begindate == null) {
            a.begindate = (wob?"j":"") + argv[i].substring(2);
          } else if (a.enddate == null) {
            a.enddate = (wob?"j":"") + argv[i].substring(2);
          } else {
            System.err.println("Invalid parameter combination:\n"+
                         "Excess -b option."+
                         "\nUse option '-h' for additional help.");
          }
        }
      } else if (argv[i].startsWith("-B") ||
                 argv[i].startsWith("-J")) {
        if (argv[i].length()<3) {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        } else {
          boolean wob = (argv[i].startsWith("-J"));
          a.enddate = (wob?"j":"") + argv[i].substring(2);
          a.duplicateTransitPoints = true; // Well, only if varyingTransitPoint == true;
        }
      } else if (argv[i].startsWith("-p")) {
        if (argv[i].length() >= 3) {
          a.pls1 = argv[i].substring(2);
        } else {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
      } else if (argv[i].startsWith("-P")) {
        if (argv[i].length() >= 3) {
          a.pls2 = argv[i].substring(2);
        } else {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
      } else if (argv[i].startsWith("-f")) {
        if (argv[i].length() > 2) {
          a.outputFormat = argv[i].substring(2);
          a.outputFormatIsSet = true;
        } else {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
      } else if (argv[i].equals("-cv")) {
        a.convert = true;
      } else if (argv[i].equals("-locales")) {
        String[] locs = el.getLocales();
        for (int n=0; n<locs.length; n++) {
          System.out.println(locs[n]);
        }
        return null;
      } else if (argv[i].startsWith("-loc")) {
        locale = argv[i].substring(4);
        int idx24 = locale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          locale = locale.substring(0,idx24) +
                   locale.substring(idx24+2);
        }
        if (locale.equals("swiss")) {
          locale = "de_DE";
          Nlocale = "en_US";
          nlocale = "en_US";
        }
      } else if (argv[i].startsWith("-iloc")) {
        Nlocale = argv[i].substring(5);
        int idx24 = Nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Nlocale = Nlocale.substring(0,idx24) +
                    Nlocale.substring(idx24+2);
        }
        Dlocale = Nlocale;
      } else if (argv[i].startsWith("-Dloc")) {
        Dlocale = argv[i].substring(5);
        int idx24 = Dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Dlocale = Dlocale.substring(0,idx24) +
                    Dlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-Nloc")) {
        Nlocale = argv[i].substring(5);
        int idx24 = Nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          Nlocale = Nlocale.substring(0,idx24) +
                    Nlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-oloc")) {
        dlocale = argv[i].substring(5);
        int idx24 = dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          dlocale = dlocale.substring(0,idx24) +
                    dlocale.substring(idx24+2);
        }
        nlocale = dlocale;
      } else if (argv[i].startsWith("-dloc")) {
        dlocale = argv[i].substring(5);
        int idx24 = dlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          dlocale = dlocale.substring(0,idx24) +
                    dlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-nloc")) {
        nlocale = argv[i].substring(5);
        int idx24 = nlocale.indexOf("24");
        if (idx24 >= 0) {
          force24hSystem = true;
          nlocale = nlocale.substring(0,idx24) +
                    nlocale.substring(idx24+2);
        }
      } else if (argv[i].startsWith("-n")) {
        try {
          a.count = Integer.parseInt(argv[i].substring(2));
          a.countIsSet = true;
        } catch (NumberFormatException nf) {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
      } else if (argv[i].startsWith("-N")) {
        try {
          a.count = Integer.parseInt(argv[i].substring(2));
          a.duplicateTransitPoints = true; // Well, only if varyingTransitPoint == true;
        } catch (NumberFormatException nf) {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
#ifdef EXTPRECISION
      } else if (argv[i].startsWith("-x")) {
        try {
          a.extPrecision = Double.parseDouble(argv[i].substring(2));
        } catch (NumberFormatException nf) {
          throw new IllegalArgumentException(invalidArgument(argv[i],2));
        }
#endif /* EXTPRECISION */
#ifdef TEST_ITERATIONS
      } else if (argv[i].startsWith("-iter")) {
        a.withIterations = true;
#endif /* TEST_ITERATIONS */
      } else {
        throw new IllegalArgumentException("Unrecognized option '" + argv[i]
                + "'");
      }
    }
    return initArgs(a);
  }

  TransitArguments initArgs(TransitArguments a) {
    // Internationalization:
    // Actually, we have five locales:
    // -loc          (var. locale) locale for ALL parsing and formatting
    //                      is overridden by the more specific locales
    // -Dloc (-iloc) (var. Dlocale) locale to parse dates
    // -Nloc (-iloc) (var. Nlocale) locale to parse numbers
    // -dloc (-oloc) (var. dlocale) locale to format dates
    // -nloc (-oloc) (var. nlocale) locale to format numbers
    locale = checkLocale(locale, "");
    Dlocale = checkLocale(Dlocale, locale);
    Nlocale = checkLocale(Nlocale, locale);
    dlocale = checkLocale(dlocale, locale);
    nlocale = checkLocale(nlocale, locale);

    dif = el.createLocDateTimeFormatter(Dlocale, true); // DateInputFormat
    dof = el.createLocDateTimeFormatter(dlocale, force24hSystem); // DateOutputFormat
    dnof = NumberFormat.getInstance(el.getLocale(nlocale)); // NumberOutputFormat
    dateFracSeparator = el.getDecimalSeparator(dnof);
    nnif = NumberFormat.getInstance(el.getLocale(Nlocale));
    nnif.setGroupingUsed(false);
    numIFracSeparator = el.getDecimalSeparator(nnif);
    nnof = NumberFormat.getInstance(el.getLocale(nlocale));
    nnof.setGroupingUsed(false);
    nnof.setMaximumFractionDigits(12);
    numOFracSeparator = el.getDecimalSeparator(nnof);
    secondsIdx = el.getPatternLastIdx(dof.toPattern(), "s", dof); // No input with parts of seconds?

    a.to = parseTransitValString(a.transitValString);
    if (a.to == null) {
      throw new IllegalArgumentException(
              "Specify a longitude, latitude or distance value to be " +
              "transited\nwith the -lon, -lat, -dist, +lon, +lat or +dist " +
              "option!\nUse the -h option for more information.");
    }

    a.beginhour = parseHour(a.sBeginhour);
    if (a.endTimeIsSet) {
      a.endhour = parseHour(a.sEndhour);
    } else {
      a.endhour = a.beginhour;
      a.isUT = a.isUt;
      a.isLT = a.isLt;
    }

    // Complete output format string:
    String format = a.outputFormat;
    boolean appendOutputFormat = (a.outputFormat.startsWith("+"));


    // varyingTransitPoints is only meaningful, when we have changing values:
    a.varyingTransitPoints = (a.to.offset != 0);
    a.duplicateTransitPoints &= a.varyingTransitPoints;


    if (!a.outputFormatIsSet || appendOutputFormat) {         // Use default
      if (a.convert) {
        a.outputFormat = "jdt";
      } else if ((a.varyingTransitPoints || // Varying values
          a.to.values.length > 1)) {           // Multiple transit values given
        a.outputFormat = "vdt";
      } else {
        a.outputFormat = "dt";
      }
    }
    if (appendOutputFormat) {
      a.outputFormat += format.substring(1);
    }

    // Initialize SweDate objects to the given times and dates:
    a.sde1 = makeDate(a.begindate, a.beginhour, a.isUt, a.isLt, Dlocale);
    a.sde2 = makeDate(a.enddate, a.endhour, a.isUT, a.isLT, Dlocale);

    if (a.convert) {
      String invalids = "npP";
      for(int r=0;r<invalids.length();r++) {
        char ch = invalids.charAt(r);
        int idx = a.outputFormat.indexOf(ch);
        while (idx >= 0) {
          System.out.println("Info: ignoring output format character '" + ch + "' on date conversions.");
          a.outputFormat = a.outputFormat.substring(0,idx) + a.outputFormat.substring(idx+1);
          idx = a.outputFormat.indexOf(ch);
        }
      }
      // Check parameters:
      if (a.begindate == null) {
        throw new IllegalArgumentException(
                "Specify a date with the -b option!\n"+
                "Use the -h option for more information.");
      }
      // Allow a restricted set options only:
      if (a.enddate != null || !"".equals(a.sEndhour)) {
        System.err.println("Warning: Ignoring a second date on -cv option.");
      }
#ifdef TEST_ITERATIONS
      if (a.withIterations) {
        System.err.println("Warning: Ignoring option -iter with -cv.");
      }
#endif /* TEST_ITERATIONS */
      if (a.back) {
        System.err.println("Warning: Ignoring option -r with -cv.");
      }
      if (a.calcSpeed) {
        System.err.println("Warning: Ignoring option -s with -cv.");
      }
      if (a.sidmode != -1) {
        System.err.println("Warning: Ignoring option -sid... with -cv.");
      }
      if (a.pl1 != -2 || a.pl2 != -2) {
        System.err.println("Warning: Ignoring options -p... / -P... options with -cv.");
      }
      if (a.countIsSet) {
        System.err.println("Warning: Ignoring options -n... / -N... with -cv.");
      }
// ...
      return a;
    }

    // Optimization to make "out of time range" errors less probable...
    if (a.to.values.length > 1 && a.to.idxOffset == 0) {
      bubbleSort(a.to.values);
    }



    // If -topo is given, read topographic values (lon / lat / height)
    if (a.topoS != null) {
      if (a.topoS.length() == 0) { // Default: ZÅrich
        a.topoS = "8" + numIFracSeparator + "55;47" + numIFracSeparator + "38;400";
      }
      try {
        // Read number of fields and normalize fields to be separated by ';'
        int cnt=0;
        for(int k=0; k<a.topoS.length(); k++) {
          if (!Character.isDigit(a.topoS.charAt(k))) {
            char ch = a.topoS.charAt(k);
            if (ch != '-' && ch != '+') {
              if (!numIFracSeparator.equals(""+ch)) { // Well, can be a string, probably????
                a.topoS = a.topoS.substring(0,k) + ";" + a.topoS.substring(k+1);
                cnt++;
              }
            }
          }
        }
        // We need exactly three fields, meaning two field separators:
        if (cnt!=2) {
          throw new IllegalArgumentException(
                  invalidArgument("-topo"+a.topoS,5,Nlocale));
        }
        // Read field values:
        String ts = a.topoS;
        a.top_long=nnif.parse(ts.substring(0,ts.indexOf(';'))).doubleValue();
        ts=ts.substring(ts.indexOf(';')+1);
        a.top_lat=nnif.parse(ts.substring(0,ts.indexOf(';'))).doubleValue();
        ts=ts.substring(ts.indexOf(';')+1);
        a.top_elev=nnif.parse(ts).doubleValue();
      } catch (StringIndexOutOfBoundsException se) {
      } catch (Exception e) {
        throw new IllegalArgumentException(
                invalidArgument("-topo"+a.topoS,5,Nlocale));
      }
    }


    // Interpret and check parameters and parameter combinations:

    // Set ephemeris data file paths:
    a.iflag = (a.iflag & ~SweConst.SEFLG_EPHMASK) | a.whicheph;
    String curDir = System.getProperties().getProperty("user.dir");
    if (a.ephepath.length() > 0) {
      sw.swe_set_ephe_path(a.ephepath);
    } else if (make_ephemeris_path(a.iflag, curDir) == SweConst.ERR) {
      a.iflag = (a.iflag & ~SweConst.SEFLG_EPHMASK) | SweConst.SEFLG_MOSEPH;
      a.whicheph = SweConst.SEFLG_MOSEPH;
    }
#ifndef NO_JPL
    if ((a.whicheph & SweConst.SEFLG_JPLEPH)!=0) {
      sw.swe_set_jpl_file(a.fname);
    }
#endif /* NO_JPL */


    // Check for required parameters
    if (a.pls1 == null) {
      throw new IllegalArgumentException(
              "Specify one or more planets with the -p option!\n"+
              "Use the -h option for more information.");
    }
    if (a.begindate == null) {
      throw new IllegalArgumentException(
              "Specify a (starting) date with the -b option!\n"+
              "Use the -h option for more information.");
    }
    if (a.to.offset != 0 && a.to.values.length != 1) {
      throw new IllegalArgumentException(
              "Specify one or more transit values separated by\n"+
              "the \"/\" character, OR(!!!) give an offset to a\n"+
              "start value, but not both.\n"+
              "Examples:\n"+
              "  ... -lon30+10 ...\n"+
              "  ... -lat0/-0.3/0.3 ...\n"+
              "Use the -h option for more information.");
    }

    // A second time gets set equal to the first time, if it is not given
    // on the command line:
    if (Double.isInfinite(a.endhour)) {
      a.endhour = a.beginhour;
      a.isUT = a.isUt;
      a.isLT = a.isLt;
    }


    // Add the transit flags to the calculation flags:
    a.cflag = a.iflag;
    if (a.to.idxOffset==0) {
      a.iflag |= SweConst.SEFLG_TRANSIT_LONGITUDE;
    } else if (a.to.idxOffset==1) {
      a.iflag |= SweConst.SEFLG_TRANSIT_LATITUDE;
    } else if (a.to.idxOffset==2) {
      a.iflag |= SweConst.SEFLG_TRANSIT_DISTANCE;
    }
    if (a.calcSpeed) {
      a.iflag |= SweConst.SEFLG_TRANSIT_SPEED;
      a.to.idxOffset += 3;
    }
    if (a.yogaTransit) {
      a.iflag |= SweConst.SEFLG_YOGA_TRANSIT;
    }

    // Swap dates if necessary:
    if (a.sde2 != null &&
        ((!a.back && a.sde1.getJulDay() > a.sde2.getJulDay()) ||
        (a.back && a.sde1.getJulDay() < a.sde2.getJulDay()))) {
      SweDate tmpdate = a.sde1;
      a.sde1 = a.sde2;
      a.sde2 = tmpdate;
    }


    // Multiple planets?
    a.mp1 = (a.pls1.length() > 1);
    a.mp2 = (a.pls2 !=null && a.pls2.length() > 1);

    // Force output of planet names, if multiple planets are requested
    if (a.mp1 || a.mp2) {
      if (a.outputFormat.indexOf("n") < 0 &&
          (!a.outputFormatIsSet || appendOutputFormat)) {
        a.outputFormat = "n" + a.outputFormat;
      }
    }


    // Checks...
    // Parameter combinations:
    //   -p... -b... +-lon/lat/dist [other options]
    //   -p... -P... -b... +-lon/lat/dist [other options]
    //   -p... -b... -B... +-lon/lat/dist [other options]
    //   -p... -P... -b... -B... +-lon/lat/dist [other options]

    if (a.pls2 == null && a.outputFormat.indexOf('P') >= 0) {
      System.out.println("Info: ignoring output format character 'P' on non-relative transits.\n");
    }

    boolean invalidComb=false;
    if ((a.enddate != null && a.countIsSet)) { // 2 times => !a.countIsSet
      invalidComb=true;
    }
    if (invalidComb) {
      throw new IllegalArgumentException(
              "Invalid parameter combination.\n" + infocmd1 + "\n" +
              "Use option '-h' for additional help.");
    }

    // Planets:
    for(int n = 0; n < a.pls1.length(); n++) {
      char p = a.pls1.charAt(n);
      if (letter_to_ipl(p) == -1) {
        throw new IllegalArgumentException(
                "Unsupported planet " +
                (Character.isDigit(p)?"number":"character") + ": '" + p +
                "'.\nCheck for valid planets with the '-h' option.");
      }
    }
    if (a.pls2 != null) {
      for(int n = 0; n < a.pls2.length(); n++) {
        char p = a.pls2.charAt(n);
        if (letter_to_ipl(p) == -1) {
          throw new IllegalArgumentException(
                  "Unsupported planet " +
                  (Character.isDigit(p)?"number":"character") + ": '" + p +
                  "'.\nCheck for valid planets with the '-h' option.");
        }
      }
    }

    if (a.yogaTransit && a.pls2 == null) {
      throw new IllegalArgumentException(
              "Yoga Transits can only be computed over two planets.\n" +
              "Use '-h' option for valid parameter combinations.");
    }

    sw.swe_set_topo(a.top_long, a.top_lat, a.top_elev);

    if (a.sidmode>=0) {
      sw.swe_set_sid_mode(a.sidmode,0.,0.);
      a.iflag |= SweConst.SEFLG_SIDEREAL;
    }


    a.tjde1 = a.sde1.getJulDay();
    if (a.sde2 != null) {
      a.tjde2 = a.sde2.getJulDay();
    }

    a.jdET2 = (a.back?-Double.MAX_VALUE:Double.MAX_VALUE);
    if (a.sde2 != null) {
      a.jdET2 = a.sde2.getJulDay();
    }

    return a;
  }

  void writeCmdLine(String[] par, boolean withHeader) {
    if (withHeader) {
      for (int i = 0; i < par.length; i++) { System.out.print(par[i]+" "); }
      System.out.println();
    }
  }

  void writeHeader(TransitArguments a) {
    ///////////////////////////////////////////////////////////////////
    // Here the output and calculation starts with the output of the //
    // header if requested:                                          //
    ///////////////////////////////////////////////////////////////////
    if (!a.withHeader) { return; }

    String line = "";

    if (a.sidmode>=0) {
      System.out.print("Ayanamsha");
// Should state the ayanamsha system here!
      if (a.enddate == null) {
         System.out.print(":         "+(a.mp1?" ":"")+
                                     doubleToDMS(sw.swe_get_ayanamsa(a.tjde1)));
      } else {
         System.out.println("\n on starting date: "+(a.mp1?" ":"")+
                                     doubleToDMS(sw.swe_get_ayanamsa(a.tjde1)));
         System.out.println(" on end date:      "+(a.mp1?" ":"")+
                                    doubleToDMS(sw.swe_get_ayanamsa(a.tjde2)));
      }
    }
    System.out.println();
    if (a.isLt) {
      System.out.println("Using localtime:   UTC+"+nnof.format(tzOffset*24) + "h");
    }
    System.out.println("Starting date:     "+(a.mp1?" ":"")+jdToDate(a.sde1, a.isUt, a.isLt, 0)+(a.isLt?" LT":(a.isUT?" UT":" ET")));
    if (a.enddate!=null) {
      System.out.println("End date:          "+(a.mp1?" ":"")+jdToDate(a.sde2, a.isUT, a.isLT, 0)+(a.isLT?" LT":(a.isUT?" UT":" ET")));
    }
    if (!a.yogaTransit) {
      System.out.println("Transiting planet" + (a.mp1?"s":"") + ": " +
                         limitLineLength(getPlanetNames(a.pls1, " or "),
                                         70,
                                         "                    "));
    }
    System.out.print("Reference point:   " + (a.mp1?" ":""));
    if (a.calcSpeed) {
      ObjFormatter dblf = new ObjFormatter(a.to.values,
                                           swed.ODEGREE_CHAR+"/day",
                                           nnof);
      if (a.pls2 != null) {
        if (a.yogaTransit) {
          line = "combined " + (a.helio?"heliocentric ":"") + " speed of " +
                  group(dblf, a.to.values.length, " or ") +
                  " of " + (a.mp1?"the planets ":"planet ") +
                  getPlanetNames(a.pls1, " or ") + (a.mp1?" with ":" and ") +
                  (a.mp2?"any of ":"") + getPlanetNames(a.pls2, ", ");
        } else {
          line = (a.helio?"heliocentric ":"") + "speed of " +
                  group(dblf, a.to.values.length, " or ") + " " +
                  (a.to.values.length!=1?"higher than":
                     (a.to.values[0].doubleValue()<0?"lower than":"higher than")) +
                  " speed of "+ getPlanetNames(a.pls2, " or ");
        }
        line += " in "+
                (a.to.idxOffset==3?"longitudinal":(a.to.idxOffset==4?
                                                 "latitudinal":"distance")) +
                " direction";
        if (a.varyingTransitPoints) {
          line += " with varying transit points";
        }
        System.out.println(limitLineLength(line,
                           70,
                           "                   "+(a.mp1?" ":"")));
      } else {
        line = group(dblf, a.to.values.length, " or ") + " in " +
                  (a.helio?"heliocentric ":"") +
                  (a.to.idxOffset==3?"longitudinal":(a.to.idxOffset==4?
                                                 "latitudinal":"distance")) +
                  " direction"+(a.sidmode>=0?" in the sidereal zodiac":"");
        if (a.varyingTransitPoints) {
          line += " with varying transit points";
        }
        System.out.println(limitLineLength(line,
                           70,
                           "                   "+(a.mp1?" ":"")));
      }
      System.out.println();
      if ((a.iflag&SweConst.SEFLG_TOPOCTR)!=0) {
        System.out.println("Topographic pos.:  "+(a.mp1?" ":"") +
          doubleToDMS(SMath.abs(a.top_long)) + (a.top_long<0?"S":"N") + "/" +
          doubleToDMS(SMath.abs(a.top_lat)) + (a.top_lat<0?"W":"E") + "/" +
          nnof.format(a.top_elev) + "m");
      }
    } else { // Transit over a lon / lat / dist position:
      ObjFormatter dblf = new ObjFormatter(a.to.values,
                                   (a.to.idxOffset==2?" AU":""+swed.ODEGREE_CHAR),
                                   nnof);
      if (a.pls2 != null) {
        if (a.yogaTransit) {
          line = "combined " + (a.helio?"heliocentric ":"") +
                (a.to.idxOffset==0?"longitudinal":(a.to.idxOffset==1?
                                              "latitudinal":"distance")) +
                " positions of " +
                (a.mp1?"the ":"") + "planets " + getPlanetNames(a.pls1, " or ") +
                (a.mp1?" with ":" and ") + (a.mp2?"any of ":"") +
                getPlanetNames(a.pls2, ", ") + " reach " +
                group(dblf, a.to.values.length, " or ") +
                (a.sidmode>=0?" in the sidereal zodiac":"");
        } else {
          line = group(dblf, a.to.values.length, " or ") + " ";
          if (a.to.idxOffset==2) { // distance
            line += "farther away than " + getPlanetNames(a.pls2, " or ") +
                    (a.helio?" (heliocentric)":"");
          } else {
            line += (a.to.values.length!=1?"ahead of":
                           (a.to.values[0].doubleValue()<0?"before":"after")) + " " + 
              (a.helio?"heliocentric ":"") +
              (a.to.idxOffset==0?"longitudinal":(a.to.idxOffset==1?
                                              "latitudinal":"distance")) +
              " position of "+  getPlanetNames(a.pls2, " or ");
          }
        }
      } else {
        line = group(dblf, a.to.values.length, " or ") + " " +
               (a.to.idxOffset==2?"of ":"") +
               (a.helio?"heliocentric ":"") +
               (a.to.idxOffset==0?"longitude":(a.to.idxOffset==1?
                                               "latitude":"distance")) +
               (a.sidmode>=0?" in the sidereal zodiac":"");
      }
      if (a.varyingTransitPoints) {
        line += " with varying transit points";
      }
      System.out.println(limitLineLength(line,
                         70,
                         "                   " + (a.mp1?" ":"")));
      if ((a.iflag&SweConst.SEFLG_TOPOCTR)!=0) {
        System.out.println("Topographic pos.:  " + (a.mp1?" ":"") +
            doubleToDMS(SMath.abs(a.top_long)) + (a.top_long<0?"S":"N") + "/" +
            doubleToDMS(SMath.abs(a.top_lat)) + (a.top_lat<0?"W":"E") + "/" +
            nnof.format(a.top_elev) + "m");
      }
    }
    System.out.println();
  }

  TransitCalculator[] initCalculators(TransitArguments a)
      throws IllegalArgumentException {
    // Init all required TransitCalculators:
    TransitCalculator tcs[] = null;
    a.idxDuplicates = Integer.MAX_VALUE;
    if (a.pls2 != null) {  // relative or yoga transit between two planets
      String planetCombinations = getPlanetCombinations(a.pls1,a.pls2);
      if ("@".equals(planetCombinations)) {
        throw new IllegalArgumentException(
                "Planets for relative and yoga transits have to be " +
                "different!\nUse the -h option for more information.");
      }
      a.idxDuplicates = planetCombinations.indexOf('@');
      planetCombinations = planetCombinations.substring(0,a.idxDuplicates) +
                           planetCombinations.substring(a.idxDuplicates+1);
      a.idxDuplicates /= 2;
      tcs = new TransitCalculator[planetCombinations.length()/2];
      a.plNumbers = new int[planetCombinations.length()/2][2];
      int ci = 0;
      int t = 0;
      while(t < tcs.length) {
        a.pl1 = letter_to_ipl(planetCombinations.charAt(ci));
        a.pl2 = letter_to_ipl(planetCombinations.charAt(ci+1));
        ci += 2;
        tcs[t] = new TCPlanetPlanet(sw, a.pl1, a.pl2, a.iflag, a.to.values[0].doubleValue());
#ifdef EXTPRECISION
        tcs[t].setPrecisionFactor(a.extPrecision);
#endif /* EXTPRECISION */
        a.plNumbers[t][0] = a.pl1;
        a.plNumbers[t][1] = a.pl2;
        t++;
      }
//    } else if (house1 >= 0) {  // planet - house transit
    } else { // transit of ONE planet over a position or speed
      tcs = new TransitCalculator[a.pls1.length()];
      a.plNumbers = new int[a.pls1.length()][2];
      // Move moon (1) and mean node (t) to the end of calculations, to
      // speed calculations up. An extreme situation: -p12 -lon0 -s -n...
      // will require more than 2,230,000(!!!) iterations, but
      // -p21 -lon0 -s -n... only 154.
      int index = a.pls1.indexOf('m');
      if (index >= 0) {
        a.pls1 = a.pls1.substring(0,index) +
                 a.pls1.substring(index+1) + 'm';
      }
      index = a.pls1.indexOf('1');
      if (index >= 0) {
        a.pls1 = a.pls1.substring(0,index) +
                 a.pls1.substring(index+1) + '1';
      }
      for(int t = 0; t < tcs.length; t++) {
        a.pl1 = letter_to_ipl(a.pls1.charAt(t));
        tcs[t] = new TCPlanet(sw, a.pl1, a.iflag, a.to.values[0].doubleValue());
#ifdef EXTPRECISION
        tcs[t].setPrecisionFactor(a.extPrecision);
#endif /* EXTPRECISION */
        a.plNumbers[t][0] = a.pl1;
        a.plNumbers[t][1] = -1;
      }
    }

    // All transit calculators "should have" the same rollover flag,
    // otherwise, I forgot about something vital...
    a.rollover = tcs[0].getRollover();

    // -lon35+1 -N or -lon35+1 -b... -B... searches for 34/35/36
    // degrees, so we '+1' in this case can be safely positive:
    if (a.rollover && a.duplicateTransitPoints) {
      // Negative offsets are mapped to positive ones, as we just look
      // for to.values[tvn] - to.offset, to.values[tvn] and
      // to.values[tvn] + to.offset in -b... -B... / or -N options.
      a.to.offset = SMath.abs(a.to.offset)%360.;
    }

    return tcs;
  }

  TransitResult calcNextTransit(TransitArguments a, TransitCalculator[] tcs) {
    TransitResult tr = new TransitResult();
    TransitResult tr2 = null;

    // Which TransitCalculator returns the nearest transit?
    // int tr.tcsNo
    // Which transit value returns the nearest transit?
    // double tr.transitValue
    // Which jdET is the nearest jd?
    tr.jdET = (a.back?-Double.MAX_VALUE:Double.MAX_VALUE);


    a.v.jdStart = a.tjde1;
    a.v.jdEnd = a.jdET2;
    a.v.rollover = a.rollover;
    a.v.varyingTransitPoints = a.varyingTransitPoints;
    a.v.duplicateTransitPoints = a.duplicateTransitPoints;
    a.v.tvOffset = a.to.offset;
    a.v.back = a.back;


    // Calculate next (prev) transit point for all planets or planet
    // combinations (== for all TransitCalculators), and put the minimum
    // found value (maximum for backwards search) into tr.jdET:
    for (a.v.tcIndex = 0; a.v.tcIndex < tcs.length; a.v.tcIndex++) {
      a.v.tc = tcs[a.v.tcIndex];

      // Calculate this planet or planet combination for all possible or
      // requested transit values 'a.to.values'.
      for(int tvn = 0; tvn < a.to.values.length; tvn++) {
        a.v.transitVal = a.to.values[tvn].doubleValue();
        tr2 = calcNeighbouringTransits(a, a.plNumbers[tr.tcsNo][0],a.plNumbers[tr.tcsNo][1]);

         // Select closest transit point of all current transit calculations
        if ((a.back && tr2.jdET > tr.jdET) ||
            (!a.back && tr2.jdET < tr.jdET)) {
          tr.tcsNo = a.v.tcIndex;
          tr.transitValue = tr2.transitValue;
          tr.jdET = tr2.jdET;
          a.v.jdEnd = tr2.jdET;

          tr.pl1 = a.plNumbers[tr.tcsNo][0];
          tr.pl2 = a.plNumbers[tr.tcsNo][1];
        }
      } // for ...
#ifdef TEST_ITERATIONS

      if (a.withIterations) {
        ObjFormatter of = new ObjFormatter(tcs[a.v.tcIndex].getObjectIdentifiers(), "", nnof);
        System.out.println("Iterations (" + group(of, tcs[a.v.tcIndex].getObjectIdentifiers().length, "/") + "): " +
            f.fmt("%8d",a.v.iterations) +
            (a.v.iterateString.lastIndexOf('/')>0?
                " (" + a.v.iterateString.substring(1) + ")":"")
        );
        a.v.iterationsSum += a.v.iterations;
        a.v.iterateString = "";
        a.v.iterations = 0;
      }
#endif /* TEST_ITERATIONS */
    } // for tc in tcs[]; do ...

    return tr;
  }

  void bubbleSort(Double[] d) {
    if (d.length < 2) { return; }
    boolean sorted = true;
    Double tmp = null;
    do {
      sorted = true;
      for (int i = 0; i < d.length - 1; i++) {
        if (SMath.abs(d[i].doubleValue()) > SMath.abs(d[i+1].doubleValue())) {
          tmp = d[i+1]; d[i+1] = d[i]; d[i] = tmp;
          sorted = false;
        }
      }
    } while (!sorted);
  }
} // End of class Transits


class TransitValues
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  TransitCalculator tc = null;
  int tcIndex = 0; // Keep track of which tc is saved to jdET

  double transitVal = 0./0.;
  double jdStart = 0./0.;
  double jdEnd = 0./0.;
  double tvOffset = 0./0.;

#ifdef TEST_ITERATIONS
  String iterateString = "";
  long iterations = 0;
  long iterationsSum = 0;

#endif /* TEST_ITERATIONS */
  double zTmp = 0./0.;
  double z0 = 0./0.; // The final minimum value
  double zm = 0./0.;
  double zp = 0./0.;

  boolean varyingTransitPoints = false;  // e.g. -lon60+10 with -n, -N, -b -b, -b -B
  boolean duplicateTransitPoints = false; // e.g. -lon60+10 -N / -B only
  boolean outOfTimeRange = false;
  boolean back = false;
  boolean rollover = false;
}


class TransitOffsets
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  int idxOffset = 0;           // The index into the xx[] array in swe_calc*()
                               // 0 to 5: lon / lat / dist / speed in lon /
                               //         lat / dist
  Double[] values = null;      // The transit values, over which the
                               // the transits should be calculated
  double offset = 0.;          // An optional offset to be added to
                               // the transit values on each calculation
                               // iteration
}


class ObjFormatter
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  Object[] arr = null;
  String postfix = "";
  NumberFormat nnof = null;

  ObjFormatter(Object[] arr, String postfix, NumberFormat nnof) {
    this.arr = arr;
    this.postfix = postfix;
    this.nnof = nnof;
  }
  String format(int idx) {
    if (nnof != null && (arr[idx] instanceof Double || arr[idx] instanceof Integer)) {
      return nnof.format(arr[idx]) + postfix;
    }
    return arr[idx] + postfix;
  }
}




class TransitArguments
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  // CH-Zuerich:
  double top_long = 8.55;
  double top_lat = 47.38;
  double top_elev = 400;

  // Default values for optional parameter:
  boolean withHeader = true;
#ifdef TEST_ITERATIONS
  boolean withIterations = false;
#endif /* TEST_ITERATIONS */
  boolean back = false;
  boolean isUt = false; // Time of starting date
  boolean isUT = false; // Time of end date
  boolean isLt = false; // Time of starting date, use local time
  boolean isLT = false; // Time of end date, use local time
  boolean calcSpeed = false;
  int sidmode=-1;                 // Means: tropical mode
  int whicheph = SweConst.SEFLG_SWIEPH;
  String ephepath = SweConst.SE_EPHE_PATH;
  String sBeginhour = "";
  double beginhour = 0;
  String pls2 = null;
  int pl2 = -2;                   // Means: not set
  double count = 1;
  String topoS = null;
  boolean convert = false;
#ifndef NO_JPL
  String fname = SweConst.SE_FNAME_DE406;
#endif /* NO_JPL */



  // Derived values:
  boolean countIsSet = false;
  // duplicateTransitPoints, e.g. -lat0+0.01 with -N / -B only
  boolean duplicateTransitPoints = false;   // multiple transit points
  // varyingTransitPoints, e.g. -lat0+0.01 with -N, -n / -b -B, -b -b
  boolean varyingTransitPoints = false;
  boolean yogaTransit = true;
  boolean helio = false;
  int iflag = 0;  // Flags to be used for transit calculations
  int cflag = 0;  // Flags to be used for pure calculations

  boolean outputFormatIsSet = false;
  String outputFormat = "dt";
#ifdef EXTPRECISION
  double extPrecision = 1;
#endif /* EXTPRECISION */
  double zm = 0;
  double zp = 0;

  // A string containing the type and value of the transit point, e.g.:
  //    +lon0         for yoga transits over 0 degrees in longitude.
  //    -lat0+0.01    for transits over 0 degrees in latitude with 0.01 degree
  //                  increment.
  //    -lon30/45/60/90/120/180/270
  //                  for transits over any of these longitudinal degrees
  String transitValString="";

  // Required parameters:
  int pl1 = -2;
  String pls1 = null;
  String begindate = null;
  String enddate = null;
  String sEndhour = "";
  double endhour = 1./0.;         // Means: not set
  boolean endTimeIsSet = false;


  // Intermediate or other derived parameters:
  TransitOffsets to = null;
  boolean mp1 = false; // More than one planet
  boolean mp2 = false; // More than one planet on relative or yoga transits

  double tjde1 = 0.;
  double tjde2 = 0.;
  int[][] plNumbers = null;
  SweDate   sde1 = new SweDate();
  SweDate   sde2 = new SweDate();
  boolean rollover = false;
  TransitValues v = new TransitValues();
  int idxDuplicates = 0;
  boolean withDuplicates = true;

  double jdET2 = 0.;
}

class TransitResult
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  // The index in the array of all TransitCalculators returning the
  // nearest transit point:
  public int tcsNo = 0;

  // The nearest transit point found, Double.MAX_VALUE or Double.MIN_VALUE,
  // if no transit found:
  public double jdET = 0./0.;

  // The planet numbers:
  public int pl1 = 0;
  public int pl2 = 0;

  public double transitValue = 0./0.;

  public String toString() {
    return "tcs[" + tcsNo + "];pl:" + pl1 + "/" + pl2 + ";" + jdET;
  }
}
#endif /* TRANSITS */
