/*
 * $Header: outdeg.c,v 1.9 91/11/16 16:25:24 alois Exp $
 */

# include  "ourdef.h"
# include  "astrolib.h"

char *TimeString (CSEC t, int sep, AS_BOOL suppressZero)
/* return a string containing the time t; this string survives only
   until the next call */
/* does not suppress zeros in hours or minutes */
{
  static char a[9];	/* must be initialized at each call */
  centisec h,m,s;
  strcpy (a, "        ");
  a[2] = a [5] = sep;
  t = ((t + 50) / 100) % (24L *3600L); /* round to seconds */
  s = t % 60L;
  m = (t / 60) % 60L;
  h = t / 3600 % 100L;
  if (s == 0 && suppressZero)  
    a[5] = '\0';
  else {
    a [6] = s / 10 + '0';
    a [7] = s % 10 + '0';
  };
  a [0] = h / 10 + '0';
  a [1] = h % 10 + '0';
  a [3] = m / 10 + '0';
  a [4] = m % 10 + '0';
  return (a);
  } /* TimeString */

char *LonLatString (CSEC t, char pchar, char mchar)
{
  static char a[10];	/* must be initialized at each call */
  char *aa;
  centisec h,m,s;
  strcpy (a, "      '  ");
  /* mask     dddEmm'ss" */
  if (t < 0 ) pchar = mchar;
  t = (ABS4 (t) + 50) / 100; /* round to seconds */
  s = t % 60L;
  m = t / 60 % 60L;
  h = t / 3600 % 1000L;
  if (s == 0)  
    a[6] = '\0';   /* cut off seconds */
  else {
    a [7] = s / 10 + '0';
    a [8] = s % 10 + '0';
  }
  a [3] = pchar;
  if (h > 99)  a [0] = h / 100 + '0';
  if (h > 9)  a [1] = h % 100 / 10 + '0';
  a [2] = h % 10 + '0';
  a [4] = m / 10 + '0';
  a [5] = m % 10 + '0';
  aa = a;
  while (*aa == ' ') aa++;
  return (aa);
} /* LonLatString */

char *DegreeString (CSEC t)
  /* does  suppress leading zeros in degrees */
{
  static char a[9];	/* must be initialized at each call */
  centisec h,m,s;
  strcpy (a, "     '  ");
  a[2] = ODEGREE_CHAR;
  t = t  / 100 % (30L*3600L); /* truncate to seconds */
  s = t % 60L;
  m = t / 60 % 60L;
  h = t / 3600 % 100L;	/* only 0..99 degrees */ 
  if (h > 9)  a [0] = h / 10 + '0';
  a [1] = h % 10 + '0';
  a [3] = m / 10 + '0';
  a [4] = m % 10 + '0';
  a [6] = s / 10 + '0';
  a [7] = s % 10 + '0';
  return (a);
} /* DegreeString */

