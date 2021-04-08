import Objects.Customer;
import Objects.Depot;
import Objects.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GA {

    public GA(){

    }

    public ArrayList<Individual> generateIntialPopulation(Individual initialIndividual, int populationSize){
          ArrayList<Individual> population=new ArrayList<Individual>();
          population.add(initialIndividual);
          RouteScheduler scheduler=new RouteScheduler();

          for (int i=0; i<populationSize-1;i++){
              Individual individual =new Individual(initialIndividual.model);
              individual.setChromosome();

              for(int j=0; j<individual.chromosome.size();j++){
                  Collections.shuffle(individual.chromosome.get(j));
              }

              scheduler.scheduleRoutes(individual);
              individual.fitness=individual.getFitness();
              population.add(individual);
          }


          return population;

    }
    public ArrayList<Individual> sortPopulationFitness(ArrayList<Individual> population){
        ArrayList<Individual> sortedPopulation =(ArrayList<Individual>) population.clone();
        sortedPopulation.sort(Comparator.comparing(individual -> individual.getFitness()));
        return sortedPopulation;
    }

    public int generateRandomInt(int min, int max){

        int index = (int)Math.floor(Math.random()*(max-min+1)+min);

        return index;

    }

    public Individual tournamentSelection(ArrayList<Individual> tournamentSet, double r_bound){
        double r=Math.random();
        //Forecast element = Collections.max(forecasts, Comparator.comparingInt(Forecast::getTemperature));
        if(r<r_bound){
            //return fittest individual
            Individual individual= Collections.min(tournamentSet, Comparator.comparingDouble(Individual::getFitness));
            return individual;
        }
        else{
            //return random individual
            int random_int = generateRandomInt(0,(tournamentSet.size()-1));
            return tournamentSet.get(random_int);
        }

    }

    public ArrayList<Individual> BestCostRouteCrossOver(Individual parent1, Individual parent2){

        ArrayList<Individual> children=new ArrayList<Individual>();

        //randomly select depot

        int depot_index = generateRandomInt(0, parent1.model.depots.size()-1);

        Depot depot = parent1.model.depots.get(depot_index);

        ArrayList<Integer> gene1Parent = parent1.chromosome.get(depot_index);
        ArrayList<Integer> gene2Parent = parent2.chromosome.get(depot_index);

        //randomly generate indexes
        int index_1 = generateRandomInt(0,gene1Parent.size());
        int index_2 = generateRandomInt(0,gene2Parent.size());

        //recombine the the chromosomes for child 1
        ArrayList<Integer> gene1Child=new ArrayList<Integer>(gene1Parent.subList(Math.min(index_1, index_2), Math.max(index_1, index_2)));

        for(Integer i:gene1Parent){
            if(!gene1Child.contains(i)){
                gene1Child.add(i);
            }
        }

        ArrayList<ArrayList<Integer>> chromosome1Child=new ArrayList<ArrayList<Integer>>(parent1.chromosome);

        chromosome1Child.set(depot_index,gene1Child);

        Individual child1=new Individual(parent1.model);

        child1.chromosome=chromosome1Child;

        RouteScheduler sheduler=new RouteScheduler();

        sheduler.scheduleRoutes(child1);

        child1.fitness=child1.getFitness();

        children.add(child1);

        //recombining procedure for child 2

        ArrayList<Integer> gene2Child=new ArrayList<Integer>(gene2Parent.subList(Math.min(index_1, index_2), Math.max(index_1, index_2)));

        for(Integer i:gene2Parent){
            if(!gene2Child.contains(i)){
                gene2Child.add(i);
            }
        }

        ArrayList<ArrayList<Integer>> chromosome2Child =new ArrayList<ArrayList<Integer>>(parent2.chromosome);

        chromosome2Child.set(depot_index,gene2Child);

        Individual child2=new Individual(parent2.model);

        child2.chromosome=chromosome2Child;

        sheduler.scheduleRoutes(child2);

        child2.fitness=child2.getFitness();

        children.add(child2);

        return children;
    }





}
