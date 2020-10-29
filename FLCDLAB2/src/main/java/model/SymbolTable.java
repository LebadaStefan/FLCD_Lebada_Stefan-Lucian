package model;

import com.sun.tools.javac.util.Pair;

public class SymbolTable {
    private MyHT hashTable;

    public SymbolTable(Integer m){
        hashTable = new MyHT(m);
    }

    public Pair<Integer, Integer> add(String ident){
        return hashTable.addElement(ident);
    }

    public Pair<Integer, Integer> search(String ident){
        return hashTable.search(ident);
    }

    public Integer size(){return hashTable.getSize();}
}
