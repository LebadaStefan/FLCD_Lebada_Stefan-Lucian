package model;

import com.sun.tools.javac.util.Pair;
import sun.tools.jstat.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyScanner {

    private static final String TOKENS_PATH = "token.in";
    private List<String> tokens;
    private String givenProgram;
    private SymbolTable symbolTable;
    private List<PIFElement> pif; // Token + hashcode + position in the SymbolTable
    private List<String> errors = new ArrayList<>();

    public MyScanner(String pathToGivenProgram){
        this.tokens = new ArrayList<>();
        this.givenProgram = pathToGivenProgram;
        this.symbolTable = new SymbolTable(29);
        this.pif = new ArrayList<>();
        loadTokens();
    }

    private void loadTokens(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MyScanner.TOKENS_PATH));
            String line = "";
            while (true){
                line = reader.readLine();
                if (line == null || line.equals(""))
                    break;
                tokens.add(line.toString());
            }
            reader.close();
        } catch (FileNotFoundException e){

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private Boolean isConstant(String token) {
         return token.matches("\\-?[1-9]+[0-9]*|0")
                || token.matches("\"[a-zA-Z0-9 _]+\"")
                || token.equals("true")
                || token.equals("false");
    }

    private Boolean isIdentifier(String token){
        return token.matches("(^[a-zA-Z][a-zA-Z0-9]*)");
    }

    public void scan(){
        Integer lineNumber = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.givenProgram));
            String currentLine = "";
            while (true){
                currentLine = reader.readLine();
                if (currentLine == null || currentLine == "")
                    break;
                if (!currentLine.equals("")){
                    List<String> tokensFound = Arrays.asList(currentLine.split(" "));
                    //System.out.println(tokensFound);
                    tokensFound = tokenize(tokensFound);
                    //System.out.println(tokensFound);
                    tokensFound = finalTokenize(tokensFound);
                    System.out.println(tokensFound);
                    for (String token: tokensFound){
                        if(this.tokens.contains(token)){
                            pif.add(new PIFElement(token,0,-1));
                        }else{
                            if (isIdentifier(token) || isConstant(token)){
                                Pair<Integer,Integer> position = symbolTable.search(token);
                                String whatType;
                                if (isIdentifier(token)){
                                    whatType = "ID";
                                }else {
                                    whatType = "Const";
                                }
                                if (position.fst == -1)
                                    position = symbolTable.add(token);
                                pif.add(new PIFElement(whatType,position.fst,position.snd));
                            }else{
                                errors.add("Lexical error: token "+ token + " cannot be identified!");
                            }
                        }
                    }
                }
                currentLine += 1;
            }
            reader.close();
            writeST();
            writePIF();
            if (errors.size() == 0)
                System.out.println("Lexically correct");
            else {
                System.out.println("Lexically incorrect");
                for (String str: errors)
                    System.out.println(str);
            }
            writeLexicalErrors();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<String> tokenize(List<String> lineTokens){
        for (String separator: this.tokens){
            List<String> updatedTokens = new ArrayList<>();
            for (String str: lineTokens) {
                //System.out.println(lineTokens);
                if (str.contains(separator)){
                    List<String> split = mySplitter(str, separator);
                    //System.out.println(split);
                    updatedTokens.addAll(split);
                } else {
                    updatedTokens.add(str);
                }
            }
            lineTokens = updatedTokens;
        }
        List<String> updatedTokens2 = new ArrayList<>();
        for (String e: lineTokens){
            Integer count = 0;
            if (!e.equals("") && count < 2){
                count = 0;
                updatedTokens2.add(e);
            }
            count ++;
        }
        lineTokens= updatedTokens2;
        return lineTokens;
    }

    private List<String> mySplitter(String string, String tokan){
        List<String> result = new ArrayList<>();
        if (string.equals(tokan)){
            result.add(string);
            return result;
        }
        Integer ind = string.indexOf(tokan);
        while (ind != -1) {
            if (ind > 0)
                if(!string.substring(0,ind).equals(" "))
                    result.add(string.substring(0, ind));
            result.add(tokan);
            string = string.substring(tokan.length() + ind);
            ind = string.indexOf(tokan);
        }
        if (string.length()>0){
            result.add(string);
        }
        return result;
    }

    private List<String> finalTokenize(List<String> tokens) {
        tokens = equalSign(tokens);
        tokens = stringConstants(tokens);
        //System.out.println(tokens);
        return tokens;
    }

    private List<String> equalSign(List<String> tokens){
        if (!tokens.toString().contains("="))
            return tokens;
        List<String> betterVersion = new ArrayList<>();
        for (int i=0; i<tokens.size()-1;i++)
            if (tokens.get(i+1).equals("=") && "=><".contains(tokens.get(i))){
                betterVersion.add(tokens.get(i)+tokens.get(i+1));
                i++;
            }else{
                betterVersion.add(tokens.get(i));
                if (i+1==tokens.size()-1)
                    betterVersion.add(tokens.get(i+1));
            }
        return betterVersion;
    }

    private List<String> stringConstants(List<String> tokens){
        if (!tokens.toString().contains("\""))
            return tokens;
        List<String> anotherTokenize = new ArrayList<>();
        for (int i=0; i<tokens.size(); i++){
            if (tokens.get(i).contains("\"")
                        && tokens.get(i).length()>1
                        && tokens.get(i).substring(1).contains("\"")){
                anotherTokenize.add(tokens.get(i));
            }else {
                if (tokens.get(i).contains("\"")){
                    String constantString = tokens.get(i);
                    for (int j = i+1; j<tokens.size(); j++){
                        constantString += " "+tokens.get(j);
                        if (tokens.get(j).contains("\"")){
                            i = j;
                            break;
                        }
                    }
                    anotherTokenize.add(constantString);
                }else {
                    anotherTokenize.add(tokens.get(i));
                }
            }
        }
        return anotherTokenize;
    }

    private Boolean isNumber(String str){
        try {
            Integer i = Integer.parseInt(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void writeST(){
        try{
            FileWriter fileWriter = new FileWriter("./output/symboltable.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Integer size = symbolTable.size();
            Node[] elements = symbolTable.getElements();

            printWriter.println(size);
            for (int i = 0; i < size; i++){
                String line = i +  ", ";
                Node currentNode = elements[i];
                while (currentNode!=null){
                    line += currentNode.identifier + " - " + currentNode.index + " ; ";
                    currentNode = currentNode.nextNode;
                }
                printWriter.println(line);
            }
            printWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePIF(){
        try{
            FileWriter fileWriter = new FileWriter("./output/pif.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(pif.size());
            for(int i=0; i<this.pif.size(); i++){
                String line ="";
                line +=  pif.get(i).token+","+pif.get(i).hashcode+","+pif.get(i).position;
                printWriter.println(line);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeLexicalErrors(){
        try {
            FileWriter fileWriter = new FileWriter("./output/lexical-errors.out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(errors.size());
            String msg;
            if (errors.size() > 0){
                msg = "Lexically incorrect!";
            }else {
                msg = "Lexically correct!";
            }
            printWriter.println(msg);
            for(String error: errors)
                printWriter.println(error);
            printWriter.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
