package com.partner.test;

import com.partner.exception.IllegalResponsePath;
import com.partner.exception.NoResponseDataException;
import com.partner.model.Event;
import com.partner.model.Reservation;
import com.partner.model.ServiceResponse;
import com.partner.servicecaller.ServiceCallerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class Test {

    private ServiceCallerImpl serviceCaller;

    @Autowired
    public Test(ServiceCallerImpl serviceCaller) {
        this.serviceCaller = serviceCaller;
    }


    @RequestMapping("/test")
    public void test() throws NoSuchAlgorithmException, IllegalResponsePath, NoResponseDataException {
        /*List<Event> eventList = serviceCaller.getEvents("http://localhost:8989","dGVzenQuY2VjaWxpYUBvdHBtb2JpbC5jb20mMzAwMCZFNjg1NjA4NzJCREIyREYyRkZFN0FEQzA5MTc1NTM3OA==");
        System.out.println(eventList.toString());*/
    }
}
