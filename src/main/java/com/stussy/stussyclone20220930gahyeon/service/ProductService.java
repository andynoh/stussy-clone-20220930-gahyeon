package com.stussy.stussyclone20220930gahyeon.service;


import com.stussy.stussyclone20220930gahyeon.dto.CollectionListRespDto;

import java.util.List;

public interface ProductService {
    public List<CollectionListRespDto> getProductList(String category, int page) throws Exception;
}
