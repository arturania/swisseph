namespace radixpro.ui {
    partial class Frm_TextPopup {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_TextPopup));
            this.btn_ok = new System.Windows.Forms.Button();
            this.tb_text = new System.Windows.Forms.TextBox();
            this.lbl_header = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // btn_ok
            // 
            this.btn_ok.Location = new System.Drawing.Point(368, 195);
            this.btn_ok.Name = "btn_ok";
            this.btn_ok.Size = new System.Drawing.Size(75, 23);
            this.btn_ok.TabIndex = 0;
            this.btn_ok.Text = "btn_ok";
            this.btn_ok.UseVisualStyleBackColor = true;
            this.btn_ok.Click += new System.EventHandler(this.btn_ok_Click);
            // 
            // tb_text
            // 
            this.tb_text.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.tb_text.CausesValidation = false;
            this.tb_text.Location = new System.Drawing.Point(12, 47);
            this.tb_text.Multiline = true;
            this.tb_text.Name = "tb_text";
            this.tb_text.ReadOnly = true;
            this.tb_text.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.tb_text.Size = new System.Drawing.Size(431, 142);
            this.tb_text.TabIndex = 1;
            // 
            // lbl_header
            // 
            this.lbl_header.AutoSize = true;
            this.lbl_header.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_header.Location = new System.Drawing.Point(12, 9);
            this.lbl_header.Name = "lbl_header";
            this.lbl_header.Size = new System.Drawing.Size(99, 24);
            this.lbl_header.TabIndex = 2;
            this.lbl_header.Text = "lbl_header";
            // 
            // Frm_TextPopup
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLight;
            this.CausesValidation = false;
            this.ClientSize = new System.Drawing.Size(455, 228);
            this.ControlBox = false;
            this.Controls.Add(this.lbl_header);
            this.Controls.Add(this.tb_text);
            this.Controls.Add(this.btn_ok);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Frm_TextPopup";
            this.ShowInTaskbar = false;
            this.Text = "Frm_TextPopup";
            this.TopMost = true;
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_ok;
        private System.Windows.Forms.TextBox tb_text;
        private System.Windows.Forms.Label lbl_header;
    }
}