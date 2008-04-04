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
using System.Collections;

namespace radixpro.ui {

    /// <summary>
    /// Form to show list of aspects
    /// </summary>
    public partial class Frm_AnalysisAspects : Form {
        
        private Agent _agent;
        private Font listBodyGlyphs;
        private Font listAspectGlyphs;

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { _agent = value; }
        }

        /// <summary>
        /// Initializes Form
        /// </summary>
        public Frm_AnalysisAspects() {
            InitializeComponent();
            this.BackColor = Color.White;
            listBodyGlyphs = new Font("RadixProBodies", 14);
            listAspectGlyphs = new Font("RadixProAspects", 14);
            setCaptions();
        }

        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_LBL_TITLE;
            btn_cancel.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_BTN_CANCEL;
            btn_chart.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_BTN_CHART;
            btn_help.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_BTN_HELP;


        }

        /// <summary>
        /// Fill data 
        /// </summary>
        public void showData() {
           Radix radix = agent.rpCase.radix;
           lbl_name.Text = radix.name;
           lbl_orbismajor.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_LBL_ORBISMAJOR + Conversions.deg2sexagesimal(agent.config.majorOrb,false,false);
           lbl_orbisminor.Text = ResourceBundle.RB_FRM_ANALYSISASPECTS_LBL_ORBISMINOR + Conversions.deg2sexagesimal(agent.config.minorOrb,false,false);

           ArrayList aspects = radix.getRadixAnalysis().aspectsAnalysis.aspects;

           dgvAspects.CellBorderStyle = DataGridViewCellBorderStyle.None;
           dgvAspects.RowsDefaultCellStyle.BackColor = Color.AliceBlue;
           dgvAspects.RowsDefaultCellStyle.ForeColor = Color.Black;
           dgvAspects.AlternatingRowsDefaultCellStyle.BackColor = Color.NavajoWhite;
           dgvAspects.AlternatingRowsDefaultCellStyle.ForeColor = Color.Black;

           dgvAspects.ClearSelection();
           

           DataGridViewTextBoxColumn col1 = new DataGridViewTextBoxColumn();
           DataGridViewTextBoxColumn col2 = new DataGridViewTextBoxColumn();
           DataGridViewTextBoxColumn col3 = new DataGridViewTextBoxColumn();
           DataGridViewTextBoxColumn col4 = new DataGridViewTextBoxColumn();
           DataGridViewTextBoxColumn col5 = new DataGridViewTextBoxColumn();
           DataGridViewTextBoxColumn col6 = new DataGridViewTextBoxColumn();


           col4.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_ACTORBIS;
           col5.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_MAXORBIS;
           col6.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_PRCORBIS;

           col1.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
           col2.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
           col3.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
           col4.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
           col5.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
           col6.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;


           col1.DefaultCellStyle.Font = listBodyGlyphs;
           col1.Width = 24;
           col2.DefaultCellStyle.Font = listAspectGlyphs;
           col2.Width = 24;
           col3.DefaultCellStyle.Font = listBodyGlyphs;
           col3.Width = 24;

           col1.SortMode = DataGridViewColumnSortMode.NotSortable;
           col3.SortMode = DataGridViewColumnSortMode.NotSortable;
           col5.SortMode = DataGridViewColumnSortMode.NotSortable;
           col6.SortMode = DataGridViewColumnSortMode.NotSortable;

           dgvAspects.Columns.AddRange(
              new DataGridViewTextBoxColumn[] { col1, col2, col3, col4, col5, col6 });

           int nrOfAspects = aspects.Count;
           int nrOfRows = Math.Max(nrOfAspects, 20);

           DataGridViewRow[] dgvr = new DataGridViewRow[nrOfAspects];

           Aspect a;
           for (int i = 0; i < nrOfAspects; i++) {
              a = (Aspect)aspects[i];
              dgvr[i] = new DataGridViewRow();
              dgvr[i].CreateCells(dgvAspects);
              dgvr[i].Cells[0].Value = Conversions.bodyGlyphFromIndex(a.pos1);
              dgvr[i].Cells[1].Value = Conversions.aspectGlyphFromIndex(a.aspectType.id);
              dgvr[i].Cells[2].Value = Conversions.bodyGlyphFromIndex(a.pos2);
              dgvr[i].Cells[3].Value = Conversions.deg2sexagesimal(a.actOrbis, true, false);
              dgvr[i].Cells[4].Value = Conversions.deg2sexagesimal(a.aspectType.orbis, false, false);
              dgvr[i].Cells[5].Value = Conversions.fixedFraction((a.actOrbis / a.aspectType.orbis) * 100, 2) + " % ";

           }
           dgvAspects.Rows.AddRange(dgvr);
     }


        private void btn_help_Click(object sender, EventArgs e) {
           Help.ShowHelp(this, "radixpro.chm", "hlp_analysisaspects.html");
        }

        private void btn_cancel_Click(object sender, EventArgs e) {
            this.Close();
        }

        private void btn_chart_Click(object sender, EventArgs e) {
            agent.sendMessage(new RpMessage(Messages.MSG_SHOWCHART, this));
        }
    }
}
