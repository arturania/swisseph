/*********************************************************
  $Header: julday.c,v 1.9 91/11/16 16:25:06 alois Exp $
*********************************************************/

# include "ourdef.h"
# include "astrolib.h"

/*************** julday ********************************************
 * This function returns the absolute Julian day number (JD)
 * for a given calendar date.
 * The arguments are a calendar date: day, month, year as integers,
 * hour as double with decimal fraction.
 * If gregflag = 1, Gregorian calendar is assumed, gregflag = 0
 * Julian calendar is assumed.
 *
 The Julian day number is system of numbering all days continously
 within the time range of known human history. It should be familiar
 for every astrological or astronomical programmer. The time variable
 in astronomical theories is usually expressed in Julian days or
 Julian centuries (36525 days per century) relative to some start day;
 the start day is called 'the epoch'.
 The Julian day number is a double representing the number of
 days since JD = 0.0 on 1 Jan -4712, 12:00 noon.
 
 Midnight has always a JD with fraction .5, because traditionally
 the astronomical day started at noon. This was practical because
 then there was no change of date during a night at the telescope.
 From this comes also the fact the noon ephemerides were printed
 before midnight ephemerides were introduced early in the 20th century.
 
 NOTE: The Julian day number is named after the monk Julianus. It must
 not be confused with the Julian calendar system, which is named after
 Julius Cesar, the Roman politician who introduced this calendar.
 The Julian century is named after Cesar, i.e. a century in the Julian
 calendar. The 'gregorian' century has a variable lenght.

 Be aware the we always use astronomical year numbering for the years
 before Christ, not the historical year numbering.
 Astronomical years are done with negative numbers, historical
 years with indicators BC or BCE (before common era).
 Year 0 (astronomical)  	= 1 BC
 year -1 (astronomical) 	= 2 BC
 etc.

 Original author: Marc Pottenger, Los Angeles.
 with bug fix for year < -4711   15-aug-88 by Alois Treindl
 (The parameter sequence m,d,y still indicates the US origin,
  be careful because the similar function date_conversion() uses
  other parameter sequence and also Astrodienst relative juldate.)
 
 References: Oliver Montenbruck, Grundlagen der Ephemeridenrechnung,
             Verlag Sterne und Weltraum (1987), p.49 ff
 
 related functions: revjul() reverse Julian day number: compute the
  			       calendar date from a given JD
	            date_conversion() includes test for legal date values
		    and notfies errors like 32 January.
 ****************************************************************/

double julday(int month, int day, int year, double hour, int gregflag) 
{
  double jd,u,u0,u1,u2,floor();
  u = year;
  if (month < 3) u -=1;
  u0 = u + 4712.0;
  u1 = month + 1.0;
  if (u1 < 4) u1 += 12.0;
  jd = floor(u0*365.25)
     + floor(30.6*u1+0.000001)
     + day + hour/24.0 - 63.5;
  if (gregflag == GREG_CAL) {
    u2 = floor(ABS8(u) / 100) - floor(ABS8(u) / 400);
    if (u < 0.0) u2 = -u2;
    jd = jd - u2 + 2;            
    if ((u < 0.0) && (u/100 == floor(u/100)) && (u/400 != floor(u/400)))
      jd -=1;
  }
  return jd;
}

/*
 * monday = 0, ... sunday = 6
 */
int day_of_week(double jd)
{
  return (((int) floor (jd - 2433282 - 1.5) %7) + 7) % 7;
}
