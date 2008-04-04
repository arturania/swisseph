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
using radixpro;
using radixpro.controller;
using radixpro.data;


namespace radixpro.ui {
    /// <summary>
    /// Form for data input
    /// </summary>
    public partial class Frm_Dataradix : Form {

        private Agent _agent;
        private VOInputDataRadix _voInputDataRadix;
        private SourceTypeList sourceTypeList;
        private ChartTypeList chartTypeList;

        /// <summary>
        /// Constructor for frm_dataradix
        /// </summary>
        public Frm_Dataradix() {
            InitializeComponent();
            sourceTypeList = new SourceTypeList();
            chartTypeList = new ChartTypeList();
            voInputDataRadix = new VOInputDataRadix();
            setCaptions();
            setStatus();
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { 
                _agent = value;
                setSettings();
            }
        }

        /// <summary>
        /// Value object with all data for input radix
        /// </summary>
        public VOInputDataRadix voInputDataRadix {
            get { return _voInputDataRadix; }
            set { _voInputDataRadix = value; }
        }


        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_DATARADIX_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_TITLE;
            lbl_intro.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_INTRO;
            lbl_name.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_NAME;
            lbl_location.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_LOCATION;
            lbl_longitude.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_LONGITUDE;
            lbl_latitude.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_LATITUDE;
            lbl_date.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_DATE;
            lbl_time.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_TIME;
            lbl_timezone.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_TIMEZONE;
            lbl_spectimezone.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_SPECTIMEZONE;
            lbl_charttype.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_CHARTTYPE;
            lbl_remarks.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_REMARKS;
            lbl_sourcetype.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_SOURCETYPE;
            lbl_sourcedescription.Text = ResourceBundle.RB_FRM_DATARADIX_LBL_SOURCEDESCRIPTION;
            gb_location.Text = ResourceBundle.RB_FRM_DATARADIX_GB_LOCATION;
            gb_datetime.Text = ResourceBundle.RB_FRM_DATARADIX_GB_DATETIME;
            gb_description.Text = ResourceBundle.RB_FRM_DATARADIX_GB_DESCRIPTION;
            ra_north.Text = ResourceBundle.RB_FRM_DATARADIX_RA_NORTH;
            ra_south.Text = ResourceBundle.RB_FRM_DATARADIX_RA_SOUTH;
            ra_east.Text = ResourceBundle.RB_FRM_DATARADIX_RA_EAST;
            ra_west.Text = ResourceBundle.RB_FRM_DATARADIX_RA_WEST;
            ra_tzeast.Text = ResourceBundle.RB_FRM_DATARADIX_RA_TZEAST;
            ra_tzwest.Text = ResourceBundle.RB_FRM_DATARADIX_RA_TZWEST;
            ra_greg.Text = ResourceBundle.RB_FRM_DATARADIX_RA_GREG;
            ra_jul.Text = ResourceBundle.RB_FRM_DATARADIX_RA_JUL;
            cb_dst.Text = ResourceBundle.RB_FRM_DATARADIX_CB_DST;
            cb_spectimezone.Text = ResourceBundle.RB_FRM_DATARADIX_CB_SPECTIMEZONE;
            btn_ok.Text = ResourceBundle.RB_FRM_DATARADIX_BTN_OK;
            btn_cancel.Text = ResourceBundle.RB_FRM_DATARADIX_BTN_CANCEL;
            btn_help.Text = ResourceBundle.RB_FRM_DATARADIX_BTN_HELP;

            TimeZoneList timeZoneList = new TimeZoneList();
            this.co_timezone.Items.Clear();
            for (int i = 0; i < Constants.C_RP_TOTAL_TIMEZONES; i++) {
                this.co_timezone.Items.Add(((RpTimeZone)timeZoneList.theList[i]).description);
            }

            this.co_charttype.Items.Clear();
            for (int i = 0; i < Constants.C_RP_TOTAL_CHARTTYPES; i++) {
                this.co_charttype.Items.Add(((ChartType)chartTypeList.theList[i]).description);
            }

            this.co_sourcetype.Items.Clear();
            for (int i = 0; i < Constants.C_RP_TOTAL_SOURCETYPES; i++) {
                this.co_sourcetype.Items.Add(((SourceType)sourceTypeList.theList[i]).description);
            }
        }


        private void setStatus() {
            ra_east.Checked = true;
            ra_north.Checked = true;
            ra_tzeast.Checked = true;
            ra_greg.Checked = true;
            cb_spectimezone.Checked = false;
            tb_spectimezone.Enabled = false;
        }

        private void setSettings() {
           if (agent.settings.location.Length > 1) {
              tb_location.Text = agent.settings.location;
              tb_location_check();
           }
           if (agent.settings.latitude.Length > 1) {
              tb_latitude.Text = agent.settings.latitude;
              tb_latitude_check();
           }
           if (agent.settings.longitude.Length > 1) {
              tb_longitude.Text = agent.settings.longitude;
              tb_longitude_check();
           }
           
           if (agent.settings.geoLatDirection == "+") ra_north.Checked = true;
           if (agent.settings.geoLatDirection == "-") ra_south.Checked = true;
           if (agent.settings.geoLongDirection == "+") ra_east.Checked = true;
           if (agent.settings.geoLongDirection == "-") ra_west.Checked = true;

           if (agent.settings.timezone > 0) {
               co_timezone.SelectedIndex = agent.settings.timezone;
               co_timezone_check();
           }
        }


        private bool checkInput() {
            bool ok = true;
            // use values for fields that do not need any check
            // direction for longitude
            if (ra_east.Checked) voInputDataRadix.geoLongDirection = new VOItem("+", true);
            else voInputDataRadix.geoLongDirection = new VOItem("-", true);
            // direction for latitude
            if (ra_north.Checked) voInputDataRadix.geoLatDirection = new VOItem("+", true);
            else voInputDataRadix.geoLatDirection = new VOItem("-", true);
            // calendar
            if (ra_jul.Checked) voInputDataRadix.calendar = new VOItem(Convert.ToString(Constants.SE_JUL_CAL), true);
            else voInputDataRadix.calendar = new VOItem(Convert.ToString(Constants.SE_GREG_CAL), true);
            // dst
            // TODO ========>>>> change to a textbox that will expect a time-string
            if (cb_dst.Checked) voInputDataRadix.dst = new VOItem("1", true);
            else voInputDataRadix.dst = new VOItem("0", true);

            // handle different possibilities for timezone
            if (cb_spectimezone.Checked) {
                // user defined timezone
                if (!voInputDataRadix.specTimezone.filled) {
                    this.tb_spectimezone.BackColor = Color.Yellow;
                    ok = false;
                } else {
                    if (ra_tzeast.Checked) voInputDataRadix.specTimezoneDir.data = "+";
                    else voInputDataRadix.specTimezoneDir.data = "-";
                }
            }
            else {    // standard timezone
                if (!voInputDataRadix.timezone.filled) {
                    this.co_timezone.BackColor = Color.Yellow;
                    ok = false;
                }
            }

            // chartType (optional)
            if (co_charttype.SelectedIndex >= 0) {
                voInputDataRadix.chartType = new VOItem(((ChartType)chartTypeList.theList[co_charttype.SelectedIndex]).description, true);
            }

            // remarks (optional)
            if (tb_remarks.Text.Length > 0) voInputDataRadix.remarks = new VOItem(tb_remarks.Text, true);
            else voInputDataRadix.remarks = new VOItem("", false);

            // sourcedescription (optional)
            if (tb_sourcedescription.Text.Length > 0) voInputDataRadix.sourceDescription = new VOItem(tb_sourcedescription.Text, true);
            else voInputDataRadix.sourceDescription = new VOItem("", false);

            // sourcetype (optional)
            if (co_sourcetype.SelectedIndex >= 0) {
                voInputDataRadix.sourceType = new VOItem(((SourceType)sourceTypeList.theList[co_sourcetype.SelectedIndex]).description, true);
            }
                

            // checked items

            // check name
            if (!voInputDataRadix.name.filled) {
                this.tb_name.BackColor = Color.Yellow;
                ok = false;
            }
            // check location
            if (!voInputDataRadix.location.filled) {
                this.tb_location.BackColor = Color.Yellow;
                ok = false;
            }
            // check longitude
            if (!voInputDataRadix.geoLongitude.filled) {
                this.tb_longitude.BackColor = Color.Yellow;
                ok = false;
            }
            // check latitude
            if (!voInputDataRadix.geoLatitude.filled) {
                this.tb_latitude.BackColor = Color.Yellow;
                ok = false;
            }
            // check date
            if (!voInputDataRadix.date.filled) {
                this.tb_date.BackColor = Color.Yellow;
                ok = false;
            }
            // check time
            if (!voInputDataRadix.time.filled) {
                this.tb_time.BackColor = Color.Yellow;
                ok = false;
            }

            return ok;
        }


        private void tb_name_check() {
           ValidationItem vi = InputChecker.checkName(tb_name.Text);
           if (vi.noErrors) {
              tb_name.Text = vi.resultTxt;
              tb_name.BackColor = Color.White;
              voInputDataRadix.name = new VOItem(vi.resultTxt, true);
           }
           else {
              tb_name.BackColor = Color.Yellow;
              this.ActiveControl = tb_name;
              voInputDataRadix.name = new VOItem("", false);
           }
        }


        private void tb_location_check() {
           ValidationItem vi = InputChecker.checkLocation(tb_location.Text);
           if (vi.noErrors) {
              tb_location.Text = vi.resultTxt;
              tb_location.BackColor = Color.White;
              voInputDataRadix.location = new VOItem(vi.resultTxt, true);
           }
           else {
              tb_location.BackColor = Color.Yellow;
              this.ActiveControl = tb_location;
              voInputDataRadix.location = new VOItem("", false);
           }
        }

        private void tb_longitude_check() {
           ValidationItem vi = InputChecker.checkGeoLongitude(tb_longitude.Text);
           if (vi.noErrors) {
              tb_longitude.Text = vi.resultTxt;
              tb_longitude.BackColor = Color.White;
              voInputDataRadix.geoLongitude = new VOItem(vi.resultTxt, true);
           }
           else {
              tb_longitude.BackColor = Color.Yellow;
              this.ActiveControl = tb_longitude;
              voInputDataRadix.geoLongitude = new VOItem("", false);
           }
        }

        private void tb_latitude_check() {
           ValidationItem vi = InputChecker.checkGeoLatitude(tb_latitude.Text);
           if (vi.noErrors) {
              tb_latitude.Text = vi.resultTxt;
              tb_latitude.BackColor = Color.White;
              voInputDataRadix.geoLatitude = new VOItem(vi.resultTxt, true);
           }
           else {
              tb_latitude.BackColor = Color.Yellow;
              this.ActiveControl = tb_latitude;
              voInputDataRadix.geoLatitude = new VOItem("", false);
           }
        }

        private void co_timezone_check() {
            if (!cb_spectimezone.Checked) {
                ValidationItem vi = InputChecker.checkTimezone(co_timezone.SelectedIndex);
                if (vi.noErrors) {
                    co_timezone.BackColor = Color.White;
                    voInputDataRadix.timezone = new VOItem(vi.resultTxt, true);
                } else {
                    co_timezone.BackColor = Color.Yellow;
                    this.ActiveControl = co_timezone;
                    voInputDataRadix.timezone = new VOItem("", false);
                }
            }
        }



        private void btn_ok_Click(object sender, EventArgs e) {
            if (checkInput())
                agent.sendMessage(new RpMessage(Messages.MSG_NEWCASE_COMPLETED, this));
        }

        private void tb_name_Leave(object sender, EventArgs e) {
           tb_name_check();
        }

        private void tb_location_leave(object sender, EventArgs e) {
           tb_location_check();
        }

        private void tb_longitude_Leave(object sender, EventArgs e) {
           tb_longitude_check();
        }

        private void tb_latitude_Leave(object sender, EventArgs e) {
           tb_latitude_check();
        }

        private void tb_date_Leave(object sender, EventArgs e) {
            int cal = Constants.SE_GREG_CAL;
            if (ra_jul.Checked) cal = Constants.SE_JUL_CAL;
            ValidationItem vi = InputChecker.checkDate(tb_date.Text, cal);
            tb_date.Text = vi.resultTxt;
            if (vi.noErrors) {
                tb_date.BackColor = Color.White;
                voInputDataRadix.date = new VOItem(vi.resultTxt, true);
            }
            else {
                tb_date.BackColor = Color.Yellow;
                this.ActiveControl = tb_date;
                voInputDataRadix.date = new VOItem(vi.resultTxt, false);
            }
        }


        private void tb_time_Leave(object sender, EventArgs e) {
            ValidationItem vi = InputChecker.checkTime(tb_time.Text);
            if (vi.noErrors) {
                tb_time.Text = vi.resultTxt;
                tb_time.BackColor = Color.White;
                voInputDataRadix.time = new VOItem(vi.resultTxt, true);
            }
            else {
                tb_time.BackColor = Color.Yellow;
                this.ActiveControl = tb_time;
                voInputDataRadix.time = new VOItem("", false);
            }
        }

        // leave method used for timezone
        private void co_timezone_Leave(object sender, EventArgs e) {
            co_timezone_check();
        }


       // leave method used for spectimezone
        private void tb_spectimezone_Leave(object sender, EventArgs e) {
            if (cb_spectimezone.Checked) {
                ValidationItem vi = InputChecker.checkSpecTimezone(tb_spectimezone.Text);
                if (vi.noErrors) {
                    tb_spectimezone.BackColor = Color.White;
                    voInputDataRadix.specTimezone = new VOItem(vi.resultTxt, true);
                } else {
                    tb_spectimezone.BackColor = Color.Yellow;
                    this.ActiveControl = tb_spectimezone;
                    voInputDataRadix.specTimezone = new VOItem("", false);
                }
            }
        }


        private void cb_spectimezone_CheckedChanged(object sender, EventArgs e) {
           if (cb_spectimezone.Checked) {      // self defined
               co_timezone.Enabled = false;
               tb_spectimezone.Enabled = true;
               ra_tzeast.Enabled = true;
               ra_tzwest.Enabled = true;
               this.ActiveControl = tb_spectimezone; 
           } else {
               tb_spectimezone.Enabled = false;
               co_timezone.Enabled = true;
               ra_tzeast.Enabled = false;
               ra_tzwest.Enabled = false;
               this.ActiveControl = co_timezone;            
           }
           tb_spectimezone.BackColor = Color.White;
           co_timezone.BackColor = Color.White;
           tb_spectimezone.Text = "";
           co_timezone.Text = "";

        }

        private void btn_cancel_Click(object sender, EventArgs e) {
           this.Visible = false;
        }

        private void btn_help_Click(object sender, EventArgs e) {
            Help.ShowHelp(this, "radixpro.chm", "hlp_dataradix.html");
        }


    }
}