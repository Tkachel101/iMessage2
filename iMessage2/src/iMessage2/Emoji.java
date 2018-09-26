package iMessage2;

import java.util.ArrayList;
import java.util.EnumSet;

public enum Emoji {
	SlightlySmilingFace("🙂"),
	FrowningFace("☹️"),
	Goblin("👺"),
	Fire("🔥"),
	RedHeart("❤️"),
	FaceBlowingAKiss("😘"),
	SkullAndCrossBones("☠️"),
	SmilingFaceWithSunglasses("😎"),
	FaceWithTearsOfJoy("😂"),
	AlienMonster("👾"),
	HorseRacing("🏇"),
	Skier("⛷️"),
	Snowboarder("🏂"),
	FlagForUnitedStates("🇺🇸"),
	Zzz("💤");
//	BirthdayCake("🎂"),
//	PartyPopper("🎉"),
//	Balloon("🎈"),
//	WrappedPresent("🎁")
	
	private String emoji;

	private Emoji(String emoji) {
		this.emoji = emoji;
	}

	public String getEmoji() {
		return emoji;
	}
	
	public static ArrayList<Emoji> getEmojis(){
		return new ArrayList<Emoji>(EnumSet.allOf(Emoji.class));
	}
	
	public static ArrayList<String> getEmojisString(){
		ArrayList<Emoji> emojis = Emoji.getEmojis();
		ArrayList<String> total = new ArrayList<>();
		total.add("Emojis");
		for(Emoji emoji : emojis){
			total.add(emoji.getEmoji());
		}
		return total;
	}
}
