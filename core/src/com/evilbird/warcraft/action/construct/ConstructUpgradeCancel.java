/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.construct;

import com.evilbird.engine.action.framework.BasicAction;
import com.evilbird.warcraft.action.common.transfer.ResourceTransfer;
import com.evilbird.warcraft.item.common.production.ProductionCosts;
import com.evilbird.warcraft.item.data.player.Player;
import com.evilbird.warcraft.item.unit.UnitAnimation;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.building.Building;

import javax.inject.Inject;

import static com.evilbird.engine.action.ActionConstants.ActionComplete;
import static com.evilbird.warcraft.item.common.query.UnitOperations.getPlayer;

/**
 * Instances of this class stop the construction of a building upgrade,
 * refunding the cost of producing it.
 *
 * @author Blair Butterworth
 */
public class ConstructUpgradeCancel extends BasicAction
{
    private transient ConstructEvents events;
    private transient ResourceTransfer resources;
    private transient ProductionCosts costs;

    @Inject
    public ConstructUpgradeCancel(
        ConstructEvents events,
        ResourceTransfer resources,
        ProductionCosts costs)
    {
        this.events = events;
        this.resources = resources;
        this.costs = costs;
    }

    @Override
    public boolean act(float delta) {
        Building building = (Building)getItem();
        building.setAnimation(UnitAnimation.Idle);
        building.setProductionProgress(1);

        Player player = getPlayer(building);
        resources.setResources(player, costs.costOf(getProduct()));

        events.notifyConstructCancelled(building);
        return ActionComplete;
    }

    private UnitType getProduct() {
        ConstructActions identifier = (ConstructActions)getIdentifier();
        return identifier.getProduct();
    }
}