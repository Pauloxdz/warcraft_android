/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.effect.confirmation;

import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.warcraft.object.effect.Effect;
import com.evilbird.warcraft.object.effect.EffectFactoryBase;
import com.evilbird.warcraft.object.effect.EffectType;

import javax.inject.Inject;

import static com.evilbird.engine.common.lang.TextIdentifier.objectIdentifier;

/**
 * A factory for the creation of attack confirmation effects, loading the
 * necessary assets and creating new game objects.
 *
 * @author Blair Butterworth
 */
public class AttackEffectFactory extends EffectFactoryBase
{
    @Inject
    public AttackEffectFactory(Device device) {
        super(device.getAssetStorage(), EffectType.Attack);
    }

    @Override
    public Effect get(Identifier type) {
        Effect result = builder.build();
        result.setType(EffectType.Attack);
        result.setIdentifier(objectIdentifier("Attack", result));
        result.setSize(32, 32);
        return result;
    }
}
