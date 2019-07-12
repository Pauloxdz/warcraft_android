/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.ui.hud.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.engine.game.GameFactory;
import com.evilbird.warcraft.item.unit.UnitFaction;

import javax.inject.Inject;

/**
 * Instances of this factory create {@link ResourcePane ResourcePanes}.
 *
 * @author Blair Butterworth
 */
public class ResourcePaneFactory implements GameFactory<ResourcePane>
{
    private ResourcePaneAssets assets;
    private ResourcePaneBuilder builder;

    @Inject
    public ResourcePaneFactory(Device device) {
        this(device.getAssetStorage());
    }

    public ResourcePaneFactory(AssetManager manager) {
        assets = new ResourcePaneAssets(manager, UnitFaction.Human);
        builder = new ResourcePaneBuilder(assets);
    }

    @Override
    public void load(Identifier context) {
        assets.load();
    }

    @Override
    public void unload(Identifier context) {
    }

    @Override
    public ResourcePane get(Identifier type) {
        return builder.build();
    }

}
