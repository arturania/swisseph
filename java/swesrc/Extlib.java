#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifndef JAVAME
#ifdef TEST_ITERATIONS
#define TRANSITS
#endif /* TEST_ITERATIONS */
#ifdef TRANSITS
/*
   This is an extension to the Java port of the Swiss Ephemeris package
   of Astrodienst AG, Zuerich (Switzerland).

   Thomas Mack, mack@ifis.cs.tu-bs.de, 25th of November, 2004

*/

package swisseph;


import java.text.*;  // DateFormat etc.
import java.util.*;  // Locale etc.

/**
* Some supportive methods, mainly for internationalization.
* These methods are not available in the original Swiss
* Ephemeris package.
*/
public class Extlib
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{

  SwissEph  sw=new SwissEph();
  SwissLib  sl=new SwissLib();
  SweDate   sde1=new SweDate();
  SweDate   sde2=new SweDate();
  SweDate   sdu1, sdu2;
  SwissData swed=new SwissData();

  double transitVal = 0.;
  SimpleDateFormat df = null;
  String decTimeSeparator = ".";
  String decNumSeparator = ".";
  int secondsIdx = 0;



  /**
  * This class contains some additional method not contained
  * in the original Swiss Ephemeris package.
  * Currently, these methods deal with internationalization
  * primarily.
  */
  public Extlib() { }

  /**
  * This method is for debugging purposes only.
  */
  public static void main(String argv[]) {
    new Extlib();
  }

//////////----------------------

  /**
  * This method returns all available locale strings
  */
  public String[] getLocales() {
    Locale[] locs = DateFormat.getAvailableLocales();
    String[] locStrings = new String[locs.length];

    for (int r=0; r<locs.length; r++) {
      locStrings[r] = locs[r].getLanguage();
      if (locs[r].getCountry().length() > 0) {
        locStrings[r] += "_"+locs[r].getCountry();
        if (locs[r].getVariant().length() > 0) {
          locStrings[r] += "_"+locs[r].getVariant();	// e.g. th_TH_TH
        }
      }
    }
    return locStrings;
  }

  /**
  * Returns the requested locale from a locale string.
  * @param locString A String describing the locale as a two letter
  * language code, a two letter language code plus a "_" plus a two
  * letter country code, or null or the empty string. Null or the
  * empty string will return the default locale, all others will
  * return the requested locale.
  * @return The locale
  */
  public Locale getLocale(String locString) {
    String lang = locString;
    String cntry = "";
    if (locString == null || "".equals(locString)) {
      return Locale.getDefault();
    }
    int idx = locString.indexOf("_");
    if (idx >= 0) {
      lang = locString.substring(0,idx);
      cntry = locString.substring(idx+1);
    }
    Locale loc = null;
    if (cntry.equals("")) {
      loc = new Locale(lang);
    } else {
      loc = new Locale(lang, cntry);
    }

    return loc;
  }


  /**
  * Creates a localized date time formatter suitable for tabular output with
  * 4 digit years and UTC timezone. You will format dates like this:<p>
  * <code>&nbsp;&nbsp;&nbsp;
  * SimpleDateFormat sdf = createLocDateTimeFormatter("da_DK", true);<br>
  * &nbsp;&nbsp;&nbsp;SweDate sd = new SweDate(2005,3,27);<br>
  * &nbsp;&nbsp;&nbsp;//...<br>
  * &nbsp;&nbsp;&nbsp;System.out.println(sdf.format(sd.getDate(0)));<br>
  * </code><p>
  * Years B.C. will be prefixed by a "-". Years are counted including year
  * "0", which differs from normal DateFormat output.
  * @param locString The input locale for which this date time format
  * should be created. See getLocale() for more infos.
  * @return The normalized form of the DateFormat.
  */
  public SimpleDateFormat createLocDateTimeFormatter(String locString, boolean force24h) {

    // Get date format:
    Locale loc = getLocale(locString);
    SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateTimeInstance(
              java.text.DateFormat.SHORT, java.text.DateFormat.MEDIUM, loc);

    // Revert to UTC:
    df.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT"));

    // Change output pattern to our needs, this means 4 letter year etc.:
    String pattern = getNormalizedDatePattern(df.toPattern(), force24h);
    df.applyPattern(pattern);

    return df;
  }

  /**
  * Ensures a date pattern with four letter year, two letter month and day
  * and 24h time format, if requested.
  */
  public String getNormalizedDatePattern(String pattern, boolean force24h) {
    int idx = 0;
//System.out.println(pattern);

    // force year, month, day, hour, minutes and seconds to appear with two digits:
    final String pats = ("yMdHhms");
    for (int n = 0; n < pats.length(); n++) {
      char ch = pats.charAt(n);
      String out = ch + "" + ch;
      idx = pattern.indexOf(out);
      if (idx < 0) {
        idx = pattern.indexOf(ch);
        if (idx >= 0) {
          pattern = pattern.substring(0,idx) + ch + pattern.substring(idx);
        }
      }
    }
    // force year to appear with four digits:
    idx = pattern.indexOf("yyyy");
    if (idx < 0) {
      idx = pattern.indexOf("yy");
      if (idx >= 0) {
        pattern = pattern.substring(0,idx) + "yy" + pattern.substring(idx);
      }
    }
    // Locale "mk" does not have a "seconds" part in its time pattern
    // (Java 1.4.2 / Linux).
    // Append it after the minutes pattern ("m"):
    if (pattern.indexOf("s") < 0) {
      idx = pattern.indexOf("mm");
      if (idx >= 0) { // If not, it not even has a minutes part???
        try {
          // We assume some non-digit char AFTER "mm" as it is the
          // case with "mk" here ("d.M.yy HH:mm:" original, "dd.MM.yyyy HH:mm:"
          // when changed):
          pattern = pattern.substring(0,idx+3) + "ss" + pattern.substring(idx+3);
        } catch (StringIndexOutOfBoundsException sb) {
          // In zh_SG, the format looks like dd/MM/yyyy a hh:mm (above assumption fails)
          pattern = pattern.substring(0,idx+2) + pattern.substring(idx-1,idx) + "ss" + pattern.substring(idx+2);
        }
      }
    }

    if (force24h) {
      idx = pattern.indexOf("a");
      if (idx >= 0) {
        pattern = pattern.substring(0,idx) + pattern.substring(idx+1);
        idx = pattern.indexOf("hh");
        pattern = pattern.substring(0,idx) + "HH" + pattern.substring(idx+2);
      }
    }

    return pattern;
  }

  /**
  * Returns the decimal separator of the NumberFormat
  */
  public String getDecimalSeparator(NumberFormat nf) {
    if (nf instanceof DecimalFormat) {
      return String.valueOf(((DecimalFormat)nf).getDecimalFormatSymbols().getDecimalSeparator());
    }
    return null;
  }

  /**
  * Returns the index in the formatter pattern of the given pattern 'what'
  * recalculated to the APPLIED pattern of the formatter.
  * E.g. for locale zh_HK the pattern is:
  *    yyyy'年'MM'月'dd'日' ahh:mm:ss
  * The index of 'ss' would NOT be 25, which we would get when simply counting in
  * the pattern string, but rather 20, when counting in the resulting string.
  */
  public int getPatternLastIdx(String pattern, String what, SimpleDateFormat dof) {
    // If we want to append fractions of a second, we have to know
    // at which position in the string this is to happen. We can
    // look for the "ss" part in the pattern string, but the pattern
    // can contain string constants delimited by the ' character.
    // Moreover, it can contain patterns expanding to more than
    // one letter when applied to a date and time. This is so far
    // known to be true for the 'a' pattern expanding to AM or PM
    // in english locales and expanding to still different values
    // in other locales.

    int idx = pattern.lastIndexOf(what) + 1;

    // Strip string constant delimiters from found pattern position:
    int last = idx;
    int i = 0;
    while (i < last) {
      if (pattern.charAt(i) == '\'') {
        idx--;
      }
      i++;
    }

    if (pattern.indexOf("a") >= 0 &&
        pattern.indexOf("a") < pattern.indexOf(what)) {
      int len = dof.getDateFormatSymbols().getAmPmStrings()[0].length(); // No input with fractions of a second?
      // We have to know the time when the length of the AM-String is
      // different from the length of the PM-String...
      // We don't care for now...
      idx += len - 1;
    }

    return idx;
  }

} // End of class Extlib
#endif /* TRANSITS */
#endif /* JAVAME */
