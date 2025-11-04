package com.synccafe.icafe.sales.application.internal.commandservices;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.commands.CreateSaleCommand;
import com.synccafe.icafe.sales.domain.services.SaleCommandService;
import com.synccafe.icafe.sales.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    private final SaleRepository saleRepository;

    public SaleCommandServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    @Transactional
    public Optional<Sale> handle(CreateSaleCommand command) {
        try {
            // Create the sale aggregate
            Sale sale = new Sale(command);
            
            // Save the sale
            Sale savedSale = saleRepository.save(sale);
            
            return Optional.of(savedSale);
        } catch (Exception e) {
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Sale> completeSale(Long saleId) {
        Optional<Sale> saleOpt = saleRepository.findById(saleId);
        if (saleOpt.isPresent()) {
            Sale sale = saleOpt.get();
            sale.completeSale();
            Sale savedSale = saleRepository.save(sale);
            return Optional.of(savedSale);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Sale> cancelSale(Long saleId) {
        Optional<Sale> saleOpt = saleRepository.findById(saleId);
        if (saleOpt.isPresent()) {
            Sale sale = saleOpt.get();
            sale.cancelSale();
            Sale savedSale = saleRepository.save(sale);
            return Optional.of(savedSale);
        }
        return Optional.empty();
    }
}