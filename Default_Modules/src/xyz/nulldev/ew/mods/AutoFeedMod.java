package xyz.nulldev.ew.mods;

import net.minecraft.client.Minecraft;
import tk.wurst_client.WurstClient;
import tk.wurst_client.api.Chat;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.MISC,
        description = "Automatically executes /feed when you are getting hungry.",
        name = "AutoFeed")
public class AutoFeedMod extends Mod implements UpdateListener {

    long lastUpdateMs = 0;

    @Override
    public void onEnable() {
        WurstClient.INSTANCE.events.add(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        long ms = System.currentTimeMillis();
        if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode ||
                Minecraft.getMinecraft().thePlayer.getFoodStats().getFoodLevel() >= 20 || (ms - lastUpdateMs) < 2000) {
            return;
        }
        lastUpdateMs = ms;
        Chat.sendMessage("/feed");
    }

    @Override
    public void onDisable() {
        WurstClient.INSTANCE.events.remove(UpdateListener.class, this);
    }
}