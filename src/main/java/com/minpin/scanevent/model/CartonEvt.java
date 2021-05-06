package com.minpin.scanevent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartonEvt {
    private String label;
    private String courier;
    private String status;
    private Date date;
}
