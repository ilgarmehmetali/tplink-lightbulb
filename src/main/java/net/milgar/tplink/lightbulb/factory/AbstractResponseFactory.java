package net.milgar.tplink.lightbulb.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.milgar.tplink.lightbulb.ResponseIdentifier;
import net.milgar.tplink.lightbulb.Utils;

import java.net.DatagramPacket;

public abstract class AbstractResponseFactory {


    private DatagramPacket datagramPacket;
    private ObjectMapper objectMapper;
    private JsonNode parsedBuffer;

    public AbstractResponseFactory() {
        this.objectMapper = new ObjectMapper();
    }

    protected void init(DatagramPacket datagramPacket){
        this.datagramPacket = datagramPacket;
        this.parseBuffer(datagramPacket.getData());
    }

    private void parseBuffer(byte[] buffer) {
        char[] decrypted = Utils.decrypt(buffer);
        try {
            this.parsedBuffer = objectMapper.readTree(String.valueOf(decrypted));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected DatagramPacket getBaseDatagramPacket(){
        return this.datagramPacket;
    }

    protected JsonNode getBufferJSONContent(){
        return this.parsedBuffer;
    }

    public abstract ResponseIdentifier createFrom(DatagramPacket datagramPacket);


}
