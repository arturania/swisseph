/************************************************************
   $Header: ourdef.h,v 1.2 91/11/16 16:21:37 alois Exp $
   definitions and constants for all Astrodienst C programs
   contains only declarations and #defines, no global variables.
   auto-dectection of MSDOS (TURBO_C or MS_C) or HPUNIX
   
************************************************************/

#ifndef _OURDEF_INCLUDED /* allow multiple #includes of ourdef.h */
#define _OURDEF_INCLUDED
 
# define MY_TRUE 1	/* for use in other defines, before TRUE is defined */
# define MY_FALSE 0	/* for use in other defines, before TRUE is defined */

#ifdef WIN32		/* Microsoft VC 5.0 does not define MSDOS anymore */
# define MSDOS MY_TRUE
#endif

#ifdef MSDOS	/* already defined by some DOS compilers */
# undef MSDOS
# define MSDOS MY_TRUE
#endif

#ifdef __TURBOC__	/* defined by  turboc */
# ifndef MSDOS
#   define MSDOS MY_TRUE
# endif
# define TURBO_C
#endif

#ifdef __SC__	/* defined by  Symantec C */
# ifndef MSDOS
#   define MSDOS MY_TRUE
# endif
# define SYMANTEC_C
#endif

#ifdef __WATCOMC__	/* defined by  WatcomC */
# ifndef MSDOS
#   define MSDOS MY_TRUE
# endif
# define WATCOMC
#endif

#if MSDOS
#  define HPUNIX MY_FALSE
#  define INTEL_BYTE_ORDER 1
#  ifndef TURBO_C
#    define MS_C	/* assume Microsoft C compiler */
#  endif
# define MYFAR far
#else
#  define MSDOS MY_FALSE
#  define HPUNIX MY_TRUE
#  ifndef _HPUX_SOURCE
#    define _HPUX_SOURCE
#  endif
#  define MYFAR
#endif

#include <math.h>
#ifndef FILE
#include <stdio.h>
#endif

#include <stdlib.h>
# if HPUNIX
#include <unistd.h>
# endif

#ifndef TRUE 
#define TRUE 1
#define FALSE 0
#endif

#ifndef OK 
#define OK (0)
#define ERR (-1)
#endif


typedef unsigned char UCHAR;
#define UCP	(UCHAR*)
#define SCP	(char*)
 
#if HPUNIX
/*#include <malloc.h> */
  typedef double  REAL8;  /* real with at least 64 bit precision */
  typedef float   REAL4;  /* real with at least 32 bit precision */
  typedef long     INT4;   /* signed integer with at least 32 bit precision */
  typedef unsigned long UINT4; 
			/* unsigned integer with at least 32 bit precision */
  typedef int     INT2;   /* signed integer with at least 16 bit precision */
  typedef int     INT1;   /* signed integer with at least 8  bit precision */
  typedef int     AS_BOOL;
  typedef unsigned short UINT2;	/* unsigned 16 bits */
# define ABS4	abs		/* abs function for long */
# endif

#ifdef DOS_DEGREE		/* use compiler switch to get DOS character! */
# define ODEGREE_CHAR	248	/* DOS degree character */
#else
# define ODEGREE_CHAR	176	/* Latin1 degree character */
#endif

#if MSDOS 
#ifdef TURBO_C
#  include <alloc.h>		/* MSC needs malloc ! */
#else
#  include <malloc.h>
#endif
#include <stdlib.h>
# define SIGALRM SIGINT
  typedef double  REAL8;  /* real with at least 64 bit precision */
  typedef float   REAL4;  /* real with at least 32 bit precision */
  typedef long    INT4;   /* signed integer with at least 32 bit precision */
  typedef unsigned long UINT4;
                          /* unsigned integer with at least 32 bit precision */
  typedef int     INT2;   /* signed integer with at least 16 bit precision */
  typedef int     INT1; /* signed integer with at least 8  bit precision */
  typedef int     AS_BOOL;
  typedef unsigned int UINT2;	/* unsigned 16 bits */
# define ABS4	labs		/* abs function for long */ 
#endif

# define CHARSET_ISO_LATIN_1 TRUE	/* used by ctype256 */

#ifndef HUGE
#  define HUGE 1.7E+308     /* biggest value for REAL8 */
#endif
#ifndef M_PI
#  define M_PI 3.14159265358979323846
#endif
 
#define forward static

#define MINUTE_CHAR  39	/* minute character */
#define SECOND_CHAR  34	/* second character */
#define AS_MAXCH 256    /* used for string declarations, allowing 255 char+\0 */
#define COMMA ','
 
#define COS8 cos
#define SIN8 sin
#define ASIN8 asin
#define TAN8 tan
#define ATAN8 atan
#define ATAN28 atan2
#define EXP10(x) pow(10.0,(x))
#define ABS8(x) fabs(x)
 
#define TANERRLIMIT 1.0E-10     /* used to check for arguments close to pi */
#define NEAR_ZERO   1.0E-16     /* used to compare for divisors close to 0 */
#define BIGREAL  1.0E+38
 
#define DEGTORAD 0.0174532925199433
#define RADTODEG 57.2957795130823
 
typedef INT4    centisec;       /* centiseconds used for angles and times */
#define CS	(centisec)	/* use for casting */
#define CSEC	centisec	/* use for typing */
	

#define DEG     360000L  /* degree expressed in centiseconds */
#define DEG7_30 (2700000L)	/* 7.5 degrees */
#define DEG15   (15 * DEG)
#define DEG24   (24 * DEG)
#define DEG30   (30 * DEG)
#define DEG60   (60 * DEG)
#define DEG90   (90 * DEG)
#define DEG120  (120 * DEG)
#define DEG150  (150 * DEG)
#define DEG180  (180 * DEG)
#define DEG270  (270 * DEG)
#define DEG360  (360 * DEG)
 
#define CSTORAD  4.84813681109536E-08 /* centisec to rad: pi / 180 /3600/100 */
#define RADTOCS  2.06264806247096E+07 /* rad to centisec 180*3600*100/pi */
 
#define DEG2MSEC 3600000.0	/* degree to milliseconds */
#define DEG2CSEC 360000.0	/* degree to centiseconds */

#define SEC2CSEC	100	/* seconds to centiseconds */

#define CS2DEG	(1.0/360000.0)	/* centisec to degree */
#define CS2CIRCLE (CS2DEG/360.0)	/* centisec to circle */
#define AU2INT	 1e7		/* factor for long storage of A.U. */

#define CSMIN	6000L
#define CSSEC	100L

# define SINDEG(x)	sin(DEGTORAD * (x))
# define COSDEG(x)	cos(DEGTORAD * (x))
# define TANDEG(x)	tan(DEGTORAD * (x))
# define SINCS(x)	sin((double)(CSTORAD * (x)))
# define COSCS(x)	cos((double)(CSTORAD * (x)))
# define TANCS(x)	tan((double)(CSTORAD * (x)))

typedef long CMM;		/* plotmod unit 0.01 mm */
# define MM2CP       283.46457		/* factor for MM to CP */
# define CMM2PT       0.028346457	/* factor for CMM to points */
# define CMM2CP      2.8346457		/* factor for CMM to CPT */
# define PT2CMM       35.27777778	/* factor for points to CMM */
# define CP2CMM      0.352777778	/* factor for CPT to CMM */

typedef double *vector;

/* control strings for fopen()	*/
#if HPUNIX
#  define BFILE_R_ACCESS "r"	/* open binary file for reading */
#  define BFILE_RW_ACCESS "r+"	/* open binary file for writing and reading */
#  define BFILE_W_CREATE "w"	/* create/open binary file for write*/
#  define BFILE_A_ACCESS "a+"	/* create/open binary file for append*/
#  define FILE_R_ACCESS "r"	/* open text file for reading */
#  define FILE_RW_ACCESS "r+"	/* open text file for writing and reading */
#  define FILE_W_CREATE "w"	/* create/open text file for write*/
#  define FILE_A_ACCESS "a+"	/* create/open text file for append*/
#  define O_BINARY 0		/* for open(), not defined in Unix */
#  define OPEN_MODE 0666	/* default file creation mode */

#  define DIR_GLUE "/"		/* glue string for directory/file */
#endif

#if MSDOS
#  define BFILE_R_ACCESS "rb"	/* open binary file for reading */
#  define BFILE_RW_ACCESS "r+b"	/* open binary file for writing and reading */
#  define BFILE_W_CREATE "wb"	/* create/open binary file for write*/
#  define BFILE_A_ACCESS "a+b"	/* create/open binary file for append*/
#  define FILE_R_ACCESS "rt"	/* open text file for reading */
#  define FILE_RW_ACCESS "r+t"	/* open text file for writing and reading */
#  define FILE_W_CREATE "wt"	/* create/open text file for write*/
#  define FILE_A_ACCESS "a+t"	/* create/open text file for append*/
#  define OPEN_MODE 0666	/* default file creation mode */

/* attention, all backslashes for msdos directry names must be written as \\,
   because it is the C escape character */
#  define DIR_GLUE "\\"		/* glue string for directory/file */
#endif
#endif /* _OURDEF_INCLUDED */
