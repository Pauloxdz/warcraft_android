/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.layer.terrain;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.common.maps.MapLayerEntry;
import com.evilbird.engine.common.maps.MapLayerIterable;
import com.evilbird.engine.item.Item;
import com.evilbird.engine.item.spatial.ItemGraph;
import com.evilbird.engine.item.spatial.ItemNode;
import com.evilbird.engine.item.spatial.SpatialObject;
import com.evilbird.warcraft.item.layer.Layer;
import com.evilbird.warcraft.item.layer.LayerAdapter;
import com.evilbird.warcraft.item.unit.Unit;
import com.google.gson.annotations.JsonAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

import static com.evilbird.warcraft.item.layer.LayerUtils.toCellDimensions;

/**
 * Instances of this {@link Layer} represent the ground below which all land
 * based {@link Unit Units} traverse.
 *
 * @author Blair Butterworth
 */
@JsonAdapter(LayerAdapter.class)
public class Terrain extends Layer implements SpatialObject
{
    @Inject
    public Terrain() {
    }

    @Override
    public Collection<ItemNode> getNodes(ItemGraph graph) {
        Collection<ItemNode> nodes = new ArrayList<>(layer.getWidth() * layer.getHeight());
        for (MapLayerEntry entry: new MapLayerIterable(layer)) {
            nodes.add(graph.getNode(entry.getPosition()));
        }
        return nodes;
    }

    @Override
    public Item hit(Vector2 position, boolean touchable) {
        if (touchable && !getTouchable()) { return null; }
        GridPoint2 location = toCellDimensions(layer, position);
        Cell cell = layer.getCell(location.x, location.y);
        return cell != null ? this : null;
    }
}
