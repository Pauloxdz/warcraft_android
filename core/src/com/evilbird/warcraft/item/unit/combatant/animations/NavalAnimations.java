/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.combatant.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.evilbird.engine.common.graphics.AnimationCatalog;
import com.evilbird.warcraft.item.unit.combatant.CombatantAssets;

import static com.evilbird.warcraft.item.unit.UnitAnimation.Attack;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Death;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Decompose;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Move;
import static java.util.Objects.requireNonNull;

/**
 * Defines a catalog of animations as laid out in naval unit texture atlas
 * files.
 *
 * @author Blair Butterworth
 */
public class NavalAnimations extends AnimationCatalog
{
    private static final GridPoint2 DECOMPOSE_SIZE = new GridPoint2(72, 72);

    public NavalAnimations(CombatantAssets assets) {
        this(assets.getBaseTexture(), assets.getDecomposeTexture(), assets.getSize());
    }

    public NavalAnimations(Texture base, Texture decompose, GridPoint2 size) {
        super(5);

        requireNonNull(base);
        requireNonNull(decompose);
        requireNonNull(size);

        idle(base, size);
        death(base, size);
        decompose(decompose);
    }

    private void idle(Texture base, GridPoint2 size) {
        alias(Attack, Idle);
        alias(Move, Idle);
        animation(Idle)
            .withTexture(base)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(10f)
            .looping();
    }

    private void death(Texture base, GridPoint2 size) {
        animation(Death)
            .withTexture(base)
            .withSequence(size.y, 2)
            .withSize(size)
            .withInterval(0.5f)
            .notLooping();
    }

    private void decompose(Texture decompose) {
        animation(Decompose)
            .withTexture(decompose)
            .withSequence(432, 1)
            .withSize(DECOMPOSE_SIZE)
            .withInterval(2f)
            .notLooping();
    }
}
