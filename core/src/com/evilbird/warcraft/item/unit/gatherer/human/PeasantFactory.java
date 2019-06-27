/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.unit.gatherer.human;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.GridPoint2;
import com.evilbird.engine.common.inject.AssetProvider;
import com.evilbird.engine.device.Device;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.item.unit.gatherer.Gatherer;
import com.evilbird.warcraft.item.unit.gatherer.GathererAssets;
import com.evilbird.warcraft.item.unit.gatherer.GathererBuilder;

import javax.inject.Inject;

import static com.evilbird.engine.common.lang.TextIdentifier.objectIdentifier;
import static com.evilbird.warcraft.item.WarcraftItemConstants.tiles;
import static com.evilbird.warcraft.item.common.movement.MovementCapability.Land;
import static com.evilbird.warcraft.item.unit.UnitType.Peasant;

/**
 * Instances of this factory create Peasants, the land based gathering unit
 * available to the human faction.
 *
 * @author Blair Butterworth
 */
public class PeasantFactory implements AssetProvider<Item>
{
    private static final GridPoint2 ICON = new GridPoint2(0, 0);
    private static final GridPoint2 SIZE = new GridPoint2(32, 32);

    private GathererAssets assets;
    private GathererBuilder builder;

    @Inject
    public PeasantFactory(Device device) {
        this(device.getAssetStorage());
    }

    public PeasantFactory(AssetManager manager) {
        this.assets = new GathererAssets(manager, Peasant, ICON, SIZE);
        this.builder = new GathererBuilder(assets);
    }

    @Override
    public void load() {
        assets.load();
    }

    @Override
    public Item get() {
        Gatherer result = builder.build();
        result.setDefence(0);
        result.setDamageMinimum(1);
        result.setDamageMaximum(5);
        result.setHealth(30);
        result.setHealthMaximum(30);
        result.setIdentifier(objectIdentifier("Peasant", result));
        result.setLevel(1);
        result.setMovementSpeed(8 * 10);
        result.setMovementCapability(Land);
        result.setName("Peasant");
        result.setRange(tiles(1));
        result.setSight(tiles(4));
        result.setType(Peasant);
        return result;
    }
}
