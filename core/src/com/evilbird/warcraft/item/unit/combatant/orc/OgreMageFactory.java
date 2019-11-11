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
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.warcraft.item.unit.combatant.Combatant;
import com.evilbird.warcraft.item.unit.combatant.CombatantFactoryBase;
import com.evilbird.warcraft.item.unit.combatant.SpellCaster;

import javax.inject.Inject;

import static com.evilbird.engine.common.lang.TextIdentifier.objectIdentifier;
import static com.evilbird.warcraft.item.common.query.GameObjectUtils.tiles;
import static com.evilbird.warcraft.item.common.capability.MovementCapability.Land;
import static com.evilbird.warcraft.item.unit.UnitType.Ogre;
import static com.evilbird.warcraft.item.unit.UnitType.OgreMage;

/**
 * Instances of this factory create Ogre Mages, an upgraded Orge version.
 *
 * @author Blair Butterworth
 */
public class OgreMageFactory extends CombatantFactoryBase
{
    @Inject
    public OgreMageFactory(Device device) {
        this(device.getAssetStorage());
    }

    public OgreMageFactory(AssetManager manager) {
        super(manager, Ogre, OgreMage);
    }

    @Override
    public Combatant get(Identifier type) {
        SpellCaster result = builder.newSpellCaster();
        result.setAttackSpeed(1);
        result.setArmour(4);
        result.setPiercingDamage(8);
        result.setBasicDamage(12);
        result.setHealth(90);
        result.setHealthMaximum(90);
        result.setIdentifier(objectIdentifier("OgreMage", result));
        result.setMana(100f);
        result.setManaMaximum(255f);
        result.setManaRegeneration(1f);
        result.setMovementSpeed(8 * 13);
        result.setMovementCapability(Land);
        result.setSight(tiles(5));
        result.setType(OgreMage);
        return result;
    }
}