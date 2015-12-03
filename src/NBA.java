import org.apache.commons.lang.StringEscapeUtils;

import java.sql.*;

/**
 * Created by jaspar.mang on 01.12.15.
 */
public class NBA {
    public static String url = "jdbc:postgresql://localhost:5432/testdb";
    public static String user = "root";
    public static String password = "root%";
    public static String serverUrl = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String serverUser = "infointe";
    public static String serverPassword = "InfoInte1516%";

    public static void main(String args[]){
        Connection con = null;
        Statement statement_team = null;
        ResultSet result_team = null;
        Statement statement_player = null;
        ResultSet result_player = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            statement_team = con.createStatement();

            //team holen und einfügen
            // alle spieler für dieses team einfügen
            result_team = statement_team.executeQuery("SELECT * FROM team");
            while (result_team.next()) {
                int teamId = insertTeam(result_team.getString("teamname"), result_team.getFloat("min"), result_team.getInt("possfor"), result_team.getInt("possopp"), result_team.getInt("pointsfor"),
                        result_team.getInt("pointopp"), result_team.getFloat("offrtg"), result_team.getFloat("defrtg"), result_team.getFloat("overallrtg"), result_team.getInt("orebfor"),
                        result_team.getInt("orebopp"), result_team.getInt("drebfor"), result_team.getInt("drebopp"), result_team.getFloat("orebrate"), result_team.getFloat("drebrate"));
                statement_player = con.createStatement();
                result_player = statement_player.executeQuery("SELECT * FROM player WHERE teamname ='"+ StringEscapeUtils.escapeSql(result_team.getString("teamname"))+"'");
                while(result_player.next()){
                    int sportmanId = insertSportsman(result_player.getString("playername"), result_player.getString("playertruename"), result_player.getString("league"), result_player.getString("year"),
                            result_player.getFloat("position"), result_player.getString("comment"), result_player.getString("startdate"), result_player.getString("enddate"));
                    insertTeamSportsman(teamId, sportmanId);
                }
            }


        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (result_team != null) {
                    result_team.close();
                }
                if (result_player != null) {
                    result_player.close();
                }
                if (statement_team != null) {
                    statement_team.close();
                }
                if (statement_player != null) {
                    statement_player.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static int insertTeam(String teamName, float min, int possfor, int possopp, int pointsfor,
                                   int pointopp, float offrtg, float defrtg, float overallrtg,
                                   int orebfor, int orebopp, int drebfor, int drebopp,
                                   float orebrate, float drebrate){
        int primary_key = -1;
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            con = DriverManager.getConnection(serverUrl, serverUser, serverPassword);
            statement = con.createStatement();
            String sql = "INSERT INTO team(name , min, possfor, possopp, pointsfor, pointopp, offrtg, " +
                    "defrtg, overallrtg, orebfor, orebopp, drebfor, drebopp, orebrate, drebrate)" +
                    "VALUES ('" + StringEscapeUtils.escapeSql(teamName) +"', "+ min +", "+ possfor +", "+ possopp +", "+pointsfor+", "+
                    pointopp +", "+ offrtg +", "+ defrtg +", "+ overallrtg +", "+ orebfor +", "+ orebopp +", "+
                    drebfor +", "+ drebopp +", "+ orebrate +", "+ drebrate +")";
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            result = statement.getGeneratedKeys();
            if (result.next()) {
                primary_key = result.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return primary_key;
    }


    private static int insertLeague(String league){
        int primary_key = -1;
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            int ligaId = -1;
            con = DriverManager.getConnection(serverUrl, serverUser, serverPassword);
            statement = con.createStatement();
            String sql = "INSERT INTO liga (verband) VALUES ('"+ StringEscapeUtils.escapeSql(league) +"')";
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            result = statement.getGeneratedKeys();
            if (result.next()) {
                ligaId = result.getInt(1);
            }
            statement = con.createStatement();
            sql = "INSERT INTO club (liga) VALUES ("+ ligaId +")";
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            result = statement.getGeneratedKeys();
            if (result.next()) {
                primary_key = result.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return primary_key;
    }

    private static int insertSportsman(String playerName, String playerTrueName, String league, String year,
                                       float position, String comment, String startDate, String endDate){
        int clubId = insertLeague(league);
        String[] parts = playerTrueName.split(", ");
        String lastName = parts[0];
        String firstName = parts[1];
        int primary_key = -1;
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            con = DriverManager.getConnection(serverUrl, serverUser, serverPassword);
            statement = con.createStatement();
            String sql = "INSERT INTO sportsman(playername, firstname, lastname, club_id, year, position, comment, startdate, enddate) " +
                    "VALUES ('"+ StringEscapeUtils.escapeSql(playerName) +"', '"+ StringEscapeUtils.escapeSql(firstName) +"', '"+ StringEscapeUtils.escapeSql(lastName)  +
                    "', "+ clubId +", '"+ StringEscapeUtils.escapeSql(year) +"', '"+ position  +"', '"+ StringEscapeUtils.escapeSql(comment) +"', '"+
                    StringEscapeUtils.escapeSql(startDate) +"', '"+ StringEscapeUtils.escapeSql(endDate) +"')";
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            result = statement.getGeneratedKeys();
            if (result.next()) {
                primary_key = result.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return primary_key;

    }

    private static void insertTeamSportsman(int teamId, int sportsmanId){
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            con = DriverManager.getConnection(serverUrl, serverUser, serverPassword);
            statement = con.createStatement();
            String sql = "INSERT INTO team_sportsman (team_id, sportsman_id) VALUES ("+teamId+", "+sportsmanId+")";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


}
