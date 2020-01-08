package com.baizhi.pwl.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

  @Id
  private String id;
  private String title;
  private String url;
  private Double size;
  private String time;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  private Date createTime;
  private String albumId;

}
