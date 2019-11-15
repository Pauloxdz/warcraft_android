/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.object.unit.combatant;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.evilbird.engine.common.audio.sound.SoundCatalog;
import com.evilbird.engine.common.collection.Maps;
import com.evilbird.engine.common.graphics.AnimationCatalog;
import com.evilbird.engine.common.graphics.FlashingRenderable;
import com.evilbird.engine.common.graphics.SpriteRenderable;
import com.evilbird.engine.object.AnimatedObjectStyle;
import com.evilbird.warcraft.object.unit.UnitAnimation;
import com.evilbird.warcraft.object.unit.UnitStyle;
import com.evilbird.warcraft.object.unit.UnitType;

import java.util.Collections;
import java.util.Map;

/**
 * Creates a new {@link Combatant} whose visual and audible presentation is
 * defined by the given {@link CombatantAssets}.
 *
 * @author Blair Butterworth
 */
public abstract class CombatantBuilder<T extends Combatant>
{
    private UnitType type;
    private CombatantAssets assets;
    private SoundCatalog sounds;
    private AnimationCatalog animations;

    public CombatantBuilder(CombatantAssets assets, UnitType type) {
        this.type = type;
        this.assets = assets;
        this.sounds = null;
        this.animations = null;
    }

    public T build() {
        T result = newCombatant(getSkin());
        result.setAnimation(UnitAnimation.Idle);
        result.setSelected(false);
        result.setSelectable(true);
        result.setTouchable(Touchable.enabled);
        result.setSize(assets.getSize());
        return result;
    }

    protected abstract T newCombatant(Skin skin);

    protected Skin getSkin() {
        UnitStyle style = getStyle();
        Skin skin = new Skin();
        skin.add("default", style, AnimatedObjectStyle.class);
        skin.add("default", style, UnitStyle.class);
        return skin;
    }

    protected UnitStyle getStyle() {
        SoundCatalog sounds = getSounds();
        AnimationCatalog animations = getAnimations();
        return newStyle(animations, sounds);
    }

    protected UnitStyle newStyle(AnimationCatalog animations, SoundCatalog sounds) {
        UnitStyle style = new UnitStyle();
        style.animations = animations.get();
        style.sounds = sounds.get();
        style.selection = new SpriteRenderable(assets.getSelectionTexture());
        style.highlight = new FlashingRenderable(assets.getHighlightTexture());
        style.masks = getMasks();
        return style;
    }

    protected AnimationCatalog getAnimations() {
        if (animations == null) {
            animations = newAnimations();
        }
        return animations;
    }

    protected abstract AnimationCatalog newAnimations();

    private SoundCatalog getSounds() {
        if (sounds == null) {
            sounds = newSounds();
        }
        return sounds;
    }

    protected abstract SoundCatalog newSounds();

    private Map<Texture, Texture> getMasks() {
        if (! type.isNeutral()) {
            return Maps.of(assets.getBaseTexture(), assets.getMaskTexture());
        }
        return Collections.emptyMap();
    }
}
