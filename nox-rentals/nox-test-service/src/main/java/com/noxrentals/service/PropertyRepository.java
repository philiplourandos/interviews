package com.noxrentals.service;

import com.noxrentals.domain.Property;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author plourand
 */
@RepositoryRestResource(path = "property", collectionResourceRel = "property")
public interface PropertyRepository extends PagingAndSortingRepository<Property, Long> {
    
}
