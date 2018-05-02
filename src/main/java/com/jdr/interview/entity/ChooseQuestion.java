package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "choose_question")
public class ChooseQuestion implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer superioe;

    private String question;

    @Column(name = "A")
    private String a;

    @Column(name = "B")
    private String b;

    @Column(name = "C")
    private String c;

    @Column(name = "D")
    private String d;

    @Column(name = "hasPic")
    private Integer haspic;

    private String pic;

    private Integer type;

    private static final long serialVersionUID = 1L;

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return superioe
     */
    public Integer getSuperioe() {
        return superioe;
    }

    /**
     * @param superioe
     */
    public void setSuperioe(Integer superioe) {
        this.superioe = superioe;
    }

    /**
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    /**
     * @return A
     */
    public String getA() {
        return a;
    }

    /**
     * @param a
     */
    public void setA(String a) {
        this.a = a == null ? null : a.trim();
    }

    /**
     * @return B
     */
    public String getB() {
        return b;
    }

    /**
     * @param b
     */
    public void setB(String b) {
        this.b = b == null ? null : b.trim();
    }

    /**
     * @return C
     */
    public String getC() {
        return c;
    }

    /**
     * @param c
     */
    public void setC(String c) {
        this.c = c == null ? null : c.trim();
    }

    /**
     * @return D
     */
    public String getD() {
        return d;
    }

    /**
     * @param d
     */
    public void setD(String d) {
        this.d = d == null ? null : d.trim();
    }

    /**
     * @return hasPic
     */
    public Integer getHaspic() {
        return haspic;
    }

    /**
     * @param haspic
     */
    public void setHaspic(Integer haspic) {
        this.haspic = haspic;
    }

    /**
     * @return pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", superioe=").append(superioe);
        sb.append(", question=").append(question);
        sb.append(", a=").append(a);
        sb.append(", b=").append(b);
        sb.append(", c=").append(c);
        sb.append(", d=").append(d);
        sb.append(", haspic=").append(haspic);
        sb.append(", pic=").append(pic);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}