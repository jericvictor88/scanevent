package com.minpin.scanevent.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartonEvt {
    private String label;
    private String courier;
    private String status;
    private Date date;
}
