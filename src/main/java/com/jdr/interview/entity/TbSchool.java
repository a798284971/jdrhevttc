package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_school")
public class TbSchool implements Serializable {
    @Id
    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_type")
    private Integer schoolType;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "display_order")
    private String displayOrder;

    private static final long serialVersionUID = 1L;

    /**
     * @return school_id
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return school_name
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * @return school_type
     */
    public Integer getSchoolType() {
        return schoolType;
    }

    /**
     * @param schoolType
     */
    public void setSchoolType(Integer schoolType) {
        this.schoolType = schoolType;
    }

    /**
     * @return area_id
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    /**
     * @return area_name
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * @return display_order
     */
    public String getDisplayOrder() {
        return displayOrder;
    }

    /**
     * @param displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder == null ? null : displayOrder.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", schoolId=").append(schoolId);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", schoolType=").append(schoolType);
        sb.append(", areaId=").append(areaId);
        sb.append(", areaName=").append(areaName);
        sb.append(", displayOrder=").append(displayOrder);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}