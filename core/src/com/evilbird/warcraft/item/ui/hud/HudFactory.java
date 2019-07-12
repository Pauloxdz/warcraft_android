/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.ui.hud;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.game.GameFactory;
import com.evilbird.engine.item.Item;
import com.evilbird.engine.item.ItemGroup;
import com.evilbird.warcraft.item.ui.hud.control.ControlPaneFactory;
import com.evilbird.warcraft.item.ui.hud.resource.ResourcePaneFactory;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;

/**
 * Creates the graphical user interface shown to the user while the game plays
 * out: the heads up display (HUD).
 *
 * @author Blair Butterworth
 */
public class HudFactory implements GameFactory<Item>
{
    private ControlPaneFactory controlPaneFactory;
    private ResourcePaneFactory resourcePaneFactory;

    @Inject
    public HudFactory(ControlPaneFactory controlPaneFactory, ResourcePaneFactory resourcePaneFactory) {
        this.controlPaneFactory = controlPaneFactory;
        this.resourcePaneFactory = resourcePaneFactory;
    }

    @Override
    public Item get(Identifier identifier) {
        Validate.isInstanceOf(HudType.class, identifier);
        return get((HudType)identifier);
    }

    private Item get(HudType hudType) {
        ItemGroup hud = new ItemGroup();
        hud.setFillParent(true);
        hud.setTouchable(Touchable.childrenOnly);
        hud.addItem(controlPaneFactory.get(HudControl.ResourcePane));
        hud.addItem(resourcePaneFactory.get(HudControl.ControlPane));
        return hud;
    }

    @Override
    public void load(Identifier context) {
        controlPaneFactory.load(context);
        resourcePaneFactory.load(context);
    }

    @Override
    public void unload(Identifier context) {
    }
}
