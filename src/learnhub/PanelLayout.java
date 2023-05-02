
package learnhub;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import static javax.swing.text.StyleConstants.setBackground;

public class PanelLayout extends JFrame {
    public PanelLayout() throws ClassNotFoundException, SQLException {
        super("Panel Layout"); // Set the title of the JFrame
         userDao dao=new userDao();
         ArrayList<String> panelTexts=new ArrayList<>();
         ArrayList<Course> res=dao.getUserCourses(1);
         for (Course elmt : res){
             panelTexts.add(elmt.title);
         }

        // Create a JPanel to hold the other panels
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayout(2, 3, 20, 10)); // Set the GridLayout with 3 columns, horizontal gap of 50 pixels, and vertical gap of 10 pixels

        // Loop over the array and create a new JPanel with the given text for each element
        for (String panelText : panelTexts) {
            // Create a new JPanel with the given text
            JPanel panel = new JPanel();
            JLabel label = new JLabel(panelText);
            panel.add(label);
            
            // Set the panel's style (optional)
            panel.setBackground(new java.awt.Color(255, 247, 250));
            panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            // Add the panel to the panel container
            panelContainer.add(panel);
        }

        // Add the panel container to the JFrame
        getContentPane().add(panelContainer);

        // Set the size of the JFrame and make it visible
        //setBackground.Color(255,247,250);
        setSize(1150, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }
}