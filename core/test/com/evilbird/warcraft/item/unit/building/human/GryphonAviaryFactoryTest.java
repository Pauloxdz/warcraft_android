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

import static com.evilbird.warcraft.item.common.query.GameObjectUtils.tiles;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitType.GryphonAviary;

/**
 * Instances of this unit test validate logic in the {@link GnomishInventorFactory} class.
 *
 * @author Blair Butterworth
 */
public class GryphonAviaryFactoryTest extends BuildingFactoryTestCase<GryphonAviaryFactory>
{
    @Override
    protected UnitType getBuildType() {
        return GryphonAviary;
    }

    @Override
    protected GryphonAviaryFactory newFactory(DeviceDisplay display, AssetManager assets) {
        return new GryphonAviaryFactory(assets);
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
                "type", GryphonAviary);
    }
}