package Measurements;

public class QUART implements MeasurementUnit{
    private static final String name = "QUART";

    @Override
    public double unitToOunces(double unit) {   //expecting Quarts as Unit
        return unit*32;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces as Unit
        return unit/32;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
