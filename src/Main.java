import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static String url = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String user = "infointe";
    public static String password = "InfoInte1516%";
    public static String file = "/Users/jaspar.mang/Downloads/players.csv";

    public static void main(String[] args) {
        //insertClub();
        //insertTeam();
        //insertPlayer();

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
                try {
                    con = DriverManager.getConnection(url, user, password);
                    st = con.createStatement();

                    rs = st.executeQuery("SELECT \"Name\" FROM \"club\" WHERE \"Name\"='" + values[11] + "'");
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
                        String stm = "INSERT INTO \"club\"(\"Name\", \"Liga\") VALUES(?, ?)";
                        pst = con.prepareStatement(stm);
                        pst.setString(1, values[11]);
                        pst.setString(2, values[12]);
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

                    rs = st.executeQuery("SELECT \"Name\" FROM \"teamsoccer\" WHERE \"Name\"='" + values[1] + "'");
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
                        String stm = "INSERT INTO \"teamsoccer\"(\"Gruppe\", \"Name\") VALUES(?, ?)";
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

                    rs = st.executeQuery("SELECT \"id\" FROM \"teamsoccer\" WHERE \"Name\"='" + values[1] + "'");
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

                    rs = st.executeQuery("SELECT \"id\" FROM \"club\" WHERE \"Name\"='" + values[11] + "'");
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
                    String stm = "INSERT INTO \"spieler\"(\"Nachname\", \"Vorname\", \"Geburtstag\", \"Captain\", \"Position\", \"Anzahl Einsaetze\", \"TeamId\", \"ClubId\", \"Spielernummer\") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pst = con.prepareStatement(stm);
                    String[] name = values[4].split(" ");
                    pst.setString(1, name[0]);
                    if (name.length == 2) {
                        pst.setString(2, name[1]);
                    } else {
                        pst.setString(2, "");
                    }
                    //new Date(Integer.parseInt(values[7]),Integer.parseInt(values[6]),Integer.parseInt(values[5]));
                    System.out.print(values[7].substring(values[7].length() - 4) + "-" + values[6] + "-" + values[5]);
                    pst.setDate(3, java.sql.Date.valueOf(values[7].substring(values[7].length() - 4) + "-" + values[6] + "-" + values[5]));
                    if (values[12].equals("TRUE")) {
                        pst.setBoolean(4, true);
                    } else {
                        pst.setBoolean(4, false);
                    }
                    pst.setString(5, values[3]);
                    pst.setInt(6, Integer.parseInt(values[10]));
                    pst.setInt(7, teamId);
                    pst.setInt(8, clubId);
                    if (values[2].equals("–")) {
                        pst.setInt(9, -1);
                    } else {
                        pst.setInt(9, Integer.parseInt(values[2]));
                    }
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