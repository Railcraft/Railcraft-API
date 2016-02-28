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

/**
 * This interface is used to provide a convenient means of dealing with entire classes of items without having to specify each item individually.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IStackFilter extends Predicate<ItemStack> {

    StackFilter and(@Nonnull Predicate<? super ItemStack>... other);

    StackFilter or(@Nonnull Predicate<? super ItemStack>... other);

    StackFilter negate();
}
