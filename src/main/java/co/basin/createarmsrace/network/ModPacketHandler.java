package co.basin.createarmsrace.network;

import co.basin.createarmsrace.CreateArmsRace;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CreateArmsRace.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerAllMessages() {
        int id = 0;
        INSTANCE.registerMessage(id++, ResearchTableMessage.class, ResearchTableMessage::encode, ResearchTableMessage::new, ResearchTableMessage::handle);
    }
}
