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
    /// Form to show list of midpoints
    /// </summary>
   public partial class Frm_AnalysisMidpoints : Form {

      private Agent _agent;
      private Font listBodyGlyphs;

      /// <summary>
      /// Agent for this form
      /// </summary>
      public Agent agent {
         get { return _agent; }
         set { _agent = value; }
      }


        /// <summary>
        /// Initializes form
        /// </summary>
      public Frm_AnalysisMidpoints() {
         InitializeComponent();
         this.BackColor = Color.White;
         listBodyGlyphs = new Font("RadixProBodies", 14);
         setCaptions();
      }


      private void setCaptions() {
         Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_FORMTITLE;
         lbl_title.Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_LBL_TITLE;
         btn_cancel.Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_BTN_CANCEL;
         btn_chart.Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_BTN_CHART;
         btn_help.Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_BTN_HELP;
      }

      /// <summary>
      /// Show data in form
      /// </summary>
      public void showData() {
         Radix radix = agent.rpCase.radix;
         lbl_name.Text = radix.name;
         lbl_orbis.Text = ResourceBundle.RB_FRM_ANALYSISMIDPOINTS_LBL_ORBIS + Conversions.deg2sexagesimal(agent.config.midpointOrb,false,false);

         ArrayList midpoints = radix.getRadixAnalysis().midpointsAnalysis.midpoints;

         dgvMidpoints.CellBorderStyle = DataGridViewCellBorderStyle.None;
         dgvMidpoints.RowsDefaultCellStyle.BackColor = Color.AliceBlue;
         dgvMidpoints.RowsDefaultCellStyle.ForeColor = Color.Black;
         dgvMidpoints.AlternatingRowsDefaultCellStyle.BackColor = Color.NavajoWhite;
         dgvMidpoints.AlternatingRowsDefaultCellStyle.ForeColor = Color.Black;
         dgvMidpoints.ClearSelection();

         DataGridViewTextBoxColumn col1 = new DataGridViewTextBoxColumn();    // first body
         DataGridViewTextBoxColumn col2 = new DataGridViewTextBoxColumn();    // just shows a slash
         DataGridViewTextBoxColumn col3 = new DataGridViewTextBoxColumn();    // second body
         DataGridViewTextBoxColumn col4 = new DataGridViewTextBoxColumn();    // just shows an equal sign
         DataGridViewTextBoxColumn col5 = new DataGridViewTextBoxColumn();    // body on midpoint
         DataGridViewTextBoxColumn col6 = new DataGridViewTextBoxColumn();    // act orbis
         DataGridViewTextBoxColumn col7 = new DataGridViewTextBoxColumn();    // max. orbis
         DataGridViewTextBoxColumn col8 = new DataGridViewTextBoxColumn();    // perc. of orbis

         col6.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_ACTORBIS;
         col7.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_MAXORBIS;
         col8.HeaderText = ResourceBundle.RB_FRM_ANALYSISASPECTS_TXT_PRCORBIS;

         col1.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
         col2.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
         col3.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
         col4.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
         col5.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter;
         col6.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
         col7.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
         col8.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;

         col1.DefaultCellStyle.Font = listBodyGlyphs;
         col1.Width = 24;
         col2.Width = 16;
         col3.DefaultCellStyle.Font = listBodyGlyphs;
         col3.Width = 24;
         col4.Width = 16;
         col5.DefaultCellStyle.Font = listBodyGlyphs;
         col5.Width = 24;

         col1.SortMode = DataGridViewColumnSortMode.NotSortable;
         col2.SortMode = DataGridViewColumnSortMode.NotSortable;
         col3.SortMode = DataGridViewColumnSortMode.NotSortable;
         col4.SortMode = DataGridViewColumnSortMode.NotSortable;
         col7.SortMode = DataGridViewColumnSortMode.NotSortable;
         col8.SortMode = DataGridViewColumnSortMode.NotSortable;
         
         dgvMidpoints.Columns.AddRange(
            new DataGridViewTextBoxColumn[] { col1, col2, col3, col4, col5, col6, col7, col8 });

         int nrOfMidpoints= midpoints.Count;
         int nrOfRows = Math.Max(nrOfMidpoints, 20);

         DataGridViewRow[] dgvr = new DataGridViewRow[nrOfMidpoints];
         Midpoint m;

         for (int i = 0; i < nrOfMidpoints; i++) {
            m = (Midpoint)midpoints[i];
            dgvr[i] = new DataGridViewRow();
            dgvr[i].CreateCells(dgvMidpoints);
            dgvr[i].Cells[0].Value = Conversions.bodyGlyphFromIndex(m.pos1);
            dgvr[i].Cells[1].Value = '/';
            dgvr[i].Cells[2].Value = Conversions.bodyGlyphFromIndex(m.pos2);
            dgvr[i].Cells[3].Value = '=';
            dgvr[i].Cells[4].Value = Conversions.bodyGlyphFromIndex(m.pos3);
            dgvr[i].Cells[5].Value = Conversions.deg2sexagesimal(m.actOrbis,true,false);
            dgvr[i].Cells[6].Value = Conversions.deg2sexagesimal(agent.config.midpointOrb, false, false);
            dgvr[i].Cells[7].Value = Conversions.fixedFraction((m.actOrbis / agent.config.midpointOrb) * 100, 2) + " % ";
         }
         dgvMidpoints.Rows.AddRange(dgvr);
      }

      private void btn_chart_Click(object sender, EventArgs e) {
          agent.sendMessage(new RpMessage(Messages.MSG_SHOWCHART, this));
      }

      private void btn_cancel_Click(object sender, EventArgs e) {
          this.Close();
      }

      private void btn_help_Click(object sender, EventArgs e) {
         Help.ShowHelp(this, "radixpro.chm", "hlp_analysismidpints.html");
      }

   }
}
