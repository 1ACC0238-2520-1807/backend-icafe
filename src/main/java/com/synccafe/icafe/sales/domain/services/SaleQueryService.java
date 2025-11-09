package com.synccafe.icafe.sales.domain.services;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.queries.GetAllSalesQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSaleByIdQuery;
import com.synccafe.icafe.sales.domain.model.queries.GetSalesByBranchIdQuery;

import java.util.List;
import java.util.Optional;

public interface SaleQueryService {
    Optional<Sale> handle(GetSaleByIdQuery query);
    List<Sale> handle(GetAllSalesQuery query);
    List<Sale> handle(GetSalesByBranchIdQuery query);
}