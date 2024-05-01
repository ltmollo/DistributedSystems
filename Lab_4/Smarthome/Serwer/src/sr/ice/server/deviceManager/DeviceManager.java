package sr.ice.server.deviceManager;

import Smarthome.DeviceInfo;
import Smarthome.DeviceType;
import Smarthome.IDeviceManager;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;

public class DeviceManager implements IDeviceManager {

    private final List<DeviceInfo> devices;

    public DeviceManager() {
        devices = new ArrayList<>();
        devices.add(new DeviceInfo("Telewizja1", DeviceType.TELEVISION, 1));
        devices.add(new DeviceInfo("Telewizja2", DeviceType.TELEVISION, 1));
        devices.add(new DeviceInfo("Mp3", DeviceType.MP3PLAYER, 1));
        devices.add(new DeviceInfo("Ekspres", DeviceType.COFFEEMAKER, 1));
        devices.add(new DeviceInfo("Termometr", DeviceType.THERMOMETER, 2));
        devices.add(new DeviceInfo("Czujnik", DeviceType.CO, 2));
    }

    @Override
    public List<DeviceInfo> getDeviceList(Current current) {
        System.out.println("[DeviceManager] " + ", getDeviceList" + ", id: " + current.id.name + ", category: " + current.id.category);
        return devices;
    }
}
