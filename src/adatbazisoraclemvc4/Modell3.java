/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adatbazisoraclemvc4;

import static adatbazisoraclemvc4.Adatok.SQLRÉSZLEGDOLGOZÓ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Nigel-727
 */

class ReszlegEsDolgozoi { //CSAK azért nem #belsőosztály, mert a külső osztály számára akkor is minden látható volna
    private String részlegNév; //ha belső volna, a külső osztály akkor is simán elérné (getter/setter nélkül)
    private ArrayList<String> dolgozóLista = new ArrayList<>(); //Sándor kikötése volt h legyen privát a részlegNév és a dolgozóLista

    public ReszlegEsDolgozoi(String részlegNév) {
      this.részlegNév = részlegNév;
    }
    public void addDolgozo(String dolgozó) {
      this.dolgozóLista.add(dolgozó);
    }
    public String getRészlegNév() {
      return this.részlegNév;
    }
    public ArrayList<String> getDolgozóLista() {  //memóriacímet ad vissza; #nemszép
      return this.dolgozóLista;
    }
  }

public class Modell3 extends Modell {
  
//  class ReszlegEsDolgozoi { // amikor még #belsőosztály volt
//    private String részlegNév; //mert privátot kért a feladat; !: hiába privát, a külső osztály mindent lát; #hujujuj
//    private ArrayList<String> dolgozóLista = new ArrayList<>(); //mert privátot kért a feladat
//
//    public ReszlegEsDolgozoi(String részlegNév) {
//      this.részlegNév = részlegNév;
//    }
//    public void addDolgozo(String dolgozó) {
//      this.dolgozóLista.add(dolgozó);
//    }
//    public String getRészlegNév() {
//      return this.részlegNév;
//    }
//    public ArrayList<String> getDolgozóLista() {  //memóriacímet ad vissza; #nemszép
//      return this.dolgozóLista;
//    }
//  }
  
  private ArrayList<ReszlegEsDolgozoi> listaKészít() {
    ArrayList<ReszlegEsDolgozoi> lista = new ArrayList<>();
    try {
      ResultSet eredmény = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGDOLGOZÓ);
      String előzőRészleg=null; //!: null az összehasonlítások miatt;
      ReszlegEsDolgozoi rAndD=null; //null a NetBeans miatt;
      while (eredmény.next()) {
        String részleg = eredmény.getString("depName");
        if (!részleg.equals(előzőRészleg)) { //csoportváltás van
          if (előzőRészleg!=null) //nem a legelső részleg
            lista.add(rAndD);
          rAndD = new ReszlegEsDolgozoi(részleg);
          előzőRészleg = részleg; //megjegyezzük az előzőt
        }          
        String dolgozó = eredmény.getString("empName"); 
        rAndD.addDolgozo(dolgozó); 
      }
    } catch (SQLException e) {
      e.printStackTrace(); //#teszt
    }
    return lista;
  }

  @Override
  protected DefaultTreeModel faModell() {
    kapcsolatNyit();
    ArrayList<ReszlegEsDolgozoi> lista = listaKészít();
    kapcsolatZár(); //!: fontos h itt legyen a zárás;
    //Rakjuk az elemeket a fába:
    DefaultMutableTreeNode faGyökér = new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm = new DefaultTreeModel(faGyökér); //kell egy gyökér mindenképp amit meg kell szólítani; "hé, te gyökér!!";
    for (ReszlegEsDolgozoi aktRészlegDolgozókkal : lista) {
      DefaultMutableTreeNode faRészleg = new DefaultMutableTreeNode(aktRészlegDolgozókkal.getRészlegNév());
      for (String aktDolgozó : aktRészlegDolgozókkal.getDolgozóLista()) 
        faRészleg.add(new DefaultMutableTreeNode(aktDolgozó));
      faGyökér.add(faRészleg);
    }
    return dtm;
  }
}