namespace radixpro.ui {
    /// <summary>
    /// Form to enter data for radix calculation
    /// </summary>
    partial class Frm_Dataradix {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_Dataradix));
            this.btn_ok = new System.Windows.Forms.Button();
            this.btn_cancel = new System.Windows.Forms.Button();
            this.lbl_title = new System.Windows.Forms.Label();
            this.btn_help = new System.Windows.Forms.Button();
            this.lbl_name = new System.Windows.Forms.Label();
            this.lbl_charttype = new System.Windows.Forms.Label();
            this.gb_location = new System.Windows.Forms.GroupBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.ra_south = new System.Windows.Forms.RadioButton();
            this.ra_north = new System.Windows.Forms.RadioButton();
            this.panel1 = new System.Windows.Forms.Panel();
            this.ra_west = new System.Windows.Forms.RadioButton();
            this.ra_east = new System.Windows.Forms.RadioButton();
            this.tb_latitude = new System.Windows.Forms.TextBox();
            this.tb_longitude = new System.Windows.Forms.TextBox();
            this.tb_location = new System.Windows.Forms.TextBox();
            this.lbl_latitude = new System.Windows.Forms.Label();
            this.lbl_longitude = new System.Windows.Forms.Label();
            this.lbl_location = new System.Windows.Forms.Label();
            this.tb_name = new System.Windows.Forms.TextBox();
            this.co_charttype = new System.Windows.Forms.ComboBox();
            this.lbl_intro = new System.Windows.Forms.Label();
            this.gb_datetime = new System.Windows.Forms.GroupBox();
            this.cb_spectimezone = new System.Windows.Forms.CheckBox();
            this.co_timezone = new System.Windows.Forms.ComboBox();
            this.panel4 = new System.Windows.Forms.Panel();
            this.ra_tzwest = new System.Windows.Forms.RadioButton();
            this.ra_tzeast = new System.Windows.Forms.RadioButton();
            this.tb_spectimezone = new System.Windows.Forms.TextBox();
            this.cb_dst = new System.Windows.Forms.CheckBox();
            this.panel3 = new System.Windows.Forms.Panel();
            this.ra_jul = new System.Windows.Forms.RadioButton();
            this.ra_greg = new System.Windows.Forms.RadioButton();
            this.tb_time = new System.Windows.Forms.TextBox();
            this.tb_date = new System.Windows.Forms.TextBox();
            this.lbl_spectimezone = new System.Windows.Forms.Label();
            this.lbl_timezone = new System.Windows.Forms.Label();
            this.lbl_time = new System.Windows.Forms.Label();
            this.lbl_date = new System.Windows.Forms.Label();
            this.gb_description = new System.Windows.Forms.GroupBox();
            this.tb_sourcedescription = new System.Windows.Forms.TextBox();
            this.co_sourcetype = new System.Windows.Forms.ComboBox();
            this.lbl_sourcedescription = new System.Windows.Forms.Label();
            this.lbl_sourcetype = new System.Windows.Forms.Label();
            this.tb_remarks = new System.Windows.Forms.TextBox();
            this.lbl_remarks = new System.Windows.Forms.Label();
            this.gb_location.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.gb_datetime.SuspendLayout();
            this.panel4.SuspendLayout();
            this.panel3.SuspendLayout();
            this.gb_description.SuspendLayout();
            this.SuspendLayout();
            // 
            // btn_ok
            // 
            this.btn_ok.Location = new System.Drawing.Point(589, 397);
            this.btn_ok.Name = "btn_ok";
            this.btn_ok.Size = new System.Drawing.Size(75, 23);
            this.btn_ok.TabIndex = 39;
            this.btn_ok.Text = "btn_ok";
            this.btn_ok.UseVisualStyleBackColor = true;
            this.btn_ok.Click += new System.EventHandler(this.btn_ok_Click);
            // 
            // btn_cancel
            // 
            this.btn_cancel.Location = new System.Drawing.Point(670, 397);
            this.btn_cancel.Name = "btn_cancel";
            this.btn_cancel.Size = new System.Drawing.Size(75, 23);
            this.btn_cancel.TabIndex = 41;
            this.btn_cancel.Text = "btn_cancel";
            this.btn_cancel.UseVisualStyleBackColor = true;
            this.btn_cancel.Click += new System.EventHandler(this.btn_cancel_Click);
            // 
            // lbl_title
            // 
            this.lbl_title.AutoSize = true;
            this.lbl_title.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_title.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lbl_title.Location = new System.Drawing.Point(12, 9);
            this.lbl_title.Name = "lbl_title";
            this.lbl_title.Size = new System.Drawing.Size(125, 37);
            this.lbl_title.TabIndex = 6;
            this.lbl_title.Text = "lbl_title";
            // 
            // btn_help
            // 
            this.btn_help.Location = new System.Drawing.Point(19, 397);
            this.btn_help.Name = "btn_help";
            this.btn_help.Size = new System.Drawing.Size(75, 23);
            this.btn_help.TabIndex = 37;
            this.btn_help.Text = "btn_help";
            this.btn_help.UseVisualStyleBackColor = true;
            this.btn_help.Click += new System.EventHandler(this.btn_help_Click);
            // 
            // lbl_name
            // 
            this.lbl_name.AutoSize = true;
            this.lbl_name.Location = new System.Drawing.Point(16, 92);
            this.lbl_name.Name = "lbl_name";
            this.lbl_name.Size = new System.Drawing.Size(49, 13);
            this.lbl_name.TabIndex = 8;
            this.lbl_name.Text = "lbl_name";
            // 
            // lbl_charttype
            // 
            this.lbl_charttype.AutoSize = true;
            this.lbl_charttype.Location = new System.Drawing.Point(9, 19);
            this.lbl_charttype.Name = "lbl_charttype";
            this.lbl_charttype.Size = new System.Drawing.Size(67, 13);
            this.lbl_charttype.TabIndex = 9;
            this.lbl_charttype.Text = "lbl_charttype";
            // 
            // gb_location
            // 
            this.gb_location.BackColor = System.Drawing.SystemColors.ControlLight;
            this.gb_location.Controls.Add(this.panel2);
            this.gb_location.Controls.Add(this.panel1);
            this.gb_location.Controls.Add(this.tb_latitude);
            this.gb_location.Controls.Add(this.tb_longitude);
            this.gb_location.Controls.Add(this.tb_location);
            this.gb_location.Controls.Add(this.lbl_latitude);
            this.gb_location.Controls.Add(this.lbl_longitude);
            this.gb_location.Controls.Add(this.lbl_location);
            this.gb_location.Location = new System.Drawing.Point(12, 115);
            this.gb_location.Name = "gb_location";
            this.gb_location.Size = new System.Drawing.Size(444, 98);
            this.gb_location.TabIndex = 10;
            this.gb_location.TabStop = false;
            this.gb_location.Text = "gb_location";
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.ra_south);
            this.panel2.Controls.Add(this.ra_north);
            this.panel2.Location = new System.Drawing.Point(296, 68);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(125, 17);
            this.panel2.TabIndex = 11;
            // 
            // ra_south
            // 
            this.ra_south.AutoSize = true;
            this.ra_south.Location = new System.Drawing.Point(72, 2);
            this.ra_south.Name = "ra_south";
            this.ra_south.Size = new System.Drawing.Size(66, 17);
            this.ra_south.TabIndex = 11;
            this.ra_south.TabStop = true;
            this.ra_south.Text = "ra_south";
            this.ra_south.UseVisualStyleBackColor = true;
            // 
            // ra_north
            // 
            this.ra_north.AutoSize = true;
            this.ra_north.Location = new System.Drawing.Point(10, 1);
            this.ra_north.Name = "ra_north";
            this.ra_north.Size = new System.Drawing.Size(64, 17);
            this.ra_north.TabIndex = 10;
            this.ra_north.TabStop = true;
            this.ra_north.Text = "ra_north";
            this.ra_north.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.ra_west);
            this.panel1.Controls.Add(this.ra_east);
            this.panel1.Location = new System.Drawing.Point(296, 42);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(125, 20);
            this.panel1.TabIndex = 6;
            // 
            // ra_west
            // 
            this.ra_west.AutoSize = true;
            this.ra_west.Location = new System.Drawing.Point(72, 3);
            this.ra_west.Name = "ra_west";
            this.ra_west.Size = new System.Drawing.Size(62, 17);
            this.ra_west.TabIndex = 7;
            this.ra_west.TabStop = true;
            this.ra_west.Text = "ra_west";
            this.ra_west.UseVisualStyleBackColor = true;
            // 
            // ra_east
            // 
            this.ra_east.AutoSize = true;
            this.ra_east.Location = new System.Drawing.Point(10, 3);
            this.ra_east.Name = "ra_east";
            this.ra_east.Size = new System.Drawing.Size(60, 17);
            this.ra_east.TabIndex = 6;
            this.ra_east.TabStop = true;
            this.ra_east.Text = "ra_east";
            this.ra_east.UseVisualStyleBackColor = true;
            // 
            // tb_latitude
            // 
            this.tb_latitude.Location = new System.Drawing.Point(150, 68);
            this.tb_latitude.Name = "tb_latitude";
            this.tb_latitude.Size = new System.Drawing.Size(130, 20);
            this.tb_latitude.TabIndex = 9;
            this.tb_latitude.Leave += new System.EventHandler(this.tb_latitude_Leave);
            // 
            // tb_longitude
            // 
            this.tb_longitude.Location = new System.Drawing.Point(150, 42);
            this.tb_longitude.Name = "tb_longitude";
            this.tb_longitude.Size = new System.Drawing.Size(130, 20);
            this.tb_longitude.TabIndex = 5;
            this.tb_longitude.Leave += new System.EventHandler(this.tb_longitude_Leave);
            // 
            // tb_location
            // 
            this.tb_location.Location = new System.Drawing.Point(150, 16);
            this.tb_location.Name = "tb_location";
            this.tb_location.Size = new System.Drawing.Size(278, 20);
            this.tb_location.TabIndex = 3;
            this.tb_location.Leave += new System.EventHandler(this.tb_location_leave);
            // 
            // lbl_latitude
            // 
            this.lbl_latitude.AutoSize = true;
            this.lbl_latitude.Location = new System.Drawing.Point(6, 72);
            this.lbl_latitude.Name = "lbl_latitude";
            this.lbl_latitude.Size = new System.Drawing.Size(57, 13);
            this.lbl_latitude.TabIndex = 2;
            this.lbl_latitude.Text = "lbl_latitude";
            // 
            // lbl_longitude
            // 
            this.lbl_longitude.AutoSize = true;
            this.lbl_longitude.Location = new System.Drawing.Point(6, 45);
            this.lbl_longitude.Name = "lbl_longitude";
            this.lbl_longitude.Size = new System.Drawing.Size(66, 13);
            this.lbl_longitude.TabIndex = 1;
            this.lbl_longitude.Text = "lbl_longitude";
            // 
            // lbl_location
            // 
            this.lbl_location.AutoSize = true;
            this.lbl_location.Location = new System.Drawing.Point(6, 19);
            this.lbl_location.Name = "lbl_location";
            this.lbl_location.Size = new System.Drawing.Size(60, 13);
            this.lbl_location.TabIndex = 0;
            this.lbl_location.Text = "lbl_location";
            // 
            // tb_name
            // 
            this.tb_name.Location = new System.Drawing.Point(162, 89);
            this.tb_name.Name = "tb_name";
            this.tb_name.Size = new System.Drawing.Size(583, 20);
            this.tb_name.TabIndex = 1;
            this.tb_name.Leave += new System.EventHandler(this.tb_name_Leave);
            // 
            // co_charttype
            // 
            this.co_charttype.FormattingEnabled = true;
            this.co_charttype.Location = new System.Drawing.Point(116, 16);
            this.co_charttype.Name = "co_charttype";
            this.co_charttype.Size = new System.Drawing.Size(158, 21);
            this.co_charttype.TabIndex = 29;
            // 
            // lbl_intro
            // 
            this.lbl_intro.AutoSize = true;
            this.lbl_intro.Location = new System.Drawing.Point(16, 56);
            this.lbl_intro.Name = "lbl_intro";
            this.lbl_intro.Size = new System.Drawing.Size(43, 13);
            this.lbl_intro.TabIndex = 13;
            this.lbl_intro.Text = "lbl_intro";
            // 
            // gb_datetime
            // 
            this.gb_datetime.Controls.Add(this.cb_spectimezone);
            this.gb_datetime.Controls.Add(this.co_timezone);
            this.gb_datetime.Controls.Add(this.panel4);
            this.gb_datetime.Controls.Add(this.tb_spectimezone);
            this.gb_datetime.Controls.Add(this.cb_dst);
            this.gb_datetime.Controls.Add(this.panel3);
            this.gb_datetime.Controls.Add(this.tb_time);
            this.gb_datetime.Controls.Add(this.tb_date);
            this.gb_datetime.Controls.Add(this.lbl_spectimezone);
            this.gb_datetime.Controls.Add(this.lbl_timezone);
            this.gb_datetime.Controls.Add(this.lbl_time);
            this.gb_datetime.Controls.Add(this.lbl_date);
            this.gb_datetime.Location = new System.Drawing.Point(12, 219);
            this.gb_datetime.Name = "gb_datetime";
            this.gb_datetime.Size = new System.Drawing.Size(444, 146);
            this.gb_datetime.TabIndex = 14;
            this.gb_datetime.TabStop = false;
            this.gb_datetime.Text = "gb_datetime";
            // 
            // cb_spectimezone
            // 
            this.cb_spectimezone.AutoSize = true;
            this.cb_spectimezone.Location = new System.Drawing.Point(150, 98);
            this.cb_spectimezone.Name = "cb_spectimezone";
            this.cb_spectimezone.Size = new System.Drawing.Size(109, 17);
            this.cb_spectimezone.TabIndex = 23;
            this.cb_spectimezone.Text = "cb_spectimezone";
            this.cb_spectimezone.UseVisualStyleBackColor = true;
            this.cb_spectimezone.CheckedChanged += new System.EventHandler(this.cb_spectimezone_CheckedChanged);
            // 
            // co_timezone
            // 
            this.co_timezone.FormattingEnabled = true;
            this.co_timezone.Location = new System.Drawing.Point(150, 71);
            this.co_timezone.Name = "co_timezone";
            this.co_timezone.Size = new System.Drawing.Size(278, 21);
            this.co_timezone.TabIndex = 21;
            this.co_timezone.Leave += new System.EventHandler(this.co_timezone_Leave);
            // 
            // panel4
            // 
            this.panel4.Controls.Add(this.ra_tzwest);
            this.panel4.Controls.Add(this.ra_tzeast);
            this.panel4.Location = new System.Drawing.Point(297, 118);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(125, 20);
            this.panel4.TabIndex = 27;
            // 
            // ra_tzwest
            // 
            this.ra_tzwest.AutoSize = true;
            this.ra_tzwest.Location = new System.Drawing.Point(72, 3);
            this.ra_tzwest.Name = "ra_tzwest";
            this.ra_tzwest.Size = new System.Drawing.Size(70, 17);
            this.ra_tzwest.TabIndex = 27;
            this.ra_tzwest.TabStop = true;
            this.ra_tzwest.Text = "ra_tzwest";
            this.ra_tzwest.UseVisualStyleBackColor = true;
            // 
            // ra_tzeast
            // 
            this.ra_tzeast.AutoSize = true;
            this.ra_tzeast.Location = new System.Drawing.Point(10, 3);
            this.ra_tzeast.Name = "ra_tzeast";
            this.ra_tzeast.Size = new System.Drawing.Size(68, 17);
            this.ra_tzeast.TabIndex = 26;
            this.ra_tzeast.TabStop = true;
            this.ra_tzeast.Text = "ra_tzeast";
            this.ra_tzeast.UseVisualStyleBackColor = true;
            // 
            // tb_spectimezone
            // 
            this.tb_spectimezone.Location = new System.Drawing.Point(150, 120);
            this.tb_spectimezone.Name = "tb_spectimezone";
            this.tb_spectimezone.Size = new System.Drawing.Size(130, 20);
            this.tb_spectimezone.TabIndex = 25;
            this.tb_spectimezone.Leave += new System.EventHandler(this.tb_spectimezone_Leave);
            // 
            // cb_dst
            // 
            this.cb_dst.AutoSize = true;
            this.cb_dst.Location = new System.Drawing.Point(306, 48);
            this.cb_dst.Name = "cb_dst";
            this.cb_dst.Size = new System.Drawing.Size(58, 17);
            this.cb_dst.TabIndex = 19;
            this.cb_dst.Text = "cb_dst";
            this.cb_dst.UseVisualStyleBackColor = true;
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.ra_jul);
            this.panel3.Controls.Add(this.ra_greg);
            this.panel3.Location = new System.Drawing.Point(296, 19);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(125, 20);
            this.panel3.TabIndex = 15;
            // 
            // ra_jul
            // 
            this.ra_jul.AutoSize = true;
            this.ra_jul.Location = new System.Drawing.Point(72, 3);
            this.ra_jul.Name = "ra_jul";
            this.ra_jul.Size = new System.Drawing.Size(50, 17);
            this.ra_jul.TabIndex = 16;
            this.ra_jul.TabStop = true;
            this.ra_jul.Text = "ra_jul";
            this.ra_jul.UseVisualStyleBackColor = true;
            // 
            // ra_greg
            // 
            this.ra_greg.AutoSize = true;
            this.ra_greg.Location = new System.Drawing.Point(10, 3);
            this.ra_greg.Name = "ra_greg";
            this.ra_greg.Size = new System.Drawing.Size(61, 17);
            this.ra_greg.TabIndex = 14;
            this.ra_greg.TabStop = true;
            this.ra_greg.Text = "ra_greg";
            this.ra_greg.UseVisualStyleBackColor = true;
            // 
            // tb_time
            // 
            this.tb_time.Location = new System.Drawing.Point(150, 45);
            this.tb_time.Name = "tb_time";
            this.tb_time.Size = new System.Drawing.Size(130, 20);
            this.tb_time.TabIndex = 17;
            this.tb_time.Leave += new System.EventHandler(this.tb_time_Leave);
            // 
            // tb_date
            // 
            this.tb_date.Location = new System.Drawing.Point(150, 19);
            this.tb_date.Name = "tb_date";
            this.tb_date.Size = new System.Drawing.Size(130, 20);
            this.tb_date.TabIndex = 13;
            this.tb_date.Leave += new System.EventHandler(this.tb_date_Leave);
            // 
            // lbl_spectimezone
            // 
            this.lbl_spectimezone.AutoSize = true;
            this.lbl_spectimezone.Location = new System.Drawing.Point(6, 123);
            this.lbl_spectimezone.Name = "lbl_spectimezone";
            this.lbl_spectimezone.Size = new System.Drawing.Size(88, 13);
            this.lbl_spectimezone.TabIndex = 3;
            this.lbl_spectimezone.Text = "lbl_spectimezone";
            // 
            // lbl_timezone
            // 
            this.lbl_timezone.AutoSize = true;
            this.lbl_timezone.Location = new System.Drawing.Point(6, 74);
            this.lbl_timezone.Name = "lbl_timezone";
            this.lbl_timezone.Size = new System.Drawing.Size(65, 13);
            this.lbl_timezone.TabIndex = 2;
            this.lbl_timezone.Text = "lbl_timezone";
            // 
            // lbl_time
            // 
            this.lbl_time.AutoSize = true;
            this.lbl_time.Location = new System.Drawing.Point(6, 48);
            this.lbl_time.Name = "lbl_time";
            this.lbl_time.Size = new System.Drawing.Size(42, 13);
            this.lbl_time.TabIndex = 1;
            this.lbl_time.Text = "lbl_time";
            // 
            // lbl_date
            // 
            this.lbl_date.AutoSize = true;
            this.lbl_date.Location = new System.Drawing.Point(6, 22);
            this.lbl_date.Name = "lbl_date";
            this.lbl_date.Size = new System.Drawing.Size(44, 13);
            this.lbl_date.TabIndex = 0;
            this.lbl_date.Text = "lbl_date";
            // 
            // gb_description
            // 
            this.gb_description.Controls.Add(this.tb_sourcedescription);
            this.gb_description.Controls.Add(this.co_sourcetype);
            this.gb_description.Controls.Add(this.lbl_sourcedescription);
            this.gb_description.Controls.Add(this.lbl_sourcetype);
            this.gb_description.Controls.Add(this.tb_remarks);
            this.gb_description.Controls.Add(this.lbl_remarks);
            this.gb_description.Controls.Add(this.co_charttype);
            this.gb_description.Controls.Add(this.lbl_charttype);
            this.gb_description.Location = new System.Drawing.Point(471, 115);
            this.gb_description.Name = "gb_description";
            this.gb_description.Size = new System.Drawing.Size(280, 250);
            this.gb_description.TabIndex = 15;
            this.gb_description.TabStop = false;
            this.gb_description.Text = "gb_description";
            // 
            // tb_sourcedescription
            // 
            this.tb_sourcedescription.Location = new System.Drawing.Point(12, 202);
            this.tb_sourcedescription.Multiline = true;
            this.tb_sourcedescription.Name = "tb_sourcedescription";
            this.tb_sourcedescription.Size = new System.Drawing.Size(262, 42);
            this.tb_sourcedescription.TabIndex = 35;
            // 
            // co_sourcetype
            // 
            this.co_sourcetype.FormattingEnabled = true;
            this.co_sourcetype.Location = new System.Drawing.Point(116, 148);
            this.co_sourcetype.Name = "co_sourcetype";
            this.co_sourcetype.Size = new System.Drawing.Size(158, 21);
            this.co_sourcetype.TabIndex = 33;
            // 
            // lbl_sourcedescription
            // 
            this.lbl_sourcedescription.AutoSize = true;
            this.lbl_sourcedescription.Location = new System.Drawing.Point(9, 178);
            this.lbl_sourcedescription.Name = "lbl_sourcedescription";
            this.lbl_sourcedescription.Size = new System.Drawing.Size(106, 13);
            this.lbl_sourcedescription.TabIndex = 14;
            this.lbl_sourcedescription.Text = "lbl_sourcedescription";
            // 
            // lbl_sourcetype
            // 
            this.lbl_sourcetype.AutoSize = true;
            this.lbl_sourcetype.Location = new System.Drawing.Point(9, 156);
            this.lbl_sourcetype.Name = "lbl_sourcetype";
            this.lbl_sourcetype.Size = new System.Drawing.Size(75, 13);
            this.lbl_sourcetype.TabIndex = 13;
            this.lbl_sourcetype.Text = "lbl_sourcetype";
            // 
            // tb_remarks
            // 
            this.tb_remarks.Location = new System.Drawing.Point(12, 67);
            this.tb_remarks.Multiline = true;
            this.tb_remarks.Name = "tb_remarks";
            this.tb_remarks.Size = new System.Drawing.Size(262, 72);
            this.tb_remarks.TabIndex = 31;
            // 
            // lbl_remarks
            // 
            this.lbl_remarks.AutoSize = true;
            this.lbl_remarks.Location = new System.Drawing.Point(9, 47);
            this.lbl_remarks.Name = "lbl_remarks";
            this.lbl_remarks.Size = new System.Drawing.Size(60, 13);
            this.lbl_remarks.TabIndex = 0;
            this.lbl_remarks.Text = "lbl_remarks";
            // 
            // Frm_Dataradix
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLight;
            this.ClientSize = new System.Drawing.Size(773, 432);
            this.Controls.Add(this.gb_description);
            this.Controls.Add(this.gb_datetime);
            this.Controls.Add(this.lbl_intro);
            this.Controls.Add(this.tb_name);
            this.Controls.Add(this.gb_location);
            this.Controls.Add(this.lbl_name);
            this.Controls.Add(this.btn_help);
            this.Controls.Add(this.lbl_title);
            this.Controls.Add(this.btn_cancel);
            this.Controls.Add(this.btn_ok);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Frm_Dataradix";
            this.ShowInTaskbar = false;
            this.Text = "frm_dataradix";
            this.gb_location.ResumeLayout(false);
            this.gb_location.PerformLayout();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.gb_datetime.ResumeLayout(false);
            this.gb_datetime.PerformLayout();
            this.panel4.ResumeLayout(false);
            this.panel4.PerformLayout();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.gb_description.ResumeLayout(false);
            this.gb_description.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_ok;
        private System.Windows.Forms.Button btn_cancel;
        private System.Windows.Forms.Label lbl_title;
        private System.Windows.Forms.Button btn_help;
        private System.Windows.Forms.Label lbl_name;
        private System.Windows.Forms.Label lbl_charttype;
        private System.Windows.Forms.GroupBox gb_location;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.RadioButton ra_south;
        private System.Windows.Forms.RadioButton ra_north;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.RadioButton ra_west;
        private System.Windows.Forms.RadioButton ra_east;
        private System.Windows.Forms.TextBox tb_latitude;
        private System.Windows.Forms.TextBox tb_longitude;
        private System.Windows.Forms.TextBox tb_location;
        private System.Windows.Forms.Label lbl_latitude;
        private System.Windows.Forms.Label lbl_longitude;
        private System.Windows.Forms.Label lbl_location;
        private System.Windows.Forms.TextBox tb_name;
        private System.Windows.Forms.ComboBox co_charttype;
        private System.Windows.Forms.Label lbl_intro;
        private System.Windows.Forms.GroupBox gb_datetime;
        private System.Windows.Forms.CheckBox cb_dst;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.RadioButton ra_jul;
        private System.Windows.Forms.RadioButton ra_greg;
        private System.Windows.Forms.TextBox tb_time;
        private System.Windows.Forms.TextBox tb_date;
        private System.Windows.Forms.Label lbl_spectimezone;
        private System.Windows.Forms.Label lbl_timezone;
        private System.Windows.Forms.Label lbl_time;
        private System.Windows.Forms.Label lbl_date;
        private System.Windows.Forms.Panel panel4;
        private System.Windows.Forms.RadioButton ra_tzwest;
        private System.Windows.Forms.RadioButton ra_tzeast;
        private System.Windows.Forms.TextBox tb_spectimezone;
        private System.Windows.Forms.GroupBox gb_description;
        private System.Windows.Forms.TextBox tb_remarks;
        private System.Windows.Forms.Label lbl_remarks;
        private System.Windows.Forms.TextBox tb_sourcedescription;
        private System.Windows.Forms.ComboBox co_sourcetype;
        private System.Windows.Forms.Label lbl_sourcedescription;
        private System.Windows.Forms.Label lbl_sourcetype;
        private System.Windows.Forms.ComboBox co_timezone;
        private System.Windows.Forms.CheckBox cb_spectimezone;
    }
}