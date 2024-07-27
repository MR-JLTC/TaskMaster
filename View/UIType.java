/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package taskmaster.View;

import java.util.ArrayList;
import taskmaster.Controller.CMDAnalyzerUtilities.IDENTIFIER;
import taskmaster.Model.DAOUtilities.RESULT;
import taskmaster.View.Utilities.OUTPUT_TYPE;

/**
 *
 * @author hunter
 */
public interface UIType {
    String Message(String m);
    void clearScreen();
    String getInput();
    String getInput(String bl);
    String getInputFHC();
    void printAppreciation();
    void print(String s, OUTPUT_TYPE type);
    void printBucketListInTable(ArrayList<String> bls);
    void printCMDs(IDENTIFIER id);
    void printHeader();
    void printResult();
    void printTaskInTable(ArrayList<String> tasks);
    void setResult(RESULT r, boolean errorType);
}
