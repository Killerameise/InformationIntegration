import org.apache.commons.lang.StringEscapeUtils;

import java.sql.*;

/**
 * Created by jaspar.mang on 07.01.16.
 */
public class OccupationFaultCorrect {
    public static String url = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String user = "infointe";
    public static String password = "InfoInte1516%";

    public static void main(String args[]){
        correct();
    }

    public static void correct() {

        Connection con = null;
        Statement stPlayer = null;
        Statement st = null;
        Statement stOccupation = null;
        ResultSet rsPlayer = null;
        ResultSet rs = null;
        ResultSet rsOccupation = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            stPlayer = con.createStatement();

            rsPlayer = stPlayer.executeQuery("SELECT firstname, lastname, birthdate, \"birthCity\" " +
                    "FROM faults GROUP BY firstname, lastname, birthdate, \"birthCity\"");
            while (rsPlayer.next()) {
                st = con.createStatement();

                rs = st.executeQuery("SELECT id, occupation FROM faults " +
                        "WHERE firstname = '"+StringEscapeUtils.escapeSql(rsPlayer.getString("firstname"))+ "' " +
                        "AND lastname = '"+StringEscapeUtils.escapeSql(rsPlayer.getString("lastname")) + "' " +
                        "AND birthdate = '"+StringEscapeUtils.escapeSql(rsPlayer.getString("birthdate")) + "' " +
                        "AND \"birthCity\" = '"+StringEscapeUtils.escapeSql(rsPlayer.getString("birthCity"))+"'");
                int sportsman_id = -1;
                if(rs.next()){
                    int occupation_id = -1;
                    sportsman_id = rs.getInt("id");
                    stOccupation = con.createStatement();
                    rsOccupation = stOccupation.executeQuery("SELECT id, occupation FROM occupation " +
                            "WHERE occupation = '"+ StringEscapeUtils.escapeSql(rs.getString("occupation")) +"'");
                    if(rsOccupation.next()){
                        occupation_id = rsOccupation.getInt("id");
                    }else{
                        stOccupation = con.createStatement();
                        stOccupation.executeUpdate("INSERT INTO occupation(occupation) " +
                                "VALUES ('" + StringEscapeUtils.escapeSql(rs.getString("occupation")) + "')",
                                stOccupation.RETURN_GENERATED_KEYS);
                        rsOccupation = stOccupation.getGeneratedKeys();
                        if (rsOccupation.next()) {
                            occupation_id = rsOccupation.getInt(1);
                        }
                    }
                    stOccupation = con.createStatement();
                    stOccupation.executeUpdate("INSERT INTO occupation_sportsman(occupation_id, sportsman_id) " +
                                    "VALUES (" + occupation_id + ", "+ sportsman_id +")");
                }
                while(rs.next()) {
                    int occupation_id = -1;
                    stOccupation = con.createStatement();
                    rsOccupation = stOccupation.executeQuery("SELECT * FROM occupation " +
                            "WHERE occupation = '"+ StringEscapeUtils.escapeSql(rs.getString("occupation")) +"'");
                    if(rsOccupation.next()){
                        occupation_id = rsOccupation.getInt("id");
                    }else{
                        stOccupation = con.createStatement();
                        stOccupation.executeUpdate("INSERT INTO occupation(occupation) " +
                                        "VALUES ('" + StringEscapeUtils.escapeSql(rs.getString("occupation")) + "')",
                                stOccupation.RETURN_GENERATED_KEYS);
                        rsOccupation = stOccupation.getGeneratedKeys();
                        if (rsOccupation.next()) {
                            occupation_id = rsOccupation.getInt(1);
                        }
                    }
                    stOccupation = con.createStatement();
                    stOccupation.executeUpdate("INSERT INTO occupation_sportsman(occupation_id, sportsman_id) " +
                            "VALUES (" + occupation_id + ", "+ sportsman_id +")");
                    stOccupation = con.createStatement();
                    stOccupation.executeUpdate("DELETE FROM sportsman WHERE id = " + rs.getInt("id"));
                }
            }


        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (rsPlayer != null) {
                    rsPlayer.close();
                }
                if (stPlayer != null) {
                    stPlayer.close();
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