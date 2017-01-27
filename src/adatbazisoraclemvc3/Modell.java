package adatbazisoraclemvc3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Modell implements Adatok {
  private ArrayList<String> részlegLista = new ArrayList<>();
  private ArrayList<String> dolgozóLista = new ArrayList<>();
  //
  public Modell() { //azonnal példányfüggvény-hívásokkal inicializál #tanulságos
    this.részlegLista=this.részlegListaKészít();
    this.dolgozóLista=this.dolgozóListaKészít(null);
  }
  public ArrayList<String> getRészlegLista() {
    return részlegLista;
  }
  public ArrayList<String> getDolgozóLista() {
    return dolgozóLista;
  }
  //Csak induláskor, a konstruktor hívja, ezért lehet privát:
  private ArrayList<String> részlegListaKészít() { 
    ArrayList<String> lista = new ArrayList<>();
//    lista.add("r1");
//    lista.add("r2");
//    lista.add("r3");
    try {
      Class.forName(DRIVER);
      Connection kapcsolat = 
              DriverManager.getConnection(
                      URL, USERNAME, PASSWORD); //connection sztring
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGEK);
      while (eredmény.next()) { //fetch-cselés; ez az egyetlen metódus amit használni javasolt;
        lista.add(
                eredmény.getString("depName")
        );
      }
      kapcsolat.close();
    }
    catch(ClassNotFoundException | SQLException e) {
      ;
    }
    return lista;
  }
  //Gombnyomásra hívódik meg, akár többször:
  /*private*/ public ArrayList<String> dolgozóListaKészít(ArrayList<String> részlegLista) {
    if (részlegLista==null)
      return new ArrayList<String>();
//    System.out.println(
//        "(DEPARTMENT_NAME='"
//        +String.join("' OR DEPARTMENT_NAME='", részlegLista) //!
//        +"')"
//    );
    String SQLDOLGOZÓK2 =
          " AND (DEPARTMENT_NAME='"
          +String.join("' OR DEPARTMENT_NAME='", részlegLista) //! #tanulságos
          +"')"; //ha csak 1 db eleme van a listának akkor a delimeter nem adódik hozzá
    String SQL=
            Adatok.SQLDOLGOZÓK1
            +SQLDOLGOZÓK2+"\n"
            +Adatok.SQLDOLGOZÓK3;
//    System.out.println(SQL);
//    ArrayList<String> lista = new ArrayList<>();
//    lista.add("d1");
//    lista.add("d2");
//    lista.add("d3");
    this.dolgozóLista.clear(); //!: vagyis, e fv. nemcsak getter, hanem setter is
    try {
      Class.forName(DRIVER);
      Connection kapcsolat = 
              DriverManager.getConnection(
                      URL, USERNAME, PASSWORD); //connection sztring
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQL);
      while (eredmény.next()) { //fetch-cselés; ez az egyetlen metódus amit használni javasolt;
        this.dolgozóLista.add(
                eredmény.getString("empName")
        );
      }
      kapcsolat.close();
    }
    catch(ClassNotFoundException | SQLException e) {
      ;
    }
//    this.dolgozóLista.add("d1");
//    this.dolgozóLista.add("d2");
//    this.dolgozóLista.add("d3");
    return this.dolgozóLista;
  }
}