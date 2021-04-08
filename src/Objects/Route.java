package Objects;

import java.util.ArrayList;

public class Route {
    public ArrayList<Customer> customers = new ArrayList();
    public int vechicle_load = 0;
    public double distance = 0.0;
    public Depot depot;

    public Route(Depot depot) {
        this.depot = depot;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String toString() {
        return this.depot.toString();
    }

}
