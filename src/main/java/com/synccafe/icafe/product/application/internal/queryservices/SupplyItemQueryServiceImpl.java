package com.synccafe.icafe.product.application.internal.queryservices;

import com.synccafe.icafe.product.domain.model.entities.SupplyItem;
import com.synccafe.icafe.product.domain.model.queries.GetAllSupplyItemByBranchIdQuery;
import com.synccafe.icafe.product.domain.model.queries.GetAllSupplyItemsQuery;
import com.synccafe.icafe.product.domain.model.queries.GetSupplyItemByIdQuery;
import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.product.domain.services.SupplyItemQueryService;
import com.synccafe.icafe.product.infrastructure.persistence.jpa.repositories.SupplyItemRepository;
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

    @Override
    public List<SupplyItem> handle(Long branchId) {
        return supplyItemRepository.findAllByBranchId(new BranchId(branchId));
    }
}
