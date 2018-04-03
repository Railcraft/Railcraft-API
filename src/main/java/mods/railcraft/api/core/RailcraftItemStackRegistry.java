/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * This class provides a method to acquire ItemStacks for Railcraft objects.
 * <p/>
 * The tags are generally similar to the ones you might find in the Blocks/Items/Localization config files.
 * <p/>
 * Ex. ItemStack stack = getStack("signal.box.capacitor");
 * <p/>
 * Created by CovertJaguar on 3/3/2016.
 */
public class RailcraftItemStackRegistry {
    private static final SortedSet<String> tags = new TreeSet<String>();
    private static final SortedSet<String> tagsImmutable = Collections.unmodifiableSortedSet(tags);
    private static final Map<String, ItemStack> stacks = new HashMap<String, ItemStack>();

    private RailcraftItemStackRegistry() {
    }

    /**
     * This should only be called by Railcraft. It will throw an exception if called during another mod's load sequence.
     */
    public static void register(IForgeRegistryEntry<?> object, @Nonnull ItemStack stack) {
        _register(object.getRegistryName().getResourcePath(), stack);
    }

    /**
     * This should only be called by Railcraft. It will throw an exception if called during another mod's load sequence.
     */
    public static void register(IRailcraftRegistryEntry<?> object, IVariantEnum variant, @Nonnull ItemStack stack) {
        _register(object.getRegistryName(variant).getResourcePath(), stack);
    }

    private static void _register(String tag, @Nonnull ItemStack stack) {
        if (stack == null)
            throw new RuntimeException("Tried to register a null stack with the tag: " + tag);
        if (!RailcraftConstantsAPI.MOD_ID.equals(Loader.instance().activeModContainer().getModId()))
            throw new RuntimeException("Only Railcraft can register a Railcraft ItemStack, if you see this message there is probably a bug.");
        if (stacks.containsKey(tag))
            throw new RuntimeException("Tried to register the tag " + tag + " multiple times!");
        stacks.put(tag, stack);
        tags.add(tag);
    }

    public static Optional<ItemStack> getStack(@Nonnull String tag) {
        return getStack(tag, 1);
    }

    public static Optional<ItemStack> getStack(@Nonnull String tag, int qty) {
        ItemStack stack = stacks.get(tag);
        if (stack != null) {
            stack = stack.copy();
            stack.setCount(Math.min(qty, stack.getMaxStackSize()));
        }
        return Optional.ofNullable(stack);
    }

    /**
     * Returns a sorted list of tags that have entries.
     */
    public static SortedSet<String> getTags() {
        return tagsImmutable;
    }
}
