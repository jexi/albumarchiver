/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 * @brief display artists form
 **/

package albums;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author yannis
 */
public class DisplayArtistsForm  extends javax.swing.JFrame {
    
    private JButton jButtonBack;
    private JButton jButtonDelete;
    private JButton jButtonUpdate;    
    private JTable jTableData;
    private JScrollPane TableScrollPane;
    private Container container;               
    private ArtistsModel TableModel;
    private final Database db;
    private String ArtistName;
    
    public DisplayArtistsForm(String SearchName, Integer NumberOfArtists) {
        super();
        db = new Database();        
        ResultDataForm(SearchName, NumberOfArtists);        
    }
    
    private void ResultDataForm(String SearchName, Integer NumberOfArtists) {
                
        setResizable(false);
        // add events
        jButtonBack = new JButton("Back");        
        jButtonBack.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButtonBackActionPerformed(evt);
        });
        jButtonDelete = new JButton("Delete");
        jButtonDelete.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButtonDeleteActionPerformer(evt);
        });
        jButtonUpdate = new JButton("Update");
        jButtonUpdate.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButtonUpdateActionPerformed(evt);
        });        
        
        // count artists
        ResultSet rs = db.SearchArtist(SearchName);
        TableModel = new ArtistsModel(NumberOfArtists);
        try {
            while (rs.next()) {
                Integer RowNumber = rs.getRow()-1;
                TableModel.setValueAt(rs.getString(db.get_fld_artist()), RowNumber, 0);
                TableModel.setOldValueAt(rs.getString(db.get_fld_artist()), RowNumber, 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayArtistsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
        // table creation
        jTableData = new JTable(TableModel);
        jTableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableData.getColumnModel().getColumn(0).setPreferredWidth(300);
        // table row selection
        jTableData.setCellSelectionEnabled(true);
        // table row selection
            jTableData.setCellSelectionEnabled(true);
            ListSelectionModel cellSelectionModel = jTableData.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {                                                
                    int selectedRow = jTableData.getSelectedRow();
                    for (int i = 0; i < TableModel.getColumnCount(); i++) {                            
                        ArtistName = (String) TableModel.getValueAt(selectedRow, i);
                    }
                }
            });
        
        
        jTableData.setPreferredScrollableViewportSize(new java.awt.Dimension(300, 200));
        TableScrollPane = new JScrollPane(jTableData);
        container = getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        // add everything to container        
        container.add(TableScrollPane);
        container.add(jButtonBack);
        container.add(jButtonDelete);
        container.add(jButtonUpdate);        
        // draw form
        setTitle("Artist(s)");
        setLocation(new java.awt.Point(300, 200));
        setSize(new java.awt.Dimension(400, 350));
    }
    
    /**
     * @brief update action event
     * @param evt 
     */
    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {
                        
        for (int i = 0; i < TableModel.getRowCount(); i++) {            
            db.UpdateArtist(TableModel.getOldValueAt(i, 0), (String) TableModel.getValueAt(i, 0));
        }
        db.Close();
        JOptionPane.showMessageDialog(null, "Update was successful", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * @brief delete action event
     * @param evt 
     */
    private void jButtonDeleteActionPerformer(java.awt.event.ActionEvent evt) {
        
        if (ArtistName == null) {
            JOptionPane.showMessageDialog(null, "Artist was not selected", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            if (db.DeleteArtist(ArtistName)) {
                JOptionPane.showMessageDialog(null, "Artist was deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        }
    }
        
    /**
     * @brief back action event
     * @param evt 
     */
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {                                
        dispose();
    }
    
}
