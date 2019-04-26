/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.construct;

import com.evilbird.engine.action.framework.FeatureAction;
import com.evilbird.engine.item.Item;
import com.evilbird.engine.item.ItemType;
import com.evilbird.warcraft.item.unit.building.Building;

import javax.inject.Inject;
import java.util.function.Consumer;

import static com.evilbird.engine.action.common.ActionRecipient.Target;
import static com.evilbird.engine.action.common.AnimateAction.animate;
import static com.evilbird.engine.action.common.AudibleAction.play;
import static com.evilbird.engine.action.common.DisableAction.disable;
import static com.evilbird.engine.action.common.DisableAction.enable;
import static com.evilbird.engine.action.common.RepeatedAudibleAction.playRepeat;
import static com.evilbird.engine.action.common.VisibleAction.hide;
import static com.evilbird.engine.action.common.VisibleAction.show;
import static com.evilbird.engine.item.utility.ItemSuppliers.ifExists;
import static com.evilbird.warcraft.action.common.create.CreateAction.create;
import static com.evilbird.warcraft.action.common.remove.RemoveAction.remove;
import static com.evilbird.warcraft.action.common.resource.ResourceTransferAction.purchase;
import static com.evilbird.warcraft.action.construct.ConstructAction.construct;
import static com.evilbird.warcraft.action.construct.ConstructEvents.constructCompleted;
import static com.evilbird.warcraft.action.construct.ConstructEvents.constructStarted;
import static com.evilbird.warcraft.action.move.MoveAdjacent.moveAdjacentTarget;
import static com.evilbird.warcraft.action.move.MoveToItemAction.move;
import static com.evilbird.warcraft.action.select.SelectAction.deselect;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.*;
import static com.evilbird.warcraft.item.unit.UnitAnimation.*;
import static com.evilbird.warcraft.item.unit.UnitSound.Build;
import static com.evilbird.warcraft.item.unit.UnitSound.Complete;

/**
 * Instances of this class construct a building.
 *
 * @author Blair Butterworth
 */
public class ConstructSequence extends FeatureAction
{
    private transient ConstructReporter reporter;

    /**
     * Constructs a new instance of this class given a {@link ConstructReporter}
     * used to report events generated by the construction action. Notably item
     * creation, resource transfer and construction events.
     *
     * @param reporter  a {@code ConstructReporter} instance. This parameter
     *                  cannot be {@code null}.
     */
    @Inject
    public ConstructSequence(ConstructReporter reporter) {
        this.reporter = reporter;
        reevaluate();
    }

    @Override
    protected void features() {
        ConstructActions building = (ConstructActions)getIdentifier();
        reposition();
        prepare();
        build(building);
    }

    private void reposition() {
        scenario("reposition builder")
            .withTarget(ifExists(getTarget()))
            .given(isAlive())
            .givenItem(isAlive())
            .whenTarget(isPlaceholder())
            .when(notAdjacent(getTarget()))
            .then(animate(Move))
            .then(move(reporter))
            .then(animate(Idle));
    }

    private void prepare() {
        scenario("hide building placeholder")
            .withTarget(ifExists(getTarget()))
            .whenTarget(isPlaceholder())
            .then(hide(Target));
    }

    private void build(ConstructActions building) {
        ItemType type = building.getItemType();
        scenario("construct building")
            .withTarget(ifExists(getTarget()))
            .whenItem(isAdjacent(getTarget()))
            .whenTarget(isPlaceholder())
            .then(purchase(building, reporter))
            .thenUpdate(remove(Target, reporter))
            .thenUpdate(create(type, properties(), reporter))
            .then(constructStarted(reporter))
            .then(hide(), disable(), deselect(reporter), animate(Target, Construct))
            .then(construct(Target, building), playRepeat(Build, 3, 5))
            .then(show(), enable(), animate(Idle), animate(Target, Idle), play(Complete), moveAdjacentTarget())
            .then(constructCompleted(reporter));
    }

    private Consumer<Item> properties() {
        return (item) -> {
            Building building = (Building)item;
            building.setConstructionProgress(0);
            building.setAnimation(BuildingSite);
            building.setPosition(getTarget().getPosition());
        };
    }
}
