/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adatbazisoraclemvc4;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Nigel-727
 */
public abstract class Modell implements Adatok { //!: absztrakt osztály;
  protected Connection kapcsolat=null; //kezdetben kell a null;
//  private Modell() { //nem OK;
//  protected Modell() { //OK; 
//    ;
//  }
  protected void kapcsolatNyit() {
    try {
      Class.forName(DRIVER);
      kapcsolat=DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (Exception e) {
      e.printStackTrace();//tesztidőszakban OK;
    }
  }
  protected void kapcsolatZár() {
    try {
      if (kapcsolat!=null) //==isClosed()
        kapcsolat.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  protected abstract DefaultTreeModel faModell(); //!: absztrakt metódus;
}//class Modell