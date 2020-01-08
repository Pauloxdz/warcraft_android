/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.effect.explosion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.evilbird.engine.common.graphics.animation.AnimationCatalog;

import static com.evilbird.warcraft.object.unit.UnitAnimation.Idle;

/**
 * Defines cannon explosion animations as laid out in cannon explosion texture
 * atlas files.
 *
 * @author Blair Butterworth
 */
public class CannonExplosionAnimations extends AnimationCatalog
{
    private static final GridPoint2 SIZE = new GridPoint2(32, 32);

    public CannonExplosionAnimations(Texture texture) {
        super(1);

        animation(Idle)
            .withTexture(texture)
            .withSequence(0, 4)
            .withSize(SIZE)
            .withInterval(0.15f)
            .singleDirection()
            .looping();
    }
}
