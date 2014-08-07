#ifdef J2ME
#define JAVAME
#endif /* J2ME */
//#ifdef NO_RISE_TRANS
//#define ASTROLOGY
//#endif /* NO_RISE_TRANS */
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 1.80.00
   of Astrodienst AG, Switzerland from the original C Code to Java. For
   copyright see the original copyright notices below and additional
   copyright notes in the file named LICENSE, or - if this file is not
   available - the copyright notes at http://www.astro.ch/swisseph/ and
   following.
   
   For any questions or comments regarding this port to Java, you should
   ONLY contact me and not Astrodienst, as the Astrodienst AG is not involved
   in this port in any way.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 23rd of April 2001

*/
/* Copyright (C) 1997 - 2008 Astrodienst AG, Switzerland.  All rights reserved.

  License conditions
  ------------------

  This file is part of Swiss Ephemeris.

  Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
  or distributor accepts any responsibility for the consequences of using it,
  or for whether it serves any particular purpose or works at all, unless he
  or she says so in writing.

  Swiss Ephemeris is made available by its authors under a dual licensing
  system. The software developer, who uses any part of Swiss Ephemeris
  in his or her software, must choose between one of the two license models,
  which are
  a) GNU public license version 2 or later
  b) Swiss Ephemeris Professional License

  The choice must be made before the software developer distributes software
  containing parts of Swiss Ephemeris to others, and before any public
  service using the developed software is activated.

  If the developer choses the GNU GPL software license, he or she must fulfill
  the conditions of that license, which includes the obligation to place his
  or her whole software project under the GNU GPL or a compatible license.
  See http://www.gnu.org/licenses/old-licenses/gpl-2.0.html

  If the developer choses the Swiss Ephemeris Professional license,
  he must follow the instructions as found in http://www.astro.com/swisseph/
  and purchase the Swiss Ephemeris Professional Edition from Astrodienst
  and sign the corresponding license contract.

  The License grants you the right to use, copy, modify and redistribute
  Swiss Ephemeris, but only under certain conditions described in the License.
  Among other things, the License requires that the copyright notices and
  this notice be preserved on all copies.

  Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl

  The authors of Swiss Ephemeris have no control or influence over any of
  the derived works, i.e. over software or services created by other
  programmers which use Swiss Ephemeris functions.

  The names of the authors or of the copyright holder (Astrodienst) must not
  be used for promoting any software, product or service which uses or contains
  the Swiss Ephemeris. This copyright notice is the ONLY place where the
  names of the authors can legally appear, except in cases where they have
  given special permission in writing.

  The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
  for promoting such software, products or services.
*/
import swisseph.*;
import java.io.*;

/**
* Test program for solar and lunar eclipses, rises, sets, and meridian
* transits for planets and fixed stars.<P>
* See class swisseph.SwissEph.<P>
* Invoke with parameter -h to get the help text.
* @see swisseph.SwissEph
*/
public class Sweclips
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  public static final int SMOD_LUNAR    =1;
  public static final int SMOD_HOW      =2;   /* an option for Lunar */ 
  public static final int SMOD_SOLAR    =4;
  public static final int SMOD_LOCAL    =8;   /* an option for Solar */
  public static final int SMOD_TOTAL    =16;
  public static final int SMOD_ANNULAR  =32;  /* count as penumbral for Lunar */
  public static final int SMOD_ANNTOT   =64;
  public static final int SMOD_PARTIAL  =128;
  public static final int SMOD_ALL      =(SMOD_TOTAL| SMOD_ANNULAR|
                                          SMOD_PARTIAL|SMOD_ANNTOT);
  public static final int SMOD_OCCULT   =256;
  public static final int SMOD_RISE     =512;
  public static final int SMOD_METR     =1024;
  public static final int SMOD_HOCAL    =2048;

  static final String infocmd0 = "\n"+
  "  Sweclips computes solar and lunar eclipses,\n"+
  "  rises, sets, and meridian transits for planets and fixed stars\n"+
  "  Input can either be a date or an absolute julian day number.\n"+
  "\n";

  static final String infocmd1 = "\n"+
  "  Command line options:\n"+
  "        -lunar  lunar eclipse\n"+
  "        -solar  solar eclipse\n"+
  "        -occult occultation by moon\n"+
  "        -local  local solar eclipse\n"+
  "        -annular\n"+
  "        -total\n"+
  "        -partial\n"+
  "        -rise   next rise and set\n"+
  "        -metr   next meridian transits (culminations)\n"+
  "        -lon...   geogr. longitude D.MMSS\n"+
  "        -lat...   geogr. longitude D.MMSS\n"+
  "        -p.       planet (like swetest.c)\n"+
  "        -edirPATH change the directory of the ephemeris files \n"+
  "        -head   don\'t print the header before the planet data. This option\n"+
  "                is useful when you want to paste the output into a\n"+
  "                spreadsheet for displaying graphical ephemeris.\n"+
  "        +head   header before every step (with -s..) \n"+
  "        -bDATE  use this begin date instead of asking; use -b1.1.1992 if\n"+
  "                the begin date string contains blanks; use format -bj2400000.5\n"+
  "                to express the date as absolute Julian day number.\n"+
  "                Note: the date format is day month year (European style).\n"+
  "        -hocal  Astrodienst internal feature\n"+
  "                \n";

  static final String infocmd2 =
  "        -eswe   swiss ephemeris\n"+
  "        -ejpl   jpl ephemeris (DE406), or with ephemeris file name\n"+
  "                -ejplDE200.cdrom \n"+
  "        -emos   moshier ephemeris\n"+
  "        -?, -h  display whole info\n"+
  "        -hcmd   display commands\n"+
  "        -hdate  display input date format\n";

  static final String infodate = "\n"+
  "  Date entry:\n"+
  "  In the interactive mode, when you are asked for a start date,\n"+
  "  you can enter data in one of the following formats:\n"+
  "\n"+
  "        1.2.1991        three integers separated by a nondigit character for\n"+
  "                        day month year. Dates are interpreted as Gregorian\n"+
  "                        after 4.10.1582 and as Julian Calender before.\n"+
  "                        Time is always set to midnight.\n"+
  "                        If the three letters jul are appended to the date,\n"+
  "                        the Julian calendar is used even after 1582.\n"+
  "                        If the four letters greg are appended to the date,\n"+
  "                        the Gregorian calendar is used even before 1582.\n"+
  "\n"+
  "        j2400123.67     the letter j followed by a real number, for\n"+
  "                        the absolute Julian daynumber of the start date.\n"+
  "                        Fraction .5 indicates midnight, fraction .0\n"+
  "                        indicates noon, other times of the day can be\n"+
  "                        chosen accordingly.\n"+
  "        \n";
  /**************************************************************/

  public static final double J2000=2451545.0;  /* 2000 January 1.5 */

  double square_sum(double x[]) { return x[0]*x[0]+x[1]*x[1]+x[2]*x[2]; }


  public static final int BIT_ROUND_SEC=1;
  public static final int BIT_ROUND_MIN=2;
  public static final int BIT_ZODIAC   =4;

  public static final int ECL_LUN_PENUMBRAL    = 1;      /* eclipse types for
                                                            hocal list */
  public static final int ECL_LUN_PARTIAL       =2;
  public static final int ECL_LUN_TOTAL         =3;
  public static final int ECL_SOL_PARTIAL       =4;
  public static final int ECL_SOL_ANNULAR       =5;
  public static final int ECL_SOL_TOTAL         =6;

  SwissEph  sw=new SwissEph();
  SwissLib  sl=new SwissLib();
  SweDate   sd=new SweDate();
  SwissData swed=new SwissData();
  CFmt f=new CFmt();

  static final String zod_nam[] = {"ar", "ta", "ge", "cn", "le", "vi",
                            "li", "sc", "sa", "cp", "aq", "pi"};

  /**
  * This class is not be instantiated, it is supposed to be run via
  * the main method.
  */
  private Sweclips() { }

  /**
  * See -h parameter for help on all parameters.
  */
  public static void main(String argv[]) {
    Sweclips sc=new Sweclips();
    sc.main_start(argv);
  }

  void main_start(String[] argv) {
    String saves;
    String s1, s2;
    StringBuffer serr=new StringBuffer(swed.AS_MAXCH),
                 serr_save=new StringBuffer(swed.AS_MAXCH),
                 serr_warn=new StringBuffer(swed.AS_MAXCH);
    String sout;
    String sp, sp2;
    String fmt = "PLBRS";
    int i, ii;
    int smod = 0;
    int ecl_type = 0;
    int jmon, jday, jyear;
    double jut = 0.0;
    long nstep = 1;
    double x[]=new double[6];
    String ephepath;
    String fname;
    String sdate;
    String begindate = null;
    int iflag = 0;
    long eclflag;
    double geopos[]=new double[20], attr[]=new double[20],
           tret[]=new double[20];
    double a, b, c;
    int whicheph = SweConst.SEFLG_SWIEPH;
    boolean with_header = true;
    boolean gregflag=SweDate.SE_GREG_CAL;
    double tjd = 2415020.5, t_ut;
    double dt;
    int direction = 1;
    boolean direction_flag = false;
    int ipl=SweConst.SE_SUN;
    StringBuffer starname;
    int search_flag = 0;
    String slon, slat;
    serr.setLength(0);
    serr_save.setLength(0);
    serr_warn.setLength(0);
    saves="";
    starname=new StringBuffer();
    ephepath=SweConst.SE_EPHE_PATH;
    fname=SweConst.SE_FNAME_DE406;
    slon="8.33";         /* geographical position of Zurich */
    slat="47.23";
    /*
     * command line
     */
    search_flag = SweConst.SE_ECL_CENTRAL | SweConst.SE_ECL_NONCENTRAL;
    smod = SMOD_SOLAR;
    for (i = 0; i < argv.length; i++) {
      if (argv[i].startsWith("-head")) {
        with_header = false;
      } else if (argv[i].equals("-lunar")) {
        smod |= SMOD_LUNAR;
        smod &= ~SMOD_SOLAR;
      } else if (argv[i].equals("-solar")) {
        smod |= SMOD_SOLAR;
      } else if (argv[i].equals("-occult")) {
        smod |= SMOD_OCCULT;
        smod &= ~SMOD_SOLAR;
        smod &= ~SMOD_LUNAR;
        if (ipl == 1) /* no occultation of moon by moon */
          ipl = 2;
      } else if (argv[i].equals("-hocal")) {
        /* used to create a listing for inclusion in hocal.c source code */
        smod |= SMOD_HOCAL;
      } else if (argv[i].equals("-solar")) {
        smod |= SMOD_SOLAR;
      } else if (argv[i].equals("-how")) {
        smod |= SMOD_HOW;
      } else if (argv[i].equals("-total")) {
        smod |= SMOD_TOTAL;
      } else if (argv[i].equals("-annular")) {
        smod |= SMOD_ANNULAR;
      } else if (argv[i].equals("-anntot")) {
        smod |= SMOD_ANNTOT;
      } else if (argv[i].equals("-partial")) {
        smod |= SMOD_PARTIAL;
      } else if (argv[i].equals("-noncentral")) {
        search_flag &= ~SweConst.SE_ECL_CENTRAL;
        search_flag |= SweConst.SE_ECL_NONCENTRAL;
      } else if (argv[i].equals("-central")) {
        search_flag &= ~SweConst.SE_ECL_NONCENTRAL;
        search_flag |= SweConst.SE_ECL_CENTRAL;
      } else if (argv[i].equals("-local")) {
        smod |= SMOD_LOCAL;
      } else if (argv[i].equals("-rise")) {
        smod |= SMOD_RISE;
        smod &= ~SMOD_SOLAR;
      } else if (argv[i].equals("-metr")) {
        smod |= SMOD_METR;
        smod &= ~SMOD_SOLAR;
      } else if (argv[i].startsWith("-j")) {
        begindate = argv[i].substring(1);
      } else if (argv[i].startsWith("-lon")) {
        slon=argv[i].substring(4);
      } else if (argv[i].startsWith("-lat")) {
        slat=argv[i].substring(4);
      } else if (argv[i].startsWith("-ejpl")) {
        whicheph = SweConst.SEFLG_JPLEPH;
        ephepath = SweConst.SE_EPHE_PATH;
        if (argv[i].length()>5)
          fname=argv[i].substring(5);
      } else if (argv[i].equals("-eswe")) {
        whicheph = SweConst.SEFLG_SWIEPH;
        ephepath = SweConst.SE_EPHE_PATH;
      } else if (argv[i].equals("-emos")) {
        whicheph = SweConst.SEFLG_MOSEPH;
      } else if (argv[i].startsWith("-edir")) {
        if (argv[i].length()>5)
          ephepath=argv[i].substring(5);
      } else if (argv[i].startsWith("-n")) {
        nstep = 0;
        if (argv[i].length()>2) {
          nstep = SwissLib.atoi(argv[i].substring(2));
        }
      } else if (argv[i].equals("-bwd")) {
        direction = -1;
            direction_flag = true;
// JAVA: unklar, was geschehen soll, falls nur "-b" ohne weitere Angabe steht.
// JAVA: Moeglicherweise inkompatibel mit der C Variante
      } else if (argv[i].startsWith("-b") && argv[i].length()>2) {
        begindate = argv[i].substring(2);
      } else if (argv[i].startsWith("-p")) {
        if (argv[i].length() == 3)
          ipl = letter_to_ipl(argv[i].charAt(2));
        else
          starname=new StringBuffer(argv[i].substring(2));
        if ((smod & SMOD_OCCULT) != 0 && ipl == 1)
          ipl = 2; /* no occultation of moon by moon */
      } else if (argv[i].startsWith("-h")
        || argv[i].startsWith("-?")) {
        char spc=' ';
        if (argv[i].length()>2) {
          spc = argv[i].charAt(2);
        }
        if (spc == 'c' || spc == ' ') {
          do_printf(infocmd0);
          do_printf(infocmd1);
          do_printf(infocmd2);
        }
        if (spc == 'd' || spc == ' ')
          do_printf(infodate);
        sw.swe_close();
        return;
      } else {
        sout="illegal option "+argv[i]+"\n";
        do_printf(sout);
        System.exit(1);
      }
    }
    if ((smod & (SMOD_LUNAR | SMOD_SOLAR | SMOD_OCCULT))!=0)  {
      if ((smod & SMOD_ALL) == 0) /* no selective eclipse type set, set all */
        smod |= SMOD_ALL;
      if ((smod & SMOD_TOTAL)!=0) search_flag |= SweConst.SE_ECL_TOTAL;
      if ((smod & SMOD_ANNULAR)!=0) search_flag |= SweConst.SE_ECL_ANNULAR |
                                                      SweConst.SE_ECL_PENUMBRAL;
      if ((smod & SMOD_PARTIAL)!=0) search_flag |= SweConst.SE_ECL_PARTIAL;
      if ((smod & SMOD_ANNTOT)!=0) search_flag |= SweConst.SE_ECL_ANNULAR_TOTAL;
    }
    if (with_header) {
      for (i = 0; i < argv.length; i++) {
        do_printf(argv[i]);
        do_printf(" ");
      }
      do_printf("\n");
    }
    iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | whicheph;
//  if (strpbrk(fmt, "SsQ") != null)
    if (fmt.indexOf("S")>=0 || fmt.indexOf("s")>=0 || fmt.indexOf("Q")>=0)
      iflag |= SweConst.SEFLG_SPEED;
    String argv0=System.getProperties().getProperty("user.dir");
    if (ephepath.length() > 0)
      sw.swe_set_ephe_path(ephepath);
    else if (make_ephemeris_path(iflag, argv0) == SweConst.ERR) {
      iflag = (iflag & ~SweConst.SEFLG_EPHMASK) | SweConst.SEFLG_MOSEPH;
      whicheph = SweConst.SEFLG_MOSEPH;
    }
    if ((whicheph & SweConst.SEFLG_JPLEPH)!=0)
      sw.swe_set_jpl_file(fname);
    serr.setLength(0); serr_save.setLength(0); serr_warn.setLength(0);
    if (begindate == null) {
      do_printf("\nDate ?");
      sdate="";
//    gets(sdate);
      try {
        InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader bin=new BufferedReader(in);
        sdate=bin.readLine();
      } catch (IOException ie) {
        System.out.println(ie.getMessage());
      }
    } else {
      sdate=begindate;
      begindate = ".";  /* to exit afterwards */
    }
    sp = sdate;
    if (sp.charAt(0) == 'j') {   /* it's a day number */
      sp=sp.replace(',','.');
      tjd=new Double(sp.substring(1)).doubleValue();
      if (tjd < 2299160.5)
        gregflag = SweDate.SE_JUL_CAL;
      else
        gregflag = SweDate.SE_GREG_CAL;
      if (sp.indexOf("jul") >= 0)
        gregflag = SweDate.SE_JUL_CAL;
      else if (sp.indexOf("greg") >= 0)
        gregflag = SweDate.SE_GREG_CAL;
//    swe_revjul(tjd, gregflag, &jyear, &jmon, &jday, &jut);
      sd.setJulDay(tjd);
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JulDay!
      jyear=sd.getYear();
      jmon=sd.getMonth();
      jday=sd.getDay();
      jut=sd.getHour();
    } else {
//    if (sscanf (sp, "%d%*c%d%*c%d", &jday,&jmon,&jyear) < 1) exit(1);
      jday=jmon=jyear=0;
      i=0;
      try {
        while (Character.isDigit(sp.charAt(i))) {
          jday=jday*10+Character.digit(sp.charAt(i++),10);
        }
        while (!Character.isDigit(sp.charAt(i))) { i++; }
        while (Character.isDigit(sp.charAt(i))) {
          jmon=jmon*10+Character.digit(sp.charAt(i++),10);
        }
        while (!Character.isDigit(sp.charAt(i)) && sp.charAt(i)!='-') { i++; }
        boolean neg=(sp.charAt(i)=='-');
        if (neg) { i++; }
        while (i<sp.length() && Character.isDigit(sp.charAt(i))) {
          jyear=jyear*10+Character.digit(sp.charAt(i++),10);
        }
        if (neg) { jyear=-jyear; }
      } catch (ArrayIndexOutOfBoundsException aob) {
        System.exit(1);
      }
      if ((long) jyear * 10000L + (long) jmon * 100L + (long) jday < 15821015L)
        gregflag = SweDate.SE_JUL_CAL;
      else
        gregflag = SweDate.SE_GREG_CAL;
      if (sp.indexOf("jul") >= 0)
        gregflag = SweDate.SE_JUL_CAL;
      else if (sp.indexOf("greg") >= 0)
        gregflag = SweDate.SE_GREG_CAL;
      jut = 0;
//    tjd = swe_julday(jyear,jmon,jday,jut,gregflag);
      sd.setDate(jyear,jmon,jday,jut);
      sd.setCalendarType(gregflag,SweDate.SE_KEEP_DATE); // Keep Date!
      tjd = sd.getJulDay();
    }
    if (with_header) {
      sout="begin date (dmy) "+jday+"."+jmon+"."+jyear+"\n";
      do_printf(sout);
    }
    /*
     * for local eclipses: set geographic position of observer
     */
    if ((smod & (SMOD_LOCAL|SMOD_RISE|SMOD_METR))!=0) {
//    sscanf(slon,"%lf", &a);
      a=new Double(slon).doubleValue();
      b = (a * 10000.)%100.;
      c = a - b / 10000;
      c = (c%1.) * 100.;
      geopos[0] = a - (a%1.) + c / 60 + b / 3600;
//    sscanf(slat,"%lf", &a);
      a=new Double(slat).doubleValue();
      b = (a * 10000.%100.);
      c = a - b / 10000;
      c = (c%1.) * 100;
      geopos[1] = a - (a%1.) + c / 60 + b / 3600;
      geopos[2] = 0;
      sw.swe_set_topo(geopos[0], geopos[1], geopos[2]);
    }
    t_ut = tjd;
    for (ii = 0; ii < nstep; ii++) {
      sout = "";
//#ifndef ASTROLOGY
      if ((smod & SMOD_LUNAR)!=0 && (smod & SMOD_HOW)!=0) {
        if ((eclflag = sw.swe_lun_eclipse_how(t_ut, whicheph, geopos,
                                              attr, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        } else {
          ecl_type = 0;
          if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
            sout="total lunar eclipse: "+f.fmt("%f",attr[0])+" o/o \n";
            ecl_type = ECL_LUN_TOTAL;
          } else if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
            sout="partial lunar eclipse: "+f.fmt("%f",attr[0])+" o/o \n";
            ecl_type = ECL_LUN_PARTIAL;
          } else if ((eclflag & SweConst.SE_ECL_PENUMBRAL)!=0) {
            sout="penumbral lunar eclipse: "+f.fmt("%f",attr[0])+" o/o \n";
            ecl_type = ECL_LUN_PENUMBRAL;
          } else {
            sout="no lunar eclipse \n";
          }
          do_printf(sout);
        }
      }
      if ((smod & SMOD_LUNAR)!=0 && (smod & SMOD_HOW)==0) {
        if ((eclflag = sw.swe_lun_eclipse_when(t_ut, whicheph, search_flag,
                                 tret, direction_flag?-1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        }
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_LUN_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_PENUMBRAL)!=0) {
          sout="penumb. ";
          ecl_type = ECL_LUN_PENUMBRAL;
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_LUN_PARTIAL;
        }
        sout+="lunar eclipse ";
//      swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t_ut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD);
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        if ((eclflag = sw.swe_lun_eclipse_how(t_ut, whicheph, geopos,
                                              attr, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        }
        sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
                              "\t"+hms(jut,0)+"\t"+f.fmt("%f",attr[0])+" o/o\n";
        /* eclipse times, penumbral, partial, total begin and end */
	  sout+="  "+hms_from_tjd(tret[6])+" ";
          if (tret[2] != 0)
	    sout+=hms_from_tjd(tret[2])+" ";
          else
            sout+="   -         ";
          if (tret[4] != 0)
	    sout+=hms_from_tjd(tret[4])+" ";
          else
            sout+="   -         ";
          if (tret[5] != 0)
	    sout+=hms_from_tjd(tret[5])+" ";
          else
            sout+="   -         ";
          if (tret[3] != 0)
	    sout+=hms_from_tjd(tret[3])+" ";
          else
            sout+="   -         ";
	  sout+=hms_from_tjd(tret[7])+"\n";
        if ((smod & SMOD_HOCAL)!=0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(),
                 isgn=new IntObj();     
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN,
                           ihou, imin, isec, dfrc, isgn);  
          sout=f.fmt("\"%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+
               f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+
               f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        } 
        do_printf(sout);
      }
      if ((smod & SMOD_SOLAR)!=0 && (smod & SMOD_LOCAL)!=0) {
        if ((eclflag = sw.swe_sol_eclipse_when_loc(t_ut, whicheph, geopos, tret,
                                attr, direction_flag?-1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        } else {
	  boolean has_found = false;
          t_ut = tret[0];
          if ((smod & SMOD_TOTAL)!=0 && (eclflag & SweConst.SE_ECL_TOTAL)!=0) {
            sout="total   ";
	    has_found = true;
            ecl_type = ECL_SOL_TOTAL;
	  }
          if ((smod & SMOD_ANNULAR)!=0 &&
              (eclflag & SweConst.SE_ECL_ANNULAR)!=0) {
            sout="annular ";
	    has_found = true;
            ecl_type = ECL_SOL_ANNULAR;
	  }
          if ((smod & SMOD_PARTIAL)!=0 &&
              (eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
            sout="partial ";
	    has_found = true;
            ecl_type = ECL_SOL_PARTIAL;
	  }
	  if (!has_found) {
	    ii--;
	  } else {
	    i = do_calc(t_ut + sd.getDeltaT(t_ut), SweConst.SE_ECL_NUT, 0,
                        x, serr);
//	  swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
            sd.setJulDay(t_ut);
            sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
            jyear=sd.getYear();
            jmon=sd.getMonth();
            jday=sd.getDay();
            jut=sd.getHour();
	    sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+
                  f.fmt("%4d",jyear)+"\t"+hms(jut,0)+"\t"+f.fmt("%f",attr[0])+
                  "o/o\n";
	    dt = (tret[3] - tret[2]) * 24 * 60;
	    sout+="\t"+(int) dt+" min "+f.fmt("%4.2f",(dt%1.)*60.)+" sec\t";
	    if ((eclflag & SweConst.SE_ECL_1ST_VISIBLE)!=0)
	      sout+=hms_from_tjd(tret[1])+" ";
	    else
	      sout+="   -         ";
	    if ((eclflag & SweConst.SE_ECL_2ND_VISIBLE)!=0)
	      sout+=hms_from_tjd(tret[2])+" ";
	    else
	      sout+="   -         ";
	    if ((eclflag & SweConst.SE_ECL_3RD_VISIBLE)!=0)
	      sout+=hms_from_tjd(tret[3])+" ";
	    else
	      sout+="   -         ";
	    if ((eclflag & SweConst.SE_ECL_4TH_VISIBLE)!=0)
	      sout+=hms_from_tjd(tret[4])+" ";
	    else
	      sout+="   -         ";
	    sout+="\n";
	    do_printf(sout);
	  }
        }
      }   /* endif search_local */
      if ((smod & SMOD_OCCULT) != 0 && (smod & SMOD_LOCAL) != 0) {
        if ((eclflag = sw.swe_lun_occult_when_loc(t_ut, ipl, starname, whicheph, geopos, tret, attr, direction_flag?-1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        } else {
          boolean has_found = false;
          t_ut = tret[0];
          if ((smod & SMOD_TOTAL) != 0 && (eclflag & SweConst.SE_ECL_TOTAL) != 0) {
            sout="total   ";
            has_found = true;
            ecl_type = ECL_SOL_TOTAL;
          }
          if ((smod & SMOD_ANNULAR) != 0 && (eclflag & SweConst.SE_ECL_ANNULAR) != 0) {
            sout="annular ";
            has_found = true;
            ecl_type = ECL_SOL_ANNULAR;
          }
          if ((smod & SMOD_PARTIAL) != 0 && (eclflag & SweConst.SE_ECL_PARTIAL) != 0) {
            sout="partial ";
            has_found = true;
            ecl_type = ECL_SOL_PARTIAL;
          }
          if (!has_found) {
            ii--;
          } else {
            i = do_calc(t_ut + sd.getDeltaT(t_ut), SweConst.SE_ECL_NUT, 0, x, serr);
//      swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
            sd.setJulDay(t_ut);
            sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
            jyear=sd.getYear();
            jmon=sd.getMonth();
            jday=sd.getDay();
            jut=sd.getHour();
            sout += f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+"\t"+hms(jut,0)+"\t"+f.fmt("%f",attr[0])+"o/o\n";
            dt = (tret[3] - tret[2]) * 24 * 60;
            sout += "\t"+(int) dt+" min "+f.fmt("%4.2f",(dt%1) * 60)+" sec\t";
            if ((eclflag & SweConst.SE_ECL_1ST_VISIBLE)!=0)
              sout += hms_from_tjd(tret[1]) + " ";
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_2ND_VISIBLE)!=0)
              sout += hms_from_tjd(tret[2]) + " ";
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_3RD_VISIBLE)!=0)
              sout += hms_from_tjd(tret[3]) + " ";
            else
              sout+="   -         ";
            if ((eclflag & SweConst.SE_ECL_4TH_VISIBLE)!=0)
              sout += hms_from_tjd(tret[4]) + " ";
            else
              sout+="   -         ";
//#if 0
//          sprintf(sout + strlen(sout), "\t%d min %4.2f sec   %s %s %s %s",
//                  (int) dt, fmod(dt, 1) * 60,
//                  strcpy(s1, hms(fmod(tret[1] + 0.5, 1) * 24, 0)),
//                  strcpy(s3, hms(fmod(tret[2] + 0.5, 1) * 24, 0)),
//                  strcpy(s4, hms(fmod(tret[3] + 0.5, 1) * 24, 0)),
//                  strcpy(s2, hms(fmod(tret[4] + 0.5, 1) * 24, 0)));
//#endif
            sout+="\n";
            do_printf(sout);
          }
        }
      }   /* endif search_local */
      if ((smod & SMOD_SOLAR)!=0 && (smod & SMOD_LOCAL)==0) {
      /* * global search for eclipses */
        if ((eclflag = sw.swe_sol_eclipse_when_glob(t_ut, whicheph, search_flag,
                                tret, direction_flag?-1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        }
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_SOL_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR)!=0) {
          sout="annular ";
          ecl_type = ECL_SOL_ANNULAR;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR_TOTAL)!=0) {
          sout="ann-tot ";
          ecl_type = ECL_SOL_ANNULAR;     /* by Alois: what is this ? */
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_SOL_PARTIAL;
        }
        if ((eclflag & SweConst.SE_ECL_NONCENTRAL)!=0 &&
            (eclflag & SweConst.SE_ECL_PARTIAL)==0) {
          sout+="non-central ";
        }
        sw.swe_sol_eclipse_where(t_ut, whicheph, geopos, attr, serr);
//      swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t_ut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
              "\t"+hms(jut,0)+"\t"+f.fmt("%f",attr[3])+" km\t"+
              f.fmt("%f",attr[0])+" o/o\n";
        sout+="\t"+hms_from_tjd(tret[2])+" ";
        if (tret[4] != 0)
	    sout+=hms_from_tjd(tret[4])+" ";
        else
            sout+="   -         ";
        if (tret[5] != 0)
	    sout+=hms_from_tjd(tret[5])+" ";
        else
            sout+="   -         ";
//#if 0   
//      swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
//      sprintf(sout + strlen(sout), "%2d.%2d.%4d\t%s\t%f km\n", 
//                jday, jmon, jyear, hms(jut,0), attr[3]);
//#endif  
        sout+=hms_from_tjd(tret[3])+"\n";
        s1=dms(geopos[0], BIT_ROUND_MIN);
        s2=dms(geopos[1], BIT_ROUND_MIN);
        sout+="\t"+s1+"\t"+s2;
        if ((eclflag & SweConst.SE_ECL_PARTIAL)==0 &&
            (eclflag & SweConst.SE_ECL_NONCENTRAL)==0) {
          if ((eclflag = sw.swe_sol_eclipse_when_loc(t_ut - 10, whicheph,
                           geopos, tret, attr, 0, serr)) == SweConst.ERR) {
            do_printf(serr);
            System.exit(0);
          }
          if (SMath.abs(tret[0] - t_ut) > 1)
            do_printf("when_loc returns wrong date\n");
          dt = (tret[3] - tret[2]) * 24 * 60;
          sout+="\t"+(int) dt+" min "+f.fmt("%4.2f",(dt%1.)*60.)+" sec\t";
        }
        sout+="\n";
        if ((smod & SMOD_HOCAL)!=0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(),
                 isgn=new IntObj();
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN,
                           ihou, imin, isec, dfrc, isgn);
          sout="\""+f.fmt("%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+
               f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+
               f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        }
        do_printf(sout);
      }
      if ((smod & SMOD_OCCULT) != 0 && (smod & SMOD_LOCAL) == 0) {
      /* * global search for eclipses */
        if ((eclflag = sw.swe_lun_occult_when_glob(t_ut, ipl, starname, whicheph, search_flag, tret, direction_flag?-1:0, serr)) == SweConst.ERR) {
          do_printf(serr);
          System.exit(0);
        }
        t_ut = tret[0];
        if ((eclflag & SweConst.SE_ECL_TOTAL)!=0) {
          sout="total   ";
          ecl_type = ECL_SOL_TOTAL;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR)!=0) {
          sout="annular ";
          ecl_type = ECL_SOL_ANNULAR;
        }
        if ((eclflag & SweConst.SE_ECL_ANNULAR_TOTAL)!=0) {
          sout="ann-tot ";
          ecl_type = ECL_SOL_ANNULAR;     /* by Alois: what is this ? */
        }
        if ((eclflag & SweConst.SE_ECL_PARTIAL)!=0) {
          sout="partial ";
          ecl_type = ECL_SOL_PARTIAL;
        }
        if ((eclflag & SweConst.SE_ECL_NONCENTRAL) != 0 && (eclflag & SweConst.SE_ECL_PARTIAL) == 0)
          sout+="non-central ";
        sw.swe_lun_occult_where(t_ut, ipl, starname, whicheph, geopos, attr, serr);
//        swe_revjul(t_ut, gregflag, &jyear, &jmon, &jday, &jut);
        sd.setJulDay(t_ut);
        sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
        jyear=sd.getYear();
        jmon=sd.getMonth();
        jday=sd.getDay();
        jut=sd.getHour();
        sout += f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+"\t"+hms(jut,0)+"\t"+f.fmt("%f",attr[3])+" km\t"+f.fmt("%f",attr[0])+" o/o\n";
        sout += "\t"+hms_from_tjd(tret[2])+" ";
        if (tret[4] != 0)
          sout += hms_from_tjd(tret[4])+" ";
        else
            sout+="   -         ";
        if (tret[5] != 0)
          sout += hms_from_tjd(tret[5])+" ";
        else
          sout+="   -         ";
        sout += hms_from_tjd(tret[3])+"\n";
//#if 0
//      swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
//      sprintf(sout + strlen(sout), "%2d.%2d.%4d\t%s\t%f km\n",
//                jday, jmon, jyear, hms(jut,0), attr[3]);
//#endif
        s1=dms(geopos[0], BIT_ROUND_MIN);
        s2=dms(geopos[1], BIT_ROUND_MIN);
        sout += "\t"+s1+"\t"+s2;
        if ((eclflag & SweConst.SE_ECL_PARTIAL)==0 && (eclflag & SweConst.SE_ECL_NONCENTRAL)==0) {
          if ((eclflag = sw.swe_lun_occult_when_loc(t_ut - 10, ipl, starname, whicheph, geopos, tret, attr, 0, serr)) == SweConst.ERR) {
            do_printf(serr);
            System.exit(0);
          }
          if (SMath.abs(tret[0] - t_ut) > 1)
            do_printf("when_loc returns wrong date\n");
          dt = (tret[3] - tret[2]) * 24 * 60;
          sout += "\t"+(int) dt+" min "+f.fmt("%4.2f",(dt%1.)*60)+" sec\t";
        }
        sout+="\n";
        if ((smod & SMOD_HOCAL)!=0) {
          IntObj ihou=new IntObj(), imin=new IntObj(), isec=new IntObj(), isgn=new IntObj();
          DblObj dfrc=new DblObj();
          sl.swe_split_deg(jut, SweConst.SE_SPLIT_DEG_ROUND_MIN, ihou, imin, isec, dfrc, isgn);
          sout="\""+f.fmt("%04d",jyear)+" "+f.fmt("%02d",jmon)+" "+f.fmt("%02d",jday)+" "+f.fmt("%02d",ihou.val)+"."+f.fmt("%02d",imin.val)+" "+f.fmt("%d",ecl_type)+"\",\n";
        }
        do_printf(sout);
      }
//#endif /* ASTROLOGY */
      DblObj dtretx=new DblObj();
      StringBuffer sbstar=new StringBuffer(starname.toString());
//#ifndef ASTROLOGY
      if ((smod & SMOD_RISE)!=0) {
//#endif /* ASTROLOGY */
        dtretx.val=tret[0];
        if (sw.swe_rise_trans(t_ut, ipl, sbstar, whicheph,
                              SweConst.SE_CALC_RISE, geopos, 1013.25, 10.,
                              dtretx, serr) != SweConst.OK) {
          tret[0]=dtretx.val;
          do_printf(serr);
          System.exit(0);
        }
        tret[0]=dtretx.val;
        dtretx.val=tret[1];
        if (sw.swe_rise_trans(t_ut, ipl, sbstar, whicheph, SweConst.SE_CALC_SET,
                          geopos, 1013.25, 10., dtretx, serr) != SweConst.OK) {
          tret[1]=dtretx.val;
          do_printf(serr);
          System.exit(0);
        }
        tret[1]=dtretx.val;
        sout="rise     ";
        if (tret[0] == 0) sout+="         -                     ";
        else {
//          swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[0]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
          sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
                "\t"+hms(jut,0)+"    ";
        }
        sout+="set      ";
        if (tret[1] == 0) sout+="         -                     \n";
        else {
//          swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[1]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
          sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
                "\t"+hms(jut,0)+"\n";
        }
        do_printf(sout);
      }
//#ifndef ASTROLOGY
      if ((smod & SMOD_METR)!=0) {
        dtretx.val=tret[0];
        if (sw.swe_rise_trans(t_ut, ipl, sbstar, whicheph,
                              SweConst.SE_CALC_MTRANSIT, geopos, 1013.25, 10.,
                              dtretx, serr) != SweConst.OK) {
          tret[0]=dtretx.val;
          do_printf(serr);
          System.exit(0);
        }
        tret[0]=dtretx.val;
        dtretx.val=tret[1];
        if (sw.swe_rise_trans(t_ut, ipl, sbstar, whicheph,
                              SweConst.SE_CALC_ITRANSIT, geopos, 1013.25, 10.,
                              dtretx, serr) != SweConst.OK) {
          tret[1]=dtretx.val;
          do_printf(serr);
          System.exit(0);
        }
        tret[1]=dtretx.val;
        sout+="mtransit ";
        if (tret[0] == 0) sout+="         -                     ";
        else {
//          swe_revjul(tret[0], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[0]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
          sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
                "\t"+hms(jut,0)+"    ";
        }
        sout+="itransit ";
        if (tret[1] == 0) sout+="         -                     \n";
        else {
//          swe_revjul(tret[1], gregflag, &jyear, &jmon, &jday, &jut);
          sd.setJulDay(tret[1]);
          sd.setCalendarType(gregflag,SweDate.SE_KEEP_JD); // Keep JD!
          jyear=sd.getYear();
          jmon=sd.getMonth();
          jday=sd.getDay();
          jut=sd.getHour();
          sout+=f.fmt("%2d",jday)+"."+f.fmt("%2d",jmon)+"."+f.fmt("%4d",jyear)+
                "\t"+hms(jut,0)+"\n";
        }
        do_printf(sout);
      }
//#endif /* ASTROLOGY */
    if ((smod & (SMOD_RISE | SMOD_METR))!=0 && tret[1] > 0)
      t_ut = tret[1] + 0.1;
    else
      t_ut += direction;
    }
    if (serr_warn.length() > 0) {
      do_printf("\nwarning: ");
      do_printf(serr_warn);
      do_printf("\n");
    }
    /* close open files and free allocated space */
//  end_main:
    sw.swe_close();
    return;
  }

  String hms_from_tjd(double x) {
    String s;
    s=hms(((x + 1000000.5)%1.) * 24, 0)+" ";
    return s;
  }

  String hms(double x, int iflag) {
    String s;
    int sp;
    String c = swed.ODEGREE_CHAR;
    x += 0.5 / 36000.0; /* round to 0.1 sec */
    s=dms(x, iflag);
    sp=s.indexOf(c);
    if (sp>=0) {
      s=s.substring(0,sp)+":"+s.substring(sp+1,sp+3)+":"+s.substring(sp+3+1);
      s=s.substring(0,sp+8);
    }
    return s;
  }

  String dms(double x, long iflag) {
    int izod;
    long k, kdeg, kmin, ksec;
    String c = swed.ODEGREE_CHAR;
    String sp, s1;
    String s;
    int sgn;
    s = "";
    if ((iflag & SweConst.SEFLG_EQUATORIAL)!=0)
      c = "h";
    if (x < 0) {
      x = -x;
      sgn = -1;
    } else
      sgn = 1;
    if ((iflag & BIT_ROUND_MIN)!=0)
      x += 0.5/60;
    if ((iflag & BIT_ROUND_SEC)!=0)
      x += 0.5/3600;
    if ((iflag & BIT_ZODIAC)!=0) {
      izod = (int) (x / 30);
      x = x%30.;
      kdeg = (long) x;
      s=f.fmt("%2ld",kdeg)+" "+zod_nam[izod]+" ";
    } else {
      kdeg = (long) x;
      s=" "+f.fmt("%3ld", kdeg)+c;
    }
    x -= kdeg;
    x *= 60;
    kmin = (long) x;
    if ((iflag & BIT_ZODIAC)!=0 && (iflag & BIT_ROUND_MIN)!=0)
      s1=f.fmt("%2ld", kmin);
    else
      s1=f.fmt("%2ld", kmin)+"'";
    s+=s1;
    if ((iflag & BIT_ROUND_MIN)!=0)
      return return_dms(sgn,s);
    x -= kmin;
    x *= 60;
    ksec = (long) x;
    if ((iflag & BIT_ROUND_SEC)!=0)
      s1=f.fmt("%2ld", ksec)+"\"";
    else
      s1=f.fmt("%2ld", ksec);
    s+=s1;
    if ((iflag & BIT_ROUND_SEC)!=0)
      return return_dms(sgn,s);
    x -= ksec;
    k = (long) (x * 10000);
    s1="."+f.fmt("%04ld", k);
    s+=s1;
    return(s);
  }

  String return_dms(int sgn, String s) {
    if (sgn < 0) {
      for (int i=0; i<s.length();i++) {
        if (Character.isDigit(s.charAt(i))) {
          s=s.substring(0,i-1)+"-"+s.substring(i);
          break;
        }
      }
    }
    return(s);
  }

  /* make_ephemeris_path().
   * ephemeris path includes
   *   current working directory
   *   + program directory
   *   + default path from swephexp.h on current drive
   *   +                              on program drive
   *   +                              on drive C:
   */
  int make_ephemeris_path(int iflag, String argv0) {
    String path="", s="";
    int sp;
    String dirglue = swed.DIR_GLUE;
    int pathlen=0;
    /* moshier needs no ephemeris path */
    if ((iflag & SweConst.SEFLG_MOSEPH)!=0)
      return SweConst.OK;
    /* current working directory */
    path="."+swed.PATH_SEPARATOR;
    /* program directory */
    sp = argv0.lastIndexOf(dirglue);
    if (sp >= 0) {
      pathlen = sp;
      if (path.length() + pathlen < swed.AS_MAXCH-1) {
        s=argv0.substring(0,pathlen);
        path+=s+swed.PATH_SEPARATOR;
      }
    }
    if (path.length() + pathlen < swed.AS_MAXCH-1)
      path+=SweConst.SE_EPHE_PATH;
    return SweConst.OK;
  }

  int letter_to_ipl(char letter) {
    if (letter >= '0' && letter <= '9')
      return (int)letter - '0' + SweConst.SE_SUN;
    if (letter >= 'A' && letter <= 'I')
      return (int)letter - 'A' + SweConst.SE_MEAN_APOG;
    if (letter >= 'J' && letter <= 'X')
      return (int)letter - 'J' + SweConst.SE_CUPIDO;
    switch ((int)letter) {
      case (int)'m': return SweConst.SE_MEAN_NODE;
      case (int)'n':
      case (int)'o': return SweConst.SE_ECL_NUT;
      case (int)'t': return SweConst.SE_TRUE_NODE;
      case (int)'f': return SweConst.SE_FIXSTAR;
    }
    return -1;
  }

  int do_calc(double tjd, int ipl, int iflag, double[] x, StringBuffer serr) {
    return sw.swe_calc(tjd, ipl, iflag, x, serr);
  }

  void do_printf(StringBuffer info) {
    System.out.print(info.toString());
  }
  void do_printf(String info) {
    System.out.print(info);
  }
} // End of class Sweclips
