/************************************************************
   $Header: placalc.h,v 1.4 93/03/22 10:08:39 alois Exp $
   definitions and constants for planetary routines

   ATTENTION: PLACALC USERS ON MSDOS:
   See the note close to the end of this file regarding EPHE_PATH.

  ---------------------------------------------------------------
  | Copyright Astrodienst AG and Alois Treindl, 1991, 1993.	|
  | The use of this source code is subject to regulations made	|
  | by Astrodienst Zurich. The code is NOT in the public domain.|
  |								|
  | This copyright notice must not be changed or removed	|
  | by any user of this program.				|
  ---------------------------------------------------------------

************************************************************/
# ifndef _PLACALC_INCLUDED
# define _PLACALC_INCLUDED

#include "ourdef.h"	/* this is the basic include files which
			contains many definitions used throughout
			Astrodienst's programs.
			*/

# define WITH_CPJV	TRUE	/* include non-Astrodienst code for
				the 4 asteroids */

#define degtocs(x)	(d2l((x) * DEG))
#define cstodeg(x)	(double)((x) * CS2DEG)
/*************************************************************
 Exported functions:

 In all functions the variable jd_ad indicates the use of
 Astrodienst relative julian days, and jd the use of absolute
 julian days.
*************************************************************/
extern int nacalc(REAL8 jd_ad, centisec *plon, centisec *pspe);
extern int calcserv(int id, REAL8 t, int flag, int plalist, char *so);
extern void helup(REAL8 jd_ad);
extern void togeo(REAL8 le, REAL8 re, REAL8 l, REAL8 r, REAL8 z, REAL8 *alg, REAL8 *arg);
extern int  calc(int p,
		 REAL8 jd_ad,
		 int flag, 
		 REAL8 *alng,
		 REAL8 *arad,
		 REAL8 *alat,
		 REAL8 *alngspeed);
extern int rel_geo(int planet, double rau);
extern int hel( int	planet,	/* planet index as defined by placalc.h */
		REAL8	jd_ad,	/* relative juliand date, ephemeris time */
				/* Now come 6 pointers to return values. */
		REAL8	*al,	/* longitude in degrees */
		REAL8   *ar,	/* radius in AU */
		REAL8   *az,	/* distance from ecliptic in AU */
		REAL8   *alp, 	/* speed in longitude, degrees per day */
		REAL8   *arp,	/* speed in radius, AU per day */
		REAL8   *azp);   /* speed in z, AU per day */
extern int  moon(REAL8 *al, REAL8 *ar, REAL8 *az);
extern REAL8 fraction(REAL8 t);
extern REAL8 sidtime(REAL8 jd_ad, REAL8 ecl, REAL8 nuta);
extern REAL8 smod8360(REAL8 x);
extern REAL8 mod8360(REAL8 x);
extern REAL8 diff8360(REAL8 x, REAL8 y);
extern REAL8 test_near_zero(REAL8 x);
extern REAL8 deltat(REAL8 jd_ad);
extern void to_mean_ekl (double jd, double xyz[], double lrz[]);
extern void placalc_close_files();
extern char *placalc_get_errtext();
extern char *placalc_set_ephepath(char *new_path);	/* sets ephepath;
				if called with NULL, returns current path */

/*
 * because deltat() required a relative Julian date due to historical reasons,
 * we define a function deltatjd() with absolute Juliand date argument.
 */
# define deltatjd(x) deltat((x) - JUL_OFFSET)

/*
 * get the planet index for an AFL letter
 * returns -1 if the letter does not correspond to a planet.
 */
extern int afl2planet(int afl);
/*
 * get the AFL letter for a planet
 * returns -1 if planet has no letter.
 */
extern int planet2afl(int planet);

extern void longreorder(UCHAR *p, int n);
 
/*************************************************************
	exported variables
	(these cannot be used by DLL clients)
*************************************************************/

  
/*************************************************************
	definitions
*************************************************************/

/*
 * planet index numbers, used to identify a planet in calc() and
 * other related functions.
 */
#define CALC_ONLY_ECL_NUT -1	/* pseudo planet index for calls to calc */
#define SUN	0		/* used synonymously for earth too */
#define EARTH	0
#define MOON	1
#define MERCURY 2
#define VENUS	3
#define MARS	4
#define JUPITER	5
#define SATURN	6
#define URANUS	7
#define NEPTUNE 8
#define PLUTO	9
#define LASTPLANET PLUTO
#define MEAN_NODE  10
#define TRUE_NODE  11
#define CHIRON	   12
#define LILITH	   13	
#define CALC_N	   14	/* number of planets in placalc module */

#if WITH_CPJV
#define CERES     14
#define PALLAS    15
#define JUNO      16
#define VESTA     17
#endif

#define MAXPL_NACALC	(LILITH)	/* nacalc computes SUN..LILITH */

/*
 * houses and axes get also a 'planet' index number, but they
 * are not used by placalc itself
 * between chiron and AC we leave 6 places unused for some other celestial
 * bodies or chart factors.
 * Axes and houses cannot be computed with calls to calc(); they must
 * be computed with the housasp module functions.
 */
# define AC	   19
# define ASC	   19
# define MC	   20
# define CALC_N_MC  21	/* number of normal natal factors */

# define FIRST_HSNR 21
# define LAST_HSNR 32
# define NO_OF_HOUSES 12
#define MAX_PL_INDEX 32
/*
 * in a bitlist flag each planet is represented by a bit;
 * all 14 defined planets can be called at once with
 */
#define CALC_ALL_PLANET_BITS  ((1 << CALC_N) - 1)	/* bits 0..13 set */

/*
 * progressed planets have the same index (up to MC)
 * but with offset 50
 */
# define PROG_PLANET_OFFSET	50	/* progressed sun */
# define PROG_OFF	50	
# define PROG_SUN	(SUN + PROG_OFF)
# define PROG_MOON	(MOON + PROG_OFF)
# define PROG_MERCURY	(MERCURY + PROG_OFF)
# define PROG_VENUS	(VENUS + PROG_OFF)
# define PROG_MARS	(MARS + PROG_OFF)
# define PROG_AC	(AC + PROG_OFF)
# define PROG_ASC	(AC + PROG_OFF)
# define PROG_MC	(MC + PROG_OFF)

/*
 * AFL: Astrological factor letters for use in selections strings.
 * Each factor (planet, house cusp etc) has a typical letter which
 * can be combined in a selection string for specifying a certain
 * sequence of factors for a table or other kind of display.
 * The function afl2planet() can be used to translate the AFL letters
 * into planet indices.
 * The function planet2afl translates a planext index into the AFL letter.
 */

# define AFL_SUN	'0'
# define AFL_MON	'1'
# define AFL_MER	'2'
# define AFL_VEN	'3'
# define AFL_MAR	'4'
# define AFL_JUP	'5'
# define AFL_SAT	'6'
# define AFL_URA	'7'
# define AFL_NEP	'8'
# define AFL_PLU	'9'
# define AFL_CHI	'c'
# define AFL_LIL	'i'	/* mean Lilith: direction of lunar aphel */
# define AFL_AC		'A'
# define AFL_MC		'M'
# define AFL_TNODE	'N'	/* TRUE_NODE */
# define AFL_MNODE	'n'	/* MEAN_NODE */
# define AFL_CER	'C'	
# define AFL_PAL	'P'
# define AFL_JUN	'J'
# define AFL_VES	'V'

/*
 * other AFL definitions not recognized by afl2planet()
 */
# define AFL_SIDT	's'	/* sidereal time */
# define AFL_WDAY	'd'	/* day of week column */
# define AFL_HOUSE	'H'	/* any house cusp */

# define apl2planet	afl2planet	/* change of original name */


# define J2000	2451545.0	/* Epoch of JPL ephemeris DE200, absolute */
# define J1950  2433282.423	/* Epoch of JPL ephemeris DE102 */
#define JUL_OFFSET 2433282.0	/* offset of Astrodienst relative Julian date */
#define EPOCH1850 -36524.0	/* jupiter,saturn 0 jan 1850, 12:00 */
#define EPOCH1900 -18262.0	/* inner planets  0 jan 1900, 12:00 */
#define EPOCH1950  0.0		/* pluto 	  0 jan 1950, 12:00 */
                                /* this is the origin of the Astrodienst
                                   relative julian calendar system */
#define EPOCH1960  3653.0	/* uranus,neptune 1 jan 1960, 12:00 */

#define ENDMARK  99		/* used to mark the end of disturbation terms */

#define NODE_INTERVAL	0.005	/* days, = 7m20s */
#define MOON_SPEED_INTERVAL  0.0001 /* 8.64 seconds later */

/*
 * flag bits used in calc and calcserv
 */
# define CALC_BIT_HELIO	1	/* geo/helio */
# define CALC_BIT_NOAPP	2	/* apparent/true positions */
# define CALC_BIT_NONUT	4	/* true eq. of date/ mean equ. of date */
# define CALC_BIT_EPHE	8	/* universal/ephemeris time */
# define CALC_BIT_SPEED	16	/* without/with speed */
# define CALC_BIT_BETA	32	/* without/with latitude */
# define CALC_BIT_RGEO	64	/* without/with relative rgeo */
# define CALC_BIT_RAU	128	/* without/with real radius */
# define CALC_BIT_MUST_USE_EPHE	256	/* epheserv may not use calc */
# define CALC_BIT_MAY_USE_EPHE	512	/* calcserv may use ephread */

# if HPUNIX
#   define XYZ_FILE  "/users/ephe/xyz1950"	/* for creating */
#   define LRZ_FILE "/users/ephe/LRZ5_"	/* for creating */
#   define OPEN_EPHE "r"	
#   define EPHE_PATH "/users/ephe"	/* for  LRZ and CHI */
# else

/* ephemeris file path for MSDOS */

/*****************************************************************************
******************************************************************************
  ATTENTION: PLACALC USERS ON MSDOS:

  EPHE_PATH indicates where the ephemeris files LRZ_nnn and CHI_nnn
  are found in you system.
  EPHE_PATH is only used once at the beginning of placalc.c to initialize
  the global variable ephe_path.

  If you do not have the ephemeris files in \PAIR\EPHE, you must do one of
  two things:
  - change the #define statement below to point to your ephemeris directory
  - OR change the global char *ephe_path before  you call any placalc
  function.
  Take care, that ephe_path ends NOT with the directory character ('\' in DOS).
******************************************************************************
*****************************************************************************/
#   define EPHE_PATH "\\pair\\ephe"	

#   define OPEN_EPHE "rb"		/* read binary */
# endif

# define EPHE_STEP	80		/* days step in LRZ ephe */
# define EPHE_DAYS_PER_FILE 100000	/* days per ephe file */
# define EPHE_OUTER "LRZ5_"		/* file name prefix */
# define EPHE_OUTER_BSIZE	60	/* blocksize  */
# define EPHE_CHIRON "CHI_"		/* file name prefix */
# define EPHE_CHIRON_BSIZE	12	/* blocksize  */
#if WITH_CPJV
# define EPHE_ASTER "CPJ2_"        /* file name prefix */
# define AST_EPHE_STEP	   20	   /* ephemeris files for asteroids */
# define EPHE_ASTER_BSIZE  48      /* blocksize */
#endif

/********************************************
  About the format of the ephemeris files
  ----------------------------------------
  We use currently ephemeris files with steps of 80 days.
  There are 1250 "records" in each file, so that one file
  spans 100000 days.
  We have two types of ephemeris files:
  LRZ5_nn 	for the outer planets Jupiter ... Pluto
  CHI_nn	for Chiron.
  nn is an expression derived from the first julian daynumber on the
  file. Jd 2100'000 to 2199'920 is on file LRZ5_21 and CHI_21;
  for negative Jd we use the filenames LRZ5_Mxx  with M indicating the minus.

  Given the jd for which you want the ephemeris, it is simple to build
  the filename and use fseek() within the file to go where the data is.
  This is done by the functions lrz_file_posit() and chi_file_posit().

  The stored coordinates are for each date and planet:
  L = ecliptic longitude relative to mean exquinox of date,
      in units of milliseconds of arc (1/3600000 degree) as type 32-bit long;
  R = radius vector, units of 10-7 AU, as type 32-bit long.
  Z = disctance of ecliptic; Z = R * sin(latitude); in units of 10-7 AU,
      as type 32-bit long.
  The data is stored in the byte ordering of the Astrodienst HPUX machines,
  which is most significant byte first. It is not the same byte ordering
  as on Intel processors; the function longreorder() converts between
  the disk file format and the internal format of MSDOS machines.

  For LRZ5- files, we have 60-byte records LRZ(Jupiter),LRZ(Saturn),
  LRZ(Uranus), LRZ(Neptune), LRZ(Pluto). 
  For CHI-files we have 12-byte records LRZ.

************************************************/

# endif /* _PLACALC_INCLUDED */
