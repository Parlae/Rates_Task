package com.ewe.parlae.ratestask;

class CurrencyModel {
    private String currencyCode;
    private String description;
    //for using as Storage of editText values eventually.  Not pursued at the moment.
    private String currentValue;
    private Double rate;
    private int id;

    public String getCurrencyCode() { return currencyCode; }
    public String getDescription() { return description; }
    public String getCurrentValue() { return currentValue; }
    public Double getRate() { return rate; }
    public int getId() { return id; }

    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    public void setDescription(String description) { this.description = description; }
    public void setCurrentValue(String currentValue) { this.currentValue = currentValue; }
    public void setRate(Double rate) { this.rate = rate; }
    public void setId(int id) { this.id = id; }

    public CurrencyModel(int id, String currencyCode, String description, String currentValue, Double rate) {
        this.currencyCode = currencyCode;
        this.description = description;
        this.currentValue = currentValue;
        this.rate = rate;
        this.id = id;
    }


}
