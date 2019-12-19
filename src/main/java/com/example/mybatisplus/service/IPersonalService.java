package com.example.mybatisplus.service;

import com.example.mybatisplus.entity.Personal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cowwa
 * @since 2019-12-09
 */
public interface IPersonalService extends IService<Personal> {
    public void dataDumpRecord(List<Personal> personalList);
}
