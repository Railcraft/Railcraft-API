/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Railcraft adds the following Predicates during preInit: ALL, FUEL, TRACK, MINECART, BALLAST, FEED
 * <p/>
 * Feel free to grab them from here or define your own.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class RailcraftStackFilters {
    public static RailcraftStackFilters instance;
    private final Map<String, Predicate<ItemStack>> standardFilters;

    public Predicate<ItemStack> get(String key) {
        return standardFilters.get(key);
    }

    private RailcraftStackFilters(Map<String, Predicate<ItemStack>> standardFilters) {
        this.standardFilters = standardFilters;
    }

    public static void init(Map<String, Predicate<ItemStack>> standardFilters) {
        instance = new RailcraftStackFilters(standardFilters);
    }
}
