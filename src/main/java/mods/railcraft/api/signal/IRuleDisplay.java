/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.signal;

import org.jetbrains.annotations.Nullable;

public interface IRuleDisplay<T extends IAspect> {

    /**
     * Gets the supported base aspect type.
     */
    Class<? extends T> getAspectType();

    /**
     * Returns how many aspects in a rule can this displayer display.
     */
    int getHeads();

    /**
     * Sets the rule displaying in the displayer.
     *
     * @param rule the rule to display
     * @return true if the rule is properly displayed
     */
    boolean setRule(@Nullable IRule<? extends T> rule);

    @Nullable IRule<? extends T> getCurrentRule();

}
