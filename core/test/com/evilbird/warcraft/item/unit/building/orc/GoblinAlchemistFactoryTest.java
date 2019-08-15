/*
 * Copyright (c) 2019, Blair Butterworth
 *  
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *  
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.building.orc;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.collection.Maps;
import com.evilbird.engine.device.DeviceDisplay;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.building.BuildingFactoryTestCase;

import java.util.Map;

import static com.evilbird.warcraft.item.WarcraftItemConstants.tiles;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitType.GoblinAlchemist;

/**
 * Instances of this unit test validate logic in the {@link GoblinAlchemistFactory} class.
 *
 * @author Blair Butterworth
 */
public class GoblinAlchemistFactoryTest extends BuildingFactoryTestCase<GoblinAlchemistFactory>
{
    @Override
    protected UnitType getBuildType() {
        return GoblinAlchemist;
    }

    @Override
    protected GoblinAlchemistFactory newFactory(DeviceDisplay display, AssetManager assets) {
        return new GoblinAlchemistFactory(assets);
    }

    @Override
    protected Map<String, Object> getProductProperties() {
        return Maps.of(
                "Animation", Idle,
                "Armour", 20,
                "Health", 500.0f,
                "HealthMaximum", 500.0f,
                "selectable", true,
                "selected", false,
                "sight", tiles(3),
                "type", GoblinAlchemist);
    }
}