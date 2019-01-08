/*-
 * #%L
 * matchmaking.common
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
package com.github.vatbub.matchmaking.common.serializationtests.requests

import com.github.vatbub.matchmaking.common.requests.JoinOrCreateRoomRequest
import com.github.vatbub.matchmaking.common.requests.Operation
import com.github.vatbub.matchmaking.common.serializationtests.SerializationTestSuperclass

class JoinOrCreateRoomRequestSerializationTest :
    SerializationTestSuperclass<JoinOrCreateRoomRequest>(JoinOrCreateRoomRequest::class.java) {
    override fun newObjectUnderTest(): JoinOrCreateRoomRequest {
        return JoinOrCreateRoomRequest(defaultConnectionId, Operation.JoinOrCreateRoom, getRandomHexString())
    }
}