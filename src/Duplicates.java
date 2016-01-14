import org.apache.commons.lang.StringEscapeUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jaspar.mang on 07.01.16.
 */
public class Duplicates {
    /*
    public static String url = "jdbc:postgresql://localhost:5432/testdb2";
    public static String user = "root";
    public static String password = "root";
    */
    public static String url = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String user = "infointe";
    public static String password = "InfoInte1516%";
    private static Compare compare = new Compare();

    public static void main(String args[]) {
        eliminate(1, 2);
    }

    public static int eliminate(int id1, int id2) {
        int newPk = -1;
        Connection con = null;
        PreparedStatement stEntry1 = null;
        PreparedStatement stEntry2 = null;
        ResultSet rsEntry1 = null;
        ResultSet rsEntry2 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM sportsman WHERE id = ?";
            stEntry1 = con.prepareStatement(sql);
            stEntry1.setInt(1, id1);
            rsEntry1 = stEntry1.executeQuery();
            stEntry2 = con.prepareStatement(sql);
            stEntry2.setInt(1, id2);
            rsEntry2 = stEntry2.executeQuery();
            if (rsEntry1.next() && rsEntry2.next()) {
                String[] mergedEntry = compare.compareResultSets(rsEntry1, rsEntry2);
                //TODO: autogenerate the SQL with ResultSetMetaData
                String insertSql = "INSERT INTO sportsman (firstname, lastname, birthdate, captain, \"position\", " +
                        "\"Anzahl Einsaetze\", club_id, playernumber, country, games, goals, assist, hand, contract_until, " +
                        "market_value, belt, coach, familynamelocal, middlenamelocal, playername, firstnamelocal, favoritetechnique, " +
                        "gender, middlename, " +
                        "shortname, side, \"birthCountry\", \"birthState\", \"birthCity\", \"deathYear\", \"deathMonth\", " +
                        "\"deathDay\", \"deathCountry\", \"deathState\", \"deathCity\", \"nameGiven\", weight, height, bats, throws, " +
                        "debut, \"finalGame\", \"retroID\", \"bbrefID\", comment, startdate, enddate, \"year\") " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement insertStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ResultSetMetaData metaData = rsEntry1.getMetaData();
                for (int i = 1; i <= 48; i++) {
                    if (mergedEntry[i] == null || mergedEntry[i].equals("null")) {
                        insertStatement.setNull(i, metaData.getColumnType(i + 1));
                    } else {
                        System.out.println(metaData.getColumnTypeName(i+1));
                        if (metaData.getColumnTypeName(i + 1).equals("date")) {
                            insertStatement.setDate(i, Date.valueOf(mergedEntry[i]));
                        } else if (metaData.getColumnTypeName(i + 1).equals("int4")) {
                            insertStatement.setInt(i, new Integer(mergedEntry[i]));
                        } else if (metaData.getColumnTypeName(i + 1).equals("float8")) {
                            insertStatement.setDouble(i, Double.parseDouble(mergedEntry[i]));
                        } else if (metaData.getColumnTypeName(i + 1).equals("bool")) {
                            if (mergedEntry[i].equals("t")) {
                                insertStatement.setBoolean(i, true);
                            } else {
                                insertStatement.setBoolean(i, false);
                            }
                        } else {
                            insertStatement.setString(i, StringEscapeUtils.escapeSql(mergedEntry[i]));
                        }
                    }
                }
                insertStatement.executeUpdate();
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (null != generatedKeys && generatedKeys.next()) {
                    newPk = generatedKeys.getInt(1);
                }
                if (newPk != -1) {
                    updateOccupationSportsman(id1, id2, newPk, con);
                    updateTeamSportsman(id1, id2, newPk, con);
                    updateHallOfFame(id1, id2, newPk, con);
                    updateCollegePlaying(id1, id2, newPk, con);
                    updateRanking(id1, id2, newPk, con);
                    PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM sportsman WHERE id = ? OR id = ?");
                    deleteStatement.setInt(1, id1);
                    deleteStatement.setInt(2, id2);
                    deleteStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rsEntry1 != null) rsEntry1.close();
                if (rsEntry2 != null) rsEntry2.close();
                if (stEntry1 != null) stEntry1.close();
                if (stEntry2 != null) stEntry2.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return newPk;
    }

    public static void updateOccupationSportsman(int id1, int id2, int newPk, Connection con) throws SQLException {
        String sql_occupation_sportsman = "SELECT occupation_id, count(occupation_id) as c FROM occupation_sportsman WHERE sportsman_id = ? or sportsman_id = ? GROUP BY occupation_id";
        PreparedStatement stGet_occupation_sportsman = con.prepareStatement(sql_occupation_sportsman);
        stGet_occupation_sportsman.setInt(1, id1);
        stGet_occupation_sportsman.setInt(2, id2);
        ResultSet rsGet_occupation_sportsman = stGet_occupation_sportsman.executeQuery();
        while (rsGet_occupation_sportsman.next()) {
            if (rsGet_occupation_sportsman.getInt("c") > 1) {
                String deleteSql = "DELETE FROM occupation_sportsman WHERE sportsman_id = ? and occupation_id = ?";
                PreparedStatement deleteSt = con.prepareStatement(deleteSql);
                deleteSt.setInt(1, id1);
                deleteSt.setInt(2, rsGet_occupation_sportsman.getInt("occupation_id"));
                deleteSt.executeUpdate();
            }
        }
        String updateSql = "UPDATE occupation_sportsman set sportsman_id = ? WHERE sportsman_id = ? or sportsman_id = ?";
        PreparedStatement updateSt = con.prepareStatement(updateSql);
        updateSt.setInt(1, newPk);
        updateSt.setInt(2, id1);
        updateSt.setInt(3, id2);
        updateSt.executeUpdate();
    }


    public static void updateHallOfFame(int id1, int id2, int newPk, Connection con) throws SQLException {
        String sql_hall_of_fame = "SELECT sportsman_id, \"year\", voted_by FROM hall_of_fame WHERE sportsman_id = ? or sportsman_id = ?";
        PreparedStatement stGet_hall_of_fame = con.prepareStatement(sql_hall_of_fame);
        stGet_hall_of_fame.setInt(1, id1);
        stGet_hall_of_fame.setInt(2, id2);
        ResultSet rsGet_hall_of_fame = stGet_hall_of_fame.executeQuery();
        while (rsGet_hall_of_fame.next()) {
            String updateSQL = "UPDATE hall_of_fame SET sportsman_id = ? WHERE sportsman_id = ? and \"year\" = ? and voted_by = ?";
            PreparedStatement updateSt = con.prepareStatement(updateSQL);
            updateSt.setInt(1, newPk);
            updateSt.setInt(2, rsGet_hall_of_fame.getInt("sportsman_id"));
            updateSt.setInt(3, rsGet_hall_of_fame.getInt("year"));
            updateSt.setString(4, rsGet_hall_of_fame.getString("voted_by"));
            updateSt.executeUpdate();

        }
    }

    public static void updateCollegePlaying(int id1, int id2, int newPk, Connection con) throws SQLException {
        String sql_hall_of_fame = "SELECT sportsman_id, school_id, \"year\" FROM college_playing WHERE sportsman_id = ? or sportsman_id = ?";
        PreparedStatement stGet_hall_of_fame = con.prepareStatement(sql_hall_of_fame);
        stGet_hall_of_fame.setInt(1, id1);
        stGet_hall_of_fame.setInt(2, id2);
        ResultSet rsGet_hall_of_fame = stGet_hall_of_fame.executeQuery();
        while (rsGet_hall_of_fame.next()) {
            String updateSQL = "UPDATE college_playing SET sportsman_id = ? WHERE sportsman_id = ? and \"year\" = ? and school_id = ?";
            PreparedStatement updateSt = con.prepareStatement(updateSQL);
            updateSt.setInt(1, newPk);
            updateSt.setInt(2, rsGet_hall_of_fame.getInt("sportsman_id"));
            updateSt.setInt(3, rsGet_hall_of_fame.getInt("year"));
            updateSt.setString(4, rsGet_hall_of_fame.getString("school_id"));
            updateSt.executeUpdate();

        }
    }

    public static void updateRanking(int id1, int id2, int newPk, Connection con) throws SQLException {
        String sql_team_sportsman = "SELECT id, count(id) as c FROM ranking WHERE sportsman_id = ? or sportsman_id = ? GROUP BY id";
        PreparedStatement stGet_team_sportsman = con.prepareStatement(sql_team_sportsman);
        stGet_team_sportsman.setInt(1, id1);
        stGet_team_sportsman.setInt(2, id2);
        ResultSet rsGet_team_sportsman = stGet_team_sportsman.executeQuery();
        while (rsGet_team_sportsman.next()) {
            if (rsGet_team_sportsman.getInt("c") > 1) {
                String deleteSql = "DELETE FROM ranking WHERE sportsman_id = ? and team_id = ?";
                PreparedStatement deleteSt = con.prepareStatement(deleteSql);
                deleteSt.setInt(1, id1);
                deleteSt.setInt(2, rsGet_team_sportsman.getInt("id"));
                deleteSt.executeUpdate();
            }
        }
        String updateSql = "UPDATE ranking set sportsman_id = ? WHERE sportsman_id = ? or sportsman_id = ?";
        PreparedStatement updateSt = con.prepareStatement(updateSql);
        updateSt.setInt(1, newPk);
        updateSt.setInt(2, id1);
        updateSt.setInt(3, id2);
        updateSt.executeUpdate();
    }

    public static void updateTeamSportsman(int id1, int id2, int newPk, Connection con) throws SQLException {
        String sql_team_sportsman = "SELECT team_id, count(team_id) as c FROM team_sportsman WHERE sportsman_id = ? or sportsman_id = ? GROUP BY team_id";
        PreparedStatement stGet_team_sportsman = con.prepareStatement(sql_team_sportsman);
        stGet_team_sportsman.setInt(1, id1);
        stGet_team_sportsman.setInt(2, id2);
        ResultSet rsGet_team_sportsman = stGet_team_sportsman.executeQuery();
        while (rsGet_team_sportsman.next()) {
            if (rsGet_team_sportsman.getInt("c") > 1) {
                String deleteSql = "DELETE FROM team_sportsman WHERE sportsman_id = ? and team_id = ?";
                PreparedStatement deleteSt = con.prepareStatement(deleteSql);
                deleteSt.setInt(1, id1);
                deleteSt.setInt(2, rsGet_team_sportsman.getInt("team_id"));
                deleteSt.executeUpdate();
            }
        }
        String updateSql = "UPDATE team_sportsman set sportsman_id = ? WHERE sportsman_id = ? or sportsman_id = ?";
        PreparedStatement updateSt = con.prepareStatement(updateSql);
        updateSt.setInt(1, newPk);
        updateSt.setInt(2, id1);
        updateSt.setInt(3, id2);
        updateSt.executeUpdate();
    }


    private static class Compare {
        public int compareInt(int value1, int value2) {
            return value1;
        }

        public String compareString(String value1, String value2) {
            if (value1 == null || value1.equals("null")) return value2;
            if (value2 == null || value2.equals("null")) return value1;
            if (value1.compareTo(value2) <= 0) {
                return value1;
            } else {
                return value2;
            }
        }

        public String[] compareResultSets(ResultSet rs1, ResultSet rs2) throws SQLException {
            ArrayList<String> result = new ArrayList<String>();
            int columnCount = rs1.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                result.add(this.compareString(rs1.getString(i), rs2.getString(i)));
            }
            return result.toArray(new String[result.size()]);
        }


    }
}
