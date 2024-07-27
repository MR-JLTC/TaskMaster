package taskmaster.View.Utilities;
/**
 *
 * @author MR_JLTC
 */
public enum SYS_HEADER {
    HEADER(CONSOLE_COLOR.WHITE.getColor()+"|->> "+CONSOLE_COLOR.GREEN.getColor()+"TASK_MASTER"+CONSOLE_COLOR.YELLOW.getColor()
          +"\n      v1.7-alpha "+CONSOLE_COLOR.WHITE.getColor());
        
    private final String h;
    
    SYS_HEADER(String s){
        this.h = s;
    }
    
    public String getHeader(){
        return this.h;
    }
}
