package adatbazisoraclemvc4;

public interface Adatok {
  String DRIVER = "oracle.jdbc.driver.OracleDriver";
  String URL = "jdbc:oracle:thin:@localhost:1521:xe";
  String USERNAME = "HR";
  String PASSWORD = "hr";
  //
  String SQLRÉSZLEGEK = 
          "SELECT DISTINCT DEPARTMENT_NAME AS depName\n" +
          "FROM DEPARTMENTS D, EMPLOYEES E\n" +
          "WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID\n" +
          "ORDER BY depName";
  String SQLDOLGOZÓADOTTRÉSZLEGBŐL = 
          "SELECT LAST_NAME || ', ' ||  FIRST_NAME AS empName\n" +
          "FROM DEPARTMENTS D, EMPLOYEES E\n" +
          "WHERE E.DEPARTMENT_ID=D.DEPARTMENT_ID AND D.DEPARTMENT_NAME=?\n" + //"prepared statement"; 
                              //bármilyen típust átvesz: ha sztringet kap, köré teszi az ''-t is;
          "ORDER BY empName";
  String SQLRÉSZLEGDOLGOZÓ =
          "SELECT DEPARTMENT_NAME AS depName, E.DEPARTMENT_ID, LAST_NAME || ', ' ||  FIRST_NAME AS empName\n" +
          "FROM DEPARTMENTS D, EMPLOYEES E\n" +
          "WHERE E.DEPARTMENT_ID=D.DEPARTMENT_ID\n" +
          "ORDER BY depName, empName";
}
/*
//SELECT FIRST_NAME || ' ' || LAST_NAME AS empName
//FROM DEPARTMENTS D, EMPLOYEES E
//WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID AND (DEPARTMENT_NAME='IT' OR DEPARTMENT_NAME='Executive')
//ORDER BY empName

SELECT DEPARTMENT_NAME AS depName, LAST_NAME || ', ' ||  FIRST_NAME AS empName
FROM DEPARTMENTS D, EMPLOYEES E
--WHERE D.DEPARTMENT_ID=E.DEPARTMENT_ID
ORDER BY depName, empName
*/