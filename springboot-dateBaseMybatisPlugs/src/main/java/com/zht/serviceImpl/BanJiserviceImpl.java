package com.zht.serviceImpl;

import com.zht.base.AbstractCrudService;
import com.zht.entity.BanJi;
import com.zht.mapper.BanJiMapper;
import com.zht.service.BanJiService;
import org.springframework.stereotype.Service;

@Service
public class BanJiserviceImpl extends AbstractCrudService<BanJiMapper, BanJi> implements BanJiService {
}
