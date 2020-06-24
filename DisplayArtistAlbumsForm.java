/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 * @brief display artist albums form
 **/

package albums;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Desktop;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.MalformedURLException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.search.Media;
import be.ceau.itunesapi.response.Result;

/**
 *
 * @author yannis
 */
public class DisplayArtistAlbumsForm extends javax.swing.JFrame {
            
    private static final String WIKIPEDIA_LINK = "http://en.wikipedia.org/wiki/";
    private static final String SUFFIX_LINK = "_discography";
    
    private JButton jButtonBack;
    private JButton jButtonDelete;
    private JButton jButtonUpdate;
    private JButton jButtonWikipedia;
    private JTable jTableData;
    private JScrollPane TableScrollPane;
    private Container container;
    private JLabel AlbumCoverArt;
       
    private Integer AlbumID;             
    private ArtistAlbumsModel TableModel;
    private final Database db;
    private String Link;
    private URL ArtworkURL;    
    
    public DisplayArtistAlbumsForm(String SearchName, Integer NumberOfAlbums) throws MalformedURLException {
        super();
        db = new Database();
        AlbumID = -1;
        ArtworkURL = null;        
        ResultDataForm(SearchName, NumberOfAlbums);
    }
    
    /**
     * @brief draws a table with results
     * @param SearchName
     */
    private void ResultDataForm(String SearchName, Integer NumberOfAlbums) throws MalformedURLException {
        
        // construct artist discography link to wikipedia
        Link = WIKIPEDIA_LINK + SearchName.replace(' ', '_') + SUFFIX_LINK;        
        
        setResizable(false);                
        // add events
        AlbumCoverArt = new JLabel();
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
        jButtonWikipedia = new JButton("Wikipedia");
        jButtonWikipedia.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                jButtonWikipediaActionPerformed(evt);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        });
       
                
        try {            
            // count album            
            ResultSet rs = db.SearchAlbum(SearchName);
            // search for artists albums in iTunes
            Response response = new Search(SearchName).setMedia(Media.MUSIC).execute();                        
            List<Result> iTunesResults = response.getResults();                                                            
            
            TableModel = new ArtistAlbumsModel(NumberOfAlbums);            
            while (rs.next()) {
                Integer RowNumber = rs.getRow()-1;
                Integer RowID = Integer.parseInt(rs.getString(db.get_fld_id()));                    
                TableModel.setAlbumID(RowNumber, RowID);
                TableModel.setValueAt(rs.getString(db.get_fld_title()), RowNumber, 0);                                
                TableModel.setValueAt(rs.getString(db.get_fld_format()), RowNumber, 1);
                TableModel.setValueAt(rs.getString(db.get_fld_year()), RowNumber, 2);
            }            
            // table creation
            jTableData = new JTable(TableModel);
            jTableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTableData.getColumnModel().getColumn(0).setPreferredWidth(340);            
            jTableData.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTableData.getColumnModel().getColumn(2).setPreferredWidth(100);
            AlbumCoverArt.setIcon(new ImageIcon(getClass().getResource("imagemissing.png"))); // display it
            // table row selection            
            jTableData.setCellSelectionEnabled(true);
            ListSelectionModel cellSelectionModel = jTableData.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int selectedRow = jTableData.getSelectedRow();
                    for (int i = 0; i < TableModel.getColumnCount(); i++) {
                        AlbumID = TableModel.getAlbumID(selectedRow);
                        for (Result result : iTunesResults) {
                            // search for album cover art in iTunes
                            if (result.getCollectionName() != null) { // artist found
                                if (result.getCollectionName().toLowerCase().contains(String.valueOf(TableModel.getValueAt(selectedRow, 0)).toLowerCase())) {
                                    try {
                                        if (!result.getArtworkUrl100().isEmpty() && result.getArtworkUrl100() != null) { // artwork found
                                            ArtworkURL = new URL(result.getArtworkUrl100()); // create url album cover art
                                            AlbumCoverArt.setIcon(new ImageIcon(ArtworkURL)); // display it
                                        } else {
                                            AlbumCoverArt.setIcon(new ImageIcon(getClass().getResource("imagemissing.png"))); // display default icon
                                        }
                                    } catch (MalformedURLException ex) {
                                        AlbumCoverArt.setIcon(new ImageIcon(getClass().getResource("imagemissing.png"))); // display default icon
                                    }
                                    break;
                                }
                            } else {
                                AlbumCoverArt.setIcon(new ImageIcon(getClass().getResource("imagemissing.png"))); // display default icon
                            }
                        }
                    }
                }
            });            
            jTableData.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 200));
            TableScrollPane = new JScrollPane(jTableData);            
            container = getContentPane();
            container.setLayout(new FlowLayout(FlowLayout.CENTER));
            // add everything to container        
            container.add(TableScrollPane);
            container.add(AlbumCoverArt);
            container.add(jButtonBack);
            container.add(jButtonDelete);
            container.add(jButtonUpdate);
            container.add(jButtonWikipedia);
            // draw form
            setTitle(FirstUpperCase(SearchName) + " - " + NumberOfAlbums.toString() + " albums");            
            setLocation(new java.awt.Point(300, 200));
            //setSize(new java.awt.Dimension(625, 300));            
            setSize(new java.awt.Dimension(650, 300));            
        } catch (SQLException ex) {
            Logger.getLogger(DisplayArtistAlbumsForm.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }            
    
    
    /**
     * @brief delete action event
     * @param evt 
     */
    private void jButtonDeleteActionPerformer(java.awt.event.ActionEvent evt) {        
        if (AlbumID == -1) {
            JOptionPane.showMessageDialog(null, "Album was not selected", "Error", JOptionPane.WARNING_MESSAGE);
        } else {            
            if (db.DeleteAlbum(AlbumID)) {
                JOptionPane.showMessageDialog(null, "Album deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
            }            
            dispose();
        }        
    }
    
    /**
     * @brief update action event
     * @param evt 
     */
    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {
                        
        for (int i = 0; i < TableModel.getRowCount(); i++) {
            db.UpdateAlbum(TableModel.getAlbumID(i), TableModel.getTableDataAt(i, 0), TableModel.getTableDataAt(i, 1), TableModel.getTableDataAt(i, 2));            
        }
        db.Close();
        JOptionPane.showMessageDialog(null, "Update was successful", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * @brief link to wikipedia action event
     * @param evt 
     */
    private void jButtonWikipediaActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        
        URL url = new URL(Link);
        try {
            Desktop.getDesktop().browse(url.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(DisplayArtistAlbumsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @brief back action event
     * @param evt 
     */
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {                                
        dispose();
    }
    
    
    
    /**
     * @brief convert to upper case the first letter of a string
     * see https://stackoverflow.com/questions/1149855/how-to-upper-case-every-first-letter-of-word-in-a-string#
     * @param s
     * @return 
     */
    private String FirstUpperCase(String s) {
        
        StringBuilder result = new StringBuilder(s.length());
        String words[] = s.split("\\ ");
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
        }
        return result.toString();
    }
    
}
