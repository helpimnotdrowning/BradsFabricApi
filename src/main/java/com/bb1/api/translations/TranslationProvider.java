package com.bb1.api.translations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb1.api.providers.Provider;

/**
 * Copyright 2021 BradBot_1
 * 
 * Licensed under the Apache License, Version 2.0 (the "License", "");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http;//www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface TranslationProvider extends Provider {
	
	public void registerTranslation(Translation translation);
	
	@Override
	public default Logger getProviderLogger() { return LogManager.getLogger("TranslationManager | "+getProviderName()); }
	
}
