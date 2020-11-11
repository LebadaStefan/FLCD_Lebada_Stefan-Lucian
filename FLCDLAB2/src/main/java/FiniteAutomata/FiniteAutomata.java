package FiniteAutomata;

import com.sun.tools.javac.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FiniteAutomata {
    private static final String FILE_PATH = "./input/integer.in";
    private List<String> alphabet;
    private List<String> states;
    private String initialState;
    private List<String> finalState;
    private List<AutoTransition> transitions;


    public FiniteAutomata(){
        read();
    }

    public void read(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line = reader.readLine();
            states = Arrays.asList(line.split(","));
            line = reader.readLine();
            alphabet = Arrays.asList(line.split(","));
            initialState = reader.readLine();
            line = reader.readLine();
            finalState = Arrays.asList(line.split(","));

            transitions = new ArrayList<>();
            while (true){
                line = reader.readLine();
                if (line == null || line.equals(""))
                    break;

                List<String> transition = Arrays.asList(line.split(","));
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.startState = transition.get(0);
                autoTransition.endState = transition.get(1);
                autoTransition.value = transition.get(2);
                transitions.add(autoTransition);
            }
            reader.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "Finite Automata { " +
                " States: " + states +
                ", Alphabet: " + alphabet +
                ", initialState: " + initialState +
                ", finalState" + finalState +
                ", transition: " + transitions + '}';
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getStates() {
        return states;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalState() {
        return finalState;
    }

    public List<AutoTransition> getTransitions() {
        return transitions;
    }

    public Boolean isDFA(){
        HashMap<Pair<String, String>, Integer> map =  new HashMap<>();
        for (AutoTransition transition: transitions)
            map.put(new Pair<>(transition.startState, transition.value), 0);
        for(AutoTransition transition: transitions) {
            if (map.get(new Pair<>(transition.startState, transition.value)) == 1)
                return false;
            map.put(new Pair<>(transition.startState,transition.value),1);
        }
        return true;
    }

    private List<AutoTransition> transitionOf(String initialState){
        List<AutoTransition> trans = new ArrayList<>();
        for (AutoTransition t: transitions)
            if (t.startState.equals(initialState))
                trans.add(t);
        return trans;
    }

    private String nextState(String initialState, String value){
        for (AutoTransition trans: transitionOf(initialState))
            if (trans.value.equals(value))
                return trans.endState;
        return "invalid state!";
    }

    public boolean isAccepted (String seq){
        String currentState = initialState;
        Integer index = 0;
        while (index < seq.length()){
            String chValue = seq.charAt(index) + "";
            String nextState = nextState(currentState, chValue);
            if (nextState.equals("invalid state!"))
                return false;
            currentState = nextState;
            index++;
        }
        return finalState.toString().contains(currentState);
    }


}
