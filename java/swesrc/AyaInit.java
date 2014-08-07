#ifdef J2ME
#define JAVAME
#endif /* J2ME */
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 2.00.00
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
* A class to contain all the data for different ayanamsas.<P>
* t0 is the epoch at which ayanamsha = 0.
* For internal use only - the data is packed into an array[21] of AyaInit.
* The available Ayanamshas are:<BR>
* <UL>
* <LI>  0: Fagan/Bradley (Default)
* <LI>  1: Lahiri (Robert Hand)
* <LI>  2: De Luce (Robert Hand)
* <LI>  3: Raman (Robert Hand)
* <LI>  4: Ushashashi (Robert Hand)
* <LI>  5: Krishnamurti (Robert Hand)
* <LI>  6: Djwhal Khool; (Graham Dawson)
* <LI>  7: Yukteshwar; (David Cochrane)
* <LI>  8: JN Bhasin; (David Cochrane)
* <LI>  9: Babylonian, Kugler 1
* <LI> 10: Babylonian, Kugler 2
* <LI> 11: Babylonian, Kugler 3
* <LI> 12: Babylonian, Huber
* <LI> 13: Babylonian, Mercier
* <LI> 14: t0 is defined by Aldebaran at 15 Taurus
* <LI> 15: Hipparchos
* <LI> 16: Sassanian
* <LI> 17: Galactic Center at 0 Sagittarius
* <LI> 18: J2000
* <LI> 19: J1900
* <LI> 20: B1950
* <LI> 21: Suryasiddhanta, assuming ingress of mean Sun into Aries at point of mean equinox of date on 21.3.499, noon, Ujjain (75.7684565 E) = 7:30:31.57 UT
* <LI> 22: Suryasiddhanta, assuming ingress of mean Sun into Aries at true position of mean Sun at same epoch
* <LI> 23: Aryabhata, same date, but UT 6:56:55.57 analogous 21
* <LI> 24: Aryabhata, analogous 22
* <LI> 25: SS, Revati/zePsc at polar long. 359°50'
* <LI> 26: SS, Citra/Spica at polar long. 180°
* </UL>
* <P><I><B>You will find the complete documentation for the original
* SwissEphemeris package at <A HREF="http://www.astro.ch/swisseph/sweph_g.htm">
* http://www.astro.ch/swisseph/sweph_g.htm</A>. By far most of the information 
* there is directly valid for this port to Java as well.</B></I>
* @version 1.0.0a
*/
class AyaInit
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  double t0;
  double ayan_t0;

  AyaInit(double t0, double ayan_t0) {
#ifdef TRACE0
    Trace.log("AyaInit(double, double)");
#endif /* TRACE0 */
    this.t0=t0;
    this.ayan_t0=ayan_t0;
  }
}
