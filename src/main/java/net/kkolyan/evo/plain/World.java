package net.kkolyan.evo.plain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author nplekhanov
 */
public class World implements Scene {

    public static final double GRAVITY_FACTOR = 1-2;
    private static final double QUANT_OF_PRODUCTION = 10;

    private List<BioUnit> bioUnits = new ArrayList<BioUnit>();
    private List<SubstanceUnit> substanceUnits = new ArrayList<SubstanceUnit>();
    private int substanceCount;

    private Color[] colors;

    public World(int substanceCount) {
        this.substanceCount = substanceCount;
        colors = new Color[substanceCount];

        colors = SubstanceColors.getColors(substanceCount);
    }

    private double getRadius(double amount) {
        return 2.0 * Math.sqrt(amount / Math.PI);
    }

    @Override
    public void render(Renderer renderer) {
        update();

        for (SubstanceUnit unit: substanceUnits) {
            double radius = getRadius(unit.getAmount());
            Color color = colors[unit.getType()];
            renderer.drawEllipse(unit.getX(), unit.getY(), radius, radius, color, true);
            renderer.drawLabel(unit.getX(), unit.getY(), unit.getAmount() + "", Color.WHITE);
        }
        for (BioUnit unit: bioUnits) {
            double radius = getRadius(unit.getConsistentContentAmount());
            renderer.drawEllipse(unit.getX(), unit.getY(), radius, radius, Color.RED, true);
            renderer.drawEllipse(unit.getX(), unit.getY(), radius, radius, Color.BLACK, false);
            renderer.drawLabel(unit.getX(), unit.getY(), unit.getConsistentContentAmount()+"", Color.WHITE);
        }

        renderer.drawText(String.format("bio: %s, substance: %s", bioUnits.size(), substanceUnits.size()), 10, 30, Color.WHITE);
    }

    public void update() {
//        Collections.shuffle(bioUnits);
//        Collections.shuffle(substanceUnits);

        List<SubstanceUnit> removedSubstances = new ArrayList<SubstanceUnit>();

        for (BioUnit bioUnit: bioUnits) {
            double contentAmount = 0;
            for (int i = 0; i < substanceCount; i ++) {
                contentAmount += bioUnit.getContent()[i];
            }
            bioUnit.setConsistentContentAmount(contentAmount);
        }

        for (BioUnit bio : bioUnits) {
            Vector attraction = new Vector();

            for (SubstanceUnit substance : substanceUnits) {
                double distance = bio.distanceTo(substance);

                Vector offset = bio.vectorTo(substance);
                offset.multiply(bio.getType().getAttractions()[substance.getType()]);
                offset.multiply(GRAVITY_FACTOR / Math.pow(distance, 2));
                offset.multiply(substance.getAmount());
                attraction.transpose(offset);

                if (distance < getRadius(bio.getConsistentContentAmount()) + getRadius(substance.getAmount())) {
                    double rate = bio.getType().getConsumingRates()[substance.getType()];
                    rate = Math.min(rate, substance.getAmount());
                    substance.setAmount(substance.getAmount() - rate);
                    bio.getContent()[substance.getType()] += rate;

                    if (substance.getAmount() <= 0) {
                        removedSubstances.add(substance);
                    }
                }
            }

            bio.transpose(attraction);
        }

        substanceUnits.removeAll(removedSubstances);
    }
    
    public BioUnit addBioUnit() {
        BioUnit unit = new BioUnit(substanceCount);
        bioUnits.add(unit);
        return unit;
    }
    
    public SubstanceUnit addSubstanceUnit(int type) {
        SubstanceUnit unit = new SubstanceUnit(type);
        substanceUnits.add(unit);
        return unit;
    }
}
