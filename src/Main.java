import Graphics.Display;
import Objects.Individual;
import Objects.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        int populationSize=100000;
        double r_bound=1;

        Model model=new Model();
        FileManager fileManager=new FileManager();
        fileManager.ImportData("Testing_Data/Data_Files/p01", model);
        fileManager.nearestDepot(model);

        Individual initialIndividual= new Individual(model);

        initialIndividual.setChromosome();

        for (int i = 0; i < model.depots.size(); i++) {
            System.out.println(model.depots.get(i).customers);
        }

        System.out.println(initialIndividual.chromosome);
        System.out.println("\n");

        RouteScheduler scheduler=new RouteScheduler();

        scheduler.scheduleRoutes(initialIndividual);

        GA ga =new GA();

        ArrayList<Individual> population = ga.generateIntialPopulation(initialIndividual,populationSize);

        for(Individual individual:population){
            System.out.println(individual.fitness);
        }

        //population=ga.sortPopulationFitness(population);

        Individual parent=ga.tournamentSelection(population,r_bound);

        fileManager.exportSolution(parent, "Testing_Data/Solution_Files/solution.txt");

        Display display=new Display(parent);

    }
}
