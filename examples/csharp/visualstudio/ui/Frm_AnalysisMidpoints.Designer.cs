namespace radixpro.ui {
   partial class Frm_AnalysisMidpoints {
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
          System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_AnalysisMidpoints));
          this.lbl_title = new System.Windows.Forms.Label();
          this.lbl_name = new System.Windows.Forms.Label();
          this.lbl_orbis = new System.Windows.Forms.Label();
          this.btn_help = new System.Windows.Forms.Button();
          this.btn_chart = new System.Windows.Forms.Button();
          this.btn_cancel = new System.Windows.Forms.Button();
          this.dgvMidpoints = new System.Windows.Forms.DataGridView();
          ((System.ComponentModel.ISupportInitialize)(this.dgvMidpoints)).BeginInit();
          this.SuspendLayout();
          // 
          // lbl_title
          // 
          this.lbl_title.AutoSize = true;
          this.lbl_title.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
          this.lbl_title.ForeColor = System.Drawing.SystemColors.HotTrack;
          this.lbl_title.Location = new System.Drawing.Point(12, 9);
          this.lbl_title.Name = "lbl_title";
          this.lbl_title.Size = new System.Drawing.Size(116, 37);
          this.lbl_title.TabIndex = 0;
          this.lbl_title.Text = "lbl_title";
          // 
          // lbl_name
          // 
          this.lbl_name.AutoSize = true;
          this.lbl_name.Location = new System.Drawing.Point(16, 68);
          this.lbl_name.Name = "lbl_name";
          this.lbl_name.Size = new System.Drawing.Size(49, 13);
          this.lbl_name.TabIndex = 1;
          this.lbl_name.Text = "lbl_name";
          // 
          // lbl_orbis
          // 
          this.lbl_orbis.AutoSize = true;
          this.lbl_orbis.Location = new System.Drawing.Point(16, 90);
          this.lbl_orbis.Name = "lbl_orbis";
          this.lbl_orbis.Size = new System.Drawing.Size(45, 13);
          this.lbl_orbis.TabIndex = 2;
          this.lbl_orbis.Text = "lbl_orbis";
          // 
          // btn_help
          // 
          this.btn_help.Location = new System.Drawing.Point(19, 431);
          this.btn_help.Name = "btn_help";
          this.btn_help.Size = new System.Drawing.Size(75, 23);
          this.btn_help.TabIndex = 3;
          this.btn_help.Text = "btn_help";
          this.btn_help.UseVisualStyleBackColor = true;
          this.btn_help.Click += new System.EventHandler(this.btn_help_Click);
          // 
          // btn_chart
          // 
          this.btn_chart.Location = new System.Drawing.Point(100, 431);
          this.btn_chart.Name = "btn_chart";
          this.btn_chart.Size = new System.Drawing.Size(75, 23);
          this.btn_chart.TabIndex = 4;
          this.btn_chart.Text = "btn_chart";
          this.btn_chart.UseVisualStyleBackColor = true;
          this.btn_chart.Click += new System.EventHandler(this.btn_chart_Click);
          // 
          // btn_cancel
          // 
          this.btn_cancel.Location = new System.Drawing.Point(181, 431);
          this.btn_cancel.Name = "btn_cancel";
          this.btn_cancel.Size = new System.Drawing.Size(75, 23);
          this.btn_cancel.TabIndex = 5;
          this.btn_cancel.Text = "btn_cancel";
          this.btn_cancel.UseVisualStyleBackColor = true;
          this.btn_cancel.Click += new System.EventHandler(this.btn_cancel_Click);
          // 
          // dgvMidpoints
          // 
          this.dgvMidpoints.AllowUserToAddRows = false;
          this.dgvMidpoints.AllowUserToDeleteRows = false;
          this.dgvMidpoints.AllowUserToResizeRows = false;
          this.dgvMidpoints.BackgroundColor = System.Drawing.Color.AliceBlue;
          this.dgvMidpoints.BorderStyle = System.Windows.Forms.BorderStyle.None;
          this.dgvMidpoints.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
          this.dgvMidpoints.Location = new System.Drawing.Point(310, 30);
          this.dgvMidpoints.Name = "dgvMidpoints";
          this.dgvMidpoints.ReadOnly = true;
          this.dgvMidpoints.RowHeadersVisible = false;
          this.dgvMidpoints.Size = new System.Drawing.Size(463, 424);
          this.dgvMidpoints.TabIndex = 6;
          // 
          // Frm_AnalysisMidpoints
          // 
          this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
          this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
          this.BackColor = System.Drawing.SystemColors.ControlLight;
          this.ClientSize = new System.Drawing.Size(785, 466);
          this.Controls.Add(this.dgvMidpoints);
          this.Controls.Add(this.btn_cancel);
          this.Controls.Add(this.btn_chart);
          this.Controls.Add(this.btn_help);
          this.Controls.Add(this.lbl_orbis);
          this.Controls.Add(this.lbl_name);
          this.Controls.Add(this.lbl_title);
          this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
          this.Name = "Frm_AnalysisMidpoints";
          this.ShowInTaskbar = false;
          this.Text = "Frm_AnalysisMidpoints";
          ((System.ComponentModel.ISupportInitialize)(this.dgvMidpoints)).EndInit();
          this.ResumeLayout(false);
          this.PerformLayout();

      }

      #endregion

      private System.Windows.Forms.Label lbl_title;
      private System.Windows.Forms.Label lbl_name;
      private System.Windows.Forms.Label lbl_orbis;
      private System.Windows.Forms.Button btn_help;
      private System.Windows.Forms.Button btn_chart;
      private System.Windows.Forms.Button btn_cancel;
      private System.Windows.Forms.DataGridView dgvMidpoints;
   }
}