package dlugolecki.pawel;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.service.MyMenu;

/**
 * @author Paweł Długołęcki
 * @version 1.0
 */

public class Menu {
    public static void main(String[] args) {
        MyMenu menu = new MyMenu();
        boolean isFinished = true;
        do {
            try {
                isFinished = menu.mainMenu();
            } catch (MyException e) {
                System.err.println(e.getMessage());
            }
        } while (isFinished);
        DbConnection.getInstance().getConnection();
        DbConnection.getInstance().close();
    }
}