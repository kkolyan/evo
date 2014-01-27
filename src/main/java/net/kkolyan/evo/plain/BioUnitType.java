package net.kkolyan.evo.plain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author nplekhanov
 */
public class BioUnitType {
    public static final double CONSUMPTION_DISTANCE = 20.0;

    private final double[] attractions;
    private final double[] consumingRates;
    private final double[] producingRates;
    private final Collection<SubstanceConversion> conversions = new ArrayList<SubstanceConversion>();


    public BioUnitType(int maxSubstanceType) {
        attractions = new double[maxSubstanceType];
        consumingRates = new double[maxSubstanceType];
        producingRates = new double[maxSubstanceType];
    }

    public double[] getAttractions() {
        return attractions;
    }

    public double[] getConsumingRates() {
        return consumingRates;
    }

    public double[] getProducingRates() {
        return producingRates;
    }

    public Collection<SubstanceConversion> getConversions() {
        return conversions;
    }
}
