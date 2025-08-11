package com.taoyuan.stardewvalleysavessync.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CustomerWebMvcConfigurer(
    val mappingJackson2HttpMessageConverter: MappingJackson2HttpMessageConverter
): WebMvcConfigurer {

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        converters.remove(mappingJackson2HttpMessageConverter);
        converters.add(1, mappingJackson2HttpMessageConverter);
    }
}