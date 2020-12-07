package com.mkyong.data;

public class Message {
    private int id;

    private long requestTimeMillis;

    private long responseTimeMillis;

    private float timeToLive;

    public Message() {
    }

    public Message(int id, long requestTimeMillis, long responseTimeMillis) {
        this.id = id;
        this.requestTimeMillis = requestTimeMillis;
        this.responseTimeMillis = responseTimeMillis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRequestTimeMillis() {
        return requestTimeMillis;
    }

    public void setRequestTimeMillis(long requestTimeMillis) {
        this.requestTimeMillis = requestTimeMillis;
    }

    public long getResponseTimeMillis() {
        return responseTimeMillis;
    }

    public void setResponseTimeMillis(long responseTimeMillis) {
        this.responseTimeMillis = responseTimeMillis;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        if (0 < id) sb.append("\"id\":" + id);
//        if (0 < requestTimeMillis) sb.append(",\"requestTimeMillis\":" + requestTimeMillis);
//        if (0 < responseTimeMillis) sb.append(",\"responseTimeMillis\":" + responseTimeMillis);
        if (0 < requestTimeMillis && 0 < responseTimeMillis) {
            timeToLive = (float) (responseTimeMillis - requestTimeMillis) / 1000;
            sb.append(",\"timeToLive\":" + timeToLive);
        }
        sb.append("}");
        return sb.toString();
    }
}
