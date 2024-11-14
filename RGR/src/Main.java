import SQLError.DataBaseException;
import controller.Controller;

public class Main {
    public static void main(String[] args) {

        try {
            Controller controller = new Controller();
            boolean endFlag = false;
            while (!endFlag) {
                endFlag = controller.startMenu();
            }
        }catch(DataBaseException e){
            System.err.println("Fatal Error! Program is failed!");
        }
    }
}