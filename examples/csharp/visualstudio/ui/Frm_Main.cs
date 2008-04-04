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
    /// Main form
    /// </summary>
    public partial class Frm_Main : Form {

        private Agent _agent = new Agent();

        /// <summary>
        /// Main form
        /// </summary>
        public Frm_Main() {
            InitializeComponent();
            setCaptions();
            checkStatus();
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { _agent = value; }
        }


        private void checkStatus() {
            int status = agent.status;
            if (status > Constants.C_RP_STATUS_INIT) {
                setAccessToInput(true);
            }
            else {
                setAccessToInput(false);
            }
        }



        private void setCaptions() {
            // menu items
            mi_file.Text = ResourceBundle.RB_FRM_MAIN_MI_FILE;
            mi_newchart.Text = ResourceBundle.RB_FRM_MAIN_MI_NEWCHART;
            mi_exit.Text = ResourceBundle.RB_FRM_MAIN_MI_EXIT;
            mi_results.Text = ResourceBundle.RB_FRM_MAIN_MI_RESULTS;
            mi_wheel.Text = ResourceBundle.RB_FRM_MAIN_MI_WHEEL;
            mi_positions.Text = ResourceBundle.RB_FRM_MAIN_MI_POSTIONS;
            mi_analysis.Text = ResourceBundle.RB_FRM_MAIN_MI_ANALYSIS;
            mi_aspects.Text = ResourceBundle.RB_FRM_MAIN_MI_ASPECTS;
            mi_midpoints.Text = ResourceBundle.RB_FRM_MAIN_MI_MIDPOINTS;
            mi_preferences.Text = ResourceBundle.RB_FRM_MAIN_MI_PREFERENCES;
            mi_configuration.Text = ResourceBundle.RB_FRM_MAIN_MI_CONFIGURATIION;
            mi_settings.Text = ResourceBundle.RB_FRM_MAIN_MI_SETTINGS;
            mi_help.Text = ResourceBundle.RB_FRM_MAIN_MI_HELP;
            mi_contents.Text = ResourceBundle.RB_FRM_MAIN_MI_CONTENTS;
            mi_index.Text = ResourceBundle.RB_FRM_MAIN_MI_INDEX;
            mi_about.Text = ResourceBundle.RB_FRM_MAIN_MI_ABOUT;

            // form items
            Text = ResourceBundle.RB_FRM_MAIN_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_MAIN_LBL_TITLE;
            lbl_intro1.Text = ResourceBundle.RB_FRM_MAIN_LBL_INTRO1;
            lbl_intro2.Text = ResourceBundle.RB_FRM_MAIN_LBL_INTRO2;
            gb_start.Text = ResourceBundle.RB_FRM_MAIN_GB_START;
            gb_options.Text = ResourceBundle.RB_FRM_MAIN_GB_OPTIONS;
            btn_new.Text = ResourceBundle.RB_FRM_MAIN_BTN_NEW;
            btn_chart.Text = ResourceBundle.RB_FRM_MAIN_BTN_CHART;
            btn_positions.Text = ResourceBundle.RB_FRM_MAIN_BTN_POSITIONS;
            btn_help.Text = ResourceBundle.RB_FRM_MAIN_BTN_HELP;
            btn_exit.Text = ResourceBundle.RB_FRM_MAIN_BTN_EXIT;
            lbl_new.Text = ResourceBundle.RB_FRM_MAIN_LBL_NEW;
            lbl_chart.Text = ResourceBundle.RB_FRM_MAIN_LBL_CHART;
            lbl_positions.Text = ResourceBundle.RB_FRM_MAIN_LBL_POSITIONS;
            lbl_help.Text = ResourceBundle.RB_FRM_MAIN_LBL_HELP;

        }

        private void setAccessToInput(Boolean chartAvail) {
            btn_chart.Enabled = chartAvail;
            btn_positions.Enabled = chartAvail;
            mi_analysis.Enabled = chartAvail;
            mi_results.Enabled = chartAvail;

        }


        private void btn_new_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_NEWCASE, this));
        }

        private void btn_open_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_LOADCASE, this));
        }

        private void newToolStripMenuItem_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_NEWCASE, this));
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_LOADCASE, this));
        }


        private void configurationToolStripMenuItem_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWCONFIG, this));
        }

        private void mi_contents_Click(object sender, EventArgs e) {
            Help.ShowHelp(this, "radixpro.chm");
        }

        private void mi_settings_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWSETTINGS, this));

        }

        private void btn_help_Click(object sender, EventArgs e) {
            Help.ShowHelp(this, "radixpro.chm");
        }

        private void btn_progressions_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_CALCEVENT, this));
        }

        private void btn_positions_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWPOSITIONS, this));
        }

        private void Frm_Main_Enter(object sender, EventArgs e) {
            checkStatus();
        }


        private void Frm_Main_MouseEnter(object sender, EventArgs e) {
            checkStatus();
        }

        private void mi_positions_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWPOSITIONS, this));
        }

        private void btn_chart_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWCHART, this));
        }

        private void mi_wheel_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWCHART, this));
        }

        private void mi_about_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWABOUT, this));
        }

        private void btn_exit_Click(object sender, EventArgs e) {
           Application.Exit();
        }

        private void mi_exit_Click(object sender, EventArgs e) {
           Application.Exit();
        }

        private void mi_aspects_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWASPECTS, this)); 
        }

        private void mi_midpoints_Click(object sender, EventArgs e) {
           agent.sendMessage(new RpMessage(Messages.MSG_SHOWMIDPOINTS, this));
        }

        private void mi_index_Click(object sender, EventArgs e) {
            Help.ShowHelpIndex(this, "radixpro.chm");
        }
    }
}