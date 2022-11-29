package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class ModMain implements ModInitializer {
    private static final String MOD_ID = "fullbright+crit";
    public static final Logger log = LogManager.getLogger(MOD_ID);

    /**
     * 
     * To log to console
     * 
     * @param message
     */
    private static void log(String message) {
        log.info("[{}] {}", log.getName(), message);
    }

    @Override
    public void onInitialize() {
        log("Initialization");
        ExampleMod.LOGGER.info("calling from modmain!"); // this calls

        AttackEntityCallback.EVENT.register(new EntityClickManager());
    }

}
