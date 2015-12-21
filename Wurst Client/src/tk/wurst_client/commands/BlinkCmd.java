/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import tk.wurst_client.WurstClient;
import tk.wurst_client.mods.BlinkMod;

@Cmd.Info(help = "Enables, disables or cancels Blink.",
	name = "blink",
	syntax = {"[(on|off|cancel)]"})
public class BlinkCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length > 1)
			syntaxError();
		if(args.length == 0)
			WurstClient.INSTANCE.mods.getModByClass(BlinkMod.class).toggle();
		else if(args[0].equalsIgnoreCase("on"))
		{
			WurstClient.INSTANCE.mods.disableModsByClass(BlinkMod.class);
		}else if(args[0].equalsIgnoreCase("off"))
			WurstClient.INSTANCE.mods.getModByClass(BlinkMod.class).setEnabled(false);
		else if(args[0].equalsIgnoreCase("cancel"))
		{
			WurstClient.INSTANCE.mods.disableModsByClass(BlinkMod.class);
		}else
			syntaxError();
	}
}
