/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/

package mods.railcraft.api.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

/**
 * Created by CovertJaguar on 3/31/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class RailcraftFakePlayer {
    private RailcraftFakePlayer() {
    }

    public static final GameProfile RAILCRAFT_USER_PROFILE = new GameProfile(UUID.nameUUIDFromBytes(RailcraftConstantsAPI.RAILCRAFT_PLAYER.getBytes()), RailcraftConstantsAPI.RAILCRAFT_PLAYER);

    public static EntityPlayerMP get(final WorldServer world, final double x, final double y, final double z) {
        EntityPlayerMP player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        assert player != null;
        player.setPosition(x, y, z);
        return player;
    }

    public static EntityPlayerMP get(final WorldServer world, final double x, final double y, final double z, final ItemStack stack) {
        EntityPlayerMP player = get(world, x, y, z);
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
        return player;
    }

    public static EntityPlayerMP get(final WorldServer world, final BlockPos pos) {
        EntityPlayerMP player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        assert player != null;
        player.setPosition(pos.getX(), pos.getY(), pos.getZ());
        return player;
    }

    public static EntityPlayerMP get(final WorldServer world, final BlockPos pos, final ItemStack stack) {
        EntityPlayerMP player = get(world, pos);
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
        return player;
    }
}
