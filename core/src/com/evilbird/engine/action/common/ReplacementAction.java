/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.common;

import com.evilbird.engine.action.framework.Action;
import com.evilbird.engine.action.framework.DelegateAction;
import com.evilbird.engine.common.function.Supplier;
import com.evilbird.engine.item.Item;

import static com.evilbird.engine.common.function.Suppliers.constantValue;

/**
 * Instances of this {@link Action} replace an items actions with a given
 * action when executed.
 *
 * @author Blair Butterworth
 */
//TODO: remove supplier
public class ReplacementAction extends DelegateAction
{
    private Supplier<Item> supplier;

    public ReplacementAction(Action next) {
        this((Item)null, next);
    }

    public ReplacementAction(Item item, Action next) {
        this(constantValue(item), next);
    }

    public ReplacementAction(Supplier<Item> supplier, Action next) {
        super(next);
        this.supplier = supplier;
    }

    @Override
    public boolean act(float delta) {
        Item item = getItem();
        item.clearActions();
        item.addAction(delegate);
        return true;
    }

    public Action getReplacement() {
        return delegate;
    }
}
