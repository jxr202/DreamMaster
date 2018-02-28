package com.jxr202.dreammaster;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Jxr35 on 2018/2/27
 */

public class Dream implements Serializable {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("des")
    String des;

    @SerializedName("list")
    String[] list;

    public Dream () {

    }

    public Dream(String id, String title, String des) {
        this.id = id;
        this.title = title;
        this.des = des;
    }

    @Override
    public String toString() {
        return "Dream{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", list=" + Arrays.toString(list) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dream dream = (Dream) o;

        if (id != null ? !id.equals(dream.id) : dream.id != null) return false;
        if (title != null ? !title.equals(dream.title) : dream.title != null) return false;
        return des != null ? des.equals(dream.des) : dream.des == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (des != null ? des.hashCode() : 0);
        return result;
    }
}
