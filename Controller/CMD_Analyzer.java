/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taskmaster.Controller;

import java.util.HashMap;
import taskmaster.Controller.CMDAnalyzerUtilities.CMD_TYPE;
import static taskmaster.Controller.CMDAnalyzerUtilities.CMD_TYPE.*;
import taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER;
import static taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER.*;


/**
 *
 * @author hunter
 */
public sealed class CMD_Analyzer permits ExecutionManager{
    private HashMap<IDENTIFIER,String> map_cmd;
    private final int CMD_MAX_ARGS_RANGE = 4;
    private final int AD_CMD_ARGS_RANGE = 3;
    
    protected CMD_Analyzer(){}
    private void initMapCMD(){
       map_cmd = new HashMap<>();
       map_cmd.put(ADD, "a");
       map_cmd.put(UPDATE, "u"); 
       map_cmd.put(DELETE, "d");
       map_cmd.put(BUCKETLIST, "-blif");
       map_cmd.put(TASK, "-tif");
       map_cmd.put(HELP, "h");
       map_cmd.put(USE, "use");
       map_cmd.put(EXIT, "q");
    }
    
    protected CMD_TYPE cmd_type(IDENTIFIER id, String c){
        initMapCMD();
        return (c.split(" ").length>CMD_MAX_ARGS_RANGE) ?  CMD_OUTOFRANGE   : cmd_TypeIdentifier(c.split(" "));
    }
    
    private CMD_TYPE cmd_TypeIdentifier(String[] cmd){
        return cmd[0].equals("closing")           ?  CLOSE_FORCEFULLY              :
               cmd[0].equals(map_cmd.get(HELP))   ?  HELP_CMD                      : 
               cmd[0].equals(map_cmd.get(USE))    ?  USE_BUCKETLIST_CMD            :
               cmd[0].equals(map_cmd.get(EXIT))   ?  EXIT_CMD                      :
               cmd[0].equals(map_cmd.get(ADD))    ?  (cmd.length> AD_CMD_ARGS_RANGE  ? CMD_OUTOFRANGE : (cmd.length==1 || cmd.length!=3) ? MISSING_ARGUMENTS : cmd_reqchecker(ADD,cmd[1]))    :
               cmd[0].equals(map_cmd.get(UPDATE)) ?  (cmd.length> CMD_MAX_ARGS_RANGE ? CMD_OUTOFRANGE : (cmd.length==1 || cmd.length!=4 ? MISSING_ARGUMENTS : cmd_reqchecker(UPDATE,cmd[1]))) :
               cmd[0].equals(map_cmd.get(DELETE)) ?  (cmd.length> AD_CMD_ARGS_RANGE  ? CMD_OUTOFRANGE : (cmd.length==1 || cmd.length!=3 ? MISSING_ARGUMENTS : cmd_reqchecker(DELETE,cmd[1]))) : 
               INVALID;
    }
    
    private CMD_TYPE cmd_reqchecker(IDENTIFIER type, String cmd){
        return switch(type){
            case ADD    -> cmd.equals(map_cmd.get(BUCKETLIST)) ? ADD_BUCKETLIST         : 
                           cmd.equals(map_cmd.get(TASK))       ? ADD_TASK               : INVALID;
            case UPDATE -> cmd.equals(map_cmd.get(BUCKETLIST)) ? UPDATE_BUCKETLIST_INFO : 
                           cmd.equals(map_cmd.get(TASK))       ? UPDATE_TASK_INFO       : INVALID;
            case DELETE -> cmd.equals(map_cmd.get(BUCKETLIST)) ? DELETE_BUCKETLIST_INFO : 
                           cmd.equals(map_cmd.get(TASK))       ? DELETE_TASK_INFO       : INVALID;
            default -> INVALID;
        };
    }
}
