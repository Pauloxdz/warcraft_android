/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.produce;

import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.object.GameObject;
import com.evilbird.engine.object.GameObjectFactory;
import com.evilbird.warcraft.action.common.transfer.ResourceTransfer;
import com.evilbird.warcraft.common.WarcraftFaction;
import com.evilbird.warcraft.data.upgrade.Upgrade;
import com.evilbird.warcraft.object.common.production.ProductionCosts;
import com.evilbird.warcraft.object.common.production.ProductionTimes;
import com.evilbird.warcraft.object.data.player.Player;
import com.evilbird.warcraft.object.unit.UnitType;
import com.evilbird.warcraft.object.unit.building.Building;
import com.evilbird.warcraft.object.unit.combatant.Combatant;

import javax.inject.Inject;

import static com.evilbird.engine.object.utility.GameObjectPredicates.hasType;
import static com.evilbird.warcraft.common.WarcraftFaction.Human;
import static com.evilbird.warcraft.data.upgrade.Upgrade.MeleeType1;
import static com.evilbird.warcraft.data.upgrade.Upgrade.RangedType1;
import static com.evilbird.warcraft.object.common.query.UnitOperations.getPlayer;
import static com.evilbird.warcraft.object.unit.UnitType.ElvenArcher;
import static com.evilbird.warcraft.object.unit.UnitType.ElvenRanger;
import static com.evilbird.warcraft.object.unit.UnitType.Knight;
import static com.evilbird.warcraft.object.unit.UnitType.Ogre;
import static com.evilbird.warcraft.object.unit.UnitType.OgreMage;
import static com.evilbird.warcraft.object.unit.UnitType.Paladin;
import static com.evilbird.warcraft.object.unit.UnitType.TrollAxethrower;
import static com.evilbird.warcraft.object.unit.UnitType.TrollBerserker;

/**
 * Instances of this action sequence research an upgrade that converts one type
 * of unit into another.
 *
 * @author Blair Butterworth
 */
public class ProduceUpgradeType extends ProduceUpgrade
{
    private GameObjectFactory objectFactory;

    @Inject
    public ProduceUpgradeType(
        ProduceEvents events,
        ResourceTransfer resources,
        ProductionCosts productionCosts,
        ProductionTimes productionTimes,
        GameObjectFactory objectFactory)
    {
        super(events, resources, productionCosts, productionTimes);
        this.objectFactory = objectFactory;
    }

    @Override
    protected boolean complete() {
        Building building = (Building) getSubject();
        UnitType type = (UnitType)building.getType();
        WarcraftFaction faction = type.getFaction();

        Upgrade upgradeType = getProduct();
        Identifier obsoleteType = getObsoleteType(upgradeType, faction);
        Identifier upgradedType = getUpgradedType(upgradeType, faction);

        Combatant upgrade = (Combatant) objectFactory.get(upgradedType);
        Player player = getPlayer(building);

        for (GameObject obsolete: player.findAll(hasType(obsoleteType))) {
            updateAttributes((Combatant)obsolete, upgrade);
        }
        return super.complete();
    }

    private Identifier getObsoleteType(Upgrade upgrade, WarcraftFaction faction) {
        if (upgrade == MeleeType1) {
            return faction == Human ? Knight : Ogre;
        }
        if (upgrade == RangedType1) {
            return faction == Human ? ElvenArcher : TrollAxethrower;
        }
        throw new UnsupportedOperationException();
    }

    private Identifier getUpgradedType(Upgrade upgrade, WarcraftFaction faction) {
        if (upgrade == MeleeType1) {
            return faction == Human ? Paladin : OgreMage;
        }
        if (upgrade == RangedType1) {
            return faction == Human ? ElvenRanger : TrollBerserker;
        }
        throw new UnsupportedOperationException();
    }

    private void updateAttributes(Combatant obsolete, Combatant upgrade) {
        obsolete.setType(upgrade.getType());
        obsolete.setHealth(upgrade.getHealth());
        obsolete.setHealthMaximum(upgrade.getHealthMaximum());
        obsolete.setSight(upgrade.getSightValue());
    }
 }