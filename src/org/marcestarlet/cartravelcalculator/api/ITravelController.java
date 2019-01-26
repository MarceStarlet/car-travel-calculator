package org.marcestarlet.cartravelcalculator.api;

public interface ITravelController {

    /**
     * Returns the travel calculated cost from traveler location to traveler destination
     * @param travelerLocation
     * @param travelerDestination
     * @return
     */
    public String calculateTravelCost(String travelerLocation, String travelerDestination);

    /**
     * Request a travel cal and return travel information
     * @param travelerLocation
     * @return
     */
    public String requestTravel(String travelerLocation);

    /**
     * Returns the current status of the travel requested
     * @param keyTravel
     * @return
     */
    public String getCurrentTravelStatus(String keyTravel);
}
