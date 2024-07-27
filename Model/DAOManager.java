package taskmaster.Model;

import java.util.ArrayList;
import taskmaster.Model.DAOUtilities.RESULT;
import static taskmaster.Model.DAOUtilities.RESULT.*;
import taskmaster.Model.DAOUtilities.TYPE;
import static taskmaster.Model.DAOUtilities.TYPE.ADD_BUCKETLIST;
import static taskmaster.Model.DAOUtilities.TYPE.ADD_TASK;
import static taskmaster.Model.DAOUtilities.TYPE.DELETE_BUCKETLIST_INFO;
import static taskmaster.Model.DAOUtilities.TYPE.DELETE_TASK_INFO;
import static taskmaster.Model.DAOUtilities.TYPE.UPDATE_BUCKETLIST_INFO;
import static taskmaster.Model.DAOUtilities.TYPE.UPDATE_TASK_INFO;

/**
 *
 * @author MR_JLTC
 */
public final class DAOManager extends DAOProcessor{
    
    public DAOManager(){
        makeDBConn();
        createReqProps();
    }
    
    public String searchBucketListName(String index){
        try{
            return searchBucketList(Integer.parseInt(index));
        }catch(NumberFormatException ex){
            return "NaN";
        }catch(IndexOutOfBoundsException iex){
            return "NOT FOUND";
        }
    }
    
    public RESULT add(TYPE t, String...s){
        return switch(t){
            case ADD_TASK -> addTask(s[0],s[1]);
            case ADD_BUCKETLIST -> BucketListNameChecker(s[0]);
            default -> INVALID;
        };
    }
    
    public RESULT update(TYPE t, String... args){
        try{
            return switch(t){
                case UPDATE_TASK_INFO -> updateTask(args[0],Integer.parseInt(args[1]),args[2]);
                case UPDATE_BUCKETLIST_INFO -> updateBucketList(Integer.parseInt(args[0]),args[1]);
                default -> INVALID;
            };
        }catch(NumberFormatException ex){
            return NaN;
        }
    }
    
    public RESULT deleteInfo(TYPE t, String... s){
        try{
            return switch(t){
                case DELETE_TASK_INFO -> deleteTaskInfo(s[0],Integer.parseInt(s[1]));
                case DELETE_BUCKETLIST_INFO -> getTask(getBucketList().get(Integer.parseInt(s[0])-1)).isEmpty() ? 
                    //If the bucketlist has no listed task    
                        deleteBucketListInfo(Integer.parseInt(s[0])) : 
                    //For a bucketlist that has a listed task inside, that wants to be deleted directly
                        DeletionInThread(s);
                default -> NOT_FOUND;
            };
        }catch(NumberFormatException ex){
            return NaN;
        }catch(IndexOutOfBoundsException eb){
            return NOT_FOUND;
        }
    }
    
    public ArrayList<String> fetchInfo(TYPE t, String bl){
         return switch(t){
            case FETCH_TASK_INFO -> getTask(bl);
            case FETCH_BUCKETLIST_INFO -> getBucketList();
            default -> null;
        };
    }
    
    private RESULT DeletionInThread(String...args) throws NumberFormatException{
        return deleteTaskInfo(Integer.parseInt(args[0]))==SUCCESSFULLY_DELETED ? 
               deleteBucketListInfo(Integer.parseInt(args[0])) : FAILED_TO_DELETE;
    }
    
    private RESULT BucketListNameChecker(String n){
        boolean isFound=false;
        for(String name : getBucketList()) if(n.equals(name))isFound=true;
        return isFound ? ALREADY_EXIST : addBucketlist(n);
    }
}
