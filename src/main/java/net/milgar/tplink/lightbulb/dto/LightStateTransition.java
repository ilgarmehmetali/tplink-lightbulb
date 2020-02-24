package net.milgar.tplink.lightbulb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.milgar.tplink.lightbulb.request.RequestPayload;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightStateTransition implements RequestPayload {

    private String mode;

    /**
     * range from 1 to 360
     */
    private Integer hue;

    /**
     * range from 1 to 100
     */
    private Integer saturation;

    /**
     * range from 2500 to 9000
     */
    private Integer color_temp;

    /**
     * range from 1 to 100
     */
    private Integer brightness;
    private Integer ignore_default;
    private Integer transition_period;
    private Integer on_off;
}
