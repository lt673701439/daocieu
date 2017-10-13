package com.liketry.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.domain.CensorRecord;
import com.liketry.mapper.CensorRecordMapper;
import com.liketry.service.CensorRecordService;
import com.liketry.web.vm.SmartPageVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CensorRecordServiceImpl extends ServiceImpl<CensorRecordMapper, CensorRecord> implements CensorRecordService {


    public Page<CensorRecord> selectPage(SmartPageVM<CensorRecord> record, Wrapper<CensorRecord> wrapper) {
        CensorRecord search = record.getSearch();
        String order = record.getSort().getPredicate();
        String orderStatus = record.getSort().getReverse() ? "desc" : "asc";
        int nop = record.getPagination().getNumberOfPages();
        int size = record.getPagination().getNumber();
        PageHelper.startPage((nop + 1) / size, size);
        PageInfo info = new PageInfo<CensorRecord>(baseMapper.selectByPage(search.getCrMerchantId(), order, orderStatus));
        Page<CensorRecord> page = new Page<CensorRecord>();
        page.setRecords(info.getList());
        page.setTotal((int) info.getTotal());
        return page;
    }
}