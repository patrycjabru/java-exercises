import java.sql.*;

public class Main{
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private boolean connected;

    public void connect() {
        try {
            connected=false;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/bruzdzin", "bruzdzin", "LpkfjSZRRCPjUy2n");
            connected=true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void makeNewConnection() {
        int it=0;
        while (connected==false & it<3) {
            connect();
            it++;
        }
        if (it==3) {
            System.out.println("Cannot connect to data base");
            System.exit(11);
        }
    }
    public void showDB() {
        try {
//            connect();
            if (!conn.isValid(1000))
            {
                makeNewConnection();
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM books");
            while(rs.next()){
                String isbn = rs.getString(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String year = rs.getString(4);
                System.out.println("Isbn: " + isbn + ", title: " + title +", author: " + author + ", year: " + year);
            }
        }catch (SQLException ex){
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }

                stmt = null;
            }
        }
    }
    public void findAuthor(String find) {
        try {
            if (!conn.isValid(1000))
            {
                makeNewConnection();
            }
//            connect();
            PreparedStatement ps;
            ps = conn.prepareStatement("SELECT * FROM books WHERE author=?");
            ps.setString(1, find);
            rs = ps.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String year = rs.getString(4);
                System.out.println("Isbn: " + isbn + ", title: " + title +", author: " + author + ", year: " + year);
            }
        } catch (SQLException ex) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                }
                stmt = null;
            }
        }
    }
    public void findISBN(String find) {
        try {
            if (!conn.isValid(1000))
            {
                makeNewConnection();
            }
//            connect();
            PreparedStatement ps;
            ps = conn.prepareStatement("SELECT * FROM books WHERE isbn=?");
            ps.setString(1, find);
            rs = ps.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String year = rs.getString(4);
                System.out.println("Isbn: " + isbn + ", title: " + title +", author: " + author + ", year: " + year);
            }
        } catch (SQLException ex) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                }
                stmt = null;
            }
        }
    }
    public void deleteAuthor(String delete) {
        try {
            if (!conn.isValid(1000))
            {
                makeNewConnection();
            }
//            connect();
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM books WHERE author="+"'"+delete+"'");
        }catch (Exception e){e.printStackTrace();}
    }
    public void deleteISBN(String delete) {
        try {
//            connect();
            if (!conn.isValid(1000))
            {
                makeNewConnection();
            }
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM books WHERE isbn=" + "'" + delete + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Main db = new Main();
        db.makeNewConnection();
        db.showDB();
        System.out.println();
        db.findAuthor("Ernest Hemingway");
        System.out.println();
        db.findISBN("1234567891246");
        System.out.println("Usuwamy...");
        db.deleteAuthor("George Orwell");
        db.showDB();
        System.out.println("Usuwamy...");
        db.deleteISBN("1234567891249");
        db.showDB();
    }
}