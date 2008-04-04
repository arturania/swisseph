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
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using radixpro;
using radixpro.controller;
using radixpro.engine;

namespace radixpro.ui {
    /// <summary>
    /// Form for list of positions
    /// </summary>
    public partial class Frm_Showpositions : Form {

        private Agent _agent;

        // Texts for headers in panel
        private String txtLongitude;
        private String txtLatitude;
        private String txtRightAscension;
        private String txtDeclination;
        private String txtSpeed;
        private String txtDistance;

        private Font listBodyGlyphs;
        private Font listSignGlyphs;

        Frm_TextPopup ftp = new Frm_TextPopup();


        /// <summary>
        /// Form for list with positions
        /// </summary>
        public Frm_Showpositions() {
            InitializeComponent();
            setCaptions();
            this.BackColor = Color.White;
            listBodyGlyphs = new Font("RadixProBodies", 14);
            listSignGlyphs = new Font("RadixProSigns", 14);
        }

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { _agent = value; }
        }

        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_LBL_TITLE;
            lbl_intro.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_LBL_INTRO;
            txtLongitude = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_LONGITUDE;
            txtLatitude = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_LATITUDE;
            txtRightAscension = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_RIGHTASCENSION;
            txtDeclination = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_DECLINATION;
            txtSpeed = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_SPEED;
            txtDistance = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_DISTANCE;
            btn_remarks.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_BTN_REMARKS;
            btn_source.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_BTN_SOURCE;
            btn_help.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_BTN_HELP;
            btn_chart.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_BTN_CHART;
            btn_cancel.Text = ResourceBundle.RB_FRM_SHOWPOSITIONS_BTN_CANCEL;
        }

        public void showData() {
            Radix radix = agent.rpCase.radix;

            lbl_datetime.Text = radix.datetime;
            lbl_location.Text = radix.location;
            lbl_coordinates.Text = radix.coordinatesText;

            PositionSet positionSet = radix.positionSet;
            ArrayList planetaryPositions = positionSet.planetaryPositions;

            dgvPositions.CellBorderStyle = DataGridViewCellBorderStyle.None;
            dgvPositions.RowsDefaultCellStyle.BackColor = Color.AliceBlue;
            dgvPositions.AlternatingRowsDefaultCellStyle.BackColor = Color.NavajoWhite;

            DataGridViewTextBoxColumn col1 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col2 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col3 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col4 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col5 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col6 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col7 = new DataGridViewTextBoxColumn();
            DataGridViewTextBoxColumn col8 = new DataGridViewTextBoxColumn();

            col2.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_LONGITUDE;
            col4.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_LATITUDE;
            col5.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_RIGHTASCENSION;
            col6.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_DECLINATION;
            col7.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_SPEED;
            col8.HeaderText = ResourceBundle.RB_FRM_SHOWPOSITIONS_TXT_DISTANCE;

            col1.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
            col2.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            col3.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleLeft;
            col4.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            col5.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            col6.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            col7.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            col8.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
           

            col1.DefaultCellStyle.Font = listBodyGlyphs;
            col1.Width = 24;
            col3.DefaultCellStyle.Font = listSignGlyphs;
            col3.Width = 24;

            col2.SortMode = DataGridViewColumnSortMode.NotSortable;
            col3.SortMode = DataGridViewColumnSortMode.NotSortable;
            col4.SortMode = DataGridViewColumnSortMode.NotSortable;
            col5.SortMode = DataGridViewColumnSortMode.NotSortable;
            col6.SortMode = DataGridViewColumnSortMode.NotSortable;
            col7.SortMode = DataGridViewColumnSortMode.NotSortable;
            col8.SortMode = DataGridViewColumnSortMode.NotSortable;


            dgvPositions.Columns.AddRange(
               new DataGridViewTextBoxColumn[] { col1, col2, col3, col4, col5, col6, col7, col8 });

            int nrOfBodies = radix.positionSet.planetaryPositions.Count;
            int nrOfRows = Math.Max(nrOfBodies, 20);

            DataGridViewRow[] dgvr = new DataGridViewRow[nrOfBodies];

            ArrayList pos = radix.positionSet.planetaryPositions;
            PlanetaryPosition pp;

            for (int i = 0; i < nrOfBodies; i++) {
                pp = (PlanetaryPosition)pos[i];
                dgvr[i] = new DataGridViewRow();
                dgvr[i].CreateCells(dgvPositions);
                dgvr[i].Cells[0].Value = Conversions.bodyGlyphFromIndex(pp.planetId);
                dgvr[i].Cells[1].Value = Conversions.deg2sexagesimal(pp.longitude,true,true);
                dgvr[i].Cells[2].Value = Conversions.signGlyphFromLong(pp.longitude);
                dgvr[i].Cells[3].Value = Conversions.deg2sexagesimalInRange(pp.latitude,-180,180,true);
                dgvr[i].Cells[4].Value = Conversions.deg2sexagesimal(pp.rightAscension, true, false);
                dgvr[i].Cells[5].Value = Conversions.deg2sexagesimalInRange(pp.declination, -90.0, 90.0, true);
                dgvr[i].Cells[6].Value = Conversions.deg2sexagesimalInRange(pp.speedLongitude,-90.0, 90.0, true);
                dgvr[i].Cells[7].Value = Conversions.fixedFraction(pp.distance,6);

            }

            dgvPositions.Rows.AddRange(dgvr);
            lbl_name.Text = radix.name;

        }

        private void btn_remarks_Click(object sender, EventArgs e) {
            ftp.setHeader(ResourceBundle.RB_FRM_TEXTPOPUP_HEADERREMARKS);
            ftp.setContent(agent.rpCase.radix.remarksText);
            ftp.Visible = true;
        }

        private void btn_source_Click(object sender, EventArgs e) {
            ftp.setHeader(ResourceBundle.RB_FRM_TEXTPOPUP_HEADERSOURCE);
            ftp.setContent(agent.rpCase.radix.sourceText);
            ftp.Visible = true;
        }

        private void btn_cancel_Click(object sender, EventArgs e) {
            this.Visible = false;
        }

        private void btn_chart_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWCHART, this));

        }

        private void btn_help_Click(object sender, EventArgs e) {
           Help.ShowHelp(this, "radixpro.chm", "hlp_showpositions.html");
        }

 }
}