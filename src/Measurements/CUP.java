package Measurements;

public class CUP implements MeasurementUnit{
    private static final String name = "CUP";

    @Override
    public double unitToOunces(double unit) {   //expecting Cup as unit
        return unit*8.115;
    }

    @Override
    public double ouncesToUnit(double unit) {   //expecting Ounces as unit
        return unit/8.115;
    }

    @Override
    public String nameOfUnit() {
        return this.name;
    }
}
