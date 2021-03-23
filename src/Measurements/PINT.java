package Measurements;

public class PINT implements MeasurementUnit{
    private static final String name = "PINT";

    @Override
    public double unitToOunces(double unit) {   //expecting Pint as Unit
        return unit*16;
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
