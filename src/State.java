
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {
    private String state ;
    private HashMap<String, ArrayList<String>> sides;

    public State(String state){
        this.state = state ;
        sides = new HashMap<>();
    }

    public void addSide(String key , String value)
    {
        if( !sides.containsKey(key)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(value) ;
            sides.put(key, temp);
        }
        else
            sides.get(key).add(value);
    }


    public String getState()
    {
        return state;
    }

    public ArrayList<String> giveSides()
    {
        ArrayList<String> res = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : sides.entrySet()) {
            for (String value : entry.getValue()) {
                res.add(state + " " + entry.getKey() + " " + value);
            }
        }
        return res;
    }
}
