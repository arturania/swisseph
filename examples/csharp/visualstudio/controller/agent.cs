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
using System.Text;
using System.Windows.Forms;
using radixpro.engine;
using radixpro.ui;

namespace radixpro.controller {

    /// <summary>
    /// The agent takes care of handling all messages.
    /// </summary>
    public class Agent {

        // TODO In future versions more cases might be opened simultaneously
        // The maintenance of the STATUS should (partly ? ) be delegated to the case itself.
        private int _status = 0;
        //private ArrayList cases;
        private RpCase _rpCase;
        private Configuration _config;
        private Settings _settings;
        private ArrayList _voBodyPositions;
        private ArrayList _voHousePositions;

        Frm_Configuration frmConfig;
        Frm_Settings frmSettings;
        Frm_Dataradix frm_Dataradix;
        Frm_Dataprog frm_Dataprog;
        Frm_Loadcase frm_Loadcase;
        Frm_Showchart frm_Showchart;
        Frm_Showpositions frm_Showpositions;
        Frm_AnalysisAspects frm_AnalysisAspects;
        Frm_AnalysisMidpoints frm_AnalysisMidpoints;
        Frm_About frm_About;
        
        /// <summary>
        /// Constructor initializes list with cases
        /// </summary>
        public Agent() {
            config = new Configuration();
            settings = new Settings();
            //this.cases = new ArrayList();    // use for support of several cases
        }

        /// <summary>
        /// List with positions of bodies
        /// </summary>
        public ArrayList voBodyPositions {
            get { return _voBodyPositions; }
            set { _voBodyPositions = value; }
        }

        /// <summary>
        /// List with positions of houses
        /// </summary>
        public ArrayList voHousePositions {
            get { return _voHousePositions; }
            set { _voHousePositions = value; }
        }        
        
        
        /// <summary>
        /// Status of agent
        /// </summary>
        public int status {
            get { return _status; }
            set { _status = value; }
        }

        /// <summary>
        /// Active case 
        /// </summary>
        public RpCase rpCase { 
            get { return _rpCase; }
            set { }
        }

        /// <summary>
        /// Configuration
        /// </summary>
        public Configuration config {
           get { return _config; }
           set { _config = value; }
        }

       /// <summary>
       /// Settings
       /// </summary>
        public Settings settings {
           get { return _settings; }
           set { _settings = value; }
        }


        /// <summary>
        /// Handle the sending of a message
        /// </summary>
        /// <param name="msg">The message to handle</param>
        /// <returns>True if message was recognized, otherwise false</returns>
        public Boolean sendMessage(RpMessage msg) {
            Boolean success = true;
            int x = msg.id;

            switch (x) {
                case Messages.MSG_CANCEL:
                    closeAnyForm(msg.sender, msg.sender);
                    break;
                case Messages.MSG_NEWCASE:
                    openFrm_Dataradix(msg.sender);
                    break;
                case Messages.MSG_NEWCASE_COMPLETED:
                    this.status = Messages.MSG_NEWCASE_COMPLETED;
                    VOInputDataRadix voir = frm_Dataradix.voInputDataRadix;
                    InputDataChart idc = new InputDataChart(voir);
                    this._rpCase = new RpCase(config, idc);
                    frm_Dataradix.Close();
                    openFrmShowchart(msg.sender);
                    break;
                case Messages.MSG_LOADCASE:  // handle
                    // openFrm_Loadcase(msg.sender);
                    break;
                case Messages.MSG_SAVECASE: // handle;
                    break;
                case Messages.MSG_SHOWCONFIG:
                    openFrmConfiguration(msg.sender);
                    break;
                case Messages.MSG_SHOWSETTINGS:
                    openFrmSettings(msg.sender);
                    break;
                case Messages.MSG_SHOWABOUT:
                    openFrmAbout(msg.sender);
                    break;
                case Messages.MSG_SHOWHELP: // handle;   (obsolete ?)
                    break;
                case Messages.MSG_CALCCASE: // handle;   (obsolete ?)
                    break;
                case Messages.MSG_SHOWCHART:
                    openFrmShowchart(msg.sender);
                    break;
                case Messages.MSG_SHOWPOSITIONS:
                    openFrmShowpositions(msg.sender);
                    break;
                case Messages.MSG_SHOWANALYSIS: // handle;
                    break;
                case Messages.MSG_SHOWASPECTS: 
                    openFrmAnalysisAspects(msg.sender);
                    break;
                case Messages.MSG_SHOWMIDPOINTS: 
                    openFrmAnalysisMidpoints(msg.sender);
                    break;
                case Messages.MSG_NEWEVENT: // handle;
                    break;
                case Messages.MSG_CALCEVENT:
                    openFrmDataprog(msg.sender);
                    break;
                case Messages.MSG_SHOWPROGTRANSITS: // handle;
                    break;
                case Messages.MSG_SHOWPROGSECUNDARY: // handle;
                    break;
                default: success = false;
                    break;
            }
            return success;
        }


        private void closeAnyForm(object sender, Form form) {
            form.Close();
        }

        // TODO find a common approach for the openFrm procedures

        private void openFrm_Dataradix(object sender) {
            if (frm_Dataradix == null || frm_Dataradix.IsDisposed) {
                frm_Dataradix = new Frm_Dataradix();
                frm_Dataradix.agent = this;
            }
            frm_Dataradix.Visible = true;
            frm_Dataradix.BringToFront();
        }

        private void openFrm_Loadcase(object sender) {
            if (frm_Loadcase == null || frm_Loadcase.IsDisposed) {
                frm_Loadcase = new Frm_Loadcase();
                frm_Loadcase.agent = this;
            }
            frm_Loadcase.Visible = true;
            frm_Loadcase.BringToFront();
        }

 
        private void openFrmConfiguration(object sender) {
            if (frmConfig == null || frmConfig.IsDisposed) {
                frmConfig = new Frm_Configuration();
                frmConfig.agent = this;
            }
            frmConfig.Visible = true;
            frmConfig.BringToFront();
        }

        private void openFrmSettings(object sender) {
            if (frmSettings == null || frmSettings.IsDisposed) {
                frmSettings = new Frm_Settings();
                frmSettings.agent = this;
            }
            frmSettings.Visible = true;
            frmSettings.BringToFront();
        }


        private void openFrmDataprog(object sender) {
            if (frm_Dataprog == null || frm_Dataprog.IsDisposed) {
                frm_Dataprog = new Frm_Dataprog();
                frm_Dataprog.agent = this;
            }
            frm_Dataprog.Visible = true;
            frm_Dataprog.BringToFront();
        }


        private void openFrmShowpositions(object sender) {
            if (frm_Showpositions == null || frm_Showpositions.IsDisposed) {
                frm_Showpositions = new Frm_Showpositions();
                frm_Showpositions.agent = this;
                frm_Showpositions.showData();
            }
            frm_Showpositions.Visible = true;
            frm_Showpositions.BringToFront();
        }

        private void openFrmShowchart(object sender) {
            if (frm_Showchart == null || frm_Showchart.IsDisposed) {
                frm_Showchart = new Frm_Showchart();
                frm_Showchart.agent = this;
            }
            voBodyPositions = new ArrayList();
            for (int i = 0; i < rpCase.radix.positionSet.planetaryPositions.Count; i++) {
                PlanetaryPosition pp = (PlanetaryPosition)rpCase.radix.positionSet.planetaryPositions[i];
                VOLongBody volb = new VOLongBody(pp);
                voBodyPositions.Add(volb);
            }

            voHousePositions = new ArrayList();
            for (int i = 0; i < rpCase.radix.positionSet.housePositionSet.housePositions.Length; i++) {
                VOLongHouseCusp volh = new VOLongHouseCusp(i + 1, rpCase.radix.positionSet.housePositionSet.housePositions[i]);
                voHousePositions.Add(volh);
            }
            frm_Showchart.Visible = true;
            frm_Showchart.BringToFront();
        }

        private void openFrmAnalysisAspects(object sender) {
            if (frm_AnalysisAspects == null || frm_AnalysisAspects.IsDisposed) {
                frm_AnalysisAspects = new Frm_AnalysisAspects();
                frm_AnalysisAspects.agent = this;
                frm_AnalysisAspects.showData();
            }
            frm_AnalysisAspects.Visible = true;
            frm_AnalysisAspects.BringToFront();
        }

        private void openFrmAnalysisMidpoints(object sender) {
           if (frm_AnalysisMidpoints == null || frm_AnalysisMidpoints.IsDisposed) {
              frm_AnalysisMidpoints = new Frm_AnalysisMidpoints();
              frm_AnalysisMidpoints.agent = this;
              frm_AnalysisMidpoints.showData();
           }
           frm_AnalysisMidpoints.Visible = true;
           frm_AnalysisMidpoints.BringToFront();
        }


        private void openFrmAbout(object sender) {
            if (frm_About == null || frm_About.IsDisposed) {
                frm_About = new Frm_About();
            }
            frm_About.Visible = true;
            frm_About.BringToFront();
        }

    }


}