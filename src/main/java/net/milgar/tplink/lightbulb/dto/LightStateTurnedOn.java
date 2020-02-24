package net.milgar.tplink.lightbulb.dto;

import lombok.Getter;
import lombok.Setter;
import net.milgar.tplink.lightbulb.response.ResponsePayload;

/**
 *
 * turnedOnResponse = "{\"smartlife.iot.smartbulb.lightingservice\":{\"transition_light_state\":{\"on_off\":1,\"mode\":\"normal\",\"hue\":0,\"saturation\":0,\"color_temp\":2500,\"brightness\":100,\"err_code\":0}}}";
 */
@Getter
@Setter
public class LightStateTurnedOn {

    private Integer on_off;
    private String mode;
    private Integer hue;
    private Integer saturation;
    private Integer color_temp;
    private Integer brightness;
    private Integer err_code;
}
