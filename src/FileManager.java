import Objects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FileManager {
    public FileManager() {
    }

    public void ImportData(String filename, Model model) throws FileNotFoundException {
        int max_cordinate = 0;
        int min_cordinate = 0;
        Scanner scanner = new Scanner(new File(filename));
        String[] list = scanner.nextLine().split(" ");
        int n_vechicles = Integer.parseInt(list[0]);
        ArrayList<Integer> duration_list = new ArrayList();
        ArrayList<Integer> max_load_vechicle_list = new ArrayList();

        int i;
        int ID;
        int x_cordinate;
        for(i = 0; i < Integer.parseInt(list[2]); ++i) {
            ID = scanner.nextInt();
            x_cordinate = scanner.nextInt();
            duration_list.add(ID);
            max_load_vechicle_list.add(x_cordinate);
        }

        int y_cordinate;
        int duration;
        int max_load_vechicle;
        for(i = 0; i < Integer.parseInt(list[1]); ++i) {
            ID = scanner.nextInt();
            x_cordinate = scanner.nextInt();
            y_cordinate = scanner.nextInt();
            duration = scanner.nextInt();
            max_load_vechicle = scanner.nextInt();
            Customer customer = new Customer(ID, x_cordinate, y_cordinate, duration, max_load_vechicle);
            model.customers.add(customer);
            max_cordinate = Math.max(max_cordinate, Math.max(x_cordinate, y_cordinate));
            min_cordinate = Math.min(min_cordinate, Math.min(x_cordinate, y_cordinate));
            scanner.nextLine();
        }

        for(i = 0; i < Integer.parseInt(list[2]); ++i) {
            ID = scanner.nextInt();
            x_cordinate = scanner.nextInt();
            y_cordinate = scanner.nextInt();
            duration = (Integer)duration_list.get(i);
            max_load_vechicle = (Integer)max_load_vechicle_list.get(i);
            Depot depot = new Depot(ID, x_cordinate, y_cordinate, duration, max_load_vechicle, n_vechicles);
            model.depots.add(depot);
            max_cordinate = Math.max(max_cordinate, Math.max(x_cordinate, y_cordinate));
            min_cordinate = Math.min(min_cordinate, Math.min(x_cordinate, y_cordinate));
            scanner.nextLine();
        }

        model.max_cordinate = max_cordinate;
        model.min_cordinate = min_cordinate;
        scanner.close();
    }

    public void nearestDepot(Model model) {
        Iterator customers = model.customers.iterator();

        while(customers.hasNext()) {
            Customer customer = (Customer)customers.next();
            Double min_distance = 999.0D;
            Depot closest_Depot = null;
            Iterator depots = model.depots.iterator();

            while(depots.hasNext()) {
                Depot depot = (Depot)depots.next();
                Double distance = Math.sqrt(Math.pow((double)(customer.x_cordinate - depot.x_cordinate), 2.0D) + Math.pow((double)(customer.y_cordinate - depot.y_cordinate), 2.0D));
                if (distance < min_distance) {
                    min_distance = distance;
                    closest_Depot = depot;
                }
            }

            closest_Depot.addCustomer(customer);
        }

    }

    public void exportSolution(Individual individual, String filename) throws IOException {
        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file);
        double fitness = individual.getFitness();
        fileWriter.write(fitness + "\n");

        for(int i = 0; i < individual.routes.size(); ++i) {
            for(int j = 0; j < ((ArrayList)individual.routes.get(i)).size(); ++j) {
                double distance = ((Route)((ArrayList)individual.routes.get(i)).get(j)).distance;
                int vechicle_load = ((Route)((ArrayList)individual.routes.get(i)).get(j)).vechicle_load;
                fileWriter.write(i + 1 + "\t" + (j + 1) + "\t" + distance + "\t" + vechicle_load + "\t0");

                for(int k = 0; k < (individual.routes.get(i).get(j)).customers.size(); ++k) {
                    Customer customer = (Customer)((Route)((ArrayList)individual.routes.get(i)).get(j)).customers.get(k);
                    fileWriter.write(" " + customer.ID);
                }

                fileWriter.write(" 0\n");
            }
        }

        fileWriter.close();
    }




}
