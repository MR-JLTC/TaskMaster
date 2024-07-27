package taskmaster;

import taskmaster.Controller.ExecutionManager;
import taskmaster.Model.DAOManager;
import taskmaster.View.ConsoleView;
import taskmaster.View.UIType;

/**
 *
 * @author MR_JLTC
 */
public class Integrator{
    public static void main(String[] args) {
        UIType ui = new ConsoleView();
        DAOManager dao_mgr = new DAOManager();
        ExecutionManager exec_manager = new ExecutionManager(dao_mgr, ui);
        exec_manager.start();
    }
}
