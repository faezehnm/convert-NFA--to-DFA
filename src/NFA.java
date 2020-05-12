import java.awt.image.AreaAveragingScaleFilter;
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
     * find all closures of nfa
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

    }

    /**
     * print closures of nfa
     */
    private void printAllClosures()
    {
//        for (Map.Entry<State, ArrayList<String>> entry : allClosures.entrySet())
//            System.out.println(entry.getKey().getName() + " " + entry.getValue());
//        System.out.println(9);
        for( State state : states)
            System.out.println(state.getName() + " " + state.getClosures());
    }

    /**
     * find state  with state's name
     * @param name
     * @return
     */
    private State findStateWithName(String name)
    {
        State res = new State("") ;
        for (State state : states){
            if( state.getName().equals(name))
                res = state;
        }
        if( res.getName().equals(""))
            res= new State(name);

        return res;
    }

    /**
     * convert nfa to dfa
     */
    public void transferToDFA()
    {
        DFA dfa = new DFA();
        dfa.setAlphabets(alphabets);
        setDfaSidesAndStates(dfa);
        setDfaFinalStates(dfa);
        setDfaStartState(dfa);
        dfa.saveInFile();
    }

    /**
     * set sides an states of dfa
     * @param dfa dfa
     */
    private void setDfaSidesAndStates(DFA dfa)
    {
        State state = findStateWithName(startState.getName());
        checkEachState(state , dfa);
        removeDuplicatedDfaSide(dfa);
        removeDuplicatedDfaState(dfa);
    }

    /**
     * check and find each dfa's state
     * @param currentState start state of dfa which is closures(startState of nfa)
     * @param dfa dfa
     */
    private void checkEachState( State currentState, DFA dfa)
    {

        String stateToAdd = "";

        if( currentState.getName().equals(startState.getName()))
            stateToAdd = convertArrayToString(currentState.getClosures()) ;
        else
            stateToAdd = currentState.getName();


        dfa.getStates().add(stateToAdd);


        //iterate for each state in alphabets
        for( String alphabet :alphabets){
            String endState = calculateEndState(stateToAdd , alphabet);

            //add side to dfa
            if( !endState.equals("")) {
                String sideToAdd = "";
                sideToAdd = stateToAdd + " " + alphabet + " " + endState;
                dfa.getSides().add(sideToAdd);


                //check new state to add
                if (!dfa.getStates().contains(endState))
                    checkEachState(findStateWithName(endState), dfa);
            }

        }

    }


    /**
     * find endState of each dfa's state
     * @param currentStateName current dfa's state
     * @param alphabet current alphabet
     * @return endState of this state
     */
    private String calculateEndState(String currentStateName , String alphabet)
    {
        String currentEndState = "" ;
        ArrayList<String> ends = new ArrayList<>();

        //iterate all subState of state
        for( String stateName : getSubState(currentStateName) ) {
            State state = findStateWithName(stateName);

            //check is there side or not
            if (state.getSides().containsKey(alphabet)) {
                ArrayList<String> values = state.getSides().get(alphabet);

                //check for are values of current side
                for (String value : values) {
                    State state1 = findStateWithName(value);

                    //add to result( end state of this state )
                    if (allClosures.containsKey(state1)) {
                        for (String str : allClosures.get(state1)) {
                            ends.add(str);
                        }
                    }
                }
            }
        }

        //remove duplicated state
        ArrayList<String> news = new ArrayList<>() ;
        for( String str : ends){
            if( !news.contains(str))
                news.add(str) ;
        }
        ends = news ;


        return convertArrayToString(ends);
    }


    private String convertArrayToString(ArrayList<String> arr)
    {
        Collections.sort(arr);
        String res ="";
        for( String str : arr)
            res+= str;

//        System.out.println("in converting: " + res);
        return res;
    }

    private void removeDuplicatedDfaState(DFA dfa)
    {
        ArrayList<String> newStates = new ArrayList<>();
        for(String state : dfa.getStates()){
            if(!newStates.contains(state))
                newStates.add(state);
        }
        dfa.setStates(newStates);

    }
    private void removeDuplicatedDfaSide(DFA dfa)
    {
        ArrayList<String> newSides = new ArrayList<>();
        for(String side : dfa.getSides()){
            if(!newSides.contains(side))
                newSides.add(side);
        }
        dfa.setSides(newSides);

    }

    private ArrayList<String> getSubState(String stateName)
    {
        ArrayList<String> res = new ArrayList<>();
        for(int i=1 ; i< stateName.length() ; i+=2 ){
            res.add( "q" +stateName.charAt(i));
        }
        return res ;
    }

    /**
     * find dfa's endStates
     * @param dfa
     */
    private void setDfaFinalStates(DFA dfa)
    {
        for(String state : dfa.getStates()){
            for( State finalState : finalStates) {
                if (getSubState(state).contains(finalState.getName()))
                    dfa.getFinalStates().add(state);
            }
        }
//        System.out.println(dfa.getFinalStates());
    }

    /**
     * find dfa's start state
     * @param dfa dfs
     */
    private void setDfaStartState(DFA dfa)
    {
        dfa.setStartState(convertArrayToString(findStateWithName(startState.getName()).getClosures()));

    }

}
