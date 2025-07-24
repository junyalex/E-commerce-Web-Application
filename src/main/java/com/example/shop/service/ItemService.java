package com.example.shop.service;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    /**
     * Saves item to itemRepository
     * @return saved Item's id (PK)
     */
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // Filter out empty MultipartFile objects
        List<MultipartFile> nonEmptyItemImgFileList = itemImgFileList.stream()
                                                        .filter(file -> !file.isEmpty())
                                                        .collect(Collectors.toList());

        for(int i=0; i<nonEmptyItemImgFileList.size(); i++) { // Iterate over non-empty files
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0){
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, nonEmptyItemImgFileList.get(i));
        }
        return item.getId();
    }

}
