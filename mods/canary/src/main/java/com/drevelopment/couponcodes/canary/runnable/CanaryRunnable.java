package com.drevelopment.couponcodes.canary.runnable;

import net.canarymod.tasks.ServerTask;
import net.canarymod.tasks.TaskOwner;

public class CanaryRunnable extends ServerTask {

	private Runnable runnable;

	public CanaryRunnable(TaskOwner owner, Runnable runnable, long delay) {
		super(owner, delay);
		this.runnable = runnable;
	}

	@Override
	public void run() {
		runnable.run();
	}

}
