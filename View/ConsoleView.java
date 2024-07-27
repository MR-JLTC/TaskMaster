package taskmaster.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER;
import taskmaster.Model.DAOUtilities.RESULT;
import static taskmaster.Model.DAOUtilities.RESULT.ALREADY_EXIST;
import static taskmaster.Model.DAOUtilities.RESULT.INVALID_ARGS;
import static taskmaster.Model.DAOUtilities.RESULT.INVALID_CMD;
import static taskmaster.Model.DAOUtilities.RESULT.MISSING_ARGS;
import static taskmaster.Model.DAOUtilities.RESULT.NAV;
import static taskmaster.Model.DAOUtilities.RESULT.NOT_FOUND;
import static taskmaster.Model.DAOUtilities.RESULT.NO_CMD_DETECTED;
import static taskmaster.Model.DAOUtilities.RESULT.NaN;
import static taskmaster.Model.DAOUtilities.RESULT.OUT_OF_RANGE;
import static taskmaster.Model.DAOUtilities.RESULT.WAIT;
import taskmaster.Utilities.ConsoleTableProperties.ConsoleTable;
import taskmaster.Utilities.ConsoleTableProperties.Styles;
import taskmaster.View.Utilities.CONSOLE_COLOR;
import taskmaster.View.Utilities.OUTPUT_TYPE;
import taskmaster.View.Utilities.SYS_HEADER;
/**
 *
 * @author MR_JLTC
 */
public class ConsoleView implements UIType {
    private ConsoleTable table;
    
    @Override
    public void printHeader(){
        System.out.println(SYS_HEADER.HEADER.getHeader());
    }
    
    @Override
    public void printTaskInTable(ArrayList<String> tasks){
        if(tasks.isEmpty()){
            table = new ConsoleTable();
            table.addRow("(No task at the moment)");
        }else{
            table = new ConsoleTable("'NO.", "-TASK");
            int i=0;
            for(String task : tasks){
                table.addRow(String.valueOf(++i),task);
            }
        }
        System.out.println(table.withStyle(Styles.SCIENTIFIC));
        System.out.println(CONSOLE_COLOR.GRAY.getColor()+"To View Commands type [h]\n"+CONSOLE_COLOR.WHITE.getColor());
    }
    
    @Override
    public void printBucketListInTable(ArrayList<String> bls){
        if(bls.isEmpty()){
            table = new ConsoleTable();
            table.addRow("(No bucketlist at the moment)");
        }else{
            table = new ConsoleTable("'NO.", "-BUCKETLIST");
             int i=0;
            for(String bl : bls){
                table.addRow(String.valueOf(++i),bl);
            }
        }
        System.out.println(table.withStyle(Styles.SCIENTIFIC));
        System.out.println(CONSOLE_COLOR.GRAY.getColor()+"To View Commands type [h]\n"+CONSOLE_COLOR.WHITE.getColor());
    }
    
    private RESULT result=NAV;
    private boolean errorType=false;
    
    @Override
    public void setResult(RESULT r, boolean erT){
        errorType=erT;
        this.result = r;
    }
    
    @Override
    public void printResult(){
        try{
            if(errorType) printError(result==NaN             ? "Argument Required for <index_no> is NaN" :
                                     result==ALREADY_EXIST   ? "BucketList name already exist"           :
                                     result==INVALID_CMD     ? "Invalid Command"                         :
                                     result==NO_CMD_DETECTED ? "No Command Detected"                     :
                                     result==OUT_OF_RANGE     ? "Arguments are out of range"             :
                                     result==NOT_FOUND       ? "Not Found"                               :
                                     result==INVALID_ARGS    ? "Invalid Arguments"                       : 
                                     result==WAIT            ? " Please Wait " : 
                                     result==MISSING_ARGS    ? "Missing Arguments" : result.toString());
            else printInfo(result==NaN             ? "Argument Required for <index_no> is    NaN" :
                           result==ALREADY_EXIST   ? "BucketList name already exist"           :
                           result==INVALID_CMD     ? "Invalid Command"                         :
                           result==NO_CMD_DETECTED ? "No Command Detected"                     :
                           result==OUT_OF_RANGE    ? "Arguments are out of range"              :
                           result==NOT_FOUND       ? "Not Found"                               :
                           result==INVALID_ARGS    ? "Invalid Arguments"                       : 
                           result==WAIT            ? " Please Wait " : 
                           result==MISSING_ARGS    ? "Missing Arguments" : result.toString());
            errorType=false;
        }catch(NullPointerException ex){}
    }
    
    @Override
    public void printCMDs(IDENTIFIER id){
        switch(id){
            case BUCKETLIST -> {
                table = new ConsoleTable("-CMD","-DESCRIPTION");
                table.addRow("a -blif <bucketlist_name>", "to add bucketlist info");
                table.addRow("u -blif <index_no> <updated_data>", "to update bucketlist info");
                table.addRow("d -blif <index_no>", "to delete bucketlist info");
                table.addRow("use <index_no>", "to use the recorded bucketlist");
                table.addRow("q", "to quit from the system");
                System.out.println(table.withStyle(Styles.DOUBLE_BORDER));
            }
            case TASK -> {
                table = new ConsoleTable("-CMD","-DESCRIPTION");
                table.addRow("a -tif <task>", "to add task info");
                table.addRow("u -tif <index_no> <updated_data>", "to update task info");
                table.addRow("d -tif <index_no>", "to delete task info");
                table.addRow("q", "to go back to main menu");
                System.out.println(table.withStyle(Styles.DOUBLE_BORDER));
            }
            default -> printError("INVALID ARGUMENTS");
        }
        System.out.println(CONSOLE_COLOR.GRAY.getColor()+"Type [q] to go back\n"+CONSOLE_COLOR.WHITE.getColor());
    }
    
    @Override
    public String getInput(){
        try{
            System.out.print("[Main]> ");
            return new Scanner(System.in).nextLine();
        }catch(NoSuchElementException nse){
            printInfo("\nForcefully Exited\n");
            return "closing";
        }
    }
    
    @Override
    public String getInput(String bl){
        try{
            System.out.print("["+bl+"]> ");
            return new Scanner(System.in).nextLine();
        }catch(NoSuchElementException nse){
            printInfo("\nForcefully Exited\n");
            return "closing";
        }    
    }
    
    @Override
    public String getInputFHC(){
        try{
            System.out.print("[HelpCenter]> ");
            return new Scanner(System.in).nextLine();
        }catch(NoSuchElementException nse){
            printInfo("\nForcefully Exited\n");
            return "closing";
        }
    }
    
    @Override
    public void print(String s, OUTPUT_TYPE type){
        switch(type){
            case DOTS -> printDots(s);
            case TEXT -> System.out.println(s);
        }
    }
    
    private void printDots(String c){
        for(int i=0;i<3;i++){
            try {
                System.out.print(c);
                Thread.sleep(700);
            }catch (InterruptedException ex) {printError("THREADING ERROR: "+ex.getMessage());}
        }
    }
    
    private void printError(String em){
        System.out.print(CONSOLE_COLOR.RED.getColor()+em+CONSOLE_COLOR.WHITE.getColor());
    }
    
    private void printInfo(String m){
        System.out.print(CONSOLE_COLOR.BLUE.getColor()+m+CONSOLE_COLOR.WHITE.getColor());
    }
    
    @Override
    public String Message(String m){
        return CONSOLE_COLOR.BLUE.getColor()+m+CONSOLE_COLOR.WHITE.getColor();
    }
    
    @Override
    public void clearScreen(){
        try{
            if(System.getProperty("os.name").contains("Windows")){
              new ProcessBuilder("cmd","/c", "cls").inheritIO().start().waitFor();
            }else{
              new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }catch(IOException | InterruptedException e){}
    }

    @Override
    public void printAppreciation() {
        table = new ConsoleTable();
        table.addRow("THANKS FOR USING MY SYSTEM");
        table.addRow("       From: Mr_JLTC       ");
        System.out.print(table.withStyle(Styles.DOUBLE_BORDER));
    }
}
