package net.milgar.tplink.lightbulb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * turnedOffResponse = "{\"smartlife.iot.smartbulb.lightingservice\":{\"transition_light_state\":{\"on_off\":0,\"dft_on_state\":{\"mode\":\"normal\",\"hue\":0,\"saturation\":0,\"color_temp\":2500,\"brightness\":100},\"err_code\":0}}}";
 */
@Getter
@Setter
public class LightStateTurnedOff {

    private Integer on_off;
    @JsonProperty("dft_on_state")
    private DefaultOnState defaultOnState;
    private Integer err_code;
}
