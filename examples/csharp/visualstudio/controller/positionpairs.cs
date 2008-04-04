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

namespace radixpro.controller {

    /// <summary>
    /// Parent for aspects and midpoints, combines two points that do have a connection and gives the acutal orbis.
    /// </summary>
    public class PositionPair {
        private int _pos1;
        private int _pos2;
        private double _actOrbis;

        /// <summary>
        /// First position in positionpair
        /// </summary>
        public int pos1 {
            get {return _pos1; }
            set {_pos1 = value; }
        }

        /// <summary>
        /// Second position in positionpair
        /// </summary>
        public int pos2 {
            get {return _pos2; }
            set {_pos2 = value; }
        }

        /// <summary>
        /// Actual orbis for positionpair
        /// </summary>
        public double actOrbis {
            get {return _actOrbis; }
            set {_actOrbis = value; }
        }

        /// <summary>
        /// Getter for actual orbis
        /// </summary>
        /// <returns>Value for actOrbis</returns>
        public double getActOrbis() {
            return this.actOrbis;
        }
    }

    /// <summary>
    /// Defines the type of an aspect
    /// </summary>
    public class AspectType {
        private int _id;
        private double _distance;
        private double _orbis;
        private Boolean _major;

        /// <summary>
        /// Constructor for Aspecttype
        /// </summary>
        /// <param name="id">Unique identifier for the aspect</param>
        /// <param name="distance">Standard distance in degrees</param>
        /// <param name="orbis">Maximal allowed orbis</param>
        /// <param name="major">True if aspect is major, otherwise false</param>
        public AspectType(int id, double distance, double orbis, Boolean major) {
            this.id = id;
            this.distance = distance;
            this.orbis = orbis;
            this.major = major;
        }
        /// <summary>
        /// Unique identifier for the aspect
        /// </summary>
        public int id {
            get {return _id; }
            set {_id = value; }
        }
        /// <summary>
        /// Distance in degrees
        /// </summary>
        public double distance {
            get {return _distance; }
            set {_distance = value; }
        }
        /// <summary>
        /// 
        /// </summary>
        public double orbis {
            get {return _orbis; }
            set {_orbis = value; }
        }

        /// <summary>
        /// True if major, otherwise false
        /// </summary>
        public bool major {
            get {return _major; }
            set {_major = value; }
        }
    }



    /// <summary>
    /// An aspect in the radix.
    /// </summary>
    public class Aspect : PositionPair {
        private AspectType _aspectType;

        /// <summary>
        /// Constructor, defines all variables.
        /// </summary>
        /// <param name="pos1">First body in the pair</param>
        /// <param name="pos2">Second body in the pair</param>
        /// <param name="actOrbis">Actual orbis</param>
        /// <param name="aspectType">Type of aspect</param>
        public Aspect(int pos1, int pos2, double actOrbis, AspectType aspectType) {
            this.pos1 = pos1;
            this.pos2 = pos2;
            this.actOrbis = actOrbis;
            this.aspectType = aspectType;
        }
        /// <summary>
        /// Type of aspect
        /// </summary>
        public AspectType aspectType {
            get {return _aspectType; }
            set {_aspectType = value; }
        }
    }

    /// <summary>
    /// Defines the type of a midpoint. 
    /// Partly defined for future use. The first version of RadixPro will only use midpoints in
    /// a 360 degree circle, so a type with division 1.
    /// </summary>
    public class MidpointType {
        private int _id;
        private int _division;
        private double _orbis;

        /// <summary>
        /// Constructor for MidpointType
        /// </summary>
        /// <param name="id">Id is used to recognize different midpointtypes</param>
        /// <param name="division">Indicates the type of wheel. In a 360 degree wheel, the division is 1,
        /// in a 90 degree wheel it is 4, in a 45 degree 8 etc.</param>
        /// <param name="orbis">The orbis to be used for this type of midpoint</param>
        public MidpointType(int id, int division, double orbis) {
            this.id = id;
            this.division = division;
            this.orbis = orbis;
        }
        /// <summary>
        /// Id to indicate type of midpoint
        /// </summary>
        public int id {
            get {return _id; }
            set {_id = value; }
        }
        /// <summary>
        /// Type of wheel (360 degrees = 1, 90 degrees = 4 etc.)
        /// </summary>
        public int division {
            get {return _division;  }
            set {_division = value; }
        }
        /// <summary>
        /// The orbis to be used for this type of midpoint
        /// </summary>
        public double orbis {
            get {return _orbis; }
            set {_orbis = value; }
        }
    }



    /// <summary>
    /// An occupied midpoint in the radix
    /// </summary>
    public class Midpoint : PositionPair {
        private int _pos3;
        private double _position;
        private MidpointType _midpointType;

        /// <summary>
        /// Constructor, defines all variables
        /// </summary>
        /// <param name="pos1">One of the paired bodies that form the midpoint</param>
        /// <param name="pos2">The other paired body</param>
        /// <param name="pos3">The body on the midpoint axis</param>
        /// <param name="position">Position of the exact midpoint, calculated within the first 180 degrees</param>
        /// <param name="actOrbis">Orbis from conjunction or opposition between pos3 and position</param>
        /// <param name="midpointType">Type of midpoint</param>
        public Midpoint(int pos1, int pos2, int pos3, double position, double actOrbis, MidpointType midpointType) {
            this.pos1 = pos1;
            this.pos2 = pos2;
            this.pos3 = pos3;
            this.position = position;
            this.actOrbis = actOrbis;
            this.midpointType = midpointType;
        }
        /// <summary>
        /// Postion of the body on the midpoint axis
        /// </summary>
        public int pos3 {
            get {return _pos3; }
            set {_pos3 = value; }
        }

        /// <summary>
        /// Posotion of midpoint
        /// </summary>
        public double position {
            get {return _position; }
            set {_position = value; }
        }

        /// <summary>
        /// Type of midpoint
        /// </summary>
        public MidpointType midpointType {
            get {return _midpointType; }
            set {_midpointType = value; }
        }
    }

}
