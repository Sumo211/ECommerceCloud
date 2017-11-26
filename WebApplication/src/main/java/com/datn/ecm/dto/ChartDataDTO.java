package com.datn.ecm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChartDataDTO {

    private List<String> label;

    private List<Double> values;

}
