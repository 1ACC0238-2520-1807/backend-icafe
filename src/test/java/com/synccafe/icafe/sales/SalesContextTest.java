package com.synccafe.icafe.sales;

import com.synccafe.icafe.sales.domain.model.aggregates.Sale;
import com.synccafe.icafe.sales.domain.model.commands.CreateSaleCommand;
import com.synccafe.icafe.sales.domain.model.commands.CreateSaleItemCommand;
import com.synccafe.icafe.sales.domain.model.valueobjects.SaleStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalesContextTest {

    @Test
    public void testCreateSale() {
        // Arrange
        List<CreateSaleItemCommand> items = List.of(
            new CreateSaleItemCommand(1L, 2.0, 10.50),
            new CreateSaleItemCommand(2L, 1.0, 5.25)
        );
        
        CreateSaleCommand command = new CreateSaleCommand(
            1L, // customerId
            1L, // branchId
            items,
            "Test sale"
        );

        // Act
        Sale sale = new Sale(command);

        // Assert
        assertNotNull(sale);
        assertEquals(1L, sale.getCustomerId().customerId());
        assertEquals(1L, sale.getBranchId().branchId());
        assertEquals(2, sale.getItems().size());
        assertEquals(SaleStatus.PENDING, sale.getStatus());
        assertEquals("Test sale", sale.getNotes());
        
        // Verify total calculation
        double expectedTotal = 10.50 * 2.0 + 5.25 * 1.0;
        assertEquals(expectedTotal, sale.getTotalAmount().amount(), 1e-6);
    }

    @Test
    public void testCompleteSale() {
        // Arrange
        List<CreateSaleItemCommand> items = List.of(
            new CreateSaleItemCommand(1L, 1.0, 10.00)
        );
        
        CreateSaleCommand command = new CreateSaleCommand(1L, 1L, items, "Test");
        Sale sale = new Sale(command);

        // Act
        sale.completeSale();

        // Assert
        assertEquals(SaleStatus.COMPLETED, sale.getStatus());
    }

    @Test
    public void testCancelSale() {
        // Arrange
        List<CreateSaleItemCommand> items = List.of(
            new CreateSaleItemCommand(1L, 1.0, 10.00)
        );
        
        CreateSaleCommand command = new CreateSaleCommand(1L, 1L, items, "Test");
        Sale sale = new Sale(command);

        // Act
        sale.cancelSale();

        // Assert
        assertEquals(SaleStatus.CANCELLED, sale.getStatus());
    }
}