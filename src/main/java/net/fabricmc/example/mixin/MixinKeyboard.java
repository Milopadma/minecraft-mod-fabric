package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ModMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

@Mixin(value = KeyboardInput.class)
public class MixinKeyboard {
    // class fields
    MinecraftClient client = MinecraftClient.getInstance();
    ClientWorld thisWorld = client.world;

    // overlays over keyboard inputs.
    @Inject(at = @At("RETURN"), method = "tick(ZF)V")
    private void tick(boolean slowDown, float f, CallbackInfo callbackInfo) {
        // check if the world is null
        if (ModMain.client.world == null) {
            ModMain.client.world = thisWorld;
        }

        // for the scaffold functionality
        if (ModMain.SCAFFOLD) {
            // check if the player is on the ground, there are AIR blocks below them, and if
            // they are holding blocks of the BUILDING_BLOCKS group
            if (ModMain.player.isOnGround()
                    && ModMain.clientWorld.getBlockState(ModMain.player.getBlockPos().down()).isAir()
                    && ModMain.player.getMainHandStack().getItem().getGroup().equals(ItemGroup.BUILDING_BLOCKS)) {
                // log this to console
                ModMain.log.info("Scaffold is on");
                // try to place the block below the player and log the result to console
                try {

                    ModMain.client.interactionManager.interactBlock(ModMain.player, ModMain.player.getActiveHand(),
                            new BlockHitResult(ModMain.player.getPos(), Direction.DOWN,
                                    ModMain.player.getBlockPos().down(), false));
                    ModMain.log.info("Block placed");
                } catch (Exception e) {
                    ModMain.log.info("Block not placed");
                }
            } else {
                return;
            }
        }
    }
}
