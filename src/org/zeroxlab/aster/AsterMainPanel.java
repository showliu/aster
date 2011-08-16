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
 * Authored by Kan-Ru Chen <kanru@0xlab.org>
 */

package org.zeroxlab.aster;

import org.zeroxlab.aster.AsterCommand;
import org.zeroxlab.aster.AsterCommand.CommandListener;
import org.zeroxlab.wookieerunner.WookieeAPI;
import org.zeroxlab.wookieerunner.WookieeRunner;
import org.zeroxlab.wookieerunner.ScriptRunner;

import com.android.chimpchat.ChimpChat;
import com.android.chimpchat.core.IChimpImage;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import javax.swing.*;
import javax.script.SimpleBindings;

public class AsterMainPanel extends JPanel {

    private AsterWorkspace mWorkspace;
    private JActionList mActionList;

    private ChimpChat mChimpChat;
    private WookieeAPI mImpl;
    private ScriptRunner mScriptRunner;

    private UpdateScreen mUpdateScreen;

    private MyListener mCmdListener;

    public AsterMainPanel() {
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
        mCmdListener = new MyListener();

        setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 3;
        c.weightx = 0;
        c.weighty = 0;
        mActionList = new JActionList();
        JScrollPane scrollPane = new JScrollPane();
        /*
         * TODO: FIXME:
         * Always show the scroll bar, otherwise when the scroll bar
         * was displayed the scrollPane will resize incorretly.
         */
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setView(mActionList);
        add(scrollPane, c);
        mWorkspace = new AsterWorkspace();
        mActionList.getModel().setRecall(new Touch());
        mActionList.addNewActionListener(new MouseAdapter () {
                public void mouseClicked(MouseEvent e) {
                    AsterCommand cmd = CmdSelector.selectCommand((Component)e.getSource());
                    if (cmd != null) {
                        mActionList.getModel().pushCmd(cmd);
                        mWorkspace.fillCmd(cmd, mCmdListener);
                    }
                }
            });

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.weightx = 0.5;
        c.weighty = 0.5;
        add(mWorkspace, c);
        setPreferredSize(new Dimension(800, 600));

        Map<String, String> options = new TreeMap<String, String>();
        options.put("backend", "adb");
        mChimpChat = ChimpChat.getInstance(options);
        mImpl = new WookieeAPI(mChimpChat.waitForConnection());

        mImpl.setRunnerChimpChat(mChimpChat);
        String wookieeRunnerPath = System.getProperty("com.android.wookieerunner.bindir") +
            File.separator + "wookieerunner";
        mScriptRunner = ScriptRunner.newInstance(null, null, wookieeRunnerPath);
        AsterCommand.setScriptRunner(mScriptRunner);

        mUpdateScreen = new UpdateScreen();
        Thread thread = new Thread(mUpdateScreen);
        thread.start();
    }

    class MyListener implements CommandListener {
        public void commandFinished(AsterCommand whichOne) {
            System.out.println("Complete cmd:" + whichOne.getName());
        }
    }

    class UpdateScreen implements Runnable {

        private boolean mKeepWalking = true;

        public void finish() {
            mKeepWalking = false;
        }

        public void run() {
            while(mKeepWalking) {
                updateScreen();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Update Screen thread is interrupted");
                    e.printStackTrace();
                }
            }
        }

        private void updateScreen() {
            IChimpImage snapshot = mImpl.takeSnapshot();
            mWorkspace.setImage(snapshot.createBufferedImage());
            mWorkspace.repaint(mWorkspace.getBounds());
        }
    }
}
