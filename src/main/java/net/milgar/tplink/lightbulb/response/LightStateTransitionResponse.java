package net.milgar.tplink.lightbulb.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOff;
import net.milgar.tplink.lightbulb.dto.LightStateTurnedOn;

@Getter
@Setter
@Builder
public class LightStateTransitionResponse implements ResponsePayload {

    private Integer on_off;
    private String mode;
    private Integer hue;
    private Integer saturation;
    private Integer color_temp;
    private Integer brightness;
    private Integer err_code;

    public static LightStateTransitionResponse from(LightStateTurnedOff lightStateTurnedOff) {
        return LightStateTransitionResponse.builder()
                .on_off(lightStateTurnedOff.getOn_off())
                .err_code(lightStateTurnedOff.getErr_code())
                .brightness(lightStateTurnedOff.getDefaultOnState().getBrightness())
                .color_temp(lightStateTurnedOff.getDefaultOnState().getColor_temp())
                .hue(lightStateTurnedOff.getDefaultOnState().getHue())
                .mode(lightStateTurnedOff.getDefaultOnState().getMode())
                .saturation(lightStateTurnedOff.getDefaultOnState().getSaturation())
                .build();
    }

    public static LightStateTransitionResponse from(LightStateTurnedOn lightStateTurnedOn) {
        return LightStateTransitionResponse.builder()
                .on_off(lightStateTurnedOn.getOn_off())
                .err_code(lightStateTurnedOn.getErr_code())
                .brightness(lightStateTurnedOn.getBrightness())
                .color_temp(lightStateTurnedOn.getColor_temp())
                .hue(lightStateTurnedOn.getHue())
                .mode(lightStateTurnedOn.getMode())
                .saturation(lightStateTurnedOn.getSaturation())
                .build();
    }
}
