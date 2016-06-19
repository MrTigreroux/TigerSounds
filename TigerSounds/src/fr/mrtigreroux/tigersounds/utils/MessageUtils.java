package fr.mrtigreroux.tigersounds.utils;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mrtigreroux.tigersounds.data.Message;

/**
 * @author MrTigreroux
 */

public class MessageUtils {
	
	public static void sendErrorMessage(CommandSender s, String message) {
		s.sendMessage(message);
		if(!(s instanceof Player)) return;
		Player p = (Player) s;
		p.playSound(p.getLocation(), ConfigUtils.getErrorSound(), 1, 1);
	}

	public static String cleanDouble(Double number) {
		String process = number+" ";
		String result = process.replaceAll(".0 ", "").replaceAll(" ", "");
		if(result.contains("E")) {
			double Power = Math.pow(10D, Double.parseDouble(result.substring(result.indexOf("E")+1)));
			double WithoutPower = Double.parseDouble(result.substring(0, result.indexOf("E")));
			long Total = (long) (WithoutPower * Power);
			result = ""+Total;
		}
		return result;
	}
	
	public static String convertToString(List<String> list) {
		String stringList = null;
		for(String string : list) {
			if(stringList == null) stringList = string;
			else stringList += Message.SEPARATION.get()+string;
		}
		return stringList;
	}
	
	public static double roundTo6Places(double d) {
	    return (long)(d * 1e6 + (d > 0 ? 0.5 : -0.5)) / 1e6;
	}
	
}
