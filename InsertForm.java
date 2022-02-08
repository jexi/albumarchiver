/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 * 
 */ 

package albums;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.Year;
import javax.swing.JOptionPane;

/**
 *
 * @author yannis
 */
public class InsertForm extends javax.swing.JFrame {

    /**
     * Creates new form InsertForm
     */
    public InsertForm() {
        initComponents();        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jLabelArtist = new javax.swing.JLabel();
        jTextFieldArtist = new javax.swing.JTextField();
        jLabelYear = new javax.swing.JLabel();
        jLabelLabel = new javax.swing.JLabel();
        jTextFieldLabel = new javax.swing.JTextField();
        jLabelComments = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaComments = new javax.swing.JTextArea();
        jButtonInsert = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelFormat = new javax.swing.JLabel();
        jComboBoxFormat = new javax.swing.JComboBox();
        jSpinnerYear = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("New Album");
        setLocation(new java.awt.Point(300, 200));

        jLabelTitle.setText("Title");

        jLabelArtist.setText("Artist");

        jTextFieldArtist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldArtistActionPerformed(evt);
            }
        });

        jLabelYear.setText("Year");

        jLabelLabel.setText("Label");

        jLabelComments.setText("Comments");

        jTextAreaComments.setColumns(20);
        jTextAreaComments.setRows(5);
        jScrollPane1.setViewportView(jTextAreaComments);

        jButtonInsert.setText("Insert");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelFormat.setText("Format");

        jComboBoxFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CD", "2CD", "CDs", "CDR", "LP", "2LP", "EP", "S", "MC", "2MC" }));

        Year currentYear = Year.now();
        jSpinnerYear.setModel(new javax.swing.SpinnerNumberModel(currentYear.getValue(), 1955, 2040, 1));
        jSpinnerYear.setToolTipText("");
        jSpinnerYear.setFocusCycleRoot(true);
        jSpinnerYear.setValue(2022);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle)
                    .addComponent(jLabelArtist)
                    .addComponent(jLabelYear)
                    .addComponent(jLabelLabel)
                    .addComponent(jLabelComments)
                    .addComponent(jLabelFormat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonInsert)
                        .addGap(39, 39, 39)
                        .addComponent(jButtonCancel))
                    .addComponent(jTextFieldTitle)
                    .addComponent(jTextFieldArtist, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jTextFieldLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitle)
                    .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelArtist)
                    .addComponent(jTextFieldArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFormat)
                    .addComponent(jComboBoxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelYear)
                    .addComponent(jSpinnerYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLabel)
                    .addComponent(jTextFieldLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelComments)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInsert)
                    .addComponent(jButtonCancel))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldArtistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldArtistActionPerformed

    }//GEN-LAST:event_jTextFieldArtistActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        
        Integer NumberOfArtist = 0;
        // get input values
        String Title = jTextFieldTitle.getText();
        String Artist = jTextFieldArtist.getText();
        String Format = (String) jComboBoxFormat.getSelectedItem();
        Integer Year = Integer.parseInt(jSpinnerYear.getValue().toString());
        String Label = jTextFieldLabel.getText();
        String Comments = jTextAreaComments.getText();
        String SortArtistName = Artist;
        // insert album in db
        Database db = new Database();
        try {
            NumberOfArtist = db.NumberOfArtists(Artist);
        } catch (SQLException ex) {
            Logger.getLogger(InsertForm.class.getName()).log(Level.SEVERE, null, ex);
        }        
        if (db.InsertAlbum(Title, Artist, Format, Year, Label, Comments, SortArtistName)) {
            JOptionPane.showMessageDialog(null, "Album inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (NumberOfArtist == 0) { // new artist ? 
                SortArtistName = (String) JOptionPane.showInputDialog(null, "Enter (sortable) artist name", "Search", JOptionPane.INFORMATION_MESSAGE, null, null, Artist);
                if (db.UpdateArtistSortName(Artist, SortArtistName)) { // if yes then update it with `sort` name
                    JOptionPane.showMessageDialog(null, "Album updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {                
                ResultSet rs = db.GetArtistSortName(Artist);                
                try {
                    rs.next();
                    SortArtistName = rs.getString(1);                    
                } catch (SQLException ex) {
                    Logger.getLogger(InsertForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                db.UpdateArtistSortName(Artist, SortArtistName);
            }
        }
        dispose();        
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JComboBox jComboBoxFormat;
    private javax.swing.JLabel jLabelArtist;
    private javax.swing.JLabel jLabelComments;
    private javax.swing.JLabel jLabelFormat;
    private javax.swing.JLabel jLabelLabel;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelYear;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerYear;
    private javax.swing.JTextArea jTextAreaComments;
    private javax.swing.JTextField jTextFieldArtist;
    private javax.swing.JTextField jTextFieldLabel;
    private javax.swing.JTextField jTextFieldTitle;
    // End of variables declaration//GEN-END:variables
}
