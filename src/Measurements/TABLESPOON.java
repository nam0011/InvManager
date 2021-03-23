package Measurements;

public class TABLESPOON implements MeasurementUnit{
    private static final String name = "TABLESPOON";
    @Override
    public double unitToOunces(double unit) {   //expecting Tablespoon for unit
        return unit/2;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces for Unit
        return 2*unit;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
