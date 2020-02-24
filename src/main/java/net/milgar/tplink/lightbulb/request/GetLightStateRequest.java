package net.milgar.tplink.lightbulb.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOff;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOn;
import net.milgar.tplink.lightbulb.response.LightStateTransitionResponse;

import java.net.InetAddress;

public class GetLightStateRequest extends AbstractRequest<net.milgar.tplink.lightbulb.response.LightStateTransitionResponse> {

    public GetLightStateRequest(InetAddress address) {
        super(address);
    }

    @Override
    protected String createRequestPayload(RequestPayload payload) {

        // {"smartlife.iot.smartbulb.lightingservice":{"get_light_state":""}}
        ObjectNode rootNode = objectMapper.createObjectNode();
        ObjectNode childNode = objectMapper.createObjectNode();
        childNode.put("get_light_state", "");
        rootNode.set("smartlife.iot.smartbulb.lightingservice", childNode);
        return rootNode.toString();
    }

    @Override
    protected LightStateTransitionResponse createResponse(JsonNode jsonNode) {
        // {"smartlife.iot.smartbulb.lightingservice":{"get_light_state":{"on_off":1,"mode":"normal","hue":30,"saturation":100,"color_temp":0,"brightness":7,"err_code":0}}}
        JsonNode transitionNode = jsonNode.get("smartlife.iot.smartbulb.lightingservice").get("get_light_state");
        try {
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
