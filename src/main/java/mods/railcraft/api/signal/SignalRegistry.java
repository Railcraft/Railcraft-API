/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.signal;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SignalRegistry {

    public static final SignalRegistry INSTANCE = new SignalRegistry();
    private final Map<ResourceLocation, IAspect> aspects = new HashMap<>();
    private final Map<ResourceLocation, IRule<?>> rules = new HashMap<>();

    public int compare(IRule<?> one, IRule<?> two) {
        return 0; //TODO
    }

    public void register(IAspect aspect) {
        aspects.put(aspect.getId(), aspect);
    }

    public void register(IRule<?> rule) {
        rules.put(rule.getId(), rule);
    }

    public boolean hasAspect(ResourceLocation id) {
        return aspects.containsKey(id);
    }

    public boolean hasRule(ResourceLocation id) {
        return rules.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends IAspect> T getAspect(ResourceLocation id) {
        return (T) Objects.requireNonNull(aspects.get(id));
    }


    public <T extends IAspect> IRule<T> getRule(ResourceLocation id) {
        return Objects.requireNonNull(getRuleUnchecked(id));
    }

    @SuppressWarnings("unchecked")
    public <T extends IAspect> @Nullable IRule<T> getRuleUnchecked(ResourceLocation id) {
        return (IRule<T>) aspects.get(id);
    }

    public String saveRule(@Nullable IRule<?> rule) {
        return rule == null ? "null" : rule.getId().toString();
    }

    public <T extends IAspect> @Nullable IRule<T> readRule(String saved) {
        return "null".equals(saved) ? null : getRuleUnchecked(new ResourceLocation(saved));
    }

    private SignalRegistry() {
        register(BuiltInSignals.OFF_ASPECT);
        register(BuiltInSignals.RED_ASPECT);
        register(BuiltInSignals.BLINK_RED_ASPECT);
        register(BuiltInSignals.YELLOW_ASPECT);
        register(BuiltInSignals.BLINK_YELLOW_ASPECT);
        register(BuiltInSignals.GREEN_ASPECT);
        register(BuiltInSignals.BLINK_GREEN_ASPECT);
        register(BuiltInSignals.BLUE_ASPECT);
        register(BuiltInSignals.LUNAR_ASPECT);

        register(BuiltInSignals.SINGLE_RED_RULE);
        register(BuiltInSignals.SINGLE_BLINK_RED_RULE);
        register(BuiltInSignals.SINGLE_YELLOW_RULE);
        register(BuiltInSignals.SINGLE_BLINK_YELLOW_RULE);
        register(BuiltInSignals.SINGLE_GREEN_RULE);
        register(BuiltInSignals.SINGLE_BLINK_GREEN_RULE);
        register(BuiltInSignals.SINGLE_BLUE_RULE);
        register(BuiltInSignals.SINGLE_LUNAR_RULE);
    }

    void load() {
        // Classloading
    }
}
