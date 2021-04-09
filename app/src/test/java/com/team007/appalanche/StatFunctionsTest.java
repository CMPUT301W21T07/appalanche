package com.team007.appalanche;

import com.team007.appalanche.StatFunctions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StatFunctionsTest {
    @Test
    public void testCalculateSD() {
        ArrayList<Integer> integerList = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            integerList.add(i);
        }
        assertEquals(28.86607004772212, StatFunctions.calculateSD(integerList));
    }

    @Test
    public void testDoubleCalculateSD() {
        ArrayList<Double> doubleList = new ArrayList<Double>();
        for (double i = 0.0; i < 150; i = i + 0.3125) {
            doubleList.add(i);
        }
        assertEquals(43.30117621934959, StatFunctions.doubleCalculateSD(doubleList));
    }

    @Test
    public void testCalculateQuartiles() {
        ArrayList<Integer> integerList = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            integerList.add(i);
        }
        double[] result = new double[]{24.0, 49.0, 74.0};
        double[] quartiles = new double[3];
        quartiles = StatFunctions.calculateQuartiles(integerList);
        assertEquals(result[0], quartiles[0]);
        assertEquals(result[1], quartiles[1]);
        assertEquals(result[2], quartiles[2]);
    }

    @Test
    public void testDoubleCalculateQuartiles() {
        ArrayList<Double> doubleList = new ArrayList<Double>();
        for (Double i = 0.0; i < 200; i++) {
            doubleList.add(i);
        }
        double[] result = new double[]{49.5, 99.5, 149.5};
        double[] quartiles = new double[3];
        quartiles = StatFunctions.doubleCalculateQuartiles(doubleList);
        assertEquals(result[0], quartiles[0]);
        assertEquals(result[1], quartiles[1]);
        assertEquals(result[2], quartiles[2]);
    }
}
