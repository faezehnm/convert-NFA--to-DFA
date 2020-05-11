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
    private HashMap<String, ArrayList<String>> landaClosures ;

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


        landaClosures= new HashMap<>();
//        setLandaClosures();
//        System.out.println(landaClosures);

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
                if(state.getState().equals(parts[0])){
                    state.addSide(parts[1],parts[2]);
                }
            }
        }
//        for( State state : states)
//            System.out.println(state.giveSides());

    }

    /**
     * convert lastVersion.NFA to DFA
     */

//    private void setLandaClosures()
//    {
//        //set single landa
//        for(String state : states){
//            ArrayList<String> closures = new ArrayList<>();
//            for( String side : sides){
//                if( side.charAt(1) == state.charAt(1) && (side.charAt(2)+"").equals("λ")){
//                    closures.add("q" + side.charAt(4));
//                }
//            }
//            landaClosures.put(state ,closures) ;
//        }
//        //set multi landa
//        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
//            for(String str : entry.getValue()){
//                if( landaClosures.containsKey(str)){
//                    ArrayList<String> news = new ArrayList<>();
//                    news.addAll(entry.getValue());
//                    news.addAll(landaClosures.get(str));
//                    landaClosures.put(entry.getKey(),news);
//                }
//            }
//        }
//        //add each state to it's landa-closure
//        for( String state : states){
//            if( landaClosures.containsKey(state) && !landaClosures.get(state).contains(state)){
//                ArrayList<String> newKey = landaClosures.get(state) ;
//                newKey.add(state);
//                landaClosures.put(state,newKey);
//            }
//        }
//        //remove duplicated
//        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
//            ArrayList<String> newArray = new ArrayList<>();
//            for (String str : entry.getValue()) {
//                if (!newArray.contains(str))
//                    newArray.add(str);
//            }
//            landaClosures.put(entry.getKey(), newArray);
//        }
//
//
//        //sort landa-closures
//        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
//            Collections.sort(entry.getValue());
//        }
//
//
//    }
//
//    public void transferToDFA()
//    {
//        DFA dfa = new DFA();
//        dfa.setAlphabets(alphabets);
//
//        System.out.println(hashSides);
//        setDfaSidesAndStates(dfa);
////        System.out.println(landaClosures);
//        System.out.println(dfa.getSides());
//
//
//
//
////        dfa.setStates(transferDFAState());
////
////        dfa.setStartState(startState);
////        dfa.setFinalStates(transferFinalState(transferDFAState()));
////        dfa.setSides(newSides);
////        dfa.saveInFile();
//    }
//
//    private void setDfaSidesAndStates(DFA dfa)
//    {
//        checkEachState(startState , dfa);
//    }
//
//    private void checkEachState( String currentState, DFA dfa)
//    {
//        for( String alphabet :alphabets){
//            String res = "";
//
//            for( String state : landaClosures.get(currentState)){
//                if( hashSides.containsKey(state+alphabet)){
//                    if( landaClosures.containsKey(hashSides.get(state+alphabet))){
//
//                        String str =  setNewCurrentState( landaClosures.get(hashSides.get(state+alphabet)));
//                        res += str ;
//                    }
//                }
//            }
//            if( !res.equals("")) {
//                dfa.addSide(currentState + alphabet + res);
//                dfa.addState(currentState);
//
//                if (!dfa.getStates().contains(res)) {
//                    checkEachState(res,dfa);
//                }
//            }
//        }
//    }
//
//
//    //TODO: check this method
//    private String setNewCurrentState(ArrayList<String> states)
//    {
//        String res ="";
//        for( String state : states){
//            res += state ;
//        }
//        return res;
//    }
//
//
//    private void putToHashMap()
//    {
//        for( int i=0 ; i<sides.size() ; i++){
//            String key = getKey(sides.get(i)) ;
//            String value = getValue(sides.get(i)) ;
//            ArrayList<String> values = new ArrayList<>();
//
//            for( int j=1 ; j< value.length() ; j+=2){
//                values.add("q"+value.charAt(j)) ;
//            }
//            hashSides.put(key,values);
//        }
//
//    }
//
//
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
//
//

}
