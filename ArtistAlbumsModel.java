/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 **/

package albums;

import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yannis
 */
public class ArtistAlbumsModel extends AbstractTableModel {            
        
        private final String[] TableHeader;
        private final Object[][] data;
        private final Object[][] TableData;
        HashMap<Integer, Integer> album_id = new HashMap<>();        
        
        public ArtistAlbumsModel(Integer num) {
            super();            
            TableHeader = new String[] {"Album", "Format", "Year", "Label"};
            data = new Object[num][4];
            TableData = new Object[num][4];
        }        
                                
        @Override
        public int getColumnCount() {
            return TableHeader.length;
        }
        
        @Override
        public String getColumnName(int column) {
            return TableHeader[column];
        }
        
        @Override
        public int getRowCount() {                        
            return data.length;
        }
        
        @Override
        public Object getValueAt(int row, int column) {            
            return data[row][column];
        }
        
        @Override
        public void setValueAt(Object value, int row, int column) {            
            data[row][column] = value;
            TableData[row][column] = value;            
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }
        
        public void setAlbumID(Integer row, Integer AlbumID) {
            album_id.put(row, AlbumID);
        }
        
        public Integer getAlbumID(Integer row) {
            return album_id.get(row);
        }

        public String getTableDataAt(int row, int column) {
            return (String) TableData[row][column];
        }
    }       
