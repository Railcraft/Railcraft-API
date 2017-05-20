/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.RailcraftFakePlayer;
import mods.railcraft.api.core.items.ITrackItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

/**
 * A number of utility functions related to rails.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings({"WeakerAccess"})
public final class TrackToolsAPI {
    public static IBlockTrackOutfitted blockTrackOutfitted = null;

    /**
     * Check if the block at the location is a Track.
     */
    public static boolean isRailBlockAt(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof BlockRailBase;
    }

    public static IBlockState makeTrackState(BlockRailBase block, @Nullable BlockRailBase.EnumRailDirection trackShape) {
        IProperty<BlockRailBase.EnumRailDirection> property = block.getShapeProperty();
        IBlockState state = block.getDefaultState();
        if (trackShape != null && property.getAllowedValues().contains(trackShape))
            state = state.withProperty(property, trackShape);
        return state;
    }

    /**
     * Attempts to place a rail of the type provided. There is no need to verify
     * that the ItemStack contains a valid rail prior to calling this function.
     * <p/>
     * The function takes care of that and will return false if the ItemStack is
     * not a valid ITrackItem or an ItemBlock who's id will return true when
     * passed to BlockRailBase.isRailBlock(itemID).
     * <p/>
     * That means this function can place any Railcraft or vanilla rail and has
     * at least a decent chance of being able to place most third party rails.
     *
     * @param stack The ItemStack containing the rail
     * @param world The World object
     * @return true if successful
     * @see ITrackItem
     */
    public static boolean placeRailAt(ItemStack stack, WorldServer world, BlockPos pos, BlockRailBase.EnumRailDirection trackShape) {
        if (stack.getItem() instanceof ITrackItem)
            return ((ITrackItem) stack.getItem()).placeTrack(stack.copy(), RailcraftFakePlayer.get(world, pos.offset(EnumFacing.UP)), world, pos, trackShape);
        if (stack.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockRailBase) {
                IBlockState blockState = makeTrackState((BlockRailBase) block, trackShape);
                boolean success = world.setBlockState(pos, blockState);
                if (success)
                    world.playSound(null, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
                return success;
            }
        }
        return false;
    }

    public static boolean placeRailAt(ItemStack stack, WorldServer world, BlockPos pos) {
        return placeRailAt(stack, world, pos, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    }

    /**
     * Returns true if the ItemStack contains a valid Railcraft Track item.
     * <p/>
     * Will return false is passed a vanilla rail.
     *
     * @param stack The ItemStack to test
     * @return true if rail
     * @see ITrackItem
     */
    public static boolean isTrackItem(@Nullable ItemStack stack) {
        return stack != null && stack.getItem() instanceof ITrackItem;
    }

    /**
     * Checks to see if a cart is being held by a ITrackLockdown.
     *
     * @param cart The cart to check
     * @return True if being held
     */
    public static boolean isCartLockedDown(EntityMinecart cart) {
        BlockPos pos = cart.getPosition();

        if (BlockRailBase.isRailBlock(cart.getEntityWorld(), pos.down()))
            pos = pos.down();

        TileEntity tile = cart.getEntityWorld().getTileEntity(pos);
        if (tile instanceof IOutfittedTrackTile) {
            ITrackKitInstance track = ((IOutfittedTrackTile) tile).getTrackKitInstance();
            return track instanceof ITrackKitLockdown && ((ITrackKitLockdown) track).isCartLockedDown(cart);
        } else if (tile instanceof ITrackKitLockdown)
            return ((ITrackKitLockdown) tile).isCartLockedDown(cart);
        return false;
    }

    public static int countAdjacentTracks(World world, BlockPos pos) {
        int i = 0;

        for (EnumFacing side : EnumFacing.HORIZONTALS) {
            if (isTrackFuzzyAt(world, pos.offset(side)))
                ++i;
        }

        return i;
    }

    public static boolean isTrackFuzzyAt(World world, BlockPos pos) {
        return BlockRailBase.isRailBlock(world, pos) || (BlockRailBase.isRailBlock(world, pos.up()) || BlockRailBase.isRailBlock(world, pos.down()));
    }

//    public static Set<IOutfittedTrackTile> getAdjacentTrackTiles(World world, BlockPos pos) {
//        Set<IOutfittedTrackTile> tracks = new HashSet<IOutfittedTrackTile>();
//
//        for (EnumFacing side : EnumFacing.HORIZONTALS) {
//            IOutfittedTrackTile tile = getTrackFuzzyAt(world, pos.offset(side));
//            if (tile != null)
//                tracks.add(tile);
//        }
//
//        return tracks;
//    }

//    @Nullable
//    public static IOutfittedTrackTile getTrackFuzzyAt(World world, BlockPos pos) {
//        TileEntity tile = world.getTileEntity(pos);
//        if (tile instanceof IOutfittedTrackTile)
//            return (IOutfittedTrackTile) tile;
//        tile = world.getTileEntity(pos.up());
//        if (tile instanceof IOutfittedTrackTile)
//            return (IOutfittedTrackTile) tile;
//        tile = world.getTileEntity(pos.down());
//        if (tile instanceof IOutfittedTrackTile)
//            return (IOutfittedTrackTile) tile;
//        return null;
//    }

//    public static <T> Set<T> getAdjacentTrackObjects(World world, BlockPos pos, Class<T> type) {
//        Set<T> tracks = new HashSet<T>();
//
//        for (EnumFacing side : EnumFacing.HORIZONTALS) {
//            T object = getTrackObjectFuzzyAt(world, pos.offset(side), type);
//            if (object != null)
//                tracks.add(object);
//        }
//
//        return tracks;
//    }

//    @Nullable
//    public static <T> T getTrackObjectFuzzyAt(World world, BlockPos pos, Class<T> type) {
//        T object = getTrackObjectAt(world, pos, type);
//        if (object != null)
//            return object;
//        object = getTrackObjectAt(world, pos.up(), type);
//        if (object != null)
//            return object;
//        object = getTrackObjectAt(world, pos.down(), type);
//        if (object != null)
//            return object;
//        return null;
//    }

//    @Nullable
//    @SuppressWarnings("unchecked")
//    public static <T> T getTrackObjectAt(World world, BlockPos pos, Class<T> type) {
//        TileEntity tile = world.getTileEntity(pos);
//        if (tile == null)
//            return null;
//        if (type.isInstance(tile))
//            return (T) tile;
//        if (tile instanceof IOutfittedTrackTile) {
//            ITrackKitInstance track = ((IOutfittedTrackTile) tile).getTrackKitInstance();
//            if (type.isInstance(track))
//                return (T) track;
//        }
//        return null;
//    }

    private TrackToolsAPI() {}

}
