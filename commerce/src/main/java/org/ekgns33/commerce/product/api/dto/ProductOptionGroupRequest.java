package org.ekgns33.commerce.product.api.dto;

import java.util.List;

public record ProductOptionGroupRequest(
    String name, Integer displayOrder, List<ProductOptionRequest> options) {}
