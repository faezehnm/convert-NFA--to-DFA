import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Nondeterministic finite automaton machine
 * @author Faezeh Naeimi
 */
public class NFA {

    private ArrayList<String> contents ;
    private ArrayList<String> alphabets ;
    private ArrayList<State> states ;
    private State startState ;
    private ArrayList<State> finalStates ;
    private HashMap<State, ArrayList<String>> allClosures ;

    /**
     * creat object
     * @param contents contents of file which is about grammar
     */
    public NFA(ArrayList<String> contents)
    {
        this.contents = contents ;
        setAlphabets(contents.get(0).split(("\\s+")));
        setStates(contents.get(1).split(("\\s+")));
        this.startState = new State(contents.get(2)) ;
        setFinalStates(contents.get(3).split(("\\s+")));
        setSides(contents);
        allClosures= new HashMap<>();
        setLandaClosures();


    }


    public ArrayList<String> getAlphabets() {
        return alphabets;
    }

    /**
     * set alphabets of grammar from file's content
     * @param splited which read from file
     */
    private void setAlphabets(String[] splited)
    {
        alphabets = new ArrayList<>();

        for( int i=0 ;i<splited.length ; i++ )
            alphabets.add(splited[i]);

//        System.out.println(alphabets);

    }

    /**
     * set states of grammar from file's content
     * @param splited which read from file
     */
    private void setStates(String[] splited)
    {
        states = new ArrayList<>();

        for( int i=0 ;i<splited.length ; i++ )
            states.add(new State(splited[i]));
//        for( State state : states){
//            System.out.println(state.getState());
//        }
    }

    /**
     * set final state of grammar from file's content
     * @param splited which read from file
     */
    private void setFinalStates(String[] splited)
    {
        finalStates = new ArrayList<>();
        for( int i=0 ;i<splited.length ; i++ )
            finalStates.add(new State(splited[i]));

//        for( State state : finalStates){
//            System.out.println(state.getState());
//        }
    }

    /**
     * set sides of grammar from file's content
     * @param contents which read from file
     */
    private void setSides(ArrayList<String> contents)
    {

        for( int i=4 ; i<contents.size() ; i++) {
            String parts[] = contents.get(i).split("\\s+");
            for( State state : states){
                if(state.getName().equals(parts[0])){
                    state.addSide(parts[1],parts[2]);
                }
            }
        }
        for( State state : states){
            state.setClosures();
        }
//        for( State state : states)
//            System.out.println(state.giveSides());

    }

    /**
     * convert lastVersion.NFA to DFA
     */

    private void setLandaClosures()
    {
        //set single landa
        for (State state : states)
            allClosures.put(state, state.getClosures());


        //set multi landa
        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet()) {
            for (String str : entry.getValue()) {
                if (allClosures.containsKey(findStateWithName(str))) {
                    System.out.println(777);
                    ArrayList<String> news = new ArrayList<>();
                    news.addAll(entry.getValue());
                    news.addAll(allClosures.get(findStateWithName(str)));
                    allClosures.put(entry.getKey(), news);
                }
            }
        }

        //remove duplicate closure
        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet() ){
            ArrayList<String> news = new ArrayList<>();
            for( String str : entry.getValue()){
                if( !news.contains(str))
                    news.add(str);
            }
            allClosures.put(entry.getKey(),news);
        }

        //sort landa-closures
        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet()) {
            Collections.sort(entry.getValue());
        }

        //update states's closures
        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet()) {
            entry.getKey().updateClosures(entry.getValue());
        }


//        printAllClosures();

    }

    private void printAllClosures()
    {
//        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet())
//            System.out.println(entry.getKey().getName() + " " + entry.getValue());
//        System.out.println(9);
        for( State state : states)
            System.out.println(state.getName() + " " + state.getClosures());
    }

    private State findStateWithName(String name)
    {
        for (State state : states){
            if( state.getName().equals(name))
                return state;
        }
        return null;
    }


    public void transferToDFA()
    {
        DFA dfa = new DFA();
        dfa.setAlphabets(alphabets);

        setDfaSidesAndStates(dfa);





//        dfa.setStates(transferDFAState());
//
//        dfa.setStartState(startState);
//        dfa.setFinalStates(transferFinalState(transferDFAState()));
//        dfa.setSides(newSides);
//        dfa.saveInFile();
    }

    private void setDfaSidesAndStates(DFA dfa)
    {
        State state = findStateWithName(startState.getName());
        checkEachState(state , dfa);
    }

    private void checkEachState( State currentState, DFA dfa)
    {
        dfa.getStates().add(currentState.getName());


        //iterate for each state in alphabets
        for( String alphabet :alphabets){

            String endState = calculateEndState(currentState , alphabet);
            if( !endState.equals("")) {
                dfa.getSides().add(currentState.getName() + " "+ alphabet +" " + endState);
                if( !dfa.getStates().contains(endState))
                    checkEachState(findStateWithName(endState),dfa);
            }

        }
        for( String side : dfa.getSides()){
            System.out.println(side);
        }
    }

    private String calculateEndState(State currentState , String alphabet)
    {
        String endState = "" ;

        System.out.println(currentState.getClosures());
        for( String stateName : allClosures.get(currentState)) {
            State state = findStateWithName(stateName);

            //check is there side or not
            if (state.giveSides().contains(alphabet)) {
                ArrayList<String> values = state.getSides().get(alphabet);

                //check for are values of current side
                for (String value : values) {
                    State state1 = findStateWithName(stateName);

                    //add to result( end state of this state )
                    if (allClosures.containsKey(state1)) {
                        for (String str : allClosures.get(state1)) {
                            endState += str;
                        }
                    }
                }
            }
        }
        return endState;
    }



//    //TODO: check this method
//    private String setNewCurrentState(ArrayList<String> states)
//    {
//        String res ="";
//        for( String state : states){
//            res += state ;
//        }
//        return res;
//    }



//    private String getKey(String side)
//    {
//        String res = "";
//        for( int i=0 ; i<side.length() ; i++){
//            for( int j=0 ; j<alphabets.size() ; j++ ){
//                if( side.contains(alphabets.get(j))) {
//                    String parts[] = side.split(Pattern.quote(alphabets.get(j)));
//                    res =parts[0] + alphabets.get(j);
//                }
//            }
//        }
//        return res ;
//    }
//
//    private String getValue(String side)
//    {
//        String res = "";
//        for( int i=0 ; i<side.length() ; i++){
//            for( int j=0 ; j<alphabets.size() ; j++ ){
//                if( side.contains(alphabets.get(j))) {
//                    String parts[] = side.split(Pattern.quote(alphabets.get(j)));
//                    res =parts[1];
//                }
//            }
//        }
//        return res ;
//    }



}
