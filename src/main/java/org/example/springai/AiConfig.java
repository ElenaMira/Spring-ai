package org.example.springai;



public class AiConfig {
    private String platform;
    private String model;
    private Double temperature;

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getPlatform() {
        return platform;
    }

    public String getModel() {
        return model;
    }

    public Double getTemperature() {
        return temperature;
    }

}
