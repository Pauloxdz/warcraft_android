/*
 * Blair Butterworth (c) 2019
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package com.evilbird.warcraft.action.attack;

import com.evilbird.engine.object.GameObject;
import com.evilbird.test.data.item.TestItems;
import com.evilbird.warcraft.common.WarcraftPreferences;
import org.junit.Before;
import org.mockito.Mockito;

public class ProximityAttackTest
{
    private AttackDamage damage;
    private AttackEvents events;
    private GameObject gameObject;
    private GameObject target;
    private ProximityAttack action;
    private WarcraftPreferences preferences;

    @Before
    public void setup() {
        gameObject = TestItems.newItem("footman");
        target = TestItems.newItem("grunt");
        preferences = Mockito.mock(WarcraftPreferences.class);

        damage = Mockito.mock(AttackDamage.class);
        events = Mockito.mock(AttackEvents.class);
        action = new ProximityAttack(damage, events, preferences);
        action.setSubject(gameObject);
        action.setTarget(target);
    }

//    @Test
//    public void equalsTest() {
//        EqualityVerifier.forClass(ProximityAttack.class)
//            .withMockedTransientFields(GameObject.class)
//            .withMockedType(Pool.class)
//            .excludeTransientFields()
//            .verify();
//    }
}