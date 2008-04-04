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
using System.Drawing.Drawing2D;
using System.Text;
using System.Windows.Forms;
using radixpro.controller;
using System.Collections;


namespace radixpro.ui {
    /// <summary>
    /// Toont de figuur
    /// </summary>
    public partial class Frm_Showchart : Form {

        double[] mainAxis; 
        ArrayList bodyPositions;
        ArrayList housePositions;
        private Agent _agent;
        Frm_TextPopup ftp = new Frm_TextPopup();

        /// <summary>
        /// Agent for this form
        /// </summary>
        public Agent agent {
            get { return _agent; }
            set { _agent = value; }
        }

        /// <summary>
        /// Form to show chart
        /// </summary>
        public Frm_Showchart() {
            InitializeComponent();
            SetStyle(ControlStyles.ResizeRedraw, true);
            this.BackColor = Color.White;
            setCaptions();
        }

        private ArrayList createBodyPositionList() {
            bodyPositions = new ArrayList();
            double munPosition;
            for (int i = 0; i < agent.voBodyPositions.Count; i++) {
               munPosition = Conversions.toRange( (mainAxis[0] - ((VOLongBody)agent.voBodyPositions[i]).fullLongitude),0,360);
               bodyPositions.Add(new BodyPosition(i, ((VOLongBody)agent.voBodyPositions[i]).fullLongitude, munPosition));
            }
            PositionComparer pc = new PositionComparer(SortDirection.Asc);
            bodyPositions.Sort(pc);
            return bodyPositions;
        }

        private ArrayList createHousePositionList() {
            ArrayList hp = new ArrayList();
            //houses = new double[23];
            for (int i = 0; i < agent.voHousePositions.Count; i++) {
                double pos = ((VOLongHouseCusp)agent.voHousePositions[i]).fullLongitude;
                hp.Add(pos);
            }
            mainAxis = new double[4];
            mainAxis[0] = (double)hp[13];
            mainAxis[1] = Conversions.toRange((double)hp[14] + 180, 0, 360);
            mainAxis[2] = Conversions.toRange((double)hp[13] + 180, 0, 360);
            mainAxis[3] = (double)hp[14];
            return hp;
        }



        private Point calcPoint(Point pBackground, Size sBackground, double corner, double hypothenusa) {
            double cosCorner = Math.Cos(Conversions.degToRad(corner));
            double sinCorner = Math.Sin(Conversions.degToRad(corner));
            int xAx = (Int32)Math.Floor(cosCorner * hypothenusa);
            int yAx = (Int32)Math.Floor(sinCorner * hypothenusa);
            return new Point(pBackground.X + sBackground.Width / 2 - xAx, pBackground.Y + sBackground.Height / 2 - yAx);
        }


        private void setCaptions() {
            Text = ResourceBundle.RB_FRM_SHOWCHART_FORMTITLE;
            lbl_title.Text = ResourceBundle.RB_FRM_SHOWCHART_LBL_TITLE;
            lbl_positions.Text = ResourceBundle.RB_FRM_SHOWCHART_LBL_POSITIONS;
            btn_remarks.Text = ResourceBundle.RB_FRM_SHOWCHART_BTN_REMARKS;
            btn_source.Text = ResourceBundle.RB_FRM_SHOWCHART_BTN_SOURCE;
            btn_help.Text = ResourceBundle.RB_FRM_SHOWCHART_BTN_HELP;
            btn_positions.Text = ResourceBundle.RB_FRM_SHOWCHART_BTN_POSITIONS;
            btn_cancel.Text = ResourceBundle.RB_FRM_SHOWCHART_BTN_CANCEL;
        }



        protected override void OnPaint(PaintEventArgs e) {
            // initialisation
            Graphics g = e.Graphics;
            SolidBrush brushBackground = new SolidBrush(Color.WhiteSmoke);
            SolidBrush brushZodiacCircle = new SolidBrush(Color.Cornsilk);
            SolidBrush brushHouseCircle = new SolidBrush(Color.WhiteSmoke);
            SolidBrush brushAspectCircle = new SolidBrush(Color.FloralWhite);
            SolidBrush brushSignGlyphs = new SolidBrush(Color.Crimson);
            SolidBrush brushBodyGlyphs = new SolidBrush(Color.DarkBlue);

            Pen penZodiacCircle = new Pen(Color.Black, 2);
            Pen penZodiacCusps = new Pen(Color.Red, 1);
            Pen penHouseCircle = new Pen(Color.Green, 1);
            Pen penHouseCusps = new Pen(Color.DarkGray, 1);
            Pen penAspectCircle = new Pen(Color.DarkBlue, 1);
            Pen penMainAxis = new Pen(Color.DarkGray, 3);
            Pen penConnectLines = new Pen(Color.LightBlue, 1);
            Pen penAspectsMajorActive = new Pen(Color.Red, 1);
            Pen penAspectsMajorPassive = new Pen(Color.Green, 1);
            Pen penAspectsMinorAspects = new Pen(Color.LightBlue, 1);
            Pen penActAspect;

            int offsetLeft = 350;    // reserve space for labels        
            Rectangle rClient = ClientRectangle;    // available size in pixels, size of full client panel
            Point p = rClient.Location;             // reserve space for textual presentation on left side of the screen
            p.X += offsetLeft;                      //    and define p as starting point for the remaining space   
            Size s = rClient.Size;                   // define size of client
            s.Width -= offsetLeft;                   // and substract reseved space for textual presentation 
            int squareDiameter = Math.Min(s.Width, s.Height);   // define diameter of square used to draw chart

            Font largeBodyGlyphs = new Font("RadixProBodies", (int)squareDiameter / 30);
            Font standardBodyGlyphs = new Font("RadixProBodies", (int)squareDiameter / 36);
            Font listBodyGlyphs = new Font("RadixProBodies", 14);
            Font largeSignGlyphs = new Font("RadixProSigns", (int)squareDiameter / 30);
            Font standardSignGlyphs = new Font("RadixProSigns", (int)squareDiameter / 36);
            Font listSignGlyphs = new Font("RadixProSigns", 14);
            Font noGlyphs = new Font("Courier New",10,FontStyle.Bold);


            Rectangle plotArea = new Rectangle(p, s);    // define area to draw
            // define set of margins, to be defined from available squaredimater. Each margin defines the difference
            // between two succeeding rectangels. To find the totlamargin between a square and the border of 
            int marginBackground = squareDiameter / 25;    // margin between outer border and Rectangle rectZodiacCircle;
            int marginZodiacCircle = squareDiameter / 12;    // margin between Rectangles rectZodiacCircle and rectHouseCircle
            int marginHouseCircle = squareDiameter / 6;     // margin between Rectangles rectHouseCircle and rectAspectCircle
            //     int marginAspectCircle = squareDiameter  - marginBackground - marginZodiacCircle - marginHouseCircle;
            int marginSigns = marginBackground + (marginZodiacCircle + marginHouseCircle) / 2;

            Size sizeBackground = new Size(squareDiameter, squareDiameter);   // size of plotarea
            Size sizeZodiacCircle = new Size(sizeBackground.Width - marginBackground * 2, sizeBackground.Height - marginBackground * 2);
            Size sizeHouseCircle = new Size(sizeZodiacCircle.Width - marginZodiacCircle * 2, sizeZodiacCircle.Height - marginZodiacCircle * 2);
            Size sizeAspectCircle = new Size(sizeHouseCircle.Width - marginHouseCircle * 2, sizeHouseCircle.Height - marginHouseCircle * 2);

            // define starting points for eacht rectangle
            Point pointBackground = new Point(plotArea.Left, plotArea.Top);    // starting point
            Point pointZodiac = new Point(pointBackground.X + marginBackground, pointBackground.Y + marginBackground);   // point for rectZodiacCircle
            Point pointHouses = new Point(pointZodiac.X + marginZodiacCircle, pointZodiac.Y + marginZodiacCircle);      // point for rectHouseCircle
            Point pointAspects = new Point(pointHouses.X + marginHouseCircle, pointHouses.Y + marginHouseCircle);

            int xcorr = (int)largeSignGlyphs.GetHeight() / 2;
            Point pointBackground_txt = new Point(plotArea.Left - xcorr, plotArea.Top - xcorr);    // starting point that handles offset for texts

            Rectangle rectBackground = new Rectangle(pointBackground, sizeBackground);
            g.FillRectangle(brushBackground, rectBackground);

            Rectangle rectZodiacCircle = new Rectangle(pointZodiac, sizeZodiacCircle);
            g.FillEllipse(brushZodiacCircle, rectZodiacCircle);
            g.DrawEllipse(penZodiacCircle, rectZodiacCircle);

            Rectangle rectHouseCircle = new Rectangle(pointHouses, sizeHouseCircle);
            g.FillEllipse(brushHouseCircle, rectHouseCircle);
            g.DrawEllipse(penHouseCircle, rectHouseCircle);

            Rectangle rectAspectCircle = new Rectangle(pointAspects, sizeAspectCircle);
            g.FillEllipse(brushAspectCircle, rectAspectCircle);
            g.DrawEllipse(penAspectCircle, rectAspectCircle);

            // test draw line from center to outercircle for all cusps

            double hypothenusaZodiac = sizeZodiacCircle.Width / 2;
            double hypothenusaHouses = sizeHouseCircle.Width / 2;
            double hypothenusaAspects = sizeAspectCircle.Width / 2;
            double hypothenusaSigns = (hypothenusaZodiac + hypothenusaHouses) / 2;
            double hypothenusaPlanets = (sizeHouseCircle.Width / 2) * 0.88;
            double hypothenusaLongText = (sizeHouseCircle.Width / 2) * 0.6;
            double hypothenusaConnectStart = (sizeHouseCircle.Width / 2) * 0.8;
            // todo: correctie voor positie letter

            Point point1;
            Point point2;
            Point point3;

            double corner = 0.0;
            double corner2 = 0.0;

            // draw houses
            double xyz;
            housePositions = createHousePositionList();
            for (int i = 1; i <= 12; i++) {
                xyz = ((double)housePositions[i]);
                corner = mainAxis[0] - xyz;
                point1 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaZodiac);
                point2 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaHouses);
                point3 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaAspects);
                g.DrawLine(penHouseCusps, point2, point3);    // standard houses
            }
            for (int i = 0; i <= 3; i++) {
                xyz = (double)mainAxis[i];
                corner = mainAxis[0] - xyz;
                point1 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaZodiac);
                point2 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaHouses);
                point3 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaAspects);
                g.DrawLine(penMainAxis, point2, point3);      // main axis
            }

            // draw sign compartiments
            for (int i = 0; i <= 11; i++) {
                corner = i * 30 + mainAxis[0];
                point1 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaZodiac);
                point2 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaHouses);
                g.DrawLine(penZodiacCusps, point1, point2);   // outer ring
            }

            // draw signs

            double signOffset = mainAxis[0] % 30;
            int risingSign = (int)Math.Floor(mainAxis[0] / 30);
            int signIndex;
            for (int i = 0; i <= 11; i++) {
              //  signIndex = risingSign - i - shiftSign;
                signIndex = risingSign - i - 1;
                corner = i * 30 + signOffset + 15;

                if (signIndex < 0) signIndex = signIndex + 12;
                point1 = calcPoint(pointBackground_txt, sizeBackground, corner, hypothenusaSigns);
                g.DrawString(Conversions.signGlyphFromIndex(signIndex), largeSignGlyphs, brushSignGlyphs, point1);
            }

            // draw bodies
            ArrayList bodyPositionList = createBodyPositionList();
            ArrayList housePositionList = createHousePositionList();

            // define corners for plotpositions
            int minDistance = 5;   // minimal distance is a fixed value
            for (int i = 0; i < bodyPositionList.Count - 1; i++) {
                while (Math.Abs(((BodyPosition)bodyPositionList[i + 1]).plotPosition - ((BodyPosition)bodyPositionList[i]).plotPosition) < minDistance) {
                    ((BodyPosition)bodyPositionList[i + 1]).plotPosition = Conversions.toRange(((BodyPosition)bodyPositionList[i + 1]).plotPosition + 1, 0, 360);
                }
            }

            string bodyGlyph = "";
            for (int i = 0; i < bodyPositionList.Count; i++) {
                // show glyph
                bodyGlyph = Conversions.bodyGlyphFromIndex(((BodyPosition)bodyPositionList[i]).bodyIndex);
                corner = ((BodyPosition)bodyPositionList[i]).plotPosition;
                point1 = calcPoint(pointBackground_txt, sizeBackground, corner, hypothenusaPlanets);
                g.DrawString(bodyGlyph, largeBodyGlyphs, brushBodyGlyphs, point1);

                // show connect line
                point2 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaConnectStart);
                corner2 = ((BodyPosition)bodyPositionList[i]).munPosition;
                point3 = calcPoint(pointBackground, sizeBackground, corner2, hypothenusaAspects);
                g.DrawLine(penConnectLines, point2, point3);
            }

            // show meta-information
            String name = "";
            if (agent.rpCase.radix.name.Length > 28)
                name = agent.rpCase.radix.name.Substring(0, 26) + "...";
            else
                name = agent.rpCase.radix.name;
            lbl_title.Text = name;
            lbl_charttype.Text = agent.rpCase.radix.chartType;
            lbl_datetime.Text = agent.rpCase.radix.datetime;
            lbl_location.Text = agent.rpCase.radix.location;
            lbl_coordinates.Text = agent.rpCase.radix.coordinatesText;

            // show positions of bodies 
            IndexComparer ic = new IndexComparer(SortDirection.Asc);
            bodyPositions.Sort(ic);
            point1 = new Point(11, 184);
            point2 = new Point (35,187);
            point3 = new Point(112, 184);
            string longTxt = "";
            for (int i = 0; i < bodyPositionList.Count; i++) {
               point1.Y += 18;
               point2.Y += 18;
               point3.Y += 18;
               bodyGlyph = Conversions.bodyGlyphFromIndex(((BodyPosition)bodyPositionList[i]).bodyIndex);
               longTxt = Conversions.deg2sexagesimal((((BodyPosition)bodyPositionList[i]).zodPosition), true, true);            
               g.DrawString(bodyGlyph,listBodyGlyphs,brushBodyGlyphs,point1);
               g.DrawString(longTxt, noGlyphs, brushBodyGlyphs, point2);
               g.DrawString(Conversions.signGlyphFromLong(((BodyPosition)bodyPositionList[i]).zodPosition), listSignGlyphs, brushBodyGlyphs, point3);
            }
            
            // show housepositions
            point1 = new Point(155, 187);
            point2 = new Point(179, 187);
            point3 = new Point(256, 184);
            string cusp = "";
            for (int i = 1; i <= 14; i++) {
                if (i < 13) cusp = i.ToString();
                else if (i == 13) cusp = "Ac";
                else if (i == 14) cusp = "MC";
                point1.Y += 18;
                point2.Y += 18;
                point3.Y += 18;
                longTxt = Conversions.deg2sexagesimal(((double)housePositions[i]), true, true);
                g.DrawString(cusp, noGlyphs, brushBodyGlyphs, point1);
                g.DrawString(longTxt, noGlyphs, brushBodyGlyphs, point2);
                g.DrawString(Conversions.signGlyphFromLong((double)housePositions[i]), listSignGlyphs, brushBodyGlyphs, point3);
            }

            AspectsAnalysis aa = agent.rpCase.radix.getRadixAnalysis().aspectsAnalysis;
            ArrayList aspects = aa.aspects;
            Aspect a;
            int j = 0;
            int k = 0;
            int d = 0;
            for (int i = 0; i < aspects.Count; i++) {
                a = (Aspect)aspects[i];
                d = (int)Math.Truncate(a.aspectType.distance);
                if (d == 90 || d == 180) penActAspect = penAspectsMajorActive;
                else if (d == 60 || d == 120) penActAspect = penAspectsMajorPassive;
                else penActAspect = penAspectsMinorAspects;
                j = a.pos1;
                k = a.pos2;
                if ((j < bodyPositionList.Count) & (k < bodyPositionList.Count)) {
                    for (int h = 0; h < bodyPositionList.Count; h++) {
                        if (((BodyPosition)bodyPositionList[h]).bodyIndex == j)
                            corner = ((BodyPosition)bodyPositionList[h]).munPosition;
                        if (((BodyPosition)bodyPositionList[h]).bodyIndex == k)
                            corner2 = ((BodyPosition)bodyPositionList[h]).munPosition;
                    }
                    point1 = calcPoint(pointBackground, sizeBackground, corner, hypothenusaAspects);
                    point2 = calcPoint(pointBackground, sizeBackground, corner2, hypothenusaAspects);

                    g.DrawLine(penActAspect, point1, point2);
                }
            }
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

        private void btn_positions_Click(object sender, EventArgs e) {
           agent.sendMessage(new RpMessage(Messages.MSG_SHOWPOSITIONS, this));
        }

        private void btn_help_Click(object sender, EventArgs e) {
           Help.ShowHelp(this, "radixpro.chm", "hlp_showchart.html");
        }


    }
}