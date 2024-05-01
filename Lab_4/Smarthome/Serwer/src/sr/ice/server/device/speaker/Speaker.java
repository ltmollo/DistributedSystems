package sr.ice.server.device.speaker;

import Smarthome.ISpeaker;
import Smarthome.InvalidVolumeException;
import com.zeroc.Ice.Current;
import sr.ice.server.device.Device;

public class Speaker extends Device implements ISpeaker {
    protected int volume;

    public Speaker(String name) {
        super(name);
        volume = 40;
    }


    @Override
    public int getVolume(Current current) {
        automaticallyTurnOnDevice(current);
        System.out.println("[Speaker] " + name + ", getVolume: " + volume + ", id: " + current.id.name + ", category: " + current.id.category);

        return volume;
    }

    @Override
    public boolean setVolume(int volume, Current current) throws InvalidVolumeException {
        automaticallyTurnOnDevice(current);
        System.out.println("[Speaker] " + name + ", setVolume: " + volume + ", id: " + current.id.name + ", category: " + current.id.category);
        if (volume < 0 || volume > 100) {
            throw new InvalidVolumeException("Volume must be between 0 and 100");
        }
        this.volume = volume;
        return true;
    }
}
