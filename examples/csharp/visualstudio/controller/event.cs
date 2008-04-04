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
    /// A moment in time for which progressions are calculated.
    /// </summary>
    public class Event {
        private InputDataProg _inputDataProg;
        private Transits _transits;
        private Secundary _secundary;
        private bool _lunarNode;
        private int _nodeType;
        private int progOrb;
        private ArrayList aspectTypes;
        private ProgPositionSet _progPositionSet;
        private ProgAnalysis _progAnalysis;


        /// <summary>
        /// Constructor fills all required data
        /// </summary>
        /// <param name="inputDataProg">Inputvalues</param>
        /// <param name="radixJD">Julian day for radix</param>
        /// <param name="houseSystem">Indication of housesystem</param>
        /// <param name="planetaryPositions">Planetary positions in the radix</param>
        /// <param name="housePositionSet">House positions in the radix</param>
        /// <param name="lunarNode">True if lunar node should be used</param>
        /// <param name="nodeType">Type of node: mean or oscillating</param>
        /// <param name="progOrb">Orbis for progressive aspects</param>
        /// <param name="aspectTypes">list with aspecttypes</param>
        public Event(InputDataProg inputDataProg, double radixJD, char houseSystem, ArrayList planetaryPositions,
                     HousePositionSet housePositionSet, Boolean lunarNode, int nodeType, int progOrb, ArrayList aspectTypes) {
            this.progOrb = progOrb;
            this.aspectTypes = aspectTypes;
            this.inputDataProg = inputDataProg;
            this.progPositionSet = new ProgPositionSet(inputDataProg, radixJD, lunarNode, nodeType, houseSystem);
            this.progAnalysis = new ProgAnalysis(planetaryPositions, housePositionSet, progPositionSet.transits.planetaryPositions, progPositionSet.secundary.planetaryPositions, aspectTypes, progOrb);
        }

        /// <summary>
        /// Set with progressive positions
        /// </summary>
        public ProgPositionSet progPositionSet {
            get {return _progPositionSet; }
            set {_progPositionSet = value; }
        }

        /// <summary>
        /// Analysis fro progressions
        /// </summary>
        public ProgAnalysis progAnalysis {
            get {return _progAnalysis; }
            set {_progAnalysis = value; }
        }

        /// <summary>
        /// Input data for progressive calculations
        /// </summary>
        public InputDataProg inputDataProg {
            get {return _inputDataProg; }
            set {_inputDataProg = value; }
        }

        /// <summary>
        /// List of tranists
        /// </summary>
        public Transits transits {
            get {return _transits; }
            set {_transits = value; }
        }


        /// <summary>
        /// List of secundary progressions
        /// </summary>
        public Secundary secundary {
            get {return _secundary; }
            set {_secundary = value; }
        }

        /// <summary>
        /// Use lunar node
        /// </summary>
        public bool lunarNode {
            get {return _lunarNode; }
            set {_lunarNode = value; }
        }

        /// <summary>
        /// Type of lunar node
        /// </summary>
        public int nodeType {
            get {return _nodeType; }
            set {_nodeType = value; }
        }

    }

}