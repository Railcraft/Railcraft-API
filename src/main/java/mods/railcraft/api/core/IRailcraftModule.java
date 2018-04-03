/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/

package mods.railcraft.api.core;

import javax.annotation.Nonnull;

/**
 * Defines a Railcraft module. Any class implementing this interface and annotated by {@link RailcraftModule}
 * will be loaded as a Module by Railcraft during its initialization phase.
 *
 * Created by CovertJaguar on 4/5/2016.
 */
public interface IRailcraftModule {

    void checkPrerequisites() throws MissingPrerequisiteException;

    @Nonnull
    ModuleEventHandler getModuleEventHandler(boolean enabled);

    class ModuleEventHandler {

        public void construction() {
        }

        public void preInit() {
        }

        public void init() {
        }

        public void postInit() {
        }

//        @SuppressWarnings("UnusedParameters")
//        @SideOnly(Side.CLIENT)
//        GuiScreen getGuiScreen(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
//            return null;
//        }
//
//        @SuppressWarnings("UnusedParameters")
//        Container getGuiContainer(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
//            return null;
//        }
    }

    class MissingPrerequisiteException extends Exception {
        public MissingPrerequisiteException(String msg) {
            super(msg);
        }
    }

}
