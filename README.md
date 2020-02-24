## TP-Link Lightbulb

A java library to control tp-link lightbulbs in network.  
Tested against TP-Link KL130.
  
DeviceScanner broadcasts the network every 5 seconds.
#### Usage Example
```
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
```

#### Resources
https://www.briandorey.com/post/tp-link-lb130-smart-wi-fi-led-bulb-python-control  
https://github.com/briandorey/tp-link-LB130-Smart-Wi-Fi-Bulb/blob/master/protocols.md
https://github.com/konsumer/tplink-lightbulb/blob/master/src/lib.js
https://github.com/plasticrake/tplink-smarthome-crypto/blob/master/lib/index.js

#### License
This project is licensed under the terms of [MIT license](http://opensource.org/licenses/MIT).