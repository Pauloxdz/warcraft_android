/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.predicates;

import com.evilbird.engine.action.Action;

import java.util.function.Predicate;

/**
 * Instances of this class determine if a given {@link Action} is free of
 * errors.
 *
 * @author Blair Butterworth
 */
public class WithoutError implements Predicate<Action>
{
    public WithoutError() {
    }

    @Override
    public boolean test(Action value) {
        return !value.hasError();
    }
}
