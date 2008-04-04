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
using radixpro.data;

namespace radixpro.controller {
   
   /// <summary>
   /// Parent for containers for inputted data
   /// </summary>
   abstract public class InputData {
      private string _dateText;
      private string _timeText;
      private string _location;
      private string _longitudeText;
      private string _latitudeText;
      private string _coordinatesText;
      private string _remarks;
      private string _sourcedescription;
      private string _sourcetype;
      private double _jdnr;
      private double _longitude;
      private double _latitude;
      private double _zoneOffset;
      private double _dst;
      private int _calendar;




      /// <summary>
      /// Text for location
      /// </summary>
      public string location {
         get {return _location; }
         set {_location = value; }
      }

      /// <summary>
      /// Text for coordinates
      /// </summary>
      public string coordinatesText {
         get {return _coordinatesText; }
         set {_coordinatesText = value; }
      }


        /// <summary>
        /// Value for remarks
        /// </summary>
        public string remarks {
            get {return _remarks; }
            set {_remarks = value; }
        }

        /// <summary>
        /// Value for sourcedescription
        /// </summary>
        public string sourcedescription {
            get {return _sourcedescription; }
            set {_sourcedescription = value; }
        }


        /// <summary>
        /// Value for sourcedetype
        /// </summary>
        public string sourcetype {
            get {return _sourcetype; }
            set {_sourcetype = value; }
        }


        /// <summary>
        /// Value for dateText
        /// </summary>
        public string dateText {
            get {return _dateText; }
            set {_dateText = value; }
        }

        /// <summary>
        /// Value for timeText
        /// </summary>
        public string timeText {
            get {return _timeText; }
            set {_timeText = value; }
        }


        /// <summary>
        /// Value for longitudeText
        /// </summary>
        public string longitudeText {
            get {return _longitudeText; }
            set {_longitudeText = value; }
        }


        /// <summary>
        /// Value for latitudeText
        /// </summary>
        public string latitudeText {
            get {return _latitudeText; }
            set {_latitudeText = value; }
        }


        /// <summary>
        /// Value for jdnr (Julian Day Number)
        /// </summary>
        public double jdnr {
            get {return _jdnr; }
            set {_jdnr = value; }
        }



        /// <summary>
        /// Value for longitude
        /// </summary>
        public double longitude {
            get {return _longitude; }
            set {_longitude = value; }
        }


        /// <summary>
        /// Value for latitude
        /// </summary>
        public double latitude {
            get {return _latitude; }
            set {_latitude = value; }
        }

        /// <summary>
        /// Value for offset timezone
        /// </summary>
        public double zoneOffset {
            get {return _zoneOffset; }
            set {_zoneOffset= value; }
        }

        /// <summary>
        /// Value for dst
        /// </summary>
        public double dst {
            get {return _dst; }
            set {_dst = value; }
        }

        /// <summary>
        /// Value for calendar
        /// </summary>
        public int calendar {
            get {return _calendar; }
            set {_calendar = value; }
        }

    }

    /// <summary>
    /// Container for inputted data to calculate a chart.
    /// </summary>
    public class InputDataChart : InputData {
        private string _name;
        private string _chartType;



        /// <summary>
        /// Constructor creates st with calculated input data from a value object with inputted strings
        /// </summary>
        /// <param name="voir">Value object with the inputted data</param>
        public InputDataChart(VOInputDataRadix voir) {
            name = voir.name.data;
            chartType = voir.chartType.data;
            // handle timezone
            TimeZoneList tzl = new TimeZoneList();
            RpTimeZone[] tz = tzl.theList;
            string zoneText;
            double offsetValue = 0;
            if (voir.specTimezone.filled) {
                offsetValue = (double)Conversions.TimeText2Double(voir.specTimezone.data);
                if (voir.specTimezoneDir.data == "-") offsetValue = -offsetValue;
                zoneText = voir.specTimezone.data;
            } else {
                offsetValue = Convert.ToDouble(tz[Convert.ToInt32(voir.timezone.data)].offset);
                zoneText = Conversions.double2OffsetHms(offsetValue);
            }

            this.dateText = voir.date.data;
            this.timeText = voir.time.data + ". " + ResourceBundle.RB_COM_ZONE + ": " + zoneText + ". "
                          + ResourceBundle.RB_COM_DST + ": " + voir.dst.data + ".";
            this.dst = Convert.ToDouble(voir.dst.data);
            this.calendar = Convert.ToInt32(voir.calendar.data);

            this.location = voir.location.data;

            string direction = "";

            if (voir.geoLongDirection.data == "+") direction = ResourceBundle.RB_COM_LONG_EAST;
            else direction = ResourceBundle.RB_COM_LONG_WEST;

            this.longitudeText = voir.geoLongitude.data + " " + direction;
            this.longitude = Conversions.Coordinates2Degrees(voir.geoLongitude.data, direction);

            if (voir.geoLatDirection.data == "+") direction = ResourceBundle.RB_COM_LAT_NORTH;
            else direction = ResourceBundle.RB_COM_LAT_SOUTH;

            this.latitudeText = voir.geoLatitude.data + " " + direction;
            this.latitude = Conversions.Coordinates2Degrees(voir.geoLatitude.data, direction);

            this.coordinatesText = longitudeText + " - " + latitudeText;

            int[] dateArray = Conversions.DateText2IntArray(voir.date.data);
            int[] timeArray = Conversions.TimeText2IntArray(voir.time.data);

            double dstValue = 0;
            if (voir.dst.filled) {
                dstValue = Convert.ToDouble(voir.dst.data);
            }

            this.sourcedescription = voir.sourceDescription.data;
            this.sourcetype = voir.sourceType.data;
            this.remarks = voir.remarks.data;

            JdBuilder jdb = new JdBuilder(dateArray[0], dateArray[1], dateArray[2],
                                          timeArray[0], timeArray[1], timeArray[2],
                                          calendar, offsetValue, dstValue);
            this.jdnr = jdb.jd;
        }

        /// <summary>
        /// Name for chart
        /// </summary>
        public string name {
            get { return _name; }
            set { _name = value; }
        }

        /// <summary>
        /// Type of chart
        /// </summary>
        public string chartType {
            get { return _chartType; }
            set { _chartType = value; }
        }
    }


    /// <summary>
    /// Container for inputted data for progressions
    /// </summary>
    public class InputDataProg : InputData {
        private bool _useMoon4Transits;
        private bool _useInnerPlanets4Transits;
        private bool _relocate;

        /// <summary>
        /// Indicates if transiting Moon will be used, does not 
        /// affect transits to radix Moon
        /// </summary>
        public bool useMoon4Transits {
            get {return _useMoon4Transits; }
            set {_useMoon4Transits = value; }
        }

        /// <summary>
        /// Indicates if Sun, Mercure and Venus will be used, does not 
        /// affect transits to these bodies
        /// </summary>
        public bool useInnerPlanets4Transits {
            get {return _useInnerPlanets4Transits; }
            set {_useInnerPlanets4Transits = value; }
        }

        /// <summary>
        /// True if location during event will be used, false if radical location is used
        /// </summary>
        public bool relocate {
            get {return _relocate; }
            set {_relocate = value; }
        }

    }

}