/******************************************
 * This is a data structure that gets     *
 * a sorted integer array, and can        *
 * then be used to return values such as  *
 * the minimum of the set, maximum of the *
 * set, the average value, number of      *
 * values, etc.
 *****************************************/

package datastructs;

public class Statistics {
    final private int[] dataset;
    final private String name;

    public Statistics(int[] dataset, String name) {
        this.dataset = dataset;
        this.name = name;
    }

    public int Minimum() {
        return dataset[0];
    }

    public int Maximum() {
        return dataset[dataset.length - 1];
    }

    public double Average() {
        int avg = 0, ret = 0;
        for (int i = 0; i < dataset.length; i++) avg += dataset[i];
        return avg / dataset.length;
    }

    public int NumberOfValues() {
        return dataset.length;
    }
}
