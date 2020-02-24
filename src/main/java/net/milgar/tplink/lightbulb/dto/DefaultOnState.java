package net.milgar.tplink.lightbulb.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefaultOnState {

    private String mode;
    private Integer hue;
    private Integer saturation;
    private Integer color_temp;
    private Integer brightness;
}
