/*
 * Copyright (c) 2020, Blair Butterworth
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.evilbird.warcraft.object.display;

import com.evilbird.engine.object.GameObjectType;

/**
 * Defines options of specifying hud presentation varieties.
 *
 * @author Blair Butterworth
 */
public enum UserInterfaceView implements GameObjectType
{
    ControlBar,
    ResourceBar,
    MapOverlay;
}
