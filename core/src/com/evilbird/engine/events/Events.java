/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.engine.events;

import java.util.Collection;

/**
 * Instances of this interface represent a repository of {@link Event Events},
 * note worthy phenomenon generated by game operations that occur within the
 * last apply cycle.
 *
 * @author Blair Butterworth
 */
public interface Events
{
    /**
     * Stores a new {@link Event} the in the event repository that will be
     * accessible until the next update cycle.
     */
    void add(Event event);

    /**
     * Stores a new {@link Event} the in the event repository that will be
     * accessible after the next update cycle.
     */
    void addDelayed(Event event);

    /**
     * Returns all {@link Event Events} stored in the repository that have the
     * given type.
     */
    <T extends Event> Collection<T> getEvents(Class<T> type);

    boolean hasEvents(Class<? extends Event> type);
}
