/**
 * @brief html output to file albums.html
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 */

package albums;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HtmlExport {
    
    private final String HTML_FILENAME = "albums.html";
    private final String CSS_FILENAME = "albums.css";    
    private String htmlArtistAlbum;    
    private String html, css;
    private String html_body, html_head;
    
    public HtmlExport() {
        html = html_body = html_head = htmlArtistAlbum = css = "";
    }
           
    /**
     * @brief export to html format     
     * @throws IOException 
     */
    public void CreateExport() throws IOException {        
        OutputToFile(CreateCSS(), CSS_FILENAME);
        OutputToFile(CreateHtml(), HTML_FILENAME);
    }
    
    /**
     * @brief create html output
     * @return html formatted string
     */
    public String CreateHtml() {
        Integer[] report_data = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Integer report_data_cnt = 0;
        
        // <head> ..html_code </head>
        html_head = htmlHead(); 
        // <body> .. html_code </body>
        String title = htmlTagWithStyle("span", "ibu", "Albums");
        html_body = htmlTagWithStyle("p", "centeredbreak", title);        
        try {
            Database db = new Database();
            ResultSet rs = db.FetchArtist();
            while (rs.next()) {
                String htmlArtistName = rs.getString(db.get_fld_artist()); // artist name
                html_body += htmlTag("h4", htmlArtistName);                
                ResultSet res = db.SearchAlbum(rs.getString(db.get_fld_artist()));
                htmlArtistAlbum = "";
                while (res.next()) {
                    String ArtistAlbum = res.getString(db.get_fld_title()); // artist album                    
                    ArtistAlbum += " (" + res.getString(db.get_fld_format()); // album format
                    if (res.getString(db.get_fld_year()) != null && !res.getString(db.get_fld_year()).isEmpty()) {
                        ArtistAlbum += " - " + res.getString(db.get_fld_year());      // year
                        if (res.getString(db.get_fld_label()) != null && !res.getString(db.get_fld_label()).isEmpty()) {
                            ArtistAlbum += " @ " + res.getString(db.get_fld_label()); // label
                        }
                    }
                    ArtistAlbum += ")";
                    String AlbumComment = res.getString(db.get_fld_comment()); // album comment
                    if (AlbumComment != null && !AlbumComment.isEmpty()) {
                        htmlArtistAlbum += htmlTag("li", ArtistAlbum + "  " + htmlTag("small", AlbumComment));                        
                    } else {
                        htmlArtistAlbum += htmlTag("li", ArtistAlbum);                        
                    }                    
                }
                html_body += htmlTag("ul", htmlArtistAlbum);
            }
            html_body += "<hr>";
            
            // Statistics                                
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


            html_body += FormatReportToHtml(
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
            String html_timestamp = "(τελευταία ενημέρωση στις: " + TimeStamp + ")";
            
            
            html_body += htmlTag("h6", html_timestamp);

            // glue all html pieces together
            html += htmlTag("html", html_head + htmlTag("body", html_body));
            
        }   catch (SQLException ex) {
                Logger.getLogger(DisplayArtistAlbumsForm.class.getName()).log(Level.SEVERE, null, ex);
        }                
        return html;
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
    private String FormatReportToHtml(Integer total, 
                                     Integer cd, Integer cd_2, Integer cds, Integer cdr, 
                                     Integer lp, Integer lp_2, Integer lps, 
                                     Integer mc, Integer mc_2) {
            
        String out = "";
        out += "<br><p>Υπάρχουν συνολικά <strong>" + total +"</strong> άλμπουμς. Αναλυτικά:</p><br><br>";        
        out += "<p><strong>" + cd + "</strong> CD (" + cd_2 + " διπλά, " + cds + " CD singles, " + cdr + " CDR)</p><br>";
        out += "<p><strong>" + lp + "</strong> δίσκοι (" + lp_2 + " διπλοί, " + lps + " singles)</p><br>";
        out += "<p><strong>" + mc + "</strong> κασσέτες (" + mc_2 + " διπλές)</p><br><br>";
        
        return out;
    }
    
    
    /**
     * @brief create css file for using it in html and epub files
     * @return css code string
     * @throws IOException 
     */
    private String CreateCSS() throws IOException {
                
        String line;
        // read css resource file
        try (InputStream is = this.getClass().getResourceAsStream(CSS_FILENAME)) {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            // read each line
            while((line = buffer.readLine()) != null) {
                css = css + line + "\n";
            }
        }
        return css;        
    }
    
    /**
     * @brief output string to file
     * @param content
     * @param filename
     * @throws IOException 
     */
    private void OutputToFile(String content, String filename) throws IOException {                                       
        Files.write(Paths.get(filename), content.getBytes());
    }

    /**
     * @brief create html tag
     * @param tag
     * @param s
     * @return html code
     */
    private String htmlTag(String tag, String s) {
        return "<" + tag + ">" + s + "</" + tag + ">" + "\n";
    }
    
    /**
     * @brief create html tag with given class style name
     * @param tag
     * @param classname
     * @param s
     * @return html code with css
     */
    private String htmlTagWithStyle(String tag, String classname, String s) {
        return "<" + tag + " class='"+ classname + "'>" + s + "</" + tag + ">" + "\n";
    }
    
    /**
     * @brief create html head tag
     * @return html head code
     */
    private String htmlHead() {
        return "<head><link rel=\"stylesheet\" type=\"text/css\" href=\"" + CSS_FILENAME + "\"></head>" + "\n";
    }
}