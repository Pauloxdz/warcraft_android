/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.engine.action.common;

import com.evilbird.engine.item.Item;
import com.evilbird.test.data.item.TestItems;
import com.evilbird.test.verifier.EqualityVerifier;
import org.junit.Before;
import org.junit.Test;

/**
 * Instances of this unit test validate the {@link RepositionAction} class.
 *
 * @author Blair Butterworth
 */
public class RepositionActionTest
{
    private RepositionAction action;

    @Before
    public void setup() {
        action = new RepositionAction();
        action.setItem(TestItems.newItem("repositionaction"));
    }

//    @Test
//    public void serializeTest() throws IOException {
//        SerializationVerifier.forClass(RepositionAction.class)
//            .withDeserializedForm(action)
//            .withSerializedResource("/action/common/repositionaction.json")
//            .verify();
//    }

    @Test
    public void equalsTest() {
        EqualityVerifier.forClass(RepositionAction.class)
            .withMockedTransientFields(Item.class)
            .excludeTransientFields()
            .verify();
    }
}