/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.combatant;

import com.evilbird.engine.common.text.CaseUtils;
import com.evilbird.warcraft.item.unit.UnitType;

/**
 * Specifies the path to assets required to display a {@link Combatant}, as well
 * as to play any sound effects used by it.
 *
 * @author Blair Butterworth
 */
public class CombatantAssetManifest
{
    private String base;
    private String icons;
    private String decompose;
    private String acknowledge;
    private String selected;
    private String attack;
    private String hit;
    private String ready;
    private String die;

    public CombatantAssetManifest(UnitType unitType) {
        String faction = getFaction(unitType);
        String name = getName(unitType);
        String type = getAttackType(unitType);
        this.attack = "data/sounds/common/unit/attack/" + type + "/";
        this.hit = "data/sounds/common/unit/hit/" + type + "/";
        this.acknowledge = "data/sounds/" + faction + "/unit/" + name + "/acknowledge/";
        this.selected = "data/sounds/" + faction + "/unit/" + name + "/selected/";
        this.ready = "data/sounds/" + faction + "/unit/" + name + "/ready/";
        this.die = "data/sounds/" + faction + "/unit/common/dead/";
        this.base = "data/textures/" + faction + "/perennial/" + name + ".png";
        this.icons = "data/textures/neutral/perennial/icons.png";
        this.decompose = "data/textures/neutral/perennial/decompose.png";
    }

    public String getBaseTexturePath() {
        return base;
    }

    public String getIconTexturePath() {
        return icons;
    }

    public String getDecomposeTexturePath() {
        return decompose;
    }

    public String getAcknowledgeSoundEffectPath() {
        return acknowledge;
    }

    public String getSelectedSoundEffectPath() {
        return selected;
    }

    public String getAttackSoundEffectPath() {
        return attack;
    }

    public String getHitSoundEffectPath() {
        return hit;
    }

    public String getReadySoundEffectPath() {
        return ready;
    }

    public String getDieSoundEffectPath() {
        return die;
    }

    private String getFaction(UnitType unitType) {
        return CaseUtils.toSnakeCase(unitType.getFaction().name());
    }

    private String getName(UnitType unitType) {
        return CaseUtils.toSnakeCase(unitType.name());
    }

    private String getAttackType(UnitType unitType) {
        switch (unitType) {
            case TrollAxethrower: return "axe";
            case ElvenArcher: return "bow";
            case ElvenDestroyer:
            case TrollDestroyer: return "siege";
            default: return "sword";
        }
    }
}
