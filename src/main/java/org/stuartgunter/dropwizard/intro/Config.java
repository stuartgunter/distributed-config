package org.stuartgunter.dropwizard.intro;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

public class Config {

    private DynamicPropertyFactory dynamicPropertyFactory = DynamicPropertyFactory.getInstance();
    private DynamicBooleanProperty killSwitch = dynamicPropertyFactory.getBooleanProperty("kill-switch", false);
    private DynamicStringProperty message = dynamicPropertyFactory.getStringProperty("message", "some default");

    public boolean getKillSwitch() {
        return killSwitch.get();
    }

    public String getMessage() {
        return message.get();
    }
}
