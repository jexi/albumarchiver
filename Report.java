/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 * @brief pdf output.
 * Uses iText library (https://itextpdf.com)
 */ 

package albums;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.font.PdfEncodings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report {
    
    private final Document document;
    private final PdfDocument pdf;
   
    // pdf filename
    private static final String FILENAME = "albums.pdf";    
    // font size
    private static final float ARTIST_FONTSIZE = 10f;
    private static final float ALBUM_FONTSIZE = 9f;
    private static final String TITLE = "Κατάλογος";
    
    public Report() throws FileNotFoundException {
        
        File file = new File(FILENAME);        
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(fos);
        pdf = new PdfDocument(writer);
        document = new Document(pdf);
        
    }            
    
    /**
     * @brief create report in PDF format
     * @throws IOException 
     */
    public void CreateReport() throws IOException {
                
        Integer[] report_data = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Integer report_data_cnt = 0;
                
        try (document) {
            String ListEntry, text, pdf_font = null;
            Config cfg = new Config();            
            
            if (cfg.ReadConfig()) { // read font file
                pdf_font = cfg.get_pdf_font();
            }
            
            PdfFont font = PdfFontFactory.createFont(pdf_font, PdfEncodings.IDENTITY_H);
            document.add(new Paragraph(TITLE).setFont(font).setFontSize(14f));
            try {
                Database db = new Database();
                ResultSet rs = db.FetchArtist();
                while (rs.next()) {
                    Text ArtistName = new Text(rs.getString(db.get_fld_artist())); // artist name
                    ArtistName.setUnderline(1, -3);
                    document.add(new Paragraph(ArtistName)).setFont(font).setFontSize(ARTIST_FONTSIZE);
                    ResultSet res = db.SearchAlbum(rs.getString(db.get_fld_artist()));
                    while (res.next()) {
                        List list = new List();
                        list.setFont(font);
                        list.setFontSize(ALBUM_FONTSIZE);
                        list.setSymbolIndent(8f);
                        ListEntry = res.getString(db.get_fld_title()); // artist album
                        ListEntry += " (" + res.getString(db.get_fld_format()); // album format
                        
                        if (res.getString(db.get_fld_year()) != null && !res.getString(db.get_fld_year()).isEmpty()) {
                            ListEntry += " - " + res.getString(db.get_fld_year());      // year
                            if (res.getString(db.get_fld_label()) != null && !res.getString(db.get_fld_label()).isEmpty()) {
                                ListEntry += " @ " + res.getString(db.get_fld_label()); // label
                            }
                        }
                        ListEntry += ")";
                        String AlbumComment = res.getString(db.get_fld_comment());  // album comment
                        if (AlbumComment != null && !AlbumComment.isEmpty()) {
                            // TO DO: display compatible unicode character !!
                            ListEntry += "  " + Character.toString((char)0x1F449) + AlbumComment;
                        }
                        list.setSymbolIndent(20f);
                        list.add(new ListItem(ListEntry));                        
                        document.add(list);
                    }
                }
                                                                
                
                // statistics                                
                                
                // count total
                ResultSet data_all = db.CountTotalAlbum();                
                while (data_all.next()) {                    
                    report_data[report_data_cnt] = data_all.getInt("cnt");
                    report_data_cnt++;
                }
                // count cds
                ResultSet data_cd = db.CountCD();                
                while (data_cd.next()) {                    
                    report_data[report_data_cnt] = data_cd.getInt("cnt");
                    report_data_cnt++;
                }
                
                // count lps
                ResultSet data_lp = db.CountLP();                
                while (data_lp.next()) {                                        
                    report_data[report_data_cnt] = data_lp.getInt("cnt");
                    report_data_cnt++;
                }
                // count mc
                ResultSet data_mc = db.CountMC();
                while (data_mc.next()) {                                        
                    report_data[report_data_cnt] = data_mc.getInt("cnt");
                    report_data_cnt++;
                }
                                    
                text = FormatReportToText(
                                           report_data[0], 
                                           report_data[5], 
                                           report_data[2], 
                                           report_data[3], 
                                           report_data[4],
                                           report_data[9],
                                           report_data[7],
                                           report_data[8],
                                           report_data[12],
                                           report_data[11]
                                        );
                                                
                // date stamp
                String TimeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("d / M / uuuu"));
                text += "(τελευταία ενημέρωση στις: " + TimeStamp + ")";
                document.add(new Paragraph(new Text(text)));
                
            }   catch (SQLException ex) {
                Logger.getLogger(DisplayArtistAlbumsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        }
    }
    
    
    /**
     * @brief format report     
     * @param total
     * @param cd
     * @param cd_2
     * @param cds
     * @param cdr
     * @param lp
     * @param lp_2
     * @param lps     
     * @param mc
     * @param mc_2
     * @return 
     */
    private String FormatReportToText(Integer total, 
                                    Integer cd, Integer cd_2, Integer cds, Integer cdr, 
                                     Integer lp, Integer lp_2, Integer lps, 
                                     Integer mc, Integer mc_2) {
            
        String out = "";
        out += "\n\nΥπάρχουν συνολικά " + total +" άλμπουμς. Αναλυτικά:\n\n";        
        out += cd + " CD (" + cd_2 + " διπλά, " + cds  + " CD singles, " + cdr + " CDR)\n";
        out += lp + " δίσκοι (" + lp_2 + " διπλοί, " + lps + " singles)\n";
        out += mc + " κασσέτες (" + mc_2 + " διπλές)\n\n";
        
        return out;
    }
    
}
