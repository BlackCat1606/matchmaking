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
package com.github.vatbub.matchmaking.server

import com.github.vatbub.matchmaking.common.InteractionConverter
import com.github.vatbub.matchmaking.common.testing.dummies.DummyRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WebsocketSessionWrapperTest {
    @Test
    fun sendSyncTest() {
        val session = MockSession()
        val sessionWrapper = WebsocketSessionWrapper(session)
        val request = DummyRequest()
        sessionWrapper.sendObjectSync(request)
        Assertions.assertEquals(1, session.mockBasicRemote.textData.size)
        val stringSent = session.mockBasicRemote.textData[0]!!
        val deserializedRequest = InteractionConverter.deserialize<DummyRequest>(stringSent)
        Assertions.assertEquals(request, deserializedRequest)
    }

    @Test
    fun sendAsyncTest() {
        val session = MockSession()
        val sessionWrapper = WebsocketSessionWrapper(session)
        val request = DummyRequest()
        sessionWrapper.sendObjectAsync(request)
        Assertions.assertEquals(1, session.mockBasicRemote.textData.size)
        val stringSent = session.mockBasicRemote.textData[0]!!
        val deserializedRequest = InteractionConverter.deserialize<DummyRequest>(stringSent)
        Assertions.assertEquals(request, deserializedRequest)
    }
}
