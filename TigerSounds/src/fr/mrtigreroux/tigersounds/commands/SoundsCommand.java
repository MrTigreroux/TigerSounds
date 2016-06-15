package fr.mrtigreroux.tigersounds.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.MessageUtils;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;
import fr.mrtigreroux.tigersounds.utils.GroupUtils;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class SoundsCommand implements CommandExecutor {

	public final Set<String> AddWords = new HashSet<String>(Arrays.asList("add", "a", "+"));
	public final Set<String> RemoveWords = new HashSet<String>(Arrays.asList("remove", "rem", "r", "delete", "del", "d", "-"));
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if(!s.hasPermission("tigersounds.manage")) {
				MessageUtils.sendErrorMessage(s, Message.PERMISSION_COMMAND.get());
				return true;
			}
			FilesManager.loadConfig(FilesManager.getConfig, FilesManager.config);
			FilesManager.loadConfig(FilesManager.getMessages, FilesManager.messages);
			FilesManager.loadConfig(FilesManager.getGroups, FilesManager.groups);
			FilesManager.loadConfig(FilesManager.getSounds, FilesManager.sounds);
			s.sendMessage(Message.RELOAD.get());
			return true;
		} else if(args.length == 3) {
			if(!s.hasPermission("tigersounds.manage")) {
				MessageUtils.sendErrorMessage(s, Message.PERMISSION_COMMAND.get());
				return true;
			}
			int groupNumber;
			try {
				groupNumber = Integer.parseInt(args[0]);
				FilesManager.getGroups.get("Groups.Group"+groupNumber);
			} catch(Exception InvalidNumber) {
				MessageUtils.sendErrorMessage(s, Message.INVALID_GROUP.get().replaceAll("_Number_", args[0]));
				return true;
			}
			
			List<String> soundsList = GroupUtils.getSoundsList(groupNumber);
			
			String action;
			if(AddWords.contains(args[1])) action = "Add";
			else if(RemoveWords.contains(args[1])) action = "Remove";
			else {
				MessageUtils.sendErrorMessage(s, Message.INVALID_ACTION.get().replaceAll("_Action_", args[1]));
				return true;
			}
			
			String criterion = args[2];
			String criterionType;
			if(criterion.startsWith("s:") || criterion.startsWith("start:")) {
				criterionType = "Start";
				criterion = criterion.replaceFirst("s:", "").replaceFirst("start:", "");
			}
			else if(criterion.startsWith("c:") || criterion.startsWith("contains:")) {
				criterionType = "Contains";
				criterion = criterion.replaceFirst("c:", "").replaceFirst("contains:", "");
			}
			else if(criterion.startsWith("e:") || criterion.startsWith("end:")) {
				criterionType = "End";
				criterion = criterion.replaceFirst("e:", "").replaceFirst("end:", "");
			}
			else criterionType = "Equals";
			
			List<String> selectedSounds = new ArrayList<String>();
			List<String> modifiedSounds = new ArrayList<String>();
			
			for(String configName : SoundUtils.getAllSounds()) {
				boolean selectedSound = false;
				switch(criterionType) {
					case "Start": if(configName.startsWith(criterion)) selectedSound = true; break;
					case "Contains": if(configName.contains(criterion)) selectedSound = true; break;
					case "End": if(configName.endsWith(criterion)) selectedSound = true; break;
					case "Equals": if(configName.equals(criterion)) selectedSound = true; break;
					default: selectedSound = false; break;
				}
				if(selectedSound) {
					selectedSounds.add(configName);
					if(action.equals("Add")) {
						if(!soundsList.contains(configName)) {
							soundsList.add(configName);
							modifiedSounds.add(configName);
						}
					} else {
						if(soundsList.contains(configName)) {
							soundsList.remove(configName);
							modifiedSounds.add(configName);
						}
					}
				}
			}
			GroupUtils.setSoundsList(groupNumber, soundsList);
			criterion = Message.CRITERION_WORD.get().replaceAll("_Word_", criterion)+" ";
			try {
				criterion = Message.valueOf(criterionType.toUpperCase()).get()+" "+criterion;
			} catch(Exception NoCriterionType) {
				if(criterionType.equals("Equals")) criterion = "";
			}
			
			int selectedAmount = selectedSounds.size();
			int modifiedAmount = modifiedSounds.size();
			String type = "SOUNDS";
			if(selectedAmount <= 0) {
				MessageUtils.sendErrorMessage(s, Message.NO_VALID_SOUND.get().replaceAll("_Criterion_", criterion));
				return true;
			} else if(selectedAmount == 1) type = "SOUND";
			
			String message = Message.SOUNDSLIST_MODIFIED_PLURAL.get();
			if(modifiedAmount == 1) {
				message = Message.SOUNDSLIST_MODIFIED_SINGULAR.get();
				action += "_SINGULAR";
			} else action += "_PLURAL";
			
			if(modifiedAmount == 0) {
				if(action.startsWith("Add")) message = Message.valueOf(type+"_ALREADY_IN_GROUP").get();
				else if(action.startsWith("Remove")) message = Message.valueOf(type+"_NOT_IN_GROUP").get();
				MessageUtils.sendErrorMessage(s, message.replaceAll("_Criterion_", criterion).replaceAll("_Sound_", "_Sounds_").replaceAll("_Sounds_", MessageUtils.convertToString(selectedSounds)).replaceAll("_Group_", GroupUtils.getCustomName(groupNumber)));
			} else s.sendMessage(ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), message.replaceAll("_Action_", Message.valueOf(action.toUpperCase()).get()).replaceAll("_Criterion_", criterion).replaceAll("_Sound_", "_Sounds_").replaceAll("_Sounds_", modifiedAmount != 0 ? MessageUtils.convertToString(modifiedSounds) : Message.NONE_SOUND.get()).replaceAll("_Group_", GroupUtils.getCustomName(groupNumber))));
			return true;
		} else if(args.length == 0) {
			if(!(s instanceof Player)) {
				s.sendMessage(Message.PLAYER_ONLY.get());
				return true;
			}
			Player p = (Player) s;
			if(ConfigUtils.isEnabled(FilesManager.getConfig, "Config.PermissionRequired") && !p.hasPermission("tigersounds.open")) {
				MessageUtils.sendErrorMessage(s, Message.PERMISSION_COMMAND.get());
				return true;
			}
			new User(p).openGroupsMenu(1);
			return true;
		}
		s.sendMessage(Message.INVALID_SYNTAX.get().replaceAll("_Command_", "/"+label+" (reload / numéro du groupe) (add / remove) (start: / contains: / end:)(texte)"));
		return true;
	}

}
