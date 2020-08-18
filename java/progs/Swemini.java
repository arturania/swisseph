#ifdef J2ME
#define JAVAME
#endif /* J2ME */
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
  $Header: /home/dieter/sweph/RCS/swemini.c,v 1.74 2008/06/16 10:07:20 dieter Exp $

  swemini.c     A minimal program to test the Swiss Ephemeris.

  Input: a date (in gregorian calendar, sequence day.month.year)
  Output: Planet positions at midnight Universal time, ecliptic coordinates,
          geocentric apparent positions relative to true equinox of date, as
          usual in western astrology.


  Authors: Dieter Koch and Alois Treindl, Astrodienst Zurich

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

public class Swemini
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  SweDate sd=new SweDate();
  SwissData swed=new SwissData();
  SwissEph sw=new SwissEph();
  CFmt f=new CFmt();

  public static void main(String[] p) {
    Swemini sw=new Swemini();
    sw.main_start();
  }

  private void main_start() {
    String sdate="", snam;
snam=null; // Realistisch?
    StringBuffer serr=new StringBuffer();
    int jday = 1, jmon = 1, jyear = 2000;
    double jut = 0.0;
    double tjd, te, x2[]=new double[6];
    long iflag, iflgret;
    int p;
    iflag = SweConst.SEFLG_SPEED;
    while (true) {
      System.out.print("\nDate (d.m.y) ?");
      /*gets(sdate);*/
      try {
        InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader bin=new BufferedReader(in);
        sdate=bin.readLine();
      } catch (IOException ie) {
        System.out.println(ie.getMessage());
        System.exit(SweConst.OK);
      }
      if( "".equals(sdate) ) System.exit(SweConst.OK);
      /*
       * stop if a period . is entered
       */
      if (sdate.equals("."))
        return;
//      if (sscanf (sdate, "%d%*c%d%*c%d", &jday,&jmon,&jyear) < 1) exit(1);
      jday=jmon=jyear=0;
      int i=0;
      try {
        while (Character.isDigit(sdate.charAt(i))) {
          jday=jday*10+Character.digit(sdate.charAt(i++),10);
        }
        while (!Character.isDigit(sdate.charAt(i))) { i++; }
        while (Character.isDigit(sdate.charAt(i))) {
          jmon=jmon*10+Character.digit(sdate.charAt(i++),10);
        }
        while (!Character.isDigit(sdate.charAt(i))) { i++; }
        while (i<sdate.length() && Character.isDigit(sdate.charAt(i))) {
          jyear=jyear*10+Character.digit(sdate.charAt(i++),10);
        }
      } catch (ArrayIndexOutOfBoundsException aob) {
        System.exit(1);
      }
      /*
       * we have day, month and year and convert to Julian day number
       */
      sd.setDate(jyear,jmon,jday,jut);
      sd.setCalendarType(sd.SE_GREG_CAL,sd.SE_KEEP_DATE);
      tjd=sd.getJulDay();
      /*
       * compute Ephemeris time from Universal time by adding delta_t
       */
      te = tjd + sd.getDeltaT(tjd);
      System.out.print("date: "+f.fmt("%02d",jday)+"."+f.fmt("%02d",jmon)+"."+
                       jyear+" at 0:00 Universal time\n");
      System.out.print("planet     \tlongitude\tlatitude\tdistance\t"+
                       "speed long.\n");
      /*
       * a loop over all planets
       */
      for (p = SweConst.SE_SUN; p <= SweConst.SE_CHIRON; p++) {
        if (p == SweConst.SE_EARTH) continue;
        /*
         * do the coordinate calculation for this planet p
         */
        iflgret = sw.swe_calc(te, p, (int)iflag, x2, serr);
        /*
         * if there is a problem, a negative value is returned and an
         * errpr message is in serr.
         */
        if (iflgret < 0)
  	  System.out.print("error: "+serr.toString()+"\n");
        else if (iflgret != iflag)
          System.out.print("warning: iflgret != iflag. "+serr.toString()+"\n");
        /*
         * get the name of the planet p
         */
        snam=sw.swe_get_planet_name(p);
        /*
         * print the coordinates
         */
        System.out.print(f.fmt("%10s",snam)+"\t"+f.fmt("%11.7f",x2[0])+"\t"+
                         f.fmt("%10.7f",x2[1])+"\t"+f.fmt("%10.7f",x2[2])+"\t"+
                         f.fmt("%10.7f",x2[3])+"\n");
      }
    }
  }
}
