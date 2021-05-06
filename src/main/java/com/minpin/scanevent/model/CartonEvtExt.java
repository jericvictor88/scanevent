package com.minpin.scanevent.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CartonEvtExt extends CartonEvt{
    private Boolean isFinalEvent;

}
