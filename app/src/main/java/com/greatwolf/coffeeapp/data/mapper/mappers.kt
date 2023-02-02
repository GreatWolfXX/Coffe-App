package com.greatwolf.coffeeapp.data.mapper

import com.greatwolf.coffeeapp.data.dto.CoffeeDto
import com.greatwolf.coffeeapp.data.dto.CoffeeListDTO
import com.greatwolf.coffeeapp.domain.model.Coffee

fun CoffeeListDTO.toDomain() = this.map { it.toDomain() }

fun CoffeeDto.toDomain() = Coffee(
    description = description,
    id = id,
    image = image,
    title = title
)