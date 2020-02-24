package net.milgar.tplink.lightbulb.factory;

import net.milgar.tplink.lightbulb.Device;
import net.milgar.tplink.lightbulb.ResponseIdentifier;

import java.net.DatagramPacket;

public class DeviceFactory extends AbstractResponseFactory{

    @Override
    public ResponseIdentifier createFrom(DatagramPacket datagramPacket) {
        this.init(datagramPacket);
        Device device = Device.builder()
                .raw(this.getBufferJSONContent().get("system").get("get_sysinfo"))
                .host(datagramPacket.getAddress())
                .port(datagramPacket.getPort())
                .name(this.getBufferJSONContent().get("system").get("get_sysinfo").get("alias").asText())
                .deviceId(this.getBufferJSONContent().get("system").get("get_sysinfo").get("deviceId").asText())
                .build();
        return device;
    }
}
