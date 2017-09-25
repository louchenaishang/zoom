package com.github.louchen.zoom.api.store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreEcharts {

    private BigDecimal amount;
    private String name;

}
