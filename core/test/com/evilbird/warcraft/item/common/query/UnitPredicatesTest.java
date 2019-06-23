/*
 * Copyright (c) 2019, Blair Butterworth
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *        https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.item.common.query;

import com.evilbird.engine.common.lang.Destroyable;
import com.evilbird.engine.item.Item;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Instances of this unit test validate the {@link UnitPredicates} class.
 *
 * @author Blair Butterworth
 */
public class UnitPredicatesTest
{
    @Test
    public void isAlive() {
        Predicate<Item> predicate = UnitPredicates.isAlive();
        Destroyable item = mock(Destroyable.class);

        when(item.getHealth()).thenReturn(40f);
        assertTrue(predicate.test(item));

        when(item.getHealth()).thenReturn(0f);
        assertFalse(predicate.test(item));
    }

    @Test
    public void isAliveNonUnit() {
        Predicate<Item> predicate = UnitPredicates.isAlive();
        Item item = mock(Item.class);
        assertFalse(predicate.test(item));
    }

    @Test
    public void isAliveNull() {
        Predicate<Item> predicate = UnitPredicates.isAlive();
        assertFalse(predicate.test(null));
    }
}