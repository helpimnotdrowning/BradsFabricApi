package com.bb1.api;

import com.bb1.api.commands.Command;
import com.bb1.api.commands.tab.ITabable;
import com.bb1.api.commands.tab.TabableString;
import com.bb1.api.translations.DefaultTranslations;
import com.bb1.api.translations.TranslationManager;
import com.bb1.api.translations.command.TranslationCommand;

import fr.catcore.server.translations.api.resource.language.TranslationsReloadListener;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
/**
 * Copyright 2021 BradBot_1
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Loader implements DedicatedServerModInitializer, TranslationsReloadListener {
	/**
	 * Formats a {@link TranslatableText} in the format "bradsfabricapi." + <i>key</i>;
	 * 
	 * @param key The key for the text
	 */
	public static TranslatableText getTranslatableText(String key) {
		return new TranslatableText("bradsfabricapi."+key);
	}
	
	private static MinecraftServer minecraftServer;
	
	public static void setMinecraftServer(MinecraftServer server) {
		minecraftServer = server;
	}
	
	public static MinecraftServer getMinecraftServer() {
		return minecraftServer;
	}
	/**
	 * Returns the servers root directory
	 */
	public static String getRootPath() {
		final String path = PlayerManager.OPERATORS_FILE.getAbsolutePath();
		return path.endsWith("/") ? path.substring(0, path.length()-10) : path.substring(0, path.length()-9);
	}
	
	public static ServerPlayerEntity getServerPlayerEntity(ServerCommandSource source) {
		try {
			return source.getPlayer();
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	public void onInitializeServer() {
		DefaultTranslations.register();
		registerCommands();
	}
	
	protected void registerCommands() {
		new TranslationCommand().register();
		
		new Command("test") {
			
			public int execute(ServerCommandSource source, String alias, String[] params) {
				source.sendFeedback(new LiteralText(""+params.length), false);
				return 1;
			}
			
			public ITabable[] getParams() {
				return new ITabable[] {new TabableString("a"), new TabableString("b"), new TabableString("c"), new TabableString("d")};
			};
			
		}.register();
		
		new Command("test2") {
			
			public int execute(ServerCommandSource source, String alias, String[] params) {
				source.sendFeedback(new LiteralText(""+params.length), false);
				return 1;
			}
			
			public ITabable[] getParams() {
				return new ITabable[] {new TabableString("a")};
			};
			
		}.register();
	}
	/**
	 * Called when translations should be reloaded
	 */
	@Override
	public void reload() {
		TranslationManager.get().pushAllTranslations(true); // TODO: idfk
	}

}
