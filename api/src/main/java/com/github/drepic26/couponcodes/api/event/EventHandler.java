package com.github.drepic26.couponcodes.api.event;

public interface EventHandler {

	public Event post(Event event);

	public void subscribe(Class<?> clazz);

	public void unsubscribe(Class<?> clazz);

}
