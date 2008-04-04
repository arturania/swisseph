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

namespace radixpro.controller {

    /// <summary>
    /// Constants for Swiss Ephemeris and RadixPro specific constants,
    /// if a constant is for RadixPro it is prefixed with RP_
    /// </summary>
    public static class Constants {

        /// <summary>
        /// Path to SE files. 
        /// </summary>
        public const string C_EPHE_PATH = "Q:\\buteodev\\radixpro\\project\\radixpro_0_8_0\\radixpro\\rpunittests\\obj\\Release\\sweph\\";

        /// <summary>
        /// Use Swiss Ephemeris
        /// </summary>
        public const int SEFLG_SWIEPH = 2;

        /// <summary>
        /// Equatorial positoons (ra and decl.)
        /// </summary>
        public const int SEFLG_EQUATORIAL = 2048;

        /// <summary>
        /// Julian calendar
        /// </summary>
        public const int SE_JUL_CAL = 0;
        /// <summary>
        /// Gregorian Calendar
        /// </summary>
        public const int SE_GREG_CAL = 1;
        /// <summary>
        /// Special body number to compute obliquity and nutation.
        /// </summary>
        public const int SE_ECL_NUT = -1;
        /// <summary>
        /// Index for Sun
        /// </summary>
        public const int SE_SUN = 0;
        /// <summary>
        /// Index for Moon
        /// </summary>
        public const int SE_MOON = 1;
        /// <summary>
        /// Index for Mercury
        /// </summary>
        public const int SE_MERCURY = 2;
        /// <summary>
        /// Index for Venus
        /// </summary>
        public const int SE_VENUS = 3;
        /// <summary>
        /// Index for Mars
        /// </summary>
        public const int SE_MARS = 4;
        /// <summary>
        /// Index for Jupiter
        /// </summary>
        public const int SE_JUPITER = 5;
        /// <summary>
        /// Index for Saturn
        /// </summary>
        public const int SE_SATURN = 6;
        /// <summary>
        /// Index for Uranus
        /// </summary>
        public const int SE_URANUS = 7;
        /// <summary>
        /// Index for Neptune
        /// </summary>
        public const int SE_NEPTUNE = 8;
        /// <summary>
        /// Index for Pluto
        /// </summary>
        public const int SE_PLUTO = 9;
        /// <summary>
        /// Index for standard node (mean)
        /// </summary>
        public const int SE_MEAN_NODE = 10;
        /// <summary>
        /// Index for oscillating node (true)
        /// </summary>
        public const int SE_TRUE_NODE = 11;          // oscillating node (true)
        /// <summary>
        /// Index for Chiron
        /// </summary>
        public const int SE_CHIRON = 15;
        /// <summary>
        /// Index for ascendant to be used during analysis
        /// </summary>
        public const int SE_ASC_AS_BODY = 200;
        /// <summary>
        /// INdex for MC to be used during analysis
        /// </summary>
        public const int SE_MC_AS_BODY = 201;
        /// <summary>
        /// Flag, if used triggers calculation of speed
        /// </summary>
        public const int SEFLG_SPEED = 256;


        /// <summary>
        /// Configuration status: Configuration file read, ok, not changed 
        /// </summary>
        public const int RP_CONFIG_OK = 0;
        /// <summary>
        /// Configuration status: Configuration file contains errors, reset to default
        /// </summary>
        public const int RP_CONFIG_CORRECTED = 1;
        /// <summary>
        /// Configuration status: Configuration file not found, default recreated
        /// </summary>
        public const int RP_CONFIG_RENEWED = 2;
        /// <summary>
        /// Configuration status: Configuration changed by user
        /// </summary>
        public const int RP_CONFIG_ALTERED = 3;
        /// <summary>
        /// Configuration status: Configuration contains unresolved errors
        /// </summary>
        public const int RP_CONFIG_ERROR = -1;

        /// <summary>
        /// Maximum index for timezones
        /// </summary>
        public const int RP_MAX_TIMEZONES = 35;

        //    public const int RP_STANDARD_BODY_COUNT = 10,
        //    public const int RP_STANDARD_CUSP_COUNT = 14;

        /// <summary>
        /// Housesystem: Placidus
        /// </summary>
        public const char C_HOUSES_PLACIDUS = 'P';
        /// <summary>
        /// Housesystem: Koch GOH
        /// </summary>
        public const char C_HOUSES_KOCH = 'K';
        /// <summary>
        /// Housesystem: Porphyrius
        /// </summary>
        public const char C_HOUSES_PORPHYRIUS = 'O';
        /// <summary>
        /// Housesystem: Regiomontanus
        /// </summary>
        public const char C_HOUSES_REGIOMONTANUS = 'R';
        /// <summary>
        /// Housesystem: Campanus
        /// </summary>
        public const char C_HOUSES_CAMPANUS = 'C';
        /// <summary>
        /// Housesystem: Equal, starting with asc.
        /// </summary>
        public const char C_HOUSES_EQUAL_ASC = 'A';
        /// <summary>
        /// Housesystem: Equal, asc centre of I (Vehlow)
        /// </summary>
        public static char C_HOUSES_EQUAL_VEHLOW = 'V';
        /// <summary>
        /// Housesystem: Axial Rotation
        /// </summary>
        public static char C_HOUSES_AXIAL_ROTATION = 'X';
        /// <summary>
        /// Housesystem: Azimuthal or horizontal system
        /// </summary>
        public static char C_HOUSES_HORIZONTAL = 'H';
        /// <summary>
        /// Housesystem: Topocentric (Polich and Page)
        /// </summary>
        public static char C_HOUSES_TOPOCENTRIC = 'T';
        /// <summary>
        /// Housesystem: Alcabitus
        /// </summary>
        public static char C_HOUSES_ALCABITIUS = 'B';
        /// <summary>
        /// Housesystem: Gauquelin sectors
        /// </summary>
        public static char C_HOUSES_GAUQUELIN = 'G';
        /// <summary>
        /// Housesystem: Morinus
        /// </summary>
        public static char C_HOUSES_MORINUS = 'M';
        /// <summary>
        /// Housesystem: Krusinski
        /// </summary>
        public static char C_HOUSES_KRUSINSKI = 'U';



        /// <summary>        
        /// Progression: an error occured
        /// </summary>
        public static int C_PROGSTATUS_ERROR = -1;
        /// <summary>
        /// Progression: started but not completed
        /// </summary>
        public static int C_PROGSTATUS_UNFINISHED = 0;
        /// <summary>
        /// Progression: calculated
        /// </summary>
        public static int C_PROGSTATUS_CALCULATED = 1;

        ///// TODO check if following constants can be rempoed
        /// <summary>
        /// Radix: an error occured
        /// </summary>
        public static int C_RADIXSTATUS_ERROR = -1;
        /// <summary>
        /// Radix: started but not completed
        /// </summary>
        public static int C_RADIXSTATUS_UNFINISHED = 0;
        /// <summary>
        /// Radix: calculated but not saved
        /// </summary>
        public static int C_RADIXSTATUS_CALCULATED = 1;
        /// <summary>
        /// Radix: has been saved
        /// </summary>
        public static int C_RADIXSTATUS_SAVED = 2;
        /// <summary>
        /// Radix: changes that might differ from saved version
        /// </summary>
        public static int C_RADIXSTATUS_UNSAVEDCHANGES = 2;

        ///// end TODO


        /// <summary>
        /// Status: initial, no data available
        /// </summary>
        public static int C_RP_STATUS_INIT = 0;

        /// <summary>
        /// Status: chart calculated but no events
        /// </summary>
        public static int C_RP_STATUS_POP = 1;

        /// <summary>
        /// Status: chart calculated, no unsaved changes
        /// </summary>
        public static int C_RP_STATUS_POP_NC = 2;

        /// <summary>
        /// Status: chart and event(s) calculated
        /// </summary>
        public static int C_RP_STATUS_EVENTS = 3;

        /// <summary>
        /// Status: chart and event(s) calculated and no unsaved changes
        /// </summary>
        public static int C_RP_STATUS_EVENTS_POP_NC = 4;


        /// <summary>
        /// Number of predefined timezones
        /// </summary>
        public static int C_RP_TOTAL_TIMEZONES = 35;

        /// <summary>
        /// Number of charttypes
        /// </summary>
        public static int C_RP_TOTAL_CHARTTYPES = 4;

        /// <summary>
        /// Number of sourcetypes
        /// </summary>
        public static int C_RP_TOTAL_SOURCETYPES = 7;

        /// <summary>
        /// Number of housesystems
        /// </summary>
        public static int C_RP_TOTAL_HOUSESYSTEMS = 14;

       /* ******************** aspects *************************************** */


       /// <summary>
       /// Index for conjunction
       /// </summary>
       public const int C_RP_ASPECT_CONJUNCTION = 1;

       /// <summary>
       /// Index for opposition
       /// </summary>
       public const int C_RP_ASPECT_OPPOSITION = 2;

       /// <summary>
       /// Index for trine
       /// </summary>
       public const int C_RP_ASPECT_TRINE = 3;

       /// <summary>
       /// Index for square
       /// </summary>
       public const int C_RP_ASPECT_SQUARE = 4;

       /// <summary>
       /// Index for quintile
       /// </summary>
       public const int C_RP_ASPECT_QUINTILE = 5;

       /// <summary>
       /// Index for sextile
       /// </summary>
       public const int C_RP_ASPECT_SEXTILE = 6;

       /// <summary>
       /// Index for septile
       /// </summary>
       public const int C_RP_ASPECT_SEPTILE = 7;

       /// <summary>
       /// Index for semisquare
       /// </summary>
       public const int C_RP_ASPECT_SEMISQUARE = 8;

       /// <summary>
       /// Index for sesquiquadrate
       /// </summary>
       public const int C_RP_ASPECT_SESQUIQUADRATE = 9;

       /// <summary>
       /// Index for semisextile
       /// </summary>
       public const int C_RP_ASPECT_SEMISEXTILE = 10;

       /// <summary>
       /// Index for inconjunct
       /// </summary>
       public const int C_RP_ASPECT_INCONJUNCT = 11;

       /// <summary>
       /// Index for biquintile
       /// </summary>
       public const int C_RP_ASPECT_BIQUINTILE = 12;

    }

}


