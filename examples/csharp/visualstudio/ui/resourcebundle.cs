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
    /// Dutch version
    /// </summary>
    public static class ResourceBundle {
        /// <summary>
        /// Version of RadixPro
        /// </summary>
        public static String RB_COM_VERSION = "0.9";

        /// <summary>
        /// Versiondate of RadixPro
        /// </summary>
        public static String RB_COM_VERSIONDATE = "Maart 2008";

        /// <summary>
        /// Copyright for RadixPro
        /// </summary>
        public static String RB_COM_COPYRIGHT = "(c) Jan Kampherbeek, RadixPro.";


        /// <summary>
        /// Default text for setting location.
        /// </summary>
        public static String RB_DEFAULT_LOCATION = "Niet gedefiniëerd";

        /// <summary>
        /// Text for DST
        /// </summary>
        public static String RB_COM_DST = "Zomertijd";

        /// <summary>
        /// Text for Timezone
        /// </summary>
        public static String RB_COM_ZONE = "Tijdzone";

        /// <summary>
        /// Text for northern latitude
        /// </summary>
        public static String RB_COM_LAT_NORTH = "N";

        /// <summary>
        /// Text for southern latitude
        /// </summary>
        public static String RB_COM_LAT_SOUTH = "Z";


        /// <summary>
        /// Text for eastern longitude
        /// </summary>
        public static String RB_COM_LONG_EAST = "O";

        /// <summary>
        /// Text for western longitude
        /// </summary>
        public static String RB_COM_LONG_WEST = "W";


        /* menu main form ===========================================  */

        /// <summary>
        /// Caption menuitem File
        /// </summary>
        public static String RB_FRM_MAIN_MI_FILE = "Horoscoop";


        /// <summary>
        /// Caption menuitem New Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_NEWCHART = "&Nieuwe horoscoop";

        /// <summary>
        /// Caption menuitem Open Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_OPENCHART = "&Open Horoscoop";

        /// <summary>
        /// Caption menuitem Save Chart
        /// </summary>
        public static String RB_FRM_MAIN_MI_SAVE = "&Sla horoscoop op";

        /// <summary>
        /// Caption menuitem Save all
        /// </summary>
        public static String RB_FRM_MAIN_MI_SAVEALL = "Sla alles op";

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
        public static String RB_FRM_MAIN_MI_EXIT = "Stop RadixPro";

        /// <summary>
        /// Caption menuitem results
        /// </summary>
        public static String RB_FRM_MAIN_MI_RESULTS = "Resultaten";

        /// <summary>
        /// Caption menuitem wheel
        /// </summary>
        public static String RB_FRM_MAIN_MI_WHEEL = "Horoscoop figuur";

        /// <summary>
        /// Caption menuitem POSITIONS
        /// </summary>
        public static String RB_FRM_MAIN_MI_POSTIONS = "Posities";

        /// <summary>
        /// Caption menuitem analysis
        /// </summary>
        public static String RB_FRM_MAIN_MI_ANALYSIS = "Analyse";

        /// <summary>
        /// Caption menuitem aspects
        /// </summary>
        public static String RB_FRM_MAIN_MI_ASPECTS = "Aspecten";

        /// <summary>
        /// Caption menuitem MIDPOINTS
        /// </summary>
        public static String RB_FRM_MAIN_MI_MIDPOINTS = "Halfsommen";

        /// <summary>
        /// Caption menuitem progressions
        /// </summary>
        public static String RB_FRM_MAIN_MI_PROGRESSIONS = "Progressies";


        /// <summary>
        /// Caption menuitem transits
        /// </summary>
        public static String RB_FRM_MAIN_MI_TRANSITS = "Transits";

        /// <summary>
        /// Caption menuitem secundary
        /// </summary>
        public static String RB_FRM_MAIN_MI_SECUNDARY = "Secundary directies";

        /// <summary>
        /// Caption menuitem preferences
        /// </summary>
        public static String RB_FRM_MAIN_MI_PREFERENCES = "Voorkeuren";

        /// <summary>
        /// Caption menuitem configuration
        /// </summary>
        public static String RB_FRM_MAIN_MI_CONFIGURATIION = "Configurate";

        /// <summary>
        /// Caption menuitem settings
        /// </summary>
        public static String RB_FRM_MAIN_MI_SETTINGS = "Instellingen";

        /// <summary>
        /// Caption menuitem help
        /// </summary>
        public static String RB_FRM_MAIN_MI_HELP = "Help";

        /// <summary>
        /// Caption menuitem help contents
        /// </summary>
        public static String RB_FRM_MAIN_MI_CONTENTS = "Inhoud";

        /// <summary>
        /// Caption menuitem help index
        /// </summary>
        public static String RB_FRM_MAIN_MI_INDEX = "Index";

        /// <summary>
        /// Caption menuitem about
        /// </summary>
        public static String RB_FRM_MAIN_MI_ABOUT = "Over...";




        /* main form ================================================  */

        /// <summary>
        /// Caption for titlebar mainform
        /// </summary>
        public static String RB_FRM_MAIN_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " Beta";

        /// <summary>
        /// Title mainform
        /// </summary>
        public static String RB_FRM_MAIN_LBL_TITLE = "RadixPro " + RB_COM_VERSION;

        /// <summary>
        /// First line of introtext in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_LBL_INTRO1 = "Begin met 'Nieuw'.";

        /// <summary>
        /// Second line of introtext in mainform 
        /// </summary>      
        public static String RB_FRM_MAIN_LBL_INTRO2 = "Gebruik 'Help' als je meer informatie wilt.";

        /// <summary>
        /// Title groupbox start in mainform
        /// </summary>
        public static String RB_FRM_MAIN_GB_START = "Begin hier";

        /// <summary>
        /// Title groupbox options in mainform
        /// </summary>
        public static String RB_FRM_MAIN_GB_OPTIONS = "Je keuzes";

        /// <summary>
        /// Text for button 'New' in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_BTN_NEW = "Nieuw";

        /// <summary>
        /// Text for button 'Open' in mainform 
        /// </summary>        
        public static String RB_FRM_MAIN_BTN_OPEN = "Open";

        /// <summary>
        /// Text for button 'Save' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_SAVE = "Bewaar";

        /// <summary>
        /// Text for button 'Chart' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_CHART = "Figuur";

        /// <summary>
        /// Text for button 'Positions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_POSITIONS = "Posities";

        /// <summary>
        /// Text for button 'Analysis' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_ANALYSIS = "Analyse";

        /// <summary>
        /// Text for button 'Progressions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_PROGRESSIONS = "Progressies";

        /// <summary>
        /// Text for button 'Help' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_HELP = "Help";


        /// <summary>
        /// Text for button 'Exit' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_BTN_EXIT = "Stop RadixPro";


        /// <summary>
        /// Text for label 'New' in mainform 
        /// </summary>
        public static String RB_FRM_MAIN_LBL_NEW = "Bereken nieuwe horoscoop";

        /// <summary>
        /// Text for label 'Open' in mainform 
        /// </summary>        
        public static String RB_FRM_MAIN_LBL_OPEN = "Open horoscoop";


        /// <summary>
        /// Text for label 'Chart' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_CHART = "Toon horoscoop figuur";

        /// <summary>
        /// Text for label 'Positions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_POSITIONS = "Toon lijst met posities";

        /// <summary>
        /// Text for label 'Analysis' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ANALYSIS = "Analyse horoscoop";

        /// <summary>
        /// Text for label 'Progressions' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_PROGRESSIONS = "Progressies";

        /// <summary>
        /// Text for button 'Help' in mainform 
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_HELP = "Meer ondersteuning";

        /// <summary>
        /// Label for list of available charts
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_AVAILCHARTS = "Horoscopen beschikbaar voor analyse";

        /// <summary>
        /// Label for active chart
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ACTIVECHART = "Momenteel actieve horoscoop: geen";

        /// <summary>
        /// Label for active chart with data
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_ACTIVECHART_TF = "Momenteel actieve horoscoop: ";


        /// <summary>
        /// Label select an active chart
        /// </summary>  
        public static String RB_FRM_MAIN_LBL_SELECTACTIVE = "Kies de standaard horoscoop door te dubbelklikken";

        /// <summary>
        /// Label to indicate no charts are available
        /// </summary>
        public static String RB_FRM_MAIN_LB_AVAILCHARTS_OPTION_0 = "Geen horoscopen beschikbaar.";


        /* input data radix form ============================================  */

        /// <summary>
        /// Caption for titlebar form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " :: gegevens invoer radix";

        /// <summary>
        /// Text for label title in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TITLE = "Gegevens invoer radix";

        /// <summary>
        /// Text for label intro in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_INTRO = "Voer de gegevens in en klik op OK om de berekening te starten.";

        /// <summary>
        /// Text for label name in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_NAME = "Naam";

        /// <summary>
        /// Text for label location in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LOCATION = "Locatie";

        /// <summary>
        /// Text for label longitude in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LONGITUDE = "Lengte (ggg.mm.ss)";

        /// <summary>
        /// Text for label latitude in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_LATITUDE = "Breedte (gg.mm.ss)";

        /// <summary>
        /// Text for label date in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_DATE = "Datum (jjjj.mm.dd)";

        /// <summary>
        /// Text for label time in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TIME = "Tijd (uu:mm:ss)";

        /// <summary>
        /// Text for label timezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_TIMEZONE = "Tijdzone";


        /// <summary>
        /// Text for label spectimezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SPECTIMEZONE = "Aparte tijdzone (uu:mm:ss)";


        /// <summary>
        /// Text for label charttype in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_CHARTTYPE = "Soort horoscoop";


        /// <summary>
        /// Text for label remarks in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_REMARKS = "Opmerkingen";

        /// <summary>
        /// Text for label sourcetype in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SOURCETYPE = "Soort bron";

        /// <summary>
        /// Text for label sourcedescription in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_LBL_SOURCEDESCRIPTION = "Beschrijving bron";


        /// <summary>
        /// Text for groupbox location in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_LOCATION = "Locatie";

        /// <summary>
        /// Text for groupbox datetime in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_DATETIME = "Datum en tijd";

        /// <summary>
        /// Text for groupbox description in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_GB_DESCRIPTION = "Beschrijving";

        /// <summary>
        /// Text for radiobutton north in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_NORTH = "N";

        /// <summary>
        /// Text for radiobutton south in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_SOUTH = "Z";

        /// <summary>
        /// Text for radiobutton east in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_EAST = "O";

        /// <summary>
        /// Text for radiobutton west in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_WEST = "W";

        /// <summary>
        /// Text for radiobutton east for timezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_RA_TZEAST = "O";

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
        public static String RB_FRM_DATARADIX_CB_DST = "Zomertijd";

        /// <summary>
        /// Text for checkbox spectimezone in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_CB_SPECTIMEZONE = "Specificatie tijdzone";

        /// <summary>
        /// Text for button OK in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_OK = "OK";

        /// <summary>
        /// Text for button cancel in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_CANCEL = "Sluit";

        /// <summary>
        /// Text for button help in form datainput radix
        /// </summary>
        public static String RB_FRM_DATARADIX_BTN_HELP = "Help";


        /* input data progressions form ============================================  */

        /// <summary>
        /// Caption for titlebar form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_FORMTITLE = "RadixPro verse " + RB_COM_VERSION + " :: data invoer progressies";

        /// <summary>
        /// Text for label title in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TITLE = "Data invoer progressies";

        /// <summary>
        /// Text for label intro in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_INTRO = "Voer de gegevens in en klik op OK om de berekening te starten";

        /// <summary>
        /// Text for label event in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_EVENT = "Gebeurtenis";

        /// <summary>
        /// Text for label location in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LOCATION = "Locatie";

        /// <summary>
        /// Text for label longitude in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LONGITUDE = "Lengte (ggg.mm.ss)";

        /// <summary>
        /// Text for label latitude in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_LATITUDE = "Breedte (gg.mm.ss)";

        /// <summary>
        /// Text for label date in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_DATE = "Datum (jjjj.mm.dd)";

        /// <summary>
        /// Text for label time in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TIME = "Tijd (uu:mm:ss)";

        /// <summary>
        /// Text for label timezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_TIMEZONE = "Tijdzone";


        /// <summary>
        /// Text for label spectimezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_SPECTIMEZONE = "Aparte tijdzone (uu:mm:ss)";


        /// <summary>
        /// Text for label remarks in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_LBL_REMARKS = "Opmerkingen";

        /// <summary>
        /// Text for groupbox location in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_LOCATION = "Locatie";

        /// <summary>
        /// Text for groupbox datetime in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_DATETIME = "Datum en tijd";

        /// <summary>
        /// Text for groupbox description in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_GB_DESCRIPTION = "Beschrijving";

        /// <summary>
        /// Text for radiobutton north in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_NORTH = "N";

        /// <summary>
        /// Text for radiobutton south in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_SOUTH = "Z";

        /// <summary>
        /// Text for radiobutton east in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_EAST = "O";

        /// <summary>
        /// Text for radiobutton west in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_WEST = "W";

        /// <summary>
        /// Text for radiobutton east for timezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_RA_TZEAST = "O";

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
        public static String RB_FRM_DATAPROG_CB_LOCFROMCHART = "Gebruik locatie uit radix";


        /// <summary>
        /// Text for checkbox DST in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_CB_DST = "Zomertijd";

        /// <summary>
        /// Text for checkbox spectimezone in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_CB_SPECTIMEZONE = "Afwijkende tijdzone";

        /// <summary>
        /// Text for button OK in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_OK = "OK";

        /// <summary>
        /// Text for button cancel in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_CANCEL = "Sluit";

        /// <summary>
        /// Text for button help in form datainput progressions
        /// </summary>
        public static String RB_FRM_DATAPROG_BTN_HELP = "Help";







        /* configuration form ================================================  */

        /// <summary>
        /// Form caption for form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " :: Configuratie.";

        /// <summary>
        /// Title for form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_TITLE = "Configurate";

        /// <summary>
        /// Text for label intro in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_INTRO = "Bepaal je configuratie en sla deze op";

        /// <summary>
        /// Text for label title in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_ORBIS = "Bepaal de obr";


        /// <summary>
        /// Text for label major in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MAJOR = "Majeure aspecten";

        /// <summary>
        /// Text for label minor in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MINOR = "Mineure aspecten";

        /// <summary>
        /// Text for label midpoints in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_MIDPOINTS = "Halfsommen";

        /// <summary>
        /// Text for label progressions in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_PROGRESSIONS = "Progressies";

        /// <summary>
        /// Text for label additional  in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_ADDITIONAL = "Kies aanvullende punten";

        /// <summary>
        /// Text for label houses in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_LBL_HOUSES = "Kies een huizensysteem";


        /// <summary>
        /// Text for groupbox orbis in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_ORBIS = "Orb";

        /// <summary>
        /// Text for groupbox additional in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_ADDITIONAL = "Aanvullende punten";

        /// <summary>
        /// Text for groupbox houses in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_GB_HOUSES = "Huizen";


        /// <summary>
        /// Text for checkbox lunarnode in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_CB_LUNARNODE = "Maan node";

        /// <summary>
        /// Text for checkbox chiron in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_CB_CHIRON = "Chiron";

        /// <summary>
        /// Text for radiobutton mean in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_RA_MEAN = "Gemiddelde node";

        /// <summary>
        /// Text for radiobutton oscillating in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_RA_OSCILLATING = "Oscillerend (True)";

        /// <summary>
        /// Text for button cancel in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_CANCEL = "Sluit af";

        /// <summary>
        /// Text for button help in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_HELP = "Help";

        /// <summary>
        /// Text for button save in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_SAVE = "OK/Sla op";

        /// <summary>
        /// Text for button defaults in form configuration
        /// </summary>
        public static String RB_FRM_CONFIGURATION_BTN_DEFAULTS = "Standaard";


        /* settings form ================================================  */

        /// <summary>
        /// Form caption for form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " :: Instellingen.";

        /// <summary>
        /// Title for form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_TITLE = "Instellingen";

        /// <summary>
        /// Text for label intro in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_INTRO = "Bepaal je standaard instelllingen en sla deze op";


        /// <summary>
        /// Text for label timezone in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_TIMEZONE = "Tijdzone";

        /// <summary>
        /// Text for label location in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LOCATION = "Naam locatie";

        /// <summary>
        /// Text for label longitude in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LONGITUDE = "Lengte (ggg.mm.ss)";


        /// <summary>
        /// Text for label latitude in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_LBL_LATITUDE = "Breedte (gg.mm.ss)";


        /// <summary>
        /// Text for radiobutton east in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_RA_EAST = "O";

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
        public static String RB_FRM_SETTINGS_RA_SOUTH = "Z";

        /// <summary>
        /// Text for button save in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_SAVE = "OK/Sla op";

        /// <summary>
        /// Text for button cancel in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_CANCEL = "Sluit af";

        /// <summary>
        /// Text for button help in form settings
        /// </summary>
        public static String RB_FRM_SETTINGS_BTN_HELP = "Help";



        /* form show positions ================================================  */

        /// <summary>
        /// Form caption for form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " :: Posities";

        /// <summary>
        /// Title for form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_LBL_TITLE = "Posities";

        /// <summary>
        /// Text for label intro in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_LBL_INTRO = "Overzicht berekende posities";

        /// <summary>
        /// Text for header longitude in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_LONGITUDE = "Lengte";

        /// <summary>
        /// Text for header latitude in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_LATITUDE = "Breedte";

        /// <summary>
        /// Text for header right ascension in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_RIGHTASCENSION = "Rechte Klimming";

        /// <summary>
        /// Text for header declination in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_DECLINATION = "Declinatie";

        /// <summary>
        /// Text for header speed in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_SPEED = "Snelheid";

        /// <summary>
        /// Text for header distance in matrix in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_TXT_DISTANCE = "Afstand";

        /// <summary>
        /// Text for button remarks in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_REMARKS = "Opmerkingen";

        /// <summary>
        /// Text for button source in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_SOURCE = "Bron";

        /// <summary>
        /// Text for button help in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_HELP = "Help";

        /// <summary>
        /// Text for button chart in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_CHART = "Figuur";


        /// <summary>
        /// Text for button cancel in form show positions
        /// </summary>
        public static String RB_FRM_SHOWPOSITIONS_BTN_CANCEL = "Sluit af";



        /* form show chart =====================================================  */

        /// <summary>
        /// Form caption for form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_FORMTITLE = "RadixPro versie " + RB_COM_VERSION + " :: Figuur";

        /// <summary>
        /// Title for form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_LBL_TITLE = "Horoscoop figuur";


        /// <summary>
        /// Text for label positions in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_LBL_POSITIONS = "Posities";


        /// <summary>
        /// Text for button remarks in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_REMARKS = "Opmerkingen";

        /// <summary>
        /// Text for button source in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_SOURCE = "Bron";

        /// <summary>
        /// Text for button help in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_HELP = "Help";

        /// <summary>
        /// Text for button chart in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_POSITIONS = "Posities";

        /// <summary>
        /// Text for button cancel in form show chart
        /// </summary>
        public static String RB_FRM_SHOWCHART_BTN_CANCEL = "Sluit af";



        /* form Analysis Aspects ==========================================  */
        /// <summary>
        /// Form caption for form Analysis Aspects
        /// </summary>
        public static String RB_FRM_ANALYSISASPECTS_FORMTITLE = "Analyse:aspecten " + RB_COM_VERSION;

        /// <summary>
        /// Form title
        /// </summary>
        public static String RB_FRM_ANALYSISASPECTS_LBL_TITLE = "Aspect analyse" ;

       /// <summary>
       /// Orbis for major aspects, text is followed by actual orbis.
       /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_LBL_ORBISMAJOR = "Orb majeure aspecten : ";

       /// <summary>
        /// Orbis for minor aspects, text is followed by actual orbis.
       /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_LBL_ORBISMINOR = "Orb mineure aspecten : ";


       /// <summary>
       /// Header in matrix: actual orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_ACTORBIS = "Orb";

       /// <summary>
       /// Header in matrix: max orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_MAXORBIS = "Max. orb";

       /// <summary>
       /// Header in matrix: percentage of orbis
       /// </summary>
       public static string RB_FRM_ANALYSISASPECTS_TXT_PRCORBIS = "% van orb";

        /// <summary>
        /// Helpbutton
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_HELP = "Help";

        /// <summary>
        /// Button to show chart
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_CHART = "Figuur";

        /// <summary>
        /// Button to close form
        /// </summary>
        public static string RB_FRM_ANALYSISASPECTS_BTN_CANCEL = "Sluit af";



       /* form Analysis Midpoints ==========================================  */
       /// <summary>
       /// Form caption for form Analysis Midpoints
       /// </summary>
       public static String RB_FRM_ANALYSISMIDPOINTS_FORMTITLE = "Analyse:halfsommen " + RB_COM_VERSION;

       /// <summary>
       /// Form title
       /// </summary>
       public static String RB_FRM_ANALYSISMIDPOINTS_LBL_TITLE = "Halfsom analyse";

       /// <summary>
       /// Orbis for midpoints, text is followed by actual orbis.
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_LBL_ORBIS = "Orb voor halfsommen : ";

       /// <summary>
       /// Header in matrix: actual orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_ACTORBIS = "Orb";

       /// <summary>
       /// Header in matrix: max orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_MAXORBIS = "Max. orb";

       /// <summary>
       /// Header in matrix: percentage of orbis
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_TXT_PRCORBIS = "% van orb";

       /// <summary>
       /// Helpbutton
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_HELP = "Help";

       /// <summary>
       /// Button to show chart
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_CHART = "Figuur";

       /// <summary>
       /// Button to close form
       /// </summary>
       public static string RB_FRM_ANALYSISMIDPOINTS_BTN_CANCEL = "Sluit af";


        /* form about =====================================================  */

        /// <summary>
        /// Form caption for form about
        /// </summary>
        public static String RB_FRM_ABOUT_FORMTITLE = "Over RadixPro " + RB_COM_VERSION;


        /// <summary>
        /// Text for label product in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_PRODUCT = "RadixPro";

        /// <summary>
        /// Text for label version in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_VERSION = "Versie : " + RB_COM_VERSION + ". " + RB_COM_VERSIONDATE;

        /// <summary>
        /// Text for label copyright in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_COPYRIGHT = RB_COM_COPYRIGHT;

        /// <summary>
        /// Text for label webaddress in form about
        /// </summary>
        public static String RB_FRM_ABOUT_LBL_WEBADDRESS = "RadixPro op Internet: http://radixpro.nl";

        /// <summary>
        /// Text for textbox description in form about
        /// </summary>
        public static String RB_FRM_ABOUT_TB_DESCRIPTION =
            "RadixPro is een programma voor astrologen en is  " +
            "gebaseerd op de Swiss Ephemeris van Astrodienst in Zürich. " +
            "Het programma is gratis en open source. " +
            "De ontwikkeling van nieuwe versies is gebaseerd op gebruikers input. " +
            "Als je iets toegevoegd wilt zien aan RadixPro kun je ons  " +
            "forum bezoeken en een voorstel doen. " +
            "Je kunt contact opnemen via onze website http://radixpro.nl, " +
            "de broncode kun je downloaden  via http://radixpro.org";

        /// <summary>
        /// Text for button OK in form about
        /// </summary>
        public static String RB_FRM_ABOUT_BTN_OK = "OK";


        /* form Frm_TextPopup  =====================================================  */
        /// <summary>
        /// Text for header 'remarks' in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_HEADERREMARKS = "Opmerkingen";

        /// <summary>
        /// Text for header 'source' in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_HEADERSOURCE = "Bron";

        /// <summary>
        /// Caption for button in popup window
        /// </summary>
        public static string RB_FRM_TEXTPOPUP_BTN_OK = "Sluit af";


        /// <summary>
        /// Texts for timezones, index should run from 0 to 34
        /// </summary>
        public static string[] RB_TIMEZONES = { 
            "+ 0:00 - GMT / UT",                        // 0
            "+ 0:20 - MAT (Amsterdam)",                 // 1
            "+ 1:00 - CET / Centraal Europese Tijd",    // 2
            "+ 2:00 - Oost Europese Tijd",              // 3
            "+ 3:00 - Baghdad Tijd",                    // 4
            "+ 3:30 - Iran Tijd",                       // 5
            "+ 4:00 - Voorm. USSR zone 3",              // 6
            "+ 5:00 - Voorm. USSR zone 4",              // 7
            "+ 5:30 - India Standaard Tijd",            // 8
            "+ 6:00 - Voorm. USSR zone 5",              // 9
            "+ 6:30 - Noord Sumatra Tijd",              // 10
            "+ 7:00 - Voorm. USSR zone 6",              // 11
            "+ 7:30 - Java Tijd",                       // 12
            "+ 8:00 - Australië: West",                 // 13
            "+ 8:30 - Molukse Tijd",                    // 14
            "+ 9:00 - Japan Standaard Tijd",            // 15
            "+ 9:30 - Australië: Centraal",             // 16
            "+10:00 - Australië: Oost",                 // 17
            "+10:30 - Australië: Zuid",                 // 18
            "+11:00 - Voorm. USSR zone 10",             // 19
            "+11:30 - Nieuw Zeeland - 1",               // 20
            "+12:00 - Nieuw Zeeland - 2",               // 21
            "-11:00 - Bering Tijd",                     // 22
            "-10:30 - Hawai",                           // 23
            "-10:00 - Alaska-Hawai",                    // 24
            "- 9:00 - Yukon",                           // 25
            "- 8:00 - Pacific",                         // 26
            "- 7:00 - US:MST",                          // 27
            "- 6:00 - US:CST",                          // 28
            "- 5:00 - US:EST",                          // 29
            "- 4:00 - Atlantische Tijd",                // 30
            "- 3:30 - New Foundland",                   // 31
            "- 3:00 - Brazilië zone 2",                 // 32
            "- 2:00 - Azoren",                          // 33
            "- 1:00 - West Afrika"                      // 34
        };

        /// <summary>
        /// Texts for types of charts
        /// </summary>
        public static string[] RB_CHARTTYPES = {
            "Vrouw",
            "Man",
            "Gebeurtenis",
            "Uurhoek"
        };

        /// <summary>
        /// Texts forB  types of source
        /// </summary>
        public static string[] RB_SOURCETYPES = {
            "AA - Geboorteakte",
            "A  - Herinnering of nieuws rapportage",
            "B  - Biografie",
            "C  - Onbekend of gerectificeerd",
            "DD - Tegenstrijdig of onbevestigd",
            "X  - Tijd onbekend",
            "XX - Niet vastgesteld"
        };

        /// <summary>
        /// Texts forB  types of source
        /// </summary>
        public static string[] RB_HOUSESYSTEMS = {
             "Ascendant gelijke huzien"     // 0, A or E
            ,"Alcabitius"                   // 1, B            
            ,"Axiale Rotatie"               // 2, X
            ,"Campanus"                     // 3, C               
            ,"Gauquelin sectoren"           // 4, G
            ,"Horizontaal (Azimutaal)"      // 5, H
            ,"Koch / Geboorteplaatshuizen"  // 6, K
            ,"Krusinski"                    // 7, U
            ,"Morinus"                      // 8, M
            ,"Placidus"                     // 9, P
            ,"Porphyrius"                   // 10, O              
            ,"Regiomontanus"                // 11, R
            ,"Topocentrisch"                // 12, T
            ,"Vehlow gelijke huizen"        // 13, V
                                                 };



    }

}
