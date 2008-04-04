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
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using radixpro.controller;
using radixpro.data;

namespace radixpro.ui {

    /// <summary>
    /// Form for settings
    /// </summary>
    public partial class Frm_Settings : Form {

        private Agent _agent;


        /// <summary>
        /// Form to maintain settings
        /// </summary>
        public Frm_Settings() {
            InitializeComponent();
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get {return _agent; }
            set {
                _agent = value;
                setCaptions();
                setValues();
            }
        }


        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_CONFIGURATION_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_TITLE;
            lbl_intro.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_INTRO;
            lbl_timezone.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_TIMEZONE;
            lbl_location.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_LOCATION;
            lbl_longitude.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_LONGITUDE;
            lbl_latitude.Text = ResourceBundle.RB_FRM_SETTINGS_LBL_LATITUDE;
            ra_east.Text = ResourceBundle.RB_FRM_SETTINGS_RA_EAST;
            ra_west.Text = ResourceBundle.RB_FRM_SETTINGS_RA_WEST;
            ra_north.Text = ResourceBundle.RB_FRM_SETTINGS_RA_NORTH;
            ra_south.Text = ResourceBundle.RB_FRM_SETTINGS_RA_SOUTH;
            btn_cancel.Text = ResourceBundle.RB_FRM_SETTINGS_BTN_CANCEL;
            btn_save.Text = ResourceBundle.RB_FRM_SETTINGS_BTN_SAVE;
            btn_help.Text = ResourceBundle.RB_FRM_SETTINGS_BTN_HELP;

            TimeZoneList timeZoneList = new TimeZoneList(); 
            for (int i = 0; i < Constants.C_RP_TOTAL_TIMEZONES; i++) {
               co_timezones.Items.Add(((RpTimeZone)timeZoneList.theList[i]).description);
            }

        }


        private void setValues() {
           tb_location.Text = agent.settings.location;
           tb_longitude.Text = agent.settings.longitude;
           tb_latitude.Text = agent.settings.latitude;
           co_timezones.SelectedIndex = agent.settings.timezone;
           ra_north.Checked = true;
           if (agent.settings.geoLatDirection == "-") ra_south.Checked = true;
            ra_east.Checked = true;
            if (agent.settings.geoLongDirection == "-") ra_west.Checked = true;
        }

        private void btn_cancel_Click(object sender, EventArgs e) {
           agent.sendMessage(new RpMessage(Messages.MSG_CANCEL, this));
        }

        private void btn_save_Click(object sender, EventArgs e) {
           agent.settings.location = tb_location.Text;
           agent.settings.longitude = tb_longitude.Text;
           agent.settings.latitude = tb_latitude.Text;
           agent.settings.timezone = Convert.ToInt32(co_timezones.SelectedIndex);
           if (ra_east.Checked) agent.settings.geoLongDirection = "+";
           else agent.settings.geoLongDirection = "-";
           if (ra_north.Checked) agent.settings.geoLatDirection = "+";
           else agent.settings.geoLatDirection = "-";

           agent.settings.saveSettings();
           this.Close();

        }

    }
}