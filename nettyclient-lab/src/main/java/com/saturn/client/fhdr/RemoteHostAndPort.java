package com.saturn.client.fhdr;

import java.net.InetSocketAddress;

/**
 * Created by john.y on 2017-6-29.
 */
public class RemoteHostAndPort {

    private String host;
    private int port;

    /**
     * remote
     */
    private InetSocketAddress address;

    public RemoteHostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
        this.address = new InetSocketAddress(host, port);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RemoteHostAndPort other = (RemoteHostAndPort) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public int hashCode() {

        int result = address.hashCode();
        return result;
    }
}
