import java.sql.*;
import java.util.*;

/**
 * Created by dennis on 07.01.16.
 */
public class ChiefExecutiveDuplicateDetector {

    public static void main (String[] args) {
        ArrayList<Sportsman> sportsmen = readAndSortSportsman();
        HashMap<String, List<String>> duplicates = findDuplicates(sportsmen);
        computeTransitiveClosureDFS(duplicates);
        removeRedundantEntries(duplicates);

        // Merge duplicates.
        for(String key : duplicates.keySet()){
            int firstKey = new Integer(key);
            for(String secondKey : duplicates.get(key)){
                firstKey = Duplicates.eliminate(firstKey, new Integer(secondKey));
            }

        }

    }

    private static void removeRedundantEntries(HashMap<String, List<String>> duplicates) {
        for (String key: duplicates.keySet()) {
            for (String entry: duplicates.get(key)) {
                if (duplicates.containsKey(entry)) {
                    for (String newEntry: duplicates.get(entry)) {
                        if (!duplicates.get(entry).contains(newEntry)) {
                            duplicates.get(entry).add(newEntry);
                        }
                    }
                    duplicates.get(entry).add(0, "remove");
                }
            }
        }
        int count = 0;
        List<String> keysToRemove = new LinkedList<>();
        for (String key: duplicates.keySet()) {
            if (duplicates.get(key).get(0).compareTo("remove") == 0) {
                count++;
                keysToRemove.add(key);
            }
        }
        for (String key: keysToRemove) {
            duplicates.remove(key);
        }
        int size = 0;
        for (String k : duplicates.keySet()) size = size + duplicates.get(k).size();
        System.out.println("removeRedundantEntries: " + (duplicates.keySet().size() + size));
    }

    private static void computeTransitiveClosureDFS(HashMap<String, List<String>> duplicates) {
        HashMap<String, Boolean> visited = new HashMap<>();

        for (String node: duplicates.keySet()) {
            for (String key: duplicates.keySet()) {
                visited.put(key, false);
            }
            List<String> newElements = new LinkedList<>();
            newElements = DFS(duplicates, node, node, visited, newElements);
            duplicates.get(node).addAll(newElements);
        }
        int size = 0;
        for (String k : duplicates.keySet()) size = size + duplicates.get(k).size();
        System.out.println("computeTransitiveClosureDFS: " + (duplicates.keySet().size() + size));
    }

    private static List<String> DFS(HashMap<String, List<String>> duplicates, String node, String originalNode, HashMap<String, Boolean> visited, List<String> newElements) {
        visited.put(node, true);

        if (duplicates.containsKey(node)) {
            for (String edge : duplicates.get(node)) {
                if (visited.containsKey(edge) && visited.get(edge) == false) {
                    if (!duplicates.get(originalNode).contains(edge)) {
                        newElements.add(edge);
                    }
                    DFS(duplicates, edge, originalNode, visited, newElements);
                }
            }
        }
        return newElements;
    }

    private static HashMap<String, List<String>> findDuplicates(ArrayList<Sportsman> sportsmen) {
        HashMap<String, List<String>> duplicates = new HashMap<>();

        int WINDOW_SIZE = 10;

        //sliding window

        for (int i = 0; i < sportsmen.size() - WINDOW_SIZE; i++) {
            for (int j = i; j < i + WINDOW_SIZE; j++) {
                for (int k = 1 + j; k < i + WINDOW_SIZE; k++) {
                    // We don't want to compare a tuple to itself
                    if (j == k) continue;

                    Sportsman sm1 = sportsmen.get(j);
                    Sportsman sm2 = sportsmen.get(k);

                    //compare sm1 to sm2
                    //if they're possible duplicates add them to the hashmap
                    int firstNameEditDistance = distance(sm1.getValues().get(1), sm2.getValues().get(1));
                    int lastNameEditDistance = distance(sm1.getValues().get(2), sm2.getValues().get(2));
                    int birthdayEditDistance = distance(sm1.getValues().get(3), sm2.getValues().get(3));
                    double birthdayTokenSimilarity = similarity(sm1.getValues().get(3), sm2.getValues().get(3), "-");

                    if (firstNameEditDistance <= 1 && lastNameEditDistance <= 1 && birthdayTokenSimilarity <= 0.7 && sm1.getKey().compareTo("") != 0 && sm2.getKey().compareTo("") != 0) {
                        if (firstNameEditDistance > 0 || lastNameEditDistance > 0 || birthdayEditDistance > 0) {
                            //System.out.println("found one");
                        }
                        if (!duplicates.containsKey(sm1.getValues().get(0))) {
                            duplicates.put(sm1.getValues().get(0), new LinkedList<String>());
                        }
                        if (!(duplicates.get(sm1.getValues().get(0)).contains(sm2.getValues().get(0)))) {
                            duplicates.get(sm1.getValues().get(0)).add(sm2.getValues().get(0));
                        }

                    }
                }
            }
        }
        int size = 0;
        for (String k : duplicates.keySet()) size = size + duplicates.get(k).size();
        System.out.println("findDuplicates: " + (duplicates.keySet().size() + size));
        return duplicates;
    }

    private static ArrayList<Sportsman> readAndSortSportsman() {
        ArrayList<Sportsman> data = new ArrayList<>();

        try {
            Connection connection = getConnectionToLocalDb();

            Statement statement = connection.createStatement();

            String sql = "SELECT * from sportsman ORDER BY firstname, lastname, birthdate;";

            statement.execute(sql);
            ResultSet results = statement.getResultSet();

            ResultSetMetaData rmd = results.getMetaData();
            int columnCount = rmd.getColumnCount();

            while(results.next()) {

                ArrayList<String> tempData = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    String result = results.getString(i);
                    if (result == null) result = "0";
                    tempData.add(result);
                }



                //Key bestimmen
                String firstNameKey = "";
                String lastNameKey = "";
                String dateKey = "";
                if (tempData.get(1).length() >= 2) {
                    firstNameKey += tempData.get(1).charAt(0);
                    firstNameKey += tempData.get(1).charAt(1);
                }
                if (tempData.get(2).length() >= 2) {
                    lastNameKey += tempData.get(2).charAt(0);
                    lastNameKey += tempData.get(2).charAt(1);
                }
                if (tempData.get(3).length() > 0) {
                    dateKey = tempData.get(3);
                }

                Sportsman tempSportsman = new Sportsman();
                tempSportsman.setKey((firstNameKey + lastNameKey/* + dateKey*/).toLowerCase());
                tempSportsman.setValues(tempData);

                data.add(tempSportsman);

            }

            data = mergeSort(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        int[] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    private static double similarity(String a, String b, String delimiter) {
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

    private static ArrayList<Sportsman> mergeSort (ArrayList<Sportsman> data) {
        if (data.size() <= 1) {
            return data;
        }
        
        ArrayList<Sportsman> first = new ArrayList<>(data.subList(0, data.size()/2));
        ArrayList<Sportsman> second = new ArrayList<>(data.subList(first.size(), data.size()));


        // Sort each half
        first = mergeSort(first);
        second = mergeSort(second);

        // Merge the halves together, overwriting the original array
        data = merge(first, second, new ArrayList<Sportsman>());
        return data;
    }

    private static ArrayList<Sportsman> merge(ArrayList<Sportsman> first, ArrayList<Sportsman> second, ArrayList<Sportsman> result) {
        // Merge both halves into the result array
        // Next element to consider in the first array
        int iFirst = 0;
        // Next element to consider in the second array
        int iSecond = 0;
        int targetSize = first.size() + second.size();

        // Next open position in the result
        int j = 0;
        // As long as neither iFirst nor iSecond is past the end, move the
        // smaller element into the result.
        while (iFirst < first.size() && iSecond < second.size()) {
            if (first.get(iFirst).getKey().compareTo(second.get(iSecond).getKey()) < 0) {
                if (j >= result.size()) {
                    result.add(j, first.get(iFirst));
                } else {
                    result.set(j, first.get(iFirst));
                }
                iFirst++;
            } else {
                if (j >= result.size()) {
                    result.add(j, second.get(iSecond));
                } else {
                    result.set(j, second.get(iSecond));
                }
                iSecond++;
            }
            j++;
        }

        // copy what's left
        if (j < targetSize && iFirst < first.size()) {
            result.addAll(new ArrayList<>(first.subList(iFirst, first.size())));
        }
        if (j < targetSize && iSecond < second.size()) {
            result.addAll(new ArrayList<>(second.subList(iSecond, second.size())));
        }
        return result;
    }

    private static Connection getConnectionToLocalDb() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://sebastiankoall.de/infointe";
        String user = "infointe";
        String password = "InfoInte1516%";

        Connection connection = null;

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);

        System.out.println("Kutmasta Kurt am Beat und Datenbank am Mic!");

        return connection;
    }

}
