package com.baykris.tr_crypto;

public class Coin {
    private String name;
    private String symbol;
    private String price_rp;
    private String percent_change_1h;
    private String iconUrl;

    public Coin(){

    }
    public Coin(String name, String symbol, String price_rp, String percent_change_1h, String iconUrl){
        this.name = name;
        this.symbol = symbol;
        this.price_rp = price_rp;
        this.percent_change_1h = percent_change_1h;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice_rp() {
        return price_rp;
    }

    public void setPrice_rp(String price_rp) {
        this.price_rp = price_rp;
    }

    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(String percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
