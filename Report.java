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
    private static final String TITLE = "Albums List";
    
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
                        list.setSymbolIndent(20f);
                        list.add(new ListItem(ListEntry));
                        document.add(list);
                    }
                }
                
                // statistics
                ResultSet data = db.CountAlbum();
                while (data.next()) {
                    if (data.getString(db.get_fld_format()) != null) {
                        text = data.getString(db.get_fld_format());
                    } else {
                        text = "Total";
                    }
                    text += ":  ";
                    text += data.getString("cnt");
                    document.add(new Paragraph(new Text(text)));
                }
                // date stamp
                String TimeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("d MMM uuuu"));                
                text = "(updated at: " + TimeStamp + ")";
                document.add(new Paragraph(new Text(text)));
            }   catch (SQLException ex) {
                Logger.getLogger(DisplayArtistAlbumsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }            
}
