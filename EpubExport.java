/**
 * @brief output to epub format
 * uses epub library (http://www.siegmann.nl/epublib)
 * and css style suitable for Amazon Kindle (http://bbebooksthailand.com/bb-CSS-boilerplate.html) 
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 */

package albums;

import java.io.FileOutputStream;
import java.io.IOException;
 
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
 

public class EpubExport {

    private final String HTML_FILENAME = "albums.html";
    private final String CSS_FILENAME = "albums.css";
    private final String EPUB_FILENAME = "albums.epub";    
    private byte[] content;
    private final HtmlExport export;
    
    public EpubExport() {
        export = new HtmlExport();
    }

    /**
     * @brief create epub output
     * @return 
     */
    public Boolean CreateEpubExport() {         
    
        String html_content = export.CreateHtml();    
        content = html_content.getBytes();

        try {           
            // Create new Book
            Book book = new Book();
            // Set the title            
            book.getMetadata().addTitle("Albums");
            // Add Author
            book.getMetadata().addAuthor(new Author("Γιάννης", "Εξηνταρίδης"));
            // cover page
            book.setCoverImage(new Resource(EpubExport.class.getResourceAsStream("cover.jpg"), "cover.jpg"));
            // Add html content
            book.addSection("Introduction", new Resource(content, HTML_FILENAME));
            // Add css file
            book.getResources().add(new Resource(EpubExport.class.getResourceAsStream(CSS_FILENAME), CSS_FILENAME));
            // Create EpubWriter
            EpubWriter epubWriter = new EpubWriter();
            // Write the Book as Epub
            epubWriter.write(book, new FileOutputStream(EPUB_FILENAME));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
    
