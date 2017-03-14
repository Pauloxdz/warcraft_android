package com.evilbird.warcraft.item.unit.resource.tree;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.evilbird.engine.common.graphics.DirectionalAnimation;
import com.evilbird.engine.common.inject.AssetProvider;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.warcraft.common.AnimationUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class TreeProvider implements AssetProvider<Tree>
{
    private AssetManager assets;

    @Inject
    public TreeProvider(Device device)
    {
        this.assets = device.getAssetStorage().getAssets();
    }

    @Override
    public void load()
    {
        assets.load("data/textures/neutral/winter/terrain.png", Texture.class);
    }

    @Override
    public Tree get()
    {
        Tree result = new Tree();
        result.setAvailableAnimations(getAnimations());
        result.setAnimation(new Identifier("Idle"));
        result.setIcon(getIcon());
        result.setName("Tree");
        result.setSelected(false);
        result.setSelectable(false);
        result.setTouchable(Touchable.enabled);
        result.setType(new Identifier("Wood"));
        result.setAmount(100f);
        result.setSize(32, 32);
        return result;
    }

    private Map<Identifier, DirectionalAnimation> getAnimations()
    {
        Texture texture = assets.get("data/textures/neutral/winter/terrain.png", Texture.class);
        TextureRegion idleTexture =  new TextureRegion(texture, 7 * 32, 0, 32, 32);
        TextureRegion deathTexture =  new TextureRegion(texture, 15 * 32, 7 * 32, 32, 32);

        DirectionalAnimation idleAnimation = AnimationUtils.getAnimation(idleTexture);
        DirectionalAnimation deathAnimation = AnimationUtils.getAnimation(deathTexture);

        Map<Identifier, DirectionalAnimation> animations = new HashMap<Identifier, DirectionalAnimation>();
        animations.put(new Identifier("Idle"), idleAnimation);
        animations.put(new Identifier("Dead"), deathAnimation);
        animations.put(new Identifier("GatherWood"), idleAnimation);

        return animations;
    }

    private Drawable getIcon()
    {
        return AnimationUtils.getDrawable(assets, "data/textures/neutral/perennial/icons.png", 46, 684, 46, 38);
    }
}
