package net.steelcodeteam.setup.network;

import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.setup.network.packets.GenerateItemPacket;

public class ModNetwork {

    private static final String VERSION = RedstonesTable.VERSION;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(RedstonesTable.MOD_ID,
            "network_tunnel"), () -> VERSION, VERSION::equals, VERSION::equals);

    private static int index = 0;

    private static int nextIndex() {
        return index++;
    }

    public static void registerPackets() {
        INSTANCE.registerMessage(nextIndex(), GenerateItemPacket.class, GenerateItemPacket::encode, GenerateItemPacket::decode, GenerateItemPacket::handle);

    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }

    public static void sendTo(Object msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            INSTANCE.sendTo(msg,player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendTo(Object msg, PacketDistributor.PacketTarget target) {
        INSTANCE.send(target, msg);
    }

    public static void syncGenerateItemPacket(Player player, ItemStack itemStack) {
        sync(new GenerateItemPacket(player.getUUID(), itemStack), player);
    }

    public static void sync(Object msg, Player player) {
        sendTo(msg, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }
}
