/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
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
package com.drevelopment.couponcodes.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.drevelopment.couponcodes.api.event.CouponListener;
import com.drevelopment.couponcodes.api.event.Event;
import com.drevelopment.couponcodes.api.event.EventHandler;

public class SimpleEventHandler implements EventHandler {

    private List<Class<?>> handlers = new ArrayList<>();

    @Override
    public Event post(final Event event) {
        new Thread() {
            @Override
            public void run() {
                for (Class<?> handler : getHandlers()) {
                    Method[] methods = handler.getMethods();

                    for (Method method : methods) {
                        CouponListener couponListener = method.getAnnotation(CouponListener.class);
                        if (couponListener != null) {
                            Class<?>[] methodParams = method.getParameterTypes();

                            if (methodParams.length < 1)
                                continue;

                            if (!event.getClass().getSimpleName().equals(methodParams[0].getSimpleName()))
                                continue;

                            try {
                                method.invoke(handler.newInstance(), event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.start();
        return event;
    }

    @Override
    public void subscribe(Class<?> clazz) {
        handlers.add(clazz);
    }

    @Override
    public void unsubscribe(Class<?> clazz) {
        handlers.remove(clazz);
    }

    private List<Class<?>> getHandlers() {
        return handlers;
    }

}
