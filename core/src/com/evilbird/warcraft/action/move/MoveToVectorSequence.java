/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.move;

import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.action.Action;
import com.evilbird.engine.item.Item;

import javax.inject.Inject;

/**
 * Instances of this {@link Action action} move an {@link Item} from its
 * current location to a given destination, specified as a {@link Vector2}. The
 * moving item will be animated with a movement animation.
 *
 * @author Blair Butterworth
 */
public class MoveToVectorSequence extends MoveSequence
{
    @Inject
    public MoveToVectorSequence(MoveToVectorAction move) {
        super(move);
        setIdentifier(MoveActions.MoveToLocation);
    }
}
