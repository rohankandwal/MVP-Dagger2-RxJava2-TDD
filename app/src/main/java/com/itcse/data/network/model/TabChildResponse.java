package com.itcse.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class containing response from {@link com.itcse.view.home.child_fragment.ChildFragment} network
 * call.
 */
public class TabChildResponse {
    public String id;
    public String name;
    public String status;
    @SerializedName("num_likes")
    public String likeCount;
    @SerializedName("num_comments")
    public String commentCount;
    public Double price;
    public String photo;
}
