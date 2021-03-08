import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        //Connections
        Connection conn = null;
        Identity identity;
        DBAccessor dbaccessor = new DBAccessor();
        dbaccessor.init();

        int x;
        String name;
        int intents = 0;

        while (intents < 3 && conn == null) {
            identity = autenticacio(intents);
            // prova de test
            identity.toString();
            conn = dbaccessor.getConnection(identity);
            intents++;
        }

        Executions executions = new Executions(conn);

        do{
            imprimirMenu();
            x = sc.nextInt();
            switch(x) {
                case 1:
                    executions.showTeams();
                    break;
                case 2:
                    System.out.println("Insert a name of a team");
                    name = sc2.nextLine();
                    executions.showPlayersfromTeam(name);
                    break;
                case 3:
                    executions.createTeam();
                    break;
                case 4:
                    executions.createPlayer();
                    break;
                case 5:
                    executions.createMatch();
                    break;
                case 6:
                    executions.showPlayers();
                    break;
                case 7:
                    executions.assignPlayerToTeam();
                    break;
                case 8:
                    executions.detachPlayerFromTeam();
                    break;
                case 9:
                    executions.pushStats();
                    break;
                case 10:
                    executions.exit();
                    break;
                default:
                    System.out.println("entry a numbe between 1 to 10");
            }

        }while (x !=10);
    }

    private static void imprimirMenu() {
        System.out.println("\n" +
                "1.     Mostra equips\n" +
                "2.     Mostra jugadors d'un determinat equip\n" +
                "3.     Crea equip\n" +
                "4.     Crea Jugador\n" +
                "5.     Crea Partit\n" +
                "6.     Mostra jugadors sense equip\n" +
                "7.     Assigna  jugador a un equip.\n" +
                "8.     Desvincula jugador  d'un equip.\n" +
                "9.     Carrega estadístiques.\n" +
                "10.    Sortir");
    }

    public static Identity autenticacio(int intents) throws IOException {
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("==============HEMEROTECA====================================");
        System.out.println("============================================================");
        System.out.println("Avís: tens " + (3 - intents) + " intents per loginarte");
        System.out.println("============================================================");
        System.out.println("Inserta nom del usuari: ");
        String usuari = br1.readLine();
        System.out.println("Inserta contrasenya: ");
        String pass = br1.readLine();

        Identity identity = new Identity(usuari, pass);
        return identity;

    }
}
