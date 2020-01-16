package com.dtupay.service;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Singleton
@ApplicationPath("/")
public class RestApplication extends Application {
}
