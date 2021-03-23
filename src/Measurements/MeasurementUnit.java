package Measurements;

public interface MeasurementUnit {
    public double unitToOunces(double unit);
    public double ouncesToUnit(double unit);
    public default String nameOfUnit() {
        return "Name Missing";
    }
}
