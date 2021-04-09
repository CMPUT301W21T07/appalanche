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
                quartile = (list.get(newArraySize1) + list.get(newArraySize1 + 1)) / 2;
            }
            quartiles[i - 1] = quartile;
        }
        return quartiles;
    }

    public static double[] doubleCalculateQuartiles(ArrayList<Double> list){
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
                quartile = (list.get(newArraySize1) + list.get(newArraySize1 + 1)) / 2;
            }
            quartiles[i - 1] = quartile;
        }
        return quartiles;
    }
}
