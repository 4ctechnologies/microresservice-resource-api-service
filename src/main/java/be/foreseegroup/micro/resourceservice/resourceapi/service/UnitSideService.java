package be.foreseegroup.micro.resourceservice.resourceapi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Kaj on 30/09/15.
 */
@RestController
@RequestMapping("/u")
public class UnitSideService {
    private static final Logger LOG = LoggerFactory.getLogger(UnitSideService.class);

    @Autowired
    private ServiceUtils util;

    private RestTemplate restTemplate = new RestTemplate();


    /**
     * Forwards for UNIT
     */

    /**
     * getAllUnits()
     *
     * GET Request to receive a List of all the Units
     * @return: Iterable<Object> - In this case, Object represents a Unit
     */
    @RequestMapping(method = RequestMethod.GET, value = "/units")
    @HystrixCommand(fallbackMethod = "objectsFallback")
    ResponseEntity<Iterable<Object>> getAllUnits() {
        LOG.debug("Will call getAllUnits with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units";
        LOG.debug("getAllUnits from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> units = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return units;
    }

    /**
     * getUnitById(String unitId)
     *
     * GET Request to receive a Unit specified by its Identifier
     * @param unitId: This is the Id of the Unit that is requested
     * @return: Object - In this case, Object represents a Unit
     */
    @RequestMapping(method = RequestMethod.GET, value = "/units/{unitId}")
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> getUnitById(@PathVariable String unitId) {
        LOG.debug("Will call getUnitById with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units/"+unitId;
        LOG.debug("getUnitById from URL: {}", url);

        ResponseEntity<Object> unit = restTemplate.getForEntity(url, Object.class);
        return unit;
    }

    /**
     * createUnit(Object unit)
     *
     * POST Request to create a new Unit
     * @param unit: This is the Unit object that has to be added
     * @return: Object - In this case, Object represents the added Unit object
     */
    @RequestMapping(method = RequestMethod.POST, value = "/units")
    @HystrixCommand(fallbackMethod = "objectFallback")
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
    @HystrixCommand(fallbackMethod = "objectFallback")
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
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteUnit(@PathVariable String unitId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteUnit with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/units/"+unitId;
        LOG.debug("deleteUnit from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultUnit = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultUnit;
    }






    /**
     * Forwards for CONSULTANT
     */
    @RequestMapping(method = RequestMethod.GET, value = "/consultants")
    @HystrixCommand(fallbackMethod = "objectsFallback")
    ResponseEntity<Iterable<Object>> getAllConsultants() {
        LOG.debug("Will call getAllConsultants with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("getAllConsultants from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> consultants = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return consultants;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/consultants/{consultantId}")
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> getConsultantById(@PathVariable String consultantId) {
        LOG.debug("Will call getConsultantById with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("getConsultantById from URL: {}", url);

        ResponseEntity<Object> consultant = restTemplate.getForEntity(url, Object.class);
        return consultant;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/consultants")
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createConsultant(@RequestBody Object consultant) {
        LOG.debug("Will call createConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants";
        LOG.debug("createConsultant from URL: {}", url);

        ResponseEntity<Object> resultConsultant = restTemplate.postForEntity(url, consultant, Object.class);
        return resultConsultant;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/consultants/{consultantId}")
    @HystrixCommand(fallbackMethod = "objectFallback")
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
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteConsultant(@PathVariable String consultantId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("deleteConsultant from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultConsultant = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultConsultant;
    }





    /**
     * Forwards for CONTRACT
     */
    @RequestMapping(method = RequestMethod.GET, value = "/contracts")
    @HystrixCommand(fallbackMethod = "objectsFallback")
    ResponseEntity<Iterable<Object>> getAllContracts() {
        LOG.debug("Will call getAllContracts with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts";
        LOG.debug("getAllContracts from URL: {}", url);


        ParameterizedTypeReference<Iterable<Object>> responseType = new ParameterizedTypeReference<Iterable<Object>>() {};
        ResponseEntity<Iterable<Object>> contracts = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return contracts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contracts/{contractId}")
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> getContractById(@PathVariable String contractId) {
        LOG.debug("Will call getContractById with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/"+contractId;
        LOG.debug("getContractById from URL: {}", url);

        ResponseEntity<Object> contract = restTemplate.getForEntity(url, Object.class);
        return contract;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contracts")
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> createContract(@RequestBody Object contract) {
        LOG.debug("Will call createContract with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts";
        LOG.debug("createContract from URL: {}", url);

        ResponseEntity<Object> resultContract = restTemplate.postForEntity(url, contract, Object.class);
        return resultContract;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/contracts/{contractId}")
    @HystrixCommand(fallbackMethod = "objectFallback")
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
    @HystrixCommand(fallbackMethod = "objectFallback")
    ResponseEntity<Object> deleteContract(@PathVariable String contractId, @RequestHeader HttpHeaders headers) {
        LOG.debug("Will call deleteContract with Hystrix protection");

        URI uri = util.getServiceUrl("unitsidecomposite");
        String url = uri.toString() + "/contracts/"+contractId;
        LOG.debug("deleteContract from URL: {}", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> resultContract = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class);
        return resultContract;
    }



    ResponseEntity<Object> objectFallback(String objectId) {
        return objectFallback();
    }
    ResponseEntity<Object> objectFallback(Object object) {
        return objectFallback();
    }
    ResponseEntity<Object> objectFallback(String objectId, Object object, HttpHeaders headers) {
        return objectFallback();
    }
    ResponseEntity<Object> objectFallback(String objectId, HttpHeaders headers) {
        return objectFallback();
    }

    ResponseEntity<Object> objectFallback() {
        LOG.warn("Using fallback method for unit-side-service");
        return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
    }

    ResponseEntity<Iterable<Object>> objectsFallback() {
        LOG.warn("Using fallback method for unit-side-service");
        return new ResponseEntity<Iterable<Object>>(HttpStatus.BAD_GATEWAY);
    }

}
