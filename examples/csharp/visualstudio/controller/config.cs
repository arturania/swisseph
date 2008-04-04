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
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using radixpro.engine;


namespace radixpro.controller {
    /// <summary>
    /// Configuration, defines behaviour for astrological calculations.
    /// </summary>
    public class Configuration {
        private int _majorOrb;
        private int _minorOrb;
        private int _midpointOrb;
        private int _progOrb;
        private int _nodetype;           // type of lunar node, possible values: SE_MEAN_NODE, SE_TRUE_NODE
        private int _status;
        private char _houseSystem;
        private bool _lunarNode;      // use Lunar node
        private bool _chiron;         // use chiron
        private ArrayList _aspectTypes;

        /// <summary>
        /// Constructor for Configuration. Uses defaults if config cannot be read.
        /// </summary>
        public Configuration() {
            if (!readConfig()) {
                setDefaults();
                bool ok = saveConfig();
            }
            else buildAspectTypes();
        }


        /// <summary>
        /// Orbis for major aspects
        /// </summary>
        public int majorOrb {
            get { return _majorOrb; }
            set { _majorOrb = value; }
        }

        /// <summary>
        /// Orbis for minor aspects
        /// </summary>
        public int minorOrb {
            get { return _minorOrb; }
            set { _minorOrb = value; }
        }


        /// <summary>
        /// Orbis for midpoints
        /// </summary>
        public int midpointOrb {
            get { return _midpointOrb; }
            set { _midpointOrb = value; }
        }

        /// <summary>
        /// Orbis for progressions
        /// </summary>
        public int progOrb { 
            get { return _progOrb; }
            set { _progOrb = value; }
        }

        /// <summary>
        /// Type of lunar node. If value not SE_MEAN_NODE or SE_TRUE_NODE set is not performed.
        /// </summary>
        public int nodeType {
            get { return _nodetype; }
            set { 
                if (value == Constants.SE_MEAN_NODE || value == Constants.SE_TRUE_NODE) 
                    _nodetype = value;
            }
        }

        /// <summary>
        /// Status. If value &lt; RP_CONFIG_ERROR || value > RP_CONFIG_ALTERED no set is performed.
        /// </summary>
        public int status {
            get { return _status; }
            set { 
                if (value >= Constants.RP_CONFIG_ERROR && value <= Constants.RP_CONFIG_ALTERED) _status = value;
            }
        }

        /// <summary>
        /// Housesystem. If value not one of P,K,O,R,C,A,V,X,H,T,B,G,M, U  no set is performed.
        /// </summary>
        public char houseSystem {
            get { return _houseSystem; }
            set {
                if ("PKORCAVXHTBGMU".IndexOf(value) >= 0) _houseSystem = value;
            }
        }

        /// <summary>
        /// True if lunar node is used.
        /// </summary>
        public bool lunarNode {
            get { return _lunarNode; }
            set { _lunarNode = value; }
        }

        /// <summary>
        /// True if Chiron is used.
        /// </summary>
        public bool chiron {
            get { return _chiron; }
            set { _chiron = value; }
        }

        /// <summary>
        /// ArrayList with objects AspectType
        /// </summary>
        public ArrayList aspectTypes {
            get { return _aspectTypes; }
            set { _aspectTypes = value; }
        }


        /// <summary>
        /// Set default values for configuration. Does not save the config.
        /// </summary>
        public void setDefaults() {
            houseSystem = Constants.C_HOUSES_PLACIDUS;
            majorOrb = 9;
            minorOrb = 3;
            midpointOrb = 2;
            progOrb = 1;
            chiron = true;
            lunarNode = true;
            nodeType = Constants.SE_MEAN_NODE;
            buildAspectTypes();
        }

        /// <summary>
        /// Save existing configuration in xml-file.
        /// </summary>
        /// <returns>If no errors occurred true, otherwise false.</returns>
        public bool saveConfig() {
            bool result = true;
            try {
                XmlTextWriter xtw = new XmlTextWriter("radixpro.config", null);
                xtw.WriteStartDocument();
                xtw.WriteComment("Configuration file for RadixPro. Do not edit manually.");
                xtw.WriteStartElement("root");
                xtw.WriteElementString("version", "1.0");
                xtw.WriteElementString("houses", houseSystem.ToString());
                xtw.WriteElementString("major", majorOrb.ToString());
                xtw.WriteElementString("minor", minorOrb.ToString());
                xtw.WriteElementString("midpoint", midpointOrb.ToString());
                xtw.WriteElementString("prog", progOrb.ToString());
                xtw.WriteElementString("chiron", chiron.ToString());
                xtw.WriteElementString("lunarnode", lunarNode.ToString());
                xtw.WriteElementString("nodetype", nodeType.ToString());
                xtw.WriteEndElement();
                xtw.WriteEndDocument();

                xtw.Close();
            }
            catch {
                // todo handle exception message
                result = false;
            }
            return result;
        }

        /// <summary>
        /// Read config from configuration file.
        /// </summary>
        /// <returns>If no errors occurred true, otherwise false.</returns>
        public Boolean readConfig() {
            Boolean result = true;
            try {
                XmlTextReader xtr = new System.Xml.XmlTextReader("radixpro.config");
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

        private void buildAspectTypes() {
            aspectTypes = new ArrayList();
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_CONJUNCTION, 0, majorOrb, true));          // conjunct
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_OPPOSITION, 180, majorOrb, true));         // opoosition
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_TRINE, 120, majorOrb, true));              // trine
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SQUARE, 90, majorOrb, true));              // square
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_QUINTILE, 72, minorOrb, false));           // quintile
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SEXTILE, 60, majorOrb, true));             // sextile
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SEPTILE, 51.428571, minorOrb, false));     // septile
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SEMISQUARE, 45, minorOrb, false));         // semisquare
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SESQUIQUADRATE, 135, minorOrb, false));    // sesquiquadrate
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_SEMISEXTILE, 30, minorOrb, false));        // semisextile
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_INCONJUNCT, 150, minorOrb, false));        // inconjunct
            aspectTypes.Add(new AspectType(Constants.C_RP_ASPECT_BIQUINTILE, 144, minorOrb, false));        // biquintile
        }


        private void parseXmlTag(String name, String value) {
            if (name.Equals("houses")) houseSystem = value[0];
            if (name.Equals("major")) majorOrb = Convert.ToInt32(value);
            if (name.Equals("minor")) minorOrb = Convert.ToInt32(value);
            if (name.Equals("midpoint")) midpointOrb = Convert.ToInt32(value);
            if (name.Equals("prog")) progOrb = Convert.ToInt32(value);
            if (name.Equals("chiron")) chiron = Convert.ToBoolean(value);
            if (name.Equals("lunarnode")) lunarNode = Convert.ToBoolean(value);
            if (name.Equals("nodetype")) nodeType = Convert.ToInt32(value);
        }

    }

}