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


        printAllClosures();

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
        removeDuplicatedDfaSide(dfa);
        removeDuplicatedDfaState(dfa);
//        System.out.println("********Sides******************");
//        for( String side : dfa.getSides()){
//            System.out.println(side);
//        }
//        for( String side : dfa.getStates()){
//            System.out.println(side);
//        }
    }

    private void checkEachState( State currentState, DFA dfa)
    {

        String stateToAdd = "";
        if( currentState.getName().equals(startState.getName()))
            stateToAdd = convertArrayToString(currentState.getClosures()) ;
        else
            stateToAdd = currentState.getName();


        dfa.getStates().add(stateToAdd);
        System.out.println("add state : " +stateToAdd );
        System.out.println("dfa states are : "+dfa.getStates());


//        System.out.println(currentState.getName() + " hh " + currentState.getClosures());


        //iterate for each state in alphabets
        for( String alphabet :alphabets){
            System.out.println("in state :" +stateToAdd + " , "+ "in alphabet : "+ alphabet);

            String endState = calculateEndState(currentState , alphabet);
            System.out.println("end state is :" + endState);

            if( !endState.equals("")) {
                String sideToAdd = "";

                sideToAdd= stateToAdd + " " + alphabet + " " + endState;

                dfa.getSides().add(sideToAdd);
                System.out.println("side to add is :" +sideToAdd);

                if( !dfa.getStates().contains(endState))
                    System.out.println("go to");
                    checkEachState(findStateWithName(endState),dfa);
            }


        }

    }



    private String calculateEndState(State currentState , String alphabet)
    {
//        System.out.println("***********************************");
        String endState = "" ;
        ArrayList<String> ends = new ArrayList<>();

//        System.out.println(currentState.getClosures());

        //iterate all closures of state
        for( String stateName : currentState.getClosures()) {

            State state = findStateWithName(stateName);


//            System.out.println(state.getName() +" has side with " + alphabet+ " :" +state.getSides().containsKey(alphabet));
            //check is there side or not
            if (state.getSides().containsKey(alphabet)) {
                ArrayList<String> values = state.getSides().get(alphabet);
//                System.out.println(values);

                //check for are values of current side
                for (String value : values) {
                    State state1 = findStateWithName(value);

//                    System.out.println(value+" is in landa closures : " + allClosures.containsKey(state1) );

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
        for( String str : ends)
            endState += str;

//        System.out.println(endState);

//        System.out.println("***********************************");
        return endState;
    }


    private String convertArrayToString(ArrayList<String> arr)
    {
        String res ="";
        for( String str : arr)
            res+= str;

        System.out.println("in converting: " + res);
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
