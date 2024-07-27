package taskmaster.Controller;

import java.util.NoSuchElementException;
import static taskmaster.Controller.CMDAnalyzerUtilities.CMD_TYPE.*;
import taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER;
import static taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER.*;
import taskmaster.Model.DAOManager;
import taskmaster.Model.DAOUtilities.RESULT;
import static taskmaster.Model.DAOUtilities.RESULT.FORCED_EXIT;
import static taskmaster.Model.DAOUtilities.RESULT.INVALID_ARGS;
import static taskmaster.Model.DAOUtilities.RESULT.INVALID_CMD;
import static taskmaster.Model.DAOUtilities.RESULT.MISSING_ARGS;
import static taskmaster.Model.DAOUtilities.RESULT.NOT_FOUND;
import static taskmaster.Model.DAOUtilities.RESULT.NO_CMD_DETECTED;
import static taskmaster.Model.DAOUtilities.RESULT.NaN;
import static taskmaster.Model.DAOUtilities.RESULT.OUT_OF_RANGE;
import static taskmaster.Model.DAOUtilities.RESULT.SUCCESSFULLY_ADDED;
import static taskmaster.Model.DAOUtilities.RESULT.SUCCESSFULLY_DELETED;
import static taskmaster.Model.DAOUtilities.RESULT.SUCCESSFULLY_UPDATED;
import static taskmaster.Model.DAOUtilities.RESULT.THREAD_ERROR;
import static taskmaster.Model.DAOUtilities.RESULT.WAIT;
import taskmaster.Model.DAOUtilities.TYPE;
import taskmaster.View.UIType;
import static taskmaster.View.Utilities.OUTPUT_TYPE.DOTS;
/**
 *
 * @author MR_JLTC
 */
public final class ExecutionManager extends CMD_Analyzer{
    private final DAOManager dao_manager;
    private final UIType console;
    
    public ExecutionManager(DAOManager dao_manager, UIType cli_viewer){
        this.dao_manager = dao_manager;
        this.console = cli_viewer;
    }
    
    public void start(){
        BucketList();
    }
    
    private void BucketList(){
        while(true){
            try {
                console.clearScreen();
                console.printHeader();
                console.printBucketListInTable(dao_manager.fetchInfo(TYPE.FETCH_BUCKETLIST_INFO, null));
                try{
                    RESULT rs;
                    String cmd = console.getInput();
                    switch(cmd_type(BUCKETLIST, cmd)){
                        case ADD_BUCKETLIST ->{
                            rs = dao_manager.add(TYPE.ADD_BUCKETLIST, cmd.split(" ")[2]);
                            console.setResult(rs, (rs != SUCCESSFULLY_ADDED));
                        }
                        case UPDATE_BUCKETLIST_INFO ->{
                            rs = dao_manager.update(TYPE.UPDATE_BUCKETLIST_INFO, cmd.split(" ")[2],cmd.split(" ")[3]);
                            console.setResult(rs, (rs != SUCCESSFULLY_UPDATED));
                        }
                        case DELETE_BUCKETLIST_INFO -> {
                            rs = dao_manager.deleteInfo(TYPE.DELETE_BUCKETLIST_INFO, cmd.split(" ")[2]);
                            console.setResult(rs, (rs != SUCCESSFULLY_DELETED));
                        }
                        case USE_BUCKETLIST_CMD -> {
                             if(cmd.split(" ").length>2 || cmd.split(" ").length==1){
                                 console.setResult((cmd.split(" ").length==1 ? MISSING_ARGS : OUT_OF_RANGE), true);
                             }else{
                                String bucketlistName = dao_manager.searchBucketListName(cmd.split(" ")[1]);
                                 switch (bucketlistName) {
                                     case "NOT FOUND" -> 
                                         console.setResult(NOT_FOUND,false);
                                     case "NaN" ->
                                         console.setResult(NaN, true);
                                     default ->
                                         BucketList_Tasks(bucketlistName);
                                 }
                             }
                        }
                        case MISSING_ARGUMENTS -> console.setResult(MISSING_ARGS, true);
                        case CMD_OUTOFRANGE -> console.setResult(OUT_OF_RANGE, true);
                        case EXIT_CMD -> appreciationPage();
                        case CLOSE_FORCEFULLY -> System.exit(0);
                        case HELP_CMD -> HelpCenter(BUCKETLIST,null);
                        default -> console.setResult(cmd.isBlank() ? NO_CMD_DETECTED : INVALID_CMD, true);
                    }
                    console.printResult();
                }catch(ArrayIndexOutOfBoundsException ex){console.setResult(MISSING_ARGS, true);}  
                Thread.sleep(1000);
            } catch (InterruptedException ex) {console.setResult(THREAD_ERROR, true);}
        }
    }
    
    private void BucketList_Tasks(String bucketlistName){
        while(true){
            try {
                RESULT rs;
                console.clearScreen();
                console.printHeader();
                console.printTaskInTable(dao_manager.fetchInfo(TYPE.FETCH_TASK_INFO, bucketlistName));
                String cmd = console.getInput(bucketlistName);
                switch(cmd_type(TASK, cmd)){
                    case ADD_TASK -> {
                        rs = dao_manager.add(TYPE.ADD_TASK, bucketlistName, cmd.split(" ")[2]);
                        console.setResult(rs, (rs != SUCCESSFULLY_ADDED));
                    }
                    case UPDATE_TASK_INFO -> {
                        rs = dao_manager.update(TYPE.UPDATE_TASK_INFO, bucketlistName, cmd.split(" ")[2], cmd.split(" ")[3]);
                        console.setResult(rs, (rs != SUCCESSFULLY_UPDATED));
                    }
                    case DELETE_TASK_INFO -> {
                        rs = dao_manager.deleteInfo(TYPE.DELETE_TASK_INFO, bucketlistName, cmd.split(" ")[2]);
                        console.setResult(rs, (rs != SUCCESSFULLY_DELETED));
                    }
                    case CMD_OUTOFRANGE -> console.setResult(OUT_OF_RANGE, true);
                    case EXIT_CMD -> BucketList();
                    case MISSING_ARGUMENTS -> console.setResult(MISSING_ARGS, true);
                    case CLOSE_FORCEFULLY -> BucketList();
                    case HELP_CMD -> HelpCenter(TASK, bucketlistName);
                    default ->  console.setResult(cmd.isBlank() ? NO_CMD_DETECTED : INVALID_CMD, true);
                }
                console.printResult();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {console.setResult(THREAD_ERROR, true);}
        }
    }
    
    private void HelpCenter(IDENTIFIER t, String bl){
        while(true){
            try {
                console.clearScreen();
                console.printHeader();
                switch(t){
                    case BUCKETLIST -> console.printCMDs(BUCKETLIST);
                    case TASK -> console.printCMDs(TASK);
                    default -> console.setResult(INVALID_ARGS, true);
                }
                
                try{
                    String cmd = console.getInputFHC();
                    switch(cmd){
                        case "q" -> {
                            if(t==BUCKETLIST) start();
                            else BucketList_Tasks(bl);
                        }
                        default -> console.setResult(cmd.isBlank() ? NO_CMD_DETECTED : INVALID_CMD, true);
                    }
                    console.printResult();
                    Thread.sleep(1000);
                }catch(NoSuchElementException nse){console.setResult(FORCED_EXIT, false);} 
            } catch (InterruptedException ex) {console.setResult(THREAD_ERROR, true);}
        }
     }
    
    private void appreciationPage(){
        try{
            console.clearScreen();
            console.printHeader();
            console.printAppreciation();
            console.setResult(WAIT, false);
            console.printResult();
            console.print(".", DOTS);
            console.clearScreen();
        }catch(NoClassDefFoundError ex){}
        System.exit(0);
    }
}
