/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.attack;

import com.evilbird.engine.events.Events;
import com.evilbird.engine.object.GameObject;

import javax.inject.Inject;

import static com.evilbird.warcraft.action.attack.AttackStatus.Cancelled;
import static com.evilbird.warcraft.action.attack.AttackStatus.Complete;
import static com.evilbird.warcraft.action.attack.AttackStatus.Failed;
import static com.evilbird.warcraft.action.attack.AttackStatus.Started;

/**
 * Helper class for generating attack events.
 *
 * @author Blair Butterworth
 */
public class AttackEvents
{
    private transient Events events;

    @Inject
    public AttackEvents(Events events) {
        this.events = events;
    }

    public void attack(GameObject attacker, GameObject target) {
        addEvent(attacker, target, Started);
        addEvent(attacker, target, Complete);
    }

    public void attackStarted(GameObject attacker, GameObject target) {
        addEvent(attacker, target, Started);
    }

    public void attackComplete(GameObject attacker, GameObject target) {
        addEvent(attacker, target, Complete);
    }

    public void attackFailed(GameObject attacker, GameObject target) {
        addEvent(attacker, target, Failed);
    }

    public void attackCancelled(GameObject attacker, GameObject target) {
        addEvent(attacker, target, Cancelled);
    }

    private void addEvent(GameObject attacker, GameObject target, AttackStatus status) {
        events.add(new AttackEvent(attacker, target, status));
    }
}
