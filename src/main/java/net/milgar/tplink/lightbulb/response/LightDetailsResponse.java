package net.milgar.tplink.lightbulb.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LightDetailsResponse implements ResponsePayload {

        private Integer lamp_beam_angle;
        private Integer min_voltage;
        private Integer max_voltage;
        private Integer wattage;
        private Integer incandescent_equivalent;
        private Integer max_lumens;
        private Integer color_rendering_index;
        private Integer err_code;
}
