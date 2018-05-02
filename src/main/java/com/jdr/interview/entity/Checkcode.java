package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Checkcode implements Serializable {
    @Id
    private String username;

    @Column(name = "timeStamp")
    private String timestamp;

    private Integer times;

    private String code;

    private static final long serialVersionUID = 1L;

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return timeStamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    /**
     * @return times
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * @param times
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", times=").append(times);
        sb.append(", code=").append(code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}