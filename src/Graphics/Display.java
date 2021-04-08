package Graphics;
import Objects.Individual;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;

public class Display extends JFrame {
    canvas canvas = new canvas();

    public Display(Individual individual) {
        this.canvas.routes = individual.routes;
        this.canvas.max_cordinate = (double)individual.model.max_cordinate;
        this.canvas.min_cordinate = (double)individual.model.min_cordinate;
        this.canvas.depots = individual.model.depots;
        this.setLayout(new BorderLayout());
        this.setSize(900, 900);
        this.setTitle("Graphical Solution");
        this.add(this.canvas);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }
}