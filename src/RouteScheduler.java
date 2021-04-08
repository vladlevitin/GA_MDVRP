import Objects.Customer;
import Objects.Depot;
import Objects.Individual;
import Objects.Route;

import java.util.ArrayList;

public class RouteScheduler {
    public RouteScheduler(){

    }
    public double eucleadianDistance(int x_1, int y_1, int x_2, int y_2) {
        return Math.sqrt(Math.pow((double)(x_1 - x_2), 2.0D) + Math.pow((double)(y_1 - y_2), 2.0D));
    }

    public boolean validConstraint(Route route, Customer customer) {
        if (route.vechicle_load + customer.demand <= route.depot.max_load_vechicle &&
                route.depot.max_duration == 0 || route.distance + (double)customer.duration <= (double)route.depot.max_duration) {
            return true;
        } else {
            return false;
        }
    }

    public Customer getCustomer(Integer ID, ArrayList<Customer> customers){
        for(Customer customer:customers){
            if(customer.ID==ID){
                return customer;
            }
        }
        return null;
    }

    public ArrayList<Route> phaseOneSchedule(ArrayList<Integer> gene, ArrayList<Customer> customers, Depot depot){
        ArrayList<Route> routeList = new ArrayList();
        Route route = new Route(depot);
        routeList.add(route);

        for (int i = 0; i < gene.size(); i++) {
            if(route.depot.n_vechicles<routeList.size()){
                System.out.println("Vechicles exceeded! Vechicle allowed: "+route.depot.n_vechicles+", Vechicles used: "+routeList.size());
            }

            Customer customer=getCustomer(gene.get(i),customers);
            double distance=0;

            if(i!=0){
                Customer prevCustomer=getCustomer(gene.get(i-1),customers);
                distance=eucleadianDistance(prevCustomer.x_cordinate,prevCustomer.y_cordinate,customer.x_cordinate,customer.y_cordinate);
            }

            double depotDistance=eucleadianDistance(depot.x_cordinate,depot.y_cordinate,customer.x_cordinate,customer.y_cordinate);

            if(validConstraint(route, customer)){

                route.customers.add(customer);
                route.vechicle_load+=customer.demand;

                //check if first customer or last customer in the route
                if(route.customers.size()==1){
                    route.distance+=depotDistance;
                }
                else{
                    route.distance+=distance;
                    if(route.customers.size()==gene.size()){
                        route.distance+=depotDistance;
                    }
                }

            }

            else{
                route.distance+=depotDistance; //since it is the final customer on the route

                route = new Route(depot);
                routeList.add(route);
                route.customers.add(customer);
                route.distance+=depotDistance;
                route.vechicle_load+=customer.demand;

            }

        }
        return routeList;
    }
    public void setRouteDuration(Route route) {
        route.distance = 0.0D;

        for(int i = 0; i < route.customers.size(); ++i) {
            double distance;
            if (i == 0) {
                distance = this.eucleadianDistance(route.depot.x_cordinate, route.depot.y_cordinate, ((Customer)route.customers.get(i)).x_cordinate, ((Customer)route.customers.get(i)).y_cordinate);
                route.distance += distance + (double)((Customer)route.customers.get(i)).duration;
            } else {
                distance = this.eucleadianDistance(((Customer)route.customers.get(i)).x_cordinate, ((Customer)route.customers.get(i)).y_cordinate, ((Customer)route.customers.get(i - 1)).x_cordinate, ((Customer)route.customers.get(i - 1)).y_cordinate);
                route.distance += distance + (double)((Customer)route.customers.get(i)).duration;
                if (i == route.customers.size() - 1) {
                    distance = this.eucleadianDistance(route.depot.x_cordinate, route.depot.y_cordinate, ((Customer)route.customers.get(i)).x_cordinate, ((Customer)route.customers.get(i)).y_cordinate);
                    route.distance += distance;
                }
            }
        }

    }
    public void setRouteLoad(Route route) {
        route.vechicle_load = 0;

        for(int i = 0; i < route.customers.size(); ++i) {
            route.vechicle_load += ((Customer)route.customers.get(i)).demand;
        }

    }

    public void phaseTwoSchedule(ArrayList<Route> depotRoutes) {
        for (int i = 0; i <depotRoutes.size()-1 ; i++) {

            Route routeOne=depotRoutes.get(i);
            Route routeTwo=depotRoutes.get(i+1);

            Double routeOneDistance=routeOne.distance;
            Double routeTwoDistance=routeTwo.distance;

            ArrayList<Customer> customersRouteOne = new ArrayList(routeOne.customers);
            ArrayList<Customer> customersRouteTwo = new ArrayList(routeTwo.customers);

            Customer customer = routeOne.customers.get(routeOne.customers.size() - 1);

            if(customersRouteOne.size()>0 && validConstraint(routeTwo, customer)){
                Route newRouteOne = new Route(routeOne.depot);
                Route newRouteTwo = new Route(routeTwo.depot);

                newRouteOne.customers=new ArrayList<Customer>(customersRouteOne);
                newRouteTwo.customers=new ArrayList<Customer>(customersRouteTwo);

                newRouteOne.customers.remove(customer);
                newRouteTwo.customers.add(0,customer);

                setRouteDuration(newRouteOne);
                setRouteDuration(newRouteTwo);

                double newRouteOneDistance=newRouteOne.distance;
                double newRouteTwoDistance=newRouteTwo.distance;

                setRouteLoad(newRouteOne);
                setRouteLoad(newRouteTwo);

                if(routeOneDistance+routeTwoDistance>newRouteOneDistance+newRouteTwoDistance){
                    //System.out.println("Routes modified");
                    depotRoutes.set(i, newRouteOne);
                    depotRoutes.set(i+1, newRouteTwo);
                }


            }


        }
    }



    public void scheduleRoutes(Individual individual) {
        ArrayList<ArrayList<Route>> routes = new ArrayList<ArrayList<Route>>();
        for (int i = 0; i <individual.chromosome.size(); i++) {

            ArrayList<Route> depotRoutes=phaseOneSchedule(individual.chromosome.get(i), individual.model.customers, individual.model.depots.get(i));
            //System.out.println(depotRoutes);
            phaseTwoSchedule(depotRoutes);
            routes.add(depotRoutes);

        }
        //System.out.println(routes.size());
        individual.routes=new ArrayList<ArrayList<Route>>();
        individual.routes.addAll(routes);

    }
}
