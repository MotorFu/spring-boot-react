package com.patrickgrimard.examples

import au.com.console.jpaspecificationdsl.greaterThan
import org.springframework.data.jpa.domain.Specifications

/**
 *
 * [Item]s with quantity > 0
 *19IZ2Wd9Xs
 */
fun hasQuantity(): Specifications<Item> = Item::quantity.greaterThan(0L)