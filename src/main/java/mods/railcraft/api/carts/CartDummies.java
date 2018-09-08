package mods.railcraft.api.carts;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.function.Predicate;

/**
 *
 */
final class CartDummies {

    static ITrainTransferHelper DUMMY_TRANSFER_HELPER = new ITrainTransferHelper() {
        @Override
        public ItemStack pushStack(EntityMinecart requester, ItemStack stack) {
            return stack;
        }

        @Override
        public ItemStack pullStack(EntityMinecart requester, Predicate<@NotNull ItemStack> filter) {
            return ItemStack.EMPTY;
        }

        @Override
        public void offerOrDropItem(EntityMinecart requester, ItemStack stack) {
        }

        @Override
        public @Nullable IItemHandler getTrainItemHandler(EntityMinecart cart) {
            return null;
        }

        @Override
        public FluidStack pushFluid(EntityMinecart requester, FluidStack fluidStack) {
            return fluidStack;
        }

        @Override
        public @Nullable FluidStack pullFluid(EntityMinecart requester, @Nullable FluidStack fluidStack) {
            return null;
        }

        @Override
        public @Nullable IFluidHandler getTrainFluidHandler(EntityMinecart cart) {
            return null;
        }
    };

    static final ILinkageManager DUMMY_LINKAGE_MANAGER = new ILinkageManager() {
        @Override
        public boolean setAutoLink(EntityMinecart cart, boolean autoLink) {
            return false;
        }

        @Override
        public boolean hasAutoLink(EntityMinecart cart) {
            return false;
        }

        @Override
        public boolean tryAutoLink(EntityMinecart cart1, EntityMinecart cart2) {
            return false;
        }

        @Override
        public boolean createLink(EntityMinecart cart1, EntityMinecart cart2) {
            return false;
        }

        @Override
        public boolean hasFreeLink(EntityMinecart cart) {
            return false;
        }

        @Override
        public @Nullable EntityMinecart getLinkedCartA(EntityMinecart cart) {
            return null;
        }

        @Override
        public @Nullable EntityMinecart getLinkedCartB(EntityMinecart cart) {
            return null;
        }

        @Override
        public boolean areLinked(EntityMinecart cart1, EntityMinecart cart2) {
            return false;
        }

        @Override
        public void breakLink(EntityMinecart cart1, EntityMinecart cart2) {
        }

        @Override
        public void breakLinkA(EntityMinecart cart) {
        }

        @Override
        public void breakLinkB(EntityMinecart cart) {
        }

        @Override
        public int countCartsInTrain(EntityMinecart cart) {
            return 0;
        }

        @Override
        public Iterable<EntityMinecart> trainIterator(EntityMinecart cart) {
            return Collections.emptyList();
        }
    };

    private CartDummies() {}
}
