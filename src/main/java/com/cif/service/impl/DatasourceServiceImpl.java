package com.cif.service.impl;

import com.cif.dao.DataSourceMapper;
import com.cif.domain.DatasourceBean;
import com.cif.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 数据源业务实现层
 *
 * @author liutianshuo
 * @Date 2022/8/25
 * @Version 1.0
 **/
@Service
public class DatasourceServiceImpl implements DatasourceService {

    @Autowired
    DataSourceMapper dataSourceMapper;

    @Override
    public DatasourceBean getDataSource(int id) {
        return dataSourceMapper.getDatasource(id);
    }

    @Override
    public DatasourceBean[] getAll() {
        return dataSourceMapper.getAll();
    }

    @Override
    public int addDataSource(DatasourceBean das) {
        return dataSourceMapper.addDatasource(das);
    }

    @Override
    public int deleteDataSource(int id) {
        return dataSourceMapper.deleteDatasource(id);
    }

    @Override
    public int updateDataSource(DatasourceBean das) {
        return dataSourceMapper.updateDatasource(das);
    }
}
