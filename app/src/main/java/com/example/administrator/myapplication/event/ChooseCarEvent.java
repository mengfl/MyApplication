package com.example.administrator.myapplication.event;

import com.example.administrator.myapplication.bean.Car;

/**
 * Created by Administrator on 2017/8/14.
 */

public class ChooseCarEvent {
    private Car car;

    public ChooseCarEvent(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
