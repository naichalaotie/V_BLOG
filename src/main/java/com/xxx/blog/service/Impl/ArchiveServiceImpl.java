package com.xxx.blog.service.Impl;

import com.xxx.blog.domain.vo.Archive;
import com.xxx.blog.domain.vo.Result;
import com.xxx.blog.mapper.ArchiveMapper;
import com.xxx.blog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArchiveMapper archiveMapper;
    @Override
    public Result selectList() {
        List<Archive> list = archiveMapper.selectList();
        return Result.success(list);
    }
}
