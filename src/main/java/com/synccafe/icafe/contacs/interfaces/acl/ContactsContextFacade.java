package com.synccafe.icafe.contacs.interfaces.acl;

import com.synccafe.icafe.contacs.domain.model.aggregates.ContactPortfolio;
import com.synccafe.icafe.contacs.domain.model.commands.CreatePortfolioCommand;
import com.synccafe.icafe.contacs.domain.model.commands.CreateProviderContactCommand;
import com.synccafe.icafe.contacs.domain.model.entities.ProviderContact;
import com.synccafe.icafe.contacs.domain.model.queries.GetProviderContactByIdQuery;
import com.synccafe.icafe.contacs.domain.service.PortfolioCommandService;
import com.synccafe.icafe.contacs.domain.service.ProviderContactCommandService;
import com.synccafe.icafe.contacs.domain.service.ProviderContactQueryService;
import org.springframework.stereotype.Service;

@Service
public class ContactsContextFacade {
    private final ProviderContactCommandService providerContactCommandService;
    private final ProviderContactQueryService providerContactQueryService;
    private final PortfolioCommandService portfolioCommandService;

    public ContactsContextFacade(ProviderContactCommandService providerContactCommandService,
                                    ProviderContactQueryService providerContactQueryService,
                                    PortfolioCommandService portfolioCommandService) {
          this.providerContactCommandService = providerContactCommandService;
          this.providerContactQueryService = providerContactQueryService;
          this.portfolioCommandService = portfolioCommandService;
     }

    //CREAS Y RETORNAS EL ID DEL PROVEEDOR
    public Long createProviderContact(CreateProviderContactCommand command) {
      var providerContact= providerContactCommandService.handle(command);
      if(providerContact.isEmpty()) {
          return 0L;
      }
        return providerContact.get().getId();
    }

    //BuscarProveedorPorId
    public ProviderContact getProviderContactById(Long id) {
        return providerContactQueryService.handle(new GetProviderContactByIdQuery(id)).orElse(null);
    }

    //VERIFICA SI EL PROVEEDOR EXISTE
    public boolean existsById(Long id) {
        return providerContactQueryService.handle(new GetProviderContactByIdQuery(id)).isPresent();
    }

    //Crear Porfolio
    public ContactPortfolio createPortfolio(Long userId) {
        return portfolioCommandService.handle(new CreatePortfolioCommand(userId)).orElse(null);
    }

}
