package Measurements;

public class POUND implements MeasurementUnit{
    private static final String name = "POUND";

    @Override
    public double unitToOunces(double unit) {   //expecting Pound as Unit
        return 16*unit;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces as Unit
        return unit/16;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
