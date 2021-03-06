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
package com.github.vatbub.matchmaking.server.logic.idprovider

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class JdbcIdProviderTest : ConnectionIdProviderTest<JdbcIdProvider>() {
    override fun getCloneOf(instance: JdbcIdProvider) = JdbcIdProvider(instance.connectionPoolWrapper)

    private var lastProviderInstance: JdbcIdProvider? = null

    companion object {
        var dbCounter = 0
    }

    override fun newObjectUnderTest(): JdbcIdProvider {
        val useMemDb = true
        @Suppress("ConstantConditionIf")
        val provider = if (useMemDb)
            JdbcIdProvider(
                    "jdbc:h2:mem:connectionIdTestDB$dbCounter",
                    "SA",
                    ""
            )
        else
            JdbcIdProvider(
                    "jdbc:postgresql://manny.db.elephantsql.com:5432/ehlblzzc",
                    "ehlblzzc",
                    "WLwb_lRqRPB8wkXl6yg37OyaciD1T2Ny"
            )
        dbCounter++
        lastProviderInstance = provider
        return provider
    }

    @AfterEach
    fun assertAllConnectionsReturned() {
        val lastProviderInstanceCopy = lastProviderInstance ?: return
        Assertions.assertEquals(0, lastProviderInstanceCopy.connectionPoolWrapper.connectionCount)
    }

    @Test
    override fun notEqualsTest() {
        val provider1 = newObjectUnderTest()
        val provider2 = newObjectUnderTest()
        Assertions.assertNotEquals(provider1, provider2)
    }
}
