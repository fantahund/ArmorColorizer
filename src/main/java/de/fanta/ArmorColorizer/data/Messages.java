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
    private String guiTitleSave;
    private String guiScrollUp;
    private String guiScrollDown;
    private String guiClose;
    private String noSavedColors;
    private String sortRainbow;
    private String sortDate;
    private String guiSaveColor;
    private String yes;
    private String no;
    private String guiTitleDelete;
    private String rgbColorizerTitle;
    private String red;
    private String addRed;
    private String removeRed;
    private String green;
    private String addGreen;
    private String removeGreen;
    private String blue;
    private String addBlue;
    private String removeBlue;
    private String click;
    private String shiftClick;
    private String price;
    private String changeColor;
    private String randomColor;
    private String hsbColorizerTitle;
    private String hue;
    private String addHue;
    private String removeHue;
    private String saturation;
    private String addSaturation;
    private String removeSaturation;
    private String brightness;
    private String addBrightness;
    private String removeBrightness;
    private String hueRangeError;
    private String saturationRangeError;
    private String brightnessRangeError;
    private String redRangeError;
    private String greenRangeError;
    private String blueRangeError;
    private String colorDeleteSuccessful;
    private String colorDeleteError;
    private String freeModeEnable;
    private String freeModeDisable;
    private String freeModeAlreadyEnabled;
    private String freeModeAlreadyDisabled;

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
        guiTitleSave = languageManager.getMessage("guiTitleSave");
        guiScrollUp = languageManager.getMessage("guiScrollUp");
        guiScrollDown = languageManager.getMessage("guiScrollDown");
        guiClose = languageManager.getMessage("guiClose");
        noSavedColors = languageManager.getMessage("noSavedColors");
        sortRainbow = languageManager.getMessage("sortRainbow");
        sortDate = languageManager.getMessage("sortDate");
        guiSaveColor = languageManager.getMessage("guiSaveColor");
        yes = languageManager.getMessage("yesString");
        no = languageManager.getMessage("noString");
        guiTitleDelete = languageManager.getMessage("guiTitleDelete");
        rgbColorizerTitle = languageManager.getMessage("rgbColorizerTitle");
        red = languageManager.getMessage("red");
        addRed = languageManager.getMessage("addRed");
        removeRed = languageManager.getMessage("removeRed");
        green = languageManager.getMessage("green");
        addGreen = languageManager.getMessage("addGreen");
        removeGreen = languageManager.getMessage("removeGreen");
        blue = languageManager.getMessage("blue");
        addBlue = languageManager.getMessage("addBlue");
        removeBlue = languageManager.getMessage("removeBlue");
        click = languageManager.getMessage("click");
        shiftClick = languageManager.getMessage("shiftClick");
        price = languageManager.getMessage("price");
        changeColor = languageManager.getMessage("changeColor");
        randomColor = languageManager.getMessage("randomColor");
        hsbColorizerTitle = languageManager.getMessage("hsbColorizerTitle");
        hue = languageManager.getMessage("hue");
        addHue = languageManager.getMessage("addHue");
        removeHue = languageManager.getMessage("removeHue");
        saturation = languageManager.getMessage("saturation");
        addSaturation = languageManager.getMessage("addSaturation");
        removeSaturation = languageManager.getMessage("removeSaturation");
        brightness = languageManager.getMessage("brightness");
        addBrightness = languageManager.getMessage("addBrightness");
        removeBrightness = languageManager.getMessage("removeBrightness");
        hueRangeError = languageManager.getMessage("hueRangeError");
        saturationRangeError = languageManager.getMessage("saturationRangeError");
        brightnessRangeError = languageManager.getMessage("brightnessRangeError");
        redRangeError = languageManager.getMessage("redRangeError");
        greenRangeError = languageManager.getMessage("greenRangeError");
        blueRangeError = languageManager.getMessage("blueRangeError");
        colorDeleteSuccessful = languageManager.getMessage("colorDeleteSuccessful");
        colorDeleteError = languageManager.getMessage("colorDeleteError");
        freeModeEnable = languageManager.getMessage("freeModeEnable");
        freeModeDisable = languageManager.getMessage("freeModeDisable");
        freeModeAlreadyEnabled = languageManager.getMessage("freeModeAlreadyEnabled");
        freeModeAlreadyDisabled = languageManager.getMessage("freeModeAlreadyDisabled");
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

    public String getGuiTitleSave() {
        return guiTitleSave;
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

    public String getGuiSaveColor() {
        return guiSaveColor;
    }

    public String getYes() {
        return yes;
    }

    public String getNo() {
        return no;
    }

    public String getGuiTitleDelete() {
        return guiTitleDelete;
    }

    public String getRgbColorizerTitle() {
        return rgbColorizerTitle;
    }

    public String getRed(int redValue) {
        return red.replace("%red%", String.valueOf(redValue));
    }

    public String getAddRed(int redValue) {
        return addRed.replace("%red%", String.valueOf(redValue));
    }

    public String getRemoveRed(int redValue) {
        return removeRed.replace("%red%", String.valueOf(redValue));
    }

    public String getGreen(int greenValue) {
        return green.replace("%green%", String.valueOf(greenValue));
    }

    public String getAddGreen(int greenValue) {
        return addGreen.replace("%green%", String.valueOf(greenValue));
    }

    public String getRemoveGreen(int greenValue) {
        return removeGreen.replace("%green%", String.valueOf(greenValue));
    }

    public String getBlue(int blueValue) {
        return blue.replace("%blue%", String.valueOf(blueValue));
    }

    public String getAddBlue(int blueValue) {
        return addBlue.replace("%blue%", String.valueOf(blueValue));
    }

    public String getRemoveBlue(int blueValue) {
        return removeBlue.replace("%blue%", String.valueOf(blueValue));
    }

    public String getClick(int value) {
        return click.replace("%value%", String.valueOf(value));
    }

    public String getShiftClick(int value) {
        return shiftClick.replace("%value%", String.valueOf(value));
    }

    public String getPrice(double priceValue, String currency) {
        return price.replace("%price%", String.valueOf(priceValue)).replace("%currency%", currency);
    }

    public String getChangeColor() {
        return changeColor;
    }

    public String getRandomColor() {
        return randomColor;
    }

    public String getHsbColorizerTitle() {
        return hsbColorizerTitle;
    }

    public String getHue(float hueValue) {
        return hue.replace("%hue%", String.valueOf(hueValue));
    }

    public String getAddHue(float hueValue) {
        return addHue.replace("%hue%", String.valueOf(hueValue));
    }

    public String getRemoveHue(float hueValue) {
        return removeHue.replace("%hue%", String.valueOf(hueValue));
    }

    public String getSaturation(float saturationValue) {
        return saturation.replace("%saturation%", String.valueOf(saturationValue));
    }

    public String getAddSaturation(float saturationValue) {
        return addSaturation.replace("%saturation%", String.valueOf(saturationValue));
    }

    public String getRemoveSaturation(float saturationValue) {
        return removeSaturation.replace("%saturation%", String.valueOf(saturationValue));
    }
    public String getBrightness(float brightnessValue) {
        return brightness.replace("%brightness%", String.valueOf(brightnessValue));
    }

    public String getAddBrightness(float brightnessValue) {
        return addBrightness.replace("%brightness%", String.valueOf(brightnessValue));
    }

    public String getRemoveBrightness(float brightnessValue) {
        return removeBrightness.replace("%brightness%", String.valueOf(brightnessValue));
    }

    public String getHueRangeError() {
        return hueRangeError;
    }

    public String getSaturationRangeError() {
        return saturationRangeError;
    }

    public String getBrightnessRangeError() {
        return brightnessRangeError;
    }

    public String getRedRangeError() {
        return redRangeError;
    }

    public String getGreenRangeError() {
        return greenRangeError;
    }

    public String getBlueRangeError() {
        return blueRangeError;
    }

    public String getColorDeleteSuccessful() {
        return colorDeleteSuccessful;
    }

    public String getColorDeleteError() {
        return colorDeleteError;
    }

    public String getFreeModeEnable() {
        return freeModeEnable;
    }

    public String getFreeModeDisable() {
        return freeModeDisable;
    }

    public String getFreeModeAlreadyEnabled() {
        return freeModeAlreadyEnabled;
    }

    public String getFreeModeAlreadyDisabled() {
        return freeModeAlreadyDisabled;
    }
}
