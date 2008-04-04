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
using radixpro.ui;
using radixpro.controller;

namespace radixpro.data {

   public class RpHouseSystem {
      private int _index;
      private string _description;
      private char _id;

       /// <summary>
       /// Container for housesystem
       /// </summary>
       /// <param name="index">Index in list</param>
       /// <param name="description">Tetual description</param>
       /// <param name="id">Identification as used in Swiss Ephemeris</param>
       public RpHouseSystem(int index, string description, char id) {
         this.index = index;
         this.description = description;
         this.id = id;
      }


      /// <summary>
      /// Index for housesystem
      /// </summary>
      public int index {
         get { return _index; }
         set { _index = value; }
      }

      /// <summary>
      /// Descriptive text for housesystem
      /// </summary>
      public string description {
         get { return _description; }
         set { _description = value; }
      }

      /// <summary>
      /// Id for housesystem
      /// </summary>
      public char id {
         get { return _id; }
         set { _id = value; }
      }
   }

    /// <summary>
    /// Holds a list with housesystems
    /// </summary>
   public class HouseSystemList {
      RpHouseSystem[] _theList;

      /// <summary>
      /// Constructor, initializes the list
      /// </summary>
      public HouseSystemList() {
         theList = new RpHouseSystem[Constants.C_RP_TOTAL_HOUSESYSTEMS];
         theList[0] = new RpHouseSystem(0, ResourceBundle.RB_HOUSESYSTEMS[0], 'A');    // Asc equal
         theList[1] = new RpHouseSystem(1, ResourceBundle.RB_HOUSESYSTEMS[1], 'B');    // Alcabitius
         theList[2] = new RpHouseSystem(2, ResourceBundle.RB_HOUSESYSTEMS[2], 'X');    // Axial rotation
         theList[3] = new RpHouseSystem(3, ResourceBundle.RB_HOUSESYSTEMS[3], 'C');    // Campanus
         theList[4] = new RpHouseSystem(4, ResourceBundle.RB_HOUSESYSTEMS[4], 'G');    // Gauquelin sectors
         theList[5] = new RpHouseSystem(5, ResourceBundle.RB_HOUSESYSTEMS[5], 'H');    // Horizontal (Azimuthal)
         theList[6] = new RpHouseSystem(6, ResourceBundle.RB_HOUSESYSTEMS[6], 'K');    // Koch
         theList[7] = new RpHouseSystem(7, ResourceBundle.RB_HOUSESYSTEMS[7], 'U');    // Krusinski
         theList[8] = new RpHouseSystem(8, ResourceBundle.RB_HOUSESYSTEMS[8], 'M');    // Morinus
         theList[9] = new RpHouseSystem(9, ResourceBundle.RB_HOUSESYSTEMS[9], 'P');    // Placidus
         theList[10] = new RpHouseSystem(10, ResourceBundle.RB_HOUSESYSTEMS[10], 'O');  // Porphyrius
         theList[11] = new RpHouseSystem(11, ResourceBundle.RB_HOUSESYSTEMS[11], 'R');  // Regiomontanus
         theList[12] = new RpHouseSystem(12, ResourceBundle.RB_HOUSESYSTEMS[12], 'T');  // Topocentric
         theList[13] = new RpHouseSystem(13, ResourceBundle.RB_HOUSESYSTEMS[13], 'V');  // Vehlow equal
      }


      /// <summary>
      /// List with housesystems
      /// </summary>
      public RpHouseSystem[] theList {
         get { return _theList; }
         set { _theList = value; }
      }

      /// <summary>
      /// Get housesystem by searching with a char
      /// </summary>
      /// <param name="c">The char that indicates the housesystem</param>
      /// <returns></returns>
      public RpHouseSystem houseSystem(char c) {
         RpHouseSystem result = null;
         for (int i = 0; i < theList.Length; i++) {
            if (theList[i].id == c) result = theList[i];
         }
         return result;
      }

   }


    /// <summary>
    /// TimeZone
    /// </summary>
    public class RpTimeZone {
        private int _index;
        private string _description;
        private double _offset;

        /// <summary>
        /// Constructor for timezone
        /// </summary>
        /// <param name="index">Index of timezone</param>
        /// <param name="description">Descriptive text for timezone</param>
        /// <param name="offset">Offset in hours, compared to UT</param>
        public RpTimeZone(int index, string description, double offset) {
            this.index = index;
            this.description = description;
            this.offset = offset;
        }


        /// <summary>
        /// Index for timezone
        /// </summary>
        public int index {
            get { return _index; }
            set { _index = value; }
        }

        /// <summary>
        /// Descriptive text for timezone
        /// </summary>
        public string description {
            get { return _description; }
            set { _description = value; }
        }

        /// <summary>
        /// Offset in hours from UT
        /// </summary>
        public double offset {
            get { return _offset; }
            set { _offset = value; }
        }
    }

    /// <summary>
    /// Holds a list with timezones
    /// </summary>
    public class TimeZoneList {
        RpTimeZone[] _theList;

        /// <summary>
        /// Constructor, initializes the list
        /// </summary>
        public TimeZoneList() {
            theList = new RpTimeZone[Constants.C_RP_TOTAL_TIMEZONES];
            theList[0] = new RpTimeZone(0, ResourceBundle.RB_TIMEZONES[0], 0);
            theList[1] = new RpTimeZone(1, ResourceBundle.RB_TIMEZONES[1], 0.33333333);
            theList[2] = new RpTimeZone(2, ResourceBundle.RB_TIMEZONES[2], 1);
            theList[3] = new RpTimeZone(3, ResourceBundle.RB_TIMEZONES[3], 2);
            theList[4] = new RpTimeZone(4, ResourceBundle.RB_TIMEZONES[4], 3);
            theList[5] = new RpTimeZone(5, ResourceBundle.RB_TIMEZONES[5], 3.5);
            theList[6] = new RpTimeZone(6, ResourceBundle.RB_TIMEZONES[6], 4);
            theList[7] = new RpTimeZone(7, ResourceBundle.RB_TIMEZONES[7], 5);
            theList[8] = new RpTimeZone(8, ResourceBundle.RB_TIMEZONES[8], 5.5);
            theList[9] = new RpTimeZone(9, ResourceBundle.RB_TIMEZONES[9], 6);
            theList[10] = new RpTimeZone(10, ResourceBundle.RB_TIMEZONES[10], 6.5);
            theList[11] = new RpTimeZone(11, ResourceBundle.RB_TIMEZONES[11], 7);
            theList[12] = new RpTimeZone(12, ResourceBundle.RB_TIMEZONES[12], 7.5);
            theList[13] = new RpTimeZone(13, ResourceBundle.RB_TIMEZONES[13], 8);
            theList[14] = new RpTimeZone(14, ResourceBundle.RB_TIMEZONES[14], 8.5);
            theList[15] = new RpTimeZone(15, ResourceBundle.RB_TIMEZONES[15], 9);
            theList[16] = new RpTimeZone(16, ResourceBundle.RB_TIMEZONES[16], 9.5);
            theList[17] = new RpTimeZone(17, ResourceBundle.RB_TIMEZONES[17], 10);
            theList[18] = new RpTimeZone(18, ResourceBundle.RB_TIMEZONES[18], 10.5);
            theList[19] = new RpTimeZone(19, ResourceBundle.RB_TIMEZONES[19], 11);
            theList[20] = new RpTimeZone(20, ResourceBundle.RB_TIMEZONES[20], 11.5);
            theList[21] = new RpTimeZone(21, ResourceBundle.RB_TIMEZONES[21], 12);
            theList[22] = new RpTimeZone(22, ResourceBundle.RB_TIMEZONES[22], -11);
            theList[23] = new RpTimeZone(23, ResourceBundle.RB_TIMEZONES[23], -10.5);
            theList[24] = new RpTimeZone(24, ResourceBundle.RB_TIMEZONES[24], -10);
            theList[25] = new RpTimeZone(25, ResourceBundle.RB_TIMEZONES[25], -9);
            theList[26] = new RpTimeZone(26, ResourceBundle.RB_TIMEZONES[26], -8);
            theList[27] = new RpTimeZone(27, ResourceBundle.RB_TIMEZONES[27], -7);
            theList[28] = new RpTimeZone(28, ResourceBundle.RB_TIMEZONES[28], -6);
            theList[29] = new RpTimeZone(29, ResourceBundle.RB_TIMEZONES[29], -5);
            theList[30] = new RpTimeZone(30, ResourceBundle.RB_TIMEZONES[30], -4);
            theList[31] = new RpTimeZone(31, ResourceBundle.RB_TIMEZONES[31], -3.5);
            theList[32] = new RpTimeZone(32, ResourceBundle.RB_TIMEZONES[32], -3);
            theList[33] = new RpTimeZone(33, ResourceBundle.RB_TIMEZONES[33], -2);
            theList[34] = new RpTimeZone(34, ResourceBundle.RB_TIMEZONES[34], -1);
        }

        /// <summary>
        /// List with timezones
        /// </summary>
        public RpTimeZone[] theList {
            get { return _theList; }
            set { _theList = value; }
        }

    }

    /// <summary>
    /// Type of chart
    /// </summary>
    public class ChartType {
        private int _index;
        private string _description;

        /// <summary>
        /// Constructor initializes 
        /// </summary>
        /// <param name="index">Index that points to charttype</param>
        /// <param name="description">Descritpion for charttype</param>
        public ChartType(int index, string description) {
            this.index = index;
            this.description = description;
        }

        /// <summary>
        /// Index of charttype
        /// </summary>
        public int index {
            get {return _index; }
            set {_index = value; }
        }

        /// <summary>
        /// Description of charttype
        /// </summary>
        public string description {
            get {return _description; }
            set {_description = value; }
        }
    }

    /// <summary>
    /// List of available charttypes
    /// </summary>
    public class ChartTypeList {
        ChartType[] _theList;
        /// <summary>
        /// Constructor for ChartTypeList, initiates the list
        /// </summary>
        public ChartTypeList() {
            theList = new ChartType[Constants.C_RP_TOTAL_CHARTTYPES];
            for (int i = 0; i < theList.Length; i++) {
                theList[i] = new ChartType(i, ResourceBundle.RB_CHARTTYPES[i]);
            }
        }

        /// <summary>
        /// List with charttypes
        /// </summary>
        public ChartType[] theList {
            get {return _theList; }
            set {_theList = value; }
        }

    }


    /// <summary>
    /// Type of source
    /// </summary>
    public class SourceType {
        private string _index;
        private string _description;

        /// <summary>
        /// Constructor initializes
        /// </summary>
        /// <param name="index">Index to sourcetype</param>
        /// <param name="description">Descritpion of sourcetype</param>
        public SourceType(string index, string description) {
            this.index = index;
            this.description = description;
        }

        /// <summary>
        /// Index of sourcetype
        /// </summary>
        public string index {
            get {return _index; }
            set {_index = value; }
        }

        /// <summary>
        /// Description of sourcetype
        /// </summary>
        public string description {
            get {return _description; }
            set {_description = value; }
        }
    }

    /// <summary>
    /// List with source types
    /// </summary>
    public class SourceTypeList {
        SourceType[] _theList;

        /// <summary>
        /// COnstructor, initializes the list
        /// </summary>
        public SourceTypeList() {
            theList = new SourceType[Constants.C_RP_TOTAL_SOURCETYPES];
            for (int i = 0; i < theList.Length; i++) {
                theList[i] = new SourceType((ResourceBundle.RB_SOURCETYPES[i].Substring(0, 2).Trim()), ResourceBundle.RB_SOURCETYPES[i]);
            }
        }

        /// <summary>
        /// List from sourcetypelist
        /// </summary>
        public SourceType[] theList {
            get {return _theList; }
            set {_theList = value; }
        }
    }
}