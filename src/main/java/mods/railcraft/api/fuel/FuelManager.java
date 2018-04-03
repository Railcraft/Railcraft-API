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
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("WeakerAccess")
public class FuelManager {

    public static final Map<Fluid, Integer> boilerFuel = new HashMap<Fluid, Integer>();

    /**
     * Register the amount of heat in a bucket of liquid fuel.
     *
     * @param fluid the fluid
     * @param heatValuePerBucket the amount of "heat" per bucket of fuel
     */
    public static void addBoilerFuel(Fluid fluid, int heatValuePerBucket) {
        ModContainer mod = Loader.instance().activeModContainer();
        String modName = mod != null ? mod.getName() : "An Unknown Mod";
        if (fluid == null) {
            FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, String.format("An error occurred while %s was registering a Boiler fuel source", modName));
            return;
        }
        boilerFuel.put(fluid, heatValuePerBucket);
        FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.DEBUG, String.format("%s registered \"%s\" as a valid Boiler fuel source with %d heat.", modName, fluid.getName(), heatValuePerBucket));
    }

    public static int getBoilerFuelValue(Fluid fluid) {
        Integer value = boilerFuel.get(fluid);
        if(value != null) return value;
        else return 0;
    }

}
