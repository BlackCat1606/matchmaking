/*-
 * #%L
 * matchmaking.server
 * %%
 * Copyright (C) 2016 - 2019 Frederik Kammel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.vatbub.matchmaking.server.roomproviders.data

import com.github.vatbub.matchmaking.common.data.GameData
import kotlin.properties.Delegates

/**
 * This class wraps a [GameData] object and allows other entities to subscribe to changes it.
 * This allows more efficient database interactions.
 * **Important:** The [GameData] is copied within the constructor. Later changes to `fromGameData` will not be reflected by this class.
 * @param fromGameData The [GameData] too copy the data from
 */
class ObservableGameData(
    fromGameData: GameData,
    var onReplace: ((oldGameData: GameData, newGameData: GameData) -> Unit)? = null,
    var onSet: ((key: String, oldValue: Any?, newValue: Any) -> Unit)? = null,
    val onRemove: ((key: String, element: Any?) -> Unit)? = null
) {
    val size: Int
        get() = backingGameData.size

    var backingGameData: GameData by Delegates.observable(fromGameData.copy()) { _, oldValue, newValue ->
        onReplace?.invoke(
            oldValue,
            newValue
        )
    }

    operator fun <T : Any> set(key: String, content: T) {
        val oldValue = backingGameData.get<T>(key)
        backingGameData[key] = content
        onSet?.invoke(key, oldValue, content)
    }

    operator fun <T : Any> get(key: String, defaultValue: T? = null, typeClass: Class<T>? = null): T? {
        return backingGameData[key, defaultValue, typeClass]
    }

    fun contains(key: String): Boolean {
        return backingGameData.contains(key)
    }

    fun <T : Any> remove(key: String): T? {
        val result = backingGameData.remove<T>(key)
        onRemove?.invoke(key, result)
        return result
    }
}
