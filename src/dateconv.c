/*********************************************************
  $Header: dateconv.c,v 1.9 91/11/16 16:24:24 alois Exp $
  version 15-feb-89 16:30

  This function converts some date+time input {d,m,y,utime}
  into the Julian day number tgmt, which is an Astrodienst relative
  Julian date.
  The function checks that the input is a legal combination
  of dates; for illegal dates like 32 January 1993 it returns ERR
  but still converts the date correctly, i.e. like 1 Feb 1993.
  The function is usually used to convert user input of birth data
  into the Julian day number. Illegal dates should be notified to the user.

  Be aware the we always use astronomical year numbering for the years
  before Christ, not the historical year numbering.
  Astronomical years are done with negative numbers, historical
  years with indicators BC or BCE (before common era).
  Year 0 (astronomical)  	= 1 BC historical.
  year -1 (astronomical) 	= 2 BC
  etc.
  Many users of Astro programs do not know about this difference.

  Return: OK or ERR (for illegal date)
*********************************************************/

# include "ourdef.h"
# include "astrolib.h"

int date_conversion (int d ,
                     int m ,
                     int y ,		/* day, month, year */
                     centisec utime, 	/* greenwich time in centiseconds */
                     char c,  /* calendar g[regorian]|j[ulian]|a[stro = greg] */
                     REAL8 *tgmt	
			/* julian date relative 0.Jan.1950 12:00 gmt */
			/* shift is 2433282 from absolute Julian date */
		    ) 
{ 
  int rday, rmon, ryear;
  REAL8 rut, jd;
  int gregflag = JUL_CAL;
  if (c == 'g' || c == 'a')
    gregflag = GREG_CAL;
  rut = utime / 360000.0;	/* hours GMT */
  jd = julday(m, d, y, rut, gregflag);
  revjul(jd, gregflag, &rmon, &rday, &ryear, &rut);
  *tgmt = jd - 2433282.0;
  if (rmon == m && rday == d && ryear == y) {
    return OK;
  } else {
    return ERR;
  }
}	/* end date_conversion */
