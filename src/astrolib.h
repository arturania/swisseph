/*******************************************
$Header: astrolib.h,v 1.2 91/11/16 16:21:02 alois Exp $
astrolib.h

NOTE for users of the PLACALC package:
This include files declares many function which
are NOT part of the PLACALC package. It is only
included because it also declares some items contained
in PLACALC.

ourdef.h should be included before this!

*******************************************/
# ifndef _ASTROLIB_INCLUDED
# define _ASTROLIB_INCLUDED

/* define days of the week for day_of_week() results */
# define DOW_MONDAY	0
# define DOW_SUNDAY	6

/* values for gregflag */
# define JUL_CAL	0
# define GREG_CAL	1

# define START_OF_EXTRA_CHAR 160
# define DEGREE_CHAR 176	/* Latin1 degree character */

# define ORD_DIV	10000		/* for kdv and clients */

/* makros for bit operations */
# define clear_bit(v,bit_nr) 	((v) & ~(1L << (bit_nr)))
# define set_bit(v,bit_nr) 	((v) | (1L << (bit_nr)))
# define bit(bit_nr)		(1L << (bit_nr))
# define check_bit(v,bit_nr)	((v) & (1L << (bit_nr)))

# define SPLIT_CS_ROUND_SEC    1
# define SPLIT_CS_ROUND_MIN    2
# define SPLIT_CS_ROUND_DEG    4
# define SPLIT_CS_ROUND_ASTRO  8
# define SPLIT_CS_MOD_360      16
# define SPLIT_CS_MOD_24       32

/*******************************************
type definitions
********************************************/
#define ADATE struct adate

ADATE {	/* date structure used by revjuls and juldays */
       int day, month, year;
       centisec csec;
       };

struct spl_cs {
  centisec cs;
  int sign;
  int degree;
  int min;
  int sec;
};

extern UCHAR *atl_collaps (UCHAR *isp, UCHAR *osp);	/* atlclps.c */
extern UCHAR *atl_uncollaps (UCHAR *isp, UCHAR *osp);

extern isalpha256(int);				/* ctype256.c	*/
extern isalnum256(int);
extern isupper256(int);
extern toupper256(int);
extern tolower256(int);
extern isspace256(int);
extern isvowel256(int);

/* functions exported by gettext.c */
  extern open_text( char *filename, int max);	/* returns OK or ERR */
  extern void close_text(void);	
  extern char *get_text(int nr);
  extern char get_textc(int nr);
  extern get_text_array(char *array[], int nr, int arraysie);
	/* returns OK or ERR */

# define isdigit256(x) (isascii(x) && isdigit(x))

/****************************************
  converts astrodienst daynumber to string
****************************************/
extern char *adate_to_asc(char *cp, int adate);
extern char *adate_to_asc4(char *cp, int adate);
extern char *adate_to_asc_num(char *cp, int adate);

extern double adate_to_julday(int adate);
extern int ulday_to_adate(int jd);

extern int cut_string(char *s, char *cpos[], int nmax);		/* cutstr.c */
extern char *remove_nl(char *s);	
extern int comma_count(char *s);
extern char *skip_white(char *s);
extern char *skip_to_next_word(char *s);

extern int cut_string_any(char *s, char *cutlist, char *cpos[], int nmax);
		/* cutstr2.c	*/

extern long d2l(double x);				/* d2l.c	*/

extern int date_conversion 				/* dateconv.c */
	    (int d,
	     int m,
	     int y,		/* day, month, year */
	     centisec gmtime, 	/* greenwich time in centiseconds */
	     char c, 	/* calendar g[regorian]|j[ulian]|a[stro = greg] */
	     REAL8 *tgmt	/* julian date relative 0.Jan.1950 12:00 gmt */
				/* shift is 2433282 from absolute Julian date */
	    );			/* return OK or ERR */

extern int degstr_conversion (char *s, centisec *t);		/* degstr.c */

extern int day_of_week(double jd);			/* julday.c */
	/* monday = 0, ... sunday = 6 */

extern double dtime(void);				/* ourtime.c */
	/* returns seconds since 1970, with fraction
	   and a resolution of microseconds */

extern void ecl_to_equ(double al, double ab, double obl, double *ra, double *d);
							/* ecl2equ.c */

extern int filemv(char *source, char*target);	/* filemv.c */
extern int filecp(char *source, char*target);	/* filemv.c */

extern int fixstar(char *star, double jd, double *lon, double *lat);
						/* fixstar.c*/

/****************************************
 * gets astrodienst daynumber from system time
****************************************/
extern int get_adate(void);

/* get a long consisting of hhmmss of current time */
extern long get_hms(void);				/* ourtime.c */

extern int get_lpb(char *pname);			/* get_lpb.c */

extern int hp2ibm(int c);				/* hp2ibm.c	*/
extern int hp2iso(int c);				/* hp2iso1.c	*/
extern char *str_hp2iso(char *c);			/* hp2iso1.c	*/
extern char *str_ibm2iso(char *c);			/* hp2iso1.c	*/
extern int iso2hp(int c);				/* iso12hp.c	*/
extern char *str_iso2hp(char *c);			/* iso12hp.c	*/
extern char *str_iso2ibm(char *c);			/* iso12hp.c	*/

extern int ibm2hp(int c);				/* ibm2hp.c	*/
extern int mac2hp(int c);				/* mac2hp.c	*/
extern int hp2mac(int c);				/* hp2mac.c	*/

/*
 * interpolation of third order with Newton and Stirling formulas for
 * vectors of double or long.
 * One needs n+1 input values for n output values.
 * u is the normalized x-value to which we interpolate.
 * u is legal within -0.5 .. + 1.0
 * The same array can be used for output and input; the last value will
 * then be unchanged after the call.
 * Attention, the function values must be steady; they cannot have jumps
 * like typical planetary longitudes have when they cross zero degree.
 * You must use make_steady() before calling interpol() otherwise,
 * and later  use norm_range() to normalize again into your range.
 */
extern int interpod(double in[], double out[], double u, int n); /*interpod.c */
extern void make_steady_d(double x[], int n, double range);
extern double norm_range_d(double x, double range);

extern int interpol(long in[], long out[], double u, int n); /* interpol.c */
extern void make_steady_l(long x[], int n, long range);
extern long norm_range_l(long x, long range);


extern double julday(int month, int day, int year, double hour, int gregflag);
							/* julday.c	*/
extern double juldays(int gregflag, ADATE *adp); /* juldays.c	*/

extern char *kdv_collaps (char *osp, char *isp);	/* kdvclps.c */
extern char *kdv_collaps_w (char *osp, char *isp, char *w);
extern char *string_to_lower(char *c);

extern void log_report(FILE *fp, char *s1, char *s2);	/* writelog.c */

extern char *get_file_name(char *file_path );
extern char *makepath(char *d, char *s);	/* makepath.c 	*/

extern char *out_ll(char *s, centisec l, char ch_dflt, char ch_opps);
						/* outll.c	*/
extern char *out_merid(char *s, centisec l);	/* outmerid.c	*/

extern char *nord_to_date(char *cp, long nord);	/* outnord.c */
extern char *nord_to_date4(char *cp, long nord);	/* outnord.c */
extern char *out_nord (char *s, long nord);	/* outnord.c */
extern char *out_nords (long nord);		/* outnord.c */
extern char *out_nordhs (long nord, int nhor);/* outnord.c */

extern char *out_time(char *s, centisec l);	/* outtime.c	*/

extern char *print_date_time(char *s);
/*
 * output current date and time in Format: 20-Jul-1989 23:20
 */
extern char *print_date_time2(char *s);		/* only 2-digit year */

extern char *print_date_time_sec(char *s);
/*
 * output current date and time with 1/100th of second precision
 */

extern char *print_timestamp(char *s, long ntst);
/*
 * output ntst date and time in format: 20-Jul-1989 23:20
 */

extern void revjul (double u,			/* revjul.c */
	      int gregflag,
	      int *jmon, int *jday, int *jyear,
	      double *jut);

extern void revjuls(double u, int gregflag, ADATE *adp); /* revjuls.c */

extern char *rm_nonprint( char *);			/* rmuscore.c	*/
extern char *rm_uscore( char *);			/* rmuscore.c	*/
extern char *right_trim( char *);			/* rmuscore.c	*/
extern char *strsqueeze( char *);			/* rmuscore.c	*/

extern void split_cs(struct spl_cs *psplit, int roundflag); /* splitcs.c */

extern char *stristr(char *si, char *pi);		/* stristr.c	*/
/* extern char *strstr(char *si, char *pi);		 stristr.c	*/

extern char *sread(char *s, char *r, int max);		/* sread.c	*/

extern centisec simple_degstr_conversion(char *s);	/* sdegstr.c	*/

extern int timestr_conversion(char *s, centisec *t);	/* timestr.c	*/

extern char *upcase_first_letters( char *s);		/* upcase1.c	*/

extern char *TimeString (CSEC t, int sep, AS_BOOL suppressZero); /* outdeg.c */
extern char *LonLatString (CSEC t, char pchar, char mchar);	/* outdeg.c */
extern char *DegreeString (CSEC t);				/* outdeg.c */

extern void a_srandom (long x);	/* random.c */
extern long lrandom(void);
extern double drandom(void);

/********************************************************
functions from csec.c
********************************************************/

/*
 * round the arguments to full seconds, takes care with zodiac sign changes:
 * does not round 29³29'59.6" to 0³ of next sign but to 29³29'59"
 */
extern centisec roundsec(centisec x);

/* normalizes p into 0..DEG360 by doing a kind of modulo */
extern centisec csnorm(centisec p);
/* the same for degree instead of centisec */
extern double degnorm(double x);

/* normalized circle distance p1 - p2, result in [0..360[ */
extern centisec difcsn(centisec p1, centisec p2);	
extern double  difdegn(double x1, double x2);

/* normalized circle distance p1 - p2, result in [-180..180[ */
extern centisec difcs2n(centisec p1, centisec p2);	
extern double  difdeg2n(double x1, double x2);

extern double dcos(centisec x);			/* trigo. functions for CSEC */
extern double dsin(centisec x);
extern double dtan(centisec x);
extern centisec datan(double x);
extern centisec dasin(double x);

extern double solcross(double x2cross, double jd0_ad, int flag); /* solcross.c*/
extern double mooncross(double x2cross, double jd0_ad, int flag);

#define SIND(x)	sin((x) * DEGTORAD)
#define COSD(x)	cos((x) * DEGTORAD)
#define TAND(x)	tan((x) * DEGTORAD)
#define ATAND(x)	(atan((x)) * RADTODEG)
#define ATAN2D(x,y)	(atan2((x),(y)) * RADTODEG)

# endif /* _ASTROLIB_INCLUDED */
