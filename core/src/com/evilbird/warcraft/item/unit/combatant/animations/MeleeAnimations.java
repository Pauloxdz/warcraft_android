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
 * Defines a catalog of animations as laid out in melee unit texture atlas
 * files. Animations are provided for unit attack, movement, idle, death and
 * decomposition.
 *
 * @author Blair Butterworth
 */
public class MeleeAnimations extends AnimationCatalog
{
    private static final GridPoint2 SIZE = new GridPoint2(72, 72);

    /**
     * Creates a new instance of this class given a {@link CombatantAssets}
     * bundle, containing the {@link CombatantAssets#getBaseTexture() base} and
     * {@link CombatantAssets#getDecomposeTexture()} decomposing} textures
     * required by melee combatant animations. Animations contained in this
     * catalog will use the default melee unit size of 72 x 72.
     *
     * @param assets an asset bundle, which must contain a valid base and
     *               decompose texture.
     *
     * @throws NullPointerException if the given asset bundle is {@code null}.
     */
    public MeleeAnimations(CombatantAssets assets) {
        this(assets.getBaseTexture(), assets.getDecomposeTexture());
    }

    /**
     * Creates a new instance of this class given the base and decomposing
     * {@link Texture textures} required by melee combatant animations.
     * Animations contained in this catalog will use the default melee unit
     * size of 72 x 72.
     *
     * @param base      a texture used for idle, movement, attack and death
     *                  animations.
     * @param decompose a texture used for decompose animation, shown after
     *                  the unit dies.
     *
     * @throws NullPointerException if either of the given textures are
     *                              {@code null}.
     */
    public MeleeAnimations(Texture base, Texture decompose) {
        this(base, decompose, SIZE);
    }

    /**
     * Creates a new instance of this class given the base and decomposing
     * {@link Texture textures} required by melee combatant animations.
     *
     * @param base      a texture used for idle, movement, attack and death
     *                  animations.
     * @param decompose a texture used for decompose animation, shown after
     *                  the unit dies.
     * @param size      the dimensions of the melee unit.
     *
     * @throws NullPointerException if either of the given textures or size
     *                              are {@code null}.
     */
    public MeleeAnimations(Texture base, Texture decompose, GridPoint2 size) {
        super(5);

        requireNonNull(base);
        requireNonNull(decompose);
        requireNonNull(size);

        idle(base, size);
        move(base, size);
        death(base, size);
        decompose(decompose, size);
        attack(base, size);
    }

    private void attack(Texture base, GridPoint2 size) {
        animation(Attack)
            .withTexture(base)
            .withSequence(size.y * 5, 4)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(0.15f)
            .looping();
    }

    private void idle(Texture base, GridPoint2 size) {
        animation(Idle)
            .withTexture(base)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(10f)
            .looping();
    }

    private void move(Texture base, GridPoint2 size) {
        animation(Move)
            .withTexture(base)
            .withSequence(0, 5)
            .withSize(size)
            .withInterval(0.10f)
            .looping();
    }

    private void death(Texture base, GridPoint2 size) {
        animation(Death)
            .withTexture(base)
            .withSequence(size.y * 9, 3)
            .withSize(size)
            .withInterval(0.15f)
            .notLooping();
    }

    private void decompose(Texture decompose, GridPoint2 size) {
        animation(Decompose)
            .withTexture(decompose)
            .withSequence(0, 6)
            .withSize(size)
            .withInterval(2f)
            .notLooping();
    }
}
