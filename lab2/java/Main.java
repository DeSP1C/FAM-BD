import controller.Controller;

public class Main {
    public static void main(String[] args) {
            Controller controller = new Controller();
            boolean endFlag = false;
            while(!endFlag){
                endFlag = controller.startMenu();
            }
    }
}