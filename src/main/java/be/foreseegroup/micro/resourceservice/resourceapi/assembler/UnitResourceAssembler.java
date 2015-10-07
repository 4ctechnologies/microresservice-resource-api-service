package be.foreseegroup.micro.resourceservice.resourceapi.assembler;

import be.foreseegroup.micro.resourceservice.resourceapi.model.Unit;
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
public class UnitResourceAssembler implements ResourceAssembler<Unit, Resource<Unit>> {

    public Resources<Resource<Unit>> toResource(Iterable<Unit> units) {
        ArrayList<Unit> unitsList = new ArrayList<>();
        for (Unit unit : units) {
            unitsList.add(unit);
        }

        List<Resource<Unit>> resourceList = unitsList.stream().map(unit -> toResource(unit)).collect(Collectors.toList());
        Resources<Resource<Unit>> resources = new Resources<>(resourceList);

        //Add links
        resources.add(linkTo(methodOn(ResourceController.class).getAllUnits()).withSelfRel());
        return resources;
    }


    public Resource<Unit> toResource(Unit unit) {
        Resource<Unit> resource = new Resource<>(unit);

        //add links
        resource.add(linkTo(methodOn(ResourceController.class).getUnitById(unit.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourceController.class).getContractsByUnitId(unit.getId())).withRel("contracts"));
        return resource;
    }


}