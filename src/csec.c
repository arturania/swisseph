/*
 * $Header$
 *
 * A collection of useful functions for centisec calculations.

  ---------------------------------------------------------------
  | Copyright Astrodienst Zurich AG and Alois Treindl, 1991.	|
  | The use of this source code is subject to regulations made	|
  | by Astrodienst Zurich. The code is NOT in the public domain.|
  |								|
  | This copyright notice must not be changed or removed	|
  | by any user of this program.				|
  ---------------------------------------------------------------
*******************************************************/
#include "ourdef.h"
#include "astrolib.h"
#include "housasp.h"

/************************************
normalize argument into interval [0..DEG360]
*************************************/
centisec csnorm(centisec p)
{
  if (p < 0) 
    do { p += DEG360; } while (p < 0);
  else if (p >= DEG360)
    do { p -= DEG360; } while (p >= DEG360);
  return (p);
}

double degnorm(double p)
{
  if (p < 0) 
    do { p += 360.0; } while (p < 0);
  else if (p >= 360.0)
    do { p -= 360.0; } while (p >= 360.0);
  return (p);
}

/************************************
distance in centisecs p1 - p2
normalized to [0..360[
**************************************/
centisec difcsn (centisec p1, centisec p2)
{ 
  return (csnorm(p1 - p2));
}

double difdegn (double p1, double p2)
{ 
  return (degnorm(p1 - p2));
}

/************************************
distance in centisecs p1 - p2
normalized to [-180..180[
**************************************/
centisec difcs2n (centisec p1, centisec p2)
{ centisec dif;
  dif = csnorm(p1 - p2);
  if (dif  >= DEG180) return (dif - DEG360);
  return (dif);
}

double difdeg2n (double p1, double p2)
{ double dif;
  dif = degnorm(p1 - p2);
  if (dif  >= 180.0) return (dif - 360.0);
  return (dif);
}

/*************************************
round second, but at 29.5959 always down
*************************************/ 
centisec roundsec(centisec x)	
{
  centisec t;
  t = (x + 50) / 100 *100L;	/* round to seconds */
  if (t > x && t % DEG30 == 0)  /* was rounded up to next sign */
    t = x / 100 * 100L;		/* round last second of sign downwards */
  return (t);
}


/******************************/
double dcos(centisec x)
{
  return (COS8 (CSTORAD * x));
}

/******************************/
double dsin(centisec x)
{
  return (SIN8 (CSTORAD * x));
}

/******************************/
double dtan(centisec x)
{
  return (TAN8 (CSTORAD * x));
}

/******************************/
centisec datan(double x)
{
  return (d2l (RADTOCS * ATAN8 (x)) );	
}

/******************************/
centisec dasin(double x)
{
  return (d2l (RADTOCS * ASIN8 (x)));	
}
