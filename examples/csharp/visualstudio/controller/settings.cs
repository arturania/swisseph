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
using System.Xml;
using radixpro.ui;

namespace radixpro.controller {
    /// <summary>
    /// Container for user preferences for default location and timezone.
    /// </summary>
    public class Settings {
        private string _location;
        private string _longitude;
        private string _latitude;
        private string _geoLongDirection;
        private string _geoLatDirection;
        private int _timezone;

        /// <summary>
        /// Constructor for Settings. Uses defaults if setings cannot be read.
        /// </summary>
        public Settings() {
            if (!readSettings()) {
                setDefaults();
                saveSettings();
            }
        }

        /// <summary>
        /// Location (e.g. city)
        /// </summary>
        public string location {
            get { return _location; }
            set { _location = value; }
        }

        /// <summary>
        /// Longitude. String should be in the format:
        /// ddd.mm.ss 0 &lt; input &lt;= 180
        /// </summary>
        public string longitude {
            get { return _longitude; }
            set { _longitude = value; }
        }

        /// <summary>
        /// Latitude. String should be in the format:
        /// dd.mm.ss 0 &lt; input &lt; 90
        /// </summary>
        public string latitude {
            get { return _latitude; }
            set { _latitude = value; }
        }

        /// <summary>
        /// Direction of longitude, "+" or "-" 
        /// </summary>
        public string geoLongDirection {
            get { return _geoLongDirection; }
            set { _geoLongDirection = value; }
        }

        /// <summary>
        /// Direction of latitude, "+" or "-" 
        /// </summary>
        public string geoLatDirection {
            get { return _geoLatDirection; }
            set { _geoLatDirection = value; }
        }

        /// <summary>
        /// Index for timezone
        /// </summary>
        public int timezone {
            get { return _timezone; }
            set { _timezone = value; }
        }



        /// <summary>
        /// Setter for 
        /// </summary>
        /// <param name="value">Text for longitude</param>
        /// <returns>true if longitude has been set, otherwise false.</returns>
        public Boolean setLLLongitude(String value) {
            ValidationItem vi = InputChecker.checkGeoLongitude(value);
            if (vi.noErrors) {
                this.longitude = value;
                return true;
            }
            else return false;
        }


        /// <summary>
        /// Setter for latitude. String should be in the format:
        /// dd.mm.ss D (D= plus or minus sign) and 0 &lt; input &lt; 90
        /// </summary>
        /// <param name="value">Text for latitude</param>
        /// <returns>true if latitude has been set, otherwise false.</returns>
        public Boolean setLLLatitude(String value) {
            ValidationItem vi = InputChecker.checkGeoLatitude(value);
            if (vi.noErrors) {
                this.latitude = value;
                return true;
            }
            else return false;
        }


        /// <summary>
        /// Setter for timezone
        /// </summary>
        /// <param name="value">Value to set</param>
        /// <returns>Returns true if timezone has been set, otherwise false</returns>
        public Boolean setTTTimezone(int value) {
            if (InputChecker.checkTimezone(value).noErrors) {
                this.timezone = value;
                return true;
            }
            else return false;
        }


        /// <summary>
        /// Set default values for configuration. Does not save the config.
        /// </summary>
        public void setDefaults() {
            location = ResourceBundle.RB_DEFAULT_LOCATION;
            longitude = "000.00.00";
            latitude = "00.00.00";
            timezone = 1;  // UT
        }

        /// <summary>
        /// Save existing settings in xml-file.
        /// </summary>
        /// <returns>If no errors occurred true, otherwise false.</returns>
        public Boolean saveSettings() {
            Boolean result = true;
            try {
                XmlTextWriter xtw = new XmlTextWriter("radixpro.settings", null);
                xtw.WriteStartDocument();
                xtw.WriteComment("Settings file for RadixPro. Do not edit manually.");
                xtw.WriteStartElement("root");
                xtw.WriteElementString("version", "1.0");
                xtw.WriteElementString("timezone", timezone.ToString());
                xtw.WriteElementString("location", location);
                xtw.WriteElementString("long", longitude.ToString());
                xtw.WriteElementString("longdir", geoLongDirection);
                xtw.WriteElementString("lat", latitude.ToString());
                xtw.WriteElementString("latdir", geoLatDirection);
                xtw.WriteEndElement();
                xtw.WriteEndDocument();

                xtw.Close();
            }
            catch {
                //catch (Exception e) {
                // todo handle exception message
                result = false;
            }
            return result;
        }


        /// <summary>
        /// Read settings from file.
        /// </summary>
        /// <returns>If no errors occurred true, otherwise false.</returns>
        public Boolean readSettings() {
            Boolean result = true;
            try {
                XmlTextReader xtr = new XmlTextReader("radixpro.settings");
                while (xtr.Read()) {
                   if (xtr.NodeType == XmlNodeType.Element) {
                      parseXmlTag(xtr.Name, xtr.ReadString());
                   }
                }
                xtr.Close();
            }
            catch {
                result = false;
                // todo handle exception message
            }
            return result;
        }

        private void parseXmlTag(String name, String value) {
            if (name.Equals("timezone")) timezone = Convert.ToInt32(value);
            if (name.Equals("location")) location = value;
            if (name.Equals("long")) longitude = value;
            if (name.Equals("lat")) latitude = value;
            if (name.Equals("longdir")) geoLongDirection = value;
            if (name.Equals("latdir")) geoLatDirection = value;

        }

    }
}