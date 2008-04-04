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
    /// Form for configuration
    /// </summary>
    public partial class Frm_Configuration : Form {

        private Agent _agent;
        private RpHouseSystem houseSystem;

        /// <summary>
        /// Form for maintaining configuration
        /// </summary>
        public Frm_Configuration() {
            InitializeComponent();
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { _agent = value;
               setCaptions();
               setValues();
            }
        }


        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_CONFIGURATION_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_TITLE;
            lbl_intro.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_INTRO;
            lbl_orbis.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_ORBIS;
            lbl_major.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_MAJOR;
            lbl_minor.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_MINOR;
            lbl_midpoints.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_MIDPOINTS;
            lbl_progressions.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_PROGRESSIONS;
            lbl_additional.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_ADDITIONAL;
            lbl_houses.Text = ResourceBundle.RB_FRM_CONFIGURATION_LBL_HOUSES;
            gb_orbis.Text = ResourceBundle.RB_FRM_CONFIGURATION_GB_ORBIS;
            gb_additional.Text = ResourceBundle.RB_FRM_CONFIGURATION_GB_ADDITIONAL;
            gb_houses.Text = ResourceBundle.RB_FRM_CONFIGURATION_GB_HOUSES;
            cb_lunarnode.Text = ResourceBundle.RB_FRM_CONFIGURATION_CB_LUNARNODE;
            cb_chiron.Text = ResourceBundle.RB_FRM_CONFIGURATION_CB_CHIRON;
            ra_mean.Text = ResourceBundle.RB_FRM_CONFIGURATION_RA_MEAN;
            ra_oscillating.Text = ResourceBundle.RB_FRM_CONFIGURATION_RA_OSCILLATING;
            btn_cancel.Text = ResourceBundle.RB_FRM_CONFIGURATION_BTN_CANCEL;
            btn_defaults.Text = ResourceBundle.RB_FRM_CONFIGURATION_BTN_DEFAULTS;
            btn_save.Text = ResourceBundle.RB_FRM_CONFIGURATION_BTN_SAVE;
            btn_help.Text = ResourceBundle.RB_FRM_CONFIGURATION_BTN_HELP;

            HouseSystemList houseSystemList = new HouseSystemList();
            for (int i = 0; i < Constants.C_RP_TOTAL_HOUSESYSTEMS; i++) {
               lb_houses.Items.Add(((RpHouseSystem)houseSystemList.theList[i]).description);   
            }
        }

        private void setValues() {
           co_major.Value = agent.config.majorOrb;
           co_minor.Value = agent.config.minorOrb;
           co_midpoints.Value = agent.config.midpointOrb;
           co_progressions.Value = agent.config.progOrb;
           if (agent.config.chiron) cb_chiron.Checked = true;
           if (agent.config.lunarNode) cb_lunarnode.Checked = true;
           if (agent.config.nodeType == Constants.SE_TRUE_NODE) ra_oscillating.Checked = true;
           else ra_mean.Checked = true;
           houseSystem = new HouseSystemList().houseSystem(agent.config.houseSystem);
           lb_houses.SelectedIndex = houseSystem.index;

        }


        private void btn_cancel_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_CANCEL, this));
        }

        private void btn_save_Click(object sender, EventArgs e) {
           agent.config.majorOrb = Convert.ToInt32(co_major.Value);
           agent.config.minorOrb = Convert.ToInt32(co_minor.Value);
           agent.config.midpointOrb = Convert.ToInt32(co_midpoints.Value);
           agent.config.progOrb = Convert.ToInt32(co_progressions.Value);
           agent.config.chiron = cb_chiron.Checked;
           agent.config.lunarNode = cb_lunarnode.Checked;
           if (ra_mean.Checked) agent.config.nodeType = Constants.SE_MEAN_NODE;
           else agent.config.nodeType = Constants.SE_TRUE_NODE;
           agent.config.houseSystem = new HouseSystemList().theList[lb_houses.SelectedIndex].id;
           agent.config.saveConfig();

           this.Close();

        }
    }
}