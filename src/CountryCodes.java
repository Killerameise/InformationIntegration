import java.sql.*;
import java.util.Locale;

/**
 * Created by jaspar.mang on 11.01.16.
 */
public class CountryCodes {
    public static String url = "jdbc:postgresql://sebastiankoall.de/infointe";
    public static String user = "infointe";
    public static String password = "InfoInte1516%";

    public static void main(String[] args){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "SELECT id, \"birthCountry\" FROM sportsman WHERE char_length(\"birthCountry\") < 4 AND \"birthCountry\" <> 'USA'";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                //Locale obj = new Locale("", rs.getString("birthCountry"));
                /*if (!((obj.getCountry()).equals(obj.getDisplayCountry()))){
                    PreparedStatement st = con.prepareStatement("UPDATE sportsman SET country = ? WHERE id = ?");
                    st.setString(1, obj.getDisplayCountry());
                    st.setInt(2, rs.getInt("id"));
                    st.executeUpdate();
                }*/
                if (rs.getString("birthCountry").equals("CAN")){
                    PreparedStatement st = con.prepareStatement("UPDATE sportsman SET country = ? WHERE id = ?");
                    st.setString(1, "Canada");
                    st.setInt(2, rs.getInt("id"));
                    st.executeUpdate();
                }

                   // System.out.println("Country Code = " + obj.getCountry()
                    //        + ", Country Name = " + obj.getDisplayCountry());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
