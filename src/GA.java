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







}
