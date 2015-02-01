package mods.railcraft.api.electricity;

public interface IPantographProvider {

	/**
	 * Can the entity accept power from overhead lines?
	 */
	public boolean canAcceptPower();
	
	/**
	 * How high should the overhead line be?
	 * If something obstructs the clearance, hitBlock() is called.
	 * If the entity successfully drew power, drewPower() is called.
	 * Otherwise, nothing else gets called.
	 */
	public int getClearance();
	
	/**
	 * If the pantograph hit a block while trying to reach the wires.
	 * You should damage the pantograph in this call
	 */
	public void hitBlock();
	
	/**
	 * The pantograph has successfully drawn power from the overhead lines.
	 * You should slightly damage the pantograph (due to friction), except when it's not moving.
	 */
	public void drewPower();
}
