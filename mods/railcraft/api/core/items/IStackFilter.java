/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.core.items;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface is used with several of the functions in IItemTransfer
 * to provide a convenient means of dealing with entire classes of items without
 * having to specify each item individually.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IStackFilter extends Predicate<ItemStack> {
    /**
     * Railcraft adds the following IItemTypes during preInit: ALL, FUEL, TRACK, MINECART, BALLAST, FEED
     * <p/>
     * Feel free to grab them from here or define your own.
     */
    Map<String, IStackFilter> standardFilters = new HashMap<String, IStackFilter>();

    StackFilter and(@Nonnull Predicate<? super ItemStack>... other);

    StackFilter or(@Nonnull Predicate<? super ItemStack>... other);

    StackFilter negate();
}
