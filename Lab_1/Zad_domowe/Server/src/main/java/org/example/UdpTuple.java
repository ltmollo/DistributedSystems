package org.example;

import java.net.InetAddress;

public record UdpTuple(InetAddress address, int port) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UdpTuple udpTuple = (UdpTuple) obj;
        return (port == udpTuple.port && udpTuple.address.equals(address));
    }

    public String toString() {
        return address + " - " + port;
    }

}
