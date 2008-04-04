namespace radixpro.ui {
    /// <summary>
    /// Generated part of Form for configuration
    /// </summary>
    partial class Frm_Configuration {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_Configuration));
            this.lbl_title = new System.Windows.Forms.Label();
            this.btn_cancel = new System.Windows.Forms.Button();
            this.gb_orbis = new System.Windows.Forms.GroupBox();
            this.co_progressions = new System.Windows.Forms.NumericUpDown();
            this.co_midpoints = new System.Windows.Forms.NumericUpDown();
            this.co_minor = new System.Windows.Forms.NumericUpDown();
            this.co_major = new System.Windows.Forms.NumericUpDown();
            this.lbl_progressions = new System.Windows.Forms.Label();
            this.lbl_midpoints = new System.Windows.Forms.Label();
            this.lbl_minor = new System.Windows.Forms.Label();
            this.lbl_major = new System.Windows.Forms.Label();
            this.lbl_orbis = new System.Windows.Forms.Label();
            this.gb_additional = new System.Windows.Forms.GroupBox();
            this.ra_oscillating = new System.Windows.Forms.RadioButton();
            this.ra_mean = new System.Windows.Forms.RadioButton();
            this.cb_chiron = new System.Windows.Forms.CheckBox();
            this.cb_lunarnode = new System.Windows.Forms.CheckBox();
            this.lbl_additional = new System.Windows.Forms.Label();
            this.lb_houses = new System.Windows.Forms.ListBox();
            this.btn_save = new System.Windows.Forms.Button();
            this.btn_defaults = new System.Windows.Forms.Button();
            this.btn_help = new System.Windows.Forms.Button();
            this.lbl_intro = new System.Windows.Forms.Label();
            this.lbl_houses = new System.Windows.Forms.Label();
            this.gb_houses = new System.Windows.Forms.GroupBox();
            this.gb_orbis.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.co_progressions)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_midpoints)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_minor)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_major)).BeginInit();
            this.gb_additional.SuspendLayout();
            this.gb_houses.SuspendLayout();
            this.SuspendLayout();
            // 
            // lbl_title
            // 
            this.lbl_title.AutoSize = true;
            this.lbl_title.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_title.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lbl_title.Location = new System.Drawing.Point(12, 9);
            this.lbl_title.Name = "lbl_title";
            this.lbl_title.Size = new System.Drawing.Size(125, 37);
            this.lbl_title.TabIndex = 0;
            this.lbl_title.Text = "lbl_title";
            // 
            // btn_cancel
            // 
            this.btn_cancel.Location = new System.Drawing.Point(641, 288);
            this.btn_cancel.Name = "btn_cancel";
            this.btn_cancel.Size = new System.Drawing.Size(75, 23);
            this.btn_cancel.TabIndex = 2;
            this.btn_cancel.Text = "btn_cancel";
            this.btn_cancel.UseVisualStyleBackColor = true;
            this.btn_cancel.Click += new System.EventHandler(this.btn_cancel_Click);
            // 
            // gb_orbis
            // 
            this.gb_orbis.Controls.Add(this.co_progressions);
            this.gb_orbis.Controls.Add(this.co_midpoints);
            this.gb_orbis.Controls.Add(this.co_minor);
            this.gb_orbis.Controls.Add(this.co_major);
            this.gb_orbis.Controls.Add(this.lbl_progressions);
            this.gb_orbis.Controls.Add(this.lbl_midpoints);
            this.gb_orbis.Controls.Add(this.lbl_minor);
            this.gb_orbis.Controls.Add(this.lbl_major);
            this.gb_orbis.Controls.Add(this.lbl_orbis);
            this.gb_orbis.Location = new System.Drawing.Point(17, 89);
            this.gb_orbis.Name = "gb_orbis";
            this.gb_orbis.Size = new System.Drawing.Size(200, 182);
            this.gb_orbis.TabIndex = 3;
            this.gb_orbis.TabStop = false;
            this.gb_orbis.Text = "gb_orbis";
            // 
            // co_progressions
            // 
            this.co_progressions.Enabled = false;
            this.co_progressions.Location = new System.Drawing.Point(122, 143);
            this.co_progressions.Name = "co_progressions";
            this.co_progressions.Size = new System.Drawing.Size(43, 20);
            this.co_progressions.TabIndex = 8;
            // 
            // co_midpoints
            // 
            this.co_midpoints.Location = new System.Drawing.Point(122, 119);
            this.co_midpoints.Name = "co_midpoints";
            this.co_midpoints.Size = new System.Drawing.Size(43, 20);
            this.co_midpoints.TabIndex = 7;
            // 
            // co_minor
            // 
            this.co_minor.Location = new System.Drawing.Point(122, 94);
            this.co_minor.Name = "co_minor";
            this.co_minor.Size = new System.Drawing.Size(43, 20);
            this.co_minor.TabIndex = 6;
            // 
            // co_major
            // 
            this.co_major.Location = new System.Drawing.Point(122, 70);
            this.co_major.Name = "co_major";
            this.co_major.Size = new System.Drawing.Size(43, 20);
            this.co_major.TabIndex = 5;
            // 
            // lbl_progressions
            // 
            this.lbl_progressions.AutoSize = true;
            this.lbl_progressions.Location = new System.Drawing.Point(7, 147);
            this.lbl_progressions.Name = "lbl_progressions";
            this.lbl_progressions.Size = new System.Drawing.Size(82, 13);
            this.lbl_progressions.TabIndex = 4;
            this.lbl_progressions.Text = "lbl_progressions";
            // 
            // lbl_midpoints
            // 
            this.lbl_midpoints.AutoSize = true;
            this.lbl_midpoints.Location = new System.Drawing.Point(7, 121);
            this.lbl_midpoints.Name = "lbl_midpoints";
            this.lbl_midpoints.Size = new System.Drawing.Size(67, 13);
            this.lbl_midpoints.TabIndex = 3;
            this.lbl_midpoints.Text = "lbl_midpoints";
            // 
            // lbl_minor
            // 
            this.lbl_minor.AutoSize = true;
            this.lbl_minor.Location = new System.Drawing.Point(7, 97);
            this.lbl_minor.Name = "lbl_minor";
            this.lbl_minor.Size = new System.Drawing.Size(48, 13);
            this.lbl_minor.TabIndex = 2;
            this.lbl_minor.Text = "lbl_minor";
            // 
            // lbl_major
            // 
            this.lbl_major.AutoSize = true;
            this.lbl_major.Location = new System.Drawing.Point(6, 72);
            this.lbl_major.Name = "lbl_major";
            this.lbl_major.Size = new System.Drawing.Size(48, 13);
            this.lbl_major.TabIndex = 1;
            this.lbl_major.Text = "lbl_major";
            // 
            // lbl_orbis
            // 
            this.lbl_orbis.AutoSize = true;
            this.lbl_orbis.Location = new System.Drawing.Point(7, 39);
            this.lbl_orbis.Name = "lbl_orbis";
            this.lbl_orbis.Size = new System.Drawing.Size(45, 13);
            this.lbl_orbis.TabIndex = 0;
            this.lbl_orbis.Text = "lbl_orbis";
            // 
            // gb_additional
            // 
            this.gb_additional.Controls.Add(this.ra_oscillating);
            this.gb_additional.Controls.Add(this.ra_mean);
            this.gb_additional.Controls.Add(this.cb_chiron);
            this.gb_additional.Controls.Add(this.cb_lunarnode);
            this.gb_additional.Controls.Add(this.lbl_additional);
            this.gb_additional.Location = new System.Drawing.Point(256, 89);
            this.gb_additional.Name = "gb_additional";
            this.gb_additional.Size = new System.Drawing.Size(200, 182);
            this.gb_additional.TabIndex = 4;
            this.gb_additional.TabStop = false;
            this.gb_additional.Text = "gb_additional";
            // 
            // ra_oscillating
            // 
            this.ra_oscillating.AutoSize = true;
            this.ra_oscillating.Location = new System.Drawing.Point(42, 119);
            this.ra_oscillating.Name = "ra_oscillating";
            this.ra_oscillating.Size = new System.Drawing.Size(86, 17);
            this.ra_oscillating.TabIndex = 4;
            this.ra_oscillating.TabStop = true;
            this.ra_oscillating.Text = "ra_oscillating";
            this.ra_oscillating.UseVisualStyleBackColor = true;
            // 
            // ra_mean
            // 
            this.ra_mean.AutoSize = true;
            this.ra_mean.Location = new System.Drawing.Point(42, 95);
            this.ra_mean.Name = "ra_mean";
            this.ra_mean.Size = new System.Drawing.Size(66, 17);
            this.ra_mean.TabIndex = 3;
            this.ra_mean.TabStop = true;
            this.ra_mean.Text = "ra_mean";
            this.ra_mean.UseVisualStyleBackColor = true;
            // 
            // cb_chiron
            // 
            this.cb_chiron.AutoSize = true;
            this.cb_chiron.Location = new System.Drawing.Point(9, 146);
            this.cb_chiron.Name = "cb_chiron";
            this.cb_chiron.Size = new System.Drawing.Size(73, 17);
            this.cb_chiron.TabIndex = 2;
            this.cb_chiron.Text = "cb_chiron";
            this.cb_chiron.UseVisualStyleBackColor = true;
            // 
            // cb_lunarnode
            // 
            this.cb_lunarnode.AutoSize = true;
            this.cb_lunarnode.Location = new System.Drawing.Point(9, 71);
            this.cb_lunarnode.Name = "cb_lunarnode";
            this.cb_lunarnode.Size = new System.Drawing.Size(91, 17);
            this.cb_lunarnode.TabIndex = 1;
            this.cb_lunarnode.Text = "cb_lunarnode";
            this.cb_lunarnode.UseVisualStyleBackColor = true;
            // 
            // lbl_additional
            // 
            this.lbl_additional.AutoSize = true;
            this.lbl_additional.Location = new System.Drawing.Point(6, 39);
            this.lbl_additional.Name = "lbl_additional";
            this.lbl_additional.Size = new System.Drawing.Size(68, 13);
            this.lbl_additional.TabIndex = 0;
            this.lbl_additional.Text = "lbl_additional";
            // 
            // lb_houses
            // 
            this.lb_houses.FormattingEnabled = true;
            this.lb_houses.Location = new System.Drawing.Point(6, 72);
            this.lb_houses.Name = "lb_houses";
            this.lb_houses.Size = new System.Drawing.Size(205, 95);
            this.lb_houses.TabIndex = 5;
            // 
            // btn_save
            // 
            this.btn_save.Location = new System.Drawing.Point(479, 288);
            this.btn_save.Name = "btn_save";
            this.btn_save.Size = new System.Drawing.Size(75, 23);
            this.btn_save.TabIndex = 6;
            this.btn_save.Text = "btn_save";
            this.btn_save.UseVisualStyleBackColor = true;
            this.btn_save.Click += new System.EventHandler(this.btn_save_Click);
            // 
            // btn_defaults
            // 
            this.btn_defaults.Location = new System.Drawing.Point(560, 288);
            this.btn_defaults.Name = "btn_defaults";
            this.btn_defaults.Size = new System.Drawing.Size(75, 23);
            this.btn_defaults.TabIndex = 7;
            this.btn_defaults.Text = "btn_defaults";
            this.btn_defaults.UseVisualStyleBackColor = true;
            // 
            // btn_help
            // 
            this.btn_help.Location = new System.Drawing.Point(27, 288);
            this.btn_help.Name = "btn_help";
            this.btn_help.Size = new System.Drawing.Size(75, 23);
            this.btn_help.TabIndex = 8;
            this.btn_help.Text = "btn_help";
            this.btn_help.UseVisualStyleBackColor = true;
            // 
            // lbl_intro
            // 
            this.lbl_intro.AutoSize = true;
            this.lbl_intro.Location = new System.Drawing.Point(18, 54);
            this.lbl_intro.Name = "lbl_intro";
            this.lbl_intro.Size = new System.Drawing.Size(43, 13);
            this.lbl_intro.TabIndex = 9;
            this.lbl_intro.Text = "lbl_intro";
            // 
            // lbl_houses
            // 
            this.lbl_houses.AutoSize = true;
            this.lbl_houses.Location = new System.Drawing.Point(6, 39);
            this.lbl_houses.Name = "lbl_houses";
            this.lbl_houses.Size = new System.Drawing.Size(57, 13);
            this.lbl_houses.TabIndex = 10;
            this.lbl_houses.Text = "lbl_houses";
            // 
            // gb_houses
            // 
            this.gb_houses.Controls.Add(this.lb_houses);
            this.gb_houses.Controls.Add(this.lbl_houses);
            this.gb_houses.Location = new System.Drawing.Point(499, 89);
            this.gb_houses.Name = "gb_houses";
            this.gb_houses.Size = new System.Drawing.Size(217, 182);
            this.gb_houses.TabIndex = 11;
            this.gb_houses.TabStop = false;
            this.gb_houses.Text = "gb_houses";
            // 
            // Frm_Configuration
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLight;
            this.ClientSize = new System.Drawing.Size(747, 345);
            this.Controls.Add(this.gb_houses);
            this.Controls.Add(this.lbl_intro);
            this.Controls.Add(this.btn_help);
            this.Controls.Add(this.btn_defaults);
            this.Controls.Add(this.btn_save);
            this.Controls.Add(this.gb_additional);
            this.Controls.Add(this.gb_orbis);
            this.Controls.Add(this.btn_cancel);
            this.Controls.Add(this.lbl_title);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Frm_Configuration";
            this.ShowInTaskbar = false;
            this.Text = "frm_Configuration";
            this.gb_orbis.ResumeLayout(false);
            this.gb_orbis.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.co_progressions)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_midpoints)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_minor)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.co_major)).EndInit();
            this.gb_additional.ResumeLayout(false);
            this.gb_additional.PerformLayout();
            this.gb_houses.ResumeLayout(false);
            this.gb_houses.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbl_title;
        private System.Windows.Forms.Button btn_cancel;
        private System.Windows.Forms.GroupBox gb_orbis;
        private System.Windows.Forms.GroupBox gb_additional;
        private System.Windows.Forms.NumericUpDown co_progressions;
        private System.Windows.Forms.NumericUpDown co_midpoints;
        private System.Windows.Forms.NumericUpDown co_minor;
        private System.Windows.Forms.NumericUpDown co_major;
        private System.Windows.Forms.Label lbl_progressions;
        private System.Windows.Forms.Label lbl_midpoints;
        private System.Windows.Forms.Label lbl_minor;
        private System.Windows.Forms.Label lbl_major;
        private System.Windows.Forms.Label lbl_orbis;
        private System.Windows.Forms.RadioButton ra_oscillating;
        private System.Windows.Forms.RadioButton ra_mean;
        private System.Windows.Forms.CheckBox cb_chiron;
        private System.Windows.Forms.CheckBox cb_lunarnode;
        private System.Windows.Forms.Label lbl_additional;
        private System.Windows.Forms.ListBox lb_houses;
        private System.Windows.Forms.Button btn_save;
        private System.Windows.Forms.Button btn_defaults;
        private System.Windows.Forms.Button btn_help;
        private System.Windows.Forms.Label lbl_intro;
        private System.Windows.Forms.Label lbl_houses;
        private System.Windows.Forms.GroupBox gb_houses;
    }
}