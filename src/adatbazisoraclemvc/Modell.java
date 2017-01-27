package adatbazisoraclemvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;

public class Modell implements Adatok {
  private Modell() { //"egyke tervezési minta"
    ;
  }
  public static DefaultListModel getDolgozóLista(String művelet, String fizetés) 
      throws IllegalArgumentException {
    try {
      int fiz = Integer.parseInt(fizetés);
      if ( fiz<0 )
        throw new IllegalArgumentException("negatív a fizetés");
    } catch (NumberFormatException nfe) {
      throw new NumberFormatException("nem pozitív egész a fizetés");
    }
    String sql = 
            "SELECT FIRST_NAME || ' ' || LAST_NAME AS empName\n" +
            " FROM EMPLOYEES \n" +
            " WHERE SALARY " + művelet + fizetés + "\n" +
            " ORDER BY FIRST_NAME, LAST_NAME";
//    this.lbStátusz.setText(sql);
    DefaultListModel dlm = new DefaultListModel();
    try {
      Class.forName(Adatok.DRIVER);
      Connection kapcsolat = 
              DriverManager.getConnection(
                      Adatok.URL, Adatok.USERNAME, Adatok.PASSWORD); //connection sztring
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(sql);
      while (eredmény.next()) { //fetch-cselés; ez az egyetlen metódus amit használni javasolt;
        dlm.addElement(
                eredmény.getString("empName")
        );
      }
      kapcsolat.close();
    }
    catch(ClassNotFoundException | SQLException e) {
      ;
    }
    return dlm;
  }
}
