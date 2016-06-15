package fr.mrtigreroux.tigersounds.commands;

import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author MrTigreroux
 */

public class TigerSoundsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		s.sendMessage("�7�m---------------------------------------------------");
		s.sendMessage("�r                         �6TigerSounds �7> �eAide");
		s.sendMessage("�7�m---------------------------------------------------");
		s.sendMessage("�7Commandes:");
		s.sendMessage("�7- �b/sounds �7: �ePermet d'ouvrir le menu des sons.");
		s.sendMessage("�7- �b/sounds reload �7: �ePermet d'actualiser les fichiers de configuration.");
		s.sendMessage("�7- �b/sounds <num�ro du groupe> <add / remove> (start: / contains: / end:)<texte> �7: �ePermet d'ajouter ou de supprimer � un groupe tous les sons qui remplissent le crit�re indiqu�.");
		s.sendMessage("�7Le plugin �6TigerSounds �7install� sur ce serveur a �t� r�alis� par �a@MrTigreroux�7.");
		s.sendMessage("�7�m---------------------------------------------------");
		return true;
	}

}
