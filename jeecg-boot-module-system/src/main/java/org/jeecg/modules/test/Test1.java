package org.jeecg.modules.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Jsoup解析html标签时类似于JQuery的一些符号
 *
 * @author chixh
 */
public class Test1 {
    protected List<List<String>> data = new LinkedList<List<String>>();

    /**
     * 获取value值
     *
     * @param e
     * @return
     */
    public static String getValue(Element e) {
        return e.attr("value");
    }

    /**
     * 获取
     * <tr>
     * 和
     * </tr>
     * 之间的文本
     *
     * @param e
     * @return
     */
    public static String getText(Element e) {
        return e.text();
    }

    /**
     * 识别属性id的标签,一般一个html页面id唯一
     *
     * @param body
     * @param id
     * @return
     */
    public static Element getID(String body, String id) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        Elements elements = doc.select("#" + id);
        // 返回第一个
        return elements.first();
    }

    /**
     * 识别属性class的标签
     *
     * @param body
     * @param classTag
     * @return
     */
    public static Elements getClassTag(String body, String classTag) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        return doc.select("." + classTag);
    }

    /**
     * 获取tr标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTR(Element e) {
        return e.getElementsByTag("tr");
    }

    /**
     * 获取td标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTD(Element e) {
        return e.getElementsByTag("td");
    }

    /**
     * 获取表元组
     *
     * @param table
     * @return
     */
    public static List<Map> getTables(Element table) {
        List<Map> data = new ArrayList<>();
        String classStr = "";
        String classStrOverall = null;
        Map<String, Object> map = null;
        Map<String, Object> map2 = new HashMap<>();
        String temp = null;
        for (Element etr : table.select("tr")) {
            Elements etrData = etr.select("td");
//            for (Element etd : etr.select("td")) {
//                classStr = etr.select("h3").attr("class");
//                if (!"".equals(classStr)) {
//                    map = new HashMap<>();
//                    temp = etd.text();
//                    map.put("设备名称", temp);
//                } else {
//
//                }
//            }
            for (int i = 0; i < etrData.size(); i++) {
                classStr = etrData.get(i).select("h3").attr("class");
                if (!"".equals(classStr)) {
                    map = new HashMap<>();
                    temp = etrData.get(i).text();
                    classStrOverall = temp;
                    map2.put("VAL", classStrOverall);
                    map.put("设备名称", temp);
                } else {
                    if (i == 0) {
                        map.put("时间段", etrData.get(i).text());
                    } else if (i == 1) {
                        map.put("可用时间", etrData.get(i).text());
                    }else if (i == 2) {
                        map.put("稼动时间", etrData.get(i).text());
                    }else if(i == 4){
                        map.put("运行时", etrData.get(i).text());
                    }else if(i == 6){
                        map.put("单板", etrData.get(i).text());
                    }else if(i == 7){
                        map.put("元件数", etrData.get(i).text());
                    }
                }
            }
            if ("".equals(classStr)) {
                data.add(map);
                map = new HashMap<>();
                map.put("设备名称", classStrOverall);
            }
        }
        return data;
    }

    /**
     * 读html文件
     *
     * @param fileName
     * @return
     */
    public static String readHtml(String fileName) {
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // String url = "http://www.baidu.com";
        // String body = HtmlBody.getBody(url);
        // System.out.println(body);

//        Document doc = Jsoup.parse(readHtml("./index.html"));
//        // 获取html的标题
//        String title = doc.select("title").text();
//        System.out.println(title);
//        // 获取按钮的文本
//        String btnText = doc.select("div div div div div form").select("#su").attr("value");
//        System.out.println(btnText);
//        // 获取导航栏文本
//        Elements elements = doc.select(".head_wrapper").select("#u1").select("a");
//        for (Element e : elements) {
//            System.out.println(e.text());
//        }
        //String str = readHtml("D:/table.html");
        Document doc2 = Jsoup.parse(readHtml("D:/table.html"));
        Element table = doc2.select("table").get(2);
        List<Map> list = getTables(table);
        System.out.println(list);
//        for (List<String> list2 : list) {
//            for (String string : list2) {
//                System.out.print(string + ",");
//            }
//            System.out.println();
//        }
    }

}
