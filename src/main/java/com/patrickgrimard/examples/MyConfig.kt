package com.patrickgrimard.examples

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created with spring-boot-react
 * Author motorfu
 * Date 2017/4/20
 * Time 15:41
 */
@Component
open class MyConfig {
  @Value("\${my.username}")
  var username : String = ""
  @Value("\${my.mobile}")
  var mobile : String = ""
}