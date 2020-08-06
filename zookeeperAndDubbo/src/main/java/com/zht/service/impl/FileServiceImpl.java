package com.zht.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.zht.dto.UploadFileDTO;
import com.zht.service.FileService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.zht.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author ZHT
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public String saveFile(byte[] fileBytes, String fileName, String fileType) {
        ObjectId objectId = gridFsTemplate.store(new ByteArrayInputStream(fileBytes), fileName, fileType);
        return objectId.toString();
    }

    @Override
    public UploadFileDTO getFileData(String objectId) throws IOException {
        GridFSFile fs = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)));
        GridFsResource resource = gridFsTemplate.getResource(fs);
        //创建文件对象
        UploadFileDTO file = new UploadFileDTO();
        file.setName(resource.getFilename());
        file.setContentType(resource.getContentType());
        file.setObjectId(objectId);
        file.setFileByte(IOUtils.toByteArray(resource.getInputStream()));
        return file;
    }
}
