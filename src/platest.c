/********************************************************
  $Header: platest.c,v 1.5 93/07/19 22:50:39 alois Exp $
  platest.c    test of placalc calculations
  by Alois Treindl

  ---------------------------------------------------------------
  | Copyright Astrodienst Zurich AG and Alois Treindl, 1989.	|
  | The use of this source code is subject to regulations made	|
  | by Astrodienst Zurich. The code is NOT in the public domain.|
  |								|
  | This copyright notice must not be changed or removed	|
  | by any user of this program.				|
  ---------------------------------------------------------------

  How this program serves as an example for using PLACALC:
  --------------------------------------------------------
  Check all lines marked with  * PLACALC: *.
  The main steps are:
  - From your calendar date and time, compute the Julian day number jd.
  - If your time is Universal Time, not Ephemeris Time, add deltat().
  - use the relative jd for calling calc()
  - call calc() for the planet you want and get the geocentric positions.
  As in all software, the rest of this example program deals with
  user input (here mainly in form of command line options)
  and with data output.

  There are some functions contained in PLACALC which are not used
  in this example, like CalcHouses() in housasp.c!
********************************************************/

char *info = "\n\
  Platest Computes a complete set of geocentric planetary positions,\n\
  for a given date or a sequence of dates.\n\
  Input can either be a date or an absolute julian day number.\n\
  0:00 (midnight).\n\
  With the proper options, platest can be used to output a printed\n\
  ephemeris and transfer the data into other programs like spreadsheets\n\
  for graphical display.\n\
\n\
  Command line options:\n\
	-pPSEQ	do planet sequence PSEQ. Default is \"0123456789nNci\".\n\
		See the letter coding below.\n\
	-nN	output data for N consecutive days; if no -n option\n\
		is given, the default is 1. If the option -n without a\n\
		number is given, the default is 20.\n\
	-sN	timestep N days, default 1. This option is only meaningful\n\
		when combined with option -n.\n\
	-edirPATH change the directory of the ephemeris files \n\
	-e	apply delta t; date is 0:00 UT instead of ET\n\
	-c	calcserv test\n\
	-d   	differential ephemeris: for each pair in PSEQ, display p1 - p2.\n\
		example: -p02 -d -fJl -n366 -b1.1.1992 prints the longitude\n\
		distance between SUN (planet 0) and MERCURY (planet 2)\n\
		for a full year starting at 1 Jan 1992.   NOTE: The radial\n\
		distance, if included in the output, is NOT differential.\n\
	-hel	compute heliocentric positions\n\
	-h	don\'t print the header before the planet data. This option\n\
		is useful when you want to paste the output into a\n\
		spreadsheet for displaying graphical ephemeris.\n\
	-bDATE	use this begin date instead of asking; use -b1.1.1992 if\n\
		the begin date string contains blanks; use format -bj2400000.5\n\
		to express the date as absolute Julian day number.\n\
		Note: the date format is day month year (European style).\n\
 	-gPPP	use PPP as gap between output columns; default is a single\n\
		blank.  -g followed by white space sets the\n\
		gap to the TAB character; which is useful for data entry\n\
		into spreadsheets.\n\
	 -fSEQ	use SEQ as format sequence for the output columns;\n\
		default is PLBRuS.\n\
         -iFLAG include fixstars, default flag = 63\n\
\n\
	-?	display this info\n\
\n\
  Planet selection PSEQ letters:\n\
	0 SUN (character zero)\n\
	1 MOON (character 1)\n\
	2 MERCURY\n\
	....\n\
	9 PLUTO\n\
	n MEAN LUNAR NODE\n\
	N TRUE LUNAR NODE\n\
	c CHIRON\n\
	i LILITH\n\
	C Ceres\n\
	P Pallas\n\
	J Juno\n\
	V Vesta\n\
\n\
  Output format SEQ letters:\n\
  In the standard setting five columns of coordinates are printed with\n\
  the default format PLBRuS. You can change the default by providing an\n\
  option like -fCCCC where CCCC is your sequence of columns.\n\
  The coding of the sequence is like this:\n\
	y year\n\
	Y year.fraction_of_year\n\
	p planet index\n\
	P planet name\n\
	j relative juldate\n\
	J absolute juldate\n\
	T date formatted like 23.02.1992 \n\
	t date formatted like 920223 for 1992 february 23\n\
	L longitude in degree ddd:mm:ss\n\
	l longitude decimal\n\
	S speed in longitude in degree ddd:mm:ss per day\n\
	s speed longitude decimal (degrees/day)\n\
	B latitude degree\n\
	b latitude decimal\n\
	R distance decimal in AU\n\
	r distance decimal in AU, Moon in seconds parallax\n\
	u relative distance (1000=nearest, 0=furthest)\n\
	A Rectascension in hh:mm:ss\n\
	a rectascension hours decimal\n\
	D Declination degree\n\
	d declination decimal\n\
\n\
  Date entry:\n\
  In the interactive mode, when you are asked for a start date,\n\
  you can enter data in one of the following formats:\n\
\n\
	1.2.1991	three integers separated by a nondigit character for\n\
			day month year. Dates are interpreted as Gregorian\n\
			after 31.12.1599 and as Julian Calender before.\n\
			Time is always set to midnight.\n\
			If the three letters jul are appended to the date,\n\
			the Julian calendar is used even after 1600.\n\
			If the four letters greg are appended to the date,\n\
			the Gregorian calendar is used even before 1600.\n\
\n\
	j2400123.67	the letter j followed by a real number, for\n\
			the absolute Julian daynumber of the start date.\n\
			Fraction .5 indicates midnight, fraction .0\n\
			indicates noon, other times of the day can be\n\
			chosen accordingly.\n\
\n\
	<RETURN>	repeat the last entry\n\
	\n\
	.		stop the program\n\
\n\
	+20		advance the date by 20 days\n\
\n\
	-10		go back in time 10 days\n";

/********************************************************************/

# include "ourdef.h"
# include "placalc.h"
# include "astrolib.h"
# include <string.h>

char *pname[] = {"Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn",
		 "Uranus","Neptune","Pluto","mNode","tNode","Chiron", "Lilith",
		 "Ceres", "Pallas", "Juno", "Vesta"};

/**************** forward declarations */
void ecl_to_equ();

main(argc, argv)
int argc;
char *argv[];
{
  int planet, p2, i, n, jmon,jday,jyear;
  int fixstars=0;
  double jd, jd1, t, julday(), delta; 
  double al, ar, ab, alp, ra, decl, sinp, al2,ar2, ab2,alp2;
  double jd2, y_frac;
  double ecldeg, nutdeg;
  char *degstr(), *timestr();
  AS_BOOL servtest = FALSE;
  AS_BOOL ephkorr = FALSE;
  AS_BOOL diff_mode = FALSE;
  AS_BOOL test_nacalc = FALSE;
  AS_BOOL with_header = TRUE;
  AS_BOOL gregflag;
  int ndays = 0;
  int flag = 0;
  char *gap = " ";
  char *pseq = "0123456789nNciCPJV";
  char *fmt = "PLBRuS";
  char *begindate = NULL;
  double step = 1.0;
  double jut = 0.0;
  CSEC ppos[MAXPL_NACALC], pspeed[MAXPL_NACALC];
  char s[AS_MAXCH], saves[AS_MAXCH], so[800], *sp, *psp;
  for (i = 1; i < argc; i++) {
    if (strncmp(argv[i], "-edir", 5) == 0) {
      placalc_set_ephepath(argv[i] + 5);
    } else if (strcmp(argv[i], "-nac") == 0) {
      test_nacalc = TRUE;
    } else if (strncmp(argv[i], "-e", 2) == 0) {
      ephkorr = TRUE;
    }  else if (strncmp(argv[i], "-p", 2) == 0) {
      pseq = argv[i]+2;
      if (strlen (pseq) == 1 && ndays == 0) ndays = 20;
    } else if (strncmp(argv[i], "-n", 2) == 0) {
      ndays = atoi(argv[i]+2);
    } else if (strncmp(argv[i], "-s", 2) == 0) {
      step = atof(argv[i]+2);
    } else if (strcmp(argv[i], "-d") == 0) {
      diff_mode = TRUE;
    } else if (strcmp(argv[i], "-hel") == 0) {
      flag |= CALC_BIT_HELIO;
    } else if (strcmp(argv[i], "-h") == 0) {
      with_header = FALSE;
    } else if (strncmp(argv[i], "-c", 2) == 0) {
      servtest = TRUE;
    } else if (strncmp(argv[i], "-b", 2) == 0) {
      begindate = argv[i] + 2;
    } else if (strncmp(argv[i], "-g", 2) == 0) {
      gap = argv[i] + 2;
      if (*gap == '\0') gap = "\t";
    } else if (strncmp(argv[i], "-f", 2) == 0) {
      fmt = argv[i] + 2;
    } else if (strncmp(argv[i], "-i", 2) == 0) {
      fixstars = atoi(argv[i]+2);
      if (fixstars == 0) fixstars = 63;
    } else if (strcmp(argv[i], "-?") == 0) {
      puts (info);
      exit (0);
    } else {
      fprintf (stderr, "illegal parameter %s. Use option -? to display help information.\n", argv[i]);
      exit (1);
    }
  }
  /*
   * if environment variable EPHE is set, it points where the
   * ephemeris files are.
   */
  sp = getenv("EPHE");
  if (sp != NULL)
    placalc_set_ephepath(sp);
  if (ndays == 0) ndays = 1;
  while (TRUE) {
    if (begindate == NULL) {
      printf ("datum ?");
      sp = gets(s);
    } else {
      sp = begindate;
      begindate = ".";	/* to exit afterwards */
    }
    if (*sp == '.') {
      exit(1);
    } else if (*sp == '\0') {
      strcpy (s, saves);
    } else {
      strcpy (saves, s);
    }
    if (*sp == 'j') {	/* it's a day number */
      sscanf(sp+1,"%lf", &jd);
      if (jd < 1721026)
	gregflag = FALSE;
      else
	gregflag = TRUE;
      if (strstr(sp, "jul") != NULL)
	gregflag = FALSE;
      else if (strstr(sp, "greg") != NULL)
	gregflag = TRUE;
      (void) revjul(jd, gregflag, &jmon, &jday, &jyear, &jut);
    } else if (*sp == '+') {
      n = atoi(sp);
      if (n == 0) n = 1;
      jd += n;
      (void) revjul(jd, gregflag, &jmon, &jday, &jyear, &jut);
    } else if (*sp == '-') {
      n = atoi(sp);
      if (n == 0) n = -1;
      jd += n;
      (void) revjul(jd, gregflag, &jmon, &jday, &jyear, &jut);
    } else {
      if (sscanf (sp, "%d%*c%d%*c%d", &jday,&jmon,&jyear) < 1) exit(1);
      if (jyear < 1600)
	gregflag = FALSE;
      else
	gregflag = TRUE;
      if (strstr(sp, "jul") != NULL)
	gregflag = FALSE;
      else if (strstr(sp, "greg") != NULL)
	gregflag = TRUE;
      jd = julday(jmon,jday,jyear,jut,gregflag);	/* PLACALC: 
							   get jd for the
							   date you want */
    }
    if (with_header) {
      for (i = 0; i < argc; i++) printf("%s ", argv[i]);
      printf ("\nJulian day# %13.4f  date (dmy) %d.%d.%d", jd, jday, jmon, jyear);
    }
    if (servtest) {
      calcserv (0, jd - JUL_OFFSET, CALC_BIT_SPEED|CALC_BIT_BETA|CALC_BIT_RGEO, 0,so);
      printf ("\n%s\n", so);
      continue;
    }
    if (ephkorr) {
      delta = deltat (jd - JUL_OFFSET) * 24 * 3600;  

      if (with_header) 
	printf (" %s UT dt=%.2f ", timestr(jut), delta);
    } else {
      if (with_header) 
	printf (" %s ET ", timestr(jut) );
    }
    if (with_header) {
      if (step != 1) printf (" step s=%f", step);
      if (gregflag)
	printf (" greg. calendar");
      else
	printf (" julian calendar");
      printf ("\n output format f=%s, planet sequence p=%s\n", fmt, pseq);
    }
    for (n = 0; n < ndays; n++) {
      jd1 = jd + n * step;
      (void) revjul(jd1, gregflag, &jmon, &jday, &jyear, &jut);

      t =  (jd1 - JUL_OFFSET);		/* PLACALC: julday() returns
					absolute jd, but calc() needs relative
					jd, therefore subtract JUL_OFFSET */


      if (ephkorr) t += deltat (t);	/* PLACALC: add deltat if your time is
					 in Universal Time, not in ET. */

      calc(CALC_ONLY_ECL_NUT, t, 0, &nutdeg, &ar, &ecldeg, &alp);
	   /* PLACALC: if you need only ecliptic and nutation, use this */
      if (n == 0 && with_header) {
	printf ("ecliptic %s ", degstr(ecldeg));
	printf ("nutation %s\n", degstr(nutdeg));
      }
      if (test_nacalc) {
	/*
	 * nacalc() is a function which computes a complete set of
	 * natal positions for a horoscope.
	 * it fills an array centisec ppos[0..13] and pspeed[0..13]
	 * with ecliptic longitudes and motion/day.
	 */
	nacalc(t, ppos, pspeed);
	for (psp = pseq; *psp != '\0'; psp++) {
	  if ((planet = apl2planet(*psp)) == -1) continue;
	  printf("%d %10ld\t%10ld\n", planet, ppos[planet], pspeed[planet]);
	}
	continue;
      }
      /*
       * The string pseq contains a character for each planet we want
       * computed; we go through this string and convert each character
       * into a planet number with apl2planet().
       * If we get -1 as a result, it was an undefined character; 
       * otherwise we call calc() tp compute the geocentric
       * longitude, radius, latitude and speed in longitude.
       * Be aware that he speed is not precise for SUN, MERCURY, VENUS, MARS.
       * To get precise speeds, calc() must be called with different times
       * and the speed must be determined from the differences.
       */
      for (psp = pseq; *psp != '\0'; psp++) {
	if ((planet = apl2planet(*psp)) == -1) continue;

	if (calc( planet, t, flag, &al, &ar, &ab, &alp) == ERR) {
					/* PLACALC: now compute geocentric
					position of planet at time t */

	  printf ("planet %d: error: %s\n", planet, placalc_get_errtext());
	} else {
	  if (diff_mode) {
	    /*
	     * in diffmode, we want the difference of two planet
	     * coordinates. We find the next planet index
	     * and call calc() again. Then we compute the difference.
	     */
	    if ((p2 = apl2planet(*(++psp))) == -1) continue;
	    if (calc( p2, t, 0, &al2, &ar2, &ab2, &alp2) == ERR) {
	      printf ("planet %d: error: %s\n", p2, placalc_get_errtext());
	      continue;
	    }
	    al = diff8360(al,al2);
	    ab -= ab2;
	    alp -= alp2;
	  }
	  /*
	   * convert ecliptic coordnates al, ab to equatorial coordinates
	   * ra = rectascensio, decl = declination. The radius is the same
	   * in both coordinate systems.
	   */
	  ecl_to_equ(al, ab, ecldeg, &ra, &decl);
	  ra = ra / 15;
	  /*
	   * The string fmt contains a sequence of format specifiers;
	   * each character in fmt creates a column, the columns are
	   * sparated by the gap string.
	   */
	  for (sp = fmt; *sp != '\0'; sp++) {
	    if (sp != fmt) 
	      printf("%s", gap);
	    switch(*sp) {
	    case 'y':
		printf ("%d", jyear);
		break;
	    case 'Y':
		jd2 = julday(1,1,jyear,0.0,gregflag);
		y_frac = (jd1 - jd2) / 365.0;
		printf ("%.2lf", jyear + y_frac);
		break;
	    case 'p':
		if (diff_mode)
		  printf ("%d-%d", planet, p2);
		else
		  printf ("%d", planet);
		break;
	    case 'P':
		if (diff_mode)
		  printf ("%.3s-%.3s", pname[planet], pname[p2]);
		else
		  printf ("%-7s", pname[planet]);
		break;
	    case 'J':
		printf ("%.2f",jd1);
		break;
	    case 'j':
		printf ("%.2f", t);
		break;
	    case 'T':
		printf ("%02d.%02d.%d", jday, jmon, jyear);
		break;
	    case 't':
		printf ("%02d%02d%02d", jyear % 100, jmon, jday);
		break;
	    case 'L':
		printf ("%13s", degstr(al));
		break;
	    case 'l':
		printf ("%11.7f", al);
		break;
	    case 'S':
		printf ("%13s", degstr(alp));
		break;
	    case 's':
		printf ("%11.7f", alp);
		break;
	    case 'B':
		printf ("%13s", degstr(ab));
		break;
	    case 'b':
		printf ("%11.7f", ab);
		break;
	    case 'A':	/* rectascensio */
		printf ("%15s", degstr(ra));
		break;
	    case 'a':	/* rectascensio */
		printf ("%11.7f", ra);
		break;
	    case 'D':	/* declination */
		printf ("%15s", degstr(decl));
		break;
	    case 'd':	/* declination */
		printf ("%11.7f", decl);
		break;
	    case 'R':
		if ( planet == MOON ) { /* for moon 9 digits */
		  printf ("%10.9f", ar);
		} else {
		  printf ("%10.7f", ar);
		}
		break;
	    case 'r':
		if ( planet == MOON ) { /* for moon print parallax */
		  sinp = 8.794 / ar;	/* in seconds of arc */
		  ar = sinp * (1 + sinp * sinp * 3.917402e-12);
                  /* the factor is 1 / (3600^2 * (180/pi)^2 * 6) */
		  printf ("%9.3f\"", ar);	
		} else {
		  printf ("%10.7f", ar);
		}
		break;
	    case 'u':
		printf ("%3d", rel_geo(planet,ar));
		break;
	    }	/* switch */
	  }	/* for sp */
	  putchar ('\n');
	}
      }
# if ! MSDOS
      if (fixstars) {
	for (i = 0; i < 16; i++) 
	  if (check_bit(fixstars, i)) {
	    sprintf(s, "%d", i);
	    if (fixstar(s, jd1, &al, &ab) == OK) {
	      strcpy(so, degstr(al));
	      printf("%-15s\t%s\t%s\n", upcase_first_letters(s), so, degstr(ab));
	    } else
	      printf("error %d %15s\n", i, s);
	  }
      }
# endif
    }
  }
}	/* end main */

/*************************************/
char *degstr (t)
double t;
{
  static char a[20];	/* must survive call */
  long d2l(double);
  centisec cs = d2l(t * DEG);
  int ideg, imin,sec;
  char sign = ' ';
  if ( cs < 0) sign = '-';
  cs =  ABS4 (cs);
  ideg = cs / DEG;
  cs = cs % DEG;
  imin =  cs / 6000L;
  cs = cs % 6000L;
  sec = cs / 100;
  cs = cs % 100;
  sprintf (a, "%c%3d %2d'%2d.%02d\"", sign, ideg, imin, sec,cs);
  return (a);
} /* degstr */


/*************************************/
char *timestr (t)
double t;
{
  static char a[20];	/* must survive call */
  double min;
  int ideg, imin;
  t =  fabs (t);
  ideg = (int) floor (t);
  min = ( t - ideg ) * 60.0;
  imin = (int) floor(min);
  sprintf (a, "%2d:%02d", ideg, imin);
  return (a);
} 


/********* convert ecliptic to equatorial coords *****/
void ecl_to_equ(al, ab, obl, ra, decl)
double al, ab, obl, *ra, *decl;	/* all in degrees */
{
  double sine, cose, cosb, cosa;
  cosb = COS8 (DEGTORAD * ab);
  sine = SIN8 (DEGTORAD * obl);
  cose = COS8 (DEGTORAD * obl);
  *decl = ASIN8 (SIN8 (al * DEGTORAD ) * cosb * sine 
		+SIN8 (ab * DEGTORAD ) * cose);	/* radians */
  cosa = cosb * COS8 (DEGTORAD * al) / COS8 ( *decl);
  *ra  = RADTODEG * acos (cosa);
  if (al >= 180.0) *ra = 360.0 - *ra;
  *decl *= RADTODEG;
}
