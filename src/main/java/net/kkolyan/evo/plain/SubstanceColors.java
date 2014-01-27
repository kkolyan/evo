package net.kkolyan.evo.plain;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * @author nplekhanov
 */
public class SubstanceColors {
    public static Color[] getColors(int substanceCount) {
        Color[] colors = new Color[substanceCount];
        Random random = new Random(1000);
        for (int i = 0; i < substanceCount; i ++ ) {
            colors[i]  = new Color(
                    128 + random.nextInt(128),
                    128 + random.nextInt(128),
                    128 + random.nextInt(128));
        }
        return colors;
    }

    public static void main(String[] args) {
        getColors(16);
    }
}
