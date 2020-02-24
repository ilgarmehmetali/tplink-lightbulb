package net.milgar.tplink.lightbulb;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.milgar.tplink.lightbulb.dto.LightStateTransition;
import net.milgar.tplink.lightbulb.request.GetLightDetailsRequest;
import net.milgar.tplink.lightbulb.request.GetLightStateRequest;
import net.milgar.tplink.lightbulb.request.SetLightStateRequest;
import net.milgar.tplink.lightbulb.response.LightDetailsResponse;
import net.milgar.tplink.lightbulb.response.LightStateTransitionResponse;

import java.awt.*;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

@Getter
@Builder
@AllArgsConstructor
public class Device implements ResponseIdentifier {

    private JsonNode raw;
    private InetAddress host;
    private int port;
    private String name;
    private String deviceId;
    private boolean isPowered;


    @Override
    public String getResponseType() {
        return "Device";
    }

    /**
     * @param state Power state
     *              true - indicates the power on state
     *              false - indicates the power off state
     */
    public CompletableFuture<LightStateTransitionResponse> setPowered(boolean state) {
        LightStateTransition lightStateTransition = LightStateTransition.builder()
                .ignore_default(1)
                .on_off(state ? 1 : 0)
                .transition_period(0)
                .build();

        SetLightStateRequest setLightStateRequest = new SetLightStateRequest(this.host);
        return setLightStateRequest.make(lightStateTransition)
                .thenApply(response -> {
                    updateSelfWithLightStateTransitionResponse(response);
                    return response;
                });
    }

    public CompletableFuture<LightStateTransitionResponse> setColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        LightStateTransition lightStateTransition = LightStateTransition.builder()
                .ignore_default(1)
                .hue((int) (hsb[0] * 360))
                .saturation((int) (hsb[1] * 100))
                .color_temp(0)
                .transition_period(0)
                .build();

        SetLightStateRequest setLightStateRequest = new SetLightStateRequest(this.host);
        return setLightStateRequest.make(lightStateTransition)
                .thenApply(response -> {
                    updateSelfWithLightStateTransitionResponse(response);
                    return response;
                });
    }

    public CompletableFuture<LightStateTransitionResponse> setTemperature(int temperature) {
        LightStateTransition lightStateTransition = LightStateTransition.builder()
                .ignore_default(1)
                .hue(0)
                .saturation(0)
                .color_temp(temperature)
                .transition_period(0)
                .build();

        SetLightStateRequest setLightStateRequest = new SetLightStateRequest(this.host);
        return setLightStateRequest.make(lightStateTransition)
                .thenApply(response -> {
                    updateSelfWithLightStateTransitionResponse(response);
                    return response;
                });
    }

    /**
     * @param brightness range of 1 to 100
     * @return CompletableFuture with LightStateTransitionResponse
     */
    public CompletableFuture<LightStateTransitionResponse> setBrightness(int brightness) {
        if (brightness < 1 || brightness > 100) {

            CompletableFuture<LightStateTransitionResponse> completableFuture = new CompletableFuture<>();
            completableFuture.completeExceptionally(new IllegalArgumentException("Brightness has to be between 1 and 100"));
            return completableFuture;
        } else {

            LightStateTransition lightStateTransition = LightStateTransition.builder()
                    .ignore_default(1)
                    .brightness(brightness)
                    .transition_period(0)
                    .build();

            SetLightStateRequest setLightStateRequest = new SetLightStateRequest(this.host);
            return setLightStateRequest.make(lightStateTransition)
                    .thenApply(response -> {
                        updateSelfWithLightStateTransitionResponse(response);
                        return response;
                    });
        }
    }

    public CompletableFuture<LightDetailsResponse> getDetails() {
        GetLightDetailsRequest getLightDetailsRequest = new GetLightDetailsRequest(this.host);
        return getLightDetailsRequest.make(null);
    }

    public CompletableFuture<LightStateTransitionResponse> getLightState() {
        GetLightStateRequest getLightStateRequest = new GetLightStateRequest(this.host);
        return getLightStateRequest.make(null)
                .thenApply(response -> {
                    updateSelfWithLightStateTransitionResponse(response);
                    return response;
                });
    }

    private void updateSelfWithLightStateTransitionResponse(LightStateTransitionResponse response) {
        this.isPowered = response.getOn_off() == 1;
    }
}
