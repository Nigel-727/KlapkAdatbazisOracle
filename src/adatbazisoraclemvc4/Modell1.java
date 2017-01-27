/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adatbazisoraclemvc4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Nigel-727
 */
public class Modell1 extends Modell {
  
  class ReszlegDolgozo {
    public String részlegNév, dolgozóNév;
    public ReszlegDolgozo(String részlegNév, String dolgozóNév) {
      this.részlegNév=részlegNév; this.dolgozóNév=dolgozóNév;
    }
  }
//  String s=DRIVER;
  public Modell1() {
//    super();
  }
  private ArrayList<ReszlegDolgozo> listaKészít() {
    ArrayList<ReszlegDolgozo> lista = new ArrayList<>();
    try {
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGDOLGOZÓ);
      while (eredmény.next()) {
        String részleg = eredmény.getString("depName");
        String dolgozó = eredmény.getString("empName");
        ReszlegDolgozo rd = new ReszlegDolgozo(részleg, dolgozó);
        lista.add(rd);
      }
    } catch (SQLException e) {
      e.printStackTrace(); //#teszt
    }
    return lista;
  }
  @Override
  public DefaultTreeModel faModell() {
    kapcsolatNyit();
    ArrayList<ReszlegDolgozo> lista = listaKészít();
    kapcsolatZár(); //!: fontos h itt legyen a zárás;
    //Rakjuk az elemeket a fába:
    DefaultMutableTreeNode faGyökér = new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm = new DefaultTreeModel(faGyökér); //kell egy gyökér mindenképp amit meg kell szólítani
    int i=0;
    while (i<lista.size()) {
      String aktRészleg=lista.get(i).részlegNév;
      DefaultMutableTreeNode faRészleg = new DefaultMutableTreeNode(aktRészleg); //"csoportváltás II. pont";
      while (i<lista.size() && lista.get(i).részlegNév.equals(aktRészleg)) {
        faRészleg.add(new DefaultMutableTreeNode(lista.get(i).dolgozóNév));
        i++;
      }
      faGyökér.add(faRészleg);
    }
    return dtm;
  }
}//class Modell1