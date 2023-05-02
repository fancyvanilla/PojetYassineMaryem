import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DynamicPanelGrid extends JPanel {
    
    private String[] labels;
    private Border border = BorderFactory.createLineBorder(Color.BLACK);
    private int panelWidth = 100;
    private int panelHeight = 100;
    private userDao dao;

    public DynamicPanelGrid() {
         super(new GridLayout(0, 2, 10, 10)); // 2 columns, 10px horizontal and vertical gaps
         String[] labels={"proba","coding","cybersec"}; // here we will have labels=dao.getMyClasses()
         this.lables=labels;
         setPreferredSize(new Dimension(450, 0)); // set preferred width to 450px
        if (labels != null) {
            for (String label : labels) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(panelWidth, panelHeight)); // set fixed panel size
                panel.setBorder(border);
                panel.add(new JLabel(label));
                add(panel);
            }
        }
    }
}
