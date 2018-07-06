/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.fuel;

import mods.railcraft.api.core.RailcraftConstantsAPI;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("WeakerAccess")
public final class FluidFuelManager {

    private static final Logger logger = LogManager.getLogger(RailcraftConstantsAPI.MOD_ID);
    public static final Map<Fluid, Integer> boilerFuel = new HashMap<>();

    /**
     * Register the amount of heat in a bucket of liquid fuel.
     *
     * @param fluid              the fluid
     * @param heatValuePerBucket the amount of "heat" per bucket of fuel
     */
    public static void addFuel(Fluid fluid, int heatValuePerBucket) {
        ModContainer mod = Loader.instance().activeModContainer();
        String modName = mod != null ? mod.getModId() : "An Unknown Mod";
        if (fluid == null) {
            logger.log(Level.WARN, "An error occurred while {} was registering a Boiler fuel source", modName);
            return;
        }
        boilerFuel.put(fluid, heatValuePerBucket);
        logger.log(Level.DEBUG, "{} registered \"{}\" as a valid Boiler fuel source with {} heat.", modName, fluid.getName(), heatValuePerBucket);
    }

    public static int getFuelValue(Fluid fluid) {
        return boilerFuel.getOrDefault(fluid, 0);
    }

}
