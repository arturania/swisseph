/***************************************************************************
 Copyright (C) 2008 RadixPro/Jan Kampherbeek (http://radixpro.org).	  
                                                                        
 This program is free software; you can redistribute it and/or          
 modify it under the terms of both the GNU General Public License (GPL).
 You should also adhere to the terms of the Swiss Ephemerice            
 License (SEPL).                                                        
 The GPL is published by the Free Software Foundation; either version 3 
 of the License, or (at your option) any later version is effective. 	  
 The SEPL (Swiss Ephemeris License) is published by AstroDienst; either 
 version 0.2 of the License, or (at your option) any later version is   
 effective.                                                             
 This program is distributed in the hope that it will be useful, but	  
 WITHOUT ANY WARRANTY; without even the implied warranty of	          
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 	  
 General Public License for more details.                               
                                                                        
 You should have received a copy of the GPL along with this program; 	  
 if not, download a copy from http://www.gnu.org/copyleft/gpl.html      
 You also should have received a copy of the SEPL along with this program; 
 if not, download a copy from http://www.astro.com/swisseph/sepl.htm                                 
 ***************************************************************************/

using System;
using System.Collections.Generic;
using System.Text;
using radixpro.engine;

namespace radixpro.controller {

    /// <summary>
    /// Support for textual and numeric conversions.
    /// </summary>
    public static class Conversions {


        /// <summary>
        /// Convert degree to Radial
        /// </summary>
        /// <param name="x">Degree to be converted</param>
        /// <returns>Input value converted to radials</returns>
        public static double degToRad(double x) {
            return x * Math.PI / 180;
        }


        /// <summary>
        /// Convert index of zodiacal sign to character with the correct glyph,
        /// to be used with the font radixpro_signs.
        /// </summary>
        /// <param name="i">Index of sign, 0 = Aries ... 11 = Pisces</param>
        /// <returns>String with character for sign if used with font radixpro_signs.</returns>
        public static string signGlyphFromIndex(int i) {
            string c;
            switch (i) {
                case 0: c = "k"; break;
                case 1: c = "l"; break;
                case 2: c = "m"; break;
                case 3: c = "n"; break;
                case 4: c = "o"; break;
                case 5: c = "p"; break;
                case 6: c = "q"; break;
                case 7: c = "r"; break;
                case 8: c = "s"; break;
                case 9: c = "t"; break;
                case 10: c = "u"; break;
                case 11: c = "v"; break;
                default: c = "-"; break;
            }
            return c;
        }

        /// Convert longitude to corresponding glyph of zodiacal sign,
        /// to be used with the font radixpro_signs.
        /// </summary>
        /// <param name="x">Longitude, 0..&lt;30 = Aries etc.</param>
        /// <returns>String with character for sign if used with font RadixPro.</returns>
        public static string signGlyphFromLong(double x) {
            int i = (int)Math.Floor(x / 30);
            return signGlyphFromIndex(i);
        }

        /// <summary>
        /// Convert index of planet to the correct glyph
        /// used by the font radixpro_bodies.
        /// </summary>
        /// <param name="i"></param>
        /// <returns>Character for glyph</returns>
        public static string bodyGlyphFromIndex(int i) {
            string c;
            switch (i) {
                case Constants.SE_SUN: c = "a"; break;
                case Constants.SE_MOON: c = "b"; break;
                case Constants.SE_MERCURY: c = "c"; break;
                case Constants.SE_VENUS: c = "d"; break;
                case Constants.SE_MARS: c = "e"; break;
                case Constants.SE_JUPITER: c = "f"; break;
                case Constants.SE_SATURN: c = "g"; break;
                case Constants.SE_URANUS: c = "h"; break;
                case Constants.SE_NEPTUNE: c = "i"; break;
                case Constants.SE_PLUTO: c = "j"; break;
                case Constants.SE_MEAN_NODE: c = "w"; break;
                case Constants.SE_TRUE_NODE: c = "w"; break;
                case Constants.SE_CHIRON: c = "C"; break;
                case Constants.SE_ASC_AS_BODY: c = "" + "v"; break;
                case Constants.SE_MC_AS_BODY: c = "" + (char)122; break;
                default: c = "-"; break;
            }
            return c;
        }


        /// <summary>
        /// Convert index of aspect to the correct glyph
        /// used by the font radixpro_aspects.
        /// </summary>
        /// <param name="i"></param>
        /// <returns>Character for glyph</returns>
        public static string aspectGlyphFromIndex(int i) {
           string c;
           switch (i) {
              case Constants.C_RP_ASPECT_CONJUNCTION: c = "L"; break;
              case Constants.C_RP_ASPECT_OPPOSITION: c = "M"; break;
              case Constants.C_RP_ASPECT_TRINE: c = "O"; break;
              case Constants.C_RP_ASPECT_SQUARE: c = "N"; break;
              case Constants.C_RP_ASPECT_QUINTILE: c = "U"; break;
              case Constants.C_RP_ASPECT_SEXTILE: c = "P"; break;
              case Constants.C_RP_ASPECT_SEMISEXTILE: c = "R"; break;
              case Constants.C_RP_ASPECT_SEPTILE: c = "X"; break;
              case Constants.C_RP_ASPECT_SEMISQUARE: c = "S"; break;
              case Constants.C_RP_ASPECT_SESQUIQUADRATE: c = "T"; break;
              case Constants.C_RP_ASPECT_BIQUINTILE: c = "V"; break;
              case Constants.C_RP_ASPECT_INCONJUNCT: c = "Q"; break;
              default: c = "-"; break;
           }
           return c;
        }



        /// <summary>
        /// limit value x to a value within a given range
        /// pre: upperRange &gt; lowerRange
        /// post: lowerRange &lt;=  x &lt; upperRange
        /// </summary>
        /// <param name="x">the value to be limited to the range</param>
        /// <param name="lowerRange">lower limit of the range (inclusive)</param>
        /// <param name="upperRange">upper limit of the range (exclusive)</param>
        /// <returns>value limited to the defined range</returns>
        public static double toRange(double x, double lowerRange, double upperRange) {
            double rangeSize = upperRange - lowerRange;
            double result = x;
            while (result < lowerRange) result += rangeSize;
            while (result >= upperRange) result -= rangeSize;
            return result;
        }

        /// <summary>
        /// Define a string for any integer including preceding characters
        /// (probably spaces or zero's, but other characters are also supported).
        /// If the available width is too small, the text of the full integer is returned.
        /// </summary>
        /// <param name="x">Integer value to format</param>
        /// <param name="width">Size of the string</param>
        /// <param name="filler">Character to use as filler</param>
        /// <returns>Formatted value</returns>
        public static string formatInt(int x, int width, char filler) {
            string prefixString = "";
            int z = 10;
            for (int i = 0; i < width - 1; i++) {
                if (x < z) prefixString += filler;
                z *= 10;
            }
            return prefixString + x;
        }

        /// <summary>
        /// Convert a value to a string with a fixed number of positions for the 
        /// fractional part. If necessary one or more '0's will be appended.
        /// </summary>
        /// <param name="x">Value to convert, max supported value 10000</param>
        /// <param name="precision">Nr. of digits, supports up to 12 positions</param>
        /// <returns>The converted string</returns>
        public static string fixedFraction(double x, int precision) {
            string sign = "";
            if (x < 0) {
                sign = "-";
                x = Math.Abs(x);
            }
            if (precision > 12) precision = 12;
            string result;
            if (x > 10000) {
                result = "Error";
            }
            else {
                long factor = 1;
                for (int i = 0; i < precision; i++) {
                    factor *= 10;
                }
                long y = (long)Math.Round(x * factor);
                string fullText = y.ToString();
                int len1 = fullText.Length - precision;
              
                if (len1 > 0) result = fullText.Substring(0, len1)+ "." + fullText.Substring(len1);
                else result = "0." + fullText;
                result = sign + result;
            }
            return result;
        }


        /// <summary>
        /// Degrees with a fractional part. Converts a double to a string with a decimal presentation 
        /// and a specific precision digits in the fractional part). 
        /// The values are converted to a range of 0 - 360.
        /// The resultingstring ends with a degree-sign (Unicode \u00B0)
        /// </summary>
        /// <param name="degrees">The value to convert</param>
        /// <param name="precision">Number of digits in the fractional part</param>
        /// <returns>The result of teh conversion</returns>
        public static String deg2dd360(double degrees, int precision) {
            StringBuilder sb = new StringBuilder("0.");
            for (int i = 0; i < precision; i++) {
                sb.Append("0");
            }
            degrees = toRange(degrees, 0, 360);
            char degreeSign = '\u00B0';
            return degrees.ToString(sb.ToString()) + degreeSign;
        }



        /// <summary>
        /// Degrees in saxagesimal notation. Format depends on parameters:
        /// dd[degree sign]mm[minute sign]ss[second sign]
        /// The part ss[seconds sign] only if par. seconds is true
        /// If par. inSign is false dd will be ddd , 0 &lt;= degrees &lt; 360 
        /// otherwise, 0 &lt;= degrees &lt; 30 
        /// </summary>
        /// <param name="degrees">The value of the degrees</param>
        /// <param name="seconds">True if seconds need to be shown</param>
        /// <param name="inSign">True if the length within a sign will be used</param>
        /// <returns></returns>
        public static string deg2sexagesimal(double degrees, Boolean seconds, Boolean inSign) {
            double degN = toRange(degrees + 0.0000001, 0, 360);
            int si = (int)Math.Floor(degN / 30);
            int degreeLength = 3;
            if (inSign) {
                degN -= (si * 30);
                degreeLength = 2;
            }
            int dd = (int)Math.Floor(degN);
            double remaining = degN - dd;
            double x = remaining * 60;
            int mm = (int)Math.Floor(x);
            remaining = x - mm;
            int ss = (int)Math.Floor(remaining * 60);
            String dds = formatInt(dd, degreeLength, ' ') + '\u00B0';   // include unicode for degree sign
            String mms = formatInt(mm, 2, '0') + '\u0027';
            String sss = "";
            if (seconds) sss = formatInt(ss, 2, '0') + '\u0022';
            return dds + mms + sss;
        }


        /// <summary>
        /// Converts double value to degree in saxagesimal notation.
        /// The values can be positive and negative. 
        /// The following condition will be met: minValue &lt;= degrees &lt;= maxValue
        /// </summary>
        /// <param name="degrees">The value to convert</param>
        /// <param name="minValue">Minimum value for the range</param>
        /// <param name="maxValue">Maximum value for the range</param>
        /// <param name="seconds">True if seconds need to be shown</param>
        /// <returns></returns>
        public static string deg2sexagesimalInRange(double degrees, double minValue, double maxValue, bool seconds) {
            if (minValue >= maxValue) return "error";
            double degN = toRange(degrees, minValue, maxValue);
            bool isNegative = (degN < 0.0);
            int degreeLength = 2;
            if (minValue <= -100.0 || maxValue >= 100.0) degreeLength = 3;
            degN = Math.Abs(degN);
            int dd = (int)Math.Floor(degN);
            double remaining = degN - dd;
            double x = remaining * 60;
            int mm = (int)Math.Floor(x);
            remaining = x - mm;
            int ss = (int)Math.Floor(remaining * 60);
            String dds = formatInt(dd, degreeLength, ' ') + '\u00B0';   // include unicode for degree sign
            String mms = formatInt(mm, 2, '0') + '\u0027';
            String sss = "";
            if (seconds) sss = formatInt(ss, 2, '0') + '\u0022';
            string pmSign = "+ ";
            if (isNegative) pmSign = "- "; 
            return pmSign + dds + mms + sss;
        }



        /// <summary>
        /// Converts a double value containing hours into a string. Supports 2 formats:
        /// 0..24 or am/pm, depending on the param h24. The format of the result is
        /// hh:mm:ss or hh:mm:ss xx If hh &lt; 10, a space character is heading the string.
        /// </summary>
        /// <param name="hours">The hours to convert</param>
        /// <param name="h24">True if 24 hours format is used, false for am/pm format.</param>
        /// <returns>The converted string</returns>
        public static string hours2hms(double hours, Boolean h24) {
            double hrs = toRange(hours + 0.0000001, 0, 24);
            int hh = (int)Math.Floor(hrs);
            double remaining = hrs - hh;
            double x = remaining * 60;
            int mm = (int)Math.Floor(x);
            remaining = x - mm;
            int ss = (int)Math.Floor(remaining * 60);
            string ampm = "";
            if (!h24) {         // correct for format with am/pm
                if (hh > 12) {
                    hh -= 12;
                    ampm = " pm";
                }
                else ampm = " am";
            }

            return formatInt(hh, 2, ' ') + ':'
                 + formatInt(mm, 2, '0') + ':'
                 + formatInt(ss, 2, '0')
                 + ampm;
        }



        /// <summary>
        /// Converts a double value containing positive or negative hours into a string. 
        /// hh:mm:ss or hh:mm:ss or -hh:mm:ss 
        /// </summary>
        /// <param name="hours">The hours to convert</param>
        /// <returns>The converted string</returns>
        public static string double2OffsetHms(double hours) {
            double hrs = toRange(hours, -11.999999, 12.000001);
            hrs+= 0.0000001;     // safetyMargin to avoid rounding errors
            int hh = (int)Math.Floor(hrs);
            double remaining = hrs - hh;
            double x = remaining * 60;
            int mm = (int)Math.Floor(x);
            remaining = x - mm;
            int ss = (int)Math.Floor(remaining * 60);

            return formatInt(hh, 1, ' ') + ':' + formatInt(mm, 2, '0') + ':' + formatInt(ss, 2, '0');
        }


        /// <summary>
        /// Converts a string in one of the formats ddd.mm.ss, dd.mm.ss to a double value,
        /// the value of direction ('+' or '-') defines the sign of he result.
        /// </summary>
        /// <param name="intext">The string to convert</param>
        /// <param name="direction">The direction, positive for north or east, negative for south or west</param>
        /// <returns>Converted value in degrees. If an error occurred: -999</returns>
        public static double Coordinates2Degrees(string intext, string direction) {
            double x;
            string[] items = intext.Split('.');
            try {
                double d = Convert.ToDouble(items[0]);
                double m = Convert.ToDouble(items[1]);
                double s = Convert.ToDouble(items[2]);
                x = (double)d + m / 60 + s / 3600;
                if (direction.Equals("-")) x = -x;
            }
            catch {
                x = -999;  // TODO better error handling
            }
            return x;
        }

        /// <summary>
        /// Converts a text with a data into an array of integers, format of text yyyy.mm.dd
        /// </summary>
        /// <param name="intext">The string to convert</param>
        /// <returns>Converted value as an array of integers</returns>
        public static int[] DateText2IntArray(string intext) {
            string[] items = intext.Split('.');
            int[] values = new int[3];
            try {
                for (int i = 0; i < 3; i++) {
                    values[i] = Convert.ToInt32(items[i]);
                }
            }
            catch {
                for (int i = 0; i < 3; i++) {     // TODO better error handling
                    values[i] = -999;
                }
            }
            return values;
        }

        /// <summary>
        /// Converts a text with a time into an array of integers, format of text h:mm:ss
        /// </summary>
        /// <param name="intext">The string to convert</param>
        /// <returns>Converted value as an array of integers</returns>
        public static int[] TimeText2IntArray(string intext) {
            string[] items = intext.Split(':');
            int[] values = new int[3];
            try {
                for (int i = 0; i < 3; i++) {
                    values[i] = Convert.ToInt32(items[i]);
                }
            }
            catch {
                for (int i = 0; i < 3; i++) {     // TODO better error handling
                    values[i] = -999;
                }
            }
            return values;
        }


        /// <summary>
        /// Converts a text with a time into a double value
        /// </summary>
        /// <param name="intext">The string to convert, it should conform to hh:mm:ss</param>
        /// <returns>Value that represents the string</returns>
        public static double TimeText2Double(String intext) {
            int[] values = TimeText2IntArray(intext);
            return (double)values[0] + (double)values[1] / 60 + (double)values[2] / 3600;
        }

    }
}


