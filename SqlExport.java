/**
 * @brief sql export
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 */ 

package albums;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yannis
 */
public class SqlExport {
            
    public final String EXPORT_FILENAME = "albums.sql";
    public String command = null;

    public SqlExport() {
        Database db = new Database();
        Config cfg = new Config();
        if (cfg.ReadConfig()) {
            command = cfg.get_mysql_exec() + " --user=" + db.get_database_username() + " --password=" + db.get_database_password() + " " + db.get_database_name() + " " + db.get_table() + " -r " + EXPORT_FILENAME;
        }
    }
    
    /**
     * @brief export to sql format
     * @return 
     */
    public Boolean CreateSqlExport() {
        
        try {            
            Process process = Runtime.getRuntime().exec(command);
            int processComplete = process.waitFor();    
            if (processComplete == 0) {
                return true;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SqlExport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}



