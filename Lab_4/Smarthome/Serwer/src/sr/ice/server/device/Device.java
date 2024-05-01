package sr.ice.server.device;
import Smarthome.IDevice;
import Smarthome.SmarthomeException;
import Smarthome.TurnDeviceException;
import com.zeroc.Ice.Current;

public class Device implements IDevice{

    protected final String name;
    protected boolean state;

    public Device(String name) {
        this.name = name;
        this.state = false;
    }

    @Override
    public boolean getState(Current current) {
            System.out.println("[Device] " + name + ", getState: " + state + ",id: " + current.id.name + ", category: " + current.id.category);
        return state;
    }

    @Override
    public boolean turnOn(Current current) throws TurnDeviceException {
        System.out.println("[Device] " + name + ", turnOn" + ",id: " + current.id.name + ", category: " + current.id.category);
        if (state) {
            throw new TurnDeviceException("Device is already on!");
        }
        state = true;
        return true;
    }

    @Override
    public boolean turnOff(Current current) throws TurnDeviceException {
        System.out.println("[Device] " + name + ", turnOff" + ",id: " + current.id.name + ", category: " + current.id.category);
        if (!state) {
            throw new TurnDeviceException("Device is already off!");
        }
        state = false;
        return true;
    }
}
