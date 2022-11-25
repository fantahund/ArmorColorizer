package de.fanta.ArmorColorizer.data;

public class Messages {

    private String prefix;
    private String wrongcolor;
    private String notdyeableitem;
    private String notenoughmoney;
    private String itemsuccessfullycolored;
    private String moneywithdrawn;
    private String itemHasSameColor;
    private String noiteminhand;

    public Messages(LanguageManager languageManager) {
        loadMessages(languageManager);
    }

    public void loadMessages(LanguageManager languageManager) {
        prefix = languageManager.getMessage("prefix");
        wrongcolor = languageManager.getMessage("wrongcolor");
        notdyeableitem = languageManager.getMessage("notdyeableitem");
        notenoughmoney = languageManager.getMessage("notenoughmoney");
        itemsuccessfullycolored = languageManager.getMessage("itemsuccessfullycolored");
        moneywithdrawn = languageManager.getMessage("moneywithdrawn");
        itemHasSameColor = languageManager.getMessage("itemHasSameColor");
        noiteminhand = languageManager.getMessage("noiteminhand");
    }

    public String getPrefix() {
        return prefix;
    }

    public String getWrongcolor() {
        return wrongcolor;
    }

    public String getNotdyeableitem() {
        return notdyeableitem;
    }

    public String getNotenoughmoney() {
        return notenoughmoney;
    }

    public String getItemsuccessfullycolored() {
        return itemsuccessfullycolored;
    }

    public String getMoneywithdrawn(double price, String currency) {
        return moneywithdrawn.replace("%price%", String.valueOf(price)).replace("%currency%", currency);
    }

    public String getItemHasSameColor() {
        return itemHasSameColor;
    }

    public String getNoiteminhand() {
        return noiteminhand;
    }
}
