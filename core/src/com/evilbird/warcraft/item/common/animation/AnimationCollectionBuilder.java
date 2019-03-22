/*
 * Blair Butterworth (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.common.animation;

import com.badlogic.gdx.graphics.Texture;
import com.evilbird.engine.common.graphics.DirectionalAnimation;
import com.evilbird.engine.common.lang.Identifier;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//TODO: Move into common
public class AnimationCollectionBuilder
{
    private Map<Identifier, Identifier> aliases;
    private Map<Identifier, List<Pair<AnimationSchema, Texture>>> animations;

    public AnimationCollectionBuilder() {
        aliases = new HashMap<>(2);
        animations = new HashMap<>(2);
    }

    public void associate(Identifier sourceId, Identifier targetId) {
        aliases.put(sourceId, targetId);
    }

    public void set(Identifier id, AnimationSchema schema, Texture texture) {
        animations.put(id, Arrays.asList(Pair.of(schema, texture)));
    }

    public void set(Identifier id, List<Pair<AnimationSchema, Texture>> data) {
        animations.put(id, data);
    }

    //TODO: MoveFactory multiple schema/texture logic into AnimationBuilder. Stop animation merging.
    public Map<Identifier, DirectionalAnimation> build() {
        AnimationBuilder builder = new AnimationBuilder();
        Map<Identifier, DirectionalAnimation> result = new HashMap<>();

        for (Entry<Identifier, List<Pair<AnimationSchema, Texture>>> animation : animations.entrySet()) {
            DirectionalAnimation product = null;
            for (Pair<AnimationSchema, Texture> schema : animation.getValue()) {
                builder.setSchema(schema.getKey());
                builder.setTexture(schema.getValue());
                product = product == null ? builder.build() : AnimationUtils.combine(product, builder.build());
            }
            result.put(animation.getKey(), product);
        }
        for (Entry<Identifier, Identifier> alias : aliases.entrySet()) {
            result.put(alias.getKey(), result.get(alias.getValue()));
        }
        return result;
    }
}
