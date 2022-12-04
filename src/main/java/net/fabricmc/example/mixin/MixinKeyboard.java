package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ModMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

@Mixin(value = KeyboardInput.class)
public class MixinKeyboard {
    // class fields
    MinecraftClient client = MinecraftClient.getInstance();
    ClientWorld thisWorld = client.world;

    Hand CURRENT_HAND = null; // nullify this first

    // overlays over keyboard inputs.
    @Inject(at = @At("RETURN"), method = "tick(ZF)V")
    private void tick(boolean slowDown, float f, CallbackInfo callbackInfo) {
        try {
            // check if the player is null
            if (ModMain.player == null) {
                ModMain.player = client.player;
                // log this
                ModMain.log.info("MixinKeyboard player null, setting to: " + client.player);
            }
            // check if the world is null
            if (ModMain.clientWorld == null) {
                ModMain.clientWorld = thisWorld;
                ModMain.log.info("MixinKeyboard world null, setting to: " + thisWorld);
            }
            // for the scaffold functionality
            if (ModMain.isScaffoldEnabled()) {
                try {
                    // then set the current hand according to where the blocks are, prioritizing the
                    // main hand
                    try {
                        // check if main hand is null, then check if the other hand is null, if both
                        // are, return
                        if (ModMain.client.player.getMainHandStack().getItem()
                                .getGroup() == ItemGroup.BUILDING_BLOCKS) {
                            CURRENT_HAND = Hand.MAIN_HAND;
                        } else if (ModMain.client.player.getOffHandStack().getItem()
                                .getGroup() == ItemGroup.BUILDING_BLOCKS) {
                            CURRENT_HAND = Hand.OFF_HAND;
                        } else {
                            // log this
                            // ModMain.log.info("No blocks in hand");
                            return;
                        }
                    } catch (Exception e) {
                        // ModMain.log.info(e);
                        return;
                    }
                    // check if the player is on the ground, there are AIR blocks below them
                    // also check if its null first to avoid console spam
                    try {
                        if (CURRENT_HAND != null) {
                            // ModMain.log.info("Current hand: " + CURRENT_HAND);
                            // then do the checks of ground
                            if (ModMain.player.isOnGround()) {
                                // then do the checks for air, lava, and water
                                if (ModMain.clientWorld.getBlockState(ModMain.player.getBlockPos().down()).isAir()
                                        || ModMain.clientWorld.getBlockState(ModMain.player.getBlockPos().down())
                                                .getFluidState().getFluid() == Fluids.WATER
                                        || ModMain.clientWorld.getBlockState(ModMain.player.getBlockPos().down())
                                                .getFluidState().getFluid() == Fluids.LAVA) {
                                    // try to place the block below the player and log the result to console
                                    try {
                                        ModMain.client.interactionManager.interactBlock(ModMain.player,
                                                CURRENT_HAND,
                                                new BlockHitResult(ModMain.player.getPos(), Direction.DOWN,
                                                        ModMain.player.getBlockPos().down(), false));
                                        // ModMain.log.info("Block placed");
                                    } catch (Exception e) {
                                        // ModMain.log.info("Block not placed" + e);
                                        return;
                                    }
                                } else {
                                    // theres blocks below
                                    return;
                                }
                            }
                        } else {
                            // current hand null
                            // ModMain.log.info("Current hand not building blocks");
                            return;
                        }
                    } catch (Exception e) {
                        // log this
                        // ModMain.log.info("Err" + e);
                        return;
                    }
                } catch (Exception e) {
                    // log this
                    // ModMain.log.info("Err" + e);
                    return;
                }
            }
        } catch (Exception e) {
            // log this
            ModMain.log.info("Err" + e);
            return;
        }
    }
}