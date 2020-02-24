package net.milgar.tplink.lightbulb;

import net.milgar.tplink.lightbulb.factory.AbstractResponseFactory;
import net.milgar.tplink.lightbulb.factory.DeviceFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class DeviceScanner {

    private static final int BROADCAST_DELAY = 5000;
    private static final int TP_LINK_LIGHTBULB_PORT = 9999;
    private static final int BUFFER_SIZE = 10000;
    private static DatagramSocket socket;
    private List<OnDeviceFoundListener> listeners;
    private AbstractResponseFactory deviceFactory;
    private CompletableFuture<Void> broadcastRequestCompletableFuture;
    private CompletableFuture<Void> waitForResponseCompletableFuture;

    public DeviceScanner() {
        listeners = new ArrayList<>();
        deviceFactory = new DeviceFactory();
    }

    private CompletableFuture<Void> waitForResponse() {
        return CompletableFuture
                .supplyAsync(this::getDatagramAsync)
                .thenAccept(this::onDatagramReceived)
                .thenCompose(aVoid -> waitForResponseCompletableFuture = waitForResponse());
    }

    public void addOnDeviceFoundListener(OnDeviceFoundListener onDeviceFoundListener) {
        if (null != onDeviceFoundListener) {
            this.listeners.add(onDeviceFoundListener);
        }
    }

    public void start() {

        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            waitForResponseCompletableFuture = waitForResponse();
            broadcastRequestCompletableFuture = broadcastRequest();
        } catch (SocketException e) {
            // Todo: log this and throw different exception
        }
    }

    public void stop() {

        if (null != broadcastRequestCompletableFuture) {
            broadcastRequestCompletableFuture.cancel(false);
            waitForResponseCompletableFuture.cancel(false);
            socket.close();
        }
    }

    public CompletableFuture<Void> broadcastRequest() {
        return CompletableFuture.runAsync(() -> {
            try {
                byte[] msgBuf = Utils.encrypt("{\"system\":{\"get_sysinfo\":{}}}".getBytes());
                DatagramPacket packet = new DatagramPacket(msgBuf, msgBuf.length, InetAddress.getByName("255.255.255.255"), TP_LINK_LIGHTBULB_PORT);
                System.out.println("Broadcasting tp-link device search");
                socket.send(packet);
                Thread.sleep(BROADCAST_DELAY);
            } catch (IOException | InterruptedException e) {
                //todo: handle
            }
        }).thenCompose(aVoid -> broadcastRequestCompletableFuture = broadcastRequest());
        // todo: handle exception in getDatagramAsync and then clause
    }

    private DatagramPacket getDatagramAsync() {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        try {
            socket.receive(datagramPacket);
            return datagramPacket;
        } catch (IOException e) {
            // TODO handle exception
            return null;
        }
    }

    private void onDatagramReceived(DatagramPacket datagramPacket) {
        if (null != datagramPacket) {
            Device device = (Device) deviceFactory.createFrom(datagramPacket);
            listeners.forEach(onDeviceFoundListener -> onDeviceFoundListener.onFound(device));
        }
    }

}
