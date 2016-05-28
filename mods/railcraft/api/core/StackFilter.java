/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/

package mods.railcraft.api.core;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to provide a convenient means of dealing with entire classes of items without having to specify each item individually.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("unused")
public class StackFilter implements IStackFilter {
    /**
     * Railcraft adds the following IItemTypes during preInit: ALL, FUEL, TRACK, MINECART, BALLAST, FEED
     * <p/>
     * Feel free to grab them from here or define your own.
     */
    public static final Map<String, IStackFilter> standardFilters = new HashMap<String, IStackFilter>();

    @Override
    public boolean apply(@Nullable final ItemStack input) {
        return true;
    }

    @Override
    public final StackFilter and(final Predicate<? super ItemStack>... other) {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                for (Predicate<? super ItemStack> filter : other) {
                    if (!filter.apply(stack))
                        return false;
                }
                return StackFilter.this.apply(stack);
            }
        };
    }

    @Override
    public final StackFilter or(final Predicate<? super ItemStack>... other) {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                for (Predicate<? super ItemStack> filter : other) {
                    if (filter.apply(stack))
                        return true;
                }
                return StackFilter.this.apply(stack);
            }
        };
    }

    @Override
    public final StackFilter negate() {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                return !StackFilter.this.apply(stack);
            }
        };
    }

    public static StackFilter buildAnd(final Predicate<? super ItemStack>... filters) {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                for (Predicate<? super ItemStack> filter : filters) {
                    if (!filter.apply(stack))
                        return false;
                }
                return true;
            }
        };
    }

    public static StackFilter buildOr(final Predicate<? super ItemStack>... filters) {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                for (Predicate<? super ItemStack> filter : filters) {
                    if (filter.apply(stack))
                        return true;
                }
                return false;
            }
        };
    }

    public static StackFilter invert(final Predicate<? super ItemStack> filter) {
        return new StackFilter() {
            @Override
            public boolean apply(ItemStack stack) {
                return !filter.apply(stack);
            }
        };
    }
}
