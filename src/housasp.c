/*******************************************************
$Header: housasp.c,v 1.10 94/06/13 12:17:03 alois Exp $
module housasp.c
house and (simple) aspect calculation 

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

/*************************************
return in which house pp is;
houses are numbered from 1 .. 12
*************************************/ 
int HouseNr(struct houses *hsp, CSEC pp)
{
  CSEC cx;
  int i = 2;
  cx = difcsn(pp, hsp->cusp [1]); 	/* distance from cusp 1 */
  while (i < 13 && cx >= difcsn(hsp->cusp [i], hsp->cusp [1])) i++;
  return (i - 1);
} 

/************************************
returns the inp-house number, where pp is in 
houses are numbered from 1 .. 12
************************************/
int InpHouseNr (struct houses *hsp, CSEC pp, CSEC *coff)
{ 
  CSEC cx;
  int i = 2;
  cx = difcsn(pp, hsp->cusp [1] + coff [1]); 
  while(i<13 &&  cx >= difcsn(hsp->cusp[i] + coff[i], hsp->cusp[1] + coff[1])) 
    i++;
  return (i - 1);
}

/*********************************************************/
void CalcHouses(CSEC th, CSEC fi, CSEC ekl, char hsy, int iteration_count,
	struct houses *hsp )

/* ********************************************************* */
/*  Arguments: th = sidereal time (angle 0..360 degrees      */
/*             hsy = letter code for house system; implemen- */
/*                   ted are P,K,C,R,E,V.                    */
/*             fi = geographic latitude                      */
/*             ekl = obliquity of the ecliptic               */
/*             iteration_count = number of iterations in     */
/*             Placidus calculation; can be 1 or 2.          */
/* ********************************************************* */
/*  Koch and Placidus don't work in the polar circle.        */
/*  We swap MC/IC so that MC is always before AC in the zodiac */
/*  We than divide the quadrants into 3 equal parts.         */
/* ********************************************************* */
/*  All angles are expressed in centiseconds (1/100th of a   */
/*  second of arc) and integer arithmetic is used for these. */
/*  Special trigonometric functions dsin, dcos etc. are im-  */
/*  plemented for arguments in centiseconds.                 */
/* ********************************************************* */


{
  REAL8  tane, tanfi, tant, tand, sina, cstorad_3, cstorad_2_3;
  centisec a, c, f, fh1, fh2, xh1, xh2, rectasc, ad3, Asc1();
  int 	i;
  REAL8 sine, cose;
  cstorad_3 = CSTORAD;
  cstorad_3 = cstorad_3  / 3;	/* Turbo C has problems, when constant
				calculation in one line! */
  cstorad_2_3 = cstorad_3 * 2;
  cose  = dcos (ekl);
  sine  = dsin (ekl);
  tane  = dtan (ekl);
  tanfi = dtan (fi);
  if (th == 0 && fi == 0) th = 1;
  if (th != DEG90 &&th != DEG270) {
    tant = dtan (th);
    hsp->mc = datan (tant / cose);
  } else {
    hsp->mc = DEG90;
    tant = BIGREAL;
  } /*  if */
  if (th > DEG90 && th <= DEG270) 
    hsp->mc += DEG180;
  hsp->mc = csnorm(hsp->mc);
  hsp->ac = Asc1 (th + DEG90, fi, sine, cose);
  hsp->cusp[1] = hsp->ac;
  hsp->cusp[10] = hsp->mc;
  if (hsy > 95) hsy = hsy - 32;	/* translate into capital letter */
  if (hsy == 'R') { /* Regiomontanus */
    fh1 = datan (tanfi * 0.5);
    fh2 = datan (tanfi * dcos (DEG30));
    hsp->cusp [11] =  Asc1 (DEG30 + th, fh1, sine, cose); 
    hsp->cusp [12] =  Asc1 (DEG60 + th, fh2, sine, cose); 
    hsp->cusp [2] =  Asc1 (DEG120 + th, fh2, sine, cose); 
    hsp->cusp [3] =  Asc1 (DEG150 + th, fh1, sine, cose); 
  } else if (hsy == 'C')  { /* Campanus */
    fh1 = dasin (dsin (fi) / 2);
    fh2 = dasin (sqrt (3.0) / 2 * dsin (fi));
    xh1 = datan (sqrt (3.0) / dcos (fi));
    xh2 = datan (1 / sqrt (3.0) / dcos (fi));
    hsp->cusp [11] = Asc1 (th + DEG90 - xh1, fh1, sine, cose);
    hsp->cusp [12] = Asc1 (th + DEG90 - xh2, fh2, sine, cose);
    hsp->cusp [2] = Asc1 (th + DEG90 + xh2, fh2, sine, cose);
    hsp->cusp [3] = Asc1 (th + DEG90 + xh1, fh1, sine, cose);
  } else if (hsy == 'V') { /* Vehlow */
    hsp->cusp [1] = csnorm(hsp->ac - DEG15);
    for (i = 2; i <=12; i++)
      hsp->cusp [i] = csnorm(hsp->cusp [1] + (i-1)*DEG30);
  } else if (hsy == 'E' || hsy == 'A') { /* equal */
    for (i = 2; i <=12; i++)
      hsp->cusp [i] = csnorm(hsp->cusp [1] + (i-1)*DEG30);
  } else if (hsy == 'K') {  /* Koch  */
    if (ABS4 (fi) >= DEG90 - ekl) {  /* within polar circle */
      /*
      * within polar circle we swap MC/IC if MC is on wrong side
      */
      CSEC acmc = difcs2n(hsp->ac, hsp->mc);
      if (acmc < 0) {
       hsp->mc = csnorm(hsp->mc + DEG180);
       hsp->cusp[10] = hsp->mc;
       acmc = difcs2n(hsp->ac, hsp->mc);
      }
      hsp->cusp [2] = hsp->ac + (DEG180 - acmc) / 3;
      hsp->cusp [3] = hsp->ac + (DEG180 - acmc) / 3 * 2;
      hsp->cusp [11] = hsp->mc + acmc / 3;
      hsp->cusp [12] = hsp->mc + acmc / 3 * 2;
    } else {
      sina = dsin (hsp->mc) * sine / dcos (fi);
      a = dasin (sina);
      c = datan (tanfi / dcos (a));
      ad3 = d2l (dasin (dsin (c) * sina) / 3.0);
      hsp->cusp [11] = Asc1 (th + DEG30 - 2 * ad3, fi, sine, cose);
      hsp->cusp [12] = Asc1 (th + DEG60 - ad3, fi, sine, cose);
      hsp->cusp [2] = Asc1 (th + DEG120 + ad3, fi, sine, cose);
      hsp->cusp [3] = Asc1 (th + DEG150 + 2 * ad3, fi, sine, cose);
    }; /* if */
  } else { /* Placidus */
    if (hsy != 'P')
      fprintf (stderr, "CalcHouses: make Placidus, unknown key %c\n", hsy);
    if (ABS4(fi) >= DEG90 - ekl) {  /* within polar circle */
      /*
      * within polar circle we swap MC/IC if MC is on wrong side
      */
      CSEC acmc = difcs2n(hsp->ac, hsp->mc);
      if (acmc < 0) {
       hsp->mc = csnorm(hsp->mc + DEG180);
       hsp->cusp[10] = hsp->mc;
       acmc = difcs2n(hsp->ac, hsp->mc);
      }
      hsp->cusp [2] = hsp->ac + (DEG180 - acmc) / 3;
      hsp->cusp [3] = hsp->ac + (DEG180 - acmc) / 3 * 2;
      hsp->cusp [11] = hsp->mc + acmc / 3;
      hsp->cusp [12] = hsp->mc + acmc / 3 * 2;
    } else {
      a = dasin (dtan(fi) * tane);
      fh1 = datan (SIN8 (cstorad_3 * a) / tane);
      fh2 = datan (SIN8 (cstorad_2_3 * a) / tane);
      /* ************  house 11 ******************** */
      rectasc = csnorm(DEG30 + th);
      if (rectasc % DEG180 == 0) {
	hsp->cusp [11] = rectasc;
      } else {
	tand = dtan (dasin (sine * dsin (Asc1 (rectasc, fh1, sine, cose))));
	if (fabs(tand) < TANERRLIMIT) {
	  hsp->cusp [11] = rectasc;
	} else {
	  f = datan (SIN8 (cstorad_3 * dasin (tanfi * tand))  /tand);  
	  /*  pole height */
	  hsp->cusp [11] = Asc1 (rectasc, f, sine, cose);
	  if (iteration_count > 1) {
	    tand = dtan (dasin (sine * dsin (hsp->cusp [11])));
	    f = datan (SIN8 (cstorad_3 * dasin (tanfi * tand)) / tand);
	    /*  pole height */
	    hsp->cusp [11] = Asc1 (rectasc, f, sine, cose);
	  }
	}
      }
      /* ************  house 12 ******************** */
      rectasc = csnorm(DEG60 + th);
      if (rectasc % DEG180 == 0) {
	hsp->cusp [12] = rectasc;
      } else {
	tand = dtan (dasin (sine*dsin (Asc1 (rectasc,  fh2, sine, cose))));
	if (fabs(tand) < TANERRLIMIT) {
	  hsp->cusp [12] = rectasc;
	} else {
	  f = datan (SIN8 (cstorad_2_3 * dasin (tanfi*tand))/tand);  
	  /*  pole height */
	  hsp->cusp [12] = Asc1 (rectasc, f, sine, cose);
	  if (iteration_count > 1) {
	    tand = dtan (dasin (sine * dsin (hsp->cusp [12])));
	    f = datan (SIN8 (cstorad_2_3 *dasin (tanfi*tand))/tand);  
	    /*  pole height */
	    hsp->cusp [12] = Asc1 (rectasc, f, sine, cose);
	  }
	}
      };
      /* ************  house  2 ******************** */
      rectasc = csnorm(DEG120 + th);
      if (rectasc % DEG180 == 0) {
	hsp->cusp [2] = rectasc;
      } else {
	tand = dtan (dasin (sine * dsin (Asc1 (rectasc,   fh2, sine, cose))));
	if (fabs(tand) < TANERRLIMIT) {
	  hsp->cusp [2] = rectasc;
	} else {
	  f = datan (SIN8 (cstorad_2_3 * dasin (tanfi * tand))/tand);
	  /*  pole height */
	  hsp->cusp [2] = Asc1 (rectasc,   f, sine, cose);
	  if (iteration_count > 1) {
	    tand = dtan (dasin (sine*dsin (hsp->cusp [2])));
	    f = datan (SIN8 (cstorad_2_3 * dasin (tanfi * tand))/tand);
	    /*  pole height */
	    hsp->cusp [2] = Asc1 (rectasc, f, sine, cose);
	  }
	}
      }
      /* ************  house  3 ******************** */
      rectasc = csnorm(DEG150 + th);
      if (rectasc % DEG180 == 0) {
	hsp->cusp [3] = rectasc;
      } else {
	tand = dtan (dasin (sine * dsin (Asc1 (rectasc, fh1, sine, cose))));
	if (fabs(tand) < TANERRLIMIT) {
	  hsp->cusp [3] = rectasc;
	} else {
	  f = datan (SIN8 (cstorad_3 * dasin (tanfi * tand))/tand);  
	  /*  pole height */
	  hsp->cusp [3] = Asc1 (rectasc, f, sine, cose);
	  if (iteration_count > 1) {
	    tand = dtan (dasin (sine*dsin (hsp->cusp [3])));
	    f = datan (SIN8 (cstorad_3 * dasin (tanfi * tand))/tand);
	    /*  pole height */
	    hsp->cusp [3] = Asc1 (rectasc, f, sine, cose);
	  }
	}
      }
    } /* if */
  } /* Plac */
  hsp->cusp [4] = csnorm(hsp->cusp [10] + DEG180);
  hsp->cusp [5] = csnorm(hsp->cusp [11] + DEG180);
  hsp->cusp [6] = csnorm(hsp->cusp [12] + DEG180);
  hsp->cusp [7] = csnorm(hsp->cusp [1] + DEG180);
  hsp->cusp [8] = csnorm(hsp->cusp [2] + DEG180);
  hsp->cusp [9] = csnorm(hsp->cusp [3] + DEG180);
} /* procedure houses */

/******************************/
centisec Asc1 (x1, f, sine, cose) 
centisec x1,f ;
REAL8 sine, cose;
{ 
  centisec Asc2();
  int n;
  x1 = csnorm(x1);
  n  = (x1 / DEG90) + 1;
  if (n == 1)
    return ( Asc2 (x1, f, sine, cose));
  else if (n == 2) 
    return (DEG180 - Asc2 (DEG180 - x1, - f, sine, cose));
  else if (n == 3)
    return (DEG180 + Asc2 (x1 - DEG180, - f, sine, cose));
  else
    return (DEG360 - Asc2 (DEG360- x1,  f, sine, cose));
}  /* Asc1 */

/******************************/
centisec Asc2 (x, f, sine, cose) 
centisec x,f;
REAL8 sine, cose;
{
  centisec ass;
  ass = datan (dsin (x) / ( - dtan (f) * sine + cose * dcos (x)));
  if (ass < 0)
    return (DEG180 + ass);
  else
    return (ass);
} /* Asc2 */

/******************************/
void RecalcAspects(struct AspectType *a)
{
  centisec diff,orbis;
  int p1, p2, i;
  struct aspRec *arp;
  if (a->ppos2 == NULL) {	/* no set ppos2, no interaspects */
    for (p1 = 0; p1 < a->NrOfPlanets; p1++) {
      a->Asp[p1][p1].index = 0;		/* ignore p1 conjunct p1 */
      for (p2 = p1 + 1; p2 < a->NrOfPlanets; p2++) {
	arp = &(a->Asp[p1][p2]);
	diff =  a->PlanetPos [p2] - a->PlanetPos [p1];
	if (diff >= DEG180) 
	  diff -= DEG360;
	else if (diff <  - DEG180) 
	  diff += DEG360;
	i = 1;                  
	arp->index = 0;
	while (i <= a->NrOfAspects) {
	  orbis = ABS4 (diff) - a->Angle [i];
	  if (ABS4 (orbis) <= a->Maxorb [i]) {
	    arp->index = i;
	    arp->orb = orbis;
	    break; 	/* out of while */
	  }
	  i++;
	}
	a->Asp [p2][p1].index = arp->index;
	a->Asp [p2][p1].orb = arp->orb;
      } /* for p2 */
    } /* for p1 */ 
  } else {	/* interaspects between set 1 and set 2 */
    for (p1 = 0; p1 < a->NrOfPlanets; p1++) {
      for (p2 = 0; p2 < a->NrOfPlanets; p2++) {
	arp = &(a->Asp[p1][p2]);
	diff =  a->ppos2 [p2] - a->PlanetPos [p1];
	if (diff >= DEG180) 
	  diff -= DEG360;
	else if (diff <  - DEG180) 
	  diff += DEG360;
	i = 1;                  
	arp->index = 0;
	while (i <= a->NrOfAspects) {
	  orbis = ABS4 (diff) - a->Angle [i];
	  if (ABS4 (orbis) <= a->Maxorb [i]) {
	    arp->index = i;
	    arp->orb = orbis;
	    break; 	/* out of while */
	  }
	  i++;
	}
      } /* for p2 */
    } /* for p1 */ 
  } 	/* else */
  a->dataValid = TRUE;
} 
