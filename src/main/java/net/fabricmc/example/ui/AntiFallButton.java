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

public class AntiFallButton extends PressableWidget {
    // private static final Random RANDOM = Random.create();
    public static String label = "Antifall: ON"; // its on by default

    // constructor
    public AntiFallButton(int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(label));
    }

    // class methods
    public void setName(String toname) {
        label = toname;
    }

    @Override
    public void onPress() {
        // toggle antifall
        if (ModMain.isAntifallEnabled()) {
            ModMain.setANTIFALL(false);
            setName("Antifall: OFF");
            this.setMessage(Text.of("Antifall: OFF"));
            // to reload the screen options
        } else {
            ModMain.setANTIFALL(true);
            setName("Antifall: ON");
            this.setMessage(Text.of("Antifall: ON"));
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder narrationMessageBuilder) {
    }
}