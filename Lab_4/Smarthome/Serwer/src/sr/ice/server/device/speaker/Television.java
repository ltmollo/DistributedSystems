package sr.ice.server.device.speaker;

import Smarthome.Channel;
import Smarthome.ITelevision;
import Smarthome.InvalidChannelException;
import com.zeroc.Ice.Current;

import java.util.Arrays;
import java.util.List;

public class Television extends Speaker implements ITelevision {

    private Channel channel;

    public Television(String name) {
        super(name);
        channel = Channel.TVP1;
    }

    @Override
    public Channel getChannel(Current current) {
        automaticallyTurnOnDevice(current);
        System.out.println("[Television] " + name + ", getChannel: " + channel + ",id: " + current.id.name + ", category: " + current.id.category);
        return channel;
    }

    @Override
    public List<Channel> getChannels(Current current) {
        automaticallyTurnOnDevice(current);
        List<Channel> result = Arrays.stream(Channel.values()).toList();
        System.out.println("[Television] " + name + ", getChannels: " + result + ",id: " + current.id.name + ", category: " + current.id.category);
        return result;
    }

    @Override
    public boolean setChannel(Channel channel, Current current) throws InvalidChannelException {
        automaticallyTurnOnDevice(current);
        System.out.println("[Television] " + name + ", setChannel;: " + channel + ",id: " + current.id.name + ", category: " + current.id.category);
        if (channel == null) {
            throw new InvalidChannelException("Channel cannot be null!");
        }
        this.channel = channel;
        return true;
    }
}
