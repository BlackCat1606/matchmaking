/*-
 * #%L
 * matchmaking.jvm-client
 * %%
 * Copyright (C) 2016 - 2020 Frederik Kammel
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
package com.github.vatbub.matchmaking.jvmclient.endpoints

import com.github.vatbub.matchmaking.common.WebsocketCloseCode

internal fun WebsocketCloseCode.generateLogMessage(closeReason: String, closedByRemote: Boolean, tryReconnect:Boolean): String {
    val builder = StringBuilder("A websocket was closed. ")
    if (closedByRemote)
        builder.append("The socket was closed by the remote. ")
    if (tryReconnect)
        builder.append("An automatic reconnect will be attempted now. ")
    builder.append("Close code: ${this.code} ${this.meaning}; Close phrase: $closeReason")
    return builder.toString()
}
