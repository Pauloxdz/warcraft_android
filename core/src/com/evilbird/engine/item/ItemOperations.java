/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.item;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.common.collection.Collections;
import com.evilbird.engine.common.function.Predicate;
import com.evilbird.engine.common.lang.Alignment;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.common.math.ShapeUtilities;

import java.util.Collection;

import static com.badlogic.gdx.Gdx.graphics;
import static com.evilbird.engine.common.function.Predicates.both;
import static com.evilbird.engine.item.ItemComparators.closestItem;
import static com.evilbird.engine.item.ItemPredicates.itemWithType;
import static com.evilbird.engine.item.ItemPredicates.selectableItem;

/**
 * Instances of this class contain common item lookup utility functions.
 *
 * @author Blair Butterworth
 */
public class ItemOperations
{
    private ItemOperations(){
    }

    public static Item findClosest(ItemComposite itemGroup, Identifier type, Item locus) {
        Predicate<Item> selector = both(itemWithType(type), selectableItem());
        Collection<Item> itemsWithType = itemGroup.findAll(selector);
        if (! itemsWithType.isEmpty()) {
            return Collections.min(itemsWithType, closestItem(locus));
        }
        return null;
    }

    public static Item findAncestor(Item item, Predicate<Item> predicate) {
        Item parent = item.getParent();
        while (parent != null) {
            if (predicate.test(parent)) {
                return parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public static Item findAncestorByType(Item item, Identifier type) {
        return findAncestor(item, itemWithType(type));
    }

    public static Vector2 getScreenCenter(ItemRoot root) {
        float x = graphics.getWidth() * 0.5f;
        float y = graphics.getHeight() * 0.5f;
        Vector2 screenCenter = new Vector2(x, y);
        return root.unproject(screenCenter);
    }
}
