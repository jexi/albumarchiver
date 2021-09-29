/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albums;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author yannis
 */
public class IntroJDialog extends javax.swing.JDialog {

    /**
     * Creates new form IntroJDialog
     */
    public IntroJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Config config = new Config();
        if (!config.ReadConfig()) {
            Object[] options = { "Install", "Cancel" };
            int choice = JOptionPane.showOptionDialog(null, "Config file not found! Click Install to create Database.", 
                                                "Warning", JOptionPane.YES_NO_OPTION,
                                                JOptionPane.WARNING_MESSAGE,
                                                null,
                                                options, 
                                                options[0]);
            if (choice == JOptionPane.YES_OPTION) {
                new Install().setVisible(true); // install
            } else {
                System.exit(0); // exit
            }            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButtonNew = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jButtonEditArtist = new javax.swing.JButton();
        jButtonExportToPDF = new javax.swing.JButton();
        jButtonStatistics = new javax.swing.JButton();
        jLabelExportTo = new javax.swing.JLabel();
        jButtonExportToSQL = new javax.swing.JButton();
        jButtonExportToHTML = new javax.swing.JButton();
        jButtonExportToEpub = new javax.swing.JButton();
        jLabelAbout = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Album Archiver");

        jButtonNew.setText("Insert");
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonEditArtist.setText("Edit Artist");
        jButtonEditArtist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditArtistActionPerformed(evt);
            }
        });

        jButtonExportToPDF.setText("PDF");
        jButtonExportToPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportToPDFActionPerformed(evt);
            }
        });

        jButtonStatistics.setText("Statistics");
        jButtonStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatisticsActionPerformed(evt);
            }
        });

        jLabelExportTo.setText("Export To ...");

        jButtonExportToSQL.setText("SQL");
        jButtonExportToSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportToSQLActionPerformed(evt);
            }
        });

        jButtonExportToHTML.setText("HTML");
        jButtonExportToHTML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportToHTMLActionPerformed(evt);
            }
        });

        jButtonExportToEpub.setText("ePUB");
        jButtonExportToEpub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportToEpubActionPerformed(evt);
            }
        });

        jLabelAbout.setText("?");
        jLabelAbout.setToolTipText("Version 2.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelExportTo)
                    .addComponent(jButtonNew))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSearch)
                        .addGap(12, 12, 12)
                        .addComponent(jButtonEditArtist)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonStatistics))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonExportToPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonExportToEpub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonExportToHTML, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonExportToSQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(62, 62, 62)
                .addComponent(jLabelAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNew)
                    .addComponent(jButtonSearch)
                    .addComponent(jButtonEditArtist)
                    .addComponent(jButtonStatistics)
                    .addComponent(jLabelAbout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonExportToPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExportToSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExportToHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExportToEpub, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelExportTo))
                .addGap(27, 27, 27))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonEditArtist, jButtonNew, jButtonSearch, jButtonStatistics});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonExportToEpub, jButtonExportToHTML, jButtonExportToPDF, jButtonExportToSQL});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        new InsertForm().setVisible(true);
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        Database db = new Database();
        Integer numOfAlbums = 0;
        String SearchName = JOptionPane.showInputDialog(null, "Enter Artist name", "Search", JOptionPane.INFORMATION_MESSAGE);
        if (SearchName != null) {            
            try {
                numOfAlbums = db.NumberOfAlbums(SearchName);
            } catch (SQLException ex) {
                Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (numOfAlbums == 0) { // no results
                JOptionPane.showMessageDialog(null, "There are no albums", "Search", JOptionPane.WARNING_MESSAGE);
            } else { try {
                // display search results
                new DisplayArtistAlbumsForm(SearchName.trim(), numOfAlbums).setVisible(true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonEditArtistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditArtistActionPerformed
        Database db = new Database();
        Integer numOfArtists = 0;
        String SearchArtistName = JOptionPane.showInputDialog(null, "Enter Artist name", "Search", JOptionPane.INFORMATION_MESSAGE);
        if (SearchArtistName != null) {
            try {
                numOfArtists = db.NumberOfArtists(SearchArtistName);
            } catch (SQLException ex) {
                Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (numOfArtists == 0) { // no results
                JOptionPane.showMessageDialog(null, "There are no artists", "Search", JOptionPane.WARNING_MESSAGE);
            } else { // display search results                
                new DisplayArtistsForm(SearchArtistName.trim(), numOfArtists).setVisible(true);
            }
        }
    }//GEN-LAST:event_jButtonEditArtistActionPerformed

    private void jButtonStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatisticsActionPerformed
                
        Database db = new Database();
        String AlbumSum = "You have";
        try {
            AlbumSum += System.lineSeparator()+db.getFormattedCountAlbum();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, AlbumSum, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonStatisticsActionPerformed

    /**
     * @brief export to pdf
     * @param evt 
     */
    private void jButtonExportToPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportToPDFActionPerformed
        
        Report pdfreport;        
        try {
            pdfreport = new Report();
            try {
                pdfreport.CreateReport(); // report
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "File could not be created!", "Error", JOptionPane.ERROR_MESSAGE);            
                Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
        }        
        JOptionPane.showMessageDialog(null, "Output created in file albums.pdf", "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonExportToPDFActionPerformed

    /**
     * @brief export to sql
     * @param evt 
     */
    private void jButtonExportToSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportToSQLActionPerformed
        SqlExport export;
        export = new SqlExport();
        
        if (export.CreateSqlExport()) {
            JOptionPane.showMessageDialog(null, "Output created in file albums.sql", "Success", JOptionPane.INFORMATION_MESSAGE);                    
        } else {
            JOptionPane.showMessageDialog(null, "File could not be created!", "Error", JOptionPane.WARNING_MESSAGE);                                
        }
    }//GEN-LAST:event_jButtonExportToSQLActionPerformed

    /**
     * @brief export to html
     * @param evt 
     */
    private void jButtonExportToHTMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportToHTMLActionPerformed
        HtmlExport export;
        export = new HtmlExport();
        
        try {
            export.CreateExport();
            JOptionPane.showMessageDialog(null, "Output created in file albums.html", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(AlbumArchiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonExportToHTMLActionPerformed

    /**
     * @brief export to ePUB
     * @param evt 
     */
    private void jButtonExportToEpubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportToEpubActionPerformed
        EpubExport export;
       export = new EpubExport();
       
       if (export.CreateEpubExport()) {
            JOptionPane.showMessageDialog(null, "Output created in file albums.epub", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "File could not be created!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonExportToEpubActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IntroJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IntroJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IntroJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IntroJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IntroJDialog dialog = new IntroJDialog(new javax.swing.JFrame(), false); // modeless dialog
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEditArtist;
    private javax.swing.JButton jButtonExportToEpub;
    private javax.swing.JButton jButtonExportToHTML;
    private javax.swing.JButton jButtonExportToPDF;
    private javax.swing.JButton jButtonExportToSQL;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonStatistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAbout;
    private javax.swing.JLabel jLabelExportTo;
    // End of variables declaration//GEN-END:variables
}