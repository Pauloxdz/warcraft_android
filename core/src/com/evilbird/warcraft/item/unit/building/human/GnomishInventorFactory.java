/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.building.human;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.warcraft.item.unit.building.Building;
import com.evilbird.warcraft.item.unit.building.BuildingFactoryBase;

import javax.inject.Inject;

import static com.evilbird.engine.common.lang.TextIdentifier.objectIdentifier;
import static com.evilbird.warcraft.item.WarcraftItemConstants.tiles;
import static com.evilbird.warcraft.item.unit.UnitType.GnomishInventor;

/**
 * Instances of this class create Gnomish Inventors, allowing for the creation
 * of Gnomish Flying Machines.
 *
 * @author Blair Butterworth
 */
public class GnomishInventorFactory extends BuildingFactoryBase
{
    @Inject
    public GnomishInventorFactory(Device device) {
        this(device.getAssetStorage());
    }

    public GnomishInventorFactory(AssetManager manager) {
        super(manager, GnomishInventor);
    }

    @Override
    public Building get(Identifier type) {
        Building result = builder.build();
        result.setDefence(20);
        result.setHealth(500);
        result.setHealthMaximum(500);
        result.setIdentifier(objectIdentifier("GnomishInventor", result));
        result.setName("Gnomish Inventor");
        result.setSight(tiles(3));
        result.setType(GnomishInventor);
        return result;
    }
}
