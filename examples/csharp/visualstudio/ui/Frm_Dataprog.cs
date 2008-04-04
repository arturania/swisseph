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

namespace radixpro.ui {

    /// <summary>
    /// Form for input data for event.
    /// Not used in the current version.
    /// </summary>
    public partial class Frm_Dataprog : Form {

        private Agent _agent;



        /// <summary>
        /// Form for input progressive data
        /// </summary>
        public Frm_Dataprog() {
            InitializeComponent();
            setCaptions();
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get {return _agent; }
            set {_agent = value; }
        }


        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_DATAPROG_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_TITLE;
            lbl_intro.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_INTRO;
            lbl_event.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_EVENT;
            lbl_location.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_LOCATION;
            lbl_longitude.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_LONGITUDE;
            lbl_latitude.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_LATITUDE;
            lbl_date.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_DATE;
            lbl_time.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_TIME;
            lbl_timezone.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_TIMEZONE;
            lbl_spectimezone.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_SPECTIMEZONE;
            lbl_remarks.Text = ResourceBundle.RB_FRM_DATAPROG_LBL_REMARKS;
            gb_location.Text = ResourceBundle.RB_FRM_DATAPROG_GB_LOCATION;
            gb_datetime.Text = ResourceBundle.RB_FRM_DATAPROG_GB_DATETIME;
            gb_description.Text = ResourceBundle.RB_FRM_DATAPROG_GB_DESCRIPTION;
            ra_north.Text = ResourceBundle.RB_FRM_DATAPROG_RA_NORTH;
            ra_south.Text = ResourceBundle.RB_FRM_DATAPROG_RA_SOUTH;
            ra_east.Text = ResourceBundle.RB_FRM_DATAPROG_RA_EAST;
            ra_west.Text = ResourceBundle.RB_FRM_DATAPROG_RA_WEST;
            ra_tzeast.Text = ResourceBundle.RB_FRM_DATAPROG_RA_TZEAST;
            ra_tzwest.Text = ResourceBundle.RB_FRM_DATAPROG_RA_TZWEST;
            ra_greg.Text = ResourceBundle.RB_FRM_DATAPROG_RA_GREG;
            ra_jul.Text = ResourceBundle.RB_FRM_DATAPROG_RA_JUL;
            cb_locfromchart.Text = ResourceBundle.RB_FRM_DATAPROG_CB_LOCFROMCHART;
            cb_dst.Text = ResourceBundle.RB_FRM_DATAPROG_CB_DST;
            cb_spectimezone.Text = ResourceBundle.RB_FRM_DATAPROG_CB_SPECTIMEZONE;
            btn_ok.Text = ResourceBundle.RB_FRM_DATAPROG_BTN_OK;
            btn_cancel.Text = ResourceBundle.RB_FRM_DATAPROG_BTN_CANCEL;
            btn_help.Text = ResourceBundle.RB_FRM_DATAPROG_BTN_HELP;

        }

        private void btn_ok_Click(object sender, EventArgs e) {
            // TODO perform calculation and check results

            agent.sendMessage(new RpMessage(Messages.MSG_NEWCASE_COMPLETED, this));

        }
    }
}