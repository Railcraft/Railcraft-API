Railcraft-API
=============

An API for interacting with Railcraft.

The latest version can be dowloanded from:
https://dl.dropboxusercontent.com/u/38558957/Minecraft/Railcraft/Railcraft_latest-api.zip

The Railcraft API is provided as Open Source with no limitation on use (MIT License - http://choosealicense.com/licenses/mit/).

Package versioning follows the rules laid out by http://semver.org/

When packaging this API with your mod, please include ONLY the classes you need, deleting those you don't.
This helps to reduce conflict if the API changes slighty between versions.

## Ore Dictionary Tags:
- blockSteel
- dustCharcoal
- dustObsidian
- dustSaltpeter
- dustSulfur
- ingotSteel
- oreSaltpeter
- oreSulfur

##InterModComms:
##String:


| ____Message____ | ______________Syntax______________ | Description |
|-----------------|------------------------------------|-------------|
| "ballast"       | "[modid]:[blockName]@[metadata]"   | Registers a block as a valid ballast for the Tunnel Bore and Undercutter.
| "boiler-fuel-liquid"  | "[liquidName]@[fuelValuePerBucket]" | Specifies a fuel value for a bucket of liquid fuel. The liquid name should be the English display name of the liquid (ie. the tag used to lookup the liquid in the LiquidDictionary).

##NBT:
Rock Crusher:

```java
public void addRockCrusherRecipe(ItemStack input, boolean matchMeta, boolean matchNBT, Map<ItemStack, Float> outputs) {
    NBTTagCompound nbt = new NBTTagCompound();

    NBTTagCompound inputNBT = new NBTTagCompound();
    input.writeToNBT(inputNBT);
    nbt.setTag("input", inputNBT);

    nbt.setBoolean("matchMeta", matchMeta);
    nbt.setBoolean("matchNBT", matchNBT);

    int outCount = 0;
    for (Entry<ItemStack, Float> output : outputs.entrySet()) {
        NBTTagCompound outputNBT = new NBTTagCompound();
        output.getKey().writeToNBT(outputNBT);
        outputNBT.setFloat("chance", output.getValue());
        nbt.setTag("output" + outCount++, outputNBT);
    }

    FMLInterModComms.sendMessage("Railcraft", "rock-crusher", nbt);
}
```
