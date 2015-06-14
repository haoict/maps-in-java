
import com.mygis.view.MyFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class WktReaderTest {

    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");   
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            System.err.println("Look and feel not set.");
        } catch (InstantiationException e) {
            System.err.println("Look and feel not set.");
        } catch (IllegalAccessException e) {
             System.err.println("Look and feel not set.");
        } catch (UnsupportedLookAndFeelException e) {
             System.err.println("Look and feel not set.");
        }
        
        MyFrame myFrame = new MyFrame(800, 600);
        myFrame.setVisible(true);
    }
}
