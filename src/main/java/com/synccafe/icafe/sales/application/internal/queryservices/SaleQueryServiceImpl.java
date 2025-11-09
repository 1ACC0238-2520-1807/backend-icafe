package com.synccafe.icafe.sales.application.internal.queryservices;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.queries.GetAllSalesQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSaleByIdQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSalesByBranchIdQuery;
import com.synccafe.icafe.sales.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.sales.domain.services.SaleQueryService;
import com.synccafe.icafe.sales.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

    private final SaleRepository saleRepository;

    public SaleQueryServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdQuery query) {
        return saleRepository.findById(query.saleId());
    }

    @Override
    public List<Sale> handle(GetAllSalesQuery query) {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> handle(GetSalesByBranchIdQuery query) {
        return saleRepository.findByBranchId(new BranchId(query.branchId()));
    }
}