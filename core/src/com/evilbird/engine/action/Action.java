/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.engine.action;

import com.evilbird.engine.common.inject.PooledObject;
import com.evilbird.engine.common.lang.Identifiable;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.device.UserInput;
import com.evilbird.engine.object.GameObject;
import com.evilbird.engine.object.GameObjectComposite;
import com.google.gson.annotations.JsonAdapter;

/**
 * Instances of this class represent a self contained "bundle" of behaviour
 * that modify the game state is a meaningful manner.
 *
 * @author Blair Butterworth
 */
@JsonAdapter(ActionAdapter.class)
public interface Action extends Identifiable
{
    /**
     * Updates the Action based on time. Typically this is called each frame by
     * {@link GameObject#update(float)}.
     *
     * @param delta the number of seconds since the last invocation.
     * @return      {@code true} if the action is done.
     */
    boolean act(float delta);

    /**
     * Frees any resources held by the action. If the action is a
     * {@link PooledObject} then the action is returned to the pool.
     */
    void free();

    /**
     * Returns the {@link GameObject} that the Action will operate on.
     *
     * @return an {@code GameObject} instance. Will not return {@code null}.
     */
    GameObject getSubject();

    /**
     * Returns an optional target that the Action will manipulate.
     *
     * @return an {@code GameObject} instance. May return {@code null}.
     */
    GameObject getTarget();

    /**
     * Returns the {@link UserInput} event that generated the Action. Not all
     * Actions are generated by user interactions so this method should be
     * considered optional.
     *
     * @return a UserInput instance. May be {@code null}.
     */
    UserInput getCause();

    /**
     * Returns a possible error generated by the execution of the Action.
     *
     * @return  a ActionException instance. May return {@code null} if
     *          execution was successful.
     */
    ActionException getError();

    /**
     * Determines whether or not an error occurred during the execution of the
     * Action.
     *
     * @return {@code true} if an error occurred during Action execution.
     */
    boolean hasError();

    /**
     * Resets the object for reuse. Object references should be set to
     * {@code null} and fields may be set to default values.
     */
    void reset();

    /**
     * Sets the state of the action so that it can be run again.
     */
    void restart();

    /**
     * Sets the {@link GameObject} that the Action will operate on.
     *
     * @param gameObject an Item instance. Cannot be {@code null}.
     */
    void setSubject(GameObject gameObject);

    /**
     * Sets an optional target that the Action will manipulate.
     *
     * @param target an Item instance. May be {@code null}.
     */
    void setTarget(GameObject target);

    /**
     * Sets the {@link GameObjectComposite} at the root of the
     * {@code GameObject} hierarchy. Actions usually maintain references to
     * {@link GameObject GameObjects}, rather than {@code GameObjects}
     * themselves, so this method will apply the parent used to resolve
     * {@code GameObject} references.
     *
     * @param root  an {@code GameObjectComposite} instance. Cannot be
     *              {@code null}.
     */
    void setRoot(GameObjectComposite root);

    /**
     * Sets the {@link UserInput} event that generated the Action.
     */
    void setCause(UserInput input);

    /**
     * Sets an error generated by the execution of the Action.
     */
    void setError(ActionException error);

    /**
     * Set the actions unique identifier.
     */
    void setIdentifier(Identifier identifier);
}
