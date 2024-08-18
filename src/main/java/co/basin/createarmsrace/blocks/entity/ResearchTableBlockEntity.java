package co.basin.createarmsrace.blocks.entity;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import co.basin.createarmsrace.screen.ResearchTableMenu;
import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResearchTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 80000;
    private int researchSelectionIndex = 0;

    public ResearchTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.RESEARCH_TABLE.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ResearchTableBlockEntity.this.progress;
                    case 1 -> ResearchTableBlockEntity.this.maxProgress;
                    case 2 -> ResearchTableBlockEntity.this.researchSelectionIndex;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ResearchTableBlockEntity.this.progress = value;
                    case 1 -> ResearchTableBlockEntity.this.maxProgress = value;
                    case 2 -> ResearchTableBlockEntity.this.setResearchSelectionIndex(index);
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public void setResearchSelectionIndex(int index) {
        if (index < 0 || index >= Config.research_lengths.size()) { return; }
        this.researchSelectionIndex = index;
        this.maxProgress = Config.research_lengths.get(index);
        setChanged(level, getBlockPos(), getBlockState());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(CreateArmsRace.MODID + ".block.entity.research_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ResearchTableMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ResearchTableBlockEntity entity) {
        if (level.isClientSide()) { return; }

        if (hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, pos, state);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ResearchTableBlockEntity entity) {
        if (hasRecipe(entity)) {
            entity.itemStackHandler.extractItem(0, 1, false);
            ItemStack stack = new ItemStack(ModItems.COMPLETED_SCHEMATICS.get(entity.researchSelectionIndex).get());
            entity.itemStackHandler.setStackInSlot(1, stack);
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(ResearchTableBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
        for (int i = 0; i < entity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
        }

        boolean hasSchematicInFirstSlot = entity.itemStackHandler.getStackInSlot(0).getItem() == AllItems.EMPTY_SCHEMATIC.get();

        return hasSchematicInFirstSlot && hasSpaceForOutput(inventory);
    }

    private static boolean canOutputItem(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(1).getItem() == itemStack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean hasSpaceForOutput(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }
}
