package com.ycl.module.card.entites;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/23 17:08
 * @description: 图片信息实体类，用于内部方法间交互
 * @modified By:
 * @version: :
 */
public class ImageInfoEntity {
    private Long imageId;
    private String imageUrl;
    private String createUser;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
