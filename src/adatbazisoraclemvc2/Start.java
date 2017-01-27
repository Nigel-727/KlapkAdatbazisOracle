package adatbazisoraclemvc2;

public class Start { //itt döntjük el h melyik nézetet példányosítjuk
  public static void main(String[] args) {
    Modell modell = new Modell(); //!: sorrend fontos (ha a nézet megvan, a modell utána abból már építkezhet)
    Nézet nézet = new Nézet(modell); //itt mutatjuk be egymásnak őket (nézetnek a modellt)
    nézet.setVisible(true);
  }
}