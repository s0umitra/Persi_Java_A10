package libraries;

import java.awt.List;
import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class DbManager {
    static String url = "jdbc:mysql://localhost:3306/contactdb";
    static String user = "root";
    static String pass = "se123ed123";
    static Connection con;
    Statement stmt;

    public DbManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pass);
        stmt = con.createStatement();
    }

    public void initTable() throws SQLException {
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet table = dbm.getTables(null, null, "contact_tbl", null);
        if (table.next()) stmt.executeUpdate("DROP TABLE contact_tbl");
        createTable();
        table.close();
    }

    public void createTable() throws SQLException {
        String state = "CREATE TABLE contact_tbl(" +
                "contactId INT PRIMARY KEY ," +
                "contactName VARCHAR(30)," +
                "contactEmail VARCHAR(40)," +
                "contactList VARCHAR(100));";

        stmt.executeUpdate(state);
    }

    public void populateDb() {
        File file = new File(Paths.get("").toAbsolutePath().toString() + "\\db\\" + "DbPopulate.txt");

        try {

            PreparedStatement pState = con.prepareStatement("INSERT INTO contact_tbl VALUES (?,?,?,?)");
            Scanner sc = new Scanner(file);
            StringBuilder param4;
            String[] line;

            while (sc.hasNextLine()) {
                param4 = new StringBuilder();
                line = sc.nextLine().split(",");
                pState.setInt(1, Integer.parseInt(line[0].strip()));
                pState.setString(2, line[1].strip().replace("’",""));
                pState.setString(3, line[2].strip().replace("’",""));

                for (int i=3; i<line.length; i++) {
                    param4.append(line[i]).append(" ");
                }
                if (!param4.toString().strip().equals("null")) {
                    pState.setString(4, param4.toString().strip().replace("’",""));
                }
                else {
                    pState.setString(4, null);
                }
                pState.executeUpdate();
            }

            pState.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Contact> readFromDb() throws SQLException {
        Set<Contact> reqSet = new HashSet<>();
        Contact c;

        String state = "SELECT * FROM contact_tbl;";
        ResultSet rs = stmt.executeQuery(state);

        while (rs.next()) {
            c = new Contact();
            c.setContactID(rs.getInt(1));
            c.setContactName(rs.getString(2));
            c.setEmail(rs.getString(3));
            c.setContactNumber( Arrays.asList(rs.getString(4) == null ? new String[]{} : rs.getString(4).strip().split(" ")));

            reqSet.add(c);
        }
        return reqSet;
    }

    public void close() throws SQLException {
        stmt.close();
        con.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DbManager dm = new DbManager();
        dm.initTable();
        dm.populateDb();
        dm.close();
    }
}
