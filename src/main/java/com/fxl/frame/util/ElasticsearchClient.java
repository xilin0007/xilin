package com.fxl.frame.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * elasticsearch java api
 * @Description TODO
 * @author fangxilin
 * @date 2018年8月8日
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2018
 */
public class ElasticsearchClient {
    
    private static RestHighLevelClient client;
    
    static {
        try {
            client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("user", "方熙林");
        source.put("title", "程序员");
        source.put("desc", "苦逼搬砖程序员");
        
        //addDoc("accounts", "person", "3", source);
        
        //System.out.println(isExistsDoc("accounts", "person", "4"));
        
        //System.out.println(deleteDoc("accounts", "person", "3"));
        
        //updateDoc("accounts", "person", "3", source);
        
        //BulkRequest request = new BulkRequest();//一次执行多个请求
        
        client.close();
    }
    
    /**
     * 添加一条文档记录
     * @createTime 2018年8月8日,下午2:44:20
     * @createAuthor fangxilin
     * @param index 索引
     * @param type 类型
     * @param documentId 文档记录id
     * @param source 需要添加的文档记录
     */
    public static void addDoc(String index, String type, String documentId, Map<String, Object> source) {
        IndexRequest request = new IndexRequest(index, type, documentId);
        //request.source(jsonString, XContentType.JSON);
        request.source(source);
        try {
            //同步执行
            Header[] headers = {};
            IndexResponse indexResponse  = client.index(request, headers);
            //异步执行
            /*client.indexAsync(request, new ActionListener<IndexResponse>() {
                
                @Override
                public void onResponse(IndexResponse arg0) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onFailure(Exception arg0) {
                    // TODO Auto-generated method stub
                    
                }
            }, null);*/
            //System.out.println(indexResponse.getResult());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 更新文档
     * @createTime 2018年8月8日,下午6:18:52
     * @createAuthor fangxilin
     * @param index
     * @param type
     * @param documentId
     * @param source
     */
    public static void updateDoc(String index, String type, String documentId, Map<String, Object> source) {
        UpdateRequest request = new UpdateRequest(index, type, documentId);
        //request.doc(jsonString, XContentType.JSON);
        request.doc(source);
        try {
            //同步执行
            Header[] headers = {};
            UpdateResponse response = client.update(request, headers);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 是否存在指定documentId的文档记录
     * @createTime 2018年8月8日,下午5:55:14
     * @createAuthor fangxilin
     * @param index
     * @param type
     * @param documentId
     * @return
     */
    public static boolean isExistsDoc(String index, String type, String documentId) {
        GetRequest request = new GetRequest(index, type, documentId);
        //exists()只返回true或false，建议关闭提取_source和任何存储的字段，以便请求稍微轻一点
        request.fetchSourceContext(new FetchSourceContext(false)); 
        request.storedFields("_none_");
        boolean exists = false;
        try {
            //同步执行
            Header[] headers = {};
            exists = client.exists(request, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    /**
     * 删除指定documentId的文档
     * @createTime 2018年8月8日,下午6:03:03
     * @createAuthor fangxilin
     * @param index
     * @param type
     * @param documentId
     * @return
     */
    public static boolean deleteDoc(String index, String type, String documentId) {
        DeleteRequest request = new DeleteRequest(index, type, documentId);
        //exists()只返回true或false，建议关闭提取_source和任何存储的字段，以便请求稍微轻一点
        boolean ret = true;
        try {
            //同步执行
            Header[] headers = {};
            DeleteResponse delete = client.delete(request, headers);
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }
    
    public static void query() throws IOException {
        //SearchRequest request = new SearchRequest();
        SearchRequest request = new SearchRequest("accounts");//只搜索accounts索引下的
        request.types("person");//搜索person类型下的
        SearchSourceBuilder ssBuilder = new SearchSourceBuilder();
        
        //MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();//matchAll
        //TermQueryBuilder query = QueryBuilders.termQuery("user", "fang");//查询包含xxx的文档
        MatchQueryBuilder query = QueryBuilders.matchQuery("user", "fang");
        query.fuzziness(Fuzziness.AUTO);//在匹配查询上启用模糊匹配
        query.prefixLength(3);//匹配查询上设置前缀长度选项
        query.maxExpansions(10);//设置最大扩展选项以控制查询的模糊过程
        
        ssBuilder.from(0);
        ssBuilder.size(5);
        ssBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        ssBuilder.query(query);
        //排序
        ssBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        
        request.source(ssBuilder);
        request.preference("_local");//选择本地分片执行搜索,默认跨分片随机化
        /**
         * 请求聚合 TermsAggregationBuilder, 请求建议 SuggestionBuilder
         */


        //异步执行请求
        /*ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        client.searchAsync(request, listener);*/

        //同步执行请求
        SearchResponse searchResponse = client.search(request);
        RestStatus status = searchResponse.status();//搜索请求先用状态码
        TimeValue took = searchResponse.getTook();//响应时间
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();//是否终止
        boolean timedOut = searchResponse.isTimedOut();//是否超时

        int totalShards = searchResponse.getTotalShards();//请求的分片总数
        int successfulShards = searchResponse.getSuccessfulShards();//请求成功的分片数
        int failedShards = searchResponse.getFailedShards();//失败的分片数
        for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
            // failures should be handled here
        }

        //搜索结果
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();//搜索请求命中数
        float maxScore = hits.getMaxScore();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();//获取index
            String type = hit.getType();//获取type
            String id = hit.getId();//获取document id
            float score = hit.getScore();

            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String documentTitle = (String) sourceAsMap.get("title");
            List<Object> users = (List<Object>) sourceAsMap.get("user");
            Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");

            //检索突出显示
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            Text[] fragments = highlight.fragments();
            String fragmentString = fragments[0].string();
        }




    }

    /**
     * 搜索模板请求
     */
    public static void queryByTemplate() throws IOException {
        //搜索模板请求
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("posts"));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(
                "{" +
                        "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                        "  \"size\" : \"{{size}}\"" +
                        "}");

        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "title");
        scriptParams.put("value", "elasticsearch");
        scriptParams.put("size", 5);
        request.setScriptParams(scriptParams);

        //同步执行
        SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
        //异步执行
        //client.searchTemplateAsync(request, RequestOptions.DEFAULT, listener);


        //多搜索模板请求
//        MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();


    }
}
