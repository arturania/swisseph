#ifdef TRACE0
#define ORIGINAL
#endif /* TRACE0 */
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

class SavePositions
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  int ipl;
  double tsave=0.;
  int iflgsave;
  /* position at t = tsave,
   * in ecliptic polar (offset 0),
   *    ecliptic cartesian (offset 6),
   *    equatorial polar (offset 12),
   *    and equatorial cartesian coordinates (offset 18).
   * 6 doubles each for position and speed coordinates.
   */
  double xsaves[]=new double[24];

  void clearData() {
#ifdef TRACE0
    Trace.level++;
    Trace.log("SavePositions.clearData()");
#endif /* TRACE0 */
    for (int i=0; i<xsaves.length; i++) {
      xsaves[i]=0.;
    }
    ipl=0; tsave=0.; iflgsave=0;
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
  }

#ifdef TRACE0
  SavePositions() {
    Trace.level++;
    Trace.log("SavePositions()");
    Trace.level--;
  }
#endif /* TRACE0 */

#ifdef TRACE0
  public String toString() {
    int i;
    StringBuffer s=new StringBuffer(512);

    s.append("ipl:\t"+ipl+"\n");
    s.append("tsave:\t"+tsave+"\n");
    s.append("iflgsave:\t"+iflgsave+"\n");
    s.append("xsaves:");
    for(i=0;i<xsaves.length;i++) {
      s.append("\t"+Trace.cv.fmt("%17.10f",xsaves[i])+"\n");
    }

    Trace.log(s.toString());
    return s.toString();
  }
#endif /* TRACE0 */

}
