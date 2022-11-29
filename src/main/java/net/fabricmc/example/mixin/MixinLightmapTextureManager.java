package net.fabricmc.example.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.example.ModMain;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.text.Text;

@Mixin(value = LightmapTextureManager.class)
public class MixinLightmapTextureManager {

	// brightness value using simpleoption
	private static final SimpleOption<Double> brightness = new SimpleOption<>("options.gamma",
			SimpleOption.emptyTooltip(),
			(optionText, value) -> Text.empty(), SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(
					d -> (double) ModMain.getBrightnessValue(), d -> 100.0),
			50.0, value -> {
			});

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getGamma()Lnet/minecraft/client/option/SimpleOption;", opcode = Opcodes.INVOKEVIRTUAL), method = "update(F)V")
	// this passes whatever the option is to the method as everytime
	// Lightmaptexturemanager is called, the modified brightness value is passed in
	private SimpleOption<Double> getFieldValue(GameOptions options) {
		return brightness;
	}
}