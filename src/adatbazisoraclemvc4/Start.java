package adatbazisoraclemvc4;

public class Start {
  public static void main(String[] args) {
//    Modell modell = new Modell1();
//    Modell modell = new Modell2();
    Modell modell = new Modell3();
//    Modell modell = new Modell3byKároly(); //#müxik :)
    new Nézet(modell).setVisible(true); //uebben a csomagban van, ezért nem kell neki mondani semmit
  }
}