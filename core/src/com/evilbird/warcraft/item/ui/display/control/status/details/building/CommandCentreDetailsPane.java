/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.ui.display.control.status.details.building;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.evilbird.engine.common.control.LabelProperty;
import com.evilbird.engine.item.specialized.Grid;
import com.evilbird.warcraft.item.common.resource.ResourceType;
import com.evilbird.warcraft.item.data.player.Player;
import com.evilbird.warcraft.item.ui.display.control.status.details.DetailsPaneStyle;
import com.evilbird.warcraft.item.unit.building.Building;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Instances of this user interface show details about a Town Hall, including
 * the resources that the owning player has accumulated.
 *
 * @author Blair Butterworth
 */
public class CommandCentreDetailsPane extends Grid
{
    private Player player;
    private LabelProperty gold;
    private LabelProperty lumber;
    private LabelProperty oil;
    private DetailsPaneStyle style;

    public CommandCentreDetailsPane(Skin skin) {
        super(1, 4);

        setSkin(skin);
        setSize(160, 100);
        setCellSpacing(4);
        setCellWidth(160);
        setCellHeight(12);

        addLabel(style.strings.getProduction(), skin);
        gold = addLabel(skin, this::getGoldValue, this::getGoldLabel);
        lumber = addLabel(skin, this::getLumberValue, this::getLumberLabel);
        oil = addLabel(skin, this::getOilValue, this::getOilLabel);
    }

    public void setBuilding(Building building) {
        this.player = (Player)building.getParent();
    }

    @Override
    public void setSkin(Skin skin) {
        super.setSkin(skin);
        this.style = skin.get(DetailsPaneStyle.class);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        gold.evaluate();
        lumber.evaluate();
        oil.evaluate();
    }

    private LabelProperty addLabel(Skin skin, Supplier<Float> value, Function<Float, String> text) {
        Label label = addLabel("", skin);
        return new LabelProperty(label, value, text);
    }

    private Label addLabel(String text, Skin skin) {
        Label result = new Label(text, skin);
        result.setSize(160, 12);
        result.setAlignment(Align.center);
        add(result);
        return result;
    }

    private Float getGoldValue() {
        return player.getResource(ResourceType.Gold);
    }

    private String getGoldLabel(Float value) {
        return style.strings.getGold(value);
    }

    private Float getLumberValue() {
        return player.getResource(ResourceType.Wood);
    }

    private String getLumberLabel(Float value) {
        return style.strings.getWood(value);
    }

    private Float getOilValue() {
        return player.getResource(ResourceType.Oil);
    }

    private String getOilLabel(Float value) {
        return style.strings.getOil(value);
    }
}
