package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.inventory.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetSupplyItemByIdQuery;
import com.synccafe.icafe.product.domain.model.entities.SupplyItem;

import java.util.List;
import java.util.Optional;

public interface SupplyItemQueryService {
    List<SupplyItem> handle(GetAllSupplyItemsQuery query);
    Optional<SupplyItem> handle(GetSupplyItemByIdQuery query);
}
