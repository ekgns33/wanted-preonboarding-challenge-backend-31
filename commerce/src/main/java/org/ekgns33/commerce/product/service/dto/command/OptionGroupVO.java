package org.ekgns33.commerce.product.service.dto.command;

import java.util.List;

public record OptionGroupVO(String name, Integer displayOrder, List<OptionVO> optionVOS) {}
