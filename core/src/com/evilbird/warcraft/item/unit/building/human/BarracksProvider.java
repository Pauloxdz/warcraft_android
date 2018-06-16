package com.evilbird.warcraft.item.unit.building.human;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evilbird.engine.action.ActionIdentifier;
import com.evilbird.engine.common.graphics.DirectionalAnimation;
import com.evilbird.engine.common.inject.AssetProvider;
import com.evilbird.engine.common.lang.NamedIdentifier;
import com.evilbird.engine.device.Device;
import com.evilbird.engine.item.Item;
import com.evilbird.engine.item.specialized.animated.AnimationIdentifier;
import com.evilbird.warcraft.action.ActionType;
import com.evilbird.warcraft.action.type.TrainAction;
import com.evilbird.warcraft.item.common.animation.AnimationCollections;
import com.evilbird.warcraft.item.unit.UnitAnimation;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.building.Building;

import java.util.*;

import javax.inject.Inject;

public class BarracksProvider implements AssetProvider<Item>
{
    private AssetManager assets;

    @Inject
    public BarracksProvider(Device device)
    {
        this.assets = device.getAssetStorage().getAssets();
    }

    @Override
    public void load()
    {
        assets.load("data/textures/human/winter/barracks.png", Texture.class);
        assets.load("data/textures/neutral/perennial/construction.png", Texture.class);
        assets.load("data/textures/neutral/winter/destroyed_site.png", Texture.class);
        assets.load("data/textures/neutral/perennial/icons.png", Texture.class);
    }

    @Override
    public Item get()
    {
        Building result = new Building();
        result.setActions(getActions());
        result.setAvailableAnimations(getAnimations());
        result.setAnimation(UnitAnimation.Idle);
        result.setHealth(800.0f);
        result.setHealthMaximum(800.0f);
        result.setIcon(getIcon());
        result.setName("Barracks");
        result.setSelected(false);
        result.setSelectable(true);
        result.setTouchable(Touchable.enabled);
        result.setType(UnitType.Barracks);
        result.setSize(96, 96);
        return result;
    }

    private Collection<ActionIdentifier> getActions()
    {
        Collection<ActionIdentifier> actions = new ArrayList<>();
        actions.add(TrainAction.TrainFootman);
        return actions;
    }

    private Map<AnimationIdentifier, DirectionalAnimation> getAnimations()
    {
        Texture general = assets.get("data/textures/human/winter/barracks.png", Texture.class);
        Texture construction = assets.get("data/textures/neutral/perennial/construction.png", Texture.class);
        Texture destruction = assets.get("data/textures/neutral/winter/destroyed_site.png", Texture.class);
        return AnimationCollections.buildingAnimations(general, construction, destruction, 96, 96);
    }

    private Drawable getIcon()
    {
        Texture iconTexture = assets.get("data/textures/neutral/perennial/icons.png", Texture.class);
        TextureRegion iconRegion = new TextureRegion(iconTexture, 92, 304, 46, 38);
        return new TextureRegionDrawable(iconRegion);
    }
}
