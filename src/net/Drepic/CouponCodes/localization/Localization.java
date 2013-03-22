package net.Drepic.CouponCodes.localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import net.Drepic.CouponCodes.CouponCodes;

import org.bukkit.ChatColor;
import org.apache.commons.io.IOUtils;

public class Localization {

	public CouponCodes plugin = CouponCodes.plugin;

	public String getMessage(String key){
		InputStream is = null;
		is = Localization.class.getResourceAsStream("/net/Drepic/CouponCodes/localization/" + CouponCodes.lang + ".lang");
		if (is == null)
			is = Localization.class.getResourceAsStream("/net/Drepic/CouponCodes/localization/English.lang");
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "ISO-8859-1");
			String contents = writer.toString();
			String[] lines = contents.split("\n");
			for (int i = 0; i < lines.length; i++){
				String[] params = lines[i].split("\\|");
				if (params[0].equalsIgnoreCase(key))
					return params[1];
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return ChatColor.RED + "Could not get message from localization!";
	}

}
