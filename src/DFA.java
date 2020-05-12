import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Deterministic finite automaton machine
 * @author Faezeh Naeimi
 */
public class DFA {

    private ArrayList<String> alphabets ;
    private ArrayList<String> states ;
    private String startState ;
    private ArrayList<String> finalStates ;
    private ArrayList<String> sides ;

    /**
     * creat object
     */
    public DFA()
    {
        states = new ArrayList<>();
        sides = new ArrayList<>();
        finalStates = new ArrayList<>();
    }


    public void setAlphabets(ArrayList<String> alphabets) {
        this.alphabets = alphabets;
    }


    /**
     * save dfa's information in file
     */
    public void saveInFile()
    {
        try {
            FileWriter myWriter = new FileWriter("DFA_Output _2.txt ");
            //write alphabets
            for( String str : alphabets){
                myWriter.write(str+" ");
            }
            myWriter.write("\n");
            //write states
            for( String str : states){
                myWriter.write(str+" ");
            }
            myWriter.write("\n");
            //write start-state
            myWriter.write(startState);
            myWriter.write("\n");
            //write final-state
            for( String str : finalStates){
                myWriter.write(str+" ");
            }
            myWriter.write("\n");
            //write sides
            for(String side : sides){
                myWriter.write(side + "\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote grammar to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public ArrayList<String> getSides() {
        return sides;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public void setSides(ArrayList<String> sides) {
        this.sides = sides;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getStartState() {
        return startState;
    }
}

