package com.evilbird.warcraft.behaviour.user;

import com.evilbird.engine.device.UserInput;
import com.evilbird.engine.device.UserInputType;
import com.evilbird.engine.item.Item;
import com.evilbird.warcraft.action.ActionType;
import com.evilbird.warcraft.action.type.GatherAction;
import com.evilbird.warcraft.behaviour.user.interaction.CompositeInteraction;
import com.evilbird.warcraft.behaviour.user.interaction.Interaction;
import com.evilbird.warcraft.behaviour.user.interaction.SelectionInteractionFactory;
import com.evilbird.warcraft.item.unit.UnitType;

import javax.inject.Inject;

/**
 * Instances of this class TODO:Finish
 *
 * @author Blair Butterworth
 */
public class GatherInteraction implements Interaction
{
    private CompositeInteraction interactions;

    @Inject
    public GatherInteraction(SelectionInteractionFactory interactionFactory)
    {
        interactions = new CompositeInteraction();
        interactions.add(interactionFactory.get(UserInputType.Action, UnitType.GoldMine, UnitType.Peasant, null, GatherAction.GatherGold));
        interactions.add(interactionFactory.get(UserInputType.Action, UnitType.Tree, UnitType.Peasant, null, GatherAction.GatherWood));
    }

    @Override
    public boolean update(UserInput input, Item target, Item worldSelection, Item hudSelection)
    {
        return interactions.update(input, target, worldSelection, hudSelection);
    }
}
