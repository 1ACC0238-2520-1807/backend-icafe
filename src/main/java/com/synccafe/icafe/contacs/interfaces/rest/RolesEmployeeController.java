package com.synccafe.icafe.contacs.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ap/v1/rolesemployee", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles-Employee", description = "Role-Employee Management Endpoints")
public class RolesEmployeeController {

}
