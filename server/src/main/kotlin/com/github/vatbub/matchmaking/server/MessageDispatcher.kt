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
package com.github.vatbub.matchmaking.server

import com.github.vatbub.matchmaking.common.Request
import com.github.vatbub.matchmaking.common.Response
import com.github.vatbub.matchmaking.server.handlers.RequestHandler
import java.net.Inet4Address
import java.net.Inet6Address

/**
 * Dispatches received requests among the registered [RequestHandler]s.
 */
class MessageDispatcher {
    internal val handlers: MutableList<RequestHandler> = mutableListOf()

    /**
     * Iterates through all registered [RequestHandler]s and dispatches the request to the first handler which
     * specifies that it can handle the request.
     * @return The response returned by the handler or `null` if no suitable handler was found.
     */
    fun dispatch(request: Request, sourceIp: Inet4Address?, sourceIpv6: Inet6Address?): Response? {
        for (handler in handlers) {
            if (handler.canHandle(request))
                return handler.handle(request, sourceIp, sourceIpv6)
        }

        return null
    }

    fun registerHandler(handler: RequestHandler) {
        if (!isHandlerRegistered(handler))
            handlers.add(handler)
    }

    fun isHandlerRegistered(handler: RequestHandler): Boolean {
        return handlers.contains(handler)
    }

    fun removeHandler(handler: RequestHandler): Boolean {
        return handlers.remove(handler)
    }

    fun removeAllHandlers(){
        handlers.clear()
    }
}
