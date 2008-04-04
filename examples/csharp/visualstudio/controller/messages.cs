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
    /// Constants for messages
    /// </summary>
    public static class Messages {
        /// <summary>
        /// Generic cancel-message
        /// </summary>
        public const int MSG_CANCEL = 0;
        /// <summary>
        /// Start calcualting a new case
        /// </summary>
        public const int MSG_NEWCASE = 100;
        /// <summary>
        /// Completed construction of a new case
        /// </summary>
        public const int MSG_NEWCASE_COMPLETED = 102;
        /// <summary>
        /// Start loading a case
        /// </summary>
        public const int MSG_LOADCASE = 110;
        /// <summary>
        /// Save a case
        /// </summary>
        public const int MSG_SAVECASE = 120;
        /// <summary>
        /// Show and edit configuration
        /// </summary>
        public const int MSG_SHOWCONFIG = 200;
        /// <summary>
        /// Show and edit settings
        /// </summary>
        public const int MSG_SHOWSETTINGS = 210;
        /// <summary>
        /// Show about screen
        /// </summary>
        public const int MSG_SHOWABOUT = 300;
        /// <summary>
        /// Show help
        /// </summary>
        public const int MSG_SHOWHELP = 310;
        /// <summary>
        /// Perform calculation for case (initially with only a chart)
        /// </summary>
        public const int MSG_CALCCASE = 400;
        /// <summary>
        /// Show chartwheel
        /// </summary>
        public const int MSG_SHOWCHART = 420;
        /// <summary>
        /// Show list with positions
        /// </summary>
        public const int MSG_SHOWPOSITIONS = 440;
        /// <summary>
        /// Show form for analysis
        /// </summary>
        public const int MSG_SHOWANALYSIS = 460;
        /// <summary>
        /// Show aspects
        /// </summary>
        public const int MSG_SHOWASPECTS = 480;
        /// <summary>
        /// Show midpoints
        /// </summary>
        public const int MSG_SHOWMIDPOINTS = 500;
        /// <summary>
        /// Start calculating a new event
        /// </summary>
        public const int MSG_NEWEVENT = 520;
        /// <summary>
        /// Perform the calculation for a new event
        /// </summary>
        public const int MSG_CALCEVENT = 540;
        /// <summary>
        /// Show transits
        /// </summary>
        public const int MSG_SHOWPROGTRANSITS = 560;
        /// <summary>
        /// Show secundary progressions
        /// </summary>
        public const int MSG_SHOWPROGSECUNDARY = 580;
    }

}