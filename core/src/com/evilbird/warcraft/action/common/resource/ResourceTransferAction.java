/*
 * Blair Butterworth (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.common.resource;

import com.badlogic.gdx.math.MathUtils;
import com.evilbird.engine.action.Action;
import com.evilbird.engine.action.common.ActionTarget;
import com.evilbird.engine.action.framework.BasicAction;
import com.evilbird.engine.action.framework.ParallelAction;
import com.evilbird.warcraft.item.common.resource.ResourceContainer;
import com.evilbird.warcraft.item.common.resource.ResourceIdentifier;
import com.evilbird.warcraft.item.data.DataType;

import static com.evilbird.engine.action.common.ActionTarget.Player;
import static com.evilbird.engine.item.utility.ItemOperations.findAncestorByType;

/**
 * Instances of this action apply a given delta to the resources contained in a
 * given resource container.
 *
 * @author Blair Butterworth
 */
public class ResourceTransferAction extends BasicAction
{
    private float factor;
    private ActionTarget target;
    private ResourceQuantity quantity;
    private ResourceTransferObserver observer;

    private ResourceTransferAction(
        ActionTarget target,
        ResourceQuantity quantity,
        float factor,
        ResourceTransferObserver observer)
    {
        this.target = target;
        this.quantity = quantity;
        this.factor = factor;
        this.observer = observer;
    }

    public static Action purchase(ResourceQuantity amount, ResourceTransferObserver observer) {
        return new ResourceTransferAction(Player, amount, -1, observer);
    }

    public static Action refund(ResourceQuantity amount, float proportion, ResourceTransferObserver observer) {
        return new ResourceTransferAction(Player, amount, proportion, observer);
    }

    public static Action transfer(ActionTarget from, ActionTarget to, ResourceQuantity amount, ResourceTransferObserver observer) {
        Action transferFrom = new ResourceTransferAction(from, amount, -1, observer);
        Action transferTo = new ResourceTransferAction(to, amount, 1, observer);
        return new ParallelAction(transferFrom, transferTo);
    }

    @Override
    public boolean act(float time) {
        ResourceIdentifier resource = quantity.getResource();
        ResourceContainer container = getContainer();

        float delta = quantity.getValue() * factor;
        float oldValue = container.getResource(resource);
        float newValue = MathUtils.clamp(oldValue + delta, 0f, Float.MAX_VALUE);

        container.setResource(resource, newValue);
        notifyObserver(container, resource, newValue);

        return true;
    }

    private ResourceContainer getContainer() {
        switch (target) {
            case Item: return (ResourceContainer)getItem();
            case Target: return (ResourceContainer)getTarget();
            case Parent: return (ResourceContainer)getItem().getParent();
            case Player: return (ResourceContainer)findAncestorByType(getItem(), DataType.Player);
            default: throw new UnsupportedOperationException();
        }
    }

    private void notifyObserver(ResourceContainer container, ResourceIdentifier resource, float value) {
        if (observer != null) {
            observer.onTransfer(container, resource, value);
        }
    }
}
