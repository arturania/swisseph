/*
 *%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 *%                                                                       
 *%     Copyright (C) Astrodienst AG
 *%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 *
 * SWEAST.C
 *
 * SWISSEPH test windows program,
 *
 */

#define BEGTHREAD TRUE

#include <windows.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <io.h>
#include <fcntl.h>
#include <dos.h>
#include <sys\types.h> 
#include <sys\stat.h>
#include <direct.h>
#include <time.h>
#include <process.h>
#include <commctrl.h>

/* windows.h is not identical in watcom and microsoft env */
#ifndef GWW_HINSTANCE           
#define GWW_HINSTANCE GWL_HINSTANCE
#endif

#include "sweodef.h"
#include "sweastr.h"
#include "swewin.h"
#include "swephexp.h"

#define SWEAST_EPHE_PATH    "\\sweph\\ephe\\"

#define BUFLEN  800000L

static char *etut[]={"UT", "ET"};
static char *lat_n_s[]={"N", "S"};
static char *lon_e_w[]={"E", "W"};
#define NEPHE           3
static char *ephe[]={"Swiss Ephemeris", "JPL Eph. DE406", "Moshier Ephemeris"};
#define NSORTTYP        7
static char *sorttyp[] = {"sort by position", "sort by name", "sort by cat. numb.", "sort by distance", "sort by latitude", "sort by declination", "sort by height",};
#define STP_POS		0
#define STP_NAM		1
#define STP_NUM		2
#define STP_DIST	3
#define STP_LAT		4
#define STP_DECL	5
#define STP_HGT		6

#define NCENTERS	6
static char *ctr[] = {"geocentric", "topocentric", "heliocentric", "barycentric", "house positions", "house p. topo"};
#define CTR_GEO		0
#define CTR_TOPO	1
#define CTR_HELIO	2
#define CTR_BARY	3
#define CTR_HOUSE	4
#define CTR_HOUSE_TOPO	5

#define NAPTR		2
static char *aptr[] = {"apparent pos.", "true positions",};
#define APTR_APP	0
#define APTR_TRUE	1

#define NHSYS        7
static char *hsysname[] = {"Placidus", "Campanus", "Regiomontanus", "Koch", "Equal", "Vehlow equal", "Horizon"};

#define NHOROTYP	8
static char *horotyp[] = {"natal chart", 
                          "composite", "combin/Davison",
                          "transit/synastry", 
                          "progressions", "progr./prim.asc.", 
                          "prim./Naibod", "prim.dir. II",};
#define NHT_NAT		0
#define NHT_COMPOSIT	1
#define NHT_COMBIN	2
#define NHT_SYN		3
#define NHT_PROGR	4
#define NHT_PROGR_2	5
#define NHT_PRIM_N	6
#define NHT_PRIM	7

static char classname[32]="SwewinClass";

static char *helptext ="\n\
Ceres 1.0 - The Asteroid Scanner, 29 May 1999.\n\
--------------------------------------------\n\
by Dieter Koch.\n\
For questions or feedback mail to dieter@astro.ch.\n\
\n\
This program does almost everything related to asteroids:\n\
- natal charts, synastries/transits, composite charts, progressions etc.\n\
- geocentric, heliocentric, topocentric, house horoscopes\n\
- lists sorted by position in zodiac, by asteroid name, by declination etc.\n\
The output is written\n\
- to the screen, unless the data are too comprehensive for the screen, AND\n\
- to a file, whose name can be specified in one of the bottom fields.\n\
\n\
There is a field, in which the ephemeris path can be specified.\n\
You need not copy the ephemerides from your CD drive to the harddisk.\n\
But I suggest to copy the program itself to the harddisk.\n\
Whenever the program is closed it tries to save all input data,\n\
which is desirable, because you probably do not want every time you\n\
start the program to replace the default coordinates (Zurich) by the\n\
coordinates of your own city. This save file cannot be written, if the\n\
program is started on CD-ROM.\n\
The second date is never saved. The program always loads the date and time\n\
of the start of the program.\n\
\n\
Some lists contain aspect points of the major planets, but not the\n\
ones of the asteroids. Otherwise the output length would have been\n\
multiplied. In order to find aspects between asteroids, use the\n\
HARMONICS field. E.g., with harm = 4 you will find planets closely\n\
together if they are conjunct, opposite or in square.\n\
\n\
SYNASTRIES, TRANSITS, PROGRESSIONS:\n\
The two charts are written to the same list, the transit (or progressive)\n\
planets being marked by 'TR', 'PRO', etc.\n\
In order to get CONVERSE transits, progressions or directions, you have to\n\
activate the check box 'converse'.\n\
If you want the second or progressive chart only, click on check box\n\
'without natal'.\n\
You can exclude the houses of the second date from the list by clicking\n\
on the check box 'houses of date 1'.\n\
\n";

char *helptext2 = "\
PRIMARY DIRECTIONS:\n\
The program does not compute lists of trigger times, but combines the\n\
house horoscope of the natal chart with the list of progressed house\n\
positions. Therefore, you can only see which directions are precise\n\
at the moment.\n\
There are two items for primary directions; one of them is named\n\
PRIM. DIR. II. This is the method that convinces me best. Rüdiger Plantiko\n\
notes in his book on primary directions that they can be interpreted as\n\
the progressions of the progressions: 1 year = 1 day = 1/365... day.\n\
The resulting dates are not far from the Naibod method. The progressed\n\
planets will, of course, slowly change their ecliptic position with\n\
time.\n\
There is a check box that allows you to disregard the planetary latitudes.\n\
\n\
Besides GEOCENTRIC and HELIOCENTRIC charts, you can compute:\n\
- TOPOCENTRIC charts: Ordinary ephemerides give planetary refered to the\n\
  to the center of the earth. But from a geographical location, the\n\
  positions are slightly different. This can make a difference of 1 degree\n\
  for the moon, and a couple of arc seconds for the planets.\n\
- The HOUSE CHART gives house positions, where every house has exactly\n\
  30 degrees. This computation is more rigorous than traditional house\n\
  horoscopes, because it takes into account the geometrical definitions\n\
  of the house systems and the planetary latitudes. The planetary latitudes\n\
  can be disregarded by clicking on check box 'ecl. lat. = 0'.\n\
  If you compute house horoscopes for synastries or transits, you can\n\
  activate the check box 'houses of date 1'. Then, the planetary positions\n\
  of the second or progressive chart are referred to the axes of the\n\
  first date. This allows you, e.g., to see the precise transit of\n\
  a planet over your natal horizon or MC. Because this depends not only\n\
  on the ecliptic longitude of the transit planet, but also on its latitude\n\
  you cannot see this from ordinary transit calculation.\n\
  House charts can be computed for topocentric planet positions as well.\n";

char *helptext3 = "\
- BARYCENTRIC planet positions are probably of academical interest only.\n\
  They are referred to the center of gravity of the solar system - which\n\
  is not precisely identical to center of the solar.\n\
\n\
There is a selection box for APPARENT POSITIONS and TRUE POSITIONS.\n\
Apparent planetary positions take account of the time it takes the\n\
the light to arrive on earth. Apparent positions are the positions,\n\
where we see the planets, not where they really are.\n\
Conventional ephemerides use apparent positions.\n\
However, since astrology is probably a synchronistic phenomenon and\n\
has nothing to do with traditional light-time-bound physical interaction\n\
between bodies, it might be more correct to work with true positions,\n\
i.e. the positions, at which the planets actually are although we see\n\
them at the places where they were minutes or hours ago.\n\
";


#define NPD		2
static struct pd {
    char etut[3];
    char lon_e_w[2];
    char lat_n_s[2];
    //char ephe[30];
	char ephe_path[AS_MAXCH];
    //char sorttyp[30];
    //char ctr[20];
    char hsysname[30];
    char fname_out[AS_MAXCH];
    unsigned int mday, mon, hour, min, sec;
    int year;
    unsigned int  lon_deg, lon_min, lon_sec;
    unsigned int  lat_deg, lat_min, lat_sec;
    unsigned long  alt;
	unsigned long  range1, range2;
	AS_BOOL print_with_lbr;
	AS_BOOL neglect_lat;
	AS_BOOL hs_date1;
	AS_BOOL without_natal;
	AS_BOOL converse;
	//char horotyp[30];
	int ihtyp, ictr, isorttyp, iephe;
	int iaptr, ihsy;
	long harm;
	//char aptr[30];
    } pd[2], old_pd[2];

static char buf[BUFLEN];
static HWND hdlgsweph;

/* forward declarations */
static void init_data(void);
static char *progname = "Swisseph Asteroids Program";

BOOL FAR PASCAL about_proc( HWND, unsigned, UINT, LONG );
BOOL FAR PASCAL inp_setup_proc( HWND, unsigned, UINT, LONG );
long FAR PASCAL WindowProc( HWND, unsigned, UINT, LONG );
static int swisseph(void);
static int calc_planets(struct calc *clp, char *buf);
static double calc_prog_date(double tjdu1, double tjdu2, AS_BOOL converse, double period, struct calc *clp);
static double calc_prog_armc(AS_BOOL converse, double period, double key, struct calc * clp);
static char *get_date_string(double tjd_ut, int gregflag, char *sp);
static char *dms(double x, long iflag);
static long do_calc(double tjd, int ipl, long iflag, double *x, char *serr);
static long do_fixstar(char *star, double tjd, long iflag, double *x, char *serr);
static void do_print(char *target, char *info);
static int letter_to_ipl(int letter);
static int make_ephemeris_path(long iflag, char *argv0);
static int search_file(char *path, long iflag);
static int atoulng(char *s, unsigned long *ulng);
static int atoslng(char *s, long *slng);
static int cut_str_any(char *s, char *cutlist, char *cpos[], int nmax);
static int ipl_compare(const struct outp *a1, const struct outp *a2);
static int name_compare(const struct outp *a1, const struct outp *a2);
static int pos_compare(const struct outp *a1, const struct outp *a2);

#define TROP_YEAR 365.2421897
#define KEY_NAIBOD (360 / TROP_YEAR)
#define J2000           2451545.0  /* 2000 January 1.5 */
#define square_sum(x)   (x[0]*x[0]+x[1]*x[1]+x[2]*x[2])
#define SEFLG_EPHMASK   (SEFLG_JPLEPH|SEFLG_SWIEPH|SEFLG_MOSEPH)

#define BIT_ROUND_SEC   1
#define BIT_ROUND_MIN   2
#define BIT_ZODIAC      4
#define PLSEL_D  "0123456789mtAC+"
#define PLSEL_P  "0123456789mtABCDEFGHI"
#define PLSEL_H  "JKLMNOPQRSTUVWX"
#define PLSEL_A  "0123456789mtABCDEFGHIJKLMNOPQRSTUVWX"

extern char FAR *pgmptr;
static char *zod_nam[] = {"ar", "ta", "ge", "cn", "le", "vi", 
                          "li", "sc", "sa", "cp", "aq", "pi"};
static    HWND        hwnd;
static AS_BOOL is_doit;

HANDLE ghThread;
static AS_BOOL abort_loop;
extern unsigned long _beginthread(); /* to prevent warning, process.h does not work */
extern void _endthread();

HANDLE progressbar;
static int astcount, astcount_max;

int PASCAL WinMain( HANDLE this_inst, HANDLE prev_inst, LPSTR cmdline,
                    int cmdshow )
/*******************************/
{
    MSG         msg;
    HWND        hwnd;
    HANDLE              inst_handle;
    WNDCLASS    wndclass;
	HMENU hmenu;
    char *argv0 = "";
    FARPROC             proc;
    cmdline = cmdline;          /* shut up compiler warning */
    prev_inst = prev_inst;

#ifdef __WINDOWS_386__
    sprintf( classname,"SwewinClass%d", this_inst );
#else
    if( !prev_inst ) {
#endif
      wndclass.style          = CS_HREDRAW | CS_VREDRAW;
      wndclass.lpfnWndProc    = (LPVOID) WindowProc;
      wndclass.cbClsExtra     = 0;
      wndclass.cbWndExtra     = 6 * sizeof( DWORD );
      wndclass.hInstance      = this_inst;
      wndclass.hIcon          = LoadIcon(this_inst, IDI_APPLICATION);
      wndclass.hCursor        = LoadCursor( NULL, IDC_ARROW );
      wndclass.hbrBackground  = (HBRUSH) COLOR_BACKGROUND;
      wndclass.lpszMenuName   = NULL; //"sweph_menu";
      wndclass.lpszClassName  = classname;
      RegisterClass( &wndclass );
#ifndef __WINDOWS_386__
    }
#endif
    init_data();
#if 1
    hwnd = CreateWindow( classname, progname,
                         //WS_OVERLAPPEDWINDOW, 
						 WS_POPUP | WS_CAPTION,
						 CW_USEDEFAULT, CW_USEDEFAULT,
                         0, 0,
                         NULL, NULL, this_inst, "DATABX" );
#else
    hwnd = CreateWindowEx(0L,
      classname, "", 
	  //WS_POPUP | WS_CAPTION,
      WS_OVERLAPPED | WS_CAPTION | WS_BORDER | WS_THICKFRAME | WS_MINIMIZEBOX 
      | WS_CLIPCHILDREN | WS_SYSMENU | WS_EX_CONTROLPARENT,
      80, 70, 300, 200,
      NULL, NULL, this_inst, NULL);
    if (hwnd == NULL)
      return FALSE;
#endif
    ShowWindow( hwnd, cmdshow );
    UpdateWindow( hwnd );
#if 1
    *buf = '\0';
    inst_handle = GET_HINST( hwnd);
    proc = MakeProcInstance( inp_setup_proc, inst_handle );
    DialogBox( inst_handle, "DATABX", hwnd, proc );
    FreeProcInstance( proc );
    SendMessage( hwnd, WM_CLOSE, 0, 0L );
    PostQuitMessage( 0 );
#endif
    while( GetMessage( &msg, NULL, (UINT) NULL, (UINT) NULL ) ) {
      TranslateMessage( &msg );
      DispatchMessage( &msg );
    }
    return( msg.wParam );
} /* WinMain */

/*
 * WindowProc - handle messages for the main application window
 */
LONG FAR PASCAL WindowProc( HWND window_handle, unsigned msg,
                                     UINT wparam, LONG lparam )
/*************************************************************/
{
    FARPROC             proc;
    HANDLE              inst_handle;
	HWND				hdial;
    WORD                cmd;
	HMENU				hmenu;
    /*
     * now process the message
     */
    switch( msg ) {
    case WM_CREATE:
      inst_handle = GET_HINST( window_handle );
#if 0
      hdial = CreateDialog(inst_handle,"DATABX", window_handle, (DLGPROC)inp_setup_proc);
//    hmenu = LoadMenu(inst_handle, "SWEPH_MENU");
//    SetMenu(window_handle, hmenu);

#endif
      break;
    case WM_CLOSE:
#if 0
      PostQuitMessage( 0 );/**/
#endif
      return( DefWindowProc( window_handle, msg, wparam, lparam ) );
      break;
    case WM_COMMAND:
      cmd = LOWORD( wparam );
      switch( cmd ) {
      case MENU_ABOUT:
        inst_handle = GET_HINST( window_handle );
        proc = MakeProcInstance( about_proc, inst_handle );
        DialogBox( inst_handle,"AboutBx", window_handle, proc );
        FreeProcInstance( proc );
        break;
      case MENU_EXIT:
        SendMessage( window_handle, WM_CLOSE, 0, 0L );
        break;
#if 0
      case MENU_CALC:
        *buf = '\0';
        inst_handle = GET_HINST( window_handle );
        proc = MakeProcInstance( inp_setup_proc, inst_handle );
        DialogBox( inst_handle, "DATABX", window_handle, proc );
        FreeProcInstance( proc );
        SendMessage( window_handle, WM_CLOSE, 0, 0L );
        break;
#endif
      }
      return 0;
    case WM_DESTROY:
#if 0
      DestroyWindow(hdial);
#endif
      PostQuitMessage( 0 );
      break;
    default:
      return( DefWindowProc( window_handle, msg, wparam, lparam ) );
    }
    return( 0L );

} /* WindowProc */

/*
 * about_proc -  processes messages for the about dialogue.
 */
BOOL FAR PASCAL about_proc( HWND window_handle, unsigned msg,
                                UINT wparam, LONG lparam )
/********************************************************/
{
    lparam = lparam;                    /* turn off warning */
    window_handle = window_handle;
    switch( msg ) {
    case WM_INITDIALOG:     
      return( TRUE );
    case WM_CLOSE :
      EndDialog( window_handle, TRUE );
      return( TRUE );
    case WM_COMMAND:
      if( LOWORD( wparam ) == IDOK ) {
        EndDialog( window_handle, TRUE );
        return( TRUE );
      }
      break;
    }
    return( FALSE );
} /* About */

/*
 * Input
 */
extern BOOL FAR PASCAL inp_setup_proc( HWND hdlg, UINT message,
                                                 UINT wparam, LONG lparam )
/*************************************************************************/
{
    WORD        cmd;
    int         i, j;
    long        slng;
    unsigned long ulng;
    FILE 	*fp;
    FARPROC     proc;
    HANDLE      inst_handle;
    char s[AS_MAXCH], s1[AS_MAXCH], *sp;
    DWORD dCode;
    if (ghThread > 0) { // check if there's a launched thread
      if (0 != GetExitCodeThread(ghThread, &dCode)) {
        if (dCode != STILL_ACTIVE)
          ghThread = 0;
      } else ghThread = 0;
    }
    lparam = lparam;    /* shut compiler up*/
    switch( message ) {
    case WM_INITDIALOG :
      ghThread = 0;
      for (i = 0; i < NPD; i++)
        old_pd[i] = pd[i];
      for (i = j = 0; i < 2; i++) {
        if (strcmp(lat_n_s[i], pd[0].lat_n_s) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_N_S, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) lat_n_s[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_N_S, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < 2; i++) {
        if (strcmp(lon_e_w[i], pd[0].lon_e_w) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_E_W, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) lon_e_w[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_E_W, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < 2; i++) {
        if (strcmp(etut[i], pd[0].etut) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_ET_UT, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) etut[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_ET_UT, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < 2; i++) {
        if (strcmp(lat_n_s[i], pd[1].lat_n_s) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_N_S2, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) lat_n_s[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_N_S2, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < 2; i++) {
        if (strcmp(lon_e_w[i], pd[1].lon_e_w) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_E_W2, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) lon_e_w[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_E_W2, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < 2; i++) {
        if (strcmp(etut[i], pd[1].etut) == 0) j = i;
        SendDlgItemMessage( hdlg, COMBO_ET_UT2, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) etut[i]);
      }
      SendDlgItemMessage( hdlg, COMBO_ET_UT2, CB_SETCURSEL, j, (long) NULL);
      for (i = j = 0; i < NEPHE; i++)
        SendDlgItemMessage( hdlg, COMBO_EPHE, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) ephe[i]);
      SendDlgItemMessage( hdlg, COMBO_EPHE, CB_SETCURSEL, pd[0].iephe, (long) NULL);
      for (i = j = 0; i < NSORTTYP; i++)
        SendDlgItemMessage( hdlg, COMBO_PLANSEL, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) sorttyp[i]);
      SendDlgItemMessage( hdlg, COMBO_PLANSEL, CB_SETCURSEL, pd[0].isorttyp, (long) NULL);
      for (i = j = 0; i < NCENTERS; i++)
        SendDlgItemMessage( hdlg, COMBO_CENTER, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) ctr[i]);
      SendDlgItemMessage( hdlg, COMBO_CENTER, CB_SETCURSEL, pd[0].ictr, (long) NULL);
      for (i = j = 0; i < NAPTR; i++)
        SendDlgItemMessage( hdlg, COMBO_APTR, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) aptr[i]);
      SendDlgItemMessage( hdlg, COMBO_APTR, CB_SETCURSEL, pd[0].iaptr, (long) NULL);
      for (i = j = 0; i < NHSYS; i++)
        SendDlgItemMessage( hdlg, COMBO_HSYS, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) hsysname[i]);
      SendDlgItemMessage( hdlg, COMBO_HSYS, CB_SETCURSEL, pd[0].ihsy, (long) NULL);
      for (i = j = 0; i < NHOROTYP; i++)
        SendDlgItemMessage( hdlg, COMBO_HOROTYP, CB_ADDSTRING, (UINT) NULL, (DWORD)(LPSTR) horotyp[i]);
      SendDlgItemMessage( hdlg, COMBO_HOROTYP, CB_SETCURSEL, pd[0].ihtyp, (long) NULL);
      /* set date */
      SetDlgItemText( hdlg, EDIT_DAY, itoa(pd[0].mday, s, 10));
      SetDlgItemText( hdlg, EDIT_MONTH, itoa(pd[0].mon, s, 10));
      SetDlgItemText( hdlg, EDIT_YEAR, itoa(pd[0].year, s, 10));
      SetDlgItemText( hdlg, EDIT_HOUR, itoa(pd[0].hour, s, 10));
      SetDlgItemText( hdlg, EDIT_MINUTE, itoa(pd[0].min, s, 10));
      SetDlgItemText( hdlg, EDIT_SECOND, itoa(pd[0].sec, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_DEG, itoa(pd[0].lon_deg, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_MIN, itoa(pd[0].lon_min, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_SEC, itoa(pd[0].lon_sec, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_DEG, itoa(pd[0].lat_deg, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_MIN, itoa(pd[0].lat_min, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_SEC, itoa(pd[0].lat_sec, s, 10));
      SetDlgItemText( hdlg, EDIT_ALT, ltoa(pd[0].alt, s, 10));
      SetDlgItemText( hdlg, EDIT_DAY2, itoa(pd[1].mday, s, 10));
      SetDlgItemText( hdlg, EDIT_MONTH2, itoa(pd[1].mon, s, 10));
      SetDlgItemText( hdlg, EDIT_YEAR2, itoa(pd[1].year, s, 10));
      SetDlgItemText( hdlg, EDIT_HOUR2, itoa(pd[1].hour, s, 10));
      SetDlgItemText( hdlg, EDIT_MINUTE2, itoa(pd[1].min, s, 10));
      SetDlgItemText( hdlg, EDIT_SECOND2, itoa(pd[1].sec, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_DEG2, itoa(pd[1].lon_deg, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_MIN2, itoa(pd[1].lon_min, s, 10));
      SetDlgItemText( hdlg, EDIT_LONG_SEC2, itoa(pd[1].lon_sec, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_DEG2, itoa(pd[1].lat_deg, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_MIN2, itoa(pd[1].lat_min, s, 10));
      SetDlgItemText( hdlg, EDIT_LAT_SEC2, itoa(pd[1].lat_sec, s, 10));
      SetDlgItemText( hdlg, EDIT_ALT2, ltoa(pd[1].alt, s, 10));
      SetDlgItemText( hdlg, EDIT_RANGE, ltoa(pd[0].range1, s, 10));
      SetDlgItemText( hdlg, EDIT_RANGE2, ltoa(pd[0].range2, s, 10));
      SetDlgItemText( hdlg, EDIT_HARM, ltoa(pd[0].harm, s, 10));
      CheckDlgButton(	hdlg, CH_LBR, pd[0].print_with_lbr);
      CheckDlgButton(	hdlg, CH_NEG_LAT, pd[0].neglect_lat);
      CheckDlgButton(	hdlg, CH_HS_DATE1, pd[0].hs_date1);
      CheckDlgButton(	hdlg, CH_WOUT_NATAL, pd[0].without_natal);
      CheckDlgButton(	hdlg, CH_CONV, pd[0].converse);
      i = abs(pd[0].range2 - pd[0].range1) + 1;
      astcount_max = i;
      if (i > 100) {
        sprintf(s, 
              "ATTENTION: computation of %d asteroids will take some time\r\n",
              i);
        SetDlgItemText( hdlg, EDIT_OUTPUT2, s);
      }
      SetDlgItemText( hdlg, EDIT_OUTFNAM, pd[0].fname_out);
      SetDlgItemText( hdlg, EDIT_EPHPATH, pd[0].ephe_path);
      return( TRUE );
    case WM_CLOSE :
      EndDialog( hdlg, TRUE );
      return( TRUE );
    case WM_COMMAND :
      cmd = LOWORD( wparam );
      switch( cmd ) {
      case MENU_ABOUT:
        inst_handle = GET_HINST( hdlg );
        proc = MakeProcInstance( about_proc, inst_handle );
        DialogBox( inst_handle,"AboutBx", hdlg, proc );
        FreeProcInstance( proc );
        break;
      case PB_EXIT :
        if (ghThread > 0)
          goto pb_abort_loop;
        if ((fp = fopen("ceres.stt", BFILE_W_CREATE)) != NULL) {
          fwrite((void *) &pd[0], 1, sizeof(struct pd) * 2, fp);
          fclose(fp);
        }
        for (i = 0; i < NPD; i++)
          pd[i] = old_pd[i];
        EndDialog( hdlg, 0 );
        return( TRUE );
pb_abort_loop:
        SetDlgItemText( hdlg, PB_EXIT, "Exit");
        abort_loop = TRUE;
        return( TRUE );
      case PB_HELP :
        *buf = '\0';
        do_print(buf, helptext);
        do_print(buf, helptext2);
        do_print(buf, helptext3);
        SetDlgItemText(hdlg, EDIT_OUTPUT2, buf);
        return(TRUE);
      case PB_DOIT :     
        hdlgsweph = hdlg;
        *buf = '\0';
        if (ghThread > 0)
          goto pb_abort_loop;
        abort_loop = FALSE;
        GetDlgItemText( hdlg, PB_DOIT, s, 10);
        if (strcmp(s, "Abort") != 0)
          SetDlgItemText( hdlg, PB_DOIT, "Abort");
        ghThread = (HANDLE) _beginthread(swisseph, 0, NULL);
        //i = strlen(buf);
        SetDlgItemText(hdlg, EDIT_OUTPUT2, buf);
        //i = GetDlgItemText(hdlg, EDIT_OUTPUT2, buf, BUFLEN);
        return( TRUE );
      case COMBO_ET_UT:
        i = (int) SendDlgItemMessage(hdlg, COMBO_ET_UT, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[0].etut, etut[i]);
        return TRUE;
      case COMBO_ET_UT2:
        i = (int) SendDlgItemMessage(hdlg, COMBO_ET_UT2, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[1].etut, etut[i]);
        return TRUE;
      case COMBO_N_S:
        i = (int) SendDlgItemMessage( hdlg, COMBO_N_S, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[0].lat_n_s, lat_n_s[i]);
        return TRUE;
      case COMBO_N_S2:
        i = (int) SendDlgItemMessage( hdlg, COMBO_N_S2, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[1].lat_n_s, lat_n_s[i]);
        return TRUE;
      case COMBO_E_W:
        i = (int) SendDlgItemMessage( hdlg, COMBO_E_W, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[0].lon_e_w, lon_e_w[i]);
        return TRUE;
      case COMBO_E_W2:
        i = (int) SendDlgItemMessage( hdlg, COMBO_E_W2, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        strcpy(pd[1].lon_e_w, lon_e_w[i]);
        return TRUE;
      case COMBO_EPHE:
        i = (int) SendDlgItemMessage( hdlg, COMBO_EPHE, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        pd[0].iephe = i;
        return TRUE;
      case COMBO_PLANSEL:
        i = (int) SendDlgItemMessage( hdlg, COMBO_PLANSEL, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        pd[0].isorttyp = i;
        return TRUE;
      case COMBO_CENTER:
        i = (int) SendDlgItemMessage( hdlg, COMBO_CENTER, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        pd[0].ictr = i;
        return TRUE;
      case COMBO_APTR:
        i = (int) SendDlgItemMessage( hdlg, COMBO_APTR, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        pd[0].iaptr = i;
        return TRUE;
      case COMBO_HSYS:
        i = (int) SendDlgItemMessage( hdlg, COMBO_HSYS, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        pd[0].ihsy = i;
        return TRUE;
      case COMBO_HOROTYP:
        i = (int) SendDlgItemMessage( hdlg, COMBO_HOROTYP, CB_GETCURSEL, (UINT) NULL, (long) NULL);
        if (i != pd[0].ihtyp) {
          if (i == NHT_PRIM || i == NHT_PRIM_N) {
            if (pd[0].ictr == CTR_TOPO || pd[0].ictr == CTR_HOUSE_TOPO )
              pd[0].ictr = CTR_HOUSE_TOPO;
            else
              pd[0].ictr = CTR_HOUSE;
          } else if (pd[0].ihtyp == NHT_PRIM || pd[0].ihtyp == NHT_PRIM_N) {
            if (pd[0].ictr == CTR_HOUSE_TOPO || pd[0].ictr == CTR_TOPO )
              pd[0].ictr = CTR_TOPO;
            else
              pd[0].ictr = CTR_GEO;
          }
          SendDlgItemMessage( hdlg, COMBO_CENTER, CB_SETCURSEL, pd[0].ictr, (long) NULL);
          pd[0].ihtyp = i;
        }
        return TRUE;
      case EDIT_DAY:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng > 0 && ulng <= 31)
            pd[0].mday = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_DAY2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng > 0 && ulng <= 31)
            pd[1].mday = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_MONTH:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng > 0 && ulng <= 12)
            pd[0].mon = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_MONTH2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng > 0 && ulng <= 12)
            pd[1].mon = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_YEAR:
        GetDlgItemText( hdlg, cmd, s, 6);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK )
            pd[0].year = (int) slng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[0].year = atoi(s);
        return TRUE;
      case EDIT_YEAR2:
        GetDlgItemText( hdlg, cmd, s, 6);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK )
            pd[1].year = (int) slng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[1].year = atoi(s);
        return TRUE;
      case EDIT_HOUR:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 24)
            pd[0].hour = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_HOUR2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 24)
            pd[1].hour = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_MINUTE:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_MINUTE2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_SECOND:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_SECOND2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_DEG:
        GetDlgItemText( hdlg, cmd, s, 4);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng <= 180)
            pd[0].lon_deg = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_DEG2:
        GetDlgItemText( hdlg, cmd, s, 4);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng <= 180)
            pd[1].lon_deg = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_MIN:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].lon_min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_MIN2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].lon_min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_SEC:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].lon_sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LONG_SEC2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].lon_sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_DEG:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng <= 90)
            pd[0].lat_deg = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_DEG2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng <= 90)
            pd[1].lat_deg = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_MIN:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].lat_min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_MIN2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].lat_min = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_SEC:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[0].lat_sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_LAT_SEC2:
        GetDlgItemText( hdlg, cmd, s, 3);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng < 60)
            pd[1].lat_sec = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case EDIT_ALT:
        GetDlgItemText( hdlg, cmd, s, 10);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK)
            pd[0].alt = slng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[0].alt = atol(s);
        return TRUE;
      case EDIT_ALT2:
        GetDlgItemText( hdlg, cmd, s, 10);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK)
            pd[1].alt = slng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[0].alt = atol(s);
        return TRUE;
      case EDIT_RANGE:
        GetDlgItemText( hdlg, cmd, s, 10);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK) {
            i = abs(pd[0].range2 - slng)+1;
            if (pd[0].range1 != (unsigned long) slng) {
              pd[0].range1 = slng;
              if (i > 100)
                sprintf(s1, "ATTENTION: computation of %d asteroids will take some time\r\n", i);
              else
                strcpy(s1, "\r\n\0");
              SetDlgItemText( hdlg, EDIT_OUTPUT2, s1);
            }
          } else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[0].range1 = atol(s);
        return TRUE;
      case EDIT_RANGE2:
        GetDlgItemText( hdlg, cmd, s, 10);
        if (*s != '\0') {
          if (atoslng(s, &slng) == OK) {
            i = abs(slng - pd[0].range1)+1;
            if (pd[0].range2 != (unsigned long) slng) {
              pd[0].range2 = slng;
              if (i > 100)
                sprintf(s1, "ATTENTION: computation of %d asteroids will take some time\r\n", i);
              else
                strcpy(s1, "\r\n\0");
              SetDlgItemText( hdlg, EDIT_OUTPUT2, s1);
            }
          }  else
            SetDlgItemText( hdlg, cmd, "");
        }
        pd[0].range2 = atol(s);
        return TRUE;
      case EDIT_HARM:
        GetDlgItemText( hdlg, cmd, s, 4);
        if (*s != '\0') {
          if (atoulng(s, &ulng) == OK && ulng <= 3600 && ulng > 0)
            pd[0].harm = (unsigned int) ulng;
          else
            SetDlgItemText( hdlg, cmd, "");
        }
        return TRUE;
      case CH_LBR:
        pd[0].print_with_lbr = (IsDlgButtonChecked( hdlg, cmd) == 1);
        return( TRUE );
      case CH_NEG_LAT:
        pd[0].neglect_lat = (IsDlgButtonChecked( hdlg, cmd) == 1);
        return( TRUE );
      case CH_HS_DATE1:
        pd[0].hs_date1 = (IsDlgButtonChecked( hdlg, cmd) == 1);
        return( TRUE );
      case CH_WOUT_NATAL:
        pd[0].without_natal = (IsDlgButtonChecked( hdlg, cmd) == 1);
        return( TRUE );
      case CH_CONV:
        pd[0].converse = (IsDlgButtonChecked( hdlg, cmd) == 1);
        return( TRUE );
      case EDIT_OUTFNAM:
        GetDlgItemText( hdlg, cmd, s, AS_MAXCH);
        if (*s == '\0')
          strcpy(pd[0].fname_out, "ast_out.txt");
        else if (strcmp(s, pd[0].fname_out) == 0)
          return TRUE;
        else
          strcpy(pd[0].fname_out, s);
        return( TRUE );
      case EDIT_EPHPATH:
        GetDlgItemText( hdlg, cmd, s, AS_MAXCH);
        if (strcmp(s, pd[0].ephe_path) != 0)
          strcpy(pd[0].ephe_path, s);
        return( TRUE );
      }
      return( FALSE );
    }
  return( FALSE );
}

static void init_data(void)
{
  int i;
  time_t time_of_day;
  struct tm tmbuf;
  FILE *fp;
  time_of_day = time(NULL);
  tmbuf = *gmtime(&time_of_day);
  //if (0) {
  if ((fp = fopen("ceres.stt", BFILE_R_ACCESS)) != NULL) {
    fread((void *) &pd[0], 1, sizeof(struct pd) * 2, fp);
    fclose(fp);
    pd[1].mday = tmbuf.tm_mday;
    pd[1].mon = tmbuf.tm_mon + 1;
    pd[1].year = tmbuf.tm_year + 1900;
    pd[1].hour = tmbuf.tm_hour;
    pd[1].min = tmbuf.tm_min;
    pd[1].sec = tmbuf.tm_sec;
  } else {
    for (i = 0; i < NPD; i++) {
      pd[i].mday = tmbuf.tm_mday;
      pd[i].mon = tmbuf.tm_mon + 1;
      pd[i].year = tmbuf.tm_year + 1900;
      pd[i].hour = tmbuf.tm_hour;
      pd[i].min = tmbuf.tm_min;
      pd[i].sec = tmbuf.tm_sec;
      /* coordinates of Zurich */
      pd[i].lon_deg = 8;
      pd[i].lon_min = 33;
      pd[i].lon_sec = 0;
      pd[i].lat_deg = 47;
      pd[i].lat_min = 23;
      pd[i].lat_sec = 0;
      pd[i].alt = 400;
      strcpy(pd[i].etut, etut[0]);
      strcpy(pd[i].lat_n_s, lat_n_s[0]);
      strcpy(pd[i].lon_e_w, lon_e_w[0]);
    }
    pd[0].harm = 1;
    pd[0].range1 = 1;
    pd[0].range2 = 10000;
    pd[0].ihtyp = NHT_NAT;
    pd[0].isorttyp = STP_POS;
    pd[0].ictr = CTR_GEO;
    pd[0].iaptr = APTR_APP;
    pd[0].ihsy = 0;
    pd[0].iephe = 0;
    pd[0].print_with_lbr = FALSE;
    pd[0].neglect_lat = FALSE;
    pd[0].hs_date1 = FALSE;
    pd[0].without_natal = FALSE;
    pd[0].converse = FALSE;
    strcpy(pd[0].fname_out, "ast_out.txt");
    strcpy(pd[0].ephe_path, SWEAST_EPHE_PATH);
  }
}

static struct outp {
  long ipl;
  double pos, x[3];
  char name[30];
};

static struct calc {
  int ihor;
  int ihtyp;
  char ephepath[AS_MAXCH];
  char fname[AS_MAXCH];
  long whicheph;
  AS_BOOL universal_time;
  AS_BOOL calc_house_pos;
  struct outp *oup;
  short gregflag;
  double tjd_ut[2], tjd_ut2;
  double tjd_et[2], t2;
  double delt;
  long iflag, iflag2;              /* external flag: helio, geo... */
  long range1, range2;
  char *plsel;
  long ioutp;
  char serr[AS_MAXCH * 2];
  int hsys;
  double armc[2], lat[2], lon[2], hgt[2], eps_true[2];
};

static int swisseph()
{
  char s[AS_MAXCH], *sp; 
  char s1[AS_MAXCH], s2[AS_MAXCH], s3[AS_MAXCH];
  char *spnam2 = "";
  char *fmt = "PLBRADS";
  char *gap = " ";
  double jut = 0.0;
  int i, j;
  int ipldiff = SE_SUN;
  AS_BOOL diff_mode = FALSE;
  int round_flag = 0, flag;
  struct outp *outp, *oup;
  struct calc calc;
  struct calc *clp = &calc;
  char serr_save[AS_MAXCH], serr_warn[AS_MAXCH];
  char *serr = clp->serr;
  int ihor = 0, hsys;
  FILE *fp = NULL;
  memset(clp, 0, sizeof(struct calc));
  clp->whicheph = SEFLG_SWIEPH;
  clp->tjd_ut[0] = 2415020.5;
  hsys = clp->hsys = (int) *hsysname[pd[0].ihsy];
  clp->range1 = pd[0].range1;
  clp->range2 = pd[0].range2;
  if (clp->range2 < clp->range1) {
    i = clp->range2;
    clp->range2 = clp->range1;
    clp->range1 = i;
  }
  do_print(buf, "");
  i = clp->range2 - clp->range1 + 500;
  i = i * 2;
  if ((outp = calloc(i, sizeof(struct outp))) == NULL) {
    do_print(buf, "calloc outp failed");
    return OK;
  }
  *serr = *serr_save = *serr_warn = '\0';
  if (pd[0].iephe == 1) {
    clp->whicheph = SEFLG_JPLEPH;
    strcpy(clp->fname, SE_FNAME_DE406);
  } else if (pd[0].iephe == 0) 
    clp->whicheph = SEFLG_SWIEPH;
  else
    clp->whicheph = SEFLG_MOSEPH;
  if (strcmp(pd[0].etut, "UT") == 0)
    clp->universal_time = TRUE;
  clp->plsel = PLSEL_D;
  switch(pd[0].ictr) {
    case CTR_HOUSE_TOPO:
      clp->calc_house_pos = TRUE;
      /* topocentric positions only if both horos have their own 
       * horizon */
      if (pd[0].ihtyp == 0 || !pd[0].hs_date1)
        clp->iflag |= SEFLG_TOPOCTR;
        break;
    case CTR_HOUSE: clp->calc_house_pos = TRUE; break;
    case CTR_TOPO: clp->iflag |= SEFLG_TOPOCTR; break;
    case CTR_HELIO: clp->iflag |= SEFLG_HELCTR; break;
    case CTR_BARY: clp->iflag |= SEFLG_BARYCTR; break;
  }
  if (pd[0].iaptr == APTR_TRUE)
    clp->iflag |= SEFLG_TRUEPOS;
  else
    clp->iflag &= ~SEFLG_TRUEPOS;
  clp->ioutp = 0;
  clp->oup = outp;
  clp->ihtyp = pd[0].ihtyp;
  calc_planets(clp, buf);
  if (abort_loop) {
    do_print(buf, "computation has been interrupted");
    SetDlgItemText( hdlgsweph, EDIT_OUTPUT2, buf);
    SetDlgItemText( hdlgsweph, PB_DOIT, "Compute");
    UpdateWindow(hdlgsweph);
    _endthread();
    return OK;
  } else {
    sprintf(s, " output was written to file %s\n\n", pd[0].fname_out);
    do_print(buf, s);
    SetDlgItemText( hdlgsweph, EDIT_OUTPUT2, buf);
    // don't update window yet, file is not written yet
  }
  switch(pd[0].isorttyp) {
  /* sort by position */
  case STP_POS: case STP_DIST: case STP_LAT: case STP_DECL: case STP_HGT:
    qsort((void *) outp, (size_t) clp->ioutp, sizeof(struct outp),
      (int (*)(const void *, const void *))(pos_compare));
    break;
  /* sort by name */
  case STP_NAM:
    qsort((void *) outp, (size_t) clp->ioutp, sizeof(struct outp),
      (int (*)(const void *, const void *))(name_compare));
    break;
  /* sort by catalogue number */
  case STP_NUM:
    qsort((void *) outp, (size_t) clp->ioutp, sizeof(struct outp),
      (int (*)(const void *, const void *))(ipl_compare));
    break;
  }
  sprintf(s, "%s", pd[0].fname_out);
  if ((fp = fopen(s, FILE_W_CREATE)) == NULL) {
    do_print(buf, "fopen(output file) failed");
    SetDlgItemText( hdlgsweph, EDIT_OUTPUT2, buf);
    SetDlgItemText( hdlgsweph, PB_DOIT, "Compute");
    UpdateWindow(hdlgsweph);
    _endthread();
    return OK;
  }
  if ((sp = strstr(buf, "output was written")) != NULL) {
    *sp = '\0';
    fprintf(fp, buf);
    *sp = 'o';
  } else
    fprintf(fp, buf);
  if (pd[0].harm > 1 && pd[0].isorttyp != STP_HGT 
               && pd[0].isorttyp != STP_DECL
               && pd[0].isorttyp != STP_LAT
               && pd[0].isorttyp != STP_DIST) {
    for (i = 0, oup = outp; i < clp->ioutp; i++, oup++) {
      strcpy(s1, dms(oup->x[0], round_flag|BIT_ZODIAC));
      strcpy(s2, dms(oup->x[1], round_flag));
      sprintf(s, "%s %s %5ld %s\n", s1, s2, oup->ipl, oup->name); 
      fprintf(fp, s);
      do_print(buf, s);
    }
  } else if (pd[0].print_with_lbr 
      && pd[0].ictr != CTR_HOUSE && pd[0].ictr != CTR_HOUSE_TOPO 
      && pd[0].isorttyp != STP_HGT) {
    for (i = 0, oup = outp; i < clp->ioutp; i++, oup++) {
      strcpy(s1, dms(oup->x[0], round_flag|BIT_ZODIAC));
      strcpy(s2, dms(oup->x[1], round_flag));
      sprintf(s3, "%# 14.9f", oup->x[2]);
      sprintf(s, "%s %s %s %5ld %s\n", s1, s2, s3, oup->ipl, oup->name); 
      fprintf(fp, s);
      do_print(buf, s);
    }
  } else if (pd[0].isorttyp == STP_DIST) {
    for (i = 0, oup = outp; i < clp->ioutp; i++, oup++) {
      sprintf(s1, "%# 14.9f", oup->x[2]);
      sprintf(s, "%s %5ld %s\n", s1, oup->ipl, oup->name); 
      fprintf(fp, s);
      do_print(buf, s);
    }
  } else {
    switch(pd[0].isorttyp) {
    case STP_NUM: case STP_NAM: case STP_POS: 
      j = 0; 
      if (pd[0].ictr != CTR_HOUSE && pd[0].ictr != CTR_HOUSE_TOPO)
        flag = round_flag|BIT_ZODIAC; 
      break;
    case STP_LAT: case STP_DECL: case STP_HGT: 
      j = 1; 
      flag = round_flag;
      break;
    }
    for (i = 0, oup = outp; i < clp->ioutp; i++, oup++) {
      strcpy(s1, dms(oup->x[j], flag));
      sprintf(s, "%s %5ld %s\n", s1, oup->ipl, oup->name); 
      fprintf(fp, s);
      do_print(buf, s);
    }
  }
  free(outp);
  fclose(fp);
  do_print(buf, "done");
  SetDlgItemText(hdlgsweph, EDIT_OUTPUT2, buf);
  //UpdateWindow(hdlgsweph);
#if BEGTHREAD
  //ghThread = 0;
  SetDlgItemText( hdlgsweph, PB_DOIT, "Compute");
  UpdateWindow(hdlgsweph);
  _endthread();
#endif
  return 0;
}

static int calc_planets(struct calc *clp, char *buf) 
{
  char *serr = clp->serr, serr_save[AS_MAXCH], serr_warn[AS_MAXCH];
  char s[AS_MAXCH]; 
  char sn[AS_MAXCH]; 
  char se_pname[AS_MAXCH];
  char *spnam2 = "";
  char *fmt = "PLBRADS";
  char *plsel = clp->plsel, *psp;
  char *gap = " ";
  double jut = 0.0;
  int i, ioutp = clp->ioutp;
  double hpos;
  int jday, jmon, jyear, jhour, jmin, jsec;
  int ipl, ipldiff = SE_SUN;
  double x[6], xequ[2][6];
  double xx[2][6];
  double cusp[2][12+1];    /* cusp[0] + 12 houses */
  double ascmc[2][10];		/* asc, mc, vertex ...*/
  double sidt, armc, lon, lat, hgt;
  double eps_true, eps_mean, nutl, nuto;
  int iast, nerr;
  long iflag = clp->iflag, iflag2 = clp->iflag2;              /* external flag: helio, geo... */
  long iflgret;
  long range1 = clp->range1, range2 = clp->range2;
  int isorttyp = pd[0].isorttyp, ictr = pd[0].ictr;
  long whicheph = clp->whicheph;
  long harm = pd[0].harm;
  int round_flag = 0;
  int ihor = clp->ihor, nhor, nhor_out, ihstart;
  int ihtyp = clp->ihtyp;
  AS_BOOL universal_time = clp->universal_time;
  AS_BOOL calc_house_pos = clp->calc_house_pos;
  short gregflag = clp->gregflag;
  AS_BOOL diff_mode = FALSE;
  double tjd_ut;
  double tjd_et;
  double delt = clp->delt;
  struct outp *oup = clp->oup;
  char spref[10];
  int hsys = clp->hsys;
  *serr_warn = *serr_save = 0;
  astcount = 0;
  swe_set_ephe_path(pd[0].ephe_path);
  sprintf(s, "Planets and Asteroids %d - %d \n", clp->range1, clp->range2);
  sprintf(s + strlen(s), " ---------------------\n");
  sprintf(s + strlen(s), " %s, %s, %s, %s, %s, harm=%d\n", 
	  horotyp[pd[0].ihtyp], ctr[pd[0].ictr], aptr[pd[0].iaptr], 
	  hsysname[pd[0].ihsy], sorttyp[pd[0].isorttyp], 
	  pd[0].harm);
  do_print(buf, s);           
  if (clp->whicheph & SEFLG_JPLEPH)
    swe_set_jpl_file(clp->fname);
  iflag = (iflag & ~SEFLG_EPHMASK) | whicheph;
  iflag |= SEFLG_SPEED;
  if (ihtyp == NHT_NAT)
    nhor = 1;
  else
    nhor = 2;
  /********************************
   * date, place, sid. time, armc
   ********************************/
  for (ihor = 0; ihor < nhor; ihor++) {
    lon = pd[ihor].lon_deg + pd[ihor].lon_min / 60.0 + pd[ihor].lon_sec / 3600.0;
    if (*pd[ihor].lon_e_w == 'W')
      lon = -lon;
    lat = pd[ihor].lat_deg + pd[ihor].lat_min / 60.0 + pd[ihor].lat_sec / 3600.0;
    if (*pd[ihor].lat_n_s == 'S')
      lat = -lat;
    hgt = pd[ihor].alt;
    if ((long) pd[ihor].year * 10000L + 
        (long) pd[ihor].mon * 100L + (long) pd[ihor].mday < 15821015L) 
      gregflag = FALSE;
    else
      gregflag = TRUE;
    jday = pd[ihor].mday;
    jmon = pd[ihor].mon;
    jyear = pd[ihor].year;
    jhour = pd[ihor].hour;
    jmin = pd[ihor].min;
    jsec = pd[ihor].sec;
    jut = jhour + jmin / 60.0 + jsec / 3600.0;
    tjd_ut = swe_julday(jyear,jmon,jday,jut,gregflag);
    if (!universal_time) {
      universal_time = TRUE;
      tjd_ut -= swe_deltat(tjd_ut);
    }
    clp->tjd_ut2 = tjd_ut;
    /* from here on, universal_time is always true ! */
    sprintf(s, " date %d: ", ihor+1);
    get_date_string(tjd_ut, gregflag, s + strlen(s));
    delt = swe_deltat(tjd_ut);
    tjd_et = tjd_ut + delt;
    sprintf(s + strlen(s), "   jd (ET) = %f\n", tjd_et);
    do_print(buf, s);
    /* ut, et wird noch nicht richtig verarbeitet !!!!!!!!!!!! */
    /* progressive date */
    if (ihor == 1) {
      if (ihtyp == NHT_PROGR || ihtyp == NHT_PROGR_2) {
        tjd_ut = calc_prog_date(clp->tjd_ut[0], tjd_ut, pd[0].converse, TROP_YEAR, clp);
        tjd_et = tjd_ut + swe_deltat(tjd_ut);
      /* primary directions: 
       * date = progressive date of progressive date */
      } else if (ihtyp == NHT_PRIM) {
        tjd_ut = calc_prog_date(clp->tjd_ut[0], tjd_ut, pd[0].converse, TROP_YEAR, clp);
        tjd_ut = calc_prog_date(clp->tjd_ut[0], tjd_ut, FALSE, TROP_YEAR, clp);
        tjd_et = tjd_ut + swe_deltat(tjd_ut);
      } else if (ihtyp == NHT_SYN && pd[0].converse) {
        tjd_ut = calc_prog_date(clp->tjd_ut[0], tjd_ut, pd[0].converse, 1, clp);
        tjd_et = tjd_ut + swe_deltat(tjd_ut);
      } else if (ihtyp == NHT_PRIM_N) {
        tjd_ut = clp->tjd_ut[0];
        tjd_et = tjd_ut + swe_deltat(tjd_ut);
      }
      if (ihtyp > NHT_NAT) {
        if (ihtyp > NHT_SYN || (ihtyp == NHT_SYN && pd[0].converse)) {
          strcpy(s, "       = ");
          get_date_string(tjd_ut, gregflag, s+strlen(s));
          sprintf(s+strlen(s), "   jd (ET) = %f\n\n", tjd_et);
          do_print(buf, s);
        }
      }
    }
    iflgret = do_calc(tjd_et, SE_ECL_NUT, iflag, x, serr);
    eps_true = x[0];
    eps_mean = x[1];
    nutl = x[2];
    nuto = x[3];
    sidt = swe_sidtime(tjd_ut) + lon / 15;
    if (sidt >= 24)
      sidt -= 24;
    if (sidt < 0)
      sidt += 24;
    armc = sidt * 15;
    if (ihor == 1) {
      if (pd[0].hs_date1) {
        armc = clp->armc[0];
      lat = clp->lat[0];
        lon = clp->lon[0];
        hgt = clp->hgt[0];
        eps_true = clp->eps_true[0];
      }
      if ((ihtyp == NHT_PROGR_2 || ihtyp == NHT_PRIM_N) && !pd[0].hs_date1) {
        armc = calc_prog_armc(pd[0].converse, TROP_YEAR, KEY_NAIBOD, clp);
        eps_true = clp->eps_true[0];
        lat = clp->lat[0];
        lon = clp->lon[0];
        hgt = clp->hgt[0];
      }
    }
    clp->armc[ihor] = armc;
    clp->lat[ihor] = lat;
    clp->lon[ihor] = lon;
    clp->hgt[ihor] = hgt;
    clp->eps_true[ihor] = eps_true;
    clp->tjd_ut[ihor] = tjd_ut;
    clp->tjd_et[ihor] = tjd_et;
  }
  if (ihtyp == NHT_COMBIN) {
    clp->tjd_ut[0] = (clp->tjd_ut[0] + clp->tjd_ut[1]) / 2;
    clp->lat[0] = (clp->lat[0] + clp->lat[1]) / 2;
    clp->lon[0] = swe_degnorm((clp->lon[0] + clp->lon[1]) / 2);
    if (clp->lon[0] > 180)
      clp->lon[0] -= 360;
    clp->hgt[0] = (clp->hgt[0] + clp->hgt[1]) / 2;
	strcpy(s, "       = ");
    get_date_string(clp->tjd_ut[0], gregflag, s+strlen(s));
    do_print(buf, s);
    delt = swe_deltat(clp->tjd_ut[0]);
    clp->tjd_et[0] = clp->tjd_ut[0] + delt;
    sprintf(s, "   jd (ET) = %f\n\n", clp->tjd_et[0]);
    do_print(buf, s);
    iflgret = do_calc(clp->tjd_et[0], SE_ECL_NUT, iflag, x, serr);
    clp->eps_true[0] = x[0];
    nutl = x[2];
    nuto = x[3];
    sidt = swe_sidtime(tjd_ut) + lon / 15;
    if (sidt >= 24)
      sidt -= 24;
    if (sidt < 0)
      sidt += 24;
    clp->armc[0] = sidt * 15;
    nhor = 1;
  }
  /********************************
   * planets
   ********************************/
  switch(ihtyp) {
  case NHT_SYN: strcpy(spref, "TR "); break;
  case NHT_PROGR: strcpy(spref, "PRO "); break;
  case NHT_PROGR_2: strcpy(spref, "PRO2 "); break;
  case NHT_PRIM: strcpy(spref, "PRI "); break;
  case NHT_PRIM_N: strcpy(spref, "PRIN "); break;
  default: *spref = 0; break;
  }
  ihstart = 0;
  if (pd[0].without_natal && nhor != 1)
    ihstart = 1;
  for (psp = plsel, iast = range1-1, astcount = 0, nerr = 0; *psp != '\0'; psp++) {
    if (abort_loop) {
      swe_close();
      return OK;
    }
    nhor_out = nhor;
    if (*psp == '+') {
      iast++;
      astcount++;
      ipl = SE_AST_OFFSET + iast;
      if (iast < range2)
        psp--;
    } else {
      /* no main planets if sort by catalogue number */
      if (isorttyp == STP_NUM)
	continue;
      ipl = letter_to_ipl(*psp);
    }
    if (iast > 0 && iast % 10 == 0) {
      sprintf(s, "Asteroid positions %d - %d being computed ...\r\n\r\n%d", range1, range2, iast);
      SetDlgItemText( hdlgsweph, EDIT_OUTPUT2, s);
      UpdateWindow(hdlgsweph);
    }
    if (iflag & SEFLG_HELCTR) {
      if (ipl == SE_SUN
        || ipl == SE_MEAN_NODE || ipl == SE_TRUE_NODE
        || ipl == SE_MEAN_APOG || ipl == SE_OSCU_APOG)
      continue;
    } else if (iflag & SEFLG_BARYCTR) {
      if (ipl == SE_MEAN_NODE || ipl == SE_TRUE_NODE
        || ipl == SE_MEAN_APOG || ipl == SE_OSCU_APOG)
      continue;
    } else {         /* geocentric */
      if (ipl == SE_EARTH)
        continue;
    }
    for (ihor = ihstart; ihor < nhor; ihor++) {
      lon = clp->lon[ihor];
      lat = clp->lat[ihor];
      hgt = clp->hgt[ihor];
      armc = clp->armc[ihor];
      eps_true = clp->eps_true[ihor];
      tjd_et = clp->tjd_et[ihor];
      if ((iflag & SEFLG_TOPOCTR) && (ihor == 0
          || (clp->lon[0] == lon && clp->lat[0] == lat && clp->hgt[0] == hgt)))
        swe_set_topo(lon, lat, hgt);
      /* ecliptic position */
      iflgret = do_calc(tjd_et, ipl, iflag, xx[ihor], serr);
      if (iflgret >= 0) {
        if (calc_house_pos) {
          if (pd[0].neglect_lat)
            xx[ihor][1] = 0;		/* neglect ecliptical latitude */
          hpos = swe_house_pos(armc, lat, eps_true, hsys, xx[ihor], serr);
          if (hpos == 0)
            iflgret = ERR;
          xx[ihor][0] = (hpos - 1) * 30;
        }
      }
      if (iflgret < 0 && nerr < 20) {
        if (*serr != '\0' && strcmp(serr, serr_save) != 0) {
          strcpy (serr_save, serr);
          do_print(buf, "error: ");
          do_print(buf, serr);
          do_print(buf, "\n");
          ihor = 1;
          nerr++;
        }
        continue;
      } else if (*serr != '\0' && *serr_warn == '\0')
        strcpy(serr_warn, serr);
      /* equator position */
      if (strpbrk(fmt, "aADdQ") != NULL) {
        iflag2 = iflag | SEFLG_EQUATORIAL;
        iflgret = do_calc(tjd_et, ipl, iflag2, xequ[ihor], serr);
      }
    }
    swe_get_planet_name(ipl, sn);
    if (isdigit(*(sn + 1)))
      continue;
    if (ihtyp == NHT_COMPOSIT) {
      /* longitude */
      double a = swe_degnorm(xx[1][0] - xx[0][0]);
      if (a > 180)
        a -= 360;
      xx[0][0] = swe_degnorm(xx[0][0] + a / 2);
      /* latitude */
      xx[0][1] = (xx[0][1] + xx[1][1]) / 2;
      /* rectasc */
      a = swe_degnorm(xequ[1][0] - xequ[0][0]);
      if (a > 180)
        a -= 360;
      xequ[0][0] = swe_degnorm(xequ[0][0] + a / 2);
      /* decl */
      xequ[0][1] = (xequ[0][1] + xequ[1][1]) / 2;
      /* distance */
      xx[0][2] = (xx[0][2] + xx[1][2]) / 2;
      /* speed */
      xx[0][3] = (xx[0][3] + xx[1][3]) / 2;
      nhor_out = 1;
    }
    for (ihor = ihstart; ihor < nhor_out; ihor++) {
      if (ihor == 1)
        strcpy(se_pname, spref);
      else
        *se_pname = '\0';
      strcpy(se_pname + strlen(se_pname), sn);
      for (i = 0; i <= 2; i++)
        oup->x[i] = xx[ihor][i];
      if (iast >= range1)
        oup->ipl = iast;
      else
        oup->ipl = 0; 
      /* 
       * azimuth and altitude
       */
      if (isorttyp == STP_DIST) /* sort by distances */
        oup->pos = xx[ihor][2];
      else if (isorttyp == STP_LAT) /* sort by latitudes */
        oup->pos = fabs(xx[ihor][1]);
      else if (isorttyp == STP_DECL) { /* sort by declinations */
        oup->pos = fabs(xequ[ihor][1]);
        oup->x[1] = xequ[ihor][1];
      } else if (isorttyp == STP_HGT) { /* sort by height */
        double xh[3], mdd;
        mdd = swe_degnorm(xequ[ihor][0] - armc);
        xh[0] = swe_degnorm(mdd - 90);
        xh[1] = xequ[ihor][1];
        xh[2] = xequ[ihor][2];
        swe_cotrans(xh, xh, 90 - lat);	/* azimuth from east, counterclock */
        oup->pos = fabs(xh[1]);
        oup->x[1] = xh[1];
      } else {
        oup->pos = swe_degnorm(xx[ihor][0] * harm);  
        if (harm > 1) /* harmonic position value into latitude */
          oup->x[1] = oup->pos;
      }
      strcpy(oup->name, se_pname); oup++; ioutp++;
      if (iast < range1) {
        /* aspect points for main planets, if sort by position */
        if (isorttyp == STP_POS && harm == 1) {
          oup->ipl = -180; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] + 180); 
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -90; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] + 90);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -90; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] - 90);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -120; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] + 120);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -120; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] - 120);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -60; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] + 60);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
          oup->ipl = -60; oup->x[0] = oup->pos = swe_degnorm(xx[ihor][0] - 60);  
          strcpy(oup->name, se_pname); oup++; ioutp++;
        }
      }
    }
  }     /* for psp */
  if (*serr_warn != '\0') {
    do_print(buf, "\nwarning: ");
    do_print(buf, serr_warn);
    do_print(buf, "\n");
  }
  /********************************
   * houses
   ********************************/
  round_flag |= BIT_ROUND_SEC;
  /* houses, if sort by position */
  if (isorttyp == STP_POS && !calc_house_pos 
      && (ictr == CTR_GEO || ictr == CTR_TOPO)) {
    nhor_out = nhor;
    if (ihtyp == NHT_COMPOSIT) {
      double a = swe_degnorm(clp->armc[1] - clp->armc[0]);
      if (a > 180)
        a -= 360;
      clp->armc[0] = swe_degnorm(clp->armc[0] + a / 2);
      clp->lat[0] = (clp->lat[0] + clp->lat[1]) / 2;
      clp->eps_true[0] = (clp->eps_true[0] + clp->eps_true[1]) / 2;
      nhor_out = 1;
    }
    for (ihor = ihstart; ihor < nhor_out; ihor++) {
      if (ihor != 0 && pd[0].hs_date1)
        continue;
      lon = clp->lon[ihor];
      lat = clp->lat[ihor];
      hgt = clp->hgt[ihor];
      armc = clp->armc[ihor];
      eps_true = clp->eps_true[ihor];
      tjd_ut = clp->tjd_ut[ihor];
      swe_houses_armc(armc, lat, eps_true, hsys, cusp[ihor], ascmc[ihor]);
    }
    for (ihor = ihstart; ihor < nhor_out; ihor++) {
      if (ihor != 0 && pd[0].hs_date1)
        continue;
      if (ihor == 1)
        strcpy(se_pname, spref);
      else
        *se_pname = '\0';
      oup->ipl = 0; oup->x[0] = ascmc[ihor][0]; 
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sAscendant", se_pname); ioutp++; oup++;
      oup->ipl = 0; oup->x[0] = ascmc[ihor][1];
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sMedium Coeli", se_pname); ioutp++; oup++;
      oup->ipl = 0; oup->x[0] = swe_degnorm(ascmc[ihor][0]+180);
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sDescendant", se_pname); ioutp++; oup++;
      oup->ipl = 0; oup->x[0] = swe_degnorm(ascmc[ihor][1]+180);
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sImum Coeli", se_pname); ioutp++; oup++;
      if (isorttyp == STP_POS && harm == 1) {
        oup->ipl = -90; oup->x[0] = swe_degnorm(ascmc[ihor][0]+90);
        oup->pos = swe_degnorm(oup->x[0] * harm); 
        if (harm > 1) oup->x[1] = oup->pos;
        sprintf(oup->name, "%sAscendant", se_pname); ioutp++; oup++;
        oup->ipl = -90; oup->x[0] = swe_degnorm(ascmc[ihor][0]-90);
        oup->pos = swe_degnorm(oup->x[0] * harm); 
        if (harm > 1) oup->x[1] = oup->pos;
        sprintf(oup->name, "%sAscendant", se_pname); ioutp++; oup++;
        oup->ipl = -90; oup->x[0] = swe_degnorm(ascmc[ihor][1]+90);
        oup->pos = swe_degnorm(oup->x[0] * harm); 
        if (harm > 1) oup->x[1] = oup->pos;
        sprintf(oup->name, "%sMedium Coeli", se_pname); ioutp++; oup++;
        oup->ipl = -90; oup->x[0] = swe_degnorm(ascmc[ihor][1]-90);
        oup->pos = swe_degnorm(oup->x[0] * harm); 
        if (harm > 1) oup->x[1] = oup->pos;
        sprintf(oup->name, "%sMedium Coeli", se_pname); ioutp++; oup++;
      }
      oup->ipl = 0; oup->x[0] = ascmc[ihor][3];
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sVertex", se_pname); ioutp++; oup++;
      oup->ipl = 0; oup->x[0] = swe_degnorm(ascmc[ihor][3]+180);
      oup->pos = swe_degnorm(oup->x[0] * harm); 
      if (harm > 1) oup->x[1] = oup->pos;
      sprintf(oup->name, "%sAntivertex", se_pname); ioutp++; oup++;
      for (i = 1; i <= 12; i++) {
        sprintf(oup->name, "%sHouse %d", se_pname, i);
        oup->ipl = 0; oup->x[0] = cusp[ihor][i];
        oup->pos = swe_degnorm(oup->x[0] * harm); 
        if (harm > 1) oup->x[1] = oup->pos;
        ioutp++; oup++;
      }
    }
  }
  clp->oup = oup;
  clp->ioutp = ioutp;
  swe_close();
  return OK;
}

static char *get_date_string(double tjd_ut, int gregflag, char *sp)
{
  int jyear, jmon, jday, jhour, jmin, jsec;
  double jut;
  char bc[20], *jul;
  swe_revjul(tjd_ut, gregflag, &jyear, &jmon, &jday, &jut);
  jut += 0.5 / 3600;
  jhour = (int) jut;
  jmin = (int) fmod(jut * 60, 60);
  jsec = (int) fmod(jut * 3600, 60);
  *bc = '\0';
  if (jyear <= 0)
    sprintf(bc, "(%d B.C.)", 1 - jyear);
  if (jyear * 10000L + jmon * 100L + jday <= 15821004)
    jul = "jul.";
  else
    jul = "";
  sprintf(sp, "%#02d.%#02d.%d %s %s    %#02d:%#02d:%#02d UT",
    jday, jmon, jyear, bc, jul,
    jhour, jmin, jsec);
  swe_revjul(tjd_ut + swe_deltat(tjd_ut), gregflag, &jyear, &jmon, &jday, &jut);
  jut += 0.5 / 3600;
  jhour = (int) jut;
  jmin = (int) fmod(jut * 60, 60);
  jsec = (int) fmod(jut * 3600, 60);
  sprintf(sp + strlen(sp), "  %#02d:%#02d:%#02d ET",jhour, jmin, jsec);  
  return sp;
}

static double calc_prog_armc(AS_BOOL converse, double period, double key, struct calc * clp)
{
  double dt, tjde1, tjde2;
  double nper, armc;
  double tjdu1 = clp->tjd_ut[0];
  double tjdu2 = clp->tjd_ut2;
  tjde1 = tjdu1 + swe_deltat(tjdu1);
  tjde2 = tjdu2 + swe_deltat(tjdu2);
  dt = tjde2 - tjde1;
  if (converse)
    dt = - dt;
  nper = dt / period;
  armc = clp->armc[0] + nper * key;
  return armc;
}

static double calc_prog_date(double tjdu1, double tjdu2, AS_BOOL converse, double period, struct calc *clp)
{
  double dt, tjde1, tjde2;
  tjde1 = tjdu1 + swe_deltat(tjdu1);
  tjde2 = tjdu2 + swe_deltat(tjdu2);
  dt = tjde2 - tjde1;
  if (converse)
    dt = - dt;
  tjde1 += dt / period;
  tjdu1 = tjde1 - swe_deltat(tjde1);
  return tjdu1;
}

static int ipl_compare(const struct outp *a1, const struct outp *a2)
{
  return (a1->ipl - a2->ipl);
}

static int name_compare(const struct outp *a1, const struct outp *a2)
{
  char s1[AS_MAXCH], s2[AS_MAXCH], *sp;
  strcpy(s1, a1->name);
  strcpy(s2, a2->name);
  for (sp = s1; *sp != 0; sp++)
    *sp = tolower(*sp);
  for (sp = s2; *sp != 0; sp++)
    *sp = tolower(*sp);
  return strcmp(s1, s2);
}

static int pos_compare(const struct outp *a1, const struct outp *a2)
{
  if (a1->pos >= a2->pos)
    return 1;
  else 
    return -1;
}
 
static char *dms(double x, long iflag)
{
  int izod;
  long k, kdeg, kmin, ksec;
  char c = (unsigned int) ODEGREE_CHAR;
  char *sp, s1[50];
  static char s[50];
  int sgn;
  *s = '\0';
  if (iflag & SEFLG_EQUATORIAL)
    c = 'h';
  if (x < 0) {
    x = -x;
    sgn = -1;
  } else 
    sgn = 1;
  if (iflag & BIT_ROUND_MIN)
    x += 0.5/60;
  if (iflag & BIT_ROUND_SEC)
    x += 0.5/3600;
  if (iflag & BIT_ZODIAC) {
    izod = (int) (x / 30); 
    x = fmod(x, 30);
    kdeg = (long) x;
    sprintf(s, "%2ld %s ", kdeg, zod_nam[izod]);
  } else {
    kdeg = (long) x;
    sprintf(s, " %3ld%c", kdeg, c);
  }
  x -= kdeg;
  x *= 60;
  kmin = (long) x;
  if ((iflag & BIT_ZODIAC) && (iflag & BIT_ROUND_MIN))
    sprintf(s1, "%2ld", kmin);
  else
    sprintf(s1, "%2ld'", kmin);
  strcat(s, s1);
  if (iflag & BIT_ROUND_MIN)
    goto return_dms;
  x -= kmin;
  x *= 60;
  ksec = (long) x;
  if (iflag & BIT_ROUND_SEC)
    sprintf(s1, "%2ld\"", ksec);
  else
    sprintf(s1, "%2ld", ksec);
  strcat(s, s1);
  if (iflag & BIT_ROUND_SEC)
    goto return_dms;
  x -= ksec;
  k = (long) (x * 10000);
  sprintf(s1, ".%04ld", k);
  strcat(s, s1);
  return_dms:;
  if (sgn < 0) {
    sp = strpbrk(s, "0123456789"); 
    *(sp-1) = '-';
  }
  return(s);
}

static long do_calc(double tjd, int ipl, long iflag, double *x, char *serr)
{
  return swe_calc(tjd, ipl, iflag, x, serr);
}

static long do_fixstar(char *star, double tjd, long iflag, double *x, char *serr)
{
  return swe_fixstar(star, tjd, iflag, x, serr);
}

static void do_print(char *target, char *info)
{
  char *sp;
  static char *pbuf = NULL;
  if (*target == '\0') {
    pbuf = target;
    strcpy(pbuf, " ");
  }
  for (sp = info; *sp != '\0'; sp++) {
    if (*sp == '\n') 
      strcat(pbuf, "\r\n");
    else /*if (isalnum(*sp) || *sp == '\0' || strchr(" \t-°'.\":", *sp) != NULL) */
      strncat(pbuf, sp, 1);
  }
  pbuf = pbuf + strlen(pbuf);
  *pbuf = '\0';
}

static int letter_to_ipl(int letter)
{
  if (letter >= '0' && letter <= '9')
    return letter - '0' + SE_SUN;
  if (letter >= 'A' && letter <= 'I')
    return letter - 'A' + SE_MEAN_APOG;
  if (letter >= 'J' && letter <= 'X')
    return letter - 'J' + SE_CUPIDO;
  switch (letter) {
  case 'm': return SE_MEAN_NODE;
  case 'n': 
  case 'o': return SE_ECL_NUT;
  case 't': return SE_TRUE_NODE;
  case 'f': return SE_FIXSTAR;
  }
  return -1;
}

static int atoulng(char *s, unsigned long *lng)
{
  char *sp;
  for (sp = s; *sp != '\0'; sp++) {
    if (!isdigit(*sp)) {
      *lng = 0;
      return ERR;
    }
  }
  *lng = atol(s);
  return OK;
}

static int atoslng(char *s, long *lng)
{
  char *sp;
  for (sp = s; *sp != '\0'; sp++) {
    if (!isdigit(*sp) && *sp != '-') {
      *lng = 0;
      return ERR;
    }
  }
  *lng = atol(s);
  return OK;
}

/* make_ephemeris_path().
 * ephemeris path includes
 *   current working directory
 *   + program directory
 *   + default path from swephexp.h on current drive
 *   +                              on program drive
 *   +                              on drive C:
 */
static int make_ephemeris_path(long iflag, char *argv0)
{
  char path[AS_MAXCH], s[AS_MAXCH];
  char *sp;
  char *dirglue = DIR_GLUE;
  size_t pathlen; 
  /* moshier needs no ephemeris path */
  if (iflag & SEFLG_MOSEPH)
    return OK;
  /* current working directory */ 
  sprintf(path, ".%c", *PATH_SEPARATOR);
  /* program directory */
  sp = strrchr(argv0, *dirglue);
  if (sp != NULL) {
    pathlen = sp - argv0;
    if (strlen(path) + pathlen < AS_MAXCH-1) {
      strcpy(s, argv0);
      *(s+pathlen) = '\0';
      sprintf(path + strlen(path), "%s%c", s, *PATH_SEPARATOR);
    } 
  }
#if MSDOS
{
  char *cpos[20];
  char s[2 * AS_MAXCH], *s1 = s + AS_MAXCH;
  char *sp[3];
  int i, j, np;
  strcpy(s1, SWEAST_EPHE_PATH);
  np = cut_str_any(s1, PATH_SEPARATOR, cpos, 20);
  /* 
   * default path from swephexp.h
   * - current drive
   * - program drive
   * - drive C
   */
  *s = '\0';
  /* current working drive */
  sp[0] = getcwd(NULL, 0);
  if (sp[0] == NULL) {
    /*do_printf("error in getcwd()\n");*/
    return ERR;
  } 
  if (*sp[0] == 'C')
    sp[0] = NULL;
  /* program drive */
  if (*argv0 != 'C' && (sp[0] == NULL || *sp[0] != *argv0))
    sp[1] = argv0;
  else
    sp[1] = NULL;
    /* drive C */
  sp[2] = "C";
  for (i = 0; i < np; i++) {
    strcpy(s, cpos[i]);
    if (*s == '.')	/* current directory */
      continue;
    if (s[1] == ':')  /* drive already there */
      continue;
    for (j = 0; j < 3; j++) {
      if (sp[j] != NULL && strlen(path) + 2 + strlen(s) < AS_MAXCH-1)
	sprintf(path + strlen(path), "%c:%s%c", *sp[j], s, *PATH_SEPARATOR);
    }
  }
}
#else
    if (strlen(path) + pathlen < AS_MAXCH-1)
      strcat(path, SWEAST_EPHE_PATH);
#endif
  return OK;
}

/**************************************************************
cut the string s at any char in cutlist; put pointers to partial strings
into cpos[0..n-1], return number of partial strings;
if less than nmax fields are found, the first empty pointer is
set to NULL.
More than one character of cutlist in direct sequence count as one
separator only! cut_str_any("word,,,word2",","..) cuts only two parts,
cpos[0] = "word" and cpos[1] = "word2".
If more than nmax fields are found, nmax is returned and the
last field nmax-1 rmains un-cut.
**************************************************************/
static int cut_str_any(char *s, char *cutlist, char *cpos[], int nmax)
{
  int n = 1;
  cpos [0] = s;
  while (*s != '\0') {
    if ((strchr(cutlist, (int) *s) != NULL) && n < nmax) {
      *s = '\0';
      while (*(s + 1) != '\0' && strchr (cutlist, (int) *(s + 1)) != NULL) s++;
      cpos[n++] = s + 1;
    }
    if (*s == '\n' || *s == '\r') {	/* treat nl or cr like end of string */
      *s = '\0';
      break;
    }
    s++;
  }
  if (n < nmax) cpos[n] = NULL;
  return (n);
}	/* cutstr */
