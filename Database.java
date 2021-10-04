/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 * @brief Class that handles Database connection / queries
 **/

package albums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
     
    // database table
    private static final String DATABASE_TABLE = "albums";    
    // database field names
    private static final String FLD_ID = "id";
    private static final String FLD_TITLE = "title";
    private static final String FLD_ARTIST_NAME = "artist_name";
    private static final String FLD_SORT_NAME = "sort_name";
    private static final String FLD_FORMAT = "format";
    private static final String FLD_YEAR = "year";
    private static final String FLD_LABEL = "label";
    private static final String FLD_COMMENT = "comment";
    
    private String database_url;
    private String database_name;
    private String database_username;
    private String database_password;
    
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement pstatement = null;
    private ResultSet result = null;     
        
    /**
     * @brief connect to existing database
     */
    public Database() {
                                
        Config db_config = new Config();        
        if (db_config.ReadConfig()) {
            try { // connect to database            
                database_url = db_config.get_database_url();            
                database_name = db_config.get_database_name();
                database_username = db_config.get_database_username();            
                database_password = db_config.get_database_password();
                connection = DriverManager.getConnection(database_url, database_username, database_password);
            }
             catch (SQLException ex) {             
                 System.out.println("Database is not working or wrong username/password!");
                 System.exit(0);
            }
        } else {
            System.out.println("Database config file not found! "
                    + "If this is the first time you are running application then click ok to begin installation");
        }
    }
    
    /**
     * @brief connect to database
     * @param db_connection_url     
     */
    public Database(String db_connection_url) {
                    
        try {
            connection = DriverManager.getConnection(db_connection_url);
        }
        catch (SQLException ex) {
            System.out.println("Database is not working or wrong username/password!!");
            System.exit(0);
       }
        
    }
 
    /**
     * @brief database query
     * @param query
     * @return 
     */
    public ResultSet DatabaseQuery(String query) {
        
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create statement", e);
        }
        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot query: " + query, e);
        }        
        return result;
    }
    
    /**
     * @brief query for inserting album
     * @param Title
     * @param Artist
     * @param Format
     * @param Year
     * @param Label
     * @param Comments
     * @param SortName
     * @return 
     */
    public boolean InsertAlbum(String Title, String Artist, String Format, Integer Year, String Label, String Comments, String SortName) {
        try {
            String query = "INSERT INTO albums (" + get_fld_title() + "," 
                                                  + get_fld_artist() + "," 
                                                  + get_fld_format() + "," 
                                                  + get_fld_year() + "," 
                                                  + get_fld_label() + "," 
                                                  + get_fld_comment() + ","
                                                  + get_fld_artist_sort_name() + ")"
                                                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, Title);
            pstatement.setString(2, Artist);
            pstatement.setString(3, Format);
            pstatement.setInt(4, Year);
            pstatement.setString(5, Label);
            pstatement.setString(6, Comments);
            pstatement.setString(7, SortName);
            
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }        
        
        return true;
    }
    
    
    /**
     * @brief search query
     * @param Artist
     * @return 
     */
    public ResultSet SearchAlbum(String Artist) {
        try {
            String query = "SELECT " + get_fld_id() + ","
                                     + get_fld_title() + "," 
                                     + get_fld_format() + "," 
                                     + get_fld_year() + "," 
                                     + get_fld_label() +
                                " FROM " + get_table() +
                              " WHERE " + get_fld_artist() + "=?"
                            + " ORDER BY " + get_fld_year() + " DESC";            
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, Artist);                        
            
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            result = pstatement.executeQuery();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        return result;
    }
    
    
    /**
     * @brief count distinct artist
     * @param Artist
     * @return 
     */
    public ResultSet SearchArtist(String Artist) {        
        try {
            String query = "SELECT artist_name FROM " + get_table() + " "
                             + "WHERE " + get_fld_artist() + 
                            " LIKE ? GROUP BY " + get_fld_artist();                             
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, "%" + Artist + "%");
            
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            result = pstatement.executeQuery();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        return result;
    }
    
    /**
     * @brief delete album
     * @param Album_ID
     * @return 
     */
    public boolean DeleteAlbum(Integer Album_ID) {        
        try {
            String query = "DELETE FROM " + get_table() + " WHERE " + get_fld_id() + "=?";                                                 
            pstatement = connection.prepareStatement(query);
            pstatement.setInt(1, Album_ID);
                        
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        Close();        
        return true;
    }
    
    
    /**
     * @brief delete artist
     * @param ArtistName
     * @return 
     */
    public boolean DeleteArtist(String ArtistName) {
        try {
            String query = "DELETE FROM " + get_table() + " WHERE " + get_fld_artist() + "=?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, ArtistName);
                        
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        Close();        
        return true;
    }
    
    /**
     * @brief update album
     * @param Album_ID
     * @param Title
     * @param Format
     * @param Year
     * @return 
     */
    public boolean UpdateAlbum(Integer Album_ID, String Title, String Format, String Year, String Label) {
        try {
            String query = "UPDATE " + get_table() + 
                                " SET " + get_fld_title() + "=?, " 
                                + get_fld_format() + "=?, "
                                + get_fld_year() + "=?, "
                                + get_fld_label() + "=? "
                            + " WHERE " + get_fld_id() + "=?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, Title);
            pstatement.setString(2, Format);
            pstatement.setString(3, Year);
            pstatement.setString(4, Label);
            pstatement.setInt(5, Album_ID);
                        
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }        
        return true;
    }
    
    
    /**
     * @brief update artist name
     * @param OldArtist
     * @param NewArtist
     * @return 
     */
    public boolean UpdateArtist(String OldArtist, String NewArtist) {
        try {
            String query = "UPDATE " + get_table() + 
                                " SET " + get_fld_artist() + "=?"
                            + " WHERE " + get_fld_artist() + "=?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, NewArtist);
            pstatement.setString(2, OldArtist);            
                        
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        return true;
    }
    
    /**
     * @brief update artist sort name
     */     
    public boolean UpdateArtistSortName(String Artist, String ArtistSortName) {
        try {
            String query = "UPDATE " + get_table() + 
                                " SET " + get_fld_artist_sort_name() + "=?"
                            + " WHERE " + get_fld_artist() + "=?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, ArtistSortName);
            pstatement.setString(2, Artist);
                        
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();        
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        return true;
    }
    
               
    /**
     * @brief count all albums and grouping them by format
     * @return 
     */
    public ResultSet CountAlbum() {
    
        String query = "SELECT " + get_fld_format() + ", COUNT(" + get_fld_format() + ") AS cnt "
                        + "FROM " + get_table() + " GROUP BY " + get_fld_format()  + " WITH ROLLUP";
        return DatabaseQuery(query);
    }
    
    /**
     * @brief count all albums and return results in string
     * @return 
     * @throws SQLException 
     */
    public String getFormattedCountAlbum() throws SQLException {
        
        String album_text = System.lineSeparator();
        String temp_text;
        
        ResultSet data = CountAlbum();
        while (data.next()) {
            if (data.getString(get_fld_format()) != null) {
                temp_text = data.getString(get_fld_format());
            } else {
                temp_text = System.lineSeparator() + "Total";
            }
            temp_text += ": ";
            temp_text += data.getString("cnt") + System.lineSeparator();            
            album_text += temp_text;
        }
        return album_text;
    }
    
    
    
    /**
     * @brief return search results
     * @param SearchName
     * @return
     * @throws SQLException 
     */
    public Integer NumberOfAlbums(String SearchName) throws SQLException {
        
        ResultSet res = CountAlbumByArtist(SearchName);
        res.next();
        return res.getInt("cnt");
    }
    
    /**
     * @brief return search results
     * @param SearchArtistName
     * @return
     * @throws SQLException 
     */
    public Integer NumberOfArtists(String SearchArtistName) throws SQLException {
        
        ResultSet res = SearchArtist(SearchArtistName);
        Integer countrows = 0;
        while (res.next()) {
            countrows++;
        }        
        return countrows;
    }
                                   
    /**
     * @brief fetch all artist
     * @return 
     */
    public ResultSet FetchArtist() {
        String query = "SELECT DISTINCT " + get_fld_artist() +                                  
                            " FROM " + get_table() +
                            " ORDER BY " + get_fld_artist_sort_name();
        return DatabaseQuery(query);
        
    }
            
    /**
     * @brief create database
     * @param db_name
     */
    public void CreateDatabase(String db_name) {
        
        String cdb_query = "CREATE DATABASE " + db_name;
        try {            
            pstatement = connection.prepareStatement(cdb_query);            
        }
         catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
    }
    
    /**
     * @brief use database
     * @param db_name
     */
    public void UseDatabase(String db_name) {
        
        String usedb_query = "USE " + db_name;
        try {            
            pstatement = connection.prepareStatement(usedb_query);            
        }
         catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
    }

    /**
     * @brief create database table
     */
    public void CreateTable() {
        String ctbl_query = "CREATE TABLE " + get_table() + "(" +
                                get_fld_id() + " INT(11) UNSIGNED NOT NULL AUTO_INCREMENT," +
                                get_fld_title() + " VARCHAR(255) DEFAULT ''," +
                                get_fld_artist() + " VARCHAR(255) DEFAULT NULL," +
                                get_fld_format() + " ENUM('CD','CDs','CDR','MC','LP','2LP','S','EP','2CD','2MC') DEFAULT NULL," +
                                get_fld_year() + " INT(11) DEFAULT NULL," +
                                get_fld_label() + " VARCHAR(255) DEFAULT NULL," +
                                get_fld_comment() + " TEXT," +
                                get_fld_artist_sort_name() + " VARCHAR(255) DEFAULT NULL, " +
                                    "PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";        
        
        try {            
            pstatement = connection.prepareStatement(ctbl_query);
        }
         catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            pstatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
    }
    
    /**
     * @brief close database connection
     */    
    public void Close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Unable to close database!");
        }
    }
        
    public String get_database_name() {
        return database_name;
    }
    
    public String get_database_username() {
        return database_username;        
    }
    
    public String get_database_password() {
        return database_password;        
    }
    
    public String get_table() {
        return DATABASE_TABLE;
    }
    
    public String get_fld_id() {
        return FLD_ID;
    }
    
    public String get_fld_title() {
        return FLD_TITLE;
    }
    
    public String get_fld_artist() {
        return FLD_ARTIST_NAME;
    }
    
    public String get_fld_artist_sort_name() {
        return FLD_SORT_NAME;
    }
    
    public String get_fld_format() {
        return FLD_FORMAT;
    }
    
    public String get_fld_year() {
        return FLD_YEAR;
    }
        
    public String get_fld_label() {
        return FLD_LABEL;
    }
    
    public String get_fld_comment() {
        return FLD_COMMENT;
    }                    
    
    /**
     * @brief count artist albums 
     * @param Artist
     * @return 
     */
    private ResultSet CountAlbumByArtist(String Artist) {
        try {
            String query = "SELECT COUNT(*) AS cnt FROM " + get_table() + " "
                             + "WHERE " + get_fld_artist() + "=?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, Artist);
            
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create query", e);
        }
        try {
            result = pstatement.executeQuery();
        } catch (SQLException e) {
            throw new IllegalStateException("Query Error! ", e);
        }
        return result;
    }                
}