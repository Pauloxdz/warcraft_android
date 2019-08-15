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
import com.evilbird.engine.common.collection.Maps;
import com.evilbird.engine.device.DeviceDisplay;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.building.BuildingFactoryTestCase;

import java.util.Map;

import static com.evilbird.warcraft.item.WarcraftItemConstants.tiles;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitType.Blacksmith;

/**
 * Instances of this unit test validate logic in the {@link BlacksmithFactory} class.
 *
 * @author Blair Butterworth
 */
public class BlacksmithFactoryTest extends BuildingFactoryTestCase<BlacksmithFactory>
{
    @Override
    protected UnitType getBuildType() {
        return Blacksmith;
    }

    @Override
    protected BlacksmithFactory newFactory(DeviceDisplay display, AssetManager assets) {
        return new BlacksmithFactory(assets);
    }

    @Override
    protected Map<String, Object> getProductProperties() {
        return Maps.of(
                "Animation", Idle,
                "Armour", 20,
                "Health", 775.0f,
                "HealthMaximum", 775.0f,
                "selectable", true,
                "selected", false,
                "sight", tiles(3),
                "type", Blacksmith);
    }
}