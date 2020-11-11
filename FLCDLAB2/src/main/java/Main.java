

import FiniteAutomata.FiniteAutomata;
import Ui.FAUi;
import com.sun.tools.javac.util.Pair;
import model.MyScanner;
import model.SymbolTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        System.out.println("1 - Run Scanner");
        System.out.println("2 - Run FiniteAutomata");
        System.out.println("Introduce command here:");
        try {
            command = reader.readLine();
        }
        catch (Exception e){
            System.exit(0);
        }
        if(command.equals("2")){
            lab4();
            System.exit(0);
        }
        try {
            System.out.println("Enter program location: ");
            String filePath = reader.readLine();
            MyScanner scanner = new MyScanner(filePath);
            scanner.scan();
        } catch (Exception e) {
            System.out.println("File path incorrect");
            System.out.println("Other remarks");
            System.out.println(e.getMessage());
        }
    }

    private static void lab4(){
        FiniteAutomata fa = new FiniteAutomata();
        FAUi ui = new FAUi(fa);
        ui.run();
    }
}
