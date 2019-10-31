/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.spell.aoe;

import com.evilbird.engine.item.ItemFactory;
import com.evilbird.warcraft.item.common.spell.Spell;
import com.evilbird.warcraft.item.effect.EffectType;
import com.evilbird.warcraft.item.unit.UnitType;

import javax.inject.Inject;

/**
 * A spell that deals damage to all game objects in a given area as well as
 * stopping those objects from receiving commands.
 *
 * @author Blair Butterworth
 */
public class WhirlwindSpell extends AoeSpellAction
{
    @Inject
    public WhirlwindSpell(ItemFactory factory, AoeSpellCancel cancel) {
        super(Spell.Whirlwind, EffectType.Spell, UnitType.Whirlwind, factory, cancel);
    }
}
