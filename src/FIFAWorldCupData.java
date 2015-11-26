import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Created by jaspar.mang on 25.11.15.
 * This code is reay
 */
public class FIFAWorldCupData {
    public static String url = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String user = "infointe";
    public static String password = "InfoInte1516%";
    public static String file = "/Users/jaspar.mang/Downloads/players.csv";

    public static void main(String args[]){
        insertClub();
        insertTeam();
        insertPlayer();
    }
    public static void insertClub() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;


        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String zeile = null;
            zeile = in.readLine();

            while ((zeile = in.readLine()) != null) {
                String[] values = zeile.split(",");

                //System.out.println("Gelesene Zeile: " + values[0] + values[1]);


                boolean exsist = false;
                boolean exsistLiga = false;

                try {
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();

                    rs = st.executeQuery("SELECT \"name\" FROM \"club\" WHERE \"name\"='" + values[11] + "'");
                    if (rs.next()) {
                        exsist = true;
                    }

                    rs = st.executeQuery("SELECT \"verband\" FROM \"liga\" WHERE \"verband\"='" + values[12] + "'");
                    if (rs.next()) {
                        exsistLiga = true;
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                if (!exsist) {
                    try {
                        int key = -1;
                        PreparedStatement pst = null;
                        con = DriverManager.getConnection(url, user, password);
                        st = con.createStatement();
                        if (!exsistLiga) {
                            String stm2 = "INSERT INTO \"liga\"(\"verband\") VALUES('" + values[12] + "')";
                            st.executeUpdate(stm2, Statement.RETURN_GENERATED_KEYS);
                            ResultSet result = st.getGeneratedKeys();
                            if (result.next()) {
                                key = result.getInt(1);
                            }
                        } else {
                            String stm2 = "SELECT \"id\" FROM \"liga\" WHERE \"verband\"='" + values[12] + "'";
                            ResultSet result = st.executeQuery(stm2);
                            if (result.next()) {
                                key = result.getInt("id");
                            }
                        }
                        String stm = "INSERT INTO \"club\"(\"name\", \"liga\") VALUES(?, ?)";
                        pst = con.prepareStatement(stm);
                        pst.setString(1, values[11]);
                        pst.setInt(2, key);
                        pst.executeUpdate();


                    } catch (SQLException ex) {
                        ex.printStackTrace();

                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (st != null) {
                                st.close();
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    public static void insertTeam() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String zeile = null;
            zeile = in.readLine();

            while ((zeile = in.readLine()) != null) {
                String[] values = zeile.split(",");

                //System.out.println("Gelesene Zeile: " + values[0] + values[1]);


                boolean exsist = false;
                try {
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();

                    rs = st.executeQuery("SELECT \"name\" FROM \"team\" WHERE \"name\"='" + values[1] + "'");
                    if (rs.next()) {
                        exsist = true;
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                if (!exsist) {
                    try {
                        PreparedStatement pst = null;
                        con = DriverManager.getConnection(url, user, password);
                        st = con.createStatement();
                        String stm = "INSERT INTO \"team\"(\"group\", \"name\") VALUES(?, ?)";
                        pst = con.prepareStatement(stm);
                        pst.setString(1, values[0]);
                        pst.setString(2, values[1]);
                        pst.executeUpdate();


                    } catch (SQLException ex) {
                        ex.printStackTrace();

                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (st != null) {
                                st.close();
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }


    public static void insertPlayer() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        int key = -1;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String zeile = null;
            zeile = in.readLine();

            while ((zeile = in.readLine()) != null) {
                String[] values = zeile.split(",");

                //System.out.println("Gelesene Zeile: " + values[0] + values[1]);
                int teamId = -1;
                int clubId = -1;


                try {
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();

                    rs = st.executeQuery("SELECT \"id\" FROM \"team\" WHERE \"name\"='" + values[1] + "'");
                    if (rs.next()) {
                        teamId = rs.getInt("id");
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


                try {
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();

                    rs = st.executeQuery("SELECT \"id\" FROM \"club\" WHERE \"name\"='" + values[11] + "'");
                    if (rs.next()) {
                        clubId = rs.getInt("id");
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


                try {
                    PreparedStatement pst = null;
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();
                    String stm = "INSERT INTO \"sportsman\"(\"lastname\", \"firstname\", \"birthdate\", \"captain\", \"position\", \"Anzahl Einsaetze\", \"club_id\", \"playernumber\") VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                    pst = con.prepareStatement(stm);
                    String[] name = values[4].split(" ");
                    pst.setString(1, name[0]);
                    if (name.length == 2) {
                        pst.setString(2, name[1]);
                    } else {
                        pst.setString(2, "");
                    }
                    pst.setDate(3, java.sql.Date.valueOf(values[7].substring(values[7].length() - 4) + "-" + values[6] + "-" + values[5]));
                    if (values[12].equals("TRUE")) {
                        pst.setBoolean(4, true);
                    } else {
                        pst.setBoolean(4, false);
                    }
                    pst.setString(5, values[3]);
                    pst.setInt(6, Integer.parseInt(values[10]));
                    pst.setInt(7, clubId);
                    if (values[2].equals("–")) {
                        pst.setInt(8, -1);
                    } else {
                        pst.setInt(8, Integer.parseInt(values[2]));
                    }
                    pst.executeUpdate();
                    ResultSet generatedKeys = pst.getGeneratedKeys();

                    // if resultset has data, get the primary key value
                    // of last inserted record
                    if (null != generatedKeys && generatedKeys.next()) {

                        // voila! we got student id which was generated from sequence
                        key = generatedKeys.getInt(1);
                    }
                    String stm2 = "INSERT INTO \"team_sportsman\"(\"team_id\", \"sportsman_id\") VALUES ("+ teamId +", "+ key +")";
                    pst = con.prepareStatement(stm2);
                    pst.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

}
