/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.framework;

import com.evilbird.engine.common.function.Predicate;

/**
 * Instances of this {@link Action Action} execute an <code>Action</code>
 * if a given {@link Predicate} evaluates to <code>true</code>. If it evaluates
 * to false a prerequisite <code>Action</code> is executed first.
 *
 * @author Blair Butterworth
 */
public class PrerequisiteAction extends CompositeAction
{
    private Action action;
    private Action requisite;
    private Action current;
    private Predicate<Action> predicate;

    public PrerequisiteAction(Action action, Action requisite, Predicate<Action> predicate) {
        super(action, requisite);
        this.action = action;
        this.requisite = requisite;
        this.predicate = predicate;
        this.current = null;
    }

    @Override
    public boolean act(float delta) {
        if (current == null) {
            current = getInitialAction();
        }
        if (current == requisite) {
            return requisiteAct(delta);
        }
        return actionAct(delta);
    }

    private Action getInitialAction() {
        return predicate.test(action) ? action : requisite;
    }

    private boolean requisiteAct(float delta) {
        if (requisite.act(delta)) {
            requisite.restart();
            current = action;
        }
        return requisite.hasError();
    }

    private boolean actionAct(float delta) {
        if (!predicate.test(action)) {
            action.restart();
            current = requisite;
            return false;
        }
        return action.act(delta);
    }
}
