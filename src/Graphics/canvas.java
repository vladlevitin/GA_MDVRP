package Graphics;

import Objects.Customer;
import Objects.Depot;
import Objects.Route;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class canvas extends Canvas {
    public ArrayList<ArrayList<Route>> routes;
    public ArrayList<Depot> depots;
    public double max_cordinate;
    public double min_cordinate;
    public double boarder = 5.0D;

    public canvas() {
    }

    public void drawPoint(Graphics g, int x_cordinate, int y_cordinate, Color color, int size) {
        g.setColor(color);
        g.fillOval((x_cordinate + (int)(-this.min_cordinate + this.boarder)) * this.getWidth() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder) - size / 2, this.getHeight() - (y_cordinate + (int)(-this.min_cordinate + this.boarder)) * this.getHeight() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder) - size / 2, size, size);
    }

    public void drawLine(Graphics g, int x_cordinate_1, int y_cordinate_1, int x_cordinate_2, int y_cordinate_2, Color color) {
        g.setColor(color);
        g.drawLine((x_cordinate_1 + (int)(-this.min_cordinate + this.boarder)) * this.getWidth() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder), this.getHeight() - (y_cordinate_1 + (int)(-this.min_cordinate + this.boarder)) * this.getHeight() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder), (x_cordinate_2 + (int)(-this.min_cordinate + this.boarder)) * this.getWidth() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder), this.getHeight() - (y_cordinate_2 + (int)(-this.min_cordinate + this.boarder)) * this.getHeight() / (int)(this.max_cordinate - this.min_cordinate + 2.0D * this.boarder));
    }

    public void paint(Graphics g) {
        ArrayList<Color> colors = new ArrayList();
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);

        int j;
        for(j = 0; j < this.depots.size(); ++j) {
            this.drawPoint(g, ((Depot)this.depots.get(j)).x_cordinate, ((Depot)this.depots.get(j)).y_cordinate, Color.BLACK, 10);
        }

        for(j = 0; j < this.routes.size(); ++j) {
            for(int k = 0; k < ((ArrayList)this.routes.get(j)).size(); ++k) {
                for(int l = 0; l < ((Route)((ArrayList)this.routes.get(j)).get(k)).customers.size(); ++l) {
                    int x_cordinate = ((Customer)((Route)((ArrayList)this.routes.get(j)).get(k)).customers.get(l)).x_cordinate;
                    int y_cordinate = ((Customer)((Route)((ArrayList)this.routes.get(j)).get(k)).customers.get(l)).y_cordinate;
                    int depot_x_cordinate = ((Route)((ArrayList)this.routes.get(j)).get(k)).depot.x_cordinate;
                    int depot_y_cordinate = ((Route)((ArrayList)this.routes.get(j)).get(k)).depot.y_cordinate;
                    this.drawPoint(g, x_cordinate, y_cordinate, Color.BLUE, 7);
                    if (l == 0 || l == ((Route)((ArrayList)this.routes.get(j)).get(k)).customers.size() - 1) {
                        this.drawLine(g, x_cordinate, y_cordinate, depot_x_cordinate, depot_y_cordinate, (Color)colors.get(k % 7));
                    }

                    if (l > 0) {
                        int x_cordinate_prev = ((Customer)((Route)((ArrayList)this.routes.get(j)).get(k)).customers.get(l - 1)).x_cordinate;
                        int y_cordinate_prev = ((Customer)((Route)((ArrayList)this.routes.get(j)).get(k)).customers.get(l - 1)).y_cordinate;
                        this.drawLine(g, x_cordinate_prev, y_cordinate_prev, x_cordinate, y_cordinate, (Color)colors.get(k % 7));
                    }
                }
            }
        }

    }
}

