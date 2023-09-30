package net.steelcodeteam.setup.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.fixes.ItemStackSpawnEggFix;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

public class GenerateItemPacket {

    private final UUID uuid;
    private final ItemStack itemStack;

    public GenerateItemPacket(UUID uuid, ItemStack itemStack) {
        this.uuid = uuid;
        this.itemStack = itemStack;
    }

    public static GenerateItemPacket decode(FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        ItemStack itemStack = buf.readItem();

        return new GenerateItemPacket(uuid, itemStack);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        buf.writeItemStack(this.itemStack, false);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();

            if (player != null) {
                player.getInventory().add(this.itemStack);
            }
        });
    }
}
