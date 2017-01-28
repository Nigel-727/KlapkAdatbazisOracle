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


public class Modell3 extends Modell {
  
  private class ReszlegEsDolgozoi { //?: hiába #belsőosztály, a külső osztály számára akkor is minden látható;  #miértvanezígy
    private String részlegNév; //Sándor kikötése volt h legyen privát a részlegNév és a dolgozóLista
    private ArrayList<String> dolgozóLista = new ArrayList<>();  //én itt rendelek hozzá memóriacímet;

    public ReszlegEsDolgozoi(String részlegNév) { //egyparaméteres a konstuktor nálam is, Károlynál is; hiszen a 2. értéket (memóriacím) nem is szabad h kívülről kapja;
      this.részlegNév = részlegNév;
    }
    public void addDolgozó(String dolgozó) {
      this.dolgozóLista.add(dolgozó);
    }
    public String getRészlegNév() {
      return this.részlegNév;
    }
    public ArrayList<String> getDolgozóLista() {  
      return new ArrayList<String>(this.dolgozóLista); //vagyis nem memóriacímet ad vissza; #szuper
    }
  }
  
    private ArrayList<ReszlegEsDolgozoi> listaKészít() {
    ArrayList<ReszlegEsDolgozoi> lista = new ArrayList<>();
    try {
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGDOLGOZÓ);
      String előzőRészleg=""; //"" az összehasonlítások miatt;
      while (eredmény.next()) {
        String részleg = eredmény.getString("depName"); //ez nálam az "aktRészleg";
        if (!részleg.equals(előzőRészleg)) { 
          lista.add(new ReszlegEsDolgozoi(részleg));
          előzőRészleg = részleg; //megjegyezzük az előzőt
        }          
        String dolgozó = eredmény.getString("empName"); //ez nálam az "aktDolgozó";
        lista.get(lista.size()-1).addDolgozó(dolgozó); //a legutolsó elemet (ami egy objektum) szólítjuk meg;
      }
    } catch (SQLException e) {
      e.printStackTrace(); //#teszt
    }
    return lista;
  }
  
//  private ArrayList<ReszlegEsDolgozoi> listaKészít2() { //ez a verzió volt először, Hédi ötlete nyomán módosítottam;
//    ArrayList<ReszlegEsDolgozoi> lista = new ArrayList<>();
//    try {
//      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGDOLGOZÓ);
//      String előzőRészleg=""; //"" az összehasonlítások miatt;
//      ReszlegEsDolgozoi aktRészlegÉsDolgozói=null; //null a NetBeans miatt;
//      while (eredmény.next()) {
//        String részleg = eredmény.getString("depName");
//        if (!részleg.equals(előzőRészleg)) { //csoportváltás vagy nem a legelső részleg;
//          if (!előzőRészleg.equals("")) //nem a legelső részleg;
//            lista.add(aktRészlegÉsDolgozói);
//          aktRészlegÉsDolgozói = new ReszlegEsDolgozoi(részleg);
//          előzőRészleg = részleg; //megjegyezzük az előzőt
//        }          
//        String dolgozó = eredmény.getString("empName"); 
//        aktRészlegÉsDolgozói.addDolgozó(dolgozó); 
//      }
//      if (!előzőRészleg.equals("")) //nem a legelső részleg; vagyis még maradt
//        lista.add(aktRészlegÉsDolgozói);
//    } catch (SQLException e) {
//      e.printStackTrace(); //#teszt
//    }
//    return lista;
//  }

  @Override
  protected DefaultTreeModel faModell() {
    kapcsolatNyit();
    ArrayList<ReszlegEsDolgozoi> lista = listaKészít();
    kapcsolatZár(); //!: fontos h itt legyen a zárás;
    DefaultMutableTreeNode faGyökér = new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm = new DefaultTreeModel(faGyökér); //kell egy gyökér mindenképp amit meg kell szólítani; "hé, te gyökér!!";
    for (ReszlegEsDolgozoi aktRészlegÉsDolgozói : lista) {
      DefaultMutableTreeNode faRészleg = new DefaultMutableTreeNode(aktRészlegÉsDolgozói.getRészlegNév());
      for (String aktDolgozó : aktRészlegÉsDolgozói.getDolgozóLista()) 
        faRészleg.add(new DefaultMutableTreeNode(aktDolgozó));
      faGyökér.add(faRészleg);
    }
    return dtm;
  }
}