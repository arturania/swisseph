#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifdef TRACE1
#define TRACE0
#endif /* TRACE1 */
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

class FileData
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  final byte SEI_FILE_NMAXPLAN=50;

  String serr_file_damage = "Ephemeris file %s is damaged. ";

  String fnam;          /* ephemeris file name */
  int fversion;         /* version number of file */
  String astnam;        /* asteroid name, if asteroid file */
  int sweph_denum;     /* DE number of JPL ephemeris, which this file
                         * is derived from. */
#ifndef JAVAME
  FilePtr fptr = null;/* ephemeris file pointer */
#endif /* JAVAME */
  double tfstart = 1;       /* file may be used from this date */
  double tfend = 0;         /*      through this date          */
  int iflg;             /* byte reorder flag and little/bigendian flag */
  short npl;            /* how many planets in file */
  int ipl[] = new int[SEI_FILE_NMAXPLAN]; /* planet numbers */

  void clearData() {
#ifdef TRACE0
    Trace.level++;
    Trace.log("FileData.clearData()");
#endif /* TRACE0 */
    int j;
    fnam="";
    fversion=0;
    astnam="";
    sweph_denum=0;
#ifndef JAVAME
    try {
      if (fptr!=null) { fptr.close(); }
    } catch (java.io.IOException e) {
// NBT
    }
    fptr=null;
#endif /* JAVAME */
    tfstart=1.0;
    tfend=0.0;
    iflg=0;
    npl=0;
    for(j=0; j<SEI_FILE_NMAXPLAN; j++) { ipl[j]=0; }
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
  }

#ifndef JAVAME
// Attention: read_const() has to be called from "swed.fidat[ifno]", where
// "ifno" has to be the first parameter in read_const:
//  struct FileData *fdp = &swed.fidat[ifno];
//
  /* SWISSEPH
   * reads constants on ephemeris file
   * ifno         file #
   * serr         error string
   */
  int read_const(int ifno, StringBuffer serr, SwissData swed) {
#ifdef TRACE0
    Trace.level++;
    Trace.log("FileData.read_const(int, StringBuffer, SwissData)");
#ifdef TRACE1
    Trace.log("ifno: " + ifno + "\n    serr: " + serr.toString() + "\n    swed: " + swed);
#endif /* TRACE1 */
#endif /* TRACE0 */
    String s="";
    String s2="";
    String sastnam="";
    int i, ipli, kpl;
    int fendian, freord;
    int lastnam = 19;
//Renamed in JAVA to fptr:// FilePtr fp;
    long lng;
    long ulng; // hat 'unsigned' long hier eine wesentliche Bedeutung?
    long flen, fpos;
    short nplan;
    PlanData pdp;
//Skipped in JAVA:// FileData fdp = swed.fidat[ifno];
    int nbytes_ipl = 2;

    try {
      /*************************************
       * version number of file            *
       *************************************/
      byte b=0;
      char cLast;
      do {
        cLast=(char)b;
        b=fptr.readByte();
        s+=(char)b;
      } while (cLast!='\r' && (char)(b)!='\n' && s.length()<SwissData.AS_MAXCH);

      s=s.trim();
      int offs=0;
      int ver=-1;
      while (!Character.isDigit(s.charAt(offs))) { offs++; }
      try {
        ver=Integer.parseInt(s.substring(offs));
      } catch (NumberFormatException n) {
        label_file_damage(serr, " (1)");
#ifdef TRACE0
        Trace.level--;
#endif /* TRACE0 */
        throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
            SweConst.ERR, serr);
      }
// Use SwissLib.atoi(...) ?
      fversion=ver;

      /*************************************
       * correct file name?                *
       *************************************/
      b=0; s="";
      do {
        cLast=(char)b;
        b=fptr.readByte();
        s+=(char)b;
      } while (cLast!='\r' && (char)(b)!='\n' && s.length()<SwissData.AS_MAXCH);

      s2=fnam.substring(fnam.lastIndexOf(swed.DIR_GLUE)+1).toLowerCase();
      s=s.trim().toLowerCase();
      if (!s.equals(s2)) {
        // Http addresses will end with '/' independent of DIR_GLUE...
        s2=fnam.substring(fnam.lastIndexOf("/")+1).toLowerCase();
        if (!s.equals(s2)) {
          s2=fnam.substring(fnam.lastIndexOf(swed.DIR_GLUE)+1).toLowerCase();
          if (serr != null) {
            serr.setLength(0);
            serr.append("Ephemeris file name '"+s2+"' is wrong; rename to '"+s+"'");
          }
          clearData();
#ifdef TRACE0
          Trace.level--;
#endif /* TRACE0 */
          throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
              SweConst.ERR, serr);
        }
      }
      /*************************************
       * copyright                         *
       *************************************/
      b=0; s="";
      do {
        cLast=(char)b;
        b=fptr.readByte();
        s+=(char)b;
      } while (cLast!='\r' && (char)(b)!='\n' && s.length()<SwissData.AS_MAXCH);

      /****************************************
       * orbital elements, if single asteroid *
       ****************************************/
      // Read up to end of line or AS_MAXCH*2 into var. 's':
      if (ifno == SwephData.SEI_FILE_ANY_AST) {
        b=0; s="";
        do {
          cLast=(char)b;
          b=fptr.readByte();
          s+=(char)b;
        } while (cLast!='\r' && (char)(b)!='\n' && s.length()<SwissData.AS_MAXCH*2);
        /* MPC number and name; will be analyzed below:
         * search "asteroid name" */
        String sp = s;
        // Strip leading white space from 'sp':
        while(Character.isWhitespace(sp.charAt(0))) {
          sp = sp.substring(1);
        }
        // Remove leading numbers from 'sp':
        while(Character.isDigit(sp.charAt(0))) {
          sp = sp.substring(1);
        }
        // Skip next character as well:
        sp = sp.substring(1);
        i = s.length() - sp.length();
        sastnam = sp.substring(0,lastnam+i);
        /* save elements, they are required for swe_plan_pheno() */
        swed.astelem = s;
        /* required for magnitude */
        swed.ast_H = SwissLib.atof(s.substring(35 + i));
        swed.ast_G = SwissLib.atof(s.substring(42 + i));
        if (swed.ast_G == 0) swed.ast_G = 0.15;
        /* diameter in kilometers, not always given: */
        s2 = s.substring(51 + i, 58 + i);
        swed.ast_diam = SwissLib.atof(s2);
        if (swed.ast_diam == 0) {
          /* estimate the diameter from magnitude; assume albedo = 0.15 */
          swed.ast_diam = 1329/SMath.sqrt(0.15) * SMath.pow(10, -0.2 * swed.ast_H);
        }
#if 0
        i = 5;
        while (s.charAt(i) != ' ') {
          i++;
        }
        j = i - 5;
        sastnam=s.substring(0,lastnam+i);
        /* save elements, they are required for swe_plan_pheno() */
        swed.astelem=s;
        /* required for magnitude */
        swed.ast_G = SwissLib.atof(s.substring(40 + j));
        swed.ast_H = SwissLib.atof(s.substring(46 + j));
        /* diameter in kilometers, not always given: */
        s2=s.substring(56 + j, 63 + j);
        swed.ast_diam = SwissLib.atof(s2);
#endif /* 0 */
      }
      /*************************************
       * one int32 for test of byte order   *
       *************************************/
      int testendian = fptr.readInt();
      /* is byte order correct?            */
lng = 0;
      if (testendian == SwephData.SEI_FILE_TEST_ENDIAN) {
        freord = SwephData.SEI_FILE_NOREORD;
      } else {
        freord = SwephData.SEI_FILE_REORD;
        lng = ((testendian & 0x000000ff) << 24) +
              ((testendian & 0x0000ff00) << 8 ) +
              ((testendian & 0x00ff0000) >> 8 ) +
              ((testendian & 0xff000000) >> 24);
        if (lng != SwephData.SEI_FILE_TEST_ENDIAN) {
#ifdef TRACE0
          Trace.level--;
#endif /* TRACE0 */
          throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
              SweConst.ERR, "File is damaged: byte ordering info not correct.");
        }
      }
      /* is file bigendian or littlendian?
       * test first byte of test integer, which is highest if bigendian */
      if (SwephData.SEI_FILE_TEST_ENDIAN / 16777216 ==
          (testendian & 0x000000ff)) {
        fendian = SwephData.SEI_FILE_BIGENDIAN;
      } else {
        fendian = SwephData.SEI_FILE_LITENDIAN;
      }
      iflg = freord | fendian;
      /*************************************
       * length of file correct?           *
       *************************************/
      lng=(long)read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian);
      if (lng < 0) { lng &= 0xffffffff; }
      fpos=fptr.getFilePointer();
      flen=fptr.length();
      if (lng!=flen) {
        label_file_damage(serr, " (2)");
#ifdef TRACE0
        Trace.level--;
#endif /* TRACE0 */
        throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
            SweConst.ERR, serr);
      }
//      fptr.seek(flen-1);
      fptr.seek(fpos);	// Not really necessary...
      /**********************************************************
       * DE number of JPL ephemeris which this file is based on *
       **********************************************************/
      sweph_denum = read4(fptr, fpos, false, freord, fendian);
      swed.jpldenum = sweph_denum;
      /*************************************
       * start and end epoch of file       *
       *************************************/
      tfstart=read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      tfend=read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      /*************************************
       * how many planets are in file?     *
       *************************************/
      nplan=read2(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      if (nplan > 256) {
        nbytes_ipl = 4;
        nplan %= 256;
      }
      if (nplan < 1 || nplan > 20) {
        label_file_damage(serr, " (3)");
#ifdef TRACE0
        Trace.level--;
#endif /* TRACE0 */
        throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
            SweConst.ERR, serr);
      }
      npl = nplan;
      /* which ones?                       */
//  retc = do_fread((void *) fdp->ipl, nbytes_ipl, (int) nplan, sizeof(int), fp, SEI_CURR_FPOS,...
//                    target            how many      count      how many
//                                   bytes to read           bytes to write
// nbytes_ipl can be 2 and 4 only...
      if (nbytes_ipl == 2) {
        for(i=0; i<nplan; i++) {
          ipl[i]=(int)(read2(fptr, SwephData.SEI_CURR_FPOS, freord, fendian) & 0xffff);
        }
      } else if (nbytes_ipl == 4) {
        for(i=0; i<nplan; i++) {
          ipl[i]=read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian);
        }
      } else { // Can't be???
        label_file_damage(serr, " (3b)");
      }
      /*************************************
       * asteroid name                     *
       *************************************/
      if (ifno == SwephData.SEI_FILE_ANY_AST) {
        String sastno;
        int j;
        /* name of asteroid is taken from orbital elements record
         * read above */
        j = 4;      /* old astorb.dat had only 4 characters for MPC# */
        while (sastnam.charAt(j) != ' ' && j < 10) {  /* new astorb.dat has 5 */
          j++;
        }
        sastno=sastnam.substring(0,SMath.min(sastnam.length(),j)).trim();
        try {
          i = Integer.parseInt(sastno);
        } catch (NumberFormatException nf) {
          i = 0;
        }
        if (i == ipl[0] - SweConst.SE_AST_OFFSET) {
          /* element record is from bowell database */
          astnam=sastnam.substring(SMath.min(sastnam.length(),j+1),
                                   SMath.min(sastnam.length(),j+1+lastnam));
          /* overread old ast. name field */
          s="";
          for(i=0; i<30; i++) {
            s+=(char)fptr.readByte();
          }
        } else {
          /* older elements record structure: the name
           * is taken from old name field */
          astnam="";
          for(i=0; i<30; i++) {
            astnam+=(char)fptr.readByte();
          }
        }
        astnam=astnam.trim();
      }
      /*************************************
       * check CRC                         *
       *************************************/
      fpos = fptr.getFilePointer();
      /* read CRC from file */
      ulng=(long)(read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian));
      /* read check area from file */
      fptr.seek(0L);
      /* must check that defined length of s is less than fpos */
      if (fpos - 1 > 2 * SwissData.AS_MAXCH) {
        label_file_damage(serr, " (4)");
#ifdef TRACE0
        Trace.level--;
#endif /* TRACE0 */
        throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
            SweConst.ERR, serr);
      }
      b=0; s="";
byte[] ba=new byte[2*SwissData.AS_MAXCH];
      for(i=0;i<fpos;i++) {
        cLast=(char)b;
        b=fptr.readByte();
        ba[i]=b;
        s+=(char)b;
      }
  if ((int)swi_crc32(/*(unsigned char *)*/ ba, (int) fpos) != (int)ulng) {
    label_file_damage(serr, " (5)");
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
    throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
        SweConst.ERR, serr);
  }
      fptr.seek(fpos+4L);
      /*************************************
       * read general constants            *
       *************************************/
      /* clight, aunit, helgravconst, ratme, sunradius
       * these constants are currently not in use */
      swed.gcdat.clight       = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      swed.gcdat.aunit        = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      swed.gcdat.helgravconst = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      swed.gcdat.ratme        = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      swed.gcdat.sunradius    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
      /*************************************
       * read constants of planets         *
       *************************************/
      for (kpl = 0; kpl < npl; kpl++) {
        /* get SEI_ planet number */
        ipli = ipl[kpl];
        if (ipli >= SweConst.SE_AST_OFFSET) {
          pdp = swed.pldat[SwephData.SEI_ANYBODY];
        } else {
          pdp = swed.pldat[ipli];
        }
        pdp.ibdy = ipli;
        /* file position of planet's index */
        pdp.lndx0=(long)(read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian))&0xffffffffL;
        /* flags: helio/geocentric, rotation, reference ellipse */
        pdp.iflg=fptr.readUnsignedByte();
        /* number of chebyshew coefficients / segment  */
        /* = interpolation order +1                    */
        pdp.ncoe=fptr.readUnsignedByte();
        /* rmax = normalisation factor */
        lng=read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian);
        pdp.rmax = (double)lng / 1000.0;
        /* start and end epoch of planetary ephemeris,   */
        /* segment length, and orbital elements          */
        pdp.tfstart  = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.tfend    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.dseg     = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.nndx     = (int) ((pdp.tfend - pdp.tfstart + 0.1) /pdp.dseg);
        pdp.telem    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.prot     = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.dprot    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.qrot     = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.dqrot    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.peri     = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        pdp.dperi    = read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
        /* alloc space for chebyshew coefficients */
        /* if reference ellipse is used, read its coefficients */
        if ((pdp.iflg & SwephData.SEI_FLG_ELLIPSE)!=0) {
          if (pdp.refep != null) { /* if switch to other eph. file */
            pdp.refep = null;
            if (pdp.segp != null) {
              pdp.segp = null; /* array of coefficients of ephemeris segment */
            }
          }
          pdp.refep = new double[2*pdp.ncoe];
          for(i=0;i<2*pdp.ncoe;i++) {
            pdp.refep[i]=read8(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
          }
        }/**/
      }
    } catch (java.io.IOException e) {
      label_file_damage(serr, " (6a)");
      System.out.println(e.getMessage());
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
#ifdef NIO
    } catch (java.nio.BufferUnderflowException e) {
      label_file_damage(serr, " (6b)");
      System.out.println(e.getMessage());
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
#endif /* NIO */
    } catch (Exception e) {
      label_file_damage(serr, " (6c)");
      System.out.println(e);
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
    }
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
    return SweConst.OK;
  }
#endif /* JAVAME */

  private int label_file_damage(StringBuffer serr, String suberror) {
    if (serr != null) {
      serr.setLength(0);
      if (serr_file_damage.length() + fnam.length() < SwissData.AS_MAXCH) {
        serr.append(serr_file_damage.replaceFirst("%s", fnam));
#ifndef ORIGINAL
        serr.append(suberror);
      } else {
        serr.append(serr_file_damage.replaceFirst("%s", fnam)).append(suberror);
#endif /* ORIGINAL */
      }
    }
    clearData();
    System.out.println(serr);
    return SweConst.ERR;
  }

  /*
   * The following C code (by Rob Warnock rpw3@sgi.com) does CRC-32 in
   * BigEndian/BigEndian byte/bit order. That is, the data is sent most
   * significant byte first, and each of the bits within a byte is sent most
   * significant bit first, as in FDDI. You will need to twiddle with it to do
   * Ethernet CRC, i.e., BigEndian/LittleEndian byte/bit order.
   *
   * The CRCs this code generates agree with the vendor-supplied Verilog models
   * of several of the popular FDDI "MAC" chips.
   */
  /* unsigned long [...] */
  static long crc32_table[]=null;
  /* Initialized first time "crc32()" is called. If you prefer, you can
   * statically initialize it at compile time. [Another exercise.]
   */

  long swi_crc32(/*unsigned???*/ byte[] buf, int len) {
#ifdef TRACE0
    Trace.level++;
    Trace.log("FileData.swi_crc32(byte[], int)");
#ifdef TRACE1
    Trace.log("buf[]: " + buf + "\n    len: " + len);
#endif /* TRACE1 */
#endif /* TRACE0 */
    int pn;
    /*unsigned*/ long crc;
    if (crc32_table==null) {  /* if not already done, */
      init_crc32();   /* build table */
    }
    crc = 0xffffffffL;       /* preload shift register, per CRC-32 spec */
    for (pn = 0; len > 0; ++pn, --len) {
      crc = ((crc << 8)&0xffffffffL) ^ crc32_table[(int)((crc >> 24) ^ ((long)buf[pn]&0xff))];
    }
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
    return ~crc;            /* transmit complement, per CRC-32 spec */
  }

  /*
   * Build auxiliary table for parallel byte-at-a-time CRC-32.
   */
  static final int CRC32_POLY=0x04c11db7;    /* AUTODIN II, Ethernet, & FDDI */

  void init_crc32() {
#ifdef TRACE0
    Trace.level++;
    Trace.log("FileData.init_crc32()");
#endif /* TRACE0 */
    long i, j;
    long c;
    crc32_table = new long[256];
    for (i = 0; i < 256; ++i) {
      for (c = i << 24, j = 8; j > 0; --j) {
        c = (c & 0x80000000L)!=0 ? (c << 1) ^ CRC32_POLY : (c << 1);
      }
      c=c & 0xffffffffL;
      crc32_table[(int)i] = c;
    }
#ifdef TRACE0
    Trace.level--;
#endif /* TRACE0 */
  }


#ifndef JAVAME
  private short read2(FilePtr fp, long fpos, int freord, int fendian)
      throws Exception { // IOException, BufferUnderflowException
    if (fpos >= 0) {
      fp.seek(fpos);
    }
    short val=(short)fp.readShort();

    if (freord != 0) {
      val = (short)(
                 (
                  ( val << 8 ) |
                  (( val >>> 8 ) & 0xff)
                 ) & 0xffff);
    }
    return val;
  }

  private int read3(FilePtr fp, long fpos, int freord, int fendian)
      throws Exception { // IOException, BufferUnderflowException
    if (fpos >= 0) {
      fp.seek(fpos);
    }
    int val=(int)fp.readUnsignedByte();
    int i2=(int)fp.readShort(); if(i2<0){i2&=0xffff;}
    val=(val<<16)+i2;

    if (freord != 0) {
      val = (( val & 0x000000ff ) << 24) +
            (( val & 0x0000ff00 ) <<  8) +
            (( val & 0x00ff0000 ) >>  8) +
            (( val & 0xff000000 ) >>> 24);

      boolean do_shift = ((fendian == SwephData.SEI_FILE_BIGENDIAN && freord != 0) ||
                          (fendian == SwephData.SEI_FILE_LITENDIAN && freord == 0));

      return (do_shift?val>>8:val);
    }
    return val;
  }

  private int read4(FilePtr fp, long fpos, boolean unsigned, int freord, int fendian)
      throws Exception { // IOException, BufferUnderflowException
    if (fpos >= 0) {
      fp.seek(fpos);
    }
    int val=(int)fp.readInt();

    if (freord != 0) {
      if (unsigned) {
        val = (( val & 0x000000ff ) << 24) +
              (( val & 0x0000ff00 ) <<  8) +
              (( val & 0x00ff0000 ) >>  8) +
              (( val & 0xff000000 ) >> 24);
      } else {
        val = (( val & 0x000000ff ) << 24) +
              (( val & 0x0000ff00 ) <<  8) +
              (( val & 0x00ff0000 ) >>  8) +
              (( val & 0xff000000 ) >>> 24);
      }
    } else if (unsigned && val<0) {
      val&=0x7fffffff;
    }

    return val;
  }

  private double read8(FilePtr fp, long fpos, int freord, int fendian)
      throws Exception { // IOException, BufferUnderflowException
    if (fpos >= 0) {
      fp.seek(fpos);
    }

    long val=Double.doubleToLongBits(fp.readDouble());

    if (freord != 0) {
      val = (( val & 0x00000000000000ffL ) << 56) +
            (( val & 0x000000000000ff00L ) << 40) +
            (( val & 0x0000000000ff0000L ) << 24) +
            (( val & 0x00000000ff000000L ) <<  8) +
            (( val & 0x000000ff00000000L ) >>  8) +
            (( val & 0x0000ff0000000000L ) >> 24) +
            (( val & 0x00ff000000000000L ) >> 40) +
            (( val & 0xff00000000000000L ) >> 56);
    }

    return Double.longBitsToDouble(val);
  }

  /* fetch chebyshew coefficients from sweph file for
   * tjd          time
   * ipli         planet number
   * ifno         file number
   * serr         error string
   */
  int get_new_segment(SwissData swed, double tjd, int ipli, int ifno,
                      StringBuffer serr) {
#ifdef TRACE0
    Trace.level++;
    Trace.log("FileData.get_new_segment(SwissData, double, int, int, StringBuffer)");
#ifdef TRACE1
    Trace.log("swed: " + swed + "\n    tjd: " + Trace.fmtDbl(tjd) + "\n    ipli: " + ipli + "\n    ifno: " + ifno + "\n    serr: " + serr.toString());
#endif /* TRACE1 */
#endif /* TRACE0 */
    int i, j, k, m, n, o, icoord;
    int iseg;
    int fpos;
    int nsizes, nsize[]=new int[6];
    int nco;
    int idbl;
    /* unsigned */ short c[]=new short[4]; // unsigned (byte) ist wichtig?
// The coefficients will be written to pdp
    PlanData pdp = swed.pldat[ipli];
    FileData fdp = swed.fidat[ifno];
    int freord  = (int) (fdp.iflg & SwephData.SEI_FILE_REORD);
    int fendian = (int) (fdp.iflg & SwephData.SEI_FILE_LITENDIAN);
    /* unsigned long */ long longs[]=new long[SwephData.MAXORD+1]; // unsigned ist wichtig?
    /* compute segment number */


    iseg = (int) ((tjd - pdp.tfstart) / pdp.dseg);
#ifdef TRACE0
#endif /* TRACE0 */
    /*if (tjd - pdp->tfstart < 0)
        return(NOT_AVAILABLE);*/
    pdp.tseg0 = pdp.tfstart + iseg * pdp.dseg;
    pdp.tseg1 = pdp.tseg0 + pdp.dseg;
    /* get file position of coefficients from file */
    fpos = (int)pdp.lndx0 + iseg * 3;
    try {
      fpos = read3(fptr, fpos, freord, fendian);
      fptr.seek(fpos);
      /* clear space of chebyshew coefficients */
      if (pdp.segp == null) {
        pdp.segp = new double[pdp.ncoe*3];
      }
      for(i=0;i<pdp.segp.length;i++) { pdp.segp[i]=0.; }
      /* read coefficients for 3 coordinates */
      for (icoord = 0; icoord < 3; icoord++) {
        idbl = icoord * pdp.ncoe;
        /* first read header */
        /* first bit indicates number of sizes of packed coefficients */
        c[0]=(short)fptr.readUnsignedByte();
        c[1]=(short)fptr.readUnsignedByte();
        if ((c[0] & 128)!=0) {
          nsizes = 6;
          c[2]=(short)fptr.readUnsignedByte();
          c[3]=(short)fptr.readUnsignedByte();
          nsize[0] = (int) c[1] / 16;
          nsize[1] = (int) c[1] % 16;
          nsize[2] = (int) c[2] / 16;
          nsize[3] = (int) c[2] % 16;
          nsize[4] = (int) c[3] / 16;
          nsize[5] = (int) c[3] % 16;
          nco = nsize[0] + nsize[1] + nsize[2] + nsize[3] + nsize[4] + nsize[5];
        } else {
          nsizes = 4;
          nsize[0] = (int) c[0] / 16;
          nsize[1] = (int) c[0] % 16;
          nsize[2] = (int) c[1] / 16;
          nsize[3] = (int) c[1] % 16;
          nco = nsize[0] + nsize[1] + nsize[2] + nsize[3];
        }
        /* there may not be more coefficients than interpolation
         * order + 1 */
        if (nco > pdp.ncoe) {
          if (serr != null) {
            serr.setLength(0);
	    serr.append("error in ephemeris file: " + nco + " coefficients instead of " + pdp.ncoe + ". ");
            if (serr.length() + fdp.fnam.length() < SwissData.AS_MAXCH - 1) {
              serr.setLength(0);
	      serr.append("error in ephemeris file " + fdp.fnam + ": " + nco + " coefficients instead of " + pdp.ncoe + ". ");
	    }
          }
#ifdef TRACE0
          Trace.level--;
#endif /* TRACE0 */
          pdp.segp = null;
          throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
              SweConst.ERR, serr);
        }
        /* now unpack */
int kCnt;
        for (i = 0; i < nsizes; i++) {
          if (nsize[i] == 0) {
            continue;
          }
          if (i < 4) {
            j = (4 - i);
            k = nsize[i];
            for(kCnt=0; kCnt<k; kCnt++) {
              switch(j) {
                case 1: longs[kCnt]=(int)fptr.readUnsignedByte();
                        break;
                case 2: longs[kCnt]=(int)read2(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
                        if(longs[kCnt]<0){longs[kCnt]&=0xffff;}
                        break;
                case 3: longs[kCnt]=read3(fptr, SwephData.SEI_CURR_FPOS, freord, fendian);
                        if(longs[kCnt]<0){longs[kCnt]&=0xffffff;}
                        break;
                case 4: longs[kCnt]=read4(fptr, SwephData.SEI_CURR_FPOS, false, freord, fendian);
                        if(longs[kCnt]<0){longs[kCnt]&=0xffffffff;}
                        break;
              }
            }
            for (m = 0; m < k; m++, idbl++) {
              if ((longs[m] & 1)!=0) {   /* will be negative */
                pdp.segp[idbl] = -((((longs[m]+1) / 2)&0x7fffffffL) / 1e+9 * pdp.rmax / 2);
              } else {
                pdp.segp[idbl] = ((longs[m] / 2)&0x7fffffffL) / 1e+9 * pdp.rmax / 2;
              }
            }
          } else if (i == 4) {              /* half byte packing */
            j = 1;
            k = (nsize[i] + 1) / 2;
            for(kCnt=0; kCnt<k; kCnt++) {
              longs[kCnt]=(int)fptr.readUnsignedByte();
            }
            for (m = 0, j = 0;
                 m < k && j < nsize[i];
                 m++) {
              for (n = 0, o = 16;
                   n < 2 && j < nsize[i];
                   n++, j++, idbl++, longs[m] %= o, o /= 16) {
                if ((longs[m] & o)!=0) {
                  pdp.segp[idbl] = -(((longs[m]+o) / o / 2) * pdp.rmax /
                                                                    2 / 1e+9);
                } else {
                  pdp.segp[idbl] = (longs[m] / o / 2) * pdp.rmax / 2 / 1e+9;
                }
              }
            }
          } else if (i == 5) {              /* quarter byte packing */
            j = 1;
            k = (nsize[i] + 3) / 4;
            for(kCnt=0; kCnt<k; kCnt++) {
              longs[kCnt]=(int)fptr.readUnsignedByte();
            }
            for (m = 0, j = 0;
                 m < k && j < nsize[i];
                 m++) {
              for (n = 0, o = 64;
                   n < 4 && j < nsize[i];
                   n++, j++, idbl++, longs[m] %= o, o /= 4) {
                if ((longs[m] & o)!=0) {
                  pdp.segp[idbl] = -(((longs[m]+o) / o / 2) * pdp.rmax /
                                                                    2 / 1e+9);
                } else {
                  pdp.segp[idbl] = (longs[m] / o / 2) * pdp.rmax / 2 / 1e+9;
                }
              }
            }
          }
        }
      }
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      return SweConst.OK;
    } catch (java.io.IOException e) {
      serr.append("file error in swisseph.FileData: "+e.getMessage());
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
#ifdef NIO
    } catch (java.nio.BufferUnderflowException e) {
      serr.append("file error in swisseph.FileData: "+e.getMessage());
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
#endif /* NIO */
    } catch (Exception e) {
      serr.append("file error in swisseph.FileData: "+e.getMessage());
#ifdef TRACE0
      Trace.level--;
#endif /* TRACE0 */
      throw new SwissephException(tfstart, SwissephException.DAMAGED_FILE_ERROR,
          SweConst.ERR, serr);
    }
  }
#endif /* JAVAME */

#if 0
// do_fread is directly mapped to read2 / read4 etc. in the Java version
  /* SWISSEPH
   * reads from a file and, if necessary, reorders bytes
   * targ         target pointer
   * size         size of item to be read
   * count        number of items
   * corrsize     in what size should it be returned
   *              (e.g. 3 byte int -> 4 byte int)
   * fp           file pointer
   * fpos         file position: if (fpos >= 0) then fseek
   * freord       reorder bytes or no
   * fendian      little/bigendian
   * ifno         file number
   * serr         error string
   */
private byte[] do_fread(void *trg, int size, int count, int corrsize, FILE *fp, int32 fpos, int freord, int fendian, int ifno, char *serr)
throws IOException {
  int i, j, k;
  int totsize;
  unsigned char space[1000];
  unsigned char *targ = (unsigned char *) trg;
  totsize = size * count;
  try {
    if (fpos >= 0) {
      fp.seek(fpos);
    }
    /* if no byte reorder has to be done, and read size == return size */
    if (freord == 0 && size == corrsize) {
      fread((void *) targ, (size_t) totsize, 1, fp);
      fp.read(,,,);
      return ...;
    } else {
      fread((void *) &space[0], (size_t) totsize, 1, fp);
      if (size != corrsize) {
        memset((void *) targ, 0, (size_t) count * corrsize);
      }
      for(i = 0; i < count; i++) {
        for (j = size-1; j >= 0; j--) {
          if (freord!=0)
            k = size-j-1;
          else
            k = j;
          if (size != corrsize)
            if ((fendian == SEI_FILE_BIGENDIAN && (freord==0)) ||
                (fendian == SEI_FILE_LITENDIAN && (freord!=0)))
              k += corrsize - size;
          targ[i*corrsize+k] = space[i*size+j];
        }
      }
    }
  } catch (Exception ioe) { // IOException, BufferUnderflowException
    if (serr != null) {
      serr.setLength(0);
// Who cares about the length of the string...?
//      serr.append("Ephemeris file is damaged. ");
//      if (strlen(serr) + strlen(swed.fidat[ifno].fnam) < AS_MAXCH - 1)
        serr.append("Ephemeris file " + swed.fidat[ifno].fnam + " is damaged.");
    }
    clearData();
    fp = null;
    throw ioe;
  }
  return SweConst.OK;
}
#endif /* 0 */


#ifdef TRACE0
  FileData() {
    Trace.level++;
    Trace.log("FileData()");
    Trace.level--;
  }
#endif /* TRACE0 */
} // Ende der Klasse FileData.


//////////////////////////////////////////////////////////////////////////////
// Anmerkungen: //////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
// String fnam;     Fuer Ausgaben von Fehlermeldungen und zum
//                  Zwischenspeichern.
// int fversion;    Wird nicht genutzt, aber aus der Datei ausgelesen.
//                  Sollte zugreifbar sein.
// String astnam;   Wird aus der Datei ausgelesen und in "swe_get_planet_name"
//                  zurueckgeliefert.
// int sweph_denum; Wird aus der Datei ausgelesen und in "sweph"
//                  einmal genutzt (4 Bytes uebrigens):
//                  if (fdp->sweph_denum >= 403 && ipl < SEI_ANYBODY) {
//                    swi_IERS_FK5(xp, xp, 1);
//                    [...]
//                  }
// java.io.RandomAccessFile fptr;
// double tfstart;  Beginn und Ende des Zeitraumes, ueber den die Datei
// double tfend;    Daten enthaelt. Wird ausgewertet.
// int iflg;        Enthaelt zwei Flags in Bit 1 und Bit 2: "little endian /
//                  big endian" und "reorder Bytes". sizeof(long), vermutlich
//                  4 Bytes=int;
// short npl;       Wird aus der Datei mit zwei Bytes ausgelesen. Anzahl der
//                  Planeten in der Datei. Aufgrund dieser Information werden
//                  "npl"-mal Konstanten ueber die Planeten aus der Datei
//                  ausgelesen (PlanData p: p.lndx0; p.iflg; p.ncoe; p.rmax;
//                  p.tfstart bis p.dperi; p.refep und FileData.ipl).
// short ipl[];     2 Bytes==int. Offenbar Nummer der Planeten...
