/******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016                                      *
 * *
 * This work (the API) is licensed under the "MIT" License,                   *
 * see LICENSE.md for details.                                                *
 ******************************************************************************/

package mods.railcraft.api.core.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public class RailcraftItemRegistry {
    private static final SortedSet<String> tags = new TreeSet<String>();
    private static final SortedSet<String> tagsImmutable = Collections.unmodifiableSortedSet(tags);
    private static final Map<String, ItemStack> stacks = new HashMap<String, ItemStack>();

    private RailcraftItemRegistry() {
    }

    /**
     * This should only be called by Railcraft. It will throw an exception if called during another mod's load sequence.
     */
    public static void register(String tag, @Nonnull ItemStack stack) {
        stack = Objects.requireNonNull(stack, "Registered ItemStacks cannot be null.");
        if (!"Railcraft".equals(Loader.instance().activeModContainer().getModId()))
            throw new RuntimeException("Only Railcraft can register Railcraft ItemStack, if you see this message there is probably a bug.");
        if (stacks.containsKey(tag)) throw new RuntimeException("Tried to register the tag " + tag + " multiple times!");
        stacks.put(tag, stack);
        tags.add(tag);
    }

    @Nullable
    public static ItemStack getStack(@Nonnull String tag) {
        return getStack(tag, 1);
    }

    @Nullable
    public static ItemStack getStack(@Nonnull String tag, int qty) {
        ItemStack stack = stacks.get(tag);
        if (stack != null) {
            stack = stack.copy();
            stack.stackSize = Math.min(qty, stack.getMaxStackSize());
            return stack;
        }
        return null;
    }

    /**
     * Returns a sorted list of tags that have entries.
     */
    public static SortedSet<String> getTags() {
        return tagsImmutable;
    }
}
