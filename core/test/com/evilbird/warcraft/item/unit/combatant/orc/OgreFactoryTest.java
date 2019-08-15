/*
 * Copyright (c) 2019, Blair Butterworth
 *  
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *  
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.combatant.orc;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.collection.Maps;
import com.evilbird.engine.device.DeviceDisplay;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.combatant.CombatantFactoryTestCase;

import java.util.Map;

import static com.evilbird.warcraft.item.unit.UnitType.Ogre;

/**
 * Instances of this unit test validate logic in the {@link OgreFactory} class.
 *
 * @author Blair Butterworth
 */
public class OgreFactoryTest extends CombatantFactoryTestCase<OgreFactory>
{
    @Override
    protected UnitType getBuildType() {
        return Ogre;
    }

    @Override
    protected OgreFactory newFactory(DeviceDisplay display, AssetManager assets) {
        return new OgreFactory(assets);
    }

    @Override
    protected Map<String, Object> getProductProperties() {
        return Maps.of("type", Ogre);
    }
}
