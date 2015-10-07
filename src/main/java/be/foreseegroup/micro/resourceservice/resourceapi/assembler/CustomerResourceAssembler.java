package be.foreseegroup.micro.resourceservice.resourceapi.assembler;

import be.foreseegroup.micro.resourceservice.resourceapi.model.Customer;
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
public class CustomerResourceAssembler implements ResourceAssembler<Customer, Resource<Customer>> {

    public Resources<Resource<Customer>> toResource(Iterable<Customer> customers) {
        ArrayList<Customer> customersList = new ArrayList<>();
        for (Customer customer : customers) {
            customersList.add(customer);
        }

        List<Resource<Customer>> resourceList = customersList.stream().map(customer -> toResource(customer)).collect(Collectors.toList());
        Resources<Resource<Customer>> resources = new Resources<>(resourceList);

        //Add links
        resources.add(linkTo(methodOn(ResourceController.class).getAllCustomers()).withSelfRel());
        return resources;
    }


    public Resource<Customer> toResource(Customer customer) {
        Resource<Customer> resource = new Resource<>(customer);

        //add links
        resource.add(linkTo(methodOn(ResourceController.class).getCustomerById(customer.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourceController.class).getAssignmentsByCustomerId(customer.getId())).withRel("assignments"));

        return resource;
    }


}