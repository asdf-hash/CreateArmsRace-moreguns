package co.basin.createarmsrace.network;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.entity.ResearchTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchTableMessage {
    private final int researchSelectionIndex;
    private final BlockPos position;

    public ResearchTableMessage(int index, BlockPos position) {
        this.researchSelectionIndex = index;
        this.position = position;
    }

    public ResearchTableMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.researchSelectionIndex);
        buf.writeBlockPos(position);
    }

    public static void handle(ResearchTableMessage message, Supplier<NetworkEvent.Context> ctx) {
        CreateArmsRace.log("Packet Received");
        ctx.get().enqueueWork(() -> {
                ServerLevel level = (ServerLevel) ctx.get().getSender().level();
                BlockEntity blockEntity = level.getBlockEntity(message.position);
                if (!(blockEntity instanceof ResearchTableBlockEntity)) { return; }
                ((ResearchTableBlockEntity) blockEntity).setResearchSelectionIndex(message.researchSelectionIndex);
        });
        ctx.get().setPacketHandled(true);
    }
}
