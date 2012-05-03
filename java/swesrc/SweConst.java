#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef JAVAME
#define NO_JPL
#endif /* JAVAME */
#ifdef TEST_ITERATIONS
#define TRANSITS
#endif /* TEST_ITERATIONS */
#ifdef NO_RISE_TRANS
#define ASTROLOGY
#endif /* NO_RISE_TRANS */
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 1.76.00
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
package swisseph;

/**
* This class contains all the constants that might be interesting to the
* user of the swisseph package.<P>
* All constants are static, so there is no need to instantiate the class.
* <P><I><B>You will find the complete documentation for the original
* SwissEphemeris package at <A HREF="http://www.astro.ch/swisseph/sweph_g.htm">
* http://www.astro.ch/swisseph/sweph_g.htm</A>. By far most of the information 
* there is directly valid for this port to Java as well.</B></I>
* @version 1.0.0a
*/
public class SweConst
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

////////////////////////////////////////////////////////////////////////////////
//// sweodef.h: ////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
  /**
  * Constant for successful return values. It is equal to 0.
  */
  public static final int OK=0;
  /**
  * Constant for unsuccessful return values. It is equal to -1.
  */
  public static final int ERR=-1;

//////////////////////////////////////////////////////////////////////////////
// swephexp.h: ///////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  /*
   * planet numbers for the ipl parameter in swe_calc()
   */
  /**
  * Planet number for the ipl parameter in swe_calc*(). It does not calculate
  * a real planet, but the ecliptic and nutation of that date. Its value is -1.
  */
  public static final int SE_ECL_NUT=-1;

  /**
  * planet number of the sun. It is equal to 0.
  */
  public static final int SE_SUN=0;
  /**
  * planet number of the moon. It is equal to 1.
  */
  public static final int SE_MOON=1;
  /**
  * planet number of mercury. It is equal to 2.
  */
  public static final int SE_MERCURY=2;
  /**
  * planet number of venus. It is equal to 3.
  */
  public static final int SE_VENUS=3;
  /**
  * planet number of mars. It is equal to 4.
  */
  public static final int SE_MARS=4;
  /**
  * planet number of jupiter. It is equal to 5.
  */
  public static final int SE_JUPITER=5;
  /**
  * planet number of saturn. It is equal to 6.
  */
  public static final int SE_SATURN=6;
  /**
  * planet number of uranus. It is equal to 7.
  */
  public static final int SE_URANUS=7;
  /**
  * planet number of neptune. It is equal to 8.
  */
  public static final int SE_NEPTUNE=8;
  /**
  * planet number of pluto. It is equal to 9.
  */
  public static final int SE_PLUTO=9;
  /**
  * (planet) number of the mean moon node. It is equal to 10.
  */
  public static final int SE_MEAN_NODE=10;
  /**
  * (planet) number of the true moon node. It is equal to 11.
  */
  public static final int SE_TRUE_NODE=11;
  /**
  * (planet) number of the mean apogee (== Lilith). It is equal to 12.
  */
  public static final int SE_MEAN_APOG=12;
  /**
  * (planet) number of the osculating apogee. It is equal to 13.
  */
  public static final int SE_OSCU_APOG=13;
  /**
  * planet number of the earth. It is equal to 14.
  */
  public static final int SE_EARTH=14;
  /**
  * planet number of chiron. It is equal to 15.
  */
  public static final int SE_CHIRON=15;
  /**
  * planet number of pholus. It is equal to 16.
  */
  public static final int SE_PHOLUS=16;
  /**
  * planet number of ceres. It is equal to 17.
  */
  public static final int SE_CERES=17;
  /**
  * planet number of pallas. It is equal to 18.
  */
  public static final int SE_PALLAS=18;
  /**
  * planet number of juno. It is equal to 19.
  */
  public static final int SE_JUNO=19;
  /**
  * planet number of vesta. It is equal to 20.
  */
  public static final int SE_VESTA=20;
  /**
  * planet number of the interpolated lunar agopee. It is equal to 21.
  */
  public static final int SE_INTP_APOG=21;
  /**
  * planet number of the interpolated lunar perigee. It is equal to 22.
  */
  public static final int SE_INTP_PERG=22;

  /**
  * Amount of defined planets. It is equal to 23.
  */
  static final int SE_NPLANETS=23;

  /**
  * The offset, where asteroid numbers start. It is equal to 10000.
  */
  public static final int SE_AST_OFFSET=10000;
  public static final int SE_VARUNA  =(SE_AST_OFFSET + 20000);

  static final int SE_FICT_OFFSET=40;
  public static final int SE_FICT_OFFSET_1=39;
  static final int SE_FICT_MAX=999;
  static final int SE_NFICT_ELEM=15;

  static final int SE_COMET_OFFSET=2000;

  static final int SE_NALL_NAT_POINTS=(SE_NPLANETS + SE_NFICT_ELEM);

  /* Hamburger or Uranian "planets" */
  /**
  * The number of the Hamburger or Uranian "planet" cupido. It is equal to 40.
  */
  public static final int SE_CUPIDO=40;
  /**
  * The number of the Hamburger or Uranian "planet" hades. It is equal to 41.
  */
  public static final int SE_HADES=41;
  /**
  * The number of the Hamburger or Uranian "planet" zeus. It is equal to 42.
  */
  public static final int SE_ZEUS=42;
  /**
  * The number of the Hamburger or Uranian "planet" kronos. It is equal to 43.
  */
  public static final int SE_KRONOS=43;
  /**
  * The number of the Hamburger or Uranian "planet" appollon. It is equal to 44.
  */
  public static final int SE_APOLLON=44;
  /**
  * The number of the Hamburger or Uranian "planet" admetos. It is equal to 45.
  */
  public static final int SE_ADMETOS=45;
  /**
  * The number of the Hamburger or Uranian "planet" vulkanus. It is equal to 46.
  */
  public static final int SE_VULKANUS=46;
  /**
  * The number of the Hamburger or Uranian "planet" poseidon. It is equal to 47.
  */
  public static final int SE_POSEIDON=47;
  /* other fictitious bodies */
  /**
  * The "planet" number of the fictitious body isis. It is equal to 48.
  */
  public static final int SE_ISIS=48;
  /**
  * The "planet" number of the fictitious body nibiru. It is equal to 49.
  */
  public static final int SE_NIBIRU=49;
  /**
  * The "planet" number of the fictitious body harrington. It is equal to 50.
  */
  public static final int SE_HARRINGTON=50;
  /**
  * The "planet" number of the fictitious body neptune-leverrier. It is equal to 51.
  */
  public static final int SE_NEPTUNE_LEVERRIER=51;
  /**
  * The "planet" number of the fictitious body neptune-adams. It is equal to 52.
  */
  public static final int SE_NEPTUNE_ADAMS=52;
  /**
  * The "planet" number of the fictitious body pluto-lowell. It is equal to 53.
  */
  public static final int SE_PLUTO_LOWELL=53;
  /**
  * The "planet" number of the fictitious body pluto-pickering. It is equal to 54.
  */
  public static final int SE_PLUTO_PICKERING=54;
  /**
  * The "planet" number of the fictitious body vulcan. It is equal to 55.
  */
  public static final int SE_VULCAN=55;
  /**
  * The "planet" number of the fictitious body &quot;white moon&quot;
  * (== Selena). It is equal to 56.
  */
  public static final int SE_WHITE_MOON=56;
  /**
  * The "planet" number of the fictitious body &quot;Proserpina&quot;.
  * It is equal to 57.
  */
  public static final int SE_PROSERPINA=57;
  /**
  * The "planet" number of the fictitious body &quot;Waldemath&quot;.
  * It is equal to 58.
  */
  public static final int SE_WALDEMATH=58;

  public static final int SE_FIXSTAR=-10;

  /**
  * This is a constant to access the ascendent value in parameter ascmc[]
  * of SwissEph.swe_houses_armc() / SwissEph.swe_houses(). Its value is 0.
  */
  public static final int SE_ASC   =0;
  /**
  * This is a constant to access the medium coeli value in parameter ascmc[]
  * of SwissEph.swe_houses_armc() / SwissEph.swe_houses(). Its value is 1.
  */
  public static final int SE_MC    =1;
  /**
  * This is a constant to access the value of the sidereal time in parameter
  * ascmc[] of SwissEph.swe_houses_armc() / SwissEph.swe_houses(). Its value
  * is 2.
  */
  public static final int SE_ARMC  =2;
  /**
  * This is a constant to access the vertex value in parameter ascmc[]
  * of SwissEph.swe_houses_armc() / SwissEph.swe_houses(). Its value is 3.
  */
  public static final int SE_VERTEX=3;
  /**
  * This is a constant to access the value of the &quot;equatorial ascendent&quot;
  * in parameter ascmc[] of SwissEph.swe_houses_armc() / SwissEph.swe_houses().
  * Its value is 4.
  */
  public static final int SE_EQUASC=4;
  /**
  * This is a constant to access the value of the &quot;co-ascendant&quot; of
  * W. Koch in parameter ascmc[] of SwissEph.swe_houses_armc() /
  * SwissEph.swe_houses(). Its value is 5.
  */
  public static final int SE_COASC1=5;
  /**
  * This is a constant to access the value of the &quot;co-ascendant&quot; of
  * M. Munkasey in parameter ascmc[] of SwissEph.swe_houses_armc() /
  * SwissEph.swe_houses(). Its value is 6.
  */
  public static final int SE_COASC2=6;
  /**
  * This is a constant to access the polar ascendent value of M. Munkasey in
  * parameter ascmc[] of SwissEph.swe_houses_armc() / SwissEph.swe_houses().
  * Its value is 7.
  */
  public static final int SE_POLASC=7;
  /**
  * This is a constant to know the count of valid parameters in parameter
  * ascmc[] of SwissEph.swe_houses_armc() / SwissEph.swe_houses(). Its value
  * is 8 now, meaning, ascmc[0] to ascmc[7] contain meaningful data, even though
  * ascmc[] has to be of size 10.
  */
  public static final int SE_NASCMC=8;

#ifndef NO_JPL
  /*
   * only used for experimenting with various JPL ephemeris files
   * which are available at Astrodienst's internal network
   */
  public static final String SE_FNAME_DE406="de406.eph";
  public static final String SE_FNAME_DE200="de200.eph";
  public static final String SE_FNAME_DFT=SE_FNAME_DE406;
#endif /* NO_JPL */

  /*
   * flag bits for parameter iflag in function swe_calc()
   * The flag bits are defined in such a way that iflag = 0 delivers what one
   * usually wants:
   *    - the default ephemeris (SWISS EPHEMERIS) is used,
   *    - apparent geocentric positions referring to the true equinox of date
   *      are returned.
   * If not only coordinates, but also speed values are required, use
   * flag = SEFLG_SPEED.
   *
   * The 'L' behind the number indicates that 32-bit integers (Long) are used.
   */
  /**
  * Calculate the heliocentric position of the planet. This is a constant to be used
  * as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_HELCTR=8;     // return heliocentric position
  /**
  * Calculate the true position of the planet versus the apparent position. This is a
  * constant to be used as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_TRUEPOS=16;   // return true positions, not apparent
  /**
  * Calculate the position of the planet without considering the precession, i.e.,
  * calculate J2000 equinox. This is a constant to be used as a flag to swe_calc() /
  * swe_fixstar().
  */
  public static final int SEFLG_J2000=32;     // no precession, i.e. give J2000 equinox
  /**
  * Calculate the position of the planet without considering the nutation, i.e.,
  * calculate the mean equinox of the day. This is a constant to be used as a flag to
  * swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_NONUT=64;     // no nutation, i.e. mean equinox of date
  /**
  * Calculate the speed of the planet. This calculation should not be used,
  * as it is slower and less precise than SEFLG_SPEED. This is a constant to be used
  * as a flag to swe_calc() / swe_fixstar().
  * @see #SEFLG_SPEED
  */
  public static final int SEFLG_SPEED3=128;   // speed from 3 positions (do not use
                                       // it, SEFLG_SPEED is faster and more
                                       // precise.)
  /**
  * Calculate the speed of the planet. This is a constant to be used
  * as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_SPEED=256;    // high precision speed
  /**
  * Calculate the position of the planet without considering gravitational deflection.
  * This is a constant to be used as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_NOGDEFL=512;  // turn off gravitational deflection
  /**
  * Calculate the position of the planet without considering the 'annual' aberration
  * of light. This is a constant to be used as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_NOABERR=1024; // turn off 'annual' aberration of light
  /**
  * Calculate the equatorial position of the planet. This is a constant to be used as
  * a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_EQUATORIAL=2*1024; // equatorial positions are wanted
  /**
  * Return cartesian coordinates instead of polar coordinates. This is a constant to be
  * used as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_XYZ=4*1024;     // cartesian, not polar, coordinates
  /**
  * Return coordinates in radians instead of degrees. This is a constant to be used as
  * a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_RADIANS=8*1024; // coordinates in radians, not degrees
  /**
  * Calculate barycentric positions. This is a constant to be used as a flag to
  * swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_BARYCTR=16*1024; // barycentric positions
  /**
  * Calculate topocentric positions. This is a constant to be used as a flag to
  * swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_TOPOCTR=32*1024; // topocentric positions
  /**
  * Calculate sidereal positions. This is a constant to be used as a flag to
  * swe_calc() / swe_fixstar().
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SEFLG_SIDEREAL=64*1024; // sidereal positions
  /**
  * Calculate in the "International Celestial Reference System." This is a
  * constant to be used as a flag to swe_calc() / swe_fixstar().
  */
  public static final int SEFLG_ICRS=128*1024; // ICRS (DE406 reference frame)

#ifdef TRANSITS
  /**
  * calculate transits over a longitude. This is a constant to be used as a
  * flag to the TransitCalculator constructors.
  * @see swisseph.TCPlanet#TCPlanet(SwissEph, int, int, double)
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  */
  public static final int SEFLG_TRANSIT_LONGITUDE =  128*1024;
  /**
  * calculate transits over a latitude. This is a constant to be used as a
  * flag to the TransitCalculator constructors.
  * @see swisseph.TCPlanet#TCPlanet(SwissEph, int, int, double)
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  */
  public static final int SEFLG_TRANSIT_LATITUDE  =  256*1024;
  /**
  * calculate transits over a distance in AU. This is a constant to be used
  * as a flag to the TransitCalculator constructors.
  * @see swisseph.TCPlanet#TCPlanet(SwissEph, int, int, double)
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  */
  public static final int SEFLG_TRANSIT_DISTANCE  =  512*1024;
  /**
  * calculate transits over a (longitudinal, latitudinal or distance)
  * speed value. This is a constant to be used as a flag the
  * TransitCalculator constructors.
  * @see swisseph.TCPlanet#TCPlanet(SwissEph, int, int, double)
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  */
  public static final int SEFLG_TRANSIT_SPEED     = 1024*1024;
  /**
  * calculate yoga transits, this means consider the SUM of two planets
  * positions or speed instead of the difference. This is a constant to be
  * used as a flag to the TransitCalculator constructors.
  * @see swisseph.TCPlanetPlanet#TCPlanetPlanet(SwissEph, int, int, int, double)
  */
  public static final int SEFLG_YOGA_TRANSIT      = 2048*1024;
#endif /* TRANSITS */

#ifndef NO_JPL
  /**
  * Use the calculation routines that use the ephemeris data files of the
  * Solar System Dynamics Group of the Jet Propulsion Laboratory of NASA
  * <A HREF="http://ssd.jpl.nasa.gov/">http://ssd.jpl.nasa.gov/</A>. This
  * requires you to have the ephemeris data files from JPL (DE406: about
  * 9.5&nbsp;MB per 300&nbsp;years). They can be obtained from
  * <A HREF="ftp://navigator.jpl.nasa.gov/pub/ephem/export">
  * ftp://navigator.jpl.nasa.gov/pub/ephem/export</A> (as of December 21, 2000).
  * @see SwissEph#swe_set_ephe_path(java.lang.String)
  */
  public static final int SEFLG_JPLEPH=1;
#endif /* NO_JPL */
#ifndef JAVAME
  /**
  * Use the calculation routines of Swiss Ephemeris from Astrodienst&nbsp;AG
  * Z&uuml;rich <A HREF="http://www.astro.com">http://www.astro.com</A>. This
  * requires you to have the data files for SwissEphemeris available and in
  * the search path for the ephemeris files.<BR>
#ifndef NO_JPL
  * Basically, these routines are
  * identical to the JPL routines, but the data files are <I>much</I> more
  * compressed (about one 10th of the original JPL files), and the exactness
  * is retained to 1/1000 of an arc second.
#endif /* NO_JPL */
  * @see SwissEph#swe_set_ephe_path(java.lang.String)
  */
  public static final int SEFLG_SWIEPH=2;
#endif /* JAVAME */
  /**
  * Use the Moshier semi-analytical calculation routines.
#ifndef JAVAME
  * These routines are the slowest (about 10 times slower than
#ifndef NO_JPL
  * JPL or
#endif /* NO_JPL */
  * SWISS EPHEMERIS) and most
  * inaccurate, but they do not require any additional data files.<BR>
#endif /* JAVAME */
  * The accuracy is about 1 arc seconds for the planets and a few arc seconds
  * for the moon. Unfortunately, the deviation for true lunar nodes may be
  * up to one arc minute.
  */
  public static final int SEFLG_MOSEPH=4;
  /**
#ifdef JAVAME
  * Defines the default method of calculation as SEFLG_MOSEPH.
#else
  * Defines the default method of calculation as SEFLG_SWIEPH.
#endif /* JAVAME */
#ifndef NO_JPL
  * @see swisseph.SweConst#SEFLG_JPLEPH
#endif /* NO_JPL */
#ifndef JAVAME
  * @see swisseph.SweConst#SEFLG_SWIEPH
#endif /* JAVAME */
  * @see swisseph.SweConst#SEFLG_MOSEPH
  */
#ifdef JAVAME
  public static final int SEFLG_DEFAULTEPH=SEFLG_MOSEPH;
#else
  public static final int SEFLG_DEFAULTEPH=SEFLG_SWIEPH;
#endif /* JAVAME */
#ifdef JAVAME
  public static final int SEFLG_EPHMASK=1|2|SEFLG_MOSEPH;
#else
#ifdef NO_JPL
  public static final int SEFLG_EPHMASK=1|SEFLG_SWIEPH|SEFLG_MOSEPH;
#else
  public static final int SEFLG_EPHMASK=SEFLG_JPLEPH|SEFLG_SWIEPH|SEFLG_MOSEPH;
#endif /* NO_JPL */
#endif /* JAVAME */

  public static final int SE_SIDBITS             =256;
  /* for projection onto ecliptic of t0 */
#ifndef ASTROLOGY
  /**
  * This specifies that sidereal calculations are done via a
  * projection onto the ecliptic at date t0.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDBIT_ECL_T0       =256;
  /* for projection onto solar system plane */
  /**
  * This specifies that sidereal calculations are done via a
  * projection onto the solar system plane.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDBIT_SSY_PLANE    =512;
#endif /* ASTROLOGY */

  /* sidereal modes (ayanamsas) */
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha).
  * Fagan/Bradley is used as default, if nothing other is specified
  * via swe_set_sid_mode.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_FAGAN_BRADLEY  = 0;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Lahiri.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_LAHIRI         = 1;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by De Luce.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_DELUCE         = 2;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Raman.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_RAMAN          = 3;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Ushashashi.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_USHASHASHI     = 4;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Krishnamurti.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_KRISHNAMURTI   = 5;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Djwhal Khool.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_DJWHAL_KHUL    = 6;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Sri Yukteshwar.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_YUKTESHWAR     = 7;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by JN Bhasin.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_JN_BHASIN      = 8;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Babylonian, Kugler 1.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_BABYL_KUGLER1  = 9;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Babylonian, Kugler 2.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_BABYL_KUGLER2  =10;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Babylonian, Kugler 3.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_BABYL_KUGLER3  =11;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Babylonian, Huber.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_BABYL_HUBER    =12;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Babylonian, Mercier.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_BABYL_ETPSC    =13;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defining t0 by Aldebaran at 15 degrees Taurus.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_ALDEBARAN_15TAU=14;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) by
  * defined by Hipparchos.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_HIPPARCHOS     =15;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by Sassanian.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_SASSANIAN      =16;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by the galactic center being at 0 degrees Sagittarius.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_GALCENT_0SAG   =17;
#ifndef ASTROLOGY
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * J2000.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_J2000          =18;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * J1900.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_J1900          =19;
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * B1950.
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_B1950          =20;
#endif /* ASTROLOGY */
  /**
  * A constant to be used for specifying the sidereal mode (ayanamsha) as
  * defined by the user in the additional two parameters of swe_set_sid_mode().
  * @see SwissEph#swe_set_sid_mode(int, double, double)
  */
  public static final int SE_SIDM_USER          =255;

  static final int SE_MAX_STNAME=256;   // maximum size of fixstar name;
                                        // the parameter star in swe_fixstar
					// must allow twice this space for
				        // the returned star name.



  /* used for swe_nod_aps(): */
  /**
  * Used for swe_nod_aps(): mean nodes/apsides
  */
  public static final int SE_NODBIT_MEAN    =1; // mean nodes/apsides
  /**
  * Used for swe_nod_aps(): osculating nodes/apsides
  */
  public static final int SE_NODBIT_OSCU    =2; // osculating nodes/apsides
  /**
  * Used for swe_nod_aps(): osculating nodes/apsides, but motion about solar
  * system barycenter is considered
  */
  public static final int SE_NODBIT_OSCU_BAR=4; // same, but motion about solar
                                             // system barycenter is considered
  /**
  * Used for swe_nod_aps(): focal point of orbit instead of aphelion
  */
  public static final int SE_NODBIT_FOPOINT =256; // focal point of orbit
                                                  // instead of aphelion


  /* defines for function swe_split_deg() (in swephlib.c) */
  public static final int SE_SPLIT_DEG_ROUND_SEC =  1;
  public static final int SE_SPLIT_DEG_ROUND_MIN =  2;
  public static final int SE_SPLIT_DEG_ROUND_DEG =  4;
  public static final int SE_SPLIT_DEG_ZODIACAL  =  8;
  public static final int SE_SPLIT_DEG_KEEP_SIGN = 16;
                                          /* don't round to next sign,
                                           * e.g. 29.9999999 will be rounded
                                           * to 29?59'59" (or 29?59' or 29?) */
  public static final int SE_SPLIT_DEG_KEEP_DEG  = 32;
                                          /* don't round to next degree
                                           * e.g. 13.9999999 will be rounded
                                           * to 13?59'59" (or 13?59' or 13?) */

  /* for heliacal functions */
  public static final int SE_HELIACAL_RISING          = 1;
  public static final int SE_HELIACAL_SETTING         = 2;
  public static final int SE_MORNING_FIRST            = SE_HELIACAL_RISING;
  public static final int SE_EVENING_LAST             = SE_HELIACAL_SETTING;
  public static final int SE_EVENING_FIRST            = 3;
  public static final int SE_MORNING_LAST             = 4;
  public static final int SE_ACRONYCHAL_RISING        = 5;  /* still not implemented */
  public static final int SE_COSMICAL_SETTING         = 6;  /* still not implemented */
  public static final int SE_ACRONYCHAL_SETTING       = SE_COSMICAL_SETTING;

  public static final int SE_HELFLAG_LONG_SEARCH      = 128;
  public static final int SE_HELFLAG_HIGH_PRECISION   = 256;
  public static final int SE_HELFLAG_OPTICAL_PARAMS   = 512;
  public static final int SE_HELFLAG_NO_DETAILS       = 1024;
  public static final int SE_HELFLAG_AVKIND_VR        = 2048;
  public static final int SE_HELFLAG_AVKIND_PTO       = 4096;
  public static final int SE_HELFLAG_AVKIND_MIN7      = 8192;
  public static final int SE_HELFLAG_AVKIND_MIN9      = 16384;
  public static final int SE_HELFLAG_AVKIND = SE_HELFLAG_AVKIND_VR|SE_HELFLAG_AVKIND_PTO|SE_HELFLAG_AVKIND_MIN7|SE_HELFLAG_AVKIND_MIN9;
  public static final double TJD_INVALID              = 99999999.0;
  public static final int SIMULATE_VICTORVB           = 1;

  public static final int SE_PHOTOPIC_FLAG            = 0;
  public static final int SE_SCOTOPIC_FLAG            = 1;
  public static final int SE_MIXEDOPIC_FLAG           = 2;


  /*
   * ephemeris path
   * this defines where ephemeris files are expected if the function
   * swe_set_ephe_path() is not called by the application.
   * Normally, every application should make this call to define its
   * own place for the ephemeris files.
   */
  /**
   * Ephemeris path.<P>
   * This defines where ephemeris files are expected if the function
   * swe_set_ephe_path() is not called by the application.<P>
   * It is defined as being <CODE>".:./ephe:/users/ephe2/:/users/ephe/"</CODE>.
   * @see SwissEph#swe_set_ephe_path(java.lang.String)
   */
  public static final String SE_EPHE_PATH=".:./ephe:/users/ephe2/:/users/ephe/";
//  public static final String SE_EPHE_PATH=".:./ephe:/users/ephe2/:/users/ephe/:c\\:\\\\ephe:d\\:\\\\ephe:http\\://www.th-mack.de/datafiles";
                        /* At Astrodienst, we maintain two ephemeris areas for
                           the thousands of asteroid files:
                           the short files in /users/ephe/ast*,
                           the long file in /users/ephe2/ast*. */

  static final String SE_STARFILE="fixstars.cat";
  static final String SE_ASTNAMFILE="seasnam.txt";
  /**
  * The name of the file containing the orbital elements of ficticious planets.
  */
  static final String SE_FICTFILE="seorbel.txt";


  /* defines for eclipse computations */

  public static final int SE_ECL_CENTRAL=1;
  public static final int SE_ECL_NONCENTRAL=2;
  public static final int SE_ECL_TOTAL=4;
  /**
  * Annular eclipse. This is an eclipse, where the moon is seen smaller
  * than the sun, so you get a "ring" of the sun around the moon.
  */
  public static final int SE_ECL_ANNULAR=8;
  public static final int SE_ECL_PARTIAL=16;
  public static final int SE_ECL_ANNULAR_TOTAL=32;
  public static final int SE_ECL_PENUMBRAL=64;
  public static final int SE_ECL_ALLTYPES_SOLAR = SE_ECL_CENTRAL|SE_ECL_NONCENTRAL|SE_ECL_TOTAL|SE_ECL_ANNULAR|SE_ECL_PARTIAL|SE_ECL_ANNULAR_TOTAL;
  public static final int SE_ECL_ALLTYPES_LUNAR = SE_ECL_TOTAL|SE_ECL_PARTIAL|SE_ECL_PENUMBRAL;
  public static final int SE_ECL_VISIBLE=128;
  public static final int SE_ECL_MAX_VISIBLE=256;
  /**
  * This is the time, when the moon touches the sun the first time.
  */
  public static final int SE_ECL_1ST_VISIBLE=512;
  /**
  * This is the time, when the sun completely disappears.
  */
  public static final int SE_ECL_2ND_VISIBLE=1024;
  /**
  * This is the time, when the sun starts to reappear.
  */
  public static final int SE_ECL_3RD_VISIBLE=2048;
  /**
  * This is the time, when the moon and the sun finally separate.
  */
  public static final int SE_ECL_4TH_VISIBLE=4096;
  /**
  * Just check if the next conjunction of the moon with
  * a planet is an occultation; don't search further.
  */
  public static final int SE_ECL_ONE_TRY=32*1024;

  /* for swe_rise_transit() */
  /**
  * Calculate the time of sunrise. This is a constant to be used as a flag to
  * swe_rise_trans().
  */
  public static final int SE_CALC_RISE          = 1;
  /**
  * Calculate the time of sunset. This is a constant to be used as a flag to
  * swe_rise_trans().
  */
  public static final int SE_CALC_SET           = 2;
#ifndef ASTROLOGY
  /**
  * Calculate the time of the upper meridian transit (southern for northern geo.
  * latitudes). This is a constant to be used as a flag to swe_rise_trans().
  */
  public static final int SE_CALC_MTRANSIT      = 4;
#endif /* ASTROLOGY */
#ifndef ASTROLOGY
  /**
  * Calculate the time of the lower meridian transit (northern, below the
  * horizon). This is a constant to be used as a flag to swe_rise_trans().
  */
  public static final int SE_CALC_ITRANSIT      = 8;
#endif /* ASTROLOGY */
  /**
  * Add this to SE_CALC_RISE/SET, if rise or set of disc center is requested.
  * This is a constant to be used as a flag to swe_rise_trans().
  * @see swisseph.SwissEph#swe_rise_trans(double, int, java.lang.StringBuffer, int, int, double[], double, double, swisseph.DblObj, java.lang.StringBuffer)
  */
  public static final int SE_BIT_DISC_CENTER   = 256;
                                    /* to be or'ed to SE_CALC_RISE/SET */
                                    /* if rise or set of disc center is */
                                    /* required */
  /**
  * Add this to SE_CALC_RISE/SET, if refraction should not to be considered.
  * This is a constant to be used as a flag to swe_rise_trans().
  * @see swisseph.SwissEph#swe_rise_trans(double, int, java.lang.StringBuffer, int, int, double[], double, double, swisseph.DblObj, java.lang.StringBuffer)
  */
  public static final int SE_BIT_NO_REFRACTION = 512;
                                    /* to be or'ed to SE_CALC_RISE/SET, */
                                    /* if refraction is not to be considered */
  public static int SE_BIT_CIVIL_TWILIGHT =  1024; /* to be or'ed to SE_CALC_RISE/SET */
  public static int SE_BIT_NAUTIC_TWILIGHT = 2048; /* to be or'ed to SE_CALC_RISE/SET */
  public static int SE_BIT_ASTRO_TWILIGHT =  4096; /* to be or'ed to SE_CALC_RISE/SET */

  /* for swe_azalt() and swe_azalt_rev() */
  /**
  * This is a constant to be used as a flag to swe_azalt(). It determines the
  * meaning of the contents of parameter xin[]. xin[0]=&nbsp;ecl. long.,
  * xin[1]=&nbsp;ecl. lat.. xin[2]=distance is not required.
  * @see swisseph.SwissEph#swe_azalt(double, int, double[], double, double, double[], double[])
  */
  public static final int SE_ECL2HOR            = 0;
  /**
  * This is a constant to be used as a flag to swe_azalt(). It determines the
  * meaning of the contents of parameter xin[]. xin[0]=&nbsp;rectascension,
  * xin[1]=&nbsp;declination. xin[2]=distance is not required.
  * @see swisseph.SwissEph#swe_azalt(double, int, double[], double, double, double[], double[])
  */
  public static final int SE_EQU2HOR            = 1;
  /**
  * This is a constant to be used as a flag to swe_azalt_rev(). It determines
  * the meaning of the contents of output parameter xout[]. xout[0]=&nbsp;ecl.
  * long., xout[1]=&nbsp;ecl. lat..
  * @see swisseph.SwissEph#swe_azalt_rev(double, int, double[], double[], double[])
  */
  public static final int SE_HOR2ECL            = 0;
  /**
  * This is a constant to be used as a flag to swe_azalt_rev(). It determines
  * the meaning of the contents of output parameter xout[].
  * xout[0]=&nbsp;equatorial long., xout[1]=&nbsp;equatorial lat..
  * @see swisseph.SwissEph#swe_azalt_rev(double, int, double[], double[], double[])
  */
  public static final int SE_HOR2EQU            = 1;

  /* for swe_refrac() */
  /**
  * Calculate the apparent altitude from the true altitude.
  * This is a constant to be used as a flag to swe_refrac().
  * @see swisseph.SwissEph#swe_refrac(double, double, double, int)
  * @see #SE_APP_TO_TRUE
  */
  public static final int SE_TRUE_TO_APP =0;
  /**
  * Calculate the true altitude from the apparent altitude.
  * This is a constant to be used as a flag to swe_refrac().
  * @see #SE_TRUE_TO_APP
  * @see swisseph.SwissEph#swe_refrac(double, double, double, int)
  */
  public static final int SE_APP_TO_TRUE =1;

//  static final int pnoext2int[] = {SwephData.SEI_SUN, SwephData.SEI_MOON,
//    SwephData.SEI_MERCURY, SwephData.SEI_VENUS, SwephData.SEI_MARS,
//    SwephData.SEI_JUPITER, SwephData.SEI_SATURN, SwephData.SEI_URANUS,
//    SwephData.SEI_NEPTUNE, SwephData.SEI_PLUTO, 0, 0, 0, 0, SwephData.SEI_EARTH,
//    SwephData.SEI_CHIRON, SwephData.SEI_PHOLUS, SwephData.SEI_CERES,
//    SwephData.SEI_PALLAS, SwephData.SEI_JUNO, SwephData.SEI_VESTA, };
//

// we always use Astronomical Almanac constants, if available
  public static final double AUNIT=1.4959787066e+11;        // au in meters,
                                                            // AA 1996 K6
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

  /**
  * All variables are static, so there is no sense in instantiating this class.
  */
  private SweConst() {
#ifdef TRACE0
    Trace.log("SweConst()");
#endif /* TRACE0 */
  }
}
