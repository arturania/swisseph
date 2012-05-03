//#ifdef J2ME
//#define JAVAME
//#endif /* J2ME */
//#ifdef NO_RISE_TRANS
//#define ASTROLOGY
//#endif /* NO_RISE_TRANS */

//#ifdef TRACE1
//#define TRACE0
//#endif /* TRACE1 */
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
/* SWISSEPH
   $Header: /home/dieter/sweph/RCS/swephlib.c,v 1.74 2008/06/16 10:07:20 dieter Exp $

   SWISSEPH modules that may be useful for other applications
   e.g. chopt.c, venus.c, swetest.c

  Authors: Dieter Koch and Alois Treindl, Astrodienst ZÅrich

   coordinate transformations
   obliquity of ecliptic
   nutation
   precession
   delta t
   sidereal time
   setting or getting of tidal acceleration of moon
   chebyshew interpolation
   ephemeris file name generation
   cyclic redundancy checksum CRC
   modulo and normalization functions

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
package swisseph;

import java.util.StringTokenizer;

/**
* This class offers many routines that might be interesting to a programmer.<p>
* One important note: in all this package, negative longitudes are considered
* to be <b>west</b> of Greenwich, positive longitudes are seen as <b>east</b>
* of Greenwich. Especially America often uses a different notation!<p> 
* Probably most interesting are the functions swe_sidtime() (calculate the
* sidereal time) and swe_degnorm() (normalize a position to the range of
* 0.0&nbsp;<=&nbsp;x&nbsp;<&nbsp;360.0) and others.
*/
public class SwissLib
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

/* Set TRUE, to include Herring's (1987) corrections to IAU 1980
 * nutation series. AA (1996) neglects them.  */
//#define NUT_CORR_1987           FALSE

/* Precession coefficients for remote past and future.
 * One of the following four defines must be true.
 */
/* Make PREC_WILLIAMS_1994 the default: */
//#ifndef PREC_WILLIAMS_1994
//#ifndef PREC_SIMON_1994
//#ifndef PREC_LASKAR_1986
//#ifndef PREC_BRETAGNON_2003
//#define PREC_WILLIAMS_1994
//#endif /* PREC_BRETAGNON_2003 */
//#endif /* PREC_LASKAR_1986 */
//#endif /* PREC_SIMON_1994 */
//#endif /* PREC_WILLIAMS_1994 */

/* used by Moshier for DE404: */
//#ifdef PREC_WILLIAMS_1994
//#undef PREC_SIMON_1994
//#undef PREC_LASKAR_1986
//#undef PREC_BRETAGNON_2003
static final boolean PREC_WILLIAMS_1994  = true;
static final boolean PREC_SIMON_1994     = false;
static final boolean PREC_LASKAR_1986    = false;
static final boolean PREC_BRETAGNON_2003 = false;
//#else
//#ifdef PREC_SIMON_1994
//#undef PREC_LASKAR_1986
//#undef PREC_BRETAGNON_2003
static final boolean PREC_WILLIAMS_1994  = false;
static final boolean PREC_SIMON_1994     = true;
static final boolean PREC_LASKAR_1986    = false;
static final boolean PREC_BRETAGNON_2003 = false;
//#else
//#ifdef PREC_LASKAR_1986
//#undef PREC_BRETAGNON_2003
static final boolean PREC_WILLIAMS_1994  = false;
static final boolean PREC_SIMON_1994     = false;
static final boolean PREC_LASKAR_1986    = true;
static final boolean PREC_BRETAGNON_2003 = false;
//#else
static final boolean PREC_WILLIAMS_1994  = false;
static final boolean PREC_SIMON_1994     = false;
static final boolean PREC_LASKAR_1986    = false;
static final boolean PREC_BRETAGNON_2003 = true;
//#endif /* PREC_LASKAR_1986 */
//#endif /* PREC_SIMON_1994 */
//#endif /* PREC_WILLIAMS_1994 */
/* IAU precession 1976 or 2003 for recent centuries.
 * only one of the following two defines may be TRUE */
//#ifndef PREC_IAU_1976
//#define PREC_IAU_2003
//#else
//#undef PREC_IAU_2003
//#endif /* PREC_IAU_1976 */
//#ifdef PREC_IAU_1976
static final boolean PREC_IAU_1976       = true;
/* precession model P03: */
static final boolean PREC_IAU_2003       = false;
//#else
static final boolean PREC_IAU_1976       = false;
/* precession model P03: */
static final boolean PREC_IAU_2003       = true;
//#endif /* PREC_IAU_1976 */

/* choose between the following nutation models */
/* Make NUT_IAU_2000B the default: */
//#ifndef NUT_IAU_1980
//#ifndef NUT_IAU_2000A
//#ifndef NUT_IAU_2000B
//#define NUT_IAU_2000B
//#endif /* NUT_IAU_2000B */
//#endif /* NUT_IAU_2000A */
//#endif /* NUT_IAU_1980 */
//#ifdef NUT_IAU_1980
//#undefine NUT_IAU_2000A
//#undefine NUT_IAU_2000B
static final boolean NUT_IAU_1980        = true;
static final boolean NUT_IAU_2000A       = false;   /* very time consuming ! */
static final boolean NUT_IAU_2000B       = false;  /* fast, but precision of milli-arcsec */
//#else
//#ifdef NUT_IAU_2000A
//#undefine NUT_IAU_2000B
static final boolean NUT_IAU_1980        = false;
static final boolean NUT_IAU_2000A       = true;   /* very time consuming ! */
static final boolean NUT_IAU_2000B       = false;  /* fast, but precision of milli-arcsec */
//#else
static final boolean NUT_IAU_1980        = false;
static final boolean NUT_IAU_2000A       = false;   /* very time consuming ! */
static final boolean NUT_IAU_2000B       = true;  /* fast, but precision of milli-arcsec */
//#endif /* NUT_IAU_2000A */
//#endif /* NUT_IAU_1980 */

  /* J2000 +/- two centuries: */
  static final double PREC_IAU_1976_CTIES=2.0;
  /* we use P03 for whole ephemeris */
  /* J2000 +/- 75 centuries: */
  static final double PREC_IAU_2003_CTIES=75.0;


  SwissData swed;

  // Konstruktor(en):
  public SwissLib() {
    this(null);
  }

  public SwissLib(SwissData swed) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib(SwissData)");
//#ifdef TRACE1
    Trace.log("   SwissData: " + swed);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    this.swed=swed;
    if (this.swed ==null) { this.swed=new SwissData(); }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }


//////////////////////////////////////////////////////////////////////////////
// Public methods: ///////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  public double square_sum(double x[]) {
////#ifdef TRACE0
//    Trace.level++;
//    Trace.log("SwissLib.square_sum(double[])");
////#ifdef TRACE1
//    Trace.logDblArr("x", x);
////#endif /* TRACE1 */
//    Trace.level--;
////#endif /* TRACE0 */
    return x[0]*x[0]+x[1]*x[1]+x[2]*x[2];
  }

  public double square_sum(double x[], int offset) {
////#ifdef TRACE0
//    Trace.level++;
//    Trace.log("SwissLib.square_sum(double[], int)");
////#ifdef TRACE1
//    Trace.logDblArr("x", x);
//    Trace.log("   offset: " + offset);
////#endif /* TRACE1 */
//    Trace.level--;
////#endif /* TRACE0 */
    return x[offset]*x[offset]+x[1+offset]*x[1+offset]+x[2+offset]*x[2+offset];
  }



  /* Reduce x modulo 360 degrees
   */
  /**
  * Normalizes a double to the range of 0.0 >= x < 360.0.
  */
  public double swe_degnorm(double x) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swe_degnorm(double)");
//#ifdef TRACE1
    Trace.log("   x: " + Trace.fmtDbl(x));
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double y;
    y = x%360.0;
    if (SMath.abs(y) < 1e-13) {
      y = 0;   /* Alois fix 11-dec-1999 */
    }
    if( y < 0.0 ) {
      y += 360.0;
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return(y);
  }

// Well: used by Swetest.java... //#ifndef ASTROLOGY
  /* Reduce x modulo TWOPI degrees
   */
  /**
  * Normalizes a double to the range 0.0 >= x < 2*PI.
  */
  public double swe_radnorm(double x) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_radnorm(double)");
//#endif /* TRACE0 */
    double y;
    y = x % SwephData.TWOPI;
    if (SMath.abs(y) < 1e-13) {
      y = 0;   /* Alois fix 11-dec-1999 */
    }
    if( y < 0.0 ) {
      y += SwephData.TWOPI;
    }
    return(y);
  }
// Well: used by Swetest.java... //#endif /* ASTROLOGY */

// Well: used by Swetest.java... //#ifndef ASTROLOGY
  public double swe_deg_midp(double x1, double x0) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_deg_midp(double, double)");
//#endif /* TRACE0 */
    double d, y;
    d = swe_difdeg2n(x1, x0);	/* arc from x0 to x1 */
    y = swe_degnorm(x0 + d / 2);
    return(y);
  }
// Well: used by Swetest.java... //#endif /* ASTROLOGY */

// Well: used by Swetest.java... //#ifndef ASTROLOGY
  public double swe_rad_midp(double x1, double x0) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_rad_midp(double, double)");
//#endif /* TRACE0 */
    return SwissData.DEGTORAD * swe_deg_midp(x1 * SwissData.RADTODEG, x0 * SwissData.RADTODEG);
  }
// Well: used by Swetest.java... //#endif /* ASTROLOGY */

  /* Reduce x modulo 2*PI
   */
  public double swi_mod2PI(double x) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_mod2PI(double)");
//#endif /* TRACE0 */
    double y;
    y = x%SwephData.TWOPI;
    if( y < 0.0 ) {
      y += SwephData.TWOPI;
    }
    return(y);
  }


//#ifndef ASTROLOGY
  public double swi_angnorm(double x) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_angnorm(double)");
//#endif /* TRACE0 */
    if (x < 0.0 ) {
      return x + SwephData.TWOPI;
    } else if (x >= SwephData.TWOPI) {
      return x - SwephData.TWOPI;
    } else {
      return x;
    }
  }
//#endif /* ASTROLOGY */

  public void swi_cross_prod(double a[], int aOffs, double b[], int bOffs,
                             double x[], int xOffs) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_cross_prod(double[], int, double[], int, double[], int)");
//#endif /* TRACE0 */
    x[0+xOffs] = a[1+aOffs]*b[2+bOffs] - a[2+aOffs]*b[1+bOffs];
    x[1+xOffs] = a[2+aOffs]*b[0+bOffs] - a[0+aOffs]*b[2+bOffs];
    x[2+xOffs] = a[0+aOffs]*b[1+bOffs] - a[1+aOffs]*b[0+bOffs];
  }

  /*  Evaluates a given chebyshev series coef[0..ncf-1]
   *  with ncf terms at x in [-1,1]. Communications of the ACM, algorithm 446,
   *  April 1973 (vol. 16 no.4) by Dr. Roger Broucke.
   */
  public double swi_echeb(double x, double coef[], int offs, int ncf) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_echeb(double, double[], int, int)");
//#ifdef TRACE1
    Trace.log("   x: " + Trace.fmtDbl(x));
    Trace.logDblArr("coef", coef, offs, ncf);
    Trace.log("   offs: " + offs + "\n    ncf: " + ncf);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    int j;
    double x2, br, brp2, brpp;

    x2 = x * 2.;
    br = 0.;
    brp2 = 0.;    /* dummy assign to silence gcc warning */
    brpp = 0.;
    for (j = ncf - 1; j >= 0; j--) {
      brp2 = brpp;
      brpp = br;
      br = x2 * brpp - brp2 + coef[j+offs];
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return (br - brp2) * .5;
  }

  /*
   * evaluates derivative of chebyshev series, see echeb
   */
  public double swi_edcheb(double x, double coef[], int offs, int ncf) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_edcheb(double, double[], int, int)");
//#ifdef TRACE1
    Trace.log("   x: " + Trace.fmtDbl(x));
    Trace.logDblArr("coef", coef, offs, ncf);
    Trace.log("   offs: " + offs + "\n    ncf: " + ncf);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double bjpl, xjpl;
    int j;
    double x2, bf, bj, dj, xj, bjp2, xjp2;
    x2 = x * 2.;
    bf = 0.;      /* dummy assign to silence gcc warning */
    bj = 0.;      /* dummy assign to silence gcc warning */
    xjp2 = 0.;
    xjpl = 0.;
    bjp2 = 0.;
    bjpl = 0.;
    for (j = ncf - 1; j >= 1; j--) {
      dj = (double) (j + j);
      xj = coef[j+offs] * dj + xjp2;
      bj = x2 * bjpl - bjp2 + xj;
      bf = bjp2;
      bjp2 = bjpl;
      bjpl = bj;
      xjp2 = xjpl;
      xjpl = xj;
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return (bj - bf) * .5;
  }

  /*
   * conversion between ecliptical and equatorial polar coordinates.
   * for users of SWISSEPH, not used by our routines.
   * for ecl. to equ.  eps must be negative.
   * for equ. to ecl.  eps must be positive.
   * xpo, xpn are arrays of 3 doubles containing position.
   * attention: input must be in degrees!
   */
  public void swe_cotrans(double xpo[],double xpn[],double eps) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_cotrans(double[], double[], double)");
//#endif /* TRACE0 */
    swe_cotrans(xpo, 0, xpn, 0, eps);
  }
  public void swe_cotrans(double xpo[],int oOffs, double xpn[],
                          int nOffs, double eps) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_cotrans(double[], int, double[], int, double)");
//#endif /* TRACE0 */
    int i;
    double x[]=new double[6], e = eps * SwissData.DEGTORAD;
    for(i = 0; i <= 1; i++)
      x[i] = xpo[i+oOffs];
    x[0] *= SwissData.DEGTORAD;
    x[1] *= SwissData.DEGTORAD;
    x[2] = 1;
    for(i = 3; i <= 5; i++)
      x[i] = 0;
    swi_polcart(x, x);
    swi_coortrf(x, x, e);
    swi_cartpol(x, x);
    xpn[  nOffs] = x[0] * SwissData.RADTODEG;
    xpn[1+nOffs] = x[1] * SwissData.RADTODEG;
    xpn[2+nOffs] = xpo[2+oOffs];
  }

//#ifndef ASTROLOGY
  /*
   * conversion between ecliptical and equatorial polar coordinates
   * with speed.
   * for users of SWISSEPH, not used by our routines.
   * for ecl. to equ.  eps must be negative.
   * for equ. to ecl.  eps must be positive.
   * xpo, xpn are arrays of 6 doubles containing position and speed.
   * attention: input must be in degrees!
   */
  public void swe_cotrans_sp(double xpo[], double xpn[], double eps) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_cotrans_sp(double[], double[], double)");
//#endif /* TRACE0 */
    int i;
    double x[]=new double[6], e = eps * SwissData.DEGTORAD;
    for (i = 0; i <= 5; i++)
      x[i] = xpo[i];
    x[0] *= SwissData.DEGTORAD;
    x[1] *= SwissData.DEGTORAD;
    x[2] = 1;     /* avoids problems with polcart(), if x[2] = 0 */
    x[3] *= SwissData.DEGTORAD;
    x[4] *= SwissData.DEGTORAD;
    swi_polcart_sp(x, x);
    swi_coortrf(x, x, e);
    swi_coortrf(x, 3, x, 3, e);
    swi_cartpol_sp(x, xpn);
    xpn[0] *= SwissData.RADTODEG;
    xpn[1] *= SwissData.RADTODEG;
    xpn[2] = xpo[2];
    xpn[3] *= SwissData.RADTODEG;
    xpn[4] *= SwissData.RADTODEG;
    xpn[5] = xpo[5];
  }
//#endif /* ASTROLOGY */

  /*
   * conversion between ecliptical and equatorial cartesian coordinates
   * for ecl. to equ.  eps must be negative
   * for equ. to ecl.  eps must be positive
   */
  public void swi_coortrf(double xpo[], double xpn[], double eps) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_coortrf(double[], double[], double)");
//#endif /* TRACE0 */
    swi_coortrf(xpo, 0, xpn, 0, eps);
  }

  public void swi_coortrf(double xpo[], int oOffs, double xpn[],
                          int nOffs, double eps) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_coortrf(double[], int, double[], int, double)");
//#endif /* TRACE0 */
    double sineps, coseps;
    double x[]=new double[3];
    sineps = SMath.sin(eps);
    coseps = SMath.cos(eps);
    x[0] = xpo[oOffs];
    x[1] = xpo[1+oOffs] * coseps + xpo[2+oOffs] * sineps;
    x[2] = -xpo[1+oOffs] * sineps + xpo[2+oOffs] * coseps;
    xpn[0+nOffs] = x[0];
    xpn[1+nOffs] = x[1];
    xpn[2+nOffs] = x[2];
  }

  /*
   * conversion between ecliptical and equatorial cartesian coordinates
   * sineps            sin(eps)
   * coseps            cos(eps)
   * for ecl. to equ.  sineps must be -sin(eps)
   */
  public void swi_coortrf2(double xpo[], double xpn[], double sineps,
                           double coseps) {
    swi_coortrf2(xpo, 0, xpn, 0, sineps, coseps);
  }
  public void swi_coortrf2(double xpo[], int oOffs, double xpn[], int nOffs,
                    double sineps, double coseps) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_coortrf2(double[], int, double[], int, double, double)");
//#ifdef TRACE1
    Trace.logDblArr("xpo", xpo);
    Trace.log("   oOffs: " + oOffs);
    Trace.logDblArr("xpn", xpn);
    Trace.log("   nOffs: " + nOffs + "\n    sineps: " + Trace.fmtDbl(sineps) + "\n    coseps: " + Trace.fmtDbl(coseps));
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double x[]=new double[3];
    x[0] = xpo[0+oOffs];
    x[1] = xpo[1+oOffs] * coseps + xpo[2+oOffs] * sineps;
    x[2] = -xpo[1+oOffs] * sineps + xpo[2+oOffs] * coseps;
    xpn[0+nOffs] = x[0];
    xpn[1+nOffs] = x[1];
    xpn[2+nOffs] = x[2];
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /* conversion of cartesian (x[3]) to polar coordinates (l[3]).
   * x = l is allowed.
   * if |x| = 0, then lon, lat and rad := 0.
   */
  public void swi_cartpol(double x[], double l[]) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_cartpol(double[], double[])");
//#endif /* TRACE0 */
    swi_cartpol(x, 0, l, 0);
  }

  public void swi_cartpol(double x[], int xOffs, double l[], int lOffs) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_cartpol(double[], int, double[], int)");
//#endif /* TRACE0 */
    double rxy;
    double ll[]=new double[3];
    if (x[0+xOffs] == 0 && x[1+xOffs] == 0 && x[2+xOffs] == 0) {
      l[0+lOffs] = l[1+lOffs] = l[2+lOffs] = 0;
      return;
    }
    rxy = x[0+xOffs]*x[0+xOffs] + x[1+xOffs]*x[1+xOffs];
    ll[2] = SMath.sqrt(rxy + x[2+xOffs]*x[2+xOffs]);
    rxy = SMath.sqrt(rxy);
//#if 0
    Real x0 = new Real(x[0+xOffs]);
    Real ll0 = new Real(x[1+xOffs]);
    ll0.atan2(x0);
    if (ll0.lessThan(Real.ZERO)) {
      ll0.add(Real.PI2);
    }
    Real ll1 = new Real(x[2+xOffs]);
    Real r_rxy = new Real(rxy);
    ll1.div(r_rxy);
    ll1.atan();
    ll[0] = ll0.toDouble();
    ll[1] = ll1.toDouble();
//#else
    ll[0] = SMath.atan2(x[1+xOffs], x[0+xOffs]);
    if (ll[0] < 0.0) {
      ll[0] += SwephData.TWOPI;
    }
    ll[1] = SMath.atan(x[2+xOffs] / rxy);
//#endif /* 0 */
    l[0+lOffs] = ll[0];
    l[1+lOffs] = ll[1];
    l[2+lOffs] = ll[2];
  }

  /* conversion from polar (l[3]) to cartesian coordinates (x[3]).
   * x = l is allowed.
   */
  public void swi_polcart(double l[], double x[]) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_polcart(double[], double[])");
//#endif /* TRACE0 */
    swi_polcart(l, 0, x, 0);
  }
  public void swi_polcart(double l[], int lOffs, double x[], int xOffs) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_polcart(double[], int, double[], int)");
//#endif /* TRACE0 */
    double xx[]=new double[3];
    double cosl1;
    cosl1 = SMath.cos(l[lOffs+1]);
    xx[0] = l[lOffs+2] * cosl1 * SMath.cos(l[lOffs]);
    xx[1] = l[lOffs+2] * cosl1 * SMath.sin(l[lOffs]);
    xx[2] = l[lOffs+2] * SMath.sin(l[lOffs+1]);
    x[xOffs] = xx[0];
    x[xOffs+1] = xx[1];
    x[xOffs+2] = xx[2];
  }

  /* conversion of position and speed.
   * from cartesian (x[6]) to polar coordinates (l[6]).
   * x = l is allowed.
   * if position is 0, function returns direction of
   * motion.
   */
//#ifndef ASTROLOGY
  public void swi_cartpol_sp(double x[], double l[]) {
    swi_cartpol_sp(x, 0, l, 0);
  }
//#endif /* ASTROLOGY */
  public void swi_cartpol_sp(double x[], int xOffs, double l[], int lOffs) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_cartpol_sp(double[], int, double[], int)");
//#ifdef TRACE1
    Trace.logDblArr("x", x);
    Trace.log("   xOffs: " + xOffs);
    Trace.logDblArr("l", l);
    Trace.log("   lOffs: " + lOffs);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double xx[]=new double[6], ll[]=new double[6];
    double rxy, coslon, sinlon, coslat, sinlat;
    /* zero position */
    if (x[0+xOffs] == 0 && x[1+xOffs] == 0 && x[2+xOffs] == 0) {
      l[0+lOffs] = l[1+lOffs] = l[3+lOffs] = l[4+lOffs] = 0;
      l[5+lOffs] = SMath.sqrt(square_sum(x, 3+xOffs));
      swi_cartpol(x, 3+xOffs, l, 0+lOffs);
      l[2+lOffs] = 0;
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return;
    }
    /* zero speed */
    if (x[3+xOffs] == 0 && x[4+xOffs] == 0 && x[5+xOffs] == 0) {
      l[3+lOffs] = l[4+lOffs] = l[5+lOffs] = 0;
      swi_cartpol(x, xOffs, l, lOffs);
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return;
    }
    /* position */
    rxy = x[0+xOffs]*x[0+xOffs] + x[1+xOffs]*x[1+xOffs];
    ll[2] = SMath.sqrt(rxy + x[2+xOffs]*x[2+xOffs]);
    rxy = SMath.sqrt(rxy);
    ll[0] = SMath.atan2(x[1+xOffs], x[0+xOffs]);
    if (ll[0] < 0.0) {
      ll[0] += SwephData.TWOPI;
    }
    ll[1] = SMath.atan(x[2+xOffs] / rxy);
    /* speed:
     * 1. rotate coordinate system by longitude of position about z-axis,
     *    so that new x-axis = position radius projected onto x-y-plane.
     *    in the new coordinate system
     *    vy'/r = dlong/dt, where r = sqrt(x^2 +y^2).
     * 2. rotate coordinate system by latitude about new y-axis.
     *    vz"/r = dlat/dt, where r = position radius.
     *    vx" = dr/dt
     */
    coslon = x[0+xOffs] / rxy;          /* cos(l[0]); */
    sinlon = x[1+xOffs] / rxy;          /* sin(l[0]); */
    coslat = rxy / ll[2];         /* cos(l[1]); */
    sinlat = x[2+xOffs] / ll[2];        /* sin(ll[1]); */
    xx[3] = x[3+xOffs] * coslon + x[4+xOffs] * sinlon;
    xx[4] = -x[3+xOffs] * sinlon + x[4+xOffs] * coslon;
    l[3+lOffs] = xx[4] / rxy;           /* speed in longitude */
    xx[4] = -sinlat * xx[3] + coslat * x[5+xOffs];
    xx[5] =  coslat * xx[3] + sinlat * x[5+xOffs];
    l[4+lOffs] = xx[4] / ll[2];         /* speed in latitude */
    l[5+lOffs] = xx[5];                 /* speed in radius */
    l[0+lOffs] = ll[0];                 /* return position */
    l[1+lOffs] = ll[1];
    l[2+lOffs] = ll[2];
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
  }

  /* conversion of position and speed
   * from polar (l[6]) to cartesian coordinates (x[6])
   * x = l is allowed
   * explanation s. swi_cartpol_sp()
   */
  public void swi_polcart_sp(double l[], double x[]) {
    swi_polcart_sp(l, 0, x, 0);
  }
  public void swi_polcart_sp(double l[], int lOffs, double x[], int xOffs) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_polcart_sp(double[], int, double[], int)");
//#ifdef TRACE1
    Trace.logDblArr("l", l);
    Trace.log("   lOffs: " + lOffs);
    Trace.logDblArr("x", x);
    Trace.log("   xOffs: " + xOffs);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double sinlon, coslon, sinlat, coslat;
    double xx[]=new double[6], rxy, rxyz;
    /* zero speed */
    if (l[3+lOffs] == 0 && l[4+lOffs] == 0 && l[5+lOffs] == 0) {
      x[3+xOffs] = x[4+xOffs] = x[5+xOffs] = 0;
      swi_polcart(l, lOffs, x, xOffs);
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return;
    }
    /* position */
    coslon = SMath.cos(l[0+lOffs]);
    sinlon = SMath.sin(l[0+lOffs]);
    coslat = SMath.cos(l[1+lOffs]);
    sinlat = SMath.sin(l[1+lOffs]);
    xx[0] = l[2+lOffs] * coslat * coslon;
    xx[1] = l[2+lOffs] * coslat * sinlon;
    xx[2] = l[2+lOffs] * sinlat;
    /* speed; explanation s. swi_cartpol_sp(), same method the other way round*/
    rxyz = l[2+lOffs];
    rxy = SMath.sqrt(xx[0] * xx[0] + xx[1] * xx[1]);
    xx[5] = l[5+lOffs];
    xx[4] = l[4+lOffs] * rxyz;
    x[5+xOffs] = sinlat * xx[5] + coslat * xx[4];       /* speed z */
    xx[3] = coslat * xx[5] - sinlat * xx[4];
    xx[4] = l[3+lOffs] * rxy;
    x[3+xOffs] = coslon * xx[3] - sinlon * xx[4];       /* speed x */
    x[4+xOffs] = sinlon * xx[3] + coslon * xx[4];       /* speed y */
    x[0+xOffs] = xx[0];                                 /* return position */
    x[1+xOffs] = xx[1];
    x[2+xOffs] = xx[2];
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
  }

//#ifndef ASTROLOGY
  public double swi_dot_prod_unit(double[] x, double[] y) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log(" SwissLib.swi_dot_prod_unit(double[], double[])");
//#ifdef TRACE1
    Trace.logDblArr("x", x);
    Trace.logDblArr("y", y);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double dop = x[0]*y[0]+x[1]*y[1]+x[2]*y[2];
    double e1 = SMath.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]);
    double e2 = SMath.sqrt(y[0]*y[0]+y[1]*y[1]+y[2]*y[2]);
    dop /= e1;
    dop /= e2;
    if (dop > 1) {
      dop = 1;
    }
    if (dop < -1) {
      dop = -1;
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return dop;
  }
//#endif /* ASTROLOGY */


  /* Obliquity of the ecliptic at Julian date J
   *
   * IAU Coefficients are from:
   * J. H. Lieske, T. Lederle, W. Fricke, and B. Morando,
   * "Expressions for the Precession Quantities Based upon the IAU
   * (1976) System of Astronomical Constants,"  Astronomy and Astrophysics
   * 58, 1-16 (1977).
   *
   * Before or after 200 years from J2000, the formula used is from:
   * J. Laskar, "Secular terms of classical planetary theories
   * using the results of general theory," Astronomy and Astrophysics
   * 157, 59070 (1986).
   *
   * Bretagnon, P. et al.: 2003, "Expressions for Precession Consistent with 
   * the IAU 2000A Model". A&A 400,785
   *B03  	84381.4088  	-46.836051*t  	-1667◊10-7*t2  	+199911◊10-8*t3  	-523◊10-9*t4  	-248◊10-10*t5  	-3◊10-11*t6
   *C03   84381.406  	-46.836769*t  	-1831◊10-7*t2  	+20034◊10-7*t3  	-576◊10-9*t4  	-434◊10-10*t5
   *
   *  See precess and page B18 of the Astronomical Almanac.
   */
  public double swi_epsiln(double J) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_epsiln(double)");
//#ifdef TRACE1
    Trace.log("   J: " + Trace.fmtDbl(J));
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double T, eps;
    T = (J - 2451545.0)/36525.0;
    if (PREC_IAU_1976 && SMath.abs(T) <= PREC_IAU_1976_CTIES ) {
      eps = (((1.813e-3*T-5.9e-4)*T-46.8150)*T+84381.448)*SwissData.DEGTORAD/3600;
    } else if (PREC_IAU_2003 && SMath.abs(T) <= PREC_IAU_2003_CTIES) {
      eps =  (((((-4.34e-8 * T -5.76e-7) * T +2.0034e-3) * T -1.831e-4) * T -46.836769) * T + 84381.406) * SwissData.DEGTORAD / 3600.0;
    } else if (PREC_BRETAGNON_2003) {
      eps =  ((((((-3e-11 * T - 2.48e-8) * T -5.23e-7) * T +1.99911e-3) * T -1.667e-4) * T -46.836051) * T + 84381.40880) * SwissData.DEGTORAD / 3600.0;/* */
    } else if (PREC_SIMON_1994) {
      eps =  (((((2.5e-8 * T -5.1e-7) * T +1.9989e-3) * T -1.52e-4) * T -46.80927) * T + 84381.412) * SwissData.DEGTORAD / 3600.0;/* */
    } else if (PREC_WILLIAMS_1994) {
      eps =  ((((-1.0e-6 * T +2.0e-3) * T -1.74e-4) * T -46.833960) * T + 84381.409) * SwissData.DEGTORAD / 3600.0;/* */
    } else { /* PREC_LASKAR_1986 */
      T /= 10.0;
      eps = ((((((((( 2.45e-10*T + 5.79e-9)*T + 2.787e-7)*T
      + 7.12e-7)*T - 3.905e-5)*T - 2.4967e-3)*T
      - 5.138e-3)*T + 1.99925)*T - 0.0155)*T - 468.093)*T
      + 84381.448;
      eps *= SwissData.DEGTORAD/3600;
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return(eps);
  }

  /* Precession of the equinox and ecliptic
   * from epoch Julian date J to or from J2000.0
   *
   * Program by Steve Moshier.
   * Changes in program structure by Dieter Koch.
   *
   * #define PREC_WILLIAMS_1994 1
   * James G. Williams, "Contributions to the Earth's obliquity rate,
   * precession, and nutation,"  Astron. J. 108, 711-724 (1994).
   *
   * #define PREC_SIMON_1994 0
   * J. L. Simon, P. Bretagnon, J. Chapront, M. Chapront-Touze', G. Francou,
   * and J. Laskar, "Numerical Expressions for precession formulae and
   * mean elements for the Moon and the planets," Astronomy and Astrophysics
   * 282, 663-683 (1994).
   *
   * #define PREC_IAU_1976 0
   * IAU Coefficients are from:
   * J. H. Lieske, T. Lederle, W. Fricke, and B. Morando,
   * "Expressions for the Precession Quantities Based upon the IAU
   * (1976) System of Astronomical Constants,"  Astronomy and
   * Astrophysics 58, 1-16 (1977).
   *
   * #define PREC_LASKAR_1986 0
   * Newer formulas that cover a much longer time span are from:
   * J. Laskar, "Secular terms of classical planetary theories
   * using the results of general theory," Astronomy and Astrophysics
   * 157, 59070 (1986).
   *
   * See also:
   * P. Bretagnon and G. Francou, "Planetary theories in rectangular
   * and spherical variables. VSOP87 solutions," Astronomy and
   * Astrophysics 202, 309-315 (1988).
   *
   * Laskar's expansions are said by Bretagnon and Francou
   * to have "a precision of about 1" over 10000 years before
   * and after J2000.0 in so far as the precession constants p^0_A
   * and epsilon^0_A are perfectly known."
   *
   * Bretagnon and Francou's expansions for the node and inclination
   * of the ecliptic were derived from Laskar's data but were truncated
   * after the term in T**6. I have recomputed these expansions from
   * Laskar's data, retaining powers up to T**10 in the result.
   *
   * The following table indicates the differences between the result
   * of the IAU formula and Laskar's formula using four different test
   * vectors, checking at J2000 plus and minus the indicated number
   * of years.
   *
   *   Years       Arc
   * from J2000  Seconds
   * ----------  -------
   *        0       0
   *      100     .006
   *      200     .006
   *      500     .015
   *     1000     .28
   *     2000    6.4
   *     3000   38.
   *    10000 9400.
   */
  /* In WILLIAMS and SIMON, Laskar's terms of order higher than t^4
     have been retained, because Simon et al mention that the solution
     is the same except for the lower order terms.  */
//#ifdef PREC_WILLIAMS_1994
//#undef PREC_SIMON_1994
//#undef PREC_LASKAR_1986
//#endif /* PREC_WILLIAMS_1994 */
//#ifdef PREC_SIMON_1994
//#undef PREC_LASKAR_1986
//#endif /* PREC_SIMON_1994 */
//#ifndef PREC_SIMON_1994
//#ifndef PREC_LASKAR_1986
//#define PREC_WILLIAMS_1994 1
//#endif /* PREC_LASKAR_1986 */
//#endif /* PREC_SIMON_1994 */

//#ifdef PREC_WILLIAMS_1994
  static final double pAcof[] = {
   -8.66e-10, -4.759e-8, 2.424e-7, 1.3095e-5, 1.7451e-4, -1.8055e-3,
   -0.235316, 0.076, 110.5407, 50287.70000 };
  static final double nodecof[] = {
    6.6402e-16, -2.69151e-15, -1.547021e-12, 7.521313e-12, 1.9e-10,
    -3.54e-9, -1.8103e-7,  1.26e-7,  7.436169e-5,
    -0.04207794833,  3.052115282424};
  static final double inclcof[] = {
    1.2147e-16, 7.3759e-17, -8.26287e-14, 2.503410e-13, 2.4650839e-11,
    -5.4000441e-11, 1.32115526e-9, -6.012e-7, -1.62442e-5,
    0.00227850649, 0.0 };
//#endif /* PREC_WILLIAMS_1994 */
  
//#ifdef PREC_SIMON_1994 
  /* Precession coefficients from Simon et al: */
  static final double pAcof[] = {
    -8.66e-10, -4.759e-8, 2.424e-7, 1.3095e-5, 1.7451e-4, -1.8055e-3, 
    -0.235316, 0.07732, 111.2022, 50288.200 }; 
  static final double nodecof[] = {
    6.6402e-16, -2.69151e-15, -1.547021e-12, 7.521313e-12, 1.9e-10,
    -3.54e-9, -1.8103e-7, 2.579e-8, 7.4379679e-5,
    -0.0420782900, 3.0521126906};
  static final double inclcof[] = {
    1.2147e-16, 7.3759e-17, -8.26287e-14, 2.503410e-13, 2.4650839e-11,
    -5.4000441e-11, 1.32115526e-9, -5.99908e-7, -1.624383e-5,
    0.002278492868, 0.0 };
//#endif /* PREC_SIMON_1994 */

//#ifdef PREC_LASKAR_1986
  /* Precession coefficients taken from Laskar's paper: */
  static final double pAcof[] = {
    -8.66e-10, -4.759e-8, 2.424e-7, 1.3095e-5, 1.7451e-4, -1.8055e-3,
    -0.235316, 0.07732, 111.1971, 50290.966 };
  /* Node and inclination of the earth's orbit computed from
   * Laskar's data as done in Bretagnon and Francou's paper.
   * Units are radians.
   */
  static final double nodecof[] = {
    6.6402e-16, -2.69151e-15, -1.547021e-12, 7.521313e-12, 6.3190131e-10,
    -3.48388152e-9, -1.813065896e-7, 2.75036225e-8, 7.4394531426e-5,
    -0.042078604317, 3.052112654975 };
  static final double inclcof[] = {
    1.2147e-16, 7.3759e-17, -8.26287e-14, 2.503410e-13, 2.4650839e-11,
    -5.4000441e-11, 1.32115526e-9, -5.998737027e-7, -1.6242797091e-5,
    0.002278495537, 0.0 };
//#endif /* PREC_LASKAR_1986 */

//#ifdef PREC_BRETAGNON_2003
  static final double pAcof[] = {};
  static final double nodecof[] = {};
  static final double inclcof[] = {};
//#endif /* PREC_BRETAGNON_2003 */

  /* Subroutine arguments:
   *
   * R = rectangular equatorial coordinate vector to be precessed.
   *     The result is written back into the input vector.
   * J = Julian date
   * direction =
   *      Precess from J to J2000: direction = 1
   *      Precess from J2000 to J: direction = -1
   * Note that if you want to precess from J1 to J2, you would
   * first go from J1 to J2000, then call the program again
   * to go from J2000 to J2.
   */
  public int swi_precess(double R[], double J, int direction ) {
    return swi_precess(R, 0, J, direction);
  }
  public int swi_precess(double R[], int rOffs, double J, int direction ) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_precess(double[], int, double, int)");
//#ifdef TRACE1
    Trace.logDblArr("R", R);
    Trace.log("   rOffs: " + rOffs + "\n    J: " + Trace.fmtDbl(J) + "\n    direction: " + direction);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    double sinth, costh, sinZ, cosZ, sinz, cosz;
    double eps, sineps, coseps;
    double A, B, T, Z, z, TH, pA, W;
    double x[]=new double[3];
    int pn;
    int i;
    if( J == SwephData.J2000 ) {
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return(0);
    }
    /* Each precession angle is specified by a polynomial in
     * T = Julian centuries from J2000.0.  See AA page B18.
     */
    T = (J - SwephData.J2000)/36525.0;
    /* Use IAU formula for a few centuries.  */
    if (PREC_IAU_1976 && SMath.abs(T) <= PREC_IAU_1976_CTIES) {
      Z =  (( 0.017998*T + 0.30188)*T + 2306.2181)*T*SwissData.DEGTORAD/3600;
      z =  (( 0.018203*T + 1.09468)*T + 2306.2181)*T*SwissData.DEGTORAD/3600;
      TH = ((-0.041833*T - 0.42665)*T + 2004.3109)*T*SwissData.DEGTORAD/3600;
    } else if (PREC_IAU_2003 && SMath.abs(T) <= PREC_IAU_2003_CTIES) {
      Z =  (((((- 0.0000003173*T - 0.000005971)*T + 0.01801828)*T + 0.2988499)*T + 2306.083227)*T + 2.650545)*SwissData.DEGTORAD/3600;
      z =  (((((- 0.0000002904*T - 0.000028596)*T + 0.01826837)*T + 1.0927348)*T + 2306.077181)*T - 2.650545)*SwissData.DEGTORAD/3600;
      TH = ((((-0.00000011274*T - 0.000007089)*T - 0.04182264)*T - 0.4294934)*T + 2004.191903)*T*SwissData.DEGTORAD/3600;
      /* AA 2006 B28:
      Z =  (((((- 0.0000002*T - 0.0000327)*T + 0.0179663)*T + 0.3019015)*T + 2306.0809506)*T + 2.5976176)*SwissData.DEGTORAD/3600;
      z =  (((((- 0.0000003*T - 0.000047)*T + 0.0182237)*T + 1.0947790)*T + 2306.0803226)*T - 2.5976176)*SwissData.DEGTORAD/3600;
      TH = ((((-0.0000001*T - 0.0000601)*T - 0.0418251)*T - 0.4269353)*T + 2004.1917476)*T*SwissData.DEGTORAD/3600;
      */
    } else if (PREC_BRETAGNON_2003) {
      Z =  ((((((-0.00000000013*T - 0.0000003040)*T - 0.000005708)*T + 0.01801752)*T + 0.3023262)*T + 2306.080472)*T + 2.72767)*SwissData.DEGTORAD/3600;
      z =  ((((((-0.00000000005*T - 0.0000002486)*T - 0.000028276)*T + 0.01826676)*T + 1.0956768)*T + 2306.076070)*T - 2.72767)*SwissData.DEGTORAD/3600;
      TH = ((((((0.000000000009*T + 0.00000000036)*T -0.0000001127)*T - 0.000007291)*T - 0.04182364)*T - 0.4266980)*T + 2004.190936)*T*SwissData.DEGTORAD/3600;
    } else {
      /* Implementation by elementary rotations using Laskar's expansions.
       * First rotate about the x axis from the initial equator
       * to the ecliptic. (The input is equatorial.)
       */
      if( direction == 1 ) {
        eps = swi_epsiln(J); /* To J2000 */
      } else {
        eps = swi_epsiln(SwephData.J2000); /* From J2000 */
      }
      sineps = SMath.sin(eps);
      coseps = SMath.cos(eps);
      x[0] = R[0+rOffs];
      z = coseps*R[1+rOffs] + sineps*R[2+rOffs];
      x[2] = -sineps*R[1+rOffs] + coseps*R[2+rOffs];
      x[1] = z;
      /* Precession in longitude */
      T /= 10.0; /* thousands of years */
      pn=0; //p = pAcof;
      pA = pAcof[pn]; pn++;
      for( i=0; i<9; i++ ) {
        pA = pA * T + pAcof[pn]; pn++;
      }
      pA *= SwissData.DEGTORAD/3600 * T;
      /* Node of the moving ecliptic on the J2000 ecliptic.
       */
      pn=0; // p = nodecof;
      W = nodecof[pn]; pn++;
      for( i=0; i<10; i++ ) {
        W = W * T + nodecof[pn]; pn++;
      }
      /* Rotate about z axis to the node.
       */
      if( direction == 1 ) {
        z = W + pA;
      } else {
        z = W;
      }
      B = SMath.cos(z);
      A = SMath.sin(z);
      z = B * x[0] + A * x[1];
      x[1] = -A * x[0] + B * x[1];
      x[0] = z;
      /* Rotate about new x axis by the inclination of the moving
       * ecliptic on the J2000 ecliptic.
       */
      pn=0; // p = inclcof;
      z = inclcof[pn]; pn++;
      for( i=0; i<10; i++ ) {
        z = z * T + inclcof[pn]; pn++;
      }
      if( direction == 1 ) {
        z = -z;
      }
      B = SMath.cos(z);
      A = SMath.sin(z);
      z = B * x[1] + A * x[2];
      x[2] = -A * x[1] + B * x[2];
      x[1] = z;
      /* Rotate about new z axis back from the node.
       */
      if( direction == 1 ) {
        z = -W;
      } else {
        z = -W - pA;
      }
      B = SMath.cos(z);
      A = SMath.sin(z);
      z = B * x[0] + A * x[1];
      x[1] = -A * x[0] + B * x[1];
      x[0] = z;
      /* Rotate about x axis to final equator.
       */
      if( direction == 1 ) {
        eps = swi_epsiln(SwephData.J2000);
      } else {
        eps = swi_epsiln(J);
      }
      sineps = SMath.sin(eps);
      coseps = SMath.cos(eps);
      z = coseps * x[1] - sineps * x[2];
      x[2] = sineps * x[1] + coseps * x[2];
      x[1] = z;
      for( i=0; i<3; i++ )
        R[i+rOffs] = x[i];
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return(0);
    }
    sinth = SMath.sin(TH);
    costh = SMath.cos(TH);
    sinZ = SMath.sin(Z);
    cosZ = SMath.cos(Z);
    sinz = SMath.sin(z);
    cosz = SMath.cos(z);
    A = cosZ*costh;
    B = sinZ*costh;
    if( direction < 0 ) { /* From J2000.0 to J */
      x[0] =    (A*cosz - sinZ*sinz)*R[0+rOffs]
              - (B*cosz + cosZ*sinz)*R[1+rOffs]
                        - sinth*cosz*R[2+rOffs];
      x[1] =    (A*sinz + sinZ*cosz)*R[0+rOffs]
              - (B*sinz - cosZ*cosz)*R[1+rOffs]
                        - sinth*sinz*R[2+rOffs];
      x[2] =              cosZ*sinth*R[0+rOffs]
                        - sinZ*sinth*R[1+rOffs]
                        + costh*R[2+rOffs];
    }
    else { /* From J to J2000.0 */
      x[0] =    (A*cosz - sinZ*sinz)*R[0+rOffs]
              + (A*sinz + sinZ*cosz)*R[1+rOffs]
                        + cosZ*sinth*R[2+rOffs];
      x[1] =  - (B*cosz + cosZ*sinz)*R[0+rOffs]
              - (B*sinz - cosZ*cosz)*R[1+rOffs]
                        - sinZ*sinth*R[2+rOffs];
      x[2] =            - sinth*cosz*R[0+rOffs]
                        - sinth*sinz*R[1+rOffs]
                        + costh*R[2+rOffs];
    }
    for( i=0; i<3; i++ )
      R[i+rOffs] = x[i];
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return(0);
  }


//#ifdef NUT_IAU_1980
  /* Nutation in longitude and obliquity
   * computed at Julian date J.
   *
   * References:
   * "Summary of 1980 IAU Theory of Nutation (Final Report of the
   * IAU Working Group on Nutation)", P. K. Seidelmann et al., in
   * Transactions of the IAU Vol. XVIII A, Reports on Astronomy,
   * P. A. Wayman, ed.; D. Reidel Pub. Co., 1982.
   *
   * "Nutation and the Earth's Rotation",
   * I.A.U. Symposium No. 78, May, 1977, page 256.
   * I.A.U., 1980.
   *
   * Woolard, E.W., "A redevelopment of the theory of nutation",
   * The Astronomical Journal, 58, 1-3 (1953).
   *
   * This program implements all of the 1980 IAU nutation series.
   * Results checked at 100 points against the 1986 AA; all agreed.
   *
   *
   * - S. L. Moshier, November 1987
   *   October, 1992 - typo fixed in nutation matrix
   *
   * - D. Koch, November 1995: small changes in structure,
   *   Corrections to IAU 1980 Series added from Expl. Suppl. p. 116
   *
   * Each term in the expansion has a trigonometric
   * argument given by
   *   W = i*MM + j*MS + k*FF + l*DD + m*OM
   * where the variables are defined below.
   * The nutation in longitude is a sum of terms of the
   * form (a + bT) * sin(W). The terms for nutation in obliquity
   * are of the form (c + dT) * cos(W).  The coefficients
   * are arranged in the tabulation as follows:
   *
   * Coefficient:
   * i  j  k  l  m      a      b      c     d
   * 0, 0, 0, 0, 1, -171996, -1742, 92025, 89,
   * The first line of the table, above, is done separately
   * since two of the values do not fit into 16 bit integers.
   * The values a and c are arc seconds times 10000.  b and d
   * are arc seconds per Julian century times 100000.  i through m
   * are integers.  See the program for interpretation of MM, MS,
   * etc., which are mean orbital elements of the Sun and Moon.
   *
   * If terms with coefficient less than X are omitted, the peak
   * errors will be:
   *
   *   omit       error,            omit  error,
   *   a <        longitude         c <   obliquity
   * .0005"       .0100"          .0008"  .0094"
   * .0046        .0492           .0095   .0481
   * .0123        .0880           .0224   .0905
   * .0386        .1808           .0895   .1129
   */
  static final short ENDMARK=-99;
  static short nt[] = {
  /* LS and OC are units of 0.0001"
   *LS2 and OC2 are units of 0.00001"
   *MM,MS,FF,DD,OM, LS, LS2,OC, OC2 */
   0, 0, 0, 0, 2, 2062, 2,-895, 5,
  -2, 0, 2, 0, 1, 46, 0,-24, 0,
   2, 0,-2, 0, 0, 11, 0, 0, 0,
  -2, 0, 2, 0, 2,-3, 0, 1, 0,
   1,-1, 0,-1, 0,-3, 0, 0, 0,
   0,-2, 2,-2, 1,-2, 0, 1, 0,
   2, 0,-2, 0, 1, 1, 0, 0, 0,
   0, 0, 2,-2, 2,-13187,-16, 5736,-31,
   0, 1, 0, 0, 0, 1426,-34, 54,-1,
   0, 1, 2,-2, 2,-517, 12, 224,-6,
   0,-1, 2,-2, 2, 217,-5,-95, 3,
   0, 0, 2,-2, 1, 129, 1,-70, 0,
   2, 0, 0,-2, 0, 48, 0, 1, 0,
   0, 0, 2,-2, 0,-22, 0, 0, 0,
   0, 2, 0, 0, 0, 17,-1, 0, 0,
   0, 1, 0, 0, 1,-15, 0, 9, 0,
   0, 2, 2,-2, 2,-16, 1, 7, 0,
   0,-1, 0, 0, 1,-12, 0, 6, 0,
  -2, 0, 0, 2, 1,-6, 0, 3, 0,
   0,-1, 2,-2, 1,-5, 0, 3, 0,
   2, 0, 0,-2, 1, 4, 0,-2, 0,
   0, 1, 2,-2, 1, 4, 0,-2, 0,
   1, 0, 0,-1, 0,-4, 0, 0, 0,
   2, 1, 0,-2, 0, 1, 0, 0, 0,
   0, 0,-2, 2, 1, 1, 0, 0, 0,
   0, 1,-2, 2, 0,-1, 0, 0, 0,
   0, 1, 0, 0, 2, 1, 0, 0, 0,
  -1, 0, 0, 1, 1, 1, 0, 0, 0,
   0, 1, 2,-2, 0,-1, 0, 0, 0,
   0, 0, 2, 0, 2,-2274,-2, 977,-5,
   1, 0, 0, 0, 0, 712, 1,-7, 0,
   0, 0, 2, 0, 1,-386,-4, 200, 0,
   1, 0, 2, 0, 2,-301, 0, 129,-1,
   1, 0, 0,-2, 0,-158, 0,-1, 0,
  -1, 0, 2, 0, 2, 123, 0,-53, 0,
   0, 0, 0, 2, 0, 63, 0,-2, 0,
   1, 0, 0, 0, 1, 63, 1,-33, 0,
  -1, 0, 0, 0, 1,-58,-1, 32, 0,
  -1, 0, 2, 2, 2,-59, 0, 26, 0,
   1, 0, 2, 0, 1,-51, 0, 27, 0,
   0, 0, 2, 2, 2,-38, 0, 16, 0,
   2, 0, 0, 0, 0, 29, 0,-1, 0,
   1, 0, 2,-2, 2, 29, 0,-12, 0,
   2, 0, 2, 0, 2,-31, 0, 13, 0,
   0, 0, 2, 0, 0, 26, 0,-1, 0,
  -1, 0, 2, 0, 1, 21, 0,-10, 0,
  -1, 0, 0, 2, 1, 16, 0,-8, 0,
   1, 0, 0,-2, 1,-13, 0, 7, 0,
  -1, 0, 2, 2, 1,-10, 0, 5, 0,
   1, 1, 0,-2, 0,-7, 0, 0, 0,
   0, 1, 2, 0, 2, 7, 0,-3, 0,
   0,-1, 2, 0, 2,-7, 0, 3, 0,
   1, 0, 2, 2, 2,-8, 0, 3, 0,
   1, 0, 0, 2, 0, 6, 0, 0, 0,
   2, 0, 2,-2, 2, 6, 0,-3, 0,
   0, 0, 0, 2, 1,-6, 0, 3, 0,
   0, 0, 2, 2, 1,-7, 0, 3, 0,
   1, 0, 2,-2, 1, 6, 0,-3, 0,
   0, 0, 0,-2, 1,-5, 0, 3, 0,
   1,-1, 0, 0, 0, 5, 0, 0, 0,
   2, 0, 2, 0, 1,-5, 0, 3, 0,
   0, 1, 0,-2, 0,-4, 0, 0, 0,
   1, 0,-2, 0, 0, 4, 0, 0, 0,
   0, 0, 0, 1, 0,-4, 0, 0, 0,
   1, 1, 0, 0, 0,-3, 0, 0, 0,
   1, 0, 2, 0, 0, 3, 0, 0, 0,
   1,-1, 2, 0, 2,-3, 0, 1, 0,
  -1,-1, 2, 2, 2,-3, 0, 1, 0,
  -2, 0, 0, 0, 1,-2, 0, 1, 0,
   3, 0, 2, 0, 2,-3, 0, 1, 0,
   0,-1, 2, 2, 2,-3, 0, 1, 0,
   1, 1, 2, 0, 2, 2, 0,-1, 0,
  -1, 0, 2,-2, 1,-2, 0, 1, 0,
   2, 0, 0, 0, 1, 2, 0,-1, 0,
   1, 0, 0, 0, 2,-2, 0, 1, 0,
   3, 0, 0, 0, 0, 2, 0, 0, 0,
   0, 0, 2, 1, 2, 2, 0,-1, 0,
  -1, 0, 0, 0, 2, 1, 0,-1, 0,
   1, 0, 0,-4, 0,-1, 0, 0, 0,
  -2, 0, 2, 2, 2, 1, 0,-1, 0,
  -1, 0, 2, 4, 2,-2, 0, 1, 0,
   2, 0, 0,-4, 0,-1, 0, 0, 0,
   1, 1, 2,-2, 2, 1, 0,-1, 0,
   1, 0, 2, 2, 1,-1, 0, 1, 0,
  -2, 0, 2, 4, 2,-1, 0, 1, 0,
  -1, 0, 4, 0, 2, 1, 0, 0, 0,
   1,-1, 0,-2, 0, 1, 0, 0, 0,
   2, 0, 2,-2, 1, 1, 0,-1, 0,
   2, 0, 2, 2, 2,-1, 0, 0, 0,
   1, 0, 0, 2, 1,-1, 0, 0, 0,
   0, 0, 4,-2, 2, 1, 0, 0, 0,
   3, 0, 2,-2, 2, 1, 0, 0, 0,
   1, 0, 2,-2, 0,-1, 0, 0, 0,
   0, 1, 2, 0, 1, 1, 0, 0, 0,
  -1,-1, 0, 2, 1, 1, 0, 0, 0,
   0, 0,-2, 0, 1,-1, 0, 0, 0,
   0, 0, 2,-1, 2,-1, 0, 0, 0,
   0, 1, 0, 2, 0,-1, 0, 0, 0,
   1, 0,-2,-2, 0,-1, 0, 0, 0,
   0,-1, 2, 0, 1,-1, 0, 0, 0,
   1, 1, 0,-2, 1,-1, 0, 0, 0,
   1, 0,-2, 2, 0,-1, 0, 0, 0,
   2, 0, 0, 2, 0, 1, 0, 0, 0,
   0, 0, 2, 4, 2,-1, 0, 0, 0,
   0, 1, 0, 1, 0, 1, 0, 0, 0,
//#ifdef NUT_CORR_1987
  /* corrections to IAU 1980 nutation series by Herring 1987
   *             in 0.00001" !!!
   *              LS      OC      */
   101, 0, 0, 0, 1,-725, 0, 213, 0,
   101, 1, 0, 0, 0, 523, 0, 208, 0,
   101, 0, 2,-2, 2, 102, 0, -41, 0,
   101, 0, 2, 0, 2, -81, 0,  32, 0,
  /*              LC      OS !!!  */
   102, 0, 0, 0, 1, 417, 0, 224, 0,
   102, 1, 0, 0, 0,  61, 0, -24, 0,
   102, 0, 2,-2, 2,-118, 0, -47, 0,
//#endif /* NUT_CORR_1987 */
   ENDMARK,
  };
//#endif /* NUT_IAU_1980 */

//#ifdef NUT_IAU_1980
  public int swi_nutation(double J, double nutlo[]) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_nutation(double, double[])");
//#ifdef TRACE1
    Trace.log("   J: " + Trace.fmtDbl(J));
    Trace.logDblArr("nutlo", nutlo);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    /* arrays to hold sines and cosines of multiple angles */
    double ss[][]=new double[5][8];
    double cc[][]=new double[5][8];
    double arg;
    double args[]=new double[5];
    double f, g, T, T2;
    double MM, MS, FF, DD, OM;
    double cu, su, cv, sv, sw, s;
    double C, D;
    int i, j, k, k1, m, n;
    int ns[]=new int[5];
    int pn;
    /* Julian centuries from 2000 January 1.5,
     * barycentric dynamical time
     */
    T = (J - 2451545.0) / 36525.0;
    T2 = T * T;
    /* Fundamental arguments in the FK5 reference system.
     * The coefficients, originally given to 0.001",
     * are converted here to degrees.
     */
    /* longitude of the mean ascending node of the lunar orbit
     * on the ecliptic, measured from the mean equinox of date
     */
    OM = -6962890.539 * T + 450160.280 + (0.008 * T + 7.455) * T2;
    OM = swe_degnorm(OM/3600) * SwissData.DEGTORAD;
    /* mean longitude of the Sun minus the
     * mean longitude of the Sun's perigee
     */
    MS = 129596581.224 * T + 1287099.804 - (0.012 * T + 0.577) * T2;
    MS = swe_degnorm(MS/3600) * SwissData.DEGTORAD;
    /* mean longitude of the Moon minus the
     * mean longitude of the Moon's perigee
     */
    MM = 1717915922.633 * T + 485866.733 + (0.064 * T + 31.310) * T2;
    MM = swe_degnorm(MM/3600) * SwissData.DEGTORAD;
    /* mean longitude of the Moon minus the
     * mean longitude of the Moon's node
     */
    FF = 1739527263.137 * T + 335778.877 + (0.011 * T - 13.257) * T2;
    FF = swe_degnorm(FF/3600) * SwissData.DEGTORAD;
    /* mean elongation of the Moon from the Sun.
     */
    DD = 1602961601.328 * T + 1072261.307 + (0.019 * T - 6.891) * T2;
    DD = swe_degnorm(DD/3600) * SwissData.DEGTORAD;
    args[0] = MM;
    ns[0] = 3;
    args[1] = MS;
    ns[1] = 2;
    args[2] = FF;
    ns[2] = 4;
    args[3] = DD;
    ns[3] = 4;
    args[4] = OM;
    ns[4] = 2;
    /* Calculate sin( i*MM ), etc. for needed multiple angles
     */
    for (k = 0; k <= 4; k++) {
      arg = args[k];
      n = ns[k];
      su = SMath.sin(arg);
      cu = SMath.cos(arg);
      ss[k][0] = su;                      /* sin(L) */
      cc[k][0] = cu;                      /* cos(L) */
      sv = 2.0*su*cu;
      cv = cu*cu - su*su;
      ss[k][1] = sv;                      /* sin(2L) */
      cc[k][1] = cv;
      for( i=2; i<n; i++ ) {
        s =  su*cv + cu*sv;
        cv = cu*cv - su*sv;
        sv = s;
        ss[k][i] = sv;            /* sin( i+1 L ) */
        cc[k][i] = cv;
      }
    }
    /* first terms, not in table: */
    C = (-0.01742*T - 17.1996)*ss[4][0];  /* sin(OM) */
    D = ( 0.00089*T +  9.2025)*cc[4][0];  /* cos(OM) */
    for(pn = 0; nt[pn] != ENDMARK; pn += 9) {
      /* argument of sine and cosine */
      k1 = 0;
      cv = 0.0;
      sv = 0.0;
      for( m=0; m<5; m++ ) {
        j = nt[pn+m];
        if (j > 100) {
          j = 0; /* p[0] is a flag */
        }
        if( j!=0 ) {
          k = j;
          if( j < 0 ) {
            k = -k;
          }
          su = ss[m][k-1]; /* sin(k*angle) */
          if( j < 0 ) {
            su = -su;
          }
          cu = cc[m][k-1];
          if( k1 == 0 ) { /* set first angle */
            sv = su;
            cv = cu;
            k1 = 1;
          }
          else {          /* combine angles */
            sw = su*cv + cu*sv;
            cv = cu*cv - su*sv;
            sv = sw;
          }
        }
      }
      /* longitude coefficient, in 0.0001" */
      f  = nt[pn+5] * 0.0001;
      if( nt[pn+6] != 0 ) {
        f += 0.00001 * T * nt[pn+6];
      }
      /* obliquity coefficient, in 0.0001" */
      g = nt[pn+7] * 0.0001;
      if( nt[pn+8] != 0 ) {
        g += 0.00001 * T * nt[pn+8];
      }
      if (nt[pn] >= 100) {    /* coefficients in 0.00001" */
        f *= 0.1;
        g *= 0.1;
      }
      /* accumulate the terms */
      if (nt[pn] != 102) {
        C += f * sv;
        D += g * cv;
      }
      else {              /* cos for nutl and sin for nuto */
        C += f * cv;
        D += g * sv;
      }
    }
    /* Save answers, expressed in radians */
    nutlo[0] = SwissData.DEGTORAD * C / 3600.0;
    nutlo[1] = SwissData.DEGTORAD * D / 3600.0;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return(0);
  }
//#endif /* NUT_IAU_1980 */

//#undefine NUT_IAU_2000_ALL
//#ifdef NUT_IAU_2000A
//#define NUT_IAU_2000_ALL
//#endif /* NUT_IAU_2000A */
//#ifdef NUT_IAU_2000B
//#define NUT_IAU_2000_ALL
//#endif /* NUT_IAU_2000B */
//#ifdef NUT_IAU_2000_ALL
  /* Nutation IAU 2000A model
   * (MHB2000 luni-solar and planetary nutation, without free core nutation)
   *
   * Function returns nutation in longitude and obliquity in radians with
   * respect to the equinox of date. For the obliquity of the ecliptic
   * the calculation of Lieske & al. (1977) must be used.
   *
   * The precision in recent years is about 0.001 arc seconds.
   *
   * The calculation includes luni-solar and planetary nutation.
   * Free core nutation, which cannot be predicted, is omitted,
   * the error being of the order of a few 0.0001 arc seconds.
   *
   * References:
   *
   * Capitaine, N., Wallace, P.T., Chapront, J., A & A 432, 366 (2005).
   *
   * Chapront, J., Chapront-Touze, M. & Francou, G., A & A 387, 700 (2002).
   *
   * Lieske, J.H., Lederle, T., Fricke, W. & Morando, B., "Expressions
   * for the precession quantities based upon the IAU (1976) System of
   * Astronomical Constants", A & A 58, 1-16 (1977).
   *
   * Mathews, P.M., Herring, T.A., Buffet, B.A., "Modeling of nutation
   * and precession   New nutation series for nonrigid Earth and
   * insights into the Earth's interior", J.Geophys.Res., 107, B4,
   * 2002.
   *
   * Simon, J.-L., Bretagnon, P., Chapront, J., Chapront-Touze, M.,
   * Francou, G., Laskar, J., A & A 282, 663-683 (1994).
   *
   * Souchay, J., Loysel, B., Kinoshita, H., Folgueira, M., A & A Supp.
   * Ser. 135, 111 (1999).
   *
   * Wallace, P.T., "Software for Implementing the IAU 2000
   * Resolutions", in IERS Workshop 5.1 (2002).
   *
   * Nutation IAU 2000A series in:
   * Kaplan, G.H., United States Naval Observatory Circular No. 179 (Oct. 2005)
   * aa.usno.navy.mil/publications/docs/Circular_179.html
   *
   * MHB2000 code at
   * - ftp://maia.usno.navy.mil/conv2000/chapter5/IAU2000A.
   * - http://www.iau-sofa.rl.ac.uk/2005_0901/Downloads.html
   */
  int swi_nutation(double J, double nutlo[]) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissLib.swi_nutation(double, double[])");
//#ifdef TRACE1
    Trace.log("   J: " + Trace.fmtDbl(J));
    Trace.logDblArr("nutlo", nutlo);
//#endif /* TRACE1 */
//#endif /* TRACE0 */
    int i, j, k, inls;
    double M, SM, F, D, OM;
//#ifdef NUT_IAU_2000A
    double AL, ALSU, AF, AD, AOM, APA;
    double ALME, ALVE, ALEA, ALMA, ALJU, ALSA, ALUR, ALNE;
//#endif /* NUT_IAU_2000A */
    double darg, sinarg, cosarg;
    double dpsi = 0, deps = 0;
    double T = (J - SwephData.J2000 ) / 36525.0;
    /* luni-solar nutation */
    /* Fundamental arguments, Simon & al. (1994) */
    /* Mean anomaly of the Moon. */
    M  = swe_degnorm(( 485868.249036 +
                T*( 1717915923.2178 +
                T*(         31.8792 +
                T*(          0.051635 +
                T*(        - 0.00024470 ))))) / 3600.0) * SwissData.DEGTORAD;
    /* Mean anomaly of the Sun */
    SM = swe_degnorm((1287104.79305 +
                T*(  129596581.0481 +
                T*(        - 0.5532 +
                T*(          0.000136 +
                T*(        - 0.00001149 ))))) / 3600.0) * SwissData.DEGTORAD;
    /* Mean argument of the latitude of the Moon. */
    F   = swe_degnorm(( 335779.526232 +
                T*( 1739527262.8478 +
                T*(       - 12.7512 +
                T*(       -  0.001037 +
                T*(          0.00000417 ))))) / 3600.0) * SwissData.DEGTORAD;
    /* Mean elongation of the Moon from the Sun. */
    D   = swe_degnorm((1072260.70369 +
                T*( 1602961601.2090 +
                T*(        - 6.3706 +
                T*(          0.006593 +
                T*(        - 0.00003169 ))))) / 3600.0) * SwissData.DEGTORAD;
    /* Mean longitude of the ascending node of the Moon. */
    OM  = swe_degnorm(( 450160.398036 +
                T*(  - 6962890.5431 +
                T*(          7.4722 +
                T*(          0.007702 +
                T*(        - 0.00005939 ))))) / 3600.0) * SwissData.DEGTORAD;
    /* luni-solar nutation series, in reverse order, starting with small terms */
//#ifdef NUT_IAU_2000B
    inls = Swenut2000a.NLS_2000B;
//#else
    inls = Swenut2000a.NLS;
//#endif /* NUT_IAU_2000B */
    for (i = inls - 1; i >= 0; i--) {
      j = i * 5;
      darg = swe_radnorm((double) Swenut2000aNls.nls[j + 0] * M  +
                         (double) Swenut2000aNls.nls[j + 1] * SM +
                         (double) Swenut2000aNls.nls[j + 2] * F   +
                         (double) Swenut2000aNls.nls[j + 3] * D   +
                         (double) Swenut2000aNls.nls[j + 4] * OM);
      sinarg = SMath.sin(darg);
      cosarg = SMath.cos(darg);
      k = i * 6;
      dpsi += (Swenut2000a.cls[k+0] + Swenut2000a.cls[k+1] * T) * sinarg + Swenut2000a.cls[k+2] * cosarg;
      deps += (Swenut2000a.cls[k+3] + Swenut2000a.cls[k+4] * T) * cosarg + Swenut2000a.cls[k+5] * sinarg;
    }
    nutlo[0] = dpsi * Swenut2000a.O1MAS2DEG;
    nutlo[1] = deps * Swenut2000a.O1MAS2DEG;
//#ifdef NUT_IAU_2000A
    /* planetary nutation
     * note: The MHB2000 code computes the luni-solar and planetary nutation
     * in different routines, using slightly different Delaunay
     * arguments in the two cases.  This behaviour is faithfully
     * reproduced here.  Use of the Simon et al. expressions for both
     * cases leads to negligible changes, well below 0.1 microarcsecond.*/
    /* Mean anomaly of the Moon.*/
    AL = swe_radnorm(2.35555598 + 8328.6914269554 * T);
    /* Mean anomaly of the Sun.*/
    ALSU = swe_radnorm(6.24006013 + 628.301955 * T);
    /* Mean argument of the latitude of the Moon. */
    AF = swe_radnorm(1.627905234 + 8433.466158131 * T);
    /* Mean elongation of the Moon from the Sun. */
    AD = swe_radnorm(5.198466741 + 7771.3771468121 * T);
    /* Mean longitude of the ascending node of the Moon. */
    AOM = swe_radnorm(2.18243920 - 33.757045 * T);
    /* General accumulated precession in longitude. */
    APA = (0.02438175 + 0.00000538691 * T) * T;
    /* Planetary longitudes, Mercury through Neptune (Souchay et al. 1999). */
    ALME = swe_radnorm(4.402608842 + 2608.7903141574 * T);
    ALVE = swe_radnorm(3.176146697 + 1021.3285546211 * T);
    ALEA = swe_radnorm(1.753470314 +  628.3075849991 * T);
    ALMA = swe_radnorm(6.203480913 +  334.0612426700 * T);
    ALJU = swe_radnorm(0.599546497 +   52.9690962641 * T);
    ALSA = swe_radnorm(0.874016757 +   21.3299104960 * T);
    ALUR = swe_radnorm(5.481293871 +    7.4781598567 * T);
    ALNE = swe_radnorm(5.321159000 +    3.8127774000 * T);
    /* planetary nutation series (in reverse order).*/
    dpsi = 0;
    deps = 0;
    for (i = NPL - 1; i >= 0; i--) {
      j = i * 14;
      darg = swe_radnorm((double) npl[j + 0] * AL   +
          (double) npl[j + 1] * ALSU +
          (double) npl[j + 2] * AF   +
          (double) npl[j + 3] * AD   +
          (double) npl[j + 4] * AOM  +
          (double) npl[j + 5] * ALME +
          (double) npl[j + 6] * ALVE +
          (double) npl[j + 7] * ALEA +
          (double) npl[j + 8] * ALMA +
          (double) npl[j + 9] * ALJU +
          (double) npl[j +10] * ALSA +
          (double) npl[j +11] * ALUR +
          (double) npl[j +12] * ALNE +
          (double) npl[j +13] * APA);
      k = i * 4;
      sinarg = SMath.sin(darg);
      cosarg = SMath.cos(darg);
      dpsi += (double) icpl[k+0] * sinarg + (double) icpl[k+1] * cosarg;
      deps += (double) icpl[k+2] * sinarg + (double) icpl[k+3] * cosarg;
    }
    nutlo[0] += dpsi * Swenut2000a.O1MAS2DEG;
    nutlo[1] += deps * Swenut2000a.O1MAS2DEG;
//#if 1
    /* changes required by adoption of P03 precession
     * according to Capitaine et al. A & A 412, 366 (2005) */
    dpsi = -8.1 * SMath.sin(OM) - 0.6 * SMath.sin(2 * F - 2 * D + 2 * OM);
    dpsi += T * (47.8 * SMath.sin(OM) + 3.7 * SMath.sin(2 * F - 2 * D + 2 * OM) + 0.6 * SMath.sin(2 * F + 2 * OM) - 0.6 * SMath.sin(2 * OM));
    deps = T * (-25.6 * SMath.cos(OM) - 1.6 * SMath.cos(2 * F - 2 * D + 2 * OM));
    nutlo[0] += dpsi / (3600.0 * 1000000.0);
    nutlo[1] += deps / (3600.0 * 1000000.0);
//#endif /* 1 */
//#endif /* NUT_IAU_2000A */
    nutlo[0] *= SwissData.DEGTORAD;
    nutlo[1] *= SwissData.DEGTORAD;
    return 0;
  }
//#endif /* NUT_IAU_2000_ALL */

  /* GCRS to J2000 */
  void swi_bias(double[] x, int iflag, boolean backward) {
//#if 0
    double DAS2R = 1.0 / 3600.0 * SwissData.DEGTORAD;
    double dpsi_bias = -0.041775 * DAS2R;
    double deps_bias = -0.0068192 * DAS2R;
    double dra0 = -0.0146 * DAS2R;
    double deps2000 = 84381.448 * DAS2R;
//#endif /* 0 */
    double xx[]=new double[6], rb[][]=new double[3][3];
    int i;
    rb[0][0] = +0.9999999999999942;
    rb[0][1] = +0.0000000707827948;
    rb[0][2] = -0.0000000805621738;
    rb[1][0] = -0.0000000707827974;
    rb[1][1] = +0.9999999999999969;
    rb[1][2] = -0.0000000330604088;
    rb[2][0] = +0.0000000805621715;
    rb[2][1] = +0.0000000330604145;
    rb[2][2] = +0.9999999999999962;
    if (backward) {
      for (i = 0; i <= 2; i++) {
        xx[i] = x[0] * rb[i][0] +
                x[1] * rb[i][1] +
                x[2] * rb[i][2];
        if ((iflag & SweConst.SEFLG_SPEED) != 0)
          xx[i+3] = x[3] * rb[i][0] +
                x[4] * rb[i][1] +
                x[5] * rb[i][2];
      }
    } else {
      for (i = 0; i <= 2; i++) {
        xx[i] = x[0] * rb[0][i] +
                x[1] * rb[1][i] +
                x[2] * rb[2][i];
        if ((iflag & SweConst.SEFLG_SPEED) != 0)
          xx[i+3] = x[3] * rb[0][i] +
                x[4] * rb[1][i] +
                x[5] * rb[2][i];
      }
    }
    for (i = 0; i <= 2; i++) x[i] = xx[i];
    if ((iflag & SweConst.SEFLG_SPEED) != 0) {
      for (i = 3; i <= 5; i++) x[i] = xx[i];
    }
  }


  /* GCRS to FK5 */
  void swi_icrs2fk5(double[] x, int iflag, boolean backward) {
//#if 0
    double DAS2R = 1.0 / 3600.0 * SwissData.DEGTORAD;
    double dra0 = -0.0229 * DAS2R;
    double dxi0 =  0.0091 * DAS2R;
    double det0 = -0.0199 * DAS2R;
//#endif /* 0 */
    double xx[]=new double[6], rb[][]=new double[3][3];
    int i;
    rb[0][0] = +0.9999999999999928;
    rb[0][1] = +0.0000001110223287;
    rb[0][2] = +0.0000000441180557;
    rb[1][0] = -0.0000001110223330;
    rb[1][1] = +0.9999999999999891;
    rb[1][2] = +0.0000000964779176;
    rb[2][0] = -0.0000000441180450;
    rb[2][1] = -0.0000000964779225;
    rb[2][2] = +0.9999999999999943;
    if (backward) {
      for (i = 0; i <= 2; i++) {
        xx[i] = x[0] * rb[i][0] +
                x[1] * rb[i][1] +
                x[2] * rb[i][2];
        if ((iflag & SweConst.SEFLG_SPEED) != 0)
          xx[i+3] = x[3] * rb[i][0] +
                x[4] * rb[i][1] +
                x[5] * rb[i][2];
      }
    } else {
      for (i = 0; i <= 2; i++) {
        xx[i] = x[0] * rb[0][i] +
                x[1] * rb[1][i] +
                x[2] * rb[2][i];
        if ((iflag & SweConst.SEFLG_SPEED) != 0)
          xx[i+3] = x[3] * rb[0][i] +
                x[4] * rb[1][i] +
                x[5] * rb[2][i];
      }
    }
    for (i = 0; i <= 5; i++) x[i] = xx[i];
  }

//#ifndef JAVAME
  /* ************************************************************
  cut the string s at any char in cutlist; put pointers to partial strings
  into cpos[0..n-1], return number of partial strings;
  if less than nmax fields are found, the first empty pointer is
  set to NULL.
  More than one character of cutlist in direct sequence count as one
  separator only! cut_str_any("word,,,word2",","..) cuts only two parts,
  cpos[0] = "word" and cpos[1] = "word2".
  If more than nmax fields are found, nmax is returned and the
  last field nmax-1 rmains un-cut.
  **************************************************************/
  /**
  * Cut the String s at any character in cutlist and put the resulting
  * Strings into String cpos[].
  * @param s The input string.
  * @param cutlist A String specifying all characters, where the input string
  * should be cut.
  * @param cpos Input and output paramater: a String[] containing maximum
  * 'nmax' Strings.
  * @param nmax The size of the cpos array. A relict from the C version...
  * @return Number of generated Strings
  */
  public int swi_cutstr(String s, String cutlist, String cpos[], int nmax) {
////#ifdef TRACE0
//    Trace.level++;
//    Trace.log("SwissLib.swi_cutstr(String, String, String[], int)");
////#ifdef TRACE1
//    Trace.log("   s: " + s + "\n    cutlist: " + cutlist);
//    for(int z = 0; z < cpos.length; z++) {
//      Trace.log("   cpos[" + z + "]: " + cpos[z]);
//    }
//    Trace.log("   nmax: " + nmax);
////#endif /* TRACE1 */
////#endif /* TRACE0 */
    if (s.indexOf('\n')>=0) { s=s.substring(0,s.indexOf('\n')); }
    if (s.indexOf('\r')>=0) { s=s.substring(0,s.indexOf('\r')); }
    StringTokenizer tk=new StringTokenizer(s,cutlist,true);
    int n=0;
    while(tk.hasMoreTokens() && n<20) {
      String g=tk.nextToken();
      // Characters in cutlist can be valid characters of the String. If
      // escaped with "\\", join together, what the StringTokenizer separated
// Well, well: 'while g.endsWith("\\\\")', then obviously not, but
// while 'g.endsWith("\\\\\\")', then yes, etc. pp.... So I would have to
// do something about this one "sometime"...
      while (g.endsWith("\\") && tk.hasMoreTokens()) {
        g=g.substring(0,g.length()-1)+tk.nextToken();
        if (tk.hasMoreTokens()) {
          g+=tk.nextToken();
        }
      }
      cpos[n]=g;
      n++;
      if (tk.hasMoreTokens()) { tk.nextToken(); }
    }
    cpos[19]="";
    while(tk.hasMoreTokens()) {
      cpos[19]+=tk.nextToken();
    }
    if (n < nmax) {
      cpos[n] = null;
    }
////#ifdef TRACE0
//    Trace.level--;
////#endif /* TRACE0 */
    return n;
  }       /* cutstr */
//#endif /* JAVAME */

  /* Apparent Sidereal Time at Greenwich with equation of the equinoxes
   * AA page B6
   *
   * returns sidereal time in hours.
   *
   * Caution. At epoch J2000.0, the 16 decimal precision
   * of IEEE double precision numbers
   * limits time resolution measured by Julian date
   * to approximately 24 microseconds.
   *
   * program returns sidereal hours since sidereal midnight
   * tjd          julian day UT
   * eps          obliquity of ecliptic, degrees
   * nut          nutation, degrees
   */
  /**
  * This calculates the sidereal time from a Julian day number, the
  * obliquity of the eclipse and the nutation (in degrees). You might
  * want to use swe_sidtime(double), if you have just the Julian day
  * number available.<p>
  * @param tjd The Julian day number
  * @param eps Obliquity of the ecliptic
  * @param nut Nutation in degrees
  * @return Sidereal time in degrees.
  * @see #swe_sidtime(double)
  */
  public double swe_sidtime0( double tjd, double eps, double nut ) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_sidtime0(double, double, double)");
//#endif /* TRACE0 */
    double jd0;           /* Julian day at midnight Universal Time */
    double secs;          /* Time of day, UT seconds since UT midnight */
    double eqeq, jd, tu, tt, msday;
    double gmst;
    /* Julian day at given UT */
    jd = tjd;
    jd0 = SMath.floor(jd);
    secs = tjd - jd0;
    if( secs < 0.5 ) {
      jd0 -= 0.5;
      secs += 0.5;
    } else {
      jd0 += 0.5;
      secs -= 0.5;
    }
    secs *= 86400.0;
    tu = (jd0 - SwephData.J2000)/36525.0; /* UT1 in centuries after J2000 */
    if (PREC_IAU_2003) {
      tt = (jd0 + SweDate.getDeltaT(jd0) - SwephData.J2000)/36525.0; /* TT in centuries after J2000 */
      gmst = (((-0.000000002454*tt - 0.00000199708)*tt - 0.0000002926)*tt + 0.092772110)*tt*tt + 307.4771013*(tt-tu) + 8640184.79447825*tu + 24110.5493771;
      /* mean solar days per sidereal day at date tu;
       * for the derivative of gmst, we can assume UT1 =~ TT */
      msday = 1 + ((((-0.000000012270*tt - 0.00000798832)*tt - 0.0000008778)*tt + 0.185544220)*tt + 8640184.79447825)/(86400.*36525.);
    } else {
      /* Greenwich Mean Sidereal Time at 0h UT of date */
      gmst = (( -6.2e-6*tu + 9.3104e-2)*tu + 8640184.812866)*tu + 24110.54841;
      /* mean solar days per sidereal day at date tu, = 1.00273790934 in 1986 */
      msday = 1.0 + ((-1.86e-5*tu + 0.186208)*tu + 8640184.812866)/(86400.*36525.);
    }
    /* Local apparent sidereal time at given UT at Greenwich */
    eqeq = 240.0 * nut * SMath.cos(eps * SwissData.DEGTORAD);
    gmst = gmst + msday*secs + eqeq  /* + 240.0*tlong */;
    /* Sidereal seconds modulo 1 sidereal day */
    gmst = gmst - 86400.0 * SMath.floor( gmst/86400.0 );
    /* return in hours */
    gmst /= 3600;
    return gmst;
  }


  /* sidereal time, without eps and nut as parameters.
   * tjd must be UT !!!
   * for more informsation, see comment with swe_sidtime0()
   */
  /**
  * This calculates the sidereal time from a Julian day number.<p>
  * @param tjd_ut The Julian day number (in UT)
  * @return Sidereal time in degrees.
  * @see #swe_sidtime0(double, double, double)
  */
  public double swe_sidtime(double tjd_ut) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_sidtime(double)");
//#endif /* TRACE0 */
    int i;
    double eps, nutlo[]=new double[2], tsid;
    double tjde = tjd_ut + SweDate.getDeltaT(tjd_ut);
    eps = swi_epsiln(tjde) * SwissData.RADTODEG;
    swi_nutation(tjde, nutlo);
    for (i = 0; i < 2; i++)
      nutlo[i] *= SwissData.RADTODEG;
    tsid = swe_sidtime0(tjd_ut, eps + nutlo[1], nutlo[0]);
    return tsid;
  }


  /* SWISSEPH
   * generates name of ephemeris file
   * file name looks as follows:
   * swephpl.m30, where
   *
   * "sweph"                      "swiss ephemeris"
   * "pl","mo","as"               planet, moon, or asteroid
   * "m"  or "_"                  BC or AD
   *
   * "30"                         start century
   * tjd          = ephemeris file for which julian day
   * ipli         = number of planet
   * fname        = ephemeris file name
   */
//  String swi_gen_filename(double tjd, int ipli, String fname)
  public String swi_gen_filename(SweDate sd, int ipli) {
////#ifdef TRACE0
//    Trace.level++;
//    Trace.log("SwissLib.swi_gen_filename(SweDate, int)");
////#ifdef TRACE1
//    Trace.log("   sd: " + sd + "\n    ipli: " + ipli);
////#endif /* TRACE1 */
////#endif /* TRACE0 */
    int icty;
    int ncties = (int) SwephData.NCTIES;
    int sgn;
    String fname;
////#ifdef ORIGINAL
//    CFmt cv=new CFmt();
////#endif /* ORIGINAL */

    switch(ipli) {
      case SwephData.SEI_MOON:
        fname="semo";
        break;
      case SwephData.SEI_EMB:
      case SwephData.SEI_MERCURY:
      case SwephData.SEI_VENUS:
      case SwephData.SEI_MARS:
      case SwephData.SEI_JUPITER:
      case SwephData.SEI_SATURN:
      case SwephData.SEI_URANUS:
      case SwephData.SEI_NEPTUNE:
      case SwephData.SEI_PLUTO:
      case SwephData.SEI_SUNBARY:
        fname="sepl";
        break;
      case SwephData.SEI_CERES:
      case SwephData.SEI_PALLAS:
      case SwephData.SEI_JUNO:
      case SwephData.SEI_VESTA:
      case SwephData.SEI_CHIRON:
      case SwephData.SEI_PHOLUS:
        fname="seas";
        break;
      default:    /* asteroid */
//#ifdef ORIGINAL
        fname = "ast" + cv.fmt("%d",(ipli - SweConst.SE_AST_OFFSET) / 1000) +
                swed.DIR_GLUE + "se" +
                cv.fmt((ipli - SweConst.SE_AST_OFFSET > 99999?"%06d":"%05d"),
                       ipli - SweConst.SE_AST_OFFSET) + "." +
                SwephData.SE_FILE_SUFFIX;
//#else
        String iplNr="00000" + (ipli - SweConst.SE_AST_OFFSET);
        iplNr = iplNr.substring(iplNr.length()-6);
        String prefix = "s";
        if ((ipli - SweConst.SE_AST_OFFSET <= 99999)) {
          iplNr = iplNr.substring(1);
          prefix = "se";
        }
        fname = "ast" + ((ipli - SweConst.SE_AST_OFFSET) / 1000) +
                swed.DIR_GLUE + prefix + iplNr + "." + SwephData.SE_FILE_SUFFIX;
//#endif /* ORIGINAL */
////#ifdef TRACE0
//        Trace.level--;
////#endif /* TRACE0 */
        return fname;   /* asteroids: only one file 3000 bc - 3000 ad */
        /* break; */
    }
    /* century of tjd */
    /* if sd.tjd > 1600 then gregorian calendar */
    if (sd.getJulDay() >= 2305447.5) {
      sd.setCalendarType(SweDate.SE_GREG_CAL, SweDate.SE_KEEP_JD);
    /* else julian calendar */
    } else {
      sd.setCalendarType(SweDate.SE_JUL_CAL, SweDate.SE_KEEP_JD);
    }
    /* start century of file containing tjd */
    int year = sd.getYear();
    if (year < 0) {
      sgn = -1;
    } else {
      sgn = 1;
    }
    icty = year / 100;
    if (sgn < 0 && year % 100 != 0) {
      icty -=1;
    }
    while(icty % ncties != 0) {
      icty--;
    }
    /* B.C. or A.D. */
    if (icty < 0) {
      fname+="m";
    } else {
      fname+="_";
    }
    icty = SMath.abs(icty);
//  sprintf(fname + strlen(fname), "%02d.%s", icty, SE_FILE_SUFFIX);
    fname+=(icty<10?"0":"")+icty+"."+SwephData.SE_FILE_SUFFIX;
////#ifdef TRACE0
//    Trace.level--;
////#endif /* TRACE0 */
    return fname;
  }

  /*********************************************************
   *  function for splitting centiseconds into             *
   *  ideg        degrees,
   *  imin        minutes,
   *  isec        seconds,
   *  dsecfr      fraction of seconds
   *  isgn        zodiac sign number;
   *              or +/- sign
   *
   *********************************************************/
  public void swe_split_deg(double ddeg, int roundflag, IntObj ideg,
                            IntObj imin, IntObj isec, DblObj dsecfr,
                            IntObj isgn) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_split_deg(double, int, IntObj, IntObj, IntObj, DblObj, IntObj)");
//#endif /* TRACE0 */
    double dadd = 0;
    isgn.val = 1;
    if (ddeg < 0) {
      isgn.val = -1;
      ddeg = -ddeg;
    }
    if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_DEG)!=0) {
      dadd = 0.5;
    } else if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_MIN)!=0) {
      dadd = 0.5 / 60;
    } else if ((roundflag & SweConst.SE_SPLIT_DEG_ROUND_SEC)!=0) {
      dadd = 0.5 / 3600;
    }
    if ((roundflag & SweConst.SE_SPLIT_DEG_KEEP_DEG)!=0) {
      if ((int) (ddeg + dadd) - (int) ddeg > 0) {
        dadd = 0;
      }
    } else if ((roundflag & SweConst.SE_SPLIT_DEG_KEEP_SIGN)!=0) {
      if ((ddeg % 30) + dadd >= 30) {
        dadd = 0;
      }
    }
    ddeg += dadd;
    if ((roundflag & SweConst.SE_SPLIT_DEG_ZODIACAL)!=0) {
      isgn.val = (int) (ddeg / 30);
      ddeg = ddeg % 30;
    }
    ideg.val = (int) ddeg;
    ddeg -= ideg.val;
    imin.val = (int) (ddeg * 60);
    ddeg -= imin.val / 60.0;
    isec.val = (int) (ddeg * 3600);
    if ((roundflag & (SweConst.SE_SPLIT_DEG_ROUND_DEG | SweConst.SE_SPLIT_DEG_ROUND_MIN | SweConst.SE_SPLIT_DEG_ROUND_SEC))==0) {
      dsecfr.val = ddeg * 3600 - isec.val;
    }
  }  /* end split_deg */

//#ifndef ASTROLOGY
  public double swi_kepler(double E, double M, double ecce) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_kepler(double, double, double)");
//#endif /* TRACE0 */
    double dE = 1, E0;
    double x;
    /* simple formula for small eccentricities */
    if (ecce < 0.4) {
      while(dE > 1e-12) {
        E0 = E;
        E = M + ecce * SMath.sin(E0);
        dE = SMath.abs(E - E0);
      }
    /* complicated formula for high eccentricities */
    } else {
      while(dE > 1e-12) {
        E0 = E;
        /*
         * Alois 21-jul-2000: workaround an optimizer problem in gcc
         * swi_mod2PI sees very small negative argument e-322 and returns +2PI;
         * we avoid swi_mod2PI for small x.
         */
        x = (M + ecce * SMath.sin(E0) - E0) / (1 - ecce * SMath.cos(E0));
        dE = SMath.abs(x);
        if (dE < 1e-2) {
          E = E0 + x;
        } else {
          E = swi_mod2PI(E0 + x);
          dE = SMath.abs(E - E0);
        }
      }
    }
    return E;
  }
//#endif /* ASTROLOGY */

//#ifndef ASTROLOGY
  public void swi_FK4_FK5(double xp[], double tjd) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_FK4_FK5(double[], double)");
//#endif /* TRACE0 */
    if (xp[0] == 0 && xp[1] == 0 && xp[2] == 0) {
      return;
    }
    swi_cartpol(xp, xp);
    /* according to Expl.Suppl., p. 167f. */
    xp[0] += (0.035 + 0.085 * (tjd - SwephData.B1950) / 36524.2198782) / 3600 * 15 * SwissData.DEGTORAD;
    xp[3] += (0.085 / 36524.2198782) / 3600 * 15 * SwissData.DEGTORAD;
    swi_polcart(xp, xp);
  }
//#endif /* ASTROLOGY */

//#ifndef ASTROLOGY
  public void swi_FK5_FK4(double[] xp, double tjd) {
//#ifdef TRACE0
    Trace.log("SwissLib.swi_FK5_FK4(double[], double)");
//#endif /* TRACE0 */
    if (xp[0] == 0 && xp[1] == 0 && xp[2] == 0) {
      return;
    }
    swi_cartpol(xp, xp);
    /* according to Expl.Suppl., p. 167f. */
    xp[0] -= (0.035 + 0.085 * (tjd - SwephData.B1950) / 36524.2198782) / 3600 * 15 * SwissData.DEGTORAD;
    xp[3] -= (0.085 / 36524.2198782) / 3600 * 15 * SwissData.DEGTORAD;
    swi_polcart(xp, xp);
  }
//#endif /* ASTROLOGY */

//////////////////////////////////////////////////////////////////////////////
// swejpl.c: /////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

  /*************************************
  double to int32 with rounding, no overflow check
  *************************************/
  public int swe_d2l(double x) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_d2l(double)");
//#endif /* TRACE0 */
    if (x >=0.) {
      return ((int) (x + 0.5));
    } else {
      return (- (int) (0.5 - x));
    }
  }

  /**
  * This calculates the difference of the two angles p1, p2 and normalizes
  * them to a range of -180.0 <= x < 180.0 degrees.
  * @param p1 The angle of point 1
  * @param p2 The angle of point 2
  * @return The normalized difference between p1, p2
  */
  public double swe_difdeg2n(double p1, double p2) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_difdeg2n(double, double)");
//#endif /* TRACE0 */
    double dif;
    dif = swe_degnorm(p1 - p2);
    if (dif  >= 180.0) {
      return (dif - 360.0);
    }
    return (dif);
  }

// Well: used by Swetest.java... //#ifndef ASTROLOGY
  public double swe_difrad2n(double p1, double p2) {
//#ifdef TRACE0
    Trace.log("SwissLib.swe_difrad2n(double, double)");
//#endif /* TRACE0 */
    double dif;
    dif = swe_radnorm(p1 - p2);
    if (dif  >= SwephData.TWOPI / 2) {
      return (dif - SwephData.TWOPI);
    }
    return (dif);
  }
// Well: used by Swetest.java... //#endif /* ASTROLOGY */

//////////////////////////////////////////////////////////////////////////////
// In this Java port only: ///////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  /**
  * This method emulates the C version of atof() allowing <i>any</i> string
  * to be parsed into a number.
  */
  public static synchronized double atof(String src) {
    // atof() (in C) allows extra strings after the number, and even no number
    // at all, so we have to work around this...
    int idx=0;
    src=src.trim();
    while(idx<src.length() &&
         (Character.isDigit(src.charAt(idx)) || src.charAt(idx)=='.')) {
      idx++;
    }
    String sout=src.substring(0,idx).trim();
    if (sout.length()==0 || sout.replace('.',' ').trim().length()==0) {
      return 0.;
    }
    return Double.valueOf(sout).doubleValue();
  }

  /**
  * This method emulates the C version of atoi() allowing <i>any</i> string
  * to be parsed into an integer.
  */
  public static synchronized int atoi(String src) {
    // atoi() (in C) allows extra strings after the number, and even no number
    // at all, so we have to work around this...
    int idx=0;
    src=src.trim();
    while(idx<src.length() && Character.isDigit(src.charAt(idx))) {
      idx++;
    }
    String sout=src.substring(0,idx).trim();
    if (sout.length()==0 || sout.replace('.',' ').trim().length()==0) {
      return 0;
    }
    return Integer.valueOf(sout).intValue();
  }

static final double PREC_IAU_CTIES=2.0; // J2000 +/- two centuries

} // End of class SwissLib.
