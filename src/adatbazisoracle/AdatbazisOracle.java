package adatbazisoracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdatbazisOracle {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection kapcsolat = 
              DriverManager.getConnection(
                      "jdbc:oracle:thin:@localhost:1521:xe", "HR", "hr"); //connection sztring
      //jdbc: csatlakózófelület típusa; 
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(
                "SELECT DEPARTMENT_NAME AS depName, COUNT(EMPLOYEE_ID) AS empCount\n" +
                "FROM DEPARTMENTS D, EMPLOYEES E\n" +
                "WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID \n" +
//                "WHERE E.DEPARTMENT_ID=D.DEPARTMENT_ID \n" +
                "GROUP BY DEPARTMENT_NAME \n" +
                "ORDER BY depName")
                        ;
      while (eredmény.next()) { //fetch-cselés; ez az egyetlen metódus amit használni javasolt;
        System.out.println(
                eredmény.getString("depName")+"\t\t\t"+eredmény.getInt("empCount")
        );
      }
      kapcsolat.close();
    }
    catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
    catch(SQLException e) {
      System.out.println("Hiba: "+e.getMessage());
    }
  }
}
