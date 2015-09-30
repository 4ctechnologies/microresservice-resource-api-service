package be.foreseegroup.micro.resourceservice.resourceapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Kaj on 30/09/15.
 */


@RestController
@RequestMapping("/c")
public class CustomerSideService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerSideService.class);

    @Autowired
    private ServiceUtils util;

    private RestTemplate restTemplate = new RestTemplate();


    /**
     * Forwards for CUSTOMER
     */
    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    ResponseEntity<Iterable<Object>> getAllCustomers() {
        LOG.debug("Will call getAllCustomers with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers";
        LOG.debug("getAllCustomers from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> customers = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return customers;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}")
    ResponseEntity<Object> getCustomerById(@PathVariable String customerId) {
        LOG.debug("Will call getCustomerById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("getCustomerById from URL: {}", url);

        ResponseEntity<Object> customer = restTemplate.getForEntity(url, Object.class);
        return customer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/customers")
    ResponseEntity<Object> createCustomer(@RequestBody Object customer) {
        LOG.debug("Will call createCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers";
        LOG.debug("createCustomer from URL: {}", url);

        ResponseEntity<Object> resultCustomer = restTemplate.postForEntity(url, customer, Object.class);
        return resultCustomer;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/customers/{customerId}")
    ResponseEntity<Object> updateCustomer(@PathVariable String customerId, @RequestBody Object customer, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("updateCustomer from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(customer, headers);
        ResponseEntity<Object> resultCustomer = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultCustomer;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/customers/{customerId}")
    ResponseEntity<Object> deleteCustomer(@PathVariable String customerId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("deleteCustomer from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultCustomer = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultCustomer;
    }






    /**
     * Forwards for CONSULTANT
     */
    @RequestMapping(method = RequestMethod.GET, value = "/consultants")
    ResponseEntity<Iterable<Object>> getAllConsultants() {
        LOG.debug("Will call getAllConsultants with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("getAllConsultants from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> consultants = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return consultants;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/consultants/{consultantId}")
    ResponseEntity<Object> getConsultantById(@PathVariable String consultantId) {
        LOG.debug("Will call getConsultantById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("getConsultantById from URL: {}", url);

        ResponseEntity<Object> consultant = restTemplate.getForEntity(url, Object.class);
        return consultant;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/consultants")
    ResponseEntity<Object> createConsultant(@RequestBody Object consultant) {
        LOG.debug("Will call createConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("createConsultant from URL: {}", url);

        ResponseEntity<Object> resultConsultant = restTemplate.postForEntity(url, consultant, Object.class);
        return resultConsultant;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/consultants/{consultantId}")
    ResponseEntity<Object> updateConsultant(@PathVariable String consultantId, @RequestBody Object consultant, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("updateConsultant from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(consultant, headers);
        ResponseEntity<Object> resultConsultant = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultConsultant;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/consultants/{consultantId}")
    ResponseEntity<Object> deleteConsultant(@PathVariable String consultantId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("deleteConsultant from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultConsultant = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultConsultant;
    }





    /**
     * Forwards for ASSIGNMENT
     */
    @RequestMapping(method = RequestMethod.GET, value = "/assignments")
    ResponseEntity<Iterable<Object>> getAllAssignments() {
        LOG.debug("Will call getAllAssignments with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments";
        LOG.debug("getAllAssignments from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> assignments = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return assignments;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/assignments/{assignmentId}")
    ResponseEntity<Object> getAssignmentById(@PathVariable String assignmentId) {
        LOG.debug("Will call getAssignmentById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("getAssignmentById from URL: {}", url);

        ResponseEntity<Object> assignment = restTemplate.getForEntity(url, Object.class);
        return assignment;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/assignments")
    ResponseEntity<Object> createAssignment(@RequestBody Object assignment) {
        LOG.debug("Will call createAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments";
        LOG.debug("createAssignment from URL: {}", url);

        ResponseEntity<Object> resultAssignment = restTemplate.postForEntity(url, assignment, Object.class);
        return resultAssignment;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/assignments/{assignmentId}")
    ResponseEntity<Object> updateAssignment(@PathVariable String assignmentId, @RequestBody Object assignment, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("updateAssignment from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(assignment, headers);
        ResponseEntity<Object> resultAssignment = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultAssignment;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/assignments/{assignmentId}")
    ResponseEntity<Object> deleteAssignment(@PathVariable String assignmentId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("deleteAssignment from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultAssignment = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultAssignment;
    }

}
