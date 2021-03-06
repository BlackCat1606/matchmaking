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
package com.github.vatbub.matchmaking.common.requests

import com.github.vatbub.matchmaking.common.Request
import com.github.vatbub.matchmaking.common.responses.DestroyRoomResponse
import com.github.vatbub.matchmaking.common.responses.GetConnectionIdResponse
import com.github.vatbub.matchmaking.common.responses.NotAllowedException

/**
 * This request must be sent by the game host which causes the specified room to be destroyed.
 * Important: Only the game host may send this request. If the sender is not the host in the specified room, a [NotAllowedException] is returned
 *
 * # JSON example
 * ```json
 * {jsonSample}
 * ```
 *
 * @param connectionId The requesting client's connection id as assigned by [GetConnectionIdResponse]
 * @param password The requesting client's password as assigned by [GetConnectionIdResponse]
 * @param roomId The id of the room to destroy
 * @see DestroyRoomResponse
 */
class DestroyRoomRequest(connectionId: String, password: String, val roomId: String, requestId: String? = null) :
        Request(connectionId, password, DestroyRoomRequest::class.qualifiedName!!, requestId) {
    override fun copy() = DestroyRoomRequest(connectionId!!, password!!, roomId, requestId)

    /**
     * Do not remove! Used by KryoNet.
     */
    @Suppress("unused")
    internal constructor() : this("", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as DestroyRoomRequest

        if (roomId != other.roomId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + roomId.hashCode()
        return result
    }
}
