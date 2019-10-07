/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.building.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.evilbird.engine.common.graphics.AnimationCatalog;
import com.evilbird.warcraft.item.common.production.ProductionTimes;
import com.evilbird.warcraft.item.unit.building.BuildingAssets;

import static com.evilbird.warcraft.item.unit.UnitAnimation.BuildingSite;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Construct;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Death;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static java.util.Objects.requireNonNull;

/**
 * Defines a catalog of animations as laid out in building texture atlas files.
 * Animations are provided for construction, destruction, pre-construction and
 * post-construction.
 *
 * @author Blair Butterworth
 */
public class BuildingAnimations extends AnimationCatalog
{
    public BuildingAnimations(BuildingAssets assets, ProductionTimes production) {
        this(assets.getBaseTexture(),
            assets.getConstructionTexture(),
            assets.getDestructionTexture(),
            assets.getSize(),
            production.buildTime(assets.getType()));
    }

    public BuildingAnimations(
        Texture base,
        Texture construction,
        Texture destruction,
        GridPoint2 size,
        float time)
    {
        super(4);

        requireNonNull(base);
        requireNonNull(construction);
        requireNonNull(destruction);
        requireNonNull(size);

        idle(base, size);
        buildingSite(construction, size);
        construction(base, construction, size, time);
        destruction(destruction, size);
    }

    private void idle(Texture base, GridPoint2 size) {
        animation(Idle)
            .withTexture(base)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(10f)
            .singleDirection()
            .looping();
    }

    private void buildingSite(Texture construction, GridPoint2 size) {
        animation(BuildingSite)
            .withTexture(construction)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(10f)
            .singleDirection()
            .looping();
    }

    private void construction(Texture base, Texture construction, GridPoint2 size, float time) {
        float duration = time / 4f;
        sequence(Construct)
            .element()
                .withTexture(construction)
                .withSequence(0, 2)
                .withSize(size)
                .withInterval(duration)
                .singleDirection()
                .notLooping()
            .element()
                .withTexture(base)
                .withSequence(0, 2)
                .withSize(size)
                .withInterval(duration)
                .singleDirection()
                .notLooping()
                .reversed();
    }

    private void destruction(Texture destruction, GridPoint2 size) {
        animation(Death)
            .withTexture(destruction)
            .withSequence(0, 1)
            .withSize(size)
            .withInterval(10f)
            .singleDirection()
            .notLooping();
    }
}
