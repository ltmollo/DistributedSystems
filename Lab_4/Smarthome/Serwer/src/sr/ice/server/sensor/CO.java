package sr.ice.server.sensor;

import Smarthome.ICO;
import com.zeroc.Ice.Current;

public class CO extends Sensor implements ICO {
    public CO(String name) {
        super(name);
    }

    @Override
    public boolean alarm(Current current) {
        double measurement = super.getMeasurement(current);
        boolean danger = measurement > 0.2;
        System.out.println("[CO] " + name + ", alarm: " + danger + ", id: " + current.id.name + ", category: " + current.id.category);
        return danger;
    }
}
