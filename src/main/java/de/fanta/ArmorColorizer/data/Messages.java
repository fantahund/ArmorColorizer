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
    private String insertColorError;
    private String insertColorSuccessful;
    private String insertColorAlreadyAvailable;
    private String guiTitle;
    private String guiScrollUp;
    private String guiScrollDown;
    private String guiClose;
    private String noSavedColors;
    private String sortRainbow;
    private String sortDate;

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
        insertColorError = languageManager.getMessage("insertColorError");
        insertColorSuccessful = languageManager.getMessage("insertColorSuccessful");
        insertColorAlreadyAvailable = languageManager.getMessage("insertColorAlreadyAvailable");
        guiTitle = languageManager.getMessage("guiTitle");
        guiScrollUp = languageManager.getMessage("guiScrollUp");
        guiScrollDown = languageManager.getMessage("guiScrollDown");
        guiClose = languageManager.getMessage("guiClose");
        noSavedColors = languageManager.getMessage("noSavedColors");
        sortRainbow = languageManager.getMessage("sortRainbow");
        sortDate = languageManager.getMessage("sortDate");
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

    public String getInsertColorError() {
        return insertColorError;
    }

    public String getInsertColorSuccessful() {
        return insertColorSuccessful;
    }

    public String getInsertColorAlreadyAvailable() {
        return insertColorAlreadyAvailable;
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public String getGuiScrollUp() {
        return guiScrollUp;
    }

    public String getGuiScrollDown() {
        return guiScrollDown;
    }

    public String getGuiClose() {
        return guiClose;
    }

    public String getNoSavedColors() {
        return noSavedColors;
    }

    public String getSortRainbow() {
        return sortRainbow;
    }

    public String getSortDate() {
        return sortDate;
    }
}
