package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModMain implements ModInitializer {
    private static final String MOD_ID = "fullbright";
    private static final Logger log = LogManager.getLogger(MOD_ID);
    private static ModMain instance;

    // private final SimpleOption<Double> gammaBypass = new
    // SimpleOption<>("options.gamma", SimpleOption.emptyTooltip(),
    // (optionText, value) -> Text.empty(),
    // SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(
    // d -> (double) getInternalFullbrightState(), d -> 1),
    // 0.5, value -> {
    // });

    // float internalFullbrightState;
    // float maxFullbrightStates;
    // private boolean fullBrightEnable = true;

    public ModMain() {
        instance = this;
    }

    public static ModMain getMod() {
        // returns an instance of this class
        return instance;
    }

    // private float getInternalFullbrightState() {
    // return 20f * internalFullbrightState / maxFullbrightStates;
    // }

    // public SimpleOption<Double> getGammaBypass() {
    // force value
    // gammaBypass.setValue(15.0);
    // return gammaBypass;
    // }

    // public boolean isInternalFullbrightEnable() {
    // return getInternalFullbrightState() != 0;
    // }

    private static void log(String message) {
        log.info("[{}] {}", log.getName(), message);
    }

    // public ModMain internalFullbright() {
    // if (fullBrightEnable) {
    // if (internalFullbrightState == 0)
    // internalFullbrightState = 1;
    // return this;
    // }
    // boolean modeEnabled = true;

    // if (modeEnabled) {
    // internalFullbrightState = maxFullbrightStates;
    // } else {
    // internalFullbrightState = 0;
    // }
    // return this;
    // }

    // @Override
    // public void onEndTick(MinecraftClient client) { // every end tick event, this
    // adds 1 to the internal fullbright
    // // state
    // if (internalFullbrightState != 0 && internalFullbrightState <
    // maxFullbrightStates) {
    // internalFullbrightState++;
    // }
    // }

    @Override
    public void onInitialize() {
        log("Initialization");
        ExampleMod.LOGGER.info("calling from modmain!"); // this calls
        ExampleMod.LOGGER.info("this got updated!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); // this calls
    }

}
