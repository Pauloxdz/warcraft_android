/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.object.unit.combatant.ranged;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.test.testcase.AnimationCatalogTestCase;
import com.evilbird.warcraft.object.unit.UnitType;
import com.evilbird.warcraft.object.unit.combatant.CombatantAssets;

import java.util.Arrays;
import java.util.Collection;

import static com.evilbird.warcraft.object.unit.UnitAnimation.Attack;
import static com.evilbird.warcraft.object.unit.UnitAnimation.Death;
import static com.evilbird.warcraft.object.unit.UnitAnimation.Decompose;
import static com.evilbird.warcraft.object.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.object.unit.UnitAnimation.Move;

/**
 * Instances of this unit test validate logic in the {@link RangedUnitAnimations}
 * class.
 *
 * @author Blair Butterworth
 */
public class RangedUnitAnimationsTest extends AnimationCatalogTestCase<RangedUnitAnimations, CombatantAssets>
{
    @Override
    protected CombatantAssets newAssets(AssetManager manager) {
        return new CombatantAssets(manager, UnitType.ElvenArcher);
    }

    @Override
    protected RangedUnitAnimations newCatalog(CombatantAssets assets) {
        return new RangedUnitAnimations(assets);
    }

    @Override
    protected Collection<Identifier> getAnimationsIds() {
        return Arrays.asList(Attack, Idle, Move, Death, Decompose);
    }
}