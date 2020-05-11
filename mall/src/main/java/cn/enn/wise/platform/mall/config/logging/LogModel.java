package cn.enn.wise.platform.mall.config.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Document(indexName = "api_logs", type = "java")
public class LogModel implements Serializable {
    private static final long serialVersionUID = 6320548148250372657L;

    @Id
    private String id;

    private String method;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss,SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss,SSS", timezone = "GMT+8")
    private Date occrTime;

    private String userInfo;

    private LoggingRequest request;

    private LoggingResponse response;

    public LogModel() {
        this.id = UUID.randomUUID().toString();
        this.occrTime = new Date();
    }
}