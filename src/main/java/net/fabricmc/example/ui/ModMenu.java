package net.fabricmc.example.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.UpdateDifficultyC2SPacket;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;

//from modmain
import net.minecraft.client.gui.screen.option.OptionsScreen;

@Environment(value = EnvType.CLIENT)
public class ModMenu
        extends Screen {
    private final Screen parent;
    private final GameOptions settings;

    private int windowHeight = MinecraftClient.getInstance().currentScreen.height;

    public ModMenu(Screen parent, GameOptions gameOptions) {
        super(Text.translatable("Mylo's Mod Menu"));
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Override
    protected void init() {
        int i = 0;
        for (SimpleOption<?> simpleOption : new SimpleOption[] { this.settings.getFov() }) {
            int j = this.width / 2 - 155 + i % 2 * 160;
            int k = this.height / 6 - 12 + 24 * (i >> 1);
            this.addDrawableChild(simpleOption.createButton(this.client.options, j, k, 150));
            ++i;
        }

        // Add a new button // criticals
        this.addDrawableChild(new CriticalsButton(this.width / 2 + 5, this.height / 6 + 144 - 6, 75, 20));
        // add the new slider // brightness slider
        this.addDrawableChild(new BrightnessSlider(this.width / 2 + 80, this.height / 6 + 144 - 6, 75, 20, "Brightness",
                windowHeight));
        // add the new button // antifall
        this.addDrawableChild(new AntiFallButton(this.width / 2 + 5, this.height / 6 + 120 - 6, 75, 20));

        // this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 -
        // 12 + 24 * (i >> 1), 150, 20,
        // Text.translatable("options.online"),
        // button -> this.client.setScreen(new OnlineOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6
        // + 48 - 6, 150, 20,
        // Text.translatable("options.skinCustomisation"),
        // button -> this.client.setScreen(new SkinOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 +
        // 48 - 6, 150, 20,
        // Text.translatable("options.sounds"),
        // button -> this.client.setScreen(new SoundOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6
        // + 72 - 6, 150, 20,
        // Text.translatable("options.video"),
        // button -> this.client.setScreen(new VideoOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 +
        // 72 - 6, 150, 20,
        // Text.translatable("options.controls"),
        // button -> this.client.setScreen(new ControlsOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6
        // + 96 - 6, 150, 20,
        // Text.translatable("options.language"), button -> this.client.setScreen(
        // new LanguageOptionsScreen((Screen) this, this.settings,
        // this.client.getLanguageManager()))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 +
        // 96 - 6, 150, 20,
        // Text.translatable("options.chat.title"),
        // button -> this.client.setScreen(new ChatOptionsScreen(this,
        // this.settings))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6
        // + 120 - 6, 150, 20,
        // Text.translatable("options.resourcepack"),
        // button -> this.client.setScreen(
        // new PackScreen(this, this.client.getResourcePackManager(),
        // this::refreshResourcePacks,
        // this.client.getResourcePackDir(),
        // Text.translatable("resourcePack.title")))));
        // this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 +
        // 120 - 6, 150, 20,
        // Text.translatable("options.accessibility.title"),
        // button -> this.client.setScreen(new AccessibilityOptionsScreen(this,
        // this.settings))));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, ScreenTexts.DONE,
                button -> this.client.setScreen(this.parent)));

    }

    public static CyclingButtonWidget<Difficulty> createDifficultyButtonWidget(int buttonIndex, int width, int height,
            String translationKey, MinecraftClient client) {
        return CyclingButtonWidget.builder(Difficulty::getTranslatableName).values((Difficulty[]) Difficulty.values())
                .initially(client.world.getDifficulty()).build(width / 2 - 155 + buttonIndex % 2 * 160,
                        height / 6 - 12 + 24 * (buttonIndex >> 1), 150, 20, Text.translatable(translationKey),
                        (button, difficulty) -> client.getNetworkHandler()
                                .sendPacket(new UpdateDifficultyC2SPacket((Difficulty) ((Object) difficulty))));
    }

    @Override
    public void removed() {
        this.settings.write();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        OptionsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
