package com.patrickgrimard.examples

import org.hamcrest.Matchers.hasEntry
import org.hamcrest.Matchers.isA
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 *
 *
 * Created on 2016-12-13
 *
 * @author Patrick
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class ItemService {

    @MockBean
    lateinit var itemRepository: ItemRepository

    @Before
    fun init() {
        given<List<Item>>(itemRepository.findAll()).willReturn(arrayListOf(Item("RxJS", 1)))
    }

    @Test
    fun testChild() {
        itemRepository.findAll()
    }
}