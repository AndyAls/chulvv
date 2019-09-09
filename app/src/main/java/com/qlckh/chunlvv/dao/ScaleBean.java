package com.qlckh.chunlvv.dao;

/**
 * @author Andy
 * @date 2018/8/18 20:26
 * Desc:
 */
public class ScaleBean {
    private String ip="";
    private String host;
    private String calibrationWeight;
    private String zeroAd;
    private String alibrationAd;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCalibrationWeight() {
        return calibrationWeight;
    }

    public void setCalibrationWeight(String calibrationWeight) {
        this.calibrationWeight = calibrationWeight;
    }

    public String getZeroAd() {
        return zeroAd;
    }

    public void setZeroAd(String zeroAd) {
        this.zeroAd = zeroAd;
    }

    public String getAlibrationAd() {
        return alibrationAd;
    }

    public void setAlibrationAd(String alibrationAd) {
        this.alibrationAd = alibrationAd;
    }
}
