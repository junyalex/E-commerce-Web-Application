package com.example.shop.repository;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.dto.QMainItemDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.QItem;
import com.example.shop.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Custom implementation of {@link ItemRepositoryCustom} using QueryDSL.
 * Provides dynamic query logic for item management in admin view.
 */
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * Filters items based on their selling status.
     *
     * @param itemSellStatus the selling status to filter by
     * @return BooleanExpression representing the condition, or null if not applicable
     */
    private BooleanExpression searchSellStatusEq(ItemSellStatus itemSellStatus) {
        return itemSellStatus == null ? null : QItem.item.itemSellStatus.eq(itemSellStatus);
    }

    /**
     * Filters items registered after a specific relative date.
     *
     * @param searchDateType relative date type ("1d", "1w", "1m", "6m", "all")
     * @return BooleanExpression representing the condition, or null if 'all' or null input
     */
    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QItem.item.regTime.after(dateTime);
    }

    /**
     * Filters items by name or createdBy fields using a LIKE query.
     *
     * @param searchBy    field to search by ("itemName" or "createdBy")
     * @param searchQuery keyword to match
     * @return BooleanExpression for LIKE query, or null if input is invalid
     */
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("itemName", searchBy)) {
            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    /**
     * Retrieves a paginated list of items for the admin interface based on search criteria.
     *
     * @param itemSearchDto DTO containing search filters
     * @param pageable      pagination and sorting information
     * @return a page of filtered items
     */
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        // Computes all items meet the filter conditions
        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Computes number of items that meet the filter conditions
        Long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(
                        regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);

    }

    private BooleanExpression itemNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        List<MainItemDto> results = queryFactory.select(
                        new QMainItemDto(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                ).from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(item.count())
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .fetchOne();

        long totalCount = (total != null) ? total : 0L;

        return new PageImpl<>(results, pageable, totalCount);
    }
}