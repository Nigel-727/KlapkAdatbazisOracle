//innen az ötlet:
//https://www.tutorialspoint.com/java/lang/runtime_exec_envp.htm

package adatbazisoraclemvc4;

import java.awt.Desktop;
import java.net.URI; //úri dolgok

/**
 *
 * @author Nigel-727
 */
public class RuntimeDemo {

   public static void main(String[] args) {
     try {
       Desktop.getDesktop().browse(new URI("index.html"));  //alapértelmezett böngésző; #juhé

//       // create a new array of 2 strings
//       String[] cmdArray = new String[2];
//
//       // first argument is the program we want to open
//       cmdArray[0] = 
////          "notepad.exe";
//            "c:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"; //OK
//
//       // second argument is a txt file we want to open with notepad
//       cmdArray[1] = "index.html";
//
//       // print a message
//       System.out.println("Executing notepad.exe and opening akármicsodafile");
//
//       // create a process and execute cmdArray and currect environment
//       //"Ezt a Runtime objektumot sem konstruktorral kell legyártani":
//       Process process = Runtime.getRuntime().exec(cmdArray,null); 
//
//       // print another message
//       System.out.println("akármicsodafile should now open.");
//
       } catch (Exception ex) {
       ex.printStackTrace();
       }
   }
}