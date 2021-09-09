package com.quick.dto.userDto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：彭来祥
 * @date ：Created in 2020/9/17 17:09
 * @description：基础bto
 * @version: $v1.0
 */
@Data
public class BaseDto implements Serializable {



    /**
     * 记录的创建人
     */
    private String  createPsnId;

    /**
     * 记录创建时间
     */
   private Date createTime;

    /**
     * 记录的最近修改人
     */
   private String modifyPsnId;

    /**
     * 记录的最近修改时间
     */
   private Date modifyTime;

    /**
     * 记录删除人
     */
   private String delPsnId;

    /**
     * 记录删除时间
     */
   private Date delPsnTime;

    /**
     * 删除标志 0 未删除，1 已删除
     */
   private Integer delFlg;


}
