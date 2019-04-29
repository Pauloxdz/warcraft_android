/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.gather;

import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.framework.FeatureAction;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.item.common.resource.ResourceQuantity;

import javax.inject.Inject;

import static com.evilbird.engine.action.common.ActionRecipient.*;
import static com.evilbird.engine.action.common.AnimateAction.animate;
import static com.evilbird.engine.action.common.AnimationAliasAction.setAnimation;
import static com.evilbird.engine.action.common.RepeatedAudibleAction.playRepeat;
import static com.evilbird.engine.action.common.VisibleAction.hide;
import static com.evilbird.engine.action.common.VisibleAction.show;
import static com.evilbird.engine.action.framework.DelayedAction.delay;
import static com.evilbird.engine.item.utility.ItemSuppliers.closest;
import static com.evilbird.warcraft.action.common.resource.ResourceTransferAction.transfer;
import static com.evilbird.warcraft.action.gather.GatherEvents.*;
import static com.evilbird.warcraft.action.move.MoveToItemAction.move;
import static com.evilbird.warcraft.action.select.SelectAction.deselect;
import static com.evilbird.warcraft.item.common.query.UnitPredicates.*;
import static com.evilbird.warcraft.item.common.resource.ResourceType.Wood;
import static com.evilbird.warcraft.item.layer.LayerType.Tree;
import static com.evilbird.warcraft.item.unit.UnitAnimation.GatherWood;
import static com.evilbird.warcraft.item.unit.UnitAnimation.*;
import static com.evilbird.warcraft.item.unit.UnitSound.ChopWood;
import static com.evilbird.warcraft.item.unit.UnitType.TownHall;

/**
 * Instances of this {@link Action} instruct a given {@link Item} to gather wood.
 *
 * @author Blair Butterworth
 */
public class GatherWood extends FeatureAction
{
    private transient GatherReporter reporter;

    @Inject
    public GatherWood(GatherReporter reporter) {
        this.reporter = reporter;
        feature(GatherActions.GatherWood);
        reevaluate();
    }

    @Override
    protected void features() {
        scenario("obtain")
            .given(isAlive())
            .when(noResources(Wood))
            .then(animate(Move))
            .then(move(reporter))
            .then(animate(GatherWood), obtainStarted(reporter, resource()))
            .then(delay(1), playRepeat(ChopWood, 1))
            .then(transfer(Target, Subject, resource(), reporter), obtainComplete(reporter, resource()))
            .then(setAnimation(Move, MoveWood), setAnimation(Idle, IdleWood))
            .then(animate(Idle))
            .withTarget(closest(Tree, getTarget()));

        scenario("deposit")
            .givenItem(isAlive())
            .whenItem(hasResources(Wood))
            .then(animate(Move))
            .then(move(reporter))
            .then(hide(), deselect(reporter), depositStarted(reporter, resource()))
            .then(delay(1))
            .then(transfer(Subject, Player, resource(), reporter), depositComplete(reporter, resource()))
            .then(show(), setAnimation(Move, MoveBasic), setAnimation(Idle, IdleBasic), animate(Idle))
            .withTarget(closest(TownHall, getTarget()));
    }

    private ResourceQuantity resource() {
        return GatherActions.GatherWood;
    }
}
