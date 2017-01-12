package com.evilbird.warcraft;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.evilbird.warcraft.device.Device;

public class AndroidLauncher extends AndroidApplication
{
	@Override
	protected void onCreate (Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		Device device = new AndroidDevice();
		ApplicationListener delegate = new GameEngine(device);
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		initialize(delegate, configuration);
	}
}
