/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.engine.action.framework;

import com.badlogic.gdx.utils.Pool;
import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.ActionException;
import com.evilbird.engine.common.inject.PooledObject;
import com.evilbird.engine.common.lang.GenericIdentifier;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.UserInput;
import com.evilbird.engine.object.GameObject;
import com.evilbird.engine.object.GameObjectComposite;
import com.evilbird.engine.object.GameObjectReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Instances of this class represent a base class for {@link Action Actions},
 * containing common methods and properties utilised by many custom actions.
 *
 * @author Blair Butterworth
 */
public abstract class BasicAction implements Action, PooledObject<Action>
{
    private Identifier identifier;
    private ActionException error;
    private UserInput cause;
    private GameObjectReference<GameObject> item;
    private GameObjectReference<GameObject> target;
    private transient Pool<Action> pool;

    public BasicAction() {
    }

    @Override
    public void free() {
        Pool<Action> pool = getPool();
        if (pool != null) {
            pool.free(this);
        }
    }

    @Override
    public UserInput getCause() {
        return cause;
    }

    @Override
    public ActionException getError() {
        return error;
    }

    @Override
    public Identifier getIdentifier() {
        if (identifier == null) {
            return GenericIdentifier.Unknown;
        }
        return identifier;
    }

    @Override
    public Pool<Action> getPool() {
        return pool;
    }

    @Override
    public GameObject getSubject() {
        return item != null ? item.get() : null;
    }

    public GameObjectReference<GameObject> getItemReference() {
        return item;
    }

    @Override
    public GameObject getTarget() {
        return target != null ? target.get() : null;
    }

    public GameObjectReference<GameObject> getTargetReference() {
        return target;
    }

    @Override
    public boolean hasError() {
        return getError() != null;
    }

    public void reset() {
        error = null;
    }

    @Override
    public void restart() {
        error = null;
    }

    @Override
    public void setCause(UserInput cause) {
        this.cause = cause;
    }

    @Override
    public void setPool(Pool<Action> pool) {
        this.pool = pool;
    }

    @Override
    public void setSubject(GameObject gameObject) {
        this.item = gameObject != null ? new GameObjectReference<>(gameObject) : null;
    }

    public void setSubjectReference(GameObjectReference<GameObject> reference) {
        this.item = reference;
    }

    @Override
    public void setTarget(GameObject target) {
        this.target = target != null ? new GameObjectReference<>(target) : null;
    }

    public void setTargetReference(GameObjectReference<GameObject> reference) {
        this.target = reference;
    }

    @Override
    public void setRoot(GameObjectComposite root) {
        if (item != null) {
            item.setParent(root);
        }
        if (target != null) {
            target.setParent(root);
        }
    }

    @Override
    public void setError(ActionException error) {
        this.error = error;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("identifier", identifier)
            .append("error", error)
            .append("cause", cause)
            .append("subject", item)
            .append("target", target)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        BasicAction that = (BasicAction)obj;
        return new EqualsBuilder()
            .append(identifier, that.identifier)
            .append(error, that.error)
            .append(cause, that.cause)
            .append(item, that.item)
            .append(target, that.target)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(identifier)
            .append(error)
            .append(cause)
            .append(item)
            .append(target)
            .toHashCode();
    }
}
