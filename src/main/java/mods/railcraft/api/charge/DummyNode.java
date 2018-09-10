package mods.railcraft.api.charge;

import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

/**
 * A dummy node.
 */
final class DummyNode implements IChargeNode {
    private final IChargeDimension dimension;
    private final BlockPos pos;

    DummyNode(IChargeDimension dimension, BlockPos pos) {
        this.dimension = dimension;
        this.pos = pos;
    }

    @Override
    public boolean canUseCharge(double amount) {
        return false;
    }

    @Override
    public boolean useCharge(double amount) {
        return false;
    }

    @Override
    public double removeCharge(double desiredAmount) {
        return 0;
    }

    @Override
    public void addListener(BiConsumer<? super IChargeNode, ? super Double> listener) {
    }

    @Override
    public void removeListener(BiConsumer<? super IChargeNode, ? super Double> listener) {
    }

    @Override
    public void loadBattery() {
    }

    @Override
    public void unloadBattery() {
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public IChargeDimension getDimension() {
        return dimension;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }
}
