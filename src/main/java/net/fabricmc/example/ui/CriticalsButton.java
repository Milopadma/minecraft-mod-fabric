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

public class CriticalsButton extends PressableWidget {
    // private static final Random RANDOM = Random.create();
    public static String label = "Criticals: ON"; // its on by default

    // constructor
    public CriticalsButton(int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(label));
    }

    // class methods
    public void setName(String toname) {
        label = toname;
    }

    @Override
    public void onPress() {
        // toggle criticals
        if (ModMain.isCriticalsEnabled()) {
            ModMain.setCriticalsEnabled(false);
            setName("Criticals: OFF");
            this.setMessage(Text.of("Criticals: OFF"));
            // to reload the screen options
        } else {
            ModMain.setCriticalsEnabled(true);
            setName("Criticals: ON");
            this.setMessage(Text.of("Criticals: ON"));
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder narrationMessageBuilder) {
    }
}