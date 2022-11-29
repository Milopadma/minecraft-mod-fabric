package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.ui.BrightnessSlider;
import net.fabricmc.example.ui.CriticalsButton;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;

public class ModMain implements ModInitializer {
    private static final String MOD_ID = "fullbright+crit";
    public static final Logger log = LogManager.getLogger(MOD_ID);

    // class variables
    private static boolean isCriticalsEnabled = true;
    private static double brightnessValue = 10;

    // class methods
    // getters and setters
    public static boolean isCriticalsEnabled() {
        return isCriticalsEnabled;
    }

    public static void setCriticalsEnabled(boolean bool) {
        isCriticalsEnabled = bool;
    }

    public static double getBrightnessValue() {
        return brightnessValue;
    }

    public static void setBrightnessValue(double value) {
        brightnessValue = value;
    }

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
            final List<ClickableWidget> buttons = Screens.getButtons(screen);

            // Shrink the realms button, should be the third button on the list
            final ClickableWidget optionsButton = buttons.get(8);
            // optionsButton.setWidth(75);

            // Add a new button
            buttons.add(new CriticalsButton(screen.width / 2 + 5, screen.height / 6 + 144 - 6, 75, 20));
            // add the new slider
            buttons.add(
                    new BrightnessSlider(screen.width / 2 + 80, screen.height / 6 + 144 - 6, 75, 20, "Brightness",
                            windowHeight));

            // Testing:
            // Some automatic validation that the screen list works, make sure the buttons
            // we added are on the list of child elements
            screen.children().stream()
                    .filter(element -> element instanceof CriticalsButton)
                    .findAny()
                    .orElseThrow(
                            () -> new AssertionError("Failed to find the button in the screen's elements"));

            // Register render event to draw an icon on the screen
            ScreenEvents.afterRender(screen).register((_screen, matrices, mouseX, mouseY,
                    tickDelta) -> {
                // Render an armor icon to test
                RenderSystem.setShaderTexture(0, InGameHud.GUI_ICONS_TEXTURE);
                DrawableHelper.drawTexture(matrices, (screen.width / 2) - 124, (screen.height
                        / 4) + 96, 20, 20, 34, 9,
                        9, 9, 256, 256);
            });

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
