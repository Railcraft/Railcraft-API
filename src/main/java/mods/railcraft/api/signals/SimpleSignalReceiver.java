/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/
package mods.railcraft.api.signals;

import mods.railcraft.api.signal.IColorLightAspect;
import mods.railcraft.api.signal.IRule;
import mods.railcraft.api.signal.SignalRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class SimpleSignalReceiver extends SignalReceiver {

    private @Nullable IRule<IColorLightAspect> rule;

    public SimpleSignalReceiver(String locTag, TileEntity tile) {
        super(locTag, tile, 1);
    }

    @Override
    public void tickServer() {
        super.tickServer();
        SignalAspect prevAspect = getAspect();
        if (!isPaired()) {
            setAspect(SignalAspect.BLINK_RED);
        }
        if (prevAspect != getAspect()) {
            sendUpdateToClient();
        }
    }

    @Deprecated
    public SignalAspect getAspect() {
        return SignalAspect.fromRule(rule);
    }

    @Deprecated
    public void setAspect(SignalAspect aspect) {
        this.rule = aspect.getRule();
    }

    public @Nullable IRule<IColorLightAspect> getRule() {
        return rule;
    }

    public void setRule(@Nullable IRule<IColorLightAspect> rule) {
        this.rule = rule;
    }

    @Override
    public void onControllerAspectChange(SignalController con, SignalAspect aspect) {
        if (this.rule != aspect.getRule()) {
            this.rule = aspect.getRule();
            super.onControllerAspectChange(con, aspect);
        }
    }

    @Override
    protected void saveNBT(NBTTagCompound data) {
        super.saveNBT(data);
        data.setString("rule", SignalRegistry.INSTANCE.saveRule(rule));
    }

    @Override
    protected void loadNBT(NBTTagCompound data) {
        super.loadNBT(data);
        if (data.hasKey("aspect", Constants.NBT.TAG_BYTE))
            rule = SignalAspect.VALUES[data.getByte("aspect")].getRule();
        if (data.hasKey("rule", Constants.NBT.TAG_STRING))
            rule = SignalRegistry.INSTANCE.readRule(data.getString("rule"));
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeUTF(SignalRegistry.INSTANCE.saveRule(rule));
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        rule = SignalRegistry.INSTANCE.readRule(data.readUTF());
    }

    @Override
    public String toString() {
        return String.format("Receiver:%s (%s)", SignalRegistry.INSTANCE.saveRule(rule), super.toString());
    }
}
