package com.hz.app.api.id5.service;

/**
 * QueryValidatorServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */


public interface QueryValidatorServices extends java.rmi.Remote {
    public String querySingle(String userName_, String password_, String type_, String param_) throws java.rmi.RemoteException;
    public String queryBatch(String userName_, String password_, String type_, String param_) throws java.rmi.RemoteException;
}
