package org.marcestarlet.cartravelcalculator.api;

import org.marcestarlet.cartravelcalculator.api.service.TravelCalculatorService;

import java.util.List;

public class TravelController implements ITravelController {

    private TravelCalculatorService travelService;

    public TravelController(){
        this.travelService = new TravelCalculatorService();
    }

    public TravelController(List<String> carSimulator){
        this.travelService = new TravelCalculatorService(carSimulator);
    }

    @Override
    public String calculateTravelCost(String travelerLocation, String travelerDestination) { return null; }

    @Override
    public String requestTravel(String travelerLocation) {

        return travelService.requestTravelService(travelerLocation);
    }

    @Override
    public String getCurrentTravelStatus(String keyTravel) {
        return null;
    }
}
