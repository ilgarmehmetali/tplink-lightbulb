package net.milgar.tplink.lightbulb;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Application {

    public static void main(String[] args) throws IOException {

        //todo: send: { system: { set_relay_state: { state: 0 }}} got error response: {"system":{"set_relay_state":{"err_code":-2000,"err_msg":"Method not support"}}}
        DeviceScanner deviceScanner = new DeviceScanner();
        deviceScanner.addOnDeviceFoundListener(device -> {
            device.setBrightness(100)
                    .whenComplete((lightStateTransitionResponse, throwable) -> {
                        if (null != throwable) {
                            System.out.println("setBrightness failed: " + throwable.getLocalizedMessage());
                        }
                    });
            device.setColor(Color.white)
                    .thenAccept(lightStateTransitionResponse -> {
                    });
            device.setTemperature(4000)
                    .thenAccept(lightStateTransitionResponse -> {
                    });
            device.getLightState()
                    .thenAccept(lightStateTransitionResponse -> {
                    });
            device.setPowered(false)
                    .thenAccept(lightStateTransitionResponse -> {
                    });
            device.getLightState()
                    .thenAccept(lightStateTransitionResponse -> {
                    });
            device.getDetails()
                    .thenAccept(lightDetailsResponse -> {
                    });
            deviceScanner.stop();
        });
        deviceScanner.start();

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(6000);
                deviceScanner.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.in.read();
    }
}
