package com.minpin.scanevent.services;

import com.minpin.scanevent.model.CartonEvt;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartonEvtPreparedStatemeSetter implements ItemPreparedStatementSetter<CartonEvt> {
    @Override
    public void setValues(CartonEvt item, PreparedStatement ps) throws SQLException {
        ps.setString(1, item.getLabel());
        ps.setString(2, item.getCourier());
        ps.setString(3, item.getStatus());
        ps.setDate(4, new Date(item.getDate().getTime()));
    }
}
