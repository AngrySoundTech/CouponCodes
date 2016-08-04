/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.couponcodes.core.event

import tech.feldman.couponcodes.api.event.CouponListener
import tech.feldman.couponcodes.api.event.Event
import tech.feldman.couponcodes.api.event.EventHandler
import java.util.*

class SimpleEventHandler : EventHandler {

    private val handlers = ArrayList<Class<*>>()

    override fun post(event: Event): Event {
        object : Thread() {
            override fun run() {
                for (handler in getHandlers()) {
                    val methods = handler.methods

                    for (method in methods) {
                        val couponListener = method.getAnnotation(CouponListener::class.java)
                        if (couponListener != null) {
                            val methodParams = method.parameterTypes

                            if (methodParams.size < 1)
                                continue

                            if (event.javaClass.simpleName != methodParams[0].simpleName)
                                continue

                            try {
                                method.invoke(handler.newInstance(), event)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }
                }
            }
        }.start()
        return event
    }

    override fun subscribe(clazz: Class<*>) {
        handlers.add(clazz)
    }

    override fun unsubscribe(clazz: Class<*>) {
        handlers.remove(clazz)
    }

    private fun getHandlers(): List<Class<*>> {
        return handlers
    }

}
