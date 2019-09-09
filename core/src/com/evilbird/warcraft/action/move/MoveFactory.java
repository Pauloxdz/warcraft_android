/*
 * Blair Butterworth (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.move;

import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.ActionIdentifier;
import com.evilbird.engine.common.inject.InjectedPool;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.action.ActionProvider;

import javax.inject.Inject;

/**
 * Instances of this class create {@link Action Actions} that instruct a given
 * {@link Item} to move to the given target following a path that conforms to
 * its movement capability.
 *
 * @author Blair Butterworth
 */
public class MoveFactory implements ActionProvider
{
    private InjectedPool<MoveCancel> cancelPool;
    private InjectedPool<MoveToItemSequence> moveItemPool;
    private InjectedPool<MoveToVectorSequence> moveLocationPool;
    private InjectedPool<MoveWithinRangeSequence> moveRangePool;

    @Inject
    public MoveFactory(
        InjectedPool<MoveCancel> cancelPool,
        InjectedPool<MoveToItemSequence> moveItemPool,
        InjectedPool<MoveToVectorSequence> moveLocationPool,
        InjectedPool<MoveWithinRangeSequence> moveRangePool)
    {
        this.cancelPool = cancelPool;
        this.moveItemPool = moveItemPool;
        this.moveLocationPool = moveLocationPool;
        this.moveRangePool = moveRangePool;
    }

    @Override
    public Action get(ActionIdentifier action) {
        switch((MoveActions)action) {
            case MoveToLocation: return moveLocationPool.obtain();
            case MoveToItem: return moveItemPool.obtain();
            case MoveWithRange: return moveRangePool.obtain();
            case MoveCancel: return cancelPool.obtain();
            default: throw new UnsupportedOperationException();
        }
    }
}
