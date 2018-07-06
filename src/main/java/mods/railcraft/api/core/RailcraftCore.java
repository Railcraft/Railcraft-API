/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import static mods.railcraft.api.core.RailcraftCore.InitStage.LOADING;

/**
 * Created by CovertJaguar on 8/23/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public final class RailcraftCore {
    public enum InitStage {
        LOADING,
        DEPENDENCY_CHECKING,
        CONSTRUCTION,
        PRE_INIT,
        INIT,
        POST_INIT,
        FINISHED
    }

    public static InitStage getInitStage() {
        return initStage;
    }

    public static void setInitStage(String stage) {
        initStage = InitStage.valueOf(stage);
    }

    private static InitStage initStage = LOADING;

    private RailcraftCore() {
    }

}
