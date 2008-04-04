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

namespace radixpro.ui {

    /// <summary>
    /// Container of texts that might be shown on forms.
    /// </summary>
    public static class ResourceBundle {
        /// <summary>
        /// Version of RadixPro
        /// </summary>
        public static String RB_COM_VERSION = "0.7.2";

        /// <summary>
        /// Versiondate of RadixPro
        /// </summary>
        public static String RB_COM_VERSIONDATE = "March 2008";

        /// <summary>
        /// Copyright for RadixPro
        /// </summary>
        public static String RB_COM_COPYRIGHT = "(c) Jan Kampherbeek, RadixPro.";


        /// <summary>
        /// Default text for setting location.
        /// </summary>
        public static String RB_DEFAULT_LOCATION = "Undefined";

        /// <summary>
        /// Text for DST
        /// </summary>
        public static String RB_COM_DST = "DST";

        /// <summary>
        /// Text for Timezone
        /// </summary>
        public static String RB_COM_ZONE = "Timezone";

        /// <summary>
        /// Text for northern latitude
        /// </summary>
        public static String RB_COM_LAT_NORTH = "N";

        /// <summary>
        /// Text for southern latitude
        /// </summary>
        public static String RB_COM_LAT_SOUTH = "S";


        /// <summary>
        /// Text for eastern longitude
        /// </summary>
        public static String RB_COM_LONG_EAST = "E";

        /// <summary>
        /// Text for western longitude
        /// </summary>
        public static String RB_COM_LONG_WEST = "W";


        /* menu main form ===========================================  */

        /// <summary>
        /// Caption menuitem File
        /// </summary>
        public static String RB_FRM_MAIN_MI_FILE = "File and chart";


        /// <summary>
        /// Caption menuitem New Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_NEWCHART = "&New Chart";

        /// <summary>
        /// Caption menuitem Open Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_OPENCHART = "&Open Chart";

        /// <summary>
        /// Caption menuitem Save Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_SAVE = "&Save Chart";

        /// <summary>
        /// Caption menuitem Save all
        /// </summary>
        public static String RB_FRM_MAIN_MI_SAVEALL = "Save All";

        /// <summary>
        /// Caption menuitem print
        /// </summary>
        public static String RB_FRM_MAIN_MI_PRINT = "Print";

        /// <summary>
        /// Caption menuitem print preview
        /// </summary>
        public static String RB_FRM_MAIN_MI_PRINTPREVIEW = "Print Preview";

        /// <summary>
        /// Caption menuitem exit
        /// </summary>
        public static String RB_FRM_MAIN_MI_EXIT = "Exit RadixPro";

        /// <summary>
        /// Caption menuitem results
        /// </summary>
        public static String RB_FRM_MAIN_MI_RESULTS = "Results";

        /// <summary>
        /// Caption menuitem wheel
        /// </summary>
        public static String RB_FRM_MAIN_MI_WHEEL = "Chart wheel";

        /// <summary>
        /// Caption menuitem POSITIONS
        /// </summary>
        public static String RB_FRM_MAIN_MI_POSTIONS = "Positions";

        /// <summary>
        /// Caption menuitem analysis
        /// </summary>
        public static String RB_FRM_MAIN_MI_ANALYSIS = "Analysis";

        /// <summary>
        /// Caption menuitem aspects
        /// </summary>
        public static String RB_FRM_MAIN_MI_ASPECTS = "Aspects";

        /// <summary>
        /// Caption menuitem MIDPOINTS
        /// </summary>
        public static String RB_FRM_MAIN_MI_MIDPOINTS = "Midpoints";

        /// <summary>
        /// Caption menuitem progressions
        /// </summary>
        public static String RB_FRM_MAIN_MI_PROGRESSIONS = "Progressions";


        /// <summary>
        /// Caption menuitem transits
        /// </summary>
        public static String RB_FRM_MAIN_MI_TRANSITS = "Transits";

        /// <summary>
        /// Caption menuitem secundary
        /// </summary>
        public static String RB_FRM_MAIN_MI_SECUNDARY = "Secundary directions";

        /// <summary>
        /// Caption menuitem preferences
        /// </summary>
        public static String RB_FRM_MAIN_MI_PREFERENCES = "Preferences";

        /// <summary>
        /// Caption menuitem configuration
        /// </summary>
        public static String RB_FRM_MAIN_MI_CONFIGURATIION = "Configuration";

        /// <summary>
        /// Caption menuitem settings
        /// </summary>
        public static String RB_FRM_MAIN_MI_SETTINGS = "Settings";

        /// <summary>
        /// Caption menuitem help
        /// </summary>
        public static String RB_FRM_MAIN_MI_HELP = "Help";

        /// <summary>
        /// Caption menuitem help contents
        /// </summary>
        public static String RB_FRM_MAIN_MI_CONTENTS = "Contents";

        /// <summary>
        /// Caption menuitem help index
        /// </summary>
        public static String RB_FRM_MAIN_MI_INDEX = "Index";

        /// <summary>
        /// Caption menuitem about
        /// </summary>
        public static String RB_FRM_MAIN_MI_ABOUT = "About";




        /* main form ================================================  */

        /// <summary>
        /// Caption for titlebar mainform
        /// </summary>
        public static String RB_FRM_MAIN_FORMTITLE = "RadixPro version " + RB_COM_VERSION;

        /// <summary>
        /// Title mainform
        /// </summary>
        public static String RB_FRM_MAIN_LBL_TITLE = "RadixPro " + RB_COM_VERSION;

        /// <summary>
        /// First line of introtext in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_LBL_INTRO1 = "Start with a 'New' chart or 'Open' an existing one.";

        /// <summary>
        /// Second line of introtext in mainform 
        /// </summary>      
        public static String RB_FRM_MAIN_LBL_INTRO2 = "Use 'Help' if you need more information.";

        /// <summary>
        /// Title groupbox start in mainform
        /// </summary>
        public static String RB_FRM_MAIN_GB_START = "Start here";

        /// <summary>
        /// Title groupbox options in mainform
        /// </summary>
        public static String RB_FRM_MAIN_GB_OPTIONS = "Your options";

        /// <summary>
        /// Text for button 'New' in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_BTN_NEW = "New";

        /// <summary>
        /// Text for button 'Open' in mainform 
        /// </summary>        
        public static String RB_FRM_MAIN_BTN_OPEN = "Open";

        /// <summary>
        /// Text for button 'Save' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_SAVE = "Save";

        /// <summary>
        /// Text for button 'Chart' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_CHART = "Chart";

        /// <summary>
        /// Text for button 'Positions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_POSITIONS = "Positions";

        /// <summary>
        /// Text for button 'Analysis' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_ANALYSIS = "Analysis";

        /// <summary>
        /// Text for button 'Progressions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_PROGRESSIONS = "Progress";

        /// <summary>
        /// Text for button 'Help' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_HELP = "Help";


        /// <summary>
        /// Text for button 'Exit' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_EXIT = "Exit RadixPro";


        /// <summary>
        /// Text for label 'New' in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_LBL_NEW = "Calculate new chart";

        /// <summary>
        /// Text for label 'Open' in mainform 
        /// </summary>        
        public static String RB_FRM_MAIN_LBL_OPEN = "Open chart from disk";


        /// <summary>
        /// Text for label 'Chart' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_CHART = "Show chart wheel";

        /// <summary>
        /// Text for label 'Positions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_POSITIONS = "Show positions list";

        /// <summary>
        /// Text for label 'Analysis' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ANALYSIS = "Analyse chart";

        /// <summary>
        /// Text for label 'Progressions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_PROGRESSIONS = "Progress chart";

        /// <summary>
        /// Text for button 'Help' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_HELP = "Getting assistance";

        /// <summary>
        /// Label for list of available charts
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_AVAILCHARTS = "Charts ready to analyse";

        /// <summary>
        /// Label for active chart
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ACTIVECHART = "Chart currently active: none";

        /// <summary>
        /// Label for active chart with data
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ACTIVECHART_TF = "Chart currently active: ";


        /// <summary>
        /// Label select an active chart
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_SELECTACTIVE = "Select the default chart by double-clicking it";

        /// <summary>
        /// Label to indicate no charts are available
        /// </summary>
        public static String RB_FRM_MAIN_LB_AVAILCHARTS_OPTION_0 = "No charts available.";


        /* input data radix form ============================================  */

        /// <summary>
        /// Caption for titlebar form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: data input radix";

        /// <summary>
        /// Text for label title in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TITLE = "Data input radix";

        /// <summary>
        /// Text for label intro in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_INTRO = "Enter data and press OK to start the calculation";

        /// <summary>
        /// Text for label name in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_NAME = "Name or id for chart";

        /// <summary>
        /// Text for label location in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LOCATION = "Location";

        /// <summary>
        /// Text for label longitude in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LONGITUDE = "Longitude (ddd.mm.ss)";

        /// <summary>
        /// Text for label latitude in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LATITUDE = "Latitude (dd.mm.ss)";

        /// <summary>
        /// Text for label date in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_DATE = "Date (yyyy.mm.dd)";

        /// <summary>
        /// Text for label time in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TIME = "Time (hh:mm:ss)";

        /// <summary>
        /// Text for label timezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TIMEZONE = "Timezone";


        /// <summary>
        /// Text for label spectimezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SPECTIMEZONE = "Specified zone (hh:mm:ss)";


        /// <summary>
        /// Text for label charttype in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_CHARTTYPE = "Type of chart";


        /// <summary>
        /// Text for label remarks in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_REMARKS = "Remarks";

        /// <summary>
        /// Text for label sourcetype in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SOURCETYPE = "Type of source";

        /// <summary>
        /// Text for label sourcedescription in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SOURCEDESCRIPTION = "Description of source";


        /// <summary>
        /// Text for groupbox location in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_LOCATION = "Location";

        /// <summary>
        /// Text for groupbox datetime in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_DATETIME = "Date and time";

        /// <summary>
        /// Text for groupbox description in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_DESCRIPTION = "Description";

        /// <summary>
        /// Text for radiobutton north in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_NORTH = "N";

        /// <summary>
        /// Text for radiobutton south in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_SOUTH = "S";

        /// <summary>
        /// Text for radiobutton east in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_EAST = "E";

        /// <summary>
        /// Text for radiobutton west in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_WEST = "W";

        /// <summary>
        /// Text for radiobutton east for timezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_TZEAST = "E";

        /// <summary>
        /// Text for radiobutton west for timezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_TZWEST = "W";

        /// <summary>
        /// Text for radiobutton greg (Gregorian Calendar) in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_GREG = "Greg.";

        /// <summary>
        /// Text for radiobutton jul (Julian Calendar) in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_JUL = "Jul.";

        /// <summary>
        /// Text for checkbox DST in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_CB_DST = "DST";

        /// <summary>
        /// Text for checkbox spectimezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_CB_SPECTIMEZONE = "Different timezone";

        /// <summary>
        /// Text for button OK in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_OK = "OK";

        /// <summary>
        /// Text for button cancel in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_CANCEL = "Cancel";

        /// <summary>
        /// Text for button help in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_HELP = "Help";


        /* input data progressions form ============================================  */

        /// <summary>
        /// Caption for titlebar form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: data input progressions";

        /// <summary>
        /// Text for label title in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TITLE = "Data input progressions";

        /// <summary>
        /// Text for label intro in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_INTRO = "Enter data and press OK to calculate the progressive positions";

        /// <summary>
        /// Text for label event in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_EVENT = "Event";

        /// <summary>
        /// Text for label location in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LOCATION = "Location";

        /// <summary>
        /// Text for label longitude in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LONGITUDE = "Longitude (ddd.mm.ss)";

        /// <summary>
        /// Text for label latitude in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LATITUDE = "Latitude (dd.mm.ss)";

        /// <summary>
        /// Text for label date in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_DATE = "Date (yyyy.mm.dd)";

        /// <summary>
        /// Text for label time in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TIME = "Time (hh:mm:ss)";

        /// <summary>
        /// Text for label timezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TIMEZONE = "Timezone";


        /// <summary>
        /// Text for label spectimezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_SPECTIMEZONE = "Specified zone (hh:mm:ss)";


        /// <summary>
        /// Text for label remarks in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_REMARKS = "Remarks";

        /// <summary>
        /// Text for groupbox location in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_LOCATION = "Location";

        /// <summary>
        /// Text for groupbox datetime in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_DATETIME = "Date and time";

        /// <summary>
        /// Text for groupbox description in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_DESCRIPTION = "Description";

        /// <summary>
        /// Text for radiobutton north in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_NORTH = "N";

        /// <summary>
        /// Text for radiobutton south in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_SOUTH = "S";

        /// <summary>
        /// Text for radiobutton east in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_EAST = "E";

        /// <summary>
        /// Text for radiobutton west in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_WEST = "W";

        /// <summary>
        /// Text for radiobutton east for timezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_TZEAST = "E";

        /// <summary>
        /// Text for radiobutton west for timezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_TZWEST = "W";

        /// <summary>
        /// Text for radiobutton greg (Gregorian Calendar) in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_GREG = "Greg.";

        /// <summary>
        /// Text for radiobutton jul (Julian Calendar) in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_JUL = "Jul.";


        /// <summary>
        /// Text for checkbox lovcation from chart in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_CB_LOCFROMCHART = "Use location from chart";


        /// <summary>
        /// Text for checkbox DST in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_CB_DST = "DST";

        /// <summary>
        /// Text for checkbox spectimezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_CB_SPECTIMEZONE = "Different timezone";

        /// <summary>
        /// Text for button OK in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_OK = "OK";

        /// <summary>
        /// Text for button cancel in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_CANCEL = "Cancel";

        /// <summary>
        /// Text for button help in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_HELP = "Help";







        /* configuration form ================================================  */

        /// <summary>
        /// Form caption for form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: Configuration.";

        /// <summary>
        /// Title for form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_TITLE = "Configuration";

        /// <summary>
        /// Text for label intro in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_INTRO = "Define your configuration and save it";

        /// <summary>
        /// Text for label title in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_ORBIS = "Define each orbis";


        /// <summary>
        /// Text for label major in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MAJOR = "Major aspects";

        /// <summary>
        /// Text for label minor in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MINOR = "Minor aspects";

        /// <summary>
        /// Text for label midpoints in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MIDPOINTS = "Midpoints";

        /// <summary>
        /// Text for label progressions in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_PROGRESSIONS = "Progressions";

        /// <summary>
        /// Text for label additional  in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_ADDITIONAL = "Select additional points";

        /// <summary>
        /// Text for label houses in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_HOUSES = "Select a housesystems";


        /// <summary>
        /// Text for groupbox orbis in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_ORBIS = "Orbis";

        /// <summary>
        /// Text for groupbox additional in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_ADDITIONAL = "Additional points";

        /// <summary>
        /// Text for groupbox houses in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_HOUSES = "Houses";


        /// <summary>
        /// Text for checkbox lunarnode in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_CB_LUNARNODE = "Lunar node";

        /// <summary>
        /// Text for checkbox chiron in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_CB_CHIRON = "Chiron";

        /// <summary>
        /// Text for radiobutton mean in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_RA_MEAN = "Mean node";

        /// <summary>
        /// Text for radiobutton oscillating in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_RA_OSCILLATING = "Oscillating (True)";

        /// <summary>
        /// Text for button cancel in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_CANCEL = "Cancel";

        /// <summary>
        /// Text for button help in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_HELP = "Help";

        /// <summary>
        /// Text for button save in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_SAVE = "OK/Save";

        /// <summary>
        /// Text for button defaults in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_DEFAULTS = "Defaults";


        /* settings form ================================================  */

        /// <summary>
        /// Form caption for form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: Settings.";

        /// <summary>
        /// Title for form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_TITLE = "Settings";

        /// <summary>
        /// Text for label intro in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_INTRO = "Define your default settings and save it";


        /// <summary>
        /// Text for label timezone in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_TIMEZONE = "Timezone";

        /// <summary>
        /// Text for label location in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LOCATION = "Name of location";

        /// <summary>
        /// Text for label longitude in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LONGITUDE = "Longitude (ddd.mm.ss)";


        /// <summary>
        /// Text for label latitude in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LATITUDE = "Latitude (dd.mm.ss)";


        /// <summary>
        /// Text for radiobutton east in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_RA_EAST = "E";

        /// <summary>
        /// Text for radiobutton west in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_RA_WEST = "W";

        /// <summary>
        /// Text for radiobutton north in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_RA_NORTH = "N";

        /// <summary>
        /// Text for radiobutton south in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_RA_SOUTH = "S";

        /// <summary>
        /// Text for button save in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_SAVE = "OK/Save";

        /// <summary>
        /// Text for button cancel in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_CANCEL = "Cancel";

        /// <summary>
        /// Text for button help in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_HELP = "Help";



        /* form show positions ================================================  */

        /// <summary>
        /// Form caption for form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: Positions";

        /// <summary>
        /// Title for form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_LBL_TITLE = "Positions";

        /// <summary>
        /// Text for label intro in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_LBL_INTRO = "Overview of calculated positions";

        /// <summary>
        /// Text for header longitude in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_LONGITUDE = "Longitude";

        /// <summary>
        /// Text for header latitude in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_LATITUDE = "Latitude";

        /// <summary>
        /// Text for header right ascension in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_RIGHTASCENSION = "Right asc.";

        /// <summary>
        /// Text for header declination in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_DECLINATION = "Declination";

        /// <summary>
        /// Text for header speed in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_SPEED = "Speed";

        /// <summary>
        /// Text for header distance in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_DISTANCE = "Distance";

        /// <summary>
        /// Text for button remarks in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_REMARKS = "Remarks";

        /// <summary>
        /// Text for button source in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_SOURCE = "Source";

        /// <summary>
        /// Text for button help in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_HELP = "Help";

        /// <summary>
        /// Text for button chart in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_CHART = "Chart";


        /// <summary>
        /// Text for button cancel in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_CANCEL = "Close";



        /* form show chart =====================================================  */

        /// <summary>
        /// Form caption for form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_FORMTITLE = "RadixPro version " + RB_COM_VERSION + " :: Chart";

        /// <summary>
        /// Title for form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_LBL_TITLE = "Chart";


        /// <summary>
        /// Text for label positions in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_LBL_POSITIONS = "Positions";


        /// <summary>
        /// Text for button remarks in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_REMARKS = "Remarks";

        /// <summary>
        /// Text for button source in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_SOURCE = "Source";

        /// <summary>
        /// Text for button help in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_HELP = "Help";

        /// <summary>
        /// Text for button chart in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_POSITIONS = "Positions";

        /// <summary>
        /// Text for button cancel in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_CANCEL = "Close";



        /* form Analysis Aspects ==========================================  */
        /// <summary>
        /// Form caption for form Analysis Aspects
        /// </summary>
        public static String RB_FRM_ANALYSISASPECTS_FORMTITLE = "Analysis:aspects " + RB_COM_VERSION;

        /// <summary>
        /// Form title
        /// </summary>
        public static String RB_FRM_ANALYSISASPECTS_LBL_TITLE = "Aspect analysis" ;

       /// <summary>
       /// Orbis for major aspects, text is followed by actual orbis.
       /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_LBL_ORBISMAJOR = "Orbis for major aspects : ";

       /// <summary>
        /// Orbis for minor aspects, text is followed by actual orbis.
       /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_LBL_ORBISMINOR = "Orbis for minor aspects : ";


       /// <summary>
       /// Header in matrix: actual orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_ACTORBIS = "Orbis";

       /// <summary>
       /// Header in matrix: max orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_MAXORBIS = "Max. orbis";

       /// <summary>
       /// Header in matrix: percentage of orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_PRCORBIS = "% of orbis";

        /// <summary>
        /// Helpbutton
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_HELP = "Help";

        /// <summary>
        /// Button to show chart
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_CHART = "Chart";

        /// <summary>
        /// Button to close form
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_CANCEL = "Close";



       /* form Analysis Midpoints ==========================================  */
       /// <summary>
       /// Form caption for form Analysis Midpoints
       /// </summary>
       public static String RB_FRM_ANALYSISMIDPOINTS_FORMTITLE = "Analysis:midpoints " + RB_COM_VERSION;

       /// <summary>
       /// Form title
       /// </summary>
       public static String RB_FRM_ANALYSISMIDPOINTS_LBL_TITLE = "Midpoint analysis";

       /// <summary>
       /// Orbis for midpoints, text is followed by actual orbis.
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_LBL_ORBIS = "Orbis for midpoints : ";

       /// <summary>
       /// Header in matrix: actual orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_ACTORBIS = "Orbis";

       /// <summary>
       /// Header in matrix: max orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_MAXORBIS = "Max. orbis";

       /// <summary>
       /// Header in matrix: percentage of orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_PRCORBIS = "% of orbis";

       /// <summary>
       /// Helpbutton
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_HELP = "Help";

       /// <summary>
       /// Button to show chart
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_CHART = "Chart";

       /// <summary>
       /// Button to close form
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_CANCEL = "Close";


        /* form about =====================================================  */

        /// <summary>
        /// Form caption for form about
        /// </summary>
        public static String RB_FRM_ABOUT_FORMTITLE = "About RadixPro " + RB_COM_VERSION;


        /// <summary>
        /// Text for label product in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_PRODUCT = "RadixPro";

        /// <summary>
        /// Text for label version in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_VERSION = "Version : " + RB_COM_VERSION + ". " + RB_COM_VERSIONDATE;

        /// <summary>
        /// Text for label copyright in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_COPYRIGHT = RB_COM_COPYRIGHT;

        /// <summary>
        /// Text for label webaddress in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_WEBADDRESS = "RadixPro on the web: http://radixpro.com";

        /// <summary>
        /// Text for textbox description in form about
        /// </summary>
        public static String RB_FRM_ABOUT_TB_DESCRIPTION =
            "RadixPro is a program for astrologers,  " +
            "based on the Swiss Ephemeris from Astrodienst Zürich. " +
            "The program is free and open source. " +
            "The development of new versions is driven by user input. " +
            "If you like to have anything added to RadixPro you can " +
            "visit our forum and make a proposal. " +
            "You can contact us via our website http://radixpro.com";

        /// <summary>
        /// Text for button OK in form about
        /// </summary>
        public static String RB_FRM_ABOUT_BTN_OK = "OK";


        /* form Frm_TextPopup  =====================================================  */
        /// <summary>
        /// Text for header 'remarks' in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_HEADERREMARKS = "Remarks";

        /// <summary>
        /// Text for header 'source' in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_HEADERSOURCE = "Source";

        /// <summary>
        /// Caption for button in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_BTN_OK = "Close";


        /// <summary>
        /// Texts for timezones, index should run from 0 to 34
        /// </summary>
        public static string[] RB_TIMEZONES = { 
            "+ 0:00 - GMT / UT",                        // 0
            "+ 0:20 - MAT (Amsterdam)",                 // 1
            "+ 1:00 - CET / Central European Time",     // 2
            "+ 2:00 - East European Time",              // 3
            "+ 3:00 - Baghdad Time",                    // 4
            "+ 3:30 - Iran Time",                       // 5
            "+ 4:00 - Former USSR zone 3",              // 6
            "+ 5:00 - Former USSR zone 4",              // 7
            "+ 5:30 - India Standard Time",             // 8
            "+ 6:00 - Former USSR zone 5",              // 9
            "+ 6:30 - North Sumatra Time",              // 10
            "+ 7:00 - Former USSR zone 6",              // 11
            "+ 7:30 - Java Time",                       // 12
            "+ 8:00 - Australia: West",                 // 13
            "+ 8:30 - Molucs Time",                     // 14
            "+ 9:00 - Japan Standard Time",             // 15
            "+ 9:30 - Australia: Central",              // 16
            "+10:00 - Australia: East",                 // 17
            "+10:30 - Australia: South",                // 18
            "+11:00 - Former USSR zone 10",             // 19
            "+11:30 - New Sealand - 1",                 // 20
            "+12:00 - New Sealand - 2",                 // 21
            "-11:00 - Bering Time",                     // 22
            "-10:30 - Hawai",                           // 23
            "-10:00 - Alaska-Hawai",                    // 24
            "- 9:00 - Yukon",                           // 25
            "- 8:00 - Pacific",                         // 26
            "- 7:00 - US:MST",                          // 27
            "- 6:00 - US:CST",                          // 28
            "- 5:00 - US:EST",                          // 29
            "- 4:00 - Atlantic Time",                   // 30
            "- 3:30 - New Foundland",                   // 31
            "- 3:00 - Brazilia zone 2",                 // 32
            "- 2:00 - Azores",                          // 33
            "- 1:00 - West Africa"                      // 34
        };

        /// <summary>
        /// Texts for types of charts
        /// </summary>
        public static string[] RB_CHARTTYPES = {
            "Female",
            "Male",
            "Event",
            "Horary"
        };

        /// <summary>
        /// Texts forB  types of source
        /// </summary>
        public static string[] RB_SOURCETYPES = {
            "AA - From Birth Record",
            "A  - From memory or News report",
            "B  - From biography",
            "C  - Source unknown or rectified",
            "DD - Conflicting or unverified",
            "X  - Time unknown",
            "XX - Undetermined"
        };

        /// <summary>
        /// Texts forB  types of source
        /// </summary>
        public static string[] RB_HOUSESYSTEMS = {
             "Ascendant equal"         // 0, A or E
            ,"Alcabitius"              // 1, B            
            ,"Axial Rotation"          // 2, X
            ,"Campanus"                // 3, C               
            ,"Gauquelin sectors"       // 4, G
            ,"Horizontal (Azimuthal)"  // 5, H
            ,"Koch"                    // 6, K
            ,"Krusinski"               // 7, U
            ,"Morinus"                 // 8, M
            ,"Placidus"                // 9, P
            ,"Porphyrius"              // 10, O              
            ,"Regiomontanus"           // 11, R
            ,"Topocentric"             // 12, T
            ,"Vehlow equal"            // 13, V
                                                 };



    }

}
