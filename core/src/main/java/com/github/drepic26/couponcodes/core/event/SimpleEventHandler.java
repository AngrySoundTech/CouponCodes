package com.github.drepic26.couponcodes.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.drepic26.couponcodes.api.event.CouponListener;
import com.github.drepic26.couponcodes.api.event.Event;
import com.github.drepic26.couponcodes.api.event.EventHandler;

public class SimpleEventHandler implements EventHandler {

	private List<Class<?>> handlers = new ArrayList<Class<?>>();

	@Override
	public void subscribe(Class<?> clazz) {
		handlers.add(clazz);
	}

	@Override
	public void unsubscribe(Class<?> clazz) {
		handlers.remove(clazz);
	}

	@Override
	public Event post(final Event event) {
		new Thread() {
			@Override
			public void run() {
				for (Class<?> handler : getHandlers()) {
					Method[] methods = handler.getMethods();

					for (int i = 0; i < methods.length; ++i) {
						CouponListener couponListener = methods[i].getAnnotation(CouponListener.class);
						if (couponListener != null) {
							Class<?>[] methodParams = methods[i].getParameterTypes();

							if (methodParams.length < 1)
								continue;

							if (!event.getClass().getSimpleName().equals(methodParams[0].getSimpleName()))
								continue;

							try {
								methods[i].invoke(handler.newInstance(), event);
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

	private List<Class<?>> getHandlers() {
		return handlers;
	}

}
