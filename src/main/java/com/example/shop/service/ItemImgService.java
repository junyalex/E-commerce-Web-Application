package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import jakarta.persistence.EntityExistsException;
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

    /**
     * Saves image of item when ADMIN registers new Item.
     */
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
    /**
     *
     * @param itemImgId id of original image that was saved
     * @param itemImgFile new image that will be replaced with original image
     * @throws EntityExistsException if DB fails to find itemImg
     */
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {

        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityExistsException::new);

            if(StringUtils.hasText(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            if (oriImgName == null || oriImgName.isBlank()) {
                throw new IllegalArgumentException("img name is null or empty");
            }

            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }




    }
}
