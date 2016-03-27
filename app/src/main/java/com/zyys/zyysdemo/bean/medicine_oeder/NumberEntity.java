package com.zyys.zyysdemo.bean.medicine_oeder;

/**
 * Created by Administrator on 2016/3/13.
 */
public class NumberEntity {//(moning,afternoom,evening,sleep);

    private String moning;
    private String afternoom;
    private String evening;
    private String sleep;

    public NumberEntity(String moning) {
        this.moning = moning;
    }

    public NumberEntity(String moning, String afternoom, String evening, String sleep) {
        this.moning = moning;
        this.afternoom = afternoom;
        this.evening = evening;
        this.sleep = sleep;
    }

    public String getMoning() {
        return moning;
    }

    public void setMoning(String moning) {
        this.moning = moning;
    }

    public String getAfternoom() {
        return afternoom;
    }

    public void setAfternoom(String afternoom) {
        this.afternoom = afternoom;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    @Override
    public String toString() {
        return "NumberEntity{" +
                "moning='" + moning + '\'' +
                ", afternoom='" + afternoom + '\'' +
                ", evening='" + evening + '\'' +
                ", sleep='" + sleep + '\'' +
                '}';
    }
}
