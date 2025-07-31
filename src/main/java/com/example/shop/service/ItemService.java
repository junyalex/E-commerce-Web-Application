package com.example.shop.service;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.dto.ItemImgDto;
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    @Value("${itemImgLocation}")
    private String itemImgLocation;

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
                                                        .toList();

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

    @Transactional(readOnly = true)
    public ItemFormDto getItemFormDto(Long itemId){

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        ArrayList<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for(ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    /**
     *
     * @param itemFormDto itemFormDto of original item
     * @param itemImgFileList list of files containing to be updated images
     * @return id of updated item
     * @throws Exception if entity is not found
     */
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds(); // IDs of existing images from the form

        // Iterate through the submitted MultipartFiles (which correspond to the 5 image slots in the form)
        for (int i = 0; i < itemImgFileList.size(); i++) {
            MultipartFile multipartFile = itemImgFileList.get(i);

            // Only process if a new file is provided for this slot
            if (!multipartFile.isEmpty()) {
                // Check if this slot corresponds to an existing image (by checking itemImgIds)
                if (i < itemImgIds.size() && itemImgIds.get(i) != null) {
                    itemImgService.updateItemImg(itemImgIds.get(i), multipartFile);
                } else {
                    ItemImg itemImg = new ItemImg();
                    itemImg.setItem(item);
                    itemImg.setRepImgYn("N"); // Default to non-representative for newly added images
                    itemImgService.saveItemImg(itemImg, multipartFile);
                }
            }

        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
