package be.foreseegroup.micro.resourceservice.resourceapi.assembler;

import be.foreseegroup.micro.resourceservice.resourceapi.model.Contract;
import be.foreseegroup.micro.resourceservice.resourceapi.service.ResourceController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Kaj on 6/10/15.
 */
@Component
public class ContractResourceAssembler implements ResourceAssembler<Contract, Resource<Contract>> {

    public Resources<Resource<Contract>> toResource(Iterable<Contract> contracts) {
        ArrayList<Contract> contractsList = new ArrayList<>();
        for (Contract contract : contracts) {
            contractsList.add(contract);
        }

        List<Resource<Contract>> resourceList = contractsList.stream().map(contract -> toResource(contract)).collect(Collectors.toList());
        Resources<Resource<Contract>> resources = new Resources<>(resourceList);

        //Add links
        resources.add(linkTo(methodOn(ResourceController.class).getAllContracts()).withSelfRel());

        return resources;
    }


    public Resource<Contract> toResource(Contract contract) {
        Resource<Contract> resource = new Resource<>(contract);

        //add links
        resource.add(linkTo(methodOn(ResourceController.class).getContractById(contract.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourceController.class).getConsultantById(contract.getConsultantId())).withRel("consultant"));
        resource.add(linkTo(methodOn(ResourceController.class).getUnitById(contract.getUnitId())).withRel("unit"));

        return resource;
    }


}