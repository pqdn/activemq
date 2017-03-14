package ru.babanin.activemq;

/**
 * Created by makcim on 14.03.17.
 */
public class BoxFlag {
    boolean flag = false;

    public void setFlag() {
        this.flag = true;
    }

    public boolean isFlag() {
        return flag;
    }
}
