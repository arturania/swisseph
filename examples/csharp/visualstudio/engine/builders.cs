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
using radixpro.controller;


namespace radixpro.engine {

    /// <summary>
    /// Constructs planetary positions
    /// </summary>
    public class PlanetaryPositionsBuilder {

        private double jdnr;
        private ArrayList _planetaryPositionList;
        private bool lunarNode;
        private int nodeType;


        /// <summary>
        /// Constructor, set path for SE, defines jdnr and constructs planetary positions.
        /// </summary>
        /// <param name="jdnr">Value for jdnr</param>
        /// <param name="lunarNode">Indicates if lunar node should be used</param>
        /// <param name="nodeType">Type of lunar node</param>
        public PlanetaryPositionsBuilder(double jdnr, Boolean lunarNode, int nodeType) {
            this.jdnr = jdnr;
            this.lunarNode = lunarNode;
            this.nodeType = nodeType;

            string sedir = Environment.CurrentDirectory + "\\sweph\\";

            Sweph.setEphePath(sedir);

            planetaryPositionList = new ArrayList();
            constructPositions();
        }



        private void constructPositions() {
            // standard planets
            for (int i = Constants.SE_SUN; i <= Constants.SE_PLUTO; i++) {
                double[] x = Sweph.getPlanet(i, jdnr);
                PlanetaryPosition pp = new PlanetaryPosition(i, x);
                planetaryPositionList.Add(pp);
            }
            // mean node if defined in config
            if (lunarNode && nodeType == Constants.SE_MEAN_NODE) {
                double[] x = Sweph.getPlanet(Constants.SE_MEAN_NODE, jdnr);
                PlanetaryPosition pp = new PlanetaryPosition(Constants.SE_MEAN_NODE, x);
                planetaryPositionList.Add(pp);
            }
            // oscillating node if defined in config
            if (lunarNode && nodeType == Constants.SE_TRUE_NODE) {
                double[] x = Sweph.getPlanet(Constants.SE_TRUE_NODE, jdnr);
                PlanetaryPosition pp = new PlanetaryPosition(Constants.SE_TRUE_NODE, x);
                planetaryPositionList.Add(pp);
            }
        }

        /// <summary>
        /// List with planetary positions
        /// </summary>
        public ArrayList planetaryPositionList {
            get {return _planetaryPositionList; }
            set {_planetaryPositionList = value; }
        }

    }

    /// <summary>
    /// Constructs house positions
    /// </summary>
    public class HousePositionsBuilder {

        private double jdnr;
        private double latitude;
        private double longitude;
        private char houseSystem;
        private HousePositionSet _housePositionSet;

        /// <summary>
        /// Constructor, set path for Se, defines jdnr and constructs house positions.
        /// </summary>
        /// <param name="jdnr">Value for jdnr</param>
        /// <param name="latitude">Latitude of location</param>
        /// <param name="longitude">Longitude of location</param>
        /// <param name="houseSystem">Housesystem to use</param>
        public HousePositionsBuilder(double jdnr, double latitude, double longitude, char houseSystem) {
            this.jdnr = jdnr;
            this.latitude = latitude;
            this.longitude = longitude;
            this.houseSystem = houseSystem;
            string sedir = Environment.CurrentDirectory + "\\sweph\\";
            Sweph.setEphePath(sedir);
            constructPositions();
        }

        private void constructPositions() {
            double[] x = Sweph.getHouses(jdnr, latitude, longitude, houseSystem);
            housePositionSet = new HousePositionSet(houseSystem, x);
        }

        /// <summary>
        /// House positions
        /// </summary>
        public HousePositionSet housePositionSet {
            get {return _housePositionSet; }
            set {_housePositionSet = value; }
        }

    }


    /// <summary>
    /// Builder for Julian Day Number
    /// </summary>
    public class JdBuilder {
        int day;
        int month;
        int year;
        double hours;
        double minutes;
        double seconds;
        int calendar;
        double dst;
        double zoneOffset;
        double _jd;

        /// <summary>
        /// Constructor for JdBuilder
        /// </summary>
        /// <param name="year">Actual year</param>
        /// <param name="month">Actual month</param>
        /// <param name="day">Actual day</param>
        /// <param name="hours">Actual horus (no fractions)</param>
        /// <param name="minutes">Actual minutes</param>
        /// <param name="seconds">Actual seconds</param>
        /// <param name="calendar">Calendar, SE_JUL_CAL or SE_GREG_CAL</param>
        /// <param name="dst">Value for daylight saving time</param>
        /// <param name="zoneOffset">Zone offset in fractional hours</param>
        public JdBuilder(int year, int month, int day, int hours, int minutes, int seconds, int calendar, double zoneOffset, double dst) {
            this.day = day;
            this.month = month;
            this.year = year;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.calendar = calendar;
            this.zoneOffset = zoneOffset;
            this.dst = dst;
            calcJd();
        }

        private void calcJd() {
            int dayCorrection = 0;
            double hourFract = hours - zoneOffset - dst + minutes / 60 + seconds / 3600;
            if (hourFract >= 24) {
                hourFract -= 24;
                dayCorrection = 1;
            }
            if (hourFract < 0) {
                hourFract += 24;
                dayCorrection = -1;
            }
            this.jd = Sweph.getJD(year, month, day, hourFract, calendar) + dayCorrection;

        }

        /// <summary>
        /// Julian Day Number (jd)
        /// </summary>
        public double jd {
            get {return _jd; }
            set {_jd = value; }
        }
    }
}