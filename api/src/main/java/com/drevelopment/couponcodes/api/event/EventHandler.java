package com.drevelopment.couponcodes.api.event;

public interface EventHandler {

	/**
	 * Fire an event to be passed to all registered listeners.
	 * @param event The event to fire
	 * @return The event fired
	 */
	public Event post(Event event);

	/**
	 * Register a class to listen for custom events.
	 * <p> listener methods should have the annotation {@link CouponListener}
	 * and the event they want to listen for as an argument
	 * @param clazz The class to register as a listener
	 */
	public void subscribe(Class<?> clazz);

	/**
	 * Unregister a class listening for custom events
	 * @param clazz The class to unregister
	 */
	public void unsubscribe(Class<?> clazz);

}
