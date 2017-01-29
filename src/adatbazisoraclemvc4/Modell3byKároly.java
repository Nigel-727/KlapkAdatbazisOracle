package adatbazisoraclemvc4;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Nigel-727
 */
public class Modell3byKároly extends Modell{

  class ReszlegEsDolgozói implements Comparable<ReszlegEsDolgozói>{
    private String reszlegNev;
    private ArrayList<String> dolgozoLista;;

    public ReszlegEsDolgozói(String reszlegNev) {
      this.reszlegNev = reszlegNev;
      this.dolgozoLista = new ArrayList<>(); //Károly meg itt;
    }

    public String getReszlegNev() {
      return reszlegNev;
    }
    
    public void addDolgozo(String dolgozo){
      this.dolgozoLista.add(dolgozo);
    }
    
    public ArrayList<String> getDolgozoLista(){
      return this.dolgozoLista;
    }
    
    public String getDolgozo(int index){
      return dolgozoLista.get(index);
    }
    public void sortDolgozoLista(){
//      dolgozoLista.sort(null);
      Collections.sort(dolgozoLista);
    }

    @Override
    public int compareTo(ReszlegEsDolgozói o) {
      return this.reszlegNev.compareTo(o.getReszlegNev());
    }
  }
  
  @Override
  protected DefaultTreeModel faModell() {
    TreeSet<ReszlegEsDolgozói> reszlegHalmaz = adatGyujtes();
    //itt rendelkezésre áll egy helamz (TreeSet) amiben részlegenként van egy POJO
    //ami tartalmazza a részleg nevét és a hozzá tartozó
    // dolgozók listáját

    DefaultMutableTreeNode faGyoker = new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm=new DefaultTreeModel(faGyoker);    
      
      for (ReszlegEsDolgozói reszlegEsDolgozoi : reszlegHalmaz) {
        DefaultMutableTreeNode faReszleg = new DefaultMutableTreeNode(reszlegEsDolgozoi.getReszlegNev());
        for(int i=0; i<reszlegEsDolgozoi.getDolgozoLista().size(); i++){
          faReszleg.add(new DefaultMutableTreeNode( reszlegEsDolgozoi.getDolgozoLista().get(i) ));
        }
        faGyoker.add(faReszleg);
      }
    return  dtm;
  }

  private TreeSet<ReszlegEsDolgozói> adatGyujtes() {
    //a TreeSet részlegenként tárol egy POJO-t 
    //ami tartalmazza a részleg navét és az adott részleghez tartozó dolgozókat listáját
    kapcsolatNyit();
    TreeSet<ReszlegEsDolgozói> eredmenyHalmz = new TreeSet<>();
    try {
      ResultSet eredmeny1 = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGEK);
      while (eredmeny1.next()) {
        eredmenyHalmz.add(new ReszlegEsDolgozói(eredmeny1.getString("depName")));
      }
      for (ReszlegEsDolgozói reszlegEsDolgozói : eredmenyHalmz) {
        PreparedStatement ps = kapcsolat.prepareStatement(SQLDOLGOZÓADOTTRÉSZLEGBŐL);
        ps.setString(1, reszlegEsDolgozói.getReszlegNev());
        ResultSet eredmeny2 = ps.executeQuery();
        while (eredmeny2.next()) {
          reszlegEsDolgozói.addDolgozo(eredmeny2.getString("empName"));
        }
        //System.out.println(reszlegEsDolgozói.getReszlegNev()+"\n"+reszlegEsDolgozói.getDolgozoLista());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    kapcsolatZár();
    return eredmenyHalmz;
  }
}

