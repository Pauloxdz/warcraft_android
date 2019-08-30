/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.construct;

import com.badlogic.gdx.math.Vector2;
import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.framework.ParallelAction;
import com.evilbird.engine.events.EventQueue;
import com.evilbird.engine.events.Events;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.action.common.scenario.ScenarioSetAction;
import com.evilbird.warcraft.item.unit.UnitType;
import com.evilbird.warcraft.item.unit.building.Building;

import javax.inject.Inject;
import java.util.function.Consumer;

import static com.evilbird.engine.action.common.ActionRecipient.Player;
import static com.evilbird.engine.action.common.ActionRecipient.Subject;
import static com.evilbird.engine.action.common.ActionRecipient.Target;
import static com.evilbird.engine.action.common.AnimateAction.animate;
import static com.evilbird.engine.action.common.AudibleAction.play;
import static com.evilbird.engine.action.common.RepeatedAudibleAction.playRepeat;
import static com.evilbird.engine.action.predicates.ActionPredicates.whileTarget;
import static com.evilbird.engine.action.predicates.ActionPredicates.withoutError;
import static com.evilbird.engine.common.function.Predicates.not;
import static com.evilbird.engine.item.utility.ItemPredicates.isNear;
import static com.evilbird.warcraft.action.common.associate.AssociateAction.associate;
import static com.evilbird.warcraft.action.common.associate.AssociateAction.unassociate;
import static com.evilbird.warcraft.action.common.create.CreateAction.create;
import static com.evilbird.warcraft.action.common.exclusion.ExcludeActions.exclude;
import static com.evilbird.warcraft.action.common.exclusion.ExcludeActions.restore;
import static com.evilbird.warcraft.action.common.remove.RemoveAction.remove;
import static com.evilbird.warcraft.action.common.transfer.TransferAction.purchase;
import static com.evilbird.warcraft.action.common.transfer.TransferAction.transferAll;
import static com.evilbird.warcraft.action.construct.ConstructAction.construct;
import static com.evilbird.warcraft.action.construct.ConstructEvents.constructCompleted;
import static com.evilbird.warcraft.action.construct.ConstructEvents.constructStarted;
import static com.evilbird.warcraft.action.move.MoveAdjacent.moveAdjacent;
import static com.evilbird.warcraft.action.move.MoveToItemAction.move;
import static com.evilbird.warcraft.action.select.SelectAction.deselect;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isAlive;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isBuilding;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isConstructing;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.isPlaceholder;
import static com.evilbird.warcraft.item.unit.UnitAnimation.BuildingSite;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Construct;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Idle;
import static com.evilbird.warcraft.item.unit.UnitAnimation.Move;
import static com.evilbird.warcraft.item.unit.UnitCosts.buildTime;
import static com.evilbird.warcraft.item.unit.UnitCosts.cost;
import static com.evilbird.warcraft.item.unit.UnitSound.Build;
import static com.evilbird.warcraft.item.unit.UnitSound.Complete;

/**
 * Instances of this class construct a building.
 *
 * @author Blair Butterworth
 */
public class ConstructSequence extends ScenarioSetAction
{
    private static final int CONSTRUCT_SOUND_INTERVAL = 10;

    private transient Events events;

    /**
     * Constructs a new instance of this class given a {@link EventQueue}
     * used to report events generated by the construction action. Notably item
     * creation, resource transferAll and construction events.
     *
     * @param events  an {@code EventQueue} instance. This parameter
     *                  cannot be {@code null}.
     */
    @Inject
    public ConstructSequence(EventQueue events) {
        this.events = events;
        reevaluate();
    }

    @Override
    protected void features() {
        ConstructActions action = (ConstructActions)getIdentifier();
        features(action.getProduct());
    }

    private void features(UnitType building) {
        repositionBuilder();
        purchaseBuilding(building);
        constructBuilding(building);
    }

    private void repositionBuilder() {
        scenario("Reposition Builder")
            .whenItem(not(isNear(getTarget())))
            .givenItem(isAlive())
            .givenAction(withoutError())
            .then(deselect(events))
            .then(animate(Move))
            .then(move(events))
            .then(animate(Idle))
            .onError(restore());
    }

    private void purchaseBuilding(UnitType building) {
        scenario("Purchase Building")
            .whenTarget(isPlaceholder())
            .then(remove(Target, events), purchase(cost(building), events))
            .thenUpdate(create(building, properties(), events), this)
            .then(associate());
    }

    private void constructBuilding(UnitType building) {
        scenario("Construct Building")
            .whenItem(isNear(getTarget()))
            .whenTarget(isBuilding())
            .whenTarget(isConstructing())
            .givenItem(isAlive())
            .then(constructStarted(events))
            .then(exclude(Subject, events))
            .then(animate(Target, Construct))
            .then(build(building))
            .then(transferAll(Target, Player, events))
            .then(moveAdjacent(Subject, Target))
            .then(restore(Subject), restore(Target))
            .then(unassociate(), play(Complete))
            .then(constructCompleted(events));
    }

    private Action build(UnitType building) {
        ParallelAction action = new ParallelAction();
        action.add(construct(progress(building), buildTime(building)));
        action.add(playRepeat(Build, CONSTRUCT_SOUND_INTERVAL, whileTarget(isConstructing())));
        return action;
    }

    private Consumer<Item> properties() {
        Vector2 position = getTarget().getPosition();
        return (item) -> {
            Building building = (Building)item;
            building.setConstructionProgress(0);
            building.setAnimation(BuildingSite);
            building.setPosition(position);
            building.setVisible(true);
        };
    }

    private float progress(UnitType unit) {
        Item target = getTarget();
        if (target instanceof Building) {
            Building building = (Building)target;
            return building.getConstructionProgress() * buildTime(unit);
        }
        return 0;
    }
}
