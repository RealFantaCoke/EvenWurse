/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.gui.options.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import tk.wurst_client.WurstClient;
import tk.wurst_client.api.Module;
import tk.wurst_client.api.ModuleConfiguration;
import tk.wurst_client.utils.F;
import tk.wurst_client.utils.ModuleUtils;

import java.io.IOException;

public class GuiConfigAdd extends GuiScreen {
    private GuiScreen prevMenu;
    private GuiTextField keyBox;
    private GuiTextField valueBox;
    private Module module;

    public GuiConfigAdd(GuiScreen par1GuiScreen, Module module) {
        prevMenu = par1GuiScreen;
        this.module = module;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
        keyBox.updateCursorCounter();
        ((GuiButton) buttonList.get(0)).enabled = keyBox.getText().trim().length() > 0 && !entryExists();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Add"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 144 + 12, "Cancel"));
        keyBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, 80, 200, 20);
        keyBox.setFocused(true);
        valueBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, 120, 200, 20);
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton clickedButton) {
        if (clickedButton.enabled) {
            if (clickedButton.id == 0) {// Add
                ModuleConfiguration.forModule(module).putString(keyBox.getText(), valueBox.getText());
                WurstClient.INSTANCE.files.saveModuleConfigs();
                mc.displayGuiScreen(prevMenu);
            } else if (clickedButton.id == 1) mc.displayGuiScreen(prevMenu);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            actionPerformed((GuiButton) buttonList.get(1));
            return;
        }
        keyBox.textboxKeyTyped(par1, par2);
        valueBox.textboxKeyTyped(par1, par2);

        if (par2 == 28 || par2 == 156) actionPerformed((GuiButton) buttonList.get(0));
    }

    /**
     * Called when the mouse is clicked.
     *
     * @throws IOException
     */
    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        keyBox.mouseClicked(par1, par2, par3);
        valueBox.mouseClicked(par1, par2, par3);
    }

    boolean entryExists() {
        return ModuleConfiguration.forModule(module).contains(keyBox.getText());
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        drawBackground(0);
        int x = width / 2 - 9;
        int y = height / 2 - 32;
        String existsString;
        if (entryExists()) {
            existsString = "<RED>YES</RED>";
        } else {
            existsString = "<GREEN>NO</GREEN>";
        }
        drawCenteredString(fontRendererObj, F.f("Entry exists: " + existsString), width / 2, y + 48, 10526880);
        drawCenteredString(fontRendererObj, ModuleUtils.getModuleName(module) + ": Add Configuration Entry", width / 2,
                20, 16777215);
        drawString(fontRendererObj, "Key", width / 2 - 100, 67, 10526880);
        keyBox.drawTextBox();
        drawString(fontRendererObj, "Value", width / 2 - 100, 107, 10526880);
        valueBox.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
