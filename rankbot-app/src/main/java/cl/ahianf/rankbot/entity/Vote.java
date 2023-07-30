/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import lombok.Data;

@Data
public class Vote {
    private int vote;
    private long token;

    @Override
    public String toString() {
        return "vote: " + vote + ", token: " + token;
    }
}
