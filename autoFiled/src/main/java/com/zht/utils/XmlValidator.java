package com.zht.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 根据Schema xsd文件验证 xml 文件
 * </p>
 *
 * @author zht
 * @since 2020-03-24
 */
public class XmlValidator {

    /**
     * 验证xml
     *
     * @param xmlContent xml文件内容
     * @param xsdContent xsd文件内容
     */
    public static void validate(String xmlContent, String xsdContent) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(new ByteArrayInputStream(xsdContent.getBytes(StandardCharsets.UTF_8))));
        Validator validator = schema.newValidator();
        //执行验证，验证失败后将抛出异常
        validator.validate(new StreamSource(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8))));
    }
}
