package com.fxl.frame.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.XML;

import net.sf.json.xml.XMLSerializer;

/**
 * xml转map，map转xml 带属性 http://happyqing.iteye.com/blog/2316275
 * 
 * @author happyqing
 * @since 2016.8.8
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XmlUtil {
    public static void main(String[] args) throws DocumentException, IOException {

        //String textFromFile = "<request><list><obj><id>11</id><name>aa</name></obj><obj><id>22</id><name>bb</name></obj></list></request>";
        
        String textFromFile = "<ROOT><STATE>T</STATE><DATAPARAM><GHLIST><GH><DJH>S0000168</DJH><HL>普通</HL><YYSJ>2018-04-19 16:19:03</YYSJ><KDKS>口腔一科门诊</KDKS><DJLX>1</DJLX><ZXZT>正在就诊</ZXZT><JE>15</JE><ZFZT>1</ZFZT><SFJSK>0</SFJSK><YZLIST><YZ><YZID>86487352</YZID><YZLX>检验</YZLX><YZMC /><FMLIST><FM><MC>检验费</MC><JE>128</JE><ZFZT>0</ZFZT><MXLIST><MX><MC>血细胞分析</MC><GG /><SL>11</SL><DW>项</DW></MX><MX><MC>血细胞分析（流式细胞仪法每增一项指标）</MC><GG /><SL>16</SL><DW>项</DW></MX><MX><MC>红细胞比积测定(HCT)</MC><GG /><SL>1</SL><DW>项</DW></MX></MXLIST></FM></FMLIST><DJLIST><DJ><DJH>S0000169</DJH><DJLX>1</DJLX><JE>128</JE><KDSJ>2018-04-19 16:44:07</KDSJ><ZFZT>0</ZFZT><YTJE>0</YTJE><SFJSK>0</SFJSK></DJ></DJLIST></YZ></YZLIST></GH></GHLIST></DATAPARAM></ROOT>";
        
        //String textFromFile = "<ROOT xmlns=\"\"><STATE>T</STATE><DATAPARAM><GHLIST /></DATAPARAM></ROOT>";
        
        /*Map<String, Object> map = xml2map(textFromFile, false);
        JSON json = JSONObject.fromObject(map);
        System.out.println(json.toString(1)); // 格式化输出 */
        
        
        /***********************xml转json****************************/
        /*XMLSerializer xmlSerializer = new XMLSerializer();    
        String xmlToString = xmlSerializer.read(textFromFile).toString();    
        com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(xmlToString);
        System.out.println(xmlToString);*/
        
        /*org.json.JSONObject json = XML.toJSONObject(textFromFile);
        org.json.JSONObject gh = (JSONObject) json.getJSONObject("ROOT").getJSONObject("DATAPARAM").getJSONObject("GHLIST").get("GH");*/
        
        XMLSerializer xmlSerializer = new XMLSerializer();
        net.sf.json.JSONObject jsonRet = (net.sf.json.JSONObject) xmlSerializer.read(textFromFile);
        System.out.println(jsonRet);
        /***********************xml转json****************************/
        
        
        String json = "{'STATE':'T','DATAPARAM':{'GHLIST':{'GH':{'DJH':'S0000168','HL':'普通','YYSJ':'2018-04-19 16:19:03','KDKS':'口腔一科门诊','DJLX':'1','ZXZT':'正在就诊','JE':'15','ZFZT':'1','SFJSK':'0','YZLIST':{'YZ':{'YZID':'86487352','YZLX':'检验','YZMC':[],'FMLIST':{'FM':{'MC':'检验费','JE':'128','ZFZT':'0','MXLIST':[{'MC':'血细胞分析','GG':[],'SL':'11','DW':'项'},{'MC':'血细胞分析（流式细胞仪法每增一项指标）','GG':[],'SL':'16','DW':'项'},{'MC':'红细胞比积测定(HCT)','GG':[],'SL':'1','DW':'项'}]}},'DJLIST':{'DJ':{'DJH':'S0000169','DJLX':'1','JE':'128','KDSJ':'2018-04-19 16:44:07','ZFZT':'0','YTJE':'0','SFJSK':'0'}}}}}}}}";
        System.out.println(json2xml(json));
    }
    
    /**
     * json转xml
     * @createTime 2018年7月27日,下午1:57:43
     * @param xml
     * @return
     */
    public static String json2xml(String json) {
        JSONObject jsonObj = new JSONObject(json);
        return "<xml>" + XML.toString(jsonObj) + "</xml>";
    }

    /**
     * xml转map 不带属性
     * @param xmlStr
     * @param needRootKey 是否需要在返回的map里加根节点键
     * @return
     * @throws DocumentException
     */
    public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        Element root = doc.getRootElement();
        Map<String, Object> map = (Map<String, Object>) xml2map(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map;
        }
        if (needRootKey) {
            // 在返回的map里加根节点键（如果需要）
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put(root.getName(), map);
            return rootMap;
        }
        return map;
    }

    /**
     * xml转map 带属性
     * @param xmlStr
     * @param needRootKey 是否需要在返回的map里加根节点键
     * @return
     * @throws DocumentException
     */
    public static Map xml2mapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        Element root = doc.getRootElement();
        Map<String, Object> map = (Map<String, Object>) xml2mapWithAttr(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map; // 根节点只有一个文本内容
        }
        if (needRootKey) {
            // 在返回的map里加根节点键（如果需要）
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put(root.getName(), map);
            return rootMap;
        }
        return map;
    }

    /**
     * xml转map 不带属性
     * @param e
     * @return
     */
    private static Map xml2map(Element e) {
        Map map = new LinkedHashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = xml2map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    /**
     * xml转map 带属性
     * @param e
     * @return
     */
    private static Map xml2mapWithAttr(Element element) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        List<Element> list = element.elements();
        List<Attribute> listAttr0 = element.attributes(); // 当前节点的所有属性的list
        for (Attribute attr : listAttr0) {
            map.put("@" + attr.getName(), attr.getValue());
        }
        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                Element iter = list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = xml2mapWithAttr(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {

                    List<Attribute> listAttr = iter.attributes(); // 当前节点的所有属性的list
                    Map<String, Object> attrMap = null;
                    boolean hasAttributes = false;
                    if (listAttr.size() > 0) {
                        hasAttributes = true;
                        attrMap = new LinkedHashMap<String, Object>();
                        for (Attribute attr : listAttr) {
                            attrMap.put("@" + attr.getName(), attr.getValue());
                        }
                    }

                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            // mapList.add(iter.getText());
                            if (hasAttributes) {
                                attrMap.put("#text", iter.getText());
                                mapList.add(attrMap);
                            } else {
                                mapList.add(iter.getText());
                            }
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            // mapList.add(iter.getText());
                            if (hasAttributes) {
                                attrMap.put("#text", iter.getText());
                                mapList.add(attrMap);
                            } else {
                                mapList.add(iter.getText());
                            }
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        // map.put(iter.getName(), iter.getText());
                        if (hasAttributes) {
                            attrMap.put("#text", iter.getText());
                            map.put(iter.getName(), attrMap);
                        } else {
                            map.put(iter.getName(), iter.getText());
                        }
                    }
                }
            }
        } else {
            // 根节点的
            if (listAttr0.size() > 0) {
                map.put("#text", element.getText());
            } else {
                map.put(element.getName(), element.getText());
            }
        }
        return map;
    }

    /**
     * map转xml map中没有根节点的键
     * @param map
     * @param rootName
     * @throws DocumentException
     * @throws IOException
     */
    public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement(rootName);
        doc.add(root);
        map2xml(map, root);
        // System.out.println(doc.asXML());
        // System.out.println(formatXml(doc));
        return doc;
    }

    /**
     * map转xml map中含有根节点的键
     * @param map
     * @throws DocumentException
     * @throws IOException
     */
    public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        if (entries.hasNext()) { // 获取第一个键创建根节点
            Map.Entry<String, Object> entry = entries.next();
            Document doc = DocumentHelper.createDocument();
            Element root = DocumentHelper.createElement(entry.getKey());
            doc.add(root);
            map2xml((Map) entry.getValue(), root);
            // System.out.println(doc.asXML());
            // System.out.println(formatXml(doc));
            return doc;
        }
        return null;
    }

    /**
     * map转xml
     * @param map
     * @param body xml元素
     * @return
     */
    private static Element map2xml(Map<String, Object> map, Element body) {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.startsWith("@")) { // 属性
                body.addAttribute(key.substring(1, key.length()), value.toString());
            } else if (key.equals("#text")) { // 有属性时的文本
                body.setText(value.toString());
            } else {
                if (value instanceof java.util.List) {
                    List list = (List) value;
                    Object obj;
                    for (int i = 0; i < list.size(); i++) {
                        obj = list.get(i);
                        // list里是map或String，不会存在list里直接是list的，
                        if (obj instanceof java.util.Map) {
                            Element subElement = body.addElement(key);
                            map2xml((Map) list.get(i), subElement);
                        } else {
                            body.addElement(key).setText((String) list.get(i));
                        }
                    }
                } else if (value instanceof java.util.Map) {
                    Element subElement = body.addElement(key);
                    map2xml((Map) value, subElement);
                } else {
                    body.addElement(key).setText(value.toString());
                }
            }
            // System.out.println("Key = " + entry.getKey() + ", Value = " +
            // entry.getValue());
        }
        return body;
    }

    /**
     * 格式化输出xml
     * @param xmlStr
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String formatXml(String xmlStr) throws DocumentException, IOException {
        Document document = DocumentHelper.parseText(xmlStr);
        return formatXml(document);
    }

    /**
     * 格式化输出xml
     * @param document
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String formatXml(Document document) throws DocumentException, IOException {
        // 格式化输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // format.setEncoding("UTF-8");
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();
        return writer.toString();
    }

}
