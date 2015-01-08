package com.github.drepic26.couponcodes.granite;

import org.granitemc.granite.api.plugin.OnEnable;
import org.granitemc.granite.api.plugin.Plugin;
import org.granitemc.granite.api.plugin.PluginContainer;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.util.CouponCodesProperties;

@Plugin(id = "couponcodes", name = "CouponCodes", version = CouponCodesProperties.VERSION)
public class GranitePlugin {

	private ServerModTransformer transformer = new GraniteServerModTransformer();

	@OnEnable
	public void onEnable(PluginContainer container) {

	}
}
