/*
 * Copyright (C) 2011 0xlab - http://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authored by Julian Chu <walkingice@0xlab.org>
 *             Kan-Ru Chen <kanru@0xlab.org>
 *             Wei-Ning Huang <azhuang@0xlab.org>
 */

package org.zeroxlab.aster.cmds;

import java.io.IOException;

import javax.script.SimpleBindings;

import org.zeroxlab.aster.operations.AsterOperation;
import org.zeroxlab.aster.operations.OpGetInput;

/**
 * Call for sub script
 * 
 * @author liuyq
 * 
 */
public class Recall extends AsterCommand {
    private String mScript;
    private String tipMsg = "Please input the path of the script!";

    public Recall() {
        mScript = new String();
        mOps = new AsterOperation[1];
        mOps[0] = new OpGetInput(tipMsg, "");
    }

    public Recall(String argline)
            throws IllegalArgumentException {
        super.setFilled(true);
        String[] args = splitArgs(argline);

        if (args.length == 1) {
            // recall(script)
            mScript = stripQuote(args[0]);
        } else {
            throw new IllegalArgumentException("Invalid argument line.");
        }
        mOps = new AsterOperation[1];
        mOps[0] = new OpGetInput(tipMsg, "");
    }

    @Override
    public String getName() {
        return "Recall";
    }

    @Override
    public SimpleBindings getSettings() {
        SimpleBindings settings = new SimpleBindings();
        settings.put("Name", "Recall");
        settings.put("Script", mScript);
        return settings;
    }

    @Override
    protected void onFillSettings(SimpleBindings settings) throws IOException {
        if (settings.containsKey("Script")) {
            mScript = (String)settings.get("Script");
        }
    }

    @Override
    public void execute() {
        // ExecutionResult result = new ExecutionResult(true, "");
        // mRunner.runStringLocal("wake()\n");
        // // try {
        // // if (mScript.length() != 0) {
        // // result = (new AsterCommandManager()).runLocal(mScript);
        // // }
        // // } catch(IOException e) {
        // // result.mSuccess = false;
        // // result.mMessage = e.toString();
        // // }
        // return result;

        // super.device.press("KEYCODE_HOME");
    }

    @Override
    public String toScript() {
        return String.format("recall('%s')\n", mScript);
    }

    @Override
    protected String getCommandPrefix() {
        // TODO Auto-generated method stub
        return "recall";
    }
}
