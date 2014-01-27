package net.kkolyan.evo.plain;

/**
 * @author nplekhanov
 */
public class SubstanceConversion {
    private double[] input;
    private double[] output;

    public SubstanceConversion(int substanceCount) {
        this.input = new double[substanceCount];
        this.output = new double[substanceCount];
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }
}
