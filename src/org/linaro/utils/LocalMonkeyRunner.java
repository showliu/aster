package org.linaro.utils;

import java.util.ArrayList;


public class LocalMonkeyRunner extends DeviceForAster {
    public LocalMonkeyRunner(String serial) {
        super(serial);
    }

    public String getScreenShotPath(){
        return null;
    }

    @Override
    protected ArrayList<String> getAdbSerialArrayList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void executeAdbShell(String... cmds) {
        // TODO Auto-generated method stub

    }

    @Override
    public void executeAdbCommands(String... cmds) {
        // TODO Auto-generated method stub

    }

}
