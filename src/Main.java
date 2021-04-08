import Graphics.Display;
import Objects.Individual;
import Objects.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        int generations=1000;
        int populationSize=100;
        int nElites=10;
        double r_bound=0.6;
        int tournamentSize=2;

        Model model=new Model();
        FileManager fileManager=new FileManager();
        fileManager.ImportData("Testing_Data/Data_Files/2", model);
        fileManager.nearestDepot(model);

        Individual initialIndividual= new Individual(model);

        initialIndividual.setChromosome();

        RouteScheduler scheduler=new RouteScheduler();

        scheduler.scheduleRoutes(initialIndividual);

        GA ga =new GA();

        ArrayList<Individual> population = ga.generateIntialPopulation(initialIndividual,populationSize);

        population=ga.sortPopulationFitness(population);

        for (int i = 0; i < generations; i++) {
            System.out.println("Generation: "+i);
            ArrayList<Individual> parents = new ArrayList<Individual>();

            for(int j = 0; j < nElites; ++j) {
                parents.add(population.get(j));
            }

            ArrayList<Individual> allChildren=new ArrayList<Individual>();

            for (int j = 0; j <populationSize; j++) {
                ArrayList<Individual> tournamentSetOne=new ArrayList<Individual>();
                ArrayList<Individual> tournamentSetTwo=new ArrayList<Individual>();

                for (int k = 0; k < tournamentSize; k++) {
                    Individual rand1Individual=parents.get(ga.generateRandomInt(0,parents.size()-1));
                    tournamentSetOne.add(rand1Individual);

                    Individual rand2Individual=parents.get(ga.generateRandomInt(0,parents.size()-1));
                    tournamentSetTwo.add(rand2Individual);


                }

                Individual selectedParentOne=ga.tournamentSelection(tournamentSetOne,r_bound);
                Individual selectedParentTwo=ga.tournamentSelection(tournamentSetTwo,r_bound);

                ArrayList<Individual> children= ga.BestCostRouteCrossOver(selectedParentOne,selectedParentTwo);
                allChildren.addAll(children);

            }

            population.addAll(allChildren);
            population = ga.sortPopulationFitness(population);

            ArrayList<Individual> survivors=new ArrayList<Individual>();
            for(int j = 0; j < populationSize; ++j) {
                survivors.add(population.get(j));
            }

            population=survivors;
            System.out.println("Lowest Distance: "+population.get(0).fitness);
        }

        //

        //Individual parent=ga.tournamentSelection(population,r_bound);

        fileManager.exportSolution(population.get(0), "Testing_Data/Solution_Files/solution.txt");

        Display display=new Display(population.get(0));

    }
}

/*
   for (int i = 0; i < model.depots.size(); i++) {
            System.out.println(model.depots.get(i).customers);
        }

        System.out.println(initialIndividual.chromosome);
        System.out.println("\n");
        for(Individual individual:population){
            System.out.println(individual.fitness);
        }



 */
