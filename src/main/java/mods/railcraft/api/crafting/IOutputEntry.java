package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;

/**
 *
 */
public interface IOutputEntry {

    ItemStack getOutput();

    IGenRule getGenRule();

}
