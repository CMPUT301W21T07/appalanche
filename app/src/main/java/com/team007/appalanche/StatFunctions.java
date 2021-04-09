package com.team007.appalanche;

import java.util.ArrayList;
import java.util.Collections;

public class StatFunctions {
    public static double calculateSD(ArrayList<Integer> list){
        double sum = 0.0, standardDeviation = 0.0;
        int length = list.size();

        for(double num : list) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: list) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    public static double doubleCalculateSD(ArrayList<Double> list){
        double sum = 0.0, standardDeviation = 0.0;
        int length = list.size();

        for(double num : list) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: list) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    public static double[] calculateQuartiles(ArrayList<Integer> list){
        double quartiles[] = new double[3];
        for (int i = 1; i < 4; i++) {
            float length = list.size() + 1;
            double quartile;
            float newArraySize = (length * ((float) (i) * 25 / 100)) - 1;
            Collections.sort(list);
            if (newArraySize % 1 == 0) {
                quartile = list.get((int) newArraySize);
            } else {
                int newArraySize1 = (int) newArraySize;
                quartile = (list.get((int) Math.floor(newArraySize1)) + list.get((int) Math.ceil(newArraySize1))) / 2;
            }
            quartiles[i - 1] = quartile;
        }
        return quartiles;
    }

    public static double[] doubleCalculateQuartiles(ArrayList<Double> list){
        // Sort the list in ascending order
        Collections.sort(list);
        float length = list.size();

        double quartiles[] = new double[3];

        for (int i = 1; i < 4; i++) {
            double quartile;

            // Get the position of the ith quartile
            float position = (float) (length * ((float) (i)) * 0.25);

            if (position % 1 == 0 || Math.ceil(position) >= length) {
                quartile = list.get((int) position);
            } else {
                quartile =
                        ((double) list.get((int) Math.floor(position)) + (double) list.get((int) Math.ceil(position))) / 2;
            }
            quartiles[i - 1] = quartile;
        }

        return quartiles;
    }
}
