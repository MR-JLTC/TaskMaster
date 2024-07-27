/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package taskmaster.Controller.CMDAnalyzerUtilities;

/**
 *
 * @author hunter
 */
public enum CMD_TYPE{
    ADD_TASK,
    ADD_BUCKETLIST,
    UPDATE_TASK_INFO,
    UPDATE_BUCKETLIST_INFO,
    DELETE_TASK_INFO,
    DELETE_BUCKETLIST_INFO,
    FETCH_TASK_INFO,
    FETCH_BUCKETLIST_INFO,
    INVALID,
    MISSING_ARGUMENTS,
    CMD_OUTOFRANGE,
    USE_BUCKETLIST_CMD,
    CLOSE_FORCEFULLY,
    HELP_CMD,
    EXIT_CMD;
}
