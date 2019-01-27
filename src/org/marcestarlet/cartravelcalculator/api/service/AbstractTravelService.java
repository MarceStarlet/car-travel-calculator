package org.marcestarlet.cartravelcalculator.api.service;

public abstract class AbstractTravelService {

    /**
     * Requests a travel.
     * @param travelerLocation The location of the Traveler
     * @return the Travel confirmation {id: 12435, car:{id:"AD1324G", name:"", lastName:""}}
     * @throws IllegalArgumentException
     */
    protected abstract String requestTravelService( String travelerLocation ) throws IllegalArgumentException;

    /**
     * Validates the traveler location
     * @param travelerLocation The location of the Traveler
     * @return true if is valid
     */
    protected abstract boolean validateLocation( String travelerLocation );

    /**
     * Calculates the shortest path to the nearest car for the travel
     * @param travelerLocation The location of the Traveler
     * @return the key vertex where from the nearest car
     */
    protected abstract String calculateShortestPath( String travelerLocation );
}
