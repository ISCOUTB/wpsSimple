package org.wpsim.AgroEcosystem.layer.data;

/**
 *
 * @author jairo
 */
public class MonthData {
    private double average;
    private double standardDeviation;
    private double maxValue;
    private double minValue;

    /**
     * Default constructor for MonthData.
     */
    public MonthData() {
    }

    /**
     * Gets the average value.
     *
     * @return The average value
     */
    public double getAverage() {
        return average;
    }

    /**
     * Sets the average value.
     *
     * @param average The average value to set
     */
    public void setAverage(double average) {
        this.average = average;
    }

    /**
     * Gets the standard deviation value.
     *
     * @return The standard deviation value
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }

    /**
     * Sets the standard deviation value.
     *
     * @param standardDeviation The standard deviation value to set
     */
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    /**
     * Gets the maximum value.
     *
     * @return The maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximum value.
     *
     * @param maxValue The maximum value to set
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Gets the minimum value.
     *
     * @return The minimum value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum value.
     *
     * @param minValue The minimum value to set
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }
}
