package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.deprecated.ExampleMod;
import net.fabricmc.example.ui.ModMenu;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.client.world.ClientWorld;

public class ModMain implements ModInitializer {
    private static final String MOD_ID = "fullbright+crit";
    public static final Logger log = LogManager.getLogger(MOD_ID);

    // class variables
    private static boolean isCriticalsEnabled = true; // on init its true
    private static double brightnessValue = 5; // on init its 10
    private static float internalFullbrightState = 1.0f; // on init its 1.0f
    private static int maxFullbrightStates = 20;
    private static boolean ANTIFALL = true; // true by default //TODO change this to false later
    public static boolean SCAFFOLD = true; // true by default //TODO change this to false later

    // client init
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static ClientPlayerEntity player = MinecraftClient.getInstance().player;

    public static ClientWorld clientWorld = MinecraftClient.getInstance().world;

    public static void setPlayer(ClientPlayerEntity clientPlayerEntity) {
        player = clientPlayerEntity;
    }

    // brightness gamma value bypass by using a new simpleoption
    private static final SimpleOption<Double> gammaBypass = new SimpleOption<>("options.gamma",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.empty(), SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(
                    d -> (double) getInternalState(), d -> 1),
            0.5, value -> {
            });

    // class methods
    // * criticals functionality
    public static boolean isCriticalsEnabled() {
        return isCriticalsEnabled;
    }

    public static void setCriticalsEnabled(boolean bool) {
        isCriticalsEnabled = bool;
    }

    // * brightness functionality
    // this is called from the mixin, returns the brightness value as a simple
    // option
    public static SimpleOption<Double> getBrightnessOption() {
        return gammaBypass;
    }

    public static void setBrightnessOption(double value) {
        brightnessValue = value;
    }

    // private static void setInternalState() {
    // internalFullbrightState = (float) (brightnessValue / maxFullbrightStates);
    // }

    private static float getInternalState() {
        return 20f * internalFullbrightState / maxFullbrightStates;
    }

    public static double getBrightnessValue() {
        return brightnessValue;
    }

    public static void setBrightnessValue(double value) {
        brightnessValue = value;
    }

    public static double getInternalFullbrightState() {
        return internalFullbrightState;
    }

    public static void setInternalFullbrightState(double brightnessValue) {
        internalFullbrightState = (float) brightnessValue;
    }

    // * ANTIFALL functionality getters and setters */
    public static boolean getANTIFALL() {
        return ANTIFALL;
    }

    public static void setANTIFALL(boolean bool) {
        ANTIFALL = bool;
    }

    // class methods
    public static void setBrightness(double value) {
        setBrightnessValue(value * 20);
        // brightnessValue = value * 20;

        // update the internal state
        setInternalFullbrightState(brightnessValue);
        // internalFullbrightState = (float) brightnessValue;

        // update the gammabypass value as well
        gammaBypass.setValue(brightnessValue);

        // changes the value in the game options
        client.options.getGamma().setValue(brightnessValue);

        log.info("Brightness value set to " + brightnessValue);
        log.info("Current brightness value get from options file is: " + client.options.getGamma().getValue());
    }

    public SimpleOption<Double> getGammaBypass() {
        // force... the value?
        gammaBypass.setValue(1.0);
        return gammaBypass;
    }

    // basic console log
    private static void log(String message) {
        log.info("[{}] {}", log.getName(), message);
    }

    @Override
    public void onInitialize() {
        log("Initialization");
        ExampleMod.LOGGER.info("calling from modmain!"); // this calls

        AttackEntityCallback.EVENT.register(new EntityClickManager());
        ScreenEvents.AFTER_INIT.register(this::afterInitScreen);
    }

    private void afterInitScreen(MinecraftClient client, Screen screen, int windowWidth, int windowHeight) {
        log.info("Initializing {}", screen.getClass().getName());

        if (screen instanceof OptionsScreen) {
            // put the existing buttons in this list
            final List<ClickableWidget> buttons = Screens.getButtons(screen);
            // Add a new button
            // buttons.add(new CriticalsButton(screen.width / 2 + 5, screen.height / 6 + 144
            // - 6, 75, 20));
            buttons.add(new ButtonWidget(screen.width / 2 + 5, screen.height / 6 + 144 - 6, 150, 20,
                    Text.translatable("Mylo's Mod Menu"),
                    button -> client.setScreen(new ModMenu(screen, client.options))));
            // add the new slider
            // buttons.add(
            // new BrightnessSlider(screen.width / 2 + 80, screen.height / 6 + 144 - 6, 75,
            // 20, "Brightness",
            // windowHeight));

            // Testing:
            // Some automatic validation that the screen list works, make sure the buttons
            // we added are on the list of child elements
            screen.children().stream()
                    .filter(element -> element instanceof ButtonWidget)
                    .findAny()
                    .orElseThrow(
                            () -> new AssertionError("Failed to find the button in the screen's elements"));

            // // Register render event to draw an icon on the screen
            // ScreenEvents.afterRender(screen).register((_screen, matrices, mouseX, mouseY,
            // tickDelta) -> {
            // // // Render an armor icon to test
            // // RenderSystem.setShaderTexture(0, InGameHud.GUI_ICONS_TEXTURE);
            // DrawableHelper.drawTexture(matrices, (screen.width / 2) - 124, (screen.height
            // / 4) + 96, 20, 20, 34, 9,
            // 9, 9, 256, 256);
            // });

            ScreenKeyboardEvents.allowKeyPress(screen).register((_screen, key, scancode, modifiers) -> {
                log.info("After Pressed, Code: {}, Scancode: {}, Modifiers: {}", key, scancode, modifiers);
                return true; // Let actions continue
            });

            ScreenKeyboardEvents.afterKeyPress(screen).register((_screen, key, scancode, modifiers) -> {
                log.warn("Pressed, Code: {}, Scancode: {}, Modifiers: {}", key, scancode, modifiers);
            });
        }
    }

}
