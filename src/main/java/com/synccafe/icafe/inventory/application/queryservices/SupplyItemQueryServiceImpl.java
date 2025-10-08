package com.synccafe.icafe.inventory.application.queryservices;

import com.synccafe.icafe.inventory.domain.model.aggregates.SupplyItem;
import com.synccafe.icafe.inventory.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.inventory.domain.model.queries.GetSupplyItemByIdQuery;
import com.synccafe.icafe.inventory.domain.service.SupplyItemQueryService;
import com.synccafe.icafe.inventory.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyItemQueryServiceImpl implements SupplyItemQueryService {

    private final SupplyItemRepository supplyItemRepository;

    public SupplyItemQueryServiceImpl(SupplyItemRepository supplyItemRepository) {
        this.supplyItemRepository = supplyItemRepository;
    }

    @Override
    public List<SupplyItem> handle(GetAllSupplyItemsQuery query) {
        return supplyItemRepository.findAll();
    }

    @Override
    public Optional<SupplyItem> handle(GetSupplyItemByIdQuery query) {
        return supplyItemRepository.findById(query.supplyItemId());
    }
}