package taskmaster.Model;

import java.sql.*;
import java.util.ArrayList;
import taskmaster.DBService.DBManager;
import taskmaster.Model.DAOUtilities.QUERY;
import taskmaster.Model.DAOUtilities.RESULT;
import static taskmaster.Model.DAOUtilities.RESULT.*;

/**
 *
 * @author MR_JLTC
 */
public sealed class DAOProcessor extends DBManager permits DAOManager{
    protected DAOProcessor(){}
    
    protected RESULT addTask(String bucketlist, String task){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.INSERT_TASKINFO.getQuery())){
            pps.setInt(1, getBucketListID(bucketlist));
            pps.setString(2, task);
            return pps.executeUpdate()!=0 ? SUCCESSFULLY_ADDED : FAILED_TO_ADD;
        } catch (SQLException  | IndexOutOfBoundsException ex) {
            return FAILED_TO_ADD;
        }
    }
    
    protected RESULT addBucketlist(String task){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.INSERT_BUCKETLIST_INFO.getQuery())){
            pps.setString(1, task);
            return pps.executeUpdate()!=0 ? SUCCESSFULLY_ADDED : FAILED_TO_ADD;
        } catch (SQLException  | IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            return FAILED_TO_ADD;
        }
    }
    
    protected RESULT updateTask(String bl, int index, String utask){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.UPDATE_TASKINFO.getQuery())){
            pps.setString(1, utask);
            pps.setInt(2, getTaskID(bl, getTask(bl).get(index-1)));
            return pps.executeUpdate()!=0 ? SUCCESSFULLY_UPDATED : FAILED_TO_UPDATE;
        } catch (SQLException  | IndexOutOfBoundsException ex) {
            return FAILED_TO_UPDATE;
        }
    }
    
    protected RESULT updateBucketList(int index, String ubl){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.UPDATE_BUCKETLIST_INFO.getQuery())){
            pps.setString(1, ubl);
            pps.setInt(2, getBucketListID(getBucketList().get(index-1)));
            return pps.executeUpdate()!=0 ? SUCCESSFULLY_UPDATED : FAILED_TO_UPDATE;
        } catch (SQLException | IndexOutOfBoundsException ex) {
            return FAILED_TO_UPDATE;
        }
    }
    
    protected RESULT deleteTaskInfo(String bl, int index){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.DELETE_TASKINFO.getQuery())){
            pps.setInt(1, getTaskID(bl, getTask(bl).get(index-1)));
            pps.executeUpdate();
            return SUCCESSFULLY_DELETED;
        } catch (SQLException ex) {
            return FAILED_TO_DELETE;
        }
    }
    
    //directly deleting the taskinfo using its bucketlist
    protected RESULT deleteTaskInfo(int indexOfBucketList ){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.DELETE_TASKINFO_DIRECTLY_FROM_BUCKETLIST.getQuery())){
            pps.setInt(1, getBucketListID(getBucketList().get(indexOfBucketList-1)));
            return (pps.executeUpdate()!=0) ? SUCCESSFULLY_DELETED : FAILED_TO_DELETE;
        } catch (Exception ex) {
            return FAILED_TO_DELETE;
        }
    }
    
    protected RESULT deleteBucketListInfo(int index){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.DELETE_BUCKETLIST_INFO.getQuery())){
            pps.setInt(1, getBucketListID(getBucketList().get(index-1)));
            int i = pps.executeUpdate();
            return SUCCESSFULLY_DELETED;
        } catch (SQLException | IndexOutOfBoundsException ex) {
            return FAILED_TO_DELETE;
        }
    }
    
    protected ArrayList<String> getTask(String bucketlist){
        ArrayList<String> task = new ArrayList<>();
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.FETCH_TASKINFO_FROM_VIEWER.getQuery())){
            pps.setString(1, bucketlist);
            ResultSet rs = pps.executeQuery();
            while(rs.next()){
                task.add(rs.getString("TASK"));
            }
        } catch (SQLException ex) {}
        return task;
    }
    
    protected ArrayList<String> getBucketList(){
        ArrayList<String> bucket = new ArrayList<>();
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.FETCH_BUCKETLIST_INFO.getQuery());
             ResultSet rs = pps.executeQuery()){
             while(rs.next()){
                bucket.add(rs.getString("Name"));
             }
        } catch (SQLException ex) {}
        return bucket;
    }
    
    protected String searchBucketList(int index){
        String n = getBucketList().get(index-1);
        String bln="";
         try (PreparedStatement pps = getConn().prepareStatement(QUERY.SEARCH_BUCKETLIST_INFO.getQuery())){
            pps.setString(1, n);
            ResultSet rs = pps.executeQuery();
            while(rs.next()){
                bln=rs.getString("Name");
            }
        } catch (SQLException ex) {}
         return bln.isBlank() || n.isBlank() ? "NOT FOUND" : bln;
    }
    
    private int getBucketListID(String n){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.FETCH_BUCKETLIST_ID.getQuery())){
            pps.setString(1, n);
            ResultSet rs = pps.executeQuery();
            int id=0;
            while(rs.next()){
                id=rs.getInt("BID");
            }
            return id;
        } catch (SQLException ex) {
            return 0;
        }
    }
    
    private int getTaskID(String bucketlist, String task){
        try (PreparedStatement pps = getConn().prepareStatement(QUERY.FETCH_TASKINFO_ID.getQuery())){
            pps.setString(1, task);
            pps.setString(2, bucketlist);
            ResultSet rs = pps.executeQuery();
            int id=0;
            while(rs.next()){
                id=rs.getInt("TID");
            }
            return id;
        } catch (SQLException ex) {
            return 0;
        }
    }
}
