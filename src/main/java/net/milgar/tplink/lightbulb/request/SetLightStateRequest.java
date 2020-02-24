package net.milgar.tplink.lightbulb.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOff;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOn;
import net.milgar.tplink.lightbulb.response.LightStateTransitionResponse;

import java.net.InetAddress;

public class SetLightStateRequest extends AbstractRequest<net.milgar.tplink.lightbulb.response.LightStateTransitionResponse> {

    public SetLightStateRequest(InetAddress address) {
        super(address);
    }

    @Override
    protected String createRequestPayload(RequestPayload payload) {

        ObjectNode rootNode = objectMapper.createObjectNode();
        ObjectNode childNode = objectMapper.createObjectNode();
        childNode.putPOJO("transition_light_state", payload);
        rootNode.set("smartlife.iot.smartbulb.lightingservice", childNode);
        return rootNode.toString();
    }

    @Override
    protected LightStateTransitionResponse createResponse(JsonNode jsonNode) {

        try {
            JsonNode transitionNode = jsonNode.get("smartlife.iot.smartbulb.lightingservice").get("transition_light_state");
            if (transitionNode.get("on_off").asInt() == 0) {
                LightStateTurnedOff lightStateTurnedOff = this.objectMapper.treeToValue(transitionNode, LightStateTurnedOff.class);
                return LightStateTransitionResponse.from(lightStateTurnedOff);
            } else if (transitionNode.get("on_off").asInt() == 1) {
                LightStateTurnedOn lightStateTurnedOn = this.objectMapper.treeToValue(transitionNode, LightStateTurnedOn.class);
                return LightStateTransitionResponse.from(lightStateTurnedOn);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
