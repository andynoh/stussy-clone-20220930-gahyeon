package com.stussy.stussyclone20220930gahyeon.api.admin;


import com.stussy.stussyclone20220930gahyeon.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930gahyeon.aop.annotation.ValidAspect;
import com.stussy.stussyclone20220930gahyeon.dto.CMRespDto;
import com.stussy.stussyclone20220930gahyeon.dto.admin.ProductRegisterReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class ProductAdminApi {

    @LogAspect
    @ValidAspect
    @PostMapping ("/api/admin/product")
    public ResponseEntity<?> registerProductMst(@Valid @RequestBody ProductRegisterReqDto productRegisterReqDto,
                                                BindingResult bindingResult){

        return ResponseEntity.created(null)
                .body(new CMRespDto<>("Register Successfully",null));
    }
}
