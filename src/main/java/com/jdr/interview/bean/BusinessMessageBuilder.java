package com.jdr.interview.bean;

/**
 * Created by SongpoLiu on 2017/7/10.
 */
public class BusinessMessageBuilder<T> {

    private BusinessMessage<T> message = new BusinessMessage<>();

    public BusinessMessageBuilder() {
        this.message.setSuccess(false);
    }

    public BusinessMessageBuilder(Boolean success) {
        this.message.setSuccess(success);
    }

    public BusinessMessageBuilder<T> code(Integer code) {
        this.message.setCode(code);

        return this;
    }

    public BusinessMessageBuilder<T> success(Boolean success) {
        this.message.setSuccess(success);
        if(success){
            this.message.setCode(200);
        }else {
            this.message.setCode(-200);
        }
        return this;
    }

    public BusinessMessageBuilder<T> msg(String msg) {
        this.message.setMsg(msg);

        return this;
    }

    public BusinessMessageBuilder<T> data(T data) {
        this.message.setData(data);

        return this;
    }

    public BusinessMessage<T> build() {
        return this.message;
    }

}
