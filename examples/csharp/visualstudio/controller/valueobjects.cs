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


using radixpro.engine;

namespace radixpro.controller {

    /// <summary>
    /// General class for positions, is parent for specific positions.
    /// </summary>
    public class VOLongPosition {
        private double _fullLongitude;
        private double _signLongitude;
        private string _signGlyph;
        private string _displayText;

        /// <summary>
        /// Dummy constructor
        /// </summary>
        public VOLongPosition() {
            fullLongitude = -1;
        }

        /// <summary>
        /// Constructor expects longitude
        /// </summary>
        /// <param name="x">Longitude of position</param>
        public VOLongPosition(double x) {
            fullLongitude = x;
            signLongitude = Conversions.toRange(x, 0, 30);
            signGlyph = Conversions.signGlyphFromLong(x);
            displayText = Conversions.deg2sexagesimal(x,true,false);
        }



        /// <summary>
        /// Longitude calculated from 0 Aries
        /// </summary>
        public double fullLongitude {
            get {return _fullLongitude; }
            set {_fullLongitude = value; }
        }

        /// <summary>
        /// Longitude within sign
        /// </summary>
        public double signLongitude {
            get {return _signLongitude; }
            set {_signLongitude = value; }
        }

        /// <summary>
        /// Glyph for sign for font RadixPro
        /// </summary>
        public string signGlyph {
            get {return _signGlyph; }
            set {_signGlyph = value; }
        }

        /// <summary>
        /// Text with degree/minute/second character
        /// </summary>
        public string displayText {
            get {return _displayText; }
            set {_displayText = value; }
        }


    }

    /// <summary>
    /// Position for a planet or other body
    /// </summary>
    public class VOLongBody : VOLongPosition {
        string _direction;
        string _bodyGlyph;

        /// <summary>
        /// Constructor for position for planet/body.
        /// </summary>
        /// <param name="index">Index of body (refers to constants)</param>
        /// <param name="longitude">Zodiacal longitude</param>
        /// <param name="speed">Daily speed</param>
        public VOLongBody(int index, double longitude, double speed): base (longitude) {
            if (speed < 0) direction = "R"; else direction = "";
            bodyGlyph = Conversions.bodyGlyphFromIndex(index);
  
        }

        /// <summary>
        /// Constructor expects planetaryPosition
        /// </summary>
        /// <param name="x">planetaryposition</param>
        public VOLongBody(PlanetaryPosition x): base (x.longitude)  {
            if (x.speedLongitude < 0) direction = "R"; else direction = "";
            signGlyph = Conversions.signGlyphFromLong(fullLongitude);
        }


        /// <summary>
        /// Indicates retrogradation with R. if direct value is empty string
        /// </summary>
        public string direction {
            get {return _direction; }
            set {_direction = value; }
        }

        /// <summary>
        /// Glyph for body, to be used with font RadixPro
        /// </summary>
        public string bodyGlyph {
            get {return _bodyGlyph; }
            set {bodyGlyph = value; }
        }

    }


    /// <summary>
    /// Position for a housecusp
    /// </summary>
    public class VOLongHouseCusp : VOLongPosition {
        private int _cuspId;

        /// <summary>
        /// Constructor for position for housecusp.
        /// </summary>
        /// <param name="index">Index of cusp</param>
        /// <param name="longitude">Zodiacal longitude</param>
        public VOLongHouseCusp(int id, double longitude): base (longitude) {
            this.cuspId = id;    

        }

        /// <summary>
        /// Id for cusp
        /// </summary>
        public int cuspId {
            get {return _cuspId; }
            set {_cuspId = value; }
        }

    }



    /// <summary>
    /// Item in a value object, represents data in a string and a status (filled
    /// or not filled) in a bool
    /// </summary>
    public class VOItem {
        private bool _filled;
        private string _data;

        /// <summary>
        /// Constructor for empty VOItem
        /// </summary>
        public VOItem() {
            this.filled = false;
            this.data = null;
        }

        /// <summary>
        /// Constructor for fully defined VOItem
        /// </summary>
        public VOItem(string data, bool filled) {
            this.data = data;
            this.filled = filled;
        }

        /// <summary>
        /// True if item contains data, otherwise false
        /// </summary>
        public bool filled {
            get {return _filled; }
            set {_filled = value; }
        }

        /// <summary>
        /// Content of the item
        /// </summary>
        public string data {
            get {return _data; }
            set {_data = value; }
        }
    }





    /// <summary>
    /// Value Object for input data
    /// </summary>
    public abstract class VOInputData {

        private VOItem _location = new VOItem();
        private VOItem _geoLatitude = new VOItem();
        private VOItem _geoLatDirection = new VOItem();
        private VOItem _geoLongitude = new VOItem();
        private VOItem _geoLongDirection = new VOItem();
        private VOItem _date = new VOItem();
        private VOItem _calendar = new VOItem();
        private VOItem _time = new VOItem();
        private VOItem _dst = new VOItem();
        private VOItem _timezone = new VOItem();
        private VOItem _specTimezone = new VOItem();
        private VOItem _specTimezoneDir = new VOItem();
        private VOItem _remarks = new VOItem();

        /// <summary>
        /// If set: VOItem with text for location (cityname) else VOItem with filled FALSE
        /// </summary>
        public VOItem location {
            get {return _location; }
            set {_location = value; }
        }


        /// <summary>
        /// If set: VOItem with text for geographic latitude in format dd.mm.ss else VOItem with filled FALSE
        /// </summary>
        public VOItem geoLatitude {
            get {return _geoLatitude; }
            set {_geoLatitude = value; }
        }

        /// <summary>
        /// If set: VOItem with 1 char string for latitude direction (+ = northern latitude, - = southern latitude)
        /// </summary>
        public VOItem geoLatDirection {
            get {return _geoLatDirection; }
            set {_geoLatDirection = value; }
        }


        /// <summary>
        /// If set: VOItem with text for geographic longitude in format ddd.mm.ss else VOItem with filled FALS
        /// </summary>
        public VOItem geoLongitude {
            get {return _geoLongitude; }
            set {_geoLongitude = value; }
        }

        /// <summary>
        /// If set: VOItem with 1 char string for longitude direction (+ = eastern longitude, - = western longitude)
        /// </summary>
        public VOItem geoLongDirection {
            get {return _geoLongDirection; }
            set {_geoLongDirection = value; }
        }


        /// <summary>
        /// If set: VOItem with text for date, format yyyy.mm.dd, else VOItem with filled FALSE
        /// </summary>
        public VOItem date {
            get {return _date; }
            set {_date = value; }
        }

        /// <summary>
        /// If set: VOItem with text for calendar (string for integer), else VOItem with filled FALSE
        /// </summary>
        public VOItem calendar {
            get {return _calendar; }
            set {_calendar = value; }
        }



        /// <summary>
        /// If set: VOItem with text for time, format hh:mm:ss, else VOItem with filled FALSE
        /// </summary>
        public VOItem time {
            get {return _time; }
            set {_time = value; }
        }

        /// <summary>
        /// If set: VOItem with text for dst, else VOItem with filled FALSE
        /// </summary>
        public VOItem dst {
            get {return _dst; }
            set {_dst = value; }
        }

        /// <summary>
        /// If set: VOItem with text for timezone, else VOItem with filled FALSE
        /// </summary>
        public VOItem timezone {
            get {return _timezone; }
            set {_timezone = value; }
        }


        /// <summary>
        /// If set: VOItem with text for specTimezone, else VOItem with filled FALSE
        /// </summary>
        public VOItem specTimezone {
            get {return _specTimezone; }
            set {_specTimezone = value; }
        }

        /// <summary>
        /// If set: VOItem with text for specTimezoneDir, else VOItem with filled FALSE
        /// </summary>
        public VOItem specTimezoneDir {
            get {return _specTimezoneDir; }
            set {_specTimezoneDir = value; }
        }

        /// <summary>
        /// If set: VOItem with text for remarks, else VOItem with filled FALSE
        /// </summary>
        public VOItem remarks {
            get {return _remarks; }
            set {_remarks = value; }
        }


    }


    /// <summary>
    /// Value object for inputted data for radix
    /// </summary>
    public class VOInputDataRadix : VOInputData {
        private VOItem _name = new VOItem();
        private VOItem _chartType = new VOItem();
        private VOItem _sourceType = new VOItem();
        private VOItem _sourceDescription = new VOItem();

        /// <summary>
        /// Item that contains a name
        /// </summary>
        public VOItem name {
            get {return _name; }
            set {_name = value; }
        }

        /// <summary>
        /// If set: VOItem with text for charttype, else VOItem with filled FALSE
        /// </summary>
        public VOItem chartType {
            get {return _chartType; }
            set {
               _chartType.data = value.data;
               _chartType.filled = true;
            }
        }

        /// <summary>
        /// If set: VOItem with text for sourcetype, else VOItem with filled FALSE
        /// </summary>
        public VOItem sourceType {
            get {return _sourceType; }
            set {
                _sourceType.data = value.data;
                _sourceType.filled = true;
            }
        }

        /// <summary>
        /// If set: VOItem with text for sourceDescription, else VOItem with filled FALSE
        /// </summary>
        public VOItem sourceDescription {
            get {return _sourceDescription; }
            set {_sourceDescription = value; }
        }

    }



    /// <summary>
    /// Value object for inputted data for progressions
    /// </summary>
    public class VOInputDataProg : VOInputData {
        private VOItem _anEvent = null;     // event is a reserved word

        /// <summary>
        /// Event
        /// </summary>
        public VOItem anEvent {
            get {return _anEvent; }
            set {_anEvent.data = value.data; }

        }

    }

}