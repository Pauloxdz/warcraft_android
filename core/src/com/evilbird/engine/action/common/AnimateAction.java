/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.common;

import com.evilbird.engine.action.framework.BasicAction;
import com.evilbird.engine.common.lang.Animated;
import com.evilbird.engine.common.lang.Identifier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.inject.Inject;

/**
 * Instances of this class represent an action that changes the animation
 * applied to the given {@link Animated element}.
 *
 * @author Blair Butterworth
 */
public class AnimateAction extends BasicAction
{
    private ActionTarget source;
    private Identifier animation;

    public static AnimateAction animate(Identifier animation) {
        return new AnimateAction(animation);
    }

    @Inject
    public AnimateAction() {
        this(null);
    }

    public AnimateAction(Identifier animation) {
        this(ActionTarget.Item, animation);
    }

    public AnimateAction(ActionTarget source, Identifier animation) {
        this.source = source;
        this.animation = animation;
    }

    public Identifier getAnimation() {
        return animation;
    }

    public void setAnimation(Identifier animation) {
        this.animation = animation;
    }

    @Override
    public boolean act(float delta) {
        Animated animated = getAnimated();
        animated.setAnimation(animation);
        return true;
    }

    private Animated getAnimated() {
        switch (source) {
            case Item: return (Animated)getItem();
            case Target: return (Animated)getTarget();
            default: throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;

        AnimateAction that = (AnimateAction)obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(source, that.source)
            .append(animation, that.animation)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(source)
            .append(animation)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("source", source)
            .append("animation", animation)
            .toString();
    }
}
