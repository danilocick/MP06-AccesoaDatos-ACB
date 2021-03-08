import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Executions {
    Connection conn;

    public Executions(Connection conn) {
        this.conn = conn;
    }

    public void showTeams() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM team");
        while (rs.next()) {
            System.out.println("Name: "+rs.getString("name")+"\t" +
                    "type: "+rs.getString("type")+"\t" +
                    "country: "+rs.getString("country")+"\t" +
                    "city: "+rs.getString("city")+"\t" +
                    "court name: "+rs.getString("court_name"));
        }
        rs.close();
        st.close();
    }

    public void showPlayersfromTeam(String name) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM player WHERE team_name='"+name+"'");
        while (rs.next()) {
            System.out.println("Federation license code: "+rs.getString("federation_license_code")+"\t" +
                    "first name: "+rs.getString("first_name")+"\t" +
                    "last name: "+rs.getString("last_name")+"\t" +
                    "birth date: "+rs.getString("birth_date")+"\t" +
                    "gender: "+rs.getString("gender")+"\t" +
                    "height: "+rs.getString("height")+"\t" +
                    "team name: "+rs.getString("team_name")+"\t" +
                    "mvp total: "+rs.getString("mvp_total"));
        }
        rs.close();
        st.close();
    }

    public void createTeam() throws SQLException {
        Scanner reader = new Scanner(System.in);
        Scanner readerInteger = new Scanner(System.in);
        System.out.println("Insert team mame ");
        String teamName = reader.nextLine();
        System.out.println("Insert type: \n 1 - National Team \n 2 - Club");
        int type = readerInteger.nextInt();
        String typeString;

        if (type == 1) typeString = "National Team";
        else typeString = "Club";

        System.out.println("Insert country");
        String country = reader.nextLine();
        System.out.println("Insert city");
        String city = reader.nextLine();
        System.out.println("Insert court name");
        String courtName = reader.nextLine();

        Statement statement = null;
        statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO team VALUES ('"+teamName+"','"+typeString+"','"+country+"','"+city+"','"+courtName+"')");

        statement.close();
    }

    public void createPlayer() throws SQLException {
        Scanner reader = new Scanner(System.in);
        Scanner readerInteger = new Scanner(System.in);
        System.out.println("Insert team mame ");
        String teamName = reader.nextLine();
        System.out.println("Insert type: \n 1 - National Team \n 2 - Club");
        int type = readerInteger.nextInt();
        String typeString;

        if (type == 1) typeString = "National Team";
        else typeString = "Club";

        System.out.println("Insert country");
        String country = reader.nextLine();
        System.out.println("Insert city");
        String city = reader.nextLine();
        System.out.println("Insert court name");
        String courtName = reader.nextLine();

        Statement statement = null;
        statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO team VALUES ('"+teamName+"','"+typeString+"','"+country+"','"+city+"','"+courtName+"')");

        statement.close();
    }

    public void createMatch() {
    }

    public void showPlayers() {
    }

    public void assignPlayerToTeam() {
    }

    public void detachPlayerFromTeam() {
    }

    public void pushStats() {
    }
    public void exit() throws SQLException {
        System.out.println("ADÉU!");
        conn.close();
        System.exit(0);
    }
}
