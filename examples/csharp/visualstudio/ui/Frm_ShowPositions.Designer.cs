namespace radixpro.ui {
    /// <summary>
    /// Form to show positions as a matrix
    /// </summary>
    partial class Frm_Showpositions {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_Showpositions));
            this.lbl_title = new System.Windows.Forms.Label();
            this.lbl_datetime = new System.Windows.Forms.Label();
            this.lbl_location = new System.Windows.Forms.Label();
            this.btn_remarks = new System.Windows.Forms.Button();
            this.btn_source = new System.Windows.Forms.Button();
            this.btn_help = new System.Windows.Forms.Button();
            this.btn_cancel = new System.Windows.Forms.Button();
            this.btn_chart = new System.Windows.Forms.Button();
            this.lbl_intro = new System.Windows.Forms.Label();
            this.dgvPositions = new System.Windows.Forms.DataGridView();
            this.lbl_name = new System.Windows.Forms.Label();
            this.lbl_coordinates = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvPositions)).BeginInit();
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
            // lbl_datetime
            // 
            this.lbl_datetime.AutoSize = true;
            this.lbl_datetime.Location = new System.Drawing.Point(16, 115);
            this.lbl_datetime.Name = "lbl_datetime";
            this.lbl_datetime.Size = new System.Drawing.Size(63, 13);
            this.lbl_datetime.TabIndex = 2;
            this.lbl_datetime.Text = "lbl_datetime";
            // 
            // lbl_location
            // 
            this.lbl_location.AutoSize = true;
            this.lbl_location.Location = new System.Drawing.Point(16, 137);
            this.lbl_location.Name = "lbl_location";
            this.lbl_location.Size = new System.Drawing.Size(60, 13);
            this.lbl_location.TabIndex = 3;
            this.lbl_location.Text = "lbl_location";
            // 
            // btn_remarks
            // 
            this.btn_remarks.Location = new System.Drawing.Point(19, 202);
            this.btn_remarks.Name = "btn_remarks";
            this.btn_remarks.Size = new System.Drawing.Size(93, 23);
            this.btn_remarks.TabIndex = 4;
            this.btn_remarks.Text = "btn_remarks";
            this.btn_remarks.UseVisualStyleBackColor = true;
            this.btn_remarks.Click += new System.EventHandler(this.btn_remarks_Click);
            // 
            // btn_source
            // 
            this.btn_source.Location = new System.Drawing.Point(118, 202);
            this.btn_source.Name = "btn_source";
            this.btn_source.Size = new System.Drawing.Size(93, 23);
            this.btn_source.TabIndex = 5;
            this.btn_source.Text = "btn_source";
            this.btn_source.UseVisualStyleBackColor = true;
            this.btn_source.Click += new System.EventHandler(this.btn_source_Click);
            // 
            // btn_help
            // 
            this.btn_help.Location = new System.Drawing.Point(19, 391);
            this.btn_help.Name = "btn_help";
            this.btn_help.Size = new System.Drawing.Size(75, 23);
            this.btn_help.TabIndex = 7;
            this.btn_help.Text = "btn_help";
            this.btn_help.UseVisualStyleBackColor = true;
            this.btn_help.Click += new System.EventHandler(this.btn_help_Click);
            // 
            // btn_cancel
            // 
            this.btn_cancel.Location = new System.Drawing.Point(181, 391);
            this.btn_cancel.Name = "btn_cancel";
            this.btn_cancel.Size = new System.Drawing.Size(75, 23);
            this.btn_cancel.TabIndex = 8;
            this.btn_cancel.Text = "btn_cancel";
            this.btn_cancel.UseVisualStyleBackColor = true;
            this.btn_cancel.Click += new System.EventHandler(this.btn_cancel_Click);
            // 
            // btn_chart
            // 
            this.btn_chart.Location = new System.Drawing.Point(100, 391);
            this.btn_chart.Name = "btn_chart";
            this.btn_chart.Size = new System.Drawing.Size(75, 23);
            this.btn_chart.TabIndex = 10;
            this.btn_chart.Text = "btn_chart";
            this.btn_chart.UseVisualStyleBackColor = true;
            this.btn_chart.Click += new System.EventHandler(this.btn_chart_Click);
            // 
            // lbl_intro
            // 
            this.lbl_intro.AutoSize = true;
            this.lbl_intro.Location = new System.Drawing.Point(16, 56);
            this.lbl_intro.Name = "lbl_intro";
            this.lbl_intro.Size = new System.Drawing.Size(43, 13);
            this.lbl_intro.TabIndex = 11;
            this.lbl_intro.Text = "lbl_intro";
            // 
            // dgvPositions
            // 
            this.dgvPositions.AllowUserToAddRows = false;
            this.dgvPositions.AllowUserToDeleteRows = false;
            this.dgvPositions.AllowUserToResizeRows = false;
            this.dgvPositions.BackgroundColor = System.Drawing.Color.AliceBlue;
            this.dgvPositions.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvPositions.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.dgvPositions.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            this.dgvPositions.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvPositions.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgvPositions.Location = new System.Drawing.Point(328, 12);
            this.dgvPositions.Name = "dgvPositions";
            this.dgvPositions.ReadOnly = true;
            this.dgvPositions.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            this.dgvPositions.RowHeadersVisible = false;
            this.dgvPositions.RowHeadersWidth = 32;
            this.dgvPositions.Size = new System.Drawing.Size(681, 402);
            this.dgvPositions.TabIndex = 12;
            // 
            // lbl_name
            // 
            this.lbl_name.AutoSize = true;
            this.lbl_name.Location = new System.Drawing.Point(16, 93);
            this.lbl_name.Name = "lbl_name";
            this.lbl_name.Size = new System.Drawing.Size(49, 13);
            this.lbl_name.TabIndex = 1;
            this.lbl_name.Text = "lbl_name";
            // 
            // lbl_coordinates
            // 
            this.lbl_coordinates.AutoSize = true;
            this.lbl_coordinates.Location = new System.Drawing.Point(16, 160);
            this.lbl_coordinates.Name = "lbl_coordinates";
            this.lbl_coordinates.Size = new System.Drawing.Size(78, 13);
            this.lbl_coordinates.TabIndex = 13;
            this.lbl_coordinates.Text = "lbl_coordinates";
            // 
            // Frm_Showpositions
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(1021, 428);
            this.Controls.Add(this.lbl_coordinates);
            this.Controls.Add(this.dgvPositions);
            this.Controls.Add(this.lbl_intro);
            this.Controls.Add(this.btn_chart);
            this.Controls.Add(this.btn_cancel);
            this.Controls.Add(this.btn_help);
            this.Controls.Add(this.btn_source);
            this.Controls.Add(this.btn_remarks);
            this.Controls.Add(this.lbl_location);
            this.Controls.Add(this.lbl_datetime);
            this.Controls.Add(this.lbl_name);
            this.Controls.Add(this.lbl_title);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Frm_Showpositions";
            this.ShowInTaskbar = false;
            this.Text = "frm_positions";
            ((System.ComponentModel.ISupportInitialize)(this.dgvPositions)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbl_title;
        private System.Windows.Forms.Label lbl_datetime;
        private System.Windows.Forms.Label lbl_location;
        private System.Windows.Forms.Button btn_remarks;
        private System.Windows.Forms.Button btn_source;
        private System.Windows.Forms.Button btn_help;
        private System.Windows.Forms.Button btn_cancel;
        private System.Windows.Forms.Button btn_chart;
        private System.Windows.Forms.Label lbl_intro;
        private System.Windows.Forms.DataGridView dgvPositions;
        private System.Windows.Forms.Label lbl_name;
        private System.Windows.Forms.Label lbl_coordinates;

    }
}