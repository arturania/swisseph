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

namespace radixpro.controller {

    /// <summary>
    /// Asynchroneous message sent from a form to the agent in the controller.
    /// </summary>
    public class RpMessage {
        private int _id;
        private Form _sender;

        /// <summary>
        /// Constructor for a rpMessage
        /// </summary>
        /// <param name="id">Identification of the message</param>
        /// <param name="sender">The sending form</param>
        public RpMessage(int id, Form sender) {
            this.id = id;
            this.sender = sender;
        }

        /// <summary>
        /// Identification for the message
        /// </summary>
        public int id {
            get {return _id; }
            set {_id = value; }
        }

        /// <summary>
        /// The sending form
        /// </summary>
        public Form sender {
            get {return _sender; }
            set {_sender = value; }
        }

    }

}