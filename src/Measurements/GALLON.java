package Measurements;

public class GALLON implements MeasurementUnit{
    private static final String name = "GALLON";

    @Override
    public double unitToOunces(double unit) {   //expecting Gallon as Unit
        return unit*128;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces as Unit
        return unit/128;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
