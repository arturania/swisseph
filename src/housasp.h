/*******************************************************
$Header: housasp.h,v 1.2 91/11/16 16:21:32 alois Exp $

header file for house and aspect calculation 
defines structures AspectType and houses and tells
which functions are exported by housasp.c

  ---------------------------------------------------------------
  | Copyright Astrodienst Zurich AG and Alois Treindl, 1991.	|
  | The use of this source code is subject to regulations made	|
  | by Astrodienst Zurich. The code is NOT in the public domain.|
  |								|
  | This copyright notice must not be changed or removed	|
  | by any user of this program.				|
  ---------------------------------------------------------------

Version 8-July-89
*******************************************************/
# ifndef _HOUSASP_INCLUDED
# define _HOUSASP_INCLUDED

#define MAXPLANETS  16

/*
 * definitions for aspect numbering; we always have a name and an angle 
 * due to historical reasons index 0 is unused, conjunct is 1.
 * We define three different names for the aspects:
 * ASP_CONJ (always 4 letters), ASP_0 and CONJ.
 */
# define ASP_CONJ	1
# define ASP_0		1
# define ASP_OPPO	2
# define ASP_180	2
# define ASP_SQUA	3
# define ASP_90		3
# define ASP_TRIN	4
# define ASP_120	4
# define ASP_SEXT	5
# define ASP_60		5
# define ASP_SMSX	6
# define ASP_30		6
# define ASP_QCNX	7
# define ASP_150	7
# define ASP_SMSQ	8
# define ASP_45 	8
# define ASP_SQSQ	9
# define ASP_135	9
# define ASP_QINT	10
# define ASP_72 	10
# define ASP_BQIN	11
# define ASP_144	11

# define CONJ	ASP_CONJ	
# define OPPO	ASP_OPPO	
# define SQUA	ASP_SQUA	
# define TRIN	ASP_TRIN	
# define SEXT	ASP_SEXT	
# define SMSX	ASP_SMSX	
# define QCNX	ASP_QCNX	
# define SMSQ	ASP_SMSQ	
# define SQSQ	ASP_SQSQ	
# define QINT	ASP_QINT	
# define BQIN	ASP_BQIN	

#define MAXASPECTS      ASP_BQIN

/*
 * for compact encoding of aspect lists we set bit 1 for CONJ, bit 2 for OPPO
 * and so on. asp_bit(asp) deleivers the mask.
 */
#define ALL_ASP_BITS	1022	/* bit mask with all aspect bits set */
#define STRONG_ASP_BITS	62	/* bit mask with strong aspect bits set */
#define HARD_ASP_BITS	14	/* bit mask with hard aspect bits set */

#define asp_bit(asp)	(1 << (asp))

/*
 * sometimes it is desirable to have a compact way to express planet-aspect
 * combinations.
 * We define PASP(planet,aspect) as a constant,  100*planet  + aspect.
 * We can then write things like:
 * case PASP(PLUTO, JONJ): ...
 * A definition like p << 8 + a would be faster but the combinations would
 * be harder to read.
 */
#define PASP(p,a)	(100*(p) + (a))

/*
 * used to initialize an array centisec angles[MAXASPECTS+1]
 */
# define ASP_ANGLES {0,	0*DEG,	180*DEG, 90*DEG, 120*DEG,\
	60*DEG, 30*DEG, 150*DEG, 45*DEG, 135*DEG, 72*DEG, 144*DEG}

struct  AspectType { 
  	AS_BOOL	dataValid;	/* used as boolean */
	int     NrOfPlanets,
	        NrOfAspects;
	centisec *PlanetPos;	/* pointer to an array of planet positions 
				   [0..NrOfPlanets-1]; the user of the
				   structure must set this pointer to his
				   array of planet positions */
        centisec *ppos2;	/* second set for mutual aspects only; if
				   not NULL, interaspects are calculated */
	centisec *Maxorb;	/* pointer to an array [0..NrOfAspects] of
				   maximum orbes; element[0] not used;
				   the user of the structure must set this 
				   pointer to his array of maxorbs */
	centisec *Angle;	/* pointer to Angle[0..NrOfAspects] of the
				   angles of aspects; [0] not used; the
				   user must set this pointer to his array
				   of angles */
	struct aspRec { 
		int  index; 	/* number of the found aspect */
		centisec orb;
	       }
		Asp [MAXPLANETS] [MAXPLANETS];
	     };

struct  houses {
	  centisec cusp[13];
	  centisec ac;
	  centisec mc;
	};

# define HOUSES 	struct houses

/**********************************
  functions exported from housasp.c 
***********************************/

extern int HouseNr(HOUSES *h, CSEC p);
  /*
  return in which house pp is,
  The caller is responsible for proper initialization of cusps
  */

extern int InpHouseNr(HOUSES *h, CSEC p, CSEC *cuspoff);
  /* returns the interpretation-house number, where pp is in;
   * an interpretation house is defined differently from a normal
   * house: the cusps are offset, so that it begins and ends
   * a little earlier.
   * cusp[1..12] and  cuspoff[1..12]  must be initialized 
   */

extern void CalcHouses(CSEC th, CSEC fi, CSEC ekl, char hsy, int icnt,
	struct houses *h);

extern void RecalcAspects(struct AspectType *a);


# endif  /* _HOUSASP_INCLUDED */
