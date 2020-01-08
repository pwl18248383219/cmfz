package com.baizhi.pwl.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course implements Serializable {

  @Id
  private String id;
  private String title;
  private String userId;
  private String type;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;

}
