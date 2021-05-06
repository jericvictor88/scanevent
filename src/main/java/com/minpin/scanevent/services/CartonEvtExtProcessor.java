package com.minpin.scanevent.services;

import com.minpin.scanevent.model.CartonEvt;
import com.minpin.scanevent.model.CartonEvtExt;
import org.springframework.batch.item.ItemProcessor;
import java.util.Arrays;
import java.util.List;

public class CartonEvtExtProcessor implements ItemProcessor<CartonEvt, CartonEvtExt> {
    private static List finalEvents = Arrays.asList("Delivered");

    @Override
    public CartonEvtExt process(CartonEvt item) {
        boolean isFinalEvent = false;
        if (finalEvents.contains(item.getStatus())) {
            isFinalEvent = true;
        }

        CartonEvtExt cartonEvtExt = CartonEvtExt.builder()
                .label(item.getLabel())
                .courier(item.getCourier())
                .status(item.getStatus())
                .date(item.getDate())
                .isFinalEvent(isFinalEvent)
                .build();

        return cartonEvtExt;
    }
}
