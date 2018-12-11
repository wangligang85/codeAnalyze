package leon.code;

/**
 * @author wangligang85@163.com on 2018-12-11 16:54
 */
public class Constants {

    public static final String MYSQL_DRIVER_KEY = "org.gjt.mm.mysql.Driver";
    public static final String MYSQL_CONNECT_INFO = "jdbc:mysql://localhost:3306/analyze_source?user=root&password=123456";

    public static final String HTTP_HEADER_KEY_COOKIE = "Cookie";
    public static final String HTTP_HEADER_KEY_ACCEPT = "Accept";
    public static final String HTTP_HEADER_VALUE_COOKIE = "JWT-SESSION=eyJhbGciOiaUl1WWGkiOiJBVxlMiIsInN1YiI6IndhbmdsaWdhJIUzI1NiJ9.eyJqd2VhX0ZsSG5KZHJLbmc5IiwiaWF0IjoxNTQ0NDkzNDg4LCJleHAiOjE1NDQ3NTNDU1LCJ4c3JmVSZWZyZXNoVGltZSI6MTU0NDQ5MzG9rZW4iOiI1NTNjbnQ2Z2Qyc2o1bzhscXBzZWI2ODgsInNzb0xhc3RQ4ODQ0NSwibGFzdFJlZnJlc2hUaW1lIjoxNTQ0NDkzNDg4wwMGR1cyJ9.4SrTxSzGVGsfdXIeXS_ct6K65F-1xHOHPVEGrRsWgwU";
    public static final String HTTP_HEAD_VALUE_ACCEPT = "application/json";
    public static final String HTTP_ENCODING = "UTF-8";
    public static final String HTTP_URL = "http://localhost:8088/api/measures/component?additionalFields=metrics%2Cperiods&metricKeys=alert_status%2Cquality_gate_details%2Cbugs%2Cnew_bugs%2Creliability_rating%2Cnew_reliability_rating%2Cvulnerabilities%2Cnew_vulnerabilities%2Csecurity_rating%2Cnew_security_rating%2Ccode_smells%2Cnew_code_smells%2Csqale_rating%2Cnew_maintainability_rating%2Csqale_index%2Cnew_technical_debt%2Ccoverage%2Cnew_coverage%2Cnew_lines_to_cover%2Ctests%2Cduplicated_lines_density%2Cnew_duplicated_lines_density%2Cduplicated_blocks%2Cncloc%2Cncloc_language_distribution%2Cprojects%2Cnew_lines";

    public static final int STORE_WORKER_THREAD_NUM = 10;
    public static final int HTTP_WORKER_THREAD_NUM = 10;

}
