/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.layer.fog;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.evilbird.engine.common.collection.BitMatrix;
import com.evilbird.engine.common.error.UnknownEntityException;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.Device;
import com.evilbird.engine.events.EventQueue;
import com.evilbird.engine.game.GameContext;
import com.evilbird.engine.game.GameFactory;
import com.evilbird.warcraft.object.layer.LayerGroupStyle;
import com.evilbird.warcraft.object.layer.LayerIdentifier;
import com.evilbird.warcraft.object.layer.LayerType;
import com.evilbird.warcraft.object.layer.LayerUtils;
import com.evilbird.warcraft.state.WarcraftContext;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.evilbird.engine.common.collection.BitMatrix.matrix3;
import static com.evilbird.warcraft.object.layer.LayerUtils.cell;
import static com.evilbird.warcraft.object.layer.LayerUtils.unpaddedCell;

/**
 * Instances of this factory create {@link Fog} instances.
 *
 * @author Blair Butterworth
 */
public class FogFactory implements GameFactory<Fog>
{
    private EventQueue events;
    private AssetManager manager;
    private FogAssets assets;

    @Inject
    public FogFactory(Device device, EventQueue events) {
        this(device.getAssetStorage(), events);
    }

    public FogFactory(AssetManager manager, EventQueue events) {
        this.manager = manager;
        this.events = events;
    }

    @Override
    public void load(GameContext context) {
        Validate.isInstanceOf(WarcraftContext.class, context);
        load((WarcraftContext)context);
    }

    private void load(WarcraftContext context) {
        assets = new FogAssets(manager, context);
        assets.load();
    }

    @Override
    public void unload(GameContext context) {
        if (assets != null) {
            assets.unload();
        }
    }

    @Override
    public Fog get(Identifier identifier) {
        Validate.isInstanceOf(LayerIdentifier.class, identifier);
        LayerIdentifier layerIdentifier = (LayerIdentifier)identifier;

        switch (layerIdentifier.getType()) {
            case OpaqueFog: return getOpaqueFog(layerIdentifier);
            case TransparentFog: return getTransparentFog(layerIdentifier);
            default: throw new UnknownEntityException(identifier);
        }
    }

    private Fog getOpaqueFog(LayerIdentifier identifier) {
        Fog fog = new Fog(getSkin(), events);
        fog.setIdentifier(identifier);
        fog.setType(LayerType.OpaqueFog);
        fog.setLayer(LayerUtils.getLayer(identifier));
        fog.setVisible(true);
        fog.setTouchable(Touchable.childrenOnly);
        return fog;
    }

    private Fog getTransparentFog(LayerIdentifier identifier) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Skin getSkin() {
        Skin skin = new Skin();
        skin.add("default", getOpaqueStyle());
        return skin;
    }

    private LayerGroupStyle getOpaqueStyle() {
        LayerGroupStyle result = new LayerGroupStyle();
        result.empty = null;
        result.full = cell(assets.getOpaqueTexture());
        result.patterns = getEdgeStyles(assets.getTerrainTexture());
        return result;
    }

    private LayerGroupStyle getTransparentStyle() {
        LayerGroupStyle result = new LayerGroupStyle();
        result.empty = null;
        result.full = cell(assets.getTransparentTexture());
        result.patterns = getEdgeStyles(assets.getTerrainTexture());
        return result;
    }

    private Map<BitMatrix, Cell> getEdgeStyles(Texture texture) {
        Map<BitMatrix, Cell> styles = new HashMap<>();
        addStraightEdges(styles, texture);
        addInternalCorners(styles, texture);
        addExternalCorners(styles, texture);
        addPeninsulas(styles, texture);
        return styles;
    }

    private void addStraightEdges(Map<BitMatrix, Cell> styles, Texture texture) {
        addLeftEdge(styles, texture);
        addRightEdge(styles, texture);
        addTopEdge(styles, texture);
        addBottomEdge(styles, texture);
    }

    private void addLeftEdge(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 192, 0, 32, 32);
        styles.put(matrix3("0,1,1,0,1,1,0,1,1"), style); //left
        styles.put(matrix3("0,1,1,0,1,1,1,1,1"), style); //top overhang
        styles.put(matrix3("1,1,1,0,1,1,0,1,1"), style); //bottom overhang
    }

    private void addRightEdge(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 128, 0, 32, 32);
        styles.put(matrix3("1,1,0,1,1,0,1,1,0"), style); //right
        styles.put(matrix3("1,1,0,1,1,0,1,1,1"), style); //top overhang
        styles.put(matrix3("1,1,1,1,1,0,1,1,0"), style); //bottom overhang
    }

    private void addTopEdge(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 256, 0, 32, 32);
        styles.put(matrix3("1,1,1,1,1,1,0,0,0"), style); //top
        styles.put(matrix3("1,1,1,1,1,1,1,0,0"), style); //left overhang
        styles.put(matrix3("1,1,1,1,1,1,0,0,1"), style); //right overhang
    }

    private void addBottomEdge(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 64, 0, 32, 32);
        styles.put(matrix3("0,0,0,1,1,1,1,1,1"), style); //bottom
        styles.put(matrix3("1,0,0,1,1,1,1,1,1"), style); //left overhang
        styles.put(matrix3("0,0,1,1,1,1,1,1,1"), style); //right overhang
    }

    private void addInternalCorners(Map<BitMatrix, Cell> styles, Texture texture) {
        styles.put(matrix3("1,1,0,1,1,1,1,1,1"), unpaddedCell(texture, 32, 0, 32, 32)); //bottom right internal
        styles.put(matrix3("0,1,1,1,1,1,1,1,1"), unpaddedCell(texture, 96, 0, 32, 32)); //bottom left internal
        styles.put(matrix3("1,1,1,1,1,1,1,1,0"), unpaddedCell(texture, 224, 0, 32, 32)); //top right internal
        styles.put(matrix3("1,1,1,1,1,1,0,1,1"), unpaddedCell(texture, 288, 0, 32, 32)); //top left internal
    }

    private void addExternalCorners(Map<BitMatrix, Cell> styles, Texture texture) {
        addTopLeftExternalCorner(styles, texture);
        addTopRightExternalCorner(styles, texture);
        addBottomLeftExternalCorner(styles, texture);
        addBottomRightExternalCorner(styles, texture);
    }

    private void addTopLeftExternalCorner(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 416, 0, 32, 32);
        styles.put(matrix3("0,1,1,0,1,1,0,0,0"), style); //top left corner
        styles.put(matrix3("1,1,1,0,1,1,0,0,0"), style); //bottom left overhang
        styles.put(matrix3("0,1,1,0,1,1,0,0,1"), style); //top right overhang
        styles.put(matrix3("1,1,1,0,1,1,0,0,1"), style); //both overhangs
    }

    private void addTopRightExternalCorner(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 384, 0, 32, 32);
        styles.put(matrix3("1,1,0,1,1,0,0,0,0"), style); //top right corner
        styles.put(matrix3("1,1,1,1,1,0,0,0,0"), style); //bottom right overhang
        styles.put(matrix3("1,1,0,1,1,0,1,0,0"), style); //top left overhang
        styles.put(matrix3("1,1,1,1,1,0,1,0,0"), style); //both overhangs
    }

    private void addBottomLeftExternalCorner(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 352, 0, 32, 32);
        styles.put(matrix3("0,0,0,0,1,1,0,1,1"), style); //bottom left corner
        styles.put(matrix3("0,0,0,0,1,1,1,1,1"), style); //top left overhang
        styles.put(matrix3("0,0,1,0,1,1,0,1,1"), style); //bottom right overhang
        styles.put(matrix3("0,0,1,0,1,1,1,1,1"), style); //both overhangs
    }

    private void addBottomRightExternalCorner(Map<BitMatrix, Cell> styles, Texture texture) {
        Cell style = unpaddedCell(texture, 320, 0, 32, 32);
        styles.put(matrix3("0,0,0,1,1,0,1,1,0"), style); //bottom right external
        styles.put(matrix3("0,0,0,1,1,0,1,1,1"), style); //top right overhang
        styles.put(matrix3("1,0,0,1,1,0,1,1,0"), style); //bottom left overhang
        styles.put(matrix3("1,0,0,1,1,0,1,1,1"), style); //both overhangs
    }

    private void addPeninsulas(Map<BitMatrix, Cell> styles, Texture texture) {
        styles.put(matrix3("0,1,0,0,1,0,0,1,0"), unpaddedCell(texture, 160, 0, 32, 32)); //vertical peninsula
        styles.put(matrix3("1,0,0,0,0,0,0,0,1"), unpaddedCell(texture, 448, 0, 32, 32)); //forward-slash peninsula
        styles.put(matrix3("0,0,1,0,0,0,1,0,0"), unpaddedCell(texture, 480, 0, 32, 32)); //back-slash peninsula
    }
}


