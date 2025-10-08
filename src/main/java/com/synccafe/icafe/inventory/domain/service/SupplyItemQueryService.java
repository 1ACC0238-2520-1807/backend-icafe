package com.synccafe.icafe.inventory.domain.service;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetSupplyItemByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SupplyItemQueryService {
    List<SupplyItem> handle(GetAllSupplyItemsQuery query);
    Optional<SupplyItem> handle(GetSupplyItemByIdQuery query);
}