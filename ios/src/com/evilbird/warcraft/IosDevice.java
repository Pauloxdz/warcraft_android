package com.evilbird.warcraft;

import com.evilbird.warcraft.device.Device;
import com.evilbird.warcraft.device.DeviceInput;
import com.evilbird.warcraft.device.DeviceStorage;

public class IOSDevice implements Device
{
    private DeviceInput input;
    private DeviceStorage storage;

    public IOSDevice()
    {
        this.input = new IOSInput();
        this.storage = new IOSStorage();
    }

    @Override
    public DeviceInput getDeviceInput()
    {
        return input;
    }

    @Override
    public DeviceStorage getAssetStorage()
    {
        return storage;
    }

    @Override
    public DeviceStorage getInternalStorage()
    {
        return null;
    }
}
