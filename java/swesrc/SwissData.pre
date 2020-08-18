//#ifdef J2ME
//#define JAVAME
//#endif /* J2ME */
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 2.00.00
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
package swisseph;

public class SwissData
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  /**
  * The character to be used as the degree character. Only textmode
  * applications (and probably awt applications in Java 1.1 and below?)
  * will require to differentiate between different characters, awt&nbsp;/
  * swing components of Java 1.2 and above will use the unicode encoding
  * always!
  */
//  public String ODEGREE_CHAR=""+'\u00b0'; // Unicode degree character 176
//                                          // Identical in most ISO-8859 sets
  public String ODEGREE_STRING="°";	/* degree as string, utf8 encoding */


  public static final String ayanamsa_name[] = {
     "Fagan/Bradley",
     "Lahiri",
     "De Luce",
     "Raman",
     "Ushashashi",
     "Krishnamurti",
     "Djwhal Khul",
     "Yukteshwar",
     "J.N. Bhasin",
     "Babylonian/Kugler 1",
     "Babylonian/Kugler 2",
     "Babylonian/Kugler 3",
     "Babylonian/Huber",
     "Babylonian/Eta Piscium",
     "Babylonian/Aldebaran = 15 Tau",
     "Hipparchos",
     "Sassanian",
     "Galact. Center = 0 Sag",
     "J2000",
     "J1900",
     "B1950",
     "Suryasiddhanta",
     "Suryasiddhanta, mean Sun",
     "Aryabhata",
     "Aryabhata, mean Sun",
     "SS Revati",
     "SS Citra",
     "True Citra",
     "True Revati",
     "True Pushya",
  };

//////////////////////////////////////////////////////////////////////////////
// sweodef.h: ////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  public static final int AS_MAXCH=256; // used for string declarations,
                                        // allowing 255 char+\0

  static final double DEGTORAD=0.0174532925199433;
  static final double RADTODEG=57.2957795130823;

  static final int DEG=360000;  // degree expressed in centiseconds
  static final int DEG7_30=2700000;	// 7.5 degrees
  static final int DEG15=15 * DEG;
  static final int DEG24=24 * DEG;
  static final int DEG30=30 * DEG;
  static final int DEG60=60 * DEG;
  static final int DEG90=90 * DEG;
  static final int DEG120=120 * DEG;
  static final int DEG150=150 * DEG;
  static final int DEG180=180 * DEG;
  static final int DEG270=270 * DEG;
  static final int DEG360=360 * DEG;

  static final double CSTORAD=4.84813681109536E-08; // centisec to rad:
                                                    // pi / 180 /3600/100
  static final double RADTOCS=2.06264806247096E+07; // rad to centisec
                                                    // 180*3600*100/pi

  static final double CS2DEG=1.0/360000.0;	     // centisec to degree

  static final String BFILE_R_ACCESS="r";  // open binary file for reading
  static final String BFILE_RW_ACCESS="r+";// open binary file for writing and reading
  static final String BFILE_W_CREATE="w";  // create/open binary file for write
  static final String BFILE_A_ACCESS="a+"; // create/open binary file for append
  static final String FILE_R_ACCESS="r";   // open text file for reading
  static final String FILE_RW_ACCESS="r+"; // open text file for writing and reading
  static final String FILE_W_CREATE="w";   // create/open text file for write
  static final String FILE_A_ACCESS="a+";  // create/open text file for append
  static final int O_BINARY=0;	           // for open(), not defined in Unix
  static final int OPEN_MODE=0666;         // default file creation mode
  // file.separator may be null with JavaME
  public static final String DIR_GLUE = (System.getProperty("file.separator") == null ? "/" : System.getProperty("file.separator"));              // glue string for directory/file
  public static final String PATH_SEPARATOR=";:"; // semicolon or colon may be used


//////////////////////////////////////////////////////////////////////////////
// swephexp.h: ///////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  public static final int SE_NSIDM_PREDEF         =30;

//  static final int SE_MAX_STNAME=20;    // maximum size of fixstar name;
//                                        // the parameter star in swe_fixstar
//					// must allow twice this space for
//				        // the returned star name.
//

  static final int pnoext2int[] = {SwephData.SEI_SUN, SwephData.SEI_MOON,
    SwephData.SEI_MERCURY, SwephData.SEI_VENUS, SwephData.SEI_MARS,
    SwephData.SEI_JUPITER, SwephData.SEI_SATURN, SwephData.SEI_URANUS,
    SwephData.SEI_NEPTUNE, SwephData.SEI_PLUTO, 0, 0, 0, 0, SwephData.SEI_EARTH,
    SwephData.SEI_CHIRON, SwephData.SEI_PHOLUS, SwephData.SEI_CERES,
    SwephData.SEI_PALLAS, SwephData.SEI_JUNO, SwephData.SEI_VESTA, };

//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
  boolean ephe_path_is_set=false;	/* ephe_path_is_set = FALSE */
//#ifndef JAVAME
  boolean jpl_file_is_open=false;	/* jpl_file_is_open = FALSE */
  FilePtr fixfp=null;			/* fixfp, fixed stars file pointer */
//#endif /* JAVAME */
//#ifdef PRELOAD_FIXSTARS
  java.util.Hashtable fixstarsHash = null;
//#endif /* PRELOAD_FIXSTARS */
  String ephepath = SweConst.SE_EPHE_PATH;	/* ephepath, ephemeris path */
//#ifndef JAVAME
  String jplfnam = SweConst.SE_FNAME_DFT;	/* jplfnam, JPL file name, default */
//#endif /* JAVAME */
  int jpldenum = 0;			/* jpldenum */
  double eop_tjd_beg;
  double eop_tjd_beg_horizons;
  double eop_tjd_end;
  double eop_tjd_end_add;
  int eop_dpsi_loaded;
  boolean geopos_is_set=false;		/* geopos_is_set, for topocentric */
  boolean ayana_is_set=false;		/* ayana_is_set, ayanamsa is set */
  boolean is_old_starfile=false;	/* is_old_starfile, fixstars.cat is used (default is sefstars.txt) */

  FileData fidat[] = new FileData[SwephData.SEI_NEPHFILES];
  GenConst gcdat;
  PlanData pldat[] = new PlanData[SwephData.SEI_NPLANETS];
//#if 0
  NodeData nddat[] = new NodeData[SwephData.SEI_NNODE_ETC];
//#else
  PlanData nddat[] = new PlanData[SwephData.SEI_NNODE_ETC];
//#endif /* 0 */
  SavePositions savedat[] = new SavePositions[SweConst.SE_NPLANETS+1];
  Epsilon oec, oec2000;
  Nut nut, nut2000, nutv;
  TopoData topd;
  SidData sidd;
  String astelem;
  double ast_G, ast_H, ast_diam;
  int i_saved_planet_name;
  String saved_planet_name;
  //double dpsi[36525];  /* works for 100 years after 1962 */
  //double deps[36525];
  double[] dpsi;
  double[] deps;
  int astro_models[] = new int[SwephData.SEI_NMODELS];
  int timeout;

  /**
  * Constructs a new SwissData object.
  */
  public SwissData() {
//#ifdef TRACE0
    Trace.level++;
    Trace.log("SwissData()");
//#endif /* TRACE0 */
    int i;
    for(i=0;i<SwephData.SEI_NEPHFILES;i++){ fidat[i] = new FileData(); }
    gcdat = new GenConst();
    for(i=0;i<SwephData.SEI_NPLANETS;i++){ pldat[i] = new PlanData(); }
    for(i=0;i<SwephData.SEI_NNODE_ETC;i++){ nddat[i] = new PlanData(); }
    for(i=0;i<SweConst.SE_NPLANETS+1;i++){ savedat[i] = new SavePositions(); }
    oec = new Epsilon();
    oec2000 = new Epsilon();
    nut = new Nut();
    nut2000 = new Nut();
    nutv = new Nut();
    topd = new TopoData();
    sidd = new SidData();
//#ifdef TRACE0
    Trace.level--;
//#endif /* TRACE0 */
  }

//#ifdef TRACE0
  public String toString() {
    String s = "fidat[]: ";
    if (fidat == null) {
      s += "null";
    } else {
      s += fidat.length + " elements: ";
      for ( int z = 0; z < fidat.length; z++) {
        s += fidat[z].toString();
      }
    }
    s += "gcdat: " + gcdat.toString();
    s += "pldat[]: ";
    if (pldat == null) {
      s += "null";
    } else {
      s += pldat.length + " elements: ";
      for ( int z = 0; z < pldat.length; z++) {
        s += pldat[z].toString();
      }
    }
    s += "nddat[]: ";
    if (nddat == null) {
      s += "null";
    } else {
      s += nddat.length + " elements: ";
      for ( int z = 0; z < nddat.length; z++) {
        s += nddat[z].toString();
      }
    }
    s += "savedat[]: ";
    if (savedat == null) {
      s += "null";
    } else {
      s += savedat.length + " elements: ";
      for ( int z = 0; z < savedat.length; z++) {
        s += savedat[z].toString();
      }
    }

    s += "oec: " + oec;
    s += "oec2000: " + oec2000;
    s += "nut: " + nut;
    s += "nut2000: " + nut2000;
    s += "nutv: " + nutv;
    s += "topd: " + topd;
    s += "sidd: " + sidd;
    s += "astelem: " + astelem;
    s += "ast_G: " + ast_G;
    s += "ast_H: " + ast_H;
    s += "ast_diam: " + ast_diam;
    s += "i_saved_planet_name: " + i_saved_planet_name;
    s += "saved_planet_name: " + saved_planet_name;
    return s + "\n";
  }
//#endif /* TRACE0 */
}
