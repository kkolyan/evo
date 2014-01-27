package net.kkolyan.evo.plain;

/**
 * @author nplekhanov
 */
public class BioUnit extends Object2d {
    private final double[] content;
    private final BioUnitType type;
    private double consistentContentAmount;

    public BioUnit(int maxSubstanceType) {
        content = new double[maxSubstanceType];
        type = new BioUnitType(maxSubstanceType);
    }

    public double[] getContent() {
        return content;
    }

    public BioUnitType getType() {
        return type;
    }

    public double getConsistentContentAmount() {
        return consistentContentAmount;
    }

    public void setConsistentContentAmount(double consistentContentAmount) {
        this.consistentContentAmount = consistentContentAmount;
    }
}
