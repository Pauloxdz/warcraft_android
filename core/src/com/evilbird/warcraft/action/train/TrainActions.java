/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.train;

import com.evilbird.engine.action.ActionIdentifier;
import com.evilbird.engine.item.ItemType;
import com.evilbird.warcraft.action.common.resource.ResourceQuantity;
import com.evilbird.warcraft.item.common.resource.ResourceIdentifier;
import com.evilbird.warcraft.item.unit.UnitType;

import static com.evilbird.warcraft.item.unit.UnitType.Footman;
import static com.evilbird.warcraft.item.unit.UnitType.Peasant;
import static com.evilbird.warcraft.item.unit.resource.ResourceType.Gold;

/**
 * Defines options of specifying training action varieties.
 *
 * @author Blair Butterworth
 */
public enum TrainActions implements ActionIdentifier, ResourceQuantity
{
    TrainFootman (20f, Footman, Gold, 250f),
    TrainPeasant (20f, Peasant, Gold, 100f),
    TrainFootmanCancel(TrainFootman),
    TrainPeasantCancel(TrainPeasant);

    private float trainTime;
    private UnitType unitType;
    private ResourceIdentifier resource;
    private float amount;

    TrainActions(TrainActions other) {
        this.trainTime = other.trainTime;
        this.unitType = other.unitType;
        this.resource = other.resource;
        this.amount = other.amount;
    }

    TrainActions(float trainTime, UnitType unitType, ResourceIdentifier resource, float amount) {
        this.trainTime = trainTime;
        this.unitType = unitType;
        this.resource = resource;
        this.amount = amount;
    }

    public float getDuration() {
        return trainTime;
    }

    public ItemType getItemType() {
        return unitType;
    }

    @Override
    public ResourceIdentifier getResource() {
        return resource;
    }

    @Override
    public float getValue() {
        return amount;
    }
}
