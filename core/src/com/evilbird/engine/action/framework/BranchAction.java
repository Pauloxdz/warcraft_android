/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.framework;

import com.evilbird.engine.action.Action;
import com.evilbird.engine.common.function.Predicate;

public class BranchAction extends CompositeAction
{
    private Action delegate;
    private Predicate<Action> decision;

    public BranchAction(Predicate<Action> decision, Action trueAction, Action falseAction) {
        this.decision = decision;
        delegates.add(trueAction);
        delegates.add(falseAction);
    }

    @Override
    public boolean act(float delta) {
        if (delegate == null) {
            delegate = decision.test(this) ? delegates.get(0) : delegates.get(1);
        }
        return delegate.act(delta);
    }

    @Override
    public void restart() {
        super.restart();
        delegate = null;
    }
}
