package sr.ice.server.sensor;

import Smarthome.IThermometer;
import com.zeroc.Ice.Current;

public class Thermometer extends Sensor implements IThermometer {

    public Thermometer(String name) {
        super(name);
    }

    @Override
    public double countInFahrenheit(Current current) {
        double measurement = super.getMeasurement(current);
        double fahrenheit = measurement * 9 / 5 + 32;
        System.out.println("[Thermometer] " + name + ", countInFahrenheit: " + fahrenheit + ",id: " + current.id.name + ", category: " + current.id.category);
        return fahrenheit;
    }
}
