import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dennis on 07.12.15.
 */
public class ChiefExecutiveIntegrationManager {

    public static void main (String[] args) {
        ArrayList<ArrayList<String>> competitors = new ArrayList<>();
        ArrayList<ArrayList<String>> fights = new ArrayList<>();
        ArrayList<ArrayList<String>> persons = new ArrayList<>();

        try {
            competitors = readDataFromTable("judobase.competitors");
            fights = readDataFromTable("judobase.fights");
            persons = readDataFromTable("wikidata.person");

            System.out.println(competitors.size() + " - " + fights.size());

            writeCompetitorsAndFightsToRemoteTable(competitors, fights);
            writePersonsToRemoteTable(persons);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<String>> readDataFromTable(String table) throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/";
        String user = "dennis";
        String password = "";

        Connection connection = null;

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);

        System.out.println("Kutmasta Kurt am Beat und Datenbank am Mic!");

        DatabaseMetaData dbMeta = connection.getMetaData();
        ResultSet pKey = dbMeta.getPrimaryKeys(null, table.split("\\.")[0], table.split("\\.")[1]);

        while (pKey.next()) {
            System.out.println(pKey.getString(4));
            System.out.println(pKey.getString(5));
        }

        Statement statement = connection.createStatement();

        String sql = "SELECT * from "+ table + ";";

        statement.execute(sql);
        ResultSet results = statement.getResultSet();

        ResultSetMetaData rmd = results.getMetaData();
        int columnCount = rmd.getColumnCount();

        ArrayList<ArrayList<String>> resultMatrix = new ArrayList<>();

        int columnIterator = 1, rowIterator = 0;
        while(results.next()) {
            if (resultMatrix.size() == rowIterator) resultMatrix.add(new ArrayList<String>());
            String result = results.getString(columnIterator);
            if (result == null) result = "0";
            resultMatrix.get(rowIterator).add(result);
            if (columnIterator == columnCount) {
                columnIterator = 0;
                rowIterator++;
            }
            columnIterator++;
        }

        for (int i = 0; i < resultMatrix.size(); i++) {
            for (int j = 0; j < resultMatrix.get(i).size(); j++) {
                System.out.print(resultMatrix.get(i).get(j) + " ");
            }
            System.out.println("");
        }

        statement.close();
        return resultMatrix;
    }

    public static void writeCompetitorsAndFightsToRemoteTable(ArrayList<ArrayList<String>> competitors, ArrayList<ArrayList<String>> fights) throws SQLException, ClassNotFoundException {
        Connection connection = getConnectionToRemoteDatabase();

        int count = 1;

        HashMap<Integer, Integer> primaryKeyMapping = new HashMap<>();
        ResultSet compPrimaryKeys = null;
        ArrayList<Integer> pkList = new ArrayList<>();

        for (ArrayList<String> tuple: competitors) {
            if (tuple.size() < 15) break;
            Statement statement = connection.createStatement();

            String side = (tuple.get(16).length() > 1) ? "0" : tuple.get(16);

            String sql = "INSERT INTO sportsman (belt, birthdate, coach, country, firstname, lastname, familynamelocal, middlenamelocal, gender, height, shortname, side, favoritetechnique) " +
                    "VALUES ('"+tuple.get(1)+"', '"+tuple.get(2)+"', '"+tuple.get(4).replace('\'', '`')+"', '"+tuple.get(5)+"', '"+tuple.get(10) + " " + tuple.get(13)+"', '"+ tuple.get(6) +
                    "', '"+tuple.get(11)+"', '"+tuple.get(14)+"', '"+tuple.get(9)+"', '"+tuple.get(12)+"', '"+tuple.get(15)+"', '"+side+"', '"+tuple.get(8)+"');";


            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            compPrimaryKeys = statement.getGeneratedKeys();
            compPrimaryKeys.next();
            pkList.add(compPrimaryKeys.getInt(1));

            statement.close();
            System.out.println(count++);
        }

        int iterator = 0;
        System.out.println(competitors.size());
        System.out.println(pkList.size());
        for (ArrayList<String> tuple: competitors) {
            if (iterator >= pkList.size()) {
                System.out.println("OHNO!");
                break;
            }
            primaryKeyMapping.put(Integer.parseInt(tuple.get(0)), pkList.get(iterator));
            iterator++;
        }

        count = 1;
        int disjunctFkCount = 0;

        for (ArrayList<String> tuple: fights) {
            if (tuple.size() < 21) break;
            Statement statement = connection.createStatement();

            Integer white = primaryKeyMapping.get(Integer.parseInt(tuple.get(1)));
            if (white == null) {
                white = 99999;
                disjunctFkCount++;
            }
            Integer blue = primaryKeyMapping.get(Integer.parseInt(tuple.get(1)));
            if (blue == null) {
                blue = 99999;
                disjunctFkCount++;
            }
            Integer winner = primaryKeyMapping.get(Integer.parseInt(tuple.get(1)));
            if (winner == null) {
                winner = 99999;
                disjunctFkCount++;
            }

            System.out.println("new FK for white: " + white);
            System.out.println("new FK for blue: " + blue);
            System.out.println("new FK for winner: " + winner);

            String sql = "INSERT INTO fight (white, blue, winner, competition, \"date\", duration, fightnumber, ipponblue, ipponwhite, penaltyblue, penaltywhite, round, " +
                    "roundcode, roundname, sccountdownoffset, tagged, wazablue, wazawhite, weight, yukoblue, yukowhite) " +
                    "VALUES ('"+white+"','"+blue+"','"+winner+"','"+tuple.get(4)+"','"+tuple.get(5)+"','"+tuple.get(6)+"','"+tuple.get(7)+"'," +
                    "'"+tuple.get(8)+"','"+tuple.get(9)+"','"+tuple.get(10)+"','"+tuple.get(11)+"','"+tuple.get(12)+"','"+tuple.get(13)+"','"+tuple.get(14)+"'," +
                    "'"+tuple.get(15)+"','"+tuple.get(16)+"','"+tuple.get(17)+"','"+tuple.get(18)+"','"+tuple.get(19)+"','"+tuple.get(20)+"','"+tuple.get(21)+"');";

            statement.executeUpdate(sql);
            statement.close();
            System.out.println(count++);
        }
        System.out.println((double)((double)disjunctFkCount/(double)(3*count)));
    }

    public static void writePersonsToRemoteTable(ArrayList<ArrayList<String>> persons) throws SQLException, ClassNotFoundException {
        Connection connection = getConnectionToRemoteDatabase();

        int j = 1;

        for (ArrayList<String> tuple: persons) {
            if (tuple.size() != 8) break;

            Statement statement = connection.createStatement();

            String firstName = "";
            String lastName = "";
            String[] nameTokens = tuple.get(1).split(" ");
            for (int i = 0; i < nameTokens.length - 1; i++) {
                firstName += nameTokens[i] + " ";
            }
            lastName = nameTokens[nameTokens.length-1];

            String gender = (tuple.get(2).equals("http://www.wikidata.org/entity/Q6581097")) ? "male" : "female";

            String[] deathTokens = tuple.get(6).split("-");
            String deathYear = "0";
            String deathMonth = "0";
            String deathDay = "0";
            if (deathTokens.length == 3) {
                deathYear = deathTokens[0];
                deathMonth = deathTokens[1];
                deathDay = "" + deathTokens[2].charAt(0) + deathTokens[2].charAt(1);
            }

            String sql = "INSERT INTO sportsman (firstname, lastname, gender, birthdate, \"birthCity\", \"deathYear\", \"deathMonth\", \"deathDay\", occupation) " +
                    "VALUES (\'"+firstName+"\', '"+lastName+"', '"+gender+"', '"+tuple.get(3).substring(0, 10)+"', '"+ tuple.get(4)+"', '"+deathYear+
                    "', '"+deathMonth+"', '"+deathDay+"', '"+tuple.get(7)+"');";

            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            statement.close();
            System.out.println(j++);
        }
    }

    public static Connection getConnectionToRemoteDatabase() throws ClassNotFoundException, SQLException {
        String serverUrl = "jdbc:postgresql://sebastiankoall.de/infointe";
        String serverUser = "infointe";
        String serverPassword = "InfoInte1516%";
//        String serverUrl = "jdbc:postgresql://localhost:5432/integrated";
//        String serverUser = "dennis";
//        String serverPassword = "";

        Connection connection = null;

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(serverUrl, serverUser, serverPassword);

        return connection;

    }
}