package mods.railcraft.api.crafting;

import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 *
 */
public interface IGenRule extends Predicate<@NotNull Random> {

    /**
     * Returns if this rule permits the generation of the output entry.
     *
     * <p>The returned result of this method should be consistent.</p>
     *
     * @param random The random instance to test against
     * @return True if an output entry can be generated
     */
    @Override
    boolean test(@NotNull Random random);

    /**
     * Returns brief description of the rules for generating this entry.
     */
    List<@NotNull ITextComponent> getToolTip();
}
