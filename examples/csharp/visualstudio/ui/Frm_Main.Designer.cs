namespace radixpro.ui {
    /// <summary>
    /// Generated part of main form
    /// </summary>
    partial class Frm_Main {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_Main));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.mi_file = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator = new System.Windows.Forms.ToolStripSeparator();
            this.mi_results = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_wheel = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_positions = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_analysis = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_aspects = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_midpoints = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_preferences = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_configuration = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_settings = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_help = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_contents = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_index = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator5 = new System.Windows.Forms.ToolStripSeparator();
            this.mi_about = new System.Windows.Forms.ToolStripMenuItem();
            this.lbl_title = new System.Windows.Forms.Label();
            this.lbl_intro1 = new System.Windows.Forms.Label();
            this.lbl_intro2 = new System.Windows.Forms.Label();
            this.gb_options = new System.Windows.Forms.GroupBox();
            this.btn_chart = new System.Windows.Forms.Button();
            this.lbl_chart = new System.Windows.Forms.Label();
            this.btn_positions = new System.Windows.Forms.Button();
            this.lbl_positions = new System.Windows.Forms.Label();
            this.lbl_help = new System.Windows.Forms.Label();
            this.btn_help = new System.Windows.Forms.Button();
            this.lbl_new = new System.Windows.Forms.Label();
            this.btn_new = new System.Windows.Forms.Button();
            this.btn_exit = new System.Windows.Forms.Button();
            this.gb_start = new System.Windows.Forms.GroupBox();
            this.mi_newchart = new System.Windows.Forms.ToolStripMenuItem();
            this.mi_exit = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.gb_options.SuspendLayout();
            this.gb_start.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_file,
            this.mi_results,
            this.mi_analysis,
            this.mi_preferences,
            this.mi_help});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(597, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // mi_file
            // 
            this.mi_file.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_newchart,
            this.toolStripSeparator,
            this.mi_exit});
            this.mi_file.Name = "mi_file";
            this.mi_file.Size = new System.Drawing.Size(49, 20);
            this.mi_file.Text = "mi_file";
            // 
            // toolStripSeparator
            // 
            this.toolStripSeparator.Name = "toolStripSeparator";
            this.toolStripSeparator.Size = new System.Drawing.Size(149, 6);
            // 
            // mi_results
            // 
            this.mi_results.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_wheel,
            this.mi_positions});
            this.mi_results.Name = "mi_results";
            this.mi_results.Size = new System.Drawing.Size(67, 20);
            this.mi_results.Text = "mi_results";
            // 
            // mi_wheel
            // 
            this.mi_wheel.Name = "mi_wheel";
            this.mi_wheel.Size = new System.Drawing.Size(152, 22);
            this.mi_wheel.Text = "mi_wheel";
            this.mi_wheel.Click += new System.EventHandler(this.mi_wheel_Click);
            // 
            // mi_positions
            // 
            this.mi_positions.Name = "mi_positions";
            this.mi_positions.Size = new System.Drawing.Size(152, 22);
            this.mi_positions.Text = "mi_positions";
            this.mi_positions.Click += new System.EventHandler(this.mi_positions_Click);
            // 
            // mi_analysis
            // 
            this.mi_analysis.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_aspects,
            this.mi_midpoints});
            this.mi_analysis.Name = "mi_analysis";
            this.mi_analysis.Size = new System.Drawing.Size(73, 20);
            this.mi_analysis.Text = "mi_analysis";
            // 
            // mi_aspects
            // 
            this.mi_aspects.Name = "mi_aspects";
            this.mi_aspects.Size = new System.Drawing.Size(152, 22);
            this.mi_aspects.Text = "mi_aspects";
            this.mi_aspects.Click += new System.EventHandler(this.mi_aspects_Click);
            // 
            // mi_midpoints
            // 
            this.mi_midpoints.Name = "mi_midpoints";
            this.mi_midpoints.Size = new System.Drawing.Size(152, 22);
            this.mi_midpoints.Text = "mi_midpoints";
            this.mi_midpoints.Click += new System.EventHandler(this.mi_midpoints_Click);
            // 
            // mi_preferences
            // 
            this.mi_preferences.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_configuration,
            this.mi_settings});
            this.mi_preferences.Name = "mi_preferences";
            this.mi_preferences.Size = new System.Drawing.Size(93, 20);
            this.mi_preferences.Text = "mi_preferences";
            // 
            // mi_configuration
            // 
            this.mi_configuration.Name = "mi_configuration";
            this.mi_configuration.Size = new System.Drawing.Size(164, 22);
            this.mi_configuration.Text = "mi_configuration";
            this.mi_configuration.Click += new System.EventHandler(this.configurationToolStripMenuItem_Click);
            // 
            // mi_settings
            // 
            this.mi_settings.Name = "mi_settings";
            this.mi_settings.Size = new System.Drawing.Size(164, 22);
            this.mi_settings.Text = "mi_settings";
            this.mi_settings.Click += new System.EventHandler(this.mi_settings_Click);
            // 
            // mi_help
            // 
            this.mi_help.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mi_contents,
            this.mi_index,
            this.toolStripSeparator5,
            this.mi_about});
            this.mi_help.Name = "mi_help";
            this.mi_help.Size = new System.Drawing.Size(55, 20);
            this.mi_help.Text = "mi_help";
            // 
            // mi_contents
            // 
            this.mi_contents.Name = "mi_contents";
            this.mi_contents.Size = new System.Drawing.Size(143, 22);
            this.mi_contents.Text = "mi_contents";
            this.mi_contents.Click += new System.EventHandler(this.mi_contents_Click);
            // 
            // mi_index
            // 
            this.mi_index.Name = "mi_index";
            this.mi_index.Size = new System.Drawing.Size(143, 22);
            this.mi_index.Text = "mi_index";
            this.mi_index.Click += new System.EventHandler(this.mi_index_Click);
            // 
            // toolStripSeparator5
            // 
            this.toolStripSeparator5.Name = "toolStripSeparator5";
            this.toolStripSeparator5.Size = new System.Drawing.Size(140, 6);
            // 
            // mi_about
            // 
            this.mi_about.Name = "mi_about";
            this.mi_about.Size = new System.Drawing.Size(143, 22);
            this.mi_about.Text = "mi_about";
            this.mi_about.Click += new System.EventHandler(this.mi_about_Click);
            // 
            // lbl_title
            // 
            this.lbl_title.AutoSize = true;
            this.lbl_title.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_title.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lbl_title.Location = new System.Drawing.Point(19, 38);
            this.lbl_title.Name = "lbl_title";
            this.lbl_title.Size = new System.Drawing.Size(125, 37);
            this.lbl_title.TabIndex = 1;
            this.lbl_title.Text = "lbl_title";
            // 
            // lbl_intro1
            // 
            this.lbl_intro1.AutoSize = true;
            this.lbl_intro1.Location = new System.Drawing.Point(21, 89);
            this.lbl_intro1.Name = "lbl_intro1";
            this.lbl_intro1.Size = new System.Drawing.Size(49, 13);
            this.lbl_intro1.TabIndex = 2;
            this.lbl_intro1.Text = "lbl_intro1";
            // 
            // lbl_intro2
            // 
            this.lbl_intro2.AutoSize = true;
            this.lbl_intro2.Location = new System.Drawing.Point(21, 115);
            this.lbl_intro2.Name = "lbl_intro2";
            this.lbl_intro2.Size = new System.Drawing.Size(49, 13);
            this.lbl_intro2.TabIndex = 3;
            this.lbl_intro2.Text = "lbl_intro2";
            // 
            // gb_options
            // 
            this.gb_options.Controls.Add(this.btn_chart);
            this.gb_options.Controls.Add(this.lbl_chart);
            this.gb_options.Controls.Add(this.btn_positions);
            this.gb_options.Controls.Add(this.lbl_positions);
            this.gb_options.Location = new System.Drawing.Point(307, 148);
            this.gb_options.Name = "gb_options";
            this.gb_options.Size = new System.Drawing.Size(239, 86);
            this.gb_options.TabIndex = 6;
            this.gb_options.TabStop = false;
            this.gb_options.Text = "gb_options";
            // 
            // btn_chart
            // 
            this.btn_chart.Location = new System.Drawing.Point(9, 19);
            this.btn_chart.Name = "btn_chart";
            this.btn_chart.Size = new System.Drawing.Size(75, 23);
            this.btn_chart.TabIndex = 8;
            this.btn_chart.Text = "btn_chart";
            this.btn_chart.UseVisualStyleBackColor = true;
            this.btn_chart.Click += new System.EventHandler(this.btn_chart_Click);
            // 
            // lbl_chart
            // 
            this.lbl_chart.AutoSize = true;
            this.lbl_chart.Location = new System.Drawing.Point(100, 24);
            this.lbl_chart.Name = "lbl_chart";
            this.lbl_chart.Size = new System.Drawing.Size(47, 13);
            this.lbl_chart.TabIndex = 9;
            this.lbl_chart.Text = "lbl_chart";
            // 
            // btn_positions
            // 
            this.btn_positions.Location = new System.Drawing.Point(9, 48);
            this.btn_positions.Name = "btn_positions";
            this.btn_positions.Size = new System.Drawing.Size(75, 23);
            this.btn_positions.TabIndex = 10;
            this.btn_positions.Text = "btn_positions";
            this.btn_positions.UseVisualStyleBackColor = true;
            this.btn_positions.Click += new System.EventHandler(this.btn_positions_Click);
            // 
            // lbl_positions
            // 
            this.lbl_positions.AutoSize = true;
            this.lbl_positions.Location = new System.Drawing.Point(100, 53);
            this.lbl_positions.Name = "lbl_positions";
            this.lbl_positions.Size = new System.Drawing.Size(64, 13);
            this.lbl_positions.TabIndex = 11;
            this.lbl_positions.Text = "lbl_positions";
            // 
            // lbl_help
            // 
            this.lbl_help.AutoSize = true;
            this.lbl_help.Location = new System.Drawing.Point(124, 277);
            this.lbl_help.Name = "lbl_help";
            this.lbl_help.Size = new System.Drawing.Size(43, 13);
            this.lbl_help.TabIndex = 19;
            this.lbl_help.Text = "lbl_help";
            // 
            // btn_help
            // 
            this.btn_help.Location = new System.Drawing.Point(34, 272);
            this.btn_help.Name = "btn_help";
            this.btn_help.Size = new System.Drawing.Size(75, 23);
            this.btn_help.TabIndex = 18;
            this.btn_help.Text = "bn_help";
            this.btn_help.UseVisualStyleBackColor = true;
            this.btn_help.Click += new System.EventHandler(this.btn_help_Click);
            // 
            // lbl_new
            // 
            this.lbl_new.AutoSize = true;
            this.lbl_new.Location = new System.Drawing.Point(100, 24);
            this.lbl_new.Name = "lbl_new";
            this.lbl_new.Size = new System.Drawing.Size(43, 13);
            this.lbl_new.TabIndex = 1;
            this.lbl_new.Text = "lbl_new";
            // 
            // btn_new
            // 
            this.btn_new.Location = new System.Drawing.Point(10, 19);
            this.btn_new.Name = "btn_new";
            this.btn_new.Size = new System.Drawing.Size(75, 23);
            this.btn_new.TabIndex = 0;
            this.btn_new.Text = "btn_new";
            this.btn_new.UseVisualStyleBackColor = true;
            this.btn_new.Click += new System.EventHandler(this.btn_new_Click);
            // 
            // btn_exit
            // 
            this.btn_exit.Location = new System.Drawing.Point(691, 345);
            this.btn_exit.Name = "btn_exit";
            this.btn_exit.Size = new System.Drawing.Size(136, 23);
            this.btn_exit.TabIndex = 20;
            this.btn_exit.Text = "btn_exit";
            this.btn_exit.UseVisualStyleBackColor = true;
            this.btn_exit.Click += new System.EventHandler(this.btn_exit_Click);
            // 
            // gb_start
            // 
            this.gb_start.Controls.Add(this.btn_new);
            this.gb_start.Controls.Add(this.lbl_new);
            this.gb_start.Location = new System.Drawing.Point(24, 148);
            this.gb_start.Name = "gb_start";
            this.gb_start.Size = new System.Drawing.Size(247, 86);
            this.gb_start.TabIndex = 24;
            this.gb_start.TabStop = false;
            this.gb_start.Text = "gb_start";
            // 
            // mi_newchart
            // 
            this.mi_newchart.Name = "mi_newchart";
            this.mi_newchart.Size = new System.Drawing.Size(152, 22);
            this.mi_newchart.Text = "mi_newchart";
            this.mi_newchart.Click += new System.EventHandler(this.newToolStripMenuItem_Click);
            // 
            // mi_exit
            // 
            this.mi_exit.Name = "mi_exit";
            this.mi_exit.Size = new System.Drawing.Size(152, 22);
            this.mi_exit.Text = "mi_exit";
            // 
            // Frm_Main
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLight;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.ClientSize = new System.Drawing.Size(597, 323);
            this.Controls.Add(this.gb_start);
            this.Controls.Add(this.lbl_help);
            this.Controls.Add(this.btn_help);
            this.Controls.Add(this.btn_exit);
            this.Controls.Add(this.gb_options);
            this.Controls.Add(this.lbl_intro2);
            this.Controls.Add(this.lbl_intro1);
            this.Controls.Add(this.lbl_title);
            this.Controls.Add(this.menuStrip1);
            this.DoubleBuffered = true;
            this.HelpButton = true;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Frm_Main";
            this.Text = "Frm_Main";
            this.MouseEnter += new System.EventHandler(this.Frm_Main_MouseEnter);
            this.Enter += new System.EventHandler(this.Frm_Main_Enter);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.gb_options.ResumeLayout(false);
            this.gb_options.PerformLayout();
            this.gb_start.ResumeLayout(false);
            this.gb_start.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem mi_file;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator;
        private System.Windows.Forms.ToolStripMenuItem mi_results;
        private System.Windows.Forms.ToolStripMenuItem mi_wheel;
        private System.Windows.Forms.ToolStripMenuItem mi_positions;
        private System.Windows.Forms.ToolStripMenuItem mi_analysis;
        private System.Windows.Forms.ToolStripMenuItem mi_aspects;
        private System.Windows.Forms.ToolStripMenuItem mi_midpoints;
        private System.Windows.Forms.ToolStripMenuItem mi_preferences;
        private System.Windows.Forms.ToolStripMenuItem mi_configuration;
        private System.Windows.Forms.ToolStripMenuItem mi_settings;
        private System.Windows.Forms.ToolStripMenuItem mi_help;
        private System.Windows.Forms.ToolStripMenuItem mi_contents;
        private System.Windows.Forms.ToolStripMenuItem mi_index;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator5;
        private System.Windows.Forms.ToolStripMenuItem mi_about;
        private System.Windows.Forms.Label lbl_title;
        private System.Windows.Forms.Label lbl_intro1;
        private System.Windows.Forms.Label lbl_intro2;
        private System.Windows.Forms.GroupBox gb_options;
        private System.Windows.Forms.Label lbl_positions;
        private System.Windows.Forms.Button btn_positions;
        private System.Windows.Forms.Label lbl_chart;
        private System.Windows.Forms.Button btn_chart;
        private System.Windows.Forms.Label lbl_new;
        private System.Windows.Forms.Button btn_new;
        private System.Windows.Forms.Label lbl_help;
        private System.Windows.Forms.Button btn_help;
        private System.Windows.Forms.Button btn_exit;
        private System.Windows.Forms.GroupBox gb_start;
        private System.Windows.Forms.ToolStripMenuItem mi_newchart;
        private System.Windows.Forms.ToolStripMenuItem mi_exit;
    }
}