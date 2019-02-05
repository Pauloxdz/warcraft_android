/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.evilbird.engine.common.lang.GenericIdentifier;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.test.MockBasicAction;
import com.evilbird.engine.test.MockDelegateAction;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DelegateActionTest
{
    @Test
    public void actorTest() {
        Action underlyingAction = new MockBasicAction();
        DelegateAction delegateAction = new MockDelegateAction(underlyingAction);

        Assert.assertNull(delegateAction.getActor());
        Actor actor = Mockito.mock(Actor.class);

        delegateAction.setActor(actor);
        Assert.assertEquals(actor, underlyingAction.getActor());
        Assert.assertEquals(actor, delegateAction.getActor());
    }

    @Test
    public void targetTest() {
        Action underlyingAction = new MockBasicAction();
        DelegateAction delegateAction = new MockDelegateAction(underlyingAction);

        Assert.assertNull(delegateAction.getTarget());
        Actor target = Mockito.mock(Actor.class);

        delegateAction.setTarget(target);
        Assert.assertEquals(target, underlyingAction.getTarget());
        Assert.assertEquals(target, delegateAction.getTarget());
    }
    
    @Test
    public void identifierTest() {
        Action underlyingAction = new MockBasicAction();
        DelegateAction delegateAction = new MockDelegateAction(underlyingAction);

        Assert.assertEquals(GenericIdentifier.Unknown, delegateAction.getIdentifier());
        Identifier identifier = Mockito.mock(Identifier.class);

        delegateAction.setIdentifier(identifier);
        Assert.assertEquals(identifier, underlyingAction.getIdentifier());
        Assert.assertEquals(identifier, delegateAction.getIdentifier());
    }
    
    @Test
    public void errorTest() {
        Action underlyingAction = new MockBasicAction();
        DelegateAction delegateAction = new MockDelegateAction(underlyingAction);

        Assert.assertNull(delegateAction.getError());
        Assert.assertFalse(delegateAction.hasError());

        Throwable error = new UnknownError();
        underlyingAction.setError(error);

        Assert.assertEquals(error, delegateAction.getError());
        Assert.assertTrue(delegateAction.hasError());
    }
}