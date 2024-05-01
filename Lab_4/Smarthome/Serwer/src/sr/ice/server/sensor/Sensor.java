package sr.ice.server.sensor;

import Smarthome.ISensor;
import com.zeroc.Ice.Current;

import java.util.Random;

public class Sensor implements ISensor {

    protected final String name;

    public Sensor(String name) {
        this.name = name;
    }


    @Override
    public double getMeasurement(Current current) {
        Random random = new Random();
        double measurement = random.nextDouble();
        System.out.println("[Sensor] " + name + ", getMeasurement: " + measurement + ", id: " + current.id.name + ", category: " + current.id.category);
        return measurement;
    }
}
