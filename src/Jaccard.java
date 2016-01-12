import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jaccard {

    public static void main(String[] args) {
        String a = "000-111-222";
        String b = "000-222-222";
        String delimiter = "-";
        try {
            System.out.println("similarity(" + a + ", " + b + ") = " + similarity(a, b, delimiter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double similarity(String a, String b, String delimiter) {
        String[] x = a.split(delimiter);
        String[] y = b.split(delimiter);
        List<String> xL = Arrays.asList(x);
        List<String> yL = Arrays.asList(y);

        if (xL.size() == 0 || yL.size() == 0)
            return 0.0;

        Set<String> unionXY = new HashSet<String>(xL);
        unionXY.addAll(yL);

        Set<String> intersectionXY = new HashSet<String>(xL);
        intersectionXY.retainAll(yL);

        return (double) intersectionXY.size() / (double) unionXY.size();
    }
}
    
