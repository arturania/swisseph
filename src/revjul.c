/*********************************************************
  $Header: revjul.c,v 1.9 91/11/16 16:25:37 alois Exp $
*********************************************************/

# include "ourdef.h"
# include "astrolib.h"

/*** revjul ******************************************************
  revjul() is the inverse function to julday(), see the description
  there.
  Arguments are julian day number, calendar flag (0=julian, 1=gregorian)
  return values are the calendar day, month, year and the hour of
  the day with decimal fraction (0 .. 23.999999).

  Be aware the we use astronomical year numbering for the years
  before Christ, not the historical year numbering.
  Astronomical years are done with negative numbers, historical
  years with indicators BC or BCE (before common era).
  Year  0 (astronomical)  	= 1 BC historical year
  year -1 (astronomical) 	= 2 BC historical year
  year -234 (astronomical) 	= 235 BC historical year
  etc.

  Original author Mark Pottenger, Los Angeles.
  with bug fix for year < -4711 16-aug-88 Alois Treindl
*************************************************************************/
void revjul (double jd, int gregflag,
	     int *jmon, int *jday, int *jyear, double *jut)
{
  double u0,u1,u2,u3,u4, floor();
  u0 = jd + 32082.5;
  if (gregflag == GREG_CAL) {
    u1 = u0 + floor (u0/36525.0) - floor (u0/146100.0) - 38.0;
    if (jd >= 1830691.5) u1 +=1;
    u0 = u0 + floor (u1/36525.0) - floor (u1/146100.0) - 38.0;
  }
  u2 = floor (u0 + 123.0);
  u3 = floor ( (u2 - 122.2) / 365.25);
  u4 = floor ( (u2 - floor (365.25 * u3) ) / 30.6001);
  *jmon = u4-1.0;
  if (*jmon > 12) *jmon -= 12;
  *jday = u2 - floor (365.25 * u3) - floor (30.6001 * u4);
  *jyear = u3 + floor ( (u4 - 2.0) / 12.0) - 4800;
  *jut = (jd - floor (jd + 0.5) + 0.5) * 24.0;
}
