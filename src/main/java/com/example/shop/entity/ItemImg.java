package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name="item_img")
@Getter @Setter
public class ItemImg extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="item_img_id")
    private Long id; // PK

    private String imgName; // name of image

    private String oriImgName; // Original name of image

    private String imgUrl; // Url of image

    private String repImgYn; // Representative image flag (Y/N)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item; // There can be more than an image for each Item


    public void updateItemImg(
            String oriImgName, String imgName, String imgUrl
    ){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
