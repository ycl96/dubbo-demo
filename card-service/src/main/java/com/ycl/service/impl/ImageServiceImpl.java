package com.ycl.service.impl;

import com.ycl.module.card.entites.ImageInfoEntity;
import com.ycl.module.card.service.ImageService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/17 16:31
 * @description: 单图相关实现类
 * @modified By:
 * @version: :
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public ImageInfoEntity getInfo (@NotNull Long imageId){
        ImageInfoEntity imageInfoEntity = new ImageInfoEntity();
        imageInfoEntity.setImageId(imageId);
        imageInfoEntity.setCreateUser("test");
        imageInfoEntity.setImageUrl("test");
        return imageInfoEntity;
    }
}
