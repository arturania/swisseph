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
using radixpro.controller;

namespace radixpro.ui {

    /// <summary>
    /// Information about a body, used for drawing
    /// </summary>
    public class BodyPosition {

        private int _bodyIndex;
        private double _zodPosition;
        private double _munPosition;
        private double _plotPosition;


        public BodyPosition(int bodyIndex, double zodPosition, double munPosition) {
            this.bodyIndex = bodyIndex;
            this.zodPosition = zodPosition;
            this.munPosition = munPosition;
            this.plotPosition = munPosition;
        }

        /// <summary>
        /// Index of the body
        /// </summary>
        public int bodyIndex {
            get {return _bodyIndex; }
            set {_bodyIndex = value; }
        }

        /// <summary>
        /// Position in the zodiac
        /// </summary>
        public double zodPosition {
            get {return _zodPosition; }
            set {_zodPosition = value; }
        }

        /// <summary>
        /// Position in the housesystem
        /// </summary>
        public double munPosition {
            get {return _munPosition; }
            set {_munPosition = value; }
        }

        /// <summary>
        /// Position to plot
        /// </summary>
        public double plotPosition {
            get {return _plotPosition; }
            set {_plotPosition = value;}
        }

    }

    /// <summary>
    /// Information about cusp, used for drawing the cusp line.
    /// </summary>
    public class HousePosition {
        private int _cuspNr;
        private bool _isMainAxis;
        private double _zodPosition;

        /// <summary>
        /// Constructor defines all attributes
        /// </summary>
        /// <param name="cuspNr">Number of cusp</param>
        /// <param name="isMainAxis">true if MC,IC,Asc. or Desc.</param>
        /// <param name="zodPosition">Position in zodiac</param>
        public HousePosition(int cuspNr, bool isMainAxis, double zodPosition) {
            this.cuspNr = cuspNr;
            this.isMainAxis = isMainAxis;
            this.zodPosition = zodPosition;
        }

        /// <summary>
        /// Number if cusp
        /// </summary>
        public int cuspNr {
            get {return _cuspNr; }
            set {_cuspNr = value; }
        }

        /// <summary>
        /// True for MC, IC, Asc. and Desc.
        /// </summary>
        public Boolean isMainAxis {
            get {return _isMainAxis; }
            set {_isMainAxis = value;}
        }
         
        /// <summary>
        /// Position in zodiac 
        /// </summary>
        public double zodPosition{
            get {return _zodPosition; }
            set {_zodPosition = value;}
        }

    }






    /// <summary>
    /// Supports sorting
    /// </summary>
    public enum SortDirection {
        Asc,
        Desc
    }

    /// <summary>
    /// Comparer for sorting positions
    /// </summary>
    public class PositionComparer : IComparer {
        private SortDirection m_direction = SortDirection.Asc;
        public PositionComparer() : base() { }

        /// <summary>
        /// Constructor defines direction (ascending or descending)
        /// </summary>
        /// <param name="direction">Enumerator: Asc or Desc</param>
        public PositionComparer(SortDirection direction) {
            this.m_direction = direction;
        }

        int IComparer.Compare(object x, object y) {

            BodyPosition posX = (BodyPosition)x;
            BodyPosition posY = (BodyPosition)y;

            if (posX == null && posY == null) {
                return 0;
            } else if (posX == null && posY != null) {
                return (this.m_direction == SortDirection.Asc) ? -1 : 1;
            } else if (posX != null && posY == null) {
                return (this.m_direction == SortDirection.Asc) ? 1 : -1;
            } else {
                return (this.m_direction == SortDirection.Asc) ?
                   posX.munPosition.CompareTo(posY.munPosition) :
                   posY.munPosition.CompareTo(posX.munPosition);
            }
        }
    }

    /// <summary>
    /// Comparer to sort on index
    /// </summary>
    public class IndexComparer : IComparer {
       private SortDirection m_direction = SortDirection.Asc;
       public IndexComparer() : base() { }


       /// <summary>
       /// Constructor defines direction (ascending or descending)
       /// </summary>
       /// <param name="direction">Enumerator: Asc or Desc</param>
       public IndexComparer(SortDirection direction) {
          this.m_direction = direction;
       }

       int IComparer.Compare(object x, object y) {

          BodyPosition posX = (BodyPosition)x;
          BodyPosition posY = (BodyPosition)y;

          if (posX == null && posY == null) {
             return 0;
          }
          else if (posX == null && posY != null) {
             return (this.m_direction == SortDirection.Asc) ? -1 : 1;
          }
          else if (posX != null && posY == null) {
             return (this.m_direction == SortDirection.Asc) ? 1 : -1;
          }
          else {
             return (this.m_direction == SortDirection.Asc) ?
                posX.bodyIndex.CompareTo(posY.bodyIndex) :
                posY.bodyIndex.CompareTo(posX.bodyIndex);
          }
       }
    }

}