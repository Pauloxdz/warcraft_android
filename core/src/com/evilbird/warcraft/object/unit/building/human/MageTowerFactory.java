/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.unit.building.human;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.warcraft.object.unit.building.Building;
import com.evilbird.warcraft.object.unit.building.BuildingFactoryBase;

import javax.inject.Inject;

import static com.evilbird.engine.common.lang.TextIdentifier.objectIdentifier;
import static com.evilbird.warcraft.object.common.query.GameObjectUtils.tiles;
import static com.evilbird.warcraft.object.unit.UnitType.MageTower;

/**
 * Instances of this class create Human Mage Towers, allowing for the creation
 * of Mages.
 *
 * @author Blair Butterworth
 */
public class MageTowerFactory extends BuildingFactoryBase
{
    @Inject
    public MageTowerFactory(Device device) {
        this(device.getAssetStorage());
    }

    public MageTowerFactory(AssetManager manager) {
        super(manager, MageTower);
    }

    @Override
    public Building get(Identifier type) {
        Building result = builder.build();
        result.setArmour(20);
        result.setHealth(500);
        result.setHealthMaximum(500);
        result.setIdentifier(objectIdentifier("MageTower", result));
        result.setSight(tiles(3));
        result.setType(MageTower);
        return result;
    }
}
