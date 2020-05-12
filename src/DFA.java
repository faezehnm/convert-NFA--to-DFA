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

//    public void setStates(ArrayList<String> states) {
//        this.states = states;
//    }
//
//    public void setStartState(String startState) {
//        this.startState = startState;
//    }
//
//    public void setFinalStates(ArrayList<String> finalStates) {
//        this.finalStates = finalStates;
//    }
//
//    /**
//     * set dfa's sides with nfa's sides
//     * @param NDFASides sides of nfa
//     */
//    public void setSides(ArrayList<String> NDFASides)
//    {
//        sides = new ArrayList<>();
//        for( int i=0 ; i<states.size() ; i++) {
//            if (states.get(i).length() == 2) {
//                singleState(i, NDFASides);
//                removeSingleDuplicates();
//            }
//            else {
//                multiState(states.get(i));
//                matchMultiSides();
//            }
//        }
//
//        sides = singleSides;
//        sides.addAll(multiSides);
//        putToHashMap();
//        removeDuplicatesEndStates();
//        HomogenizationSides();
////        System.out.println(finalSides);
////        removeJunkSides();
//    }
//
////    /**
////     * remove not useful sides
////     */
////    private void removeJunkSides() {
////
////    }
//
//    /**
//     * creat multi-state of dfa
//     * @param currentState dfa's single-state
//     */
//    private void multiState(String currentState)
//    {
//        String str ="" ;
//        //iterate all states of this state
//        for( int k=1 ; k<currentState.length() ; k+=2) {
//            //iterate in alphabets
//            for (int i = 0; i < alphabets.size(); i++) {
//                //iterate in sides
//                for (int j = 0; j < singleSides.size(); j++) {
//                    if ( (currentState.charAt(k) + "").equals(singleSides.get(j).charAt(1)+"") &&
//                        alphabets.get(i).equals(singleSides.get(j).charAt(2)+"")){
//                        str = currentState + alphabets.get(i);
//                        for (int n = 3; n < singleSides.get(j).length(); n++) {
//                            str += singleSides.get(j).charAt(n);
//                        }
//                        multiSides.add(str);
//                    }
//                }
//            }
//        }
////        System.out.println("inja");
////        System.out.println(multiSides);
//    }
//
//    /**
//     * if multi-side contain duplicated
//     */
//    private void  matchMultiSides()
//    {
//        HashMap<String,String> hashSides = new HashMap<>();
//        for( int i=0 ; i<multiSides.size() ; i++){
//            String currentKey = getKey(multiSides.get(i));
//            if( !hashSides.containsKey(currentKey))
//                hashSides.put(currentKey,getValue(multiSides.get(i)));
//            else{
//                hashSides.put(currentKey,hashSides.get(currentKey)+ getValue(multiSides.get(i)));
//            }
//        }
//        setHashMapToMultiSides(hashSides);
//        removeNullInMultiSides();
//    }
//
//    /**
//     * if an state repeat in multi-sides's end-states
//     */
//    private void removeDuplicatesEndStates()
//    {
////        System.out.println("are you exist");
//        for (Map.Entry<String, ArrayList<String>> entry : finalSides.entrySet()) {
//            ArrayList<String> news = new ArrayList<>();
//            for (String str : entry.getValue()) {
////                System.out.println(!news.contains(str));
//                if (!news.contains(str))
//                    news.add(str);
//            }
////            System.out.println(news);
//            finalSides.put(entry.getKey(), news);
//        }
//
//    }
//
//    /**
//     * make sides with hashMap
//     */
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
//            finalSides.put(key,values);
//        }
//
//    }
//
//    /**
//     * Homogenize final-states with states
//     */
//    private void HomogenizationSides()
//    {
//        for (Map.Entry<String,ArrayList<String>> entry :  finalSides.entrySet()) {
//            Collections.sort(entry.getValue());
//        }
//    }
//
//    /**
//     * if just has null in multi-sides final-state
//     */
//    private void removeNullInMultiSides()
//    {
//        ArrayList<String> newSides = new ArrayList<>();
//        for( String str : multiSides){
//            String tmp = str ;
//            if( tmp.contains("[]")){
//                String parts[] = tmp.split(Pattern.quote("[]"));
//                String tmp2 = parts[0];
//                if( tmp.length()-tmp2.length()!=2 ) {
//                    tmp2 += parts[1];
//                }
//                newSides.add(tmp2);
//            }
//            else
//                newSides.add(tmp) ;
//        }
//        multiSides = newSides ;
//    }
//
//    /**
//     * creat hashMap of multi-sides
//     * @param hashSides sides in form of hashMap
//     */
//    private void setHashMapToMultiSides(HashMap<String,String> hashSides)
//    {
//        multiSides  = new ArrayList<>();
//        for (Map.Entry<String,String> entry : hashSides.entrySet()) {
//            multiSides.add(entry.getKey()+ entry.getValue());
//        }
//    }
//
//    /**
//     * get start-state of each side
//     * @param side one side of machine
//     * @return start-state of this side
//     */
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
//    /**
//     * get end-state of each side
//     * @param side one side of machine
//     * @return end-state of this side
//     */
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
//    /**
//     * make single state
//     * @param i
//     * @param NDFASides nfa's sides
//     */
//    private void singleState(int i ,ArrayList<String> NDFASides)
//    {
//        String currentState[] = states.get(i).split("(?!^)");
//        String endStates = "";
//        for (int k = 0; k < alphabets.size(); k++) {
//            for (int j = 1; j < currentState.length; j += 2) {
//                endStates = findLastState(NDFASides, currentState[j], alphabets.get(k));
//                if (!endStates.equals(""))
//                    singleSides.add(endStates);
////                else
////                    singleSides.add("q" + currentState[j] + alphabets.get(k) + "[]");
//            }
//
//        }
//    }
//
//    /**
//     * remove if has duplicates side
//     */
//    private void removeSingleDuplicates()
//    {
//        ArrayList<String> newSide = new ArrayList<>();
//        for (String element : singleSides) {
//            if (!newSide.contains(element))
//                newSide.add(element);
//        }
//        singleSides = newSide ;
//    }
//
//    /**
//     * find last state
//     * @param NDFASides nfa's sides
//     * @param currentState dfa's state
//     * @param alphabet alphabet of machine
//     * @return
//     */
//    private String findLastState(ArrayList<String> NDFASides , String currentState , String alphabet)
//    {
//        String str = new String("") ;
//
//        for( int i=0 ; i <NDFASides.size() ; i++){
//            if( currentState.equals(NDFASides.get(i).charAt(1)+"") && alphabet.equals(NDFASides.get(i).charAt(2)+"")){
//                if( str.equals(""))
//                    str += NDFASides.get(i) ;
//                else
//                    str += "q"+ NDFASides.get(i).charAt(4) ;
//            }
//        }
//        return str ;
//    }
//
//    /**
//     * save dfa's information in file
//     */
//    public void saveInFile()
//    {
//        try {
//            FileWriter myWriter = new FileWriter("DFA_Output _2.txt ");
//            //write alphabets
//            for( String str : alphabets){
//                myWriter.write(str+" ");
//            }
//            myWriter.write("\n");
//            //write states
//            for( String str : states){
//                myWriter.write(str+" ");
//            }
//            myWriter.write("\n");
//            //write start-state
//            myWriter.write(startState);
//            myWriter.write("\n");
//            //write final-state
//            for( String str : finalStates){
//                myWriter.write(str+" ");
//            }
//            myWriter.write("\n");
//            //write sides
//            for (Map.Entry<String, ArrayList<String>> entry : finalSides.entrySet()) {
//                int i ;
//                for(  i=0 ; i<entry.getKey().length()-1 ; i++){
//                    myWriter.write(entry.getKey().charAt(i));
//                }
//                myWriter.write(" ");
//                myWriter.write(entry.getKey().charAt(i) + " ");
//
//                for( String str : entry.getValue()){
//                    myWriter.write(str);
//                }
//                myWriter.write("\n");
//            }
//
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }

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

