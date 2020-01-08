/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.behaviour.ui.interaction;

import com.evilbird.engine.device.UserInput;
import com.evilbird.engine.device.UserInputType;
import com.evilbird.engine.object.GameObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.inject.Inject;
import java.util.LinkedHashMap;

/**
 * Instances of this class define the different ways the user can interact with
 * the game and the actions that result from these interactions.
 *
 * @author Blair Butterworth
 */
public class Interactions
{
    private InteractionContainer interactions;
    private LinkedHashMap<InteractionQuery, InteractionQueryResult> cache;

    @Inject
    public Interactions(
        InteractionContainer interactions,
        AttackInteractions attackInteractions,
        CameraInteractions cameraInteractions,
        CheatInteractions cheatInteractions,
        GatherInteractions gatherInteractions,
        MenuInteractions menuInteractions,
        SelectionInteractions selectionInteractions,
        MoveInteractions moveInteractions,
        SelectorInteractions selectorInteractions,
        ConstructInteractions constructInteractions,
        ProduceInteractions produceInteractions,
        SpellInteractions spellInteractions,
        TransportInteractions transportInteractions)
    {
        this.interactions = interactions;
        this.interactions.addActions(moveInteractions);
        this.interactions.addActions(spellInteractions);
        this.interactions.addActions(attackInteractions);
        this.interactions.addActions(gatherInteractions);
        this.interactions.addActions(constructInteractions);
        this.interactions.addActions(produceInteractions);
        this.interactions.addActions(transportInteractions);
        this.interactions.addActions(menuInteractions);
        this.interactions.addActions(cameraInteractions);
        this.interactions.addActions(selectorInteractions);
        this.interactions.addActions(selectionInteractions);
        this.interactions.addActions(cheatInteractions);
        this.cache = new LinkedHashMap<>(100);
    }

    /**
     * Returns the first interaction that applies to the given user input, target
     * item and selected item. The specific matching algorithm is contained in
     * the individual {@link Interaction} implementations.
     *
     * @param input     a {@link UserInput} instance specifying the interaction
     *                  the user made with the application
     * @param gameObject      the target of the users interaction.
     * @param selected  a currently selected item.
     *
     * @return the matching {@code Interaction}, if any.
     */
    public Interaction getInteraction(UserInput input, GameObject gameObject, GameObject selected) {
        InteractionQuery query = new InteractionQuery(input, gameObject, selected);
        InteractionQueryResult result = cache.get(query);
        if (result == null) {
            Interaction interaction = interactions.getInteraction(input, gameObject, selected);
            result = new InteractionQueryResult(interaction);
            cacheResult(query, result);
        }
        return result.getValue();
    }

    private void cacheResult(InteractionQuery query, InteractionQueryResult result) {
//        if (cache.size() == 100) {
//            cache.entrySet().iterator().remove();
//        }
//        cache.put(query, result);
    }

    private static class InteractionQueryResult {
        private Interaction value;

        public InteractionQueryResult(Interaction value) {
            this.value = value;
        }

        public Interaction getValue() {
            return value;
        }
    }

    private static class InteractionQuery {
        private UserInputType type;
        private GameObject target;
        private GameObject selected;

        public InteractionQuery(UserInput input, GameObject target, GameObject selected) {
            this.type = input.getType();
            this.target = target;
            this.selected = selected;
        }

        @Override
        public boolean equals(Object object) {
            if (object == null) { return false; }
            if (object == this) { return true; }
            if (object.getClass() != getClass()) { return false; }

            InteractionQuery that = (InteractionQuery)object;
            return new EqualsBuilder()
                .append(type, that.type)
                .append(target, that.target)
                .append(selected, that.selected)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(type)
                .append(target)
                .append(selected)
                .toHashCode();
        }
    }
}
