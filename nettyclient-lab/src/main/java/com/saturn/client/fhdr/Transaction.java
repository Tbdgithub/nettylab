package com.saturn.client.fhdr;

/**
 * Created by john.y on 2017-6-29.
 */
public class Transaction {

    private RequestMsg request;
    private RespBody response;
    private RespFuture future;
    private String tid;

    private Connection connection;


    public Transaction(Connection connection) {
        this.connection = connection;
        this.setTid(String.valueOf(IdGenerator.getNextTid()));


    }

    public void handleResp(RespBody response) {

        System.out.println("handleResp:");
        future.complete(response);
    }

    public RequestMsg getRequest() {
        return request;
    }

    public void setRequest(RequestMsg request) {
        this.request = request;
    }

    public RespBody getResponse() {
        return response;
    }

    public void setResponse(RespBody response) {
        this.response = response;
    }

    public RespFuture getFuture() {
        return future;
    }

    public void setFuture(RespFuture future) {
        this.future = future;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public RespFuture begin() throws Exception {
        RespFuture result = new RespFuture();

        //并发check
        //   if (!future.compareAndSet(null, f)) {
        //   throw new IllegalStateException("already began");
        //}
        //Response respBody = result.getValue();
        this.future = result;
        TransactionManager.Instance.addTransaction(this);
        System.out.println("txid:" + this.getTid());
        this.getRequest().getHeaderIdentity().setTransactionID(Integer.parseInt(this.getTid()));
        connection.sendRequest(this.getRequest());
        return result;

    }


}
