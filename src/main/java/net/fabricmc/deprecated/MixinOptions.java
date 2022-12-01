package net.fabricmc.deprecated;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ModMain;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

@Mixin(value = OptionsScreen.class)
public class MixinOptions extends Screen {
    private int width;
    private int height;

    private final Screen parent;

    public Screen getParent() {
        return parent;
    }

    private final GameOptions settings;

    public MixinOptions(Screen parent, GameOptions gameOptions) {
        super(Text.translatable("options.title"));
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    private void init(CallbackInfo info) {
        ModMain.log.info("Options menu opened");
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20,
                Text.translatable("options.video"),
                button -> this.client.setScreen(new VideoOptionsScreen(this, this.settings))));
    }
}