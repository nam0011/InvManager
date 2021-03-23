package Measurements;

public class TEASPOON implements MeasurementUnit{
    private static final String name = "TEASPOON";

    @Override
    public double unitToOunces(double unit) {   //expecting the unit in teaspoon amount
        return unit/6;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting the unit in ounces amount
        return 6*unit;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
