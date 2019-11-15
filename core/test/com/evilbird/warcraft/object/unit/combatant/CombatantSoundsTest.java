/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.object.unit.combatant;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.test.testcase.SoundCatalogTestCase;
import com.evilbird.warcraft.object.unit.UnitType;

import java.util.Arrays;
import java.util.Collection;

import static com.evilbird.warcraft.object.unit.UnitSound.Acknowledge;
import static com.evilbird.warcraft.object.unit.UnitSound.Attack;
import static com.evilbird.warcraft.object.unit.UnitSound.Captured;
import static com.evilbird.warcraft.object.unit.UnitSound.Die;
import static com.evilbird.warcraft.object.unit.UnitSound.Ready;
import static com.evilbird.warcraft.object.unit.UnitSound.Rescued;
import static com.evilbird.warcraft.object.unit.UnitSound.Selected;

/**
 * Instances of this unit test validate logic in the {@link CombatantSounds}
 * class.
 *
 * @author Blair Butterworth
 */
public class CombatantSoundsTest extends SoundCatalogTestCase<CombatantSounds, CombatantAssets>
{
    @Override
    protected CombatantAssets newAssets(AssetManager manager) {
        return new CombatantAssets(manager, UnitType.Footman);
    }

    @Override
    protected CombatantSounds newCatalog(CombatantAssets assets) {
        return new CombatantSounds(assets);
    }

    @Override
    protected Collection<Identifier> getSoundIds() {
        return Arrays.asList(Acknowledge, Selected, Attack, Die, Ready, Captured, Rescued);
    }
}