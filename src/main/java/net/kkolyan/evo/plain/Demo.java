package net.kkolyan.evo.plain;

import net.kkolyan.evo.awt.GraphicEngine;

import java.util.Arrays;
import java.util.Random;

/**
 * @author nplekhanov
 */
public class Demo {
    static Random random = new Random();
    public static void main(String[] args) {
        int substanceCount = 4;
        World world = new World(substanceCount);
        for (int i = 0; i < 1; i ++) {
            BioUnit unit = world.addBioUnit();
            unit.setX(random.nextDouble() * 100);
            unit.setY(random.nextDouble() * 100);

            Arrays.fill(unit.getContent(), 50);
            int specialization = random.nextInt(substanceCount);
            unit.getType().getAttractions()[specialization] = 10;
            Arrays.fill(unit.getType().getConsumingRates(), 1000);
            Arrays.fill(unit.getType().getProducingRates(), 1);

            SubstanceConversion conversion = new SubstanceConversion(substanceCount);
            conversion.getInput()[specialization] = 1;
            conversion.getOutput()[random.nextInt(substanceCount)] = 1;

            unit.getType().getConversions().add(conversion);
        }
        for (int i = 0; i < 100; i ++) {
            SubstanceUnit unit = world.addSubstanceUnit(0);
            unit.setAmount(10 + 10 * random.nextInt(3));
            unit.setX(random.nextDouble() * 100);
            unit.setY(random.nextDouble() * 100);
        }
        for (int i = 0; i < 100; i ++) {
            SubstanceUnit unit = world.addSubstanceUnit(1);
            unit.setAmount(10 + 10 * random.nextInt(3));
            unit.setX(random.nextDouble() * 100 + 100);
            unit.setY(random.nextDouble() * 100);
        }
        for (int i = 0; i < 100; i ++) {
            SubstanceUnit unit = world.addSubstanceUnit(2);
            unit.setAmount(10 + 10 * random.nextInt(3));
            unit.setX(random.nextDouble() * 100);
            unit.setY(random.nextDouble() * 100 + 100);
        }
        for (int i = 0; i < 100; i ++) {
            SubstanceUnit unit = world.addSubstanceUnit(3);
            unit.setAmount(10 + 10 * random.nextInt(3));
            unit.setX(random.nextDouble() * 100 + 100);
            unit.setY(random.nextDouble() * 100 + 100);
        }

        GraphicEngine engine = new GraphicEngine(world);
        engine.setFps(12);
        engine.setSize(1024, 768);
        engine.start();
    }

    private static void fillRandom(double[] array, double magnifier) {
        for (int i = 0; i < array.length; i ++) {
            array[i] = random.nextDouble() * magnifier;
        }
    }
}
