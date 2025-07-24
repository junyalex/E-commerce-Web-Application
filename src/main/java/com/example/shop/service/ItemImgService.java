package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String originalFilename = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // upload File
        if (StringUtils.hasText(originalFilename)) {
            imgName = fileService.uploadFile(itemImgLocation, originalFilename, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        // Save image information
        itemImg.updateItemImg(originalFilename, imgName, imgUrl);
        itemImgRepository.save(itemImg);

    }
}
