package model;


import com.sun.tools.javac.util.Pair;

public class MyHT {

    private Integer number; // preferably a prime number
    private Node[] elements;
    public Node[] getElements(){return elements;}

    public MyHT(Integer number){
        this.number = number;
        this.elements = new Node[number];
    }

    private Integer hashFunction(String ident){
        Integer sum = 0;
        for (int i = 0; i <ident.length(); i++){
            sum += ident.charAt(i);
        }
        return sum % this.number;
    }

    public Pair<Integer, Integer> addElement(String ident){
        Integer hv = this.hashFunction(ident);
        if (this.elements[hv] == null){
            Node node = new Node(ident, 0 , null);
            this.elements[hv] = node;
            return new Pair<>(hv, 0);

        }
        Node current = this.elements[hv];
        while (current.nextNode != null){
            current = current.nextNode;
        }
        Node newNode = new Node(ident, current.index+1, current);
        current.nextNode = newNode;
        return new Pair<>(hv,newNode.index);
    }

    public Pair<Integer, Integer> search(String ident){
        Integer hv = this.hashFunction(ident);
        Node current = this.elements[hv];
        if (current != null){
            while (current != null){
                if (current.identifier.equals(ident)) {
                    return new Pair<>(hv, current.index);
                }
                current = current.nextNode;
            }
        }
        return new Pair<>(-1 ,-1);
    }

    public Integer getSize(){
        return this.number;
    }
}


