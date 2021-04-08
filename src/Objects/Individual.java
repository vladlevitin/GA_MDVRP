package Objects;

import java.util.ArrayList;

public class Individual {
     public ArrayList<ArrayList<Integer>> chromosome=new ArrayList<ArrayList<Integer>>();
     public ArrayList<ArrayList<Route>> routes=new ArrayList<ArrayList<Route>>();

     public Model model;

     public double fitness = 0.0D;

     public Individual(Model model){
        this.model=model; //refrence the adress to the model
     }

     public void setChromosome(){
          for (int i=0; i<this.model.depots.size();i++){
               ArrayList<Integer> gene= new ArrayList<>();
               for(int j=0; j<this.model.depots.get(i).customers.size();j++){
                    gene.add(this.model.depots.get(i).customers.get(j).ID);
               }
               this.chromosome.add(gene);
          }

     }

     public double getFitness() {
          double fit_val = 0.0D;

          for(int i = 0; i < this.routes.size(); ++i) {
               for(int j = 0; j < ((ArrayList)this.routes.get(i)).size(); ++j) {
                    fit_val += ((Route)((ArrayList)this.routes.get(i)).get(j)).distance;
               }
          }

          return fit_val;
     }




}
