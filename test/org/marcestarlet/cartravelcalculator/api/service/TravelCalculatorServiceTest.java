package org.marcestarlet.cartravelcalculator.api.service;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class TravelCalculatorServiceTest {

    @Test
    public void requestTravelSrvVertexA() {
        TravelCalculatorService calc = new TravelCalculatorService(Arrays.asList("P","Q","S"));

        assertEquals("S", calc.requestTravelService("A"));
    }

    @Test
    public void requestTravelSrvVertexADiffLevels() {
        TravelCalculatorService calc = new TravelCalculatorService(Arrays.asList("A11","Q","S"));

        assertEquals("S", calc.requestTravelService("A"));
    }

    @Test
    public void requestTravelSrvVertexE() {
        TravelCalculatorService calc = new TravelCalculatorService(Arrays.asList("A10","A9","A7"));

        assertEquals("A7", calc.requestTravelService("E"));
    }

    @Test
    public void requestTravelSrvVertexENoCars() {
        TravelCalculatorService calc = new TravelCalculatorService(Arrays.asList("X","XX","XXX"));

        assertNull("No car found!", calc.requestTravelService("E"));
    }

    @Test
    public void requestTravelSrvVertexENoCars2() { // scenario not valid
        TravelCalculatorService calc = new TravelCalculatorService(Arrays.asList("H","I","J"));

        assertNotEquals("A7", calc.requestTravelService("E"));
    }

    @Test ( expected = IllegalArgumentException.class )
    public void requestTravelSrvInvalidLocation() {
        TravelCalculatorService calc = new TravelCalculatorService();

        calc.requestTravelService("XCV");
    }
}