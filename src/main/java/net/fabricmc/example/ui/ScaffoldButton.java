package net.fabricmc.example.ui;

import net.fabricmc.example.ModMain;

/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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
 */

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;

public class ScaffoldButton extends PressableWidget {
    public static String label = "Scaffold: ON"; // its on by default

    // constructor
    public ScaffoldButton(int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(label));
    }

    // class methods
    public void setName(String toname) {
        label = toname;
    }

    @Override
    public void onPress() {
        // toggle antifall
        if (ModMain.isScaffoldEnabled()) {
            ModMain.setScaffoldEnabled(false);
            setName("Scaffold: OFF");
            this.setMessage(Text.of("Scaffold: OFF"));
            // to reload the screen options
        } else {
            ModMain.setScaffoldEnabled(true);
            setName("Scaffold: ON");
            this.setMessage(Text.of("Scaffold: ON"));
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder narrationMessageBuilder) {
    }
}