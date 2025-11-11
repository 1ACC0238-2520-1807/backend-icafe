package com.synccafe.icafe.product.domain.services;

import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetSupplyItemByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SupplyItemQueryService {
    List<SupplyItem> handle(GetAllSupplyItemsQuery query);
    Optional<SupplyItem> handle(GetSupplyItemByIdQuery query);
    List<SupplyItem> handle(Long branchId);
}
