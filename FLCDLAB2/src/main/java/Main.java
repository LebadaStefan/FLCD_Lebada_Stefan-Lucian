

import com.sun.tools.javac.util.Pair;
import model.MyScanner;
import model.SymbolTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter program location: ");
            String filePath = reader.readLine();
            MyScanner scanner = new MyScanner(filePath);
            scanner.scan();
        } catch (Exception e) {
            System.out.println("File path incorect");
            System.out.println("Other remarks");
            System.out.println(e.getMessage());
        }
    }
}
