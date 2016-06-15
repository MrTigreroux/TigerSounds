package fr.mrtigreroux.tigersounds.data;

import org.bukkit.ChatColor;

import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;

/**
 * @author MrTigreroux
 */

public enum Message {

	RELOAD("Messages.Reload", "§7[§6TigerSounds§7] §eLes fichiers de configuration ont été actualisés."),
	SOUNDSLIST_MODIFIED_SINGULAR("Messages.Sounds-list-modified-singular", "§7[§6Sounds§7] §eLe son §7_Sound_ §e_Criterion_§ea été _Action_ §eau groupe §r_Group_§e."),
	SOUNDSLIST_MODIFIED_PLURAL("Messages.Sounds-list-modified-plural", "§7[§6Sounds§7] §eLes sons §e_Criterion_§eont été _Action_ §eau groupe _Group_§e: §7_Sounds_§e."),
	PLAYSOUND("Messages.Play-sound", "§7[§6Sounds§7] §eCliquez §epour §efaire §eécouter §ele §eson §r_Sound_ §eà §eun §ejoueur."),
	PLAYSOUND_DETAILS("Messages.Play-sound-details", "§6Clic gauche §7pour écrire la commande permettant de faire écouter le son §a_Sound_ §7à un joueur."),
	
	INVALID_SYNTAX("ErrorMessages.Invalid-syntax", "§7[§6Sounds§7] §eVeuillez détailler votre commande tel que §b_Command_§e."),
	PERMISSION_COMMAND("ErrorMessages.Permission-command", "§7[§6Sounds§7] §cVous n'avez pas accès à cette commande."),
	PLAYER_ONLY("ErrorMessages.Player-only", "TigerSounds > Cette commande est reservee aux joueurs."),
	INVALID_GROUP("ErrorMessages.Invalid-group", "§7[§6Sounds§7] §cVous avez indiqué un numéro de groupe invalide §7(§e_Number_§7)§c."),
	INVALID_ACTION("ErrorMessages.Invalid-action", "§7[§6Sounds§7] §cVous avez indiqué une action invalide §7(§e_Action_§7)§c."),
	NO_VALID_SOUND("ErrorMessages.No-valid-sound", "§7[§6Sounds§7] §cAucun son _Criterion_§cn'a été trouvé."),
	SOUND_NOT_IN_GROUP("ErrorMessages.Sound-not-in-group", "§7[§6Sounds§7] §cLe son §7_Sound_ §c_Criterion_§cn'est pas dans le groupe §b_Group_§c."),
	SOUND_ALREADY_IN_GROUP("ErrorMessages.Sound-already-in-group", "§7[§6Sounds§7] §cLe son §7_Sound_ §c_Criterion_§cest déjà dans le groupe §b_Group_§c."),
	SOUNDS_NOT_IN_GROUP("ErrorMessages.Sounds-not-in-group", "§7[§6Sounds§7] §cLes sons _Criterion_§cne sont pas dans le groupe §b_Group_§c: §7_Sounds_§c."),
	SOUNDS_ALREADY_IN_GROUP("ErrorMessages.Sounds-already-in-group", "§7[§6Sounds§7] §cLes sons _Criterion_§csont déjà dans le groupe §b_Group_§c: §7_Sounds_§c."),
	
	CLOSE("Menus.Close", "§cFermer"),
	PAGE_SWITCH_PREVIOUS("Menus.Switch-to-previous-page", "§6§l< §7Page précédente"),
	PAGE_SWITCH_NEXT("Menus.Switch-to-next-page", "§7Page suivante §6§l>"),
	GROUP_SWITCH_PREVIOUS("Menus.Switch-to-previous-group", "§6§l< §7Groupe précédent"),
	GROUP_SWITCH_NEXT("Menus.Switch-to-next-group", "§7Groupe suivant §6§l>"),
	SOUND_SWITCH_PREVIOUS("Menus.Switch-to-previous-sound", "§6§l< §7Son précédent"),
	SOUND_SWITCH_NEXT("Menus.Switch-to-next-sound", "§7Son suivant §6§l>"),
	GROUPS_ICONNAME("Menus.Groups-icon-name", "§eGroupes"),
	GROUPS_TITLE("Menus.Groups-title", "§eGroupes de sons"),
	GROUP_DETAILS("Menus.Group-details", "§7§o_Description_// //§7Numéro: §e_Number_//§7Sons: §r_Sounds_// //§6Clic §7pour afficher les sons."),
	SOUNDS_TITLE("Menus.Sounds-title", "§6Groupe §7> §e_Group_"),
	SOUND_DETAILS("Menus.Sound-details", "§7§o_Description_// //§7Bukkit: §b_Bukkit_//§7Vanilla: §a_Vanilla_//§7Config: §e_Config_// //§6Clic gauche §7pour l'écouter.//§6Clic molette §7pour le modifier.//§6Clic droit §7pour le faire écouter à un joueur."),
	ADVANCED_TITLE("Menus.Advanced-title", "§6Son §7> §e_Sound_"),
	CHANGE_PITCH("Menus.Change-pitch", "§eModifier le pitch §7(§b_Pitch_§7)"),
	CHANGE_PITCH_DETAILS("Menus.Change-pitch-details", "//§6Clic gauche §7pour augmenter le pitch.//§6Clic droit §7pour diminuer le pitch."),
	CHANGE_VOLUME("Menus.Change-volume", "§eModifier le volume §7(§b_Volume_§7)"),
	CHANGE_VOLUME_DETAILS("Menus.Change-volume-details", "//§6Clic gauche §7pour augmenter le volume.//§6Clic droit §7pour diminuer le volume."),
	CHANGE_DISTANCE("Menus.Change-distance", "§eModifier la distance §7(§b_Distance_§7)"),
	CHANGE_DISTANCE_DETAILS("Menus.Change-distance-details", "//§6Clic gauche §7pour augmenter la distance.//§6Clic droit §7pour diminuer la distance."),
	GLOW("Menus.Glow", "§eDémarquer le son"),
	GLOW_DETAILS("Menus.Glow-details", "//§6Clic gauche §7pour faire briller le son.//§6Clic droit §7pour ne pas faire briller le son."),
	CHANGE_NAME("Menus.Change-name", "§eChanger le nom"),
	CHANGE_NAME_DETAILS("Menus.Change-name-details", "//§6Clic §7pour changer le nom."),
	CHANGE_DESCRIPTION("Menus.Change-description", "§eChanger la description"),
	CHANGE_DESCRIPTION_DETAILS("Menus.Change-description-details", "//§6Clic §7pour changer la description."),
	CHANGE_VANILLANAME("Menus.Change-vanilla-name", "§eChanger le no vanillam"),
	CHANGE_VANILLANAME_DETAILS("Menus.Change-vanilla-name-details", "//§6Clic §7pour changer le nom vanilla."),
	CHANGE_BUKKITNAME("Menus.Change-bukkit-name", "§eChanger le nom bukkit"),
	CHANGE_BUKKITNAME_DETAILS("Menus.Change-bukkit-name-details", "//§6Clic §7pour changer le nom bukkit."),
	REMOVE_SOUND("Menus.Remove-sound", "§cSupprimer le son"),
	REMOVE_SOUND_DETAILS("Menus.Remove-sound-details", "//§6Clic gauche §7pour retirer le son du groupe.//§6Clic droit §7pour supprimer le son."),
	CONFIRM_SUPPRESSION("Menus.Confirm-suppression", "§a§lConfirmer la suppression"),
	CONFIRM_SUPPRESSION_FROM_GROUP_TITLE("Menus.Confirm-suppression-from-group-title", "§6Retirer §7> §e_Sound_"),
	CONFIRM_SUPPRESSION_FROM_GROUP_DETAILS("Menus.Confirm-suppression-from-group-details", "//§6Clic §7pour retirer le son §e_Sound_ //§7du groupe _Group_§7."),
	CONFIRM_SUPPRESSION_OF_SOUND_TITLE("Menus.Confirm-suppression-of-sound-title", "§6Supprimer §7> §e_Sound_"),
	CONFIRM_SUPPRESSION_OF_SOUND_DETAILS("Menus.Confirm-suppression-of-sound-details", "//§6Clic §7pour supprimer le son §e_Sound_§7."),
	CANCEL_SUPPRESSION("Menus.Cancel-suppression", "§c§lAnnuler la suppression"),
	CANCEL_SUPPRESION_DETAILS("Menus.Cancel-suppression-details", "//§6Clic §7pour annuler la suppression."),

	START("Words.Start", "commençant par"),
	CONTAINS("Words.Contains", "contenant"),
	END("Words.End", "se terminant par"),
	CRITERION_WORD("Words.Criterion-word", "§r"+'"'+"§7_Word_§r"+'"'),
	SEPARATION("Words.Sepration", "§7, "),
	DEFAULT_GROUP_NAME("Words.Default-group-name", "§7Groupe §an°_Number_"),
	DEFAULT_SOUND_NAME("Words.Default-sound-name", "§b_Name_"),
	NONE_DESCRIPTION("Words.None-description", "§7Aucune description"),
	NONE_SOUND("Words.None-sound", "§cAucun"),
	NONE_BUKKITNAME("Words.None-bukkit-name", "§cAucun"),
	ADD_SINGULAR("Words.Add-singular", "§aajouté"),
	ADD_PLURAL("Words.Add-plural", "§aajoutés"),
	REMOVE_SINGULAR("Words.Remove-singular", "§cretiré"),
	REMOVE_PLURAL("Words.Remove-plural", "§cretirés");
	
	private final String path;
	private final String defaultMessage;
	
	Message(String path, String defaultMessage) {
		this.path = path;
		this.defaultMessage = defaultMessage;
	}
	
	public String get() {
		if(FilesManager.getMessages.contains(path) && FilesManager.getMessages.get(path) != null) return ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), FilesManager.getMessages.getString(path));
		else return defaultMessage;
	}
	
}
