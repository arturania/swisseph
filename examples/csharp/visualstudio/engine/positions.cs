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
using radixpro.engine;

namespace radixpro.engine {

    /// <summary>
    /// Container for planetary positions and housepositions
    /// </summary>
    public class PositionSet {
        private ArrayList _planetaryPositions;
        private HousePositionSet _housePositionSet;
        private int _status;

        /// <summary>
        /// Constructor, takes care of calculations
        /// </summary>
        /// <param name="inputDataChart">Data for chart</param>
        /// <param name="lunarNode">True if lunarnode should be used</param>
        /// <param name="nodeType">Type of node (mean or oscillating)</param>
        /// <param name="houseSystem">Indication of housesystem</param>
        public PositionSet(InputDataChart inputDataChart, Boolean lunarNode, int nodeType, char houseSystem) {
            this.status = Constants.C_RADIXSTATUS_UNFINISHED;
            try {
                PlanetaryPositionsBuilder pb = new PlanetaryPositionsBuilder(inputDataChart.jdnr, lunarNode, nodeType);
                planetaryPositions = pb.planetaryPositionList;
                HousePositionsBuilder hb = new HousePositionsBuilder(inputDataChart.jdnr, inputDataChart.latitude,
                                                                     inputDataChart.longitude, houseSystem);
                housePositionSet = hb.housePositionSet;
                this.status = Constants.C_RADIXSTATUS_CALCULATED;
            }
            catch {
                this.status = Constants.C_RADIXSTATUS_ERROR;
            }
        }

        /// <summary>
        /// List with planetary positions
        /// </summary>
        public ArrayList planetaryPositions {
            get {return _planetaryPositions; }
            set {_planetaryPositions = value; }
        }

        /// <summary>
        /// HousePositionSet containing all house positions.
        /// </summary>
        public HousePositionSet housePositionSet {
            get {return _housePositionSet; }
            set {_housePositionSet = value; }
        }


        /// <summary>
        /// Status
        /// </summary>
        public int status {
            get {return _status; }
            set {_status = value; }
        }

    }


    /// <summary>
    /// Container for data of a planet or lunar node.
    /// </summary>
    public class PlanetaryPosition {
        private double _longitude;
        private double _latitude;
        private double _distance;
        private double _speedLongitude;
        private double _rightAscension;
        private double _declination;
        private int _planetId;

        /// <summary>
        /// Constructor with only id for planetary index. Creates array to hold values.
        /// </summary>
        /// <param name="planetId">Id for pantary index.</param>
        public PlanetaryPosition(int planetId) {
            this.planetId = planetId;
        }

        /// <summary>
        /// Constructor that defines all data
        /// </summary>
        /// <param name="id">id for planetary body</param>
        /// <param name="seData">results from calculations by Swiss Ephemeris,
        ///        holds: longitude, latitude, distance, speed in long., speed in lat. (not used), 
        ///        speed in dist. (not used), right ascension and declination</param>     
        public PlanetaryPosition(int id, double[] seData) {
            this.planetId = id;
            this.longitude = seData[0];
            this.latitude = seData[1];
            this.distance = seData[2];
            this.speedLongitude = seData[3];
            this.rightAscension = seData[6];
            this.declination = seData[7];
        }

        /// <summary>
        /// Longiutde, new values will be converted to a range of 0..360
        /// </summary>
        public double longitude {
            get { return _longitude; }
            set { _longitude = Conversions.toRange(value, 0, 360); }
        }

        /// <summary>
        /// Latitude, new values will be converted to a range of -90..+90
        /// </summary>
        public double latitude {
            get { return _latitude; }
            set { _latitude = Conversions.toRange(value, -90, 90); }
        }

        /// <summary>
        /// Distance of body (radius vector) in AU
        /// </summary>
        public double distance {
            get { return _distance; }
            set { _distance = value; }
        }

        /// <summary>
        /// Speed in longitude
        /// </summary>
        public double speedLongitude {
            get { return _speedLongitude;  }
            set { _speedLongitude = value; }
        }

        /// <summary>
        /// Right ascension in degrees
        /// </summary>
        public double rightAscension {
            get { return _rightAscension; }
            set { _rightAscension = value; }
        }

        /// <summary>
        /// Declination
        /// </summary>
        public double declination {
            get { return _declination; }
            set { _declination = value; }
        }



        /// <summary>
        /// Id of planet
        /// </summary>
        public int planetId {
            get { return _planetId; }
            set { _planetId = value; }
        }

    }


    /// <summary>
    /// Container for housepositions
    /// </summary>
    public class HousePositionSet {
        double[] _housePositions;
        char _systemIndex;

        /// <summary>
        /// Obligatory constructor
        /// </summary>
        /// <param name="systemIndex">Character that indicates the housesystem, see constants for values</param>
        /// <param name="positions">double[23] array with values</param>
        public HousePositionSet(char systemIndex, double[] positions) {
            this.systemIndex = systemIndex;
            this.housePositions = positions;
        }

        /// <summary>
        /// Index of housesystem.
        /// </summary>
        public char systemIndex {
            get { return _systemIndex; }
            set { _systemIndex = value; }
        }

        /// <summary>
        /// Array with all house related positions
        /// </summary>
        public double[] housePositions {
            get { return _housePositions; }
            set { _housePositions = value; }
        }

        /// <summary>
        /// Getter for ascendant
        /// </summary>
        /// <returns>Longitude of ascendant</returns>
        public double getAsc() {
            return housePositions[13];
        }

        /// <summary>
        /// Getter for Medium Coeli
        /// </summary>
        /// <returns>Longitude of Medium Coeli</returns>
        public double getMC() {
            return housePositions[14];
        }

        /// <summary>
        /// Getter for cusp
        /// </summary>
        /// <param name="cusp">Index for cusp 1..12</param>
        /// <returns>Longitude for cusp</returns>
        public double getCusp(int cusp) {
            return housePositions[cusp];
        }


    }



    /// <summary>
    /// Container for progressed positions for specific progressive techniques.
    /// </summary>
    public class ProgPositionSet {

        private Transits _transits;
        private Secundary _secundary;
        private int _status;

        /// <summary>
        /// Constructor, takes care of calculations
        /// </summary>
        /// <param name="inputDataprog">Data for progressions</param>
        /// <param name="jdRadix">Julian Day number or radixchart</param>
        /// <param name="lunarNode">True if lunarnode should be used</param>
        /// <param name="nodeType">Type of node (mean or oscillating)</param>
        /// <param name="houseSystem">Indication of housesystem</param>
        public ProgPositionSet(InputDataProg inputDataprog, double jdRadix, Boolean lunarNode, int nodeType, char houseSystem) {
            this.status = Constants.C_PROGSTATUS_UNFINISHED;   // TODO change
            try {
                transits = new Transits(inputDataprog, lunarNode, nodeType);
                secundary = new Secundary(inputDataprog, jdRadix, lunarNode, nodeType);
                this.status = Constants.C_PROGSTATUS_CALCULATED;
            }
            catch {
                this.status = Constants.C_PROGSTATUS_ERROR;
            }
        }

        /// <summary>
        /// Positions for transits
        /// </summary>
        public Transits transits {
            get {return _transits;  }
            set {_transits = value; }
        }

        /// <summary>
        /// Positions for secundary progressions
        /// </summary>
        public Secundary secundary {
            get {return _secundary; }
            set {_secundary = value; }
        }

        /// <summary>
        /// Status of calculations
        /// </summary>
        public int status {
            get {return _status; }
            set {_status = value; }
        }

    }



    /// <summary>
    /// Container for Progressed Positions. Is used to be inherited.
    /// </summary>
    abstract public class ProgPositions {
        private InputDataProg _inputDataProg;
        private ArrayList _planetaryPositions;
        /// <summary>
        /// Inputted data for progressions
        /// </summary>
        public InputDataProg inputDataProg {
            get {return _inputDataProg; }
            set {_inputDataProg = value; }
        }
        /// <summary>
        /// List with planetary positions
        /// </summary>
        public ArrayList planetaryPositions {
            get {return _planetaryPositions; }
            set {_planetaryPositions = value; }
        }
    }


    /// <summary>
    /// Container and calculator for transits
    /// </summary>
    public class Transits : ProgPositions {

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="ipd">Contains inputted data</param>
        /// <param name="lunarNode">True if lunar node should be used</param>
        /// <param name="nodeType">Type of node: mean of oscillating</param>
        public Transits(InputDataProg ipd, Boolean lunarNode, int nodeType) {
            this.inputDataProg = ipd;
            this.planetaryPositions = new ArrayList();
            calcIt(lunarNode, nodeType);
        }

        private void calcIt(Boolean lunarNode, int nodeType) {
            PlanetaryPositionsBuilder ppb = new PlanetaryPositionsBuilder(inputDataProg.jdnr, lunarNode, nodeType);
            int id = -1;
            for (int i = 0; i < ppb.planetaryPositionList.Count; i++) {
                id = ((PlanetaryPosition)ppb.planetaryPositionList[i]).planetId;
                if ((id != Constants.SE_MOON || inputDataProg.useMoon4Transits)
                  && (id > Constants.SE_VENUS || inputDataProg.useInnerPlanets4Transits)) {
                    this.planetaryPositions.Add((PlanetaryPosition)ppb.planetaryPositionList[i]);
                }
            }
        }
    }

    /// <summary>
    /// Container and calculator for secundary progressions
    /// </summary>
    public class Secundary : ProgPositions {
        private double radixJD;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="ipd">Contains inputted data</param>
        /// <param name="radixJD">Julian Day for radix</param>
        /// <param name="lunarNode">True if lunar node should be used</param>
        /// <param name="nodeType">Type of node: mean of oscillating</param>
        public Secundary(InputDataProg ipd, double radixJD, Boolean lunarNode, int nodeType) {
            this.inputDataProg = ipd;
            this.radixJD = radixJD;
            this.planetaryPositions = new ArrayList();
            calcIt(lunarNode, nodeType);
        }


        private void calcIt(Boolean lunarNode, int nodeType) {
            // define jd for secundary positions
            double timeSpanYears = inputDataProg.jdnr - radixJD;
            double timeSpanDays = timeSpanYears / 365.24;   // mean tropical year    
            double progrJD = radixJD + timeSpanDays;

            PlanetaryPositionsBuilder ppb = new PlanetaryPositionsBuilder(progrJD, lunarNode, nodeType);
            int id = -1;
            for (int i = 0; i < ppb.planetaryPositionList.Count; i++) {
                id = ((PlanetaryPosition)ppb.planetaryPositionList[i]).planetId;
                if (id <= Constants.SE_SATURN) {
                    this.planetaryPositions.Add((PlanetaryPosition)ppb.planetaryPositionList[i]);
                }
            }
        }
    }

}