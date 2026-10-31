package com.fastclick.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Invoker("doAttack")
    boolean fastclick$doAttack();

    @Invoker("doItemUse")
    void fastclick$doItemUse();

    @Accessor("itemUseCooldown")
    void fastclick$setItemUseCooldown(int value);
}
