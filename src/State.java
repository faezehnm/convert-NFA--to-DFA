
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {
    private String name ;
    private HashMap<String, ArrayList<String>> sides;
    private ArrayList<String> closures ;

    public State(String name)
    {
        this.name = name ;
        sides = new HashMap<>();
        closures = new ArrayList<>();
    }

//    public void addCompletSides(HashMap<String, ArrayList<String>> toAdd)
//    {
//        for (Map.Entry<String, ArrayList<String>> entry : toAdd.entrySet()){
//            for (String value : entry.getValue()) {
//                addSide(entry.getKey(),value);
//            }
//        }
////
//        System.out.println("8888888ohmygoodnes888888888");
//        System.out.println(toAdd);
//        System.out.println(sides);
//        System.out.println("8888888ohmygoodnes888888888");
//    }

    public void addSide(String key , String value)
    {
        if( !sides.containsKey(key)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(value);
            sides.put(key, temp);
        }
        else {
            if( !sides.get(key).contains(value))
                sides.get(key).add(value);
        }

    }


    public String getName()
    {
        return name;
    }

    public ArrayList<String> giveSides()
    {
        ArrayList<String> res = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : sides.entrySet()) {
            for (String value : entry.getValue()) {
                res.add(name + " " + entry.getKey() + " " + value);
            }
        }
        return res;
    }

    public void setClosures()
    {
        closures.add(this.name) ;
        for (Map.Entry<String, ArrayList<String>> entry : sides.entrySet()) {
            if ( entry.getKey().equals("λ") )
                closures.addAll(entry.getValue()) ;
        }
    }

    /*
    after adding multi closures
     */
    public void updateClosures(ArrayList<String> news)
    {
        closures = new ArrayList<>();
        closures.addAll(news) ;
    }

    public ArrayList<String> getClosures() {
        return closures;
    }

    public HashMap<String, ArrayList<String>> getSides() {
        return sides;
    }

}
