package com.ycl.module.card.service;

import com.ycl.module.card.entites.ImageInfoEntity;

import javax.validation.constraints.NotNull;

public interface ImageService {
    ImageInfoEntity getInfo (@NotNull Long imageId);
}
