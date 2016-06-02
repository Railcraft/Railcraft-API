/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/

package mods.railcraft.api.carts;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * Created by CovertJaguar on 6/1/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ICartContentsTextureProvider {
    /**
     * Should return a 6 element array of textures.
     */
    TextureAtlasSprite[] getTextures();
}
