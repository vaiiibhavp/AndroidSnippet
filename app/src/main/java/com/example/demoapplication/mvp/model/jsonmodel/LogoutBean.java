package com.example.demoapplication.mvp.model.jsonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogoutBean implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("result")
@Expose
private Boolean result;
private final static long serialVersionUID = -2966728698156093701L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public LogoutBean withStatus(Integer status) {
this.status = status;
return this;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public LogoutBean withMsg(String msg) {
this.msg = msg;
return this;
}

public Boolean getResult() {
return result;
}

public void setResult(Boolean result) {
this.result = result;
}

public LogoutBean withResult(Boolean result) {
this.result = result;
return this;
}

}