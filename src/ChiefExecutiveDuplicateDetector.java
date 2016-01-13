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
        /* Merge duplicates.
        for(String key : duplicates.keySet()){
            int firstKey = new Integer(key);
            for(String secondKey : duplicates.get(key)){
                firstKey = Duplicates.eliminate(firstKey, new Integer(secondKey));
            }

        }
        */
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
        System.out.println(count);
        for (String key: keysToRemove) {
            duplicates.remove(key);
        }
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
    }

    private static List<String> DFS(HashMap<String, List<String>> duplicates, String node, String originalNode, HashMap<String, Boolean> visited, List<String> newElements) {
        visited.put(node, true);

        if (duplicates.containsKey(node)) {
            for (String edge : duplicates.get(node)) {
                if (visited.containsKey(edge) && visited.get(edge) == false) {
                    System.out.println("found " + edge);
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

                    if (firstNameEditDistance <= 2 && lastNameEditDistance <= 2 && sm1.getKey().compareTo("") != 0 && sm2.getKey().compareTo("") != 0) {
                        System.out.println("comapring " + j + " to " + k);
                        System.out.println(firstNameEditDistance + ", " + lastNameEditDistance);

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

        System.out.println(duplicates.size());
        return duplicates;
    }

    private static ArrayList<Sportsman> readAndSortSportsman() {
        ArrayList<Sportsman> data = new ArrayList<>();

        try {
            Connection connection = getConnectionToLocalDb();

            Statement statement = connection.createStatement();

            String sql = "SELECT * from sportsman;";

            statement.execute(sql);
            ResultSet results = statement.getResultSet();

            ResultSetMetaData rmd = results.getMetaData();
            int columnCount = rmd.getColumnCount();

            int rowIterator = 0;
            while(results.next()) {

                ArrayList<String> tempData = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    String result = results.getString(i);
                    if (result == null) result = "0";
                    tempData.add(result);
                }

                if (rowIterator == 25259) {
                    System.out.println("fou");
                }

                //Key bestimmen
                String firstNameKey = "";
                String lastNameKey = "";
                if (tempData.get(1).length() >= 2) {
                    firstNameKey += tempData.get(1).charAt(0);
                    firstNameKey += tempData.get(1).charAt(1);
                }
                if (tempData.get(2).length() >= 2) {
                    lastNameKey += tempData.get(2).charAt(0);
                    lastNameKey += tempData.get(2).charAt(1);
                }

                Sportsman tempSportsman = new Sportsman();
                tempSportsman.setKey((firstNameKey + lastNameKey).toLowerCase());
                tempSportsman.setValues(tempData);

                data.add(tempSportsman);

                rowIterator++;
                System.out.println(rowIterator);
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

    private static ArrayList<Sportsman> mergeSort (ArrayList<Sportsman> data) {
        if (data.size() <= 1) {
            return data;
        }

        ArrayList<Sportsman> first = new ArrayList<>(data.subList(0, data.size()/2));
        ArrayList<Sportsman> second = new ArrayList<>(data.subList(first.size(), data.size()));


        // Sort each half
        mergeSort(first);
        mergeSort(second);

        // Merge the halves together, overwriting the original array
        merge(first, second, new ArrayList<Sportsman>());
        return data;
    }

    private static void merge(ArrayList<Sportsman> first, ArrayList<Sportsman> second, ArrayList<Sportsman> result) {
        // Merge both halves into the result array
        // Next element to consider in the first array
        int iFirst = 0;
        // Next element to consider in the second array
        int iSecond = 0;

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
        if (j < first.size() - iFirst) {
            result.addAll(new ArrayList<>(first.subList(iFirst, j)));
        }
        if (j < second.size() - iSecond) {
            result.addAll(new ArrayList<>(second.subList(iSecond, j)));
        }
    }

    private static Connection getConnectionToLocalDb() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/testdb3";
        String user = "root";
        String password = "root";

        Connection connection = null;

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);

        System.out.println("Kutmasta Kurt am Beat und Datenbank am Mic!");

        return connection;
    }

}
