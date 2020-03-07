/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.layer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.common.maps.TiledMapFile;
import com.evilbird.engine.common.maps.TiledMapLoader;

/**
 * Instances of this class contain common functions for working with
 * {@link Layer Layers}.
 *
 * @author Blair Butterworth
 */
public class LayerUtils
{
    private LayerUtils() {
    }

    public static TiledMapTileLayer getLayer(LayerIdentifier type) {
        TiledMapTileLayer layer = type.getLayer();
        if (layer == null) {
            TiledMapLoader loader = new TiledMapLoader();
            TiledMapFile map = loader.load(type.getFile());
            MapLayers layers = map.getLayers();
            layer = (TiledMapTileLayer)layers.get(type.getName());
        }
        return layer;
    }

    public static Cell cell(Texture texture) {
        return cell(new TextureRegion(texture));
    }

    public static Cell cell(TextureRegion region) {
        TiledMapTile tile = new StaticTiledMapTile(region);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);
        return cell;
    }

    public static Cell unpaddedCell(Texture texture, int x, int y, int width, int height) {
        int padding = 2;
        int axisPadding = padding + padding;
        int xIndex = x / width;
        int yIndex = y / height;
        int paddedX = x + (xIndex * axisPadding) + padding;
        int paddedY = y + (yIndex * axisPadding) + padding;
        return cell(new TextureRegion(texture, paddedX, paddedY, width, height));
    }

    public static GridPoint2 toCellDimensions(TiledMapTileLayer layer, Vector2 vector) {
        GridPoint2 result = new GridPoint2();
        result.x = vector.x != 0 ? Math.round(vector.x / layer.getTileWidth()) : 0;
        result.y = vector.y != 0 ? Math.round(vector.y / layer.getTileHeight()) : 0;
        return result;
    }

    public static Vector2 toLayerDimensions(TiledMapTileLayer layer, GridPoint2 gridPoint) {
        Vector2 result = new Vector2();
        result.x = gridPoint.x * layer.getTileWidth();
        result.y = gridPoint.y * layer.getTileHeight();
        return result;
    }

    public static int toCellDimensions(TiledMapTileLayer layer, int value) {
        return value != 0 ? Math.round(value / layer.getTileWidth()) : 0;
    }
}
