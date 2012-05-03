#ifdef J2ME
#define JAVAME
#endif /* J2ME */
import java.math.*;
import swisseph.SMath;

/**
* CFmt: Formatting strings and numbers like printf in C.<P>
*
* The first purpose of this class is to ease the migration of C-programs
* regarding the printf family. I needed this kind of class, but I am not
* very experienced in Java, so if anyone sees very funny kind of programming
* style in parts of this class, he / she should tell me about an alternative.
* Same thing applies to any errors in programming or erronous behaviour of
* the class.<P>
*
* This class is meant to be completely public domain - do whatever you
* like to do with it, but if you would like to see some improvements or
* if you implemented some improvements, I would like to know of it as well.<P>
*
* Thomas Mack, May 1999<BR>
* mack@ifis.cs.tu-bs.de<P>
*
*
*
* <B>Introduction:</B><P>
*
* The format specifier splits up into 5 parts: '%'; flags '-', '+', ' ', '#';
* minimum fieldwidth; "." + precision; type specifier (c (char), s (string),
* d,i,o,u,p,x,X (integer numbers), f,e,E,g,G (real numbers)). "%" and type
* specifier are necessary, all others are optional.<P>
*
* For further info see the excerpt of the manpage below.<P>
*
* <B>Examples:</B><P>
*
* <CODE>
* CFmt cv = new CFmt();<BR>
* System.out.println("Int:    '"+ cv.fmt("%17d",-653)+"'";<BR>
* System.out.println("Int:    '"+ cv.fmt("%17X", 653)+"'";<BR>
* System.out.println("Int:    '"+ cv.fmt("%#17x", 653)+"'";<BR>
* System.out.println("Double: '"+ cv.fmt("%17.4f",-653.0)+"'";<BR>
* System.out.println("Double: '"+ cv.fmt("%-17.4e",-653.0)+"'";<BR>
* System.out.println("Double: '"+ cv.fmt("%17.4G",-653.0)+"'";<BR>
* System.out.println("Double: '"+ cv.fmt("%#17.4G",-653.0)+"'";<BR>
* System.out.println("String: '"+ cv.fmt("%17.10s","I like it! (Do I?)")+"'";<BR>
* System.out.println("Char:   '"+ cv.fmt("%-5c",'Y')+"'";<BR>
* </CODE><P>
*
*
* Output:
*
* <P><CODE>
* Int:    '             -653'<BR>
* Int:    '              28D'<BR>
* Int:    '            0x28d'<BR>
* Double: '        -653.0000'<BR>
* Double: '-6.5300e+02      '<BR>
* Double: '             -653'<BR>
* Double: '           -653.0'<BR>
* String: '       I like it!'<BR>
* Char:   'Y    '<BR>
* </CODE><P>
*
* Read the relevant excerpt of the man(ual)page for printf further below
* for a rather complete description of the formatting options.<P>
*
*
*
* <B>Behaviour that is different to C under Solaris 2.6:</B><P>
*
* <UL>
* <LI> Hexadecimal numbers (type 'x', 'X' und 'p') in C will be displayed
*   with max. 4 Bytes, in Java with max. 8 bytes:<P><CODE>
*
*     Java (x): ffffffffffffffff   [-1 decimal]<BR>
*     &nbsp;&nbsp;&nbsp;C (x):         ffffffff   [-1 decimal]</CODE><P>
*
*   Same behaviour applies to octal numbers (type 'o'):<P><CODE>
*
*   Java (o): 1777777777777777777777   [-1 decimal]<BR>
*   &nbsp;&nbsp;&nbsp;C (o):            37777777777   [-1 decimal]</CODE><P>
*
*   You can optionally adjust the behaviour in CFmt via the constructor
*   or with the method "void setHexByteCnt(int)". You would have to pass
*   an integer specifying the maximum number of bytes that should be used
*   for display. This could be four or eight. All other values will NOT
*   change the behaviour!<P>
*
*   CFmt cv = new CFmt(4) will switch to the C behaviour, default is the
*   Java behaviour with 8 bytes displayed.<P>
*
*   You can always query the value with "int getHexByteCnt()".<P>
*
*
*
*
* <LI> Type 'u' (unsigned) kann ONLY display bytes, shorts or ints, if you
*   want to display longs, you would have to use 'd', 'i', 'o', 'p', 'x',
*   or 'X'. (Funnily, I did not manage to display a "long long" or similar
*   in C via printf - but I did not look too close into these mechanics.)<P>
*
*
*
* <LI> precision and / or minimum field width can be 320 maximum.
*   Bigger Values will be shortened to 320.<P>
*
*
*
* <LI> If type specifications don't match the value, an empty string will be
*   returned. Example:<P><CODE>
*
*     fmt("Hello","%f");</CODE><P>
*
*   So far I did allow two exceptions: Strings with "%c" and Characters
*   with a format specifier of "%s".<P>
*
*
* <LI> Format specifier not sticking to the below detailed scheme will lead
*   to "some" result, but the behaviour is not clearly defined. Definately
*   the behaviour is different to the behaviour under C in our environment.<P>
* </UL>
*
*
* <B>Excerpt of the manpage used as the basis for the CFmt class:</B><P>
*
*  [...]<P>
*
*     Each conversion specification is introduced by the character
*     %.  After the %, the following appear in sequence:<P>
*
*          Zero or more flags, which modify  the  meaning  of  the
*          conversion specification.<P>
*
*          An optional decimal digit string specifying  a  minimum
*          field  width.  If the converted value has fewer charac-
*          ters than the field width, it will  be  padded  on  the
*          left  (or  right,  if  the  left-adjustment  flag  `-',
*          described below, has been given) to  the  field  width.
*          The padding is with blanks unless the field width digit
*          string starts with a zero, in which case the padding is
*          with zeros.<P>
*
*          A precision that gives the minimum number of digits  to
*          appear  for  the  d,  i, o, u, x, or X conversions, the
*          number of digits to appear after the decimal point  for
*          the e, E, and f conversions, the maximum number of sig-
*          nificant digits for the g and G conversion, or the max-
*          imum  number  of characters to be printed from a string
*          in s conversion.  The precision takes  the  form  of  a
*          period  (.)  followed by a decimal digit string; a NULL
*          digit string is treated as zero.  Padding specified  by
*          the  precision  overrides  the padding specified by the
*          field width.<P>
*
*          An optional l (ell) specifying that a following  d,  i,
*          o,  u,  x,  or X conversion character applies to a long
*          integer arg.  An l before any other conversion  charac-
*          ter is ignored.<P>
*
*>[Note: I did never(!) see any difference between the output with
*> a specification containing an additional 'l' and a specification
*> not containing the 'l'.]<P>
*
*          A character that indicates the type of conversion to be
*          applied.<P>
*
*     A field width or precision or both may be  indicated  by  an
*     asterisk  (*)  instead  of a digit string.  In this case, an
*     integer arg supplies the field width or precision.  The  arg
*     that  is actually converted is not fetched until the conver-
*     sion letter is seen, so the args specifying field  width  or
*     precision  must  appear  before  the arg (if any) to be con-
*     verted.  A negative field width argument is taken as  a  `-'
*     flag  followed  by a positive field width.  If the precision
*     argument is negative, it will be changed to zero.<P>
*
*     The flag characters and their meanings are:<P>
*     -         The  result  of  the  conversion  will  be   left-
*               justified within the field.<BR>
*     +         The result of  a  signed  conversion  will  always
*               begin with a sign (+ or -).<BR>
*     blank     If the first character of a signed  conversion  is
*               not  a  sign,  a  blank  will  be  prefixed to the
*               result.  This implies that  if  the  blank  and  +
*               flags both appear, the blank flag will be ignored.<BR>
*     #         This flag specifies that the value is to  be  con-
*               verted  to an "alternate form."For c, d, i, s, and
*               u conversions, the flag  has  no  effect.   For  o
*               conversion,  it  increases  the precision to force
*               the first digit of the result to be a zero.  For x
*               or X conversion, a non-zero result will have 0x or
*               0X prefixed to it.  For e, E, f, g, and G  conver-
*               sions,  the  result  will always contain a decimal
*               point, even if no digits follow  the  point  (nor-
*               mally,  a  decimal  point appears in the result of
*               these conversions only if  a  digit  follows  it).
*               For  g and G conversions, trailing zeroes will not
*               be removed from the result  (which  they  normally
*               are).<P>
*
*     The conversion characters and their meanings are:<P>
*
*     d,i,o,u,x,X<BR>
*               The integer arg is converted to signed decimal  (d
*               or  i),  unsigned octal (o), unsigned decimal (u),
*               or  unsigned  hexadecimal  notation  (x  and   X),
*               respectively;  the  letters  abcdef are used for x
*               conversion and the letters ABCDEF  for  X  conver-
*               sion.   The precision specifies the minimum number
*               of digits to appear; if the value being  converted
*               can  be  represented  in  fewer digits, it will be
*               expanded with leading zeroes.  (For  compatibility
*               with  older  versions, padding with leading zeroes
*               may alternatively be  specified  by  prepending  a
*               zero  to  the field width.  This does not imply an
*               octal value for the  field  width.)   The  default
*               precision  is  1.  The result of converting a zero
*               value with a precision of zero is a NULL string.<BR>
*     f         The float or double arg is  converted  to  decimal
*               notation  in the style [-]ddd.ddd where the number
*               of digits after the decimal point is equal to  the
*               precision  specification.   If  the  precision  is
*               missing, 6 digits are given; if the  precision  is
*               explicitly  0,  no digits and no decimal point are
*               printed.<BR>
*
*     e,E       The float or double arg is converted in the  style
*               [-]d.ddde+ddd, where there is one digit before the
*               decimal point and the number of digits after it is
*               equal  to  the  precision;  when  the precision is
*               missing, 6 digits are produced; if  the  precision
*               is  zero,  no decimal point appears.  The E format
*               code will produce a number with  E  instead  of  e
*               introducing  the  exponent.   The  exponent always
*               contains at least two digits.<BR>
*     g,G       The float or double arg is printed in style f or e
*               (or  in  style  E in the case of a G format code),
*               with the precision specifying the number of signi-
*               ficant  digits.   The  style  used  depends on the
*               value converted:  style e or E will be  used  only
*               if  the  exponent resulting from the conversion is
*               less  than  -4  or  greater  than  the  precision.
*               Trailing  zeroes  are  removed  from the result; a
*               decimal point appears only if it is followed by  a
*               digit.<P>
*
*     The e, E f, g, and G formats print IEEE indeterminate values
*     (infinity  or  not-a-number)  as "Infinity" or "NaN" respec-
*     tively.<P>
*
*     c         The character arg is printed.<BR>
*     s         The  arg  is  taken  to  be  a  string  (character
*               pointer)   and  characters  from  the  string  are
*               printed until a NULL character (\0) is encountered
*               or until the number of characters indicated by the
*               precision specification is reached.  If the preci-
*               sion  is  missing,  it is taken to be infinite, so
*               all characters up to the first NULL character  are
*               printed.   A  NULL  value for arg will yield unde-
*               fined results.<BR>
*     %         Print a %; no argument is converted.<P>
*
*     In no case does a non-existent or small  field  width  cause
*     truncation  of  a  field;  if  the result of a conversion is
*     wider than the field width, the field is simply expanded  to
*     contain  the conversion result.  Padding takes place only if
*     the specified field width exceeds the actual width.  Charac-
*     ters  generated  by printf() and fprintf() are printed as if
*     putc(3S) had been called.<P>
*
*[...]<P>
*
*EXAMPLES<BR>
*     To print a date and  time  in  the  form  "Sunday,  July  3,
*     10:02,"  where  weekday  and  month  are  pointers  to NULL-
*     terminated strings:<P><CODE>
*
*          printf("%s, %s %i, %d:%.2d", weekday, month, day, hour, min);
*<P></CODE>
*[Note:
*<P><CODE>
*    System.out.print(cv.fmt("%s",weekday)+", "+<BR>
*    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cv.fmt("%s",month)+" "+<BR>
*    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cv.fmt("%i",day)+", "+<BR>
*    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cv.fmt("%d",hour)+":"+<BR>
*    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cv.fmt("%.2d",min));
*<P></CODE>
*  should be abbreviated to:
*<P><CODE>
*    System.out.print(weekday+", "+month+" "+day+", "+<BR>
*    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hour+":"+cv.fmt("%.2d",min));
*<P></CODE>
*
*  as in the first four places the CFmt conversion does nothing more than
*  the default conversions in System.out.print().<BR>
*]<P>
*
*     To print pi to 5 decimal places:<P><CODE>
*
*          printf("pi = %.5f", 4 * atan(1. 0));<P></CODE>
*[Note: <BR><CODE>
*  System.out.print("pi = "+cv.fmt("%.5f",4 * atan(1.0)));<BR></CODE>
*]<P>
*
*[...]<P>
*
*     Very wide fields (>128 characters) fail.<P>
*
*[Note: The limit in this class is about 320]<P>
*
*[...]
* @version 1.0.0a
*/
public class CFmt
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  // public:
  // Constructors:

  /**
  * This creates a new instance with a default of 8 as the hexByteCount.
  * @see #setHexByteCnt
  */
  public CFmt() {
    this.hexByteCnt=8;
  }

  /**
  * This creates a new instance with the specified value as hexByteCnt.
  * @param hexByteCnt How many bytes a hexadecimal number will contain
  * maximum (4 or 8).
  * @see #setHexByteCnt
  */
  public CFmt(int hexByteCnt) {
    setHexByteCnt(hexByteCnt);
  }


  // Public Methods: /////////////////////////////////////////////////////////

  /**
  * The Hexadecimal-Byte-Count is ONLY relevant to negative numbers of one of
  * byte, short and int and type of 'o', 'x', 'X', 'p'. It specifies how
  * many bytes maximum a resulting hexadecimal number will contain.<P>
  * Examples:<P>
  * -1 will be displayed with a format of "%x" and hexByteCnt of 4 as:<P>
  * <CODE>
  * ffffffff
  * </CODE><P>
  * and with a hexByteCnt of 8 as:<P>
  * <CODE>
  * ffffffffffffffff
  * </CODE><P>
  * In C the default seems to be 4 bytes, the default here is 8 bytes.
  * Possible values are 4 and 8, other values will NOT change the last value!
  * @param hexByteCnt How many bytes to use for output of negative
  * hexadecimal numbers.
  */
  public void setHexByteCnt(int hexByteCnt) {
    if (hexByteCnt==8 || hexByteCnt==4) {
      this.hexByteCnt=hexByteCnt;
    }
  }

  /**
  * Returns the current value for hexByteCnt.
  * @return the current value of hexByteCnt
  * @see #setHexByteCnt(int)
  */
  public int getHexByteCnt() {
    return this.hexByteCnt;
  }




  // Methods for conversion: /////////////////////////////////////////////////
  // char "c":
  /**
  * Formats a character as specified by the conversion specification.
  * @param conv the conversion specification. It may only contain the
  * character 'c' as conversion character.
  * @param c the char that should become formatted
  * @return A String containing the formatted character
  */
  public String fmt( String conv, char c ) {
    return fmt(conv, new Character(c).toString());
  }


  // Strings "s":
  /**
  * Formats a String as specified by the conversion specification.
  * @param conv the conversion specification. It may only contain the
  * character 's' as conversion character.
  * @param s the String that should become formatted
  * @return A String containing the formatted String
  */
  public String fmt( String conv, String s ) {
    CFmtCvt cv = new CFmtCvt(conv);
    if (cv.type=='c') {
      s=s.substring(0,1);
    } else if (cv.type!='s') {
      return "";
    }

    // Precision:
    if (cv.withPrec && cv.precision<s.length() && cv.type!='c') {
      s=s.substring(0,cv.precision);
    }

    // MinimumFieldWidth:
    if (cv.minimum>320) { cv.minimum=320; }
    String padString=empty;
    if (cv.padChar=='0' && !cv.fMinus) { padString=zeros; }
    if (cv.withMin && s.length()<cv.minimum) {
      if (cv.fMinus) {
        s+=padString.substring(0,cv.minimum-s.length());
      } else {
        s=padString.substring(0,cv.minimum-s.length())+s;
      }
    }

    return s;
  }



  // byte d,i,o,p,u,x,X:
  /**
  * Formats a byte as specified by the conversion specification.
  * @param conv the conversion specification. It may contain the
  * characters 'd', 'i', 'o', 'p', 'u', 'x' and 'X' as conversion
  * characters.
  * @param bval the byte value that should become formatted
  * @return A String containing the formatted byte value
  */
  public String fmt( String conv, byte bval ) {
    return intFmt(conv, (long) bval, 'b');
  }

  // integer d,i,o,p,u,x,X:
  /**
  * Formats an integer as specified by the conversion specification.
  * @param conv the conversion specification. It may contain the
  * characters 'd', 'i', 'o', 'p', 'u', 'x' and 'X' as conversion
  * characters.
  * @param ival the integer value that should become formatted
  * @return A String containing the formatted integer value
  */
  public String fmt( String conv, int ival  ) {
    return intFmt(conv, (long) ival, 'i');
  }

  // long d,i,o,p,u,x,X:
  /**
  * Formats a long value as specified by the conversion specification.
  * @param conv the conversion specification. It may contain the
  * characters 'd', 'i', 'o', 'p', 'u', 'x' and 'X' as conversion
  * characters.
  * @param lval the long value that should become formatted
  * @return A String containing the formatted long value
  */
  public String fmt( String conv, long lval  ) {
    return intFmt(conv, lval, 'l');
  }



  // float f, e, E, g, G:
  /**
  * Formats a float value as specified by the conversion specification.
  * @param conv the conversion specification. It may contain the
  * characters 'f', 'e', 'E', 'g' and 'G' as conversion characters.
  * @param fval the float value that should become formatted
  * @return A String containing the formatted float value
  */
  public String fmt( String conv, float fval  ) {
    return fmt( conv, (double)fval );
  }

  // double f, e, E, g, G:
  /**
  * Formats a double value as specified by the conversion specification.
  * @param conv the conversion specification. It may contain the
  * characters 'f', 'e', 'E', 'g' and 'G' as conversion characters.
  * @param dval the double value that should become formatted
  * @return A String containing the formatted double value
  */
  public String fmt( String conv, double dval  ) {
    CFmtCvt cv = new CFmtCvt(conv);
    boolean isG=false;
    boolean neg=false;
    String res="";

    neg=(Double.doubleToLongBits(dval)&0x8000000000000000L)!=0L;

    if (Double.isNaN(dval)) { res="nan"; }
    if (dval == Double.NEGATIVE_INFINITY ||
        dval == Double.POSITIVE_INFINITY) {
      if (cv.precision<8) { res="Inf";
      } else { res="Infinity";
      }
    }
    if (neg) { dval=-dval; }

    if (res.equals("")) {
      if (!cv.withPrec) { cv.precision=6; cv.withPrec=true; }

      if (cv.type=='g' || cv.type=='G') { isG=true; }

// Rounding is not accurate!!!
double dval_tmp=dval; // postpone rounding
      // Rounding:
      // cv.precision:
      // e, E, f: no. of digits after decimalpoint
      // g, G   : max. no. of significant digits
      int pexp=0;
      int nexp=0;
      if (dval_tmp!=0.) {
        double fd=dval_tmp;
        while (fd>=10.) { fd/=10.; pexp++; }
        while (fd<1.) { fd*=10.; nexp++; }
        if (isG) {
          if (cv.precision==0) { cv.precision++; }
          dval_tmp+=5*SMath.pow(10,pexp-nexp-cv.precision);
        } else {
          if (cv.type=='e' || cv.type=='E') { // Exponential number:
            dval_tmp+=5*SMath.pow(10,-cv.precision-1+pexp-nexp);
          } else { // Floatingpoint number without exponent:
            dval_tmp+=5*SMath.pow(10,-cv.precision-1);
          }
        }
      }

      // Mapping 'g' and 'G' to 'f', 'e' or 'E' and changing the meaning
      // of 'precision' to the meaning of precision in 'f, 'e', 'E'
      // (precision='f', 'e', 'E': Count of digits after the decimalpoint
      //            'g', 'G':      Count of all signifikant digits):
      if (isG) {
        if (dval_tmp!=0. && (dval_tmp<1E-4 || dval_tmp>=SMath.pow(10,cv.precision))) {
          if (cv.precision>0) { cv.precision--; } // Digit before decimalpoint
          if (cv.type=='g') { cv.type='e';
          } else { cv.type='E';
          }
        } else {
          cv.precision=cv.precision-pexp-1+nexp;
          cv.type='f';
        }
        if (cv.precision<0) { cv.precision=0; }
      }

      if (cv.type!='f' && cv.type!='e' && cv.type!='E') {
        return "";
      }

      String integer="";
      String mantisse="";
      int exponent=0;
      int i;

      // Zahl zerlegen in Integer-Anteil, Mantisse und Exponenten:
//System.out.println("\n@@@ "+Long.toBinaryString(Double.doubleToLongBits(dval)));
//      res=Double.toString(dval);
res=dblToString(dval,cv.precision); // Includes rounding!!!
//System.out.println(">>> "+res);
      exponent=res.indexOf(".")-1;
      integer=res.substring(0,exponent+1);
      mantisse=res.substring(exponent+2);
      if (mantisse.indexOf("E")>=0) {
        exponent+=Integer.parseInt(mantisse.substring(mantisse.indexOf("E")+1));
        mantisse=mantisse.substring(0,mantisse.indexOf("E"));
      }

      // "integer" is supposed to hold all digits
      if (integer.charAt(0)=='0' && dval!=0.) {
        exponent--; i=0;
        integer=integer.substring(1);
        while (i<mantisse.length() && mantisse.charAt(i)=='0') {
          exponent--; i++;
        }
        mantisse=mantisse.substring(i);
      }
      integer+=mantisse;
      mantisse="";

      // Separating integer part and mantissa into the desired forms ('f', 'e')
      if (cv.type=='f') {
        if (exponent<0) {
          mantisse=zeros.substring(0,-exponent-1)+integer;
          integer="0";
        } else {
          if (integer.length()<exponent+1) {
            integer+=zeros.substring(0,exponent+1-integer.length());
          }
          mantisse=integer.substring(exponent+1);
          integer=integer.substring(0,exponent+1);
        }
      } else { // Exponential form
        mantisse=integer.substring(1);
        integer=integer.substring(0,1);
      }

      // Precision:
      if (cv.precision>320) { cv.precision=320; }
      // Count of digits after the decimalpoint:
      int len=mantisse.length();
      if (cv.precision>len && (!isG || cv.fHash)) {
        // Add '0's:
        mantisse+=zeros.substring(0,cv.precision-len);
      } else if (cv.precision<len) {
        // Truncate:
        mantisse=mantisse.substring(0,cv.precision);
      }
      if (isG && !cv.fHash) {
        // Truncate leftover zeros at the end of the mantissa:
        i=mantisse.length()-1;
        while (i>=0 && mantisse.charAt(i)=='0') {
          i--;
        }
        mantisse=mantisse.substring(0,i+1);
      }

      res=integer;
      if (cv.fHash || (cv.withPrec && !isG && cv.precision>0) ||
          (isG && cv.withPrec && mantisse.length()>0 )) {
        res+="."+mantisse;
      }

      if (cv.type=='e' || cv.type=='E') {
        if (cv.type=='e') { res+="e"; } else { res+='E'; }
        if (exponent<0) {
          exponent=-exponent; res+="-";
        } else {
          res+="+";
        }
        if (exponent<10) {
          res+="0";
        }
        res+=Integer.toString(exponent);
      }
    }

    String prefix="";
    if (neg) { prefix="-";
    } else if (cv.fPlus) { prefix="+";
    } else if (cv.fSpace) { prefix=" ";
    }

    if (cv.withMin && cv.padChar=='0') {
      int len=prefix.length();
      // Pad left, before a sign or others will be prefixed!
      if (res.length()+len<cv.minimum && !cv.fMinus) {
        res=zeros.substring(0,cv.minimum-res.length()-len)+res;
      }
    }
    res=prefix+res;


    // MinimumFieldWidth:
    if (cv.withMin) {
      if (res.length()<cv.minimum) {
        if (cv.minimum>320) { cv.minimum=320; }
        if (cv.fMinus) {
          res+=empty.substring(0,cv.minimum-res.length());
        } else {
          String padString=empty;
          if (cv.padChar=='0') { padString=zeros; }
          res=padString.substring(0,cv.minimum-res.length())+res;
        }
      }
    }

    return res;
  } // double







  // Private Methods: ////////////////////////////////////////////////////////
  private String intFmt( String conv, long lval, char baseType ) {
    CFmtCvt cv = new CFmtCvt(conv);
    if (cv.type!='d' && cv.type!='i' && cv.type!='o' && cv.type!='p' &&
        cv.type!='u' && cv.type!='x' && cv.type!='X') {
      return "";
    }
    if (baseType=='l' && cv.type=='u') { return ""; }

    boolean neg=false;
    String res="";
    String padString=empty;

    if (cv.type=='d' || cv.type=='i') { // signed decimal
      if (lval<0) { lval=-lval; neg=true; }
      res=String.valueOf(lval);
    } else if (cv.type=='o') { // unsigned octal
      res=Long.toOctalString(lval);
      if (lval<0 && this.hexByteCnt==4 && baseType!='l') {
        // 1 777 777 777 777 777 777 777 -> 37777777777 [         -1]
        // 1 777 777 777 760 000 000 000 -> 20000000000 [-2147483648]
        if (res.charAt(11)=='7') {
          res="3"+res.substring(12);
        } else {
          res="2"+res.substring(12);
        }
      }
    } else if (cv.type=='u') { // unsigned decimal
      if (lval>=0) {
        res=Long.toString(lval);
      } else { // Negative number to be interpreted as positive
        res=Long.toString(256L*256L*256L*256L+lval);
      }
    } else if (cv.type=='x' || cv.type=='p' || cv.type=='X') {
                                                     // unsigned hexadecimal
      // "p" exists ONLY as "%p" without any modifications and then seems to
      // be identical with "%x"!
      res=Long.toHexString(lval);
      if (cv.type=='p' && (cv.withPrec || cv.withMin || cv.fMinus ||
                                    cv.fPlus || cv.fSpace || cv.fHash)) {
        return "";
      }
      if (this.hexByteCnt==4 && baseType!='l') { res=res.substring(8); }
    }

    // Precision:
    // Minimum count of digits (WITHOUT sign!). Add zeros before the number
    // if necessary.
    if (!cv.withPrec) { cv.precision=1; }
    if (cv.precision>320) { cv.precision=320; }
    if (cv.precision>res.length()) {
      res=zeros.substring(0,cv.precision-res.length())+res;
    } else if (cv.precision==0 && lval==0) {
      res="";
    }

    String prefix="";
    if (cv.type=='d' || cv.type=='i') { // negative / positive
      // Flags:
      if (neg) {
        prefix="-";
      } else if (cv.fPlus) {
        prefix="+";
      } else if (cv.fSpace) {
        prefix=" ";
      }
    } else if (cv.type=='o' && cv.fHash && res.charAt(0)!='0') {
      res="0"+res;
    } else if (cv.fHash && lval!=0 && (cv.type=='x' || cv.type=='X')) {
      prefix="0x";
    }


    // MinimumFieldWidth:
    if (cv.minimum>320) { cv.minimum=320; }
    if (cv.withPrec) { cv.padChar=' '; }
    if (cv.padChar=='0' && !cv.fMinus) { padString=zeros; }
    if (cv.withMin) {
      if (cv.padChar=='0') {
        // First pad, then add sign in front:
        int len=prefix.length();
        if (res.length()+len<cv.minimum) {
          res=padString.substring(0,cv.minimum-res.length()-len)+res;
        }
        res=prefix+res;
      } else {
        // First the sign, then pad the string
        res=prefix+res;
        if (res.length()<cv.minimum) {
          if (cv.fMinus) {
            res+=empty.substring(0,cv.minimum-res.length());
          } else {
            res=empty.substring(0,cv.minimum-res.length())+res;
          }
        }
      }
    } else {
      res=prefix+res;
    }

    if (cv.type=='X') { // ALL letters uppercase
      res=res.toUpperCase();
    }

    return res;
  }

  private int hexByteCnt;

  // 340 chars (how much max.?)
  static private final String empty="                                        "+
        "                                                            "+
        "                                                            "+
        "                                                            "+
        "                                                            "+
        "                                                            ";
  static private final String zeros="0000000000000000000000000000000000000000"+
        "000000000000000000000000000000000000000000000000000000000000"+
        "000000000000000000000000000000000000000000000000000000000000"+
        "000000000000000000000000000000000000000000000000000000000000"+
        "000000000000000000000000000000000000000000000000000000000000"+
        "000000000000000000000000000000000000000000000000000000000000";

  private BigDecimal const2_52=new BigDecimal("2251799813685248");
  private BigDecimal const2=new BigDecimal("2");

  private String dblToString(double d, int prec) {
    long exp, i, lMant;
    boolean nexp=false;

// Fuer positive Zahlen gilt:
// 2^(Exponent+1) + 2^Exponent * Mantisse / 2^51
//
// mit Exponent    = double_bitmap & 0x3ff0000000000000,
// Exp. Vorzeichen = double_bitmap & 0x4000000000000000
// und Mantisse    = double_bitmap & 0x000fffffffffffff
//

    lMant=Double.doubleToLongBits(d)&0x000fffffffffffffL;
    exp =(Double.doubleToLongBits(d)&0x3ff0000000000000L) >> 52;
    nexp=(Double.doubleToLongBits(d)&0x4000000000000000L) == 0L;
    if (nexp) { exp=-1024+exp; }

    BigDecimal mant=new BigDecimal(Long.toString(lMant));
    BigDecimal res=new BigDecimal("0");

    BigDecimal res1;
    if (exp==0 && lMant==0L) {
      res1=new BigDecimal("0");
    } else {
      res1=new BigDecimal("1");
      if (exp>0) {
        for(i=0; i<exp; i++) {
          res1=res1.multiply(const2);
        }
      } else {
        for(i=0; i>exp; i--) {
          res1=res1.divide(const2,50,BigDecimal.ROUND_HALF_UP);
        }
      }
    }

    res=res1.multiply(const2);
    res=res.add(res1.multiply(mant.divide(const2_52,50,BigDecimal.ROUND_HALF_UP)));
    res=res.add((new BigDecimal("5")).movePointLeft(prec+1));

    return res.toString();
  }

} // End of class CFmt




class CFmtCvt
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{ // Interprets a formatting string
  char flag='\0';
  boolean fMinus=false;
  boolean fPlus=false;
  boolean fSpace=false;
  boolean fHash=false;
  char padChar=' ';
  int minimum=0;
  int precision=0;
  boolean longSpec=false; // longSpec does not have any meaning???
  char type='\0';

  boolean withMin=false;
  boolean withPrec=false;
  boolean validConv=false;

  CFmtCvt(String cv) {
    char ch;
    int len=cv.length()-1;

    if (len>=1 && cv.charAt(0)=='%') {
      int i=1;

      type=cv.charAt(len); len--;
      if (cv.charAt(len)=='l') { longSpec=true; len--; }

      // Flags:
      do {
        ch=cv.charAt(i);
        if (ch=='-')      { fMinus=true; }
        else if (ch=='+') { fPlus=true;  }
        else if (ch==' ') { fSpace=true; }
        else if (ch=='#') { fHash=true;  }
        i++;
      } while (i<=len && (ch=='-' || ch=='+' || ch==' ' || ch=='#'));
      i--;

      // Minimum fieldwidth:
      ch=cv.charAt(i);
      if (ch=='0') {
        withMin=true;
        padChar='0';
        withMin=true;
        i++; if (i>len) { return; }
        ch=cv.charAt(i);
      }
      if (Character.isDigit(ch)) {
        withMin=true;
        minimum=Character.digit(ch,10);
        i++; if (i>len) { return; }
        ch=cv.charAt(i);
        while (Character.isDigit(ch)) {
          minimum=10*minimum+Character.digit(ch,10);
          i++; if (i>len) { return; }
          ch=cv.charAt(i);
        }
      }

      // Precision:
      if (ch=='.') {
        withPrec=true;
        i++; if (i>len) { return; }
        ch=cv.charAt(i);
        while (Character.isDigit(ch)) {
          precision=10*precision+Character.digit(ch,10);
          i++; ch=cv.charAt(i);
        }
      }
    }
  }
} // End of class CFmtCvt
