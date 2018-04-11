/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/

package mods.railcraft.api.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

/**
 * Created by CovertJaguar on 3/31/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public final class RailcraftFakePlayer {
    private RailcraftFakePlayer() {
    }

    public static final GameProfile RAILCRAFT_USER_PROFILE = new GameProfile(UUID.nameUUIDFromBytes(RailcraftConstantsAPI.RAILCRAFT_PLAYER.getBytes()), RailcraftConstantsAPI.RAILCRAFT_PLAYER);

    public static EntityPlayer get(WorldServer world, double x, double y, double z, ItemStack stack, EnumHand hand) {
        EntityPlayer player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        player.setPosition(x, y, z);
        player.setHeldItem(hand, stack);
        return player;
    }

    public static EntityPlayer get(final WorldServer world, final double x, final double y, final double z) {
        EntityPlayer player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        player.setPosition(x, y, z);
        return player;
    }

    public static EntityPlayer get(final WorldServer world, final BlockPos pos) {
        EntityPlayer player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        player.setPosition(pos.getX(), pos.getY(), pos.getZ());
        return player;
    }

    public static EntityPlayer get(WorldServer world, BlockPos pos, ItemStack stack, EnumHand hand) {
        EntityPlayer player = FakePlayerFactory.get(world, RAILCRAFT_USER_PROFILE);
        player.setPosition(pos.getX(), pos.getY(), pos.getZ());
        player.setHeldItem(hand, stack);
        return player;
    }
}
