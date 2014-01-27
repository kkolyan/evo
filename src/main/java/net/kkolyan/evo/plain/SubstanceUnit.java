package net.kkolyan.evo.plain;

/**
 * @author nplekhanov
 */
public class SubstanceUnit extends Object2d {
    private final int type;
    private double amount;

    public SubstanceUnit(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
