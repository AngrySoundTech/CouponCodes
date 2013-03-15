package net.Drepic.CouponCodes.sql.options;

import java.io.File;

public class SQLiteOptions implements DatabaseOptions {

	private File file;
	
	public SQLiteOptions(File file) {
		this.file = file;
	}
	
	public File getSQLFile() {
		return file;
	}
}
