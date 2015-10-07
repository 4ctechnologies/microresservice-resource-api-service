package be.foreseegroup.micro.resourceservice.resourceapi.assembler;

import be.foreseegroup.micro.resourceservice.resourceapi.model.Consultant;
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
public class ConsultantResourceAssembler implements ResourceAssembler<Consultant, Resource<Consultant>> {

    public Resources<Resource<Consultant>> toResource(Iterable<Consultant> consultants) {
        ArrayList<Consultant> consultantsList = new ArrayList<>();
        for (Consultant consultant : consultants) {
            consultantsList.add(consultant);
        }

        List<Resource<Consultant>> resourceList = consultantsList.stream().map(consultant -> toResource(consultant)).collect(Collectors.toList());
        Resources<Resource<Consultant>> resources = new Resources<>(resourceList);

        //Add links
        resources.add(linkTo(methodOn(ResourceController.class).getAllConsultants()).withSelfRel());

        return resources;
    }


    public Resource<Consultant> toResource(Consultant consultant) {
        Resource<Consultant> resource = new Resource<>(consultant);

        //Add links
        resource.add(linkTo(methodOn(ResourceController.class).getConsultantById(consultant.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourceController.class).getAssignmentsByConsultantId(consultant.getId())).withRel("assignments"));
        resource.add(linkTo(methodOn(ResourceController.class).getContractsByConsultantId(consultant.getId())).withRel("contracts"));

        return resource;
    }


}