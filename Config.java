/**
 * @brief creation of 'properties' file
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 */ 

package albums;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 *
 * @author yannis
 */

public class Config {
    
    private final String CONFIG_FILENAME = "dbconfig.properties";
        
    Properties DBConfig;
    InputStream ConfigFile = null;
    FileOutputStream NewConfigFile = null;
   
    public Config()  {
        DBConfig = new Properties();
    }
    
    /**
     * @return read success or failure if file not exists
     * @brief open dbconfig.properties
     */
    public Boolean ReadConfig() {
        try {            
            ConfigFile = new FileInputStream(CONFIG_FILENAME);
        } catch (FileNotFoundException ex) {            
            return false;
        }
        try {
            DBConfig.load(ConfigFile);            
        } catch (IOException ex) {            
            return false;
        }
        return true;
    }
    
    /**
     * @return success or failure
     * @brief create dbconfig.properties
     * @param db_url
     * @param db_name
     * @param db_username
     * @param db_password 
     */
    public Boolean CreateConfig(String db_url, String db_name, String db_username, String db_password) {
        try {
            NewConfigFile = new FileOutputStream(CONFIG_FILENAME);

            String line_1 = "database_url = " + db_url + "\r\n";
            String line_2 = "database_name = " + db_name + "\r\n";
            String line_3 = "database_username = " + db_username + "\r\n";
            String line_4 = "database_password = "  + db_password;
            String file_contents = line_1 + line_2 + line_3 + line_4;
            byte[] byte_content = file_contents.getBytes();

            NewConfigFile.write(byte_content);
            NewConfigFile.close();
        } catch (IOException ex) {
            return false;
        }
        return true;        
    }            
    
    public String get_database_url() {
        return DBConfig.getProperty("database_url");
    }
    
    public String get_database_name() {
        return DBConfig.getProperty("database_name");
    }
    
    public String get_database_username() {
        return DBConfig.getProperty("database_username");
    }
    
    public String get_database_password() {
        return DBConfig.getProperty("database_password");
    }
        
}