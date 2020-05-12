import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * convert lastVersion.NFA-λ to lastVersion.NFA
 * @author Faezeh Naeimi
 */
public class Transform {
    private ArrayList<String> contents ;
    private ArrayList<String> alphabets ;
    private ArrayList<String> states ;
    private String startState ;
    private ArrayList<String> finalStates ;
    private ArrayList<String> oldSides ;
    private ArrayList<String> newSides ;
    private HashMap<String, ArrayList<String>> landaClosures ;

    /**
     * creat object
     * @param alphabets
     * @param states
     * @param startState
     * @param finalStates
     * @param sides
     */
//    public Transform(ArrayList<String> alphabets, ArrayList<String> states, String startState, ArrayList<String> finalStates, ArrayList<String> sides)
//    {
//        super();
//        this.alphabets = alphabets;
//        this.states = states;
////        System.out.println(states);
//        this.startState = startState ;
//        this.finalStates = finalStates;
//        this.oldSides = sides ;
//        landaClosures = new HashMap<>();
//
//        //step-1
////        setLandaClosures();
//        System.out.println(landaClosures);
//
//
////        newSides = new ArrayList<>();
////        updateSides();
//    }
//
//    /**
//     * set landa-closure of all state
//     */
////    private void setLandaClosures()
////    {
////        //set single landa
////        for(String state : states){
////            ArrayList<String> closures = new ArrayList<>();
////            for( String side : oldSides){
////                if( side.charAt(1) == state.charAt(1) && (side.charAt(2)+"").equals("λ")){
////                    closures.add("q" + side.charAt(4));
////                }
////            }
////            landaClosures.put(state ,closures) ;
////        }
////        //set multi landa
////        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
////            for(String str : entry.getValue()){
////                if( landaClosures.containsKey(str)){
////                    ArrayList<String> news = new ArrayList<>();
////                    news.addAll(entry.getValue());
////                    news.addAll(landaClosures.get(str));
////                    landaClosures.put(entry.getKey(),news);
////                }
////            }
////        }
////        //add each state to it's landa-closure
////        for( String state : states){
////            if( landaClosures.containsKey(state) && !landaClosures.get(state).contains(state)){
////                ArrayList<String> newKey = landaClosures.get(state) ;
////                newKey.add(state);
////                landaClosures.put(state,newKey);
////            }
////        }
////        //remove duplicated
////        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
////            ArrayList<String> newArray = new ArrayList<>();
////            for (String str : entry.getValue()) {
////                if (!newArray.contains(str))
////                    newArray.add(str);
////            }
////            landaClosures.put(entry.getKey(), newArray);
////        }
////
////
////        //sort landa-closures
////        for(Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
////            Collections.sort(entry.getValue());
////        }
////
//////        System.out.println(landaClosures);
////
////    }
//
//    /**
//     * update final-states with λ-closures
//     */
//    private void updateFinalStates()
//    {
//        ArrayList<String> newFinalStates = new ArrayList<>() ;
//        newFinalStates.addAll(finalStates) ;
//        for (Map.Entry<String, ArrayList<String>> entry : landaClosures.entrySet()) {
//            for( String fs : finalStates){
//                if( entry.getValue().contains(fs))
//                    newFinalStates.add(entry.getKey()) ;
//
//            }
//        }
//        finalStates = newFinalStates ;
//    }
//
//    /**
//     * update sides with λ-closures
//     */
//    private void updateSides()
//    {
//        for( String state : states){
//            for(String alphabet : alphabets){
//                String fs  =giveLandaClosure(state,alphabet) ;
////                System.out.println(fs);
//                for(int i=1 ; i<fs.length() ; i+=2){
//                    newSides.add(state + alphabet + "q"+ fs.charAt(i));
//                }
//
//            }
//        }
////        System.out.println(newSides);
//    }
//
//    /**
//     * give and check λ-closure of each state
//     * @param state
//     * @param alphabet
//     * @return if has condition return λ-closure of state
//     */
//    private String giveLandaClosure(String state , String alphabet)
//    {
//        String res = "";
//        ArrayList<String> stateLandaCosure = landaClosures.get(state) ;
//        ArrayList<String> tocheckNewLandaClosure =  new ArrayList<>();
//        for( String str : stateLandaCosure){
//            for( String oldSide : oldSides){
//                if( oldSide.charAt(1) == str.charAt(1) &&
//                        (oldSide.charAt(2)+"").equals(alphabet) ){
//                    tocheckNewLandaClosure.add("q"+oldSide.charAt(4)) ;
//                }
//            }
//        }
//        for(String str : tocheckNewLandaClosure){
//            for( int i=0 ; i< landaClosures.get(str).size()  ; i++){
//                res += landaClosures.get(str).get(i) ;
//            }
//        }
////        System.out.println(res);
//        return res;
//    }
//
//
//    /**
//     * convert lastVersion.NFA to DFA
//     */
//    public void transferToDFA()
////    {
////        DFA dfa = new DFA();
////        dfa.setAlphabets(alphabets);
////        dfa.setStates(transferDFAState());
////
////        dfa.setStartState(startState);
////        dfa.setFinalStates(transferFinalState(transferDFAState()));
////        dfa.setSides(newSides);
////        dfa.saveInFile();
//    }
//
//    /**
//     * make converted dfa's state
//     * @return dfa's state
//     */
//    private ArrayList<String> transferDFAState()
//    {
//        ArrayList<String> newStates = new ArrayList<>();
//        String set[] = new String[states.size()];
//        for( int i=0 ; i<states.size() ; i++ ){
//            set[i] = states.get(i) ;
//        }
//        int n = set.length;
//
//        for (int i = 0; i < (1<<n); i++)
//        {
//            String str ="" ;
//
//            for (int j = 0; j < n; j++)
//                if ((i & (1 << j)) > 0)
//                    str+= set[j];
//
//            newStates.add(str);
//        }
//        newStates.set(0,"[]") ;
//        return newStates ;
//    }
//
//    /**
//     * make converted dfa's final-state
//     * @param newStates dfa's states
//     * @return dfa's final-state
//     */
//    private ArrayList<String> transferFinalState(ArrayList<String> newStates)
//    {
//        ArrayList<String> newFinalStates = new ArrayList<>();
//        for( int i=0 ; i<newStates.size() ; i++ ){
//            for( int j=0 ; j<finalStates.size() ; j++) {
//                if (newStates.get(i).contains(finalStates.get(j)))
//                    newFinalStates.add(newStates.get(i));
//            }
//        }
//        return newFinalStates ;
//    }
}
