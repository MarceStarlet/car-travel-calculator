package org.marcestarlet.cartravelcalculator.api.service;

public abstract class AbstractTravelService {

    protected abstract String requestTravelService( String travelerLocation ) throws IllegalArgumentException;

    protected abstract boolean validateLocation( String travelerLocation );

    protected abstract String calculateShortestPath( String travelerLocation );
}
