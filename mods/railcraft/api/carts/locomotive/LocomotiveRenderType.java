/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.carts.locomotive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class is used to register new Locomotive Skins with Railcraft.
 *
 * Usage example: LocomotiveRenderType.STEAM_SOLID.registerRenderer(new
 * MyRenderer());
 *
 * Registration must be done in the Client side initialization.
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public enum LocomotiveRenderType {

    STEAM_SOLID("cart.loco.steam.solid"),
    STEAM_MAGIC("cart.loco.steam.magic"),
    ELECTRIC("cart.loco.electric");
    private final Map<String, LocomotiveModelRenderer> renderers = new HashMap<String, LocomotiveModelRenderer>();
    private final String cartTag;

    private LocomotiveRenderType(String cartTag) {
        this.cartTag = cartTag;
    }

    /**
     * This is how you register a new renderer. It can be a model renderer, an
     * obj renderer, or anything else you want. It just needs to extend
     * LocomotiveModelRenderer.
     *
     * @param renderer
     */
    public void registerRenderer(LocomotiveModelRenderer renderer) {
        renderers.put(renderer.getRendererTag(), renderer);
    }

    /**
     * Railcraft calls this method, you don't need to worry about it.
     *
     * @param iconRegister
     */
    public void registerSprites(TextureMap textureMap) {
        Set<LocomotiveModelRenderer> set = new HashSet<LocomotiveModelRenderer>(renderers.values());
        for (LocomotiveModelRenderer renderer : set) {
            renderer.registerItemSprites(textureMap);
        }
    }

    /**
     * Railcraft calls this method, you don't need to worry about it.
     *
     * @param tag
     * @return
     */
    public LocomotiveModelRenderer getRenderer(String tag) {
        LocomotiveModelRenderer renderer = renderers.get(tag);
        if (renderer == null)
            renderer = renderers.get("railcraft:default");
        return renderer;
    }

    /**
     * This function will return a Locomotive item with the skin identifier
     * saved in the NBT. Use it to create a recipe for your skin.
     *
     * @param rendererTag
     * @return
     */
    public ItemStack getItemWithRenderer(String rendererTag) {
        Item item = GameRegistry.findItem("Railcraft", cartTag);
        if (item == null)
            return null;
        ItemStack stack = new ItemStack(item, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("model", rendererTag);
        stack.setTagCompound(nbt);
        return stack;
    }

    /**
     * Railcraft calls this method, you don't need to worry about it.
     *
     * @return
     */
    public Set<String> getRendererTags() {
        return renderers.keySet();
    }

}
