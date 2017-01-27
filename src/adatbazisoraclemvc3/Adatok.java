package adatbazisoraclemvc3;

public interface Adatok {
  String DRIVER = "oracle.jdbc.driver.OracleDriver";
  String URL = "jdbc:oracle:thin:@localhost:1521:xe";
  String USERNAME = "HR";
  String PASSWORD = "hr";
  String SQLRÉSZLEGEK = 
          "SELECT DISTINCT DEPARTMENT_NAME AS depName\n" +
          "FROM DEPARTMENTS D, EMPLOYEES E\n" +
          "WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID\n" +
          "ORDER BY depName";
  String SQLDOLGOZÓK1 =
          "SELECT FIRST_NAME || ' ' || LAST_NAME AS empName\n" +
          "FROM DEPARTMENTS D, EMPLOYEES E\n" +
          "WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID";
  //SQLDOLGOZÓK2 GUI-tól függ
  String SQLDOLGOZÓK3=
          "ORDER BY empName";
}

/*
//SELECT FIRST_NAME || ' ' || LAST_NAME AS empName
//FROM DEPARTMENTS D, EMPLOYEES E
//WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID AND (DEPARTMENT_NAME='IT' OR DEPARTMENT_NAME='Executive')
//ORDER BY empName
*/