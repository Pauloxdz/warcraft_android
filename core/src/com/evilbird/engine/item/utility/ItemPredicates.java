/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.item.utility;

import com.badlogic.gdx.math.Circle;
import com.evilbird.engine.common.lang.Alignment;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.common.math.ShapeUtilities;
import com.evilbird.engine.item.Item;
import com.evilbird.engine.item.ItemRoot;

import java.util.function.Predicate;

/**
 * Defines commonly used {@link Predicate Predicates} that operate on
 * {@link Item Items}.
 *
 * @author Blair Butterworth
 */
public class ItemPredicates
{
    private ItemPredicates() {
        throw new UnsupportedOperationException();
    }

    public static Predicate<Item> itemWithId(final Identifier id) {
        return (item) -> id.equals(item.getIdentifier());
    }

    public static Predicate<Item> itemWithType(final Identifier type) {
        return (item) -> type.equals(item.getType());
    }

    public static Predicate<Item> withType(final Identifier type) {
        return itemWithType(type);
    }

    public static Predicate<Item> withClazz(final Class<?> type) {
        return (item) -> type.isAssignableFrom(item.getClass());
    }

    public static Predicate<Item> selectedItem() {
        return Item::getSelected;
    }

    public static Predicate<Item> touchableItem() {
        return Item::getTouchable;
    }

    public static Predicate<Item> isNear(Item item, float radius) {
        return new IsNear(item, radius);
    }

    public static class IsNear implements Predicate<Item>
    {
        private Item locus;
        private Circle perimeter;

        private IsNear(Item item, float radius) {
            locus = item;
            perimeter = new Circle(item.getPosition(), radius);
        }

        @Override
        public boolean test(Item target) {
            perimeter.setPosition(locus.getPosition(Alignment.Center));
            return ShapeUtilities.contains(perimeter, target.getBounds());
        }
    }

    public static Predicate<ItemRoot> hasMinimum(Predicate<Item> condition, int count) {
        return (composite) -> composite.findAll(condition).size() >= count;
    }

    public static Predicate<ItemRoot> hasMaximum(Predicate<Item> condition, int count) {
        return (composite) -> composite.findAll(condition).size() <= count;
    }
}
