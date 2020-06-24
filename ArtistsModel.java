/**
 * @author Yannis Exidaridis <jexi@noc.uoa.gr>
 **/

package albums;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yannis
 */
public class ArtistsModel extends AbstractTableModel {
    
        private final String[] TableHeader;
        private final Object[][] TableData;
        private final String[][] OldData;

        public ArtistsModel(Integer num) {
            super();            
            TableHeader = new String[] {"Artist"};            
            TableData = new Object[num][1];
            OldData = new String[num][1];
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
            return TableData.length;
        }
        
        @Override
        public Object getValueAt(int row, int column) {            
            return TableData[row][column];
        }
        
        @Override
        public void setValueAt(Object value, int row, int column) {                        
            TableData[row][column] = value;            
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }
        
        public void setOldValueAt(Object value, int row, int column) {
            OldData[row][column] = value.toString();
        }            
        
        public String getOldValueAt(int row, int column) {
            return OldData[row][column];
        }
    
}
