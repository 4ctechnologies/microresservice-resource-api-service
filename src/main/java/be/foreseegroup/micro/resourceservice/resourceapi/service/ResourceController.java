package be.foreseegroup.micro.resourceservice.resourceapi.service;

import be.foreseegroup.micro.resourceservice.resourceapi.assembler.*;
import be.foreseegroup.micro.resourceservice.resourceapi.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Kaj on 6/10/15.
 */

@RestController
public class ResourceController {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ServiceUtils util;

    @Autowired
    private AssignmentResourceAssembler assignmentAssembler;
    @Autowired
    private ConsultantResourceAssembler consultantAssembler;
    @Autowired
    private ContractResourceAssembler contractAssembler;
    @Autowired
    private UnitResourceAssembler unitAssembler;
    @Autowired
    private CustomerResourceAssembler customerAssembler;


    private RestTemplate restTemplate = new RestTemplate();


    public ResourceController() {
        /**
         * Set the behaviour of the restTemplate that it does not throw exceptions (causing hystrix to break)
         * on error HttpStatus 4xx codes
         */
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // do nothing, or something
            }
        });
    }

    //@HystrixCommand(fallbackMethod = "consultantsFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/consultants")
    public ResponseEntity<Resources<Resource<Consultant>>> getAllConsultants() {
        LOG.debug("Will call getAllConsultants with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("getAllConsultants from URL: {}", url);

        ParameterizedTypeReference<Iterable<Consultant>> responseType = new ParameterizedTypeReference<Iterable<Consultant>>() {};
        ResponseEntity<Iterable<Consultant>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Consultant> consultants = response.getBody();
            Resources<Resource<Consultant>> resources = consultantAssembler.toResource(consultants);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    //@HystrixCommand(fallbackMethod = "consultantFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/consultants/{consultantId}")
    public ResponseEntity<Resource<Consultant>> getConsultantById(@PathVariable String consultantId) {
        LOG.debug("Will call getConsultantById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("getConsultantById from URL: {}", url);

        ResponseEntity<Consultant> response = restTemplate.getForEntity(url, Consultant.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Consultant consultant = response.getBody();
            Resource<Consultant> resource = consultantAssembler.toResource(consultant);
            return new ResponseEntity<>(resource, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/consultants/{consultantId}/contracts")
    public ResponseEntity<Resources<Resource<Contract>>> getContractsByConsultantId(@PathVariable String consultantId) {
        LOG.debug("Will call getContractsByConsultantId with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/bycid/"+consultantId;
        LOG.debug("getContractsByConsultantId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Contract>> responseType = new ParameterizedTypeReference<Iterable<Contract>>() {};
        ResponseEntity<Iterable<Contract>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Contract> contracts = response.getBody();
            Resources<Resource<Contract>> resources = contractAssembler.toResource(contracts);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/consultants/{consultantId}/assignments")
    public ResponseEntity<Resources<Resource<Assignment>>> getAssignmentsByConsultantId(@PathVariable String consultantId) {
        LOG.debug("Will call getAssignmentsByConsultantId with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/bycid/"+consultantId;
        LOG.debug("getAssignmentsByConsultantId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Assignment> assignments = response.getBody();
            Resources<Resource<Assignment>> resources = assignmentAssembler.toResource(assignments);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }

    
    
    @RequestMapping(method = RequestMethod.POST, value = "/consultants")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createConsultant(@RequestBody Object consultant) {
        LOG.debug("Will call createConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("createConsultant from URL: {}", url);

        ResponseEntity<Object> resultConsultant = restTemplate.postForEntity(url, consultant, Object.class);
        return resultConsultant;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/consultants/{consultantId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> updateConsultant(@PathVariable String consultantId, @RequestBody Object consultant, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("updateConsultant from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(consultant, headers);
        ResponseEntity<Object> resultConsultant = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultConsultant;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/consultants/{consultantId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteConsultant(@PathVariable String consultantId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("deleteConsultant from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultConsultant = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultConsultant;
    }
    


















    //@HystrixCommand(fallbackMethod = "unitsFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/units")
    public ResponseEntity<Resources<Resource<Unit>>> getAllUnits() {
        LOG.debug("Will call getAllUnits with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units";
        LOG.debug("getAllUnits from URL: {}", url);

        ParameterizedTypeReference<Iterable<Unit>> responseType = new ParameterizedTypeReference<Iterable<Unit>>() {};
        ResponseEntity<Iterable<Unit>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Unit> units = response.getBody();
            Resources<Resource<Unit>> resources = unitAssembler.toResource(units);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    //@HystrixCommand(fallbackMethod = "unitFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/units/{unitId}")
    public ResponseEntity<Resource<Unit>> getUnitById(@PathVariable String unitId) {
        LOG.debug("Will call getUnitById with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units/"+unitId;
        LOG.debug("getUnitById from URL: {}", url);

        ResponseEntity<Unit> response = restTemplate.getForEntity(url, Unit.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Unit unit = response.getBody();
            Resource<Unit> resource = unitAssembler.toResource(unit);
            return new ResponseEntity<>(resource, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/units/{unitId}/contracts")
    public ResponseEntity<Resources<Resource<Contract>>> getContractsByUnitId(@PathVariable String unitId) {
        LOG.debug("Will call getContractsByUnitId with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/byuid/"+unitId;
        LOG.debug("getContractsByUnitId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Contract>> responseType = new ParameterizedTypeReference<Iterable<Contract>>() {};
        ResponseEntity<Iterable<Contract>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Contract> contracts = response.getBody();
            Resources<Resource<Contract>> resources = contractAssembler.toResource(contracts);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }

    /**
     * createUnit(Object unit)
     *
     * POST Request to create a new Unit
     * @param unit: This is the Unit object that has to be added
     * @return: Object - In this case, Object represents the added Unit object
     */
    @RequestMapping(method = RequestMethod.POST, value = "/units")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createUnit(@RequestBody Object unit) {
        LOG.debug("Will call createUnit with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units";
        LOG.debug("createUnit from URL: {}", url);

        ResponseEntity<Object> resultUnit = restTemplate.postForEntity(url, unit, Object.class);
        return resultUnit;
    }

    /**
     * updateUnit(..)
     *
     * PUT Request to update an existing Unit
     * @param unitId: The Id of the Unit that needs to be updated
     * @param unit: The Unit object with the updated content that needs to be saved
     * @param headers: The Http Headers (the back-end needs this)
     * @return: Object - In this case, Object represents the updated Unit object
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/units/{unitId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> updateUnit(@PathVariable String unitId, @RequestBody Object unit, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateUnit with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units/"+unitId;
        LOG.debug("updateUnit from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(unit, headers);
        ResponseEntity<Object> resultUnit = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultUnit;
    }

    /**
     * deleteUnit(..)
     *
     * DELETE Request to delete an existing Unit
     * @param unitId: The Id of the Unit that needs to be deleted
     * @param headers: The Http Headers (the back-end needs this)
     * @return: Object - In this case, Object represents the deleted Unit object
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/units/{unitId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteUnit(@PathVariable String unitId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteUnit with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units/"+unitId;
        LOG.debug("deleteUnit from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultUnit = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultUnit;
    }
    
    
    
    





    //@HystrixCommand(fallbackMethod = "customersFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    public ResponseEntity<Resources<Resource<Customer>>> getAllCustomers() {
        LOG.debug("Will call getAllCustomers with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers";
        LOG.debug("getAllCustomers from URL: {}", url);

        ParameterizedTypeReference<Iterable<Customer>> responseType = new ParameterizedTypeReference<Iterable<Customer>>() {};
        ResponseEntity<Iterable<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Customer> customers = response.getBody();
            Resources<Resource<Customer>> resources = customerAssembler.toResource(customers);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    //@HystrixCommand(fallbackMethod = "customerFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}")
    public ResponseEntity<Resource<Customer>> getCustomerById(@PathVariable String customerId) {
        LOG.debug("Will call getCustomerById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("getCustomerById from URL: {}", url);

        ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Customer customer = response.getBody();
            Resource<Customer> resource = customerAssembler.toResource(customer);
            return new ResponseEntity<>(resource, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}/assignments")
    public ResponseEntity<Resources<Resource<Assignment>>> getAssignmentsByCustomerId(@PathVariable String customerId) {
        LOG.debug("Will call getAssignmentsByCustomerId with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/bycuid/"+customerId;
        LOG.debug("getAssignmentsByCustomerId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Assignment> assignments = response.getBody();
            Resources<Resource<Assignment>> resources = assignmentAssembler.toResource(assignments);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    @RequestMapping(method = RequestMethod.POST, value = "/customers")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createCustomer(@RequestBody Object customer) {
        LOG.debug("Will call createCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers";
        LOG.debug("createCustomer from URL: {}", url);

        ResponseEntity<Object> resultCustomer = restTemplate.postForEntity(url, customer, Object.class);
        return resultCustomer;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/customers/{customerId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
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
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteCustomer(@PathVariable String customerId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("deleteCustomer from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultCustomer = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultCustomer;
    }
    
    
    
    






    //@HystrixCommand(fallbackMethod = "contractsFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/contracts")
    public ResponseEntity<Resources<Resource<Contract>>> getAllContracts() {
        LOG.debug("Will call getAllContracts with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts";
        LOG.debug("getAllContracts from URL: {}", url);

        ParameterizedTypeReference<Iterable<Contract>> responseType = new ParameterizedTypeReference<Iterable<Contract>>() {};
        ResponseEntity<Iterable<Contract>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Contract> contracts = response.getBody();
            Resources<Resource<Contract>> resources = contractAssembler.toResource(contracts);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    //@HystrixCommand(fallbackMethod = "contractFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/contracts/{contractId}")
    public ResponseEntity<Resource<Contract>> getContractById(@PathVariable String contractId) {
        LOG.debug("Will call getContractById with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/"+contractId;
        LOG.debug("getContractById from URL: {}", url);

        ResponseEntity<Contract> response = restTemplate.getForEntity(url, Contract.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Contract contract = response.getBody();
            Resource<Contract> resource = contractAssembler.toResource(contract);
            return new ResponseEntity<>(resource, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());

    }

    @RequestMapping(method = RequestMethod.POST, value = "/contracts")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createContract(@RequestBody Object contract) {
        LOG.debug("Will call createContract with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts";
        LOG.debug("createContract from URL: {}", url);

        ResponseEntity<Object> resultContract = restTemplate.postForEntity(url, contract, Object.class);
        return resultContract;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/contracts/{contractId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> updateContract(@PathVariable String contractId, @RequestBody Object contract, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call updateContract with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/"+contractId;
        LOG.debug("updateContract from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(contract, headers);
        ResponseEntity<Object> resultContract = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
        return resultContract;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/contracts/{contractId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteContract(@PathVariable String contractId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteContract with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/"+contractId;
        LOG.debug("deleteContract from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultContract = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultContract;
    }
    //@RequestMapping(method = RequestMethod.GET, value = "/contracts")
    //@RequestMapping(method = RequestMethod.GET, value = "/contracts/{contractId}")







    //@HystrixCommand(fallbackMethod = "assignmentsFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/assignments")
    public ResponseEntity<Resources<Resource<Assignment>>> getAllAssignments() {
        LOG.debug("Will call getAllAssignments with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments";
        LOG.debug("getAllAssignments from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            Iterable<Assignment> assignments = response.getBody();
            Resources<Resource<Assignment>> resources = assignmentAssembler.toResource(assignments);
            return new ResponseEntity<>(resources, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());
    }


    //@HystrixCommand(fallbackMethod = "assignmentFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/assignments/{assignmentId}")
    public ResponseEntity<Resource<Assignment>> getAssignmentById(@PathVariable String assignmentId) {
        LOG.debug("Will call getAssignmentById with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("getAssignmentById from URL: {}", url);

        ResponseEntity<Assignment> response = restTemplate.getForEntity(url, Assignment.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Assignment assignment = response.getBody();
            Resource<Assignment> resource = assignmentAssembler.toResource(assignment);
            return new ResponseEntity<>(resource, response.getHeaders(), response.getStatusCode());
        }
        return new ResponseEntity<>(response.getHeaders(), response.getStatusCode());

    }


    @RequestMapping(method = RequestMethod.POST, value = "/assignments")
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createAssignment(@RequestBody Object assignment) {
        LOG.debug("Will call createAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments";
        LOG.debug("createAssignment from URL: {}", url);

        ResponseEntity<Object> resultAssignment = restTemplate.postForEntity(url, assignment, Object.class);
        return resultAssignment;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/assignments/{assignmentId}")
    //@HystrixCommand(fallbackMethod = "objectFallback")
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
    //@HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteAssignment(@PathVariable String assignmentId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("customersidecomposite");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("deleteAssignment from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultAssignment = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultAssignment;
    }
    //@RequestMapping(method = RequestMethod.GET, value = "/assignments")
    //@RequestMapping(method = RequestMethod.GET, value = "/assignments/{assignmentId}")


    /**
     * Fallback methods
     */
    ResponseEntity<Resource<Consultant>> consultantFallback(String consultantId) {
        return consultantFallback();
    }
    ResponseEntity<Resource<Consultant>> consultantFallback() {
        LOG.warn("Using fallback method for getConsultantById");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
    ResponseEntity<Resources<Resource<Consultant>>> consultantsFallback() {
        LOG.warn("Using fallback method for getAllConsultants");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

}
