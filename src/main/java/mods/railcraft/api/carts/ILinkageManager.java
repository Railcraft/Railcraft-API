/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.carts;

import net.minecraft.entity.item.EntityMinecart;

import org.jetbrains.annotations.Nullable;
import java.util.UUID;

/**
 * The LinkageManager contains all the functions needed to link and interact
 * with linked carts.
 * <p/>
 * To obtain an instance of this interface, call CartTools.getLinkageManager().
 * <p/>
 * Each cart can up to two links. They are called Link A and Link B. Some carts
 * will have only Link A, for example the Tunnel Bore.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 * @see CartToolsAPI , ILinkableCart
 */
public interface ILinkageManager {

    /**
     * The default max distance at which carts can be linked, divided by 2.
     */
    float LINKAGE_DISTANCE = 1.25f;
    /**
     * The default distance at which linked carts are maintained, divided by 2.
     */
    float OPTIMAL_DISTANCE = 0.78f;

    boolean setAutoLink(EntityMinecart cart, boolean autoLink);

    boolean hasAutoLink(EntityMinecart cart);

    boolean tryAutoLink(EntityMinecart cart1, EntityMinecart cart2);

    /**
     * Creates a link between two carts, but only if there is nothing preventing
     * such a link.
     *
     * @return True if the link succeeded.
     */
    boolean createLink(EntityMinecart cart1, EntityMinecart cart2);

    boolean hasFreeLink(EntityMinecart cart);

    /**
     * Returns the cart linked to Link A or null if nothing is currently
     * occupying Link A.
     *
     * @param cart The cart for which to get the link
     * @return The linked cart or null
     */
    @Nullable
    EntityMinecart getLinkedCartA(EntityMinecart cart);

    /**
     * Returns the cart linked to Link B or null if nothing is currently
     * occupying Link B.
     *
     * @param cart The cart for which to get the link
     * @return The linked cart or null
     */
    @Nullable
    EntityMinecart getLinkedCartB(EntityMinecart cart);

    /**
     * Returns true if the two carts are linked to each other.
     *
     * @return True if linked
     */
    boolean areLinked(EntityMinecart cart1, EntityMinecart cart2);

    /**
     * Breaks a link between two carts, if any link exists.
     */
    void breakLink(EntityMinecart cart1, EntityMinecart cart2);

    /**
     * Breaks all links the cart has.
     */
    void breakLinks(EntityMinecart cart);

    /**
     * Break only link A.
     */
    void breakLinkA(EntityMinecart cart);

    /**
     * Break only link B.
     */
    void breakLinkB(EntityMinecart cart);

    /**
     * Counts how many carts are in the train.
     *
     * @param cart Any cart in the train
     * @return The number of carts in the train
     */
    @SuppressWarnings("unused")
    int countCartsInTrain(EntityMinecart cart);

    /**
     * Returns an iterator which will iterate over every cart in the provided cart's train.
     *
     * There is no guarantee of order.
     */
    Iterable<EntityMinecart> trainIterator(EntityMinecart cart);

    /**
     * Replaced with WorldServer#getEntityFromUuid.
     */
    @Nullable
    @Deprecated
    EntityMinecart getCartFromUUID(UUID id);

}
