package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상단에 리드 온리를 적용 하였어도 가까운 @이 우선권을 가지고있다.
    //상품 등록
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //모든 상품 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //상품 하나 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
