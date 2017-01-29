/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adatbazisoraclemvc4;

import static adatbazisoraclemvc4.Adatok.SQLDOLGOZÓADOTTRÉSZLEGBŐL;
import static adatbazisoraclemvc4.Adatok.SQLRÉSZLEGEK;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class Modell4 extends Modell {
  private String htmlHead =
    "<head>\n" + //az alábbi 2 sor vmiért rosszul müxik; #TODO
//    "<META HTTP-EQUIV=\"Content-Type\" Content=\"text/html; Charset=iso-8859-2\">\n" +
//    "<META HTTP-EQUIV=\"Content-Language\" Content=\"hu\">" +
    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
    //"egymilliárd weboldalba van a jquery.com beágyazva..."
    "  <link rel=\"stylesheet\" href=\"https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css\">\n" +
    "  <script src=\"https://code.jquery.com/jquery-1.11.3.min.js\"></script>\n" + 
    "  <script src=\"https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js\"></script>\n" +
    "</head>";

  public Modell4() {
    File htmlFájl = new File("./index.html");
    htmlTartalmatKészít(htmlFájl);
  }
  
  private void htmlTartalmatKészít(File htmlFájl)  {
    kapcsolatNyit();
    StringBuilder html = new StringBuilder("<html>\n");
    html.append(htmlHead);
    html.append("\n<body>\n"); 
    html.append(
      "<div data-role=\"page\" id=\"pageone\">\n" +
      "  \n" +
      "  <div data-role=\"main\" class=\"ui-content\">\n" +
      "    <h2>HR nyilvántartás</h2>\n\n" //ide kerülnek majd az adatbázisfüggő adatok
    );
    //eddig állandó.
    try {
      TreeSet<String> részlegHalmaz = new TreeSet<>(); //a treeset-nek van collator-os konstruktora is; lehetne ArrayList is, sőt;
      ResultSet eredmény1 = kapcsolat.createStatement().executeQuery(SQLRÉSZLEGEK);
      while (eredmény1.next()) 
        részlegHalmaz.add(eredmény1.getString("depName"));
      for (String részlegNév : részlegHalmaz) {
        html.append(
            "<div data-role=\"collapsible\">\n" +
            "    <h4>"+részlegNév+"</h4>\n" +
            "    <ul data-role=\"listview\">\n");
        PreparedStatement ps = kapcsolat.prepareStatement(SQLDOLGOZÓADOTTRÉSZLEGBŐL); //
        ps.setString(1, részlegNév); //
        ResultSet eredmény2 = ps.executeQuery();
        while (eredmény2.next()) {
          html.append(
            "<li><a href=\"#\">"+eredmény2.getString("empName")+"</a></li>\n");
        }
        html.append(
          "     </ul>\n" +
          "</div>\n");
      }
    } catch (SQLException e) {
      e.printStackTrace(); //#teszt
    }
    //ezután állandó:
    html.append(
      "  </div>\n"+
      "</div> \n" +
      "\n" +
      "</body>\n" +
      "</html>"
    );
//    System.out.println(""+html);
    try {
      FileWriter fw = new FileWriter(htmlFájl);
      fw.write(html.toString());
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    kapcsolatZár();
  }

  @Override
  protected DefaultTreeModel faModell() { //!: a láthatósága lehetne akár bővebb mint az ősben;
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
  }
} //class Modell4

/*
 String SQLDOLGOZOK2 = 
    " AND (DEPARTMENT_NAME IN('" 
    + String.join("', '", reszlegLista) //!:
    + "') "; 
*/