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
using radixpro.engine;

namespace radixpro.controller {

    /// <summary>
    /// A Case contains a radix and optional a number of events.
    /// It is the class that initiates he calculations.
    /// </summary>
    public class RpCase {
        private Radix _radix;
        private ArrayList _events;
        private Configuration _config;


        /// <summary>
        /// Constructor for a Case
        /// </summary>
        /// <param name="config">Actual configuration</param>
        /// <param name="idc">Input data for a chart</param>
        public RpCase(Configuration config, InputDataChart idc) {
            this.xconfig = config;
            this.radix = new Radix(idc, config.lunarNode, config.nodeType, config.houseSystem,
                        config.aspectTypes, config.midpointOrb);
        }

        /// <summary>
        /// Calculated radix
        /// </summary>
        public Radix radix {
            get { return _radix; }
            set { _radix = value; }
        }

        /// <summary>
        /// List of events
        /// </summary>
        public ArrayList events {
            get {return _events; }
            set { _events = value; }
        }

        /// <summary>
        /// Configuration
        /// </summary>
        public Configuration xconfig {
            get { return _config; }
            set { _config = value; }
        }
    }




    /// <summary>
    /// Contains all radix related information.
    /// Calculates its own positions during instantation
    /// </summary>
    public class Radix {
        private int _status;
        private string _name;
        private string _chartType;
        private string _datetime;
        private string _location;
        private string _coordinatesText;
        private string _remarksText;
        private string _sourceText;
        private PositionSet _positionSet;
        private int _midpointOrb;
        private ArrayList _aspectTypes;
        private char _houseSystem;

       /// <summary>
       /// Constructor for Radix
       /// </summary>
       /// <param name="inputDataChart">InputDataChart, contains inputted values</param>
       /// <param name="lunarNode">True if lunar node will be used</param>
       /// <param name="nodeType">Type of node (mean or oscillating)</param>
       /// <param name="houseSystem">Indication of the housesystem to use</param>
       /// <param name="aspectTypes">List with supported aspectTypes</param>
       /// <param name="midpointOrb">Orb for midpoints</param>
       public Radix(InputDataChart inputDataChart, Boolean lunarNode, int nodeType, char houseSystem, ArrayList aspectTypes, int midpointOrb) {
          name = inputDataChart.name;
          location = inputDataChart.location;
          datetime = inputDataChart.dateText + " " + inputDataChart.timeText;
          coordinatesText = inputDataChart.coordinatesText;
          remarksText = inputDataChart.remarks;
          sourceText = inputDataChart.sourcetype +  "\r\n" +   inputDataChart.sourcedescription;
          chartType = inputDataChart.chartType;
          this.houseSystem = houseSystem;
          this.positionSet = new PositionSet(inputDataChart, lunarNode, nodeType, houseSystem);
          this.midpointOrb = midpointOrb;
          this.aspectTypes = aspectTypes;
          this.status = positionSet.status;
        }
        /// <summary>
        /// Status, possible values:
        /// C_RADIXSTATUS_ERROR, C_RADIXSTATUS_UNFINISHED, C_RADIXSTATUS_CALCUALTED, 
        /// C_RADIXSTATUS_SAVED, C_RADIXSTATUS_UNSAVEDCHANGES)
        /// TODO: check values before setting them
        /// </summary>
        public int status {
            get {return _status; }
            set {_status = value; }
        }

        /// <summary>
        /// Name for chart
        /// </summary>
        public string name {
            get {return _name; }
            set {_name = value; }
        }

        /// <summary>
        /// Text for type of chart
        /// </summary>
        public string chartType {
            get {return _chartType; }
            set {_chartType = value; }
        }


       /// <summary>
       /// Value for location
       /// </summary>
        public string location {
           get {return _location; }
           set {_location = value; }
        }

        
       /// <summary>
       /// Text for coordinates (combined longiutde and latitude)
       /// </summary>
       public string coordinatesText {
           get {return _coordinatesText; }
           set {_coordinatesText = value; }
        }


        /// <summary>
        /// Textual presentation of date and time
        /// </summary>
        public string datetime {
            get {return _datetime; }
            set {_datetime = value; }
        }


        /// <summary>
        /// Set with all positions
        /// </summary>
        public PositionSet positionSet {
            get {return _positionSet; }
            set {_positionSet = value; }
        }


        /// <summary>
        /// Orb for midpoints
        /// </summary>
        public int midpointOrb {
            get {return _midpointOrb; }
            set {_midpointOrb = value; }
        }

        /// <summary>
        /// List with supported aspecttypes
        /// </summary>
        public ArrayList aspectTypes {
            get {return _aspectTypes; }
            set {_aspectTypes = value; }
        }

        /// <summary>
        /// Character indicating the housesystem to use
        /// </summary>
        public char houseSystem {
            get {return _houseSystem; }
            set {_houseSystem = value; }
        }

        /// <summary>
        /// Text with remarks
        /// </summary>
        public string remarksText {
            get {return _remarksText; }
            set {_remarksText = value; }
        }

        /// <summary>
        /// Text with description of source(s)
        /// </summary>
        public string sourceText {
            get {return _sourceText; }
            set {_sourceText = value; }
        }

        /// <summary>
        /// Getter for RadixAnalysis
        /// TODO check for alternative using properties
        /// </summary>
        /// <returns>Calculated version of RadixAnalysis</returns>
        public RadixAnalysis getRadixAnalysis() {
            return new RadixAnalysis(positionSet, aspectTypes, midpointOrb);
        }

        /// <summary>
        /// TODO: build this method
        /// </summary>
        /// <returns>True if chart was saved</returns>
        public Boolean saveChart() {
            // TODO build
            return true;
        }

    }
}