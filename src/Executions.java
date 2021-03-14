import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void createPlayer() throws SQLException, ParseException {
        Scanner reader = new Scanner(System.in);
        Scanner readerInteger = new Scanner(System.in);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Insert Federation license code ");
        String federation_license_code = reader.nextLine();
        System.out.println("Insert first name");
        String first_name = reader.nextLine();
        System.out.println("Insert last name");
        String last_name = reader.nextLine();
        System.out.println("Insert birth date (YYYY-MM-DD)");
            Date bDate = format.parse(reader.nextLine());
        System.out.println("Insert gender (M/F)");
        String gender = reader.nextLine();
        System.out.println("Insert height");
        int height = readerInteger.nextInt();
        System.out.println("Insert team name");
        String team_name = reader.nextLine();

        System.out.println("Insert total MVP");
        int mvp = readerInteger.nextInt();

        Statement statement = null;
        statement = conn.createStatement();

        if (team_name.equals("")){
            statement.executeUpdate("INSERT INTO player(federation_license_code,first_name,last_name,birth_date,gender,height,mvp_total ) VALUES ('"+federation_license_code+"','"+first_name+"','"+last_name+"','"+bDate+"','"+gender+"','"+height+"','"+mvp+"')");
        }else {
            statement.executeUpdate("INSERT INTO player VALUES ('"+federation_license_code+"','"+first_name+"','"+last_name+"','"+bDate+"','"+gender+"','"+height+"','"+team_name+"','"+mvp+"')");
        }
        statement.close();
    }

    public void createMatch() throws SQLException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Scanner reader = new Scanner(System.in);
        Scanner readerInteger = new Scanner(System.in);
        System.out.println("Insert home team mame ");
        String teamHomeName = reader.nextLine();
        System.out.println("Insert away team mame ");
        String teamAwayName = reader.nextLine();
        System.out.println("Insert match date (YYYY-MM-DD)");
        Date mDate = format.parse(reader.nextLine());
        System.out.println("Insert attendance");
        Long attendance = readerInteger.nextLong();

        System.out.println("Do you want to add the MVP of the game? (Y/N)");
        String ans = reader.nextLine();
        if (ans.equals("Y")){
            System.out.println("MVP of the game");
            String mVP_player = reader.nextLine();
            Statement statement = null;
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO match VALUES ('"+teamHomeName+"','"+teamAwayName+"','"+mDate+"','"+attendance+"','"+mVP_player+"')");
            statement.close();
        }else {
            Statement statement = null;
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO match(home_team,visitor_team,match_date,attendance) VALUES ('"+teamHomeName+"','"+teamAwayName+"','"+mDate+"','"+attendance+"')");
            statement.close();
        }
    }

    public void showPlayersWithoutTeam()  throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM player WHERE team_name is null");
        while (rs.next()) {
            System.out.println("Federation license code: "+rs.getString("federation_license_code")+"\t" +
                    "First name: "+rs.getString("first_name")+"\t" +
                    "Last name: "+rs.getString("last_name")+"\t" +
                    "birth date: "+rs.getString("birth_date")+"\t" +
                    "Gender: "+rs.getString("gender")+"\t" +
                    "Gender: "+rs.getString("gender")+"\t" +
                    "Height: "+rs.getString("height")+"\t" +
                    "Team name: "+rs.getString("team_name")+"\t" +
                    "MVP total: "+rs.getString("mvp_total"));
        }
        rs.close();
        st.close();
    }

    public void assignPlayerToTeam() throws SQLException, IOException {
        ResultSet rs = null;
        Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        rs = st.executeQuery("SELECT * FROM player WHERE team_name IS NULL");

        if (!rs.next()) {
            System.out.println("No hi ha jugadors.");
        } else {
            do{
                System.out.println("First name: "+ rs.getString("first_name"));

                System.out.println("Do you want to assign to a team? (Y/N)");
                String resposta = br.readLine();

                if (resposta.equals("Y")) {
                    // demana l'identificador de la revista
                    System.out.println("Insert team name");
                    String team_name = br.readLine();
                    // actualitza el camp
                    rs.updateString("team_name", team_name);
                    // actualitza la fila
                    rs.updateRow();
                }
            }while (rs.next());
        }
    }

    public void detachPlayerFromTeam() throws SQLException, IOException {
        ResultSet rs = null;
        Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        Scanner reader = new Scanner(System.in);

        try {
            System.out.println("Insert federation number of player");
            int code = reader.nextInt();
            rs = st.executeQuery("SELECT * FROM player WHERE federation_license_code='"+code+"' AND team_name IS NOT NULL");

            if (!rs.next()) {
                System.out.println("This player have a team");
            } else {
                do {
                    System.out.println("Name: " + rs.getString("first_name"));

                    System.out.println("¿Do you want to break the player with his team?(Y|N)");
                    String resposta = br.readLine();

                    if (resposta.equals("Y")) {
                        rs.updateString("team_name", null);
                        rs.updateRow();
                    }
                }while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushStats() throws IOException, SQLException {
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader("estadistiques.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // do something with the data

            System.out.println("INSERT INTO match_statistics VALUES ('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"','"+data[12]+"','"+data[13]+"','"+data[14]+"','"+data[15]+"','"+data[16]+"','"+data[17]+"','"+data[18]+"','"+data[19]+"','"+data[20]+"','"+data[21]+"')");



            ResultSet rs = null;
            Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            //TODO: CONSULTA
            rs = st.executeQuery("SELECT * FROM match_statistics WHERE home_team ='"+data[0]+"' AND visitor_team='"+data[1]+"' AND match_date='"+data[2]+"'");

            if (rs.wasNull()){
                Statement statement = null;
                statement = conn.createStatement();
                statement.executeUpdate("INSERT INTO match_statistics VALUES ('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"','"+data[12]+"','"+data[13]+"','"+data[14]+"','"+data[15]+"','"+data[16]+"','"+data[17]+"','"+data[18]+"','"+data[19]+"','"+data[20]+"','"+data[21]+"')");
                statement.close();
            }else {
                Statement statement = null;
                statement = conn.createStatement();
                statement.executeUpdate("UPDATE match_statistics SET home_team = '"+data[0]+"', visitor_team = '"+data[1]+"', match_date = '"+data[2]+"', player = '"+data[3]+"', minutes_played = '"+data[4]+"', points = '"+data[5]+"', offensive_rebounds = '"+data[6]+"', defensive_rebounds = '"+data[7]+"', assists = '"+data[8]+"', comitted_fouls = '"+data[9]+"', recived_fouls = '"+data[10]+"', recived_fouls = '"+data[11]+"', free_throw_attempts  = '"+data[12]+"', free_throw_made = '"+data[13]+"', two_point_attempts = '"+data[14]+"', two_point_made'"+data[15]+"', three_point_attempts = '"+data[16]+"', three_point_made = '"+data[17]+"', blocks = '"+data[18]+"', blocks_against = '"+data[19]+"', steals = '"+data[20]+"', turnovers = '"+data[21]+"') WHERE home_team ='"+data[0]+"' AND visitor_team='"+data[1]+"' AND match_date='"+data[2]+"'");
                statement.close();
            }


        }
        csvReader.close();
    }

    public void exit() throws SQLException {
        System.out.println("ADÉU!");
        conn.close();
        System.exit(0);
    }
}
