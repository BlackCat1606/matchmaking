/*-
 * #%L
 * matchmaking.server
 * %%
 * Copyright (C) 2016 - 2018 Frederik Kammel
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
package com.github.vatbub.matchmaking.server.logic.idprovider

import com.github.vatbub.matchmaking.server.logic.idprovider.AuthorizationResult.*

/**
 * Provides and stores connection ids.
 * Implementations are expected to produce connection ids and store them for later use, e. g. in memory or in a database.
 * See [MemoryIdProvider] for a reference implementation
 */
interface ConnectionIdProvider {
    /**
     * Called when a new connection id is requested. The implementation is expected to store the generated connection id
     * automatically
     */
    fun getNewId(): Id

    /**
     * Deletes the specified id if it exists
     * @return The deleted id or `null` if it didn't exist
     */
    fun deleteId(id: String): Id?

    /**
     * Deletes the specified id if it exists
     * @return The deleted id or `null` if it didn't exist
     */
    fun deleteId(id: Id): Id? {
        if (id.connectionId == null)
            return null
        return deleteId(id.connectionId)
    }

    operator fun get(id: String): Id?

    fun containsId(id: String?): Boolean
    /**
     * Deletes all known ids
     */
    fun reset()

    fun isAuthorized(id: Id): AuthorizationResult {
        if (id.connectionId == null)
            return NotAuthorized
        val lookUpResult = this[id.connectionId] ?: return NotFound
        if (lookUpResult.password != id.password)
            return NotAuthorized
        return Authorized
    }
}

data class Id(val connectionId: String?, val password: String?)

enum class AuthorizationResult {
    NotFound, Authorized, NotAuthorized
}