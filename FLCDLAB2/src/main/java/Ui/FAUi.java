package Ui;

import FiniteAutomata.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FAUi {
    private FiniteAutomata fa;

    public FAUi(FiniteAutomata a){fa = a;}

    public void showCommands() {
        System.out.println("1 - Show initial state");
        System.out.println("2 - Show states");
        System.out.println("3 - Show alphabet");
        System.out.println("4 - Show transitions");
        System.out.println("5 - Show final states");
        System.out.println("6 - Is DFA");
        System.out.println("7 - Sequence is accepted by FA");
        System.out.println("0 - Exit");
    }

    private void command1() {
        System.out.println("The initial state is: ");
        System.out.println(fa.getInitialState());
    }

    private void command2() {
        System.out.println("Existing states: ");
        for (String state : fa.getStates())
            System.out.println(state);
    }

    private void command3() {
        System.out.println("The alphabet is: ");
        for (String alphabet : fa.getAlphabet())
            System.out.println(alphabet);
    }

    private void command4() {
        System.out.println("The transitions are: ");
        for (AutoTransition tr: fa.getTransitions()) {
            System.out.println("Start: " + tr.startState + " End: " + tr.endState + " Value: " + tr.value);
        }
    }

    private void command5() {
        System.out.println("Final states: ");
        for (String finalS : fa.getFinalState())
            System.out.println(finalS);
    }

    private void command6() {
        if (fa.isDFA()){
            System.out.println("The given FA is a DFA");
        }else {
            System.out.println("The given FA is NOT a DFA");
        }
    }

    private void command7() {
        if (!fa.isDFA()) {
            System.out.println("FA needs to be a DFA");
            return;
        }

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter sequence");
            String sequence = r.readLine();
            if (fa.isAccepted(sequence)){
                System.out.println("Sequence is accepted");
            }else{
                System.out.println("Sequence is NOT accepted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        while (true){
            try {
                showCommands();
                String command = "";
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter command: ");
                command = reader.readLine();
                switch(command)
                {
                    case "1":
                        command1();
                        break;
                    case "2":
                        command2();
                        break;
                    case "3":
                        command3();
                        break;
                    case "4":
                        command4();
                        break;
                    case "5":
                        command5();
                        break;
                    case "6":
                        command6();
                        break;
                    case "7":
                        command7();
                        break;
                    case "0":
                        System.exit(0);
                    default:
                        throw new Exception("Invalid command!");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
