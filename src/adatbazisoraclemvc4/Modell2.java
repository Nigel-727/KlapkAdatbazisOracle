/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adatbazisoraclemvc4;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Nigel-727
 */
public class Modell2 extends Modell {

  @Override
  protected DefaultTreeModel faModell() {
    kapcsolatNyit();
    DefaultMutableTreeNode faGyökér = new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm = new DefaultTreeModel(faGyökér); //kell egy gyökér mindenképp amit meg kell szólítani
    try {
//      HashSet<String> részlegHalmaz = new HashSet<>(); //!nem garantált az eredeti sorrend;
      TreeSet<String> részlegHalmaz = new TreeSet<>(); //a treeset-nek van collator-os konstruktora is; lehetne ArrayList is, sőt;
      ResultSet eredmény1 = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGEK);
      while (eredmény1.next()) 
        részlegHalmaz.add(eredmény1.getString("depName"));
      for (String részlegNév : részlegHalmaz) {
//        System.out.println(részlegNév);
        DefaultMutableTreeNode faRészleg = new DefaultMutableTreeNode(részlegNév);
        PreparedStatement ps = kapcsolat.prepareStatement(SQLDOLGOZÓADOTTRÉSZLEGBŐL); //
        ps.setString(1, részlegNév); //
        ResultSet eredmény2 = ps.executeQuery();
        while (eredmény2.next()) {
//          System.out.println(" "+eredmény2.getString("empName"));
          faRészleg.add(new DefaultMutableTreeNode(eredmény2.getString("empName")));
        }
        faGyökér.add(faRészleg);//a for ciklus "utolsó leheletével adja hozzá a fához";
      }
    } catch (SQLException e) {
      e.printStackTrace(); //#teszt
    }
    kapcsolatZár();
    return dtm;
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
