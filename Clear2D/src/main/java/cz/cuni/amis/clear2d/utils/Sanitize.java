package cz.cuni.amis.clear2d.utils;

public class Sanitize {

	public static String id_allowed = "abcdefghijklmnopqrstuvwxyz0123456789_-";
	
	public static String id_allowedUpper = id_allowed.toUpperCase();
	
	public static String var_allowed = "abcdefghijklmnopqrstuvwxyz0123456789_";
	
	public static String var_allowedUpper = var_allowed.toUpperCase();
	
	public static String idify(String id) {
		StringBuffer result = new StringBuffer(id.length());
		for (int i = 0; i < id.length(); ++i) {
			if (id_allowed.contains(id.substring(i, i+1)) || id_allowedUpper.contains(id.substring(i, i+1))) {
				result.append(id.charAt(i));
			} else {
				result.append("_");
			}
		}
		return result.toString();
	}
	
	public static String variablify(String id) {
		StringBuffer result = new StringBuffer(id.length());
		for (int i = 0; i < id.length(); ++i) {
			if (var_allowed.contains(id.substring(i, i+1)) || var_allowedUpper.contains(id.substring(i, i+1))) {
				result.append(id.charAt(i));
			} else {
				result.append("_");
			}
		}
		return result.toString();
	}
	
}
