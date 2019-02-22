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
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumMap;

import static mods.railcraft.api.signals.DualLamp.BOTTOM;
import static mods.railcraft.api.signals.DualLamp.TOP;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class DualSignalReceiver extends SignalReceiver {

    private EnumMap<DualLamp, @Nullable IRule<IColorLightAspect>> rules = new EnumMap<>(DualLamp.class);

    public DualSignalReceiver(String locTag, TileEntity tile) {
        super(locTag, tile, 2);
    }

    @Override
    public void onControllerAspectChange(SignalController con, SignalAspect aspect) {
        BlockPos coord = pairings.peekFirst();
        if (coord == null) {
            return;
        }
        if (coord.equals(con.getCoords())) {
            if (setAspect(TOP, aspect)) {
                super.onControllerAspectChange(con, aspect);
            }
        } else {
            if (setAspect(DualLamp.BOTTOM, aspect)) {
                super.onControllerAspectChange(con, aspect);
            }
        }
    }

    @Override
    protected void saveNBT(NBTTagCompound data) {
        super.saveNBT(data);
        data.setString("topRule", SignalRegistry.INSTANCE.saveRule(rules.get(TOP)));
        data.setString("bottomRule", SignalRegistry.INSTANCE.saveRule(rules.get(BOTTOM)));
    }

    @Override
    protected void loadNBT(NBTTagCompound data) {
        super.loadNBT(data);
        setAspect(TOP, SignalAspect.values()[data.getByte("topAspect")]);
        setAspect(DualLamp.BOTTOM, SignalAspect.values()[data.getByte("bottomAspect")]);

        if (data.hasKey("topRule", Constants.NBT.TAG_STRING))
            setRule(TOP, SignalRegistry.INSTANCE.readRule(data.getString("topRule")));
        if (data.hasKey("bottomRule", Constants.NBT.TAG_STRING))
            setRule(BOTTOM, SignalRegistry.INSTANCE.readRule(data.getString("bottomRule")));
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeUTF(SignalRegistry.INSTANCE.saveRule(rules.get(TOP)));
        data.writeUTF(SignalRegistry.INSTANCE.saveRule(rules.get(DualLamp.BOTTOM)));
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        setRule(TOP, SignalRegistry.INSTANCE.readRule(data.readUTF()));
        setRule(DualLamp.BOTTOM, SignalRegistry.INSTANCE.readRule(data.readUTF()));
    }

    @Deprecated
    public SignalAspect getAspect(DualLamp lamp) {
        return SignalAspect.fromRule(getRule(lamp));
    }

    @Deprecated
    public boolean setAspect(DualLamp lamp, SignalAspect aspect) {
        return setRule(lamp, aspect.getRule());
    }

    public @Nullable IRule<IColorLightAspect> getRule(DualLamp lamp) {
        return rules.get(lamp);
    }

    public boolean setRule(DualLamp lamp, @Nullable IRule<IColorLightAspect> rule) {
        return rules.put(lamp, rule) != rule;
    }
}
