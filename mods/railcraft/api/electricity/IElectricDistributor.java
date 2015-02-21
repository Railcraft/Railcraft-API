package mods.railcraft.api.electricity;

import java.util.HashSet;
import java.util.Random;

import mods.railcraft.api.core.WorldCoordinate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface IElectricDistributor {

	public ChargeHandler getChargeHandler();
	
	public TileEntity getTile();
	
    public static final int SEARCH_INTERVAL = 32;
    public static final int VALIDATION_INTERVAL = SEARCH_INTERVAL * 2;
    public static final int INVALIDATION_INTERVAL = VALIDATION_INTERVAL  * 2;
    public static final Random rand = new Random();
	
	public static final class ChargeHandler {
		
		private final IElectricDistributor distrObject;
		private final IElectricGrid[] gridsProviding = new IElectricGrid[ForgeDirection.VALID_DIRECTIONS.length];
		private final HashSet<IElectricGrid>[] gridsGiven = new HashSet[ForgeDirection.VALID_DIRECTIONS.length];
		private final HashSet<IElectricGrid> gridsTotal = new HashSet<IElectricGrid>();
		private final double lossPerTick;
		private final boolean isNeutral;
		private double draw, lastTickDraw;
		private int clock = rand.nextInt();
		
		private enum ValidationStatus {INVALID, UNVERIFIED, INDETERMINED, VALID, VERIFIED}
		private ValidationStatus valid = ValidationStatus.INDETERMINED;
		
		public ChargeHandler(IElectricDistributor distrObject, double lossPerTick) {
			this.distrObject = distrObject;
			this.lossPerTick = lossPerTick;
			this.isNeutral = false;
		}
		
		public ChargeHandler(IElectricDistributor distrObject, boolean isNeutral) {
			this.distrObject = distrObject;
			this.lossPerTick = 0.0;
			this.isNeutral = isNeutral;
		}

        public double getLosses() {
        	return lossPerTick;
        }

        public double getDraw() {
            return draw;
        }
        
        public int getNumberOfSources() {
        	return gridsTotal.size();
        }
        
        private double actuallyRemoveCharge(double request) {
        	if(request <= 0)
        		return 0;
        	
        	double totalAvailableEnergy = 0;

        	for(IElectricGrid grid : gridsTotal) {
        		if(grid == null)
        			gridsTotal.remove(grid);
        		else
        			totalAvailableEnergy += grid.getChargeHandler().getCapacity();
        	}
        	
        	if(totalAvailableEnergy == 0)	//To avoid a division by zero
        		return 0;
        	
        	double ret = 0;
        	for(IElectricGrid grid : gridsTotal)
        		ret += grid.getChargeHandler().removeCharge(request * grid.getChargeHandler().getCharge() / totalAvailableEnergy);
        	
        	return ret;
        }
        
        public double removeCharge(double request) {
        	double ret = actuallyRemoveCharge(request);
        	lastTickDraw += ret;
        	return ret;
        }
        
        private void removeLosses() {
        	actuallyRemoveCharge(lossPerTick);
        }
        
        private HashSet<IElectricGrid> getGridsFromSide(ForgeDirection side) {
        	HashSet<IElectricGrid> grids = new HashSet<IElectricGrid>();
        	for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS) {
        		int dir = fd.ordinal();
        		if(fd == side)
        			continue;
        		else if(gridsProviding[dir] != null)
        			grids.add(gridsProviding[dir]);
        		else if(gridsGiven[dir] != null)
        			grids.addAll(gridsGiven[dir]);
        	}
        	
        	return grids;
        }
        
        private void findGrids() {
        	if(isNeutral)
        		return;
        	gridsTotal.clear();
        	
        	WorldCoordinate coord = new WorldCoordinate((TileEntity) distrObject);
			for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS) {
				int dir = fd.ordinal();
				TileEntity te = distrObject.getTile().getWorldObj().getTileEntity(coord.x + fd.offsetX, coord.y + fd.offsetY, coord.z + fd.offsetZ);
				if(te == null) {
					gridsProviding[dir] = null;
					gridsGiven[dir] = null;
					continue;
				} else if(te instanceof IElectricGrid) {
					gridsProviding[fd.ordinal()] = (IElectricGrid) te;
					gridsGiven[dir] = null;
					gridsTotal.add((IElectricGrid) te);
					valid = ValidationStatus.VERIFIED;
					continue;
				} else if(!(te instanceof IElectricDistributor) || ((IElectricDistributor)te).getChargeHandler().isNeutral)
					continue;
				else if(valid == ValidationStatus.INVALID && ((IElectricDistributor) te).getChargeHandler().valid != ValidationStatus.VERIFIED)
					continue;
				
				gridsGiven[dir] = ((IElectricDistributor) te).getChargeHandler().getGridsFromSide(fd.getOpposite());
				gridsTotal.addAll(gridsGiven[dir]);
				if((valid == ValidationStatus.INVALID || valid == ValidationStatus.UNVERIFIED) &&
						((IElectricDistributor) te).getChargeHandler().valid == ValidationStatus.VERIFIED)
					valid = ValidationStatus.VERIFIED;
			}
        }
        
        
        /**
         * Must be called once per tick by the owning object. Server side only.
         */
        public void tick() {
        	clock++;
        	removeLosses();
        	
        	draw = (draw * 49.0 + lastTickDraw) / 50.0;
            lastTickDraw = 0.0;
        	
            if(clock % VALIDATION_INTERVAL == 0){
            	switch(valid) {
            	case VERIFIED:
            		valid = ValidationStatus.VALID;
            		break;
            	case VALID:
            		valid = ValidationStatus.INDETERMINED;
            		break;
            	case INDETERMINED:
            		valid = ValidationStatus.UNVERIFIED;
            		break;
            	case UNVERIFIED:
            		valid = ValidationStatus.INVALID;
            		break;
            	default: break;
            	}
            }
            
            if(clock % INVALIDATION_INTERVAL == 0 && valid == ValidationStatus.INVALID) {
            	for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS)
        			gridsGiven[fd.ordinal()] = null;
        		gridsTotal.clear();
            }
            
            if(clock % SEARCH_INTERVAL == 0)
        		findGrids();
        }
        
        /**
         * Must be called by the owning object's save function.
         *
         * @param nbt
         */
        public void writeToNBT(NBTTagCompound nbt) {
            
        }

        /**
         * Must be called by the owning object's load function.
         *
         * @param nbt
         */
        public void readFromNBT(NBTTagCompound nbt) {
            
        }
		
	}
	
}
