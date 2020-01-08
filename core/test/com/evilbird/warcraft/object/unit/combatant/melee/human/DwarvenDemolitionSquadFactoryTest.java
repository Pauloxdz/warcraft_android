/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.unit.combatant.melee.human;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.collection.Maps;
import com.evilbird.engine.device.DeviceDisplay;
import com.evilbird.warcraft.object.unit.UnitType;
import com.evilbird.warcraft.object.unit.combatant.CombatantFactoryTestCase;

import java.util.Map;

import static com.evilbird.warcraft.object.unit.UnitType.DwarvenDemolitionSquad;

/**
 * Instances of this unit test validate logic in the {@link com.evilbird.warcraft.object.unit.combatant.melee.human.DwarvenDemolitionSquadFactory} class.
 *
 * @author Blair Butterworth
 */
public class DwarvenDemolitionSquadFactoryTest extends CombatantFactoryTestCase<com.evilbird.warcraft.object.unit.combatant.melee.human.DwarvenDemolitionSquadFactory>
{
    @Override
    protected UnitType getBuildType() {
        return DwarvenDemolitionSquad;
    }

    @Override
    protected DwarvenDemolitionSquadFactory newFactory(DeviceDisplay display, AssetManager assets) {
        return new com.evilbird.warcraft.object.unit.combatant.melee.human.DwarvenDemolitionSquadFactory(assets);
    }

    @Override
    protected Map<String, Object> getProductProperties() {
        return Maps.of("type", DwarvenDemolitionSquad);
    }
}
