package adatbazisoraclemvc4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Modell3byHédi extends Modell {
  
  class ReszlegDolgozo {
    private String részlegNév;
    private ArrayList<String> dolgozóNévLista;

    public ReszlegDolgozo(String részlegNév, ArrayList<String> dolgozóNévLista) {
      this.részlegNév = részlegNév;
      this.dolgozóNévLista = dolgozóNévLista;
    }    

    public String getRészlegNév() {
      return részlegNév;
    }

    public ArrayList<String> getDolgozóNévLista() {
      return dolgozóNévLista;
    }    
  }

  private ArrayList<ReszlegDolgozo> listaKészít() {
    ArrayList<ReszlegDolgozo> lista=new ArrayList<>();
    String aktRészlegNév="", elozoRészlegNév="";
    ArrayList<String> aktDolgozoNevLista=new ArrayList<>();
    try {
      ResultSet eredmény=kapcsolat.createStatement().
        executeQuery(SQLRÉSZLEGDOLGOZÓ);
      while(eredmény.next()) {
        aktRészlegNév=eredmény.getString("depName");
        String aktDolgozo=eredmény.getString("empName");
        if (!elozoRészlegNév.equals(aktRészlegNév)) { //reszlegvaltas van/
          lista.add(new ReszlegDolgozo(aktRészlegNév, new ArrayList<String>()));
          elozoRészlegNév=aktRészlegNév;          
        }
        lista.get(lista.size()-1).getDolgozóNévLista().add(aktDolgozo);
      }
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return lista;
  }
  
  public DefaultTreeModel faModell() {
    kapcsolatNyit();
    ArrayList<ReszlegDolgozo> lista=listaKészít();
    kapcsolatZár();
    DefaultMutableTreeNode faGyökér=
      new DefaultMutableTreeNode("Cég");
    DefaultTreeModel dtm=new DefaultTreeModel(faGyökér);
    int i=0;
    while(i<lista.size()) {
      String aktRészleg=lista.get(i).getRészlegNév();
      DefaultMutableTreeNode faRészleg=
        new DefaultMutableTreeNode(aktRészleg);
      ArrayList<String> dolgozóLista=lista.get(i).getDolgozóNévLista();
      int j=0;
      while(j<dolgozóLista.size()) {
        faRészleg.add(new DefaultMutableTreeNode(
          dolgozóLista.get(j)
        ));
        j++;
      }
      faGyökér.add(faRészleg);
      i++;
    }
    return dtm;
  }
}