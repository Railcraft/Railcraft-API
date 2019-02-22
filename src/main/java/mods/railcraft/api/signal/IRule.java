/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.signal;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Represents a rule in signal.
 *
 * They can be compared based on their restrictiveness.
 */
public interface IRule<T extends IAspect> extends Comparable<IRule<?>> {

    ResourceLocation getId();

    List<T> getAspects();

    Class<? extends T> getAspectType();

    // TODO speed limits, etc.

    @Override
    default int compareTo(@NotNull IRule<?> o) {
        return SignalRegistry.INSTANCE.compare(this, o);
    }

    class Impl<T extends IAspect> implements IRule<T> {

        protected final ResourceLocation id;
        protected final List<T> aspects;
        protected final Class<? extends T> type;

        public Impl(ResourceLocation id, T aspect, Class<? extends T> type) {
            this(id, Collections.singletonList(aspect), type);
        }

        // Make sure the aspects list is immutable!
        public Impl(ResourceLocation id, List<T> aspects, Class<? extends T> type) {
            this.id = id;
            this.aspects = aspects;
            this.type = type;
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public List<T> getAspects() {
            return aspects;
        }

        @Override
        public Class<? extends T> getAspectType() {
            return type;
        }
    }
}
