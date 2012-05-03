//#ifdef J2ME
//#define JAVAME
//#endif /* J2ME */
//#ifdef TRACE0
//#define ORIGINAL
//#endif /* TRACE0 */
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

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

/**
* This class is a date class specialized for the use with the swisseph
* package. You will like to use it, if you need a Julian Day number or
* the deltaT for a date or a Julian Day or if like to convert from Gregorian
* to Julian calendar system or vice versa.<P>
* This is a port of the SwissEphemeris package to Java. See
* <A HREF="http://www.astro.ch">Astrodienst Z&uuml;rich</A>
* for more infos and the original authors.
* <P><I><B>You will find the complete documentation for the original
* SwissEphemeris package at <A HREF="http://www.astro.ch/swisseph/sweph_g.htm">
* http://www.astro.ch/swisseph/sweph_g.htm</A>. By far most of the information 
* there is directly valid for this port to Java as well.</B></I>
* @author Thomas Mack / mack@ifis.cs.tu-bs.de
* @version 1.0.0c
*/
public class SweDate
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  private static SwissEph sw = new SwissEph();

  /**
  * Constant for weekdays. SUNDAY is equal to 0.
  */
  public static final int SUNDAY=0;
  /**
  * Constant for weekdays. MONDAY is equal to 1.
  */
  public static final int MONDAY=1;
  /**
  * Constant for weekdays. TUESDAY is equal to 2.
  */
  public static final int TUESDAY=2;
  /**
  * Constant for weekdays. WEDNESDAY is equal to 3.
  */
  public static final int WEDNESDAY=3;
  /**
  * Constant for weekdays. THURSDAY is equal to 4.
  */
  public static final int THURSDAY=4;
  /**
  * Constant for weekdays. FRIDAY is equal to 5.
  */
  public static final int FRIDAY=5;
  /**
  * Constant for weekdays. SATURDAY is equal to 6.
  */
  public static final int SATURDAY=6;

  public static final boolean SE_JUL_CAL=false;
  public static final boolean SE_GREG_CAL=true;
  public static final boolean SE_KEEP_DATE=true;
  public static final boolean SE_KEEP_JD=false;

  public boolean init_leapseconds_done=false;


// for delta t: tidal acceleration in the mean motion of the moon

  /**
  * Tidal acceleration value in the mean motion of the moon of DE403 (-25.8).
  */
  public static final double SE_TIDAL_DE403=-25.8;
  /**
  * Tidal acceleration value in the mean motion of the moon of DE404 (-25.8).
  */
  public static final double SE_TIDAL_DE404=-25.8;
  /**
  * Tidal acceleration value in the mean motion of the moon of DE405 (-25.7376).
  */
  public static final double SE_TIDAL_DE405=-25.7376;
  /**
  * Tidal acceleration value in the mean motion of the moon of DE406 (-25.7376).
  */
  public static final double SE_TIDAL_DE406=-25.7376;
  /**
  * Tidal acceleration value in the mean motion of the moon of DE200 (-23.8946).
  */
  public static final double SE_TIDAL_DE200=-23.8946;
  /**
  * Tidal acceleration value in the mean motion of the moon of -26.
  */
  public static final double SE_TIDAL_26=-26.0;
  /**
  * Default tidal acceleration value in the mean motion of the moon (=SE_TIDAL_DE406).
  * @see #SE_TIDAL_DE406
  */
  public static final double SE_TIDAL_DEFAULT=SE_TIDAL_DE406;



  /**
  * The Julian day number of 1970 January 1.0. Useful for conversions
  * from or to a Date object.
  * @see #getDate(long)
  */
  public static final double JD0=2440587.5;          /* 1970 January 1.0 */

  private double tid_acc = SE_TIDAL_DEFAULT;
  private static boolean init_dt_done = false;
  private double jd;
  // JD for the start of the Gregorian calendar system (October 15, 1582):
  private double jdCO = 2299160.5;
  private boolean calType;
  private int year;
  private int month;
  private int day;
  private double hour;
  private double deltaT;
  private boolean deltatIsValid=false;


  //////////////////////////////////////////////////////////////////////////////
  // Constructors //////////////////////////////////////////////////////////////

  // The following constructors keep julian day in favor of date:
  /**
  * This constructs a new SweDate with a default of the current date
  * and current time at GMT.
  */
  public SweDate() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate()");
//#endif /* TRACE0 */
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    setFields(cal.get(Calendar.YEAR),
          cal.get(Calendar.MONTH) + 1,
          cal.get(Calendar.DAY_OF_MONTH),
          cal.get(Calendar.HOUR_OF_DAY) +
                cal.get(Calendar.MINUTE)/60. +
                cal.get(Calendar.SECOND)/3600. +
                cal.get(Calendar.MILLISECOND)/3600000.,
          SE_GREG_CAL);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }
  /**
  * This constructs a new SweDate with the given Julian Day number.
  * The calendar system will be Gregorian after October 15, 1582 or
  * Julian before that date.
  * @param jd Julian Day number
  */
  public SweDate(double jd) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate(double)");
//#endif /* TRACE0 */
    initDateFromJD(jd, jdCO<=jd?SE_GREG_CAL:SE_JUL_CAL);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }
  /**
  * This constructs a new SweDate with the given Julian Day number.
  * The dates will be calculated according to the given calendar system
  * (Gregorian or Julian calendar).
  * @param jd Julian Day number
  * @param calType calendar type (Gregorian or Julian calendar system)
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  public SweDate(double jd, boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate(double, boolean)");
//#endif /* TRACE0 */
    initDateFromJD(jd, calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * This constructs a new SweDate with the given date and time. The calendar
  * type is automatically adjusted to Julian calendar system before October 15,
  * 1582, and to Gregorian calendar system after and including that date. The
  * dates from October 5 to October 14, 1582 had been skipped during the
  * conversion to the Gregorian calendar, so we just convert any such date to
  * Julian calendar system even though no such date did exist.
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number in a month of that date
  * @param hour The hour of the day
  */
  public SweDate(int year, int month, int day, double hour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate(int, int, int, double)");
//#endif /* TRACE0 */
    setFields(year, month, day, hour);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }
  /**
  * This constructs a new SweDate with the given date and time. The
  * date numbers will be interpreted according to the given calendar
  * system (Gregorian or Julian calendar).
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number of the date
  * @param hour The hour of the day
  * @param calType calendar type (Gregorian or Julian calendar system)
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  public SweDate(int year, int month, int day, double hour, boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate(int, int, int, double, boolean)");
//#endif /* TRACE0 */
     setFields(year, month, day, hour, calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
   }
  // End of constructors
  //////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////
  // Public methods: ///////////////////////////////////////////////////////////


  //////////////////////////////////////////////////////////////////////////////
  // Access to private variables ///////////////////////////////////////////////
  // Read access: //
  /**
  * Queries the Julian Day number of this object.
  * @return Julian Day number
  */
  public double getJulDay() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getJulDay()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.jd;
  }
  /**
  * Queries the Julian Day number of the given date in Gregorian calendar
  * system - this is a static method.
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number of the date
  * @param hour The hour of the day
  * @return Julian Day number
  */
  public static double getJulDay(int year, int month, int day, double hour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getJulDay(int, int, int, double)");
//#endif /* TRACE0 */
    double sjd = swe_julday(year, month, day, hour, SE_GREG_CAL);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sjd;
  }
  /**
  * Queries the Julian Day number of the given date that is interpreted as
  * a date in the given calendar system - this is a static method.
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number of the date
  * @param hour The hour of the day
  * @param calType calendar type (Gregorian or Julian calendar system)
  * @return Julian Day number
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  public static double getJulDay(int year, int month, int day, double hour,
                                 boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getJulDay(int, int, int, double, boolean)");
//#endif /* TRACE0 */
    double sjd = swe_julday(year, month, day, hour, calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sjd;
  }

  // 0=Sunday, 1=Monday etc.
  /**
  * Queries the day of the week, i.e. Sunday to Saturday as represented by
  * an integer. Sunday is represented by 0, Saturday by 6. Any discontinuity
  * in the sequence of weekdays is <b>not</b> taken into account!
  * <B>Attention: the numbers are different from the numbers returned by the
  * java.awt.Calendar class!</B>
  * @return Number of the day of week
  * @see #SUNDAY
  * @see #MONDAY
  * @see #TUESDAY
  * @see #WEDNESDAY
  * @see #THURSDAY
  * @see #FRIDAY
  * @see #SATURDAY
  */
  public int getDayOfWeekNr() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDayOfWeekNr()");
    Trace.level--;
//#endif /* TRACE0 */
    return ((int)(this.jd-5.5))%7;
  }
  /**
  * Queries the day of the week of the given Julian Day number (interpreted
  * in the gregorian calendar system!). Sunday is represented by 0, Saturday
  * by 6. Any discontinuity in the sequence of weekdays is <b>not</b> taken
  * into account! <B>Attention: the numbers are different from the numbers
  * returned by the java.awt.Calendar class!</B>
  * @param jd The Julian Day number of the date
  * @return Number of the day of week
  * @see #SUNDAY
  * @see #MONDAY
  * @see #TUESDAY
  * @see #WEDNESDAY
  * @see #THURSDAY
  * @see #FRIDAY
  * @see #SATURDAY
  */
  public static synchronized int getDayOfWeekNr(double jd) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDayOfWeekNr(double)");
    Trace.level--;
//#endif /* TRACE0 */
    return ((int)(jd-5.5))%7;
  }
  /**
  * Queries the day of the week of the given date that is interpreted as
  * being a date in the Gregorian or Julian calendar system depending on
  * the date, the switch from Julian to Gregorian calendar system occured.
  * Sunday is represented by 0, Saturday by 6. Any discontinuity in the
  * sequence of weekdays is <b>not</b> taken into account! <B>Attention:
  * the numbers are different from the numbers returned by the
  * java.awt.Calendar class!</B>
  * @return Number of the day of week
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number of the date
  * @see #SUNDAY
  * @see #MONDAY
  * @see #TUESDAY
  * @see #WEDNESDAY
  * @see #THURSDAY
  * @see #FRIDAY
  * @see #SATURDAY
  */
  public static int getDayOfWeekNr(int year, int month, int day) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDayOfWeekNr(int, int, int)");
//#endif /* TRACE0 */
    int sdow = ((int)(swe_julday(year, month, day, 0.0, SE_GREG_CAL)-5.5))%7;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sdow;
  }

  /**
  * Queries the day of the week of the given date that is interpreted as
  * being a date in the given calendar system. Sunday is represented by 0,
  * Saturday by 6. Any discontinuity in the sequence of weekdays is
  * <b>not</b> taken into account! <B>Attention: the numbers are different
  * from the numbers returned by the java.awt.Calendar class!</B>
  * @return Number of the day of week
  * @param year The year of the date
  * @param month The month of the date
  * @param day The day-number of the date
  * @param calType calendar type (Gregorian or Julian calendar system)
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  * @see #SUNDAY
  * @see #MONDAY
  * @see #TUESDAY
  * @see #WEDNESDAY
  * @see #THURSDAY
  * @see #FRIDAY
  * @see #SATURDAY
  */
  public static int getDayOfWeekNr(int year, int month, int day,
                                   boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDayOfWeekNr(int, int, int, boolean)");
//#endif /* TRACE0 */
    int sdow = ((int)(swe_julday(year, month, day, 0.0, calType)-5.5))%7;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sdow;
  }

  /**
  * Queries the type of calendar in effect - Gregorian or Julian calendar.
  * This will effect what date you will get back for a given Julian Day.
  * @return Calendar type
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  public boolean getCalendarType() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getCalendarType()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.calType;
  }

  /**
  * Queries the year of this SweDate object.
  * @return year
  */
  public int getYear() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getYear()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.year;
  }
//  int getYear(double jd /*, boolean calType ?*/) { swe_revjul(jd,calType); }

  /**
  * Queries the month of this SweDate object.
  * @return month <B>Attention:</B> The month ranges from 1 to 12, this is
  * different to the java.util.Calendar class!
  */
  public int getMonth() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getMonth()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.month;
  }

  /**
  * Queries the day of this SweDate object.
  * @return day number
  */
  public int getDay() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDay()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.day;
  }

  /**
  * Queries the hour of the day of this SweDate object.
  * @return hour
  */
  public double getHour() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getHour()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.hour;
  }

  /**
  * Queries the delta T value for the date of this object.
  * @return delta T
  */
  public double getDeltaT() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDeltaT()");
//#endif /* TRACE0 */
    if (deltatIsValid) { return this.deltaT; }
    this.deltaT=calc_deltaT(this.getJulDay());
    deltatIsValid=true;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return this.deltaT;
  }

  /**
  * Queries the delta T value for the given Julian Day number - this is a
  * static method. Delta T is calculated with a tidal acceleration of
  * SE_TIDAL_DEFAULT.
  * @param tjd Julian Day number
  * @return delta T
  * @see #SE_TIDAL_DEFAULT
  */
  public static double getDeltaT(double tjd) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDeltaT(double)");
//#endif /* TRACE0 */
    double sdt = calc_deltaT(tjd, SE_TIDAL_DEFAULT);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sdt;
  }

  /**
  * This will return a java.util.Date object with the date of this
  * SweDate object. This is needed often in internationalisation of date
  * and time formats. You can add an offset in milliseconds to account for
  * timezones or daylight savings time, as SweDate is meant to be in GMT
  * time always.
  * @param offset An offset in milliseconds to be added to the current
  * date and time.
  */
  public Date getDate(long offset) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDate(long)");
//#endif /* TRACE0 */
    long millis=(long)((getJulDay()-JD0)*24L*3600L*1000L)+offset;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return new Date(millis);
  }

  /**
  * This will return a java.util.Date object from a julian day number.
  * @param jd The julian day number for which to create a Date object.
  */
  public static Date getDate(double jd) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getDate(double)");
//#endif /* TRACE0 */
    long millis=(long)((jd-JD0)*24L*3600L*1000L);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return new Date(millis);
  }
  // End of read access //


  // Write access: //
  /**
  * Sets the new Julian Day for this object. This operation does NOT
  * change the calendar type (Gregorian or Julian calendar). Use methods
  * setCalendarType() or updateCalendarType() for this.
  * @param newJD Julian Day number
  */
  public void setJulDay(double newJD) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setJulDay(double)");
//#endif /* TRACE0 */
    this.jd=newJD;
    deltatIsValid=false;
    IDate dt=swe_revjul(newJD,this.calType);
    this.year=dt.year;
    this.month=dt.month;
    this.day=dt.day;
    this.hour=dt.hour;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Sets the calendar type for this object.
  * @param newCalType Calendar type (Greogorian or Julian calendar)
  * @param keepDate Determines, if the date or the julian day should
  * be fix in this operation.
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  * @see #SE_KEEP_DATE
  * @see #SE_KEEP_JD
  */
  public void setCalendarType(boolean newCalType, boolean keepDate) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setCalendarType(boolean, boolean)");
//#endif /* TRACE0 */
    if (this.calType != newCalType) {
      this.calType=newCalType;
      deltatIsValid=false;
      if (keepDate) {
        this.jd=swe_julday(this.year, this.month, this.day,
                           this.hour, this.calType);
      } else {
        IDate dt=swe_revjul(this.jd,newCalType);
        this.year=dt.year;
        this.month=dt.month;
        this.day=dt.day;
        this.hour=dt.hour;
      }
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Update the calendar type according to the Gregorian calendar start
  * date and the date of this object.
  */
  public void updateCalendarType() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.updateCalendarType()");
//#endif /* TRACE0 */
    this.calType=(this.jdCO<=this.jd?SE_GREG_CAL:SE_JUL_CAL);;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }


  // Date:
  /**
  * Sets a new date for this object.
  * @param newYear the year-part of the new date
  * @param newMonth the month-part of the new date [1-12]
  * @param newDay the day-part of the new date [1-31]
  * @param newHour the hour of the new date
  * @return true
  */
  public boolean setDate(int newYear, int newMonth, int newDay,
                         double newHour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setDate(int, int, int, double)");
//#endif /* TRACE0 */
    this.year=newYear;
    this.month=newMonth;
    this.day=newDay;
    this.hour=newHour;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }

  /**
  * Sets a new date for this object. The input can be checked, if it is a
  * valid date and can be modified, if not. See parameter "check".
  * @param newYear the year-part of the new date
  * @param newMonth the month-part of the new date [1-12]
  * @param newDay the day-part of the new date [1-31]
  * @param newHour the hour of the new date
  * @param check if true it returns if the date is valid and converts the
  * the date into a valid date
  * @return true, if check==false, or if the date is valid. False otherwise
  */
  public boolean setDate(int newYear, int newMonth, int newDay, double newHour,
                         boolean check) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setDate(int, int, int, double, boolean)");
//#endif /* TRACE0 */
    this.year=newYear;
    this.month=newMonth;
    this.day=newDay;
    this.hour=newHour;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);
    if (check) {
      double oldMonth=newMonth;
      double oldDay=newDay;
      double oldHour=newHour;
      IDate dt=swe_revjul(this.jd,this.calType);  // -> erzeugt neues Datum
      this.year=dt.year;
      this.month=dt.month;
      this.day=dt.day;
      this.hour=dt.hour;
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return (this.year==newYear &&
          this.month==oldMonth &&
          this.day==oldDay &&
          Math.abs(this.hour-oldHour)<1E-6);
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }


  // Year:
  /**
  * Sets the year-part of the date.
  * @param newYear The new year
  * @return true
  */
  public boolean setYear(int newYear) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setYear(int)");
//#endif /* TRACE0 */
    this.year=newYear;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }

  /**
  * Sets the year-part of the date. The input can be checked, if the result
  * is a valid date and can be modified, if not. E.g., the date was 29th of
  * february 2004, and the year gets set to 2001. 2001 does not have a
  * 29th of february, so if parameter check is set to true, it will
  * return false and modify the date to 1st of march 2001.
  * @param newYear The new year
  * @param check check, if the resulting new date is a valid date and
  * adjust the values for day, month or year if necessary
  * @return true if check==false, or if the date is valid. False otherwise
  */
  public boolean setYear(int newYear, boolean check) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setYear(int, boolean)");
//#endif /* TRACE0 */
    this.year=newYear;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
    if (check) {
      double oldMonth=this.month;
      double oldDay=this.day;
      IDate dt=swe_revjul(this.jd,this.calType);  // -> erzeugt neues Datum
      this.year=dt.year;
      this.month=dt.month;
      this.day=dt.day;
      this.hour=dt.hour;
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return (this.year==newYear && this.month==oldMonth && this.day==oldDay);
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }


  /**
  * Sets the month-part of the date.
  * @param newMonth The new month
  * @return true
  */
  // Monat:
  public boolean setMonth(int newMonth) {
//#ifdef TRACE0
    Trace.log("SweDate.setMonth(int)");
//#endif /* TRACE0 */
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setMonth(int)");
//#endif /* TRACE0 */
    this.month=newMonth;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }

  /**
  * Sets the year-part of the date. The input can be checked, if the result
  * is a valid date and can be modified, if not.
  * @param newMonth The new year
  * @param check check, if the resulting new date is a valid date and
  * adjust the values for day, month or year if necessary
  * @return true, if check==true, otherwise return true only, if the date is
  * valid
  * @see SweDate#setYear(int, boolean)
  */
  public boolean setMonth(int newMonth, boolean check) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setMonth(int, boolean)");
//#endif /* TRACE0 */
    this.month=newMonth;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
    if (check) {
      double oldYear=this.year;
      double oldDay=this.day;
      IDate dt=swe_revjul(this.jd,this.calType);  // -> erzeugt neues Datum
      this.year=dt.year;
      this.month=dt.month;
      this.day=dt.day;
      this.hour=dt.hour;
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return (this.year==oldYear && this.month==newMonth && this.day==oldDay);
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }


  // Tag:
  /**
  * Sets the day-part of the date.
  * @param newDay The new day
  * @return true
  */
  public boolean setDay(int newDay) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setDay(int)");
//#endif /* TRACE0 */
    this.day=newDay;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }

  /**
  * Sets the day-part of the date. The input can be checked, if the result
  * is a valid date and can be modified, if not.
  * @param newDay The new day
  * @param check check, if the resulting new date is a valid date and
  * adjust the values for day, month or year if necessary
  * @return true, if check==true, otherwise return true only, if the date is
  * valid
  * @see SweDate#setYear(int, boolean)
  */
  public boolean setDay(int newDay, boolean check) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setDay(int, boolean)");
//#endif /* TRACE0 */
    this.day=newDay;
    deltatIsValid=false;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);  // -> erzeugt JD
    if (check) {
      double oldYear=this.year;
      double oldMonth=this.month;
      IDate dt=swe_revjul(this.jd,this.calType);  // -> erzeugt neues Datum
      this.year=dt.year;
      this.month=dt.month;
      this.day=dt.day;
      this.hour=dt.hour;
//#ifdef TRACE0
      Trace.level--;
//#endif /* TRACE0 */
      return (this.year==oldYear && this.month==oldMonth && this.day==newDay);
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }


  // Time:
  /**
  * Sets a new hour.
  * @param newHour The new hour
  * @return true
  */
  public boolean setHour(double newHour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setHour(double)");
//#endif /* TRACE0 */
    this.hour=newHour;
    this.jd=swe_julday(this.year, this.month, this.day,
                       this.hour, this.calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return true;
  }


  // Datum ueberpruefen:
  /**
  * Checks the date to see, if it is a valid date.
  * @return true, if the date is valid, false, if not
  */
  public boolean checkDate() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.checkDate()");
//#endif /* TRACE0 */
    boolean cd = checkDate(this.year, this.month, this.day, this.hour);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return cd;
  }

  /**
  * Checks the given date to see, if it is a valid date.
  * @param year the year, for which is to be checked
  * @param month the month, for which is to be checked
  * @param day the day, for which is to be checked
  * @return true, if the date is valid, false, if not
  */
  public boolean checkDate(int year, int month, int day) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.checkDate(int, int, int)");
//#endif /* TRACE0 */
    boolean cd = checkDate(year, month, day, 0.0);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return cd;
  }

  /**
  * Checks the given date to see, if it is a valid date.
  * @param year the year, for which is to be checked
  * @param month the month, for which is to be checked
  * @param day the day, for which is to be checked
  * @param hour the hour, for which is to be checked
  * @return true, if the date is valid, false, if not
  */
  public boolean checkDate(int year, int month, int day, double hour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.checkDate(int, int, int, hour)");
//#endif /* TRACE0 */
    double jd=swe_julday(year,month,day,hour,SE_GREG_CAL);
    IDate dt=swe_revjul(jd,SE_GREG_CAL);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return (dt.year==year && dt.month==month && dt.day==day);
  }

  /**
  * Makes the date to be a valid date.
  */
  public void makeValidDate() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.makeValidDate()");
//#endif /* TRACE0 */
    double jd=swe_julday(this.year,this.month,this.day,this.hour,SE_GREG_CAL);
    IDate dt=swe_revjul(jd,SE_GREG_CAL);
    this.year=dt.year;
    this.month=dt.month;
    this.day=dt.day;
    this.hour=dt.hour;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Returns the julian day number on which the Gregorian calendar system
  * comes to be in effect.
  */
  public double getGregorianChange() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getGregorianChange()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.jdCO;
  }

  /**
  * Changes the date of the start of the Gregorian calendar system.
  * This method will keep the date and change the julian day number
  * of the date of this SweDate object if required.
  * @param year The year (in Gregorian system) for the new start date
  * @param month The month (in Gregorian system) for the new start date.
  * Adversely to java.util.Calendar, the month is to be given in the
  * range of 1 for January to 12 for December!
  * @param day The day of the month (in Gregorian system, from 1 to 31)
  * for the new start date
  */
  public void setGregorianChange(int year, int month, int day) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setGregorianChange(int, int, int)");
//#endif /* TRACE0 */
    this.year = year;
    this.month = month;
    this.day = day;
    deltatIsValid = false;
    this.calType = SE_GREG_CAL;
    if (this.year < year ||
        (this.year == year && this.month < month) ||
        (this.year == year && this.month == month && this.day < day)) {
      this.calType = SE_JUL_CAL;
    }
    this.jdCO = swe_julday(year, month, day, 0., SE_GREG_CAL);
    this.jd = swe_julday(this.year, this.month, this.day, this.hour,
                         this.calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Changes the date of the start of the Gregorian calendar system.
  * This method will keep the julian day number and change year,
  * month and day of the date of this SweDate object if required.
  * @param newJDCO The julian day number, on which the Gregorian calendar
  * came into effect.
  */
  public void setGregorianChange(double newJDCO) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setGregorianChange(double)");
//#endif /* TRACE0 */
    this.jdCO = newJDCO;
    this.calType = (this.jd>=this.jdCO?SE_GREG_CAL:SE_JUL_CAL);
    IDate dt = swe_revjul(this.jd,this.calType);
    this.year = dt.year;
    this.month = dt.month;
    this.day = dt.day;
    this.hour = dt.hour;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }
  // End of access to private variables ////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////

  /**
  * Returns the tidal acceleration used in calculations of delta T.
  * @return Tidal acceleration
  */
  public double getTidalAcc() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.getTidalAcc()");
    Trace.level--;
//#endif /* TRACE0 */
    return this.tid_acc;
  }

  /**
  * Sets the tidal acceleration used in calculations of delta T.
  * @param tid_acc tidal acceleration
  * @see #SE_TIDAL_DE403
  * @see #SE_TIDAL_DE404
  * @see #SE_TIDAL_DE405
  * @see #SE_TIDAL_DE406
  * @see #SE_TIDAL_DE200
  * @see #SE_TIDAL_26
  * @see #SE_TIDAL_DEFAULT
  */
  public void setTidalAcc(double tid_acc) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setTidalAcc(double)");
//#endif /* TRACE0 */
    this.tid_acc=tid_acc;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Returns the date, calendar type (gregorian / julian), julian day
  * number and the deltaT value of this object.
  * @return Infos about this object
  */
  public String toString() {
    double hour = getHour();
    String h = (int)hour + ":";
    hour = 60 * (hour - (int)hour);
    h += (int)hour + ":";
    hour = 60 * (hour - (int)hour);
    h += hour ;
               
    return "(YYYY/MM/DD) " +
           getYear() + "/" +
           (getMonth()<10?"0":"") + getMonth() + "/" +
           (getDay()<10?"0":"") + getDay() + ", " +
           h + "h " +
           (getCalendarType()?"(greg)":"(jul)") + "\n" +
           "Jul. Day: " + getJulDay() + "; " +
           "DeltaT: " + getDeltaT();
  }

  // End of public methods /////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Private methods: //////////////////////////////////////////////////////////
  private static synchronized double swe_julday(int year, int month, int day,
                                                double hour, boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.julday(int, int, int, double, boolean)");
//#endif /* TRACE0 */
    double jd;
    double u,u0,u1,u2;
    u = year;
    if (month < 3) { u -=1; }
    u0 = u + 4712.0;
    u1 = month + 1.0;
    if (u1 < 4) { u1 += 12.0; }
    jd = SMath.floor(u0*365.25)
       + SMath.floor(30.6*u1+0.000001)
       + day + hour/24.0 - 63.5;
    if (calType == SE_GREG_CAL) {
      u2 = SMath.floor(SMath.abs(u) / 100) - SMath.floor(SMath.abs(u) / 400);
      if (u < 0.0) {
        u2 = -u2;
      }
      jd = jd - u2 + 2;
      if ((u < 0.0) && (u/100 == SMath.floor(u/100)) &&
                          (u/400 != SMath.floor(u/400))) {
        jd -=1;
      }
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return jd;
  }


  //////////////////////////////////////////////////////////////////////
  // Erzeugt aus einem jd/calType Jahr, Monat, Tag und Stunde.        //
  // It does NOT change any global variables.                         //
  //////////////////////////////////////////////////////////////////////
  private synchronized IDate swe_revjul (double jd, boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.swe_revjul(double, boolean)");
//#endif /* TRACE0 */
    IDate dt=new IDate();
    double u0,u1,u2,u3,u4;

    u0 = jd + 32082.5;
    if (calType == SE_GREG_CAL) {
      u1 = u0 + SMath.floor (u0/36525.0) - SMath.floor (u0/146100.0) - 38.0;
      if (jd >= 1830691.5) {
        u1 +=1;
      }
      u0 = u0 + SMath.floor (u1/36525.0) - SMath.floor (u1/146100.0) - 38.0;
    }
    u2 = SMath.floor (u0 + 123.0);
    u3 = SMath.floor ( (u2 - 122.2) / 365.25);
    u4 = SMath.floor ( (u2 - SMath.floor (365.25 * u3) ) / 30.6001);
    dt.month = (int) (u4 - 1.0);
    if (dt.month > 12) {
      dt.month -= 12;
    }
    dt.day = (int) (u2 - SMath.floor (365.25 * u3) - SMath.floor (30.6001 * u4));
    dt.year = (int) (u3 + SMath.floor ( (u4 - 2.0) / 12.0) - 4800);
    dt.hour = (jd - SMath.floor (jd + 0.5) + 0.5) * 24.0;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return dt;
  }

  ////////////////////////////////////////////////////////////////////////////
  /// deltaT:
  ////////////////////////////////////////////////////////////////////////////
  /* DeltaT = Ephemeris Time - Universal Time, in days.
   * 
   * 1620 - today + a couple of years:
   * ---------------------------------
   * The tabulated values of deltaT, in hundredths of a second,
   * were taken from The Astronomical Almanac 1997, page K8.  The program
   * adjusts for a value of secular tidal acceleration ndot = -25.7376.
   * arcsec per century squared, the value used in JPL's DE403 ephemeris.
   * ELP2000 (and DE200) used the value -23.8946.
   * To change ndot, one can
   * either redefine SE_TIDAL_DEFAULT in swephexp.h
   * or use the routine swe_set_tid_acc() before calling Swiss 
   * Ephemeris.
   * Bessel's interpolation formula is implemented to obtain fourth 
   * order interpolated values at intermediate times.
   *
   * -1000 - 1620:
   * ---------------------------------
   * For dates between -500 and 1600, the table given by Morrison &
   * Stephenson (2004; p. 332) is used, with linear interpolation.
   * This table is based on an assumed value of ndot = -26.
   * The program adjusts for ndot = -25.7376.
   * For 1600 - 1620, a linear interpolation between the last value
   * of the latter and the first value of the former table is made.
   *
   * before -1000:
   * ---------------------------------
   * For times before -1100, a formula of Morrison & Stephenson (2004)
   * (p. 332) is used:
   * dt = 32 * t * t - 20 sec, where t is centuries from 1820 AD.
   * For -1100 to -1000, a transition from this formula to the Stephenson
   * table has been implemented in order to avoid a jump.
   *
   * future:
   * ---------------------------------
   * For the time after the last tabulated value, we use the formula
   * of Stephenson (1997; p. 507), with a modification that avoids a jump
   * at the end of the tabulated period. A linear term is added that
   * makes a slow transition from the table to the formula over a period
   * of 100 years. (Need not be updated, when table will be enlarged.)
   *
   * References:
   *
   * Stephenson, F. R., and L. V. Morrison, "Long-term changes
   * in the rotation of the Earth: 700 B.C. to A.D. 1980,"
   * Philosophical Transactions of the Royal Society of London
   * Series A 313, 47-70 (1984)
   *
   * Borkowski, K. M., "ELP2000-85 and the Dynamical Time
   * - Universal Time relation," Astronomy and Astrophysics
   * 205, L8-L10 (1988)
   * Borkowski's formula is derived from partly doubtful eclipses 
   * going back to 2137 BC and uses lunar position based on tidal 
   * coefficient of -23.9 arcsec/cy^2.
   *
   * Chapront-Touze, Michelle, and Jean Chapront, _Lunar Tables
   * and Programs from 4000 B.C. to A.D. 8000_, Willmann-Bell 1991
   * Their table agrees with the one here, but the entries are
   * rounded to the nearest whole second.
   *
   * Stephenson, F. R., and M. A. Houlden, _Atlas of Historical
   * Eclipse Maps_, Cambridge U. Press (1986)
   *
   * Stephenson, F.R. & Morrison, L.V., "Long-Term Fluctuations in 
   * the Earth's Rotation: 700 BC to AD 1990", Philosophical 
   * Transactions of the Royal Society of London, 
   * Ser. A, 351 (1995), 165-202. 
   *
   * Stephenson, F. Richard, _Historical Eclipses and Earth's 
   * Rotation_, Cambridge U. Press (1997)
   * 
   * Morrison, L. V., and F.R. Stephenson, "Historical Values of the Earth's
   * Clock Error DT and the Calculation of Eclipses", JHA xxxv (2004),
   * pp.327-336
   *
   * Table from AA for 1620 through today
   * Note, Stephenson and Morrison's table starts at the year 1630.
   * The Chapronts' table does not agree with the Almanac prior to 1630.
   * The actual accuracy decreases rapidly prior to 1780.
   *
   * Jean Meeus, Astronomical Algorithms, 2nd edition, 1998.
   * 
   * For a comprehensive collection of publications and formulae, see:
   * http://www.phys.uu.nl/~vgent/astro/deltatime.htm
   * 
   * For future values of delta t, the following data from the 
   * Earth Orientation Department of the US Naval Observatory can be used:
   * (TAI-UTC) from: ftp://maia.usno.navy.mil/ser7/tai-utc.dat
   * (UT1-UTC) from: ftp://maia.usno.navy.mil/ser7/finals.all
   * file description in: ftp://maia.usno.navy.mil/ser7/readme.finals
   * Delta T = TAI-UT1 + 32.184 sec = (TAI-UTC) - (UT1-UTC) + 32.184 sec
   *
   * Also, there is the following file:
   * http://maia.usno.navy.mil/ser7/deltat.data, but it is about 3 months
   * behind (on 3 feb 2009)
   *
   * Last update of table dt[]: Dieter Koch, 3 feb 2009.
   * ATTENTION: Whenever updating this table, do not forget to adjust
   * the macros TABEND and TABSIZ !
   */

  private static final int TABSTART=1620;
  private static final int TABEND=2014;
  private static final int TABSIZ=TABEND-TABSTART+1;

  /* we make the table greater for additional values read from external file */
  private static final int TABSIZ_SPACE=TABSIZ+100;

  private static short dt[]=new short[] {
  /* 1620.0 thru 1659.0 */
  12400, 11900, 11500, 11000, 10600, 10200, 9800, 9500, 9100, 8800,
  8500, 8200, 7900, 7700, 7400, 7200, 7000, 6700, 6500, 6300,
  6200, 6000, 5800, 5700, 5500, 5400, 5300, 5100, 5000, 4900,
  4800, 4700, 4600, 4500, 4400, 4300, 4200, 4100, 4000, 3800,
  /* 1660.0 thru 1699.0 */
  3700, 3600, 3500, 3400, 3300, 3200, 3100, 3000, 2800, 2700,
  2600, 2500, 2400, 2300, 2200, 2100, 2000, 1900, 1800, 1700,
  1600, 1500, 1400, 1400, 1300, 1200, 1200, 1100, 1100, 1000,
  1000, 1000, 900, 900, 900, 900, 900, 900, 900, 900,
  /* 1700.0 thru 1739.0 */
  900, 900, 900, 900, 900, 900, 900, 900, 1000, 1000,
  1000, 1000, 1000, 1000, 1000, 1000, 1000, 1100, 1100, 1100,
  1100, 1100, 1100, 1100, 1100, 1100, 1100, 1100, 1100, 1100,
  1100, 1100, 1100, 1100, 1200, 1200, 1200, 1200, 1200, 1200,
  /* 1740.0 thru 1779.0 */
  1200, 1200, 1200, 1200, 1300, 1300, 1300, 1300, 1300, 1300,
  1300, 1400, 1400, 1400, 1400, 1400, 1400, 1400, 1500, 1500,
  1500, 1500, 1500, 1500, 1500, 1600, 1600, 1600, 1600, 1600,
  1600, 1600, 1600, 1600, 1600, 1700, 1700, 1700, 1700, 1700,
  /* 1780.0 thru 1799.0 */
  1700, 1700, 1700, 1700, 1700, 1700, 1700, 1700, 1700, 1700,
  1700, 1700, 1600, 1600, 1600, 1600, 1500, 1500, 1400, 1400,
  /* 1800.0 thru 1819.0 */
  1370, 1340, 1310, 1290, 1270, 1260, 1250, 1250, 1250, 1250,
  1250, 1250, 1250, 1250, 1250, 1250, 1250, 1240, 1230, 1220,
  /* 1820.0 thru 1859.0 */
  1200, 1170, 1140, 1110, 1060, 1020, 960, 910, 860, 800,
  750, 700, 660, 630, 600, 580, 570, 560, 560, 560,
  570, 580, 590, 610, 620, 630, 650, 660, 680, 690,
  710, 720, 730, 740, 750, 760, 770, 770, 780, 780,
  /* 1860.0 thru 1899.0 */
  788, 782, 754, 697, 640, 602, 541, 410, 292, 182,
  161, 10, -102, -128, -269, -324, -364, -454, -471, -511,
  -540, -542, -520, -546, -546, -579, -563, -564, -580, -566,
  -587, -601, -619, -664, -644, -647, -609, -576, -466, -374,
  /* 1900.0 thru 1939.0 */
  -272, -154, -2, 124, 264, 386, 537, 614, 775, 913,
  1046, 1153, 1336, 1465, 1601, 1720, 1824, 1906, 2025, 2095,
  2116, 2225, 2241, 2303, 2349, 2362, 2386, 2449, 2434, 2408,
  2402, 2400, 2387, 2395, 2386, 2393, 2373, 2392, 2396, 2402,
  /* 1940.0 thru 1979.0 */
   2433, 2483, 2530, 2570, 2624, 2677, 2728, 2778, 2825, 2871,
   2915, 2957, 2997, 3036, 3072, 3107, 3135, 3168, 3218, 3268,
   3315, 3359, 3400, 3447, 3503, 3573, 3654, 3743, 3829, 3920,
   4018, 4117, 4223, 4337, 4449, 4548, 4646, 4752, 4853, 4959,
  /* 1980.0 thru 1999.0 */
   5054, 5138, 5217, 5296, 5379, 5434, 5487, 5532, 5582, 5630,
   5686, 5757, 5831, 5912, 5998, 6078, 6163, 6230, 6297, 6347,
  /* 2000.0 thru 2009.0 */
   6383, 6409, 6430, 6447, 6457, 6469, 6485, 6515, 6546, 6578,
  /* Extrapolated values, 2010 - 2014 */
   6607, 6660, 6700, 6750, 6800,
  // JAVA ONLY: add 100 empty elements, see constant TABSIZ_SPACE above!
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  };
//#define DELTAT_STEPHENSON_2004
//#ifdef DELTAT_STEPHENSON_2004
private static final int TAB2_SIZ    = 27;
private static final int TAB2_START  = -1000;
private static final int TAB2_END    = 1600;
private static final int TAB2_STEP   = 100;
private static final int LTERM_EQUATION_YSTART = 1820;
private static final int LTERM_EQUATION_COEFF = 32;
  /* Table for -1000 through 1600, from Morrison & Stephenson (2004).  */
  private static short dt2[]=new short[] {
  /*-1000  -900  -800  -700  -600  -500  -400  -300  -200  -100*/
    25400,23700,22000,21000,19040,17190,15530,14080,12790,11640,
  /*    0   100   200   300   400   500   600   700   800   900*/
    10580, 9600, 8640, 7680, 6700, 5710, 4740, 3810, 2960, 2200,
  /* 1000  1100  1200  1300  1400  1500  1600,                 */
     1570, 1090,  740,  490,  320,  200,  120,
  };
//#else
  /* Table for -500 through 1600, from Stephenson & Morrison (1995).  */
//C only://  private static final int TAB2_SIZ=43;
  private static final int TAB2_START=-500;
  private static final int TAB2_END=1600;
  private static final int TAB2_STEP     = 50;
  private static final int LTERM_EQUATION_YSTART = 1735;
  private static final int LTERM_EQUATION_COEFF  = 35;
  private static short dt2[]=new short[] {
  /* -500  -450  -400  -350  -300  -250  -200  -150  -100   -50*/
    16800,16000,15300,14600,14000,13400,12800,12200,11600,11100,
  /*    0    50   100   150   200   250   300   350   400   450*/
    10600,10100, 9600, 9100, 8600, 8200, 7700, 7200, 6700, 6200,
  /*  500   550   600   650   700   750   800   850   900   950*/
     5700, 5200, 4700, 4300, 3800, 3400, 3000, 2600, 2200, 1900,
  /* 1000  1050  1100  1150  1200  1250  1300  1350  1400  1450*/
     1600, 1350, 1100,  900,  750,  600,  470,  380,  300,  230,
  /* 1500  1550  1600 */
      180,  140,  110,
  };
//#endif
  /* returns DeltaT (ET - UT) in days
   * double tjd 	= 	julian day in UT
   */
//#define DEMO 0
  private synchronized double calc_deltaT(double tjd) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.deltaT(double)");
//#endif /* TRACE0 */
    double sdt = calc_deltaT(tjd, this.tid_acc);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return sdt;
  }
  private static synchronized double calc_deltaT(double tjd, double tid_acc) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.deltaT(double, double)");
//#endif /* TRACE0 */
    double ans = 0., ans2, ans3;
    double p, B=0., B2, Y=0., Ygreg, dd; // To remove Java warning of "maybe" not initialized
    int d[]=new int[6];
    int i, iy, k;
//#ifdef JAVAME
    int tabsiz = TABSIZ;
//#else
    /* read additional values from swedelta.txt */
    int tabsiz = init_dt();
//#endif /* JAVAME */
    int tabend = TABSTART + tabsiz - 1;
    Y = 2000.0 + (tjd - SwephData.J2000)/365.25;
    Ygreg = 2000.0 + (tjd - SwephData.J2000)/365.2425;
    /* before -500:
     * formula by Stephenson (1997; p. 508) but adjusted to fit the starting
     * point of table dt2 (Stephenson 1997). */
    if( Y < TAB2_START ) {
      B = (Y - LTERM_EQUATION_YSTART) * 0.01;
      ans = -20 + LTERM_EQUATION_COEFF * B * B;
      ans = adjust_for_tidacc(tid_acc, ans, Y);
      /* transition from formula to table over 100 years */
      if (Y >= TAB2_START - 100) {
        /* starting value of table dt2: */
        ans2 = adjust_for_tidacc(tid_acc, dt2[0], TAB2_START);
        /* value of formula at epoch TAB2_START */
        B = (TAB2_START - LTERM_EQUATION_YSTART) * 0.01;
        ans3 = -20 + LTERM_EQUATION_COEFF * B * B;
        ans3 = adjust_for_tidacc(tid_acc, ans3, Y);
        dd = ans3 - ans2;
        B = (Y - (TAB2_START - 100)) * 0.01;
        /* fit to starting point of table dt2. */
        ans = ans - dd * B;
      }
    }
    /* between -500 and 1600:
     * linear interpolation between values of table dt2 (Stephenson 1997) */
    if (Y >= TAB2_START && Y < TAB2_END) {
      double Yjul = 2000 + (tjd - 2451557.5) / 365.25;
      p = SMath.floor(Yjul);
      iy = (int) ((p - TAB2_START) / TAB2_STEP);
      dd = (Yjul - (TAB2_START + TAB2_STEP * iy)) / TAB2_STEP;
      ans = dt2[iy] + (dt2[iy+1] - dt2[iy]) * dd;
      /* correction for tidal acceleration used by our ephemeris */
      ans = adjust_for_tidacc(tid_acc, ans, Y);
    }
    /* between 1600 and 1620:
     * linear interpolation between 
     * end of table dt2 and start of table dt */
    if (Y >= TAB2_END && Y < TABSTART) { 
      B = TABSTART - TAB2_END;
      iy = (TAB2_END - TAB2_START) / TAB2_STEP;
      dd = (Y - TAB2_END) / B;
      ans = dt2[iy] + dd * (dt[0] / 100.0 - dt2[iy]);
      ans = adjust_for_tidacc(tid_acc, ans, Y);
    }
    /* 1620 - today + a few years (tabend):
     * Besselian interpolation from tabulated values in table dt.
     * See AA page K11.
     */
    if (Y >= TABSTART && Y <= tabend) {
      /* Index into the table.
       */
      p = SMath.floor(Y);
      iy = (int) (p - TABSTART);
      /* Zeroth order estimate is value at start of year
       */
      ans = dt[iy];
      k = iy + 1;
      if( k >= tabsiz )
        return deltatIsDone(ans, Y, B, tid_acc, tabsiz, tabend); /* No data, can't go on. */
      /* The fraction of tabulation interval
       */
      p = Y - p;
      /* First order interpolated value
       */
      ans += p*(dt[k] - dt[iy]);
      if( (iy-1 < 0) || (iy+2 >= tabsiz) )
        return deltatIsDone(ans, Y, B, tid_acc, tabsiz, tabend); /* can't do second differences */
      /* Make table of first differences
       */
      k = iy - 2;
      for( i=0; i<5; i++ ) {
        if( (k < 0) || (k+1 >= tabsiz) ) 
          d[i] = 0;
        else
          d[i] = dt[k+1] - dt[k];
        k += 1;
      }
      /* Compute second differences
       */
      for( i=0; i<4; i++ )
        d[i] = d[i+1] - d[i];
      B = 0.25*p*(p-1.0);
      ans += B*(d[1] + d[2]);
//#if DEMO
//    printf( "B %.4lf, ans %.4lf\n", B, ans );
//#endif /* DEMO */
      if( iy+2 >= tabsiz )
        return deltatIsDone(ans, Y, B, tid_acc, tabsiz, tabend);
      /* Compute third differences
       */
      for( i=0; i<3; i++ )
        d[i] = d[i+1] - d[i];
      B = 2.0*B/3.0;
      ans += (p-0.5)*B*d[1];
//#if DEMO
//    printf( "B %.4lf, ans %.4lf\n", B*(p-0.5), ans );
//#endif /* DEMO */
      if( (iy-2 < 0) || (iy+3 > tabsiz) )
        return deltatIsDone(ans, Y, B, tid_acc, tabsiz, tabend);
      /* Compute fourth differences
       */
      for( i=0; i<2; i++ )
        d[i] = d[i+1] - d[i];
      B = 0.125*B*(p+1.0)*(p-2.0);
      ans += B*(d[0] + d[1]);
//#if DEMO
//    printf( "B %.4lf, ans %.4lf\n", B, ans );
//#endif /* DEMO */
    }

    return deltatIsDone(ans, Y, B, tid_acc, tabsiz, tabend);
  }

  private synchronized static double deltatIsDone(double ans, double Y,
                                                  double B, double tid_acc, int tabsiz, int tabend) {
// //#ifdef TRACE0
//     // Trace.level++; Don't increment here, as the calling method calc_deltat() does not decrement on return!
//     Trace.log("SweDate.deltatIsDone(double, double, double, double, int, int)");
// //#endif /* TRACE0 */
    double ans2, ans3, B2, dd;
    if (Y >= TABSTART && Y <= tabend) {
      ans *= 0.01;
      ans = adjust_for_tidacc(tid_acc, ans, Y);
    }
    /* today - :
     * Formula Stephenson (1997; p. 507),
     * with modification to avoid jump at end of AA table,
     * similar to what Meeus 1998 had suggested.
     * Slow transition within 100 years.
     */
    if (Y > tabend) {
      B = 0.01 * (Y - 1820);
      ans = -20 + 31 * B * B;
      /* slow transition from tabulated values to Stephenson formula: */
      if (Y <= tabend+100) {
        B2 = 0.01 * (tabend - 1820);
        ans2 = -20 + 31 * B2 * B2;
        ans3 = dt[tabsiz-1] * 0.01;
        dd = (ans2 - ans3);
        ans += dd * (Y - (tabend + 100)) * 0.01;
      }
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return ans / 86400.0;
  }


//#ifndef JAVAME
  /* Read delta t values from external file.
   * record structure: year(whitespace)delta_t in 0.01 sec.
   */
  private static int init_dt() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.init_dt()");
//#endif /* TRACE0 */
    FilePtr fp = null;
    int year;
    int tab_index;
    int tabsiz;
    int i;
    String s;
    if (!init_dt_done) {
      init_dt_done = true;
      /* no error message if file is missing */
      try {
        if ((fp = sw.swi_fopen(-1, "swe_deltat.txt", sw.swed.ephepath, null)) == null &&
            (fp = sw.swi_fopen(-1, "sedeltat.txt", sw.swed.ephepath, null)) == null) {
//#ifdef TRACE0
          Trace.level--;
//#endif /* TRACE0 */
          return TABSIZ;  // I think, I could skip this one...
        }
      } catch (SwissephException se) {
//#ifdef TRACE0
        Trace.level--;
//#endif /* TRACE0 */
        return TABSIZ;
      }
      try {
        while ((s=fp.readLine()) != null) {
          s.trim();
          if (s.length() == 0 || s.charAt(0) == '#') {
            continue;
          }
          year = SwissLib.atoi(s);
          tab_index = year - TABSTART;
          /* table space is limited. no error msg, if exceeded */
          if (tab_index >= TABSIZ_SPACE)
            continue;
          if (s.length() > 4) {
            s = s.substring(4).trim();
          }
          dt[tab_index] = (short)(SwissLib.atof(s) * 100 + 0.5);
        }
      } catch (java.io.IOException e) {
//#ifndef NO_NIO
      } catch (java.nio.BufferUnderflowException e) {
//#endif /* NO_NIO */
      }
      try { fp.close(); } catch (java.io.IOException e) {}
    }
    /* find table size */
    tabsiz = 2001 - TABSTART + 1;
    for (i = tabsiz - 1; i < TABSIZ_SPACE; i++) {
      if (dt[i] == 0)
        break;
      else
        tabsiz++;
    }
    tabsiz--;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return tabsiz;
  }
//#endif /* JAVAME */
 
  /* Astronomical Almanac table is corrected by adding the expression
   *     -0.000091 (ndot + 26)(year-1955)^2  seconds
   * to entries prior to 1955 (AA page K8), where ndot is the secular
   * tidal term in the mean motion of the Moon.
   *
   * Entries after 1955 are referred to atomic time standards and
   * are not affected by errors in Lunar or planetary theory.
   */
  private static double adjust_for_tidacc(double tid_acc_local, double ans, double Y) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.adjust_for_tidacc(double, double, double");
//#endif /* TRACE0 */
    double B;
    if( Y < 1955.0 ) {
      B = (Y - 1955.0);
      ans += -0.000091 * (tid_acc_local + 26.0) * B * B;
    }
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
    return ans;
  }

  /**
  * Sets the year, month, day, hour, calType and jd fields of this
  * SweDate instance.
  */
  private void initDateFromJD(double jd, boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.initDateFromJD(double, boolean");
//#endif /* TRACE0 */
    this.jd=jd;
    this.calType=calType;
    IDate dt=swe_revjul(jd, calType);
    this.year=dt.year;
    this.month=dt.month;
    this.day=dt.day;
    this.hour=dt.hour;
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Sets the year, month, day, hour, calType and jd fields of this
  * object.
  */
  private void setFields(int year, int month, int day, double hour) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setFields(int, int, int, double");
//#endif /* TRACE0 */
    // Get year, month, day of jdCO and compare to given date to
    // find out about the calendar system:
    IDate dt=swe_revjul(jdCO,SE_GREG_CAL);
    boolean calType = SE_GREG_CAL;
    if (dt.year > year ||
        (dt.year == year && dt.month > month) ||
        (dt.year == year && dt.month == month && dt.day > day)) {
      calType = SE_JUL_CAL;
    }
    setFields(year, month, day, hour, calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

  /**
  * Sets the year, month, day, hour, calType and jd fields of this
  * object.
  */
  private void setFields(int year, int month, int day, double hour,
        boolean calType) {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SweDate.setFields(int, int, int, double, boolean");
//#endif /* TRACE0 */
    this.year=year;
    this.month=month;
    this.day=day;
    this.hour=hour;
    this.calType=calType;
    this.jd=swe_julday(year, month, day, hour, calType);
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }


  /*
   * functions for the handling of UTC
   */
 
  /* Leap seconds were inserted at the end of the following days:*/
  private static final int NLEAP_SECONDS = 24;
  private static final int NLEAP_SECONDS_SPACE = 100;
  private static final int leap_seconds[] = new int[]{
  19720630,
  19721231,
  19731231,
  19741231,
  19751231,
  19761231,
  19771231,
  19781231,
  19791231,
  19810630,
  19820630,
  19830630,
  19850630,
  19871231,
  19891231,
  19901231,
  19920630,
  19930630,
  19940630,
  19951231,
  19970630,
  19981231,
  20051231,
  20081231,
  0,  /* keep this 0 as end mark */
  // JAVA ONLY to have the array extended to NLEAP_SECONDS_SPACE elements:
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  };
  private static final double J1972 = 2441317.5;
  private static final int NLEAP_INIT = 10;

  /* Read additional leap second dates from external file, if given.
   */
  private int init_leapsec() {
//#ifdef JAVAME
    return NLEAP_SECONDS;
//#else
    FilePtr fp = null;
    int ndat, ndat_last;
    int tabsiz = 0;
    int i;
    String s;
    if (!init_leapseconds_done) {
      init_leapseconds_done = true;
      tabsiz = NLEAP_SECONDS;
      ndat_last = leap_seconds[NLEAP_SECONDS - 1];
      /* no error message if file is missing */
      try {
        if ((fp = sw.swi_fopen(-1, "seleapsec.txt", sw.swed.ephepath, null)) == null)
          return NLEAP_SECONDS;
        while ((s=fp.readLine()) != null) {
          s.trim();
          if (s.startsWith("#") || s.length() == 0)
            continue;
          ndat = SwissLib.atoi(s);
          if (ndat <= ndat_last)
            continue;
          /* table space is limited. no error msg, if exceeded */
          if (tabsiz >= NLEAP_SECONDS_SPACE)
            return tabsiz;
          leap_seconds[tabsiz] = ndat;
          tabsiz++;
        }
        if (tabsiz > NLEAP_SECONDS) leap_seconds[tabsiz] = 0; /* end mark */
        fp.close();
      } catch (java.io.IOException e) {
      } catch (SwissephException e) {
        return tabsiz;
//#ifndef NO_NIO
      } catch (java.nio.BufferUnderflowException e) {
//#endif /* NO_NIO */
      }
      try { fp.close(); } catch (java.io.IOException e) {}
      return tabsiz;
    }
    /* find table size */
    tabsiz = 0;
    for (i = 0; i < NLEAP_SECONDS_SPACE; i++) {
      if (leap_seconds[i] == 0)
        break;
      else
        tabsiz++;
    }
    return tabsiz;
//#endif /* JAVAME */
  }

  /**
  * Determines, if the year, month, day, hour, minute and second fields
  * describe a valid date.
  * @param iyear Year of the input date (UTC)
  * @param imonth Month of the input date (UTC, 1 to 12)
  * @param iday Day of the input date (UTC, 1 to 31)
  * @param ihour Hour of the input date (UTC, 0 to 23)
  * @param imin Minute of the input date (UTC, 0 to 59)
  * @param dsec Second of the input date (UTC, 1.0 to less than 61.0)
  * @param gregflag true == Gregorian calendar, false == Julian calendar
  * @return true, if the date is valid, false otherwise.
  */
  public boolean isValidUTCDate(int iyear, int imonth, int iday, int ihour, int imin, double dsec, boolean gregflag) {
    return getInvalidUTCDateError(iyear, imonth, iday, ihour, imin, dsec, gregflag) == null;
  }
  /**
  * Returns a String error message, if the year, month, day, hour, minute and
  * second fields do not describe a valid date.
  * @param iyear Year of the input date (UTC)
  * @param imonth Month of the input date (UTC, 1 to 12)
  * @param iday Day of the input date (UTC, 1 to 31)
  * @param ihour Hour of the input date (UTC, 0 to 23)
  * @param imin Minute of the input date (UTC, 0 to 59)
  * @param dsec Second of the input date (UTC, 1.0 to less than 61.0)
  * @param gregflag true == Gregorian calendar, false == Julian calendar
  * @return null, if the date is valid, otherwise it returns a message in english, describing the error field.
  */
  public String getInvalidUTCDateError(int iyear, int imonth, int iday, int ihour, int imin, double dsec, boolean gregflag) {
    double[] dret = new double[2];
    double tjd_ut1, tjd_et, tjd_et_1972, dhour, d;
    int iyear2, imonth2, iday2;
    int i, j, ndat, nleap, tabsiz_nleap;
    /*
     * error handling: invalid iyear etc.
     */
    tjd_ut1 = swe_julday(iyear, imonth, iday, 0, gregflag);
    IDate dt = swe_revjul(tjd_ut1, gregflag);
//   tjd_ut1 = swe_julday(iyear, imonth, iday, 0, gregflag);
//   swe_revjul(tjd_ut1, gregflag, &iyear2, &imonth2, &iday2, &d);
    if (iyear != dt.year || imonth != dt.month || iday != dt.day) {
      return "invalid date: year = " + iyear + ", month = " + imonth + ", day = " + iyear + iday;
    }
    if (ihour < 0 || ihour > 23
     || imin < 0 || imin > 59
     || dsec < 0 || dsec >= 61
     || (dsec >= 60 && (imin < 59 || ihour < 23 || tjd_ut1 < J1972))) {
      return "invalid time: " + ihour + ":" + imin + ":" + dsec;
    }
    /*
     * number of leap seconds since 1972:
     */
    tabsiz_nleap = init_leapsec();
    ndat = iyear * 10000 + imonth * 100 + iday;
    /*
     * if input second is 60: is it a valid leap second ?
     */
    if (dsec >= 60) {
      j = 0;
      for (i = 0; i < tabsiz_nleap; i++) {
        if (ndat == leap_seconds[i]) {
          j = 1;
          break;
        }
      }
      if (j != 1) {
        return "invalid time (no leap second!): " + ihour + ":" + imin + ":" + dsec;
      }
    }
    return null;
  }
  /**
  * Calculates the julian day numbers (TT (==ET) and UT1) from a given date.
  * @param iyear Year of the input date (UTC)
  * @param imonth Month of the input date (UTC, 1 to 12)
  * @param iday Day of the input date (UTC, 1 to 31)
  * @param ihour Hour of the input date (UTC, 0 to 23)
  * @param imin Minute of the input date (UTC, 0 to 59)
  * @param dsec Second of the input date (UTC, 1.0 to less than 61.0)
  * @param gregflag true == Gregorian calendar, false == Julian calendar
  * @param checkValidInput if true, throws SwissephException, when any of month,
  * day, hour, minute or second are out of their valid ranges. If false, it
  * doesn't care about invalid values (e.g. month = 13 or second = 61, even
  * though there is no leap second on that date and time).
  * @return an array of two doubles<br>
  *         first value = Julian day number TT (ET)<br>
  *         second value = Julian day number UT1<p>
  *
  * Note:<pre>
  * - Before 1972, swe_utc_to_jd() treats its input time as UT1.
  *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
  *   UTC second was regularly changed, so that UTC remained very close to UT1.
  * - From 1972 on, input time is treated as UTC.
  * - If delta_t - nleap - 32.184 > 1, the input time is treated as UT1.
  *   Note: Like this we avoid errors greater than 1 second in case that
  *   the leap seconds table (or the Swiss Ephemeris version) is not updated
  *   for a long time.</pre>
  * @see #getUTCfromJDET(double, boolean)
  * @see #getUTCfromJDUT1(double, boolean)
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  /*
   * Input:  Clock time UTC, year, month, day, hour, minute, second (decimal).
   *         gregflag  Calendar flag
   *         serr      error string
   * Output: An array of doubles:
   *         dret[0] = Julian day number TT (ET)
   *         dret[1] = Julian day number UT1
   *
   * Function returns OK or Error.
   *
   * - Before 1972, swe_utc_to_jd() treats its input time as UT1.
   *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
   *   UTC second was regularly changed, so that UTC remained very close to UT1.
   * - From 1972 on, input time is treated as UTC.
   * - If delta_t - nleap - 32.184 > 1, the input time is treated as UT1.
   *   Note: Like this we avoid errors greater than 1 second in case that
   *   the leap seconds table (or the Swiss Ephemeris version) is not updated
   *   for a long time.
  */
  public double[] getJDfromUTC(int iyear,
      int imonth,
      int iday,
      int ihour,
      int imin,
      double dsec,
      boolean gregflag,
      boolean checkValidInput) throws SwissephException {
    double[] dret = new double[2];
    double tjd_ut1, tjd_et, tjd_et_1972, dhour, d;
    int i, j, ndat, nleap, tabsiz_nleap;
    /*
     * error handling: invalid iyear etc.
     */
    if (checkValidInput) {
      String err = getInvalidUTCDateError(iyear, imonth, iday, ihour, imin, dsec, gregflag);
      if (err != null) {
        tjd_ut1 = swe_julday(iyear, imonth, iday, ihour+imin/60.+dsec/3600., gregflag);
        throw new SwissephException(tjd_ut1, SwissephException.INVALID_DATE, err);
      }
    }
    tjd_ut1 = swe_julday(iyear, imonth, iday, 0, gregflag);
    dhour = (double) ihour + ((double) imin) / 60.0 + dsec / 3600.0;
    /*
     * before 1972, we treat input date as UT1
     */
    if (tjd_ut1 < J1972) {
      dret[1] = swe_julday(iyear, imonth, iday, dhour, gregflag);
      dret[0] = dret[1] + getDeltaT(dret[1]);
      return dret;
    }
    /*
     * if gregflag = Julian calendar, convert to gregorian calendar
     */
    if (gregflag == SE_JUL_CAL) {
      gregflag = SE_GREG_CAL;
//      swe_revjul(tjd_ut1, gregflag, &iyear, &imonth, &iday, &d);
      IDate dt = swe_revjul(tjd_ut1, gregflag);
    }
    /*
     * number of leap seconds since 1972:
     */
    tabsiz_nleap = init_leapsec();
    nleap = NLEAP_INIT; /* initial difference between UTC and TAI in 1972 */
    ndat = iyear * 10000 + imonth * 100 + iday;
    for (i = 0; i < tabsiz_nleap; i++) {
      if (ndat <= leap_seconds[i])
        break;
      nleap++;
    }
    /*
     * For input dates > today:
     * If leap seconds table is not up to date, we'd better interpret the
     * input time as UT1, not as UTC. How do we find out?
     * Check, if delta_t - nleap - 32.184 > 0.9
     */
    d = getDeltaT(tjd_ut1) * 86400.0;
    if (d - (double) nleap - 32.184 >= 1.0) {
      dret[1] = tjd_ut1 + dhour / 24.0;
      dret[0] = dret[1] + getDeltaT(dret[1]);
      return dret;
    }
    /*
     * convert UTC to ET and UT1
     */
    /* the number of days between input date and 1 jan 1972: */
    d = tjd_ut1 - J1972;
    /* SI time since 1972, ignoring leap seconds: */
    d += (double) ihour / 24.0 + (double) imin / 1440.0 + dsec / 86400.0;
    /* ET (TT) */
    tjd_et_1972 = J1972 + (32.184 + NLEAP_INIT) / 86400.0;
    tjd_et = tjd_et_1972 + d + ((double) (nleap - NLEAP_INIT)) / 86400.0;
    d = getDeltaT(tjd_et);
    tjd_ut1 = tjd_et - getDeltaT(tjd_et - d);
    dret[0] = tjd_et;
    dret[1] = tjd_ut1;
    return dret;
  }

  /**
  * Calculates the UTC date from ET Julian day number.
  * @param tjd_et Julian day number (ET) to be converted.
  * @param gregflag true == Gregorian calendar, false == Julian calendar
  * @return The UTC date as SDate object.
  *
  * Note:<pre>
  * - Before 1 jan 1972 UTC, output UT1.
  *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
  *   UTC second was regularly changed, so that UTC remained very close to UT1.
  * - From 1972 on, output is UTC.
  * - If delta_t - nleap - 32.184 > 1, the output is UT1.
  *   Note: Like this we avoid errors greater than 1 second in case that
  *   the leap seconds table (or the Swiss Ephemeris version) has not been
  *   updated for a long time.</pre>
  * @see #getUTCfromJDUT1(double, boolean)
  * @see swisseph.SDate
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  /*
   * Input:  tjd_et   Julian day number, terrestrial time (ephemeris time).
   *         gregfalg Calendar flag
   * Output: UTC year, month, day, hour, minute, second (decimal).
   *
   * - Before 1 jan 1972 UTC, output UT1.
   *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
   *   UTC second was regularly changed, so that UTC remained very close to UT1.
   * - From 1972 on, output is UTC.
   * - If delta_t - nleap - 32.184 > 1, the output is UT1.
   *   Note: Like this we avoid errors greater than 1 second in case that
   *   the leap seconds table (or the Swiss Ephemeris version) has not been
   *   updated for a long time.
   */
  public SDate getUTCfromJDET(double tjd_et, boolean gregflag) {
    int i;
    int second_60 = 0;
    int iyear, imonth, iday, ihour, imin, iyear2, imonth2, iday2, nleap, ndat, tabsiz_nleap;
    double dsec, d, tjd, tjd_et_1972, tjd_ut, dret[] = new double[10];
    /*
     * if tjd_et is before 1 jan 1972 UTC, return UT1
     */
    tjd_et_1972 = J1972 + (32.184 + NLEAP_INIT) / 86400.0;
    d = getDeltaT(tjd_et);
    tjd_ut = tjd_et - getDeltaT(tjd_et - d);
    if (tjd_et < tjd_et_1972) {
//     swe_revjul(tjd_ut, gregflag, iyear, imonth, iday, &d);
      IDate dt=swe_revjul(tjd_ut, gregflag);
      return new SDate(dt.year, dt.month, dt.day, dt.hour);
    }
    /*
     * minimum number of leap seconds since 1972; we may be missing one leap
     * second
     */
    tabsiz_nleap = init_leapsec();
//   swe_revjul(tjd_ut-1, SE_GREG_CAL, &iyear2, &imonth2, &iday2, &d);
    IDate dt=swe_revjul(tjd_ut-1, SE_GREG_CAL);
    iyear2 = dt.year;
    imonth2 = dt.month;
    iday2 = dt.day;
    d = dt.hour;
    ndat = iyear2 * 10000 + imonth2 * 100 + iday2;
    nleap = 0;
    for (i = 0; i < tabsiz_nleap; i++) {
      if (ndat <= leap_seconds[i])
        break;
      nleap++;
    }
    /* date of potentially missing leapsecond */
    if (nleap < tabsiz_nleap) {
      i = leap_seconds[nleap];
      iyear2 = i / 10000;
      imonth2 = (i % 10000) / 100;;
      iday2 = i % 100;
      tjd = swe_julday(iyear2, imonth2, iday2, 0, SE_GREG_CAL);
//     swe_revjul(tjd+1, SE_GREG_CAL, &iyear2, &imonth2, &iday2, &d);
      dt=swe_revjul(tjd_ut+1, SE_GREG_CAL);
      iyear2 = dt.year;
      imonth2 = dt.month;
      iday2 = dt.day;
      d = dt.hour;
      dret = getJDfromUTC(iyear2,imonth2,iday2, 0, 0, 0, SE_GREG_CAL, false);
      d = tjd_et - dret[0];
      if (d >= 0) {
        nleap++;
      } else if (d < 0 && d > -1.0/86400.0) {
        second_60 = 1;
      }
    }
    /*
     * UTC, still unsure about one leap second
     */
    tjd = J1972 + (tjd_et - tjd_et_1972) - ((double) nleap + second_60) / 86400.0;
//   swe_revjul(tjd, SE_GREG_CAL, iyear, imonth, iday, &d);
    dt=swe_revjul(tjd, SE_GREG_CAL);
    iyear = dt.year;
    imonth = dt.month;
    iday = dt.day;
    d = dt.hour;
    ihour = (int) d;
    d -= (double) ihour;
    d *= 60;
    imin = (int) d;
    dsec = (d - (double) imin) * 60.0 + second_60;
    /*
     * For input dates > today:
     * If leap seconds table is not up to date, we'd better interpret the
     * input time as UT1, not as UTC. How do we find out?
     * Check, if delta_t - nleap - 32.184 > 0.9
     */
    d = getDeltaT(tjd_et);
    d = getDeltaT(tjd_et - d);
    if (d * 86400.0 - (double) (nleap + NLEAP_INIT) - 32.184 >= 1.0) {
//     swe_revjul(tjd_et - d, SE_GREG_CAL, iyear, imonth, iday, &d);
      dt=swe_revjul(tjd_et - d, SE_GREG_CAL);
      iyear = dt.year;
      imonth = dt.month;
      iday = dt.day;
      d = dt.hour;
      ihour = (int) d;
      d -= (double) ihour;
      d *= 60;
      imin = (int) d;
      dsec = (d - (double) imin) * 60.0;
    }
    if (gregflag == SE_JUL_CAL) {
      tjd = swe_julday(iyear, imonth, iday, 0, SE_GREG_CAL);
//     swe_revjul(tjd, gregflag, iyear, imonth, iday, &d);
      dt=swe_revjul(tjd, SE_GREG_CAL);
      return new SDate(dt.year, dt.month, dt.day, dt.hour);
    }
    return new SDate(iyear, imonth, iday, ihour, imin, dsec);
  }
 
  /**
  * Calculates the UTC date from UT1 (universal time) Julian day number.
  * @param tjd_ut Julian day number (UT1) to be converted.
  * @param gregflag true == Gregorian calendar, false == Julian calendar
  * @return The UTC date as SDate object.
  *
  * Note:<pre>
  * - Before 1 jan 1972 UTC, output UT1.
  *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
  *   UTC second was regularly changed, so that UTC remained very close to UT1.
  * - From 1972 on, output is UTC.
  * - If delta_t - nleap - 32.184 > 1, the output is UT1.
  *   Note: Like this we avoid errors greater than 1 second in case that
  *   the leap seconds table (or the Swiss Ephemeris version) has not been
  *   updated for a long time.</pre>
  * @see #getUTCfromJDET(double, boolean)
  * @see swisseph.SDate
  * @see #SE_GREG_CAL
  * @see #SE_JUL_CAL
  */
  /*
   * Input:  tjd_ut   Julian day number, universal time (UT1).
   *         gregfalg Calendar flag
   * Output: UTC year, month, day, hour, minute, second (decimal).
   *
   * - Before 1 jan 1972 UTC, output UT1.
   *   Note: UTC was introduced in 1961. From 1961 - 1971, the length of the
   *   UTC second was regularly changed, so that UTC remained very close to UT1.
   * - From 1972 on, output is UTC.
   * - If delta_t - nleap - 32.184 > 1, the output is UT1.
   *   Note: Like this we avoid errors greater than 1 second in case that
   *   the leap seconds table (or the Swiss Ephemeris version) has not been
   *   updated for a long time.
   */
  public SDate getUTCfromJDUT1(double tjd_ut, boolean gregflag) {
    double tjd_et = tjd_ut + getDeltaT(tjd_ut);
    return getUTCfromJDET(tjd_et, gregflag);
  }
} // end of class SweDate


class IDate
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  public int year;
  public int month;
  public int day;
  public double hour;
}
