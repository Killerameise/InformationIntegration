import java.util.ArrayList;

/**
 * Created by dennis on 10.01.16.
 */
public class Sportsman {
    private String key;
    private ArrayList<String> values;

    public Sportsman(){
        values = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}
