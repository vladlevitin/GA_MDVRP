package Objects;

import java.util.ArrayList;

public class Depot {
    public int n_vechicles;
    public int max_duration;
    public int max_load_vechicle;
    public boolean vehicles_exceeded = false;

    public int ID;
    public int x_cordinate;
    public int y_cordinate;
    public ArrayList<Customer> customers = new ArrayList();

    public Depot(int ID, int x_cordinate, int y_cordinate, int max_duration, int max_load_vechicle, int n_vechicles) {
        this.ID = ID;
        this.x_cordinate = x_cordinate;
        this.y_cordinate = y_cordinate;
        this.max_duration = max_duration;
        this.max_load_vechicle = max_load_vechicle;
        this.n_vechicles = n_vechicles;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public String toString() {
        return "Depot ID: " + this.ID + ", n_vechicles: " + this.n_vechicles + ", max_duration: " + this.max_duration + ", max_load_vechicle: " + this.max_load_vechicle + " (x:" + this.x_cordinate + ", y:" + this.y_cordinate + ")";
    }
}

