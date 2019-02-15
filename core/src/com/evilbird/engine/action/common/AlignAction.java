/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.common;

import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.framework.BasicAction;
import com.evilbird.engine.common.lang.Alignment;
import com.evilbird.engine.item.Item;

/**
 * Instances of this class represent an {@link Action} that aligns
 * the given {@link Item} with respect to another <code>Item</code>.
 *
 * @author Blair Butterworth
 */
//TODO: handle destination being occupied
public class AlignAction extends BasicAction
{
    private Alignment alignment;

    public AlignAction(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public boolean act(float delta) {
        Item item = getItem();
        Item reference = getTarget();
        Vector2 position = calculatePosition(item, reference, alignment);
        item.setPosition(position);
        return true;
    }

    private Vector2 calculatePosition(Item item, Item reference, Alignment alignment) {
        Vector2 itemSize = item.getSize();
        Vector2 referencePosition = reference.getPosition();

        switch(alignment) {
            case BottomLeft: return new Vector2(referencePosition.x - itemSize.x, referencePosition.y);
            default: throw new UnsupportedOperationException();
        }
    }
}
