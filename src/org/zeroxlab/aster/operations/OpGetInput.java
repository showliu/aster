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

package org.zeroxlab.aster.operations;

import javax.script.SimpleBindings;
import javax.swing.JOptionPane;

public class OpGetInput implements AsterOperation {

    public final static String sName = "Get Input";
    private String mInput;
    private String tipMsg = "Input string to send to device";

    public OpGetInput(String tipMsg, String defaultValue) {
        this.mInput = new String(defaultValue);
        this.tipMsg = tipMsg;
    }

    @Override
    public String getName() {
        return sName;
    }

    @Override
    public void record(OperationListener listener) {
        String input = JOptionPane.showInputDialog(null, tipMsg, mInput);
        if (input != null) {
            setInput(input);
        }
        listener.operationFinished(this);
    }

    @Override
    public SimpleBindings getSettings() {
        SimpleBindings settings = new SimpleBindings();
        if (!mInput.equals("")) {
            settings.put("Text", mInput);
        }
        return settings;
    }

    public String getInput() {
        return mInput;
    }

    public void setInput(String input) {
        mInput = input;
    }
}
