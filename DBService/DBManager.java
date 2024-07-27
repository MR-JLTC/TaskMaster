package taskmaster.DBService;
import java.sql.*;
import taskmaster.Model.DAOUtilities.RESULT;
import static taskmaster.Model.DAOUtilities.RESULT.*;
/**
 *
 * @author MR_JLTC
 */
public class DBManager {
    private Connection conn;
    
    protected DBManager(){}
    
    protected RESULT makeDBConn(){
        try {
            conn = DriverManager.getConnection(PROPQS.URL.getQuery());
            return SUCCESSFUL;
        } catch (SQLException ex){
            return FAILED;
        }
        
    }
    
    protected Connection getConn(){
        return this.conn;
    }
    
    protected RESULT createReqProps(){
        return switch(createBucketList_Table()){
            case SUCCESSFUL -> createTaskInfo_Table()==SUCCESSFUL ? createTaskInfo_Viewer() : FAILED;
            case FAILED -> FAILED;
            default -> NAV;
        };
    }
    
    private RESULT createBucketList_Table(){
        try {
            PreparedStatement pps = conn.prepareStatement(PROPQS.CREATE_TABLE_BUCKETLIST_INFO.getQuery());
            pps.execute();
            return SUCCESSFUL;
        } catch (SQLException ex) {
            return FAILED;
        }
    }
    
    private RESULT createTaskInfo_Table(){
        try {
            PreparedStatement pps = conn.prepareStatement(PROPQS.CREATE_TABLE_TASKINFO.getQuery());
            pps.execute();
            return SUCCESSFUL;
        } catch (SQLException ex) {
            return FAILED;
        }
    }
    
    private RESULT createTaskInfo_Viewer(){
        try {
            PreparedStatement pps = conn.prepareStatement(PROPQS.CREATE_VIEW_TASKINFO.getQuery());
            pps.execute();
            return SUCCESSFUL;
        } catch (SQLException ex) {
            return FAILED;
        }
    }
    
    protected RESULT closeConn(){
        try {
            conn.close();
            return SUCCESSFUL;
        } catch (SQLException ex) {
            return FAILED;
        }
    }
}
