package cn.stone.batch.misc.misc01;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 10:01 2020/9/19
 * @Modified By:
 */

public class EMail {
    public int userId;
    public String subject;
    public String body;
    public EMail() {

    }

    public EMail(int userId, String subject, String body) {
        this.userId = userId;
        this.subject = subject;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EMail{" +
                "userId=" + userId +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
