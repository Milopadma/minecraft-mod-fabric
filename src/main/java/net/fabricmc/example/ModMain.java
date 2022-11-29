package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;

public class ModMain implements ModInitializer {
    private static final String MOD_ID = "fullbright+crit";
    public static final Logger log = LogManager.getLogger(MOD_ID);

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

        if (screen instanceof TitleScreen) {
            final List<ClickableWidget> buttons = Screens.getButtons(screen);

            // Shrink the realms button, should be the third button on the list
            final ClickableWidget optionsButton = buttons.get(2);
            optionsButton.setWidth(98);

            // Add a new button
            buttons.add(new SoundButton((screen.width / 2) + 2, ((screen.height / 4) + 96), 72, 20));

            // Testing:
            // Some automatic validation that the screen list works, make sure the buttons
            // we added are on the list of child elements
            screen.children().stream()
                    .filter(element -> element instanceof SoundButton)
                    .findAny()
                    .orElseThrow(
                            () -> new AssertionError("Failed to find the \"Sound\" button in the screen's elements"));

            // Register render event to draw an icon on the screen
            ScreenEvents.afterRender(screen).register((_screen, matrices, mouseX, mouseY, tickDelta) -> {
                // Render an armor icon to test
                RenderSystem.setShaderTexture(0, InGameHud.GUI_ICONS_TEXTURE);
                DrawableHelper.drawTexture(matrices, (screen.width / 2) - 124, (screen.height / 4) + 96, 20, 20, 34, 9,
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
