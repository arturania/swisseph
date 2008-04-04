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
using radixpro.controller;


namespace radixpro.engine {

    /// <summary>
    /// Progressive aspect, actual aspect between a progressive positon and a radix position
    /// </summary>
    public class ProgAspect : Aspect {

        private int _idProg;
        private double _posProg;

        /// <summary>
        /// Constructor, defines all variables.
        /// </summary>
        /// <param name="pos1">Radix body</param>
        /// <param name="pos2">Dummy for pos2</param>/// 
        /// <param name="idProg">Progressive body</param>
        /// <param name="posProg">Progressive position</param>
        /// <param name="actOrbis">Actual orbis for progression</param>
        /// <param name="aspectType">Type of aspect</param>
        public ProgAspect(int pos1, int pos2, int idProg,
               double posProg, double actOrbis, AspectType aspectType
            )
            : base(pos1, pos2, actOrbis, aspectType) {
            this.pos1 = pos1;
            this.pos2 = -1;      // pos2 is not used    TODO refactor
            this.idProg = idProg;
            this.posProg = posProg;
            this.actOrbis = actOrbis;
            this.aspectType = aspectType;
        }


        /// <summary>
        /// Id of progressive body
        /// </summary>
        public int idProg {
            get {return _idProg; }
            set {_idProg = value; }
        }

        /// <summary>
        /// Progressive position
        /// </summary>
        public double posProg {
            get {return _posProg; }
            set {_posProg = value; }
        }

    }

    /// <summary>
    /// Container for progressive analysis
    /// </summary>
    public class ProgAnalysis {

        private ArrayList _transitAspectMatches;
        private ArrayList _secundaryAspectMatches;
        private ArrayList aspectTypes;
        private int progOrb;

        /// <summary>
        /// Constructor initializes
        /// </summary>
        /// <param name="planetaryPositions">List with planetary positions</param>
        /// <param name="housePositionSet">List with house positions</param>
        /// <param name="transitPositions">List with transits</param>
        /// <param name="secundaryPositions">List with secundary positions</param>
        /// <param name="aspectTypes">List with aspect types</param>
        /// <param name="progOrb">ORbis for progressions</param>
        public ProgAnalysis(ArrayList planetaryPositions, HousePositionSet housePositionSet, ArrayList transitPositions, ArrayList secundaryPositions, ArrayList aspectTypes, int progOrb) {
            this.aspectTypes = aspectTypes;
            this.progOrb = progOrb;
            calcAspects4Transits(planetaryPositions, housePositionSet, transitPositions);
            calcAspects4Secundary(planetaryPositions, housePositionSet, secundaryPositions);
        }



        private void calcAspects4Transits(ArrayList radixPositions, HousePositionSet housePositionSet, ArrayList progPositions) {
            double radixPos;
            double progPos;
            int progId;

            this.transitAspectMatches = new ArrayList();
            for (int i = 0; i < progPositions.Count; i++) {
                progPos = ((PlanetaryPosition)progPositions[i]).longitude;
                progId = ((PlanetaryPosition)progPositions[i]).planetId;
                // check planetary positions
                for (int j = 0; j < radixPositions.Count; j++) {
                    radixPos = ((PlanetaryPosition)radixPositions[j]).longitude;
                    compareIt(radixPos, progPos, j, progId);
                }
                // check additional points
                double asc = housePositionSet.getAsc();
                compareIt(asc, progPos, Constants.SE_ASC_AS_BODY, progId);
                double mc = housePositionSet.getMC();
                compareIt(mc, progPos, Constants.SE_MC_AS_BODY, progId);
            }
        }

        private void calcAspects4Secundary(ArrayList radixPositions, HousePositionSet housePositionSet, ArrayList progPositions) {
            double radixPos;
            double progPos;
            int progId;

            this.secundaryAspectMatches = new ArrayList();
            for (int i = 0; i < progPositions.Count; i++) {
                progPos = ((PlanetaryPosition)progPositions[i]).longitude;
                progId = ((PlanetaryPosition)progPositions[i]).planetId;
                // check planetary positions
                for (int j = 0; j < radixPositions.Count; j++) {
                    radixPos = ((PlanetaryPosition)radixPositions[j]).longitude;
                    compareIt(radixPos, progPos, j, progId);
                }
                // check additional points
                double asc = housePositionSet.getAsc();
                compareIt(asc, progPos, Constants.SE_ASC_AS_BODY, progId);
                double mc = housePositionSet.getMC();
                compareIt(mc, progPos, Constants.SE_MC_AS_BODY, progId);
            }

        }


        private void compareIt(double radixPos, double progPos, int rIndex, int pIndex) {
            double actOrbis;
            double long1 = Math.Min(radixPos, progPos);
            double long2 = Math.Max(radixPos, progPos);
            double distance1 = long2 - long1;
            double distance2 = long1 - long2 + 360;
            for (int k = 0; k < aspectTypes.Count; k++) {
                AspectType aspectType = (AspectType)aspectTypes[k];
                double normDistance = aspectType.distance;
                if ((Math.Abs(distance1 - normDistance)) <= progOrb) {   // aspect found
                    actOrbis = (Math.Abs(distance1 - normDistance));
                    transitAspectMatches.Add(new ProgAspect(rIndex, 0, pIndex, progPos, actOrbis, (AspectType)aspectTypes[k]));
                }
                else
                    if ((Math.Abs(distance2 - normDistance)) <= progOrb) {   // aspect found
                        actOrbis = (Math.Abs(distance2 - normDistance));
                        transitAspectMatches.Add(new ProgAspect(rIndex, 0, pIndex, progPos, actOrbis, (AspectType)aspectTypes[k]));
                    }
            }
        }


        /// <summary>
        /// Matching aspects for transits
        /// </summary>
        public ArrayList transitAspectMatches {
            get {return _transitAspectMatches; }
            set {_transitAspectMatches = value; }
        }


        /// <summary>
        /// Matching aspect for secundary progressions
        /// </summary>
        public ArrayList secundaryAspectMatches {
            get {return _secundaryAspectMatches; }
            set {_secundaryAspectMatches = value; }
        }
    }

}