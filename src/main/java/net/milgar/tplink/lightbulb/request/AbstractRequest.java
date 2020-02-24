package net.milgar.tplink.lightbulb.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.milgar.tplink.lightbulb.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public abstract class AbstractRequest<T> {

    private static int BUFFER_SIZE = 10000;
    private static int TARGET_PORT = 9999;
    protected ObjectMapper objectMapper;
    protected DatagramPacket datagramPacket;
    protected InetAddress address;
    private DatagramSocket socket;

    public AbstractRequest(InetAddress address) {
        this.objectMapper = new ObjectMapper();
        try {
            this.socket = new DatagramSocket(0);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.address = address;
    }

    public CompletableFuture<T> make(RequestPayload payload) {
        try {
            if (null == this.socket) {
                throw new NullPointerException();
            }
            CompletableFuture<T> completableFuture = CompletableFuture
                    .supplyAsync(this::getJsonNodeFromSocketAsync)
                    .thenApply(jsonNode -> null != jsonNode ? createResponse(jsonNode) : null);

            byte[] buffer = Utils.encrypt(createRequestPayload(payload).getBytes());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, TARGET_PORT);
            socket.send(packet);
            return completableFuture;
        } catch (Exception ex) {
            CompletableFuture<T> completedFuture = CompletableFuture.completedFuture(null);
            completedFuture.completeExceptionally(ex);
            return completedFuture;
        }
    }

    private JsonNode getJsonNodeFromSocketAsync() {
        this.datagramPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        try {
            socket.receive(this.datagramPacket);
            socket.close();
            return objectMapper.readTree(String.valueOf(Utils.decrypt(datagramPacket.getData())));
        } catch (IOException e) {
            // TODO handle exception
            throw new CompletionException(e);
        }
    }

    protected abstract String createRequestPayload(RequestPayload payload);

    protected abstract T createResponse(JsonNode jsonNode);
}
