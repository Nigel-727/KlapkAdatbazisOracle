package adatbazisoraclemvc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Nézet extends JFrame 
    implements ActionListener { //CleanCode ajánlás a 4 szóköz
  
  private JComboBox cbMűvelet = new JComboBox();
  private JTextField tfFizetés = new JTextField(6);
  private DefaultListModel dlm = new DefaultListModel();
  private JList lDolgozók = new JList();
  private JLabel lbStátusz;

  public Nézet() {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("HR Lekérdező 1.0");
    this.setSize(800, 400);
    this.setLocationRelativeTo(this);
    JPanel pnFelső = new JPanel(); //flowlayout  by default
    pnFelső.add(new JLabel("Fizetés "));
    this.cbMűvelet = new JComboBox(new String[] {"<=","<","=",">",">=","<>"});
    pnFelső.add(this.cbMűvelet);
    pnFelső.add(this.tfFizetés);
    JButton btLekérdez = new JButton("Lekérdez");
    pnFelső.add(btLekérdez);
    this.add(pnFelső, BorderLayout.NORTH);
    this.add(new JScrollPane(this.lDolgozók)); //alapból CENTER-be rakja
    this.add(lbStátusz=new JLabel(" "), BorderLayout.SOUTH);
    //
    btLekérdez.addActionListener(this);
    this.setAlwaysOnTop(true);
    this.setVisible(true);
    this.tfFizetés.requestFocus();
  }
  
  public static void main(String[] args) {
    new Nézet();
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      DefaultListModel dlm = 
              Modell.getDolgozóLista((String)cbMűvelet.getSelectedItem(), tfFizetés.getText());
      this.lDolgozók.setModel(dlm);
      this.lbStátusz.setText(
              "Fizetés " 
              +(String)cbMűvelet.getSelectedItem() + " "
              +tfFizetés.getText() + ", " 
              +dlm.getSize()+" fő"
      );
    } catch (IllegalArgumentException iae) {
      JOptionPane.showMessageDialog(
              this, 
              "<html><strong>hiba:</strong><br>"+iae.getMessage()+"</html>", 
              "Hibaüzenet", 
              JOptionPane.ERROR_MESSAGE);
    }
  }
}
//https://1drv.ms/f/s!AtHra2s5kiqB92YwkF9Iumq1EyOp
