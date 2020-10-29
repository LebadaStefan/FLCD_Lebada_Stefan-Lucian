

import com.sun.tools.javac.util.Pair;
import model.SymbolTable;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(17);
        Pair<Integer, Integer> a = symbolTable.add("a");
        System.out.println("Hashvalue: "+ a.fst +" is at position: "+ a.snd);
        Pair<Integer, Integer> b = symbolTable.add("b");
        System.out.println("Hashvalue: "+ b.fst +" is at position: "+ b.snd);
        Pair<Integer, Integer> c = symbolTable.add("c");
        System.out.println("Hashvalue: "+ c.fst +" is at position: "+ c.snd);
        Pair<Integer, Integer> c2 = symbolTable.add("c");
        System.out.println("Hashvalue: "+ c2.fst +" is at position: "+ c2.snd);
        Pair<Integer, Integer> c3 = symbolTable.add("c");
        System.out.println("Hashvalue: "+ c3.fst +" is at position: "+ c3.snd);
        Pair<Integer, Integer> c4 = symbolTable.add("c");
        System.out.println("Hashvalue: "+ c4.fst +" is at position: "+ c4.snd);



    }
}
