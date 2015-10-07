package be.foreseegroup.micro.resourceservice.resourceapi.assembler;

import be.foreseegroup.micro.resourceservice.resourceapi.model.Assignment;
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
public class AssignmentResourceAssembler implements ResourceAssembler<Assignment, Resource<Assignment>> {

    public Resources<Resource<Assignment>> toResource(Iterable<Assignment> assignments) {
        ArrayList<Assignment> assignmentsList = new ArrayList<>();
        for (Assignment assignment : assignments) {
            assignmentsList.add(assignment);
        }

        List<Resource<Assignment>> resourceList = assignmentsList.stream().map(assignment -> toResource(assignment)).collect(Collectors.toList());
        Resources<Resource<Assignment>> resources = new Resources<>(resourceList);

        //Add links
        resources.add(linkTo(methodOn(ResourceController.class).getAllAssignments()).withSelfRel());

        return resources;
    }


    public Resource<Assignment> toResource(Assignment assignment) {
        Resource<Assignment> resource = new Resource<>(assignment);

        //add links
        resource.add(linkTo(methodOn(ResourceController.class).getAssignmentById(assignment.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourceController.class).getConsultantById(assignment.getConsultantId())).withRel("consultant"));
        resource.add(linkTo(methodOn(ResourceController.class).getCustomerById(assignment.getCustomerId())).withRel("customer"));

        return resource;
    }


}