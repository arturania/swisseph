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
    /// Frontend to all radix analysis
    /// </summary>
    public class RadixAnalysis {
        private AspectsAnalysis _aspectsAnalysis;
        private MidpointsAnalysis _midpointsAnalysis;

        /// <summary>
        /// Analysis for a radix, supports a set of analysing techniques
        /// </summary>
        /// <param name="positionSet">Postions of bodies and houses</param>
        /// <param name="aspectTypes">Supported aspecttypes</param>
        /// <param name="midpointOrb">Orbis for midpoints</param>
        public RadixAnalysis(PositionSet positionSet, ArrayList aspectTypes, double midpointOrb) {
            aspectsAnalysis = new AspectsAnalysis(aspectTypes, positionSet.planetaryPositions, positionSet.housePositionSet);
            midpointsAnalysis = new MidpointsAnalysis(positionSet.planetaryPositions, positionSet.housePositionSet, midpointOrb);

        }

        /// <summary>
        /// Analysed set for midpoints
        /// </summary>
        public MidpointsAnalysis midpointsAnalysis {
            get {return _midpointsAnalysis; }
            set {_midpointsAnalysis = value; }
        }

        /// <summary>
        /// Analyses set for aspects
        /// </summary>
        public AspectsAnalysis aspectsAnalysis {
            get {return _aspectsAnalysis; }
            set {_aspectsAnalysis = value; }
        }
    }


    /// <summary>
    /// Generic class for items that are used in analysis, like aspects and midpoints
    /// </summary>
    public class AnalysedItem {
        private int _index;
        private double _longitude;

        /// <summary>
        /// Constructor. Initializes members.
        /// </summary>
        /// <param name="index">Index that indicates the body involved, uses constants SE_SUN and up,
        /// including SE_ASC_AS_BODY and SE_MC_AS_BODY for resp. Ascendant and MC</param>
        /// <param name="longitude"></param>
        public AnalysedItem(int index, double longitude) {
            this.index = index;
            this.longitude = longitude;
        }

        /// <summary>
        /// Index of analysed item
        /// </summary>
        public int index {
            get {return _index; }
            set {_index = value; }
        }

        /// <summary>
        /// Value for longitude of analysed item
        /// </summary>
        public double longitude {
            get {return _longitude; }
            set {_longitude = value; }
        }

    }



    /// <summary>
    /// Builds ArrayList with actual aspects in Radix.
    /// </summary>
    public class AspectsAnalysis {
        private ArrayList _aspects;
        private ArrayList analysedItems;
        private ArrayList planetaryPositions;
        private ArrayList aspectTypes;
        private HousePositionSet housePositionSet;

        /// <summary>
        /// Constructor takes care of the calculation of the aspects
        /// </summary>
        /// <param name="aspectTypes">List with aspect types</param>
        /// <param name="planetaryPositions">Planetary positons in longitude</param>
        /// <param name="housePositionSet">Positions of houses</param>
        public AspectsAnalysis(ArrayList aspectTypes, ArrayList planetaryPositions, HousePositionSet housePositionSet) {
            this.planetaryPositions = planetaryPositions;
            this.housePositionSet = housePositionSet;
            this.aspectTypes = aspectTypes;
            aspects = new ArrayList();
            analysedItems = new ArrayList();
            calculateAspects();
        }

        private void calculateAspects() {
            // build list to check
            PlanetaryPosition pp; ;
            for (int i = 0; i < planetaryPositions.Count; i++) {
                pp = (PlanetaryPosition)planetaryPositions[i];
                analysedItems.Add(new AnalysedItem(pp.planetId, pp.longitude));
            }
            // add Ascendant and MC
            analysedItems.Add(new AnalysedItem(Constants.SE_ASC_AS_BODY, housePositionSet.getAsc()));
            analysedItems.Add(new AnalysedItem(Constants.SE_MC_AS_BODY, housePositionSet.getMC()));

            // analyse list
            double itemsDistance1;
            double itemsDistance2;
            double normDistance;
            double orbis;
            double actOrbis;
            double long1;
            double long2;
            AnalysedItem item1;
            AnalysedItem item2;
            //   AnalysedItem swapItem;
            for (int i = 0; i < analysedItems.Count; i++) {
                item1 = (AnalysedItem)analysedItems[i];
                for (int j = i + 1; j < analysedItems.Count; j++) {
                    item2 = (AnalysedItem)analysedItems[j];
                    long1 = Math.Min(item1.longitude, item2.longitude);
                    long2 = Math.Max(item1.longitude, item2.longitude);
                    itemsDistance1 = long2 - long1;
                    itemsDistance2 = long1 - long2 + 360;
                    for (int k = 0; k < aspectTypes.Count; k++) {
                        AspectType aspectType = (AspectType)aspectTypes[k];
                        normDistance = aspectType.distance;
                        orbis = aspectType.orbis;
                        if ((Math.Abs(itemsDistance1 - normDistance)) <= orbis) {   // aspect found
                            actOrbis = (Math.Abs(itemsDistance1 - normDistance));
                            aspects.Add(new Aspect(item1.index, item2.index, actOrbis, aspectType));
                        }
                        else
                            if ((Math.Abs(itemsDistance2 - normDistance)) <= orbis) {   // aspect found
                                actOrbis = (Math.Abs(itemsDistance2 - normDistance));
                                aspects.Add(new Aspect(item1.index, item2.index, actOrbis, aspectType));
                            }
                    }
                }
            }
        }

        /// <summary>
        /// List with calculated aspects
        /// </summary>
        public ArrayList aspects {
            get {return _aspects; }
            set {_aspects = value; }
        }
    }


    /// <summary>
    /// Builds ArrayList with actual midpoints in Radix.
    /// </summary>
    public class MidpointsAnalysis {
        private ArrayList analysedItems;
        private ArrayList _midpoints;
        private ArrayList planetaryPositions;
        private HousePositionSet housePositionSet;
        private double midpointOrb;

        /// <summary>
        /// Constructor takes care of calculating the midpoints
        /// </summary>
        /// <param name="planetaryPositions">Planetary positions in longitude</param>
        /// <param name="housePositionSet">Positions of houses</param>
        /// <param name="midpointOrb">Orbis for midpoints</param>

        public MidpointsAnalysis(ArrayList planetaryPositions, HousePositionSet housePositionSet, double midpointOrb) {
            this.planetaryPositions = planetaryPositions;
            this.housePositionSet = housePositionSet;
            this.midpointOrb = midpointOrb;
            analysedItems = new ArrayList();
            midpoints = new ArrayList();
            calculateMidpoints();
        }

        private void calculateMidpoints() {
            // build list to check
            PlanetaryPosition pp;
            MidpointType midpointType = new MidpointType(1, 1, midpointOrb);
            for (int i = 0; i < planetaryPositions.Count; i++) {
                pp = (PlanetaryPosition)planetaryPositions[i];
                analysedItems.Add(new AnalysedItem(pp.planetId, pp.longitude));
            }
            // add Ascendant and MC
            analysedItems.Add(new AnalysedItem(Constants.SE_ASC_AS_BODY, housePositionSet.getAsc()));
            analysedItems.Add(new AnalysedItem(Constants.SE_MC_AS_BODY, housePositionSet.getMC()));

            double normPosition;
            double actOrbis;
            double long1;
            double long2;
            double long3;
            AnalysedItem item1;
            AnalysedItem item2;
            AnalysedItem item3;

            for (int i = 0; i < analysedItems.Count; i++) {
                item1 = (AnalysedItem)analysedItems[i];
                for (int j = i + 1; j < analysedItems.Count; j++) {
                    item2 = (AnalysedItem)analysedItems[j];
                    long1 = item1.longitude;
                    long2 = item2.longitude;
                    normPosition = (long1 + long2) / 2;
                    if (normPosition >= 180) normPosition -= 180;

                    for (int k = 0; k < analysedItems.Count; k++) {
                        item3 = (AnalysedItem)analysedItems[k];
                        long3 = item3.longitude;
                        actOrbis = long3 - normPosition;                       
                        if (actOrbis >= 90) actOrbis -= 180;
                        actOrbis = Math.Abs(actOrbis);

                        if (actOrbis < midpointType.orbis) {
                            // midpoint found
                            midpoints.Add(new Midpoint(item1.index, item2.index, item3.index,
                                                       normPosition, actOrbis, midpointType));
                        }
                    }
                }
            }
        }

        /// <summary>
        /// List with calculated midpoints
        /// </summary>
        public ArrayList midpoints {
            get {return _midpoints; }
            set {_midpoints = value; }
        }

    }


}
