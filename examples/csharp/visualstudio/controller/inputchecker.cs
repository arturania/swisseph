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
using radixpro.ui;

namespace radixpro.controller {

    /// <summary>
    /// Validated item
    /// </summary>
    public class ValidationItem {
        private string _resultTxt;
        private bool _noErrors;

        /// <summary>
        /// Constructor, initializes
        /// </summary>
        /// <param name="resultTxt">Text for result</param>
        /// <param name="noErrors">Indicates the absence of errors</param>
        public ValidationItem(string resultTxt, bool noErrors) {
            this.resultTxt = resultTxt;
            this.noErrors = noErrors;
        }

        /// <summary>
        /// Text for result
        /// </summary>
        public string resultTxt {
            get {return _resultTxt; }
            set {_resultTxt = value; }
        }

        /// <summary>
        /// True if no errrors found, otherwise false
        /// </summary>
        public bool noErrors {
            get {return _noErrors; }
            set {_noErrors = value; }
        }

    }



    /// <summary>
    /// Makes members available to test input formats.
    /// </summary>
    public static class InputChecker {

        /// <summary>
        /// Check value for name, name should have a minimum length of 1 character
        /// </summary>
        /// <param name="input">Value to check</param>
        /// <returns></returns>
        public static ValidationItem checkName(String input) {
            ValidationItem vi;
            try {
                input = input.Trim();
                if (input.Length > 0) vi = new ValidationItem(input, true);
                else vi = new ValidationItem(input, false);
            }
            catch {
                vi = new ValidationItem(input, false);
            }
            return vi;
        }

        /// <summary>
        /// Check value for location, location is optional, only preceding and fololowing spaces will be stripped
        /// </summary>
        /// <param name="input"></param>
        /// <returns>validated item</returns>
        public static ValidationItem checkLocation(String input) {
            ValidationItem vi;
            try {
                input = input.Trim();
                vi = new ValidationItem(input, true);
            }
            catch {
                vi = new ValidationItem(input, false);
            }
            return vi;
        }


        /// <summary>
        /// Check value in geographic longitude for correct syntax. 
        /// 0 &lt; input &lt;= 180
        /// </summary>
        /// <param name="input">String in the format ddd.mm.ss</param>
        /// <returns>Validated item</returns>
        public static ValidationItem checkGeoLongitude(String input) {
            ValidationItem vi = new ValidationItem(input, false);
            bool result = true;
            try {
                input = input.Trim();
                // check for 1 or 2 ciphers for degrees
                if (input.IndexOf('.') == 2) input = "0" + input;
                if (input.IndexOf('.') == 1) input = "00" + input;
                // check for missing seconds
                if (input.Length == 6) input = input + ".00";
                if (input.Length == 7) input = input + "00";
                vi.resultTxt = input;
                // check correct formatting
                if (input.Length != 9) result = false;
                else if (!Char.IsDigit(input[0])
                        || !Char.IsDigit(input[1])
                        || !Char.IsDigit(input[2])
                        || !input[3].Equals('.')
                        || !Char.IsDigit(input[4])
                        || !Char.IsDigit(input[5])
                        || !input[6].Equals('.')
                        || !Char.IsDigit(input[7])
                        || !Char.IsDigit(input[8])) result = false;
                else {
                    // check for correct content
                    int d = Convert.ToInt32(input.Substring(0, 3));
                    int m = Convert.ToInt32(input.Substring(4, 2));
                    int s = Convert.ToInt32(input.Substring(7, 2));
                    if (d < 0 || d > 180) result = false;
                    if (m < 0 || m > 59) result = false;
                    if (s < 0 || s > 59) result = false;
                    if (d == 180 && (m > 0 || s > 0)) result = false;
                }
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = input;
                vi.noErrors = false;
            }
            return vi;
        }


        /// <summary>
        /// Check value in geographic latitude for correct syntax.
        /// 0 &lt; input &lt; 89.59.59
        /// </summary>
        /// <param name="input">String in the format dd.mm.ss</param>
        /// <returns>Validated item</returns>
        public static ValidationItem checkGeoLatitude(String input) {
            ValidationItem vi = new ValidationItem(input, false);
            bool result = true;
            try {
                input = input.Trim();
                // check for 1 cipher for degrees
                if (input.IndexOf('.') == 1) input = "0" + input;
                // check for missing seconds
                if (input.Length == 5) input = input + ".00";
                if (input.Length == 6) input = input + "00";
                vi.resultTxt = input;
                // check correct formatting
                if (input.Length != 8) result = false;
                else if (!Char.IsDigit(input[0])
                        || !Char.IsDigit(input[1])
                        || !input[2].Equals('.')
                        || !Char.IsDigit(input[3])
                        || !Char.IsDigit(input[4])
                        || !input[5].Equals('.')
                        || !Char.IsDigit(input[6])
                        || !Char.IsDigit(input[7])) result = false;
                else {
                    int d = Convert.ToInt32(input.Substring(0, 2));
                    int m = Convert.ToInt32(input.Substring(3, 2));
                    int s = Convert.ToInt32(input.Substring(6, 2));
                    if (d < 0 || d > 89) result = false;
                    if (m < 0 || m > 59) result = false;
                    if (s < 0 || s > 59) result = false;
                }
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = input;
                vi.noErrors = false;
            }
            return vi;
        }


        /// <summary>
        /// Check date for correct format and validness.
        /// </summary>
        /// <param name="input">String in the format yyyy.mm.dd</param>
        /// <param name="calendar">Indicates type of calendar, possible values SE_JUL_CAL and SE_GREG_CAL</param>
        /// <returns>Validated item</returns>
        public static ValidationItem checkDate(String input, int calendar) {
            ValidationItem vi = new ValidationItem(input, false);
            bool result = true;
            try {
                input = input.Trim();
                vi.resultTxt = input;
                if (input.Length != 10) result = false;
                else if (!Char.IsDigit(input[0])
                    || !Char.IsDigit(input[1])
                    || !Char.IsDigit(input[2])
                    || !Char.IsDigit(input[3])
                    || !input[4].Equals('.')
                    || !Char.IsDigit(input[5])
                    || !Char.IsDigit(input[6])
                    || !input[7].Equals('.')
                    || !Char.IsDigit(input[8])
                    || !Char.IsDigit(input[9])) result = false;
                else {
                    int d = Convert.ToInt32(input.Substring(8, 2));
                    int m = Convert.ToInt32(input.Substring(5, 2));
                    int y = Convert.ToInt32(input.Substring(0, 4));
                    double ut = 0.0;
                    double jd = Sweph.getJD(y, m, d, ut, calendar);
                    if (d != Sweph.getDayFromJd(jd, calendar) || m < 1 || m > 12) result = false;
                }
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = input;
                vi.noErrors = false;
            }
            return vi;
        }


        /// <summary>
        /// Check time for correct format and validness
        /// </summary>
        /// <param name="input">String with time in the format hh:mm</param>
        /// <returns></returns>
        public static ValidationItem checkTime(String input) {
            ValidationItem vi = new ValidationItem(input, false);
            bool result = true;
            try {
                input = input.Trim();
                // check for 1 cipher for hours
                if (input.IndexOf(':') == 1) input = "0" + input;
                // check for missing seconds
                if (input.Length == 5) input = input + ":00";
                if (input.Length == 6) input = input + "00";
                vi.resultTxt = input;
                if (input.Length != 8) result = false;
                else {
                    if (!Char.IsDigit(input[0])
                        || !Char.IsDigit(input[1])
                        || !input[2].Equals(':')
                        || !Char.IsDigit(input[3])
                        || !Char.IsDigit(input[4])
                        || !input[5].Equals(':')
                        || !Char.IsDigit(input[6])
                        || !Char.IsDigit(input[7])) result = false;

                }
                if (result) {
                    int h = Convert.ToInt32(input.Substring(0, 2));
                    int m = Convert.ToInt32(input.Substring(3, 2));
                    int s = Convert.ToInt32(input.Substring(6, 2));
                    if (h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59) result = false;
                }
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = input;
                vi.noErrors = false;
            }
            return vi;
        }


        /// <summary>
        /// Check index for timezone. Syntax check is not necessary as the input
        /// is already an integer. Condition to check: 0 &lt;= value &lt; Constants.RP_MAX_TIMEZONES
        /// </summary>
        /// <param name="input">The value to check</param>
        /// <returns>Validated item</returns>
        public static ValidationItem checkTimezone(int input) {
            ValidationItem vi = new ValidationItem(Convert.ToString(input), false);
            bool result = true;
            try {
                if (input < 0 || input > Constants.RP_MAX_TIMEZONES) result = false;
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = Convert.ToString(input);
                vi.noErrors = false;
            }
            return vi;
        }

        /// <summary>
        /// Check specified timezone
        /// Condition to check: same as for ours but max value = 12:00:00
        /// </summary>
        /// <param name="input">The value to check</param>
        /// <returns>Validated item</returns>
        public static ValidationItem checkSpecTimezone(String input) {
            ValidationItem vi = new ValidationItem(input, false);
            bool result = true;
            try {
                input = input.Trim();
                // check for 1 cipher for hours
                if (input.IndexOf(':') == 1) input = "0" + input;
                // check for missing seconds
                if (input.Length == 5) input = input + ":00";
                if (input.Length == 6) input = input + "00";
                vi.resultTxt = input;
                if (input.Length != 8) result = false;
                else {
                    if (!Char.IsDigit(input[0])
                        || !Char.IsDigit(input[1])
                        || !input[2].Equals(':')
                        || !Char.IsDigit(input[3])
                        || !Char.IsDigit(input[4])
                        || !input[5].Equals(':')
                        || !Char.IsDigit(input[6])
                        || !Char.IsDigit(input[7])) result = false;

                }
                if (result) {
                    int h = Convert.ToInt32(input.Substring(0, 2));
                    int m = Convert.ToInt32(input.Substring(3, 2));
                    int s = Convert.ToInt32(input.Substring(6, 2));
                    if (h < 0 || h > 12 || m < 0 || m > 59 || s < 0 || s > 59) result = false;
                }
                vi.noErrors = result;
            }
            catch {
                vi.resultTxt = input;
                vi.noErrors = false;
            }
            return vi;
        }

        /// <summary>
        /// Check value for DST for correct range. Syntax check is not necessary as the input
        /// is already a double. Condition to check: -4 &lt; value &lt; 4
        /// </summary>
        /// <param name="input">The value to check</param>
        /// <returns>True if value is within range</returns>
        public static Boolean checkDst(double input) {
            if (input > -4.0 && input < 4.0) return true;
            else return false;
        }

    }
}