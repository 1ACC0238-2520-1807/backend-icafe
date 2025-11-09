package com.synccafe.icafe.purchasing.application.internal.commandservices;

import com.synccafe.icafe.product.domain.model.valueobjects.BranchId;
import com.synccafe.icafe.purchasing.domain.model.aggregates.PurchaseOrder;
import com.synccafe.icafe.purchasing.domain.model.commands.CreatePurchaseOrderCommand;
import com.synccafe.icafe.purchasing.domain.services.PurchaseOrderCommandService;
import com.synccafe.icafe.purchasing.infrastructure.persistence.jpa.repositories.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderCommandServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    @Transactional
    public Optional<PurchaseOrder> handle(CreatePurchaseOrderCommand command) {
        var purchaseOrder = new PurchaseOrder(command);
        var savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        
        // Los eventos de dominio se publican automáticamente por Spring Data JPA
        // cuando se guarda la entidad que extiende AbstractAggregateRoot
        
        return Optional.of(savedPurchaseOrder);
    }

    @Override
    @Transactional
    public Optional<PurchaseOrder> confirmPurchaseOrder(Long purchaseOrderId, Long branchId) {
        var branchIdVO = new BranchId(branchId);
        var purchaseOrder = purchaseOrderRepository.findByIdAndBranchId(purchaseOrderId, branchIdVO);
        
        if (purchaseOrder.isEmpty()) {
            return Optional.empty();
        }
        
        purchaseOrder.get().confirm();
        return Optional.of(purchaseOrderRepository.save(purchaseOrder.get()));
    }

    @Override
    @Transactional
    public Optional<PurchaseOrder> completePurchaseOrder(Long purchaseOrderId, Long branchId) {
        var branchIdVO = new BranchId(branchId);
        var purchaseOrder = purchaseOrderRepository.findByIdAndBranchId(purchaseOrderId, branchIdVO);
        
        if (purchaseOrder.isEmpty()) {
            return Optional.empty();
        }
        
        purchaseOrder.get().complete();
        return Optional.of(purchaseOrderRepository.save(purchaseOrder.get()));
    }

    @Override
    @Transactional
    public Optional<PurchaseOrder> cancelPurchaseOrder(Long purchaseOrderId, Long branchId) {
        var branchIdVO = new BranchId(branchId);
        var purchaseOrder = purchaseOrderRepository.findByIdAndBranchId(purchaseOrderId, branchIdVO);
        
        if (purchaseOrder.isEmpty()) {
            return Optional.empty();
        }
        
        purchaseOrder.get().cancel();
        return Optional.of(purchaseOrderRepository.save(purchaseOrder.get()));
    }
}