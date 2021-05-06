package com.minpin.scanevent.services;

import com.minpin.scanevent.model.CartonEvt;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CartonEvtFieldSetMapper implements FieldSetMapper<CartonEvt> {

    @Override
    public CartonEvt mapFieldSet(FieldSet fieldSet) throws BindException {
        CartonEvt cartonEvt = CartonEvt.builder()
                .label(fieldSet.readString("Label"))
                .courier(fieldSet.readString("Courier"))
                .status(fieldSet.readString("Status"))
                .date(fieldSet.readDate("Date"))
                .build();
        return cartonEvt;
    }
}
