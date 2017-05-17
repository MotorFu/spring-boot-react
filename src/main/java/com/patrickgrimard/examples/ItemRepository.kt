package com.patrickgrimard.examples

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository

/**
 *
 *
 * Created on 2016-12-03
 *
 * @author Patrick
 */
@Repository
interface ItemRepository : JpaRepository<Item, String>, JpaSpecificationExecutor<Item>