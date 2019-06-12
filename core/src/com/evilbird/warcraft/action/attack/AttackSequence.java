/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.attack;

import com.evilbird.engine.action.Action;
import com.evilbird.engine.events.EventQueue;
import com.evilbird.engine.events.Events;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.action.common.scenario.ScenarioSetAction;
import com.evilbird.warcraft.item.unit.UnitAnimation;
import com.evilbird.warcraft.item.unit.combatant.Combatant;

import javax.inject.Inject;

import static com.evilbird.engine.action.common.ActionRecipient.Target;
import static com.evilbird.engine.action.common.AnimateAction.animate;
import static com.evilbird.engine.action.common.AssignAction.assignIfAbsent;
import static com.evilbird.engine.action.common.DirectionAction.reorient;
import static com.evilbird.engine.action.common.RepeatedAudibleAction.playRepeat;
import static com.evilbird.engine.action.predicates.ActionPredicates.whileTarget;
import static com.evilbird.engine.common.function.Predicates.not;
import static com.evilbird.warcraft.action.attack.AttackEvents.attackComplete;
import static com.evilbird.warcraft.action.attack.AttackEvents.attackStarted;
import static com.evilbird.warcraft.action.attack.MeleeAttack.meleeAttack;
import static com.evilbird.warcraft.action.attack.RangedAttack.rangedAttack;
import static com.evilbird.warcraft.action.common.death.DeathAction.kill;
import static com.evilbird.warcraft.action.move.MoveToItemAction.move;
import static com.evilbird.warcraft.action.move.MoveWithinRangeAction.moveWithinRange;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.inRange;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isAlive;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isRanged;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.notInRange;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Move;
import static com.evilbird.warcraft.item.unit.UnitSound.Attack;

/**
 * Instances of this {@link Action} cause a given {@link Item} to attack
 * another, after first moving within attack range.
 *
 * @author Blair Butterworth
 */
public class AttackSequence extends ScenarioSetAction
{
    private transient Events events;

    /**
     * Constructs a new instance of this class given an {@link EventQueue}
     * used to report events generated by the attack action. Notably attack
     * start, completion and cancellation.
     *
     * @param events  a {@code EventQueue} instance. This parameter
     *                  cannot be {@code null}.
     */
    @Inject
    public AttackSequence(EventQueue events) {
        this.events = events;
        reevaluate();
    }

    @Override
    protected void features() {
        Combatant combatant = (Combatant)getItem();
        reposition(combatant);
        engageMelee(combatant);
        engageRanged(combatant);
    }

    private void reposition(Combatant combatant) {
        scenario("Reposition for attack")
            .whenItem(not(isRanged()))
            .whenTarget(isAlive())
            .whenTarget(notInRange(combatant))
            .givenItem(isAlive())
            .givenTarget(isAlive())
            .then(animate(Move))
            .then(move(events))
            .then(animate(Idle));

        scenario("Reposition for attack")
            .whenItem(isRanged())
            .whenTarget(isAlive())
            .whenTarget(notInRange(combatant))
            .givenItem(isAlive())
            .givenTarget(isAlive())
            .then(animate(Move))
            .then(moveWithinRange(events))
            .then(animate(Idle));
    }

    private void engageMelee(Combatant combatant) {
        scenario("Melee attack")
            .whenItem(not(isRanged()))
            .whenTarget(isAlive())
            .whenTarget(inRange(combatant))
            .givenItem(isAlive())
            .givenTarget(inRange(combatant))
            .then(animate(UnitAnimation.Attack))
            .then(attackStarted(events))
            .then(meleeAttack(), playRepeat(Attack, whileTarget(isAlive())))
            .then(attackComplete(events))
            .then(animate(Idle))
            .then(assignIfAbsent(kill(events), Target));
    }

    private void engageRanged(Combatant combatant) {
        scenario("Ranged attack")
            .whenItem(isRanged())
            .whenTarget(isAlive())
            .whenTarget(inRange(combatant))
            .givenItem(isAlive())
            .givenTarget(inRange(combatant))
            .then(reorient())
            .then(attackStarted(events))
            .then(rangedAttack())
            .then(attackComplete(events))
            .then(animate(Idle))
            .then(assignIfAbsent(kill(events), Target));
    }
}
