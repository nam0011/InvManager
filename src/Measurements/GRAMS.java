package Measurements;

public class GRAMS implements MeasurementUnit{
    private static final String name = "GRAMS";

    @Override
    public double unitToOunces(double unit) {   //expecting Grams as Unit
        return unit/28.349523125;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces as Unit
        return unit*28.349523125;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
