package FiniteAutomata;

public class AutoTransition {

    public String startState;
    public String endState;
    public String value;

    @Override
    public String toString(){
        return "[start state: " + startState +'\''+
                ", end state: " + endState + '\'' +
                ", value: " + value + '\'' + "]";
    }
}
