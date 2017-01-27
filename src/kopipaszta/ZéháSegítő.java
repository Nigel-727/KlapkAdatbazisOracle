package kopipaszta;

import java.awt.Component;
import java.awt.Container;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;

/**
 *
 * @author Nigel-727
 */
public class ZéháSegítő {
  public static int véletlenszám(int a, int b) {            //a<=b
    return (int)(Math.random()*(b-a+1)+a);
  }
  public static String dátumidő(long utc) {
    DateFormat dátumIdőFormázó=
      DateFormat.getDateTimeInstance(
        DateFormat.SHORT, DateFormat.SHORT);
    return
      dátumIdőFormázó.format(new Date(utc));
  }
  public static void igazítKomponenseket(Container panel, Class keresettKomponensTípus, int hovámerre) { //SwingConstants.CENTER
    for(int i=0; i<panel.getComponentCount(); i++) { //JFrame-ből hívva  getComponentPane() a szülő
      Component komponens=panel.getComponent(i);
      if(komponens.getClass() == keresettKomponensTípus) {
        switch (keresettKomponensTípus.getSimpleName()) {
          case "JLabel":
            ((JLabel)komponens).setHorizontalAlignment(hovámerre);
            break;   
          default:
            throw new IllegalArgumentException("figyelj már oda mit gépelsz");
        }
      }
    }
  }  
  public static long időtartam(Calendar c1, Calendar c2) { /*
    System.out.println("az első paraméter"
            + "\n mint Date (dátum): " + c1.getTime() //Date
            + "\n mint long (UTC): " + c1.getTime().getTime() //long
    );
    System.out.println("az második paraméter"
            + "\n mint Date (dátum): " + c2.getTime() //Date
            + "\n mint long (UTC): " + c2.getTime().getTime() //long
    ); */
    long l1 = c1.getTime().getTime(),
          l2 = c2.getTime().getTime();
    return Math.abs(l2-l1)/1000/60/60/24;
  } 
  public static ArrayList<Integer> lottószámok(int hányat, int hányból) { 
    TreeSet<Integer> segítőhalmaz = new TreeSet<>();
    while (segítőhalmaz.size()<hányat)
      segítőhalmaz.add(véletlenszám(1, hányból));
    return new ArrayList(segítőhalmaz); //#iLoveJava
  }
  public static void setFaÁllapot(JTree tree, boolean expanded) {
    Object root = tree.getModel().getRoot();
    setFaÁllapot(tree, new TreePath(root),expanded);
  }
  private static void setFaÁllapot(JTree tree, TreePath path, boolean expanded) {
    Object lastNode = path.getLastPathComponent();
    for (int i = 0; i < tree.getModel().getChildCount(lastNode); i++) {
      Object child = tree.getModel().getChild(lastNode,i);
      TreePath pathToChild = path.pathByAddingChild(child);
      setFaÁllapot(tree,pathToChild,expanded);
    }
    if (expanded) 
      tree.expandPath(path);
    else
      tree.collapsePath(path);
  }
  public static boolean isPrím(int szám) { //public: emiatt hívhatjuk másik package-ből/class-ból?
    if (szám < 2)
      return false;
    boolean vanosztója = false;
    int i = 2;
    while (!vanosztója && i<=Math.sqrt(szám)) 
      vanosztója = szám%i++ == 0; 
    return !vanosztója;
  }

  
  public static void main(String[] args) { //#teszt
    JFrame fr = new JFrame();
    JPanel pn = new JPanel();
    igazítKomponenseket(pn, JLabel.class, SwingConstants.CENTER);
  }
}

/* példák használatra:
Calendar calNow = Calendar.getInstance(), calMásikdátum = Calendar.getInstance();    
    calMásikdátum.set(2016, 5-1, 26);
/////////////////////

*/