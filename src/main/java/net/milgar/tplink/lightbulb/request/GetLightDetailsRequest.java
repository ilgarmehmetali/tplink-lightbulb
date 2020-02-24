package net.milgar.tplink.lightbulb.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.milgar.tplink.lightbulb.response.LightDetailsResponse;

import java.net.InetAddress;

public class GetLightDetailsRequest extends AbstractRequest<LightDetailsResponse> {

    public GetLightDetailsRequest(InetAddress address) {
        super(address);
    }

    @Override
    protected String createRequestPayload(RequestPayload payload) {

        // {"smartlife.iot.smartbulb.lightingservice":{"get_light_details":""}}
        ObjectNode rootNode = objectMapper.createObjectNode();
        ObjectNode childNode = objectMapper.createObjectNode();
        childNode.put("get_light_details", "");
        rootNode.set("smartlife.iot.smartbulb.lightingservice", childNode);
        return rootNode.toString();
    }

    @Override
    protected LightDetailsResponse createResponse(JsonNode jsonNode) {
        JsonNode transitionNode = jsonNode.get("smartlife.iot.smartbulb.lightingservice").get("get_light_details");
        try {
            return this.objectMapper.treeToValue(transitionNode, LightDetailsResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
