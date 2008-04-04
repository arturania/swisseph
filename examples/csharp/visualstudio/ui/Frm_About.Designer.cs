namespace radixpro.ui {
    partial class Frm_About {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Frm_About));
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            this.logoPictureBox = new System.Windows.Forms.PictureBox();
            this.lbl_product = new System.Windows.Forms.Label();
            this.lbl_version = new System.Windows.Forms.Label();
            this.lbl_copyright = new System.Windows.Forms.Label();
            this.lbl_webaddress = new System.Windows.Forms.Label();
            this.tb_description = new System.Windows.Forms.TextBox();
            this.btn_ok = new System.Windows.Forms.Button();
            this.tableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.logoPictureBox)).BeginInit();
            this.SuspendLayout();
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.ColumnCount = 2;
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 33F));
            this.tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 67F));
            this.tableLayoutPanel.Controls.Add(this.logoPictureBox, 0, 0);
            this.tableLayoutPanel.Controls.Add(this.lbl_product, 1, 0);
            this.tableLayoutPanel.Controls.Add(this.lbl_version, 1, 1);
            this.tableLayoutPanel.Controls.Add(this.lbl_copyright, 1, 2);
            this.tableLayoutPanel.Controls.Add(this.lbl_webaddress, 1, 3);
            this.tableLayoutPanel.Controls.Add(this.tb_description, 1, 4);
            this.tableLayoutPanel.Controls.Add(this.btn_ok, 1, 5);
            this.tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel.Location = new System.Drawing.Point(9, 9);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.RowCount = 6;
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40.75472F));
            this.tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel.Size = new System.Drawing.Size(417, 265);
            this.tableLayoutPanel.TabIndex = 0;
            // 
            // logoPictureBox
            // 
            this.logoPictureBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.logoPictureBox.Image = global::radixpro.Properties.Resources.globe;
            this.logoPictureBox.InitialImage = null;
            this.logoPictureBox.Location = new System.Drawing.Point(3, 3);
            this.logoPictureBox.Name = "logoPictureBox";
            this.tableLayoutPanel.SetRowSpan(this.logoPictureBox, 6);
            this.logoPictureBox.Size = new System.Drawing.Size(131, 259);
            this.logoPictureBox.TabIndex = 12;
            this.logoPictureBox.TabStop = false;
            // 
            // lbl_product
            // 
            this.lbl_product.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbl_product.Location = new System.Drawing.Point(143, 0);
            this.lbl_product.Margin = new System.Windows.Forms.Padding(6, 0, 3, 0);
            this.lbl_product.MaximumSize = new System.Drawing.Size(0, 17);
            this.lbl_product.Name = "lbl_product";
            this.lbl_product.Size = new System.Drawing.Size(271, 17);
            this.lbl_product.TabIndex = 19;
            this.lbl_product.Text = "lbl_product";
            this.lbl_product.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lbl_version
            // 
            this.lbl_version.BackColor = System.Drawing.Color.Transparent;
            this.lbl_version.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbl_version.Location = new System.Drawing.Point(143, 26);
            this.lbl_version.Margin = new System.Windows.Forms.Padding(6, 0, 3, 0);
            this.lbl_version.MaximumSize = new System.Drawing.Size(0, 17);
            this.lbl_version.Name = "lbl_version";
            this.lbl_version.Size = new System.Drawing.Size(271, 17);
            this.lbl_version.TabIndex = 0;
            this.lbl_version.Text = "lbl_version";
            this.lbl_version.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lbl_copyright
            // 
            this.lbl_copyright.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbl_copyright.Location = new System.Drawing.Point(143, 52);
            this.lbl_copyright.Margin = new System.Windows.Forms.Padding(6, 0, 3, 0);
            this.lbl_copyright.MaximumSize = new System.Drawing.Size(0, 17);
            this.lbl_copyright.Name = "lbl_copyright";
            this.lbl_copyright.Size = new System.Drawing.Size(271, 17);
            this.lbl_copyright.TabIndex = 21;
            this.lbl_copyright.Text = "lbl_copyright";
            this.lbl_copyright.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lbl_webaddress
            // 
            this.lbl_webaddress.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbl_webaddress.Location = new System.Drawing.Point(143, 78);
            this.lbl_webaddress.Margin = new System.Windows.Forms.Padding(6, 0, 3, 0);
            this.lbl_webaddress.MaximumSize = new System.Drawing.Size(0, 17);
            this.lbl_webaddress.Name = "lbl_webaddress";
            this.lbl_webaddress.Size = new System.Drawing.Size(271, 17);
            this.lbl_webaddress.TabIndex = 22;
            this.lbl_webaddress.Text = "lbl_webaddress";
            this.lbl_webaddress.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // tb_description
            // 
            this.tb_description.BackColor = System.Drawing.Color.NavajoWhite;
            this.tb_description.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tb_description.Location = new System.Drawing.Point(143, 107);
            this.tb_description.Margin = new System.Windows.Forms.Padding(6, 3, 3, 3);
            this.tb_description.Multiline = true;
            this.tb_description.Name = "tb_description";
            this.tb_description.ReadOnly = true;
            this.tb_description.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.tb_description.Size = new System.Drawing.Size(271, 101);
            this.tb_description.TabIndex = 23;
            this.tb_description.TabStop = false;
            this.tb_description.Text = "tb_description";
            // 
            // btn_ok
            // 
            this.btn_ok.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btn_ok.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.btn_ok.Location = new System.Drawing.Point(339, 239);
            this.btn_ok.Name = "btn_ok";
            this.btn_ok.Size = new System.Drawing.Size(75, 23);
            this.btn_ok.TabIndex = 24;
            this.btn_ok.Text = "btn_ok";
            this.btn_ok.Click += new System.EventHandler(this.btn_ok_Click);
            // 
            // Frm_About
            // 
            this.AcceptButton = this.btn_ok;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.NavajoWhite;
            this.ClientSize = new System.Drawing.Size(435, 283);
            this.Controls.Add(this.tableLayoutPanel);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Frm_About";
            this.Padding = new System.Windows.Forms.Padding(9);
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Frm_About";
            this.TopMost = true;
            this.tableLayoutPanel.ResumeLayout(false);
            this.tableLayoutPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.logoPictureBox)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
        private System.Windows.Forms.PictureBox logoPictureBox;
        private System.Windows.Forms.Label lbl_product;
        private System.Windows.Forms.Label lbl_version;
        private System.Windows.Forms.Label lbl_copyright;
        private System.Windows.Forms.Label lbl_webaddress;
        private System.Windows.Forms.TextBox tb_description;
        private System.Windows.Forms.Button btn_ok;
    }
}
